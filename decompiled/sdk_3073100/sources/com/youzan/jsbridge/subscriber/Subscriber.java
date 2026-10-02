package com.youzan.jsbridge.subscriber;

import com.youzan.jsbridge.method.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Subscriber<T extends Method> {
    void onCall(T t);

    String subscribe();
}
