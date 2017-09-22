package com.rayhahah.easysports.utils;

import android.content.Context;
import android.graphics.Color;

import com.rayhahah.dialoglib.RLoadingDialog;
import com.rayhahah.dialoglib.RProgressDialog;
import com.rayhahah.easysports.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 用于处理加载dialog,图片选择dialog,检查更新dialog
 * Created by ares on 16/3/29.
 */
public class DialogUtil {

    /* 加载框 */
    private static RLoadingDialog loadingDialog;
    private static int mLoadingDuration = 1250;
    private static int mLoadingWidth = 10;
    private static int mLoadingRadius = 100;
    private static RProgressDialog progressDialog;
    private static int mLastProgress = 0;
    private static int mTargetProgress;

    /**
     * 显示加载dialog
     *
     * @param context
     */
    public static void showProgressDialog(Context context, int waveColor) {
        progressDialog = new RProgressDialog.Builder(context)
                .setBgResource(R.drawable.shape_alpha_corner4)
                .setCircleColor(Color.parseColor("#00000000"))
                .setWaveColor(waveColor)
                .setSuccessColor(waveColor)
                .setPauseColor(waveColor)
                .setBeginColor(waveColor)
                .setTouchOutside(false)
                .setTextSize(50)
                .setTextColor(Color.parseColor("#ffffff"))
                .setSpeed(1000).build();
        progressDialog.show();
    }

    /**
     * 设置进度
     * <p>
     * 1-100
     *
     * @param progress 进度
     */
    public static void setProgress(final int progress) {
        mTargetProgress = progress;
        if (progressDialog != null) {
            int p = progress * 10;
            if (mLastProgress == 1000) {
                dismissDialog(true);
            }
            if (p > mLastProgress) {
                mLastProgress++;
            } else if (p < mLastProgress) {
                mLastProgress--;
            } else if (p == mLastProgress) {
                return;
            }
            Observable.just(mLastProgress).delay(50, TimeUnit.MILLISECONDS).subscribe(new Consumer<Integer>() {
                @Override
                public void accept(@NonNull Integer integer) throws Exception {
                    if (progressDialog == null) {
                        return;
                    }
                    progressDialog.setProgress(mLastProgress * 1f / 1000);
                    setProgress(mTargetProgress);
                }
            });
        }
    }

    public static void showLoadingDialog(Context context, String tips, int colorPrimary) {
        loadingDialog = new RLoadingDialog.Builder(context)
                .setBgResource(R.drawable.shape_bg_corner4)
                .setLoadingColor(colorPrimary)
                .setloadingDuration(mLoadingDuration)
                .setLoadingWidth(mLoadingWidth)
                .setLoadingRadius(mLoadingRadius)
                .setTips(tips)
                .setTipsColor(colorPrimary)
                .setTipsSize(20)
                .setTouchOutside(false)
                .build();
        loadingDialog.show();
    }

    public static void dismissDialog(boolean isOk) {
        if (loadingDialog != null) {
            loadingDialog.dismiss(isOk);
            loadingDialog = null;
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
