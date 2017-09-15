package com.rayhahah.easysports.module.mine.business.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityLoginBinding;
import com.rayhahah.easysports.module.mine.business.account.AccountActivity;
import com.rayhahah.easysports.module.mine.business.forget.ForgetActivity;
import com.rayhahah.easysports.module.mine.business.register.RegisterActivity;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            //返回上级
            case R.id.iv_toolbar_back:
                finish();
                break;
            //登陆
            case R.id.btn_mine_login:
                DialogUtil.showLoadingDialog(mContext, "正在登陆", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
                mPresenter.login(mBinding.etMineLoginUsername.getText().toString()
                        , mBinding.etMineLoginPassword.getText().toString());
                break;
            //注册
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
            //忘记密码
            case R.id.tv_mine_register_forget:
                ForgetActivity.start(mContext,mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ToastUtils.showShort("登陆成功！");
        DialogUtil.dismissDialog(true);
        EventBus.getDefault().post(new MsgEvent(C.EventAction.UPDATE_CURRENT_USER, null));
        AccountActivity.start(mContext, mContext);
        finish();
    }

    @Override
    public void loginFailed() {
        ToastUtils.showShort("账号或密码不匹配");
        DialogUtil.dismissDialog(false);
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
        mBinding.tvMineRegisterForget.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MsgEvent event) {
        if (C.EventAction.UPDATE_CURRENT_USER.equals(event.getAction())) {
            if (C.TRUE.equals(SPManager.get().getStringValue(C.SP.IS_LOGIN))) {
                finish();
            }
        }
    }

}
