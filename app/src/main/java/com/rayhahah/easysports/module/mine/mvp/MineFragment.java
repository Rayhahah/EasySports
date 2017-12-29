package com.rayhahah.easysports.module.mine.mvp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDAlertDialog;
import com.rayhahah.dialoglib.MDEditDialog;
import com.rayhahah.easysports.R;
import com.rayhahah.easysports.app.C;
import com.rayhahah.easysports.app.MyApp;
import com.rayhahah.easysports.common.BaseFragment;
import com.rayhahah.easysports.common.RWebActivity;
import com.rayhahah.easysports.databinding.FragmentMineBinding;
import com.rayhahah.easysports.module.home.HomeActivity;
import com.rayhahah.easysports.module.mine.bean.MineListBean;
import com.rayhahah.easysports.module.mine.business.account.AccountActivity;
import com.rayhahah.easysports.module.mine.business.livelist.LiveListActivity;
import com.rayhahah.easysports.module.mine.business.login.LoginActivity;
import com.rayhahah.easysports.module.mine.business.teamplayer.SingleListActivity;
import com.rayhahah.easysports.module.mine.domain.MineListAdapter;
import com.rayhahah.easysports.net.version.VersionListner;
import com.rayhahah.easysports.net.version.VersionUpdateUtil;
import com.rayhahah.easysports.utils.DialogUtil;
import com.rayhahah.easysports.view.TitleItemDecoration;
import com.rayhahah.easysports.zxing.app.CaptureActivity;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.base.CacheUtils;
import com.rayhahah.rbase.utils.base.StringUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.PermissionManager;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by a on 2017/5/17.
 */

