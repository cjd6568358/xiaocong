package com.ta.utdid2.b.a;

import android.annotation.TargetApi;
import android.content.SharedPreferences;

/* JADX INFO: compiled from: SharedPreferenceHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    @TargetApi(9)
    public static void a(SharedPreferences.Editor editor) {
        if (editor != null) {
            editor.apply();
        }
    }
}
