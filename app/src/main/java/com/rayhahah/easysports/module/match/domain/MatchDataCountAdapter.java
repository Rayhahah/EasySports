package com.rayhahah.easysports.module.match.domain;

import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;

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
 * @time 2017/10/11
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchDataCountAdapter extends BaseQuickAdapter<MatchStatusBean.TeamStats, BaseViewHolder> {
    private int mPrimaryColor;

    public MatchDataCountAdapter() {
        super(R.layout.item_match_data_count);
    }

    public void setPrimaryColor(int primaryColor) {
        mPrimaryColor = primaryColor;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MatchStatusBean.TeamStats item) {
        helper.setText(R.id.tv_right_value, item.rightVal + "")
                .setText(R.id.tv_left_value, item.leftVal + "")
                .setText(R.id.tv_value_name, item.text);
        final LinearLayout llLeftProgress = (LinearLayout) helper.getView(R.id.ll_left_progress);
        final LinearLayout llRightProgress = (LinearLayout) helper.getView(R.id.ll_right_progress);

        int sum = item.leftVal + item.rightVal;
        final float left = sum <= 0 ? 0 : (float) item.leftVal / (float) sum;
        final float right = sum <= 0 ? 0 : (float) item.rightVal / (float) sum;

        ViewTreeObserver vto = llRightProgress.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 务必移除监听，会多次调用
                if (Build.VERSION.SDK_INT < 16) {
                    llRightProgress.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    llRightProgress.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                View leftLine = new View(mContext);
                leftLine.setBackgroundColor(mPrimaryColor);
                int leftWidth = llLeftProgress.getWidth();
                int newWidth = (int) (leftWidth * left);
                //LogUtils.e("newwidth = " + newWidth + " leftWidth=" + leftWidth + " left=" + left);
                LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(newWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                leftLine.setLayoutParams(leftParams);
                llLeftProgress.setGravity(Gravity.RIGHT);
                llLeftProgress.addView(leftLine);

                View rightLine = new View(mContext);
                rightLine.setBackgroundColor(mPrimaryColor);
                int rightWidth = llRightProgress.getWidth();
                LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams((int) (rightWidth * right), ViewGroup.LayoutParams.MATCH_PARENT);
                rightLine.setLayoutParams(rightParams);
                llRightProgress.setGravity(Gravity.LEFT);
                llRightProgress.addView(rightLine);
                if (helper.getAdapterPosition() == 0) { // 没明白为什么，第一条数据的param总是不生效，移除重新添加就可以...
                    llLeftProgress.removeAllViews();
                    llLeftProgress.addView(leftLine);
                    llRightProgress.removeAllViews();
                    llRightProgress.addView(rightLine);
                }

                if (item.leftVal > item.rightVal) {
                    rightLine.setBackgroundColor(Color.GRAY);
                } else if (item.leftVal < item.rightVal) {
                    leftLine.setBackgroundColor(Color.GRAY);
                }
            }
        });
    }
}
