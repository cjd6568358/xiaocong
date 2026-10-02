package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceSdkCheckModel {
    private int sdkId;
    private int upgrade;
    private String upgradeMsg;

    public String getUpgradeMsg() {
        return this.upgradeMsg;
    }

    public void setUpgradeMsg(String upgradeMsg) {
        this.upgradeMsg = upgradeMsg;
    }

    public int getUpgrade() {
        return this.upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public int getSdkId() {
        return this.sdkId;
    }

    public void setSdkId(int sdkId) {
        this.sdkId = sdkId;
    }
}
