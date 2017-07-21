package com.rayhahah.easysports.module.mine.api;

import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by a on 2017/5/16.
 */

public interface MineService {

    /**
     * 获取全部球员列表
     */
    @GET("/player/list")
    Observable<PlayerListBean> getPlayerList();

    /**
     * 获取全部球队列表
     */
    @GET("/team/list")
    Observable<TeamListBean> getTeamList();
}
