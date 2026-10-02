package com.xiaocong.smarthome.sdk.http.bean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCResponseBean {
    private String data;
    private Boolean success;
    private Integer code = -1;
    private String msg = "";

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String toString() {
        return "XCResponseBean{code=" + this.code + ", msg='" + this.msg + "', data='" + this.data + "', success=" + this.success + '}';
    }
}
