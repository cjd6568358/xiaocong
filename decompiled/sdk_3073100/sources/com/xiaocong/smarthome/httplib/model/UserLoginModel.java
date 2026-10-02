package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UserLoginModel {
    private String nickname;
    private String openId;
    private String phone;
    private String portrait;
    private String token;
    private String uid;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPortrait() {
        return this.portrait;
    }

    public String getToken() {
        return this.token;
    }

    public String getOpenId() {
        return this.openId;
    }

    public String getUid() {
        return this.uid;
    }
}
