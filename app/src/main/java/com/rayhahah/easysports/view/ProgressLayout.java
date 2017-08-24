package com.rayhahah.easysports.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rayhahah.easysports.R;

public class ProgressLayout extends RelativeLayout {
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_LOADING = 2;
    private LinearLayout loadingView;
    private LinearLayout errorView;
    private LayoutInflater mInflater;
    private LayoutParams layoutParams;
    private int mStatus;
    private BallRollView mLoadingBall;
    private TextView mRefresh;

    public ProgressLayout(Context context) {
        super(context);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void showLoading() {
        if (errorView != null && errorView.getVisibility() == VISIBLE) {
            errorView.setVisibility(GONE);
        }
        if (loadingView == null) {
            initLoadingView();
        } else {
            loadingView.setVisibility(VISIBLE);
        }
        mStatus = STATUS_LOADING;
    }


    public void showError() {
        if (loadingView != null && loadingView.getVisibility() == VISIBLE) {
            loadingView.setVisibility(GONE);
        }
        if (errorView == null) {
            initErrorView();
        } else {
            errorView.setVisibility(VISIBLE);
        }
        mStatus = STATUS_ERROR;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setColor(int normalColor, int activecColor) {
        if (mLoadingBall == null) {
            initLoadingView();
        }
        mLoadingBall.setColor(normalColor, activecColor);
        postInvalidate();
    }

    public int getStatus() {
        return mStatus;
    }

    public void setRefreshClick(OnClickListener listener) {
        if (mRefresh == null) {
            initErrorView();
        }
        mRefresh.setOnClickListener(listener);
        postInvalidate();
    }

    private void initLoadingView() {
        loadingView = (LinearLayout) mInflater.inflate(R.layout.layout_loading_view, null);
        mLoadingBall = ((BallRollView) loadingView.findViewById(R.id.loadingView));
        layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        loadingView.setLayoutParams(layoutParams);
        this.addView(loadingView, layoutParams);
    }

    private void initErrorView() {
        errorView = (LinearLayout) mInflater.inflate(R.layout.layout_error_view, null);
        mRefresh = ((TextView) errorView.findViewById(R.id.tv_refresh));
        layoutParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        errorView.setLayoutParams(layoutParams);
        this.addView(errorView);
    }

    /**
     * 展示内容
     *
     * @param content
     */
    public void showContent(View content) {
        this.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }


    /**
     * 显示错误引导页
     *
     * @param content
     */
    public void showError(View content) {
        this.setVisibility(View.VISIBLE);
        this.showError();
        content.setVisibility(View.GONE);
    }

    /**
     * 显示加载页面
     *
     * @param content
     */
    public void showLoading(View content) {
        this.setVisibility(View.VISIBLE);
        this.showLoading();
        content.setVisibility(View.GONE);
    }

}
