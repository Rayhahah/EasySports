package com.rayhahah.easysports.module.mine.domain;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.MyApplication;
import com.rayhahah.easysports.module.mine.bean.MineListBean;
import com.rayhahah.rbase.utils.base.CacheUtils;

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
                helper.setVisible(R.id.view_mine_list_empty, true)
                        .setVisible(R.id.iv_mine_list_goto, true)
                        .setVisible(R.id.tv_mine_list_desc, false)
                        .setVisible(R.id.cb_mine_list_theme, false);
                break;
            case MineListBean.TYPE_CHECKBOX:
                helper.setVisible(R.id.view_mine_list_empty, true)
                        .setVisible(R.id.iv_mine_list_goto, false)
                        .setVisible(R.id.tv_mine_list_desc, false)
                        .setVisible(R.id.cb_mine_list_theme, true);
                if (MyApplication.isNightTheme()) {
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
                helper.setVisible(R.id.view_mine_list_empty, false)
                        .setVisible(R.id.iv_mine_list_goto, true)
                        .setVisible(R.id.tv_mine_list_desc, true)
                        .setVisible(R.id.cb_mine_list_theme, false);
                break;
        }
        helper.setText(R.id.tv_mine_list_desc, CacheUtils.getCacheSize(mContext));
        helper.setImageResource(R.id.iv_mine_list_cover, item.getCoverRes())
                .setText(R.id.tv_mine_list_title, item.getTitle());
        helper.addOnClickListener(R.id.fbl_mine_item);
    }

    public void setItemCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
