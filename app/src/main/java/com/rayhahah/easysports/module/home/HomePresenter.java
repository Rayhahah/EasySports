package com.rayhahah.easysports.module.home;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/16.
 */

public class HomePresenter extends RBasePresenter<HomeContract.IHomeView>
        implements HomeContract.IHomePresenter {

    public HomePresenter(HomeContract.IHomeView view) {
        super(view);
    }

}
