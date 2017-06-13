package com.rayhahah.easysports.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayhahah.easysports.module.news.bean.NewsItem;

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
     *
     * @param json
     * @return
     */
    public static List<NewsItem.DataBean.ItemInfo> parseNewsItem(String json) {
        try {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
