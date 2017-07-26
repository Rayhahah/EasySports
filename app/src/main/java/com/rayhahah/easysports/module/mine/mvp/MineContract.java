package com.rayhahah.easysports.module.mine.mvp;

import android.content.Context;

import com.rayhahah.easysports.module.mine.bean.MineListBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by a on 2017/5/17.
 */

public class MineContract {
    public interface IMineView extends IRBaseView {

        void uploadFeedbackDone(BmobException e);

        void updateCurrentUserSuccess(MineListBean mineListBean);
    }

    public interface IMinePresenter  {

        List<MineListBean> getMineListData(Context context);

        void uploadFeedback(String editTextContent);

        void updateCurrentUser(MineListBean mineListBean);
    }
}
