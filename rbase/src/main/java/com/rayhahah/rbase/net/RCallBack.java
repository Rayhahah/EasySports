package com.rayhahah.rbase.net;

import rx.Subscriber;

/**
 * Created by a on 2017/6/8.
 */

public abstract class RCallBack<T extends Object> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

}
