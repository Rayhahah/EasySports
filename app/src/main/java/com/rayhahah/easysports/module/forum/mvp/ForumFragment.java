package com.rayhahah.easysports.module.forum.mvp;

import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.FragmentForumBinding;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.easysports.module.mine.business.account.AccountActivity;
import com.rayhahah.easysports.module.mine.business.login.LoginActivity;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class ForumFragment extends BaseFragment<ForumPresenter, FragmentForumBinding>
        implements ForumContract.IForumView, View.OnClickListener {

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.forum));
        initBtn();
        // TODO: 2017/8/15 完善虎扑登陆接口
        // TODO: 2017/8/15 获取Token以后才能获取社区列表
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBtn() {
        mBinding.btnForumBindHupu.setOnClickListener(this);
        if (C.NULL.equals(SPManager.get().getStringValue(C.SP.TOKEN))) {
            mBinding.rvForumList.setVisibility(View.GONE);
            mBinding.btnForumBindHupu.setVisibility(View.VISIBLE);
            if (C.FALSE.equals(SPManager.get().getStringValue(C.SP.IS_LOGIN))) {
                mBinding.btnForumBindHupu.setText(R.string.login_first);
            } else {
                mBinding.btnForumBindHupu.setText(R.string.bind_hupu_first);
            }
        } else {
            mBinding.rvForumList.setVisibility(View.VISIBLE);
            mBinding.btnForumBindHupu.setVisibility(View.GONE);
            DialogUtil.showLoadingDialog(mContext, "正在获取板块数据", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
            mPresenter.getAllForums();
        }
    }

    @Override
    protected ForumPresenter getPresenter() {
        return new ForumPresenter(this);
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
            case R.id.btn_forum_bind_hupu:
                if (C.FALSE.equals(SPManager.get().getStringValue(C.SP.IS_LOGIN))) {
                    LoginActivity.start(mContext, mContext);
                } else {
                    AccountActivity.start(mContext, mContext);
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MsgEvent event) {
        if (C.EventAction.UPDATE_CURRENT_USER.equals(event.getAction())) {
            initBtn();
        }
    }

    @Override
    public void getAllForumsFailed() {
        DialogUtil.dismissDialog(false);
        ToastUtils.showShort("获取板块数据失败");
    }

    @Override
    public void getAllForumsSuccess(List<ForumsData.Forum> data) {
        DialogUtil.dismissDialog(true);


    }
}
