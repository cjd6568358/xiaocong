package com.youzan.jsbridge.dispatcher;

import android.text.TextUtils;
import com.youzan.jsbridge.method.Method;
import com.youzan.jsbridge.subscriber.Subscriber;
import com.youzan.jsbridge.util.Logger;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class MethodDispatcher<T extends Method> {
    protected Map<String, Subscriber<T>> mSubscribers = new HashMap();

    public abstract void doCall(T t, Subscriber<T> subscriber);

    public final boolean dispatch(T method) {
        Subscriber<T> subscriber;
        String name = method.getName();
        if (TextUtils.isEmpty(name) || (subscriber = this.mSubscribers.get(name)) == null) {
            return false;
        }
        doCall(method, subscriber);
        return true;
    }

    public final void subscribe(Subscriber<T> subscriber) {
        if (this.mSubscribers.get(subscriber.subscribe()) != null) {
            Logger.w("Subscriber named " + subscriber.subscribe() + " has already existed.");
        }
        this.mSubscribers.put(subscriber.subscribe(), subscriber);
    }
}
