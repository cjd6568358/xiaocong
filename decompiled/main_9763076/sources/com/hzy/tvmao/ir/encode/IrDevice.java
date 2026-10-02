package com.hzy.tvmao.ir.encode;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IrDevice {
    public static native synchronized void createRemote(int i, byte[] bArr);

    private static native synchronized int[] enc(byte[] bArr);

    public static native synchronized int getFrequency();

    public static native synchronized boolean init(Context context, String str);

    static {
        System.loadLibrary("kksdk");
    }

    public static int[] getPulsePattern(byte[] bArr) {
        return enc(bArr);
    }

    public static int[] getTimePattern(byte[] bArr) {
        int[] iArrEnc = enc(bArr);
        int frequency = 1000000 / getFrequency();
        for (int i = 0; i < iArrEnc.length; i++) {
            iArrEnc[i] = iArrEnc[i] * frequency;
        }
        return iArrEnc;
    }
}
