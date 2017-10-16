package com.rayhahah.rbase.utils.useful;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static com.rayhahah.rbase.utils.useful.RLog.TYPE.E;
import static com.rayhahah.rbase.utils.useful.RLog.TYPE.FILE;
import static com.rayhahah.rbase.utils.useful.RLog.TYPE.JSON;
import static com.rayhahah.rbase.utils.useful.RLog.TYPE.XML;


/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @time 2017/3/29
 * @fuction 一个优化Log显示的工具类
 * @use Application中构造器初始化，其他方式和log一样
 * 轻量级，支持本地
 */
public class RLog {

    private static TYPE mLogType = TYPE.V; // 默认Log过滤为VerBose
    private static String mGlobalTag = "RLog";  //默认的全局Tag
    private static boolean mSwitchLog = true; // log是否启用的开关
    private static boolean mLogBorder = false; //是否打印边框
    private static String dir = ""; //log写入文件的目录
    private static String mFileName = "DefaultLog";// 全局log 写入的文件名

    //边框
    private static final String TOP_BORDER = "╔═══════════════════════════════════════════════════════════════════════════════════════════════════";
    private static final String LEFT_BORDER = "║ ";
    private static final String BOTTOM_BORDER = "╚═══════════════════════════════════════════════════════════════════════════════════════════════════";
    //换行标识符
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String NULL = "null";
    private static final String ARGS = "args";
    //每行最大字符数
    public static final int LINE_MAX_WORD = 3000;

    private RLog() {
    }

    public static void init(Builder builder) {
        mSwitchLog = builder.mSwitchLog;
        mGlobalTag = builder.mGlobalTag;
        mLogType = builder.mLogType;
        mLogBorder = builder.mLogBorder;
        dir = builder.dir;
        mFileName = builder.mFileName;
    }

    /**
     * 各种Log类型过滤
     *
     * @param contents
     */

    public static void v(Object contents) {
        log(TYPE.V, mGlobalTag, contents);
    }

    public static void v(String tag, Object... contents) {
        log(TYPE.V, tag, contents);
    }

    public static void d(Object contents) {
        log(TYPE.D, mGlobalTag, contents);
    }

    public static void d(String tag, Object... contents) {
        log(TYPE.D, tag, contents);
    }

    public static void i(Object contents) {
        log(TYPE.I, mGlobalTag, contents);
    }

    public static void i(String tag, Object... contents) {
        log(TYPE.I, tag, contents);
    }

    public static void w(Object contents) {
        log(TYPE.W, mGlobalTag, contents);
    }

    public static void w(String tag, Object... contents) {
        log(TYPE.W, tag, contents);
    }

    public static void e(Object contents) {
        log(E, mGlobalTag, contents);
    }

    public static void e(String tag, Object... contents) {
        log(E, tag, contents);
    }

    public static void a(Object contents) {
        log(TYPE.A, mGlobalTag, contents);
    }

    public static void a(String tag, Object... contents) {
        log(TYPE.A, tag, contents);
    }

    public static void file(Object contents) {
        log(FILE, mGlobalTag, contents);
    }

    public static void file(String tag, Object contents) {
        log(FILE, tag, contents);
    }

    public static void file(String fileName, String tag, Object contents) {
        log(fileName, tag, contents);
    }


    public static void json(String contents) {
        log(JSON, mGlobalTag, contents);
    }

    public static void json(String tag, String contents) {
        log(JSON, tag, contents);
    }

    public static void xml(String contents) {
        log(XML, mGlobalTag, contents);
    }

    public static void xml(String tag, String contents) {
        log(XML, tag, contents);
    }


