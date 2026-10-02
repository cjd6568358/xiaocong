package com.alibaba.mtl.appmonitor;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SdkMeta {
    private static final Map<String, String> d = new HashMap();

    public static Map<String, String> getSDKMetaData() {
        if (com.alibaba.mtl.log.a.getContext() != null) {
        }
        if (!d.containsKey("sdk-version")) {
            d.put("sdk-version", "2.6.0_for_bc");
        }
        return d;
    }

    static {
        d.put("sdk-version", "2.6.0_for_bc");
    }
}
