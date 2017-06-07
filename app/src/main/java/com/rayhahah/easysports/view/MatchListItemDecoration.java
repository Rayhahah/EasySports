package com.rayhahah.easysports.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.rayhahah.easysports.bean.BaseSection;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.rbase.utils.base.ConvertUtils;
import com.rayhahah.rbase.utils.base.StringUtils;

import java.util.List;

/**
 * Created by a on 2017/6/6.
 */

public class MatchListItemDecoration<T extends BaseSection> extends RecyclerView.ItemDecoration {


    private final int mActiveColor;
    private final int mTextColor;
    private Paint paint;
    private TextPaint mTextPaint;
    private int alginBottom;
    private int topGap;
    private List<MatchListBean.DataBean.MatchesBean.MatchInfoBean> mData;
    private DecorationCallback mCallback;
    private Paint.FontMetrics fontMetrics;

    public MatchListItemDecoration(Context context, List<MatchListBean.DataBean.MatchesBean.MatchInfoBean> data
            , int textColor, int bgColor, int activeColor, DecorationCallback callback) {
        Resources res = context.getResources();

        mData = data;
        mCallback = callback;

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
            outRect.top = topGap;
            if (pos < mData.size()
                    && mData.get(pos).getSectionData() == null) {
                outRect.top = 0;
            }
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
            String textLine = mCallback.getGroupFirstLine(pos);

            Rect bounds = new Rect();
            mTextPaint.getTextBounds(textLine, 0, textLine.length(), bounds);

            if (StringUtils.isEmpty(textLine)) {
//                float top = view.getTop();
//                float bottom = view.getTop();
//                c.drawRect(left, top, right, bottom, paint);
                return;
            } else {
                if (textLine.equals(mCallback.getActiveGroup())) {
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
                    c.drawText(textLine, right / 2 - bounds.width() / 2, bottom, mTextPaint);
                }
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
        float lineHeight = mTextPaint.getTextSize() + fontMetrics.descent;

        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = mCallback.getGroupId(pos);
            if (null == groupId || groupId.equals(preGroupId)) continue;

            String firstLine = mCallback.getGroupFirstLine(pos);
            Rect bounds = new Rect();
            mTextPaint.getTextBounds(firstLine, 0, firstLine.length(), bounds);

            if (StringUtils.isEmpty(firstLine)) continue;

            if (firstLine.equals(mCallback.getActiveGroup())) {
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
                if (!nextGroupId.equals(groupId) && bottom < textY) {
                    textY = bottom;
                }
            }
            //textY - topGap决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - topGap, right, textY, paint);
            //left+2*alignBottom 决定了文本往左偏移的多少（加-->向左移）
            //textY-alignBottom  决定了文本往右偏移的多少  (减-->向上移)
            c.drawText(firstLine, right / 2 - bounds.width() / 2
                    , textY - alginBottom + bounds.height() / 2 + topGap / 2, mTextPaint);
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

    public void setNewData(List<MatchListBean.DataBean.MatchesBean.MatchInfoBean> newData) {
        mData = newData;
    }

    //定义一个借口方便外界的调用
    public interface DecorationCallback {
        String getGroupId(int position);

        String getGroupFirstLine(int position);

        String getActiveGroup();
    }

}
