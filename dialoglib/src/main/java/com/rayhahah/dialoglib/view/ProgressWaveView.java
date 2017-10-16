package com.rayhahah.dialoglib.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.rayhahah.dialoglib.R;
import com.rayhahah.dialoglib.utils.ColorUtil;

import java.text.DecimalFormat;

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
 * @time 2017/9/21
 * @tips 这个类是Object的子类
 * @fuction
 */
public class ProgressWaveView extends View {


    //控件长宽
    private int mRealHeight;
    private int mRealWidth;
    private int radius;//半径
    private int mTextColor = Color.parseColor("#ffffff");

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
    }

    public void setBeginColor(int beginColor) {
        this.beginColor = beginColor;
    }

    public void setSuccessColor(int successColor) {
        this.successColor = successColor;
    }

    public void setPauseColor(int pauseColor) {
        this.pauseColor = pauseColor;
    }

    public void setProgressTextSize(int progressTextSize) {
        this.progressTextSize = progressTextSize;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
    }

    //圆的颜色
    private int circleColor;
    //波浪颜色
    private int waveColor;
    //波浪起始颜色
    private int beginColor;
    //进度为100%时波浪的颜色
    private int successColor;
    //暂停时碧浪的颜色
    private int pauseColor;
    //进度文字大小
    private int progressTextSize;
    private float progress = 0;
    //波浪高度
    private int waveHeight;
    //波浪向右移动的距离
    private float waveMoveX = 0;
    //第一个波浪的贝塞尔曲线路径及所有相关点
    private Path wavePath;
    private Point p1;
    private Point pControl1;
    private Point p2;
    private Point pControl2;
    private Point p3;
    private Point pControl3;
    private Point p4;
    private Point pControl4;
    private Point p5;

    //第二个颜色较浅的波浪的贝塞尔曲线路径及所有相关点
    private Path waveShadowPath;
    private Point p1_shadow;
    private Point pControl1_shadow;
    private Point p2_shadow;
    private Point pControl2_shadow;
    private Point p3_shadow;
    private Point pControl3_shadow;
    private Point p4_shadow;
    private Point pControl4_shadow;
    private Point p5_shadow;

    //圆与深色波浪的路径交集
    private Path waveInCirclePath;
    //圆与浅色波浪的路径交集
    private Path waveShadowInCirclePath;

    public ProgressWaveView(Context context) {
        super(context);
        initAllPath();
        circleColor = Color.parseColor("#bababa");
        waveColor = Color.parseColor("#3F51B5");
        beginColor = waveColor;
        successColor = Color.parseColor("#3F51B5");
        progressTextSize = 80;
        pauseColor = Color.parseColor("#fa6c0e");
        initAllPaint();

    }

    public ProgressWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAllPath();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressWaveView);
        obtainTypedArray(array);
    }

    private void initAllPath() {
        wavePath = new Path();
        waveShadowPath = new Path();
        waveInCirclePath = new Path();
        waveShadowInCirclePath = new Path();
    }

    private void obtainTypedArray(TypedArray array) {

        circleColor = array.getColor(R.styleable.ProgressWaveView_circleColor, Color.parseColor("#bababa"));
        waveColor = array.getColor(R.styleable.ProgressWaveView_waveColor, Color.parseColor("#3F51B5"));
        beginColor = waveColor;
        successColor = array.getColor(R.styleable.ProgressWaveView_successColor, Color.parseColor("#3F51B5"));
        progressTextSize = array.getDimensionPixelSize(R.styleable.ProgressWaveView_progressTextSize, 80);
        pauseColor = array.getColor(R.styleable.ProgressWaveView_pauseColor, Color.parseColor("#fa6c0e"));

        initAllPaint();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startWavingAnim(1000);
            }
        }, 20);
    }


    /**
     * @return 当前进度值（0.0-1.0）
     */
    public float getProgress() {
        return progress;
    }

    /**
     * 设置当前进度值
     *
     * @param progress 前进度值
     */
    public void setProgress(float progress) {
        if (progress >= 1) {
            progress = 1;
        }
        if (progress <= 0) {
            progress = 0;
        }
        this.progress = progress;

        //进度进行时，暂停状态为 false
        isPaused = false;
        //根据进度改变波浪的颜色
        waveColor = ColorUtil.getCurrentColor(beginColor, successColor, progress);
        postInvalidate();
    }


    //当前的暂停状态，初始为 false
    private boolean isPaused = false;

    /**
     * 当前是否暂停
     *
     * @return
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * 暂停进度
     */
    public void onPause() {
        if (!isPaused) {
            isPaused = true;
            changeWaveColor(waveColor, pauseColor);
            postInvalidate();
        }
    }

    /**
     * 恢复进度
     */
    public void recover() {

        if (isPaused) {
            setProgress(this.progress);
        }
    }


    private Paint wavePaint;
    private Paint waveShadowPaint;
    private Paint circlePaint;
    private Paint textPaint;

    /**
     * 初始化所有画笔
     */
    private void initAllPaint() {

        if (wavePaint == null) {
            wavePaint = new Paint();
        }
        if (waveShadowPaint == null) {
            waveShadowPaint = new Paint();
        }
        if (circlePaint == null) {
            circlePaint = new Paint();
        }
        if (textPaint == null) {
            textPaint = new Paint();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(radius, radius, radius, circlePaint);
        drawWaveShadow(canvas);
        drawWave(canvas);
        drawProgressText(canvas);

    }


    /**
     * 绘制深色波浪
     *
     * @param canvas 画布
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void drawWave(Canvas canvas) {
        //根据进度获取相应增加的高度
        float addHeight = 2 * radius * progress;
        wavePaint.setColor(waveColor);
        wavePaint.setAntiAlias(true);
        wavePaint.setStrokeWidth(2);
        wavePaint.setStyle(Paint.Style.FILL);

        wavePath.reset();
        wavePath.moveTo(p1.x + waveMoveX, p1.y - addHeight);
        wavePath.quadTo(pControl1.x + waveMoveX, pControl1.y - addHeight, p2.x + waveMoveX, p2.y - addHeight);
        wavePath.quadTo(pControl2.x + waveMoveX, pControl2.y - addHeight, p3.x + waveMoveX, p3.y - addHeight);
        wavePath.quadTo(pControl3.x + waveMoveX, pControl3.y - addHeight, p4.x + waveMoveX, p4.y - addHeight);
        wavePath.quadTo(pControl4.x + waveMoveX, pControl4.y - addHeight, p5.x + waveMoveX, p5.y - addHeight);
        wavePath.lineTo(p5.x + waveMoveX, radius * 2);
        wavePath.lineTo(p1.x + waveMoveX, radius * 2);
        wavePath.lineTo(p1.x + waveMoveX, p1.y + addHeight);
        wavePath.close();

        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);

        waveInCirclePath.reset();
        waveInCirclePath.addCircle(radius, radius, radius, Path.Direction.CW);
        //取该圆与波浪路径的交集，形成波浪在圆内的效果
        waveInCirclePath.op(wavePath, Path.Op.INTERSECT);
        canvas.drawPath(waveInCirclePath, wavePaint);
    }

    /**
     * 绘制浅色波浪
     *
     * @param canvas 画布
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void drawWaveShadow(Canvas canvas) {

        waveShadowPath.reset();
        //float disY_move = 0;
        float addHeight = 2 * radius * progress;
        waveShadowPath.moveTo(p1_shadow.x + waveMoveX, p1_shadow.y - addHeight);
        waveShadowPath.quadTo(pControl1_shadow.x + waveMoveX, pControl1_shadow.y - addHeight, p2_shadow.x + waveMoveX, p2_shadow.y - addHeight);
        waveShadowPath.quadTo(pControl2_shadow.x + waveMoveX, pControl2_shadow.y - addHeight, p3_shadow.x + waveMoveX, p3_shadow.y - addHeight);
        waveShadowPath.quadTo(pControl3_shadow.x + waveMoveX, pControl3_shadow.y - addHeight, p4_shadow.x + waveMoveX, p4_shadow.y - addHeight);
        waveShadowPath.quadTo(pControl4_shadow.x + waveMoveX, pControl4_shadow.y - addHeight, p5_shadow.x + waveMoveX, p5_shadow.y - addHeight);
        waveShadowPath.lineTo(p5_shadow.x + waveMoveX, radius * 2 + addHeight);
        waveShadowPath.lineTo(p1_shadow.x + waveMoveX, radius * 2 + addHeight);
        waveShadowPath.lineTo(p1_shadow.x + waveMoveX, p1_shadow.y + radius * 2 + addHeight);
        waveShadowPath.close();


        waveShadowPaint.setColor(waveColor);
        waveShadowPaint.setAntiAlias(true);
        waveShadowPaint.setStrokeWidth(2);
        waveShadowPaint.setStyle(Paint.Style.FILL);
        waveShadowPaint.setAlpha(60);


        waveShadowInCirclePath.reset();
        waveShadowInCirclePath.addCircle(radius, radius, radius, Path.Direction.CW);
        //取该圆与波浪路径的交集，形成波浪在圆内的效果
        waveShadowInCirclePath.op(waveShadowPath, Path.Op.INTERSECT);
        canvas.drawPath(waveShadowInCirclePath, waveShadowPaint);

    }

    /**
     * 绘制进度文字
     *
     * @param canvas 画布
     */
    public void drawProgressText(Canvas canvas) {
        textPaint.setColor(mTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(progressTextSize);
        //四舍五入，保留小数点后一位
        DecimalFormat format = new DecimalFormat("##0.0");
        textPaint.setTextAlign(Paint.Align.CENTER);
        String progressText = format.format(progress * 100) + "%";
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        if (isPaused) {
            progressText = "已暂停";
        }
        canvas.drawText(progressText, radius, radius + fontMetrics.bottom, textPaint);

    }


    /**
     * 暂停状态下，循环改变波浪的颜色，产生颜色过渡效果
     *
     * @param fromColor 起点颜色
     * @param toColor   终点颜色
     */
    public void changeWaveColor(int fromColor, int toColor) {
        //用两个动画
        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor, fromColor);

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (isPaused) {
                    waveColor = (Integer) animator.getAnimatedValue();
                }
            }
        });
        //无限重复
        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimation.setDuration(2000);
        colorAnimation.start();

    }


    /**
     * 循环播放波浪动画
     *
     * @param duration 时长
     */
    public void startWavingAnim(final long duration) {
        ValueAnimator waveAnimator = ValueAnimator.ofFloat(0, 2 * radius);
        waveAnimator.setDuration(duration);
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //改变波浪的 x 值
                waveMoveX = (Float) animation.getAnimatedValue();
                postInvalidate();


            }

        });
        waveAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                waveMoveX = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        //无限重复
        waveAnimator.setRepeatCount(ValueAnimator.INFINITE);
        waveAnimator.start();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量控件的真实长宽
        mRealHeight = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

        int min = mRealHeight > mRealWidth ? mRealWidth : mRealHeight;
        radius = min / 2;
        //取两者最小值,并设置为正方形
        setMeasuredDimension(min, min);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initAllPathPoint(w, h);

    }


    /**
     * 初始化所有波浪贝塞尔曲线的点
     *
     * @param w 控件的宽度
     * @param h 控件的高度
     */
    private void initAllPathPoint(int w, int h) {

        waveHeight = h / 20;
        float initPositionY = h / 3;
        p1 = new Point(-w, (int) (h * 2 / 3 + initPositionY));
        pControl1 = new Point(-w * 3 / 4, (int) (h - h / 3 - waveHeight + initPositionY));
        p2 = new Point(-w / 2, (int) (h * 2 / 3 + initPositionY));
        pControl2 = new Point(-w / 4, (int) (h * 2 / 3 + waveHeight + initPositionY));
        p3 = new Point(0, (int) (h * 2 / 3 + initPositionY));

        pControl3 = new Point(w / 4, (int) (h - h / 3 - waveHeight + initPositionY));
        p4 = new Point(w / 2, (int) (h * 2 / 3 + initPositionY));
        pControl4 = new Point(w * 3 / 4, (int) (h * 2 / 3 + waveHeight + initPositionY));
        p5 = new Point(w, (int) (h * 2 / 3 + initPositionY));


        int dis = w / 7;
        int disY = h / 70;
        p1_shadow = new Point(-w + dis, (int) (h * 2 / 3 - disY + initPositionY));
        pControl1_shadow = new Point(-w * 3 / 4 + dis, (int) (h - h / 3 - waveHeight - disY + initPositionY));
        p2_shadow = new Point(-w / 2 + dis, (int) (h * 2 / 3 - disY + initPositionY));
        pControl2_shadow = new Point(-w / 4 + dis, (int) (h * 2 / 3 + waveHeight - disY + initPositionY));
        p3_shadow = new Point(dis, (int) (h * 2 / 3 - disY + initPositionY));

        pControl3_shadow = new Point(w / 4 + dis, (int) (h - h / 3 - waveHeight - disY + initPositionY));
        p4_shadow = new Point(w / 2 + dis, (int) (h * 2 / 3 - disY + initPositionY));
        pControl4_shadow = new Point(w * 3 / 4 + dis, (int) (h * 2 / 3 + waveHeight - disY + initPositionY));
        p5_shadow = new Point(w + dis, (int) (h * 2 / 3 - disY + initPositionY));


    }
}
