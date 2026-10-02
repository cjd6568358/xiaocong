package com.baidu.uaq.agent.android.transmission.lss;

import com.baidu.uaq.agent.android.harvest.bean.f;
import com.baidu.uaq.agent.android.logging.b;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: LssDataParser.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a implements com.baidu.uaq.agent.android.transmission.a {
    private static final com.baidu.uaq.agent.android.logging.a LOG = b.bg();

    @Override // com.baidu.uaq.agent.android.transmission.a
    public JSONArray b(List<f> list) {
        JSONArray array = new JSONArray();
        Map<String, JSONObject> baseInfo = new HashMap<>();
        Map<String, JSONArray> eventList = new HashMap<>();
        try {
            Iterator<f> it = list.iterator();
            while (it.hasNext()) {
                f transmission = it.next();
                JSONObject jo = transmission.aw();
                if (jo != null) {
                    String vvid = jo.getJSONObject("baseInfo").getString("vvid");
                    if (baseInfo.containsKey(vvid)) {
                        JSONArray event = eventList.get(vvid);
                        jo.remove("baseInfo");
                        event.put(jo);
                    } else {
                        baseInfo.put(vvid, new JSONObject().put("baseInfo", jo.remove("baseInfo")));
                        eventList.put(vvid, new JSONArray().put(jo));
                    }
                }
                it.remove();
            }
            for (Object object : baseInfo.entrySet()) {
                Map.Entry<String, JSONObject> entry = (Map.Entry) object;
                JSONObject ele = entry.getValue();
                ele.put("events", eventList.get(entry.getKey()));
                array.put(ele);
            }
        } catch (JSONException e) {
            LOG.a("Caught error while LssDataParser parse: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        return array;
    }
}
