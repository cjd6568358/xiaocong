package com.tencent.android.tpush;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPro {
    private static boolean a = false;

    public static void enableXGPro(Context context, boolean z) {
        a = z;
    }

    public static boolean isEnableXGPro(Context context) {
        return a;
    }
}
