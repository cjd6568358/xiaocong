package com.ixiaocong.smarthome.phone.softap.utils;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApNetworkId {
    private String apBroadAddress;
    private String homeBroadAddress;
    private String homeNetName;
    private String softApName;
    private int softApId = -1;
    private int homeNetId = -1;

    public static SoftApNetworkId getInstance() {
        return SoftApNetworkIdHolder.INSTANCE;
    }

    public int getHomeNetId() {
        return this.homeNetId;
    }

    public int getSoftApId() {
        return this.softApId;
    }

    public String getHomeNetName() {
        return this.homeNetName;
    }

    public String getSoftApName() {
        return this.softApName;
    }

    public void setHomeNetId(int homeNetId) {
        this.homeNetId = homeNetId;
    }

    public void setSoftApId(int softApId) {
        this.softApId = softApId;
    }

    public void setHomeNetName(String homeNetName) {
        this.homeNetName = homeNetName.replaceAll("/", Constants.MAIN_VERSION_TAG);
    }

    public void setSoftApName(String softApName) {
        this.softApName = softApName.replaceAll("/", Constants.MAIN_VERSION_TAG);
    }

    public void setApBroadAddress(String apBroadAddress) {
        this.apBroadAddress = apBroadAddress;
    }

    public void setHomeBroadAddress(String homeBroadAddress) {
        this.homeBroadAddress = homeBroadAddress;
    }

    public void clearId() {
        setHomeNetId(-1);
        setSoftApId(-1);
        setSoftApName(Constants.MAIN_VERSION_TAG);
        setHomeNetName(Constants.MAIN_VERSION_TAG);
        setApBroadAddress(Constants.MAIN_VERSION_TAG);
        setHomeBroadAddress(Constants.MAIN_VERSION_TAG);
    }

    private static final class SoftApNetworkIdHolder {
        private static final SoftApNetworkId INSTANCE = new SoftApNetworkId();
    }
}
