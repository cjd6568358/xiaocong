package com.alibaba.mtl.log.b;

import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import java.util.List;

/* JADX INFO: compiled from: CoreStatics.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static volatile long e;
    private static long f;
    private static long g;
    private static long h;
    private static long i;
    private static int u;
    private static long j = 0;
    private static long k = 0;
    private static long l = 0;
    private static long m = 0;
    private static int v = 0;
    private static int w = 0;
    private static long n = 0;
    private static long o = 0;
    private static long p = 0;
    private static long q = 0;
    private static long r = 0;
    private static long s = 0;
    private static long t = 0;

    /* JADX INFO: renamed from: u, reason: collision with other field name */
    private static long f27u = 0;

    /* JADX INFO: renamed from: v, reason: collision with other field name */
    private static long f28v = 0;

    /* JADX INFO: renamed from: w, reason: collision with other field name */
    private static long f29w = 0;
    private static long x = 0;
    private static long y = 0;
    private static StringBuilder a = new StringBuilder();

    public static synchronized void l(String str) {
        if (!d(str)) {
            if ("65501".equalsIgnoreCase(str)) {
                y++;
            } else if ("65133".equalsIgnoreCase(str)) {
                f29w++;
            } else if ("65502".equalsIgnoreCase(str)) {
                x++;
            } else if ("65503".equalsIgnoreCase(str)) {
                f28v++;
            }
            e++;
        }
    }

    public static synchronized void m(String str) {
        if (!d(str)) {
            f++;
            D();
        }
    }

    public static synchronized void a(List<com.alibaba.mtl.log.model.a> list, int i2) {
        if (list != null) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < list.size()) {
                com.alibaba.mtl.log.model.a aVar = list.get(i3);
                if (aVar != null) {
                    if (!"6005".equalsIgnoreCase(aVar.T)) {
                        i4++;
                    }
                    a.append(aVar.X);
                    if (i3 != list.size() - 1) {
                        a.append(",");
                    }
                }
                i3++;
                i4 = i4;
            }
            i.a("CoreStatics", "[uploadInc]:", Long.valueOf(g), "count:", Integer.valueOf(i2));
            g += (long) i2;
            i.a("CoreStatics", "[uploadInc]:", Long.valueOf(g));
            if (i4 != i2) {
                i.a("CoreStatics", "Mutil Process Upload Error");
            }
        }
    }

    public static synchronized void d(int i2) {
        u += i2;
    }

    public static synchronized void t() {
        h++;
    }

    public static synchronized void u() {
        i++;
    }

    public static synchronized void v() {
        n++;
    }

    public static synchronized void w() {
        o++;
    }

    public static synchronized void x() {
        p++;
    }

    public static synchronized void y() {
        q++;
    }

    public static synchronized void z() {
        r++;
    }

    public static synchronized void A() {
        s++;
    }

    public static synchronized void B() {
        t++;
    }

    public static synchronized void C() {
        f27u++;
    }

    public static synchronized void c(boolean z) {
    }

    private static void D() {
        String strT = l.t();
        if ("wifi".equalsIgnoreCase(strT)) {
            m++;
            return;
        }
        if ("3G".equalsIgnoreCase(strT)) {
            k++;
            return;
        }
        if ("4G".equalsIgnoreCase(strT)) {
            l++;
        } else if ("2G".equalsIgnoreCase(strT)) {
            j++;
        } else {
            v++;
        }
    }

    public static synchronized void E() {
        w++;
        if ((e != 0 || g != 0) && (com.alibaba.mtl.log.a.o || w >= 6)) {
            c(true);
        }
    }

    private static boolean d(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return "6005".equalsIgnoreCase(str.trim());
    }
}
