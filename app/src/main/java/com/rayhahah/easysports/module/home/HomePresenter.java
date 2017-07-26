package com.rayhahah.easysports.module.home;

import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.bean.db.LocalUser;
import com.rayhahah.greendao.gen.LocalUserDao;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by a on 2017/5/16.
 */

public class HomePresenter extends RBasePresenter<HomeContract.IHomeView>
        implements HomeContract.IHomePresenter {

    public HomePresenter(HomeContract.IHomeView view) {
        super(view);
    }

    @Override
    public void getCurrentUser(String username) {
        Observable.just(username).map(new Function<String, LocalUser>() {
            @Override
            public LocalUser apply(@NonNull String username) throws Exception {
                LocalUserDao localUserDao = MyApp.getDaoSession().getLocalUserDao();
                LocalUser localUser = localUserDao.queryBuilder().where(LocalUserDao.Properties.User_name.eq(username))
                        .build().unique();
                return localUser;
            }
        }).compose(RxSchedulers.<LocalUser>ioMain()).subscribe(new Consumer<LocalUser>() {
            @Override
            public void accept(@NonNull LocalUser localUser) throws Exception {
                if (localUser != null) {
                    MyApp.setCurrentUser(localUser);
                }
            }
        });
    }
}
