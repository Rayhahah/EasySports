package com.rayhahah.easysports.module.mine.business.livelist;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseActivity;
import com.rayhahah.easysports.databinding.ActivityLivePlayBinding;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.easysports.utils.PlayManager;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.RLog;
import com.rayhahah.rbase.utils.useful.RxSchedulers;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LivePlayActivity extends BaseActivity<RBasePresenter, ActivityLivePlayBinding> implements ITXLivePlayListener {


    private String mUrl;
    private String mTitle;
    private PlayManager playManager;
    private int event;
    private Disposable mTimer;
    private long counter = 0;

    public static void start(Context context, Activity preActivity, String url, String title) {
        putParmToNextPage(C.MINE.INTENT_URL, url);
        putParmToNextPage(C.MINE.INTENT_TITLE, title);
        toNextActivity(context, LivePlayActivity.class, preActivity);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_live_play;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mUrl = getValueFromPrePage(C.MINE.INTENT_URL);
        mTitle = getValueFromPrePage(C.MINE.INTENT_TITLE);

        initPlayManager();
        // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        int code = playManager.play(mUrl, PlayManager.VIDEO_TYPE.LIVE_FLV);
//        int code = playManager.play(mUrl, PlayManager.VIDEO_TYPE.LIVE_RTMP);
        RLog.e("code=" + code);
        mBinding.tvRecordProjectId.setText(mTitle);
        ToastUtils.showShort("正在连接直播间");
//        DialogUtil.showLoadingDialog(mContext, getString(R.string.GET_LIVING), mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
    }

    private void initPlayManager() {
        playManager = new PlayManager.Builder(mContext, mBinding.videoView)
                //缓冲模式
                .setCacheMode(PlayManager.CacheMode.AUTO)
                //缩放模式
                .setRenderMode(PlayManager.RenderMode.AUTO_JUST)
                //连接监听
                .setListener(this)
                .build();
        //监听电话状态,有电话打入的时候暂停播放功能
        playManager.registerPhoneListener();

    }

    public void liveBack(View view) {
        finish();
    }

    public void livePlay(View view) {
        if (playManager.isPlaying()) {
            playManager.pause();
            //清除最后一帧画面,如果不清除,画面会停留在最后一刻
            playManager.clearLastFrame(true);
            mBinding.ivRecordSwitch.setImageResource(R.drawable.live_video_switch_off);
            stopTimer();
            mBinding.tvRecordTime.setText(R.string.example_time);
        } else {
            playManager.play(mUrl, PlayManager.VIDEO_TYPE.LIVE_FLV);
//            DialogUtil.showLoadingDialog(mContext, getString(R.string.GET_LIVING), mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
        }
    }

    public void liveScreenSwitch(View view) {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            DialogUtil.dismissDialog(true);
            mBinding.ivRecordSwitch.setImageResource(R.drawable.live_video_switch_on);
            startTimer();
        }

        if (event < 0) {
            DialogUtil.dismissDialog(false);
            Toast.makeText(mContext, bundle.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        playManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playManager.destroy();
    }

    private void startTimer() {
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
}
