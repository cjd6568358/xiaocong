package com.youzan.androidsdk.tool;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class UserAgent {
    private static final String USER_AGENT_PREFIX = "kdtUnion_";
    public static String clintId = null;
    public static String httpUA = null;

    public static void setupUA(Context context, String clientId, boolean autoPrefix) {
        if (clientId != null) {
            if (autoPrefix && !clientId.toLowerCase().startsWith(USER_AGENT_PREFIX.toLowerCase())) {
                clientId = USER_AGENT_PREFIX + clientId;
            }
            clintId = clientId;
            httpUA = buildYouzanHttpUA(context, clintId);
        }
    }

    public static String buildYouzanHttpUA(Context context, String youzanUA) {
        String systemUA = buildSystemUA(context);
        return systemUA + " " + youzanUA;
    }

    private static String buildSystemUA(Context context) {
        String VMCode = getVMVersionCode();
        String buildCode = Build.DISPLAY;
        String deviceModel = Build.MODEL;
        String systemVersion = Build.VERSION.RELEASE;
        String appInfo = Environment.m81(context);
        return String.format("Dalvik/%s (%s; %s %s; %s Build/%s; %s)", VMCode, "Linux", "Android", systemVersion, deviceModel, buildCode, appInfo);
    }

    private static String getVMVersionCode() {
        return System.getProperty("java.vm.version");
    }
}
