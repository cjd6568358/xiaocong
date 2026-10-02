package com.xiaocong.smarthome.httplib.model.request;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SceneCommandsModel {
    private List<CommandsModel> commands;

    public void setCommands(List<CommandsModel> commands) {
        this.commands = commands;
    }

    public List<CommandsModel> getCommands() {
        return this.commands;
    }

    public static class CommandsModel implements Serializable {
        private int commandId;
        private String deviceId;
        private String deviceName;
        private String parameterKey;
        private String parameterName;
        private String parameterType;
        private String parameterValue;
        private String productId;
        private String productImage;
        private String productParameterId;

        public void setCommandId(int commandId) {
            this.commandId = commandId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public void setParameterKey(String parameterKey) {
            this.parameterKey = parameterKey;
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

        public String getParameterName() {
            return this.parameterName;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public String getParameterKey() {
            return this.parameterKey;
        }

        public String getParameterType() {
            return this.parameterType;
        }

        public String getParameterValue() {
            return this.parameterValue;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public String getProductImage() {
            return this.productImage;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getCommandId() {
            return this.commandId;
        }
    }
}
