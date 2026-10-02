package com.ut.mini;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UTPageHitHelper$UTPageEventObject {

    /* JADX INFO: renamed from: A, reason: collision with other field name */
    private Map<String, String> f0A = new HashMap();
    private long A = 0;
    private Uri a = null;
    private String ai = null;
    private String aj = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private UTPageStatus f1a = null;
    private boolean O = false;
    private boolean P = false;
    private boolean Q = false;
    private String ak = null;

    public void setCacheKey(String aCacheKey) {
        this.ak = aCacheKey;
    }

    public String getCacheKey() {
        return this.ak;
    }

    public void resetPropertiesWithoutSkipFlagAndH5Flag() {
        this.f0A = new HashMap();
        this.A = 0L;
        this.a = null;
        this.ai = null;
        this.aj = null;
        if (this.f1a == null || this.f1a != UTPageStatus.UT_H5_IN_WebView) {
            this.f1a = null;
        }
        this.O = false;
        this.Q = false;
    }

    public boolean isH5Called() {
        return this.Q;
    }

    public void setH5Called() {
        this.Q = true;
    }

    public void setToSkipPage() {
        this.P = true;
    }

    public boolean isSkipPage() {
        return this.P;
    }

    public void setPageAppearCalled() {
        this.O = true;
    }

    public boolean isPageAppearCalled() {
        return this.O;
    }

    public void setPageStatus(UTPageStatus aPageStatus) {
        this.f1a = aPageStatus;
    }

    public UTPageStatus getPageStatus() {
        return this.f1a;
    }

    public Map<String, String> getPageProperties() {
        return this.f0A;
    }

    public void setPageProperties(Map<String, String> aPageProperties) {
        this.f0A = aPageProperties;
    }

    public long getPageStayTimstamp() {
        return this.A;
    }

    public void setPageStayTimstamp(long aPageStayTimstamp) {
        this.A = aPageStayTimstamp;
    }

    public Uri getPageUrl() {
        return this.a;
    }

    public void setPageUrl(Uri aPageUrl) {
        this.a = aPageUrl;
    }

    public void setPageName(String aPageName) {
        this.ai = aPageName;
    }

    public String getPageName() {
        return this.ai;
    }

    public void setRefPage(String aRefPage) {
        this.aj = aRefPage;
    }

    public String getRefPage() {
        return this.aj;
    }
}
