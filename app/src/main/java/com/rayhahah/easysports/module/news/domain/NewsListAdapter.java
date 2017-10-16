package com.rayhahah.easysports.module.news.domain;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.news.api.NewsApiFactory;
import com.rayhahah.easysports.module.news.bean.NewsItem;
import com.rayhahah.easysports.module.news.bean.VideoInfo;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rvideoplayer.JC.JCVideoPlayer;
import com.rayhahah.rvideoplayer.JC.JCVideoPlayerStandard;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by a on 2017/6/12.
 */

public class NewsListAdapter extends BaseQuickAdapter<NewsItem.DataBean.ItemInfo, BaseViewHolder> {

    public NewsListAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<NewsItem.DataBean.ItemInfo>() {
            @Override
            protected int getItemType(NewsItem.DataBean.ItemInfo itemInfo) {
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
    protected void convert(final BaseViewHolder helper, final NewsItem.DataBean.ItemInfo item) {
        switch (helper.getItemViewType()) {
            case C.NEWS.ITEM_TYPE_ARTICLE:
                helper.setText(R.id.tv_news_item_title, item.getTitle())
                        .setText(R.id.tv_news_item_date, item.getPub_time_detail());

                GlideUtil.load(mContext, item.getImgurl2(), ((ImageView) helper.getView(R.id.iv_news_item_cover)));

                helper.addOnClickListener(R.id.ll_news_item);
                break;
            case C.NEWS.ITEM_TYPE_VIDEOS:
                helper.setText(R.id.tv_video_title, item.getTitle())
                        .setText(R.id.tv_video_date, item.getPub_time());
                helper.addOnClickListener(R.id.iv_goto_web);
                final JCVideoPlayerStandard newsPlayer = (JCVideoPlayerStandard) helper.getView(R.id.jc_news_list_player);

//                if (StringUtils.isEmpty(item.getVideoUrl())) {
                NewsApiFactory.getVideoInfo(item.getVid())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                                VideoInfo videoInfo = JsonParser.parseVideoInfo(responseBody.string());

                                if (videoInfo.vl.vi != null && videoInfo.vl.vi.size() > 0) {
                                    String vid = videoInfo.vl.vi.get(0).vid;
                                    String vkey = videoInfo.vl.vi.get(0).fvkey;
                                    String url = videoInfo.vl.vi.get(0).ul.ui.get(0).url + vid + ".mp4?vkey=" + vkey;

                                    item.setVideoUrl(url);
                                    newsPlayer.setUp(item.getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, item.getTitle());
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                newsPlayer.setUp(item.getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, item.getTitle());
                            }
                        });
//                }

                newsPlayer.setPlayerClick(new JCVideoPlayerStandard.playerClickListenr() {
                    @Override
                    public void onClick(View v) {
                        helper.setVisible(R.id.tv_video_date, false)
                                .setVisible(R.id.iv_goto_web, false);
                    }
                });

                GlideUtil.load(mContext, item.getImgurl(), newsPlayer.thumbImageView);

                break;

            default:
                break;
        }
    }
}
