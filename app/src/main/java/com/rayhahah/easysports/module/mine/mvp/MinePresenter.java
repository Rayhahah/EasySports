package com.rayhahah.easysports.module.mine.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.bean.db.LocalUser;
import com.rayhahah.easysports.module.mine.api.MineApiFactory;
import com.rayhahah.easysports.module.mine.bean.MineListBean;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ImageUtils;
import com.rayhahah.rbase.utils.useful.RxSchedulers;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


public class MinePresenter extends RBasePresenter<MineContract.IMineView>
        implements MineContract.IMinePresenter {
    public MinePresenter(MineContract.IMineView view) {
        super(view);
    }

    @Override
    public List<MineListBean> getMineListData(Context context) {
        List<MineListBean> mData = new ArrayList<>();

        MineListBean login = new MineListBean();

        login.setCoverRes(R.drawable.ic_svg_person_colorful_24);
        login.setTitle("账号/登陆");
        login.setSectionData("账号");
        login.setType(MineListBean.TYPE_NULL);
        login.setId(C.MINE.ID_LOGIN);
        mData.add(login);

        MineListBean team = new MineListBean();
        team.setCoverRes(R.drawable.ic_svg_team_colorful_24);
        team.setTitle("所有球队");
        team.setSectionData("其他");
        team.setType(MineListBean.TYPE_NULL);
        team.setId(C.MINE.ID_TEAM);
        mData.add(team);

        MineListBean player = new MineListBean();
        player.setCoverRes(R.drawable.ic_svg_player_colorful_24);
        player.setTitle("所有球员");
        player.setSectionData("其他");
        player.setType(MineListBean.TYPE_NULL);
        player.setId(C.MINE.ID_PLAYER);
        mData.add(player);

        MineListBean live = new MineListBean();
        live.setCoverRes(R.drawable.ic_svg_live_record_blue_24);
        live.setTitle("直播间");
        live.setSectionData("其他");
        live.setType(MineListBean.TYPE_NULL);
        live.setId(C.MINE.ID_LIVE);
        mData.add(live);

        MineListBean version = new MineListBean();
        version.setCoverRes(R.drawable.ic_svg_version_colorful_24);
        version.setTitle("版本更新");
        version.setSectionData("其他");
        version.setType(MineListBean.TYPE_NULL);
        version.setId(C.MINE.ID_VERSION);
        mData.add(version);

        MineListBean qrcode = new MineListBean();
        qrcode.setCoverRes(R.drawable.ic_svg_qrcode_pink_24);
        qrcode.setTitle("扫一扫");
        qrcode.setSectionData("其他");
        qrcode.setType(MineListBean.TYPE_NULL);
        qrcode.setId(C.MINE.ID_QRCODE);
        mData.add(qrcode);

        MineListBean theme = new MineListBean();
        theme.setCoverRes(R.drawable.ic_svg_nighttheme_colorful_24);
        theme.setTitle("夜间模式");
        theme.setSectionData("设置");
        theme.setType(MineListBean.TYPE_CHECKBOX);
        theme.setId(C.MINE.ID_THEME);
        mData.add(theme);

        MineListBean clean = new MineListBean();
        clean.setCoverRes(R.drawable.ic_svg_clean_colorful_24);
        clean.setTitle("清除缓存");
        clean.setSectionData("设置");
        clean.setType(MineListBean.TYPE_TEXTVIEW);
        clean.setId(C.MINE.ID_CLEAN);
        mData.add(clean);

        MineListBean feedback = new MineListBean();
        feedback.setCoverRes(R.drawable.ic_svg_feedback_colorful_24);
        feedback.setTitle("意见反馈");
        feedback.setSectionData("设置");
        feedback.setType(MineListBean.TYPE_NULL);
        feedback.setId(C.MINE.ID_FEEDBACK);
        mData.add(feedback);

        MineListBean about = new MineListBean();
        about.setCoverRes(R.drawable.ic_svg_about_colorful_24);
        about.setTitle("关于");
        about.setSectionData("设置");
        about.setType(MineListBean.TYPE_NULL);
        about.setId(C.MINE.ID_ABOUT);
        mData.add(about);

        return mData;
    }

    @Override
    public void uploadFeedback(final Activity context, String editTextContent) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            LocalUser localUser = MyApp.getCurrentUser();
            addSubscription(MineApiFactory.commitFeedback(packageInfo.versionName, packageInfo.versionCode + "",
                    editTextContent, Integer.parseInt(localUser.getEssysport_id()))
                    .subscribe(new Consumer<RResponse>() {
                        @Override
                        public void accept(@NonNull RResponse rResponse) throws Exception {
                            if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                                mView.commitFeedbackSuccess();
                            } else {
                                mView.commitFeedbackFailed();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            mView.commitFeedbackFailed();
                        }
                    }));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mView.commitFeedbackFailed();
        }
    }

    @Override
    public void updateCurrentUser(final MineListBean mineListBean) {
        if (C.TRUE.equals(SPManager.get().getStringValue(C.SP.IS_LOGIN))) {
            LocalUser currentUser = MyApp.getCurrentUser();
            mineListBean.setTitle(currentUser.getScreen_name());
            mineListBean.setCoverPath(currentUser.getCover());
        } else {
            mineListBean.setTitle("账号/登陆");
            mineListBean.setCoverPath(C.NULL);
        }
        mView.updateCurrentUserSuccess(mineListBean);
    }

    @Override
    public void saveBitmap(final Bitmap bitmap) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                File file = new File(C.DIR.PIC_DIR, System.currentTimeMillis() + ".jpg");
                boolean createFile = FileUtils.createOrExistsFile(file);
                if (createFile) {
                    boolean isSuccess = ImageUtils.save(bitmap, file, Bitmap.CompressFormat.JPEG, true);
                    e.onNext(isSuccess);
                } else {
                    e.onNext(createFile);
                }

            }
        }).compose(RxSchedulers.<Boolean>ioMain()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean result) throws Exception {
                if (result) {
                    mView.saveBitmapSuccess();
                } else {
                    mView.saveBitmapFailed(null);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.saveBitmapFailed(throwable);

            }
        });
    }
}
