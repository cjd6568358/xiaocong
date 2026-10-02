package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwitchRelationTriggerParameterModel {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return this.list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String relationDeviceId;
        private String relationDeviceName;
        private String relationParameterId;
        private String relationParameterName;
        private String selected;

        public String getRelationDeviceId() {
            return this.relationDeviceId;
        }

        public void setRelationDeviceId(String relationDeviceId) {
            this.relationDeviceId = relationDeviceId;
        }

        public String getRelationParameterName() {
            return this.relationParameterName;
        }

        public void setRelationParameterName(String relationParameterName) {
            this.relationParameterName = relationParameterName;
        }

        public String getRelationDeviceName() {
            return this.relationDeviceName;
        }

        public void setRelationDeviceName(String relationDeviceName) {
            this.relationDeviceName = relationDeviceName;
        }

        public String getRelationParameterId() {
            return this.relationParameterId;
        }

        public void setRelationParameterId(String relationParameterId) {
            this.relationParameterId = relationParameterId;
        }

        public String getSelected() {
            return this.selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }
    }
}
