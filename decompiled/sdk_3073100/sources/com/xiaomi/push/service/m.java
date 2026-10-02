package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class m {
    private static volatile m a;
    private SharedPreferences b;

    private m(Context context) {
        this.b = context.getSharedPreferences("mipush", 0);
    }

    public static m a(Context context) {
        if (a == null) {
            synchronized (m.class) {
                if (a == null) {
                    a = new m(context);
                }
            }
        }
        return a;
    }

    public synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "0";
        }
        SharedPreferences.Editor editorEdit = this.b.edit();
        editorEdit.putString("miid", str);
        editorEdit.commit();
    }
}
