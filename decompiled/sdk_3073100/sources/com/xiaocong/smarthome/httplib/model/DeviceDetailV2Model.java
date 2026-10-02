package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceDetailV2Model {
    private String deviceId;
    private DeviceInfoBean deviceInfo;
    private String parameterList;
    private RnInfoBean rnInfo;
    private SdkInfoBean sdkInfo;
    private String snapshot;
    private int status;

    public static class SdkInfoBean {
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceInfoBean getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getParameterList() {
        return this.parameterList;
    }

    public void setParameterList(String parameterList) {
        this.parameterList = parameterList;
    }

    public RnInfoBean getRnInfo() {
        return this.rnInfo;
    }

    public void setRnInfo(RnInfoBean rnInfo) {
        this.rnInfo = rnInfo;
    }

    public SdkInfoBean getSdkInfo() {
        return this.sdkInfo;
    }

    public void setSdkInfo(SdkInfoBean sdkInfo) {
        this.sdkInfo = sdkInfo;
    }

    public String getSnapshot() {
        return this.snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DeviceInfoBean {
        private String deviceName;
        private int isAdmin;
        private int productId;
        private String top;

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public int getIsAdmin() {
            return this.isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getProductId() {
            return this.productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getTop() {
            return this.top;
        }

        public void setTop(String top) {
            this.top = top;
        }
    }

    public static class RnInfoBean {
        private String downloadUrl;
        private String fileName;
        private String md5;
        private String upgrade;
        private String version;

        public String getUpgrade() {
            return this.upgrade;
        }

        public void setUpgrade(String upgrade) {
            this.upgrade = upgrade;
        }

        public String getMd5() {
            return this.md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getDownloadUrl() {
            return this.downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getFileName() {
            return this.fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
