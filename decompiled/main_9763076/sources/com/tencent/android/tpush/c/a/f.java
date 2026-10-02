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
public class f extends com.tencent.android.tpush.c.d {
    @Override // com.tencent.android.tpush.c.d
    public String a() {
        return "meizu";
    }

    @Override // com.tencent.android.tpush.c.d
    public void a(Context context) {
        boolean z = false;
        if (m.b(com.tencent.android.tpush.c.e.c)) {
            com.tencent.android.tpush.a.a.j("OtherPushMZImpl", "registerPush Error for mz null appid");
            return;
        }
        if (m.b(com.tencent.android.tpush.c.e.d)) {
            com.tencent.android.tpush.a.a.j("OtherPushMZImpl", "registerPush Error for mz null mzAppkey");
            return;
        }
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        String packageName = context.getPackageName();
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            z = (runningAppProcessInfo.pid == iMyPid && packageName.equals(runningAppProcessInfo.processName)) ? true : z;
        }
        if (z) {
            com.tencent.android.tpush.a.a.e("OtherPushMZImpl", "begin Mzpush register!" + com.tencent.android.tpush.c.e.c + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + com.tencent.android.tpush.c.e.d);
            try {
                Class<?> cls = Class.forName("com.meizu.cloud.pushsdk.PushManager");
                new Class[1][0] = Context.class;
                Method method = cls.getMethod("register", Context.class, String.class, String.class);
                com.tencent.android.tpush.a.a.e("OtherPushMZImpl", "begin Mzpush register!" + com.tencent.android.tpush.c.e.c + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + com.tencent.android.tpush.c.e.d);
                method.invoke(cls, context, com.tencent.android.tpush.c.e.c, com.tencent.android.tpush.c.e.d);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                com.tencent.android.tpush.a.a.j("OtherPushMZImpl", "registerPush Error for InvocationTargetException: " + cause.getMessage());
                cause.printStackTrace();
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.d("OtherPushMZImpl", "registerPush Error ", e2);
            }
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public void b(Context context) {
        try {
            Class<?> cls = Class.forName("com.meizu.cloud.pushsdk.PushManager");
            new Class[1][0] = Context.class;
            Method method = cls.getMethod("unRegister", Context.class, String.class, String.class);
            com.tencent.android.tpush.a.a.e("OtherPushMZImpl", "begin Mzpush unregister!" + com.tencent.android.tpush.c.e.c + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + com.tencent.android.tpush.c.e.d);
            method.invoke(cls, context, com.tencent.android.tpush.c.e.c, com.tencent.android.tpush.c.e.d);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            com.tencent.android.tpush.a.a.j("OtherPushMZImpl", "unregisterPush Error for InvocationTargetException: " + cause.getMessage());
            cause.printStackTrace();
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.j("OtherPushMZImpl", "unregisterPush Error, are you import otherpush package? " + e2);
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public String c(Context context) {
        try {
            Class<?> cls = Class.forName("com.meizu.cloud.pushsdk.PushManager");
            Object objInvoke = cls.getMethod("getPushId", Context.class).invoke(cls, context);
            if (objInvoke != null) {
                return objInvoke.toString();
            }
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            com.tencent.android.tpush.a.a.j("OtherPushMZImpl", "getToken Error for InvocationTargetException: " + cause.getMessage());
            cause.printStackTrace();
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.d("OtherPushMZImpl", "getToken Error", e2);
        }
        return null;
    }

    @Override // com.tencent.android.tpush.c.d
    public boolean d(Context context) {
        return (m.b(com.tencent.android.tpush.c.e.c) || m.b(com.tencent.android.tpush.c.e.d)) ? false : true;
    }
}
