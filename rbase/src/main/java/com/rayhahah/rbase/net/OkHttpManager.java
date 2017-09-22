package com.rayhahah.rbase.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.rayhahah.rbase.BaseApplication;
import com.rayhahah.rbase.net.download.ProgressListener;
import com.rayhahah.rbase.net.download.ProgressResponseBody;
import com.rayhahah.rbase.utils.base.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttpClient管理类
 */
public class OkHttpManager {

    private OkHttpManager() {

    }

    public static OkHttpClient create() {
        return create(null);
    }

    public static OkHttpClient create(ProgressListener progressListener) {
        Interceptor interceptor = new HttpCacheInterceptor();
        if (progressListener != null) {
            interceptor = new HttpProgressInterceptor(progressListener);
        }
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache)
                .build();
        return okHttpClient;
    }


    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    static class HttpProgressInterceptor implements Interceptor {
        private ProgressListener mProgressListener;

        public HttpProgressInterceptor(ProgressListener progressListener) {
            mProgressListener = progressListener;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse
                    .newBuilder()
                    .body(new ProgressResponseBody(originalResponse, mProgressListener))
                    .build();
        }
    }

}
