package com.rayhahah.easysports.module.info.mvp;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.DecorationPlayerRankBinding;
import com.rayhahah.easysports.databinding.FragmentInfoBinding;
import com.rayhahah.easysports.module.info.bean.InfoData;
import com.rayhahah.easysports.module.info.bean.InfoIndex;
import com.rayhahah.easysports.module.info.bean.StatusRank;
import com.rayhahah.easysports.module.info.bean.TeamRank;
import com.rayhahah.easysports.module.info.domain.InfoDataListAdapter;
import com.rayhahah.easysports.module.info.domain.InfoIndexListAdapter;
import com.rayhahah.easysports.view.TitleItemDecoration;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends BaseFragment<InfoPresenter, FragmentInfoBinding>
        implements InfoContract.IInfoView, BaseQuickAdapter.OnItemChildClickListener, TabLayout.OnTabSelectedListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static final int ROWS = 10;
    private InfoIndexListAdapter mIndexAdapter;
    private ArrayList<InfoData> mInfoData;
    private InfoDataListAdapter mInfoAdapter;
    private String[] playerInfoType = {C.INFO.POINT, C.INFO.REBOUND, C.INFO.ASSIST, C.INFO.BLOCK, C.INFO.STEAL};
    private int typePosition = 0;
    private String currentTayType = C.INFO.TAG_TYPE_DAILY;
    private LinearLayoutManager mInfoListLayoutManager;
    private boolean mNeedMove;
    private int targetPosition = 0;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_info;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.info));
        initTab();
        initProgressLayout();
        initIndexRv();
        initInfoRv();
        initData();
    }

    @Override
    protected InfoPresenter getPresenter() {
        return new InfoPresenter(this);
    }


    @Override
    public void showViewLoading() {
        mBinding.pl.showLoading(mBinding.rvInfoList);
    }

    @Override
    public void showViewError(Throwable t) {
        mBinding.pl.showError(mBinding.rvInfoList);
    }

    @Override
    public void getTeamRankSuccess(TeamRank teamRank) {
        InfoData east = new InfoData();
        east.setId(C.INFO.ID_EAST);
        east.setType(C.INFO.TYPE_TEAM);
        east.setTeamData(teamRank.east);
        mInfoData.add(east);

        InfoData west = new InfoData();
        west.setId(C.INFO.ID_WEST);
        west.setType(C.INFO.TYPE_TEAM);
        west.setTeamData(teamRank.west);
        mInfoData.add(west);

        mInfoAdapter.setNewData(mInfoData);
        mBinding.pl.showContent(mBinding.rvInfoList);
    }

    @Override
    public void getTeamRankFailed(Throwable throwable) {
        ToastUtils.showShort("获取数据失败");
        showViewError(throwable);
    }

    @Override
    public void getStatusRankSuccess(StatusRank statusRank) {
        InfoData players = new InfoData();
        players.setId(typePosition);
        players.setType(C.INFO.TYPE_PLAYER);
        players.playerType = statusRank.type;
        players.setPlayerData(statusRank.getRankList());
        mInfoAdapter.addData(players);
        typePosition++;
        mInfoAdapter.loadMoreComplete();
        if (typePosition < targetPosition) {
            mPresenter.getStatusRank(playerInfoType[typePosition], ROWS, currentTayType);
        }
        if (typePosition == targetPosition) {
            smoothScrollToTop(targetPosition + 2);
        }
        if (typePosition == playerInfoType.length) {
            mInfoAdapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void getStatusRankFailed(Throwable throwable) {
        ToastUtils.showShort("获取数据失败！");
        mInfoAdapter.loadMoreFail();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.fbl_item_index:
                mIndexAdapter.setCheckedPos(position);
                smoothScrollToTop(position);
                targetPosition = position - 2;
                if (targetPosition >= 0 && typePosition < playerInfoType.length) {
                    mPresenter.getStatusRank(playerInfoType[typePosition], ROWS, currentTayType);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (typePosition < playerInfoType.length) {
            mPresenter.getStatusRank(playerInfoType[typePosition], ROWS, currentTayType);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        String tag = (String) tab.getTag();
        if (C.INFO.TAG_TYPE_DAILY.equals(tag)) {
            currentTayType = C.INFO.TAG_TYPE_DAILY;
        } else if (C.INFO.TAG_TYPE_SEASON.equals(tag)) {
            currentTayType = C.INFO.TAG_TYPE_SEASON;
        } else if (C.INFO.TAG_TYPE_NORMAL.equals(tag)) {
            currentTayType = C.INFO.TAG_TYPE_NORMAL;
        }

        List<InfoData> data = mInfoAdapter.getData();
        List<InfoData> temp = new ArrayList<>();
        temp.add(data.get(0));
        temp.add(data.get(1));
        mInfoAdapter.setNewData(temp);
//        for (int i = 2; i < mInfoAdapter.getData().size(); i++) {
//            mInfoAdapter.remove(i);
//        }
//        for (int i = 0; i < typePosition; i++) {
//            mInfoAdapter.remove(i + 2);
//        }
        mInfoAdapter.setEnableLoadMore(true);
        typePosition = 0;
        targetPosition = 0;
        mIndexAdapter.setCheckedPos(0);
        mPresenter.getStatusRank(playerInfoType[typePosition], ROWS, currentTayType);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * 初始化顶部导航栏
     */
    private void initTab() {
        mBinding.tlInfoSelection.addTab(mBinding.tlInfoSelection.newTab().setTag(C.INFO.TAG_TYPE_DAILY).setText(R.string.info_data_daily), 0, true);
        mBinding.tlInfoSelection.addTab(mBinding.tlInfoSelection.newTab().setTag(C.INFO.TAG_TYPE_SEASON).setText(R.string.info_data_season), 1, false);
        mBinding.tlInfoSelection.addTab(mBinding.tlInfoSelection.newTab().setTag(C.INFO.TAG_TYPE_NORMAL).setText(R.string.info_data_normal), 2, false);
        mBinding.tlInfoSelection.addOnTabSelectedListener(this);
    }


    /**
     * 初始化ProgressLayout
     */
    private void initProgressLayout() {
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewLoading();
                mPresenter.getTeamRank();
            }
        });
    }

    /**
     * 初始化索引列表
     */
    private void initIndexRv() {
        List<InfoIndex> data = mPresenter.getIndexData(mThemeColorMap);
        mIndexAdapter = new InfoIndexListAdapter(data);
        mIndexAdapter.setOnItemChildClickListener(this);
        mIndexAdapter.openLoadAnimation();
        mBinding.rvInfoIndex.setAdapter(mIndexAdapter);
        mBinding.rvInfoIndex.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * 初始化数据信息列表
     */
    private void initInfoRv() {
        mInfoData = new ArrayList<>();
        mInfoAdapter = new InfoDataListAdapter(mInfoData, mContext);
        mInfoAdapter.openLoadAnimation();
        mInfoListLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        TitleItemDecoration titleItemDecoration = new TitleItemDecoration(mContext, new TitleItemDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                List<InfoData> data = mInfoAdapter.getData();
                if (position < data.size() && position >= 0) {
                    return data.get(position).getId() + "";
                }
                return "";
            }
        }, new TitleItemDecoration.TitleViewCallback() {
            @Override
            public View getGroupFirstView(int position) {
                List<InfoData> data = mInfoAdapter.getData();
                View v = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.empty_view, null, false).getRoot();
                if (position < data.size() && position >= 0) {
                    switch (data.get(position).getType()) {
                        case C.INFO.TYPE_TEAM:
                            final ViewDataBinding teamBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.decoration_team_rank, null, false);
                            v = teamBinding.getRoot();
                            break;
                        case C.INFO.TYPE_PLAYER:
                            final DecorationPlayerRankBinding playerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.decoration_player_rank, null, false);
                            String strType = "";
                            strType = getPlayersListTypeStr(position, data, strType);
                            playerBinding.tvDecorationRankType.setText(strType + "榜");
                            v = playerBinding.getRoot();
                            break;
                        default:
                            break;
                    }
                    return v;
                }
                return v;
            }
        });
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mNeedMove && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mNeedMove = false;
                    int lastPosition = mInfoListLayoutManager.findLastVisibleItemPosition();
                    smoothScrollToTop(lastPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = mInfoListLayoutManager.findFirstVisibleItemPosition();
                mIndexAdapter.setCheckedPos(firstPosition);
            }
        };
        mInfoAdapter.setOnLoadMoreListener(this, mBinding.rvInfoList);
        mBinding.rvInfoList.addItemDecoration(titleItemDecoration);
        mBinding.rvInfoList.setLayoutManager(mInfoListLayoutManager);
        mBinding.rvInfoList.addOnScrollListener(scrollListener);
        mBinding.rvInfoList.setFocusableInTouchMode(false);
        mBinding.rvInfoList.setAdapter(mInfoAdapter);
    }

    private void initData() {
        showViewLoading();
        mPresenter.getTeamRank();
    }

    /**
     * 获取榜单中文
     *
     * @param position
     * @param data
     * @param strType
     * @return
     */
    private String getPlayersListTypeStr(int position, List<InfoData> data, String strType) {
        switch (data.get(position).getId()) {
            case C.INFO.ID_POINT:
                strType = "得分";
                break;
            case C.INFO.ID_REBOUND:
                strType = "篮板";
                break;
            case C.INFO.ID_ASSIST:
                strType = "助攻";
                break;
            case C.INFO.ID_BLOCK:
                strType = "盖帽";
                break;
            case C.INFO.ID_STEAL:
                strType = "抢断";
                break;
            default:
                break;
        }
        return strType;
    }

    /**
     * 将列表目标位置滚动到列表最上方
     *
     * @param position
     */
    private void smoothScrollToTop(Integer position) {
        if (position == null) {
            return;
        }
        int firstPosition = mInfoListLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mInfoListLayoutManager.findLastVisibleItemPosition();
        if (position <= firstPosition) {
            mBinding.rvInfoList.smoothScrollToPosition(position);
        } else if (position <= lastPosition) {
            int top = mBinding.rvInfoList.getChildAt(position - firstPosition).getTop();
            mBinding.rvInfoList.smoothScrollBy(0, top);
        } else {
            mBinding.rvInfoList.smoothScrollToPosition(position);
            mNeedMove = true;
        }
    }

}
