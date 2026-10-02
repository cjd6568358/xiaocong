package com.xiaomi.network;

import android.app.IntentService;
import android.content.Intent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HostRefreshService extends IntentService {
    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        HostManager.getInstance().refreshFallbacks();
    }
}
