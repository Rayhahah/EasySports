package com.rayhahah.easysports.module.mine.business.forget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityForgetBinding;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;

public class ForgetActivity extends BaseActivity<ForgetPresenter, ActivityForgetBinding> implements ForgetContract.IForgetView {

    int currentTag = C.FORGET.TAG_GET_QUESTION;
    private String mUsername;
    private String mQuestion;
    private String mToken;

    public static void start(Context context, Activity activity) {
        toNextActivity(context, ForgetActivity.class, activity);
    }

    @Override
    protected ForgetPresenter getPresenter() {
        return new ForgetPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_forget;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initToolbar();
        mBinding.tvForgetClick.setText("获取问题");
    }

    private void initToolbar() {
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.tvToolbarTitle.setText("忘记密码");
        mBinding.toolbar.ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    public void forgetCilckEvent(View view) {
        switch (currentTag) {
            case C.FORGET.TAG_GET_QUESTION:
                mUsername = mBinding.etForgetUsername.getText().toString();
                if (StringUtils.isNotEmpty(mUsername)) {
                    DialogUtil.showLoadingDialog(this, "正在获取重置密码问题", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
                    mPresenter.getQuestion(mUsername);
                } else {
                    ToastUtils.showShort("用户名不能为空");
                }
                break;
            case C.FORGET.TAG_ANSWER:
                String answer = mBinding.etForgetAnswer.getText().toString();
                if (StringUtils.isNotEmpty(answer)) {
                    DialogUtil.showLoadingDialog(this, "正在验证答案", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
                    mPresenter.checkAnswer(mUsername, mQuestion, answer);
                } else {
                    ToastUtils.showShort("答案不能为空");
                }
                break;
            case C.FORGET.TAG_RESET_PASSWORD:
                String passwordNew = mBinding.etForgetPassword.getText().toString();
                if (StringUtils.isNotEmpty(passwordNew)) {
                    DialogUtil.showLoadingDialog(this, "正在重置密码", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
                    mPresenter.resetPassword(mUsername, passwordNew, mToken);
                } else {
                    ToastUtils.showShort("新密码不能为空");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void getQuestionSuccess(String question) {
        DialogUtil.dismissDialog(true);
        currentTag = C.FORGET.TAG_ANSWER;
        mBinding.etForgetUsername.setEnabled(false);
        mBinding.tvForgetQuestion.setVisibility(View.VISIBLE);
        mBinding.etForgetQuestion.setVisibility(View.VISIBLE);
        mQuestion = question;
        mBinding.etForgetQuestion.setText(mQuestion);
        mBinding.tvForgetAnswer.setVisibility(View.VISIBLE);
        mBinding.etForgetAnswer.setVisibility(View.VISIBLE);
        ToastUtils.showShort("获取问题成功");
        mBinding.tvForgetClick.setText("验证答案");
    }

    @Override
    public void checkAnswerSuccess(String token) {
        DialogUtil.dismissDialog(true);
        currentTag = C.FORGET.TAG_RESET_PASSWORD;
        mToken = token;
        mBinding.etForgetAnswer.setEnabled(false);
        mBinding.tvForgetPassword.setVisibility(View.VISIBLE);
        mBinding.etForgetPassword.setVisibility(View.VISIBLE);

        ToastUtils.showShort("请设置新密码");
        mBinding.tvForgetClick.setText("重置密码");
    }

    @Override
    public void resetPasswordSuccess(String msg) {
        DialogUtil.dismissDialog(true);
        ToastUtils.showShort(msg);
        finish();
    }

    @Override
    public void requestFailed(String msg) {
        DialogUtil.dismissDialog(false);
        ToastUtils.showShort(msg);
    }


}
