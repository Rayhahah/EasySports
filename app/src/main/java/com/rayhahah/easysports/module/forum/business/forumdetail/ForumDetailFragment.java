package com.rayhahah.easysports.module.forum.business.forumdetail;

import android.os.Bundle;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentForumDetailBinding;
import com.rayhahah.easysports.view.HuPuWebView;

/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author Rayhahah
 * @blog http://rayhahah.com
 * @time 2017/9/26
 * @tips 这个类是Object的子类
 * @fuction
 */
public class ForumDetailFragment extends BaseFragment<ForumDetailPresenter, FragmentForumDetailBinding> implements HuPuWebView.HuPuWebViewCallBack, HuPuWebView.OnScrollChangedCallback {


    public static final String URL = "url";
    private String url;

    public static ForumDetailFragment newInstance(String url) {
        ForumDetailFragment mFragment = new ForumDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_forum_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        url = getValueFromPrePage("url");
        iniPL();
        mBinding.pl.showLoading(mBinding.wvHupu);
        mBinding.wvHupu.setActivity(getActivity());
        mBinding.wvHupu.loadUrl(url);
        mBinding.wvHupu.setCallBack(this);
        mBinding.wvHupu.setOnScrollChangedCallback(this);
    }

    private void iniPL() {
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.pl.showLoading(mBinding.wvHupu);
                mBinding.wvHupu.reload();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinding.wvHupu != null) {
            mBinding.wvHupu.removeAllViews();
            mBinding.wvHupu.destroy();
        }
    }

    @Override
    protected ForumDetailPresenter getPresenter() {
        return new ForumDetailPresenter(null);
    }

    @Override
    public void onFinish() {
        mBinding.pl.showContent(mBinding.wvHupu);
    }

    @Override
    public void onUpdatePager(int page, int total) {
    }

    @Override
    public void onError() {
        mBinding.pl.showError(mBinding.wvHupu);
    }

    @Override
    public void onScroll(int dx, int dy, int y, int oldy) {
    }
}
