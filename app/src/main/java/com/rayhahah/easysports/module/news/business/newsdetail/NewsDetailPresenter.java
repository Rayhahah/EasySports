package com.rayhahah.easysports.module.news.business.newsdetail;

import android.graphics.Bitmap;

import com.rayhahah.easysports.app.MyApplication;
import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.news.api.NewsApiFactory;
import com.rayhahah.easysports.module.news.bean.NewsDetail;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.net.RCallBack;
import com.rayhahah.rbase.utils.base.ImageUtils;

import java.io.File;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by a on 2017/6/22.
 */

public class NewsDetailPresenter extends RBasePresenter<NewsDetailContract.INewsDetailView>
        implements NewsDetailContract.INewsDetailPresenter {
    public NewsDetailPresenter(NewsDetailContract.INewsDetailView view) {
        super(view);
    }

    @Override
    public void getNewsDetail(String column, String articleId) {
        addSubscription(NewsApiFactory.getNewsDetail(column, articleId), new RCallBack<NewsDetail>() {
            @Override
            public void onError(Throwable e) {
                mView.showViewError(e);
            }

            @Override
            public void onNext(NewsDetail newsDetail) {
                mView.getNewsDetail(newsDetail);
            }
        });
    }

    /**
     * 保存图片本地，并通知图库刷新
     *
     * @param bitmap
     */
    @Override
    public void saveBitmap(final Bitmap bitmap) {
        Observable observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                String fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(C.PIC_DIR, fileName);
                if (ImageUtils.save(bitmap, file, Bitmap.CompressFormat.JPEG)) {
                    if (ImageUtils.insertImage(MyApplication.getAppContext(), file.getAbsolutePath(), fileName, null)) {
                        subscriber.onNext(true);
                    } else {
                        subscriber.onNext(false);
                    }
                } else {
                    subscriber.onNext(false);
                }
            }
        });
        addSubscription(observable, new RCallBack<Boolean>() {
            @Override
            public void onNext(Boolean b) {
                mView.savePicDone(b);
            }
        });

    }
}
