package com.huawei.hms.c;

import android.os.Looper;

/* JADX INFO: compiled from: Checker.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a {
    public static <T> T a(T t, String str) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(str));
        }
        return t;
    }

    public static void a(String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(str);
        }
    }

    public static <T> T b(T t, String str) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(str));
        }
        return t;
    }
}
