package com.rayhahah.easysports.module.match.mvp;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentMatchBinding;

/**
 * Created by a on 2017/5/17.
 */

public class MatchFragment extends BaseFragment<MatchPresenter, FragmentMatchBinding>
        implements MatchContract.IMatchView {

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_match;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.match));

        mPresenter.getMatchList();
    }

    @Override
    protected MatchPresenter getPresenter() {
        return new MatchPresenter(this);
    }

    @Override
    public void showMatchList() {

    }
}
