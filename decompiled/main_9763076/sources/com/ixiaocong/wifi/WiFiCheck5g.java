package com.ixiaocong.wifi;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.R;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.utils.StringUtils;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WiFiCheck5g {
    public static void getCheckIs5G(Context context, String ssid) {
        String wifiState = Constants.MAIN_VERSION_TAG;
        if (!TextUtils.isEmpty(ssid)) {
            if ("5G".equals(WifiUtils.getWifiInfo(context).frequency) || ssid.contains("5G") || ssid.contains("5g")) {
                wifiState = context.getResources().getString(R.string.wifi_state_5g_hint);
                XConfigLog.e("Wi-Fi链接频率-----//", "----//----" + WifiUtils.getWifiInfo(context).frequency);
            } else if (StringUtils.isContainChinese(ssid)) {
                wifiState = context.getResources().getString(R.string.wifi_state_chinese_hint);
            }
            if (!TextUtils.isEmpty(wifiState)) {
                Toast.makeText(context, wifiState, 0).show();
            }
        }
    }
}
