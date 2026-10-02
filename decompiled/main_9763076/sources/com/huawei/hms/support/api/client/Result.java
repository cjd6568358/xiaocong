package com.huawei.hms.support.api.client;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class Result {
    private Status a = Status.FAILURE;

    public Status getStatus() {
        return this.a;
    }

    public void setStatus(Status status) {
        if (status != null) {
            this.a = status;
        }
    }
}
