package com.rayhahah.easysports.module.mine.business.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.databinding.ActivityLoginBinding;
import com.rayhahah.easysports.module.mine.business.account.AccountActivity;
import com.rayhahah.easysports.module.mine.business.register.RegisterActivity;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.DialogUtil;
import com.rayhahah.rbase.utils.base.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding>
        implements LoginContract.ILoginView, View.OnClickListener {

    public static void start(Context context, Activity preActivity) {
        toNextActivity(context, LoginActivity.class, preActivity);
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initToolBar();
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.btn_mine_login:
                DialogUtil.showLoadingDialog(mContext, "正在登陆");
                mPresenter.login(mBinding.etMineLoginUsername.getText().toString()
                        , mBinding.etMineLoginPassword.getText().toString());
                break;
            case R.id.tv_mine_register_now:
                RegisterActivity.start(mContext, mContext);
                break;
            // TODO: 2017/7/24 第三方登陆以及短信推送登陆功能
            case R.id.iv_mine_login_wechat:

                break;
            case R.id.iv_mine_login_qq:

                break;
            case R.id.iv_mine_login_tel:

                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ToastUtils.showShort("登陆成功！");
        DialogUtil.dismissDialog();
        EventBus.getDefault().post(new MsgEvent(C.EventAction.UPDATE_CURRENT_USER, null));
        AccountActivity.start(mContext, mContext);
        finish();
    }

    @Override
    public void loginFailed() {
        ToastUtils.showShort("账号或密码不匹配");
        DialogUtil.dismissDialog();
    }

    private void initToolBar() {
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.mine_login_myaccount));
        mBinding.toolbar.ivToolbarBack.setOnClickListener(this);
    }

    private void initView() {
        mBinding.btnMineLogin.setOnClickListener(this);
        mBinding.tvMineRegisterNow.setOnClickListener(this);
        mBinding.ivMineLoginWechat.setOnClickListener(this);
        mBinding.ivMineLoginQq.setOnClickListener(this);
        mBinding.ivMineLoginTel.setOnClickListener(this);
    }

}
