package com.xiaocong.smarthome.sdk.mqtt.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSubscribeMessage extends XCMessage {
    private List<String> subscribeIds;

    public List<String> getSubscribeIds() {
        return this.subscribeIds;
    }

    public void setSubscribeIds(List<String> subscribeIds) {
        this.subscribeIds = subscribeIds;
    }
}
