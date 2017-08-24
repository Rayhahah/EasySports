package com.rayhahah.easysports.app;

import com.rayhahah.rbase.utils.base.FileUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量配置类
 */
public class C {


    public interface DIR {
        String PIC_DIR = FileUtils.getRootFilePath() + "EasySport/images";
        String CRASH = FileUtils.getRootFilePath() + "EasySport/crashLog";
        String SONIC = FileUtils.getRootFilePath() + "EasySport/sonic";
    }

    //数据库名字
    public static final String DB_EASYSPORTS = "easysports.db";

    //打印Log的标签
    public static final String LOG_TAG = "lzh";

    public static final String DeviceId = "Android";

    @Retention(RetentionPolicy.SOURCE)
    public @interface AppKey {
        //Bmob APPID配置
        String BMOB_APPID = "3c3ed1d32d5cc139e228eed122e7dedc";

        //阿里云
        String ALIYUN_KEY = "24594916-1";
        String ALIYUN_SECRECT = "7452a4dc80e687201f2a41ecc61a3c24";

    }

    /**
     * 主机地址常量保存类
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface BaseURL {
        String HUPU_FORUM_SERVER = "http://bbs.mobileapi.hupu.com/1/7.0.8/";
        String HUPU_GAMES_SERVER = "http://games.mobileapi.hupu.com/1/7.0.8/";
        String HUPU_LOGIN_SERVER = "http://passport.hupu.com/";
        String TECENT_VIDEO_SERVER = "http://vv.video.qq.com";
        String TECENT_VIDEO_SERVER_H5 = "http://h5vv.video.qq.com";
        String TECENT_SERVER = "http://sportsnba.qq.com";
        String TMIAAO_SERVER = "http://nba.tmiaoo.com";
    }

    /**
     * EventBus Action常量保存类
     */
    public interface EventAction {

        String UPDATE_CURRENT_USER = "UPDATE_CURRENT_USER";
    }


    /**
     * SharePreferences常量保存类
     */
    public interface SP {
        String THEME = "THEME";
        String IS_LOGIN = "IS_LOGIN";
        String TAG_MINE_SELECTED = "TAG_MINE_SELECTED";
        String CURRENT_USER = "CURRENT_USER";
        String TOKEN = "TOKEN";
        String HUPU_UID = "uid";
        String HUPU_NICKNAME = "HUPU_NICKNAME";
    }

    /**
     * 主题属性常量保存类
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface ATTRS {
        String COLOR_PRIMARY = "COLOR_PRIMARY";
        String COLOR_PRIMARY_DARK = "COLOR_PRIMARY_DARK";
        String COLOR_ACCENT = "COLOR_ACCENT";
        String COLOR_TEXT_LIGHT = "COLOR_TEXT_LIGHT";
        String COLOR_TEXT_DARK = "COLOR_TEXT_DARK";
        String COLOR_BG = "COLOR_BG";
        String COLOR_BG_DARK = "COLOR_BG_DARK";
    }

    /**
     * 列表数据状态
     */
    public interface STATUS {
        int INIT = 1001;
        int REFRESH = 1002;
        int LOAD_MORE = 1003;
        int NULL = 1000;
    }

    /**
     * 新闻模块常量
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface NEWS {
        String BANNER = "banner";
        String NEWS = "news";
        String VIDEOS = "videos";
        String DEPTH = "depth";
        String HIGHLIGHT = "highlight";

        String TAB_INDEX = "TAB_INDEX";
        String TAB_TYPE = "TAB_TYPE";

        int ITEM_TYPE_ARTICLE = 0;
        int ITEM_TYPE_VIDEOS = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface INFO {
        int ID_EAST = 11;
        int ID_WEST = 12;
        int ID_POINT = 0;
        int ID_REBOUND = 1;
        int ID_ASSIST = 2;
        int ID_BLOCK = 3;
        int ID_STEAL = 4;

        int TYPE_TEAM = 101;
        int TYPE_PLAYER = 102;

        String POINT = "point";
        String REBOUND = "rebound";
        String ASSIST = "assist";
        String BLOCK = "block";
        String STEAL = "steal";

        String CURRENT_SEASON = "2016";

        String TAG_TYPE_DAILY = "0";
        String TAG_TYPE_SEASON = "1";
        String TAG_TYPE_NORMAL = "2";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MINE {
        int ID_LOGIN = 1;
        int ID_TEAM = 2;
        int ID_PLAYER = 3;
        int ID_VERSION = 4;
        int ID_THEME = 5;
        int ID_CLEAN = 6;
        int ID_FEEDBACK = 7;
        int ID_ABOUT = 8;
        int ID_QRCODE = 9;
    }


    @Retention(RetentionPolicy.SOURCE)
    public @interface ACCOUNT {
        int ID_RESET_PASSWORD = 1;
        int ID_HUPU = 2;
        int ID_TEL = 3;
        int ID_SCREENNAME = 4;
        int ID_HUPU_BIND = 5;
        int CODE_TAKE_PHOTO = 101;
        int CODE_CHOOSE_PHOTO = 102;
    }

    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String NULL = "";


    public interface Utils {
        //广告过滤字符集
        String[] AD = {"/d/js/js/"
                , "u.xcy8.com"
                , "http://nba.tmiaoo.com/body.html"
                , "http://nba.tmiaoo.com/gg.html"
                , "http://img.ychtjd88.com"
                , "http://hm.baidu.com"
                , "http://img.jgchq.com"
                , "http://img1.pxpbj.com"
                , "http://img1.pxpbj.com"};

    }

}
