package com.tencent.wxop.stat.common;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class h {
    static final /* synthetic */ boolean a;

    static {
        a = !h.class.desiredAssertionStatus();
    }

    private h() {
    }

    public static byte[] a(byte[] bArr, int i) {
        return a(bArr, 0, bArr.length, i);
    }

    public static byte[] a(byte[] bArr, int i, int i2, int i3) {
        j jVar = new j(i3, new byte[(i2 * 3) / 4]);
        if (!jVar.a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (jVar.b == jVar.a.length) {
            return jVar.a;
        }
        byte[] bArr2 = new byte[jVar.b];
        System.arraycopy(jVar.a, 0, bArr2, 0, jVar.b);
        return bArr2;
    }

    public static byte[] b(byte[] bArr, int i) {
        return b(bArr, 0, bArr.length, i);
    }

    public static byte[] b(byte[] bArr, int i, int i2, int i3) {
        k kVar = new k(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!kVar.d) {
            switch (i2 % 3) {
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (kVar.e && i2 > 0) {
            i4 += (kVar.f ? 2 : 1) * (((i2 - 1) / 57) + 1);
        }
        kVar.a = new byte[i4];
        kVar.a(bArr, i, i2, true);
        if (a || kVar.b == i4) {
            return kVar.a;
        }
        throw new AssertionError();
    }
}
