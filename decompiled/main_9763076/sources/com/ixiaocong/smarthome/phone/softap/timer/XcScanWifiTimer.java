package com.ixiaocong.smarthome.phone.softap.timer;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.ixiaocong.log.XConfigLog;
import com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApCheckUtils;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApNetworkId;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XcScanWifiTimer extends DeviceScanner {
    private ScanWifiCallback mCallback;
    private String mHomeSSID;
    private String mProductId;
    private WifiManager mWifiManager;

    public XcScanWifiTimer(Context context, ScanWifiCallback callback, String productId, String homeSSID) {
        super(context);
        this.mContext = context;
        this.mCallback = callback;
        this.mProductId = productId;
        this.mHomeSSID = homeSSID;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    public XcScanWifiTimer(Context context, ScanWifiCallback callback, String productId) {
        super(context);
        this.mContext = context;
        this.mCallback = callback;
        this.mProductId = productId;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
    }

    public void startDeviceScan() {
        if (this.mTimer == null) {
            this.mTimer = new Timer();
        }
        this.mTimer.schedule(new TimerTask() { // from class: com.ixiaocong.smarthome.phone.softap.timer.XcScanWifiTimer.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (SoftApStage.getInstance().getStage() != -1) {
                    if (TextUtils.isEmpty(SoftApNetworkId.getInstance().getSoftApName()) || SoftApNetworkId.getInstance().getHomeNetId() == -1) {
                        XConfigLog.e("SoftAp", "2---startTimer,同步扫描获取配置的Wi-Fi信息");
                        XcScanWifiTimer.this.mWifiManager.startScan();
                        XcScanWifiTimer.this.checkAp();
                    }
                }
            }
        }, 0L, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAp() {
        List<ScanResult> scanList = this.mWifiManager.getScanResults();
        if (scanList != null && scanList.size() > 0) {
            for (int i = 0; i < scanList.size(); i++) {
                if (scanList.get(i).SSID.contains("smart-" + this.mProductId + "-")) {
                    if (SoftApCheckUtils.verifySSID(scanList.get(i).SSID)) {
                        XConfigLog.e("SoftAp", "2---scanAp timer,筛选符合规则的ap--" + scanList.get(i).SSID);
                        SoftApNetworkId.getInstance().setSoftApName(scanList.get(i).SSID);
                        this.mCallback.scanApCallback(scanList.get(i).SSID);
                    }
                } else if (!TextUtils.isEmpty(this.mHomeSSID) && this.mHomeSSID.equals(scanList.get(i).SSID)) {
                    SoftApNetworkId.getInstance().setHomeNetName(this.mHomeSSID);
                    WifiInfo info = this.mWifiManager.getConnectionInfo();
                    int netWorkID = info.getNetworkId();
                    if (netWorkID != -1) {
                        XConfigLog.e("SoftAp", "scanAp --- timer---获取到家庭网络ID--" + scanList.get(i).SSID);
                        SoftApNetworkId.getInstance().setHomeNetId(netWorkID);
                    }
                }
            }
        }
    }

    public void stopDeviceScan() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
        SoftApStage.getInstance().setStage(-1);
    }
}
