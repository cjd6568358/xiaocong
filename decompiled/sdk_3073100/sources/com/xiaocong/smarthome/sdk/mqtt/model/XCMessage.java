package com.xiaocong.smarthome.sdk.mqtt.model;

import com.xiaocong.smarthome.network.BuildConfig;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCMessage {
    private Integer code;
    protected Long messageId;
    protected String protocolVersion = BuildConfig.VERSION_NAME;
    protected String receiveId;
    protected String senderId;

    public String getSenderId() {
        return this.senderId;
    }

    public String getReceiveId() {
        return this.receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
