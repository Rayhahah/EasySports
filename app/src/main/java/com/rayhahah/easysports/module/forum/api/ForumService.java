package com.rayhahah.easysports.module.forum.api;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by a on 2017/5/16.
 */

public interface ForumService {

    /**
     * 获取论坛板块列表
     *
     * @param sign
     * @param params
     * @return
     */
    @GET()
    Observable<ResponseBody> getAllForums(@Query("sign") String sign,
                                          @QueryMap HashMap<String, String> params);
}
