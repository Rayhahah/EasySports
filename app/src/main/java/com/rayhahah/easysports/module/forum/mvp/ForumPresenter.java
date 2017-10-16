package com.rayhahah.easysports.module.forum.mvp;

import com.rayhahah.easysports.module.forum.api.ForumApiFactory;
import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.rbase.base.RBasePresenter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by a on 2017/5/17.
 */

public class ForumPresenter extends RBasePresenter<ForumContract.IForumView>
        implements ForumContract.IForumPresenter {
    public ForumPresenter(ForumContract.IForumView view) {
        super(view);
    }

    @Override
    public void getAllForums() {
        try {
            addSubscription(ForumApiFactory.getAllForums().subscribe(new Consumer<ForumsData>() {
                @Override
                public void accept(@NonNull ForumsData forumsData) throws Exception {
                    List<ForumsData.Forum> list = new ArrayList<>();
                    ArrayList<ForumsData.ForumsResult> data = forumsData.data;
                    if (data != null && !data.isEmpty()) {
                        for (ForumsData.ForumsResult result : data) {
                            if ("1".equals(result.fid) || "232".equals(result.fid)) { // 目前只加入NBA和CBA
                                ArrayList<ForumsData.Forums> sub = result.sub;
                                for (ForumsData.Forums forums : sub) {
                                    ForumsData.Forum forum = new ForumsData.Forum();
                                    forum.fid = "0";
                                    forum.name = forums.name;
                                    list.add(forum);
                                    list.addAll(forums.data);
                                }
                                mView.getAllForumsSuccess(list);
                            }
                        }
                    }else{
                        mView.getAllForumsFailed();
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.getAllForumsFailed();
                }
            }));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
