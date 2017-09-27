package com.rayhahah.easysports.module.forum.api;

import com.rayhahah.easysports.module.forum.bean.DetailListData;
import com.rayhahah.easysports.module.forum.bean.ForumDetailInfoData;
import com.rayhahah.easysports.module.forum.bean.ForumsData;

import java.util.Map;

import io.reactivex.Observable;
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
    @GET("forums/getForums")
    Observable<ForumsData> getAllForums(@Query("sign") String sign,
                                        @QueryMap Map<String, String> params);

    @GET("forums/getForumsInfoList")
    Observable<DetailListData> getForumInfosList(@Query("sign") String sign, @QueryMap Map<String, String> params);

    @GET("threads/getThreadsSchemaInfo")
    Observable<ForumDetailInfoData> getThreadInfo(@Query("sign") String sign, @QueryMap Map<String, String> params);


}
