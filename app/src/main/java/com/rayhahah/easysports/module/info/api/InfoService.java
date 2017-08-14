package com.rayhahah.easysports.module.info.api;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InfoService {
    /**
     * 获取NBA数据排名
     *
     * @param statType 数据类型。 ex: point 得分,rebound 篮板,assist 助攻,block 盖帽,steal 抢断
     * @param num      查询数据条数。建议25
     * @param tabType  比赛类型。   ex: 1 每日,2 季后赛,3 常规赛
     * @param seasonId 赛季。格式：YYYY  ex : 2015
     */
    @GET("/player/statsRank")
    Observable<ResponseBody> getStatsRank(@Query("statType") String statType, @Query("num") int num, @Query("tabType") String tabType, @Query("seasonId") String seasonId);

    // rankByDivision // 分区排名
    @GET("/team/rank")
    Observable<ResponseBody> getTeamsRank();
}
