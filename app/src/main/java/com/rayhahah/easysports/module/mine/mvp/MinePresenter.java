package com.rayhahah.easysports.module.mine.mvp;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/17.
 */

public class MinePresenter extends RBasePresenter<MineContract.IMineView>
        implements MineContract.IMinePresenter {
    public MinePresenter(MineContract.IMineView view) {
        super(view);
    }
}
