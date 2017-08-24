package com.rayhahah.easysports.module.news.mvp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.FragmentNewsBinding;
import com.rayhahah.easysports.module.news.business.newslist.NewsListFragment;
import com.rayhahah.easysports.module.news.domain.VPNewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class NewsFragment extends BaseFragment<NewsPresenter, FragmentNewsBinding>
        implements NewsContract.INewsView, TabLayout.OnTabSelectedListener {


    private List<String> tabTitles = new ArrayList<>();
    private ArrayList<BaseFragment> mFragmentList;
    private VPNewsAdapter mVPAdapter;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.news));
        initTabTitles();

        initFragmentList();

//        需要管理相互独立的并且隶属于Activity的Fragment使用FragmentManager（）
//        而在Fragment中动态的添加Fragment要使用getChildFragmetManager（）来管理。
        mVPAdapter = new VPNewsAdapter(getChildFragmentManager(), mFragmentList, tabTitles);
        mBinding.vpNews.setOffscreenPageLimit(1);
        mBinding.vpNews.setAdapter(mVPAdapter);
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

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    /**
     * 初始化Tab标题
     */
    private void initTabTitles() {
        tabTitles.add(getResources().getString(R.string.news_banner));
        tabTitles.add(getResources().getString(R.string.news_news));
        tabTitles.add(getResources().getString(R.string.news_videos));
        tabTitles.add(getResources().getString(R.string.news_depth));
        tabTitles.add(getResources().getString(R.string.news_highlight));
    }

    /**
     * 初始化FragmentList
     */
    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < tabTitles.size(); i++) {
            NewsListFragment fragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(C.NEWS.TAB_INDEX, i + "");
            String tabType = "";
            switch (i) {
                case 0:
                    tabType = C.NEWS.BANNER;
                    break;
                case 1:
                    tabType = C.NEWS.NEWS;
                    break;
                case 2:
                    tabType = C.NEWS.VIDEOS;
                    break;
                case 3:
                    tabType = C.NEWS.DEPTH;
                    break;
                case 4:
                    tabType = C.NEWS.HIGHLIGHT;
                    break;
                default:
                    tabType = C.NEWS.BANNER;
                    break;
            }
            bundle.putString(C.NEWS.TAB_TYPE, tabType);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
    }
}
