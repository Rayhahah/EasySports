package com.rayhahah.easysports.module.mine.business.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;

import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.bean.db.LocalUser;
import com.rayhahah.easysports.module.mine.api.MineApiFactory;
import com.rayhahah.easysports.module.mine.bean.HupuUserData;
import com.rayhahah.easysports.module.mine.bean.MineListBean;
import com.rayhahah.easysports.module.mine.bean.RResponse;
import com.rayhahah.easysports.net.ApiFactory;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.easysports.utils.JsonParser;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.net.download.ProgressListener;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
 * @time 2017/7/21
 * @tips 这个类是Object的子类
 * @fuction
 */
public class AccountPresenter extends RBasePresenter<AccountContract.IAccountView>
        implements AccountContract.IAccountPresenter {

    private Uri mUri;

    public AccountPresenter(AccountContract.IAccountView view) {
        super(view);
    }

    @Override
    public List<MineListBean> getListData(Context context) {
        List<MineListBean> mData = new ArrayList<>();

//        MineListBean scrreenName = new MineListBean();
//        scrreenName.setCoverRes(R.drawable.ic_svg_screenname_colorful_24);
//        scrreenName.setTitle("设置昵称");
//        scrreenName.setSectionData(context.getResources().getString(R.string.account_setting));
//        scrreenName.setType(MineListBean.TYPE_NULL);
//        scrreenName.setId(C.ACCOUNT.ID_SCREENNAME);
//        mData.add(scrreenName);

        MineListBean reset = new MineListBean();
        reset.setCoverRes(R.drawable.ic_svg_reset_blue_24);
        reset.setTitle("重置密码");
        reset.setSectionData(context.getResources().getString(R.string.account_setting));
        reset.setType(MineListBean.TYPE_NULL);
        reset.setId(C.ACCOUNT.ID_RESET_PASSWORD);
        mData.add(reset);

        MineListBean hupu = new MineListBean();
        hupu.setCoverRes(R.drawable.ic_svg_hupu_red_24);
        hupu.setTitle("虎扑账号设置");
        hupu.setSectionData(context.getResources().getString(R.string.account_setting));
        hupu.setType(MineListBean.TYPE_NULL);
        hupu.setId(C.ACCOUNT.ID_HUPU);
        mData.add(hupu);

        MineListBean set = new MineListBean();
        set.setCoverRes(R.drawable.ic_svg_setting_orange_24);
        set.setTitle("常规设置");
        set.setSectionData(context.getResources().getString(R.string.account_setting));
        set.setType(MineListBean.TYPE_NULL);
        set.setId(C.ACCOUNT.ID_SETTING);
        mData.add(set);

//        MineListBean tel = new MineListBean();
//        tel.setCoverRes(R.drawable.ic_svg_telephone_orange_24);
//        tel.setTitle("电话设置");
//        tel.setSectionData(context.getResources().getString(R.string.account_setting));
//        tel.setType(MineListBean.TYPE_NULL);
//        tel.setId(C.ACCOUNT.ID_TEL);
//        mData.add(tel);

        MineListBean hupuBind = new MineListBean();
        hupuBind.setCoverRes(R.drawable.ic_svg_bind_hupu_colorful_24);
        hupuBind.setTitle("绑定JRS");
        hupuBind.setSectionData(context.getResources().getString(R.string.account_setting));
        hupuBind.setType(MineListBean.TYPE_NULL);
        hupuBind.setId(C.ACCOUNT.ID_HUPU_BIND);
        mData.add(hupuBind);

        return mData;
    }

    @Override
    public void updateUser(final LocalUser localUser) {
        HashMap<String, String> params = new HashMap<>();
        params.put(C.MINE.USERNAME, localUser.getUser_name());
        params.put(C.MINE.PASSWORD, localUser.getPassword());
        params.put(C.MINE.SCREENNAME, localUser.getScreen_name());
        params.put(C.MINE.EMAIL, localUser.getEmail());
        params.put(C.MINE.PHONE, localUser.getTel());
        params.put(C.MINE.QUESTION, localUser.getQuestion());
        params.put(C.MINE.ANSWER, localUser.getAnswer());
        addSubscription(MineApiFactory.updateNormalInfo(params).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    updateUserSuccess(rResponse, localUser);
                } else {
                    mView.updateInfoFailed(rResponse.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.updateInfoFailed(throwable.getMessage());
            }
        }));
    }

    @Override
    public void choosePhoto(Activity context) {
        Intent chooseIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseIntent.setType("image/*");
        context.startActivityForResult(chooseIntent, C.ACCOUNT.CODE_CHOOSE_PHOTO);
    }

    @Override
    public void loginHupu(String hupuUserName, String hupuPassword) {
        addSubscription(MineApiFactory.loginHupu(hupuUserName, hupuPassword).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(@NonNull ResponseBody body) throws Exception {
                String string = body.string();
                HupuUserData hupuUserData = JsonParser.parseWithGson(HupuUserData.class, string);
                if (hupuUserData != null && hupuUserData.is_login == 1) { // 登录成功
                    HupuUserData.LoginResult data = hupuUserData.result;
                    String cookie = URLDecoder.decode(C.NULL, "UTF-8");
                    String uid = cookie.split("\\|")[0];
//                    user.uid = uid;
//                    user.cookie = cookie;
                    SPManager.get().putString(C.SP.HUPU_TOKEN, data.token);
                    SPManager.get().putString(C.SP.HUPU_UID, data.uid);
                    SPManager.get().putString(C.SP.HUPU_NICKNAME, data.nickname);

                    mView.updateInfoSuccess("绑定成功");
                } else {
                    mView.updateInfoFailed("绑定失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.updateInfoFailed("绑定失败");
            }
        }));
    }

    @Override
    public void resetPassword(final LocalUser localUser, String passwordOld) {
        addSubscription(MineApiFactory.resetPassword(localUser.getUser_name(), passwordOld, localUser.getPassword()).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    updateUserSuccess(rResponse, localUser);
                } else {
                    mView.updateInfoFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.updateInfoFailed(throwable.getMessage());
            }
        }));
    }

    @Override
    public void updateHupuInfo(final LocalUser localUser) {
        addSubscription(MineApiFactory.updateHupuInfo(localUser.getUser_name(), localUser.getPassword(),
                localUser.getHupu_user_name(), localUser.getPassword()).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    updateUserSuccess(rResponse, localUser);
                } else {
                    mView.updateInfoFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.updateInfoFailed(throwable.getMessage());
            }
        }));


    }

    @Override
    public void updateCover(final LocalUser localUser) {
        addSubscription(MineApiFactory.updateCover(localUser.getUser_name(), localUser.getPassword(), localUser.getCover()).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    updateUserSuccess(rResponse, localUser);
                } else {
                    mView.updateInfoFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.updateInfoFailed(throwable.getMessage());
            }
        }));
    }

    public void updateUserSuccess(@NonNull RResponse rResponse, LocalUser localUser) {
        MyApp.getDaoSession().getLocalUserDao().insertOrReplace(localUser);
        MyApp.setCurrentUser(localUser);
        mView.updateInfoSuccess(rResponse.getData());
    }

    @Override
    public void takePhoto(Activity context) {
        File file = new File(C.DIR.PIC_DIR);
        String name = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".png";
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        // 调用摄像头程序
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//								图片文件
        File picture = new File(file, name);
        mUri = Uri.fromFile(picture);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        context.startActivityForResult(intent, C.ACCOUNT.CODE_TAKE_PHOTO);
    }

    @Override
    public void uploadCover(String path) {
        final LocalUser currentUser = MyApp.getCurrentUser();
        File file = FileUtils.getFileByPath(path);
        addSubscription(ApiFactory.uploadCover(currentUser.getUser_name(), currentUser.getPassword(), file, file.getName(), new ProgressListener() {
            @Override
            public void onProgressChange(long progress, long total, boolean done) {
                DialogUtil.setProgress((int) (progress * 100 / total));
            }
        }).subscribe(new Consumer<RResponse>() {
            @Override
            public void accept(@NonNull RResponse rResponse) throws Exception {
                if (rResponse.getStatus() == C.RESPONSE_SUCCESS) {
                    currentUser.setCover(rResponse.getData());
                    updateUserSuccess(rResponse, currentUser);
                } else {
                    mView.uploadCoverFailed(rResponse.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mView.uploadCoverFailed(throwable.getMessage());
            }
        }));
    }

    public Uri getUri() {
        return mUri;
    }

}
