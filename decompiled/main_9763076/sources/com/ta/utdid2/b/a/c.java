package com.ta.utdid2.b.a;

import android.annotation.TargetApi;
import android.util.Base64;

/* JADX INFO: compiled from: Base64Helper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    @TargetApi(8)
    public static String encodeToString(byte[] input, int flags) {
        return Base64.encodeToString(input, flags);
    }
}
