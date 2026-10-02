package com.youzan.spiderman.html;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HtmlStatistic {
    private String a;
    private boolean b = false;
    private boolean c = false;

    public HtmlStatistic(String url) {
        this.a = url;
    }

    public void setPrefetch(boolean prefetch) {
        this.b = prefetch;
    }

    public void setNeedRecord(boolean needRecord) {
        this.c = needRecord;
    }

    public boolean isNeedRecord() {
        return this.c;
    }

    public Map<String, String> getStatisticData() {
        Map<String, String> map = new HashMap<>();
        map.put("url", this.a);
        map.put("prefetch", String.valueOf(this.b ? 1 : 0));
        return map;
    }
}
