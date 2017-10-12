package com.rayhahah.easysports.net.version;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.databinding.DialogUpdateVersionBinding;
import com.rayhahah.easysports.net.ApiClient;
import com.rayhahah.easysports.net.ApiStore;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.rbase.net.ProgressFileDownloader;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.RxSchedulers;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

import static com.rayhahah.easysports.net.version.VersionUpdateUtil.TAG.TAG_FINISH;
import static com.rayhahah.easysports.net.version.VersionUpdateUtil.TAG.TAG_PROGRESS;
import static com.rayhahah.easysports.net.version.VersionUpdateUtil.TAG.TAG_UPDATE_VERSION;


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
 * @time 2017/8/14
 * @tips 这个类是Object的子类
 * @fuction 版本更新工具类
 */
public class VersionUpdateUtil {

    public static final int TAG_NEEDED_UPDATE = 1;
    public static final int TAG_IS_LATEST = 0;
    private static VersionListner mListner;
    private static CustomDialog mDialog;

    //    public static final String TEST_APK_URL="ftp://v0.ftp.upyun.com/workmap_dianAn/test/workMapTest1.3.4.apk";
    public static final String TEST_APK_URL = "http://192.168.191.1:8080/jijian/api/workMapTest1.3.4.apk";

    public @interface TAG {
        String TAG_UPDATE_VERSION = "TAG_UPDATE_VERSION";

        String TAG_FINISH = "TAG_FINISH";
        String TAG_FAILED = "TAG_FAILED";
        String TAG_PROGRESS = "TAG_PROGRESS";

        String ACTION = "ACTION";
        String DATA = "DATA";
    }

    public static void updateApp(final Activity context, final VersionListner versionListner) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        final String versionName = pi.versionName;
        final String code = pi.versionCode + "";
        mListner = versionListner;
        requestCheckVersion().subscribe(new Consumer<VersionInfo>() {
            @Override
            public void accept(@NonNull VersionInfo versionInfo) throws Exception {
                if (versionInfo.getStatus() == C.RESPONSE_SUCCESS) {
                    checkAppVersion(context, versionInfo, code);
                } else {
                    mListner.updateFailed(null);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mListner.updateFailed(throwable);
            }
        });
    }

    private static void checkAppVersion(final Activity context, VersionInfo versionInfo, String currentVersion) throws PackageManager.NameNotFoundException {

        if (isNewVersion(currentVersion, versionInfo.getData().getVersionCode())) {
            final VersionInfo.DataBean data = versionInfo.getData();
            mDialog = createUpdateDialog(context, "发现新版本", "V" + data.getVersionName(), data.getVersionCode(), data.getDescription()
                    , DateTimeUitl.getDateTimeWithOutSenondString(Long.toString(data.getCreateTime())),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtil.showProgressDialog(context, Color.parseColor("#acacac"));
                            registerDownlistner();
                            Intent intent = new Intent(context, UpdateAppService.class);
                            intent.putExtra(UpdateAppService.CONS.URL, data.getUrl());
                            intent.putExtra(UpdateAppService.CONS.DIR_NAME, C.DIR.FILE);
                            intent.putExtra(UpdateAppService.CONS.FILE_NAME, C.APP_NAME + "V" + data.getVersionName());
                            context.startService(intent);
                            dismissDialog();
                            ToastUtils.showShort("正在下载最新APP");
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissDialog();
                        }
                    });
            mDialog.show();
        } else {
            ToastUtils.showShort("当前已是最新版本啦！");
        }
    }


    private static void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    private static CustomDialog createUpdateDialog(
            Activity context, String title, String version, String versionCode,
            String content, String time, View.OnClickListener confirmListner,
            View.OnClickListener cancelListner) {
        DialogUpdateVersionBinding versionBinding = (DialogUpdateVersionBinding) DataBindingUtil.inflate(context.getLayoutInflater()
                , R.layout.dialog_update_version, null, false);
        versionBinding.tvItemTitle.setText(title);
        versionBinding.tvVersionName.setText(version);
        versionBinding.tvVersionCode.setText(versionCode);
        versionBinding.tvDesc.setText(content);
        versionBinding.tvTime.setText(time);
        versionBinding.btnEditConfirm.setOnClickListener(confirmListner);
        versionBinding.btnEditCancel.setOnClickListener(cancelListner);
        CustomDialog customDialog = new CustomDialog.Builder(context)
                .setView(versionBinding.getRoot())
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        return customDialog;
    }

    private static void registerDownlistner() {
        RxBus.getInstance().toObserverableOnMainThread(TAG_UPDATE_VERSION, new RxBus.RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                if (!(o instanceof HashMap)) {
                    return;
                }
                HashMap<String, Object> map = (HashMap<String, Object>) o;
                if (TAG.TAG_FAILED.equals(map.get(TAG.ACTION))) {
                    mListner.updateFailed((Throwable) map.get(TAG.DATA));
                } else if (TAG_FINISH.equals(map.get(TAG.ACTION))) {
                    mListner.updateSuccess((File) map.get(TAG.DATA));
                } else if (TAG_PROGRESS.equals(map.get(TAG.ACTION))) {
                    mListner.onProgress((Integer) map.get(TAG.DATA), 0);
                }
            }
        });


    }

    /**
     * 通过判断VersionName,来判断是否是最新版本
     *
     * @param currentVersion 当前版本 1
     * @param newVersion     最新版本号 2
     * @return
     */
    private static boolean isNewVersion(String currentVersion, String newVersion) {
        return Integer.parseInt(currentVersion) >= Integer.parseInt(newVersion) ? false : true;
    }

    /**
     * 获取最新版本数据
     */
    private static Observable<VersionInfo> requestCheckVersion() {
        return ApiClient.get(C.BaseURL.RAYMALL)
                .create(ApiStore.class)
                .checkVersion()
                .compose(RxSchedulers.<VersionInfo>ioMain());
    }

    /**
     * 下载文件
     *
     * @param url        文件资源
     * @param tarDir     目标文件夹路径
     * @param fileName   目标文件名
     * @param downloader 下载管理器
     */
    public static void download(String url, final String tarDir, final String fileName
            , final ProgressFileDownloader<File> downloader) {
        ApiClient.get(C.BaseURL.RAYMALL)
                .create(ApiStore.class)
                .downLoadFile(url)
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
                        return downloader.saveFile(responseBody, tarDir, fileName);
                    }
                }).compose(RxSchedulers.<File>ioMain())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {
                        downloader.onDownLoadSuccess(file);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        downloader.onDownLoadFail(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        downloader.onComplete();
                    }
                });
    }


}
