package com.huawei.hms.update.e;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.huawei.android.hms.base.R;
import com.huawei.hms.activity.BridgeActivity;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import java.lang.ref.WeakReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: SilentUpdateWizard.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class o extends com.huawei.hms.update.e.a implements com.huawei.hms.activity.a {
    private WeakReference<Activity> a;
    private BroadcastReceiver b;
    private b d;
    private com.huawei.hms.activity.a f;
    private Handler c = new Handler();
    private boolean e = false;
    private Handler g = new p(this);

    @Override // com.huawei.hms.activity.a
    public void a(Activity activity, boolean z) {
        this.a = new WeakReference<>(activity);
        a(activity);
    }

    private void a(Activity activity) {
        Intent intent = new Intent("com.huawei.appmarket.intent.action.ThirdUpdateAction");
        intent.setPackage("com.huawei.appmarket");
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pkgName", HuaweiApiAvailability.SERVICES_PACKAGE);
            jSONObject.put("versioncode", 20502300);
            jSONArray.put(jSONObject);
            intent.putExtra(RNMessageModule.PARAMS, jSONArray.toString());
            intent.putExtra("isHmsOrApkUpgrade", true);
            intent.putExtra("buttonDlgY", activity.getString(R.string.hms_install));
            intent.putExtra("buttonDlgN", activity.getString(R.string.hms_cancel));
            intent.putExtra("upgradeDlgContent", activity.getString(R.string.hms_update_message_new, new Object[]{"%P"}));
            try {
                activity.startActivityForResult(intent, BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN);
            } catch (ActivityNotFoundException e) {
                com.huawei.hms.support.log.a.d("SilentUpdateWizard", "ActivityNotFoundException");
            }
        } catch (JSONException e2) {
            com.huawei.hms.support.log.a.d("SilentUpdateWizard", "create hmsJsonObject fail" + e2.getMessage());
        }
    }

    @Override // com.huawei.hms.activity.a
    public void a() {
        this.a = null;
        this.c.removeCallbacksAndMessages(null);
        e();
        if (this.d != null) {
            this.d.c();
            this.d = null;
        }
        if (this.e && this.f != null) {
            this.f.a();
        } else {
            com.huawei.hms.update.c.a.a((Class<?>) null);
        }
    }

    @Override // com.huawei.hms.activity.a
    public boolean a(int i, int i2, Intent intent) {
        if (this.e && this.f != null) {
            return this.f.a(i, i2, intent);
        }
        if (i != 2000) {
            return false;
        }
        if (i2 == 0) {
            d();
            a(20000);
            return true;
        }
        if (i2 == 4) {
            c(13);
            return true;
        }
        a(i2, 0);
        a(true);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.huawei.hms.activity.a
    public void b() {
        if (this.e && this.f != null) {
            this.f.b();
        } else if (this.d != null) {
            Class<?> cls = this.d.getClass();
            this.d.c();
            this.d = null;
            a((Class<? extends b>) cls);
        }
    }

    private void a(boolean z) {
        Activity activityC = c();
        if (activityC != null) {
            if (this.f == null) {
                b(activityC);
            }
            if (this.f != null) {
                this.e = true;
                com.huawei.hms.update.c.a.a(this.f.getClass());
                this.f.a(activityC, z);
            }
        }
    }

    private void b(Activity activity) {
        String stringExtra;
        Intent intent = activity.getIntent();
        if (intent != null && (stringExtra = intent.getStringExtra(BridgeActivity.EXTRA_DELEGATE_CLASS_EX_NAME)) != null) {
            try {
                this.f = (com.huawei.hms.activity.a) Class.forName(stringExtra).asSubclass(com.huawei.hms.activity.a.class).newInstance();
            } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                com.huawei.hms.support.log.a.d("SilentUpdateWizard", "getBridgeActivityDelegate error" + e.getMessage());
            }
        }
    }

    private void d() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.huawei.appmarket.service.downloadservice.Receiver");
        intentFilter.addAction("com.huawei.appmarket.service.downloadservice.progress.Receiver");
        intentFilter.addAction("com.huawei.appmarket.service.installerservice.Receiver");
        this.b = new com.huawei.hms.update.d.a(this.g);
        Activity activityC = c();
        if (activityC != null) {
            activityC.registerReceiver(this.b, intentFilter);
        }
    }

    private void e() {
        Activity activityC = c();
        if (activityC != null && this.b != null) {
            activityC.unregisterReceiver(this.b);
            this.b = null;
        }
    }

    @Override // com.huawei.hms.update.e.a
    void a(b bVar) {
        com.huawei.hms.support.log.a.a("SilentUpdateWizard", "on SilentUpdate cancelled");
    }

    @Override // com.huawei.hms.update.e.a
    void b(b bVar) {
        com.huawei.hms.support.log.a.a("SilentUpdateWizard", "on SilentUpdate dowork");
    }

    @Override // com.huawei.hms.update.e.a
    Activity c() {
        if (this.a == null) {
            return null;
        }
        return this.a.get();
    }

    private void a(int i) {
        this.c.removeCallbacksAndMessages(null);
        this.c.postDelayed(new a(this, null), i);
    }

    /* JADX INFO: compiled from: SilentUpdateWizard.java */
    private class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(o oVar, p pVar) {
            this();
        }

        @Override // java.lang.Runnable
        public void run() {
            o.this.b(14);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i) {
        this.c.removeCallbacksAndMessages(null);
        e();
        if (this.d != null) {
            this.d.c();
            this.d = null;
        }
        a(i, 0);
        a(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle) {
        String string = null;
        if (bundle.containsKey("UpgradePkgName")) {
            string = bundle.getString("UpgradePkgName");
        }
        if (string != null && HuaweiApiAvailability.SERVICES_PACKAGE.equals(string) && bundle.containsKey("downloadtask.status")) {
            int i = bundle.getInt("downloadtask.status");
            if (i == 3 || i == 5 || i == 6 || i == 8) {
                b(i);
            } else if (i == 4) {
                a(60000);
            } else {
                a(20000);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Bundle bundle) {
        String string = null;
        if (bundle.containsKey("UpgradePkgName")) {
            string = bundle.getString("UpgradePkgName");
        }
        if (string != null && HuaweiApiAvailability.SERVICES_PACKAGE.equals(string) && bundle.containsKey("UpgradeDownloadProgress") && bundle.containsKey("UpgradeAppName")) {
            int i = bundle.getInt("UpgradeDownloadProgress");
            a(20000);
            if (i >= 99) {
                i = 99;
            }
            if (this.d == null) {
                a(l.class);
            }
            if (this.d != null) {
                ((l) this.d).a(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Bundle bundle) {
        if (bundle.containsKey("packagename") && bundle.containsKey("status")) {
            String string = bundle.getString("packagename");
            int i = bundle.getInt("status");
            if (string != null && HuaweiApiAvailability.SERVICES_PACKAGE.equals(string)) {
                if (i == 2) {
                    this.c.removeCallbacksAndMessages(null);
                    if (this.d != null) {
                        ((l) this.d).a(100);
                    }
                    c(0);
                    return;
                }
                if (i == -1 || i == -2) {
                    b(i);
                } else {
                    a(60000);
                }
            }
        }
    }

    private void a(Class<? extends b> cls) {
        try {
            b bVarNewInstance = cls.newInstance();
            bVarNewInstance.a(this);
            this.d = bVarNewInstance;
        } catch (IllegalAccessException | IllegalStateException | InstantiationException e) {
            com.huawei.hms.support.log.a.d("SilentUpdateWizard", "In showDialog, Failed to show the dialog." + e.getMessage());
        }
    }

    private void c(int i) {
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            a(i, 0);
            Intent intent = new Intent();
            intent.putExtra(BridgeActivity.EXTRA_DELEGATE_CLASS_NAME, getClass().getName());
            intent.putExtra(BridgeActivity.EXTRA_RESULT, i);
            activityC.setResult(-1, intent);
            activityC.finish();
        }
    }

    @Override // com.huawei.hms.activity.a
    public void a(int i, KeyEvent keyEvent) {
        if (this.e && this.f != null) {
            this.f.a(i, keyEvent);
        }
    }
}
