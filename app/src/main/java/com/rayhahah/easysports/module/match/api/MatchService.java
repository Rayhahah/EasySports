package com.rayhahah.easysports.module.match.api;

import com.rayhahah.easysports.module.match.bean.LiveIndex;
import com.rayhahah.easysports.module.match.bean.MatchDetailBean;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.easysports.module.match.bean.MatchVideo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

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

    /**
     * 获取比赛对阵信息
     *
     * @param mid
     * @return
     */
    // baseInfo?mid=100000:1468573
    @GET("/match/baseInfo")
    Observable<MatchDetailBean> getMatchBaseInfo(@Query("mid") String mid);

    /**
     * 获取比赛详细数据信息
     *
     * @param mid     比赛id
     * @param tabType 1：比赛数据  2：技术统计  3：比赛前瞻
     * @return
     */
    // stat?mid=100000:1468573&tabType=3
    @GET("/match/stat")
    Observable<ResponseBody> getMatchStat(@Query("mid") String mid, @Query("tabType") String tabType);

    /**
     * 根据MID获取图文直播的条目id
     * @param mid
     * @return
     */
    // baseInfo?mid=100000:1468573
    @GET("/match/textLiveIndex")
    Observable<LiveIndex> getMatchLiveIndex(@Query("mid") String mid);


    /**
     * 根据ID获取图文直播具体内容
     * @param mid
     * @param ids
     * @return
     */
    @GET("/match/textLiveDetail")
    Observable<ResponseBody> getMatchLiveDetail(@Query("mid") String mid, @Query("ids") String ids);


    /**
     * 获取比赛视频
     * @param mid
     * @return
     */
    @GET("/video/matchVideo")
    Observable<MatchVideo> getMatchVideo(@Query("matchId") String mid);

}
