package com.rayhahah.rbase.utils.useful;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
 * @time 2017/11/13
 * @tips 这个类是Object的子类
 * @fuction 基于HttpUrlConnection的网路请求封装
 */
public class RRequestHelper {

    //请求超时时间10s
    private static final int CONNECTION_TIMEOUT = 10000;
    //数据传输超时时间，很重要，必须设置。
    private static final int READ_TIMEOUT = 10000;
    //线程池最大支持线程数量
    private static final int FIX_POOL = 10;

    //请求方法，暂时只支持GET和POST
    private static final int METHOD_GET = 0;
    private static final int METHOD_POST = 1;

    //线程池服务
    private static ExecutorService mExecutorService;

    //标识协议类型
    private static final int PROTOCOL_HTTP = 0;
    private static final int PROTOCOL_HTTPS = 1;

    private static void init() {
        if (mExecutorService == null) {
            synchronized (RRequestHelper.class) {
                if (mExecutorService == null) {
                    //效果是一样的
                    mExecutorService = Executors.newFixedThreadPool(FIX_POOL);
                }
            }
        }
    }

    /**
     * 通用GET请求
     *
     * @param context    上下文Activity
     * @param requestUrl 请求地址
     * @param rcb        回调函数
     */
    public static void get(Context context,
                           String requestUrl,
                           IRRequestCallback rcb) {
        sendRequest(METHOD_GET, context, requestUrl, null, rcb);
    }


    /**
     * 通用post请求
     *
     * @param context    上下文Activity
     * @param requestUrl 请求地址
     * @param params     请求参数
     * @param rcb        回调函数
     */
    public static void post(Context context,
                            String requestUrl,
                            HashMap<String, String> params,
                            IRRequestCallback rcb) {
        sendRequest(METHOD_POST, context, requestUrl, params, rcb);
    }


    private static void sendRequest(final int method,
                                    final Context context,
                                    final String requestUrl,
                                    final HashMap<String, String> params,
                                    final IRRequestCallback rcb) {
        init();
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                if (!networkEnable(context)) {
                    sendResponse(context, false, "当前没有网络！", rcb);
                    return;
                }
                String result = "";
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(requestUrl);
                    int protocolType = PROTOCOL_HTTP;
                    if (url.getProtocol().toLowerCase().equals("https")) {
                        protocolType = PROTOCOL_HTTPS;
                    } else if (url.getProtocol().toLowerCase().equals("http")) {
                        protocolType = PROTOCOL_HTTP;
                    }

                    switch (protocolType) {
                        case PROTOCOL_HTTP:
                            urlConnection = (HttpURLConnection) url.openConnection();
                            break;
                        case PROTOCOL_HTTPS:
                            TrustManager[] xtmArray = new MytmArray[]{new MytmArray()};
                            trustAllHosts(xtmArray);
                            urlConnection = (HttpsURLConnection) url.openConnection();
                            // 不进行主机名确认
                            ((HttpsURLConnection) urlConnection).setHostnameVerifier(DO_NOT_VERIFY);
                            break;
                        default:
                            break;
                    }
                    initUrlConnection(urlConnection, method, params);

                    urlConnection.connect();

                    //check the result of connection
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 获得读取的内容
                        InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                        // 获取输入流对象
                        BufferedReader buffer = new BufferedReader(in);
                        String inputLine = "";
                        while ((inputLine = buffer.readLine()) != null) {
                            result += inputLine + "\n";
                        }
                        in.close(); // 关闭字符输入流
                    } else {
                        sendResponse(context, false, result, rcb);
                        return;
                    }
                } catch (MalformedURLException e) {
                    try {
                        sendResponse(context, false, "code=" + urlConnection.getResponseCode() + ",message=" + urlConnection.getResponseMessage(), rcb);
                    } catch (IOException e1) {
                        sendResponse(context, false, e.getMessage(), rcb);
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                } catch (IOException e) {
                    sendResponse(context, false, e.getMessage(), rcb);
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect(); // 断开连接
                    }
                }
                sendResponse(context, true, result, rcb);
            }
        });
    }

    /**
     * 初始化urlConnection配置参数
     *
     * @param urlConnection
     * @param method
     * @param params
     * @throws IOException
     */
    private static void initUrlConnection(HttpURLConnection urlConnection, int method, HashMap<String, String> params) throws IOException {
        // 建立连接超时时间
        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        //数据传输超时时间，很重要，必须设置。
        urlConnection.setReadTimeout(READ_TIMEOUT);
        // 向连接中写入数据
        urlConnection.setDoInput(true);
        // 从连接中读取数据
        urlConnection.setDoOutput(true);
        // 禁止缓存
        urlConnection.setUseCaches(false);
        urlConnection.setInstanceFollowRedirects(true);
        urlConnection.setRequestProperty("Charset", "UTF-8");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        switch (method) {
            case METHOD_GET:
                urlConnection.setRequestMethod("GET");
                break;
            case METHOD_POST:
                // 设置请求类型为
                urlConnection.setRequestMethod("POST");

                if (params != null) {
                    String strParams = params2String(params);
                    // 获取输出流
                    DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
                    // 将要传递的数据写入数据输出流,不要使用out.writeBytes(param); 否则中文时会出错
                    out.write(strParams.getBytes("utf-8"));
                    // 输出缓存
                    out.flush();
                    // 关闭数据输出流
                    out.close();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 将HashMap中的参数拼接成字符串
     *
     * @param params
     */
    private static String params2String(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator != null && iterator.hasNext()) {
            String key = iterator.next();
            sb.append(key + "=" + URLEncoder.encode(params.get(key), "UTF-8"));
            sb.append("&");
        }
        String result = "";
        if (sb.length() > 0) {
            result = sb.substring(0, sb.length() - 1);
        }
        return result;
    }

    /**
     * 将结果调回主线程中
     *
     * @param isSuccess
     * @param result
     * @param rcb
     */
    private static void sendResponse(Context context,
                                     final boolean isSuccess,
                                     final String result,
                                     final IRRequestCallback rcb) {

        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isSuccess) {
                        rcb.response(result);
                    } else {
                        rcb.failure(result);
                    }
                }
            });
        } else {
            if (isSuccess) {
                rcb.response(result);
            } else {
                rcb.failure(result);
            }
        }
    }

    /**
     * 检测网络是否可用
     *
     * @return true为网络可用，否则不可用
     */
    private static boolean networkEnable(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifiInfo != null
                && wifiInfo.isConnected())
                || (mobileInfo != null
                && mobileInfo.isConnected())) {
            return true;
        }
        return false;
    }

    /**
     * 信任所有主机-对于任何证书都不做检查
     *
     * @param xtmArray
     */
    private static void trustAllHosts(TrustManager[] xtmArray) {
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, xtmArray, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface IRRequestCallback {
        void response(String result);

        void failure(String msg);
    }

    /**
     * Https认证
     */
    private static class MytmArray implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // return null;
            return new X509Certificate[]{};
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub
            // System.out.println("cert: " + chain[0].toString() + ", authType: "
            // + authType);
        }
    }

    /**
     * 主机地址认证
     * 这里允许所有主机，不做认证处理
     */
    private static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }
    };
}
