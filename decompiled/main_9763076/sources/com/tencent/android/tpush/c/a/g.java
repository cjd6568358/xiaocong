package com.tencent.android.tpush.c.a;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.service.e.m;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends com.tencent.android.tpush.c.d {
    @Override // com.tencent.android.tpush.c.d
    public String a() {
        return "xiaomi";
    }

    @Override // com.tencent.android.tpush.c.d
    public void a(Context context) {
        boolean z = false;
        if (m.b(com.tencent.android.tpush.c.e.a)) {
            com.tencent.android.tpush.a.a.j("OtherPushMiImpl", "registerPush Error for xiaomi null appid");
            return;
        }
        if (m.b(com.tencent.android.tpush.c.e.b)) {
            com.tencent.android.tpush.a.a.j("OtherPushMiImpl", "registerPush Error for xiaomi null miAppkey");
            return;
        }
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        String packageName = context.getPackageName();
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            z = (runningAppProcessInfo.pid == iMyPid && packageName.equals(runningAppProcessInfo.processName)) ? true : z;
        }
        if (z) {
            com.tencent.android.tpush.a.a.e("OtherPushMiImpl", "begin Mipush register!" + com.tencent.android.tpush.c.e.a + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + com.tencent.android.tpush.c.e.b);
            try {
                Class<?> cls = Class.forName("com.xiaomi.mipush.sdk.MiPushClient");
                Method method = cls.getMethod("registerPush", Context.class, String.class, String.class);
                com.tencent.android.tpush.a.a.e("OtherPushMiImpl", "begin Mipush register!" + com.tencent.android.tpush.c.e.a + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + com.tencent.android.tpush.c.e.b);
                method.invoke(cls, context, com.tencent.android.tpush.c.e.a, com.tencent.android.tpush.c.e.b);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                com.tencent.android.tpush.a.a.j("OtherPushMiImpl", "registerPush Error for InvocationTargetException: " + cause.getMessage());
                cause.printStackTrace();
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.d("OtherPushMiImpl", "registerPush Error ", e2);
            }
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public void b(Context context) {
        try {
            Class<?> cls = Class.forName("com.xiaomi.mipush.sdk.MiPushClient");
            cls.getMethod("unregisterPush", Context.class).invoke(cls, context);
        } catch (InvocationTargetException e) {
            com.tencent.android.tpush.a.a.j("OtherPushMiImpl", "unregisterPush Error for InvocationTargetException: " + e.getCause().getMessage());
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.j("OtherPushMiImpl", "unregisterPush Error, are you import otherpush package? " + e2);
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public String c(Context context) {
        try {
            Class<?> cls = Class.forName("com.xiaomi.mipush.sdk.MiPushClient");
            Object objInvoke = cls.getMethod("getRegId", Context.class).invoke(cls, context);
            if (objInvoke != null) {
                return objInvoke.toString();
            }
        } catch (InvocationTargetException e) {
            com.tencent.android.tpush.a.a.j("OtherPushMiImpl", "getToken Error for InvocationTargetException: " + e.getCause().getMessage());
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.d("OtherPushMiImpl", "getToken Error", e2);
        }
        return null;
    }

    @Override // com.tencent.android.tpush.c.d
    public boolean d(Context context) {
        return (m.b(com.tencent.android.tpush.c.e.a) || m.b(com.tencent.android.tpush.c.e.b)) ? false : true;
    }
}
