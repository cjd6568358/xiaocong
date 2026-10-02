package com.baidu.uaq.agent.android.harvest.bean;

import com.baidu.uaq.agent.android.UAQ;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;

/* JADX INFO: compiled from: Transmissions.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends com.baidu.uaq.agent.android.harvest.type.c {
    private final List<f> aU = new ArrayList();
    private com.baidu.uaq.agent.android.transmission.a aV;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();

    public g(com.baidu.uaq.agent.android.transmission.a dataParser) {
        this.aV = dataParser;
    }

    public static void a(f transmission) {
        if (AGENT.getConfig().isEnableTransmission()) {
            com.baidu.uaq.agent.android.g.a(transmission);
        }
    }

    public synchronized void b(f transmission) {
        this.aU.add(transmission);
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        List<f> allTransmissions = ax();
        JSONArray array = new JSONArray();
        if (allTransmissions.size() != 0) {
            JSONArray array2 = this.aV.b(allTransmissions);
            return array2;
        }
        return array;
    }

    private List<f> ax() {
        List<f> arrayList;
        synchronized (this) {
            if (this.aU.size() == 0) {
                arrayList = Collections.emptyList();
            } else {
                arrayList = new ArrayList<>(this.aU);
                this.aU.clear();
            }
        }
        return arrayList;
    }
}
