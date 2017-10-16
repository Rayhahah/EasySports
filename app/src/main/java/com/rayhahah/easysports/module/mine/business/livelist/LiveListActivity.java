package com.rayhahah.easysports.module.mine.business.livelist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityLiveListBinding;
import com.rayhahah.easysports.module.mine.bean.LiveBean;
import com.rayhahah.easysports.module.mine.business.record.RecordActivity;
import com.rayhahah.easysports.module.mine.domain.LiveListAdapter;
import com.rayhahah.rbase.utils.useful.PermissionManager;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.List;

public class LiveListActivity extends BaseActivity<LiveListPresenter, ActivityLiveListBinding>
        implements LiveListContract.ILiveListView, PermissionManager.PermissionsResultListener, BaseQuickAdapter.OnItemChildClickListener, SwipeRefreshLayout.OnRefreshListener {

    private LiveListAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public static void start(Context context, Activity preActivity) {
        toNextActivity(context, LiveListActivity.class, preActivity);
    }

    @Override
    protected LiveListPresenter getPresenter() {
        return new LiveListPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_live_list;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initToolBar();
        initPL();
        initRv();
        initData();
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


    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void getCurrentLiveSuccess(List<LiveBean.DataBean> data) {
        mBinding.pl.showContent(mBinding.srlLiveList);
        if (mBinding.srlLiveList.isRefreshing()) {
            mBinding.srlLiveList.setRefreshing(false);
        }
        mAdapter.setNewData(data);
    }

    @Override
    public void getCurrentLiveFailed(String msg) {
        mBinding.pl.showError(mBinding.srlLiveList);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case C.MINE.CODE_REQUEST_AUDIO:
                Toast.makeText(mContext, R.string.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();
                RecordActivity.start(mContext, mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionDenied(int requestCode) {
        switch (requestCode) {
            case C.MINE.CODE_REQUEST_AUDIO:
                Toast.makeText(mContext, R.string.PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.cl_item_live_list:
                List<LiveBean.DataBean> data = adapter.getData();
                LiveBean.DataBean dataBean = data.get(position);
                LivePlayActivity.start(mContext, mContext, dataBean.getFlvUrl(), dataBean.getScreenname() + "的直播间");
                break;
            default:
                break;
        }
    }


    private void initToolBar() {
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.tvToolbarTitle.setText("直播列表");
    }

    private void initData() {
        mBinding.pl.showLoading(mBinding.srlLiveList);
        mPresenter.getCurrentLive();
    }

    private void initRv() {
        mAdapter = new LiveListAdapter();
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemChildClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mBinding.rvLiveList.setLayoutManager(mLinearLayoutManager);
        mBinding.rvLiveList.setAdapter(mAdapter);
        mBinding.srlLiveList.setOnRefreshListener(this);
        mBinding.srlLiveList.setColorSchemeColors(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
    }


    public void toRecord(View view) {
        if (SPManager.get().getStringValue(C.SP.IS_LOGIN, C.FALSE).equals(C.TRUE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionManager.requestPermission(this, getString(R.string.REQUEST_PERMISSION)
                        , C.MINE.CODE_REQUEST_AUDIO, this
                        , Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.CAMERA);
            } else {
                RecordActivity.start(mContext, mContext);
            }
        } else {
            Toast.makeText(mContext, "请先登录账号", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
