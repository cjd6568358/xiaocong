package com.baidu.mobstat;

import android.app.Activity;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bh extends View.AccessibilityDelegate {
    final /* synthetic */ bf a;
    private View.AccessibilityDelegate b;
    private Activity c;
    private View d;
    private String e;

    public bh(bf bfVar, Activity activity, View view, String str, View.AccessibilityDelegate accessibilityDelegate) {
        this.a = bfVar;
        this.b = accessibilityDelegate;
        this.c = activity;
        this.d = view;
        this.e = str;
    }

    @Override // android.view.View.AccessibilityDelegate
    public void sendAccessibilityEvent(View view, int i) {
        if (view == this.d && i == 1) {
            this.a.b(this.c, this.d, this.e);
        }
        if (this.b != null) {
            this.b.sendAccessibilityEvent(view, i);
        }
    }
}
