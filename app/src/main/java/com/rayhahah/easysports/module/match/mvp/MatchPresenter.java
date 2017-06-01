package com.rayhahah.easysports.module.match.mvp;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/17.
 */

public class MatchPresenter extends RBasePresenter<MatchContract.IMatchView>
        implements MatchContract.IMatchPresenter {
    public MatchPresenter(MatchContract.IMatchView view) {
        super(view);
    }

    @Override
    public void getMatchList() {

    }
}
