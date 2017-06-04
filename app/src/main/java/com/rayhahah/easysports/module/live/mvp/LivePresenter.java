package com.rayhahah.easysports.module.live.mvp;

import com.rayhahah.easysports.module.live.api.LiveApiFactory;
import com.rayhahah.easysports.module.live.bean.MatchListBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by a on 2017/5/17.
 */

public class LivePresenter extends RBasePresenter<LiveContract.ILiveView>
        implements LiveContract.ILivePresenter {
    public LivePresenter(LiveContract.ILiveView view) {
        super(view);
    }

    @Override
    public void addMatchListData(String data) {
        addSubscription(LiveApiFactory.getMatchsByData(data),
                new Subscriber<List<MatchListBean.MatchInfoBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<MatchListBean.MatchInfoBean> matchInfoBeanList) {
                        mView.addMatchListData(matchInfoBeanList);
                    }
                });
    }


}
