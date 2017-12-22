package com.rayhahah.easysports.module.forum.mvp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentForumBinding;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.easysports.module.forum.business.forumdetaillist.ForumDetailListActivity;
import com.rayhahah.easysports.module.forum.domain.ForumListAdapter;
import com.rayhahah.easysports.module.mine.business.account.AccountActivity;
import com.rayhahah.easysports.module.mine.business.login.LoginActivity;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.easysports.view.DividerItemDecoration;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.StringUtils;
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
        implements ForumContract.IForumView, View.OnClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private ForumListAdapter mAdapter;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        /**
         * 因为虎扑的登陆接口Sign的生成规则发生改变
         * 所以这里先用硬编码
         */
        if (StringUtils.isEmpty(SPManager.get().getStringValue(C.SP.HUPU_TOKEN))) {
            SPManager.get().putString(C.SP.HUPU_TOKEN, C.ACCOUNT.DEFAULT_HUPU_TOKEN);
        }

        if (StringUtils.isEmpty(SPManager.get().getStringValue(C.SP.HUPU_UID))) {
            SPManager.get().putString(C.SP.HUPU_UID, C.ACCOUNT.DEFAULT_HUPU_UID);
        }
        if (StringUtils.isEmpty(SPManager.get().getStringValue(C.SP.HUPU_NICKNAME))) {
            SPManager.get().putString(C.SP.HUPU_NICKNAME, C.ACCOUNT.DEFAULT_HUPU_NICKNAME);
        }
        EventBus.getDefault().register(this);
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.forum));
        initPL();
        initBtn();
        initRv();
    }

    private void initPL() {
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllForumsData();
            }
        });
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
    }

    private void initRv() {
        mAdapter = new ForumListAdapter();
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemChildClickListener(this);
        mBinding.rvForumList.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.BOTH_SET,3,mThemeColorMap.get(C.ATTRS.COLOR_BG_DARK)));
        mBinding.rvForumList.setAdapter(mAdapter);
        mBinding.rvForumList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initBtn() {
        mBinding.btnForumBindHupu.setOnClickListener(this);
        if (C.NULL.equals(SPManager.get().getStringValue(C.SP.HUPU_TOKEN))) {
            mBinding.rvForumList.setVisibility(View.GONE);
            mBinding.pl.setVisibility(View.GONE);
            mBinding.btnForumBindHupu.setVisibility(View.VISIBLE);
            if (C.FALSE.equals(SPManager.get().getStringValue(C.SP.IS_LOGIN))) {
                mBinding.btnForumBindHupu.setText(R.string.login_first);
            } else {
                mBinding.btnForumBindHupu.setText(R.string.bind_hupu_first);
            }
        } else {
            mBinding.rvForumList.setVisibility(View.VISIBLE);
            mBinding.pl.setVisibility(View.VISIBLE);
            mBinding.btnForumBindHupu.setVisibility(View.GONE);
            getAllForumsData();
        }
    }

    private void getAllForumsData() {
        mBinding.pl.showLoading(mBinding.rvForumList);
//        DialogUtil.showLoadingDialog(mContext, "正在获取板块数据", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mPresenter.getAllForums();
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
            default:
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
        mBinding.pl.showError(mBinding.rvForumList);
    }

    @Override
    public void getAllForumsSuccess(List<ForumsData.Forum> data) {
//        DialogUtil.dismissDialog(true);
        ToastUtils.showShort("获取板块数据成功");
        mBinding.pl.showContent(mBinding.rvForumList);
        mAdapter.setNewData(data);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<ForumsData.Forum> data = adapter.getData();
        switch (view.getId()) {
            case R.id.ll_item_forum_title:
                ForumDetailListActivity.start(mContext, data.get(position));
                break;
            default:
                break;
        }
    }
}
