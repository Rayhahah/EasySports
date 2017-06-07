package com.rayhahah.easysports.app;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import com.rayhahah.easysports.bean.CrashMessage;
import com.rayhahah.easysports.common.C;
import com.rayhahah.greendao.gen.DaoMaster;
import com.rayhahah.greendao.gen.DaoSession;
import com.rayhahah.rbase.BaseApplication;
import com.rayhahah.rbase.net.ApiClient;
import com.rayhahah.rbase.net.OkHttpManager;
import com.rayhahah.rbase.utils.RCrashHandler;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.useful.RLog;

import java.util.concurrent.ConcurrentHashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.OkHttpClient;

public class MyApplication extends BaseApplication {


    private static DaoSession daoSession;
    private RCrashHandler.CrashUploader mCrashUploader;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(mAppContext, C.BMOB_APPID);

//        initCrashHandler();

        initRetrofit();

        initGreenDao();
    }

    /**
     * 初始化崩溃处理器
     */
    private void initCrashHandler() {

        mCrashUploader = new RCrashHandler.CrashUploader() {
            @Override
            public void uploadCrashMessage(ConcurrentHashMap<String, Object> infos) {
                CrashMessage cm = new CrashMessage();
                ConcurrentHashMap<String, String> packageInfos = (ConcurrentHashMap<String, String>) infos.get(RCrashHandler.PACKAGE_INFOS_MAP);
                cm.setDate(DateTimeUitl.getCurrentWithFormate(DateTimeUitl.sysDateFormate));
                cm.setVersionName(packageInfos.get(RCrashHandler.VERSION_NAME));
                cm.setVersionCode(packageInfos.get(RCrashHandler.VERSION_CODE));
                cm.setExceptionInfos(((String) infos.get(RCrashHandler.EXCEPETION_INFOS_STRING)));
                cm.setMemoryInfos((String) infos.get(RCrashHandler.MEMORY_INFOS_STRING));
                cm.setDeviceInfos(RCrashHandler.getInfosStr((ConcurrentHashMap<String, String>) infos
                        .get(RCrashHandler.BUILD_INFOS_MAP)).toString());
                cm.setSystemInfoss(RCrashHandler.getInfosStr((ConcurrentHashMap<String, String>) infos
                        .get(RCrashHandler.SYSTEM_INFOS_MAP)).toString());
                cm.setSecureInfos(RCrashHandler.getInfosStr((ConcurrentHashMap<String, String>) infos
                        .get(RCrashHandler.SECURE_INFOS_MAP)).toString());
                cm.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            RLog.e("上传成功！");

                        } else {
                            RLog.e("上传Bmob失败 错误码：" + e.getErrorCode());
                        }
                    }
                });
            }
        };
        RCrashHandler.getInstance(FileUtils.getRootFilePath() + "EasySport/crashLog")
                .init(mAppContext, mCrashUploader);
    }

    /**
     * 初始化RetrofitClient
     */
    private void initRetrofit() {
        OkHttpClient okHttpClient = OkHttpManager.create();
        ApiClient.create(C.BaseURL.TECENT_URL_SERVER, okHttpClient);
        ApiClient.create(C.BaseURL.TECENT_URL_SERVER_1, okHttpClient);
        ApiClient.create(C.BaseURL.TENCENT_SERVER, okHttpClient);
        ApiClient.create(C.BaseURL.TMIAAO_SERVER, okHttpClient);
        ApiClient.create(C.BaseURL.HUPU_FORUM_SERVER, okHttpClient);
        ApiClient.create(C.BaseURL.HUPU_GAMES_SERVER, okHttpClient);
        ApiClient.create(C.BaseURL.HUPU_LOGIN_SERVER, okHttpClient);
    }

    /**
     * 初始化数据库信息
     */
    private void initGreenDao() {
        //创建数据库db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, C.DB_EASYSPORTS, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    /**
     * 获取表操作类 DAO
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }

}
