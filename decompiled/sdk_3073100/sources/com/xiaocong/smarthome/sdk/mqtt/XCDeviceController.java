package com.xiaocong.smarthome.sdk.mqtt;

import android.content.Context;
import com.xiaocong.smarthome.sdk.mqtt.helper.ContrlPublishHelper;
import com.xiaocong.smarthome.sdk.mqtt.helper.MqttObserver;
import com.xiaocong.smarthome.sdk.mqtt.helper.XCMqttOberserverManager;
import com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCDeviceController {
    private boolean isShowDialog = true;

    private static class XcMqttManagerHolder {
        public static final XCDeviceController mqttManager = new XCDeviceController();
    }

    public static XCDeviceController getInstance() {
        return XcMqttManagerHolder.mqttManager;
    }

    public void XCDeviceControllerDelegate(MqttObserver observer) {
        XCMqttOberserverManager.getDefault().register(observer);
    }

    public void unregistDelegate(MqttObserver observer) {
        XCMqttOberserverManager.getDefault().unregister(observer);
    }

    public boolean XCDeviceControllerStatus() {
        return XCMqttService.isConnected();
    }

    public int XCDeviceControllerStatusInt() {
        return XCMqttService.getMqttStatus();
    }

    public void XCDeviceControllerReconnect(Context context) {
        XCMqttService.actionReconnect(context);
    }

    public void XCDeviceControllerStop(Context context) {
        XCMqttService.actionStop(context);
    }

    public void publishJsonObject(Context context, String deviceId, String contrlId, Object status) {
        ContrlPublishHelper.contrlPublish(context, deviceId, contrlId, status);
    }

    public void publishJsonObject(Context context, String deviceId, Map<String, Object> params) {
        ContrlPublishHelper.contrlPublish(context, deviceId, params);
    }

    public void appendZibeePublish(Context context, String gatewayId, String productId, String moudelId) {
        ContrlPublishHelper.appendZibeePublish(context, gatewayId, productId, moudelId);
    }

    public boolean getShowDialog() {
        return this.isShowDialog;
    }
}
