package com.rayhahah.easysports.module.match.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author Rayhahah
 * @blog http://rayhahah.com
 * @time 2017/9/30
 * @tips 这个类是Object的子类
 * @fuction
 */
public class LiveDetail {

    public LiveDetailData data;

    public static class LiveDetailData implements Serializable {
        public TeamInfo teamInfo;
        public List<LiveContent> detail;
    }

    public static class TeamInfo implements Serializable {
        public String leftName;
        public String rightName;
    }

    public static class LiveContent implements Serializable {
        public String id;
        public String ctype;
        public String content;
        public String type;
        public String quarter;
        public String time;
        public String teamId;
        public String plus;
        public String sendTime;
        public String topIndex;
        public String version;
        public String leftGoal;
        public String rightGoal;
        public String teamName;

        public CommentatorBean commentator;
        public ImageBean image;
        public VideoBean video;
    }

    public static class CommentatorBean {
        public String role;
        public String nick;
        public String logo;
    }

    public static class ImageBean {
        public List<UrlsBean> urls;
    }

    public static class UrlsBean {
        public String imageType;
        public String imageSize;
        public String small;
        public String large;
    }

    public static class VideoBean {
        public String vid;
        public String covers;
        public String area;
        public String langue;
        public String title;
        public String secondtitle;
        public String sectitle;
        public String desc;
        public String type;
        public String type_name;
        public String drm;
        public String duration;
        public String pic;
        public String pic_160x90;
        public String pic_496x280;
        public String playurl;
        public String playright;
        public String state;
        public String src;
        public String mtime;
        public String checkuptime;
        public String sync_cover;
        public String is_trailer;
        public String view_all;
        public String view_tod;
        public String view_yed;
        public String year;
        public String author;
        public String copyright_id;
        public String copyright;
        public String cmscheck;
        public String competitionid;
        public String matchid;
        public String weishi;
        public String ugc;
        public String imgurl;
        public String imgurl2;
        @SerializedName("abstract")
        public String abstractX;
        public String url;

    }
}
