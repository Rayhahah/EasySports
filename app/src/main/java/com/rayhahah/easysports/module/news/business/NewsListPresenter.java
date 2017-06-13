package com.rayhahah.easysports.module.news.business;

import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.news.api.NewsApiFactory;
import com.rayhahah.easysports.module.news.bean.NewsIndex;
import com.rayhahah.easysports.module.news.bean.NewsItem;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.net.RCallBack;

import java.util.List;

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
    public void getNewsIndex(String column) {
        mView.showViewLoading();
        addSubscription(NewsApiFactory.getNewsIndex(column)
                , new RCallBack<NewsIndex>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mView.getNewsError(throwable, C.STATUS.REFRESH);
                    }

                    @Override
                    public void onNext(NewsIndex newsIndex) {
                        mView.getNewsIndex(newsIndex);
                    }
                });
    }

    @Override
    public void getNewsItem(String column, String articleIds, final int status) {
//        mView.showViewLoading();
        addSubscription(NewsApiFactory.getNewsItemJson(column, articleIds)
                , new RCallBack<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.getNewsError(e, status);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String json = null;
                        try {
                            json = responseBody.string();
                            List<NewsItem.DataBean.ItemInfo> dataBeen = JsonParser.parseNewsItem(json);
                            mView.getNewsItem(dataBeen, status);
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.getNewsError(e, status);
                        }
                    }
                });
    }
}
