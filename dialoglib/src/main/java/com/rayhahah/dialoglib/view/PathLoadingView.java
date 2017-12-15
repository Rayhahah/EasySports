package com.rayhahah.dialoglib.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @Github https://github.com/Rayhahah
 * @time 2017/4/3
 * @fuction 原生实现路径绘制动画
 * @use
 */
public class PathLoadingView extends View implements Animator.AnimatorListener {
    private Paint mPaint;
    private Path mCirclePath;
    private Path mDstPath;
    private PathMeasure mCirclePathMeasure;
    private float mCircleLength;
    private ValueAnimator mCircleValueAnimator;
    private float mAnimatedValue;
    private boolean mIsWindow = true;
    private boolean mIsArrow = false;
    private float[] mPos = new float[2];
    private float[] mTan = new float[2];

    private boolean isStop = false;
    private int mColor = Color.BLACK;
    private int mStrokeWidth = 5;
    private int mRadius = 100;
    private int mDuration = 2000;
    private AnimListner mAnimListner;
    private Path mOkPath;
    private PathMeasure mOkPathMeasure;
    private float mOkLength;
    private boolean isCircle = true;
    private ValueAnimator mOkValueAnimator;
    private int mOkDuration = 750;
    private Path mXPath;
    private PathMeasure mXPathMeasure;
    private float mXLength;
    private boolean isOk = true;
    private boolean isX = false;
    private Path mXPathI;
    private PathMeasure mXIPathMeasure;
    private float mXILength;
    private Path mDstPathI;
    private ValueAnimator mXValueAnimator;
    private long mXDuration = 1500;
    private ValueAnimator mXRotateVa;

    public PathLoadingView(Context context) {
        this(context, null);
//        initParams();
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
//        initParams();
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams();
    }

    public PathLoadingView setStoppedListner(AnimListner listner) {
        mAnimListner = listner;
        invalidate();
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //必须要有lineTo（0,0）才可以实现路径的完整绘制
        mDstPath.reset();
        mDstPath.lineTo(0, 0);
        mDstPathI.reset();
        mDstPathI.lineTo(0, 0);
        if (isCircle) {
            float stopD = mAnimatedValue * mCircleLength;
            float startD = 0;
            if (mIsWindow) {
                //通过设置其实位置的变化来实现Window加载风格
                startD = (float) (stopD - ((0.5 - Math.abs(mAnimatedValue - 0.5)) * mCircleLength));
                if (isStop && Math.abs(stopD - startD) < 3 && mCircleValueAnimator.isRunning()) {
                    isCircle = false;
                    mCircleValueAnimator.cancel();
                    if (isOk) {
                        mOkValueAnimator.start();
                    } else {
                        mXValueAnimator.start();
                    }
                }
            }
            //获取当前进度的路径，同时赋值给传入的mDstPath
            mCirclePathMeasure.getSegment(startD, stopD, mDstPath, true);
            canvas.save();
            int widthCenter = this.getWidth() / 2;
            int heightCenter = getHeight() / 2;
            canvas.rotate(180, widthCenter, heightCenter);
            canvas.translate(widthCenter, heightCenter);
            canvas.drawPath(mDstPath, mPaint);
            canvas.restore();
        } else if (isOk) {
            float stopD = mAnimatedValue * mOkLength;
            float startD = 0;
            if (isStop && mAnimatedValue >= 0.98 && mOkValueAnimator.isRunning()) {
                mOkValueAnimator.cancel();
                ValueAnimator va = ObjectAnimator.ofFloat(this, "rotation", 0, 25, 0, -25, 0);
                va.setRepeatCount(0);
                va.setInterpolator(new OvershootInterpolator());
                va.setDuration(1000);
                va.start();
                va.addListener(this);
            }
            //获取当前进度的路径，同时赋值给传入的mDstPath
            mOkPathMeasure.getSegment(startD, stopD, mDstPath, true);
            canvas.save();
            int widthCenter = this.getWidth() / 2;
            int heightCenter = getHeight() / 2;
            canvas.translate(widthCenter - mRadius, heightCenter + mRadius / 4);
            canvas.drawPath(mDstPath, mPaint);
            canvas.restore();
        } else if (isX) {
            float stopD = mAnimatedValue * mXLength;
            float startD = 0;
            float stopDI = mAnimatedValue * mXILength;
            float startDI = 0;
            if (isStop && mAnimatedValue >= 0.98 && mXValueAnimator.isRunning()) {
                mXValueAnimator.cancel();
            } else if (mAnimatedValue > 0.4) {
                if (mXRotateVa == null) {
                    mXRotateVa = ObjectAnimator.ofFloat(this, "rotation", 0, 135);
                    mXRotateVa.setRepeatCount(0);
                    mXRotateVa.setInterpolator(new BounceInterpolator());
                    mXRotateVa.setDuration(2000);
                    mXRotateVa.start();
                    mXRotateVa.addListener(this);
                }
            }
            //获取当前进度的路径，同时赋值给传入的mDstPath
            mXPathMeasure.getSegment(startD, stopD, mDstPath, false);
            mXIPathMeasure.getSegment(startDI, stopDI, mDstPathI, false);
            canvas.save();
            int widthCenter = this.getWidth() / 2;
            int heightCenter = getHeight() / 2;
            canvas.translate(widthCenter, heightCenter);
            canvas.drawPath(mDstPath, mPaint);
            canvas.drawPath(mDstPathI, mPaint);
            canvas.restore();
        }

        if (mIsArrow) {
            //mPos是当前路径点的坐标
            //mTan通过下面公式可以得到当前点的切线角度
            mCirclePathMeasure.getPosTan(mAnimatedValue * mCircleLength, mPos, mTan);
            float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
            canvas.save();
            canvas.translate(300, 300);
            canvas.drawCircle(mPos[0], mPos[1], 10, mPaint);
            canvas.rotate(degree);
            //绘制切线
            canvas.drawLine(0, -200, 200, -200, mPaint);
            canvas.restore();

        }
    }

