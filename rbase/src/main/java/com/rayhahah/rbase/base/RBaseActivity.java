package com.rayhahah.rbase.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;

import com.rayhahah.rbase.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by a on 2017/5/10.
 */

public abstract class RBaseActivity<T extends RBasePresenter, V extends ViewDataBinding>
        extends AppCompatActivity {

    protected Activity mContext;
    protected T mPresenter;
    protected CompositeSubscription compositeSubscription;
    protected ConcurrentHashMap<String, String> paramMap = new ConcurrentHashMap<String, String>();
    protected ConcurrentHashMap<String, String> valueMap = new ConcurrentHashMap<String, String>();

    private String isNightTheme;
    protected V mBinding;

    protected FragmentManager fm;
    protected RBaseFragment currentFragment;
    protected HashMap<String, Integer> mThemeColorMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initTheme();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initWindowTransition(getWindowTransition());
        }
        setContentView(getLayoutID());
        ActivityCollector.addActivity(this);
        mBinding = DataBindingUtil.setContentView(mContext, getLayoutID());
        initThemeAttrs();
        setStatusColor();
        mPresenter = getPresenter();
        getValueFormPrePage();
        initEventAndData(savedInstanceState);
    }

    /**
     * 初始化视图以及数据操作
     *
     * @param savedInstanceState
     */
    protected abstract void initEventAndData(Bundle savedInstanceState);

    /**
     * 设置 Presenter
     *
     * @return
     */
    protected abstract T getPresenter();

    /**
     * 设置布局界面id
     *
     * @return
     */
    protected abstract int getLayoutID();

    /**
     * 设置Fragment容器id
     *
     * @return
     */
    protected abstract int setFragmentContainerResId();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        onUnsubscribe();
    }

    /**
     * 初始化主题颜色
     */
    protected void initTheme() {
//
    }

    /**
     * 初始化获取当前主题中的颜色
     *
     * @return
     */
    protected void initThemeAttrs() {
    }


    /**
     * 设置过渡动画
     * 默认是淡入淡出，可重写
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected Transition getWindowTransition() {
        return new Explode();
    }

    /**
     * 初始化过渡动画
     *
     * @param transition
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initWindowTransition(Transition transition) {
        getWindow().setReturnTransition(transition);
        getWindow().setExitTransition(transition);
        getWindow().setEnterTransition(transition);
        getWindow().setReenterTransition(transition);
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected void setStatusColor() {
        //获取主题中的颜色
//        TypedValue typedValue = new TypedValue();
//        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//        int color = typedValue.data;
//        StatusBarUtils.setColor(mContext, mThemeColorMap.get(C.ATTRS.COLOR_PRIMARY), 0);
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    protected void showFragment(RBaseFragment fragment, int position) {
        if (fm == null) {
            fm = getSupportFragmentManager();
        }
        /**
         * 参数传递
         */
        Bundle bundle = new Bundle();
        Iterator it = paramMap.keySet().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj != null) {
                String key = String.valueOf(obj);
                String value = paramMap.get(key);
                bundle.putString(key, value);
            }
        }

        FragmentTransaction transaction = fm.beginTransaction();
        //Fragment添加
        if (!fragment.isAdded()) {
            fragment.setArguments(bundle);
            transaction.add(setFragmentContainerResId(), fragment, position + "");
        }
        if (currentFragment == null) {
            currentFragment = fragment;
        }
        //通过tag进行过渡动画滑动判断
        if (Integer.parseInt(currentFragment.getTag()) >= Integer.parseInt(fragment.getTag())) {
            transaction.setCustomAnimations(R.anim.fragment_push_left_in, R.anim.fragment_push_right_out);
        } else {
            transaction.setCustomAnimations(R.anim.fragment_push_right_in, R.anim.fragment_push_left_out);
        }

        transaction.hide(currentFragment).show(fragment);
        transaction.commit();
        currentFragment = fragment;
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
     * RxJava取消注册，避免内存泄露
     * 取消以后就只能重新新建一个了
     */
    protected void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }


    /**
     * 将参数值传递到下个页面
     *
     * @param name
     * @param value
     */
    public void putParmToNextPage(String name, String value) {
        paramMap.put(name, value);
    }

    /**
     * 从上个页面取得传递参数的值
     *
     * @param name
     * @return
     */
    public String getParmValueFormPrePage(String name) {
        return valueMap.get(name);
    }

    /**
     * 将上个页面传递过来的参数值全部放到valueMap 中
     */
    public void getValueFormPrePage() {
        if (getIntent() == null) {
            return;
        }
        if (getIntent().getExtras() == null) {
            return;
        }
        Bundle extBundle = getIntent().getExtras();
        if (extBundle != null) {
            Iterator<String> it = extBundle.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (key == null) {
                    continue;
                }
                String value = extBundle.getString(key);
                valueMap.put(key, value);
            }
        }

    }

    /**
     * 跳转至下一个页面
     *
     * @param clazz
     */
    public void toNextActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        Bundle bundle = new Bundle();
        Iterator it = paramMap.keySet().iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj != null) {
                String key = String.valueOf(obj);
                String value = paramMap.get(key);
                bundle.putString(key, value);
            }
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void finish() {
        super.finish();
        ActivityCollector.finishActivity(this);
    }
}
