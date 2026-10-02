package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CodeListModel {
    private int code;
    private String codeName;
    private int infraredCodeId;

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setInfraredCodeId(int infraredCodeId) {
        this.infraredCodeId = infraredCodeId;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.codeName;
    }

    public int getInfraredCodeId() {
        return this.infraredCodeId;
    }
}
