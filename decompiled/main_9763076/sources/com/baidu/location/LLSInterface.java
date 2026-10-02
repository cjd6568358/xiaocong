package com.baidu.location;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface LLSInterface {
    double getVersion();

    IBinder onBind(Intent intent);

    void onCreate(Context context);

    void onDestroy();

    int onStartCommand(Intent intent, int i, int i2);

    void onTaskRemoved(Intent intent);

    boolean onUnBind(Intent intent);
}