    /**
     * Log 核心实现类
     *
     * @param type     过滤类型
     * @param tag      Log标签
     * @param contents Log内容
     */
    private static void log(TYPE type, String tag, Object... contents) {
        if (!mSwitchLog) {
            return;
        }
        String msg = processContents(type, contents);
        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case A:
                realLog(type, tag, msg);
                break;
            case FILE:
                logToFile(E, tag, msg);
                break;
            case JSON:
                realLog(E, tag, msg);
                break;
            case XML:
                realLog(E, tag, msg);
                break;
            default:
                break;
        }
    }

    /**
     * 写入log到指定文件中
     *
     * @param fileName
     * @param tag
     * @param contents
     */
    private static void log(String fileName, String tag, Object... contents) {
        if (!mSwitchLog) {
            return;
        }
        String msg = processContents(FILE, contents);
        logToFile(E, fileName, tag, msg);
    }

    /**
     * 编辑整理Log内容，包括任务栈信息
     *
     * @param type
     * @param contents
     * @return
     */
    private static String processContents(TYPE type, Object... contents) {
        StackTraceElement targetElement = Thread.currentThread().getStackTrace()[5];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0];
        }

        //Log任务栈位置信息追溯
        String head = new Formatter()
                .format("Thread: %s, %s(%s.java:%d)" + LINE_SEPARATOR,
                        Thread.currentThread().getName(),
                        targetElement.getMethodName(),
                        className,
                        targetElement.getLineNumber())
                .toString();
        String msg = "";
        if (contents != null) {
            //只传入一个打印对象
            if (contents.length == 1) {
                Object object = contents[0];
                msg = object == null ? NULL : object.toString();
                if (type == JSON) {
                    msg = formatJson(msg);
                } else if (type == XML) {
                    msg = formatXml(msg);
                }
            } else {
                //传入多个打印对象
                //需要表示出每个打印对象的顺序
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append(ARGS)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(content == null ? NULL : content.toString())
                            .append(LINE_SEPARATOR);
                }
                msg = sb.toString();
            }
        }
        //根据要打印的信息
        //在信息的头部添加边框
        if (mLogBorder) {
            StringBuilder sb = new StringBuilder();
            String[] lines = msg.split(LINE_SEPARATOR);
            for (String line : lines) {
                sb.append(LEFT_BORDER).append(line).append(LINE_SEPARATOR);
            }
            msg = sb.toString();
        }
        return head + msg;
    }


    /**
     * 打印Log到文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void logToFile(final TYPE type, String fileName, final String tag, String msg) {
        synchronized (RLog.class) {
            try {
                String fullPath = dir + fileName + ".txt";
                final File file = new File(fullPath);
                if (!file.exists()) {
                    boolean newFile = file.createNewFile();
                    if (!newFile) {
                        log(type, tag, "create logFile failed!");
                        return;
                    }
                }

                String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ").format(new Date());
                StringBuilder sb = new StringBuilder();
                if (mLogBorder) {
                    sb.append(TOP_BORDER).append(LINE_SEPARATOR);
                }
                sb.append(time)
                        .append(tag)
                        .append(": ")
                        .append(msg)
                        .append(LINE_SEPARATOR);
                if (mLogBorder) {
                    sb.append(BOTTOM_BORDER).append(LINE_SEPARATOR);
                }

                final String writeMsg = sb.toString();

                new Thread() {
                    @Override
                    public void run() {
                        BufferedWriter bw = null;
                        try {
                            bw = new BufferedWriter(new FileWriter(file));
                            bw.write(writeMsg, 0, writeMsg.length());
                            bw.flush();
                            log(type, tag, "log into file success!");
                        } catch (
                                IOException e)

                        {
                            log(type, tag, "log into file failed!");
                            e.printStackTrace();
                        } finally

                        {
                            if (bw != null) {
                                try {
                                    bw.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }.start();
            } catch (IOException e) {
                log(type, tag, "write to file failed!");
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印Log到文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void logToFile(TYPE type, String tag, String msg) {
        logToFile(type, mFileName, tag, msg);
    }

    /**
     * 打印Log
     *
     * @param type
     * @param tag
     * @param msg
     */
    private static void realLog(TYPE type, String tag, String msg) {
        if (mLogBorder) {
            logBorder(type, tag, true);
        }
        int length = msg.length();
        int count = length / LINE_MAX_WORD;
        if (count > 0) {
            int index = 0;
            for (int i = 0; i < count; i++) {
                printLog(type, tag, msg.substring(index, index + LINE_MAX_WORD));
                index += LINE_MAX_WORD;
            }
            printLog(type, tag, msg.substring(index, length));
        } else {
            printLog(type, tag, msg);
        }
        if (mLogBorder) {
            logBorder(type, tag, false);
        }
    }

    private static void printLog(TYPE type, String tag, String msg) {
        if (mLogBorder) {
            msg = LEFT_BORDER + msg;
        }
        switch (type) {
            case V:
                Log.v(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case A:
                Log.wtf(tag, msg);
                break;
            default:
                break;
        }
    }

    /**
     * 打印上下边框
     *
     * @param type
     * @param tag
     * @param isTop
     */
    private static void logBorder(TYPE type, String tag, boolean isTop) {
        String border = isTop ? TOP_BORDER : BOTTOM_BORDER;
        switch (type) {
            case V:
                Log.v(tag, border);
                break;
            case D:
                Log.d(tag, border);
                break;
            case I:
                Log.i(tag, border);
                break;
            case W:
                Log.w(tag, border);
                break;
            case E:
                Log.e(tag, border);
                break;
            case A:
                Log.wtf(tag, border);
                break;
            default:
                break;
        }
    }


    /**
     * Json字符串格式转换
     *
     * @param json
     * @return
     */
    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Xml格式字符串转换
     *
     * @param xml
     * @return
     */
    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEPARATOR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }


    /**
     * 打印过滤器
     */
    public enum TYPE {
        V, //Verbose
        D, // Debug
        I, //Info
        W, // Warm
        E, // Error
        A,  // Assert
        FILE, // Write to File
        JSON, // Parse Json
        XML //Parse XML
    }

    /**
     * Log 配置方法
     * -------------------------------------------------------
     */


    public static class Builder {

        private String dir = "";
        private boolean mSwitchLog = true;
        private String mGlobalTag = "RLog";
        private boolean mLogBorder = true;
        private TYPE mLogType = TYPE.V;
        private String mFileName = "DefaultLog";

        /**
         * 上下文设置
         *
         * @param context
         */
        public Builder(Context context) {
            //判断外存设备是否就绪
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                this.dir = context.getExternalCacheDir() + File.separator + "log" + File.separator;
            } else {
                this.dir = context.getCacheDir() + File.separator + "log" + File.separator;
            }
        }

        /**
         * 设置是否开启log
         *
         * @param isLog
         * @return
         */
        public Builder isLog(boolean isLog) {
            this.mSwitchLog = isLog;
            return this;
        }

        /**
         * 全局的Log 标签
         *
         * @param tag
         * @return
         */
        public Builder setTag(String tag) {
            this.mGlobalTag = tag;
            return this;
        }

        /**
         * log是否带标签
         *
         * @param isLogBorder
         * @return
         */
        public Builder isLogBorder(boolean isLogBorder) {
            this.mLogBorder = isLogBorder;
            return this;
        }

        /**
         * 设置Log过滤器
         *
         * @param type
         * @return
         */
        public Builder setLogType(TYPE type) {
            this.mLogType = type;
            return this;
        }


        /**
         * 自定义Log写入文件路径
         *
         * @param dirPath
         * @return
         */
        public Builder setLogFileDir(String dirPath) {
            this.dir = dirPath;
            return this;
        }


        /**
         * 设置全局的log写入目标文件
         *
         * @param fileName
         * @return
         */
        public Builder setLogFileName(String fileName) {
            this.mFileName = fileName;
            return this;
        }
    }
}
