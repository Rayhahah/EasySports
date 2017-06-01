package com.rayhahah.rbase.utils.useful;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.rayhahah.rbase.BaseApplication;
import com.rayhahah.rbase.utils.base.ThreadUtil;

import com.rayhahah.rbase.R;

/**
 * Gilde管理类
 */
public class GlideUtil {

    /**
     * 加载资源到ImageView上
     *
     * @param context   上下文
     * @param res       目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView 目标
     */
    public static void load(Context context, Object res, ImageView imageView) {

        loadBase(withContext(context), res, R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp,
                imageView, Priority.NORMAL, new BaseTransformation(), null, 0, null);
    }

    /**
     * 加载资源到ImageView上
     *
     * @param context   上下文
     * @param res       目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView 目标
     */
    public static void load(Activity context, Object res, ImageView imageView) {
        loadBase(withContext(context), res, R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp,
                imageView, Priority.NORMAL, new BaseTransformation(), null, 0, null);
    }

    /**
     * 加载资源到ImageView上
     *
     * @param context   上下文
     * @param res       目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView 目标
     */
    public static void load(Fragment context, Object res, ImageView imageView) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, new BaseTransformation(), null, 0, null);
    }

    /**
     * 加载资源到ImageView上
     *
     * @param context        上下文
     * @param res            目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView      目标
     * @param transformation 图像变形
     */
    public static void loadWithTransform(Fragment context, Object res, ImageView imageView, Transformation transformation) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, transformation, null, 0, null);
    }

    /**
     * 加载资源到ImageView上
     *
     * @param context        上下文
     * @param res            目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView      目标
     * @param transformation 图像变形
     */
    public static void loadWithTransform(Context context, Object res, ImageView imageView, Transformation transformation) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, transformation, null, 0, null);
    }

    /**
     * 加载资源到ImageView上
     *
     * @param context        上下文
     * @param res            目标资源，类型有： File、String、Uri、Integer resId
     * @param imageView      目标
     * @param transformation 图像变形
     */
    public static void loadWithTransform(Activity context, Object res, ImageView imageView, Transformation transformation) {
        loadBase(withContext(context), res,
                R.drawable.ic_insert_photo_placeholder_24dp,
                R.drawable.ic_broken_image_black_24dp, imageView,
                Priority.NORMAL, transformation, null, 0, null);
    }


    /**
     * 清除磁盘缓存
     * 只能在子线程执行
     */
    public static void clearDiskCache() {
        ThreadUtil.excute(new Runnable() {
            @Override
            public void run() {
                Glide.get(BaseApplication.getAppContext()).clearDiskCache();
            }
        });
    }

    /**
     * 清除内存缓存
     * 可以在UI线程执行
     */
    public static void clearMemory() {
        Glide.get(BaseApplication.getAppContext()).clearMemory();
    }


    private static <C extends Fragment> RequestManager withContext(C context) {
        return Glide.with(context);
    }

    private static <C extends Context> RequestManager withContext(C context) {
        return Glide.with(context);
    }


    private static <R extends Object> void loadBase(
            RequestManager manager, R res, int phRes, int errorRes,
            ImageView target, Priority priority,
            Transformation transformation,
            ViewPropertyAnimation.Animator animator,
            int crossDuration, RequestListener requestListener
    ) {

        manager.load(res)
                .listener(requestListener)
                .placeholder(phRes)
                .error(errorRes)
                .priority(priority)
                .animate(animator)
                .bitmapTransform(transformation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(crossDuration)
                .into(target);
    }

    static class BaseTransformation implements Transformation {

        @Override
        public Resource transform(Resource resource, int outWidth, int outHeight) {
            return resource;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
