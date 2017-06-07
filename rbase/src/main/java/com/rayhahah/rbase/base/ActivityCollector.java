package com.rayhahah.rbase.base;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有的活动
 */
public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<Activity>();
    //建立弱引用
    public static WeakReference<List<Activity>> listWeakReference = new WeakReference<>(activities);

    public static void addActivity(Activity activity){
        listWeakReference.get().add(activity);
    }

    /**
     * 结束指定 activity
     * @param activity
     */
    public  static void finishActivity(Activity activity){
        listWeakReference.get().remove(activity);
        if(!activity.isFinishing()){
            activity.finish();
        }
    }

    /**
     * 清空所用Activity
     */
    public  static void finishAll(){
        for(Activity activity:listWeakReference.get()){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
