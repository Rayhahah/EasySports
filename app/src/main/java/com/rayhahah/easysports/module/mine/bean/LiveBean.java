package com.rayhahah.easysports.module.mine.bean;

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
 * @time 2017/9/18
 * @tips 这个类是Object的子类
 * @fuction
 */
public class LiveBean {


    /**
     * status : 0
     * msg : 获取直播列表成功
     * data : [{"liveStatus":"1","streamId":"11148_easysportd15E9D1D0A4F","username":"test","picUrl":"","screenname":"tt","flvUrl":"http://11148..livepush.myqcloud.com/live/11148_easysportd15E9D1D0A4F.flv","hlsUrl":"http://11148..livepush.myqcloud.com/live/11148_easysportd15E9D1D0A4F.m3u8","rtmpUrl":"rtmp://11148..livepush.myqcloud.com/live/11148_easysportd15E9D1D0A4F"},{"liveStatus":"1","streamId":"11148_easysporta15E9D1D0E2E","username":"test","picUrl":"","screenname":"tt","flvUrl":"http://11148..livepush.myqcloud.com/live/11148_easysporta15E9D1D0E2E.flv","hlsUrl":"http://11148..livepush.myqcloud.com/live/11148_easysporta15E9D1D0E2E.m3u8","rtmpUrl":"rtmp://11148..livepush.myqcloud.com/live/11148_easysporta15E9D1D0E2E"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * liveStatus : 1
         * streamId : 11148_easysportd15E9D1D0A4F
         * username : test
         * picUrl :
         * screenname : tt
         * flvUrl : http://11148..livepush.myqcloud.com/live/11148_easysportd15E9D1D0A4F.flv
         * hlsUrl : http://11148..livepush.myqcloud.com/live/11148_easysportd15E9D1D0A4F.m3u8
         * rtmpUrl : rtmp://11148..livepush.myqcloud.com/live/11148_easysportd15E9D1D0A4F
         */

        private String liveStatus;
        private String streamId;
        private String username;
        private String picUrl;
        private String screenname;
        private String flvUrl;
        private String hlsUrl;
        private String rtmpUrl;

        public String getLiveStatus() {
            return liveStatus;
        }

        public void setLiveStatus(String liveStatus) {
            this.liveStatus = liveStatus;
        }

        public String getStreamId() {
            return streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getScreenname() {
            return screenname;
        }

        public void setScreenname(String screenname) {
            this.screenname = screenname;
        }

        public String getFlvUrl() {
            return flvUrl;
        }

        public void setFlvUrl(String flvUrl) {
            this.flvUrl = flvUrl;
        }

        public String getHlsUrl() {
            return hlsUrl;
        }

        public void setHlsUrl(String hlsUrl) {
            this.hlsUrl = hlsUrl;
        }

        public String getRtmpUrl() {
            return rtmpUrl;
        }

        public void setRtmpUrl(String rtmpUrl) {
            this.rtmpUrl = rtmpUrl;
        }
    }
}
