package com.youzan.spiderman.cache;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface SpiderCacheCallback {
    void onCustomRequestHeader(String str, Map<String, String> map);

    void onStatistic(String str, String str2, Map<String, String> map);

    String onTokenInactive(String str);

    String onTokenNeeded();
}
