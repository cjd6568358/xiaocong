package com.alibaba.mtl.appmonitor.e;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.s;
import com.alibaba.mtl.log.model.LogField;
import java.util.Map;

/* JADX INFO: compiled from: UTAggregationPlugin.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static final String TAG = null;
    private static a a;

    private a() {
    }

    public static synchronized a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    public void a(Map<String, String> map) {
        if (map != null) {
            i.a(TAG, "[sendToUT]:", " args:", map);
            if (com.alibaba.mtl.log.a.r) {
                if (map != null) {
                    com.alibaba.mtl.log.a.a(map.get(LogField.PAGE.toString()), map.get(LogField.EVENTID.toString()), map.get(LogField.ARG1.toString()), map.get(LogField.ARG2.toString()), map.get(LogField.ARG3.toString()), map);
                }
            } else {
                map.put("_fuamf", "yes");
                s.send(map);
            }
        }
    }
}
