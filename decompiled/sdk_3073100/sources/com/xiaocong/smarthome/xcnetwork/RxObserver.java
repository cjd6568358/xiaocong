package com.xiaocong.smarthome.xcnetwork;

import android.content.Context;
import com.xiaocong.smarthome.xcnetwork.utils.XcLogger;
import rx.Subscriber;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class RxObserver<T> extends Subscriber<T> {
    protected Context context;

    public abstract void onSuccess(T t);

    public RxObserver(Context context) {
        this.context = context;
    }

    @Override // rx.Subscriber
    public void onStart() {
        super.onStart();
    }

    @Override // rx.Observer
    public void onCompleted() {
    }

    @Override // rx.Observer
    public void onError(Throwable e) {
        XcLogger.e("error", e.getMessage());
    }

    @Override // rx.Observer
    public void onNext(T t) {
        onSuccess(t);
    }
}
