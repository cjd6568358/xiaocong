package com.xiaocong.smarthome.sdk.mqtt.model;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSnapshotMessage {
    private int code;
    private long messageId;
    private int networkType;
    private String protocolVersion;
    private String receiveId;
    private String senderId;
    private Map<String, Object> snapshot;
    private int status;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiveId() {
        return this.receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(int networkType) {
        this.networkType = networkType;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, Object> getSnapshot() {
        return this.snapshot;
    }

    public void setSnapshot(Map<String, Object> snapshot) {
        this.snapshot = snapshot;
    }
}
