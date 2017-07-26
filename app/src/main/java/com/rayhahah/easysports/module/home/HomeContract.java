package com.rayhahah.easysports.module.home;

import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by a on 2017/5/16.
 */

public class HomeContract {

    public interface IHomeView extends IRBaseView {

    }

    public interface IHomePresenter {

        void getCurrentUser(String username);
    }
}
