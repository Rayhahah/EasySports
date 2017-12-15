package com.rayhahah.easysports.module.mine.api;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.mine.bean.ESUser;
import com.rayhahah.easysports.module.mine.bean.LiveBean;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.PushBean;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.easysports.utils.HuPuHelper;
import com.rayhahah.easysports.utils.security.MD5;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


/**
 * Created by a on 2017/5/16.
 */

public class MineApiFactory {

    public static Observable<ESUser> register(HashMap<String, String> params) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .register(params)
                .compose(RxSchedulers.<ESUser>ioMain());
    }

    public static Observable<ESUser> getUserInfo(String username, String password) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .getUserInfo(username, password)
                .compose(RxSchedulers.<ESUser>ioMain());
    }

    public static Observable<RResponse> forgetGetQuestion(String username) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .forgetGetQuestion(username)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> forgetCheckAnswer(String username, String question, String answer) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .forgetCheckAnswer(username, question, answer)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> forgetResetPassword(String username, String passwordNew, String fogetToken) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .forgetResetPassword(username, passwordNew, fogetToken)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> resetPassword(String username, String passwordOld, String passwordNew) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .resetPassword(username, passwordOld, passwordNew)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> updateHupuInfo(String username, String password, String hupuUsername, String hupuPassword) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .updateHupuInfo(username, password, hupuUsername, hupuPassword)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> updateNormalInfo(HashMap<String, String> params) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .updateNormalInfo(params)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> updateCover(String username, String password, String cover) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .updateCover(username, password, cover)
                .compose(RxSchedulers.<RResponse>ioMain());
    }

    public static Observable<RResponse> commitFeedback(String versionName, String versionCode, String comment, int easysportId) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .commitFeedback(versionName, versionCode, comment, easysportId)
                .compose(RxSchedulers.<RResponse>ioMain());
    }


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

    public static Observable<PushBean> getPushUrl(String username) {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .getPushUrl(username)
                .compose(RxSchedulers.<PushBean>ioMain());
    }

    public static Observable<LiveBean> getCurrentLive() {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(MineService.class)
                .getCurrentLive()
                .compose(RxSchedulers.<LiveBean>ioMain());
    }

    public static Observable<ResponseBody> loginHupu(String userName, String password) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("client", C.DEVICE_ID);
        params.put("client", "864444036940802");
        params.put("username", userName);
        // TODO: 2017/9/25 后台直接返回非MD5密码，这里就可以不用判断了
        params.put("password", MD5.getMD5(password));
        params.put("crt", System.currentTimeMillis() + "");
        params.put("night", "0");
        params.put("channel", "miui");
        params.put("android_id", "864c5bdabcd5586a");
        params.put("time_zone", "Asia/Shanghai");
        String sign = HuPuHelper.getRequestSign(params);
        params.put("sign", sign);
//        params.put("sign", "66e7cf600416efdef3062296442516f5");
        return ApiClient.get(C.BaseURL.HUPU_GAMES_SERVER)
                .create(MineService.class)
                .login(params, "864444036940802")
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }


}
