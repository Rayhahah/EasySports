package com.rayhahah.easysports.module.mine.business.teamplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.RWebActivity;
import com.rayhahah.easysports.databinding.ActivitySingleListBinding;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.easysports.module.mine.domain.PlayerListAdapter;
import com.rayhahah.easysports.module.mine.domain.TeamListAdapter;
import com.rayhahah.easysports.view.IndexBar;
import com.rayhahah.easysports.view.TitleItemDecoration;
import com.rayhahah.rbase.utils.base.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SingleListActivity extends BaseActivity<SingleListPresenter, ActivitySingleListBinding>
        implements SingleListContract.ISingleListView
        , BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {

    public static final String TYPE = "type";
    public static final String TYPE_TEAM = "type_team";
    public static final String TYPE_PLAYER = "type_player";
    private String mType;
    private List<TeamListBean.DataBean.TeamBean> currentTeamData;
    private List<PlayerListBean.DataBean> currentPlayerData;
    private List<PlayerListBean.DataBean> totalPlayerData;
    private PlayerListAdapter mPlayerListAdapter;
    private SparseArray<Integer> mIndexPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mNeedMove;

    public static void start(Context context, Activity preActivity, String type) {
        putParmToNextPage(TYPE, type + "");
        toNextActivity(context, SingleListActivity.class, preActivity);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mType = getValueFromPrePage(TYPE);
        initPL();
        initSearch();
        initData();
    }

    @Override
    protected SingleListPresenter getPresenter() {
        return new SingleListPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_single_list;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    public void showViewLoading() {
        mBinding.pl.showLoading(mBinding.rvSingleList);
    }

    @Override
    public void showViewError(Throwable t) {
        mBinding.pl.showError(mBinding.rvSingleList);
    }

    @Override
    public void getTeamList(List<TeamListBean.DataBean.TeamBean> teamList) {
        currentTeamData = teamList;
        mBinding.rvSingleList.setLayoutManager(
                new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        TeamListAdapter teamListAdapter = new TeamListAdapter();
        teamListAdapter.setNewData(teamList);
        teamListAdapter.openLoadAnimation();
        teamListAdapter.setOnItemChildClickListener(this);
        mBinding.rvSingleList.setAdapter(teamListAdapter);
        mBinding.pl.showContent(mBinding.rvSingleList);
    }

    @Override
    public void getPlayerList(List<PlayerListBean.DataBean> playerList) {
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.rvSingleList.setLayoutManager(mLinearLayoutManager);
        mPlayerListAdapter = new PlayerListAdapter();
        mPlayerListAdapter.openLoadAnimation();
        mPlayerListAdapter.setOnItemChildClickListener(this);
        mPlayerListAdapter.setNewData(playerList);
        currentPlayerData = mPlayerListAdapter.getData();
        totalPlayerData = mPlayerListAdapter.getData();
        initIndexPosition(currentPlayerData);
        mBinding.rvSingleList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mNeedMove && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mNeedMove = false;
                    int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                    smoothScrollToTop(lastPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                PlayerListBean.DataBean dataBean = currentPlayerData.get(firstPosition);
                String s = dataBean.getEnName().substring(0, 1);

                for (int i = 0; i < mBinding.indexBar.getIndexArr().length; i++) {
                    if (s.equals(mBinding.indexBar.getIndexArr()[i])) {
                        mBinding.indexBar.setIndexChange(i);
                    }
                }
            }
        });
        mBinding.rvSingleList.addItemDecoration(new TitleItemDecoration(this
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK)
                , mThemeColorMap.get(C.ATTRS.COLOR_BG_DARK)
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK)
                , TitleItemDecoration.GRAVITY_LEFT
                , new TitleItemDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (position < currentPlayerData.size()
                        && StringUtils.isNotEmpty(currentPlayerData.get(position).getSectionData())) {
                    return currentPlayerData.get(position).getSectionData();
                }
                return null;
            }
        }, new TitleItemDecoration.TitleTextCallback() {
            @Override
            public String getGroupFirstLine(int position) {
                if (position < currentPlayerData.size()
                        && StringUtils.isNotEmpty(currentPlayerData.get(position).getSectionData())) {
                    return currentPlayerData.get(position).getSectionData();
                }
                return "";
            }

            @Override
            public String getActiveGroup() {
                return "";
            }
        }));
        mBinding.rvSingleList.setAdapter(mPlayerListAdapter);
        mBinding.indexBar.setVisibility(View.VISIBLE);
        mBinding.indexBar.setOnTouchIndexListner(new IndexBar.OnTouchIndexListner() {

            @Override
            public void onIndexChanged(final int index, String letter) {
                Integer position = mIndexPosition.get(index);
                smoothScrollToTop(position);
            }
        });
        mBinding.pl.showContent(mBinding.rvSingleList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.iv_single_list_search_close:
                mBinding.etSingleListSearch.setText("");
                mPlayerListAdapter.setNewData(totalPlayerData);
                mBinding.rvSingleList.scrollToPosition(0);
                mBinding.ivSingleListSearchClose.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.fbl_item_team:
                if (currentTeamData == null) {
                    return;
                }
                TeamListBean.DataBean.TeamBean teamBean = currentTeamData.get(position);
                RWebActivity.start(this, this, teamBean.getDetailUrl()
                        , teamBean.getTeamName(), true, true);
                break;
            case R.id.fbl_item_player:
                if (currentPlayerData == null) {
                    return;
                }
                PlayerListBean.DataBean dataBean = currentPlayerData.get(position);
                RWebActivity.start(this, this, dataBean.getDetailUrl()
                        , dataBean.getCnName(), true, true);
                break;
            default:
                break;
        }
    }

    private void initSearch() {
        if (TYPE_TEAM.equals(mType)) {
            mBinding.llSingleListSearch.setVisibility(View.GONE);
        }
        if (TYPE_PLAYER.equals(mType)) {
            mPlayerListAdapter = new PlayerListAdapter();

            mBinding.llSingleListSearch.setVisibility(View.VISIBLE);
            mBinding.ivSingleListSearchClose.setOnClickListener(this);
            mBinding.etSingleListSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mBinding.ivSingleListSearchClose.setVisibility(View.VISIBLE);
                    ArrayList<PlayerListBean.DataBean> newData = new ArrayList<>();
                    for (PlayerListBean.DataBean dataBean : currentPlayerData) {
                        String eng = dataBean.getEnName().toLowerCase();
                        String cn = dataBean.getCnName();

                        if (eng.contains(s)) {
                            newData.add(dataBean);
                        } else if (cn.contains(s)) {
                            newData.add(dataBean);
                        }
                    }
                    currentPlayerData.clear();
                    currentPlayerData.addAll(newData);
                    initIndexPosition(currentPlayerData);
                    mPlayerListAdapter.setNewData(currentPlayerData);
                    mBinding.rvSingleList.scrollToPosition(0);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            mBinding.etSingleListSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Animatable) mBinding.ivSingleListSearchIcon.getDrawable()).start();
                }
            });
        }
    }


    /**
     * 初始化列表数据
     */
    private void initData() {
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(this);
//        mBinding.indexBar.setVisibility(View.GONE);
        showViewLoading();
        if (TYPE_TEAM.equals(mType)) {
            mBinding.toolbar.tvToolbarTitle.setText(R.string.mine_all_team);
            mPresenter.getTeamList();
        }
        if (TYPE_PLAYER.equals(mType)) {
            mBinding.toolbar.tvToolbarTitle.setText(R.string.mine_all_player);
            mPresenter.getPlayerList();
        }
    }

    /**
     * 初始化加载列表
     */
    private void initPL() {
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    /**
     * 初始化导航栏索引位置信息
     */
    private void initIndexPosition(List<PlayerListBean.DataBean> data) {
        mIndexPosition = new SparseArray<>();
        String[] indexArr = new String[mBinding.indexBar.getIndexArr().length];
        for (int i = 0; i < mBinding.indexBar.getIndexArr().length; i++) {
            indexArr[i] = mBinding.indexBar.getIndexArr()[i];
        }
        for (int j = 0; j < data.size(); j++) {
            PlayerListBean.DataBean dataBean = data.get(j);
            for (int i = 0; i < indexArr.length; i++) {
                if (dataBean.getSectionData().equals(indexArr[i])) {
                    mIndexPosition.put(i, j);
                    indexArr[i] = "";
                    break;
                }
            }
        }
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
        int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLinearLayoutManager.findLastVisibleItemPosition();
        if (position <= firstPosition) {
            mBinding.rvSingleList.smoothScrollToPosition(position);
        } else if (position <= lastPosition) {
            int top = mBinding.rvSingleList.getChildAt(position - firstPosition).getTop();
            mBinding.rvSingleList.smoothScrollBy(0, top);
        } else {
            mBinding.rvSingleList.smoothScrollToPosition(position);
            mNeedMove = true;
        }
    }

}
