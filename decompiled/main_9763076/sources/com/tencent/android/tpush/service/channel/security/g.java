package com.tencent.android.tpush.service.channel.security;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    static final /* synthetic */ boolean a;
    private int[] b = new int[4];

    static {
        a = !g.class.desiredAssertionStatus();
    }

    public g(byte[] bArr) {
        if (bArr == null) {
            throw new RuntimeException("Invalid key: Key was null");
        }
        if (bArr.length < 16) {
            throw new RuntimeException("Invalid key: Length was less than 16 bytes");
        }
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int[] iArr = this.b;
            int i3 = i + 1;
            int i4 = i3 + 1;
            int i5 = (bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED) | ((bArr[i3] & Constants.NETWORK_TYPE_UNCONNECTED) << 8);
            int i6 = i4 + 1;
            int i7 = ((bArr[i4] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | i5;
            i = i6 + 1;
            iArr[i2] = ((bArr[i6] & Constants.NETWORK_TYPE_UNCONNECTED) << 24) | i7;
        }
    }

    public byte[] a(byte[] bArr) {
        int[] iArr = new int[(((bArr.length % 8 == 0 ? 0 : 1) + (bArr.length / 8)) * 2) + 1];
        iArr[0] = bArr.length;
        a(bArr, iArr, 1);
        a(iArr);
        return a(iArr, 0, iArr.length * 4);
    }

    public byte[] b(byte[] bArr) {
        if (!a && bArr.length % 4 != 0) {
            throw new AssertionError();
        }
        if (!a && (bArr.length / 4) % 2 != 1) {
            throw new AssertionError();
        }
        int[] iArr = new int[bArr.length / 4];
        a(bArr, iArr, 0);
        b(iArr);
        return a(iArr, 1, iArr[0]);
    }

    void a(int[] iArr) {
        if (!a && iArr.length % 2 != 1) {
            throw new AssertionError();
        }
        for (int i = 1; i < iArr.length; i += 2) {
            int i2 = 32;
            int i3 = iArr[i];
            int i4 = iArr[i + 1];
            int i5 = i3;
            int i6 = 0;
            while (true) {
                int i7 = i2 - 1;
                if (i2 > 0) {
                    int i8 = i6 - 1640531527;
                    i5 += (((i4 << 4) + this.b[0]) ^ i4) + ((i4 >>> 5) ^ i8) + this.b[1];
                    i4 = (((i5 << 4) + this.b[2]) ^ i5) + ((i5 >>> 5) ^ i8) + this.b[3] + i4;
                    i6 = i8;
                    i2 = i7;
                }
            }
            iArr[i] = i5;
            iArr[i + 1] = i4;
        }
    }

    void b(int[] iArr) {
        if (!a && iArr.length % 2 != 1) {
            throw new AssertionError();
        }
        for (int i = 1; i < iArr.length; i += 2) {
            int i2 = 32;
            int i3 = iArr[i];
            int i4 = iArr[i + 1];
            int i5 = -957401312;
            while (true) {
                int i6 = i2 - 1;
                if (i2 > 0) {
                    i4 -= ((((i3 << 4) + this.b[2]) ^ i3) + ((i3 >>> 5) ^ i5)) + this.b[3];
                    i3 -= ((((i4 << 4) + this.b[0]) ^ i4) + ((i4 >>> 5) ^ i5)) + this.b[1];
                    i5 = 1640531527 + i5;
                    i2 = i6;
                }
            }
            iArr[i] = i3;
            iArr[i + 1] = i4;
        }
    }

    void a(byte[] bArr, int[] iArr, int i) {
        if (!a && (bArr.length / 4) + i > iArr.length) {
            throw new AssertionError();
        }
        iArr[i] = 0;
        int i2 = 24;
        for (int i3 : bArr) {
            iArr[i] = iArr[i] | ((i3 & 255) << i2);
            if (i2 == 0) {
                i++;
                if (i < iArr.length) {
                    iArr[i] = 0;
                    i2 = 24;
                } else {
                    i2 = 24;
                }
            } else {
                i2 -= 8;
            }
        }
    }

    byte[] a(int[] iArr, int i, int i2) {
        if (!a && i2 > (iArr.length - i) * 4) {
            throw new AssertionError();
        }
        byte[] bArr = new byte[i2];
        int i3 = 0;
        int i4 = i;
        for (int i5 = 0; i5 < i2; i5++) {
            bArr[i5] = (byte) ((iArr[i4] >> (24 - (i3 * 8))) & 255);
            i3++;
            if (i3 == 4) {
                i4++;
                i3 = 0;
            }
        }
        return bArr;
    }
}
