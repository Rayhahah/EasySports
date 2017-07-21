package com.rayhahah.easysports.module.match.api;

import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.rbase.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;


/**
 * Created by a on 2017/5/16.
 */

public class MatchApiFactory {
    public static Observable<MatchListBean> getMatchsByData(String date) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchsByData(date)
                .compose(RxSchedulers.<MatchListBean>ioMain());
    }
}
