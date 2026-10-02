package com.youzan.jsbridge;

import com.youzan.jsbridge.dispatcher.MethodDispatcher;
import com.youzan.jsbridge.method.JsMethod;
import com.youzan.jsbridge.method.JsMethodCompat;
import com.youzan.jsbridge.subscriber.MethodSubscriberCompat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class JsBridgeManager {
    private MethodDispatcher<JsMethod> mDispatcher;
    private MethodDispatcher<JsMethodCompat> mDispatcherCompat;

    public JsBridgeManager(MethodDispatcher<JsMethod> dispatcher, MethodDispatcher<JsMethodCompat> compatDispatcher) {
        this.mDispatcher = dispatcher;
        this.mDispatcherCompat = compatDispatcher;
    }

    @Deprecated
    public void subscribe(MethodSubscriberCompat subscriber) {
        this.mDispatcherCompat.subscribe(subscriber);
    }
}
