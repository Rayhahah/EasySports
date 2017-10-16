package com.rayhahah.easysports.module.mine.domain;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.mine.bean.MineListBean;

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
 * @time 2017/7/24
 * @tips 这个类是Object的子类
 * @fuction
 */
public class AccountListAdapter extends BaseQuickAdapter<MineListBean, BaseViewHolder> {
    public AccountListAdapter(@Nullable List<MineListBean> data) {
        super(R.layout.item_mine_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineListBean item) {
        switch (item.getType()) {
            case MineListBean.TYPE_NULL:
                helper.setVisible(R.id.view_mine_list_empty, true)
                        .setVisible(R.id.iv_mine_list_goto, true)
                        .setVisible(R.id.tv_mine_list_desc, false)
                        .setVisible(R.id.cb_mine_list_theme, false);
                break;
            case MineListBean.TYPE_TEXTVIEW:
                helper.setVisible(R.id.view_mine_list_empty, false)
                        .setVisible(R.id.iv_mine_list_goto, true)
                        .setVisible(R.id.tv_mine_list_desc, true)
                        .setVisible(R.id.cb_mine_list_theme, false);
                break;
            default:
                break;
        }

        helper.setText(R.id.tv_mine_list_title, item.getTitle())
                .setImageResource(R.id.iv_mine_list_cover, item.getCoverRes());
        helper.addOnClickListener(R.id.fbl_mine_item);
    }

}
