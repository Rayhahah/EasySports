package com.rayhahah.easysports.module.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.ActivityHomeBinding;
import com.rayhahah.easysports.module.forum.mvp.ForumFragment;
import com.rayhahah.easysports.module.info.mvp.InfoFragment;
import com.rayhahah.easysports.module.match.mvp.MatchFragment;
import com.rayhahah.easysports.module.mine.mvp.MineFragment;
import com.rayhahah.easysports.module.news.mvp.NewsFragment;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity<HomePresenter, ActivityHomeBinding>
        implements HomeContract.IHomeView {

    private long exitTime = 0;
    private ArrayList<RBaseFragment> mFragmentList;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        initFragment();
        initBnb();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setContentView(getLayoutID());
        mBinding = DataBindingUtil.setContentView(mContext, getLayoutID());
        initThemeAttrs();
        setStatusColor();
        mFragmentList.clear();
        mBinding.bnbHome.clearAll();
        initBnb();
        initFragment();
    }

    public static void start(Context context, Activity preActivity) {
        toNextActivity(context, HomeActivity.class, preActivity);
    }


    private void initFragment() {
        MatchFragment matchFragment = new MatchFragment();
        NewsFragment newsFragment = new NewsFragment();
        InfoFragment infoFragment = new InfoFragment();
        ForumFragment forumFragment = new ForumFragment();
        MineFragment mineFragment = new MineFragment();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(matchFragment);
        mFragmentList.add(newsFragment);
        mFragmentList.add(infoFragment);
        mFragmentList.add(forumFragment);
        mFragmentList.add(mineFragment);

        String isMine = SPManager.get().getStringValue(C.SP.TAG_MINE_SELECTED, C.FALSE);
        if (C.FALSE.equals(isMine)) {
            showFragment(mFragmentList.get(0), 0);
        } else {
//            showFragment(mFragmentList.get(4), 4);
            mBinding.bnbHome.selectTab(4, true);
        }
        SPManager.get().putString(C.SP.TAG_MINE_SELECTED, C.FALSE);
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected int setFragmentContainerResId() {
        return R.id.fl_home_container;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
        }

    }

    /**
     * 初始化底部导航栏
     */
    private void initBnb() {
        if (SPManager.get().getStringValue(C.SP.IS_LOGIN).equals(C.TRUE)) {
            mPresenter.getCurrentUser(SPManager.get().getStringValue(C.SP.CURRENT_USER));
        }

        mBinding.bnbHome
                .addItem(getBnbItem(getResources().getString(R.string.match), R.drawable.ic_svg_match_bg_dark_24))
                .addItem(getBnbItem(getResources().getString(R.string.news), R.drawable.ic_svg_news_bg_dark_24))
                .addItem(getBnbItem(getResources().getString(R.string.info), R.drawable.ic_svg_info_bg_dark_24))
                .addItem(getBnbItem(getResources().getString(R.string.forum), R.drawable.ic_svg_forum_bg_dark_24))
                .addItem(getBnbItem(getResources().getString(R.string.mine), R.drawable.ic_svg_mine_bg_dark_24))
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .initialise();

        mBinding.bnbHome.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showFragment(mFragmentList.get(position), position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private BottomNavigationItem getBnbItem(String title, int resId) {
        return new BottomNavigationItem(resId, title);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
