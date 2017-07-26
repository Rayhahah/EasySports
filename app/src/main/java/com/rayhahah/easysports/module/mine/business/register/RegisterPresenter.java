package com.rayhahah.easysports.module.mine.business.register;

import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.bean.db.LocalUser;
import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.mine.bean.BmobUsers;
import com.rayhahah.greendao.gen.LocalUserDao;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author Rayhahah
 * @time 2017/7/21
 * @tips 这个类是Object的子类
 * @fuction
 */
public class RegisterPresenter extends RBasePresenter<RegisterContract.IRegisterView>
        implements RegisterContract.IRegisterPresenter {
    public RegisterPresenter(RegisterContract.IRegisterView view) {
        super(view);
    }

    @Override
    public void registerNewUser(final String userName, final String password, final String screenName, final String tel, final String hupuUsername, final String hupuPassword) {
        BmobQuery<BmobUsers> usersBmobQuery = new BmobQuery<>();
        usersBmobQuery.addWhereEqualTo("userName", userName).findObjects(new FindListener<BmobUsers>() {
            @Override
            public void done(List<BmobUsers> list, BmobException e) {
                if (list.size() != 0) {
                    mView.registerFailed(e);
                    return;
                }

                BmobUsers bmobUsers = new BmobUsers(userName, password, screenName, tel, C.NULL, hupuUsername, hupuPassword);
                bmobUsers.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            LocalUserDao localUserDao = MyApp.getDaoSession().getLocalUserDao();
                            String isNight = SPManager.get().getStringValue(C.SP.THEME, C.FALSE);
                            LocalUser localUser = new LocalUser(s, userName, password, screenName, tel,
                                    C.NULL, hupuUsername, hupuPassword, C.NULL, isNight);
                            long rowId = localUserDao.insert(localUser);

                            if (rowId > 0) {
                                SPManager.get().putString(C.SP.IS_LOGIN, C.TRUE);
                                SPManager.get().putString(C.SP.CURRENT_USER, userName);
                                MyApp.setCurrentUser(localUser);
                                mView.registerSuccess();
                            } else {
                                mView.registerFailed(e);
                            }
                        } else {
                            mView.registerFailed(e);
                        }
                    }
                });
            }
        });
    }
}
