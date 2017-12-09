package com.rayhahah.easysports.app;

import com.rayhahah.rbase.utils.base.FileUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量配置类
 */
public class C {


    public static final int RESPONSE_SUCCESS = 0;
    public static final int RESPONSE_FAILED = 1;
    public static final String APP_NAME = "EasySport";

    public static final int TYPE_COMMENT = 1001;
    public static final int TYPE_FEEDBACK = 1002;
    public static final int TYPE_AT = 1003;
    public static final int TYPE_POST = 1004;
    public static final int TYPE_REPLY = 1005;
    public static final int TYPE_QUOTE = 1006;
    public static final int DEFAULT_SIZE = 10;

    public interface DIR {
        String PIC_DIR = FileUtils.getRootFilePath() + "EasySport/images";
        String CRASH = FileUtils.getRootFilePath() + "EasySport/crashLog";
        String SONIC = FileUtils.getRootFilePath() + "EasySport/sonic";
        String FILE = FileUtils.getRootFilePath() + "EasySport/file";
    }

    //数据库名字
    public static final String DB_EASYSPORTS = "easysports.db";

    //打印Log的标签
    public static final String LOG_TAG = "lzh";

    public static final String DEVICE_ID = "Android";

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
        String RAYMALL = "http://mall.rayhahah.com/";
        //        String RAYMALL = "http://rayhahah.s1.natapp.cc/raymall/";
        String RAY_FTP = "ftp://88.128.18.163:21/raymall/";
    }

    /**
     * EventBus Action常量保存类
     */
    public interface EventAction {

        String UPDATE_CURRENT_USER = "UPDATE_CURRENT_USER";
        String REFRESH_MATCH_DATA = "REFRESH_MATCH_DATA";
    }


    /**
     * SharePreferences常量保存类
     */
    public interface SP {
        String THEME = "THEME";
        String IS_LOGIN = "IS_LOGIN";
        String TAG_MINE_SELECTED = "TAG_MINE_SELECTED";
        String CURRENT_USER = "CURRENT_USER";
        String HUPU_TOKEN = "TOKEN";
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
    public @interface FORUM {

        int ITEM_TYPE_CONTENT = 0;

        int ITEM_TYPE_TITLE = 1;
        String LAST_TAMP = "";
        int LIMIT = 20;
        String TYPE_FORUM_HOT = "2";//热帖
        String TYPE_FORUM_NEW = "1";//新帖
        String SHARE_TITLE = "SHARE_TITLE";
        String SHARE_URL = "SHARE_URL";
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
    public @interface MATCH {
        String INTENT_MID = "MID";

        String MATCH_DATA = "比赛数据";
        String MATCH_PLAYER = "技术统计";
        String MATCH_LIVE = "图文直播";
        String MATCH_VIDEO = "视频集锦";
        String MATCH_FORWARD = "比赛前瞻";
        String BUNDLE_MID = "MID";
        String BUNDLE_INFO = "INFO";
        int ITEM_TYPE_FORWARD_MAX = 101;
        int ITEM_TYPE_FORWARD_STATUS = 102;
        int ITEM_TYPE_FORWARD_HISTORY = 103;
        int ITEM_TYPE_MATCH_DATA_POINT = 12;
        int ITEM_TYPE_MATCH_DATA_TEAM_COUNT = 14;
        int ITEM_TYPE_MATCH_DATA_TEAM_BEST = 13;

        //比赛数据
        String TAB_TPYE_MATCHDATA = "1";
        //技术统计
        String TAB_TPYE_STROKE = "2";
        //比赛前瞻
        String TAB_TPYE_FORWARD = "3";
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
        int ID_LIVE = 10;

        String USERNAME = "username";
        String PASSWORD = "password";
        String SCREENNAME = "screenname";
        String QUESTION = "question";
        String ANSWER = "answer";
        String PHONE = "phone";
        String EMAIL = "email";
        String HUPU_USERNAME = "hupuUsername";
        String HUPU_PASSWORD = "hupuPassword";
        String INTENT_URL = "URL";
        String INTENT_TITLE = "TITLE";
        int CODE_REQUEST_AUDIO = 2001;

    }


    @Retention(RetentionPolicy.SOURCE)
    public @interface ACCOUNT {
        int ID_RESET_PASSWORD = 1;
        int ID_HUPU = 2;
        int ID_TEL = 3;
        int ID_SCREENNAME = 4;
        int ID_HUPU_BIND = 5;
        int ID_SETTING = 6;
        int CODE_TAKE_PHOTO = 101;
        int CODE_CHOOSE_PHOTO = 102;
        int PERMISSION_PHOTO = 1001;
        int PERMISSION_CAMERA = 1002;
        String DEFAULT_HUPU_NICKNAME = "Rayhahaha";
        String DEFAULT_HUPU_UID = "31362159";
        String DEFAULT_HUPU_TOKEN = "MzEzNjIxNTg=|MTUxMjc5NTc2MA==|5b0cf4bbdca9f34e45b4b1017ff93732";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FORGET {
        int TAG_GET_QUESTION = 0;
        int TAG_ANSWER = 1;
        int TAG_RESET_PASSWORD = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CRASH {
        String VERSION_NAME = "versionName";
        String VERSION_CODE = "";
        String EXCEPTION_INFO = "";
        String DEVICE_INFO = "";
        String SYSTEM_INFO = "";
        String SECURE_INFO = "";
        String MEMORY_INFO = "";
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
