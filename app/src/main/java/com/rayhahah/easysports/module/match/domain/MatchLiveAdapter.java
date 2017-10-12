package com.rayhahah.easysports.module.match.domain;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.common.RWebActivity;
import com.rayhahah.easysports.module.match.bean.LiveDetail;
import com.rayhahah.rbase.utils.useful.GlideUtil;

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
 * @blog http://rayhahah.com
 * @time 2017/10/10
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchLiveAdapter extends BaseQuickAdapter<LiveDetail.LiveContent, BaseViewHolder> {
    private Activity mActivity;

    public MatchLiveAdapter(Activity activity) {
        super(R.layout.item_match_live);
        mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveDetail.LiveContent item) {
        helper.setText(R.id.tv_live_time, item.time)
                .setText(R.id.tv_live_team, item.teamName)
                .setText(R.id.tv_live_content, item.content);
        if (!(TextUtils.isEmpty(item.leftGoal) || TextUtils.isEmpty(item.rightGoal))) {
            helper.setVisible(R.id.tv_live_score, true);
            helper.setText(R.id.tv_live_score, item.leftGoal + ":" + item.rightGoal);
        } else {
            helper.setVisible(R.id.tv_live_score, false);
        }

        ImageView image = (ImageView) helper.getView(R.id.iv_live_image);

        if ("图片".equals(item.time) && item.image != null
                && item.image.urls != null && item.image.urls.size() > 0) {
            final List<LiveDetail.UrlsBean> urls = item.image.urls;
            image.setVisibility(View.VISIBLE);
            GlideUtil.load(mContext, urls.get(0).small, image);
            if (!TextUtils.isEmpty(urls.get(0).large)) {
                // TODO: 2017/10/10 点击进入大图模式
            }
        } else {
            image.setVisibility(View.GONE);
        }

        ImageView video = (ImageView) helper.getView(R.id.iv_live_video);
        if ("视频".equals(item.time) && item.video != null) {
            final LiveDetail.VideoBean videoBean = item.video;
            if (!TextUtils.isEmpty(videoBean.pic_160x90)) {
                video.setVisibility(View.VISIBLE);
                GlideUtil.load(mContext, videoBean.pic_160x90, video);
                video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RWebActivity.start(mContext, mActivity, videoBean.playurl, "", true, true);
                    }
                });
            } else {
                video.setVisibility(View.GONE);
            }
        } else {
            video.setVisibility(View.GONE);
        }

        if ("1".equals(item.ctype) && TextUtils.isEmpty(item.time)) {
            helper.setText(R.id.tv_live_time, "结束");
        }
    }

}
