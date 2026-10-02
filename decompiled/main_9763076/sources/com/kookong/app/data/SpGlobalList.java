package com.kookong.app.data;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SpGlobalList implements SerializableEx {
    public List<SpGlobal> spGlobalList = new ArrayList();

    public static class SpGlobal implements SerializableEx {
        public int spGlobalId;
        public String spGlobalName;
    }
}
