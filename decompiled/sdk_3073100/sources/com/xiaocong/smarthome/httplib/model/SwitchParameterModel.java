package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwitchParameterModel {
    private Switch1Model switch1;
    private Switch2Model switch2;
    private Switch3Model switch3;

    public Switch3Model getSwitch3() {
        return this.switch3;
    }

    public void setSwitch3(Switch3Model switch3) {
        this.switch3 = switch3;
    }

    public Switch2Model getSwitch2() {
        return this.switch2;
    }

    public void setSwitch2(Switch2Model switch2) {
        this.switch2 = switch2;
    }

    public Switch1Model getSwitch1() {
        return this.switch1;
    }

    public void setSwitch1(Switch1Model switch1) {
        this.switch1 = switch1;
    }

    public static class Switch3Model {
        private String deviceParameterId;
        private String parameterName;

        public void setDeviceParameterId(String deviceParameterId) {
            this.deviceParameterId = deviceParameterId;
        }

        public String getDeviceParameterId() {
            return this.deviceParameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }
    }

    public static class Switch2Model {
        private String deviceParameterId;
        private String parameterName;

        public void setDeviceParameterId(String deviceParameterId) {
            this.deviceParameterId = deviceParameterId;
        }

        public String getDeviceParameterId() {
            return this.deviceParameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }
    }

    public static class Switch1Model {
        private String deviceParameterId;
        private String parameterName;

        public void setDeviceParameterId(String deviceParameterId) {
            this.deviceParameterId = deviceParameterId;
        }

        public String getDeviceParameterId() {
            return this.deviceParameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }
    }
}
