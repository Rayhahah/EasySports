package com.rayhahah.easysports.module.forum.domain;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

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
 * @time 2017/9/15
 * @tips 这个类是Object的子类
 * @fuction
 */
public class ForumListAdapter extends BaseQuickAdapter<ForumsData.Forum, BaseViewHolder> {

    public ForumListAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<ForumsData.Forum>() {
            @Override
            protected int getItemType(ForumsData.Forum itemInfo) {
                if (StringUtils.isNotEmpty(itemInfo.fid)) {
                    if ("0".equals(itemInfo.fid)) {
                        return C.FORUM.ITEM_TYPE_TITLE;
                    } else {
                        return C.FORUM.ITEM_TYPE_CONTENT;
                    }
                } else {
                    return C.FORUM.ITEM_TYPE_TITLE;
                }
            }
        });
        getMultiTypeDelegate()
                .registerItemType(C.FORUM.ITEM_TYPE_CONTENT, R.layout.item_forum_list)
                .registerItemType(C.FORUM.ITEM_TYPE_TITLE, R.layout.item_forum_list_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, ForumsData.Forum item) {
        switch (item.getItemType()) {
            case C.FORUM.ITEM_TYPE_CONTENT:
                helper.setText(R.id.tv_item_forum_title, item.name);
                GlideUtil.load(mContext, item.logo, (ImageView) helper.getView(R.id.iv_item_forum_cover));
                helper.addOnClickListener(R.id.ll_item_forum_title);
                break;
            case C.FORUM.ITEM_TYPE_TITLE:
                helper.setText(R.id.tv_item_forum_list_title, item.name);
                break;
            default:
                break;
        }
    }
}
