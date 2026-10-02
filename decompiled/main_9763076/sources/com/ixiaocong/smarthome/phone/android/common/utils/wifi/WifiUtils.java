package com.ixiaocong.smarthome.phone.android.common.utils.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WifiUtils {
    public static boolean isWifiActive(Context icontext) {
        NetworkInfo[] info;
        Context context = icontext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity != null && (info = connectivity.getAllNetworkInfo()) != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static WifiBaseInfo getWifiInfo(Context context) {
        WifiBaseInfo wifiInfo = new WifiBaseInfo();
        WifiManager wm = (WifiManager) context.getSystemService("wifi");
        WifiInfo info = wm.getConnectionInfo();
        DhcpInfo di = wm.getDhcpInfo();
        wifiInfo.ssid = getSSID(wm);
        wifiInfo.gatewayip = long2ip(di.gateway);
        wifiInfo.maskip = long2ip(di.netmask);
        wifiInfo.localip = getlocalip(context);
        wifiInfo.bssid = info.getBSSID();
        wifiInfo.rssi = info.getRssi();
        wifiInfo.ssidIshidden = info.getHiddenSSID() ? 0 : 1;
        wifiInfo.encrypt_type = getEncrypt_type(context);
        if (Build.VERSION.SDK_INT > 20) {
            wifiInfo.frequency = getWorkModeByFrequency(info.getFrequency());
        } else {
            wifiInfo.frequency = getWorkModeByFrequency(getFrequency(context, wifiInfo.ssid, wifiInfo.bssid));
        }
        return wifiInfo;
    }

    public static int getFrequency(Context context, String current_ssid, String current_bssid) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService("wifi");
        mWifiManager.startScan();
        XcLogger.e("frequency", "WifiUtils---startScan");
        List<ScanResult> mWifiList = mWifiManager.getScanResults();
        int frequency = -1;
        if (mWifiList != null) {
            for (int i = 0; i < mWifiList.size(); i++) {
                ScanResult mScanResult = mWifiList.get(i);
                if (current_ssid.replaceAll("\"", Constants.MAIN_VERSION_TAG).equals(mScanResult.SSID.replaceAll("\"", Constants.MAIN_VERSION_TAG))) {
                    frequency = mScanResult.frequency;
                    if (!TextUtils.isEmpty(current_bssid) && current_bssid.equals(mScanResult.BSSID)) {
                        return mScanResult.frequency;
                    }
                    XcLogger.e("frequency", mScanResult.SSID + "---" + mScanResult.frequency);
                }
            }
        }
        return frequency;
    }

    public static String getWorkModeByFrequency(int freq) {
        if (freq > 2400 && freq < 2500) {
            return "2.4G";
        }
        if (freq > 4900 && freq < 5900) {
            return "5G";
        }
        return null;
    }

    private static String getlocalip(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0) {
            return null;
        }
        return (ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255);
    }

    private static String getSSID(WifiManager wifiManager) {
        try {
            WifiInfo info = wifiManager.getConnectionInfo();
            String CurInfoStr = info.toString() + Constants.MAIN_VERSION_TAG;
            String CurSsidStr = info.getSSID().toString() + Constants.MAIN_VERSION_TAG;
            if (CurInfoStr.contains(CurSsidStr)) {
                return CurSsidStr;
            }
            String mSSID = CurSsidStr.replaceAll("\"", Constants.MAIN_VERSION_TAG) + Constants.MAIN_VERSION_TAG;
            return mSSID;
        } catch (Exception e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((int) (ip & 255)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 255)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 255)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 255)));
        return sb.toString();
    }

    public static String getEncrypt_type(Context mContext) {
        WifiManager mWifiManager = (WifiManager) mContext.getSystemService("wifi");
        WifiInfo info = mWifiManager.getConnectionInfo();
        List<WifiConfiguration> wifiConfigList = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfiguration : wifiConfigList) {
            String configSSid = wifiConfiguration.SSID;
            String configSSid2 = configSSid.replace("\"", Constants.MAIN_VERSION_TAG);
            String currentSSid = info.getSSID();
            if (currentSSid.replace("\"", Constants.MAIN_VERSION_TAG).equals(configSSid2) && info.getNetworkId() == wifiConfiguration.networkId) {
                return getWifiConfigurationSecurity(wifiConfiguration);
            }
        }
        return null;
    }

    public static String getWifiConfigurationSecurity(WifiConfiguration wifiConfig) {
        if (wifiConfig.allowedKeyManagement.get(0)) {
            if (!wifiConfig.allowedGroupCiphers.get(3) && (wifiConfig.allowedGroupCiphers.get(0) || wifiConfig.allowedGroupCiphers.get(1))) {
                return "WEP";
            }
            return "Open";
        }
        if (wifiConfig.allowedProtocols.get(1)) {
            return "WPA2";
        }
        if (wifiConfig.allowedKeyManagement.get(2)) {
            return "WPA-EAP";
        }
        if (wifiConfig.allowedKeyManagement.get(3)) {
            return "IEEE8021X";
        }
        if (wifiConfig.allowedProtocols.get(0)) {
            return "WPA";
        }
        return "Open";
    }
}
