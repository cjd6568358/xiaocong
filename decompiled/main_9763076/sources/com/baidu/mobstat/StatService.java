package com.baidu.mobstat;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StatService {
    private static boolean a = false;

    public interface WearListener {
        boolean onSendLogData(String str);
    }

    private static boolean a(Class<?> cls, String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean z = false;
        for (int i = 2; i < stackTrace.length; i++) {
            StackTraceElement stackTraceElement = stackTrace[i];
            if (stackTraceElement.getMethodName().equals(str)) {
                try {
                    for (Class<?> cls2 = Class.forName(stackTraceElement.getClassName()); cls2.getSuperclass() != null && cls2.getSuperclass() != cls; cls2 = cls2.getSuperclass()) {
                    }
                    z = true;
                } catch (Exception e) {
                    db.a(e);
                }
            }
        }
        return z;
    }

    private static String a(boolean z) {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            if (!TextUtils.isEmpty(className)) {
                Class<?> cls = null;
                try {
                    cls = Class.forName(className);
                } catch (Throwable th) {
                }
                if (cls != null && Activity.class.isAssignableFrom(cls)) {
                    if (z) {
                        return cls.getName();
                    }
                    return cls.getSimpleName();
                }
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static synchronized void onResume(Context context) {
        if (a(context, "onResume(...)")) {
            if (!a((Class<?>) Activity.class, "onResume")) {
                throw new SecurityException("onResume(Context context)不在Activity.onResume()中被调用||onResume(Context context)is not called in Activity.onResume().");
            }
            a(context);
            bv.a().a(context);
            ch.a().a(context, System.currentTimeMillis(), false);
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x000f A[Catch: all -> 0x002c, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x0007, B:11:0x0016, B:8:0x000f), top: B:16:0x0007 }] */
    public static synchronized void onPageStart(Context context, String str) {
        if (context == null || str == null) {
            db.c("onPageStart :parame=null || empty");
        } else if (str.equals(Constants.MAIN_VERSION_TAG)) {
            db.c("onPageStart :parame=null || empty");
        } else {
            a(context);
            bv.a().a(context);
            ch.a().a(context, System.currentTimeMillis(), str);
        }
        throw th;
    }

    /* JADX WARN: Code duplicated, block: B:8:0x000f A[Catch: all -> 0x004a, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x0007, B:12:0x0017, B:8:0x000f), top: B:17:0x0007 }] */
    private static synchronized void a(Context context, String str, ExtraInfo extraInfo) {
        if (context == null || str == null) {
            db.c("onPageEnd :parame=null || empty");
        } else if (str.equals(Constants.MAIN_VERSION_TAG)) {
            db.c("onPageEnd :parame=null || empty");
        } else {
            String strA = a(false);
            db.a("pageName is:" + str + "; activityName is:" + strA);
            ch.a().a(context, System.currentTimeMillis(), strA, str, extraInfo);
        }
        throw th;
    }

    public static synchronized void onPageEnd(Context context, String str) {
        a(context, str, null);
    }

    private static synchronized void a(Context context, ExtraInfo extraInfo) {
        if (a(context, "onPause(...)")) {
            if (!a((Class<?>) Activity.class, "onPause")) {
                throw new SecurityException("onPause(Context context)不在Activity.onPause()中被调用||onPause(Context context)is not called in Activity.onPause().");
            }
            ch.a().a(context, System.currentTimeMillis(), false, extraInfo);
        }
    }

    public static synchronized void onPause(Context context) {
        a(context, (ExtraInfo) null);
    }

    public static void setOn(Context context, int i) {
        if (a(context, "setOn(...)") && !a) {
            a = true;
            if ((i & 1) != 0) {
                a(context, false);
            } else if ((i & 16) != 0) {
                a(context, true);
            }
            a(context);
        }
    }

    public static void start(Context context) {
        if (a(context, "start(...)")) {
            boolean zA = dg.a(Application.class, "onCreate");
            if (zA) {
                db.c("method:start() 被 Application.onCreate()调用，not a good practice; 可能由于多进程反复重启等原因造成Application.onCreate() 方法多次被执行，导致启动次数高；建议埋点在统计路径触发的第一个页面中，比如APP主页面中");
            }
            a(context);
            bv.a().a(context, zA);
        }
    }

    private static void a(Context context, boolean z) {
        if (a(context, "onError(...)")) {
            bt.a().a(context.getApplicationContext(), z);
        }
    }

    private static boolean a(Context context, String str) {
        if (context != null) {
            return true;
        }
        db.b(str + ":context=null");
        return false;
    }

    public static void setDebugOn(boolean z) {
        db.a = z ? 2 : 7;
    }

    private static void a(Context context) {
        bf.a().a(context);
    }
}
