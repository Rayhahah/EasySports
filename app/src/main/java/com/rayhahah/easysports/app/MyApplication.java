package com.rayhahah.easysports.app;

import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import com.rayhahah.easysports.common.C;
import com.rayhahah.rbase.net.ApiClient;
import com.rayhahah.rbase.net.OkHttpManager;
import com.rayhahah.greendao.gen.DaoMaster;
import com.rayhahah.greendao.gen.DaoSession;
import com.rayhahah.rbase.BaseApplication;

import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;

/**
 * Created by a on 2017/5/9.
 */

public class MyApplication extends BaseApplication {


    private static DaoSession daoSession;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(mAppContext, C.BMOB_APPID);

//        CrashHandler.getInstance().init(mAppContext);

        initRetrofit();

        initGreenDao();
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
