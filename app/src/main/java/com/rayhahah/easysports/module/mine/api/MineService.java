package com.rayhahah.easysports.module.mine.api;

import com.rayhahah.easysports.module.mine.bean.ESUser;
import com.rayhahah.easysports.module.mine.bean.LiveBean;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.PushBean;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by a on 2017/5/16.
 */

public interface MineService {

    /**
     * 完整参数 ( * 为必填)
     * username *
     * password *
     * screenname *
     * email
     * phone
     * cover
     * question *
     * answer *
     * hupuUsername
     * hupuPassword
     * <p>
     * 注册新用户
     */
    @FormUrlEncoded
    @POST("easysport/user/register.do")
    Observable<ESUser> register(@FieldMap HashMap<String, String> params);

    /**
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("easysport/user/get_user_info.do")
    Observable<ESUser> getUserInfo(@Field("username") String username,
                                   @Field("password") String password);

    /**
     * 根据用户名获取问题
     */
    @FormUrlEncoded
    @POST("easysport/user/forget_get_question.do")
    Observable<RResponse> forgetGetQuestion(@Field("username") String username);

    /**
     * 验证回答是否正确
     */
    @FormUrlEncoded
    @POST("easysport/user/forget_check_answer.do")
    Observable<RResponse> forgetCheckAnswer(@Field("username") String username,
                                            @Field("question") String question,
                                            @Field("answer") String answer);

    /**
     * 重置密码
     */
    @FormUrlEncoded
    @POST("easysport/user/forget_reset_password.do")
    Observable<RResponse> forgetResetPassword(@Field("username") String username,
                                              @Field("passwordNew") String passwordNew,
                                              @Field("forgetToken") String forgetToken);

    /**
     * 更新密码
     */
    @FormUrlEncoded
    @POST("easysport/user/reset_password.do")
    Observable<RResponse> resetPassword(@Field("username") String username,
                                        @Field("passwordOld") String passwordOld,
                                        @Field("passwordNew") String passwordNew);

    /**
     * 设置用户绑定虎扑账号
     */
    @FormUrlEncoded
    @POST("easysport/user/update_hupuinfo.do")
    Observable<RResponse> updateHupuInfo(@Field("username") String username,
                                         @Field("password") String password,
                                         @Field("hupuUsername") String hupuUsername,
                                         @Field("hupuPassword") String hupuPassword);

    /**
     * 完整参数
     * username
     * password
     * screenname
     * email
     * phone
     * question
     * answer
     * <p>
     * 设置用户普通信息
     */
    @FormUrlEncoded
    @POST("easysport/user/update_normalinfo.do")
    Observable<RResponse> updateNormalInfo(@FieldMap HashMap<String, String> params);

    /**
     * 设置用户头像资源
     */
    @FormUrlEncoded
    @POST("easysport/user/update_cover.do")
    Observable<RResponse> updateCover(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("cover") String cover);

    /**
     * 提交反馈信息
     */
    @FormUrlEncoded
    @POST("easysport/comment/commit.do")
    Observable<RResponse> commitFeedback(@Field("versionName") String versionName,
                                         @Field("versionCode") String versionCode,
                                         @Field("comment") String comment,
                                         @Field("userId") int easysportId);

    /**
     * 提交反馈信息
     */
    @FormUrlEncoded
    @POST("easysport/tecentcloud/get_push_url.do")
    Observable<PushBean> getPushUrl(@Field("username") String username);

    /**
     * 提交反馈信息
     */
    @POST("easysport/tecentcloud/get_current_live.do")
    Observable<LiveBean> getCurrentLive();

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

//    @FormUrlEncoded
//    @POST("user/loginUsernameEmail")
//    Observable<HupuUserData> login(@FieldMap Map<String, String> params,
//                                   @Field("client") String client);

    @FormUrlEncoded
    @POST("user/loginUsernameEmail")
    Observable<ResponseBody> login(@FieldMap Map<String, String> params,
                                   @Query("client") String client);


}
