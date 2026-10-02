package com.alibaba.sdk.android.utils;

import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AlicloudTracker {
    private c a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f84a;
    private String b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Map<String, String> f85b = new HashMap();

    AlicloudTracker(c cVar, String str, String str2) {
        this.a = cVar;
        this.f84a = str;
        this.b = str2;
    }

    public void sendCustomHit(String str, long j, Map<String, String> map) {
        try {
            if (this.a == null) {
                Log.e("AlicloudTracker", "dataTracker is null, can not sendCustomHit");
                return;
            }
            if (map == null) {
                map = new HashMap<>();
            }
            map.putAll(this.f85b);
            map.put("sdkId", this.f84a);
            map.put("sdkVersion", this.b);
            this.a.sendCustomHit(this.f84a + "_" + str, j, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCustomHit(String str, Map<String, String> map) {
        sendCustomHit(str, 0L, map);
    }

    public void setGlobalProperty(String str, String str2) {
        if (!TextUtils.isEmpty(str) && str2 != null) {
            if (this.f85b.containsKey(str)) {
                this.f85b.remove(str);
            }
            this.f85b.put(str, str2);
            return;
        }
        Log.e("AlicloudTracker", "key is null or key is empty or value is null,please check it!");
    }

    public void removeGlobalProperty(String str) {
        if (!TextUtils.isEmpty(str) && this.f85b.containsKey(str)) {
            this.f85b.remove(str);
        } else {
            Log.e("AlicloudTracker", "key is null or key is empty,please check it!");
        }
    }
}
