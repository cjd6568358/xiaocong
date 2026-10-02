package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SharedMemberModel {
    private List<MemberModel> list;

    public void setList(List<MemberModel> list) {
        this.list = list;
    }

    public List<MemberModel> getList() {
        return this.list;
    }

    public static class MemberModel {
        private String nickname;
        private String portrait;
        private String uid;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
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
