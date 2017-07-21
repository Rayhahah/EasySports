package com.rayhahah.easysports.module.mine.business.teamplayer;

import com.rayhahah.easysports.module.mine.api.MineApiFactory;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by a on 2017/6/28.
 */

public class SingleListPresenter extends RBasePresenter<SingleListContract.ISingleListView>
        implements SingleListContract.ISingleListPresenter {

    private int start = 0;
    private List<PlayerListBean.DataBean> mData;
    private int rows = 10;

    public SingleListPresenter(SingleListContract.ISingleListView view) {
        super(view);
    }


    @Override
    public void getTeamList() {
        addSubscription(MineApiFactory.getTeamList()
                .subscribe(new Consumer<TeamListBean>() {
                    @Override
                    public void accept(@NonNull TeamListBean teamListBean) throws Exception {
                        ArrayList<TeamListBean.DataBean.TeamBean> teamList = new ArrayList<>();
                        List<TeamListBean.DataBean.TeamBean> east = teamListBean.getData().getEast();
                        List<TeamListBean.DataBean.TeamBean> west = teamListBean.getData().getWest();
                        teamList.addAll(east);
                        teamList.addAll(west);
                        mView.getTeamList(teamList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showViewError(throwable);
                    }
                }));
    }

    @Override
    public void getPlayerList() {
        if (mData == null) {
            addSubscription(MineApiFactory.getPlayerList()
                    .subscribe(new Consumer<PlayerListBean>() {
                        @Override
                        public void accept(@NonNull PlayerListBean playerListBean) throws Exception {
                            mData = playerListBean.getData();
                            List<PlayerListBean.DataBean> list = new ArrayList<PlayerListBean.DataBean>();
                            for (PlayerListBean.DataBean dataBean : mData) {
                                dataBean.setSectionData(dataBean.getEnName().substring(0, 1));
                                list.add(dataBean);
                            }
                            mView.getPlayerList(list);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            mView.showViewError(throwable);
                        }
                    }));
        }
    }

    private List<PlayerListBean.DataBean> showPlayerData() {
        ArrayList<PlayerListBean.DataBean> list = new ArrayList<>();
        for (int i = start, j = 0; i < mData.size() && j < rows; i++, start++, j++) {
            PlayerListBean.DataBean dataBean = mData.get(i);
            dataBean.setSectionData(dataBean.getEnName().substring(0, 1));
            list.add(dataBean);
        }
        return list;
    }
}
