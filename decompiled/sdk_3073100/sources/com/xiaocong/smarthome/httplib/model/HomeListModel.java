package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HomeListModel {
    private List<HomeListBean> homeList;

    public List<HomeListBean> getHomeList() {
        return this.homeList;
    }

    public void setHomeList(List<HomeListBean> homeList) {
        this.homeList = homeList;
    }

    public static class HomeListBean {
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
