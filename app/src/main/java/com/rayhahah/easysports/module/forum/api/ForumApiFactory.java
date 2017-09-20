package com.rayhahah.easysports.module.forum.api;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static com.rayhahah.easysports.module.mine.api.MineApiFactory.getRequestSign;

/**
 * Created by a on 2017/5/16.
 */

public class ForumApiFactory {

    public static Observable<ForumsData> getAllForums() throws UnsupportedEncodingException {
        Map<String, String> params = getRequsetMap();
        String sign = getRequestSign(params);
        return ApiClient.get(C.BaseURL.HUPU_FORUM_SERVER)
                .create(ForumService.class)
                .getAllForums(sign,params)
                .compose(RxSchedulers.<ForumsData>ioMain());
    }


    public static HashMap<String, String> getRequsetMap() throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<>();
        map.put("client", "Android");
        map.put("night", "0");
        map.put("token", URLEncoder.encode("", "UTF-8"));
        return map;
    }
}
