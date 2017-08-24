package com.rayhahah.easysports.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.ActivityRwebBinding;
import com.rayhahah.rbase.base.RBasePresenter;

public class RWebActivity extends BaseActivity<RBasePresenter, ActivityRwebBinding> {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_BOTTOM_BAR = "BUNDLE_KEY_BOTTOM_BAR";
    public static final String BUNDLE_KEY_OVERRIDE_URL = "BUNDLE_KEY_OVERRIDE_URL";
    private String mUrl;
    private String mTitle;
    private String mIsShowBottomBar;
    private String mIsOverrideUrl;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mUrl = getValueFromPrePage(BUNDLE_KEY_URL);
        mTitle = getValueFromPrePage(BUNDLE_KEY_TITLE);
        mIsShowBottomBar = getValueFromPrePage(BUNDLE_KEY_BOTTOM_BAR);
        mIsOverrideUrl = getValueFromPrePage(BUNDLE_KEY_OVERRIDE_URL);
        mBinding.toolbar.tvToolbarTitle.setText("详细内容");
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.blWebview.setOverrideUrlLoading(C.TRUE.equals(mIsOverrideUrl));
        mBinding.blWebview.loadUrl(mUrl);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_rweb;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void onPause() {
        if (mBinding.blWebview.getWebView() != null) {
            mBinding.blWebview.getWebView().onPause();
            mBinding.blWebview.getWebView().reload();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.blWebview.destory();
        if (mBinding.blWebview.getWebView() != null) {
            mBinding.blWebview.getWebView().removeAllViews();
            mBinding.blWebview.getWebView().destroy();
        }
    }

    public static void start(Context context, Activity preActivity,
                             String url, String title,
                             boolean isShowBottomBar, boolean isOverrideUrlLoading) {
        putParmToNextPage(BUNDLE_KEY_URL, url);
        putParmToNextPage(BUNDLE_KEY_TITLE, title);
        putParmToNextPage(BUNDLE_KEY_BOTTOM_BAR, isShowBottomBar ? C.TRUE : C.FALSE);
        putParmToNextPage(BUNDLE_KEY_OVERRIDE_URL, isOverrideUrlLoading ? C.TRUE : C.FALSE);
        toNextActivity(context, RWebActivity.class, preActivity);
    }

}
