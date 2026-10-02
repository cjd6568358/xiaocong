package com.meizu.cloud.pushsdk.b.f;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class d {
    public static void a(boolean z, Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static <T> T a(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public static <T> T a(T t, Object obj) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(obj));
        }
        return t;
    }
}
