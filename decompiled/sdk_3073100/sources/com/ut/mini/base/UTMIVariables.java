package com.ut.mini.base;

import com.ut.mini.sdkevents.UTMI1010_2001Event;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTMIVariables {
    private static UTMIVariables a = new UTMIVariables();
    private String am = null;
    private String aj = null;
    private String an = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UTMI1010_2001Event f2a = null;
    private boolean R = false;

    public synchronized void setToAliyunOSPlatform() {
        this.R = true;
    }

    public synchronized boolean isAliyunOSPlatform() {
        return this.R;
    }

    public synchronized void setUTMI1010_2001EventInstance(UTMI1010_2001Event aInstance) {
        this.f2a = aInstance;
    }

    public synchronized UTMI1010_2001Event getUTMI1010_2001EventInstance() {
        return this.f2a;
    }

    public static UTMIVariables getInstance() {
        return a;
    }

    public String getH5Url() {
        return this.an;
    }

    public void setH5Url(String aH5Url) {
        this.an = aH5Url;
    }

    public String getRefPage() {
        return this.aj;
    }

    public void setRefPage(String aRefPage) {
        this.aj = aRefPage;
    }

    public String getH5RefPage() {
        return this.am;
    }

    public void setH5RefPage(String aH5PrePage) {
        this.am = aH5PrePage;
    }
}
