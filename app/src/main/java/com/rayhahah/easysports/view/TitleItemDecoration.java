package com.rayhahah.easysports.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.rayhahah.rbase.utils.base.ConvertUtils;
import com.rayhahah.rbase.utils.base.ImageUtils;
import com.rayhahah.rbase.utils.base.StringUtils;

/**
 * Created by a on 2017/6/6.
 */

public class TitleItemDecoration extends RecyclerView.ItemDecoration {

    private int mActiveColor;
    private int mTextColor;
    private int mGravity;
    private Paint paint;
    private TextPaint mTextPaint;
    private int alginBottom;
    private int topGap;
    private DecorationCallback mCallback;
    private Paint.FontMetrics fontMetrics;

    public final static int GRAVITY_LEFT = 0;
    public final static int GRAVITY_MIDDLE = 1;
    public final static int GRAVITY_RIGHT = 2;
    private TitleTextCallback mTextCallback;
    private TitleViewCallback mViewCallback;
    private Activity mActivity;

    public TitleItemDecoration(Context context
            , int textColor, int bgColor, int activeColor
            , int gravity, DecorationCallback callback, TitleTextCallback textCallback) {
        Resources res = context.getResources();

        mCallback = callback;
        mTextCallback = textCallback;

        paint = new Paint();
        paint.setColor(bgColor);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(ConvertUtils.sp2px(20));
        mTextPaint.setColor(textColor);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mTextColor = textColor;
        mActiveColor = activeColor;

        fontMetrics = new Paint.FontMetrics();

        topGap = res.getDimensionPixelSize(com.rayhahah.rbase.R.dimen.dp_30);
        alginBottom = res.getDimensionPixelSize(com.rayhahah.rbase.R.dimen.dp_30);
        mGravity = gravity;
    }

