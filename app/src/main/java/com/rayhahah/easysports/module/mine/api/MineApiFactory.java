package com.rayhahah.easysports.module.mine.api;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.mine.bean.HupuUserData;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.easysports.utils.security.MD5;
import com.rayhahah.rbase.utils.useful.RxSchedulers;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by a on 2017/5/16.
 */

public class MineApiFactory {

    public static Observable<PlayerListBean> getPlayerList() {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MineService.class)
                .getPlayerList()
                .compose(RxSchedulers.<PlayerListBean>ioMain());
    }

    public static Observable<TeamListBean> getTeamList() {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MineService.class)
                .getTeamList()
                .compose(RxSchedulers.<TeamListBean>ioMain());
    }

    public static Observable<HupuUserData> loginHupu(String userName,String password){
        HashMap<String, String> params = new HashMap<>();
        params.put("client", C.DeviceId);
        params.put("username", userName);
        params.put("password", MD5.getMD5(password));
        String sign = getRequestSign(params);

        return ApiClient.get(C.BaseURL.HUPU_GAMES_SERVER)
                .create(MineService.class)
                .login(userName,MD5.getMD5(password),sign, C.DeviceId)
                .compose(RxSchedulers.<HupuUserData>ioMain());
    }



    public static HashMap<String, String> getRequsetMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("client", C.DeviceId);
        map.put("night", "0");
        try {
            map.put("token", URLEncoder.encode(SPManager.get().getStringValue(C.SP.TOKEN), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 虎扑url sign生成
     *
     * @param map url参数，按字典序拼接key和value
     * @return sign值
     */
    public static String getRequestSign(Map<String, String> map) {
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() { // 字典序
            @Override
            public int compare(Map.Entry<String, String> lhs, Map.Entry<String, String> rhs) {
                return lhs.getKey().compareTo(rhs.getKey());
            }
        });
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i = i + 1) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            Map.Entry<String, String> map1 = list.get(i);
            builder.append(map1.getKey()).append("=").append(map1.getValue());
        }
        builder.append("HUPU_SALT_AKJfoiwer394Jeiow4u309");
        return MD5.getMD5(builder.toString());
    }


}
