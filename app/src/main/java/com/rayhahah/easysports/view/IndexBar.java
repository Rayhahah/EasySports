package com.rayhahah.easysports.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.rayhahah.easysports.R;
import com.rayhahah.rbase.utils.base.ConvertUtils;

/**
 * Created by a on 2017/6/29.
 */

public class IndexBar extends View {

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Paint mNormalPaint;
    private Paint mActivePaint;
    private int mActivrColor = Color.RED;
    private int mNormalColor = Color.BLACK;
    private int mTextSize = ConvertUtils.sp2px(16);
    private int mWidth;
    private int mHeight;
    private float mSingleHeight;
    private int lastIndex;
    private boolean isFirst = true;
    private Scroller mScroller;

    public IndexBar(Context context) {
        super(context, null);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initAttr(context, attrs, 0);
        initPaint();
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    public void scrollBy(@Px int x, @Px int y) {
        super.scrollBy(x, y);
    }

    @Override
    public void scrollTo(@Px int x, @Px int y) {
        super.scrollTo(x, y);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndexBar, defStyleAttr, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.IndexBar_normalTextColor:
                    mNormalColor = ta.getColor(attr, mNormalColor);
                    break;
                case R.styleable.IndexBar_normalTextSize:
                    mTextSize = ta.getDimensionPixelSize(attr, mTextSize);
                    break;
                case R.styleable.IndexBar_activeTextColor:
                    mActivrColor = ta.getColor(attr, mActivrColor);
                    break;
                case R.styleable.IndexBar_activeTextSize:
                    mTextSize = ta.getDimensionPixelSize(attr, mTextSize);
                    break;
                default:
                    break;
            }
        }
        ta.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mNormalPaint = new Paint();
        mNormalPaint.setAntiAlias(true);
        mNormalPaint.setColor(mNormalColor);
        mNormalPaint.setTextSize(mTextSize);
        //设置文本的起点是文字边框底边的中心
        mNormalPaint.setTextAlign(Paint.Align.CENTER);

        mActivePaint = new Paint();
        mActivePaint.setAntiAlias(true);
        mActivePaint.setTextSize(mTextSize);
        mActivePaint.setColor(mActivrColor);
        //设置文本的起点是文字边框底边的中心
        mActivePaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirst) {
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            mSingleHeight = mHeight * 1f / indexArr.length;
            isFirst = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            float x = mWidth / 2;
            float y = mSingleHeight / 2 + getTextHeight(indexArr[i]) / 2 + i * mSingleHeight;
            if (lastIndex == i) {
                canvas.drawText(indexArr[i], x, y, mActivePaint);
//                canvas.drawCircle(x, y - getTextHeight(indexArr[i]) / 2, fontsize / 2 + 4, paint);
            } else {
                canvas.drawText(indexArr[i], x, y, mNormalPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getIndex(event);
                break;
            case MotionEvent.ACTION_MOVE:
                getIndex(event);
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        postInvalidate();
        return true;
    }

    public void setIndexChange(int index) {
        lastIndex = index;
        postInvalidate();
    }

    private void getIndex(MotionEvent event) {
        float y = event.getY();
        int index = (int) (y / mSingleHeight);
        if (lastIndex != index) {
            if (index >= 0 && index < indexArr.length) {
                if (mListner != null) {
                    mListner.onIndexChanged(index, indexArr[index]);
                }
            }
        }
        lastIndex = index;
    }

    /**
     * 获取文本的高度
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {
        //获取文本的高度
        Rect bounds = new Rect();
        if (text != null) {
            mNormalPaint.getTextBounds(text, 0, text.length(), bounds);
        }

        return bounds.height();
    }

    public String[] getIndexArr() {
        return indexArr;
    }

    private OnTouchIndexListner mListner;

    public void setOnTouchIndexListner(OnTouchIndexListner listner) {
        mListner = listner;
    }

    public interface OnTouchIndexListner {
        void onIndexChanged(int index, String letter);
    }


}
