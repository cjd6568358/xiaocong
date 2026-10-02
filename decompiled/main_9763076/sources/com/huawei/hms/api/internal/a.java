package com.huawei.hms.api.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.KeyEvent;
import com.huawei.android.hms.base.R;
import com.huawei.hms.activity.BridgeActivity;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* JADX INFO: compiled from: BindingFailedResolution.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a implements ServiceConnection, com.huawei.hms.activity.a {
    private Activity a;
    private C0015a c;
    private boolean b = true;
    private Handler d = null;

    @Override // com.huawei.hms.activity.a
    public void a(Activity activity, boolean z) {
        this.a = activity;
        d.a.a(this.a);
        a(activity);
    }

    private void a(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(HuaweiApiAvailability.SERVICES_PACKAGE, HuaweiApiAvailability.ACTIVITY_NAME);
        try {
            activity.startActivityForResult(intent, c());
        } catch (ActivityNotFoundException e) {
            com.huawei.hms.support.log.a.d("BindingFailedResolution", "ActivityNotFoundException：" + e.getMessage());
            e();
        }
    }

    @Override // com.huawei.hms.activity.a
    public void a() {
        h();
        d.a.b(this.a);
        this.a = null;
    }

    @Override // com.huawei.hms.activity.a
    public boolean a(int i, int i2, Intent intent) {
        if (i != c()) {
            return false;
        }
        e();
        return true;
    }

    private void e() {
        if (f()) {
            g();
        } else {
            com.huawei.hms.support.log.a.d("BindingFailedResolution", "In connect, bind core try fail");
            b(false);
        }
    }

    @Override // com.huawei.hms.activity.a
    public void b() {
        if (this.c != null) {
            com.huawei.hms.support.log.a.b("BindingFailedResolution", "re show prompt dialog");
            i();
        }
    }

    public int c() {
        return PushConstants.NOTIFICATIONSERVICE_SEND_MESSAGE;
    }

    @Override // com.huawei.hms.activity.a
    public void a(int i, KeyEvent keyEvent) {
        com.huawei.hms.support.log.a.b("BindingFailedResolution", "On key up when resolve conn error");
    }

    protected Activity d() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z) {
        if (this.b) {
            this.b = false;
            a(z);
        }
    }

    protected void a(boolean z) {
        if (d() != null) {
            if (z) {
                a(0);
            } else {
                i();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: com.huawei.hms.api.internal.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: BindingFailedResolution.java */
    static class C0015a extends com.huawei.hms.b.f {
        private C0015a() {
        }

        /* synthetic */ C0015a(b bVar) {
            this();
        }

        @Override // com.huawei.hms.b.a
        protected String a(Context context) {
            return context.getResources().getString(R.string.hms_bindfaildlg_message, com.huawei.hms.c.g.a(context, (String) null), com.huawei.hms.c.g.a(context, HuaweiApiAvailability.SERVICES_PACKAGE));
        }

        @Override // com.huawei.hms.b.a
        protected String b(Context context) {
            return context.getResources().getString(R.string.hms_confirm);
        }
    }

    private boolean f() {
        Activity activityD = d();
        if (activityD == null) {
            return false;
        }
        Intent intent = new Intent(HuaweiApiAvailability.SERVICES_ACTION);
        intent.setPackage(HuaweiApiAvailability.SERVICES_PACKAGE);
        return activityD.bindService(intent, this, 1);
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        h();
        b(true);
        Activity activityD = d();
        if (activityD != null) {
            com.huawei.hms.c.g.a(activityD, this);
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        Activity activityD = d();
        if (activityD != null && !activityD.isFinishing()) {
            com.huawei.hms.support.log.a.b("BindingFailedResolution", "finishBridgeActivity：" + i);
            Intent intent = new Intent();
            intent.putExtra(BridgeActivity.EXTRA_RESULT, i);
            activityD.setResult(-1, intent);
            activityD.finish();
        }
    }

    private void g() {
        if (this.d != null) {
            this.d.removeMessages(2);
        } else {
            this.d = new Handler(Looper.getMainLooper(), new b(this));
        }
        this.d.sendEmptyMessageDelayed(2, 3000L);
    }

    private void h() {
        if (this.d != null) {
            this.d.removeMessages(2);
            this.d = null;
        }
    }

    private void i() {
        Activity activityD = d();
        if (activityD != null && !activityD.isFinishing()) {
            if (this.c == null) {
                this.c = new C0015a(null);
            } else {
                this.c.b();
            }
            com.huawei.hms.support.log.a.d("BindingFailedResolution", "showPromptdlg to resolve conn error");
            this.c.a(activityD, new c(this));
        }
    }
}
