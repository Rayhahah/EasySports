package com.rayhahah.easysports.module.mine.business.register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.ActivityRegisterBinding;
import com.rayhahah.easysports.module.mine.business.account.AccountActivity;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class RegisterActivity extends BaseActivity<RegisterPresenter, ActivityRegisterBinding> implements RegisterContract.IRegisterView, View.OnClickListener {


    public static void start(Context context, Activity preActivity) {
        toNextActivity(context, RegisterActivity.class, preActivity);
    }

    @Override
    protected RegisterPresenter getPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initToolBar();
        initView();
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

    @Override
    public void registerSuccess() {
        DialogUtil.dismissDialog(true);
        ToastUtils.showShort(getResources().getString(R.string.register_regist_success));
        EventBus.getDefault().post(new MsgEvent(C.EventAction.UPDATE_CURRENT_USER, null));
        AccountActivity.start(mContext, mContext);
        finish();
    }

    @Override
    public void registerFailed(Throwable e) {
        DialogUtil.dismissDialog(false);
        ToastUtils.showShort(getResources().getString(R.string.register_regist_fail));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.btn_register_confirm:
                if (!checkNecessaryMessage()) {
                    return;
                }
                DialogUtil.showLoadingDialog(mContext, getResources().getString(R.string.registering), mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
                mPresenter.registerNewUser(mBinding.etRegisterUsername.getText().toString()
                        , mBinding.etRegisterPassword.getText().toString()
                        , mBinding.etRegisterScreenname.getText().toString()
                        , mBinding.etRegisterQuestion.getText().toString()
                        , mBinding.etRegisterAnswer.getText().toString()
                        , mBinding.etRegisterTelephone.getText().toString()
                        , mBinding.etRegisterEmail.getText().toString()
                        , mBinding.etRegisterHupuUsername.getText().toString()
                        , mBinding.etRegisterHupuPassword.getText().toString());
                break;
            default:
                break;
        }
    }

    private void initView() {
        mBinding.btnRegisterConfirm.setOnClickListener(this);
    }

    private void initToolBar() {
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(this);
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.register_username));
    }


    private boolean checkNecessaryMessage() {
        if (!StringUtils.isNotEmpty(mBinding.etRegisterUsername.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.username)
                    + getResources().getString(R.string.not_null));
            return false;
        }
        if (!StringUtils.isNotEmpty(mBinding.etRegisterPassword.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.password)
                    + getResources().getString(R.string.not_null));
            return false;
        }
        if (!StringUtils.isNotEmpty(mBinding.etRegisterScreenname.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.screenname)
                    + getResources().getString(R.string.not_null));
            return false;
        }

        if (!StringUtils.isLegalUsername(mBinding.etRegisterPassword.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.password) + "必须是字母和数字的结合");
            return false;
        }
        if (!StringUtils.isNotEmpty(mBinding.etRegisterQuestion.getText().toString())) {
            ToastUtils.showShort("问题不能为空");
            return false;
        }
        if (!StringUtils.isNotEmpty(mBinding.etRegisterAnswer.getText().toString())) {
            ToastUtils.showShort("答案不能为空");
            return false;
        }

        if (StringUtils.isNotEmpty(mBinding.etRegisterTelephone.getText().toString())
                && !StringUtils.isLegalTel(mBinding.etRegisterTelephone.getText().toString())) {
            ToastUtils.showShort(getResources().getString(R.string.telephone)
                    + getResources().getString(R.string.illegal));
            return false;
        }
        return true;
    }
}
