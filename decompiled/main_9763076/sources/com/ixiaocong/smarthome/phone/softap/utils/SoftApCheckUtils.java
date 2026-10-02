package com.ixiaocong.smarthome.phone.softap.utils;

import android.text.TextUtils;
import com.ixiaocong.log.XConfigLog;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApCheckUtils {
    public static boolean verifySSID(String ssid) {
        if (TextUtils.isEmpty(ssid)) {
            return false;
        }
        XConfigLog.d("SoftAp", "校验设备AP名称--" + ssid);
        ssid.replaceAll("\"", Constants.MAIN_VERSION_TAG);
        String verifyValue = ssid.substring(0, ssid.length() - 2);
        String verifyCode = ssid.substring(ssid.length() - 2);
        byte[] verifys = verifyValue.getBytes();
        byte verify = 0;
        for (byte b : verifys) {
            verify = (byte) (b + verify);
        }
        try {
            return Integer.parseInt(Integer.toHexString(verify & Constants.NETWORK_TYPE_UNCONNECTED), 16) == Integer.parseInt(verifyCode, 16);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
