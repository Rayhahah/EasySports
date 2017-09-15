package com.rayhahah.easysports.net;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 * Created by a on 2017/5/15.
 */

public class ApiFactory {

    public static Observable<RResponse> commitCrashMessage(int userId, HashMap<String, String> infos) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(ApiStore.class)
                .commitCrashMessage(userId, infos)
                .compose(RxSchedulers.<RResponse>ioMain());
    }


}
