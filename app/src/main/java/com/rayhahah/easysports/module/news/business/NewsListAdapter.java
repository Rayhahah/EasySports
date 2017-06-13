package com.rayhahah.easysports.module.news.business;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.C;
import com.rayhahah.easysports.module.news.bean.NewsItem;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.RLog;

/**
 * Created by a on 2017/6/12.
 */

public class NewsListAdapter extends BaseQuickAdapter<NewsItem.DataBean.ItemInfo, BaseViewHolder> {

    public NewsListAdapter() {
        super(null);
//        addItemType(C.NEWS.ITEM_TYPE_ARTICLE, R.layout.item_news_list_article);
//        addItemType(C.NEWS.ITEM_TYPE_VIDEOS, R.layout.item_news_list_videos);

        setMultiTypeDelegate(new MultiTypeDelegate<NewsItem.DataBean.ItemInfo>() {
            @Override
            protected int getItemType(NewsItem.DataBean.ItemInfo itemInfo) {
                RLog.e("iteminfo=" + itemInfo.getAtype() + ",name=" + itemInfo.getAtypeName());

                RLog.e("type=" + Integer.parseInt(itemInfo.getAtype()));

                if (StringUtils.isNotEmpty(itemInfo.getAtype())) {
                    int i = Integer.parseInt(itemInfo.getAtype());
                    switch (i) {
                        case 0:
                        case 1:
                            return C.NEWS.ITEM_TYPE_ARTICLE;
                        case 2:
                            return C.NEWS.ITEM_TYPE_VIDEOS;
                        default:
                            return C.NEWS.ITEM_TYPE_ARTICLE;
                    }
                } else {
                    return C.NEWS.ITEM_TYPE_ARTICLE;
                }
            }
        });
        getMultiTypeDelegate()
                .registerItemType(C.NEWS.ITEM_TYPE_ARTICLE, R.layout.item_news_list_article)
                .registerItemType(C.NEWS.ITEM_TYPE_VIDEOS, R.layout.item_news_list_videos);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItem.DataBean.ItemInfo item) {
        switch (helper.getItemViewType()) {
            case C.NEWS.ITEM_TYPE_ARTICLE:
                helper.setText(R.id.tv_news_item_title, item.getTitle())
                        .setText(R.id.tv_news_item_date, item.getPub_time_detail());
                GlideUtil.load(mContext, item.getImgurl2(), ((ImageView) helper.getView(R.id.iv_news_item_cover)));
                break;
            case C.NEWS.ITEM_TYPE_VIDEOS:


                break;
        }
    }
}
