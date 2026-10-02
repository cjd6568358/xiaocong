package com.alibaba.mtl.log.sign;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.j;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BaseRequestAuth implements IRequestAuth {
    private boolean D;
    private String Y;
    private String g;

    @Override // com.alibaba.mtl.log.sign.IRequestAuth
    public String getAppkey() {
        return this.g;
    }

    public BaseRequestAuth(String aAppkey, String aAppSecret, boolean isEncode) {
        this.g = null;
        this.Y = null;
        this.D = false;
        this.g = aAppkey;
        this.Y = aAppSecret;
        this.D = isEncode;
    }

    public boolean isEncode() {
        return this.D;
    }

    @Override // com.alibaba.mtl.log.sign.IRequestAuth
    public String getSign(String toBeSignedStr) {
        if (this.g == null || this.Y == null) {
            i.a("BaseRequestAuth", "There is no appkey,please check it!");
            return null;
        }
        if (toBeSignedStr != null) {
            return j.a(j.m26a((toBeSignedStr + this.Y).getBytes()));
        }
        return null;
    }
}
