package com.baidu.mobstat;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.tencent.android.tpush.common.Constants;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class co implements Runnable {
    final /* synthetic */ ch a;
    private long b;
    private WeakReference<Context> c;
    private WeakReference<Fragment> d;
    private WeakReference<Object> e;
    private long f;
    private WeakReference<Context> g;
    private WeakReference<Fragment> h;
    private WeakReference<Object> i;
    private int j;
    private String k;
    private String l;
    private boolean m;
    private ExtraInfo n;
    private cm o;

    public co(ch chVar, long j, Context context, Fragment fragment, long j2, Context context2, Fragment fragment2, int i, String str, Object obj, Object obj2, String str2, boolean z, ExtraInfo extraInfo, cm cmVar) {
        this.a = chVar;
        this.b = j;
        this.f = j2;
        this.c = new WeakReference<>(context);
        this.g = new WeakReference<>(context2);
        this.d = new WeakReference<>(fragment);
        this.h = new WeakReference<>(fragment2);
        this.i = new WeakReference<>(obj);
        this.e = new WeakReference<>(obj2);
        this.j = i;
        this.k = str;
        this.l = str2;
        this.m = z;
        this.n = extraInfo;
        this.o = cmVar;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        CharSequence title;
        if (this.j == 1) {
            Context context = this.c.get();
            Context context2 = this.g.get();
            if (context == null || context2 == null) {
                db.c("onPause, WeakReference is already been released");
                return;
            }
            if (context != context2) {
                if (this.k != null) {
                    db.b("onPageStart() or onPageEnd() install error.");
                    return;
                } else {
                    db.b("onPause() or onResume() install error.");
                    return;
                }
            }
            String string = Constants.MAIN_VERSION_TAG;
            long j = this.b - this.f;
            StringBuilder sb = new StringBuilder();
            if (this.k != null) {
                sb.append(this.k);
                if (this.o != null) {
                    j = this.o.d - this.o.c;
                    db.c("page time = " + this.o.a + "; time = " + j);
                    if (j < 20) {
                        db.c("page time little than 20 mills.");
                        return;
                    }
                }
            } else if (!(context instanceof Activity)) {
                db.c("onPause, pause is not a Activity");
                return;
            } else {
                sb.append(((Activity) context).getComponentName().getShortClassName());
                if (sb.charAt(0) == '.') {
                    sb.deleteCharAt(0);
                }
            }
            if ((context instanceof Activity) && (title = ((Activity) context).getTitle()) != null) {
                string = title.toString();
            }
            db.a("new page view, page name = " + sb.toString() + ", stay time = " + j + "(ms)");
            String string2 = sb.toString();
            if (this.k == null) {
                this.l = string2;
            }
            this.a.i.a(new cg(string2, string, this.l, j, this.f, this.m, this.n));
            if (this.k == null) {
                this.a.i.d(this.b);
                this.a.c(context);
                return;
            } else {
                if (this.o != null) {
                    this.a.i.d(this.o.d);
                    this.a.c(context);
                    return;
                }
                return;
            }
        }
        if (this.j == 2) {
            Fragment fragment = this.d.get();
            Fragment fragment2 = this.h.get();
            if (fragment == null || fragment2 == null) {
                db.c("onPause, WeakReference is already been released");
                return;
            }
            if (fragment != fragment2) {
                db.c("onPause() or onResume() install error.");
                return;
            }
            String string3 = Constants.MAIN_VERSION_TAG;
            FragmentActivity activity = fragment.getActivity();
            if (activity != null) {
                string3 = activity.getTitle().toString();
            }
            long j2 = this.b - this.f;
            String name = fragment.getClass().getName();
            String strSubstring = name.substring(name.lastIndexOf(".") + 1);
            db.a("Fragment new page view, page name = " + name.toString() + ", stay time = " + j2 + "(ms)");
            this.a.i.a(new cg(strSubstring, string3, strSubstring, j2, this.f, this.m, this.n));
            this.a.i.d(this.b);
            this.a.c(fragment.getActivity());
            return;
        }
        if (this.j == 3) {
            android.app.Fragment fragment3 = (android.app.Fragment) this.e.get();
            android.app.Fragment fragment4 = (android.app.Fragment) this.i.get();
            if (fragment3 == null || fragment4 == null) {
                db.c("onPause, WeakReference is already been released");
                return;
            }
            if (fragment3 != fragment4) {
                db.c("onPause() or onResume() install error.");
                return;
            }
            String string4 = Constants.MAIN_VERSION_TAG;
            Activity activity2 = fragment3.getActivity();
            if (activity2 != null) {
                string4 = activity2.getTitle().toString();
            }
            long j3 = this.b - this.f;
            Context contextA = ch.a(fragment3);
            if (contextA == null) {
                db.c("getContxtFromReverse faild.");
                return;
            }
            String name2 = fragment3.getClass().getName();
            String strSubstring2 = name2.substring(name2.lastIndexOf(".") + 1);
            db.a("android.app.Fragment new page view, page name = " + name2.toString() + "; stay time = " + j3 + "(ms)");
            this.a.i.a(new cg(strSubstring2, string4, strSubstring2, j3, this.f, this.m, this.n));
            this.a.i.d(this.b);
            this.a.c(contextA);
        }
    }
}
