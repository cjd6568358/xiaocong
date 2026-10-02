package com.baidu.uaq.agent.android.crashes;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: JsonCrashStore.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g implements d {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private final Context j;

    public g(Context context) {
        this.j = context;
    }

    @Override // com.baidu.uaq.agent.android.crashes.d
    public List<b> L() {
        Map<String, ?> crashStrings;
        SharedPreferences store = this.j.getSharedPreferences("APMCrashStore", 0);
        List<b> crashes = new ArrayList<>();
        synchronized (this) {
            crashStrings = store.getAll();
        }
        for (Object string : crashStrings.values()) {
            if (string instanceof String) {
                try {
                    crashes.add(b.d((String) string));
                } catch (Exception e) {
                    LOG.a("Exception encountered while deserializing crash", e);
                }
            }
        }
        return crashes;
    }

    @Override // com.baidu.uaq.agent.android.crashes.d
    public int count() {
        SharedPreferences store = this.j.getSharedPreferences("APMCrashStore", 0);
        return store.getAll().size();
    }

    @Override // com.baidu.uaq.agent.android.crashes.d
    public void c(b crash) {
        synchronized (this) {
            SharedPreferences store = this.j.getSharedPreferences("APMCrashStore", 0);
            SharedPreferences.Editor editor = store.edit();
            editor.remove(crash.getUuid().toString());
            editor.commit();
        }
    }
}
