package com.kookong.sdk.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ManualMatchLineupData {
    private List<Channel> list;
    private String result;
    private int resultCode;

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int i) {
        this.resultCode = i;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public List<Channel> getList() {
        return this.list;
    }

    public void setList(List<Channel> list) {
        this.list = list;
    }
}
