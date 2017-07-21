package com.rayhahah.easysports.module.mine.business.teamplayer;

import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/6/28.
 */

public class SingleListContract {
    public interface ISingleListView extends IRBaseView {

        void getTeamList(List<TeamListBean.DataBean.TeamBean> teamList);

        void getPlayerList(List<PlayerListBean.DataBean> playerList);
    }

    public interface ISingleListPresenter{
        void getTeamList();

        void getPlayerList();
    }
}
