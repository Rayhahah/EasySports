package com.rayhahah.easysports.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.rayhahah.rbase.utils.base.StringUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

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
 * @time 2017/7/21
 * @tips 这个类是Object的子类
 * @fuction 封装分享工具类
 * @href 具体参数拓展可以看：
 * http://wiki.mob.com/%E4%B8%8D%E5%90%8C%E5%B9%B3%E5%8F%B0%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9%E7%9A%84%E8%AF%A6%E7%BB%86%E8%AF%B4%E6%98%8E/
 */
public class ShareUtils {

    public static final int TYPE_LOCAL = 1;
    public static final int TYPE_NET = 2;
    public static final int TYPE_BITMAP = 3;

    /**
     * 分享文本到微博
     *
     * @param text
     * @param listener
     */
    public static void shareToSinaWeibo(String text, PlatformActionListener listener) {
        shareToSinaWeibo(text, null, null, listener);
    }

    /**
     * 分享图文到微博
     * 本地图片和网络图片二选一
     *
     * @param text       文本
     * @param imagePath  本地图片
     * @param imageUrl   网络图片
     * @param paListener 分享事件回调
     */
    public static void shareToSinaWeibo(String text, String imagePath, String imageUrl, PlatformActionListener paListener) {
        Platform.ShareParams sp = new Platform.ShareParams();
        if (StringUtils.isNotEmpty(text)) {
            // text是分享文本，所有平台都需要这个字段
            sp.setText(text);

        }
        if (StringUtils.isNotEmpty(imagePath)) {
            //imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //确保SDcard下面存在此张图片
            sp.setImagePath(imagePath);
        }
        if (StringUtils.isNotEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        if (paListener != null) {
            weibo.setPlatformActionListener(paListener); // 设置分享事件回调
        }
        // 执行图文分享
        weibo.share(sp);

        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
    }

    /**
     * 分享视频
     *
     * @param filePath 本地视频路径
     * @param listener 分享事件回调
     */
    public static void shareToQQZone(String filePath, PlatformActionListener listener) {
        shareToQQZone(null, null, null, null, null, filePath, null, null, listener);
    }

    /**
     * 分享文本
     *
     * @param title
     * @param titleUrl
     * @param text
     * @param site
     * @param siteUrl
     * @param listener
     */
    public static void shareToQQZone(String title, String titleUrl, String text, String site, String siteUrl, PlatformActionListener listener) {
        shareToQQZone(title, titleUrl, text, null, null, null, site, siteUrl, listener);
    }

    /**
     * 分享图文
     *
     * @param title
     * @param titleUrl
     * @param text
     * @param imagePath 本地图片资源路径
     * @param site
     * @param siteUrl
     * @param listener
     */
    public static void shareToQQZone(String title, String titleUrl, String text, String imagePath, String site, String siteUrl, PlatformActionListener listener) {
        shareToQQZone(title, titleUrl, text, null, imagePath, null, site, siteUrl, listener);
    }


    /**
     * 发表说说
     *
     * @param imageTye     网络图片还是本地图片
     * @param imageUrlPath 图片资源地址
     * @param text
     * @param site
     * @param siteUrl
     * @param listener
     */
    public static void shareToQQZone(int imageTye, String imageUrlPath, String text, String site, String siteUrl, PlatformActionListener listener) {
        switch (imageTye) {
            case TYPE_LOCAL:
                shareToQQZone(null, null, text, null, imageUrlPath, null, site, siteUrl, listener);
                break;
            case TYPE_NET:
                shareToQQZone(null, null, text, imageUrlPath, null, null, site, siteUrl, listener);
                break;
            default:
                break;
        }
    }


    /**
     * 分享信息到QQ空间
     *
     * @param title                  测试分享的标题
     * @param titleUrl               http://sharesdk.cn
     * @param text                   测试分享的文本
     * @param imageUrl               http://www.someserver.com/测试图片网络地址.jpg
     * @param imagePath              本地图片地址
     * @param filePath               本地视频地址
     * @param site                   发布分享的网站名称
     * @param siteUrl                发布分享网站的地址
     * @param platformActionListener 分享事件回调
     */
    public static void shareToQQZone(String title, String titleUrl, String text
            , String imageUrl, String imagePath, String filePath
            , String site, String siteUrl, PlatformActionListener platformActionListener) {
        int shareType = -1;
        if (StringUtils.isNotEmpty(filePath)) {
            shareType = Platform.SHARE_VIDEO;
        }
        Platform.ShareParams sp = initQQZoneShareParams(shareType
                , title, titleUrl, text
                , imageUrl, imagePath, filePath, site, siteUrl);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        if (platformActionListener != null) {
            qzone.setPlatformActionListener(platformActionListener);
        }
//        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
//        qzone.setPlatformActionListener (new PlatformActionListener() {
//            public void onError(Platform arg0, int arg1, Throwable arg2) {
//                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
//            }
//            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
//                //分享成功的回调
//            }
//            public void onCancel(Platform arg0, int arg1) {
//                //取消分享的回调
//            }
//        });
        // 执行图文分享
        qzone.share(sp);
    }

    private static Platform.ShareParams initQQZoneShareParams(int shareType
            , String title, String titleUrl, String text
            , String imageUrl, String imagePath, String filePath
            , String site, String siteUrl) {
        Platform.ShareParams sp = new Platform.ShareParams();

        if (shareType != -1) {
            sp.setShareType(shareType);
        }

        if (StringUtils.isNotEmpty(title)) {
            sp.setTitle(title);
        }
        if (StringUtils.isNotEmpty(titleUrl)) {
            sp.setTitleUrl(titleUrl); // 标题的超链接
        }
        if (StringUtils.isNotEmpty(text)) {
            sp.setText(text);
        }
        if (StringUtils.isNotEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        if (StringUtils.isNotEmpty(imagePath)) {
            sp.setImagePath(imagePath);
        }

        if (StringUtils.isNotEmpty(filePath)) {
            sp.setFilePath(filePath);
        }

        if (StringUtils.isNotEmpty(site)) {
            sp.setSite(site);

        }
        if (StringUtils.isNotEmpty(siteUrl)) {
            sp.setSiteUrl(siteUrl);
        }

        return sp;
    }


    /**
     * ·····································微信好友···························
     */

    /**
     * 分享文本到微信
     *
     * @param title
     * @param text
     * @param listener
     */
    public static void shareToWechat(String title, String text, PlatformActionListener listener) {
        shareToWechat(Platform.SHARE_TEXT, title, text, null, null, null, null, null, null, listener);
    }

    /**
     * 分享图片到微信
     *
     * @param title
     * @param listener
     */
    public static void shareToWechat(int imageType, String title, String imageUrlPath
            , Bitmap imageData, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_IMAGE, title, null, null, null, imageUrlPath, null, null, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_IMAGE, title, null, null, imageUrlPath, null, null, null, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_IMAGE, title, null, null, null, null, imageData, null, null, listener);
                break;
        }
    }

