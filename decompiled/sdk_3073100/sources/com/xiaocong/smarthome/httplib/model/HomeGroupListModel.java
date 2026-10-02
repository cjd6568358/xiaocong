package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HomeGroupListModel {
    private List<GroupListBean> groupList;

    public List<GroupListBean> getGroupList() {
        return this.groupList;
    }

    public void setGroupList(List<GroupListBean> groupList) {
        this.groupList = groupList;
    }

    public static class GroupListBean {
        private String id;
        private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
