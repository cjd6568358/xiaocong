package com.tencent.android.tpush.c.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.service.e.h;
import com.tencent.android.tpush.service.e.m;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends com.tencent.android.tpush.c.d {
    Object a;
    Context b;
    BroadcastReceiver c = null;
    String d;

    @Override // com.tencent.android.tpush.c.d
    public void a(Context context) throws Throwable {
        t.a("registerPush huawei", context);
        e(context);
        try {
            this.b = context;
            Class<?> cls = Class.forName("com.huawei.hms.api.HuaweiApiClient");
            Class<?> cls2 = Class.forName("com.huawei.hms.api.HuaweiApiClient$Builder");
            Constructor<?> declaredConstructor = cls2.getDeclaredConstructor(Context.class);
            Class<?> clsLoadClass = cls2.getClassLoader().loadClass("com.huawei.hms.api.HuaweiApiClient$OnConnectionFailedListener");
            Class<?> cls3 = Class.forName("com.huawei.hms.api.HuaweiApiClient$ConnectionCallbacks");
            d dVar = new d(this);
            Object objNewProxyInstance = Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls3}, dVar);
            Object objNewProxyInstance2 = Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{clsLoadClass}, dVar);
            Object objNewInstance = declaredConstructor.newInstance(context);
            Class<?> cls4 = Class.forName("com.huawei.hms.support.api.push.HuaweiPush");
            Class<?> cls5 = Class.forName("com.huawei.hms.api.Api");
            cls2.getDeclaredMethod("addApi", cls5).invoke(objNewInstance, cls4.getDeclaredField("PUSH_API").get(cls5));
            cls2.getDeclaredMethod("addConnectionCallbacks", cls3).invoke(objNewInstance, objNewProxyInstance);
            cls2.getDeclaredMethod("addOnConnectionFailedListener", clsLoadClass).invoke(objNewInstance, objNewProxyInstance2);
            this.a = cls2.getDeclaredMethod("build", new Class[0]).invoke(objNewInstance, new Object[0]);
            cls.getDeclaredMethod("connect", new Class[0]).invoke(this.a, new Object[0]);
            t.a("connect to huawei", context);
        } catch (Throwable th) {
            t.a("register =  " + th.getMessage(), context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() throws Throwable {
        t.a("getTokenAsyn", this.b);
        try {
            Class<?> cls = Class.forName("com.huawei.hms.support.api.client.ApiClient");
            if (((Boolean) cls.getDeclaredMethod("isConnected", new Class[0]).invoke(this.a, new Object[0])).booleanValue()) {
                Class<?> cls2 = Class.forName("com.huawei.hms.support.api.push.HuaweiPush");
                Class.forName("com.huawei.hms.support.api.push.HuaweiPushApi");
                Object obj = cls2.getDeclaredField("HuaweiPushApi").get(cls2);
                Object objInvoke = obj.getClass().getDeclaredMethod("getToken", cls).invoke(obj, this.a);
                Class<?> cls3 = Class.forName("com.huawei.hms.support.api.client.PendingResult");
                Class<?> cls4 = Class.forName("com.huawei.hms.support.api.client.ResultCallback");
                cls3.getDeclaredMethod("setResultCallback", cls4).invoke(objInvoke, Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls4}, new e(this)));
            } else {
                com.tencent.android.tpush.a.a.i("OtherPushHuaWeiImpl", "getTokenAsyn failed with unconnected");
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("OtherPushHuaWeiImpl", "registerPush ", th);
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public void b(Context context) {
        try {
            Class<?> cls = Class.forName("com.huawei.hms.support.api.client.ApiClient");
            if (((Boolean) cls.getDeclaredMethod("isConnected", new Class[0]).invoke(this.a, new Object[0])).booleanValue()) {
                Class<?> cls2 = Class.forName("com.huawei.hms.support.api.push.HuaweiPush");
                Class.forName("com.huawei.hms.support.api.push.HuaweiPushApi");
                cls2.getDeclaredField("HuaweiPushApi").get(cls2).getClass().getDeclaredMethod("deleteToken", cls, String.class).invoke(this.a, c(context));
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("OtherPushHuaWeiImpl", "unregisterPush ", e);
        }
    }

    private void e(Context context) throws Throwable {
        if (this.c == null) {
            this.c = new c(this);
            try {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.huawei.android.push.intent.REGISTRATION");
                intentFilter.addAction("com.huawei.android.push.intent.RECEIVE");
                intentFilter.addAction("com.huawei.intent.action.PUSH_STATE");
                context.registerReceiver(this.c, intentFilter);
            } catch (Throwable th) {
                t.a("registerReceiver error " + th.getLocalizedMessage(), this.b);
            }
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public String c(Context context) {
        return !m.b(this.d) ? this.d : h.a(context, "huawei_token", Constants.MAIN_VERSION_TAG);
    }

    @Override // com.tencent.android.tpush.c.d
    public boolean d(Context context) {
        return true;
    }

    @Override // com.tencent.android.tpush.c.d
    public String a() {
        return "huawei";
    }
}
