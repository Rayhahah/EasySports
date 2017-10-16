package com.rayhahah.easysports.module.match.api;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.match.bean.LiveIndex;
import com.rayhahah.easysports.module.match.bean.MatchDetailBean;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.easysports.module.match.bean.MatchVideo;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


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

    public static Observable<MatchDetailBean> getMatchDetail(String mid) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchBaseInfo(mid)
                .compose(RxSchedulers.<MatchDetailBean>ioMain());
    }

    public static Observable<ResponseBody> getMatchStatus(String mid, String tabType) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchStat(mid, tabType)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }

    public static Observable<LiveIndex> getMatchLiveIndex(String mid) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchLiveIndex(mid)
                .compose(RxSchedulers.<LiveIndex>ioMain());
    }

    public static Observable<ResponseBody> getMatchLiveDetail(String mid, String ids) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchLiveDetail(mid, ids)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }

    public static Observable<MatchVideo> getMatchVideo(String mid) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MatchService.class)
                .getMatchVideo(mid)
                .compose(RxSchedulers.<MatchVideo>ioMain());
    }

}
