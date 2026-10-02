package com.ixiaocong.smarthome.phone.softap.utils;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftApStage {
    private String checkCode;
    private String discoverTime;
    private int stage;
    private boolean isStartHttp = true;
    private boolean isError = false;

    public static SoftApStage getInstance() {
        return SoftApStageHolder.INSTANCE;
    }

    public boolean isStartHttp() {
        return this.isStartHttp;
    }

    public int getStage() {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getCheckCode() {
        return this.checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getDiscoverTime() {
        return this.discoverTime;
    }

    public void setDiscoverTime(String discoverTime) {
        this.discoverTime = discoverTime;
    }

    public boolean isError() {
        return this.isError;
    }

    public void setError(boolean error) {
        this.isError = error;
    }

    public void clearStage() {
        this.isStartHttp = false;
        this.checkCode = Constants.MAIN_VERSION_TAG;
        this.stage = -1;
        this.isError = false;
    }

    public boolean isStartStage() {
        return getStage() != -1;
    }

    private static final class SoftApStageHolder {
        private static final SoftApStage INSTANCE = new SoftApStage();
    }
}
