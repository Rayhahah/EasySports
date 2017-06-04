package com.rayhahah.easysports.module.live.api;

import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.live.bean.MatchListBean;
import com.rayhahah.rbase.net.ApiClient;

import java.util.List;

import rx.Observable;

/**
 * Created by a on 2017/5/16.
 */

public class LiveApiFactory {
    public static Observable<List<MatchListBean.MatchInfoBean>> getMatchsByData(String data) {
        return ApiClient.get(C.BaseURL.TENCENT_SERVER).create(LiveService.class).getMatchsByData(data);
    }
}
