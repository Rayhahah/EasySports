package com.rayhahah.easysports.module.live.api;


import com.rayhahah.easysports.module.live.bean.MatchListBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface LiveService {

    @GET("/match/listByDate")
    Observable<List<MatchListBean.MatchInfoBean>> getMatchsByData(@Query("date") String date);

}
