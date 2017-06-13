package com.rayhahah.easysports.module.news.domain;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.rayhahah.easysports.common.BaseFragment;

import java.util.List;

/**
 * Created by a on 2017/6/7.
 */

public class VPNewsAdapter extends FragmentPagerAdapter {
    private FragmentManager mFm;
    private List<BaseFragment> mData;
    private List<String> mTitles;
    private FragmentTransaction mFragmentTransaction;
    private BaseFragment mCurFragment;

    public VPNewsAdapter(FragmentManager fm, List<BaseFragment> data, List<String> titles) {
        super(fm);
        mFm = fm;
        mData = data;
        mTitles = titles;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 让TabLayout显示Tab标签标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

}
