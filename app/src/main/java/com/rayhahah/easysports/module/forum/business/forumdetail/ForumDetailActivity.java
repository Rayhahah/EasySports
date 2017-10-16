package com.rayhahah.easysports.module.forum.business.forumdetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.ActivityForumDetailBinding;
import com.rayhahah.easysports.databinding.DialogShareBinding;
import com.rayhahah.easysports.module.forum.bean.ForumDetailInfoData;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 论坛帖子详情页面
 *
 * @author Rayhahah
 */
public class ForumDetailActivity extends BaseActivity<ForumDetailPresenter, ActivityForumDetailBinding> implements ForumDetailContract.IForumDetailView, ViewPager.OnPageChangeListener {

    private static final String INTENT_PID = "pid";
    private static final String INTENT_TID = "tid";
    private static final String INTENT_PAGE = "page";
    private static final String INTENT_FID = "fid";
    private String mPid;
    private String mTid;
    private String mFid;
    private int mPage;
    private int totalPage;
    private int currentPage = 1;
    private ArrayList<BaseFragment> mFragmentsList = new ArrayList<>();
    private HashMap<String, String> mShareMap;
    ;

    public static void start(Context context, String pid, String tid, int page, String fid) {
        Intent intent = new Intent(context, ForumDetailActivity.class);
        intent.putExtra(ForumDetailActivity.INTENT_PID, pid);
        intent.putExtra(ForumDetailActivity.INTENT_TID, tid);
        intent.putExtra(ForumDetailActivity.INTENT_PAGE, page);
        intent.putExtra(ForumDetailActivity.INTENT_FID, fid);
        context.startActivity(intent);
    }


    @Override
    protected ForumDetailPresenter getPresenter() {
        return new ForumDetailPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_forum_detail;
    }

    @Override
    protected int setFragmentContainerResId() {
        return R.id.fl_forrum_detail;
    }


    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mPid = getIntent().getStringExtra(INTENT_PID);
        mTid = getIntent().getStringExtra(INTENT_TID);
        mPage = getIntent().getIntExtra(INTENT_PAGE, 1);
        mFid = getIntent().getStringExtra(INTENT_FID);
        initToolbar();
        initData();

    }

    private void initData() {
        currentPage = 1;
        mPresenter.getForumDetail(mTid, mFid, 1, "");
    }

    private void initToolbar() {
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.tvToolbarTitle.setText("帖子详情");
    }


    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        onUpdatePager(position + 1, totalPage);
        currentPage = position + 1;
    }

    private void onUpdatePager(int currentPage, int totalPage) {
        mBinding.tvForumDetailPage.setText(currentPage + "/" + totalPage);
        if (currentPage == 1) {
            mBinding.tvForumDetailBefore.setTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT));
            mBinding.tvForumDetailBefore.setClickable(false);
        } else {
            mBinding.tvForumDetailBefore.setTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK));
            mBinding.tvForumDetailBefore.setClickable(true);
        }

        if (currentPage == totalPage) {
            mBinding.tvForumDetailNext.setTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT));
            mBinding.tvForumDetailNext.setClickable(false);
        } else {
            mBinding.tvForumDetailNext.setTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK));
            mBinding.tvForumDetailNext.setClickable(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void previousPage(View view) {
        currentPage--;
        if (currentPage <= 1) {
            currentPage = 1;
        }
        showFragment(mFragmentsList.get(currentPage - 1), currentPage - 1);
        onUpdatePager(currentPage, totalPage);
    }

    public void nextPage(View view) {
        currentPage++;
        if (currentPage >= totalPage) {
            currentPage = totalPage;
        }
        showFragment(mFragmentsList.get(currentPage - 1), currentPage - 1);
        onUpdatePager(currentPage, totalPage);
    }

    @Override
    public void getForumDetailFailed(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void getForumDetailSuccess(ForumDetailInfoData data) {
        List<String> urls = createPageList(data.url, data.page, data.pageSize);
        totalPage = data.pageSize;
        mShareMap = new HashMap<String, String>(C.DEFAULT_SIZE);
        mShareMap.put(C.FORUM.SHARE_TITLE, data.share.wechat);
        mShareMap.put(C.FORUM.SHARE_URL, data.share.url);
        for (String url : urls) {

            ForumDetailFragment forumDetailFragment = new ForumDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            forumDetailFragment.setArguments(bundle);
            mFragmentsList.add(forumDetailFragment);
        }
        onUpdatePager(currentPage, totalPage);
        showFragment(mFragmentsList.get(currentPage - 1), currentPage - 1);
    }

    private List<String> createPageList(String url, int page, int pageSize) {
        List<String> urls = new ArrayList<>();
        for (int i = 1; i <= pageSize; i++) {
            String newUrl = url.replace("page=" + page, "page=" + i);
            urls.add(newUrl);
        }
        return urls;
    }

    public void share(View view) {
        DialogShareBinding shareBinding = (DialogShareBinding) DataBindingUtil.inflate(getLayoutInflater()
                , R.layout.dialog_share, null, false);
        shareBinding.ivShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform platform = ShareSDK.getPlatform(Wechat.NAME);
                Platform.ShareParams params = new Platform.ShareParams();
                params.setShareType(Platform.SHARE_WEBPAGE);
                params.setTitle(mShareMap.get(C.FORUM.SHARE_TITLE));
                params.setUrl(mShareMap.get(C.FORUM.SHARE_URL));
                platform.share(params);

            }
        });
        shareBinding.ivShareMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform platform = ShareSDK.getPlatform(WechatMoments.NAME);
                Platform.ShareParams params = new Platform.ShareParams();
                params.setShareType(Platform.SHARE_WEBPAGE);
                params.setTitle(mShareMap.get(C.FORUM.SHARE_TITLE));
                params.setUrl(mShareMap.get(C.FORUM.SHARE_URL));
                platform.share(params);
            }
        });
        CustomDialog customDialog = new CustomDialog.Builder(mContext)
                .setView(shareBinding.getRoot())
                .setTouchOutside(true)
                .setDialogGravity(Gravity.BOTTOM)
                .build();
        customDialog.show();
    }
}
