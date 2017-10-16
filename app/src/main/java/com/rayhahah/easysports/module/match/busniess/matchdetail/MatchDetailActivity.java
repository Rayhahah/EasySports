package com.rayhahah.easysports.module.match.busniess.matchdetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.databinding.ActivityMatchDetailBinding;
import com.rayhahah.easysports.module.match.bean.MatchDetailBean;
import com.rayhahah.easysports.module.match.domain.VPMatchDetailAdapter;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.RxSchedulers;
import com.rayhahah.rbase.utils.useful.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MatchDetailActivity extends BaseActivity<MatchDetailPresenter, ActivityMatchDetailBinding> implements MatchDetailContract.IMatchDetailView, TabLayout.OnTabSelectedListener {

    private String mId;
    private String year;
    private Disposable mTimer;
    private int counter = 0;


    private boolean isNeedUpdateTab = true;
    private VPMatchDetailAdapter mVPAdapter;

    public static void start(Context context, Activity preActivity, String mid) {
        putParmToNextPage(C.MATCH.INTENT_MID, mid);
        toNextActivity(context, MatchDetailActivity.class, preActivity);
    }

    @Override
    protected void setStatusColor() {
        StatusBarUtils.setTranslucent(this, 0);
    }

    @Override
    protected MatchDetailPresenter getPresenter() {
        return new MatchDetailPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_match_detail;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mId = getValueFromPrePage(C.MATCH.INTENT_MID);
        year = DateTimeUitl.strGetTime().substring(0, 4);
        initToolBar();
        initData();
    }

    private void initData() {
//        DialogUtil.showLoadingDialog(mContext, "正在获取比赛数据", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mPresenter.getMatchDetail(mId);
    }

    private void initToolBar() {
        mBinding.toolbar.rlToolbar.setBackgroundColor(Color.parseColor("#00000000"));
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.tvToolbarTitle.setText("比赛详情");
        mBinding.toolbar.ivToolbarRefresh.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("正在刷新数据");
                initData();
            }
        });
    }


    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void getMatchDetailSuccess(MatchDetailBean data) {
        MatchDetailBean.BaseInfo info = data.data;
        mBinding.toolbar.tvToolbarTitle.setText(info.leftName + "vs" + info.rightName);
        mBinding.tvLeftInfo.setText(info.leftWins + "胜" + info.leftLosses + "负");
        mBinding.tvRightInfo.setText(info.rightWins + "胜" + info.rightLosses + "负");

        String startTime = info.startDate + info.startHour;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM月dd日HH:mm");
        // 当前比赛状态
        String state = "未开始";
        try {
            Date startDate = format.parse(year + startTime);
            Date todayDate = new Date();
            if (startDate.getTime() > todayDate.getTime()) { // 未开始
                if (isNeedUpdateTab) { // 更新TAB
                    mPresenter.initTab(false, mId, info);
                }
            } else {
                state = info.quarterDesc;
                // 第四节 00:00 或 加时n 00:00 表示比赛已经结束
                if (((state.contains("第4节")
                        || state.contains("加时"))
                        && !info.leftGoal.equals(info.rightGoal))
                        && state.contains("00:00")) {
                    state = "已结束";
                    stopTimer();
                } else {
                    //正在进行
                    //启动每10秒刷新数据
                    startTimer();
                }
                if (isNeedUpdateTab) {
                    mPresenter.initTab(true, mId, info);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        isNeedUpdateTab = false;
        mBinding.tvMatchDetailStatus.setText(state);
        mBinding.tvMatchDetailType.setText(info.desc);
        mBinding.tvMatchDetailInfo.setText(info.startDate + "   " + info.startHour + "   " + info.venue);
        mBinding.tvLeftPoint.setText(info.leftGoal);
        mBinding.tvRightPoint.setText(info.rightGoal);
        GlideUtil.load(mContext, info.leftBadge, mBinding.ivLeftCover);
        GlideUtil.load(mContext, info.rightBadge, mBinding.ivRightCover);
        DialogUtil.dismissDialog(true);
    }

    @Override
    public void getMatchDetailFailed(String message) {
        DialogUtil.dismissDialog(false);
        ToastUtils.showShort(message);
    }

    @Override
    public void initTabSuccess(ArrayList<String> tabTitles, ArrayList<BaseFragment> fragmentList) {
        mVPAdapter = new VPMatchDetailAdapter(getSupportFragmentManager(), fragmentList, tabTitles);
        mBinding.vpMatchDetail.setAdapter(mVPAdapter);
        mBinding.vpMatchDetail.setScroll(true);
        mBinding.tlMatchDetail.setupWithViewPager(mBinding.vpMatchDetail);
        mBinding.tlMatchDetail.setTabsFromPagerAdapter(mVPAdapter);
        mBinding.tlMatchDetail.addOnTabSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
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


    private void startTimer() {
        if (mTimer == null) {
            mTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
                    .compose(RxSchedulers.<Long>ioMain())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
                            counter++;
                            if (counter % 20 == 0) {
                                EventBus.getDefault().post(new MsgEvent(C.EventAction.REFRESH_MATCH_DATA, null));
                                mPresenter.getMatchDetail(mId);
                            }
                        }
                    });
        }
    }

    private void stopTimer() {
        if (mTimer != null && !mTimer.isDisposed()) {
            mTimer.dispose();
            counter = 0;
        }
    }
}
