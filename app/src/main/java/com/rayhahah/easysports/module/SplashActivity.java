package com.rayhahah.easysports.module;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivitySplashBinding;
import com.rayhahah.easysports.module.home.HomeActivity;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;

public class SplashActivity extends BaseActivity<RBasePresenter, ActivitySplashBinding> {

    private CountDownTimer mTimer;

    @Override
    protected void setStatusColor() {
    }

    protected void initTheme() {
        setTheme(R.style.NoTitleFullscreen);
    }

    @Override
    public void initValueFromPrePage() {
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        GlideUtil.load(this, R.mipmap.bryant_cover, mBinding.ivSplashCover);
        mBinding.tvSplashSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.start(SplashActivity.this, SplashActivity.this);
                mTimer.cancel();
                finish();
            }
        });
        mTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBinding.tvSplashSkip.setText("跳过(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                HomeActivity.start(SplashActivity.this, SplashActivity.this);
                finish();
            }
        };
        mTimer.start();
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
        mTimer.cancel();
    }
}
