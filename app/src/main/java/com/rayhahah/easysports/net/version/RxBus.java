package com.rayhahah.easysports.net.version;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * Created by ywl on 2016/5/20.
 */
public class RxBus {

    private final Subject<Object> mBus = PublishSubject.create().toSerialized();
    private final Map<String, Object> tags = new HashMap<>();

    private static RxBus rxbus;

    public static RxBus getInstance() {
        if (rxbus == null) {
            synchronized (RxBus.class) {
                if (rxbus == null) {
                    rxbus = new RxBus();
                }
            }
        }
        return rxbus;
    }

    /**
     * 发送事件消息
     *
     * @param tag    用于区分事件
     * @param object 事件的参数
     */
    public void post(String tag, Object object) {
        mBus.onNext(object);
        if (!tags.containsKey(tag)) {
            tags.put(tag, object);
        }
    }

    /**
     * 主线程中执行
     *
     * @param tag
     * @param rxBusResult
     */
    public void toObserverableOnMainThread(final String tag, final RxBusResult rxBusResult) {

        mBus.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                if (tags.containsKey(tag)) {
                    rxBusResult.onRxBusResult(o);
                }
            }
        });
    }

    /**
     * 子线程中执行
     *
     * @param tag
     * @param rxBusResult
     */
    public void toObserverableChildThread(final String tag, final RxBusResult rxBusResult) {

        mBus.observeOn(Schedulers.io()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                if (tags.containsKey(tag)) {
                    rxBusResult.onRxBusResult(o);
                }
            }
        });
    }

    /**
     * 移除tag
     *
     * @param tag
     */
    public void removeObserverable(String tag) {
        if (tags.containsKey(tag)) {
            tags.remove(tag);
        }
    }

    /**
     * 退出应用时，清空资源
     */
    public void release() {
        tags.clear();
        rxbus = null;
    }
    /**
     * Created by ywl on 2016/5/20.
     */
    public interface RxBusResult {

        /**
         * 事件回调接口
         * @param o
         */
        void onRxBusResult(Object o);

    }
}
