package com.rayhahah.easysports.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.ActivityRwebBinding;
import com.rayhahah.easysports.sonic.SonicRuntimeImpl;
import com.rayhahah.easysports.sonic.SonicSessionClientImpl;
import com.rayhahah.easysports.view.BrowserLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RWebActivity extends BaseActivity<RBasePresenter, ActivityRwebBinding> {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_BOTTOM_BAR = "BUNDLE_KEY_BOTTOM_BAR";
    public static final String BUNDLE_KEY_OVERRIDE_URL = "BUNDLE_KEY_OVERRIDE_URL";
    private String mUrl;
    private String mTitle;
    private String mIsShowBottomBar;
    private String mIsOverrideUrl;
    private SonicSession sonicSession;
    private SonicSessionClientImpl sonicSessionClient;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mUrl = getValueFromPrePage(BUNDLE_KEY_URL);
        mTitle = getValueFromPrePage(BUNDLE_KEY_TITLE);
        mIsShowBottomBar = getValueFromPrePage(BUNDLE_KEY_BOTTOM_BAR);
        mIsOverrideUrl = getValueFromPrePage(BUNDLE_KEY_OVERRIDE_URL);
        mBinding.toolbar.tvToolbarTitle.setText("详细内容");
        mBinding.toolbar.ivToolbarBack.setVisibility(View.VISIBLE);
        mBinding.toolbar.ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initSonic(mUrl);
        mBinding.blWebview.setWebClient(new MonitorWebClient(this, mBinding.blWebview));
        mBinding.blWebview.setChromeClient(new AppCacheWebChromeClient(mBinding.blWebview));
        mBinding.blWebview.setLoadUrl(mUrl);
        boolean isShowController = C.TRUE.equals(mIsShowBottomBar);
        mBinding.blWebview.setIsShowController(isShowController);
        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(mBinding.blWebview.getWebView());
            sonicSessionClient.clientReady();
        } else { // default mode
            mBinding.blWebview.getWebView().loadUrl(mUrl);
        }
    }

    private void initSonic(String url) {

        // init sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicConfig build = new SonicConfig.Builder().build();
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), build);
        }

        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);
//        offLineMode(sessionConfigBuilder);

        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSessionClient = new SonicSessionClientImpl();
            sonicSession.bindClient(sonicSessionClient);
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            // throw new UnknownError("create session fail!");
            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 离线模式
     *
     * @param sessionConfigBuilder
     */
    private void offLineMode(SonicSessionConfig.Builder sessionConfigBuilder) {
        // if it's offline pkg mode, we need to intercept the session connection
        sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
            @Override
            public String getCacheData(SonicSession session) {
                return null; // offline pkg does not need cache
            }
        });

        sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
            @Override
            public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                return new OfflinePkgSessionConnection(RWebActivity.this, session, intent);
            }
        });
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_rweb;
    }

    @Override
    protected int setFragmentContainerResId() {
        return 0;
    }

    @Override
    protected void onResume() {
        if (mBinding.blWebview.getWebView() != null) {
            mBinding.blWebview.getWebView().onResume();
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        if (mBinding.blWebview.getWebView() != null) {
            mBinding.blWebview.getWebView().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
        mBinding.blWebview.destory();
        super.onDestroy();
    }

    public static void start(Context context, Activity preActivity,
                             String url, String title,
                             boolean isShowBottomBar, boolean isOverrideUrlLoading) {
        putParmToNextPage(BUNDLE_KEY_URL, url);
        putParmToNextPage(BUNDLE_KEY_TITLE, title);
        putParmToNextPage(BUNDLE_KEY_BOTTOM_BAR, isShowBottomBar ? C.TRUE : C.FALSE);
        putParmToNextPage(BUNDLE_KEY_OVERRIDE_URL, isOverrideUrlLoading ? C.TRUE : C.FALSE);
        toNextActivity(context, RWebActivity.class, preActivity);
    }

    /**
     * 过滤广告字符集
     *
     * @param context
     * @param url
     * @return
     */
    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = C.Utils.AD;
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }


    private class MonitorWebClient extends WebViewClient {

        private Context mContext;
        private BrowserLayout mBrowserLayout;

        public MonitorWebClient(Context context, BrowserLayout browserLayout) {
            mBrowserLayout = browserLayout;
            mContext = context;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            String url = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString().toLowerCase();
            }
            if (!hasAd(mContext, url)) {
                return shouldInterceptRequest(view, url);
            } else {
                return new WebResourceResponse(null, null, null);
            }
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            if (sonicSession != null) {
                //step 6: Call sessionClient.requestResource when host allow the application
                // to return the local data .
                return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
            }
            return null;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            //错误提示
            Toast toast = Toast.makeText(RWebActivity.this.mContext, "Oh no! " + description + " " + failingUrl, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
            toast.show();
            //错误处理
            try {
                mBrowserLayout.getWebView().stopLoading();
            } catch (Exception ignored) {
            }
            try {
                mBrowserLayout.getWebView().loadUrl("about:blank");
            } catch (Exception ignored) {
            }
            if (mBrowserLayout.getWebView().canGoBack()) {
                mBrowserLayout.getWebView().goBack();
            }
            //  super.onReceivedError(view, errorCode, description, failingUrl);
        }

        //当load有ssl层的https页面时，如果这个网站的安全证书在Android无法得到认证，WebView就会变成一个空白页，而并不会像PC浏览器中那样跳出一个风险提示框
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            //忽略证书的错误继续Load页面内容
            handler.proceed();
            //handler.cancel(); // Android默认的处理方式
            //handleMessage(Message msg); // 进行其他处理
            //  super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mBrowserLayout.setLoadUrl(url);
            //页面加载完毕再去加载图片
            if (!view.getSettings().getLoadsImagesAutomatically()) {
                view.getSettings().setLoadsImagesAutomatically(true);
            }
            if (sonicSession != null) {
                sonicSession.getSessionClient().pageFinish(url);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }


    private class AppCacheWebChromeClient extends WebChromeClient {

        private BrowserLayout mBrowserLayout;

        public AppCacheWebChromeClient(BrowserLayout browserLayout) {
            mBrowserLayout = browserLayout;
        }

        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
            //    Log.e(APP_CACHE, "onReachedMaxAppCacheSize reached, increasing space: " + spaceNeeded);
            quotaUpdater.updateQuota(spaceNeeded * 2);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mBrowserLayout.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }


    /**
     * 离线模式
     */
    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }
}
