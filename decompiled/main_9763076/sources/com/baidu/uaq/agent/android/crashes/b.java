package com.baidu.uaq.agent.android.crashes;

import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.util.k;
import com.tencent.android.tpush.common.Constants;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Crash.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends com.baidu.uaq.agent.android.harvest.type.d {
    private static final UAQ AGENT = UAQ.getInstance();
    private int L;
    private final String M;
    private final String N;
    private e O;
    private a P;
    private f Q;
    private List<h> R;
    private final String cuid;
    private final long timestamp;
    private final UUID uuid;

    public b(UUID uuid, String buildId, long timestamp) {
        this.L = 1;
        this.uuid = uuid;
        this.M = buildId;
        this.timestamp = timestamp;
        this.N = AGENT.getConfig().getAPIKey();
        this.cuid = AGENT.getConfig().getCuid();
    }

    public b(Throwable throwable) {
        this.L = 1;
        com.baidu.uaq.agent.android.b agentImpl = com.baidu.uaq.agent.android.a.a();
        Throwable cause = a(throwable);
        this.uuid = new UUID(k.bA().nextLong(), k.bA().nextLong());
        this.M = A();
        this.timestamp = System.currentTimeMillis() / 1000;
        this.N = AGENT.getConfig().getAPIKey();
        this.cuid = AGENT.getConfig().getCuid();
        this.O = new e(agentImpl.e(), agentImpl.i());
        this.P = new a(agentImpl.f());
        this.Q = new f(cause);
        this.R = h.b(cause);
    }

    public static String A() {
        return Constants.MAIN_VERSION_TAG;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject data = new JSONObject();
        try {
            data.put("protocolVersion", this.L);
            data.put("platform", "Android");
            data.put("uuid", this.uuid.toString());
            data.put("buildId", this.M);
            data.put("timestamp", Long.valueOf(this.timestamp));
            data.put("appToken", this.N);
            data.put("cuid", com.baidu.uaq.agent.android.util.d.P(this.cuid));
            data.put("deviceInfo", this.O.z());
            data.put("appInfo", this.P.z());
            data.put("exception", this.Q.z());
            data.put("threads", C());
            data.put("activityHistory", new JSONArray());
            com.baidu.uaq.agent.android.harvest.bean.b dataToken = com.baidu.uaq.agent.android.harvest.multiharvest.d.aQ().r();
            if (dataToken != null) {
                data.put("dataToken", dataToken.U());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static b d(String json) {
        b crash = null;
        try {
            JSONObject crashObject = new JSONObject(json);
            String uuid = crashObject.getString("uuid");
            String buildIdentifier = crashObject.getString("buildId");
            long timestamp = crashObject.getLong("timestamp");
            b crash2 = new b(UUID.fromString(uuid), buildIdentifier, timestamp);
            try {
                crash2.O = e.b(crashObject.getJSONObject("deviceInfo"));
                crash2.P = a.a(crashObject.getJSONObject("appInfo"));
                crash2.Q = f.c(crashObject.getJSONObject("exception"));
                crash2.R = h.c(crashObject.getJSONArray("threads"));
                return crash2;
            } catch (JSONException e) {
                e = e;
                crash = crash2;
                e.printStackTrace();
                return crash;
            }
        } catch (JSONException e2) {
            e = e2;
        }
    }

    private static Throwable a(Throwable throwable) {
        Throwable cause = throwable.getCause();
        return cause == null ? throwable : a(cause);
    }

    private JSONArray C() {
        JSONArray data = new JSONArray();
        for (h thread : this.R) {
            data.put(thread.z());
        }
        return data;
    }
}
