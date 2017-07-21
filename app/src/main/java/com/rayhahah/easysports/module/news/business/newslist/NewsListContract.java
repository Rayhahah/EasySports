package com.rayhahah.easysports.module.news.business.newslist;

import com.rayhahah.easysports.module.news.bean.NewsIndex;
import com.rayhahah.easysports.module.news.bean.NewsItem;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class NewsListContract {
    public interface INewsListView extends IRBaseView {
        void getNewsIndex(NewsIndex newsIndex);

        void getNewsItem(List<NewsItem.DataBean.ItemInfo> data, int status);

        void getNewsError(Throwable t, int status);
    }

    public interface INewsListPresenter {
        void getNewsIndex(String column);

        void getNewsItem(String column, String articleIds, int status);
    }
}
