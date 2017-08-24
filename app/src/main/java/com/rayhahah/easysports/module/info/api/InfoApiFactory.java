package com.rayhahah.easysports.module.info.api;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by a on 2017/5/16.
 */

public class InfoApiFactory {

    /**
     * @param statusType
     * @param rows
     * @param tagType
     * @param seasonId
     * @return
     */
    public static Observable<ResponseBody> getStatsRank(String statusType, int rows, String tagType, String seasonId) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(InfoService.class)
                .getStatsRank(statusType, rows, tagType, seasonId)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }

    /**
     * 获取球队战绩排行
     *
     * @return
     */
    public static Observable<ResponseBody> getTeamRank() {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(InfoService.class)
                .getTeamsRank()
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }

}
