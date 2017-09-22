package com.rayhahah.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rayhahah.dialoglib.utils.ScreenSizeUtils;
import com.rayhahah.dialoglib.utils.UiUtils;
import com.rayhahah.dialoglib.view.ProgressWaveView;

/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author Rayhahah
 * @blog http://rayhahah.com
 * @time 2017/9/21
 * @tips 这个类是Object的子类
 * @fuction 进度弹窗，海水薄凉贝塞尔
 */
public class RProgressDialog {


    private ProgressWaveView mWaveView;
    private Dialog mDialog;
    private View mDialogView;
    private RProgressDialog.Builder mBuilder;
    private long mSpeed;
    private int mLastProgress;
    private int mFakeProgress;

    public RProgressDialog(Builder builder) {
        mBuilder = builder;
        mDialog = new Dialog(mBuilder.getContext(), R.style.bottomLoadingDialogStyle);
        mDialogView = View.inflate(mBuilder.getContext(), R.layout.widget_progress_dialog, null);
        mWaveView = (ProgressWaveView) mDialogView.findViewById(R.id.pwv_loading);
        mDialogView.setMinimumHeight((int) (ScreenSizeUtils.getInstance(mBuilder.getContext())
                .getScreenHeight() * builder.getItemHeight()));
        mDialog.setContentView(mDialogView);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        int screenWidth = ScreenSizeUtils.getInstance(mBuilder.getContext()).getScreenWidth();
        int screenHeight = ScreenSizeUtils.getInstance(mBuilder.getContext()).getScreenHeight();
        lp.width = (int) (screenWidth *
                builder.getItemWidth());
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = (int) (screenWidth *
                builder.getItemWidth());
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        initDialog(builder);
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
    }

    public void setProgress(float progress) {
        mWaveView.setProgress(progress);
    }

    private void initDialog(RProgressDialog.Builder builder) {
        mDialogView.setBackgroundResource(builder.getBgResource());
        mWaveView.setBeginColor(builder.getBeginColor());
        mWaveView.setWaveColor(builder.getWaveColor());
        mWaveView.setCircleColor(builder.getCircleColor());
        mWaveView.setSuccessColor(builder.getSuccessColor());
        mWaveView.setPauseColor(builder.getPauseColor());
        mWaveView.setProgressTextSize(builder.getTextSize());
        mWaveView.setTextColor(builder.getTextColor());
        mSpeed = builder.getSpeed();
    }

    public void show() {
        mWaveView.startWavingAnim(mSpeed);
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public Dialog getDialog() {
        return mDialog;
    }


    public static class Builder {

        private float itemHeight;
        private float itemWidth;
        private Context mContext;
        private boolean isTouchOutside;

        private int bgResource;

        private int beginColor;
        private int waveColor;
        private int circleColor;
        private int successColor;
        private int pauseColor;
        private int textSize;
        private int textColor;
        private long speed;

        public Builder(Context context) {
            mContext = context;
            bgResource = Color.WHITE;
            itemHeight = 0.28f;
            itemWidth = 0.5f;
            isTouchOutside = true;

            beginColor = Color.BLUE;
            waveColor = Color.BLUE;
            circleColor = Color.BLUE;
            successColor = Color.BLUE;
            pauseColor = Color.BLUE;
            textSize = UiUtils.sp2px(mContext, 20);
            textColor = Color.BLUE;
            speed = 1000;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setTouchOutside(boolean touchOutside) {
            isTouchOutside = touchOutside;
            return this;
        }

        public float getItemHeight() {
            return itemHeight;
        }

        public Builder setItemHeight(float itemHeight) {
            this.itemHeight = itemHeight;
            return this;
        }

        public float getItemWidth() {
            return itemWidth;
        }

        public Builder setItemWidth(float itemWidth) {
            this.itemWidth = itemWidth;
            return this;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public int getBgResource() {
            return bgResource;
        }

        public Builder setBgResource(int resId) {
            this.bgResource = resId;
            return this;
        }

        public int getBeginColor() {
            return beginColor;
        }

        public Builder setBeginColor(int beginColor) {
            this.beginColor = beginColor;
            return this;
        }

        public int getWaveColor() {
            return waveColor;
        }

        public Builder setWaveColor(int waveColor) {
            this.waveColor = waveColor;
            return this;
        }

        public int getCircleColor() {
            return circleColor;
        }

        public Builder setCircleColor(int circleColor) {
            this.circleColor = circleColor;
            return this;
        }

        public int getSuccessColor() {
            return successColor;
        }

        public Builder setSuccessColor(int successColor) {
            this.successColor = successColor;
            return this;
        }

        public int getPauseColor() {
            return pauseColor;
        }

        public Builder setPauseColor(int pauseColor) {
            this.pauseColor = pauseColor;
            return this;
        }

        public int getTextSize() {
            return textSize;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public int getTextColor() {
            return textColor;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public long getSpeed() {
            return speed;
        }

        public Builder setSpeed(long speed) {
            this.speed = speed;
            return this;
        }

        public RProgressDialog build() {
            return new RProgressDialog(this);
        }
    }
}
