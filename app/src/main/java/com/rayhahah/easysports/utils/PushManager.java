package com.rayhahah.easysports.utils;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Surface;

import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;


/**
 * 推流工具类
 */

public class PushManager {

    private Activity mContext;
    private TXLivePusher mLivePusher;//推流
    private RotationObserver mRotationObserver;//旋转监听
    private TXLivePushConfig mLivePushConfig;//推流配置
    private TXCloudVideoView mVideoView;//视频界面
    private ITXLivePushListener mListener;//监听
    private int mDrawableId;

    /**
     * 推流质量
     */
    public enum PushQuality {
        STANDARD,
        HIGH,
        SUPER
    }

    /**
     * 视频方向
     */
    public enum SCREEN_ORIENTATION {
        HOME_DOWN,
        HOME_LEFT,
        HOME_RIGHT,
        HOME_TOP
    }

    private PushManager() {
    }

    private PushManager(PushManager.Builder builder) {
        mContext = builder.mContext;
        mVideoView = builder.mVideoView;
        mLivePushConfig = builder.mLivePushConfig;
        mLivePusher = builder.mLivePusher;
        mListener = builder.mListener;
        mDrawableId = builder.mDrawableId;
    }


    public static class Builder {
        private Activity mContext;
        private TXLivePusher mLivePusher;//推流
        private TXLivePushConfig mLivePushConfig;//推流配置
        private TXCloudVideoView mVideoView;//视频界面
        private ITXLivePushListener mListener;
        private int mDrawableId;//暂停图片的Id

        public Builder(WeakReference<Activity> context, TXCloudVideoView videoView) {
            mContext = context.get();
            mVideoView = videoView;
            mLivePusher = new TXLivePusher(mContext);
            mLivePushConfig = new TXLivePushConfig();
            //默认硬件加速
            mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_AUTO);
            //默认自动对焦
            mLivePushConfig.setTouchFocus(false);
            mLivePusher.setConfig(mLivePushConfig);
//            mLivePusher.setRenderRotation(pushRotation);
        }

        /**
         * 开启相机预览
         */
        public Builder setCameraPreview() {
            mLivePusher.startCameraPreview(mVideoView);
            return this;
        }

        /**
         * 设置暂停时候的观众背景图
         *
         * @param resourceId 图片资源
         * @return
         */
        public Builder setPauseImg(int resourceId) {
            mDrawableId = resourceId;
            Bitmap bitmap = getBitmap(mContext, resourceId);
            mLivePushConfig.setPauseImg(bitmap);
            return this;
        }


        /**
         * 设置默认为前置摄像头进行拍摄
         */
        public Builder setDefaultFrontCamera(boolean front) {
            mLivePushConfig.setFrontCamera(front);
            return this;
        }

        /**
         * 是否启动硬件加速,默认为自动选择硬件加速,建议不手动设置
         *
         * @param open true为打开硬件加速,否为软件加速
         */
        public Builder setHardWareAccelerate(boolean open) {
            mLivePushConfig.setHardwareAcceleration(open ? TXLiveConstants.ENCODE_VIDEO_HARDWARE : TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
            return this;
        }

        /**
         * 水印图片
         *
         * @param drawableId 图片id
         */
        public Builder setWaterMarker(int drawableId) {
            mLivePushConfig.setWatermark(BitmapFactory.decodeResource(mContext.getResources(), drawableId), 10, 10);
            return this;
        }

        /**
         * true开启手动对焦
         * false 关闭手动对焦,使用自动对焦
         * 默认为自动对焦
         */
        public Builder setTouchFocusMode(boolean touchFocus) {
            mLivePushConfig.setTouchFocus(touchFocus);
//        mLivePusher.setConfig(mLivePushConfig);
            return this;
        }

        /**
         * 监听
         *
         * @param onPushListener
         */
        public Builder setPushListener(final OnPushListener onPushListener) {
            mListener = new ITXLivePushListener() {
                @Override
                public void onPushEvent(int event, Bundle bundle) {

                    if (event == TXLiveConstants.PUSH_EVT_CONNECT_SUCC) {
                        onPushListener.pushSuccess(bundle.getString(TXLiveConstants.EVT_DESCRIPTION));
                    }

                    if (event < 0) {
                        //回调错误
                        onPushListener.onPushFail(bundle.getString(TXLiveConstants.EVT_DESCRIPTION));
                        if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
                            //硬件加速失败,进行软解
                            mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
                            mLivePusher.setConfig(mLivePushConfig);
                        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
                            onPushListener.networkUnstable("当前上行网络质量很差，观众端已经出现了卡顿");
                        }
                    }
                }

                @Override
                public void onNetStatus(Bundle bundle) {

                }
            };
            mLivePusher.setPushListener(mListener);
            return this;
        }

