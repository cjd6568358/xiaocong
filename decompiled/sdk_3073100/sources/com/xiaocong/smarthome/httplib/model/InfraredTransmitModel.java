package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class InfraredTransmitModel {
    private List<CodeListModel> codeList;
    private String[] enabledCodes;

    public String[] getEnabledCodes() {
        return this.enabledCodes;
    }

    public List<CodeListModel> getCodeList() {
        return this.codeList;
    }

    public void setCodeList(List<CodeListModel> codeList) {
        this.codeList = codeList;
    }

    public void setEnabledCodes(String[] enabledCodes) {
        this.enabledCodes = enabledCodes;
    }
}
