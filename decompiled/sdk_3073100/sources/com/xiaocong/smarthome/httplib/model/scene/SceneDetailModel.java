package com.xiaocong.smarthome.httplib.model.scene;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SceneDetailModel {
    private TriggerModel trigger;

    public TriggerModel getTrigger() {
        return this.trigger;
    }

    public void setTrigger(TriggerModel trigger) {
        this.trigger = trigger;
    }

    public static class TriggerModel {
        private List<RelateActionModel> actionList;
        private String deviceId;
        private String deviceName;
        private String parameterKey;
        private String parameterName;
        private String parameterType;
        private String parameterValue;
        private String productParameterId;
        private int showType;
        private int source;
        private String startWorkTime;
        private int status;
        private String stopWorkTime;
        private String threshold;
        private String triggerCondition;
        private String triggerDesc;
        private String triggerIcon;
        private String triggerId;
        private String triggerIntro;
        private String triggerName;
        private String type;
        private String workday;

        public String getParameterType() {
            return this.parameterType;
        }

        public void setParameterType(String parameterType) {
            this.parameterType = parameterType;
        }

        public void setTriggerCondition(String triggerCondition) {
            this.triggerCondition = triggerCondition;
        }

        public void setTriggerName(String triggerName) {
            this.triggerName = triggerName;
        }

        public void setTriggerId(String triggerId) {
            this.triggerId = triggerId;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public void setTriggerDesc(String triggerDesc) {
            this.triggerDesc = triggerDesc;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setParameterValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setTriggerIcon(String triggerIcon) {
            this.triggerIcon = triggerIcon;
        }

        public void setParameterKey(String parameterKey) {
            this.parameterKey = parameterKey;
        }

        public void setTriggerIntro(String triggerIntro) {
            this.triggerIntro = triggerIntro;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public void setActionList(List<RelateActionModel> actionList) {
            this.actionList = actionList;
        }

        public String getTriggerCondition() {
            return this.triggerCondition;
        }

        public String getTriggerName() {
            return this.triggerName;
        }

        public String getTriggerId() {
            return this.triggerId;
        }

        public String getThreshold() {
            return this.threshold;
        }

        public String getTriggerDesc() {
            return this.triggerDesc;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public String getType() {
            return this.type;
        }

        public String getParameterValue() {
            return this.parameterValue;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getTriggerIcon() {
            return this.triggerIcon;
        }

        public String getParameterKey() {
            return this.parameterKey;
        }

        public String getTriggerIntro() {
            return this.triggerIntro;
        }

        public int getStatus() {
            return this.status;
        }

        public List<RelateActionModel> getActionList() {
            return this.actionList;
        }

        public int getSource() {
            return this.source;
        }

        public int getShowType() {
            return this.showType;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public String getWorkday() {
            return this.workday;
        }

        public void setWorkday(String workday) {
            this.workday = workday;
        }

        public String getStopWorkTime() {
            return this.stopWorkTime;
        }

        public void setStopWorkTime(String stopWorkTime) {
            this.stopWorkTime = stopWorkTime;
        }

        public String getStartWorkTime() {
            return this.startWorkTime;
        }

        public void setStartWorkTime(String startWorkTime) {
            this.startWorkTime = startWorkTime;
        }
    }
}
