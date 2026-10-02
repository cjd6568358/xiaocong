package com.baidu.uaq.agent.android.harvest;

/* JADX INFO: compiled from: HarvestResponse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private String ay;
    private long az;
    private int statusCode;

    public boolean isError() {
        return this.statusCode != 200;
    }

    public boolean Y() {
        return this.statusCode == 200;
    }

    public boolean Z() {
        return this.statusCode == 200 || this.statusCode == 201 || this.statusCode == 202 || this.statusCode == 203 || this.statusCode == 204 || this.statusCode == 205 || this.statusCode == 206 || this.statusCode == 207 || this.statusCode == 208;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String aa() {
        return this.ay;
    }

    public void f(String responseBody) {
        this.ay = responseBody;
    }

    public long ab() {
        return this.az;
    }

    public void f(long responseTime) {
        this.az = responseTime;
    }
}
