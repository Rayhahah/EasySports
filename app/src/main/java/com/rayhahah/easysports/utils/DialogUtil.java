package com.rayhahah.easysports.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.rayhahah.dialoglib.RLoadingDialog;
import com.rayhahah.easysports.R;

/**
 * 用于处理加载dialog,图片选择dialog,检查更新dialog
 * Created by ares on 16/3/29.
 */
public class DialogUtil {

    /* 加载框 */
    private static ProgressDialog progressDialog;

    private static RLoadingDialog loadingDialog;
    private static int mLoadingDuration = 1250;
    private static int mLoadingWidth = 10;
    private static int mLoadingRadius = 100;

    /**
     * 显示加载dialog
     *
     * @param context
     * @param msg
     */
    public static ProgressDialog showProgressDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 设置进度
     *
     * @param progress 进度
     */
    public static void setProgress(int progress) {
        if (progressDialog != null) {
            progressDialog.setProgress(progress);
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
    }
}
