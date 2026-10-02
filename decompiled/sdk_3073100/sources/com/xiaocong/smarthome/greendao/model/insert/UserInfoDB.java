package com.xiaocong.smarthome.greendao.model.insert;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UserInfoDB {
    private String nickname;
    private String phone;
    private String uid;
    private String userId;
    private String userImg;

    public UserInfoDB(String userId, String nickname, String phone, String userImg, String uid) {
        this.userId = userId;
        this.nickname = nickname;
        this.phone = phone;
        this.userImg = userImg;
        this.uid = uid;
    }

    public UserInfoDB() {
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserImg() {
        return this.userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
