package com.rayhahah.easysports.module.match.mvp;

import com.rayhahah.easysports.module.match.api.MatchApiFactory;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by a on 2017/5/17.
 */

public class MatchPresenter extends RBasePresenter<MatchContract.IMatchView>
        implements MatchContract.IMatchPresenter {
    public MatchPresenter(MatchContract.IMatchView view) {
        super(view);
    }


    @Override
    public void addMatchListData(final String date, final int status) {
        addSubscription(MatchApiFactory.getMatchsByData(date),
                new Subscriber<MatchListBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.addMatchListDataFailed(throwable, status);
                    }

                    @Override
                    public void onNext(MatchListBean matchListBean) {
                        ArrayList<MatchListBean.DataBean.MatchesBean.MatchInfoBean> mibs = new ArrayList<>();
                        for (MatchListBean.DataBean.MatchesBean matchesBean : matchListBean.getData().getMatches()) {
                            MatchListBean.DataBean.MatchesBean.MatchInfoBean matchInfo = matchesBean.getMatchInfo();
                            String sd = date;
                            if (sd.equals(mView.getCurrentDate())) {
                                sd = "今日";
                            } else {
                                sd = sd.substring(5, sd.length());
                            }
                            matchInfo.setSectionData(sd);
                            mibs.add(matchInfo);
                        }
                        mView.addMatchListData(mibs, status);
                    }
                });
    }
}
