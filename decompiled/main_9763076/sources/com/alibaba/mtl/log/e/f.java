package com.alibaba.mtl.log.e;

/* JADX INFO: compiled from: IntUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    public static byte[] getBytes(int i) {
        byte[] bArr = {(byte) ((i >> 8) % 256), (byte) (i % 256), (byte) (i % 256), (byte) (i % 256)};
        int i2 = i >> 8;
        int i3 = i2 >> 8;
        return bArr;
    }
}
