package com.meizu.cloud.pushsdk.common.b;

import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static HashMap<String, e.c> a = new HashMap<>();

    public static <T> e.c<T> a(String str) {
        if (a.containsKey(str)) {
            return a.get(str);
        }
        e.c<T> cVarA = e.a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES).b("get").a(new Object[]{str}).a();
        if (cVarA.a) {
            a.put(str, cVarA);
            return cVarA;
        }
        return cVarA;
    }
}
