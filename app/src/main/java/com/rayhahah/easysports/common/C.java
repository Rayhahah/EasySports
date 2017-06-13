package com.rayhahah.easysports.common;

/**
 * 常量配置类
 */
public class C {

    //数据库名字
    public static final String DB_EASYSPORTS = "easysports.db";

    //打印Log的标签
    public static final String LOG_TAG = "lzh";

    //Bmob APPID配置
    public static final String BMOB_APPID = "3c3ed1d32d5cc139e228eed122e7dedc";

    /**
     * 主机地址常量保存类
     */
    public interface BaseURL {
        String HUPU_FORUM_SERVER = "http://bbs.mobileapi.hupu.com/1/7.0.8/";
        String HUPU_GAMES_SERVER = "http://games.mobileapi.hupu.com/1/7.0.8/";
        String HUPU_LOGIN_SERVER = "http://passport.hupu.com/";
        String TECENT_URL_SERVER = "http://vv.video.qq.com";
        String TECENT_URL_SERVER_1 = "http://h5vv.video.qq.com";
        String TECENT_SERVER = "http://sportsnba.qq.com";
        String TMIAAO_SERVER = "http://nba.tmiaoo.com";
    }

    /**
     * EventBus Action常量保存类
     */
    public interface EventAction {

    }

    /**
     * SharePreferences常量保存类
     */
    public interface SP {

        String THEME = "THEME";
    }

    /**
     * 主题属性常量保存类
     */
    public interface ATTRS {
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
    public interface NEWS {
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

    public static final String TRUE = "true";
    public static final String FALSE = "false";
}
