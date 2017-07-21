package com.rayhahah.easysports.module.mine.business.account;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityAccountBinding;

public class AccountActivity extends BaseActivity<AccountPresenter, ActivityAccountBinding>
        implements AccountContract.IAccountView {

    @Override
    protected AccountPresenter getPresenter() {
        return new AccountPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_account;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
