package com.rayhahah.easysports.module.news.mvp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentNewsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class NewsFragment extends BaseFragment<NewsPresenter, FragmentNewsBinding>
        implements NewsContract.INewsView, TabLayout.OnTabSelectedListener {


    private List<String> tabTitles = new ArrayList<>();

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.news));
        tabTitles.add(getResources().getString(R.string.news_banner));
        tabTitles.add(getResources().getString(R.string.news_news));
        tabTitles.add(getResources().getString(R.string.news_videos));
        tabTitles.add(getResources().getString(R.string.news_depth));
        tabTitles.add(getResources().getString(R.string.news_highlight));


        mBinding.vpNews.setAdapter();
        mBinding.tlNews.setupWithViewPager(mBinding.vpNews);
        mBinding.tlNews.addOnTabSelectedListener(this);
    }

    @Override
    protected NewsPresenter getPresenter() {
        return new NewsPresenter(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
