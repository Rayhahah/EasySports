package com.rayhahah.easysports.module.news.business;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/17.
 */

public class NewsListPresenter extends RBasePresenter<NewsListContract.INewsView>
        implements NewsListContract.INewsPresenter {
    public NewsListPresenter(NewsListContract.INewsView view) {
        super(view);
    }
}
