package com.rayhahah.easysports.module.match.api;

import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.rbase.net.ApiClient;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by a on 2017/5/16.
 */

public class MatchApiFactory {
    public static Observable<MatchListBean> getMatchsByData(String data) {
        return ApiClient
                .get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchsByData(data);
    }
}
