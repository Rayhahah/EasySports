package com.rayhahah.easysports.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @Github https://github.com/Rayhahah
 * @time 2017/4/5
 * @fuction 小球滚动Loading动画
 * @usage
 */
public class BallRollView extends View {

    private int mViewWidth;
    private int mViewHeight;
    private int mCenterHeight;
    private int mBallCount = 7;
    private int mBallSize;
    private int mBallRadius;
    private int mBallOffset = 10;
    private ValueAnimator mValueAnimator;
    private int mDuration = 500;
    private float mAnimatedValue;
    private Path mBezierPathI;
    private Path mBezierPathII;
    private Paint mBallPaint;
    private int mOffSet = 150;
    private PathMeasure mPathMeasureI;
    private PathMeasure mPathMeasureII;
    private boolean mIsBezier = true;
    private float mBezierILength;
    private float mBezierIILength;
    private float[] mPos = new float[2];
    private float[] mTan = new float[2];
    private int mBezierHeight = 300;
    private ValueAnimator mValueAnimatorBezier;
    private PointF mBezierPoint;
    private Paint mBezierPaint;
    private int mTarRed = 255;
    private int mTarGreen = 0;
    private int mTarBlue = 0;
    private int[] mNRgb = new int[]{0, 0, 0};
    private int[] mARgb = new int[]{255, 0, 0};

    public BallRollView(Context context) {
        super(context);
    }

    public BallRollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BallRollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;
        mCenterHeight = mViewHeight / 2;

        mBallSize = (mViewWidth - mOffSet) / mBallCount;
        mBallRadius = mBallSize / 2 - mBallOffset;

        initPaint();

        initBezierPath();

        initTarColor();

        initValueAnimator();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsBezier) {
            mPathMeasureI.getPosTan(mAnimatedValue * mBezierILength, mPos, mTan);
        } else {
            mPathMeasureII.getPosTan(mAnimatedValue * mBezierIILength, mPos, mTan);
        }
        mBezierPaint.setColor(Color.rgb((int) (mARgb[0] - mTarRed * mAnimatedValue),
                (int) (mARgb[1] - mTarGreen * mAnimatedValue),
                (int) (mARgb[2] - mTarBlue * mAnimatedValue)));
//        mBezierPaint.setColor(Color.rgb(255, 0, 0));
        canvas.drawCircle(mPos[0], mPos[1], mBallRadius, mBezierPaint);

        canvas.save();
        canvas.translate(0 - mAnimatedValue * mBallSize, mCenterHeight);
        for (int i = 1; i < mBallCount; i++) {
            if (i == 1) {
                mBallPaint.setColor(Color.rgb((int) (mNRgb[0] + mTarRed * mAnimatedValue),
                        (int) (mNRgb[1] + mTarGreen * mAnimatedValue),
                        (int) (mNRgb[2] + mTarBlue * mAnimatedValue)));
            } else {
                mBallPaint.setColor(Color.rgb(mNRgb[0], mNRgb[1], mNRgb[2]));
            }
            canvas.drawCircle(mBallRadius + mBallOffset + i * mBallSize + mOffSet / 2, 0, mBallRadius, mBallPaint);
        }
        canvas.restore();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mBallPaint = new Paint();
        mBallPaint.setStyle(Paint.Style.FILL);
        mBallPaint.setColor(Color.rgb(mNRgb[0], mNRgb[1], mNRgb[2]));
        mBallPaint.setAntiAlias(true);

        mBezierPaint = new Paint();
        mBezierPaint.setStyle(Paint.Style.FILL);
        mBezierPaint.setColor(Color.rgb(mNRgb[0] + mTarRed,
                mNRgb[1] + mTarGreen,
                mNRgb[2] + mTarBlue));
        mBezierPaint.setAntiAlias(true);
    }

    /**
     * 初始化渐变颜色
     */
    private void initTarColor() {
        mTarRed = mARgb[0] - mNRgb[0];
        mTarGreen = mARgb[1] - mNRgb[1];
        mTarBlue = mARgb[2] - mNRgb[2];
    }


    /**
     * 初始化动画生成器
     */
    private void initValueAnimator() {
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofFloat(0, 1);
            mValueAnimator.setDuration(mDuration);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mAnimatedValue = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mValueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    mIsBezier = !mIsBezier;
                }
            });
            mValueAnimator.start();
        }
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        if (mValueAnimator != null && !mValueAnimator.isRunning()) {
            mValueAnimator.start();
        }
    }

    /**
     * 初始化贝塞尔曲线路径
     */
    private void initBezierPath() {
        mBezierPathI = new Path();
        mBezierPathI.moveTo((mBallSize + mOffSet) / 2, mCenterHeight);
        mBezierPathI.quadTo(mViewWidth / 2, mCenterHeight - mBezierHeight,
                (mBallCount - 1) * mBallSize + (mBallSize + mOffSet) / 2, mCenterHeight);

        mPathMeasureI = new PathMeasure();
        mPathMeasureI.setPath(mBezierPathI, false);
        mBezierILength = mPathMeasureI.getLength();


        mBezierPathII = new Path();
        mBezierPathII.moveTo((mBallSize + mOffSet) / 2, mCenterHeight);
        mBezierPathII.quadTo(mViewWidth / 2, mCenterHeight + mBezierHeight,
                (mBallCount - 1) * mBallSize + (mBallSize + mOffSet) / 2, mCenterHeight);

        mPathMeasureII = new PathMeasure();
        mPathMeasureII.setPath(mBezierPathII, false);
        mBezierIILength = mPathMeasureII.getLength();
    }

    /**
     * 设置小球渐变颜色
     * 只支持rgb通道，不支持argb
     *
     * @param normalColor 普通小球颜色
     * @param activeColor 移动小球颜色
     */
    public BallRollView setColor(int normalColor, int activeColor) {
        mNRgb = int2RGB(normalColor);
        mARgb = int2RGB(activeColor);
        initTarColor();
        postInvalidate();
        return this;
    }

    /**
     * 设置小球个数
     *
     * @param ballCount
     */
    public BallRollView setBallCount(int ballCount) {
        mBallCount = ballCount;
        return this;
    }

    /**
     * 设置一轮动画的时长
     *
     * @param duration
     */
    public BallRollView setDuration(int duration) {
        mDuration = duration;
        return this;
    }


    /**
     * 提取出颜色的rgb三色通道的值
     * 范围是0-255
     *
     * @param color
     * @return
     */
    private int[] int2RGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color & 0xff0000) >> 16;
        rgb[1] = (color & 0x00ff00) >> 8;
        rgb[2] = (color & 0x0000ff);
        return rgb;
    }
}
