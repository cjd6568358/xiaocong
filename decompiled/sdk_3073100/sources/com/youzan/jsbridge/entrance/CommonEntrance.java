package com.youzan.jsbridge.entrance;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CommonEntrance extends JsBridgeEntrance {
    @Override // com.youzan.jsbridge.entrance.JsBridgeEntrance
    protected String getEntrance() {
        return "YZAndroidJS";
    }

    @Override // com.youzan.jsbridge.entrance.JsBridgeEntrance
    protected Set<String> getMethods() {
        Set<String> methods = new HashSet<>();
        methods.add("doCall");
        return methods;
    }
}
