package com.rayhahah.easysports.module.live.mvp;

import com.rayhahah.easysports.module.live.bean.MatchListBean;
import com.rayhahah.rbase.base.IRBasePresenter;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class LiveContract {
    public interface ILiveView extends IRBaseView {
        void addMatchListData(List<MatchListBean.MatchInfoBean> data);
    }

    public interface ILivePresenter extends IRBasePresenter {
        void addMatchListData(String data);

    }
}
