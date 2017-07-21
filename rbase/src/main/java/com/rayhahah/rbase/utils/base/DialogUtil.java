package com.rayhahah.rbase.utils.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 用于处理加载dialog,图片选择dialog,检查更新dialog
 * Created by ares on 16/3/29.
 */
public class DialogUtil {


    /* 加载框 */
    private static ProgressDialog progressDialog;

    /**
     * 显示加载dialog
     *
     * @param context
     * @param msg
     */
    public static ProgressDialog showLoadingDialog(Context context, String msg) {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

        }
        progressDialog = ProgressDialog.show(context, "", msg);

        return progressDialog;
    }

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


    /**
     * 设置是否可以取消
     *
     * @param cancel
     */
    public static void setDialogCancelAble(boolean cancel) {
        if (progressDialog != null) {
            progressDialog.setCancelable(cancel);
        }


    }


    /**
     * dialog消失
     */
    public static void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 消失
     *
     * @param cancelListener
     */
    public static void setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
        if (progressDialog != null) {

            progressDialog.setOnCancelListener(cancelListener);
        }
    }


    private Context context;

    public DialogUtil(Context context) {
        this.context = context;
    }


}
