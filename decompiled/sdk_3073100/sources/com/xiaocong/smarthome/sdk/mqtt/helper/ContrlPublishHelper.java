package com.xiaocong.smarthome.sdk.mqtt.helper;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.mqtt.model.XCControlMessage;
import com.xiaocong.smarthome.sdk.mqtt.model.XCManageMessage;
import com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService;
import com.xiaocong.smarthome.sdk.mqtt.utils.RandomUtils;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.xiaocong.smarthome.uilib.widget.XCToastUtil;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ContrlPublishHelper {
    public static void contrlPublish(Context context, String deviceId, String contrlId, Object status) {
        XCControlMessage controlMessage = new XCControlMessage();
        Map<String, Object> params = new HashMap<>();
        params.put(contrlId, status);
        controlMessage.setMessageId(Long.valueOf(RandomUtils.getRandomInt(9)));
        controlMessage.setReceiveId(deviceId);
        controlMessage.setSenderId(XCHelp.mClientId);
        controlMessage.setCommand(params);
        if (NetworkUtils.isNetworkAvailable(context)) {
            if (XCMqttService.isConnected()) {
                XCMqttService.publishMsg(context, "control", JSON.toJSONString(controlMessage), deviceId);
                return;
            } else {
                XCToastUtil.showToast(context, "正在连接,请稍后重试", 0);
                XCMqttService.actionReconnect(context);
                return;
            }
        }
        XCToastUtil.showToast(context, "请检查当前网络连接", 0);
    }

    public static void contrlPublish(Context context, String deviceId, Map<String, Object> params) {
        XCControlMessage controlMessage = new XCControlMessage();
        controlMessage.setMessageId(Long.valueOf(RandomUtils.getRandomInt(9)));
        controlMessage.setReceiveId(deviceId);
        controlMessage.setSenderId(XCHelp.mClientId);
        controlMessage.setCommand(params);
        if (NetworkUtils.isNetworkAvailable(context)) {
            if (XCMqttService.isConnected()) {
                XCMqttService.publishMsg(context, "control", JSON.toJSONString(controlMessage), deviceId);
                return;
            } else {
                XCToastUtil.showToast(context, "正在连接,请稍后重试", 0);
                XCMqttService.actionReconnect(context);
                return;
            }
        }
        XCToastUtil.showToast(context, "请检查当前网络连接", 0);
    }

    public static void appendZibeePublish(Context context, String gatewayId, String productId, String moudelId) {
        XCManageMessage manageMessage = new XCManageMessage();
        manageMessage.setSenderId(XCHelp.mClientId);
        manageMessage.setReceiveId(gatewayId);
        manageMessage.setProductId(productId);
        manageMessage.setModuleId(moudelId);
        manageMessage.setType("add");
        manageMessage.setMessageId(Long.valueOf(RandomUtils.getRandomInt(9)));
        if (NetworkUtils.isNetworkAvailable(context)) {
            if (XCMqttService.isConnected()) {
                XCMqttService.publishMsg(context, "manage", JSON.toJSONString(manageMessage), gatewayId);
                return;
            } else {
                XCMqttService.actionReconnect(context);
                return;
            }
        }
        XCToastUtil.showToast(context, "请检查当前网络连接", 0);
    }
}
