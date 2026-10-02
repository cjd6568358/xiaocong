package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceParameterModifiableModel {
    private List<ParametersBean> parameters;

    public List<ParametersBean> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<ParametersBean> parameters) {
        this.parameters = parameters;
    }

    public static class ParametersBean {
        private String deviceParameterId;
        private String parameterName;

        public String getDeviceParameterId() {
            return this.deviceParameterId;
        }

        public void setDeviceParameterId(String deviceParameterId) {
            this.deviceParameterId = deviceParameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }
    }
}
