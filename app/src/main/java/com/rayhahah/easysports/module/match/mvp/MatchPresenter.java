package com.rayhahah.easysports.module.match.mvp;

import com.rayhahah.easysports.module.match.api.MatchApiFactory;
import com.rayhahah.easysports.module.match.bean.MatchListBeanNew;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;


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
//        addSubscription(MatchApiFactory.getMatchsByData(date)
//                .subscribe(new Consumer<MatchListBean>() {
//                    @Override
//                    public void accept(@NonNull MatchListBean matchListBean) throws Exception {
//                        ArrayList<MatchListBean.DataBean.MatchesBean.MatchInfoBean> mibs = new ArrayList<>();
//                        for (MatchListBean.DataBean.MatchesBean matchesBean : matchListBean.getData().getMatches()) {
//                            MatchListBean.DataBean.MatchesBean.MatchInfoBean matchInfo = matchesBean.getMatchInfo();
//                            String sd = date;
//                            if (sd.equals(mView.getCurrentDate())) {
//                                sd = "今日";
//                            } else {
//                                sd = sd.substring(5, sd.length());
//                            }
//                            matchInfo.setSectionData(sd);
//                            mibs.add(matchInfo);
//                        }
//                        mView.addMatchListData(mibs, status);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        mView.addMatchListDataFailed(throwable, status);
//                    }
//                }));
        addSubscription(MatchApiFactory.getMatchsByDataWeb(date)
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        MatchListBeanNew.DataBean dataBean = JsonParser.parseMatchDataListWeb(responseBody.string(), date);
                        ArrayList<MatchListBeanNew.DataBean.MatchesBean> mibs = new ArrayList<>();
                        for (MatchListBeanNew.DataBean.MatchesBean matchesBean : dataBean.getMatches()) {
                            String sd = date;
                            if (sd.equals(mView.getCurrentDate())) {
                                sd = "今日";
                            } else {
                                sd = sd.substring(5, sd.length());
                            }
                            matchesBean.setSectionData(sd);
                            mibs.add(matchesBean);
                        }
                        mView.addMatchListData(mibs, status);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.addMatchListDataFailed(throwable, status);
                    }
                }));
    }
}
