package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HomeHasSomeoneModel {
    private List<HasSomeoneModel> list;
    private String queryId;

    public List<HasSomeoneModel> getList() {
        return this.list;
    }

    public void setList(List<HasSomeoneModel> list) {
        this.list = list;
    }

    public String getQueryId() {
        return this.queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public class HasSomeoneModel {
        private int status;
        private long timestamp;

        public HasSomeoneModel() {
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
