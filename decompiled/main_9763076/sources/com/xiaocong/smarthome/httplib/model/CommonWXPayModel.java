package com.xiaocong.smarthome.httplib.model;

import com.alibaba.fastjson.annotation.JSONField;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CommonWXPayModel {
    private String appId;
    private String nonceStr;
    private String orderId;

    @JSONField(name = "package")
    private String packageValue;
    private String partnerId;
    private String payId;
    private String paySign;
    private String signType;
    private String timestamp;

    public String getPartnerId() {
        return this.partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPackageValue() {
        return this.packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPayId() {
        return this.payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return this.nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return this.signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return this.paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
