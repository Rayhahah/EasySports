package com.rayhahah.easysports.module.live.mvp;

import com.rayhahah.easysports.module.live.api.LiveApiFactory;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.RLog;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by a on 2017/5/17.
 */

public class LivePresenter extends RBasePresenter<LiveContract.ILiveView>
        implements LiveContract.ILivePresenter {
    public LivePresenter(LiveContract.ILiveView view) {
        super(view);
    }



}
