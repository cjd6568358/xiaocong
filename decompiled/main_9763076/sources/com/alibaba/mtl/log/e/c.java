package com.alibaba.mtl.log.e;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: Base64.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    static final /* synthetic */ boolean I;

    static {
        I = !c.class.desiredAssertionStatus();
    }

    /* JADX INFO: compiled from: Base64.java */
    static abstract class a {
        public int op;
        public byte[] output;

        a() {
        }
    }

    public static byte[] decode(byte[] input, int flags) {
        return decode(input, 0, input.length, flags);
    }

    public static byte[] decode(byte[] input, int offset, int len, int flags) {
        b bVar = new b(flags, new byte[(len * 3) / 4]);
        if (!bVar.process(input, offset, len, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (bVar.op == bVar.output.length) {
            return bVar.output;
        }
        byte[] bArr = new byte[bVar.op];
        System.arraycopy(bVar.output, 0, bArr, 0, bVar.op);
        return bArr;
    }

    /* JADX INFO: compiled from: Base64.java */
    static class b extends a {
        private static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private final int[] c;
        private int state;
        private int value;

        public b(int i, byte[] bArr) {
            this.output = bArr;
            this.c = (i & 8) == 0 ? a : b;
            this.state = 0;
            this.value = 0;
        }

        public boolean process(byte[] input, int offset, int len, boolean finish) {
            int i;
            if (this.state == 6) {
                return false;
            }
            int len2 = len + offset;
            int i2 = this.state;
            int i3 = this.value;
            int i4 = 0;
            byte[] bArr = this.output;
            int[] iArr = this.c;
            int i5 = offset;
            while (true) {
                if (i5 < len2) {
                    if (i2 == 0) {
                        while (i5 + 4 <= len2 && (i3 = (iArr[input[i5] & Constants.NETWORK_TYPE_UNCONNECTED] << 18) | (iArr[input[i5 + 1] & Constants.NETWORK_TYPE_UNCONNECTED] << 12) | (iArr[input[i5 + 2] & Constants.NETWORK_TYPE_UNCONNECTED] << 6) | iArr[input[i5 + 3] & Constants.NETWORK_TYPE_UNCONNECTED]) >= 0) {
                            bArr[i4 + 2] = (byte) i3;
                            bArr[i4 + 1] = (byte) (i3 >> 8);
                            bArr[i4] = (byte) (i3 >> 16);
                            i4 += 3;
                            i5 += 4;
                        }
                        if (i5 >= len2) {
                            i = i3;
                        }
                    }
                    int i6 = i5 + 1;
                    int i7 = iArr[input[i5] & Constants.NETWORK_TYPE_UNCONNECTED];
                    switch (i2) {
                        case 0:
                            if (i7 >= 0) {
                                i2++;
                                i3 = i7;
                            } else if (i7 != -1) {
                                this.state = 6;
                                return false;
                            }
                            i2 = i2;
                            i5 = i6;
                            break;
                        case 1:
                            if (i7 >= 0) {
                                i3 = (i3 << 6) | i7;
                                i2++;
                            } else if (i7 != -1) {
                                this.state = 6;
                                return false;
                            }
                            i2 = i2;
                            i5 = i6;
                            break;
                        case 2:
                            if (i7 >= 0) {
                                i3 = (i3 << 6) | i7;
                                i2++;
                            } else if (i7 == -2) {
                                bArr[i4] = (byte) (i3 >> 4);
                                i2 = 4;
                                i4++;
                            } else if (i7 != -1) {
                                this.state = 6;
                                return false;
                            }
                            i2 = i2;
                            i5 = i6;
                            break;
                        case 3:
                            if (i7 >= 0) {
                                i3 = (i3 << 6) | i7;
                                bArr[i4 + 2] = (byte) i3;
                                bArr[i4 + 1] = (byte) (i3 >> 8);
                                bArr[i4] = (byte) (i3 >> 16);
                                i4 += 3;
                                i2 = 0;
                            } else if (i7 == -2) {
                                bArr[i4 + 1] = (byte) (i3 >> 2);
                                bArr[i4] = (byte) (i3 >> 10);
                                i4 += 2;
                                i2 = 5;
                            } else if (i7 != -1) {
                                this.state = 6;
                                return false;
                            }
                            i2 = i2;
                            i5 = i6;
                            break;
                        case 4:
                            if (i7 == -2) {
                                i2++;
                            } else if (i7 != -1) {
                                this.state = 6;
                                return false;
                            }
                            i2 = i2;
                            i5 = i6;
                            break;
                        case 5:
                            if (i7 != -1) {
                                this.state = 6;
                                return false;
                            }
                            i2 = i2;
                            i5 = i6;
                            break;
                        default:
                            i2 = i2;
                            i5 = i6;
                            break;
                    }
                } else {
                    i = i3;
                }
            }
            if (!finish) {
                this.state = i2;
                this.value = i;
                this.op = i4;
                return true;
            }
            switch (i2) {
                case 1:
                    this.state = 6;
                    return false;
                case 2:
                    bArr[i4] = (byte) (i >> 4);
                    i4++;
                    break;
                case 3:
                    int i8 = i4 + 1;
                    bArr[i4] = (byte) (i >> 10);
                    i4 = i8 + 1;
                    bArr[i8] = (byte) (i >> 2);
                    break;
                case 4:
                    this.state = 6;
                    return false;
            }
            this.state = i2;
            this.op = i4;
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
        C0002c c0002c = new C0002c(flags, null);
        int i = (len / 3) * 4;
        if (c0002c.do_padding) {
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
        if (c0002c.do_newline && len > 0) {
            i += (c0002c.do_cr ? 2 : 1) * (((len - 1) / 57) + 1);
        }
        c0002c.output = new byte[i];
        c0002c.process(input, offset, len, true);
        if (I || c0002c.op == i) {
            return c0002c.output;
        }
        throw new AssertionError();
    }

    /* JADX INFO: renamed from: com.alibaba.mtl.log.e.c$c, reason: collision with other inner class name */
    /* JADX INFO: compiled from: Base64.java */
    static class C0002c extends a {
        static final /* synthetic */ boolean I;
        private static final byte[] a;
        private static final byte[] b;
        int D;
        private final byte[] c;
        private int count;
        private final byte[] d;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;

        static {
            I = !c.class.desiredAssertionStatus();
            a = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
            b = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        }

        public C0002c(int i, byte[] bArr) {
            this.output = bArr;
            this.do_padding = (i & 1) == 0;
            this.do_newline = (i & 2) == 0;
            this.do_cr = (i & 4) != 0;
            this.d = (i & 8) == 0 ? a : b;
            this.c = new byte[2];
            this.D = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        public boolean process(byte[] input, int offset, int len, boolean finish) {
            int i;
            int i2;
            byte b2;
            int i3;
            byte b3;
            int i4;
            byte b4;
            int i5;
            int i6;
            int i7;
            int i8;
            byte[] bArr = this.d;
            byte[] bArr2 = this.output;
            int i9 = 0;
            int i10 = this.count;
            int len2 = len + offset;
            int i11 = -1;
            switch (this.D) {
                case 0:
                    i = offset;
                    break;
                case 1:
                    if (offset + 2 > len2) {
                        i = offset;
                    } else {
                        int i12 = offset + 1;
                        i11 = ((this.c[0] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((input[offset] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (input[i12] & Constants.NETWORK_TYPE_UNCONNECTED);
                        this.D = 0;
                        i = i12 + 1;
                    }
                    break;
                case 2:
                    if (offset + 1 > len2) {
                        i = offset;
                    } else {
                        i = offset + 1;
                        i11 = ((this.c[0] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((this.c[1] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (input[offset] & Constants.NETWORK_TYPE_UNCONNECTED);
                        this.D = 0;
                    }
                    break;
                default:
                    i = offset;
                    break;
            }
            if (i11 != -1) {
                bArr2[0] = bArr[(i11 >> 18) & 63];
                bArr2[1] = bArr[(i11 >> 12) & 63];
                bArr2[2] = bArr[(i11 >> 6) & 63];
                i9 = 4;
                bArr2[3] = bArr[i11 & 63];
                i10--;
                if (i10 == 0) {
                    if (this.do_cr) {
                        i8 = 5;
                        bArr2[4] = JceStruct.SIMPLE_LIST;
                    } else {
                        i8 = 4;
                    }
                    i9 = i8 + 1;
                    bArr2[i8] = 10;
                    i10 = 19;
                }
            }
            while (true) {
                int i13 = i10;
                int i14 = i9;
                if (i + 3 <= len2) {
                    int i15 = ((input[i] & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((input[i + 1] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (input[i + 2] & Constants.NETWORK_TYPE_UNCONNECTED);
                    bArr2[i14] = bArr[(i15 >> 18) & 63];
                    bArr2[i14 + 1] = bArr[(i15 >> 12) & 63];
                    bArr2[i14 + 2] = bArr[(i15 >> 6) & 63];
                    bArr2[i14 + 3] = bArr[i15 & 63];
                    i += 3;
                    i9 = i14 + 4;
                    i10 = i13 - 1;
                    if (i10 == 0) {
                        if (this.do_cr) {
                            i7 = i9 + 1;
                            bArr2[i9] = JceStruct.SIMPLE_LIST;
                        } else {
                            i7 = i9;
                        }
                        i9 = i7 + 1;
                        bArr2[i7] = 10;
                        i10 = 19;
                    }
                } else {
                    if (finish) {
                        if (i - this.D == len2 - 1) {
                            if (this.D > 0) {
                                i6 = 1;
                                b4 = this.c[0];
                                i5 = i;
                            } else {
                                b4 = input[i];
                                i5 = i + 1;
                                i6 = 0;
                            }
                            int i16 = (b4 & Constants.NETWORK_TYPE_UNCONNECTED) << 4;
                            this.D -= i6;
                            int i17 = i14 + 1;
                            bArr2[i14] = bArr[(i16 >> 6) & 63];
                            int i18 = i17 + 1;
                            bArr2[i17] = bArr[i16 & 63];
                            if (this.do_padding) {
                                int i19 = i18 + 1;
                                bArr2[i18] = 61;
                                i18 = i19 + 1;
                                bArr2[i19] = 61;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    bArr2[i18] = JceStruct.SIMPLE_LIST;
                                    i18++;
                                }
                                bArr2[i18] = 10;
                                i18++;
                            }
                            i = i5;
                            i14 = i18;
                        } else if (i - this.D == len2 - 2) {
                            if (this.D > 1) {
                                i3 = 1;
                                b2 = this.c[0];
                            } else {
                                b2 = input[i];
                                i++;
                                i3 = 0;
                            }
                            int i20 = (b2 & Constants.NETWORK_TYPE_UNCONNECTED) << 10;
                            if (this.D > 0) {
                                b3 = this.c[i3];
                                i3++;
                            } else {
                                b3 = input[i];
                                i++;
                            }
                            int i21 = ((b3 & Constants.NETWORK_TYPE_UNCONNECTED) << 2) | i20;
                            this.D -= i3;
                            int i22 = i14 + 1;
                            bArr2[i14] = bArr[(i21 >> 12) & 63];
                            int i23 = i22 + 1;
                            bArr2[i22] = bArr[(i21 >> 6) & 63];
                            int i24 = i23 + 1;
                            bArr2[i23] = bArr[i21 & 63];
                            if (this.do_padding) {
                                i4 = i24 + 1;
                                bArr2[i24] = 61;
                            } else {
                                i4 = i24;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    bArr2[i4] = JceStruct.SIMPLE_LIST;
                                    i4++;
                                }
                                bArr2[i4] = 10;
                                i4++;
                            }
                            i14 = i4;
                        } else if (this.do_newline && i14 > 0 && i13 != 19) {
                            if (this.do_cr) {
                                i2 = i14 + 1;
                                bArr2[i14] = JceStruct.SIMPLE_LIST;
                            } else {
                                i2 = i14;
                            }
                            i14 = i2 + 1;
                            bArr2[i2] = 10;
                        }
                        if (!I && this.D != 0) {
                            throw new AssertionError();
                        }
                        if (!I && i != len2) {
                            throw new AssertionError();
                        }
                    } else if (i == len2 - 1) {
                        byte[] bArr3 = this.c;
                        int i25 = this.D;
                        this.D = i25 + 1;
                        bArr3[i25] = input[i];
                    } else if (i == len2 - 2) {
                        byte[] bArr4 = this.c;
                        int i26 = this.D;
                        this.D = i26 + 1;
                        bArr4[i26] = input[i];
                        byte[] bArr5 = this.c;
                        int i27 = this.D;
                        this.D = i27 + 1;
                        bArr5[i27] = input[i + 1];
                    }
                    this.op = i14;
                    this.count = i13;
                    return true;
                }
            }
        }
    }

    private c() {
    }
}
