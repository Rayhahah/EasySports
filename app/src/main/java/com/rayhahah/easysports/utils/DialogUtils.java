package com.rayhahah.easysports.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 弹窗工具类
 */
public class DialogUtils {

    private static ProgressDialog mProgressDialog;

    public static void showLoading(Context context, String msg) {
        showLoading(context, null, msg);
    }

    public static void showLoading(Context context, String title, String msg) {
        dismiss();
        mProgressDialog = ProgressDialog.show(context, null, msg);
    }

    public static void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
