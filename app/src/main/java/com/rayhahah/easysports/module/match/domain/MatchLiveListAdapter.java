package com.rayhahah.easysports.module.match.domain;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.CommonAdapterDCB;
import com.rayhahah.easysports.module.match.bean.MatchListBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;

/**
 * Created by a on 2017/6/1.
 */

public class MatchLiveListAdapter extends BaseQuickAdapter<MatchListBean.DataBean.MatchesBean.MatchInfoBean, BaseViewHolder> {
    public MatchLiveListAdapter() {
        super(R.layout.item_match_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchListBean.DataBean.MatchesBean.MatchInfoBean item) {
        List<String> broadcasters = item.getBroadcasters();
        StringBuffer sb = new StringBuffer();
        for (String s : broadcasters) {
            sb.append(s + "/");
        }
        String md = sb.toString();
        String matchDesc = md.substring(0, md.length() - 1);
        String matchStatus = "";

        String quarter = item.getQuarter();

        if ("4".equals(item.getLiveType())) {
            matchStatus = "已结束";
        } else {
            if ("".equals(quarter)) {
                matchStatus = "未开始";
            } else {
                matchStatus = quarter;
            }
        }

        helper.setText(R.id.tv_left_team, item.getLeftName())
                .setText(R.id.tv_left_team_point, item.getLeftGoal())
                .setText(R.id.tv_right_team, item.getRightName())
                .setText(R.id.tv_right_team_point, item.getRightGoal())
                .setText(R.id.tv_match_desc, item.getMatchDesc())
                .setText(R.id.tv_broadcasters, matchDesc)
                .setText(R.id.tv_match_status, matchStatus);

        GlideUtil.load(mContext, item.getLeftBadge(), (ImageView) helper.getView(R.id.iv_left_team));
        GlideUtil.load(mContext, item.getRightBadge(), (ImageView) helper.getView(R.id.iv_right_team));

        helper.addOnClickListener(R.id.ll_match_list);
    }

    public static class DiffCallBack extends CommonAdapterDCB<MatchListBean.DataBean.MatchesBean.MatchInfoBean> {

        public DiffCallBack(List<MatchListBean.DataBean.MatchesBean.MatchInfoBean> oldData, List<MatchListBean.DataBean.MatchesBean.MatchInfoBean> newData) {
            super(oldData, newData);
        }

        @Override
        public boolean areContentsSame(MatchListBean.DataBean.MatchesBean.MatchInfoBean oldDataBean, MatchListBean.DataBean.MatchesBean.MatchInfoBean newDataBean) {
            return oldDataBean.getId().equals(newDataBean.getId());
        }
    }
}
