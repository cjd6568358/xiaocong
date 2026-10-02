package com.baidu.mobstat;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import com.tencent.bugly.BuglyStrategy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ch {
    private static final ch a = new ch();
    private static HashMap<String, cm> o = new HashMap<>();
    private boolean h;
    private volatile int k;
    private volatile long l;
    private Handler m;
    private cn b = new cn();
    private cn c = new cn();
    private cn d = new cn();
    private cn e = new cn();
    private long f = 0;
    private boolean g = true;
    private cf i = new cf();
    private int j = -1;
    private Runnable n = null;

    public static ch a() {
        return a;
    }

    private ch() {
        HandlerThread handlerThread = new HandlerThread("SessionAnalysisThread");
        handlerThread.start();
        handlerThread.setPriority(10);
        this.m = new Handler(handlerThread.getLooper());
    }

    public int b() {
        return this.k;
    }

    public int c() {
        if (this.j == -1) {
            this.j = BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH;
        }
        return this.j;
    }

    public void d() {
        this.i.a();
    }

    public void a(long j) {
        this.i.a(j);
    }

    public void b(long j) {
        this.i.b(j);
    }

    public void b(int i) {
        this.i.a(i);
    }

    public long e() {
        return this.i.b();
    }

    private boolean g() {
        return this.g;
    }

    private void a(boolean z) {
        this.g = z;
    }

    public void a(Context context, long j) {
        if (this.l == 0) {
            this.m.post(new ci(this, j));
        }
        this.l = j;
    }

    public void b(Context context, long j) {
        this.m.post(new cj(this, j, context));
    }

    public void a(Context context, long j, String str) {
        db.a("AnalysisPageStart");
        if (TextUtils.isEmpty(str)) {
            db.c("自定义页面 pageName 为 null");
            return;
        }
        cm cmVarB = b(str);
        if (cmVarB == null) {
            db.c("get page info, PageInfo null");
            return;
        }
        if (cmVarB.b) {
            db.c("遗漏StatService.onPageEnd() || missing StatService.onPageEnd()");
        }
        cmVarB.b = true;
        cmVarB.c = j;
        i();
        if (!this.h) {
            this.m.post(new cp(this, this.f, j, this.l, context, null, null, h(), 1));
            this.h = true;
        }
        this.b.b = new WeakReference<>(context);
        this.b.a = j;
    }

    private int h() throws ClassNotFoundException {
        Class<?> cls;
        Class<?> cls2;
        Class<?> cls3;
        try {
            cls = Class.forName("android.app.Fragment");
        } catch (ClassNotFoundException e) {
            cls = null;
        }
        try {
            cls2 = Class.forName("android.support.v4.app.Fragment");
        } catch (ClassNotFoundException e2) {
            cls2 = null;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            String methodName = stackTrace[i].getMethodName();
            if (!TextUtils.isEmpty(className) && !TextUtils.isEmpty(methodName) && methodName.equals("onResume")) {
                try {
                    cls3 = Class.forName(className);
                } catch (Throwable th) {
                    cls3 = null;
                }
                if (cls3 == null) {
                    continue;
                } else {
                    if (Activity.class.isAssignableFrom(cls3)) {
                        return 1;
                    }
                    if (cls != null && cls.isAssignableFrom(cls3)) {
                        return 2;
                    }
                    if (cls2 != null && cls2.isAssignableFrom(cls3)) {
                        return 2;
                    }
                }
            }
        }
        return 3;
    }

    public void a(Context context, long j, String str, String str2, ExtraInfo extraInfo) {
        db.a("post pause job");
        this.h = false;
        if (TextUtils.isEmpty(str2)) {
            db.c("自定义页面 pageName 无效值");
            return;
        }
        cm cmVarB = b(str2);
        if (cmVarB == null) {
            db.c("get page info, PageInfo null");
            return;
        }
        if (!cmVarB.b) {
            db.c("Please check (1)遗漏StatService.onPageStart() || missing StatService.onPageStart()");
            return;
        }
        cmVarB.b = false;
        cmVarB.d = j;
        this.m.post(new co(this, j, context, null, cmVarB.c, (Context) this.b.b.get(), null, 1, str2, null, null, str, false, extraInfo, cmVarB));
        c(str2);
        this.f = j;
    }

    public void a(Context context, long j, boolean z) {
        if (z) {
            this.e.c = true;
            this.e.b = new WeakReference<>(context);
            this.e.a = j;
        }
        db.a("AnalysisResume job");
        if (!z && this.b.c) {
            db.c("遗漏StatService.onPause() || missing StatService.onPause()");
        }
        if (!z) {
            this.b.c = true;
        }
        i();
        if (!this.h) {
            this.m.post(new cp(this, this.f, j, this.l, context, null, null, 1, 1));
            this.h = true;
        }
        this.b.b = new WeakReference<>(context);
        this.b.a = j;
    }

    private void i() {
        boolean zG = g();
        db.a("isFirstResume:" + zG);
        if (zG) {
            a(false);
            this.m.post(new ck(this));
        }
    }

    public void a(Context context, long j, boolean z, ExtraInfo extraInfo) {
        db.a("post pause job");
        this.h = false;
        if (z) {
            this.e.c = false;
            this.m.post(new co(this, j, context, null, this.e.a, (Context) this.e.b.get(), null, 1, null, null, null, null, z, extraInfo, null));
            this.f = j;
            return;
        }
        if (!this.b.c) {
            db.c("遗漏StatService.onResume() || missing StatService.onResume()");
            return;
        }
        this.b.c = false;
        this.m.post(new co(this, j, context, null, this.b.a, (Context) this.b.b.get(), null, 1, null, null, null, null, z, extraInfo, null));
        this.f = j;
    }

    public void a(Context context) {
        this.n = new cl(this, context);
        this.m.postDelayed(this.n, c());
    }

    public void f() {
        Runnable runnable = this.n;
        this.n = null;
        if (runnable != null) {
            this.m.removeCallbacks(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, boolean z) throws Throwable {
        if (this.i.c() > 0) {
            String string = this.i.d().toString();
            db.a("new session: " + string);
            DataCore.instance().putSession(string);
            DataCore.instance().flush(context);
            this.i.d(0L);
        }
        if (z) {
            d();
        }
        DataCore.instance().saveLogDataToSend(context, z, false);
        by.a().a(context);
        b(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context) throws Throwable {
        String string = this.i.d().toString();
        this.k = string.getBytes().length;
        db.a("cacheString = " + string);
        cu.a(context, de.q(context) + "__local_last_session.json", string, false);
    }

    public void b(Context context) throws Throwable {
        if (context == null) {
            db.a("clearLastSession context is null, invalid");
            return;
        }
        cu.a(context, de.q(context) + "__local_last_session.json", new JSONObject().toString(), false);
    }

    static Context a(Object obj) {
        try {
            return (Context) obj.getClass().getMethod("getActivity", new Class[0]).invoke(obj, new Object[0]);
        } catch (Throwable th) {
            db.a(th.getMessage());
            return null;
        }
    }

    private void a(String str) {
        synchronized (o) {
            try {
                if (str == null) {
                    db.c("page Object is null");
                    return;
                }
                cm cmVar = new cm(str);
                if (!o.containsKey(str)) {
                    o.put(str, cmVar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private cm b(String str) {
        cm cmVar;
        synchronized (o) {
            if (!o.containsKey(str)) {
                a(str);
            }
            cmVar = o.get(str);
        }
        return cmVar;
    }

    private void c(String str) {
        synchronized (o) {
            try {
                if (str == null) {
                    db.c("pageName is null");
                } else {
                    if (o.containsKey(str)) {
                        o.remove(str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
