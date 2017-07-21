package com.rayhahah.easysports.module.mine.domain;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.mine.bean.PlayerListBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

/**
 * Created by a on 2017/6/28.
 */

public class PlayerListAdapter extends BaseQuickAdapter<PlayerListBean.DataBean, BaseViewHolder> {
    public PlayerListAdapter() {
        super(R.layout.item_player_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayerListBean.DataBean item) {
        helper.setText(R.id.tv_item_player_name, item.getCnName())
                .setText(R.id.tv_item_player_desc, item.getJerseyNum()
                        + "号\u3000" + item.getTeamName()
                        + "\u3000" + item.getPosition());
        GlideUtil.load(mContext, item.getIcon(), ((ImageView) helper.getView(R.id.iv_item_player_cover)));
        helper.addOnClickListener(R.id.fbl_item_player);
    }

}
