package com.baidu.cloud.media.download;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LocalHlsSec {
    static {
        System.loadLibrary("hls-download");
    }

    public static String bytes2HexStr(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED) < 16) {
                stringBuffer.append(PushConstants.PUSH_TYPE_NOTIFY);
            }
            stringBuffer.append(Long.toHexString(bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED));
        }
        return stringBuffer.toString();
    }

    public static String decryptStr(Context context, String str) {
        return bytes2HexStr(new LocalHlsSec().crypt(context, hexStr2Bytes(str), 1));
    }

    public static byte[] hexStr2Bytes(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i * 2, (i * 2) + 1), 16) * 16) + Integer.parseInt(str.substring((i * 2) + 1, (i * 2) + 2), 16));
        }
        return bArr;
    }

    public native byte[] crypt(Context context, byte[] bArr, int i);

    public String getMD5(String str) {
        return a.b(str);
    }
}
