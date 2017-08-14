package com.rayhahah.easysports.module.info.bean;

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
 * @time 2017/7/26
 * @tips 这个类是Object的子类
 * @fuction 数据排行信息实体类
 */
public class StatusRank {

   public String type;

    private List<PlayerBean> rankList;

    public List<PlayerBean> getRankList() {
        return rankList;
    }

    public void setRankList(List<PlayerBean> rankList) {
        this.rankList = rankList;
    }

    public static class PlayerBean {
        /**
         * serial : 1
         * playerId : 3704
         * playerUrl : http://sports.qq.com/kbsweb/kbsshare/player.htm?ref=nbaapp&pid=3704
         * playerName : 勒布朗-詹姆斯
         * playerEnName : LeBron James
         * playerIcon : http://nbachina.qq.com/media/img/players/head/260x190/2544.png
         * jerseyNum : 23
         * teamId : 5
         * teamUrl : http://sports.qq.com/kbsweb/kbsshare/team.htm?ref=nbaapp&cid=100000&tid=5
         * teamName : 骑士
         * teamIcon : http://mat1.gtimg.com/sports/nba/logo/1602/5.png
         * value : 41
         */

        private String serial;
        private String playerId;
        private String playerUrl;
        private String playerName;
        private String playerEnName;
        private String playerIcon;
        private String jerseyNum;
        private String teamId;
        private String teamUrl;
        private String teamName;
        private String teamIcon;
        private String value;

        public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getPlayerUrl() {
            return playerUrl;
        }

        public void setPlayerUrl(String playerUrl) {
            this.playerUrl = playerUrl;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerEnName() {
            return playerEnName;
        }

        public void setPlayerEnName(String playerEnName) {
            this.playerEnName = playerEnName;
        }

        public String getPlayerIcon() {
            return playerIcon;
        }

        public void setPlayerIcon(String playerIcon) {
            this.playerIcon = playerIcon;
        }

        public String getJerseyNum() {
            return jerseyNum;
        }

        public void setJerseyNum(String jerseyNum) {
            this.jerseyNum = jerseyNum;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getTeamUrl() {
            return teamUrl;
        }

        public void setTeamUrl(String teamUrl) {
            this.teamUrl = teamUrl;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getTeamIcon() {
            return teamIcon;
        }

        public void setTeamIcon(String teamIcon) {
            this.teamIcon = teamIcon;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
