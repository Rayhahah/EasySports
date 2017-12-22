package com.rayhahah.easysports.module.forum.business.forumdetaillist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityForumDetailListBinding;
import com.rayhahah.easysports.module.forum.bean.DetailListData;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.easysports.module.forum.business.forumdetail.ForumDetailActivity;
import com.rayhahah.easysports.module.forum.domain.ForumDetailListAdapter;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;

public class ForumDetailListActivity extends BaseActivity<ForumDetailListPresenter, ActivityForumDetailListBinding> implements ForumDetailListContract.IForumDetailListView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemChildClickListener {


    public static final String TAG_DATA = "data";
    private ForumsData.Forum mData;
    private ForumDetailListAdapter mAdapter;
    private String mFid;
    private String mType = C.FORUM.TYPE_FORUM_NEW;
    private String mLast = "";
    private boolean isInit = true;

    public static void start(Context context, ForumsData.Forum data) {
        Intent intent = new Intent(context, ForumDetailListActivity.class);
        intent.putExtra(TAG_DATA, data);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_forum_detail_list;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected ForumDetailListPresenter getPresenter() {
        return new ForumDetailListPresenter(this);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mData = (ForumsData.Forum) getIntent().getSerializableExtra(TAG_DATA);
        mFid = mData.fid;
        initRv();
        initPL();
        initData();
    }

    private void initPL() {
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.pl.showLoading(mBinding.srlForumDetailList);
                mLast = "";
                isInit = true;
                mPresenter.getForumPost(mFid, mLast, mType, true);
            }
        });
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
    }

    private void initData() {
        isInit = true;
        mBinding.pl.showLoading(mBinding.srlForumDetailList);
//        DialogUtil.showLoadingDialog(mContext, "加载专区数据", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mPresenter.getForumPost(mFid, mLast, mType, true);
    }

    private void initRv() {
        mAdapter = new ForumDetailListAdapter();
        mAdapter.openLoadAnimation();
        mAdapter.setOnLoadMoreListener(this, mBinding.rvForumDetailList);
        mAdapter.setOnItemChildClickListener(this);
        mBinding.rvForumDetailList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mBinding.rvForumDetailList.setAdapter(mAdapter);
        mBinding.srlForumDetailList.setColorSchemeColors(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mBinding.srlForumDetailList.setOnRefreshListener(this);
    }

    /**
     * 初始化顶部设置
     * 巨坑！！！！
     * toolbar初始化会覆盖RV的内容
     */
    private void initTop() {
        mBinding.toolbar.setTitle(mData.name);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GlideUtil.load(mContext, mData.backImg, mBinding.ivPrefectureCover);
        mBinding.tvPrefectureSubTitle.setText(mData.description);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<DetailListData.ThreadInfo> data = adapter.getData();
        switch (view.getId()) {
            case R.id.cv_item_detail_list:
                DetailListData.ThreadInfo threadInfo = data.get(position);
                ForumDetailActivity.start(mContext, "", threadInfo.tid, 1, threadInfo.fid);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mLast = "";
        mPresenter.getForumPost(mFid, mLast, mType, true);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getForumPost(mFid, mLast, mType, false);
    }

    @Override
    public void getForumPostSuccess(List<DetailListData.ThreadInfo> data, boolean isRefresh) {
        mLast = data.get(data.size() - 1).tid;
        if (isRefresh) {
            mBinding.srlForumDetailList.setRefreshing(false);
            mAdapter.setNewData(data);
            mAdapter.loadMoreComplete();
            mBinding.pl.showContent(mBinding.srlForumDetailList);
            mBinding.rvForumDetailList.setVisibility(View.VISIBLE);
            isInit = false;
            initTop();
        } else {
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void NoMoreForumPost() {
        ToastUtils.showShort("没有更多内容了");
        DialogUtil.dismissDialog(true);
        mBinding.pl.showContent(mBinding.srlForumDetailList);
        isInit = false;
    }

    @Override
    public void getForumPostFailed(String message) {
        if (isInit) {
            mBinding.pl.showError(mBinding.srlForumDetailList);
        }
        DialogUtil.dismissDialog(false);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

}
