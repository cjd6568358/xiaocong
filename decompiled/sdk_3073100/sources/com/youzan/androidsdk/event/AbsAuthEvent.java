package com.youzan.androidsdk.event;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AbsAuthEvent implements Event {
    public abstract void call(Context context, boolean z);

    public String subscribe() {
        return EventAPI.EVENT_AUTHENTICATION;
    }

    public final void call(Context context, String data) {
        call(context, EventAPI.SIGN_NEED_LOGIN.equals(data));
    }
}
