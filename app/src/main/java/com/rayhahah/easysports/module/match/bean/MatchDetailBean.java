package com.rayhahah.easysports.module.match.bean;

import java.io.Serializable;

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
 * @time 2017/9/29
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchDetailBean implements Serializable {
    public BaseInfo data;

    /**
     * leftId : 25
     * leftName : 雷霆
     * leftBadge : http://mat1.gtimg.com/sports/nba/logo/1602/25.png
     * leftGoal : 0
     * leftUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=25
     * rightId : 6
     * rightName : 小牛
     * rightBadge : http://mat1.gtimg.com/sports/nba/logo/1602/6.png
     * rightGoal : 0
     * rightUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=6
     * matchPeriod : 0
     * matchType : 2
     * quarterDesc :  12:00
     * startDate : 07月02日
     * startHour : 21:00
     * venue : 美航中心
     * desc : 夏季联赛
     * hasLiveText : 1
     * updateFrequency : 1800
     * leftWins : 55
     * leftLosses : 27
     * rightWins : 42
     * rightLosses : 40
     */

    public static class BaseInfo implements Serializable {
        public String leftId;
        public String leftName;
        public String leftBadge;
        public String leftGoal;
        public String leftUrl;
        public String rightId;
        public String rightName;
        public String rightBadge;
        public String rightGoal;
        public String rightUrl;
        public String matchPeriod;
        public String matchType;
        public String quarterDesc;
        public String startDate;
        public String startHour;
        public String venue;
        public String desc;
        public String hasLiveText;
        public String updateFrequency;
        public String leftWins;
        public String leftLosses;
        public String rightWins;
        public String rightLosses;
    }

}
