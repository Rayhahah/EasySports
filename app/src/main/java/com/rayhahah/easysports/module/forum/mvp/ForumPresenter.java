package com.rayhahah.easysports.module.forum.mvp;

import com.rayhahah.rbase.base.RBasePresenter;

/**
 * Created by a on 2017/5/17.
 */

public class ForumPresenter extends RBasePresenter<ForumContract.IForumView>
        implements ForumContract.IForumPresenter {
    public ForumPresenter(ForumContract.IForumView view) {
        super(view);
    }
}
