package com.alibaba.mtl.log.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: PhoneInfoUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class m {
    private static final Random a = new Random();

    public static final String getUniqueID() {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNanoTime = (int) System.nanoTime();
        int iNextInt = a.nextInt();
        int iNextInt2 = a.nextInt();
        byte[] bytes = f.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = f.getBytes(iNanoTime);
        byte[] bytes3 = f.getBytes(iNextInt);
        byte[] bytes4 = f.getBytes(iNextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return c.encodeToString(bArr, 2);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0045  */
    /* JADX WARN: Code duplicated, block: B:24:0x0076  */
    /* JADX WARN: Code duplicated, block: B:30:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002c, code lost:
    
        if (android.text.TextUtils.isEmpty(r0) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getImei(Context context) {
        String deviceId;
        String str;
        String str2 = null;
        if (context != null) {
            try {
                String string = context.getSharedPreferences("UTCommon", 0).getString("_ie", Constants.MAIN_VERSION_TAG);
                if (!TextUtils.isEmpty(string)) {
                    str = new String(c.decode(string.getBytes(), 2), HTTP.UTF_8);
                }
            } catch (Exception e) {
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    deviceId = null;
                } else {
                    deviceId = telephonyManager.getDeviceId();
                }
                str2 = deviceId;
            } catch (Exception e2) {
            }
            if (TextUtils.isEmpty(str2)) {
                str = getUniqueID();
            } else {
                str = str2;
            }
            if (context != null) {
                try {
                    SharedPreferences.Editor editorEdit = context.getSharedPreferences("UTCommon", 0).edit();
                    editorEdit.putString("_ie", new String(c.encode(str.getBytes(HTTP.UTF_8), 2)));
                    editorEdit.commit();
                } catch (UnsupportedEncodingException e3) {
                    e3.printStackTrace();
                }
            }
        } else {
            if (TextUtils.isEmpty(str2)) {
                str = getUniqueID();
            } else {
                str = str2;
            }
            if (context != null) {
                SharedPreferences.Editor editorEdit2 = context.getSharedPreferences("UTCommon", 0).edit();
                editorEdit2.putString("_ie", new String(c.encode(str.getBytes(HTTP.UTF_8), 2)));
                editorEdit2.commit();
            }
        }
        return str;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0045  */
    /* JADX WARN: Code duplicated, block: B:24:0x0076  */
    /* JADX WARN: Code duplicated, block: B:30:0x004b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002c, code lost:
    
        if (android.text.TextUtils.isEmpty(r0) == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getImsi(Context context) {
        String subscriberId;
        String str;
        String str2 = null;
        if (context != null) {
            try {
                String string = context.getSharedPreferences("UTCommon", 0).getString("_is", Constants.MAIN_VERSION_TAG);
                if (!TextUtils.isEmpty(string)) {
                    str = new String(c.decode(string.getBytes(), 2), HTTP.UTF_8);
                }
            } catch (Exception e) {
            }
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    subscriberId = null;
                } else {
                    subscriberId = telephonyManager.getSubscriberId();
                }
                str2 = subscriberId;
            } catch (Exception e2) {
            }
            if (TextUtils.isEmpty(str2)) {
                str = getUniqueID();
            } else {
                str = str2;
            }
            if (context != null) {
                try {
                    SharedPreferences.Editor editorEdit = context.getSharedPreferences("UTCommon", 0).edit();
                    editorEdit.putString("_is", new String(c.encode(str.getBytes(HTTP.UTF_8), 2)));
                    editorEdit.commit();
                } catch (UnsupportedEncodingException e3) {
                    e3.printStackTrace();
                }
            }
        } else {
            if (TextUtils.isEmpty(str2)) {
                str = getUniqueID();
            } else {
                str = str2;
            }
            if (context != null) {
                SharedPreferences.Editor editorEdit2 = context.getSharedPreferences("UTCommon", 0).edit();
                editorEdit2.putString("_is", new String(c.encode(str.getBytes(HTTP.UTF_8), 2)));
                editorEdit2.commit();
            }
        }
        return str;
    }
}
