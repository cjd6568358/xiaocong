package com.xiaocong.smarthome.sdk.mqtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class DeviceStatusReceiver extends BroadcastReceiver {
    protected abstract void onReceiveDeviceSnapshot(String str, String str2, boolean z);

    protected abstract void onReceiveDeviceStatus(String str, int i, boolean z);

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String topic = intent.getStringExtra("mqttTopic");
        String snapshotDevId = intent.getStringExtra("snapshotDeviceId");
        String snapshot = intent.getStringExtra("snapshotMsg");
        boolean isConnect = intent.getBooleanExtra("paho_mqtt_isconnect", true);
        int deviceStatus = intent.getIntExtra("snapshotOnlineStatus", 1);
        if (!TextUtils.isEmpty(topic)) {
            if (topic.equals("offline") || topic.equals("online")) {
                onReceiveDeviceStatus(snapshotDevId, deviceStatus, isConnect);
                return;
            } else {
                onReceiveDeviceSnapshot(snapshotDevId, snapshot, isConnect);
                return;
            }
        }
        onReceiveDeviceSnapshot(snapshotDevId, snapshot, isConnect);
    }
}
