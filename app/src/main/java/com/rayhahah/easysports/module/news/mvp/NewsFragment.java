package com.rayhahah.easysports.module.news.mvp;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentNewsBinding;

/**
 * Created by a on 2017/5/17.
 */

public class NewsFragment extends BaseFragment<NewsPresenter, FragmentNewsBinding>
        implements NewsContract.INewsView {

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.news));
    }

    @Override
    protected NewsPresenter getPresenter() {
        return new NewsPresenter(this);
    }
}
