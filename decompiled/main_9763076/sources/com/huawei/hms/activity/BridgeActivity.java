package com.huawei.hms.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BridgeActivity extends Activity {
    public static final String EXTRA_DELEGATE_CLASS_EX_NAME = "intent.extra.DELEGATE_CLASS_OBJECT_EX";
    public static final String EXTRA_DELEGATE_CLASS_NAME = "intent.extra.DELEGATE_CLASS_OBJECT";
    public static final String EXTRA_RESULT = "intent.extra.RESULT";
    private a a;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        a();
        if (!b()) {
            setResult(1, null);
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.a != null) {
            this.a.a();
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.a != null) {
            this.a.b();
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.a != null && !this.a.a(i, i2, intent) && !isFinishing()) {
            setResult(i2, intent);
            finish();
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.a != null) {
            this.a.a(i, keyEvent);
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.app.Activity
    public void finish() {
        com.huawei.hms.support.log.a.b("BridgeActivity", "Enter finish.");
        super.finish();
    }

    private void a() {
        requestWindowFeature(1);
        if (com.huawei.hms.a.a.C0014a.a >= 9) {
            Window window = getWindow();
            window.addFlags(67108864);
            a(window, true);
        }
    }

    private boolean b() {
        Intent intent = getIntent();
        if (intent == null) {
            com.huawei.hms.support.log.a.d("BridgeActivity", "In initialize, Must not pass in a null intent.");
            return false;
        }
        String stringExtra = intent.getStringExtra(EXTRA_DELEGATE_CLASS_NAME);
        if (stringExtra == null) {
            com.huawei.hms.support.log.a.d("BridgeActivity", "In initialize, Must not pass in a null or non class object.");
            return false;
        }
        try {
            this.a = (a) Class.forName(stringExtra).asSubclass(a.class).newInstance();
            this.a.a((Activity) this, true);
            return true;
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            com.huawei.hms.support.log.a.d("BridgeActivity", "In initialize, Failed to create 'IUpdateWizard' instance." + e.getMessage());
            return false;
        }
    }

    private static void a(Window window, boolean z) {
        try {
            window.getClass().getMethod("setHwFloating", Boolean.TYPE).invoke(window, Boolean.valueOf(z));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            com.huawei.hms.support.log.a.d("BridgeActivity", "In setHwFloating, Failed to call Window.setHwFloating()." + e.getMessage());
        }
    }
}
