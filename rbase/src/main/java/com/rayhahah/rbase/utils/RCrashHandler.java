package com.rayhahah.rbase.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import com.rayhahah.rbase.base.ActivityCollector;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.RLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @Github https://github.com/Rayhahah
 * @time 2017/6/4
 * @fuction 捕获应用Crash信息，同时上传到服务器和写入本地文件保存
 * @usage App中getInstance().init(context, crashUploader)
 */
public class RCrashHandler implements Thread.UncaughtExceptionHandler {

    //Log Tag
    public static final String TAG = "RCrashHandler";
    //异常信息
    public static final String EXCEPETION_INFOS_STRING = "EXCEPETION_INFOS_STRING";
    //应用包信息
    public static final String PACKAGE_INFOS_MAP = "PACKAGE_INFOS_MAP";
    //设备数据信息
    public static final String BUILD_INFOS_MAP = "BUILD_INFOS_MAP";
    //系统常规配置信息
    public static final String SYSTEM_INFOS_MAP = "SYSTEM_INFOS_MAP";
    //手机安全配置信息
    public static final String SECURE_INFOS_MAP = "SECURE_INFOS_MAP";
    //内存情况信息
    public static final String MEMORY_INFOS_STRING = "MEMORY_INFOS_STRING";
    public static final String VERSION_NAME = "versionName";
    public static final String VERSION_CODE = "versionCode";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static volatile RCrashHandler INSTANCE;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private ConcurrentHashMap<String, Object> infos = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> mPackageInfos = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> mDeviceInfos = new ConcurrentHashMap<>();
    private String mMemInfos = new String();
    private ConcurrentHashMap<String, String> mSystemInfos = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> mSecureInfos = new ConcurrentHashMap<>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private static String mDirPath;
    private String mExceptionInfos;
    //上传文件具体实现
    private CrashUploader mCrashUploader;

    private RCrashHandler(String dirPath) {
        mDirPath = dirPath;
        File mDirectory = new File(mDirPath);
        if (!mDirectory.exists()) {
            mDirectory.mkdirs();
        }
    }