    public TitleItemDecoration(Activity activity
            , DecorationCallback callback, TitleViewCallback viewCallback) {
        mViewCallback = viewCallback;
        mActivity = activity;
        mCallback = callback;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String groupId = mCallback.getGroupId(pos);
        if (groupId == null) {
            return;
        }
        //只有同一组第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            if (mViewCallback == null) {
                outRect.top = topGap;
                if ("".equals(mTextCallback.getGroupFirstLine(pos))) {
                    outRect.top = 0;
                }
            } else {
                View v = mViewCallback.getGroupFirstView(pos);
                v = ImageUtils.measureView(mActivity, v);
                outRect.top = v.getHeight();
                if (mViewCallback.getGroupFirstView(pos) == null) {
                    outRect.top = 0;
                }
            }


//            if (pos < mData.size()
//                    && mData.get(pos).getSectionData() == null) {
//                outRect.top = 0;
//            }
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);
            String groupId = mCallback.getGroupId(pos);
            if (null == groupId) {
                return;
            }

            if (mViewCallback == null) {
                Rect bounds = new Rect();
                String textLine = mTextCallback.getGroupFirstLine(pos);
                mTextPaint.getTextBounds(textLine, 0, textLine.length(), bounds);

                if (StringUtils.isEmpty(textLine)) {
//                float top = view.getTop();
//                float bottom = view.getTop();
//                c.drawRect(left, top, right, bottom, paint);
                    return;
                }
                drawText(c, parent, left, right, view, pos, textLine, bounds);
            } else {
//                int height = mTarView.getMeasuredHeight();
                View v = mViewCallback.getGroupFirstView(pos);
                v = ImageUtils.measureView(mActivity, v);
                int height = v.getMeasuredHeight();
                int top = view.getTop();
                Bitmap bitmap = ConvertUtils.view2Bitmap(v);
                Drawable drawable = ConvertUtils.bitmap2Drawable(mActivity.getResources(), bitmap);
                drawable.setBounds(left, top - height - height, right, top);
                drawable.draw(c);
            }
        }
    }

    private void drawText(Canvas c, RecyclerView parent, int left, int right, View view, int pos, String textLine, Rect bounds) {
        if (textLine.equals(mTextCallback.getActiveGroup())) {
            mTextPaint.setColor(mActiveColor);
        } else {
            mTextPaint.setColor(mTextColor);
        }

        if (pos == 0 || isFirstInGroup(pos)) {
            float top = view.getTop() - topGap;
            float bottom = view.getTop();
            //绘制悬浮栏
            c.drawRect(left, top - topGap, right, bottom, paint);
            //绘制文本
            switch (mGravity) {
                case GRAVITY_LEFT:
//                    c.drawText(textLine, parent.getPaddingRight() + 20, bottom, mTextPaint);
                    break;
                case GRAVITY_MIDDLE:
//                    c.drawText(textLine, right / 2 - bounds.width() / 2, bottom, mTextPaint);
                    break;
                case GRAVITY_RIGHT:
//                    c.drawText(textLine, right - bounds.width(), bottom, mTextPaint);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = mCallback.getGroupId(pos);
            if (null == groupId || groupId.equals(preGroupId)) {
                continue;
            }

            if (mViewCallback == null) {
                String firstLine = mTextCallback.getGroupFirstLine(pos);
                drawTextOver(c, parent, itemCount, left, right, groupId, view, pos, firstLine);
            } else {

                int bottom = view.getBottom();
                View v = mViewCallback.getGroupFirstView(pos);
                v = ImageUtils.measureView(mActivity, v);
                int height = v.getMeasuredHeight();
                int top = Math.max(height, view.getTop());

                //下一个和当前不一样移动当前
                if (pos + 1 < itemCount) {
                    String nextGroupId = mCallback.getGroupId(pos + 1);
                    //组内最后一个view进入header
                    if (!nextGroupId.equals(groupId) && bottom < top) {
                        top = bottom;
                    }
                }
                Bitmap bitmap = ConvertUtils.view2Bitmap(v);
                Drawable drawable = ConvertUtils.bitmap2Drawable(mActivity.getResources(), bitmap);
                drawable.setBounds(left, top - height, right, top);
                drawable.draw(c);
            }
        }
    }

    private void drawTextOver(Canvas c, RecyclerView parent, int itemCount, int left, int right, String groupId, View view, int pos, String firstLine) {
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(firstLine, 0, firstLine.length(), bounds);

        if (StringUtils.isEmpty(firstLine)) {
            return;
        }

        if (firstLine.equals(mTextCallback.getActiveGroup())) {
            mTextPaint.setColor(mActiveColor);
        } else {
            mTextPaint.setColor(mTextColor);
        }

        int bottom = view.getBottom();

        int textY = Math.max(topGap, view.getTop());

        //下一个和当前不一样移动当前
        if (pos + 1 < itemCount) {
            String nextGroupId = mCallback.getGroupId(pos + 1);
            //组内最后一个view进入header
            if (nextGroupId != null) {
                if (!nextGroupId.equals(groupId) && bottom < textY) {
                    textY = bottom;
                }
            }
        }
        //textY - topGap决定了悬浮栏绘制的高度和位置
        c.drawRect(left, textY - topGap, right, textY, paint);
        //left+2*alignBottom 决定了文本往左偏移的多少（加-->向左移）
        //textY-alignBottom  决定了文本往右偏移的多少  (减-->向上移)
        switch (mGravity) {
            case GRAVITY_LEFT:
                c.drawText(firstLine, parent.getPaddingRight() + 20
                        , textY - alginBottom + bounds.height() / 2 + topGap / 2
                        , mTextPaint);
                break;
            case GRAVITY_MIDDLE:
                c.drawText(firstLine, right / 2 - bounds.width() / 2
                        , textY - alginBottom + bounds.height() / 2 + topGap / 2
                        , mTextPaint);
                break;
            case GRAVITY_RIGHT:
                c.drawText(firstLine, right - bounds.width()
                        , textY - alginBottom + bounds.height() / 2 + topGap / 2
                        , mTextPaint);
                break;
            default:
                break;
        }
    }

    /**
     * 判断是不是组中的第一个位置
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            // 因为是根据 字符串内容的相同与否 来判断是不是同意组的，所以此处的标记id 要是String类型
            // 如果你只是做联系人列表，悬浮框里显示的只是一个字母，则标记id直接用 int 类型就行了
            String prevGroupId = mCallback.getGroupId(pos - 1);
            String groupId = mCallback.getGroupId(pos);
            //判断前一个字符串 与 当前字符串 是否相同
            if (prevGroupId.equals(groupId)) {
                return false;
            } else {
                return true;
            }
        }
    }

    //定义一个借口方便外界的调用
    public interface DecorationCallback {
        String getGroupId(int position);
    }

    public interface TitleTextCallback {
        String getGroupFirstLine(int position);

        String getActiveGroup();
    }

    public interface TitleViewCallback {
        View getGroupFirstView(int position);
    }

}
