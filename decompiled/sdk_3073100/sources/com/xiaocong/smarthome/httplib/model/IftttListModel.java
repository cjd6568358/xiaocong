package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class IftttListModel {
    private List<IftttTriggers> triggers;

    public void setTriggers(List<IftttTriggers> triggers) {
        this.triggers = triggers;
    }

    public List<IftttTriggers> getTriggers() {
        return this.triggers;
    }

    public class IftttTriggers {
        private String alias;
        private String deviceId;
        private String deviceTriggerId;
        private String parameterType;
        private String productParameterId;
        private List<IftttRelateModel> relateList;
        private String status;
        private String threshold;
        private String triggerCondition;
        private String triggerIcon;
        private String triggerName;

        public IftttTriggers() {
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public void setParameterType(String parameterType) {
            this.parameterType = parameterType;
        }

        public void setTriggerCondition(String triggerCondition) {
            this.triggerCondition = triggerCondition;
        }

        public String getTriggerCondition() {
            return this.triggerCondition;
        }

        public void setRelateList(List<IftttRelateModel> relateList) {
            this.relateList = relateList;
        }

        public List<IftttRelateModel> getRelateList() {
            return this.relateList;
        }

        public void setDeviceTriggerId(String deviceTriggerId) {
            this.deviceTriggerId = deviceTriggerId;
        }

        public String getDeviceTriggerId() {
            return this.deviceTriggerId;
        }

        public void setTriggerName(String triggerName) {
            this.triggerName = triggerName;
        }

        public String getTriggerName() {
            return this.triggerName;
        }

        public void setTriggerIcon(String triggerIcon) {
            this.triggerIcon = triggerIcon;
        }

        public String getTriggerIcon() {
            return this.triggerIcon;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public String getThreshold() {
            return this.threshold;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        public String getAlias() {
            return this.alias;
        }

        public String getParameterType() {
            return this.parameterType;
        }
    }
}
