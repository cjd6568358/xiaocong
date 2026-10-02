package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCDeviceRelationListModel {
    private List<TriggersBean> triggers;

    public List<TriggersBean> getTriggers() {
        return this.triggers;
    }

    public void setTriggers(List<TriggersBean> triggers) {
        this.triggers = triggers;
    }

    public static class TriggersBean {
        private String alias;
        private String deviceId;
        private String deviceTriggerId;
        private String parameterType;
        private String productParameterId;
        private List<XCDeviceRelationModel> relateList;
        private String status;
        private String threshold;
        private String triggerCondition;
        private String triggerIcon;
        private String triggerName;

        public String getTriggerCondition() {
            return this.triggerCondition;
        }

        public void setTriggerCondition(String triggerCondition) {
            this.triggerCondition = triggerCondition;
        }

        public List<XCDeviceRelationModel> getRelateList() {
            return this.relateList;
        }

        public void setRelateList(List<XCDeviceRelationModel> relateList) {
            this.relateList = relateList;
        }

        public String getDeviceTriggerId() {
            return this.deviceTriggerId;
        }

        public void setDeviceTriggerId(String deviceTriggerId) {
            this.deviceTriggerId = deviceTriggerId;
        }

        public String getTriggerName() {
            return this.triggerName;
        }

        public void setTriggerName(String triggerName) {
            this.triggerName = triggerName;
        }

        public String getAlias() {
            return this.alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTriggerIcon() {
            return this.triggerIcon;
        }

        public void setTriggerIcon(String triggerIcon) {
            this.triggerIcon = triggerIcon;
        }

        public String getThreshold() {
            return this.threshold;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public String getParameterType() {
            return this.parameterType;
        }

        public void setParameterType(String parameterType) {
            this.parameterType = parameterType;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
