package com.rayhahah.easysports.module.match.domain;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.match.bean.MatchStatusBean;
import com.rayhahah.rbase.utils.base.ConvertUtils;

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
 * @time 2017/10/12
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchPlayerListAdapter extends BaseQuickAdapter<MatchStatusBean.PlayerStats, BaseViewHolder> {
    private LinearLayout.LayoutParams params;


    public MatchPlayerListAdapter() {
        super(R.layout.item_match_player);
        params = new LinearLayout.LayoutParams(ConvertUtils.dp2px(50), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchStatusBean.PlayerStats item) {
        LinearLayout llPlayerDataItem = helper.getView(R.id.ll_match_player);

        if (llPlayerDataItem.getChildCount() > 2) {
            llPlayerDataItem.removeViews(2, llPlayerDataItem.getChildCount() - 2);
        }

        if (item.head != null && !item.head.isEmpty()) {
            List<String> head = item.head;
            helper.setText(R.id.tv_match_player_name, head.get(0));
            for (int i = 2; i < head.size(); i++) {
                TextView tv = (TextView) mLayoutInflater.inflate(R.layout.player_textview, null);
                tv.setText(head.get(i));
                tv.setLayoutParams(params);
                llPlayerDataItem.addView(tv);
            }
        } else if (item.row != null) {
            List<String> row = item.row;
            helper.setText(R.id.tv_match_player_name, row.get(0));
            if ("是".equals(row.get(1))) {
                //是首发球员
            }
            for (int i = 2; i < row.size(); i++) {
                TextView tv = (TextView) mLayoutInflater.inflate(R.layout.player_textview, null);
                tv.setText(row.get(i));
                tv.setLayoutParams(params);
                llPlayerDataItem.addView(tv);
            }

//            helper.addOnClickListener(R.id.ll_match_player);
        }
    }
}
