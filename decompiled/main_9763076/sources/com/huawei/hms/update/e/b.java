package com.huawei.hms.update.e;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

/* JADX INFO: compiled from: AbstractDialog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class b {
    private AlertDialog a;
    private a b;

    protected abstract AlertDialog a();

    public void a(a aVar) {
        this.b = aVar;
        if (f() == null || f().isFinishing()) {
            com.huawei.hms.support.log.a.d("AbstractDialog", "In show, The activity is null or finishing.");
            return;
        }
        this.a = a();
        this.a.setCanceledOnTouchOutside(false);
        this.a.setOnCancelListener(new c(this));
        this.a.show();
    }

    public void b() {
        if (this.a != null) {
            this.a.cancel();
        }
    }

    public void c() {
        if (this.a != null) {
            this.a.dismiss();
        }
    }

    protected void d() {
        if (this.b != null) {
            this.b.a(this);
        }
    }

    protected void e() {
        if (this.b != null) {
            this.b.b(this);
        }
    }

    protected Activity f() {
        if (this.b != null) {
            return this.b.c();
        }
        return null;
    }

    protected int g() {
        return (a(f()) == 0 || Build.VERSION.SDK_INT < 16) ? 3 : 0;
    }

    private static int a(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getIdentifier("androidhwext:style/Theme.Emui", null, null);
    }
}
