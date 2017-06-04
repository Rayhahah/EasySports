package com.rayhahah.easysports.module.live.domain;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.live.bean.MatchListBean;

/**
 * Created by a on 2017/6/1.
 */

public class MatchLiveListAdapter extends BaseQuickAdapter<MatchListBean.MatchInfoBean, BaseViewHolder> {
    public MatchLiveListAdapter() {
        super(R.layout.item_match_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchListBean.MatchInfoBean item) {


    }
}