        /**
         * 设置默认的监听回调,没有封装的回调监听
         */
        public Builder setOriginListener(ITXLivePushListener listener) {
            mListener = listener;
            mLivePusher.setPushListener(listener);
            return this;
        }

        /**
         * @param pushQuality      推流清晰度 ec:PushManager.PushQuality.HIGH
         * @param adjustBitrate    是否开启 Qos 流量控制，开启后SDK 会根据主播上行网络的好坏自动调整视频码率。相应的代价就是，主播如果网络不好，画面会很模糊且有很多马赛克。
         * @param adjustResolution 是否允许动态分辨率，开启后 SDK 会根据当前的视频码率选择相匹配的分辨率，这样能获得更好的清晰度。相应的代价就是，动态分辨率的直播流所录制下来的文件，在很多播放器上会有兼容性问题。
         */
        public Builder setQuality(PushQuality pushQuality, boolean adjustBitrate, boolean adjustResolution) {
            int quality = TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION;

            switch (pushQuality) {
                case STANDARD:
                    quality = TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION;
                    break;
                case HIGH:
                    quality = TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION;
                    break;
                case SUPER:
                    quality = TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION;
                    break;
                default:
                    break;
            }
            mLivePusher.setVideoQuality(quality, adjustBitrate, adjustResolution);
            return this;
        }


        /**
         * 设置的直播者的屏幕的显示方向
         *
         * @param orientation SCREEN_ORIENTATION 默认为home键在下
         */
        public Builder setScreenOrientation(SCREEN_ORIENTATION orientation) {
            int pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
            switch (orientation) {
                case HOME_DOWN:
                    pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                    break;
                case HOME_LEFT:
                    pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                    break;
                case HOME_RIGHT:
                    pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                    break;
                case HOME_TOP:
                    pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_UP;
                    break;
                default:
                    break;
            }
            mLivePusher.setRenderRotation(pushRotation);
            return this;
        }

        /**
         * 设置观看者的屏幕方向
         */
        public Builder setWatcherScreen(SCREEN_ORIENTATION orientation) {
            int rotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
            switch (orientation) {
                case HOME_DOWN:
                    rotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                    break;
                case HOME_LEFT:
                    rotation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                    break;
                case HOME_RIGHT:
                    rotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                    break;
                case HOME_TOP:
                    rotation = TXLiveConstants.VIDEO_ANGLE_HOME_UP;
                    break;
                default:
                    break;
            }
            mLivePushConfig.setHomeOrientation(rotation);
            return this;
        }

