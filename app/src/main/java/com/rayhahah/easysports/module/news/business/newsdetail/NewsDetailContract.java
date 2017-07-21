package com.rayhahah.easysports.module.news.business.newsdetail;

import android.graphics.Bitmap;

import com.rayhahah.easysports.module.news.bean.NewsDetail;
import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by a on 2017/6/22.
 */

public class NewsDetailContract {
    public interface INewsDetailView extends IRBaseView {
        void getNewsDetail(NewsDetail newsDetail);

        void savePicDone(Boolean b);
    }

    public interface INewsDetailPresenter {
        void getNewsDetail(String column,String articleId);
        void saveBitmap(Bitmap bitmap);
    }

}