    public void init(Builder builder) {
        mRadius = builder.getRadius();
        mDuration = builder.getDuration();
        mColor = builder.getColor();
        mStrokeWidth = builder.getStrokeWidth();
        initParams();
    }

    private void initParams() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        /**
         * 线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT。
         */
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        /**
         *设置拐角的形状：MITER 尖角、 BEVEL 平角和 ROUND 圆角。默认为 MITER。
         */
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setColor(mColor);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));
        //CornerPathEffect 圆角笔刷
        //DashPathEffect   可以使用DashPathEffect来创建一个虚线的轮廓(短横线/小圆点)，而不是使用实线。你还可以指定任意的虚/实线段的重复模式。
        mPaint.setPathEffect(new CornerPathEffect(10));//画笔笔刷
        //完整的圆的路径
        mCirclePath = new Path();
        //打勾路径
        mOkPath = new Path();
        //打叉路径
        mXPath = new Path();
        mXPathI = new Path();
        //路径绘制每段截取出来的路径
        mDstPath = new Path();
        mDstPathI = new Path();

        mXPath.lineTo(0, -mRadius);
        mXPath.lineTo(0, 0);
        mXPath.lineTo(mRadius, 0);

        mXPathI.lineTo(0, mRadius);
        mXPathI.lineTo(0, 0);
        mXPathI.lineTo(-mRadius, 0);

        mXPathMeasure = new PathMeasure();
        mXPathMeasure.setPath(mXPath, false);
        mXLength = mXPathMeasure.getLength();

        mXIPathMeasure = new PathMeasure();
        mXIPathMeasure.setPath(mXPathI, false);
        mXILength = mXIPathMeasure.getLength();

        mOkPath.lineTo(0, 0);
        mOkPath.lineTo(mRadius * 4 / 5, mRadius * 4 / 5);
        mOkPath.lineTo(mRadius + mRadius, -mRadius * 4 / 5);

        mOkPathMeasure = new PathMeasure();
        mOkPathMeasure.setPath(mOkPath, false);
        mOkLength = mOkPathMeasure.getLength();

        mCirclePath.addCircle(0, 0, mRadius, Path.Direction.CW);
        //路径测量类
        mCirclePathMeasure = new PathMeasure();
        //测量路径
        mCirclePathMeasure.setPath(mCirclePath, true);
        //获取被测量路径的总长度
        mCircleLength = mCirclePathMeasure.getLength();


        mOkValueAnimator = ValueAnimator.ofFloat(0, 1);
        mOkValueAnimator.setDuration(mOkDuration);
        mOkValueAnimator.setRepeatCount(0);
        mOkValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mOkValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取从0-1的变化值
                mAnimatedValue = (float) animation.getAnimatedValue();
                //不断刷新绘图，实现路径绘制
                invalidate();
            }
        });

        mXValueAnimator = ValueAnimator.ofFloat(0, 1);
        mXValueAnimator.setDuration(mXDuration);
        mXValueAnimator.setRepeatCount(0);
        mXValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mXValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取从0-1的变化值
                mAnimatedValue = (float) animation.getAnimatedValue();
                //不断刷新绘图，实现路径绘制
                invalidate();
            }
        });

        mCircleValueAnimator = ValueAnimator.ofFloat(0, 1);
        mCircleValueAnimator.setDuration(mDuration);
        mCircleValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mCircleValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mCircleValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取从0-1的变化值
                mAnimatedValue = (float) animation.getAnimatedValue();
                //不断刷新绘图，实现路径绘制
                invalidate();
            }
        });
    }

    public void startAnim() {
        if (mCircleValueAnimator != null) {
            mCircleValueAnimator.start();
        }
    }

    public void pauseAnim() {
        if (mCircleValueAnimator != null
                && mCircleValueAnimator.isStarted()
                && mCircleValueAnimator.isRunning()) {
            mCircleValueAnimator.pause();
        }
    }

    public void stopAnim(boolean isExitOk) {
        if (isExitOk) {
            isOk = true;
            isX = false;
        } else {
            isOk = false;
            isX = true;
        }
        isStop = true;
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (mAnimListner != null) {
            mAnimListner.onStopped();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public interface AnimListner {
        void onStopped();
    }


    public static class Builder {

        private int color;
        private int duration;
        private int radius;
        private int strokeWidth;
        private Context mContext;

        public Builder() {
            mContext = null;
            color = Color.WHITE;
            duration = 2000;
            strokeWidth = 5;
            radius = 100;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public int getColor() {
            return color;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public int getDuration() {
            return duration;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public int getRadius() {
            return radius;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public int getStrokeWidth() {
            return strokeWidth;
        }

        public Builder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }
    }

    public PathLoadingView setWindow(boolean window) {
        mIsWindow = window;
        invalidate();
        return this;
    }

    public PathLoadingView setArrow(boolean arrow) {
        mIsArrow = arrow;
        invalidate();
        return this;
    }
}
