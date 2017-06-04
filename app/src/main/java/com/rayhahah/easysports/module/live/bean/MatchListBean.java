package com.rayhahah.easysports.module.live.bean;

import java.util.List;

/**
 * Created by a on 2017/6/1.
 */

public class MatchListBean {


    /**
     * liveId :
     * userNum : 1508
     * updateFrequency : 1800
     * matchInfo : {"matchType":"2","mid":"100000:1467919","leftId":"4","leftName":"公牛","leftBadge":"http://mat1.gtimg.com/sports/nba/logo/1602/4.png","leftGoal":"98","leftHasUrl":"1","leftDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=4","rightId":"11","rightDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=11","rightHasUrl":"1","rightName":"步行者","rightBadge":"http://mat1.gtimg.com/sports/nba/logo/1602/11.png","rightGoal":"96","matchDesc":"NBA常规赛","startTime":"2016-03-30 07:00:00","title":"","logo":"","matchPeriod":"2","quarter":"第4节","quarterTime":"00:00","liveType":"4","tabs":[{"type":"3","desc":"技术统计"},{"type":"5","desc":"精彩视频"}],"broadcasters":["BesTV"]}
     */

    private String liveId;
    private String userNum;
    private String updateFrequency;
    private MatchInfoBean matchInfo;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public MatchInfoBean getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(MatchInfoBean matchInfo) {
        this.matchInfo = matchInfo;
    }

    public static class MatchInfoBean {
        /**
         * matchType : 2
         * mid : 100000:1467919
         * leftId : 4
         * leftName : 公牛
         * leftBadge : http://mat1.gtimg.com/sports/nba/logo/1602/4.png
         * leftGoal : 98
         * leftHasUrl : 1
         * leftDetailUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=4
         * rightId : 11
         * rightDetailUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=11
         * rightHasUrl : 1
         * rightName : 步行者
         * rightBadge : http://mat1.gtimg.com/sports/nba/logo/1602/11.png
         * rightGoal : 96
         * matchDesc : NBA常规赛
         * startTime : 2016-03-30 07:00:00
         * title :
         * logo :
         * matchPeriod : 2
         * quarter : 第4节
         * quarterTime : 00:00
         * liveType : 4
         * tabs : [{"type":"3","desc":"技术统计"},{"type":"5","desc":"精彩视频"}]
         * broadcasters : ["BesTV"]
         */

        private String matchType;
        private String mid;
        private String leftId;
        private String leftName;
        private String leftBadge;
        private String leftGoal;
        private String leftHasUrl;
        private String leftDetailUrl;
        private String rightId;
        private String rightDetailUrl;
        private String rightHasUrl;
        private String rightName;
        private String rightBadge;
        private String rightGoal;
        private String matchDesc;
        private String startTime;
        private String title;
        private String logo;
        private String matchPeriod;
        private String quarter;
        private String quarterTime;
        private String liveType;
        private List<TabsBean> tabs;
        private List<String> broadcasters;

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getLeftId() {
            return leftId;
        }

        public void setLeftId(String leftId) {
            this.leftId = leftId;
        }

        public String getLeftName() {
            return leftName;
        }

        public void setLeftName(String leftName) {
            this.leftName = leftName;
        }

        public String getLeftBadge() {
            return leftBadge;
        }

        public void setLeftBadge(String leftBadge) {
            this.leftBadge = leftBadge;
        }

        public String getLeftGoal() {
            return leftGoal;
        }

        public void setLeftGoal(String leftGoal) {
            this.leftGoal = leftGoal;
        }

        public String getLeftHasUrl() {
            return leftHasUrl;
        }

        public void setLeftHasUrl(String leftHasUrl) {
            this.leftHasUrl = leftHasUrl;
        }

        public String getLeftDetailUrl() {
            return leftDetailUrl;
        }

        public void setLeftDetailUrl(String leftDetailUrl) {
            this.leftDetailUrl = leftDetailUrl;
        }

        public String getRightId() {
            return rightId;
        }

        public void setRightId(String rightId) {
            this.rightId = rightId;
        }

        public String getRightDetailUrl() {
            return rightDetailUrl;
        }

        public void setRightDetailUrl(String rightDetailUrl) {
            this.rightDetailUrl = rightDetailUrl;
        }

        public String getRightHasUrl() {
            return rightHasUrl;
        }

        public void setRightHasUrl(String rightHasUrl) {
            this.rightHasUrl = rightHasUrl;
        }

        public String getRightName() {
            return rightName;
        }

        public void setRightName(String rightName) {
            this.rightName = rightName;
        }

        public String getRightBadge() {
            return rightBadge;
        }

        public void setRightBadge(String rightBadge) {
            this.rightBadge = rightBadge;
        }

        public String getRightGoal() {
            return rightGoal;
        }

        public void setRightGoal(String rightGoal) {
            this.rightGoal = rightGoal;
        }

        public String getMatchDesc() {
            return matchDesc;
        }

        public void setMatchDesc(String matchDesc) {
            this.matchDesc = matchDesc;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getMatchPeriod() {
            return matchPeriod;
        }

        public void setMatchPeriod(String matchPeriod) {
            this.matchPeriod = matchPeriod;
        }

        public String getQuarter() {
            return quarter;
        }

        public void setQuarter(String quarter) {
            this.quarter = quarter;
        }

        public String getQuarterTime() {
            return quarterTime;
        }

        public void setQuarterTime(String quarterTime) {
            this.quarterTime = quarterTime;
        }

        public String getLiveType() {
            return liveType;
        }

        public void setLiveType(String liveType) {
            this.liveType = liveType;
        }

        public List<TabsBean> getTabs() {
            return tabs;
        }

        public void setTabs(List<TabsBean> tabs) {
            this.tabs = tabs;
        }

        public List<String> getBroadcasters() {
            return broadcasters;
        }

        public void setBroadcasters(List<String> broadcasters) {
            this.broadcasters = broadcasters;
        }

        public static class TabsBean {
            /**
             * type : 3
             * desc : 技术统计
             */

            private String type;
            private String desc;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
