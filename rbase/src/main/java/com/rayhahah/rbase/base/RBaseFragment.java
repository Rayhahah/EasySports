package com.rayhahah.rbase.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayhahah.rbase.utils.useful.PermissionManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by a on 2017/5/14.
 */

public abstract class RBaseFragment<T extends IRBasePresenter, V extends ViewDataBinding>
        extends Fragment {


    protected View mView;
    protected Bundle savedInstanceState;
    private boolean isPrepare;
    private boolean isVisible;
    protected T mPresenter;
    protected CompositeSubscription compositeSubscription;
    protected ConcurrentHashMap<String, String> mValueMap = new ConcurrentHashMap<>();
    protected V mBinding;
    protected HashMap<String, Integer> mThemeColorMap;
    private boolean isFirstInit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(inflater, setFragmentLayoutRes(), container, false);
            mPresenter = getPresenter();
            isPrepare = true;
            isVisible = true;
            isFirstInit = true;
            onVisible();
        }
        this.savedInstanceState = savedInstanceState;
        return mBinding.getRoot();
    }


    /**
     * 在这里实现Fragment数据的懒加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestResult(requestCode, permissions, grantResults);
    }

    /**
     * 懒加载，触发调用数据封装层
     */
    private void onVisible() {
        if (!isPrepare || !isVisible) {
            return;
        }

        if (isFirstInit) {
            initValueFromPrePage();
            initThemeAttrs();
            initView(savedInstanceState);
            isFirstInit = false;
        } else {
            onRepeatVisible();

        }
    }

    /**
     * 重复可见调用方法
     */
    private void onRepeatVisible() {

    }

    /**
     * 不可见的时候
     */
    private void onInvisible() {

    }


    /**
     * 设置当前Fragment的布局文件资源
     *
     * @return
     */
    protected abstract int setFragmentLayoutRes();

    /**
     * 初始化页面
     *
     * @param savedInstanceState
     */
    public abstract void initView(Bundle savedInstanceState);

    protected abstract T getPresenter();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onUnsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 获取当前主题中的颜色
     *
     * @return
     */
    protected void initThemeAttrs() {
    }

    /**
     * RxJava取消注册，避免内存泄露
     * 取消以后就只能重新新建一个了
     */
    protected void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }


    /**
     * 添加事件监听处理到 事件管理类
     *
     * @param observable 启动响应
     * @param subscriber 监听响应
     */
    protected <T> void addSubscription(Observable observable, Subscriber<T> subscriber) {

        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
        );
    }

    /**
     * 从上个页面取得传递参数的值
     *
     * @param name
     * @return
     */
    public String getValueFromPrePage(String name) {
        return mValueMap.get(name);
    }

    /**
     * 将上个页面传递过来的参数值全部放到valueMap 中
     */
    public void initValueFromPrePage() {
        Bundle extBundle = getArguments() != null ? getArguments() : null;
        if (extBundle != null) {
            Iterator<String> it = extBundle.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = extBundle.getString(key);
                mValueMap.put(key, value);
            }
        }

    }
}
