package com.ut.mini.core.sign;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.j;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTBaseRequestAuthentication implements IUTRequestAuthentication {
    private boolean D;
    private String Y;
    private String g;

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getAppkey() {
        return this.g;
    }

    public String getAppSecret() {
        return this.Y;
    }

    public UTBaseRequestAuthentication(String aAppkey, String aAppSecret) {
        this.g = null;
        this.Y = null;
        this.D = false;
        this.g = aAppkey;
        this.Y = aAppSecret;
    }

    public UTBaseRequestAuthentication(String aAppkey, String aAppSecret, boolean isEncode) {
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

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getSign(String toBeSignedStr) {
        if (this.g == null || this.Y == null) {
            i.a("UTBaseRequestAuthentication", "There is no appkey,please check it!");
            return null;
        }
        if (toBeSignedStr != null) {
            return j.a(j.a((toBeSignedStr + this.Y).getBytes()));
        }
        return null;
    }
}
