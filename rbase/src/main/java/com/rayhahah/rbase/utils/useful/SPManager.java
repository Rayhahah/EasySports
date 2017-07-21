package com.rayhahah.rbase.utils.useful;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;

/**
 * @author Rayhahah
 * @blog http://www.jianshu.com/u/ec42ce134e8d
 * @time 2017/2/15
 * @fuction SharedPreferences管理工具类
 * @use 1.在MyApplication中init()
 * 2.使用get()来指定当前使用的SP，然后链式编程数据操作
 */

public class SPManager {

    private static ArrayMap<String, SharedPreferences> spMap = new ArrayMap<>();

    private static Context mContext;
    private static String mPackageName;
    private static SharedPreferences currentSP;
    private static SharedPreferences.Editor currentEditor;
    private static SPManager mInstance;
    private static ArrayMap<String, Object> valueMap;

    private static String DEFAULT_STRING = "";
    private static float DEFAULT_FLOAT = 0;
    private static int DEFAULT_INT = 0;
    private static long DEFAULT_LONG = 0;
    private static boolean DEFAULT_BOOLEAN = false;

    private SPManager() {

    }

    private SPManager(Context appContext) {
        mContext = appContext;
        mPackageName = appContext.getPackageName();
        SharedPreferences sp = mContext.getSharedPreferences(mPackageName, Context.MODE_PRIVATE);
        spMap.put(mPackageName, sp);
    }

    /**
     * 单例初始化，系统默认的SharedPreferences文件
     * (默认是当前应用包名)
     *
     * @param appContext 上下文
     */
    public static void init(Context appContext) {
        if (mContext == null && mInstance == null) {
            synchronized (SPManager.class) {
                if (mContext == null && mInstance == null) {
                    mInstance = new SPManager(appContext);
                }
            }
        }
    }


    /**
     * 指定当前链的SP对象
     * 默认使用包名SP
     *
     * @return
     */
    public static SPManager get() {
        return get(null);
    }

    /**
     * 指定当前链的SP对象
     *
     * @param spName
     * @return
     */
    public static SPManager get(String spName) {
        if (spName == null || "".equals(spName)) {
            currentSP = spMap.get(mPackageName);
        }

        if (spMap.containsKey(spName)) {
            currentSP = spMap.get(spName);
        } else {
            SharedPreferences sp = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
            spMap.put(spName, sp);
            currentSP = sp;
        }
        currentEditor = currentSP.edit();
        valueMap = new ArrayMap<>();

        return mInstance;
    }


    public static String getStringValue(String key) {
        return getStringValue(key, DEFAULT_STRING);
    }

    public static String getStringValue(String key, String default_value) {
        return currentSP.getString(key, default_value);
    }

    public static SPManager getString(String key) {
        return getString(key, DEFAULT_STRING);
    }

    public static SPManager getString(String key, String default_value) {
        String value = currentSP.getString(key, default_value);
        valueMap.put(key, value);
        return mInstance;
    }

    public static int getIntValue(String key) {
        return getIntValue(key, DEFAULT_INT);
    }

    private static int getIntValue(String key, int default_value) {
        return currentSP.getInt(key, default_value);
    }

    public static SPManager getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }

    public static SPManager getInt(String key, int default_value) {
        int value = currentSP.getInt(key, default_value);
        valueMap.put(key, value);
        return mInstance;
    }

    public static float getFloatValue(String key) {
        return getFloatValue(key, DEFAULT_FLOAT);
    }

    private static float getFloatValue(String key, float default_value) {
        return currentSP.getFloat(key, default_value);
    }

    public static SPManager getFloat(String key) {
        return getFloat(key, DEFAULT_FLOAT);
    }

    public static SPManager getFloat(String key, float default_value) {
        float value = currentSP.getFloat(key, default_value);
        valueMap.put(key, value);
        return mInstance;
    }

    public static long getLongValue(String key) {
        return getLongValue(key, DEFAULT_LONG);
    }

    public static long getLongValue(String key, long default_value) {
        return currentSP.getLong(key, default_value);
    }

    public static SPManager getLong(String key) {
        return getLong(key, DEFAULT_LONG);
    }

    public static SPManager getLong(String key, long default_value) {
        long value = currentSP.getLong(key, default_value);
        valueMap.put(key, value);
        return mInstance;
    }

    public static boolean getBooleanValue(String key) {
        return getBooleanValue(key, DEFAULT_BOOLEAN);
    }

    public static boolean getBooleanValue(String key, boolean default_value) {
        return currentSP.getBoolean(key, default_value);
    }

    public static SPManager getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    public static SPManager getBoolean(String key, boolean default_value) {
        boolean value = currentSP.getBoolean(key, default_value);
        valueMap.put(key, value);
        return mInstance;
    }

    /**
     * 导出获取的数据
     *
     * @return
     */
    public static ArrayMap<String, Object> exportValue() {
        return valueMap;
    }


    /**
     * 储存数据方法
     */

    public static SPManager putString(String key, String value) {
        currentEditor.putString(key, value);
        currentEditor.apply();
        return mInstance;
    }

    public static SPManager putInt(String key, int value) {
        currentEditor.putInt(key, value);
        currentEditor.apply();
        return mInstance;
    }

    public static SPManager putFloat(String key, float value) {
        currentEditor.putFloat(key, value);
        currentEditor.apply();
        return mInstance;
    }

    public static SPManager putLong(String key, long value) {
        currentEditor.putLong(key, value);
        currentEditor.apply();
        return mInstance;
    }

    public static SPManager putBoolean(String key, boolean value) {
        currentEditor.putBoolean(key, value);
        currentEditor.apply();
        return mInstance;
    }


    /**
     * 删除指定的SP文件，24API以后才开始支持
     *
     * @param spName
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void delete(String spName) {
        mContext.deleteSharedPreferences(spName);
    }


    /**
     * 清除当前链的SP的数据
     *
     * @return
     */
    public static SPManager clear() {
        if (currentSP == null) {
            return mInstance;
        }
        currentEditor.clear().apply();
        return mInstance;
    }
}
