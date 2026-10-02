package com.baidu.uaq.agent.android.harvest.bean;

/* JADX INFO: compiled from: EnvironmentInformation.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private String aR;
    private long ac;
    private String ad;
    private long[] ae;
    private int orientation;

    public d() {
    }

    public d(long memoryUsage, int orientation, String networkStatus, String networkWanType, long[] diskAvailable) {
        this.ac = memoryUsage;
        this.orientation = orientation;
        this.ad = networkStatus;
        this.aR = networkWanType;
        this.ae = diskAvailable;
    }

    public void i(long memoryUsage) {
        this.ac = memoryUsage;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void s(String networkStatus) {
        this.ad = networkStatus;
    }

    public void t(String networkWanType) {
        this.aR = networkWanType;
    }

    public void a(long[] diskAvailable) {
        this.ae = diskAvailable;
    }

    public long ar() {
        return this.ac;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public String as() {
        return this.ad;
    }

    public long[] at() {
        return this.ae;
    }
}
