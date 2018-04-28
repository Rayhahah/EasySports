package com.rayhahah.easysports.module.match.mvp;

import com.rayhahah.easysports.module.match.bean.MatchListBeanNew;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class MatchContract {
    public interface IMatchView extends IRBaseView {
        void addMatchListData(List<MatchListBeanNew.DataBean.MatchesBean> data, int status);

        void addMatchListDataFailed(Throwable throwable, int status);

        String getCurrentDate();
    }

    public interface IMatchPresenter  {
        void addMatchListData(String data, int status);
    }
}
