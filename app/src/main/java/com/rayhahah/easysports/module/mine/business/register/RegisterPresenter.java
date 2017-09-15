package com.rayhahah.easysports.module.mine.business.register;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.bean.db.LocalUser;
import com.rayhahah.easysports.module.mine.api.MineApiFactory;
import com.rayhahah.easysports.module.mine.bean.ESUser;
import com.rayhahah.greendao.gen.LocalUserDao;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
    public void registerNewUser(String userName, String password, String screenName, String question, String answer, String tel, String email, String hupuUsername, String hupuPassword) {
        HashMap<String, String> params = new HashMap<>();
        params.put(C.MINE.USERNAME, userName);
        params.put(C.MINE.PASSWORD, password);
        params.put(C.MINE.SCREENNAME, screenName);
        params.put(C.MINE.QUESTION, question);
        params.put(C.MINE.ANSWER, answer);
        params.put(C.MINE.PHONE, tel);
        params.put(C.MINE.EMAIL, email);
        params.put(C.MINE.HUPU_USERNAME, hupuUsername);
        params.put(C.MINE.HUPU_PASSWORD, hupuPassword);
        addSubscription(MineApiFactory.register(params).subscribe(new Consumer<ESUser>() {
            @Override
            public void accept(@NonNull ESUser esUser) throws Exception {
                if (esUser.getStatus() != C.RESPONSE_SUCCESS) {
                    mView.registerFailed(null);
                    return;
                }
                LocalUserDao localUserDao = MyApp.getDaoSession().getLocalUserDao();
                String isNight = SPManager.get().getStringValue(C.SP.THEME, C.FALSE);
                ESUser.DataBean user = esUser.getData();
                LocalUser localUser = new LocalUser(user.getId() + "", user.getUsername(), user.getPassword(), user.getScreenname()
                        , user.getPhone(), user.getEmail(), user.getQuestion(), user.getAnswer()
                        , user.getCover(), user.getHupuUsername(), user.getHupuPassword(), isNight);
                long rowId = localUserDao.insert(localUser);

                if (rowId > 0) {
                    SPManager.get().putString(C.SP.IS_LOGIN, C.TRUE);
                    SPManager.get().putString(C.SP.CURRENT_USER, user.getUsername());
                    MyApp.setCurrentUser(localUser);
                    mView.registerSuccess();
                } else {
                    mView.registerFailed(null);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.registerFailed(throwable);
            }
        }));
    }
}