        public PushManager build() {
            return new PushManager(this);
        }
    }

    private static Bitmap getBitmap(Activity context, int resourceId) {
        TypedValue value = new TypedValue();
        context.getResources().openRawResource(resourceId, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(context.getResources(), resourceId, opts);
    }


    /**
     * 在onResume调用
     */
    public void onResume() {
        mVideoView.onResume();
        mLivePusher.resumePusher();
    }

    /**
     * 在onStop调用
     */
    public void onStop() {
        mVideoView.onPause();
        mLivePusher.pausePusher();

    }


    /**
     * 切换摄像头
     */
    public void switchCamera() {
        mLivePusher.switchCamera();
    }

    /**
     * 打开闪光灯
     *
     * @param open 是否打开
     * @return 打开成功与否
     */
    public boolean openFlash(boolean open) {
        //mFlashTurnOn为true表示打开，否则表示关闭
        return mLivePusher.turnOnFlashLight(open);
    }




    /**
     * 开启推流
     *
     * @param url 推流地址
     */
    public void startPush(String url) {
        mLivePusher.startPusher(url);
        //防止暂停推流后重新开始的listener为空
        mLivePusher.setPushListener(mListener);
        //防止暂停推流后重新开始的暂停图片为空
        mLivePushConfig.setPauseImg(getBitmap(mContext, mDrawableId));
    }

    /**
     * 是否正在推流
     *
     * @return
     */
    public boolean isPushing() {
        return mLivePusher.isPushing();
    }

    /**
     * 切换摄像头预览
     *
     * @param open
     */
    public void toggleCameraPreview(boolean open) {
        if (open) {
            mLivePusher.startCameraPreview(mVideoView);
        } else {
            mLivePusher.stopCameraPreview(true); //停止摄像头预览
        }
    }

    /**
     * 结束推流
     */
    public void stopRtmpPublish() {
        if (mLivePusher != null) {
            mLivePusher.stopCameraPreview(true); //停止摄像头预览
            mLivePusher.stopPusher();            //停止推流
            mLivePusher.stopScreenCapture();//停止录屏
            mLivePusher.setPushListener(null);   //解绑 listener
            mLivePushConfig.setPauseImg(null);
        }
    }

    /**
     * 获取推流对象
     *
     * @return mLivePusher
     */
    public TXLivePusher getLivePusher() {
        return mLivePusher;
    }

    /**
     * 获取推流配置
     *
     * @return mLivePushConfig
     */
    public TXLivePushConfig getPushConfig() {
        return mLivePushConfig;
    }


    /**
     * 初始化电话状态,对用户电话状态进行监听
     */
    public void initPhoneState() {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(stateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }


    //监听电话状态
    private final PhoneStateListener stateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (mLivePusher != null) {
                        mLivePusher.pausePusher();
                    }
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (mLivePusher != null) {
                        mLivePusher.pausePusher();
                    }
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mLivePusher != null) {
                        mLivePusher.resumePusher();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 开始监听旋转状态
     */
    public void registerRotation() {
        mRotationObserver = new RotationObserver(new Handler());
        mRotationObserver.startObserver();
    }

    /**
     * 结束监听旋转状态
     */
    public void unregisterRotation() {
        if (mRotationObserver != null) {
            mRotationObserver.stopObserver();
        }
    }


    //观察屏幕旋转设置变化，类似于注册动态广播监听变化机制
    private class RotationObserver extends ContentObserver {
        ContentResolver mResolver;

        public RotationObserver(Handler handler) {
            super(handler);
            mResolver = mContext.getContentResolver();
        }

        //屏幕旋转设置改变时调用
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //更新按钮状态
            if (isActivityCanRotation()) {
//                mBtnOrientation.setVisibility(View.GONE);
                onActivityRotation();
            } else {
                mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
                mLivePusher.setRenderRotation(0);
                mLivePusher.setConfig(mLivePushConfig);
            }

        }

        public void startObserver() {
            mResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false, this);
        }

        public void stopObserver() {
            mResolver.unregisterContentObserver(this);
        }
    }

    // 判断自动旋转是否打开
    private boolean isActivityCanRotation() {
        int flag = Settings.System.getInt(mContext.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        return flag != 0;
    }

    /**
     * 自动旋转打开，Activity随手机方向旋转之后，需要改变推流方向
     */
    public void onActivityRotation() {
        // 自动旋转打开，Activity随手机方向旋转之后，需要改变推流方向
        int mobileRotation = mContext.getWindowManager().getDefaultDisplay().getRotation();
        int pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        switch (mobileRotation) {
            case Surface.ROTATION_0:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
                break;
            case Surface.ROTATION_90:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
                break;
            case Surface.ROTATION_270:
                pushRotation = TXLiveConstants.VIDEO_ANGLE_HOME_LEFT;
                break;
            default:
                break;
        }
        mLivePusher.setRenderRotation(0); //因为activity也旋转了，本地渲染相对正方向的角度为0。
        mLivePushConfig.setHomeOrientation(pushRotation);
        mLivePusher.setConfig(mLivePushConfig);
    }


    //监听接口
    public interface OnPushListener {
        void pushSuccess(String reason);

        void onPushFail(String reason);

        void networkUnstable(String netWorkUnStable);
    }
}
