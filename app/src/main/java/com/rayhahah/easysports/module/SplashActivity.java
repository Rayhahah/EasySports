package com.rayhahah.easysports.module;

import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivitySplashBinding;
import com.rayhahah.easysports.module.home.HomeActivity;
import com.rayhahah.easysports.utils.AnimatorUtil;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SplashActivity extends BaseActivity<RBasePresenter, ActivitySplashBinding> {

    private int count = 4;
    private Disposable timer;

    @Override
    protected void setStatusColor() {
    }

    @Override
    protected void initTheme() {
        setTheme(R.style.NoTitleFullscreen);
    }

    @Override
    public void initValueFromPrePage() {
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        AnimatorUtil.animAplhaIn(mBinding.rlSplash,1000,null);
        timer = Observable
                .interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return count - aLong;
                    }
                }).compose(RxSchedulers.<Long>ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mBinding.tvSplashSkip.setText("跳过(" + aLong + "s)");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        HomeActivity.start(SplashActivity.this, SplashActivity.this);
                        finish();
                    }
                });

        GlideUtil.load(this, R.mipmap.bryant_cover, mBinding.ivSplashCover);
        mBinding.tvSplashSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.start(SplashActivity.this, SplashActivity.this);
                if (timer != null && !timer.isDisposed()) {
                    timer.dispose();
                }
                finish();
            }
        });
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null && !timer.isDisposed()) {
            timer.dispose();
        }
    }
}
