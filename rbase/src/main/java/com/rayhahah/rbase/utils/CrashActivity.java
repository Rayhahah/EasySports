package com.rayhahah.rbase.utils;

import android.os.Bundle;
import android.view.View;

import com.rayhahah.rbase.R;
import com.rayhahah.rbase.base.ActivityCollector;
import com.rayhahah.rbase.base.RBaseActivity;
import com.rayhahah.rbase.base.RBasePresenter;

public class CrashActivity extends RBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    public void crashClick(View v) {
        ActivityCollector.finishAll();
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_crash;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }
}
