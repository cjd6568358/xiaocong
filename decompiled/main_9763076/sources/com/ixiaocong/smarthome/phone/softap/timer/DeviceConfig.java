package com.ixiaocong.smarthome.phone.softap.timer;

import android.content.Context;
import java.util.Timer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DeviceConfig {
    protected Context mContext;
    protected Timer timer;

    public DeviceConfig(Context context) {
        this.mContext = context;
    }
}
