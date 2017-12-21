package com.rayhahah.easysports.module.news.api;

import com.rayhahah.easysports.module.news.bean.NewsIndex;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by a on 2017/5/16.
 */

public interface NewsService {

    /**
     * 获取新闻列表条目索引
     *
     * @param column 列表类型
     * @return
     */
    @GET("/news/index")
    Observable<NewsIndex> getNewsIndex(@Query("column") String column);

    /**
     * 获取新闻条目具体Json数据
     *
     * @param column     列表类型
     * @param articleIds 新闻索引
     * @return
     */
    @GET("/news/item")
    Observable<ResponseBody> getNewsItem(@Query("column") String column, @Query("articleIds") String articleIds);

    /**
     * 根据Video Id 获取视频详细信息
     *
     * @param vids 视频id
     * @return
     */
    @POST("getinfo?platform=11001&charge=0&otype=json")
    Observable<ResponseBody> getVideosInfo(@Query("vids") String vids);

    /**
     * 根据column和articleId 获取新闻详情信息
     *
     * @param column    列表类型
     * @param articleId 新闻索引
     * @return
     */
    @GET("/news/detail")
    Observable<ResponseBody> getNewsDetail(@Query("column") String column, @Query("articleId") String articleId);

}
