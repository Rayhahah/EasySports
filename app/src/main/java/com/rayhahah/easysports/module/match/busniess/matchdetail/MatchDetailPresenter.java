package com.rayhahah.easysports.module.match.busniess.matchdetail;

import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.module.match.api.MatchApiFactory;
import com.rayhahah.easysports.module.match.bean.MatchDetailBean;
import com.rayhahah.easysports.module.match.busniess.matchdata.MatchDataFragment;
import com.rayhahah.easysports.module.match.busniess.matchforward.MatchForwardFragment;
import com.rayhahah.easysports.module.match.busniess.matchlive.MatchLiveFragment;
import com.rayhahah.easysports.module.match.busniess.matchplayer.MatchPlayerFragment;
import com.rayhahah.easysports.module.match.busniess.matchvideo.MatchVideoFragment;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
 * @time 2017/9/29
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchDetailPresenter extends RBasePresenter<MatchDetailContract.IMatchDetailView> implements MatchDetailContract.IMatchDetailPresenter {

    private ArrayList<BaseFragment> mFragmentList = new ArrayList<>();

    public MatchDetailPresenter(MatchDetailContract.IMatchDetailView view) {
        super(view);

    }

    private ArrayList<String> tabTitles = new ArrayList<>();

    @Override
    public void getMatchDetail(String mid) {
        addSubscription(MatchApiFactory.getMatchDetail(mid).subscribe(new Consumer<MatchDetailBean>() {
            @Override
            public void accept(@NonNull MatchDetailBean data) throws Exception {
                if (data != null) {
                    mView.getMatchDetailSuccess(data);
                } else {
                    mView.getMatchDetailFailed("获取数据失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.getMatchDetailFailed(throwable.getMessage());
            }
        }));

    }

    @Override
    public void initTab(boolean isStart, String mId, MatchDetailBean.BaseInfo info) {
        if (isStart) {
            mFragmentList = new ArrayList<>();
            mFragmentList.add(MatchDataFragment.newInstance(mId, info));
            mFragmentList.add(MatchPlayerFragment.newInstance(mId));
            mFragmentList.add(MatchLiveFragment.newInstance(mId));
            mFragmentList.add(MatchVideoFragment.newInstance(mId));
            tabTitles = new ArrayList<>();
            tabTitles.add(C.MATCH.MATCH_DATA);
            tabTitles.add(C.MATCH.MATCH_PLAYER);
            tabTitles.add(C.MATCH.MATCH_LIVE);
            tabTitles.add(C.MATCH.MATCH_VIDEO);
            mView.initTabSuccess(tabTitles, mFragmentList);
        } else {
            mFragmentList = new ArrayList<>();
            mFragmentList.add(MatchForwardFragment.newInstance(mId));
            mFragmentList.add(MatchLiveFragment.newInstance(mId));
            tabTitles = new ArrayList<>();
            tabTitles.add(C.MATCH.MATCH_FORWARD);
            tabTitles.add(C.MATCH.MATCH_LIVE);
            mView.initTabSuccess(tabTitles, mFragmentList);
        }
    }

    @Override
    public void onDestory() {
        super.onDestory();
    }
}
