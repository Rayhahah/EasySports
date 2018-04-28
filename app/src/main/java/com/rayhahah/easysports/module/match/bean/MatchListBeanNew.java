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
 * @time 2018/4/28
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchListBeanNew {


    /**
     * code : 0
     * data : {"2018-04-28":[{"matchType":"2","mid":"100000:1467029","leftId":"28","leftName":"猛龙","leftBadge":"http://img1.gtimg.com/sports/pics/hv1/133/21/2268/147482188.png","leftGoal":"102","leftHasUrl":"1","rightId":"27","rightName":"奇才","rightBadge":"http://mat1.gtimg.com/sports/nba/logo/1602/27.png","rightGoal":"92","rightHasUrl":"1","matchDesc":"NBA季后赛","startTime":"2018-04-28 07:00:00","title":"","logo":"","matchPeriod":"2","livePeriod":"2","quarter":"第4节","quarterTime":"00:00","liveType":"4","liveId":"100209400","programId":"51239","isPay":"0","groupName":"","competitionId":"100000","tvLiveId":"100206100","ifHasPlayback":"1","url":"","categoryId":"0","scheduleId":"","leftScore":"4","rightScore":"2","latestNews":{"newsId":"20180428012376","site":"sports","title":"洛瑞24+6带队打破魔咒 猛龙4-2淘汰奇才晋级","pub_time":"2018-04-28 09:28:15","url":"http://sports.qq.com/a/20180428/012376.htm","atype":"2","articleType":"1","competitionId":"100000","matchId":"1467029","pushTime":"2018-04-28 10:06:54"},"VURL":"http://v.qq.com/cover/q/qqwq0kklv2edc9m.html","week":"星期六","leftEnName":"Raptors","rightEnName":"Wizards"},{"matchType":"2","mid":"100000:1470010","leftId":"5","leftName":"骑士","leftBadge":"http://img1.gtimg.com/sports/pics/hv1/131/116/2220/144385211.png","leftGoal":"87","leftHasUrl":"1","rightId":"11","rightName":"步行者","rightBadge":"http://img1.gtimg.com/sports/pics/hv1/43/116/2220/144385123.png","rightGoal":"121","rightHasUrl":"1","matchDesc":"NBA季后赛","startTime":"2018-04-28 08:00:00","title":"","logo":"","matchPeriod":"2","livePeriod":"2","quarter":"第4节","quarterTime":"00:00","liveType":"4","liveId":"100204100","programId":"51240","isPay":"0","groupName":"","competitionId":"100000","tvLiveId":"100204300","ifHasPlayback":"1","url":"","categoryId":"0","scheduleId":"","leftScore":"3","rightScore":"3","latestNews":{"newsId":"20180428016335","site":"sports","title":"詹皇里程碑却遭遇四重打击 他身边究竟谁是帮手","pub_time":"2018-04-28 10:37:16","url":"http://sports.qq.com/a/20180428/016335.htm","atype":"2","articleType":"1","competitionId":"100000","matchId":"1470010","pushTime":"2018-04-28 10:37:37"},"VURL":"http://v.qq.com/cover/6/6dmd8kcvx61pv4o.html","week":"星期六","leftEnName":"Cavaliers","rightEnName":"Pacers"},{"matchType":"2","mid":"100000:1471521","leftId":"25","leftName":"雷霆","leftBadge":"http://mat1.gtimg.com/sports/nba/logo/1602/25.png","leftGoal":"0","leftHasUrl":"1","rightId":"26","rightName":"爵士","rightBadge":"http://mat1.gtimg.com/sports/nba/logo/1602/26.png","rightGoal":"0","rightHasUrl":"1","matchDesc":"NBA季后赛","startTime":"2018-04-28 10:30:00","title":"","logo":"","matchPeriod":"1","livePeriod":"1","quarter":"进行中","quarterTime":"","liveType":"3","liveId":"100209400","programId":"51241","isPay":"0","groupName":"","competitionId":"100000","tvLiveId":"100206100","ifHasPlayback":"0","url":"","categoryId":"0","scheduleId":"","leftScore":"2","rightScore":"3","latestNews":{"newsId":"20180427025318","site":"sports","title":"28日视频直播雷霆vs爵士 韦少乔治欲绝地求生","pub_time":"2018-04-27 13:48:47","url":"http://sports.qq.com/a/20180427/025318.htm","atype":"2","articleType":"7","competitionId":"100000","matchId":"1471521","pushTime":"2018-04-27 15:21:06"},"week":"星期六","leftEnName":"Thunder","rightEnName":"Jazz"}]}
     * version : 99e90bb9963468e92a721f0f20866b3e
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
        private List<MatchesBean> matches;

        public List<MatchesBean> getMatches() {
            return matches;
        }

        public void setMatches(List<MatchesBean> matches) {
            this.matches = matches;
        }

        public static class MatchesBean  extends BaseSection {
            /**
             * matchType : 2
             * mid : 100000:1467029
             * leftId : 28
             * leftName : 猛龙
             * leftBadge : http://img1.gtimg.com/sports/pics/hv1/133/21/2268/147482188.png
             * leftGoal : 102
             * leftHasUrl : 1
             * rightId : 27
             * rightName : 奇才
             * rightBadge : http://mat1.gtimg.com/sports/nba/logo/1602/27.png
             * rightGoal : 92
             * rightHasUrl : 1
             * matchDesc : NBA季后赛
             * startTime : 2018-04-28 07:00:00
             * title :
             * logo :
             * matchPeriod : 2
             * livePeriod : 2
             * quarter : 第4节
             * quarterTime : 00:00
             * liveType : 4
             * liveId : 100209400
             * programId : 51239
             * isPay : 0
             * groupName :
             * competitionId : 100000
             * tvLiveId : 100206100
             * ifHasPlayback : 1
             * url :
             * categoryId : 0
             * scheduleId :
             * leftScore : 4
             * rightScore : 2
             * latestNews : {"newsId":"20180428012376","site":"sports","title":"洛瑞24+6带队打破魔咒 猛龙4-2淘汰奇才晋级","pub_time":"2018-04-28 09:28:15","url":"http://sports.qq.com/a/20180428/012376.htm","atype":"2","articleType":"1","competitionId":"100000","matchId":"1467029","pushTime":"2018-04-28 10:06:54"}
             * VURL : http://v.qq.com/cover/q/qqwq0kklv2edc9m.html
             * week : 星期六
             * leftEnName : Raptors
             * rightEnName : Wizards
             */

            private String matchType;
            private String mid;
            private String leftId;
            private String leftName;
            private String leftBadge;
            private String leftGoal;
            private String leftHasUrl;
            private String rightId;
            private String rightName;
            private String rightBadge;
            private String rightGoal;
            private String rightHasUrl;
            private String matchDesc;
            private String startTime;
            private String title;
            private String logo;
            private String matchPeriod;
            private String livePeriod;
            private String quarter;
            private String quarterTime;
            private String liveType;
            private String liveId;
            private String programId;
            private String isPay;
            private String groupName;
            private String competitionId;
            private String tvLiveId;
            private String ifHasPlayback;
            private String url;
            private String categoryId;
            private String scheduleId;
            private String leftScore;
            private String rightScore;
            private LatestNewsBean latestNews;
            private String VURL;
            private String week;
            private String leftEnName;
            private String rightEnName;

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

            public String getRightId() {
                return rightId;
            }

            public void setRightId(String rightId) {
                this.rightId = rightId;
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

            public String getRightHasUrl() {
                return rightHasUrl;
            }

            public void setRightHasUrl(String rightHasUrl) {
                this.rightHasUrl = rightHasUrl;
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

            public String getLivePeriod() {
                return livePeriod;
            }

            public void setLivePeriod(String livePeriod) {
                this.livePeriod = livePeriod;
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

            public String getLiveId() {
                return liveId;
            }

            public void setLiveId(String liveId) {
                this.liveId = liveId;
            }

            public String getProgramId() {
                return programId;
            }

            public void setProgramId(String programId) {
                this.programId = programId;
            }

            public String getIsPay() {
                return isPay;
            }

            public void setIsPay(String isPay) {
                this.isPay = isPay;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getCompetitionId() {
                return competitionId;
            }

            public void setCompetitionId(String competitionId) {
                this.competitionId = competitionId;
            }

            public String getTvLiveId() {
                return tvLiveId;
            }

            public void setTvLiveId(String tvLiveId) {
                this.tvLiveId = tvLiveId;
            }

            public String getIfHasPlayback() {
                return ifHasPlayback;
            }

            public void setIfHasPlayback(String ifHasPlayback) {
                this.ifHasPlayback = ifHasPlayback;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getScheduleId() {
                return scheduleId;
            }

            public void setScheduleId(String scheduleId) {
                this.scheduleId = scheduleId;
            }

            public String getLeftScore() {
                return leftScore;
            }

            public void setLeftScore(String leftScore) {
                this.leftScore = leftScore;
            }

            public String getRightScore() {
                return rightScore;
            }

            public void setRightScore(String rightScore) {
                this.rightScore = rightScore;
            }

            public LatestNewsBean getLatestNews() {
                return latestNews;
            }

            public void setLatestNews(LatestNewsBean latestNews) {
                this.latestNews = latestNews;
            }

            public String getVURL() {
                return VURL;
            }

            public void setVURL(String VURL) {
                this.VURL = VURL;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getLeftEnName() {
                return leftEnName;
            }

            public void setLeftEnName(String leftEnName) {
                this.leftEnName = leftEnName;
            }

            public String getRightEnName() {
                return rightEnName;
            }

            public void setRightEnName(String rightEnName) {
                this.rightEnName = rightEnName;
            }

            public static class LatestNewsBean {
                /**
                 * newsId : 20180428012376
                 * site : sports
                 * title : 洛瑞24+6带队打破魔咒 猛龙4-2淘汰奇才晋级
                 * pub_time : 2018-04-28 09:28:15
                 * url : http://sports.qq.com/a/20180428/012376.htm
                 * atype : 2
                 * articleType : 1
                 * competitionId : 100000
                 * matchId : 1467029
                 * pushTime : 2018-04-28 10:06:54
                 */

                private String newsId;
                private String site;
                private String title;
                private String pub_time;
                private String url;
                private String atype;
                private String articleType;
                private String competitionId;
                private String matchId;
                private String pushTime;

                public String getNewsId() {
                    return newsId;
                }

                public void setNewsId(String newsId) {
                    this.newsId = newsId;
                }

                public String getSite() {
                    return site;
                }

                public void setSite(String site) {
                    this.site = site;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPub_time() {
                    return pub_time;
                }

                public void setPub_time(String pub_time) {
                    this.pub_time = pub_time;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getAtype() {
                    return atype;
                }

                public void setAtype(String atype) {
                    this.atype = atype;
                }

                public String getArticleType() {
                    return articleType;
                }

                public void setArticleType(String articleType) {
                    this.articleType = articleType;
                }

                public String getCompetitionId() {
                    return competitionId;
                }

                public void setCompetitionId(String competitionId) {
                    this.competitionId = competitionId;
                }

                public String getMatchId() {
                    return matchId;
                }

                public void setMatchId(String matchId) {
                    this.matchId = matchId;
                }

                public String getPushTime() {
                    return pushTime;
                }

                public void setPushTime(String pushTime) {
                    this.pushTime = pushTime;
                }
            }
        }
    }
}
