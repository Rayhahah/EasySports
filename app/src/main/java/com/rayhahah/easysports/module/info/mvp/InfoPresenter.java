package com.rayhahah.easysports.module.info.mvp;

import android.support.v4.util.ArrayMap;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.info.api.InfoApiFactory;
import com.rayhahah.easysports.module.info.bean.InfoIndex;
import com.rayhahah.easysports.module.info.bean.StatusRank;
import com.rayhahah.easysports.module.info.bean.TeamRank;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by a on 2017/5/17.
 */

public class InfoPresenter extends RBasePresenter<InfoContract.IInfoView>
        implements InfoContract.IInfoPresenter {
    public InfoPresenter(InfoContract.IInfoView view) {
        super(view);
    }

    @Override
    public void getStatusRank(String statusType, int rows, String tagType) {
        addSubscription(InfoApiFactory.getStatsRank(statusType, rows, tagType, C.INFO.CURRENT_SEASON)
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        String string = responseBody.string();
                        StatusRank statusRank = JsonParser.parseStatusRank(string);
                        if (statusRank != null) {
                            mView.getStatusRankSuccess(statusRank);
                        } else {
                            mView.getStatusRankFailed(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.getStatusRankFailed(throwable);
                    }
                }));
    }

    @Override
    public void getTeamRank() {
        addSubscription(InfoApiFactory.getTeamRank().subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                TeamRank rank = JsonParser.parseTeamRank(responseBody.string());
                if (rank != null) {
                    rank.all = new ArrayList<>();
                    TeamRank.TeamBean eastTitle = new TeamRank.TeamBean();
                    eastTitle.type = 1;
                    eastTitle.name = "东部联盟";
                    rank.all.add(eastTitle);
                    rank.all.addAll(rank.east);

                    TeamRank.TeamBean westTitle = new TeamRank.TeamBean();
                    westTitle.type = 2;
                    westTitle.name = "西部联盟";
                    rank.all.add(westTitle);
                    rank.all.addAll(rank.west);
                    mView.getTeamRankSuccess(rank);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.getTeamRankFailed(throwable);
            }
        }));
    }

    @Override
    public List<InfoIndex> getIndexData(ArrayMap<String, Integer> themeColorMap) {
        ArrayMap<String, Integer> attrs = new ArrayMap<>();
        attrs.put(InfoIndex.ATTR.CHECKED_TEXT_COLOR,themeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        attrs.put(InfoIndex.ATTR.UNCHECKED_TEXT_COLOR,themeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT));
        attrs.put(InfoIndex.ATTR.CHECKED_BG_COLOR,themeColorMap.get(C.ATTRS.COLOR_BG));
        attrs.put(InfoIndex.ATTR.UNCHECKED_BG_COLOR,themeColorMap.get(C.ATTRS.COLOR_BG_DARK));

        List<InfoIndex> data=new ArrayList<>();

        InfoIndex indexEast = new InfoIndex();
        indexEast.setTitle("东部联盟");
        indexEast.setId(C.INFO.ID_EAST);
        indexEast.setAttrs(attrs);
        data.add(indexEast);

        InfoIndex indexWest = new InfoIndex();
        indexWest.setTitle("西部联盟");
        indexWest.setId(C.INFO.ID_WEST);
        indexWest.setAttrs(attrs);
        data.add(indexWest);

        InfoIndex indexPoint = new InfoIndex();
        indexPoint.setTitle("得分");
        indexPoint.setId(C.INFO.ID_POINT);
        indexPoint.setAttrs(attrs);
        data.add(indexPoint);

        InfoIndex indexRebound = new InfoIndex();
        indexRebound.setTitle("篮板");
        indexRebound.setId(C.INFO.ID_REBOUND);
        indexRebound.setAttrs(attrs);
        data.add(indexRebound);

        InfoIndex indexAssist = new InfoIndex();
        indexAssist.setTitle("助攻");
        indexAssist.setId(C.INFO.ID_ASSIST);
        indexAssist.setAttrs(attrs);
        data.add(indexAssist);

        InfoIndex indexBlock = new InfoIndex();
        indexBlock.setTitle("盖帽");
        indexBlock.setId(C.INFO.ID_BLOCK);
        indexBlock.setAttrs(attrs);
        data.add(indexBlock);

        InfoIndex indexSteal = new InfoIndex();
        indexSteal.setTitle("抢断");
        indexSteal.setId(C.INFO.ID_STEAL);
        indexSteal.setAttrs(attrs);
        data.add(indexSteal);

        return data;
    }
}
