package com.alibaba.sdk.android.utils.crashdefend;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: compiled from: CrashDefendManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static c b = null;
    private Context a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.utils.c f91a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private d f94b;
    private boolean d;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.b f92a = new com.alibaba.sdk.android.utils.crashdefend.b();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private List<d> f95b = new ArrayList();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private ExecutorService f96b = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private com.alibaba.sdk.android.utils.crashdefend.a f93b = null;
    private Map<String, String> e = new HashMap();

    public static synchronized c a(Context context, com.alibaba.sdk.android.utils.c cVar) {
        if (b == null) {
            b = new c(context, cVar);
        }
        return b;
    }

    private c(Context context, com.alibaba.sdk.android.utils.c cVar) {
        this.d = true;
        this.a = context;
        this.d = f.a(context);
        this.f91a = cVar;
        this.e.put("sdkId", "utils");
        this.e.put("sdkVersion", "1.1.3");
        if (this.d) {
            a();
            b();
            c();
        }
    }

    private void a() {
        if (f.m56a(this.a, this.f92a, this.f95b)) {
            this.f92a.a++;
        } else {
            this.f92a.a = 1L;
        }
    }

    private void b() {
        this.f93b = com.alibaba.sdk.android.utils.crashdefend.a.a(this.a);
        for (d dVar : this.f95b) {
            if (dVar != null) {
                this.f93b.a(dVar, new b());
            }
        }
    }

    private void c() {
        this.f94b = null;
        ArrayList<d> arrayList = new ArrayList();
        ArrayList<d> arrayList2 = new ArrayList();
        for (d dVar : this.f95b) {
            if (dVar.c == 0) {
                if (dVar.crashCount >= dVar.a) {
                    arrayList.add(dVar);
                } else {
                    arrayList2.add(dVar);
                }
            }
        }
        int[] iArr = new int[5];
        for (int i = 0; i < 5; i++) {
            iArr[i] = (i * 5) + 5;
        }
        for (d dVar2 : arrayList) {
            if (dVar2.d >= 5) {
                Log.i("UtilsSDK", "SDK " + dVar2.f99a + " has been closed");
            } else {
                long j = this.f92a.a - ((long) iArr[dVar2.d]);
                if (dVar2.f97a < j && dVar2.f102c < j) {
                    if (this.f94b == null) {
                        this.f94b = dVar2;
                    } else if (dVar2.f97a < this.f94b.f97a) {
                        this.f94b = dVar2;
                    } else if (dVar2.f97a == this.f94b.f97a && this.f94b.crashCount - this.f94b.a < dVar2.crashCount - dVar2.a) {
                        this.f94b = dVar2;
                    }
                }
            }
        }
        if (this.f94b == null) {
            Log.i("UtilsSDK", "NO SDK restore");
            return;
        }
        for (d dVar3 : arrayList2) {
            if (dVar3.crashCount > 0 && dVar3.f100b >= this.f94b.f100b) {
                this.f94b = null;
                break;
            }
        }
        if (this.f94b == null) {
            Log.i("UtilsSDK", "NO SDK restore");
            return;
        }
        this.f94b.d++;
        Log.i("UtilsSDK", this.f94b.f99a + " will restore --- startSerialNumber:" + this.f94b.f97a + "   crashCount:" + this.f94b.crashCount);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public boolean m55a(d dVar, SDKMessageCallback sDKMessageCallback) {
        d dVarA;
        if (!this.d) {
            Log.i("UtilsSDK", "NO Crash Defend Service");
            return false;
        }
        if (dVar == null || sDKMessageCallback == null || TextUtils.isEmpty(dVar.f101b) || TextUtils.isEmpty(dVar.f99a) || (dVarA = a(dVar, sDKMessageCallback)) == null) {
            return false;
        }
        boolean zM54a = m54a(dVarA);
        if (dVarA.crashCount == dVarA.a) {
            a(dVarA.f99a, dVarA.f101b, dVarA.crashCount, dVarA.a);
        }
        dVarA.crashCount++;
        f.a(this.a, this.f92a, this.f95b);
        if (zM54a) {
            a(dVarA);
            Log.i("UtilsSDK", "START:" + dVarA.f99a + " --- limit:" + dVarA.a + "  count:" + (dVarA.crashCount - 1) + "  restore:" + dVarA.d + "  startSerialNumber:" + dVarA.f97a + "  restoreSerialNumber:" + dVarA.f102c + "  registerSerialNumber:" + dVarA.f100b);
        } else {
            sDKMessageCallback.crashDefendMessage(dVarA.a, dVarA.crashCount - 1);
            Log.i("UtilsSDK", "STOP:" + dVarA.f99a + " --- limit:" + dVarA.a + "  count:" + (dVarA.crashCount - 1) + "  restore:" + dVarA.d + "  startSerialNumber:" + dVarA.f97a + "  restoreSerialNumber:" + dVarA.f102c + "  registerSerialNumber:" + dVarA.f100b);
        }
        return true;
    }

    public void d(String str, String str2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        boolean z;
        synchronized (this.f95b) {
            Iterator<d> it = this.f95b.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                }
                d next = it.next();
                if (next != null && next.f) {
                    z = false;
                    break;
                }
            }
        }
        if (z) {
            synchronized (this) {
                if (this.f96b != null && !this.f96b.isShutdown()) {
                    this.f96b.shutdown();
                    Log.i("UtilsSDK", "Thread Pool is close");
                }
            }
        }
    }

    private d a(d dVar, SDKMessageCallback sDKMessageCallback) {
        d next;
        synchronized (this.f95b) {
            if (this.f95b != null && this.f95b.size() > 0) {
                Iterator<d> it = this.f95b.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (next != null && next.f99a.equals(dVar.f99a)) {
                        if (!next.f101b.equals(dVar.f101b)) {
                            next.f101b = dVar.f101b;
                            next.a = dVar.a;
                            next.b = dVar.b;
                            next.c = 0;
                            e();
                            this.f93b.a(next, new b());
                        }
                        if (next.e) {
                            Log.i("UtilsSDK", "SDK " + dVar.f99a + " has been registered");
                            return null;
                        }
                        next.e = true;
                        next.f98a = sDKMessageCallback;
                        next.f100b = this.f92a.a;
                        break;
                    }
                }
            } else {
                next = null;
                break;
            }
            if (next == null) {
                next = (d) dVar.clone();
                next.e = true;
                next.f98a = sDKMessageCallback;
                next.crashCount = 0;
                next.f100b = this.f92a.a;
                this.f95b.add(next);
                this.f93b.a(next, new b());
            }
            return next;
        }
    }

    private void e() {
        for (d dVar : this.f95b) {
            dVar.crashCount = 0;
            dVar.d = 0;
            dVar.f102c = 0L;
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private boolean m54a(d dVar) {
        if (dVar.c == 1) {
            dVar.crashCount = 0;
            dVar.f97a = dVar.f100b;
            return true;
        }
        if (dVar.c == 2) {
            dVar.crashCount = dVar.a;
            return false;
        }
        if (dVar.crashCount < dVar.a) {
            dVar.f97a = dVar.f100b;
            return true;
        }
        if (this.f94b == null || !this.f94b.f99a.equals(dVar.f99a)) {
            return false;
        }
        dVar.crashCount = dVar.a - 1;
        dVar.f97a = dVar.f100b;
        dVar.f102c = dVar.f100b;
        return true;
    }

    private void a(d dVar) {
        if (dVar != null) {
            if (dVar.f98a != null) {
                dVar.f98a.crashDefendMessage(dVar.a, dVar.crashCount - 1);
            }
            e eVar = new e();
            eVar.a = dVar;
            eVar.e = dVar.b;
            eVar.a.f = true;
            a(eVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(d dVar) {
        if (dVar != null) {
            if (dVar.d > 0) {
                b(dVar.f99a, dVar.f101b, dVar.d, 5);
            }
            dVar.crashCount = 0;
            dVar.d = 0;
            dVar.f = false;
        }
    }

    private void a(e eVar) {
        if (eVar != null && eVar.a != null) {
            synchronized (this) {
                if (this.f96b == null) {
                    this.f96b = Executors.newCachedThreadPool();
                }
                if (this.f96b.isShutdown()) {
                    this.f96b = Executors.newCachedThreadPool();
                    Log.i("UtilsSDK", "Thread Pool is restart");
                }
                if (!this.f96b.isShutdown()) {
                    try {
                        this.f96b.execute(new a(eVar));
                    } catch (RejectedExecutionException e) {
                        Log.e("UtilsSDK", "add task fail:", e);
                    }
                }
            }
        }
    }

    private void a(String str, String str2, int i, int i2) {
        if (this.f91a != null) {
            HashMap map = new HashMap();
            map.putAll(this.e);
            map.put("crashSdkId", str);
            map.put("crashSdkVer", str2);
            map.put("curCrashCount", String.valueOf(i));
            map.put("crashThreshold", String.valueOf(i2));
            this.f91a.sendCustomHit("utils_biz_crash", 0L, map);
        }
    }

    private void b(String str, String str2, int i, int i2) {
        if (this.f91a != null) {
            HashMap map = new HashMap();
            map.putAll(this.e);
            map.put("crashSdkId", str);
            map.put("crashSdkVer", str2);
            map.put("recoverCount", String.valueOf(i));
            map.put("recoverThreshold", String.valueOf(i2));
            this.f91a.sendCustomHit("utils_biz_recover", 0L, map);
        }
    }

    /* JADX INFO: compiled from: CrashDefendManager.java */
    private class a implements Runnable {
        private e a;

        public a(e eVar) {
            this.a = eVar;
        }

        @Override // java.lang.Runnable
        public void run() {
            do {
                try {
                    Thread.sleep(1000L);
                    this.a.e--;
                } catch (InterruptedException e) {
                    c.this.d();
                    return;
                } catch (Throwable th) {
                    c.this.d();
                    throw th;
                }
            } while (this.a.e > 0);
            if (this.a.e <= 0) {
                c.this.b(this.a.a);
                f.a(c.this.a, c.this.f92a, (List<d>) c.this.f95b);
            }
            c.this.d();
        }
    }

    /* JADX INFO: compiled from: CrashDefendManager.java */
    private class b implements com.alibaba.sdk.android.utils.crashdefend.a.InterfaceC0004a {
        private b() {
        }

        @Override // com.alibaba.sdk.android.utils.crashdefend.a.InterfaceC0004a
        public void update() {
            f.a(c.this.a, c.this.f92a, (List<d>) c.this.f95b);
        }
    }
}
