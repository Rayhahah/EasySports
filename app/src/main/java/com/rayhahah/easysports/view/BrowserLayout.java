package com.rayhahah.easysports.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rayhahah.easysports.R;
import com.rayhahah.rbase.utils.base.ConvertUtils;


/**
 * 浏览器Layout,封装以及初始化WebView
 */
public class BrowserLayout extends LinearLayout {

    private Context mContext = null;
    private WebView mWebView = null;
    private View mBrowserControllerView = null;
    private ImageButton mGoBackBtn = null;
    private ImageButton mGoForwardBtn = null;
    private ImageButton mGoBrowserBtn = null;
    private ImageButton mRefreshBtn = null;

    private int mBarHeight = 5;
    private ProgressBar mProgressBar = null;

    private String mLoadUrl = "";

    public BrowserLayout(Context context) {
        this(context, null);
    }

    public BrowserLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);
        mProgressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mBarHeight, getResources().getDisplayMetrics()));

        mWebView = new WebView(context);
        initWebView();

        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        addView(mWebView, lps);

        mBrowserControllerView = LayoutInflater.from(context).inflate(R.layout.layout_browser_controller, null);
        mGoBackBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_back);
        mGoForwardBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_forward);
        mGoBrowserBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_go);
        mRefreshBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_refresh);

        mGoBackBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (canGoBack()) {
                    goBack();
                }
            }
        });

        mGoForwardBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (canGoForward()) {
                    goForward();
                }
            }
        });

        mRefreshBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                refresh(mLoadUrl);
            }
        });

        mGoBrowserBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mLoadUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mLoadUrl));
                    mContext.startActivity(intent);
                }
            }
        });

        addView(mBrowserControllerView, LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(45));
    }

    private void initWebView() {
        mWebView.setLayerType(LAYER_TYPE_HARDWARE, null);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        //mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        //图片加载
        if(Build.VERSION.SDK_INT >= 19){
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        }else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    public void setWebClient(WebViewClient webClient) {

        if (mWebView != null) {
            mWebView.setWebViewClient(webClient);
        }
    }

    public void setChromeClient(WebChromeClient chromeClient) {
        if (mWebView != null) {
            mWebView.setWebChromeClient(chromeClient);
        }
    }

    /**
     * 设置加载进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (mProgressBar != null) {
            if (progress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(progress);
            }
        }
    }

    /**
     * 设置当前网页地址
     *
     * @param url
     */
    public void setLoadUrl(String url) {
        mLoadUrl = url;
    }

    public void destory() {
        //如此才能真正清除，不会导致内存泄漏
        if (mWebView != null) {
            //加载空内容
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            mWebView.clearHistory();
            mWebView.removeAllViews();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    /**
     * 刷新网页
     *
     * @param url
     */
    public void refresh(String url) {
        mWebView.reload();
    }

    /**
     * 是否能回退
     *
     * @return
     */
    public boolean canGoBack() {
        return null != mWebView ? mWebView.canGoBack() : false;
    }

    /**
     * 是否能前进
     *
     * @return
     */
    public boolean canGoForward() {
        return null != mWebView ? mWebView.canGoForward() : false;
    }


    /**
     * 回退
     */
    public void goBack() {
        if (null != mWebView) {
            mWebView.goBack();
        }
    }

    /**
     * 前进
     */
    public void goForward() {
        if (null != mWebView) {
            mWebView.goForward();
        }
    }

    public WebView getWebView() {
        return mWebView != null ? mWebView : null;
    }

    /**
     * 是否显示控制布局
     */
    public void setIsShowController(boolean isShow) {
        if (isShow) {
            mBrowserControllerView.setVisibility(View.VISIBLE);
        } else {
            mBrowserControllerView.setVisibility(View.GONE);
        }
    }
}
