package com.rayhahah.easysports.module.match.mvp;

import com.rayhahah.rbase.base.IRBasePresenter;
import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by a on 2017/5/17.
 */

public class MatchContract {
    public interface IMatchView  extends IRBaseView{
        void showMatchList();

    }

    public interface IMatchPresenter extends IRBasePresenter {
        void getMatchList();

    }
}
