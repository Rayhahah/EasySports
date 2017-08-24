package com.rayhahah.easysports.module.news.business.newslist;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.news.api.NewsApiFactory;
import com.rayhahah.easysports.module.news.bean.NewsIndex;
import com.rayhahah.easysports.module.news.bean.NewsItem;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by a on 2017/5/17.
 */

public class NewsListPresenter extends RBasePresenter<NewsListContract.INewsListView>
        implements NewsListContract.INewsListPresenter {
    public NewsListPresenter(NewsListContract.INewsListView view) {
        super(view);
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void getNewsIndex(String column) {
        mView.showViewLoading();
        addSubscription(NewsApiFactory.getNewsIndex(column)
                .subscribe(new Consumer<NewsIndex>() {
                    @Override
                    public void accept(@NonNull NewsIndex newsIndex) throws Exception {
                        mView.getNewsIndex(newsIndex);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.getNewsError(throwable, C.STATUS.REFRESH);
                    }
                }));
    }

    @Override
    public void getNewsItem(String column, String articleIds, final int status) {
//        mView.showViewLoading();
        addSubscription(NewsApiFactory.getNewsItemJson(column, articleIds)
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        String json = responseBody.string();
                        List<NewsItem.DataBean.ItemInfo> dataBeen = JsonParser.parseNewsItem(json);
                        mView.getNewsItem(dataBeen, status);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.getNewsError(throwable, status);
                    }
                }));
    }
}
