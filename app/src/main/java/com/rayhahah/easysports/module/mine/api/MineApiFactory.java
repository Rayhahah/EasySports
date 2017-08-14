package com.rayhahah.easysports.module.mine.api;

import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;


/**
 * Created by a on 2017/5/16.
 */

public class MineApiFactory {

    public static Observable<PlayerListBean> getPlayerList() {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MineService.class)
                .getPlayerList()
                .compose(RxSchedulers.<PlayerListBean>ioMain());
    }

    public static Observable<TeamListBean> getTeamList() {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(MineService.class)
                .getTeamList()
                .compose(RxSchedulers.<TeamListBean>ioMain());
    }


}
