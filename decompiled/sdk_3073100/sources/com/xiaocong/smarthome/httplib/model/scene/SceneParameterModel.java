package com.xiaocong.smarthome.httplib.model.scene;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SceneParameterModel {
    private List<ParameterModel> parameters;

    public void setParameters(List<ParameterModel> parameters) {
        this.parameters = parameters;
    }

    public List<ParameterModel> getParameters() {
        return this.parameters;
    }

    public static class ParameterModel {
        private String parameterKey;
        private String parameterName;
        private String parameterType;
        private String parameterValue;
        private String productParameterId;
        private int showType;

        public void setParameterKey(String parameterKey) {
            this.parameterKey = parameterKey;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public void setParameterType(String parameterType) {
            this.parameterType = parameterType;
        }

        public void setParameterValue(String parameterValue) {
            this.parameterValue = parameterValue;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public String getParameterKey() {
            return this.parameterKey;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public String getParameterName() {
            return this.parameterName;
        }

        public String getParameterValue() {
            return this.parameterValue;
        }

        public String getParameterType() {
            return this.parameterType;
        }

        public int getShowType() {
            return this.showType;
        }
    }
}
