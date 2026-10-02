package com.tencent.android.tpush.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushServiceV3 extends Service {
    private static Boolean d = null;
    public static long a = 0;
    public static int b = 0;
    public static JSONArray c = null;
    private static Service e = null;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void c() {
        com.tencent.android.tpush.common.g.a().a(new z(this));
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        a = System.currentTimeMillis();
        e = this;
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(-1998, new Notification());
        }
        Context applicationContext = getApplicationContext();
        com.tencent.android.tpush.service.d.a.a(applicationContext);
        n.d(applicationContext);
        c();
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.a("XGPushService", "onCreate() : " + getPackageName());
        }
        n.a().b();
        a();
    }

    public void a() {
        try {
            String strA = com.tencent.android.tpush.service.e.h.a(n.f(), "service_state", Constants.MAIN_VERSION_TAG);
            com.tencent.android.tpush.a.a.c("XGPushService", "reportLastServiceState " + strA);
            if (!com.tencent.android.tpush.service.e.m.b(strA)) {
                com.tencent.android.tpush.service.d.a.a(getApplicationContext(), "SdkService", new JSONObject(strA));
                com.tencent.android.tpush.service.e.h.b(n.f(), "service_state", Constants.MAIN_VERSION_TAG);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("XGPushService", "reportLastServiceState", th);
        }
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
    }

    public static Service b() {
        return e;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, 1, i2);
        b++;
        if (d == null) {
            d = true;
        } else {
            d = false;
        }
        if (com.tencent.android.tpush.common.t.a(getApplicationContext()) > 0) {
            com.tencent.android.tpush.service.e.m.y(getApplicationContext());
            return 2;
        }
        if (intent != null) {
            if (c == null) {
                c = new JSONArray();
            }
            String action = intent.getAction();
            if (!com.tencent.android.tpush.service.e.m.b(action) && c != null && c.length() < 10) {
                try {
                    action = action.replace(Constants.ACTION_PREFFIX, Constants.MAIN_VERSION_TAG);
                } catch (Throwable th) {
                }
                c.put(action);
            }
        }
        c();
        n.a().a(intent);
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() {
        n.a().c();
        super.onDestroy();
    }
}
