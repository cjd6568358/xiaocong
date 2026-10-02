package com.baidu.uaq.agent.android.harvest.bean;

import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: compiled from: DataToken.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends com.baidu.uaq.agent.android.harvest.type.c {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private long aD;
    private long aE;

    public b() {
    }

    public b(long accountId, long agentId) {
        this.aD = accountId;
        this.aE = agentId;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        JSONArray array = new JSONArray();
        try {
            array.put(0, this.aD);
            array.put(1, this.aE);
        } catch (JSONException e) {
            LOG.a("Caught error while DataToken asJSONArray: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        return array;
    }

    public long af() {
        return this.aD;
    }

    public void g(long accountId) {
        this.aD = accountId;
    }

    public long ag() {
        return this.aE;
    }

    public void h(long agentId) {
        this.aE = agentId;
    }

    public String toString() {
        return "DataToken{accountId=" + this.aD + ", agentId=" + this.aE + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        b dataToken = (b) o;
        return this.aD == dataToken.aD && this.aE == dataToken.aE;
    }

    public int hashCode() {
        int result = (int) (this.aD ^ (this.aD >>> 32));
        return (result * 31) + ((int) (this.aE ^ (this.aE >>> 32)));
    }
}
