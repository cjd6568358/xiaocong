package com.xiaocong.smarthome.sdk.mqtt.model;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCControlMessage extends XCMessage {
    private Map<String, Object> command;

    public Map<String, Object> getCommand() {
        return this.command;
    }

    public void setCommand(Map<String, Object> command) {
        this.command = command;
    }
}
