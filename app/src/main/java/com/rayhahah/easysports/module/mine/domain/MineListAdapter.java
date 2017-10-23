package com.rayhahah.easysports.module.mine.domain;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.module.mine.bean.MineListBean;
import com.rayhahah.easysports.utils.glide.GlideCircleTransform;
import com.rayhahah.rbase.utils.base.CacheUtils;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;

/**
 * Created by a on 2017/6/26.
 */

public class MineListAdapter extends BaseQuickAdapter<MineListBean, BaseViewHolder> {

    public MineListAdapter(List<MineListBean> data) {
        super(R.layout.item_mine_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineListBean item) {
        switch (item.getType()) {
            case MineListBean.TYPE_NULL:

                helper.getView(R.id.view_mine_list_empty).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_mine_list_goto).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_mine_list_desc).setVisibility(View.GONE);
                helper.getView(R.id.cb_mine_list_theme).setVisibility(View.GONE);
                break;
            case MineListBean.TYPE_CHECKBOX:

                helper.getView(R.id.view_mine_list_empty).setVisibility(View.VISIBLE);
                helper.getView(R.id.iv_mine_list_goto).setVisibility(View.GONE);
                helper.getView(R.id.tv_mine_list_desc).setVisibility(View.GONE);
                helper.getView(R.id.cb_mine_list_theme).setVisibility(View.VISIBLE);
                if (MyApp.isNightTheme()) {
                    helper.setChecked(R.id.cb_mine_list_theme, true);
                } else {
                    helper.setChecked(R.id.cb_mine_list_theme, false);
                }
                helper.setOnCheckedChangeListener(R.id.cb_mine_list_theme, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        setItemCheckedChanged(buttonView, isChecked);
                    }
                });

                break;
            case MineListBean.TYPE_TEXTVIEW:
                helper.getView(R.id.view_mine_list_empty).setVisibility(View.GONE);
                helper.getView(R.id.iv_mine_list_goto).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_mine_list_desc).setVisibility(View.VISIBLE);
                helper.getView(R.id.cb_mine_list_theme).setVisibility(View.GONE);
                break;
            default:
                break;
        }

        helper.setText(R.id.tv_mine_list_desc, CacheUtils.getCacheSize(mContext));

        helper.setText(R.id.tv_mine_list_title, item.getTitle())
                .setImageResource(R.id.iv_mine_list_cover, item.getCoverRes());
        helper.addOnClickListener(R.id.fbl_mine_item);
        if (StringUtils.isNotEmpty(item.getCoverPath())) {
            GlideUtil.loadWithTransform(mContext, item.getCoverPath()
                    , ((ImageView) helper.getView(R.id.iv_mine_list_cover)), new GlideCircleTransform(mContext));
        }

    }

    public void setItemCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
