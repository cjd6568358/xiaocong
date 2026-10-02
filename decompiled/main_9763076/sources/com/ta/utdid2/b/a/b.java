package com.ta.utdid2.b.a;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: Base64.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    static final /* synthetic */ boolean a;

    static {
        a = !b.class.desiredAssertionStatus();
    }

    /* JADX INFO: compiled from: Base64.java */
    static abstract class a {
        public int a;
        public byte[] b;

        a() {
        }
    }

    public static byte[] decode(String str, int flags) {
        return decode(str.getBytes(), flags);
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        C0037b c0037b = new C0037b(flags, new byte[(len * 3) / 4]);
        if (!c0037b.a(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (c0037b.a == c0037b.b.length) {
            return c0037b.b;
        }
        byte[] bArr = new byte[c0037b.a];
        System.arraycopy(c0037b.b, 0, bArr, 0, c0037b.a);
        return bArr;
    }

    /* JADX INFO: renamed from: com.ta.utdid2.b.a.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: Base64.java */
    static class C0037b extends a {
        private static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private final int[] c;
        private int state;
        private int value;

        public C0037b(int i, byte[] bArr) {
            this.b = bArr;
            this.c = (i & 8) == 0 ? a : b;
            this.state = 0;
            this.value = 0;
        }

        public boolean a(byte[] bArr, int i, int i2, boolean z) {
            int i3;
            if (this.state == 6) {
                return false;
            }
            int i4 = i2 + i;
            int i5 = this.state;
            int i6 = this.value;
            int i7 = 0;
            byte[] bArr2 = this.b;
            int[] iArr = this.c;
            int i8 = i5;
            int i9 = i;
            while (true) {
                if (i9 < i4) {
                    if (i8 == 0) {
                        while (i9 + 4 <= i4 && (i6 = (iArr[bArr[i9] & Constants.NETWORK_TYPE_UNCONNECTED] << 18) | (iArr[bArr[i9 + 1] & Constants.NETWORK_TYPE_UNCONNECTED] << 12) | (iArr[bArr[i9 + 2] & Constants.NETWORK_TYPE_UNCONNECTED] << 6) | iArr[bArr[i9 + 3] & Constants.NETWORK_TYPE_UNCONNECTED]) >= 0) {
                            bArr2[i7 + 2] = (byte) i6;
                            bArr2[i7 + 1] = (byte) (i6 >> 8);
                            bArr2[i7] = (byte) (i6 >> 16);
                            i7 += 3;
                            i9 += 4;
                        }
                        if (i9 >= i4) {
                            i3 = i6;
                        }
                    }
                    i9++;
                    int i10 = iArr[bArr[i9] & Constants.NETWORK_TYPE_UNCONNECTED];
                    switch (i8) {
                        case 0:
                            if (i10 >= 0) {
                                i8++;
                                i6 = i10;
                            } else if (i10 != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 1:
                            if (i10 >= 0) {
                                i6 = (i6 << 6) | i10;
                                i8++;
                            } else if (i10 != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 2:
                            if (i10 >= 0) {
                                i6 = (i6 << 6) | i10;
                                i8++;
                            } else if (i10 == -2) {
                                bArr2[i7] = (byte) (i6 >> 4);
                                i8 = 4;
                                i7++;
                            } else if (i10 != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 3:
                            if (i10 >= 0) {
                                i6 = (i6 << 6) | i10;
                                bArr2[i7 + 2] = (byte) i6;
                                bArr2[i7 + 1] = (byte) (i6 >> 8);
                                bArr2[i7] = (byte) (i6 >> 16);
                                i7 += 3;
                                i8 = 0;
                            } else if (i10 == -2) {
                                bArr2[i7 + 1] = (byte) (i6 >> 2);
                                bArr2[i7] = (byte) (i6 >> 10);
                                i7 += 2;
                                i8 = 5;
                            } else if (i10 != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 4:
                            if (i10 == -2) {
                                i8++;
                            } else if (i10 != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        case 5:
                            if (i10 != -1) {
                                this.state = 6;
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    i3 = i6;
                }
            }
            if (!z) {
                this.state = i8;
                this.value = i3;
                this.a = i7;
                return true;
            }
            switch (i8) {
                case 1:
                    this.state = 6;
                    return false;
                case 2:
                    bArr2[i7] = (byte) (i3 >> 4);
                    i7++;
                    break;
                case 3:
                    int i11 = i7 + 1;
                    bArr2[i7] = (byte) (i3 >> 10);
                    i7 = i11 + 1;
                    bArr2[i11] = (byte) (i3 >> 2);
                    break;
                case 4:
                    this.state = 6;
                    return false;
            }
            this.state = i8;
            this.a = i7;
            return true;
        }
    }

    public static String encodeToString(byte[] input, int flags) {
        try {
            return new String(encode(input, flags), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] encode(byte[] input, int flags) {
        return encode(input, 0, input.length, flags);
    }

    public static byte[] encode(byte[] input, int offset, int len, int flags) {
        c cVar = new c(flags, null);
        int i = (len / 3) * 4;
        if (cVar.f107b) {
            if (len % 3 > 0) {
                i += 4;
            }
        } else {
            switch (len % 3) {
                case 1:
                    i += 2;
                    break;
                case 2:
                    i += 3;
                    break;
            }
        }
        if (cVar.f108c && len > 0) {
            i += (cVar.f109d ? 2 : 1) * (((len - 1) / 57) + 1);
        }
        cVar.b = new byte[i];
        cVar.a(input, offset, len, true);
        if (a || cVar.a == i) {
            return cVar.b;
        }
        throw new AssertionError();
    }

    /* JADX INFO: compiled from: Base64.java */
    static class c extends a {
        static final /* synthetic */ boolean a;
        private static final byte[] c;
        private static final byte[] d;
        int b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        public final boolean f107b;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        public final boolean f108c;
        private int count;

        /* JADX INFO: renamed from: d, reason: collision with other field name */
        public final boolean f109d;
        private final byte[] e;
        private final byte[] f;

        static {
            a = !b.class.desiredAssertionStatus();
            c = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
            d = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        }

        public c(int i, byte[] bArr) {
            this.b = bArr;
            this.f107b = (i & 1) == 0;
            this.f108c = (i & 2) == 0;
            this.f109d = (i & 4) != 0;
            this.f = (i & 8) == 0 ? c : d;
            this.e = new byte[2];
            this.b = 0;
            this.count = this.f108c ? 19 : -1;
        }

        public boolean a(byte[] bArr, int i, int i2, boolean z) {
            int i3;
            int i4;
            byte b;
            int i5;
            byte b2;
            int i6;
            byte b3;
            int i7;
            int i8;
            int i9;
            int i10;
            byte[] bArr2 = this.f;
            byte[] bArr3 = this.b;
            int i11 = 0;
            int i12 = this.count;
            int i13 = i2 + i;
            int i14 = -1;
            switch (this.b) {
                case 0:
                    i3 = i;
                    break;
                case 1:
                    if (i + 2 > i13) {
                        i3 = i;
                    } else {
                        int i15 = i + 1;
                        i14 = ((this.e[0] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (bArr[i15] & Constants.NETWORK_TYPE_UNCONNECTED);
                        this.b = 0;
                        i3 = i15 + 1;
                    }
                    break;
                case 2:
                    if (i + 1 > i13) {
                        i3 = i;
                    } else {
                        i3 = i + 1;
                        i14 = ((this.e[0] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((this.e[1] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED);
                        this.b = 0;
                    }
                    break;
                default:
                    i3 = i;
                    break;
            }
            if (i14 != -1) {
                bArr3[0] = bArr2[(i14 >> 18) & 63];
                bArr3[1] = bArr2[(i14 >> 12) & 63];
                bArr3[2] = bArr2[(i14 >> 6) & 63];
                i11 = 4;
                bArr3[3] = bArr2[i14 & 63];
                i12--;
                if (i12 == 0) {
                    if (!this.f109d) {
                        i10 = 4;
                    } else {
                        i10 = 5;
                        bArr3[4] = JceStruct.SIMPLE_LIST;
                    }
                    i11 = i10 + 1;
                    bArr3[i10] = 10;
                    i12 = 19;
                }
            }
            while (true) {
                int i16 = i12;
                int i17 = i11;
                if (i3 + 3 <= i13) {
                    int i18 = ((bArr[i3] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((bArr[i3 + 1] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (bArr[i3 + 2] & Constants.NETWORK_TYPE_UNCONNECTED);
                    bArr3[i17] = bArr2[(i18 >> 18) & 63];
                    bArr3[i17 + 1] = bArr2[(i18 >> 12) & 63];
                    bArr3[i17 + 2] = bArr2[(i18 >> 6) & 63];
                    bArr3[i17 + 3] = bArr2[i18 & 63];
                    i3 += 3;
                    i11 = i17 + 4;
                    i12 = i16 - 1;
                    if (i12 == 0) {
                        if (this.f109d) {
                            i9 = i11 + 1;
                            bArr3[i11] = JceStruct.SIMPLE_LIST;
                        } else {
                            i9 = i11;
                        }
                        i11 = i9 + 1;
                        bArr3[i9] = 10;
                        i12 = 19;
                    }
                } else {
                    if (z) {
                        if (i3 - this.b == i13 - 1) {
                            if (this.b > 0) {
                                i8 = 1;
                                b3 = this.e[0];
                                i7 = i3;
                            } else {
                                b3 = bArr[i3];
                                i7 = i3 + 1;
                                i8 = 0;
                            }
                            int i19 = (b3 & Constants.NETWORK_TYPE_UNCONNECTED) << 4;
                            this.b -= i8;
                            int i20 = i17 + 1;
                            bArr3[i17] = bArr2[(i19 >> 6) & 63];
                            int i21 = i20 + 1;
                            bArr3[i20] = bArr2[i19 & 63];
                            if (this.f107b) {
                                int i22 = i21 + 1;
                                bArr3[i21] = 61;
                                i21 = i22 + 1;
                                bArr3[i22] = 61;
                            }
                            if (this.f108c) {
                                if (this.f109d) {
                                    bArr3[i21] = JceStruct.SIMPLE_LIST;
                                    i21++;
                                }
                                i17 = i21 + 1;
                                bArr3[i21] = 10;
                                i3 = i7;
                            } else {
                                i3 = i7;
                                i17 = i21;
                            }
                        } else if (i3 - this.b == i13 - 2) {
                            if (this.b > 1) {
                                i5 = 1;
                                b = this.e[0];
                            } else {
                                b = bArr[i3];
                                i3++;
                                i5 = 0;
                            }
                            int i23 = (b & Constants.NETWORK_TYPE_UNCONNECTED) << 10;
                            if (this.b > 0) {
                                b2 = this.e[i5];
                                i5++;
                            } else {
                                b2 = bArr[i3];
                                i3++;
                            }
                            int i24 = ((b2 & Constants.NETWORK_TYPE_UNCONNECTED) << 2) | i23;
                            this.b -= i5;
                            int i25 = i17 + 1;
                            bArr3[i17] = bArr2[(i24 >> 12) & 63];
                            int i26 = i25 + 1;
                            bArr3[i25] = bArr2[(i24 >> 6) & 63];
                            int i27 = i26 + 1;
                            bArr3[i26] = bArr2[i24 & 63];
                            if (this.f107b) {
                                i6 = i27 + 1;
                                bArr3[i27] = 61;
                            } else {
                                i6 = i27;
                            }
                            if (this.f108c) {
                                if (this.f109d) {
                                    bArr3[i6] = JceStruct.SIMPLE_LIST;
                                    i6++;
                                }
                                i17 = i6 + 1;
                                bArr3[i6] = 10;
                            } else {
                                i17 = i6;
                            }
                        } else if (this.f108c && i17 > 0 && i16 != 19) {
                            if (this.f109d) {
                                i4 = i17 + 1;
                                bArr3[i17] = JceStruct.SIMPLE_LIST;
                            } else {
                                i4 = i17;
                            }
                            i17 = i4 + 1;
                            bArr3[i4] = 10;
                        }
                        if (!a && this.b != 0) {
                            throw new AssertionError();
                        }
                        if (!a && i3 != i13) {
                            throw new AssertionError();
                        }
                    } else if (i3 == i13 - 1) {
                        byte[] bArr4 = this.e;
                        int i28 = this.b;
                        this.b = i28 + 1;
                        bArr4[i28] = bArr[i3];
                    } else if (i3 == i13 - 2) {
                        byte[] bArr5 = this.e;
                        int i29 = this.b;
                        this.b = i29 + 1;
                        bArr5[i29] = bArr[i3];
                        byte[] bArr6 = this.e;
                        int i30 = this.b;
                        this.b = i30 + 1;
                        bArr6[i30] = bArr[i3 + 1];
                    }
                    this.a = i17;
                    this.count = i16;
                    return true;
                }
            }
        }
    }

    private b() {
    }
}
