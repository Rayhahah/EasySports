package com.rayhahah.easysports.module.match.domain;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;

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
 * @time 2017/9/30
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchForwardAdapter extends BaseQuickAdapter<MatchStatusBean.StatsBean, BaseViewHolder> {
    private String mLeftName = "左队";
    private String mRightName = "右队";

    public MatchForwardAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<MatchStatusBean.StatsBean>() {
            @Override
            protected int getItemType(MatchStatusBean.StatsBean statsBean) {
                switch (Integer.parseInt(statsBean.type)) {
                    case 1:
//                        历史对阵
                        return C.MATCH.ITEM_TYPE_FORWARD_HISTORY;
                    case 2:
                        //近期赛事
                        return C.MATCH.ITEM_TYPE_FORWARD_STATUS;
                    case 13:
//                        球队数据王
                        return C.MATCH.ITEM_TYPE_FORWARD_MAX;
                    default:
                        break;
                }
                return 0;
            }
        });
        getMultiTypeDelegate()
                .registerItemType(C.MATCH.ITEM_TYPE_FORWARD_MAX, R.layout.item_match_forward_list)
                .registerItemType(C.MATCH.ITEM_TYPE_FORWARD_STATUS, R.layout.item_match_forward_list_status)
                .registerItemType(C.MATCH.ITEM_TYPE_FORWARD_HISTORY, R.layout.item_match_forward_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchStatusBean.StatsBean item) {
        switch (helper.getItemViewType()) {
            //球队数据王者
            case C.MATCH.ITEM_TYPE_FORWARD_MAX:
                RecyclerView rvMax = (RecyclerView) helper.getView(R.id.rv_item_forward);
                MatchMaxAdapter maxAdapter = new MatchMaxAdapter();
                maxAdapter.openLoadAnimation();
                maxAdapter.setNewData(item.maxPlayers);
                rvMax.setAdapter(maxAdapter);
                rvMax.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                break;
            //近期赛事
            case C.MATCH.ITEM_TYPE_FORWARD_STATUS:
                final MatchStatusAdapter statusLeftAdapter = new MatchStatusAdapter(true);
                statusLeftAdapter.openLoadAnimation();
                statusLeftAdapter.setNewData(item.teamMatches.left);

                final MatchStatusAdapter statusRightAdapter = new MatchStatusAdapter(true);
                statusRightAdapter.openLoadAnimation();
                statusRightAdapter.setNewData(item.teamMatches.right);

                final CheckBox cbLeft = (CheckBox) helper.getView(R.id.cb_match_status_left);
                final CheckBox cbRight = (CheckBox) helper.getView(R.id.cb_match_status_right);
                final RecyclerView rvStatus = (RecyclerView) helper.getView(R.id.rv_item_forward);
                rvStatus.setAdapter(statusLeftAdapter);
                rvStatus.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                cbLeft.setText(mLeftName);
                cbRight.setText(mRightName);
                cbLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            cbRight.setChecked(false);
                            rvStatus.setAdapter(statusLeftAdapter);
                        } else {
                            cbRight.setChecked(true);
                            rvStatus.setAdapter(statusRightAdapter);
                        }
                    }
                });

                cbRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            cbLeft.setChecked(false);
                            rvStatus.setAdapter(statusRightAdapter);
                        } else {
                            cbLeft.setChecked(true);
                            rvStatus.setAdapter(statusLeftAdapter);
                        }
                    }
                });
                break;
            //历史对阵
            case C.MATCH.ITEM_TYPE_FORWARD_HISTORY:
                MatchHistoryAdapter historyAdapter = new MatchHistoryAdapter();
                historyAdapter.openLoadAnimation();
                historyAdapter.setNewData(item.vs);
                RecyclerView rvHistory = (RecyclerView) helper.getView(R.id.rv_item_forward);
                rvHistory.setAdapter(historyAdapter);
                rvHistory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                break;
            default:
                break;
        }
    }

    public void setTeamName(String leftName, String rightName) {
        mLeftName = leftName;
        mRightName = rightName;
        notifyDataSetChanged();
    }
}
