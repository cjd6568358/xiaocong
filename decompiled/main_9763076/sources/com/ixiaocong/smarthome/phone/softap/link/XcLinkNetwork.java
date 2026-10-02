package com.ixiaocong.smarthome.phone.softap.link;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApNetworkId;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcLinkNetwork {
    private static int mSoftApID = -1;
    private static int mNetWorkID = -1;

    public static void linkApNetwork(Context context, WifiManager wifiManager, String ssid) {
        if (wifiManager == null) {
            wifiManager = (WifiManager) context.getSystemService("wifi");
        }
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        boolean isTure = false;
        if (list != null && list.size() != 0) {
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"") && i.networkId >= 0) {
                    XConfigLog.e("SoftAp", "6 -- Wi-Fi配置信息有此网络-----" + i.networkId);
                    mSoftApID = i.networkId;
                    if (mSoftApID == SoftApNetworkId.getInstance().getHomeNetId()) {
                        wifiManager.removeNetwork(mSoftApID);
                        isTure = false;
                        XConfigLog.e("SoftAp", "error!!!----ap网络id与家庭网络id一致" + i.networkId + "//" + mNetWorkID);
                        break;
                    }
                    isTure = true;
                    break;
                }
            }
            if (!isTure) {
                WifiConfiguration config = new WifiConfiguration();
                config.SSID = "\"" + ssid + "\"";
                XConfigLog.e("SoftAp", "7 -- Wi-Fi配置信息没有此网络-----配置");
                config.allowedKeyManagement.set(0);
                mSoftApID = wifiManager.addNetwork(config);
                XConfigLog.e("SoftAp", "8 -- 拿到Wi-Fi配置后的id---//" + mSoftApID);
            }
            wifiManager.disableNetwork(mSoftApID);
            wifiManager.disconnect();
            boolean isConnect = wifiManager.enableNetwork(mSoftApID, true);
            wifiManager.saveConfiguration();
            wifiManager.reconnect();
            if (isConnect) {
                XConfigLog.w("SoftAp", "6---尝试连接并启动softAP=" + isConnect + "//--mSoftApID=" + mSoftApID);
                SoftApStage.getInstance().setStage(3);
            }
        }
    }

    public static void linkHomeNetwork(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        XConfigLog.w("SoftAp", "14---softAP配网成功后,切换回最初链接的Wi-Fi,NetWorkID=" + mNetWorkID);
        if (SoftApNetworkId.getInstance().getSoftApId() != -1) {
            wifiManager.disableNetwork(SoftApNetworkId.getInstance().getSoftApId());
            wifiManager.disconnect();
            wifiManager.removeNetwork(SoftApNetworkId.getInstance().getSoftApId());
            XConfigLog.w("SoftAp", "15---移除掉AP网络,SoftApID=" + mSoftApID);
        }
        if (SoftApNetworkId.getInstance().getHomeNetId() != -1) {
            wifiManager.enableNetwork(SoftApNetworkId.getInstance().getHomeNetId(), true);
            XConfigLog.w("SoftAp", "16---已经配置过的--->" + SoftApNetworkId.getInstance().getHomeNetName());
            return;
        }
        XConfigLog.w("SoftAp", "16---wifi链接不一致,HomeNetId= -1");
        if (!TextUtils.isEmpty(SoftApNetworkId.getInstance().getHomeNetName())) {
            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.replaceAll("\"", Constants.MAIN_VERSION_TAG).equals(SoftApNetworkId.getInstance().getHomeNetName()) && i.networkId >= 0) {
                    wifiManager.enableNetwork(i.networkId, true);
                    XConfigLog.w("SoftAp", "17---找回家庭网络到Wi-Fi配置信息并连接,networkId=" + i.networkId);
                    return;
                }
            }
        }
    }
}
