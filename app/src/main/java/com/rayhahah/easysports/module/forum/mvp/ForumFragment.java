package com.rayhahah.easysports.module.forum.mvp;

import android.os.Bundle;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.FragmentForumBinding;

/**
 * Created by a on 2017/5/17.
 */

public class ForumFragment extends BaseFragment<ForumPresenter, FragmentForumBinding>
        implements ForumContract.IForumView {

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_forum;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.forum));
    }

    @Override
    protected ForumPresenter getPresenter() {
        return new ForumPresenter(this);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
