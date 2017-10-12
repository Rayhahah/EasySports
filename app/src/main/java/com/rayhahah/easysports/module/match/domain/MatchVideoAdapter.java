package com.rayhahah.easysports.module.match.domain;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.module.match.bean.MatchVideo;
import com.rayhahah.easysports.module.news.api.NewsApiFactory;
import com.rayhahah.easysports.module.news.bean.VideoInfo;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rvideoplayer.JC.JCVideoPlayer;
import com.rayhahah.rvideoplayer.JC.JCVideoPlayerStandard;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

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
 * @time 2017/10/10
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchVideoAdapter extends BaseQuickAdapter<MatchVideo.VideoBean, BaseViewHolder> {
    public MatchVideoAdapter() {
        super(R.layout.item_news_list_videos);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MatchVideo.VideoBean item) {
        helper.setText(R.id.tv_video_title, item.title)
                .setText(R.id.tv_video_date, item.pub_time);
        helper.setVisible(R.id.iv_goto_web, false)
                .setVisible(R.id.tv_video_duration, true);
        helper.setText(R.id.tv_video_duration, item.duration);
        final JCVideoPlayerStandard newsPlayer = (JCVideoPlayerStandard) helper.getView(R.id.jc_news_list_player);

        if (TextUtils.isEmpty(item.realUrl)) {
            NewsApiFactory.getVideoInfo(item.vid).subscribe(new Consumer<ResponseBody>() {
                @Override
                public void accept(@NonNull ResponseBody responseBody) throws Exception {
                    VideoInfo videoInfo = JsonParser.parseVideoInfo(responseBody.string());

                    if (videoInfo.vl.vi != null && videoInfo.vl.vi.size() > 0) {
                        String vid = videoInfo.vl.vi.get(0).vid;
                        String vkey = videoInfo.vl.vi.get(0).fvkey;
                        String url = videoInfo.vl.vi.get(0).ul.ui.get(0).url + vid + ".mp4?vkey=" + vkey;

                        item.realUrl = url;
                        newsPlayer.setUp(item.realUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST, item.title);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    newsPlayer.setUp(item.realUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST, item.title);
                }
            });
        } else {
            newsPlayer.setUp(item.realUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST, item.title);
        }

        newsPlayer.setPlayerClick(new JCVideoPlayerStandard.playerClickListenr() {
            @Override
            public void onClick(View v) {
                helper.setVisible(R.id.tv_video_date, false)
                        .setVisible(R.id.iv_goto_web, false)
                        .setVisible(R.id.tv_video_duration, false);
            }
        });

        GlideUtil.load(mContext, item.imgurl, newsPlayer.thumbImageView);
    }
}
