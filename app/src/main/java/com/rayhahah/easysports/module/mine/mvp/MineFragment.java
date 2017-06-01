package com.rayhahah.easysports.module.mine.mvp;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMineBinding;

/**
 * Created by a on 2017/5/17.
 */

public class MineFragment extends BaseFragment<MinePresenter, FragmentMineBinding>
        implements MineContract.IMineView {

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.mine));
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter(this);
    }
}
