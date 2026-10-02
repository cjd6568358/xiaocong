package com.youzan.jsbridge.entrance;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class CompatEntrance extends JsBridgeEntrance {
    protected String getEntrance() {
        return "androidJS";
    }

    protected Set<String> getMethods() {
        Set<String> methods = new HashSet<>();
        methods.add("getData");
        methods.add("putData");
        methods.add("doAction");
        methods.add("gotoNative");
        methods.add("gotoWebview");
        methods.add("configNative");
        methods.add("setRightMenu");
        methods.add("turnOffPullDownRefresh");
        methods.add("webReady");
        methods.add("returnShareData");
        methods.add("getUserInfo");
        return methods;
    }
}
