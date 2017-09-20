package com.rayhahah.easysports.module.forum.mvp;

import com.rayhahah.easysports.module.forum.bean.ForumsData;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class ForumContract {
    public interface IForumView extends IRBaseView {

        void getAllForumsFailed();

        void getAllForumsSuccess(List<ForumsData.Forum> data);
    }

    public interface IForumPresenter{

        void getAllForums();
    }
}
