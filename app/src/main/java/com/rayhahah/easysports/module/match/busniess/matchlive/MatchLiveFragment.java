package com.rayhahah.easysports.module.match.busniess.matchlive;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMatchLiveBinding;
import com.rayhahah.easysports.module.match.bean.LiveDetail;
import com.rayhahah.easysports.module.match.domain.MatchLiveAdapter;
import com.rayhahah.easysports.view.ProgressLayout;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author Rayhahah
 * @blog http://rayhahah.com
 * @time 2017/9/30
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchLiveFragment extends BaseFragment<MatchLivePresenter, FragmentMatchLiveBinding> implements MatchLiveContract.IMatchLiveView, BaseQuickAdapter.RequestLoadMoreListener {

    private String mId;
    private MatchLiveAdapter mAdapter;

    public static BaseFragment newInstance(String mid) {
        MatchLiveFragment fragment = new MatchLiveFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.MATCH.BUNDLE_MID, mid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {
        if (mBinding.pl.getStatus() == ProgressLayout.STATUS_LOADING) {
            mBinding.tvLiveNull.setVisibility(View.GONE);
            mBinding.viewLine.setVisibility(View.GONE);
            mBinding.pl.showError(mBinding.rvMatchLive);
        }
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_match_live;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mId = getValueFromPrePage(C.MATCH.BUNDLE_MID);
        EventBus.getDefault().register(this);
        initPL();
        initRv();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void initData() {
        mBinding.pl.showLoading(mBinding.rvMatchLive);
        mBinding.tvLiveNull.setVisibility(View.GONE);
        mBinding.viewLine.setVisibility(View.GONE);
        mPresenter.getLiveIndex(mId);
    }

    private void initRv() {
        mAdapter = new MatchLiveAdapter(mContext);
        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this, mBinding.rvMatchLive);
        mBinding.rvMatchLive.setAdapter(mAdapter);
        mBinding.rvMatchLive.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    private void initPL() {
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
    }

    @Override
    protected MatchLivePresenter getPresenter() {
        return new MatchLivePresenter(this);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreContent(mId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(MsgEvent event) {
        if (C.EventAction.REFRESH_MATCH_DATA.equals(event.getAction())) {
            mPresenter.getLiveIndex(mId);
        }
    }

    @Override
    public void getLiveDataSuccess(List<LiveDetail.LiveContent> detail, boolean front) {
        if (front) {
            mAdapter.addData(0, detail);
        } else {
            mAdapter.addData(detail);
            mAdapter.loadMoreComplete();
        }
        mBinding.tvLiveNull.setVisibility(View.GONE);
        mBinding.viewLine.setVisibility(View.VISIBLE);
        mBinding.rvMatchLive.setVisibility(View.VISIBLE);
        mBinding.pl.setVisibility(View.GONE);
    }

    @Override
    public void getLiveDataFailed(String message, boolean front) {
        if (mBinding.pl.getStatus() == ProgressLayout.STATUS_LOADING) {
            mBinding.tvLiveNull.setVisibility(View.VISIBLE);
            mBinding.viewLine.setVisibility(View.GONE);
            mBinding.rvMatchLive.setVisibility(View.GONE);
            mBinding.pl.setVisibility(View.GONE);
        } else {
            mBinding.tvLiveNull.setVisibility(View.GONE);
            mBinding.viewLine.setVisibility(View.VISIBLE);
            mBinding.rvMatchLive.setVisibility(View.VISIBLE);
            mBinding.pl.setVisibility(View.GONE);
        }
        ToastUtils.showShort(message);
        mAdapter.loadMoreComplete();
    }
}
