package com.huawei.hms.b;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

/* JADX INFO: compiled from: AbstractDialog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    private Activity a;
    private AlertDialog b;
    private InterfaceC0016a c;

    /* JADX INFO: renamed from: com.huawei.hms.b.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: AbstractDialog.java */
    public interface InterfaceC0016a {
        void a(a aVar);

        void b(a aVar);
    }

    protected abstract String a(Context context);

    protected abstract String b(Context context);

    protected abstract String c(Context context);

    protected abstract String d(Context context);

    protected AlertDialog a(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(e(), f());
        String strC = c(activity);
        if (strC != null) {
            builder.setTitle(strC);
        }
        String strA = a((Context) activity);
        if (strA != null) {
            builder.setMessage(strA);
        }
        String strB = b(activity);
        if (strB != null) {
            builder.setPositiveButton(strB, new b(this));
        }
        String strD = d(activity);
        if (strD != null) {
            builder.setNegativeButton(strD, new c(this));
        }
        return builder.create();
    }

    public void a(Activity activity, InterfaceC0016a interfaceC0016a) {
        this.a = activity;
        this.c = interfaceC0016a;
        if (this.a == null || this.a.isFinishing()) {
            com.huawei.hms.support.log.a.d("AbstractDialog", "In show, The activity is null or finishing.");
            return;
        }
        this.b = a(this.a);
        this.b.setCanceledOnTouchOutside(false);
        this.b.setOnCancelListener(new d(this));
        this.b.setOnKeyListener(new e(this));
        this.b.show();
    }

    public void a() {
        if (this.b != null) {
            this.b.cancel();
        }
    }

    public void b() {
        if (this.b != null) {
            this.b.dismiss();
        }
    }

    protected void c() {
        if (this.c != null) {
            this.c.b(this);
        }
    }

    protected void d() {
        if (this.c != null) {
            this.c.a(this);
        }
    }

    protected Activity e() {
        return this.a;
    }

    protected int f() {
        return (e(this.a) == 0 || Build.VERSION.SDK_INT < 16) ? 3 : 0;
    }

    private static int e(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null);
    }
}
