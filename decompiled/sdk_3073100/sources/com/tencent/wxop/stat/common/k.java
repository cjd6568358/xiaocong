package com.tencent.wxop.stat.common;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class k extends i {
    static final /* synthetic */ boolean g;
    private static final byte[] h;
    private static final byte[] i;
    int c;
    public final boolean d;
    public final boolean e;
    public final boolean f;
    private final byte[] j;
    private int k;
    private final byte[] l;

    static {
        g = !h.class.desiredAssertionStatus();
        h = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        i = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    }

    public k(int i2, byte[] bArr) {
        this.a = bArr;
        this.d = (i2 & 1) == 0;
        this.e = (i2 & 2) == 0;
        this.f = (i2 & 4) != 0;
        this.l = (i2 & 8) == 0 ? h : i;
        this.j = new byte[2];
        this.c = 0;
        this.k = this.e ? 19 : -1;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:4:0x000f  */
    public boolean a(byte[] bArr, int i2, int i3, boolean z) {
        int i4;
        int i5;
        int i6;
        int i7;
        byte b;
        int i8;
        byte b2;
        int i9;
        byte b3;
        int i10;
        int i11;
        byte[] bArr2 = this.l;
        byte[] bArr3 = this.a;
        int i12 = 0;
        int i13 = this.k;
        int i14 = i3 + i2;
        switch (this.c) {
            case 0:
                i5 = -1;
                i4 = i2;
                break;
            case 1:
                if (i2 + 2 > i14) {
                    i5 = -1;
                    i4 = i2;
                } else {
                    int i15 = i2 + 1;
                    int i16 = ((this.j[0] & 255) << 16) | ((bArr[i2] & 255) << 8) | (bArr[i15] & 255);
                    this.c = 0;
                    i5 = i16;
                    i4 = i15 + 1;
                }
                break;
            case 2:
                if (i2 + 1 > i14) {
                    i5 = -1;
                    i4 = i2;
                } else {
                    i4 = i2 + 1;
                    int i17 = ((this.j[0] & 255) << 16) | ((this.j[1] & 255) << 8) | (bArr[i2] & 255);
                    this.c = 0;
                    i5 = i17;
                }
                break;
            default:
                i5 = -1;
                i4 = i2;
                break;
        }
        if (i5 != -1) {
            bArr3[0] = bArr2[(i5 >> 18) & 63];
            bArr3[1] = bArr2[(i5 >> 12) & 63];
            bArr3[2] = bArr2[(i5 >> 6) & 63];
            int i18 = 4;
            bArr3[3] = bArr2[i5 & 63];
            int i19 = i13 - 1;
            if (i19 == 0) {
                if (this.f) {
                    i18 = 5;
                    bArr3[4] = 13;
                }
                i12 = i18 + 1;
                bArr3[i18] = 10;
                i6 = 19;
            } else {
                i6 = i19;
                i12 = 4;
            }
        } else {
            i6 = i13;
        }
        while (i4 + 3 <= i14) {
            int i20 = ((bArr[i4] & 255) << 16) | ((bArr[i4 + 1] & 255) << 8) | (bArr[i4 + 2] & 255);
            bArr3[i12] = bArr2[(i20 >> 18) & 63];
            bArr3[i12 + 1] = bArr2[(i20 >> 12) & 63];
            bArr3[i12 + 2] = bArr2[(i20 >> 6) & 63];
            bArr3[i12 + 3] = bArr2[i20 & 63];
            i4 += 3;
            int i21 = i12 + 4;
            int i22 = i6 - 1;
            if (i22 == 0) {
                if (this.f) {
                    i11 = i21 + 1;
                    bArr3[i21] = 13;
                } else {
                    i11 = i21;
                }
                i12 = i11 + 1;
                bArr3[i11] = 10;
                i6 = 19;
            } else {
                i6 = i22;
                i12 = i21;
            }
        }
        if (z) {
            if (i4 - this.c == i14 - 1) {
                if (this.c > 0) {
                    i10 = 1;
                    b3 = this.j[0];
                } else {
                    b3 = bArr[i4];
                    i4++;
                    i10 = 0;
                }
                int i23 = (b3 & 255) << 4;
                this.c -= i10;
                int i24 = i12 + 1;
                bArr3[i12] = bArr2[(i23 >> 6) & 63];
                int i25 = i24 + 1;
                bArr3[i24] = bArr2[i23 & 63];
                if (this.d) {
                    int i26 = i25 + 1;
                    bArr3[i25] = 61;
                    i25 = i26 + 1;
                    bArr3[i26] = 61;
                }
                if (this.e) {
                    if (this.f) {
                        bArr3[i25] = 13;
                        i25++;
                    }
                    bArr3[i25] = 10;
                    i25++;
                }
                i12 = i25;
            } else if (i4 - this.c == i14 - 2) {
                if (this.c > 1) {
                    i8 = 1;
                    b = this.j[0];
                } else {
                    b = bArr[i4];
                    i4++;
                    i8 = 0;
                }
                int i27 = (b & 255) << 10;
                if (this.c > 0) {
                    b2 = this.j[i8];
                    i8++;
                } else {
                    b2 = bArr[i4];
                    i4++;
                }
                int i28 = ((b2 & 255) << 2) | i27;
                this.c -= i8;
                int i29 = i12 + 1;
                bArr3[i12] = bArr2[(i28 >> 12) & 63];
                int i30 = i29 + 1;
                bArr3[i29] = bArr2[(i28 >> 6) & 63];
                int i31 = i30 + 1;
                bArr3[i30] = bArr2[i28 & 63];
                if (this.d) {
                    i9 = i31 + 1;
                    bArr3[i31] = 61;
                } else {
                    i9 = i31;
                }
                if (this.e) {
                    if (this.f) {
                        bArr3[i9] = 13;
                        i9++;
                    }
                    bArr3[i9] = 10;
                    i9++;
                }
                i12 = i9;
            } else if (this.e && i12 > 0 && i6 != 19) {
                if (this.f) {
                    i7 = i12 + 1;
                    bArr3[i12] = 13;
                } else {
                    i7 = i12;
                }
                i12 = i7 + 1;
                bArr3[i7] = 10;
            }
            if (!g && this.c != 0) {
                throw new AssertionError();
            }
            if (!g && i4 != i14) {
                throw new AssertionError();
            }
        } else if (i4 == i14 - 1) {
            byte[] bArr4 = this.j;
            int i32 = this.c;
            this.c = i32 + 1;
            bArr4[i32] = bArr[i4];
        } else if (i4 == i14 - 2) {
            byte[] bArr5 = this.j;
            int i33 = this.c;
            this.c = i33 + 1;
            bArr5[i33] = bArr[i4];
            byte[] bArr6 = this.j;
            int i34 = this.c;
            this.c = i34 + 1;
            bArr6[i34] = bArr[i4 + 1];
        }
        this.b = i12;
        this.k = i6;
        return true;
    }
}
