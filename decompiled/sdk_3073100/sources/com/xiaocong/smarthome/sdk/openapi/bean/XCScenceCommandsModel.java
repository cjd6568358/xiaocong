package com.xiaocong.smarthome.sdk.openapi.bean;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCScenceCommandsModel {
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
        private int status;

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductParameterId() {
            return this.productParameterId;
        }

        public void setProductParameterId(String productParameterId) {
            this.productParameterId = productParameterId;
        }

        public String getParameterKey() {
            return this.parameterKey;
        }

        public void setParameterKey(String parameterKey) {
            this.parameterKey = parameterKey;
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

        public String getProductImage() {
            return this.productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public int getCommandId() {
            return this.commandId;
        }

        public void setCommandId(int commandId) {
            this.commandId = commandId;
        }
    }
}
