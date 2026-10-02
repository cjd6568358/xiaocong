package com.xiaocong.smarthome.greendao.model.insert;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ClientConfigDB {
    private String cliendId;
    private long id;
    private String live;
    private String uid;

    public ClientConfigDB(long id, String live, String cliendId, String uid) {
        this.id = id;
        this.live = live;
        this.cliendId = cliendId;
        this.uid = uid;
    }

    public ClientConfigDB() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLive() {
        return this.live;
    }

    public void setLive(String live) {
        this.live = live;
    }

    public String getCliendId() {
        return this.cliendId;
    }

    public void setCliendId(String cliendId) {
        this.cliendId = cliendId;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
