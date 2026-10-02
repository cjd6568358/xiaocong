package com.baidu.mobstat;

import android.content.Context;
import android.support.v4.app.Fragment;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cp implements Runnable {
    final /* synthetic */ ch a;
    private long b;
    private long c;
    private WeakReference<Context> d;
    private WeakReference<Fragment> e;
    private WeakReference<Object> f;
    private long g;
    private int h;
    private int i;

    public cp(ch chVar, long j, long j2, long j3, Context context, Fragment fragment, Object obj, int i, int i2) {
        this.a = chVar;
        this.i = 1;
        this.b = j;
        this.c = j2;
        this.d = new WeakReference<>(context);
        this.e = new WeakReference<>(fragment);
        this.f = new WeakReference<>(obj);
        this.g = j3;
        this.h = i;
        this.i = i2;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        Context contextA;
        Context context = this.d.get();
        Fragment fragment = this.e.get();
        Object obj = this.f.get();
        if (context != null || fragment != null || obj != null) {
            if (this.i == 1) {
                contextA = context;
            } else if (this.i == 2) {
                contextA = fragment.getActivity();
            } else if (this.i != 3) {
                contextA = null;
            } else {
                contextA = ch.a(obj);
            }
            if (contextA != null) {
                if (this.c - this.b >= ((long) this.a.c())) {
                    if (this.b <= 0) {
                        if (this.b == 0) {
                            this.a.b(this.h);
                        }
                    } else {
                        if (this.i == 3 || this.i == 2) {
                            this.a.i.d(this.b);
                        }
                        this.a.a(contextA, true);
                        this.a.a(this.g);
                        this.a.b(this.h);
                    }
                }
            }
        }
    }
}
