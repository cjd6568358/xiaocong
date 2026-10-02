package com.xiaomi.mipush.sdk;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public abstract class MiPushClient$MiPushClientCallback {
    private String category;

    protected String getCategory() {
        return this.category;
    }

    public void onCommandResult(String str, long j, String str2, List<String> list) {
    }

    public void onInitializeResult(long j, String str, String str2) {
    }

    public void onReceiveMessage(MiPushMessage miPushMessage) {
    }

    public void onReceiveMessage(String str, String str2, String str3, boolean z) {
    }

    public void onSubscribeResult(long j, String str, String str2) {
    }

    public void onUnsubscribeResult(long j, String str, String str2) {
    }
}
