package com.rayhahah.easysports.module.match.bean;

import com.rayhahah.easysports.bean.BaseSection;

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
public class MatchListBean {
    /**
     * code : 0
     * data : {"matches":[{"updateFrequency":"1800","matchInfo":{"matchType":"2","mid":"100000:1470069","leftId":"9","leftName":"勇士","leftBadge":"http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/9.png","leftGoal":"0","leftHasUrl":"1","leftDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=9","rightId":"5","rightDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=5","rightHasUrl":"1","rightName":"骑士","rightBadge":"http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/5.png","rightGoal":"0","matchDesc":"季后赛","startTime":"2017-06-08 09:00:00","title":"","logo":"","matchPeriod":"0","quarter":"","quarterTime":"12:00","liveType":"3","tabs":[{"type":"1","desc":"比赛前瞻"},{"type":"4","desc":"图文直播"}],"broadcasters":["腾讯","CCTV"],"isBook":"-1","phaseText":"","playoffDesc":{"text":"系列赛 2-0","url":"http://m.china.nba.com/articles/licensee_widget_bracket.html"}}}],"updateFrequency":"1800","today":"2017-06-05"}
     * version : 63c56b53d76d09915930a3738a29c56d
     */
    private int code;
    private DataBean data;
    private String version;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class DataBean {
        /**
         * matches : [{"updateFrequency":"1800","matchInfo":{"matchType":"2","mid":"100000:1470069","leftId":"9","leftName":"勇士","leftBadge":"http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/9.png","leftGoal":"0","leftHasUrl":"1","leftDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=9","rightId":"5","rightDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=5","rightHasUrl":"1","rightName":"骑士","rightBadge":"http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/5.png","rightGoal":"0","matchDesc":"季后赛","startTime":"2017-06-08 09:00:00","title":"","logo":"","matchPeriod":"0","quarter":"","quarterTime":"12:00","liveType":"3","tabs":[{"type":"1","desc":"比赛前瞻"},{"type":"4","desc":"图文直播"}],"broadcasters":["腾讯","CCTV"],"isBook":"-1","phaseText":"","playoffDesc":{"text":"系列赛 2-0","url":"http://m.china.nba.com/articles/licensee_widget_bracket.html"}}}]
         * updateFrequency : 1800
         * today : 2017-06-05
         */

        private String updateFrequency;
        private String today;
        private List<MatchesBean> matches;

        public String getUpdateFrequency() {
            return updateFrequency;
        }

        public void setUpdateFrequency(String updateFrequency) {
            this.updateFrequency = updateFrequency;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public List<MatchesBean> getMatches() {
            return matches;
        }

        public void setMatches(List<MatchesBean> matches) {
            this.matches = matches;
        }

        public static class MatchesBean {
            /**
             * updateFrequency : 1800
             * matchInfo : {"matchType":"2","mid":"100000:1470069","leftId":"9","leftName":"勇士","leftBadge":"http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/9.png","leftGoal":"0","leftHasUrl":"1","leftDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=9","rightId":"5","rightDetailUrl":"http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=5","rightHasUrl":"1","rightName":"骑士","rightBadge":"http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/5.png","rightGoal":"0","matchDesc":"季后赛","startTime":"2017-06-08 09:00:00","title":"","logo":"","matchPeriod":"0","quarter":"","quarterTime":"12:00","liveType":"3","tabs":[{"type":"1","desc":"比赛前瞻"},{"type":"4","desc":"图文直播"}],"broadcasters":["腾讯","CCTV"],"isBook":"-1","phaseText":"","playoffDesc":{"text":"系列赛 2-0","url":"http://m.china.nba.com/articles/licensee_widget_bracket.html"}}
             */

            private String updateFrequency;
            private MatchInfoBean matchInfo;

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

            public static class MatchInfoBean extends BaseSection {
                /**
                 * matchType : 2
                 * mid : 100000:1470069
                 * leftId : 9
                 * leftName : 勇士
                 * leftBadge : http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/9.png
                 * leftGoal : 0
                 * leftHasUrl : 1
                 * leftDetailUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=9
                 * rightId : 5
                 * rightDetailUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=5
                 * rightHasUrl : 1
                 * rightName : 骑士
                 * rightBadge : http://mat1.gtimg.com/chinanba/images/nbateamlogo/126x90/5.png
                 * rightGoal : 0
                 * matchDesc : 季后赛
                 * startTime : 2017-06-08 09:00:00
                 * title :
                 * logo :
                 * matchPeriod : 0
                 * quarter :
                 * quarterTime : 12:00
                 * liveType : 3
                 * tabs : [{"type":"1","desc":"比赛前瞻"},{"type":"4","desc":"图文直播"}]
                 * broadcasters : ["腾讯","CCTV"]
                 * isBook : -1
                 * phaseText :
                 * playoffDesc : {"text":"系列赛 2-0","url":"http://m.china.nba.com/articles/licensee_widget_bracket.html"}
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
                private String isBook;
                private String phaseText;
                private PlayoffDescBean playoffDesc;
                private List<TabsBean> tabs;
                private List<String> broadcasters;

                public String getId(){
                    return startTime+leftName+rightName;
                }

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

                public String getIsBook() {
                    return isBook;
                }

                public void setIsBook(String isBook) {
                    this.isBook = isBook;
                }

                public String getPhaseText() {
                    return phaseText;
                }

                public void setPhaseText(String phaseText) {
                    this.phaseText = phaseText;
                }

                public PlayoffDescBean getPlayoffDesc() {
                    return playoffDesc;
                }

                public void setPlayoffDesc(PlayoffDescBean playoffDesc) {
                    this.playoffDesc = playoffDesc;
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

                public static class PlayoffDescBean {
                    /**
                     * text : 系列赛 2-0
                     * url : http://m.china.nba.com/articles/licensee_widget_bracket.html
                     */

                    private String text;
                    private String url;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }

                public static class TabsBean {
                    /**
                     * type : 1
                     * desc : 比赛前瞻
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
    }
}
