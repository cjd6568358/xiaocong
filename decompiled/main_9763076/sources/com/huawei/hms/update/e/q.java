package com.huawei.hms.update.e;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.huawei.hms.activity.BridgeActivity;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.update.provider.UpdateProvider;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: UpdateWizard.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class q extends a implements com.huawei.hms.activity.a, com.huawei.hms.update.a.a.b {
    private WeakReference<Activity> a;
    private com.huawei.hms.update.a.a.a b;
    private b c;
    private com.huawei.hms.update.a.a.c d;
    private int e = -1;

    @Override // com.huawei.hms.activity.a
    public void a(Activity activity, boolean z) {
        this.a = new WeakReference<>(activity);
        if (z) {
            a(i.class);
        } else {
            e();
        }
    }

    @Override // com.huawei.hms.activity.a
    public void a() {
        g();
        j();
        com.huawei.hms.update.c.a.a((Class<?>) null);
        this.a = null;
    }

    public int d() {
        if (this.e == 1) {
            return 2001;
        }
        if (this.e == 2) {
            return 2002;
        }
        if (this.e == 3) {
            return PushConstants.NOTIFICATIONSERVICE_SEND_MESSAGE;
        }
        return 2001;
    }

    @Override // com.huawei.hms.activity.a
    public boolean a(int i, int i2, Intent intent) {
        Activity activityC = c();
        if (activityC == null || activityC.isFinishing()) {
            return false;
        }
        if (this.e == 1 && i == 2001) {
            if (a(activityC)) {
                a(0);
                return true;
            }
            a(8);
            return true;
        }
        if (this.e == 2 && i == 2002) {
            if (a(activityC)) {
                a(0);
                return true;
            }
            a(8, this.e);
            b(activityC);
            return true;
        }
        if (this.e != 3 || i != 2003) {
            return false;
        }
        if (a(activityC)) {
            a(0);
            return true;
        }
        a(8);
        return true;
    }

    private boolean a(Activity activity) {
        return new com.huawei.hms.c.e(activity).b(HuaweiApiAvailability.SERVICES_PACKAGE) >= 20502300;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.huawei.hms.activity.a
    public void b() {
        if (this.c != null) {
            Class<?> cls = this.c.getClass();
            this.c.c();
            this.c = null;
            a((Class<? extends b>) cls);
        }
    }

    @Override // com.huawei.hms.update.a.a.b
    public void a(int i, com.huawei.hms.update.a.a.c cVar) {
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("UpdateWizard", "Enter onCheckUpdate, status: " + com.huawei.hms.update.a.a.d.a(i));
        }
        switch (i) {
            case 1000:
                this.d = cVar;
                a(h.class);
                i();
                break;
            case 1201:
            case 1202:
            case 1203:
                a(m.b.class);
                break;
        }
    }

    @Override // com.huawei.hms.update.a.a.b
    public void a(int i, int i2, int i3, File file) {
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("UpdateWizard", "Enter onDownloadPackage, status: " + com.huawei.hms.update.a.a.d.a(i) + ", reveived: " + i2 + ", total: " + i3);
        }
        switch (i) {
            case BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN /* 2000 */:
                g();
                if (file == null) {
                    a(8);
                } else {
                    a(file);
                }
                break;
            case PushConstants.BROADCAST_MESSAGE_ARRIVE /* 2100 */:
                if (this.c != null && (this.c instanceof h)) {
                    ((h) this.c).a(i2, i3);
                    break;
                }
                break;
            case PushConstants.ONTIME_NOTIFICATION /* 2201 */:
                if (this.d != null && this.b != null) {
                    this.d.c(this.b.a());
                }
                a(m.c.class);
                break;
            case PushConstants.DELAY_NOTIFICATION /* 2202 */:
                a(e.b.class);
                break;
            case 2203:
            case 2204:
                a(m.d.class);
                break;
        }
    }

    private void a(File file) {
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            if (!new com.huawei.hms.c.e(activityC).a(file.toString(), HuaweiApiAvailability.SERVICES_PACKAGE, HuaweiApiAvailability.SERVICES_SIGNATURE)) {
                com.huawei.hms.support.log.a.d("UpdateWizard", "In startInstaller, Failed to verify package archive.");
                a(8);
                return;
            }
            Uri uriA = a(activityC, file);
            if (uriA == null) {
                com.huawei.hms.support.log.a.d("UpdateWizard", "In startInstaller, Failed to creates a Uri from a file.");
                a(8);
                return;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(uriA, "application/vnd.android.package-archive");
            intent.setFlags(3);
            try {
                activityC.startActivityForResult(intent, d());
            } catch (ActivityNotFoundException e) {
                com.huawei.hms.support.log.a.d("UpdateWizard", "In startInstaller, Failed to start package installer." + e.getMessage());
                a(8);
            }
        }
    }

    private static Uri a(Context context, File file) {
        boolean z = true;
        com.huawei.hms.c.e eVar = new com.huawei.hms.c.e(context);
        String packageName = context.getPackageName();
        String str = packageName + UpdateProvider.AUTHORITIES_SUFFIX;
        if (Build.VERSION.SDK_INT <= 23 || (context.getApplicationInfo().targetSdkVersion <= 23 && !eVar.a(packageName, str))) {
            z = false;
        }
        if (z) {
            return UpdateProvider.getUriForFile(context, str, file);
        }
        return Uri.fromFile(file);
    }

    @Override // com.huawei.hms.update.e.a
    Activity c() {
        if (this.a == null) {
            return null;
        }
        return this.a.get();
    }

    @Override // com.huawei.hms.update.e.a
    void a(b bVar) {
        com.huawei.hms.support.log.a.b("UpdateWizard", "Enter onCancel.");
        if (bVar instanceof i) {
            if (com.huawei.hms.c.g.a()) {
                this.e = 1;
            } else {
                this.e = 2;
            }
            a(13);
            return;
        }
        if (bVar instanceof d) {
            j();
            a(13);
            return;
        }
        if (bVar instanceof h) {
            j();
            a(e.c.class);
        } else if (bVar instanceof e.c) {
            a(h.class);
            i();
        } else if (bVar instanceof e.b) {
            a(13);
        }
    }

    @Override // com.huawei.hms.update.e.a
    void b(b bVar) {
        com.huawei.hms.support.log.a.b("UpdateWizard", "Enter onDoWork.");
        if (bVar instanceof i) {
            bVar.c();
            e();
            return;
        }
        if (bVar instanceof e.c) {
            bVar.c();
            a(13);
            return;
        }
        if (bVar instanceof e.b) {
            a(h.class);
            i();
        } else if (bVar instanceof m.b) {
            a(8);
        } else if (bVar instanceof m.c) {
            a(8);
        } else if (bVar instanceof m.d) {
            a(8);
        }
    }

    private void e() {
        if (com.huawei.hms.c.g.a()) {
            this.e = 1;
            a(d.class);
            h();
            return;
        }
        f();
    }

    private void f() {
        this.e = 2;
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.huawei.hwid"));
                intent.setPackage("com.android.vending");
                activityC.startActivityForResult(intent, d());
            } catch (ActivityNotFoundException e) {
                com.huawei.hms.support.log.a.d("UpdateWizard", "can not open google play");
            }
        }
    }

    private void b(Activity activity) {
        this.e = 3;
        try {
            activity.startActivityForResult(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.huawei.hwid")), d());
        } catch (ActivityNotFoundException e) {
            com.huawei.hms.support.log.a.d("UpdateWizard", "can not find web to hold update hms apk");
        }
    }

    private void a(Class<? extends b> cls) {
        g();
        try {
            b bVarNewInstance = cls.newInstance();
            bVarNewInstance.a(this);
            this.c = bVarNewInstance;
        } catch (IllegalAccessException | IllegalStateException | InstantiationException e) {
            com.huawei.hms.support.log.a.d("UpdateWizard", "In showDialog, Failed to show the dialog." + e.getMessage());
        }
    }

    private void g() {
        if (this.c != null) {
            try {
                this.c.c();
                this.c = null;
            } catch (IllegalStateException e) {
                com.huawei.hms.support.log.a.d("UpdateWizard", "In dismissDialog, Failed to dismiss the dialog." + e.getMessage());
            }
        }
    }

    private void h() {
        this.d = null;
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            j();
            this.b = new com.huawei.hms.update.a.i(new com.huawei.hms.update.a.e(activityC));
            this.b.a(this);
        }
    }

    private void i() {
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            j();
            this.b = new com.huawei.hms.update.a.i(new com.huawei.hms.update.a.f(activityC));
            this.b.a(this, this.d);
        }
    }

    private void j() {
        if (this.b != null) {
            this.b.b();
            this.b = null;
        }
    }

    private void a(int i) {
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            a(i, this.e);
            Intent intent = new Intent();
            intent.putExtra(BridgeActivity.EXTRA_DELEGATE_CLASS_NAME, getClass().getName());
            intent.putExtra(BridgeActivity.EXTRA_RESULT, i);
            activityC.setResult(-1, intent);
            activityC.finish();
        }
    }

    @Override // com.huawei.hms.activity.a
    public void a(int i, KeyEvent keyEvent) {
        if (4 == i) {
            com.huawei.hms.support.log.a.b("UpdateWizard", "In onKeyUp, Call finish.");
            Activity activityC = c();
            if (activityC != null && !activityC.isFinishing()) {
                activityC.setResult(0, null);
                activityC.finish();
            }
        }
    }
}
