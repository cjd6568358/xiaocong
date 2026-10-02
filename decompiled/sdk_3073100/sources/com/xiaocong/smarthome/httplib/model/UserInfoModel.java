package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UserInfoModel {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public class User {
        private String nickname;
        private String phone;
        private String portrait;
        private String uid;

        public User() {
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhone() {
            return this.phone;
        }

        public String getUid() {
            return this.uid;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getPortrait() {
            return this.portrait;
        }
    }
}
