package com.rayhahah.easysports.module.live.mvp;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentLiveBinding;
import com.rayhahah.easysports.module.match.domain.MatchLiveListAdapter;

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
    }

    @Override
    protected LivePresenter getPresenter() {
        return new LivePresenter(this);
    }


    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
