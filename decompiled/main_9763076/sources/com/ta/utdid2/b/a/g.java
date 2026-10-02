package com.ta.utdid2.b.a;

import android.content.Context;
import android.telephony.TelephonyManager;
import java.util.Random;

/* JADX INFO: compiled from: PhoneInfoUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    public static final String c() {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNanoTime = (int) System.nanoTime();
        int iNextInt = new Random().nextInt();
        int iNextInt2 = new Random().nextInt();
        byte[] bytes = e.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = e.getBytes(iNanoTime);
        byte[] bytes3 = e.getBytes(iNextInt);
        byte[] bytes4 = e.getBytes(iNextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return b.encodeToString(bArr, 2);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x001f  */
    public static String a(Context context) {
        String deviceId;
        if (context == null) {
            deviceId = null;
        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    deviceId = null;
                } else {
                    deviceId = telephonyManager.getDeviceId();
                }
            } catch (Exception e) {
                deviceId = null;
            }
        }
        if (i.m99a(deviceId)) {
            return c();
        }
        return deviceId;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x001f  */
    public static String b(Context context) {
        String subscriberId;
        if (context == null) {
            subscriberId = null;
        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    subscriberId = null;
                } else {
                    subscriberId = telephonyManager.getSubscriberId();
                }
            } catch (Exception e) {
                subscriberId = null;
            }
        }
        if (i.m99a(subscriberId)) {
            return c();
        }
        return subscriberId;
    }
}
