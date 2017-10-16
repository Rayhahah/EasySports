package com.rayhahah.easysports.module.match.busniess.matchforward;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMatchForwardBinding;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;
import com.rayhahah.easysports.module.match.domain.MatchForwardAdapter;
import com.rayhahah.easysports.view.TitleItemDecoration;
import com.rayhahah.rbase.utils.base.ToastUtils;

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
public class MatchForwardFragment extends BaseFragment<MatchForwardPresenter, FragmentMatchForwardBinding> implements MatchForwardContract.IMatchForwardView {

    private String mId;
    private MatchForwardAdapter mAdapter;
    private List<MatchStatusBean.StatsBean> mStatsData;

    public static BaseFragment newInstance(String mid) {
        MatchForwardFragment fragment = new MatchForwardFragment();
        Bundle bundle = new Bundle();
        bundle.putString(C.MATCH.BUNDLE_MID, mid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_match_forward;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mId = getValueFromPrePage(C.MATCH.BUNDLE_MID);
        initPL();
        initRv();
        initData();
    }

    private void initData() {
        mBinding.pl.showLoading(mBinding.rvMatchForward);
        mPresenter.getMatchStatus(mId, C.MATCH.TAB_TPYE_FORWARD);
    }

    private void initRv() {
        mAdapter = new MatchForwardAdapter();
        mAdapter.openLoadAnimation();
        mBinding.rvMatchForward.setAdapter(mAdapter);
        mBinding.rvMatchForward.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
    protected MatchForwardPresenter getPresenter() {
        return new MatchForwardPresenter(this);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void getMatchStatusSuccess(MatchStatusBean.MatchStatInfo data) {
        mStatsData = data.stats;
        mAdapter.setTeamName(data.teamInfo.leftName, data.teamInfo.rightName);
        mAdapter.setNewData(mStatsData);
        mBinding.rvMatchForward.addItemDecoration(new TitleItemDecoration(mContext
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK), mThemeColorMap.get(C.ATTRS.COLOR_BG_DARK)
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK), TitleItemDecoration.GRAVITY_MIDDLE
                , new TitleItemDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                return mStatsData.get(position).type;
            }
        }, new TitleItemDecoration.TitleTextCallback() {
            @Override
            public String getGroupFirstLine(int position) {
                switch (Integer.parseInt(mStatsData.get(position).type)) {
                    case 1:
//                        历史对阵
                        return "历史对阵";
                    case 2:
                        //近期赛事
                        return "近期赛事";
                    case 13:
//                        球队数据王
                        return "球队数据王";
                    default:
                        break;
                }
                return null;
            }

            @Override
            public String getActiveGroup() {
                return null;
            }
        }));

        mBinding.pl.showContent(mBinding.rvMatchForward);
    }

    @Override
    public void getMatchStatusFailed(String message) {
        mBinding.pl.showError(mBinding.rvMatchForward);
        ToastUtils.showShort("获取数据失败");
    }
}
