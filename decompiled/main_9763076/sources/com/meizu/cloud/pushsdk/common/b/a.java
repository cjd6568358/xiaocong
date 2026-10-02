package com.meizu.cloud.pushsdk.common.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a {
    private static String a = "android.os.BuildExt";
    private static e.c<Boolean> b;

    public static synchronized e.c<Boolean> a() {
        if (b == null) {
            b = new e.c<>();
        }
        if (!b.a) {
            b = e.a(a).b("isProductInternational").a();
        }
        return b;
    }
}