    public static RCrashHandler getInstance(String dirPath) {
        if (INSTANCE == null) {
            synchronized (RCrashHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RCrashHandler(dirPath);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context       上下文
     * @param crashUploader 崩溃信息上传接口回调
     */
    public void init(Context context, CrashUploader crashUploader) {
        mCrashUploader = crashUploader;
        mContext = context;
        //保存一份系统默认的CrashHandler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //使用我们自定义的异常处理器替换程序默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，系统将会自动调用uncaughtException方法
     *
     * @param t 出现未捕获异常的线程
     * @param e 未捕获的异常，有了这个ex，我们就可以得到异常信息
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!catchCrashException(e) && mDefaultHandler != null) {
            //没有自定义的CrashHandler的时候就调用系统默认的异常处理方式
            mDefaultHandler.uncaughtException(t, e);
        } else {
            //退出应用
            killProcess();
        }
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean catchCrashException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(mContext, CrashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityCollector.finishAll();
                mContext.startActivity(intent);
            }
        }.start();
        //收集设备参数信息
        collectInfos(mContext, ex);
        //保存日志文件
        saveCrashInfo2File();
        //上传崩溃信息
        uploadCrashMessage(infos);

        return true;
    }

    /**
     * 退出应用
     */
    public static void killProcess() {
        //结束应用
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showLong("哎呀，程序发生异常啦...");
                Looper.loop();
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            RLog.e("CrashHandler.InterruptedException--->" + ex.toString());
        }
        //退出程序
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    /**
     * 获取设备参数信息
     *
     * @param context
     */
    private void collectInfos(Context context, Throwable ex) {
        mExceptionInfos = collectExceptionInfos(ex);
        collectPackageInfos(context);
        collectBuildInfos();
        collectSystemInfos();
        collectSecureInfos();
        mMemInfos = collectMemInfos();

        //将信息储存到一个总的Map中提供给上传动作回调
        infos.put(EXCEPETION_INFOS_STRING, mExceptionInfos);
        infos.put(PACKAGE_INFOS_MAP, mPackageInfos);
        infos.put(BUILD_INFOS_MAP, mDeviceInfos);
        infos.put(SYSTEM_INFOS_MAP, mSystemInfos);
        infos.put(SECURE_INFOS_MAP, mSecureInfos);
        infos.put(MEMORY_INFOS_STRING, mMemInfos);
    }

    /**
     * 将崩溃日志信息写入本地文件
     */
    private String saveCrashInfo2File() {
        StringBuffer mStringBuffer = getInfosStr(mPackageInfos);
        mStringBuffer.append(mExceptionInfos);
        // 保存文件，设置文件名
        String mTime = formatter.format(new Date());
        String mFileName = "CrashLog-" + mTime + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File mDirectory = new File(mDirPath);
                Log.v(TAG, mDirectory.toString());
                if (!mDirectory.exists()) {
                    mDirectory.mkdirs();
                }
                FileOutputStream mFileOutputStream = new FileOutputStream(mDirectory + File.separator + mFileName);
                mFileOutputStream.write(mStringBuffer.toString().getBytes());
                mFileOutputStream.close();
                return mFileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 上传崩溃信息到服务器
     */
    public void uploadCrashMessage(ConcurrentHashMap<String, Object> infos) {
        mCrashUploader.uploadCrashMessage(infos);
    }


    /**
     * 获取捕获异常的信息
     *
     * @param ex
     */
    private String collectExceptionInfos(Throwable ex) {
        Writer mWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mWriter);
        ex.printStackTrace(mPrintWriter);
        ex.printStackTrace();
        Throwable mThrowable = ex.getCause();
        // 迭代栈队列把所有的异常信息写入writer中
        while (mThrowable != null) {
            mThrowable.printStackTrace(mPrintWriter);
            // 换行 每个个异常栈之间换行
            mPrintWriter.append("\r\n");
            mThrowable = mThrowable.getCause();
        }
        // 记得关闭
        mPrintWriter.close();
        return mWriter.toString();
    }

    /**
     * 获取内存信息
     */
    private String collectMemInfos() {
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();

        ArrayList<String> commandLine = new ArrayList<>();
        commandLine.add("dumpsys");
        commandLine.add("meminfo");
        commandLine.add(Integer.toString(Process.myPid()));
        try {
            java.lang.Process process = Runtime.getRuntime()
                    .exec(commandLine.toArray(new String[commandLine.size()]));
            br = new BufferedReader(new InputStreamReader(process.getInputStream()), 8192);

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取系统安全设置信息
     */
    private void collectSecureInfos() {
        Field[] fields = Settings.Secure.class.getFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Deprecated.class)
                    && field.getType() == String.class
                    && field.getName().startsWith("WIFI_AP")) {
                try {
                    String value = Settings.Secure.getString(mContext.getContentResolver(), (String) field.get(null));
                    if (value != null) {
                        mSecureInfos.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取系统常规设定属性
     */
    private void collectSystemInfos() {
        Field[] fields = Settings.System.class.getFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Deprecated.class)
                    && field.getType() == String.class) {
                try {
                    String value = Settings.System.getString(mContext.getContentResolver(), (String) field.get(null));
                    if (value != null) {
                        mSystemInfos.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从系统属性中提取设备硬件和版本信息
     */
    private void collectBuildInfos() {
        // 反射机制
        Field[] mFields = Build.class.getDeclaredFields();
        // 迭代Build的字段key-value 此处的信息主要是为了在服务器端手机各种版本手机报错的原因
        for (Field field : mFields) {
            try {
                field.setAccessible(true);
                mDeviceInfos.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取应用包参数信息
     */
    private void collectPackageInfos(Context context) {
        try {
            // 获得包管理器
            PackageManager mPackageManager = context.getPackageManager();
            // 得到该应用的信息，即主Activity
            PackageInfo mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (mPackageInfo != null) {
                String versionName = mPackageInfo.versionName == null ? "null" : mPackageInfo.versionName;
                String versionCode = mPackageInfo.versionCode + "";
                mPackageInfos.put(VERSION_NAME, versionName);
                mPackageInfos.put(VERSION_CODE, versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将HashMap遍历转换成StringBuffer
     */
    @NonNull
    public static StringBuffer getInfosStr(ConcurrentHashMap<String, String> infos) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            mStringBuffer.append(key + "=" + value + "\r\n");
        }
        return mStringBuffer;
    }

    /**
     * 崩溃信息上传接口回调
     */
    public interface CrashUploader {
        void uploadCrashMessage(ConcurrentHashMap<String, Object> infos);
    }
}
