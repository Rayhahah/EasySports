package com.rayhahah.easysports.module.news.business.newsdetail;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalSelectionDialog;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.databinding.ActivityNewsDetailBinding;
import com.rayhahah.easysports.module.news.bean.NewsDetail;
import com.rayhahah.easysports.utils.AnimatorUtil;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.PermissionManager;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter, ActivityNewsDetailBinding>
        implements NewsDetailContract.INewsDetailView
        , View.OnClickListener, PermissionManager.PermissionsResultListener {

    public static final String BUNDLE_KEY_COLUMN = "BUNDLE_KEY_COLUMN";
    public static final String BUNDLE_KEY_ARTICLEID = "BUNDLE_KEY_ARTICLEID";
    public static final int PER_CODE_STORAGE = 1;
    private String mColumn;
    private String mArticleId;
    private LayoutInflater mInflater;

    public static void start(Context context, Activity preActivity
            , String colunm, String articleId) {
        putParmToNextPage(BUNDLE_KEY_COLUMN, colunm);
        putParmToNextPage(BUNDLE_KEY_ARTICLEID, articleId);
        toNextActivity(context, NewsDetailActivity.class, preActivity);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mColumn = getValueFromPrePage(BUNDLE_KEY_COLUMN);
        mArticleId = getValueFromPrePage(BUNDLE_KEY_ARTICLEID);

        mInflater = LayoutInflater.from(this);

        mBinding.toolbar.tvToolbarTitle.setText("详细内容");
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(this);
        initPL();
        initPic();
        showViewLoading();
        mPresenter.getNewsDetail(mColumn, mArticleId);
    }


    @Override
    protected NewsDetailPresenter getPresenter() {
        return new NewsDetailPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    public void showViewLoading() {
        showLoading(mBinding.slNewsDetailContent, mBinding.pl);
    }

    @Override
    public void showViewError(Throwable t) {
        showError(mBinding.slNewsDetailContent, mBinding.pl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_news_detail_cover:
                mBinding.rlNewsDetailPic.setVisibility(View.VISIBLE);
                AnimatorUtil.animAplhaIn(mBinding.rlNewsDetailPic, 250
                        , new AnimatorUtil.AnimListener() {
                            @Override
                            public void onEnd(Animator animation) {
                                mBinding.llNewsDetailFull.setVisibility(View.GONE);
                            }
                        });

                break;
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.rl_news_detail_pic:
                mBinding.llNewsDetailFull.setVisibility(View.VISIBLE);
                mBinding.rlNewsDetailPic.setVisibility(View.GONE);

                break;


        }
    }

    @Override
    public void getNewsDetail(NewsDetail newsDetail) {
        NewsDetail.DataBean data = newsDetail.getData();
        mBinding.tvNewsDetailTitle.setText(data.getTitle());
        mBinding.tvNewsDetailDate.setText(data.getPub_time());
        GlideUtil.load(this, data.getImgurl(), mBinding.ivNewsDetailCover);
        List<NewsDetail.DataBean.ContentBean> content = data.getContent();
        for (NewsDetail.DataBean.ContentBean bean : content) {
            if (bean.getType().equals("text")) {
                TextView tv = (TextView) mInflater.inflate(R.layout.textview_content, null, false);
                tv.setText("\u3000\u3000" + bean.getInfo());
                mBinding.llNewsDetailContent.addView(tv);
            }
        }

        GlideUtil.load(this, data.getImgurl(), mBinding.pvNewsDetailPic);
        mBinding.pvNewsDetailPic.setDrawingCacheEnabled(true);
        showContent(mBinding.slNewsDetailContent, mBinding.pl);
    }

    @Override
    public void savePicDone(Boolean b) {
        if (b) {
            ToastUtils.showShort("保存图片成功");
        } else {
            ToastUtils.showShort("保存图片失败");
        }
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case PER_CODE_STORAGE:
                Bitmap cache = mBinding.pvNewsDetailPic.getDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(cache);
                mPresenter.saveBitmap(bitmap);
                break;
        }

    }

    @Override
    public void onPermissionDenied(int requestCode) {
        switch (requestCode) {
            case PER_CODE_STORAGE:
                ToastUtils.showShort("保存图片失败,未授予权限");
                break;
        }
    }

    /**
     * 初始化ProgressLayout
     */
    public void initPL() {
        mBinding.pl.setColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT)
                , mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mBinding.pl.setRefreshClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewLoading();
                mPresenter.getNewsDetail(mColumn, mArticleId);
            }
        });
    }

    /**
     * 初始化大图查看模式
     */
    private void initPic() {
        mBinding.ivNewsDetailCover.setOnClickListener(this);
        mBinding.rlNewsDetailPic.setOnClickListener(this);
        mBinding.pvNewsDetailPic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                NormalSelectionDialog dialog = new NormalSelectionDialog.Builder(NewsDetailActivity.this)
                        .setlTitleVisible(false)
                        .setItemHeight(50)  //设置item的高度
                        .setItemWidth(0.9f)  //屏幕宽度*0.9
                        .setItemTextColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))  //设置item字体颜色
                        .setItemTextSize(18)  //设置item字体大小
                        .setCancleButtonText(getResources().getString(R.string.cancel))  //设置最底部“取消”按钮文本
                        .setTopBgResResources(R.drawable.selector_actiondialog_top_color_bg)
                        .setMiddleBgResResources(R.drawable.selector_actiondialog_middle_color_bg)
                        .setBottomBgResResources(R.drawable.selector_actiondialog_bottom_color_bg)
                        .setSingleBgResResources(R.drawable.selector_actiondialog_single_color_bg)
                        .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                            @Override
                            public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                                switch (position) {
                                    case 0:
                                        PermissionManager.requestPermission(NewsDetailActivity.this
                                                , "保存图片需要开启文件权限"
                                                , PER_CODE_STORAGE, NewsDetailActivity.this
                                                , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        break;
                                }

                                dialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .build();
                ArrayList<String> datas = new ArrayList<>();
                datas.add(getResources().getString(R.string.save_picture));
                dialog.setDatas(datas).show();
                return true;
            }
        });
    }
}
