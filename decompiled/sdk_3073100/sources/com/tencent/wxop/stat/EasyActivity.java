package com.tencent.wxop.stat;

import android.app.Activity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class EasyActivity extends Activity {
    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }
}