    /**
     * 分享音乐到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param musicUrl
     * @param url          消息点击后打开的页面
     * @param listener
     */
    public static void shareToWechat(int imageType, String title, String text
            , String imageUrlPath, Bitmap imageData
            , String musicUrl, String url, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_MUSIC, title, text, url, null, imageUrlPath, null, musicUrl, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_MUSIC, title, text, url, imageUrlPath, null, null, musicUrl, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_MUSIC, title, text, url, null, null, imageData, musicUrl, null, listener);
                break;
        }
    }

    /**
     * 分享视频到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param url          视频网页地址
     * @param listener
     */
    public static void shareToWechat(int imageType, String title, String text
            , String imageUrlPath, Bitmap imageData
            , String url, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_VIDEO, title, text, url, null, imageUrlPath, null, null, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_VIDEO, title, text, url, imageUrlPath, null, null, null, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_VIDEO, title, text, url, null, null, imageData, null, null, listener);
                break;
        }
    }

    /**
     * 分享网页到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param url          网页地址
     * @param listener
     */
    public static void shareWebToWechat(int imageType, String title, String text
            , String imageUrlPath, Bitmap imageData
            , String url, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_WEBPAGE, title, text, url, null, imageUrlPath, null, null, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_WEBPAGE, title, text, url, imageUrlPath, null, null, null, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_WEBPAGE, title, text, url, null, null, imageData, null, null, listener);
                break;
        }
    }

    /**
     * 分享网页到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param filePath     文件本地路径
     * @param listener
     */
    public static void shareFileToWechat(int imageType, String title, String text
            , String imageUrlPath, Bitmap imageData
            , String filePath, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_FILE, title, text, null, null, imageUrlPath, null, null, filePath, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_FILE, title, text, null, imageUrlPath, null, null, null, filePath, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_FILE, title, text, null, null, null, imageData, null, filePath, listener);
                break;
        }
    }

    /**
     * 分享到微信好友
     *
     * @param shareType
     * @param title
     * @param text
     * @param url
     * @param imageUrl
     * @param imagePath
     * @param imageData
     * @param musicUrl
     * @param filePath
     * @param platformActionListener
     */
    public static void shareToWechat(int shareType
            , String title, String text, String url
            , String imageUrl, String imagePath, Bitmap imageData
            , String musicUrl, String filePath, PlatformActionListener platformActionListener) {
        Platform.ShareParams sp = initWechatShareParams(shareType, title, text, url
                , imageUrl, imagePath, imageData, musicUrl, filePath);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        if (platformActionListener != null) {
            wechat.setPlatformActionListener(platformActionListener);
        }
        wechat.share(sp);
    }


    /**
     * ·····································朋友圈·········································
     */

    /**
     * 分享文本到微信
     *
     * @param title
     * @param text
     * @param listener
     */
    public static void shareToWechatMoment(String title, String text, PlatformActionListener listener) {
        shareToWechatMoment(Platform.SHARE_TEXT, title, text, null, null, null, null, null, null, listener);
    }

    /**
     * 分享图片到微信
     *
     * @param title
     * @param listener
     */
    public static void shareToWechatMoment(int imageType, String title, String imageUrlPath
            , Bitmap imageData, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_IMAGE, title, null, null, null, imageUrlPath, null, null, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_IMAGE, title, null, null, imageUrlPath, null, null, null, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_IMAGE, title, null, null, null, null, imageData, null, null, listener);
                break;
        }
    }

    /**
     * 分享音乐到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param musicUrl
     * @param url          消息点击后打开的页面
     * @param listener
     */
    public static void shareToWechatMoment(int imageType, String title
            , String imageUrlPath, Bitmap imageData
            , String musicUrl, String url, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_MUSIC, title, null, url, null, imageUrlPath, null, musicUrl, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_MUSIC, title, null, url, imageUrlPath, null, null, musicUrl, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_MUSIC, title, null, url, null, null, imageData, musicUrl, null, listener);
                break;
        }
    }

    /**
     * 分享视频到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param url          视频网页地址
     * @param listener
     */
    public static void shareToWechatMoment(int imageType, String title
            , String imageUrlPath, Bitmap imageData
            , String url, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_VIDEO, title, null, url, null, imageUrlPath, null, null, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_VIDEO, title, null, url, imageUrlPath, null, null, null, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_VIDEO, title, null, url, null, null, imageData, null, null, listener);
                break;
        }
    }

    /**
     * 分享网页到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param url          网页地址
     * @param listener
     */
    public static void shareWebToWechatMoment(int imageType, String title
            , String imageUrlPath, Bitmap imageData
            , String url, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_WEBPAGE, title, null, url, null, imageUrlPath, null, null, null, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_WEBPAGE, title, null, url, imageUrlPath, null, null, null, null, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_WEBPAGE, title, null, url, null, null, imageData, null, null, listener);
                break;
        }
    }

    /**
     * 分享网页到微信
     *
     * @param imageType
     * @param title
     * @param imageUrlPath
     * @param imageData
     * @param filePath     文件本地路径
     * @param listener
     */
    public static void shareFileToWechatMoment(int imageType, String title
            , String imageUrlPath, Bitmap imageData
            , String filePath, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToWechat(Platform.SHARE_FILE, title, null, null, null, imageUrlPath, null, null, filePath, listener);
                break;
            case TYPE_NET:
                shareToWechat(Platform.SHARE_FILE, title, null, null, imageUrlPath, null, null, null, filePath, listener);
                break;
            case TYPE_BITMAP:
                shareToWechat(Platform.SHARE_FILE, title, null, null, null, null, imageData, null, filePath, listener);
                break;
        }
    }

    private static Platform.ShareParams initWechatShareParams(int shareType
            , String title, String text, String url
            , String imageUrl, String imagePath, Bitmap imageData
            , String musicUrl, String filePath) {
        Platform.ShareParams sp = new Platform.ShareParams();
        if (shareType != -1) {
            sp.setShareType(shareType);
        }
        if (StringUtils.isNotEmpty(title)) {
            sp.setTitle(title);
        }
        if (StringUtils.isNotEmpty(text)) {
            sp.setText(text);
        }
        if (StringUtils.isNotEmpty(url)) {
            sp.setUrl(url);
        }
        if (StringUtils.isNotEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        if (StringUtils.isNotEmpty(imagePath)) {
            sp.setImagePath(imagePath);
        }
        if (imageData != null) {
            sp.setImageData(imageData);
        }

        if (StringUtils.isNotEmpty(musicUrl)) {
            sp.setMusicUrl(musicUrl);
        }

        if (StringUtils.isNotEmpty(filePath)) {
            sp.setFilePath(filePath);
        }
        return sp;
    }


    public static void shareToWechatMoment(int shareType
            , String title, String text, String url
            , String imageUrl, String imagePath, Bitmap imageData
            , String musicUrl, String filePath, PlatformActionListener platformActionListener) {

        Platform.ShareParams sp = initWechatShareParams(shareType, title, text, url
                , imageUrl, imagePath, imageData, musicUrl, filePath);

        Platform moment = ShareSDK.getPlatform(WechatMoments.NAME);
        if (platformActionListener != null) {
            moment.setPlatformActionListener(platformActionListener);
        }
        moment.share(sp);
    }

    /**
     * ········································QQ好友·······································
     */

    /**
     * 分享图片
     *
     * @param imageType
     * @param imageUrlPath
     * @param listener
     */
    public static void shareToQQ(int imageType, String imageUrlPath, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToQQ(null, null, null, null, imageUrlPath, null, listener);
                break;
            case TYPE_NET:
                shareToQQ(null, null, null, imageUrlPath, null, null, listener);
                break;
        }
    }

    /**
     * 分享链接
     *
     * @param imageType
     * @param imageUrlPath
     * @param listener
     */
    public static void shareToQQ(int imageType, String title, String titleUrl, String text, String imageUrlPath, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToQQ(title, titleUrl, text, null, imageUrlPath, null, listener);
                break;
            case TYPE_NET:
                shareToQQ(title, titleUrl, text, imageUrlPath, null, null, listener);
                break;
        }
    }

    /**
     * 分享音频
     *
     * @param imageType
     * @param imageUrlPath
     * @param listener
     */
    public static void shareToQQ(int imageType, String title, String titleUrl, String text, String imageUrlPath, String musicUrl, PlatformActionListener listener) {
        switch (imageType) {
            case TYPE_LOCAL:
                shareToQQ(title, titleUrl, text, null, imageUrlPath, musicUrl, listener);
                break;
            case TYPE_NET:
                shareToQQ(title, titleUrl, text, imageUrlPath, null, musicUrl, listener);
                break;
        }
    }

    /**
     * 分享到QQ好友
     *
     * @param title
     * @param titleUrl
     * @param text
     * @param imageUrl
     * @param imagePath
     * @param musicUrl
     * @param platformActionListener
     */
    private static void shareToQQ(String title, String titleUrl
            , String text, String imageUrl, String imagePath, String musicUrl
            , PlatformActionListener platformActionListener) {

        Platform.ShareParams sp = initQQShareParams(title, titleUrl, text
                , imageUrl, imagePath, musicUrl);

        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if (platformActionListener != null) {
            qq.setPlatformActionListener(platformActionListener);
        }
        qq.share(sp);
    }

    private static Platform.ShareParams initQQShareParams(String title, String titleUrl
            , String text, String imageUrl, String imagePath, String musicUrl) {
        Platform.ShareParams sp = new Platform.ShareParams();
        if (StringUtils.isNotEmpty(title)) {
            sp.setTitle(title);
        }
        if (StringUtils.isNotEmpty(titleUrl)) {
            sp.setTitleUrl(titleUrl); // 标题的超链接
        }
        if (StringUtils.isNotEmpty(text)) {
            sp.setText(text);
        }
        if (StringUtils.isNotEmpty(imageUrl)) {
            sp.setImageUrl(imageUrl);
        }
        if (StringUtils.isNotEmpty(imagePath)) {
            sp.setImagePath(imagePath);
        }
        if (StringUtils.isNotEmpty(musicUrl)) {
            sp.setMusicUrl(musicUrl);
        }
        return sp;
    }

    public static void shareNativeImage(Context mContext, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        mContext.startActivity(Intent.createChooser(shareIntent, title));
    }

    public static void shareNativeText(Context mContext, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        Intent sendIntent = Intent.createChooser(intent, "分享");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(sendIntent);
    }
}
