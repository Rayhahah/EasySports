package com.rayhahah.easysports.module.live.mvp;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/17.
 */

public class LivePresenter extends RBasePresenter<LiveContract.ILiveView>
        implements LiveContract.ILivePresenter {
    public LivePresenter(LiveContract.ILiveView view) {
        super(view);
    }
}
