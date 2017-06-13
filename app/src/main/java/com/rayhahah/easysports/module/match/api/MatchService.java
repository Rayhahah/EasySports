package com.rayhahah.easysports.module.match.api;

import com.rayhahah.easysports.module.match.bean.MatchListBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by a on 2017/5/16.
 */

public interface MatchService {
    /**
     * 根据日期获取当天比赛数据
     *
     * @param date 当前日期  example: "2017-06-05"
     * @return
     */
    @GET("/match/listByDate")
    Observable<MatchListBean> getMatchsByData(@Query("date") String date);

}
