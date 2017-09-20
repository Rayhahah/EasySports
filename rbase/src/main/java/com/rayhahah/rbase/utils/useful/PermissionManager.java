package com.rayhahah.rbase.utils.useful;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @time 2017/2/20
 * @fuction 动态权限管理封装类
 * @use 1. 在BaseActivity和BaseFragment重写onRequestPermissionsResult ,并实现如下代码PermissionManager.onRequestResult(requestCode, permissions, grantResults);
 * 2. 然后需要请求权限的时候调用requestPermission方法 即可
 */
public class PermissionManager {

    //维护的每个Activity的申请权限的监听
    //便于清除释放
    private static ConcurrentHashMap<Integer, PermissionsResultListener> mListenerMap = new ConcurrentHashMap<>();

    /**
     * 权限申请
     *
     * @param context             Activity
     * @param desc                再次申请权限的提示语
     * @param requestCode
     * @param permissionsListener
     * @param permissions
     */
    public static void requestPermission(Activity context,
                                         String desc,
                                         int requestCode,
                                         PermissionsResultListener permissionsListener,
                                         String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mListenerMap.put(requestCode, permissionsListener);
            if (checkEachSelfPermission(context, permissions)) {
                requestEachPermission(context, desc, permissions, requestCode);
            } else {
                mListenerMap.get(requestCode).onPermissionGranted(requestCode);
            }
        }
    }

    /**
     * 权限申请
     *
     * @param context             Fragment
     * @param desc                再次申请权限的提示语
     * @param requestCode
     * @param permissionsListener
     * @param permissions
     */
    public static void requestPermission(Fragment context,
                                         String desc,
                                         int requestCode,
                                         PermissionsResultListener permissionsListener,
                                         String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mListenerMap.put(requestCode, permissionsListener);

            if (checkEachSelfPermission(context.getActivity(), permissions)) {
                requestEachPermission(context, desc, permissions, requestCode);
            } else {
                mListenerMap.get(requestCode).onPermissionGranted(requestCode);
            }
        }
    }


    /**
     * 权限请求
     *
     * @param context     Activity
     * @param desc        再次申请权限的提示语
     * @param permissions
     * @param requestCode
     */
    private static void requestEachPermission(final Activity context, String desc, final String[] permissions, final int requestCode) {
        if (shouldShowRequestPermissionRationale(context, permissions)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("权限申请")
                    .setMessage(desc)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(context, permissions, requestCode);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            ActivityCompat.requestPermissions(context, permissions, requestCode);
        }
    }

    /**
     * 权限请求
     *
     * @param context     Fragment
     * @param desc        再次申请权限的提示语
     * @param permissions
     * @param requestCode
     */
    private static void requestEachPermission(final Fragment context, String desc, final String[] permissions, final int requestCode) {
        if (shouldShowRequestPermissionRationale(context, permissions)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
            builder.setTitle("权限申请")
                    .setMessage(desc)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            context.requestPermissions(permissions, requestCode);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            context.requestPermissions(permissions, requestCode);
        }
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param context     Activity
     * @param permissions
     * @return
     */
    private static boolean shouldShowRequestPermissionRationale(Activity context, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 再次申请权限时，是否需要声明
     *
     * @param context     Fragment
     * @param permissions
     * @return
     */
    private static boolean shouldShowRequestPermissionRationale(Fragment context, String[] permissions) {
        for (String permission : permissions) {
            if (context.shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */
    private static boolean checkEachSelfPermission(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 权限申请处理回调
     * 写在Activity或者Fragment的onRequestPermissionsResult 方法内
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestResult(int requestCode,
                                       @NonNull String[] permissions,
                                       @NonNull int[] grantResults) {
        PermissionsResultListener permissionsResultListener = mListenerMap.get(requestCode);
        if (permissionsResultListener == null) {
            return;
        }
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionsResultListener.onPermissionGranted(requestCode);
        } else {
            permissionsResultListener.onPermissionDenied(requestCode);
        }
    }

    /**
     * 清除掉已用完的Listner
     */
    public static void clearListner(int requestCode) {
        if (mListenerMap.get(requestCode) != null) {
            mListenerMap.remove(requestCode);
        }
    }


    public interface PermissionsResultListener {

        /**
         * 权限申请成功回调
         */
        void onPermissionGranted(int requestCode);

        /**
         * 权限申请失败回调
         */
        void onPermissionDenied(int requestCode);
    }

    /**
     * 危险权限 授权一个就等于同组都授权了
     * Manifest.permission.XXX
     *
     * group:android.permission-group.CONTACTS
     permission:android.permission.WRITE_CONTACTS
     permission:android.permission.GET_ACCOUNTS
     permission:android.permission.READ_CONTACTS

     group:android.permission-group.PHONE
     permission:android.permission.READ_CALL_LOG
     permission:android.permission.READ_PHONE_STATE
     permission:android.permission.CALL_PHONE
     permission:android.permission.WRITE_CALL_LOG
     permission:android.permission.USE_SIP
     permission:android.permission.PROCESS_OUTGOING_CALLS
     permission:com.android.voicemail.permission.ADD_VOICEMAIL

     group:android.permission-group.CALENDAR
     permission:android.permission.READ_CALENDAR
     permission:android.permission.WRITE_CALENDAR

     group:android.permission-group.CAMERA
     permission:android.permission.CAMERA

     group:android.permission-group.SENSORS
     permission:android.permission.BODY_SENSORS

     group:android.permission-group.LOCATION
     permission:android.permission.ACCESS_FINE_LOCATION
     permission:android.permission.ACCESS_COARSE_LOCATION

     group:android.permission-group.STORAGE
     permission:android.permission.READ_EXTERNAL_STORAGE
     permission:android.permission.WRITE_EXTERNAL_STORAGE

     group:android.permission-group.MICROPHONE
     permission:android.permission.RECORD_AUDIO

     group:android.permission-group.SMS
     permission:android.permission.READ_SMS
     permission:android.permission.RECEIVE_WAP_PUSH
     permission:android.permission.RECEIVE_MMS
     permission:android.permission.RECEIVE_SMS
     permission:android.permission.SEND_SMS
     permission:android.permission.READ_CELL_BROADCASTS
     */

}
