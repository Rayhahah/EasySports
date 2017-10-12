package com.rayhahah.easysports.module.match.busniess.matchvideo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMatchVideoBinding;
import com.rayhahah.easysports.module.match.bean.MatchVideo;
import com.rayhahah.easysports.module.match.domain.MatchVideoAdapter;
import com.rayhahah.easysports.view.DividerItemDecoration;
import com.rayhahah.rbase.bean.MsgEvent;

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
public class MatchVideoFragment extends BaseFragment<MatchVideoPresenter, FragmentMatchVideoBinding>
        implements MatchVideoContract.IMatchVideoView {


    private String mId;
    private MatchVideoAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public static BaseFragment newInstance(String mid) {
        MatchVideoFragment fragment = new MatchVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.MATCH.BUNDLE_MID, mid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected MatchVideoPresenter getPresenter() {
        return new MatchVideoPresenter(this);
    }


    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_match_video;
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

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {
        mBinding.pl.showError(mBinding.rvMatchVideo);
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

    private void initRv() {
        mAdapter = new MatchVideoAdapter();
        mAdapter.openLoadAnimation();
        mBinding.rvMatchVideo.addItemDecoration(new DividerItemDecoration(mContext
                , DividerItemDecoration.HORIZONTAL_LIST
                , 5, mThemeColorMap.get(C.ATTRS.COLOR_BG)));
        mBinding.rvMatchVideo.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mBinding.rvMatchVideo.setLayoutManager(mLayoutManager);
    }

    private void initData() {
        mBinding.pl.showLoading(mBinding.rvMatchVideo);
        mPresenter.getVideoData(mId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(MsgEvent event) {
        if (C.EventAction.REFRESH_MATCH_DATA.equals(event.getAction())) {
            // TODO: 2017/10/10 感觉会消耗很多流量 所以决定取消监听
//            mPresenter.getVideoData(mId);
        }
    }

    @Override
    public void getVideoDataSuccess(List<MatchVideo.VideoBean> data) {
        mAdapter.setNewData(data);
        mBinding.pl.showContent(mBinding.rvMatchVideo);
    }

}
