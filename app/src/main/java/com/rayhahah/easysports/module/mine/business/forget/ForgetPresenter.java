package com.rayhahah.easysports.module.mine.business.forget;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.mine.api.MineApiFactory;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.rbase.base.RBasePresenter;

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
 * @blog http://rayhahah.com
 * @time 2017/9/15
 * @tips 这个类是Object的子类
 * @fuction
 */
public class ForgetPresenter extends RBasePresenter<ForgetContract.IForgetView> implements ForgetContract.IForgetPresenter {
    public ForgetPresenter(ForgetContract.IForgetView view) {
        super(view);
    }

    @Override
    public void getQuestion(String username) {
        addSubscription(MineApiFactory.forgetGetQuestion(username).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    String question = rResponse.getData();
                    mView.getQuestionSuccess(question);
                } else {
                    mView.requestFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.requestFailed(throwable.getMessage());
            }
        }));
    }

    @Override
    public void checkAnswer(String username, String question, String answer) {
        addSubscription(MineApiFactory.forgetCheckAnswer(username, question, answer).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    mView.checkAnswerSuccess(rResponse.getData());

                } else {
                    mView.requestFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.requestFailed(throwable.getMessage());
            }
        }));
    }

    @Override
    public void resetPassword(String username, String passwordNew, String token) {
        addSubscription(MineApiFactory.forgetResetPassword(username, passwordNew, token).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    mView.resetPasswordSuccess(rResponse.getMsg());
                } else {
                    mView.requestFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.requestFailed(throwable.getMessage());
            }
        }));
    }
}
