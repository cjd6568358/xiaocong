package com.huawei.hms.support.api.client;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ApiClient {
    String getAppID();

    Context getContext();

    String getPackageName();

    String getSessionId();

    SubAppInfo getSubAppInfo();

    String getTransportName();

    boolean isConnected();
}
