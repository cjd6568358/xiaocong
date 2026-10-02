package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceLogsModel {
    private List<DeviceLog> list;

    public void setList(List<DeviceLog> list) {
        this.list = list;
    }

    public List<DeviceLog> getList() {
        return this.list;
    }

    public class DeviceLog {
        private String queryId;
        private int state;
        private String t;

        public DeviceLog() {
        }

        public void setT(String t) {
            this.t = t;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setQueryId(String queryId) {
            this.queryId = queryId;
        }

        public String getT() {
            return this.t;
        }

        public int getState() {
            return this.state;
        }

        public String getQueryId() {
            return this.queryId;
        }
    }
}