public class MineFragment extends BaseFragment<MinePresenter, FragmentMineBinding>
        implements MineContract.IMineView, BaseQuickAdapter.OnItemChildClickListener {

    private static final int REQUEST_QRCODE = 100;
    public static final int CAMERA_PERMISSIONS_REQUEST_CODE = 10;
    private List<MineListBean> mData;
    private MineListAdapter mMineListAdapter;
    private Disposable mDisposable;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mBinding.toolbar.tvToolbarTitle.setText(getResources().getString(R.string.mine));
        initRv();
        mPresenter.updateCurrentUser(mData.get(0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        PermissionManager.clearListner(CAMERA_PERMISSIONS_REQUEST_CODE);
    }

    private void initRv() {
        mData = mPresenter.getMineListData(mContext);
        mMineListAdapter = new MineListAdapter(mData) {
            @Override
            public void setItemCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!MyApp.isNightTheme()) {
                    SPManager.get().putString(C.SP.THEME, C.TRUE);
                    getActivity().setTheme(R.style.AppNightTheme);
                } else {
                    SPManager.get().putString(C.SP.THEME, C.FALSE);
                    getActivity().setTheme(R.style.AppDayTheme);
                }
                refreshUI();
            }
        };
        mBinding.rvMineList.setAdapter(mMineListAdapter);
        TitleItemDecoration decor = new TitleItemDecoration(getActivity()
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK)
                , mThemeColorMap.get(C.ATTRS.COLOR_BG_DARK)
                , mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK)
                , TitleItemDecoration.GRAVITY_LEFT
                , new TitleItemDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (position < mData.size()
                        && StringUtils.isNotEmpty(mData.get(position).getSectionData())) {
                    return mData.get(position).getSectionData();
                }
                return null;
            }

        }, new TitleItemDecoration.TitleTextCallback() {

            @Override
            public String getGroupFirstLine(int position) {
                if (position < mData.size()
                        && StringUtils.isNotEmpty(mData.get(position).getSectionData())) {
                    return mData.get(position).getSectionData();
                }
                return "";
            }

            @Override
            public String getActiveGroup() {
                return "";
            }
        });
        mMineListAdapter.setOnItemChildClickListener(this);
        mBinding.rvMineList.addItemDecoration(decor);
        mBinding.rvMineList.setLayoutManager(new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter(this);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onItemChildClick(final BaseQuickAdapter adapter, View view, int position) {
        List<MineListBean> data = adapter.getData();
        MineListBean bean = data.get(position);
        String isLogin = SPManager.get().getStringValue(C.SP.IS_LOGIN, C.FALSE);
        switch (bean.getId()) {
            //登陆与注册
            case C.MINE.ID_LOGIN:
                if (C.TRUE.equals(isLogin)) {
                    AccountActivity.start(mContext, mContext);
                } else {
                    LoginActivity.start(mContext, mContext);
                }
                break;
            //所有球队
            case C.MINE.ID_TEAM:
                SingleListActivity.start(getActivity(), getActivity(), SingleListActivity.TYPE_TEAM);
                break;
            //所有球员
            case C.MINE.ID_PLAYER:
                SingleListActivity.start(getActivity(), getActivity(), SingleListActivity.TYPE_PLAYER);
                break;
            //直播间
            case C.MINE.ID_LIVE:
                LiveListActivity.start(getActivity(), getActivity());
                break;
            //版本更新
            case C.MINE.ID_VERSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PermissionManager.requestPermission(mContext, "请求写入本地数据", 1, new PermissionManager.PermissionsResultListener() {
                        @Override
                        public void onPermissionGranted(int requestCode) {
                            VersionUpdateUtil.updateApp(mContext, new VersionListner() {
                                @Override
                                public void isLatest() {

                                }

                                @Override
                                public void updateSuccess(File file) {

                                }

                                @Override
                                public void updateFailed(Throwable throwable) {
                                    ToastUtils.showShort("更新失败");
                                }

                                @Override
                                public void onProgress(int progress, long total) {

                                }
                            });
                        }

                        @Override
                        public void onPermissionDenied(int requestCode) {
                            ToastUtils.showShort("请授权，否则无法开启该功能");

                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    VersionUpdateUtil.updateApp(mContext, new VersionListner() {
                        @Override
                        public void isLatest() {

                        }

                        @Override
                        public void updateSuccess(File file) {

                        }

                        @Override
                        public void updateFailed(Throwable throwable) {
                            ToastUtils.showShort("更新失败");
                        }

                        @Override
                        public void onProgress(int progress, long total) {

                        }
                    });
                }
                break;
            //扫一扫
            case C.MINE.ID_QRCODE:
                PermissionManager.requestPermission(this, "请求照相机权限", CAMERA_PERMISSIONS_REQUEST_CODE, new PermissionManager.PermissionsResultListener() {
                    @Override
                    public void onPermissionGranted(int requestCode) {
                        //启动二维码扫描的页面功能
                        Intent intent = new Intent(mContext, CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_QRCODE);
                    }

                    @Override
                    public void onPermissionDenied(int requestCode) {
                        ToastUtils.showShort("授权失败，无法启用扫一扫功能");
                    }
                }, Manifest.permission.CAMERA);

                break;
            //反馈信息
            case C.MINE.ID_FEEDBACK:
                if (C.TRUE.equals(isLogin)) {
                    MDEditDialog mdEditDialog = initFeedbackDialog();
                    mdEditDialog.show();
                } else {
                    ToastUtils.showShort("请先登录~");
                }
                break;
            //清除缓存
            case C.MINE.ID_CLEAN:
                MDAlertDialog dialog = initCleanDialog();
                dialog.show();
                break;
            //关于我们
            case C.MINE.ID_ABOUT:

                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_QRCODE) {
            switch (resultCode) {
                case CaptureActivity.RESULT_CODE_DECODE:
                case Activity.RESULT_OK:
                    String codeData = data.getStringExtra(CaptureActivity.EXTRA_DATA);
                    if (codeData.startsWith("http")) {
                        RWebActivity.start(mContext, mContext, codeData, getString(R.string.detail_content), true, true);
                    } else {
                        ToastUtils.showShort(codeData);
                    }
                    break;
                case CaptureActivity.RESULT_CODE_ENCODE:
                    // TODO: 2017/9/13 不可用
                    Bitmap bitmap2 = ((Bitmap) data.getParcelableExtra(CaptureActivity.EXTRA_DATA));
//                    Bitmap bitmap = ((Bitmap) data.getParcelableExtra("QR_CODE"));
                    mPresenter.saveBitmap(bitmap2);
                    break;
                default:
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccess(MsgEvent event) {
        if (C.EventAction.UPDATE_CURRENT_USER.equals(event.getAction())) {
            mPresenter.updateCurrentUser(mData.get(0));
        }
    }

    @Override
    public void commitFeedbackSuccess() {
        DialogUtil.dismissDialog(true);
        ToastUtils.showShort("感谢您的建议！");
    }

    @Override
    public void commitFeedbackFailed() {
        DialogUtil.dismissDialog(false);
        ToastUtils.showShort("提交失败~");
    }

    @Override
    public void updateCurrentUserSuccess(MineListBean mineListBean) {
        mData.set(0, mineListBean);
        mMineListAdapter.setNewData(mData);
    }

    @Override
    public void saveBitmapSuccess() {
        ToastUtils.showShort("生成二维码图片成功");
    }

    @Override
    public void saveBitmapFailed(Throwable throwable) {
        ToastUtils.showShort("生成二维码图片失败");
    }


    /**
     * 切换皮肤刷新UI
     */
    private void refreshUI() {
        SPManager.get().putString(C.SP.TAG_MINE_SELECTED, C.TRUE);
        getActivity().overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
        HomeActivity.start(getActivity(), getActivity());
    }

    /**
     * 初始化清除缓存dialog
     */
    private MDAlertDialog initCleanDialog() {
        return new MDAlertDialog.Builder(getActivity())
                .setDialogBgResource(R.drawable.shape_bg_corner4)
                .setTitleVisible(false)
                .setContentText(getResources().getString(R.string.mine_clean_ask))
                .setContentTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK))
                .setContentTextSize(20)
                .setButtonTextSize(16)
                .setLeftButtonTextColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))
                .setRightButtonTextColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDAlertDialog>() {
                    @Override
                    public void clickLeftButton(MDAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(MDAlertDialog dialog, View view) {
                        CacheUtils.cleanApplicationCache(getActivity());
                        ToastUtils.showShort(getResources().getString(R.string.mine_clean_success));
                        mMineListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setButtonBgResource(R.drawable.selector_md_dialog_color_primary)
                .setCanceledOnTouchOutside(true)
                .build();
    }

    /**
     * 初始化意见反馈弹窗
     */
    private MDEditDialog initFeedbackDialog() {
        MDEditDialog mdEditDialog = new MDEditDialog.Builder(getActivity())
                .setDialogBgResource(R.drawable.shape_bg_corner4)
                .setTitleTextSize(20)
                .setTitleTextColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))
                .setTitleText(getResources().getString(R.string.mine_feedback_title))
                .setContentTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_DARK))
                .setContentTextSize(15)
                .setInputTpye(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                .setLineColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))
                .setLineViewVisibility(View.GONE)
                //屏幕数值方向百分比
                .setMinHeight((float) 0.5)
                .setHintTextColor(mThemeColorMap.get(C.ATTRS.COLOR_TEXT_LIGHT))
                .setHintText(getResources().getString(R.string.mine_feedback_hint))
                .setButtonTextSize(20)
                .setLeftButtonTextColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))
                .setRightButtonTextColor(mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY))
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
                    @Override
                    public void clickLeftButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(final MDEditDialog dialog, View view) {
                        DialogUtil.showLoadingDialog(getActivity(), "提交ing", mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY));
                        mPresenter.uploadFeedback(mContext, dialog.getEditTextContent());
                        dialog.dismiss();
                    }
                })
                .setButtonBgResource(R.drawable.selector_md_dialog_color_primary)
                .setCanceledOnTouchOutside(false)
                .build();
        return mdEditDialog;
    }
}
