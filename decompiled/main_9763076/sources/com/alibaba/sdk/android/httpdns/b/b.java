package com.alibaba.sdk.android.httpdns.b;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static f a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static h f55a;
    private static boolean h = false;

    public static List<e> a() {
        ArrayList arrayList = new ArrayList();
        if (h) {
            arrayList.addAll(a.a());
        }
        return arrayList;
    }

    public static void a(Context context) {
        if (context != null) {
            f55a.m35b(context);
        }
    }

    public static void a(Context context, h hVar) {
        a = new a(context);
        f55a = hVar;
        if (f55a == null) {
            f55a = new h();
        }
    }

    public static void a(e eVar) {
        if (eVar == null) {
            return;
        }
        a.a(eVar);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static boolean m31a() {
        return h;
    }

    public static void b(e eVar) {
        if (eVar == null) {
            return;
        }
        a.b(eVar);
    }

    public static void c(boolean z) {
        h = z;
    }

    public static String g() {
        return f55a.g();
    }

    public static void init(Context context) {
        a(context, null);
    }
}
