package com.ixiaocong.smarthome.phone.softap.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback;
import com.ixiaocong.smarthome.phone.softap.link.XcLinkNetwork;
import com.ixiaocong.smarthome.phone.softap.timer.XcScanWifiTimer;
import com.ixiaocong.utils.StringUtils;
import com.ixiaocong.wifi.WiFiCheck5g;
import com.ixiaocong.wifi.WifiBroadAddressUtils;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApScanWifiUtils {
    private static boolean isConnectAp = false;
    private static String mHomeSSID;
    private static String mProductId;
    private static SoftApReceiver mReceiver;
    private static ScanWifiCallback mScanCallback;
    private static WifiManager mWifiManager;
    private static XcScanWifiTimer mWifiTimer;

    public static void startXConfig(Context context) {
        if (mReceiver == null) {
            mWifiManager = (WifiManager) context.getSystemService("wifi");
            mReceiver = new SoftApReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.addAction("android.net.wifi.STATE_CHANGE");
            intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            context.registerReceiver(mReceiver, intentFilter);
            XConfigLog.w("SoftAp", "开始注册广播----监听Wi-Fi状态");
        }
    }

    public static void stopXConfig(Context context) {
        if (mReceiver != null) {
            context.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        if (mWifiTimer != null) {
            mWifiTimer.stopDeviceScan();
        }
        isConnectAp = false;
        SoftApNetworkId.getInstance().clearId();
        SoftApStage.getInstance().clearStage();
    }

    public static void scanAboutWifi(Context context, ScanWifiCallback callback, String productId, String homeSSID) {
        mProductId = productId;
        mScanCallback = callback;
        mHomeSSID = homeSSID;
        XConfigLog.w("SoftAp", "1---发现并校验设备ap及获取家庭网络ID,homeSSID=" + mHomeSSID);
        if (TextUtils.isEmpty(homeSSID)) {
            Toast.makeText(context, "家庭网络不能为空", 0).show();
            return;
        }
        SoftApStage.getInstance().setStage(0);
        SoftApStage.getInstance().setCheckCode(String.valueOf(StringUtils.getRandomInt(6)));
        WiFiCheck5g.getCheckIs5G(context, homeSSID);
        mWifiTimer = new XcScanWifiTimer(context, callback, productId, homeSSID);
        mWifiTimer.startDeviceScan();
    }

    public static void scanDeviceAp(Context context, ScanWifiCallback callback, String productId) {
        mProductId = productId;
        mScanCallback = callback;
        SoftApStage.getInstance().setStage(1);
        mWifiTimer = new XcScanWifiTimer(context, callback, productId);
        mWifiTimer.startDeviceScan();
    }

    public static void stopScanDeviceAp() {
        if (mWifiTimer != null) {
            mWifiTimer.stopDeviceScan();
            mWifiTimer = null;
        }
        isConnectAp = false;
    }

    private static final class SoftApReceiver extends BroadcastReceiver {
        private SoftApReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XConfigLog.w("softApReceiver", intent.getAction() + "---接收到Wi-Fi的相关广播==" + SoftApStage.getInstance().getStage());
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                if (SoftApScanWifiUtils.mWifiManager != null && SoftApStage.getInstance().getStage() != -1) {
                    if (!SoftApScanWifiUtils.mWifiManager.getConnectionInfo().getSSID().equals(SoftApNetworkId.getInstance().getSoftApName()) || SoftApStage.getInstance().getStage() != 3) {
                        List<ScanResult> scanList = SoftApScanWifiUtils.mWifiManager.getScanResults();
                        if (SoftApStage.getInstance().getStage() == 1) {
                            if (scanList != null && scanList.size() > 0) {
                                XConfigLog.w("SoftAp", "扫描到Wi-Fi列表的长度---" + scanList.size());
                                for (int i = 0; i < scanList.size(); i++) {
                                    if (scanList.get(i).SSID.contains("smart-" + SoftApScanWifiUtils.mProductId + "-") && SoftApCheckUtils.verifySSID(scanList.get(i).SSID) && (SoftApStage.getInstance().getStage() == 0 || SoftApStage.getInstance().getStage() == 1)) {
                                        XConfigLog.w("SoftAp", "2---scanAp,接收到广播,筛选符合规则的ap=" + scanList.get(i).SSID);
                                        SoftApNetworkId.getInstance().setSoftApName(scanList.get(i).SSID);
                                    }
                                }
                                if (TextUtils.isEmpty(SoftApNetworkId.getInstance().getSoftApName())) {
                                    if (SoftApScanWifiUtils.mWifiTimer != null) {
                                        SoftApScanWifiUtils.mWifiTimer.startDeviceScan();
                                        return;
                                    }
                                    return;
                                } else {
                                    if (SoftApScanWifiUtils.mWifiTimer != null) {
                                        SoftApScanWifiUtils.mWifiTimer.stopDeviceScan();
                                        scanApNetwork(SoftApNetworkId.getInstance().getSoftApName());
                                        return;
                                    }
                                    return;
                                }
                            }
                            if (SoftApStage.getInstance().getStage() == 0 && scanList != null && scanList.size() > 0) {
                                XConfigLog.w("SoftAp", "扫描到Wi-Fi列表的长度---" + scanList.size());
                                for (int i2 = 0; i2 < scanList.size(); i2++) {
                                    if (scanList.get(i2).SSID.contains("smart-" + SoftApScanWifiUtils.mProductId + "-")) {
                                        if (!SoftApCheckUtils.verifySSID(scanList.get(i2).SSID)) {
                                            if (!TextUtils.isEmpty(SoftApScanWifiUtils.mHomeSSID) && ((SoftApStage.getInstance().getStage() == 0 || SoftApStage.getInstance().getStage() == 2) && scanList.get(i2).SSID.equals(SoftApScanWifiUtils.mHomeSSID))) {
                                                SoftApNetworkId.getInstance().setHomeNetName(SoftApScanWifiUtils.mHomeSSID);
                                                WifiInfo info = SoftApScanWifiUtils.mWifiManager.getConnectionInfo();
                                                int netWorkID = info.getNetworkId();
                                                if (netWorkID != -1) {
                                                    XConfigLog.w("SoftAp", "2---scanAp,接收到广播筛选家庭网络的netWorkID=" + netWorkID);
                                                    SoftApNetworkId.getInstance().setHomeNetId(netWorkID);
                                                }
                                            }
                                        } else if (SoftApStage.getInstance().getStage() == 0 || SoftApStage.getInstance().getStage() == 1) {
                                            XConfigLog.w("SoftAp", "2---scanAp,接收到广播,筛选符合规则的ap=" + scanList.get(i2).SSID);
                                            SoftApNetworkId.getInstance().setSoftApName(scanList.get(i2).SSID);
                                        }
                                    }
                                }
                                if (TextUtils.isEmpty(SoftApNetworkId.getInstance().getSoftApName()) || SoftApNetworkId.getInstance().getHomeNetId() == -1) {
                                    if (SoftApScanWifiUtils.mWifiTimer != null) {
                                        SoftApScanWifiUtils.mWifiTimer.startDeviceScan();
                                        return;
                                    }
                                    return;
                                } else {
                                    if (SoftApScanWifiUtils.mWifiTimer != null) {
                                        SoftApScanWifiUtils.mWifiTimer.stopDeviceScan();
                                        SoftApStage.getInstance().setStage(-1);
                                        scanApNetwork(SoftApNetworkId.getInstance().getSoftApName());
                                        return;
                                    }
                                    return;
                                }
                            }
                            return;
                        }
                        return;
                    }
                    XConfigLog.e("SoftAp", "7---可以直接执行softAp设备入网ApName=" + SoftApNetworkId.getInstance().getSoftApName() + "//" + SoftApScanWifiUtils.mWifiManager.getConnectionInfo().getSSID());
                    startSoftAp(context, SoftApScanWifiUtils.mWifiManager.getConnectionInfo().getSSID());
                    return;
                }
                return;
            }
            if (SoftApStage.getInstance().getStage() == 3) {
                String apSSID = SoftApScanWifiUtils.mWifiManager.getConnectionInfo().getSSID();
                if (!SoftApScanWifiUtils.isConnectAp) {
                    if (!TextUtils.isEmpty(apSSID) && !TextUtils.isEmpty(SoftApNetworkId.getInstance().getSoftApName())) {
                        String apSSID2 = apSSID.replaceAll("\"", Constants.MAIN_VERSION_TAG);
                        if (apSSID2.equals(SoftApNetworkId.getInstance().getSoftApName())) {
                            startSoftAp(context, apSSID2);
                            return;
                        } else {
                            XConfigLog.w("SoftAp", "error 当前连接网络与ap网络名称不一致");
                            XcLinkNetwork.linkApNetwork(context, SoftApScanWifiUtils.mWifiManager, SoftApNetworkId.getInstance().getSoftApName());
                            return;
                        }
                    }
                    XConfigLog.w("SoftAp", "error ap网络名称都为空");
                    XcLinkNetwork.linkApNetwork(context, SoftApScanWifiUtils.mWifiManager, SoftApNetworkId.getInstance().getSoftApName());
                    return;
                }
                return;
            }
            if (SoftApStage.getInstance().getStage() == 4) {
                String homeSSID = SoftApScanWifiUtils.mWifiManager.getConnectionInfo().getSSID().replaceAll("\"", Constants.MAIN_VERSION_TAG);
                XConfigLog.w("SoftAp", "17---scanAp,接收到广播,家庭切换回网络=" + homeSSID);
                if (!TextUtils.isEmpty(homeSSID) && !TextUtils.isEmpty(SoftApNetworkId.getInstance().getHomeNetName())) {
                    homeSSID.replaceAll("\"", Constants.MAIN_VERSION_TAG);
                    if (homeSSID.equals(SoftApNetworkId.getInstance().getHomeNetName())) {
                        startScanDevice(context);
                        return;
                    }
                    XConfigLog.w("SoftAp", "error 当前连接网络与家庭网络名称不一致");
                    if (!homeSSID.equals("<unknown ssid>")) {
                        XcLinkNetwork.linkHomeNetwork(context);
                        return;
                    }
                    return;
                }
                XConfigLog.w("SoftAp", "error 家庭网络都为空");
                XcLinkNetwork.linkHomeNetwork(context);
            }
        }

        private void scanApNetwork(String ssid) {
            if (SoftApScanWifiUtils.mScanCallback != null) {
                SoftApScanWifiUtils.mScanCallback.scanApCallback(ssid);
            }
        }

        private void startScanDevice(Context context) {
            int netmask = SoftApScanWifiUtils.mWifiManager.getDhcpInfo().netmask;
            int ipAddress = SoftApScanWifiUtils.mWifiManager.getDhcpInfo().ipAddress;
            if (netmask >= 0 && ipAddress != 0) {
                String homeBroadAddress = WifiBroadAddressUtils.getBroadcastAddress(netmask, ipAddress);
                SoftApNetworkId.getInstance().setHomeBroadAddress(homeBroadAddress);
                if (SoftApScanWifiUtils.mScanCallback != null) {
                    SoftApScanWifiUtils.mScanCallback.startScanDevice(homeBroadAddress);
                } else {
                    Toast.makeText(context, "缺少ScanCallback实例", 0).show();
                }
            }
        }

        private void startSoftAp(Context context, String apName) {
            int netmask = SoftApScanWifiUtils.mWifiManager.getDhcpInfo().netmask;
            int ipAddress = SoftApScanWifiUtils.mWifiManager.getDhcpInfo().ipAddress;
            if (netmask >= 0 && ipAddress != 0) {
                XConfigLog.w("SoftAp", "7---scanAp,接收到广播,连接到设备ap网络=" + apName);
                boolean unused = SoftApScanWifiUtils.isConnectAp = true;
                String apBroadAddress = WifiBroadAddressUtils.getBroadcastAddress(netmask, ipAddress);
                SoftApNetworkId.getInstance().setApBroadAddress(apBroadAddress);
                if (SoftApScanWifiUtils.mScanCallback != null) {
                    XConfigLog.w("SoftAp", "8---scanAp,准备进行设备配网,apBroadAddress=" + apBroadAddress + "//checkCode=" + SoftApStage.getInstance().getCheckCode());
                    SoftApScanWifiUtils.mScanCallback.startConfigNetwork(apBroadAddress, SoftApStage.getInstance().getCheckCode());
                } else {
                    Toast.makeText(context, "缺少ScanCallback实例", 0).show();
                }
            }
        }
    }
}
