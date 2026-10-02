package com.baidu.uaq.agent.android.harvest.health;

import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.g;
import org.json.JSONArray;

/* JADX INFO: compiled from: AgentHealth.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends com.baidu.uaq.agent.android.harvest.type.c {
    private final c aW = new c();
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();

    public static void a(Exception exception) {
        if (AGENT.getConfig().isCollectAgentHealth() && exception != null) {
            g.a(new b(exception));
        }
    }

    public void a(b exception) {
        this.aW.b(exception);
    }

    public void clear() {
        this.aW.clear();
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        JSONArray data = new JSONArray();
        if (!this.aW.isEmpty()) {
            data.put(this.aW.z());
        }
        return data;
    }
}
