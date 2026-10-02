package com.youzan.androidsdk.event;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AbsStateEvent implements Event {
    public abstract void call(Context context);

    public String subscribe() {
        return EventAPI.EVENT_PAGE_READY;
    }

    public final void call(Context context, String data) {
        call(context);
    }
}
