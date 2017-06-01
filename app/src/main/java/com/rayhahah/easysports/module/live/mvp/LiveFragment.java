package com.rayhahah.easysports.module.live.mvp;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentLiveBinding;

/**
 * Created by a on 2017/5/17.
 */

public class LiveFragment extends BaseFragment<LivePresenter, FragmentLiveBinding>
        implements LiveContract.ILiveView {

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
}
