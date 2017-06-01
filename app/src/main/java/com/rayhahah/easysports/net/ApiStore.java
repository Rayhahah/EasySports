package com.rayhahah.easysports.net;

import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Api接口管理类
 */
public interface ApiStore {


    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@NonNull @Url String url);
}
