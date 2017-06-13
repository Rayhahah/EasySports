package com.rayhahah.rbase.net;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit初始化配置类
 */
public class ApiClient {

    private static Retrofit mRetrofit;

    private static ConcurrentHashMap<String, Retrofit> retrofitFactory = new ConcurrentHashMap<>();

    private ApiClient() {
    }

    private ApiClient(OkHttpClient okHttpClient, String baseUrl) {
        for (Map.Entry<String, Retrofit> retrofitEntry : retrofitFactory.entrySet()) {
            if (retrofitEntry.getKey().equals(baseUrl)) {
                return;
            }
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofitFactory.put(baseUrl, retrofit);
    }


    public static void create(String baseUrl, OkHttpClient okHttpClient) {
        new ApiClient(okHttpClient, baseUrl);
    }

    /**
     * 获取对应的retrofit
     *
     * @param baseUrl
     * @return
     */
    public static Retrofit get(String baseUrl) {
        return retrofitFactory.get(baseUrl);
    }

}
