package com.rayhahah.easysports.module.news.business;

import android.os.Bundle;

import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentNewslistBinding;

/**
 * Created by a on 2017/6/7.
 */

public class NewsListFragment extends BaseFragment<NewsListPresenter, FragmentNewslistBinding> {

    @Override
    protected int setFragmentLayoutRes() {
        return 0;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected NewsListPresenter getPresenter() {
        return null;
    }
}
