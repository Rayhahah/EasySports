package com.rayhahah.easysports.module.news.mvp;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/17.
 */

public class NewsPresenter extends RBasePresenter<NewsContract.INewsView>
        implements NewsContract.INewsPresenter {
    public NewsPresenter(NewsContract.INewsView view) {
        super(view);
    }
}
