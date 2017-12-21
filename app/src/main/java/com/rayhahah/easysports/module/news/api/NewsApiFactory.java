package com.rayhahah.easysports.module.news.api;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.news.bean.NewsIndex;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by a on 2017/5/16.
 */

public class NewsApiFactory {

    public static Observable<NewsIndex> getNewsIndex(String column) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(NewsService.class)
                .getNewsIndex(column)
                .compose(RxSchedulers.<NewsIndex>ioMain());
    }

    public static Observable<ResponseBody> getNewsItemJson(String column, String articleIds) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(NewsService.class)
                .getNewsItem(column, articleIds)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }

    public static Observable<ResponseBody> getVideoInfo(String vids) {
        return ApiClient.get(C.BaseURL.TECENT_VIDEO_SERVER_H5)
                .create(NewsService.class)
                .getVideosInfo(vids)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }

    public static Observable<ResponseBody> getNewsDetail(String column, String articleId) {
        return ApiClient.get(C.BaseURL.TECENT_SERVER)
                .create(NewsService.class)
                .getNewsDetail(column, articleId)
                .compose(RxSchedulers.<ResponseBody>ioMain());
    }
}
