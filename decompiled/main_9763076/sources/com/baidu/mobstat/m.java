package com.baidu.mobstat;

import android.content.Context;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m {
    static m a = new m();

    m() {
    }

    public synchronized void a(Context context) {
        String strL = de.l(context);
        if (!TextUtils.isEmpty(strL)) {
            y.a.a(System.currentTimeMillis(), strL);
        }
    }
}
