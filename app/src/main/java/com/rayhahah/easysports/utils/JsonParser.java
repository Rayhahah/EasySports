package com.rayhahah.easysports.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rayhahah.easysports.module.info.bean.StatusRank;
import com.rayhahah.easysports.module.info.bean.TeamRank;
import com.rayhahah.easysports.module.news.bean.NewsItem;
import com.rayhahah.easysports.module.news.bean.VideoInfo;
import com.rayhahah.rbase.utils.base.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 动态key Json解析类
 */
public class JsonParser {

    static Gson gson = new GsonBuilder().serializeNulls()
//            .registerTypeAdapter(MatchStat.MaxPlayers.MatchPlayerInfo.class, new MatchPlayerInfoDefaultAdapter())
//            .registerTypeHierarchyAdapter(List.class, new ListDefaultAdapter())
            .create();

    /**
     * Gson解析标准的Json数据
     *
     * @param classOfT
     * @param jsonStr
     * @param <T>
     * @return
     */
    public static <T> T parseWithGson(Class<T> classOfT, String jsonStr) {
        return gson.fromJson(jsonStr, classOfT);
    }

    /**
     * 解析NewsItem
     */
    public static List<NewsItem.DataBean.ItemInfo> parseNewsItem(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        Iterator<String> keys = data.keys();
        ArrayList<NewsItem.DataBean.ItemInfo> dataList = new ArrayList<>();
        while (keys.hasNext()) {
            String next = keys.next();
            JSONObject itemJO = data.getJSONObject(next);
            NewsItem.DataBean.ItemInfo itemInfo = new Gson().fromJson(itemJO.toString(), NewsItem.DataBean.ItemInfo.class);
            dataList.add(itemInfo);
        }
        return dataList;
    }

    /**
     * 解析JSON获取VideoInfo数据对象
     */
    public static VideoInfo parseVideoInfo(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        String tarJson = json
                .replaceAll("QZOutputJson=", "")
                .replaceAll(" ", "")
                .replaceAll("\n", "");
        if (tarJson.endsWith(";")) {
            tarJson = tarJson.substring(0, tarJson.length() - 1);
        }
        VideoInfo videoInfo = new Gson().fromJson(tarJson, VideoInfo.class);
        return videoInfo;
    }

    /**
     * 解析JSON数据获取排行榜信息
     */
    public static StatusRank parseStatusRank(String json) throws JSONException {
        JSONObject jo = new JSONObject(json);
        JSONObject data = jo.getJSONObject("data");
        Iterator<String> keys = data.keys();
        StatusRank statusRank = new StatusRank();
        while (keys.hasNext()) {
            String next = keys.next();
            statusRank.type=next;
            JSONArray itemJO = data.getJSONArray(next);
            List<StatusRank.PlayerBean> playerBeen = (List<StatusRank.PlayerBean>) new Gson()
                    .fromJson(itemJO.toString(), new TypeToken<List<StatusRank.PlayerBean>>(){}.getType());
            statusRank.setRankList(playerBeen);
        }
        return statusRank;
    }

    /**
     * 解析JSON获取球队战绩排行
     */
    public static TeamRank parseTeamRank(String json) throws JSONException {
        JSONObject jo = new JSONObject(json);
        JSONArray data = jo.getJSONArray("data");
        TeamRank rank = new TeamRank();
        rank.east = new ArrayList<>();
        rank.west = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject item = data.getJSONObject(i);
            String title = item.getString("title");
            JSONArray teamsArray = item.optJSONArray("rows");
            for (int j = 0; j < teamsArray.length(); j++) {
                JSONArray teamsInfo = teamsArray.getJSONArray(j);
                Gson gson = new Gson();
                TeamRank.TeamBean bean = gson.fromJson(teamsInfo.getString(0), TeamRank.TeamBean.class);
                bean.win = teamsInfo.optInt(1);
                bean.lose = teamsInfo.optInt(2);
                bean.rate = teamsInfo.optString(3);
                bean.difference = teamsInfo.optString(4);
                if (title.equals("东部联盟")) {
                    rank.east.add(bean);
                } else {
                    rank.west.add(bean);
                }
            }
        }
        return rank;
    }
}
