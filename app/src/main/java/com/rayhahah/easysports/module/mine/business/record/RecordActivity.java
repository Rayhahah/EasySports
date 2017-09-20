package com.rayhahah.easysports.module.mine.business.record;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.bean.db.LocalUser;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityRecordBinding;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.easysports.utils.PushManager;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.RLog;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RecordActivity extends BaseActivity<RecordPresenter, ActivityRecordBinding> implements RecordContract.IRecordView {

    private LocalUser mCurrentUser;
    private PushManager pushManager;
    private boolean pushSuccess = false;
    private Disposable mTimer;
    private int counter = 0;
    private String mUrl;
    private boolean openFlash = false;

    public static void start(Context context, Activity preActivity){
        toNextActivity(context,RecordActivity.class,preActivity);
    }

    @Override
    protected RecordPresenter getPresenter() {
        return new RecordPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_record;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        DialogUtil.showLoadingDialog(mContext, getString(R.string.GETTING_PUSHING_URL), mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        mCurrentUser = MyApp.getCurrentUser();
        mBinding.tvRecordProjectId.setText(mCurrentUser.getScreen_name() + "的直播间");
        initPushManager();
        mPresenter.getPushUrl();
    }

    private void initPushManager() {
        pushManager = new PushManager.Builder(new WeakReference<>(mContext), mBinding.videoView)
                //取消默认前置摄像头
                .setDefaultFrontCamera(false)
                //设置推流清晰度,参数意义详见注释
                .setQuality(PushManager.PushQuality.HIGH, true, false)
                //摄像头预览
                .setCameraPreview()
                //观看者屏幕方向,会和重力感应冲突,AndroidManifest中的screenOrientation和configChanges会互斥,同时设置会导致录制界面混乱
                //建议使用现有设置
//                .setWatcherScreen(PushManager.SCREEN_ORIENTATION.HOME_RIGHT)
                //关闭手动对焦,使用自动对焦
                .setTouchFocusMode(false)
                //暂停图片
                .setPauseImg(R.drawable.live_pause)
                //硬件加速,不设置默认会自动选择,(如果调用硬件加速失败会自动切换到手动加速)
                .setHardWareAccelerate(true)
                //监听
                .setPushListener(new PushManager.OnPushListener() {
                    @Override
                    public void pushSuccess(String reason) {
                        pushSuccess = true;
                        RLog.e("reason=" + reason);
                        Toast.makeText(mContext, reason, Toast.LENGTH_SHORT).show();
                        mBinding.ivRecordSwitch.setImageResource(R.drawable.live_video_switch_on);
                        DialogUtil.dismissDialog(true);
                        startTimer();
                        mBinding.tvRecordTime.setVisibility(View.VISIBLE);
                        mBinding.tvRecordRec.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onPushFail(String reason) {
                        //提示错误原因
                        pushSuccess = false;
                        RLog.e("reason=" + reason);
                        Toast.makeText(mContext, reason, Toast.LENGTH_SHORT).show();
//                        ToastUtil.showToast(reason);
                        pushManager.stopRtmpPublish();
                        DialogUtil.dismissDialog(false);
                        stopTimer();
                        mBinding.tvRecordTime.setVisibility(View.GONE);
                        mBinding.tvRecordRec.setVisibility(View.GONE);
                        mBinding.ivRecordSwitch.setImageResource(R.drawable.live_video_switch_off);
                    }

                    @Override
                    public void networkUnstable(String netWorkUnStable) {
                        //网络连接不稳定,提示
                        Toast.makeText(mContext, netWorkUnStable, Toast.LENGTH_SHORT).show();
                        DialogUtil.dismissDialog(false);
                    }
                })
                .build();
        pushManager.registerRotation();
        pushManager.initPhoneState();
    }

    private void startTimer() {
        stopTimer();
        mTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.<Long>ioMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        counter++;
                        mBinding.tvRecordTime.setText(DateTimeUitl.secondToMinuteOrHour(counter));
                    }
                });
    }

    private void stopTimer() {
        if (mTimer != null && !mTimer.isDisposed()) {
            mTimer.dispose();
            counter = 0;
        }
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    public void back(View view) {
        finish();
    }

    public void startRecord(View view) {
        //推送成功并且正在推流,文本显示为开启推流
        if (pushSuccess) {
            stopPush();
        } else {
            DialogUtil.showLoadingDialog(mContext, getString(R.string.GET_PUSHING), mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
            pushManager.startPush(mUrl);
        }
    }

    private void stopPush() {
        pushManager.stopRtmpPublish();
        pushSuccess = false;
        mBinding.ivRecordSwitch.setImageResource(R.drawable.live_video_switch_off);
        stopTimer();
        Toast.makeText(mContext, R.string.STOP_PUSH, Toast.LENGTH_SHORT).show();
        mBinding.tvRecordTime.setVisibility(View.GONE);
        mBinding.tvRecordRec.setVisibility(View.GONE);
    }

    public void flashlight(View view) {
        pushManager.openFlash(openFlash);
        if (openFlash) {
            mBinding.ivRecordFlashlight.setImageResource(R.drawable.live_video_flashlight_on);
        } else {
            mBinding.ivRecordFlashlight.setImageResource(R.drawable.live_video_flashlight_off);
        }
        openFlash = !openFlash;
    }

    public void switchCamera(View view) {
        pushManager.switchCamera();
    }

    public void switchScreen(View view) {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void switchVoise(View view) {
    }

    @Override
    public void getPushUrlSuccess(String pushUrl) {
        DialogUtil.dismissDialog(true);
        mUrl = pushUrl;
    }

    @Override
    public void getPushUrlFailed(String msg) {
        DialogUtil.dismissDialog(false);
        Toast.makeText(mContext, "获取直播推流失败", Toast.LENGTH_SHORT).show();
    }
}
