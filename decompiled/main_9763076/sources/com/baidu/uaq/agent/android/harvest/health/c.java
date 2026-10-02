package com.baidu.uaq.agent.android.harvest.health;

import com.baidu.uaq.agent.android.harvest.type.d;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: AgentHealthExceptions.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends d {
    private final Map<String, b> ba = new ConcurrentHashMap();

    public void b(b exception) {
        String aggregationKey = exception.ay() + exception.getStackTrace()[0].toString();
        synchronized (this.ba) {
            b healthException = this.ba.get(aggregationKey);
            if (healthException == null) {
                this.ba.put(aggregationKey, exception);
            } else {
                healthException.increment();
            }
        }
    }

    public void clear() {
        synchronized (this.ba) {
            this.ba.clear();
        }
    }

    public boolean isEmpty() {
        return this.ba.isEmpty();
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject exceptions = new JSONObject();
        JSONArray data = new JSONArray();
        try {
            for (b exception : this.ba.values()) {
                data.put(exception.U());
            }
            exceptions.put("Type", "AgentErrors");
            exceptions.put("Data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exceptions;
    }
}
