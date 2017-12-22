package com.rayhahah.easysports.module.match.domain;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;

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
 * @time 2017/10/11
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchDataAdapter extends BaseQuickAdapter<MatchStatusBean.StatsBean, BaseViewHolder> {
    private String mLeftCover;
    private String mRightCover;
    private int mColorPrimary;

    public MatchDataAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<MatchStatusBean.StatsBean>() {
            @Override
            protected int getItemType(MatchStatusBean.StatsBean statsBean) {
                switch (Integer.parseInt(statsBean.type)) {
                    case 12:
//                        比分
                        return C.MATCH.ITEM_TYPE_MATCH_DATA_POINT;
                    case 14:
                        //球队统计
                        return C.MATCH.ITEM_TYPE_MATCH_DATA_TEAM_COUNT;
                    case 13:
                        //全场最佳
                        return C.MATCH.ITEM_TYPE_MATCH_DATA_TEAM_BEST;
                    default:
                        break;
                }
                return 0;
            }
        });
        getMultiTypeDelegate()
                .registerItemType(C.MATCH.ITEM_TYPE_MATCH_DATA_POINT, R.layout.item_match_data_point)
                .registerItemType(C.MATCH.ITEM_TYPE_MATCH_DATA_TEAM_COUNT, R.layout.item_match_data_count_list)
                .registerItemType(C.MATCH.ITEM_TYPE_MATCH_DATA_TEAM_BEST, R.layout.item_null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchStatusBean.StatsBean item) {
        switch (helper.getItemViewType()) {
            //比分
            case C.MATCH.ITEM_TYPE_MATCH_DATA_POINT:
                if (item.goals != null && !item.goals.isEmpty()) {
                    GlideUtil.load(mContext, mLeftCover, (ImageView) helper.getView(R.id.iv_match_point_left));
                    GlideUtil.load(mContext, mRightCover, (ImageView) helper.getView(R.id.iv_match_point_right));

                    MatchStatusBean.Goals goals = item.goals.get(0);
                    List<String> head = goals.head;
                    List<String> left = goals.rows.get(0);
                    List<String> right = goals.rows.get(1);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

                    LinearLayout llMatchPointHead = (LinearLayout) helper.getView(R.id.ll_match_point_head);
                    LinearLayout llMatchPointLeft = (LinearLayout) helper.getView(R.id.ll_match_point_left);
                    LinearLayout llMatchPointRight = (LinearLayout) helper.getView(R.id.ll_match_point_right);

                    for (int i = 0; i < head.size() && i < left.size() && i < right.size(); i++) {
                        if (llMatchPointRight.getChildAt(i + 1) != null) {
                            TextView tv = (TextView) llMatchPointHead.getChildAt(i + 1);
                            tv.setText(head.get(i));
                        } else {

                            TextView tv = (TextView) mLayoutInflater.inflate(R.layout.match_textview, null);
                            tv.setText(head.get(i));
                            tv.setLayoutParams(params);
                            llMatchPointHead.addView(tv, i + 1);
                        }
                        if (llMatchPointLeft.getChildAt(i + 1) != null) {
                            TextView tv1 = (TextView) llMatchPointLeft.getChildAt(i + 1);
                            tv1.setText(left.get(i));
                        } else {
                            TextView tv1 = (TextView) mLayoutInflater.inflate(R.layout.match_textview, null);
                            tv1.setText(left.get(i));
                            tv1.setLayoutParams(params);
                            llMatchPointLeft.addView(tv1, i + 1);
                        }

                        if (llMatchPointRight.getChildAt(i + 1) != null) {
                            TextView tv2 = (TextView) llMatchPointRight.getChildAt(i + 1);
                            tv2.setText(right.get(i));
                        } else {
                            TextView tv2 = (TextView) mLayoutInflater.inflate(R.layout.match_textview, null);
                            tv2.setText(right.get(i));
                            tv2.setLayoutParams(params);
                            llMatchPointRight.addView(tv2, i + 1);
                        }
                    }
                }
                break;
            //球队统计
            case C.MATCH.ITEM_TYPE_MATCH_DATA_TEAM_COUNT:
                if (item.teamStats != null && !item.teamStats.isEmpty()) {
                    MatchDataCountAdapter countAdapter = new MatchDataCountAdapter();
                    countAdapter.openLoadAnimation();
                    countAdapter.setNewData(item.teamStats);
                    countAdapter.setPrimaryColor(mColorPrimary);
                    RecyclerView rvCount = (RecyclerView) helper.getView(R.id.rv_item_match_count);
                    rvCount.setAdapter(countAdapter);
                    rvCount.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                }
                break;
            case C.MATCH.ITEM_TYPE_MATCH_DATA_TEAM_BEST:
                TextView tv = (TextView) helper.getView(R.id.tv_item_null);
                tv.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                break;
            default:
                break;
        }
    }

    public void setTeamCover(String leftCover, String rightCover) {
        mLeftCover = leftCover;
        mRightCover = rightCover;
        notifyDataSetChanged();
    }

    public void setPrimaryColor(int colorPrimary) {
        mColorPrimary = colorPrimary;
        notifyDataSetChanged();
    }
}
