package com.rayhahah.rbase.base;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by a on 2017/5/15.
 */

public class RBasePresenter<T extends IRBaseView> implements IRBasePresenter {

    protected T mView;
    protected CompositeSubscription compositeSubscription;

    public RBasePresenter(T view) {
        mView = view;
    }

    @Override
    public void onDestory() {
        onUnsubscribe();
    }

    @Override
    public void onStop() {

    }

    /**
     * 添加事件监听处理到 事件管理类
     *
     * @param observable 启动响应
     * @param subscriber 监听响应
     */
    protected <T> void addSubscription(Observable observable, Subscriber<T> subscriber) {

        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
        );
    }


    /**
     * RxJava取消注册，避免内存泄露
     * 取消以后就只能重新新建一个了
     */
    protected void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }

}
