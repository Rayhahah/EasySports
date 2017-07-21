package com.rayhahah.easysports.module.mine.domain;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.mine.bean.TeamListBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

/**
 * Created by a on 2017/6/28.
 */

public class TeamListAdapter extends BaseQuickAdapter<TeamListBean.DataBean.TeamBean, BaseViewHolder> {
    public TeamListAdapter() {
        super(R.layout.item_team_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamListBean.DataBean.TeamBean item) {
        helper.setText(R.id.tv_item_team_name, item.getTeamName());
        GlideUtil.load(mContext, item.getLogo(),
                ((ImageView) helper.getView(R.id.iv_item_team_cover)));
        helper.addOnClickListener(R.id.fbl_item_team);
    }

}
