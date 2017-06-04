package com.rayhahah.easysports.module.live.mvp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentLiveBinding;
import com.rayhahah.easysports.module.live.bean.MatchListBean;
import com.rayhahah.easysports.module.live.domain.MatchLiveListAdapter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;

import java.util.List;

public class LiveFragment
        extends BaseFragment<LivePresenter, FragmentLiveBinding>
        implements LiveContract.ILiveView {

    private MatchLiveListAdapter mMatchListAdapter;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_live;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.live));

        mBinding.rvMatchList.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mMatchListAdapter = new MatchLiveListAdapter();
        mBinding.rvMatchList.setAdapter(mMatchListAdapter);

        String currentData = DateTimeUitl.getCurrentWithFormate("yyyy-MM-dd");
        mPresenter.addMatchListData(currentData);
    }

    @Override
    protected LivePresenter getPresenter() {
        return new LivePresenter(this);
    }

    @Override
    public void addMatchListData(List<MatchListBean.MatchInfoBean> data) {
        mMatchListAdapter.setNewData(data);
    }
}
