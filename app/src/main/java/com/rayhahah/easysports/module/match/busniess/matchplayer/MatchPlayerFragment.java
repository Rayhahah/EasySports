package com.rayhahah.easysports.module.match.busniess.matchplayer;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMatchPlayerBinding;
import com.rayhahah.easysports.module.match.bean.MatchPlayer;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;
import com.rayhahah.easysports.module.match.domain.MatchPlayerAdapter;
import com.rayhahah.easysports.view.ProgressLayout;
import com.rayhahah.easysports.view.TitleItemDecoration;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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
public class MatchPlayerFragment extends BaseFragment<MatchPlayerPresenter, FragmentMatchPlayerBinding> implements MatchPlayerContract.IMatchPlayerView {

    private String mId;
    private List<MatchStatusBean.PlayerStats> left = new ArrayList<>();
    private List<MatchStatusBean.PlayerStats> right = new ArrayList<>();
    private String mLeftTitle;
    private String mRightTitle;
    private MatchPlayerAdapter mAdapter;
    private TitleItemDecoration mTitleItemDecoration;

    public static BaseFragment newInstance(String mid) {
        MatchPlayerFragment fragment = new MatchPlayerFragment();
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

    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_match_player;
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
        mBinding.pl.showLoading(mBinding.rvMatchPlayer);
        mPresenter.getMatchStatus(mId, C.MATCH.TAB_TPYE_STROKE);
    }

    private void initRv() {
        mAdapter = new MatchPlayerAdapter(mContext);
        mAdapter.openLoadAnimation();
        mBinding.rvMatchPlayer.setAdapter(mAdapter);
        mBinding.rvMatchPlayer.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
    protected MatchPlayerPresenter getPresenter() {
        return new MatchPlayerPresenter(this);
    }

    @Override
    public void getMatchStatusSuccess(List<MatchStatusBean.PlayerStats> playerStats) {
        boolean isLeft = false;
        boolean isRight = false;
        left.clear();
        right.clear();
        MatchPlayer playerLeft = new MatchPlayer();
        MatchPlayer playerRight = new MatchPlayer();
        for (MatchStatusBean.PlayerStats item : playerStats) {
            if (item.subText != null && !isLeft) {
                isLeft = true;
                playerLeft.setTeam(item.subText);
            } else if (item.subText != null && isLeft) {
                isRight = true;
                playerRight.setTeam(item.subText);
            } else {
                if (isRight) {
                    right.add(item);
                } else {
                    left.add(item);
                }
            }
        }
        playerLeft.setData(left);
        playerRight.setData(right);

        final List<MatchPlayer> result = new ArrayList();
        result.add(playerLeft);
        result.add(playerRight);
        if (mTitleItemDecoration != null) {
            mBinding.rvMatchPlayer.removeItemDecoration(mTitleItemDecoration);
        }
        mTitleItemDecoration = new TitleItemDecoration(mContext
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK), mThemeColorMap.get(C.ATTRS.COLOR_BG_DARK)
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK), TitleItemDecoration.GRAVITY_MIDDLE
                , new TitleItemDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                return result.get(position).getTeam();
            }
        }, new TitleItemDecoration.TitleTextCallback() {
            @Override
            public String getGroupFirstLine(int position) {
                return result.get(position).getTeam();
            }

            @Override
            public String getActiveGroup() {
                return null;
            }
        });
        mBinding.rvMatchPlayer.addItemDecoration(mTitleItemDecoration);
        mAdapter.setNewData(result);
        mBinding.pl.showContent(mBinding.rvMatchPlayer);
    }

    @Override
    public void getMatchStatusFailed(String msg) {
        if (mBinding.pl.getStatus() == ProgressLayout.STATUS_LOADING) {
            mBinding.pl.showError(mBinding.rvMatchPlayer);
        } else {
            ToastUtils.showShort(msg);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(MsgEvent event) {
        if (C.EventAction.REFRESH_MATCH_DATA.equals(event.getAction())) {
            mPresenter.getMatchStatus(mId, C.MATCH.TAB_TPYE_STROKE);
        }
    }
}
