package com.alibaba.mtl.log.e;

/* JADX INFO: compiled from: RC4.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {

    /* JADX INFO: compiled from: RC4.java */
    private static class a {
        public int[] d;
        public int x;
        public int y;

        private a() {
            this.d = new int[256];
        }
    }

    public static byte[] a(byte[] bArr, String str) {
        a aVarA;
        if (bArr == null || str == null || (aVarA = a(str)) == null) {
            return null;
        }
        return a(bArr, aVarA);
    }

    private static a a(String str) {
        if (str == null) {
            return null;
        }
        a aVar = new a();
        for (int i = 0; i < 256; i++) {
            aVar.d[i] = i;
        }
        aVar.x = 0;
        aVar.y = 0;
        int iCharAt = 0;
        int length = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            try {
                iCharAt = (iCharAt + (str.charAt(length) + aVar.d[i2])) % 256;
                int i3 = aVar.d[i2];
                aVar.d[i2] = aVar.d[iCharAt];
                aVar.d[iCharAt] = i3;
                length = (length + 1) % str.length();
            } catch (Exception e) {
                return null;
            }
        }
        return aVar;
    }

    private static byte[] a(byte[] bArr, a aVar) {
        if (bArr == null || aVar == null) {
            return null;
        }
        int i = aVar.x;
        int i2 = aVar.y;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) % 256;
            i2 = (i2 + aVar.d[i]) % 256;
            int i4 = aVar.d[i];
            aVar.d[i] = aVar.d[i2];
            aVar.d[i2] = i4;
            int i5 = (aVar.d[i] + aVar.d[i2]) % 256;
            bArr[i3] = (byte) (aVar.d[i5] ^ bArr[i3]);
        }
        aVar.x = i;
        aVar.y = i2;
        return bArr;
    }
}
