package com.xiaocong.smarthome.sdk.openapi.bean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSDKUserModel {
    private UserBean user;

    public UserBean getUser() {
        return this.user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        private String nickname;
        private String phone;
        private String portrait;
        private String uid;

        public String getNickname() {
            return this.nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPortrait() {
            return this.portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getUid() {
            return this.uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPhone() {
            return this.phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
