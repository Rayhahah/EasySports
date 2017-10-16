package com.rayhahah.easysports.utils;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * 点播或者直播工具类
 */

public class PlayManager {

    private Context mContext;
    private TXLivePlayer mLivePlayer;
    private TXCloudVideoView mPlayerView;
    private Bitmap mBitmap;

    /**
     * 视频类型
     */
    public enum VIDEO_TYPE {
        LIVE_RTMP,
        LIVE_FLV,
        VOD_FLV,
        VOD_HLS,
        VOD_MP4,
        LIVE_RTMP_ACC,
        LOCAL_VIDEO,
    }

    /**
     * 缓冲模式
     */
    public enum CacheMode {
        AUTO,
        SPEED,
        SMOOTH
    }

    public enum ScreenOrientation {
        PORTRAIT,
        LANDSCAPE
    }

    public enum RenderMode {
        FILL,
        AUTO_JUST
    }


    private PlayManager() {
    }

    /**
     * 初始化播放工具类
     */
    private PlayManager(PlayManager.Builder builder) {
        mContext = builder.mContext;
        mPlayerView = builder.mPlayerView;
        mLivePlayer = builder.mLivePlayer;
    }

    public static class Builder {
        private Context mContext;
        private TXLivePlayer mLivePlayer;
        private TXCloudVideoView mPlayerView;
        private TXLivePlayConfig mPlayConfig;

        private Builder() {
        }

        public Builder(Context context, TXCloudVideoView playerView) {
            mContext = context;
            mPlayerView = playerView;
            mLivePlayer = new TXLivePlayer(mContext);
            mLivePlayer.setPlayerView(playerView);
            mPlayConfig = new TXLivePlayConfig();
//            mLivePlayer.setConfig(mPlayConfig);
        }

        public Builder setListener(ITXLivePlayListener listener) {
            mLivePlayer.setPlayListener(listener);
            return this;
        }

        /**
         * 缓冲模式
         *
         * @param cacheMode 缓冲模式
         */
        public Builder setCacheMode(CacheMode cacheMode) {
            switch (cacheMode) {
                case AUTO:
                    //自动模式
                    mPlayConfig.setAutoAdjustCacheTime(true);
                    mPlayConfig.setMinAutoAdjustCacheTime(1);
                    mPlayConfig.setMaxAutoAdjustCacheTime(5);
                    break;
                case SPEED:
                    //极速模式
                    mPlayConfig.setAutoAdjustCacheTime(true);
                    mPlayConfig.setMinAutoAdjustCacheTime(1);
                    mPlayConfig.setMaxAutoAdjustCacheTime(1);
                    break;
                case SMOOTH:
                    //流畅模式
                    mPlayConfig.setAutoAdjustCacheTime(false);
                    mPlayConfig.setCacheTime(5);
                    break;
                default:
                    break;
            }
//            mLivePlayer.setConfig(mPlayConfig);
            return this;
        }

        /**
         * 平铺模式
         *
         * @param renderMode
         * @return
         */
        public Builder setRenderMode(RenderMode renderMode) {
            int mode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
            switch (renderMode) {
                case FILL:
                    mode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
                    break;
                case AUTO_JUST:
                    mode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
                    break;
            }
            mLivePlayer.setRenderMode(mode);
            return this;
        }

        public PlayManager build() {
            return new PlayManager(this);
        }


    }

    /**
     * 设置屏幕方向
     *
     * @param screenOrientation PORTRAIT,LANDSCAPE
     */
    public void setScreenOrientation(ScreenOrientation screenOrientation) {
        int orientation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        switch (screenOrientation) {
            case PORTRAIT:
                orientation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
                break;
            case LANDSCAPE:
                orientation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
                break;
        }
        mLivePlayer.setRenderRotation(orientation);
    }




    /**
     * 播放视频,传入地址和视频类型
     *
     * @param url        播放地址,直播流推荐传入FLV地址
     * @param video_type 视频类型,默认为直播流FLV
     *                   LIVE_RTMP   传入的URL为RTMP直播地址
     *                   LIVE_FLV    传入的URL为FLV直播地址
     *                   VOD_FLV    传入的URL为FLV点播地址
     *                   VOD_HLS    传入的URL为HLS(m3u8)点播地址
     *                   VOD_MP4    传入的URL为MP4点播地址
     *                   LIVE_RTMP_ACC    低延迟连麦链路直播地址（仅适合于连麦场景）
     *                   LOCAL_VIDEO    手机本地视频文件
     */
    public int play(String url, VIDEO_TYPE video_type) {
        int playType = 1;
        switch (video_type) {
            case LIVE_RTMP:
                playType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                break;
            case LIVE_FLV:
                playType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                break;
            case VOD_FLV:
                playType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                break;
            case VOD_HLS:
                playType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                break;
            case VOD_MP4:
                playType = TXLivePlayer.PLAY_TYPE_VOD_MP4;
                break;
            case LIVE_RTMP_ACC:
                playType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
                break;
            case LOCAL_VIDEO:
                playType = TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO;
                break;
        }
        return mLivePlayer.startPlay(url, playType); //推荐FLV
    }


    /**
     * 截图返回一个bitmap
     *
     * @return bitmap
     */
    public Bitmap takeSnapShot() {
        mLivePlayer.snapshot(new TXLivePlayer.ITXSnapshotListener() {
            @Override
            public void onSnapshot(Bitmap bitmap) {
                if (bitmap != null) {
                    mBitmap = bitmap;
                }
            }
        });
        return mBitmap;
    }


    /**
     * 是否正在播放
     */
    public boolean isPlaying() {
        return mLivePlayer.isPlaying();
    }

    /**
     * 继续播放
     */
    public void resume() {
        if (mLivePlayer == null) {
            throw new RuntimeException("TXLivePlayer 还未初始化");
        }
        mLivePlayer.resume();
    }

    /**
     * 暂停播放,画面保留在最后一帧
     */
    public void pause() {
        mLivePlayer.pause();
    }


    /**
     * 清除画面的最后一帧
     * @param clear 是否清除
     */
    public void clearLastFrame(boolean clear){
        mLivePlayer.stopPlay(clear);
    }

    /**
     * 销毁播放
     */
    public void destroy() {
        mLivePlayer.stopPlay(true); // true代表清除最后一帧画面
        mPlayerView.onDestroy();
    }


    /**
     * 调整进度,只对点播有效
     *
     * @param progress
     */
    public void setProgress(int progress) {
        mLivePlayer.seek(progress);
    }

    public void registerPhoneListener() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

    }


    //电话监听
    private PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (mLivePlayer != null) {
                        mLivePlayer.setMute(true);
                    }
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (mLivePlayer != null) {
                        mLivePlayer.setMute(true);
                    }
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mLivePlayer != null) {
                        mLivePlayer.setMute(false);
                    }
                    break;
            }
        }
    };


}
