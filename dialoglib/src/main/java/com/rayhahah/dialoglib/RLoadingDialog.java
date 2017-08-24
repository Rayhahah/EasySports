package com.rayhahah.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rayhahah.dialoglib.utils.ScreenSizeUtils;
import com.rayhahah.dialoglib.utils.UiUtils;
import com.rayhahah.dialoglib.view.PathLoadingView;

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
 * @time 2017/8/15
 * @tips 这个类是Object的子类
 * @fuction 优雅的加载弹窗
 */
public class RLoadingDialog {


    private final TextView mTips;
    private final PathLoadingView mLoadingView;
    private Dialog mDialog;
    private View mDialogView;
    private Builder mBuilder;

    public RLoadingDialog(Builder builder) {
        mBuilder = builder;
        mDialog = new Dialog(mBuilder.getContext(), R.style.bottomLoadingDialogStyle);
        mDialogView = View.inflate(mBuilder.getContext(), R.layout.widget_loading_dialog, null);
        mTips = (TextView) mDialogView.findViewById(R.id.tv_loading_tips);
        mLoadingView = (PathLoadingView) mDialogView.findViewById(R.id.plv_loading);
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

    private void initDialog(Builder builder) {
        mDialogView.setBackgroundResource(builder.getBgResource());
        mTips.setText(builder.getTips());
        mTips.setTextColor(builder.getTipsColor());
        mTips.setTextSize(builder.getTipsSize());
        PathLoadingView.Builder plvBuilder = new PathLoadingView.Builder()
                .setColor(builder.getLoadingColor())
                .setDuration(builder.getLoadingDuration())
                .setStrokeWidth(builder.getLoadingWidth())
                .setRadius(builder.getLoadingRadius());
        mLoadingView.init(plvBuilder);
        mLoadingView.setStoppedListner(new PathLoadingView.AnimListner() {
            @Override
            public void onStopped() {
                mDialog.dismiss();
            }
        });
    }

    public void show() {
        mLoadingView.startAnim();
        mDialog.show();
    }

    public void dismiss(boolean isOk) {
        mLoadingView.stopAnim(isOk);
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

        private String tips;
        private int tipsColor;
        private int loadingWidth;
        private int loadingColor;
        private int loadingDuration;
        private int loadingRadius;
        private int bgResource;
        private int tipsSize;

        public Builder(Context context) {
            mContext = context;
            bgResource = Color.WHITE;
            itemHeight = 0.28f;
            itemWidth = 0.5f;
            isTouchOutside = true;
            tips = "正在加载中";
            tipsColor = Color.BLACK;
            loadingWidth = 5;
            loadingColor = Color.BLACK;
            loadingDuration = 2000;
            loadingRadius = 100;
            tipsSize = UiUtils.px2sp(mContext, 16);
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

        public String getTips() {
            return tips;
        }

        public Builder setTips(String tips) {
            this.tips = tips;
            return this;
        }

        public int getTipsColor() {
            return tipsColor;
        }

        public Builder setTipsColor(int tipsColor) {
            this.tipsColor = tipsColor;
            return this;
        }

        public int getLoadingWidth() {
            return loadingWidth;
        }

        public Builder setLoadingWidth(int loadingWidth) {
            this.loadingWidth = loadingWidth;
            return this;
        }

        public int getLoadingColor() {
            return loadingColor;
        }

        public Builder setLoadingColor(int loadingColor) {
            this.loadingColor = loadingColor;
            return this;
        }

        public int getLoadingDuration() {
            return loadingDuration;
        }

        public Builder setloadingDuration(int loadingDuration) {
            this.loadingDuration = loadingDuration;
            return this;
        }

        public int getLoadingRadius() {
            return loadingRadius;
        }

        public Builder setLoadingRadius(int loadingRadius) {
            this.loadingRadius = loadingRadius;
            return this;
        }

        public int getBgResource() {
            return bgResource;
        }

        public Builder setBgResource(int resId) {
            this.bgResource = resId;
            return this;
        }

        public int getTipsSize() {
            return tipsSize;
        }

        public Builder setTipsSize(int size) {
            this.tipsSize = size;
            return this;
        }

        public RLoadingDialog build() {
            return new RLoadingDialog(this);
        }

    }


}
