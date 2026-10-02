package com.ixiaocong.smarthome.phone.android.common.utils.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.StringUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WiFi5gManager {
    private static String wifiState;

    public static String getWifiName(Context context) {
        String SSID = Constants.MAIN_VERSION_TAG;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        WifiInfo info = wifiManager.getConnectionInfo();
        String infoStr = info.toString();
        String ssidStr = info.getSSID().toString() + Constants.MAIN_VERSION_TAG;
        try {
            if (infoStr.contains(ssidStr)) {
                SSID = ssidStr;
            } else {
                SSID = ssidStr.replaceAll("\"", Constants.MAIN_VERSION_TAG) + Constants.MAIN_VERSION_TAG;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isWifiConnect(context) && !TextUtils.isEmpty(SSID)) {
            if ("5G".equals(WifiUtils.getWifiInfo(context).frequency) || SSID.contains("5G") || SSID.contains("5g")) {
                wifiState = context.getResources().getString(R.string.wifi_state_5g_hint);
                XcLogger.e("Wi-Fi链接频率-----//", "----//----" + WifiUtils.getWifiInfo(context).frequency);
            } else if (StringUtils.isContainChinese(SSID)) {
                wifiState = context.getResources().getString(R.string.wifi_state_chinese_hint);
            }
            if (!TextUtils.isEmpty(wifiState) && !SoftApStage.getInstance().isStartStage()) {
                ToastUtils.showToast(context, wifiState, 0);
            }
            return SSID + Constants.MAIN_VERSION_TAG;
        }
        return "请检查WiFi设置";
    }

    public static boolean isWifiConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = manager.getNetworkInfo(1);
        return networkInfo.isConnected();
    }
}
