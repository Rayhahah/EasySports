package com.rayhahah.easysports.module.match.busniess.matchlive;

import com.rayhahah.easysports.module.match.api.MatchApiFactory;
import com.rayhahah.easysports.module.match.bean.LiveDetail;
import com.rayhahah.easysports.module.match.bean.LiveIndex;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

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
 * @time 2017/9/30
 * @tips 这个类是Object的子类
 * @fuction
 */
public class MatchLivePresenter extends RBasePresenter<MatchLiveContract.IMatchLiveView> implements MatchLiveContract.IMatchLivePresenter {
    private List<String> index = new ArrayList<>();
    private String lastId;
    private String firstId;

    public MatchLivePresenter(MatchLiveContract.IMatchLiveView view) {
        super(view);
    }

    @Override
    public void getLiveIndex(final String mid) {
        addSubscription(MatchApiFactory.getMatchLiveIndex(mid).subscribe(new Consumer<LiveIndex>() {
            @Override
            public void accept(@NonNull LiveIndex liveIndex) throws Exception {

                if (liveIndex.data != null && liveIndex.data.index != null && !liveIndex.data.index.isEmpty()) {
                    index.clear();
                    index.addAll(liveIndex.data.index);
                    String ids = "";
                    for (int i = 0; i < 20 && i < index.size(); i++) { // 每次最多请求20条
                        if (index.get(i).equals(firstId)) {
                            break;
                        } else {
                            ids += index.get(i) + ",";
                            lastId = index.get(i);
                        }
                    }
                    if (ids.length() > 1) {
                        ids = ids.substring(0, ids.length() - 1);
                        getLiveContent(mid, ids, true);
                    } else {
                        mView.getLiveDataFailed("当前为最新数据", true);
                    }
                } else {
                    mView.getLiveDataFailed("暂无数据", true);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.showViewError(throwable);
            }
        }));
    }


    public void getLiveContent(String mid, String ids, final boolean front) {
        addSubscription(MatchApiFactory.getMatchLiveDetail(mid, ids).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                firstId = index.get(0);
                LiveDetail liveDetail = JsonParser.parseMatchLiveDetail(responseBody.string());
                mView.getLiveDataSuccess(liveDetail.data.detail, front);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.showViewError(throwable);
            }
        }));
    }

    @Override
    public void getMoreContent(String mid) {
        String ids = "";
        boolean start = false;
        for (int i = 0, sum = 0; sum < 10 && i < index.size(); i++) { // 每次最多请求20条
            if (index.get(i).equals(lastId)) {
                start = true;
            } else {
                if (start) {
                    sum++;
                    ids += index.get(i) + ",";
                    lastId = index.get(i);
                }
            }
        }
        if (ids.length() > 1) {
            ids = ids.substring(0, ids.length() - 1);
            getLiveContent(mid, ids, false);
        }
    }
}
