package com.rayhahah.easysports.module.match.busniess.matchdata;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMatchDataBinding;
import com.rayhahah.easysports.module.match.bean.MatchDetailBean;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;
import com.rayhahah.easysports.module.match.domain.MatchDataAdapter;
import com.rayhahah.easysports.view.ProgressLayout;
import com.rayhahah.easysports.view.TitleItemDecoration;
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
public class MatchDataFragment extends BaseFragment<MatchDataPresenter, FragmentMatchDataBinding>
        implements MatchDataContract.IMatchDataView {

    private String mId;
    private MatchDataAdapter mAdapter;
    private List<MatchStatusBean.StatsBean> mStats;
    private TitleItemDecoration mTitleItemDecoration;

    public static BaseFragment newInstance(String mid, MatchDetailBean.BaseInfo info) {
        MatchDataFragment fragment = new MatchDataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.MATCH.BUNDLE_MID, mid);
//        bundle.putSerializable(C.MATCH.BUNDLE_INFO, info);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_match_data;
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
        mBinding.pl.showLoading(mBinding.rvMatchData);
        mPresenter.getMatchStatus(mId, C.MATCH.TAB_TPYE_MATCHDATA);

    }

    private void initRv() {
        mAdapter = new MatchDataAdapter();
        mAdapter.openLoadAnimation();
        mBinding.rvMatchData.setAdapter(mAdapter);
        mBinding.rvMatchData.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected MatchDataPresenter getPresenter() {
        return new MatchDataPresenter(this);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {
        if (mBinding.pl.getStatus() == ProgressLayout.STATUS_LOADING) {
            mBinding.pl.showError(mBinding.rvMatchData);
        } else {
            ToastUtils.showShort(t.getMessage());
        }
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
    public void getMatchStatusSuccess(MatchStatusBean.MatchStatInfo data) {
        if (data != null && data.stats != null) {
            mStats = data.stats;
            mAdapter.setTeamCover(data.teamInfo.leftBadge, data.teamInfo.rightBadge);
            mAdapter.setPrimaryColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
            mAdapter.setNewData(mStats);

            if (mTitleItemDecoration != null) {
                mBinding.rvMatchData.removeItemDecoration(mTitleItemDecoration);
            }

            mTitleItemDecoration = new TitleItemDecoration(mContext
                    , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK), mThemeColorMap.get(C.ATTRS.COLOR_BG_DARK)
                    , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK), TitleItemDecoration.GRAVITY_MIDDLE
                    , new TitleItemDecoration.DecorationCallback() {
                @Override
                public String getGroupId(int position) {
                    return mStats.get(position).type;
                }
            }, new TitleItemDecoration.TitleTextCallback() {
                @Override
                public String getGroupFirstLine(int position) {
                    switch (Integer.parseInt(mStats.get(position).type)) {
                        case 12:
//                        比分
                            return "比分";
                        case 14:
                            //球队统计
                            return "球队统计";
                        case 13:
                            //本场最佳
                            return "";
                        default:
                            break;
                    }
                    return "";
                }

                @Override
                public String getActiveGroup() {
                    return null;
                }
            });
            mBinding.rvMatchData.addItemDecoration(mTitleItemDecoration);
        } else {
            ToastUtils.showShort("暂无数据");
        }

        mBinding.pl.showContent(mBinding.rvMatchData);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(MsgEvent event) {
        if (C.EventAction.REFRESH_MATCH_DATA.equals(event.getAction())) {
            mPresenter.getMatchStatus(mId, C.MATCH.TAB_TPYE_MATCHDATA);
        }
    }
}
