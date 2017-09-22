package com.rayhahah.easysports.net;

import android.support.annotation.NonNull;

import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.easysports.net.version.VersionInfo;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Api接口管理类
 */
public interface ApiStore {


    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@NonNull @Url String url);

    @Multipart
    @POST("easysport/user/upload_cover.do")
    Observable<RResponse> uploadFile(@Part() MultipartBody.Part part,
                                     @Query("username") String username,
                                     @Query("password") String password);

    /**
     * 提交反馈信息
     */
    @FormUrlEncoded
    @POST("easysport/crashmessage/commit.do")
    Observable<RResponse> commitCrashMessage(@Field("userId") int userId,
                                             @FieldMap HashMap<String, String> infos);

    /**
     * 提交反馈信息
     */
    @POST("easysport/version/check.do")
    Observable<VersionInfo> checkVersion();


}
