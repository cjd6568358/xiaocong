package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSceneParameterModel {
    private List<ParameterModel> parameters;

    public List<ParameterModel> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<ParameterModel> parameters) {
        this.parameters = parameters;
    }

    public static class ParameterModel {
        private String parameterKey;
        private String parameterName;
        private String parameterType;
        private String parameterValue;
        private String productParameterId;
        private int showType;

        public String getParameterKey() {
            return this.parameterKey;
        }

        public void setParameterKey(String parameterKey) {
            this.parameterKey = parameterKey;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getParameterType() {
            return this.parameterType;
        }

        public void setParameterType(String parameterType) {
            this.parameterType = parameterType;
        }

        public String getParameterValue() {
            return this.parameterValue;
        }

        public void setParameterValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public int getShowType() {
            return this.showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }
    }
}
