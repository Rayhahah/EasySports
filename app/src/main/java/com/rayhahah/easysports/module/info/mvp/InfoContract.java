package com.rayhahah.easysports.module.info.mvp;

import android.support.v4.util.ArrayMap;

import com.rayhahah.easysports.module.info.bean.InfoIndex;
import com.rayhahah.easysports.module.info.bean.StatusRank;
import com.rayhahah.easysports.module.info.bean.TeamRank;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class InfoContract {
    public interface IInfoView extends IRBaseView {

        void getStatusRankSuccess(StatusRank statusRank);

        void getStatusRankFailed(Throwable throwable);

        void getTeamRankSuccess(TeamRank teamRank);

        void getTeamRankFailed(Throwable throwable);
    }

    public interface IInfoPresenter {
        void getStatusRank(String statusType, int rows, String tagType);
        void getTeamRank();

        List<InfoIndex> getIndexData(ArrayMap<String, Integer> themeColorMap);
    }
}
