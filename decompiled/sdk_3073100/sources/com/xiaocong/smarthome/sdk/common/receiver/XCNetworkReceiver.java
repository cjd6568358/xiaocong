package com.xiaocong.smarthome.sdk.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService;
import com.xiaocong.smarthome.util.log.XCLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCNetworkReceiver extends BroadcastReceiver {
    private boolean isConnect = true;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                if (!this.isConnect) {
                    XCLog.e("NetworkReceiver", "网络已连接----");
                    if (!XCMqttService.isConnected()) {
                        XCMqttService.actionStart(context);
                        XCLog.i("NetworkReceiver", "XCMqttService   actionStart");
                    }
                }
            } else if (this.isConnect) {
                XCMqttService.actionStop(context);
                XCLog.e("NetworkReceiver", "当前网络已断开");
            }
            this.isConnect = NetworkUtils.isNetworkAvailable(context);
        }
    }
}
