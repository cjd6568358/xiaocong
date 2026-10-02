package com.youzan.androidsdk;

import android.content.Context;
import com.youzan.androidsdk.tool.AnalyticsUtil;
import com.youzan.systemweb.YZWebSDK;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YouzanSDK {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static YouzanSDKAdapter f128 = null;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static boolean f129 = false;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static void m111() {
        if (f128 == null) {
            throw new IllegalStateException("You Should Init with A Valid SDK Adapter,YouzanBasicSDKAdapter for basic sdk, and YouzanHybridSDKAdapter for native sdk");
        }
    }

    public static boolean isReady() {
        m111();
        return f128.isReady();
    }

    public static void init(Context context, String clientId, YouzanSDKAdapter adapter) {
        String clientId2 = SDKUtil.verifyClientId(clientId);
        f128 = adapter;
        m111();
        if (f129) {
            f128.isDebug(f129);
        }
        f128.init(context, clientId2);
        AnalyticsUtil.initAnalytics(context, clientId2);
        YZWebSDK.init(context, "appsdk", new 1(context));
        YZWebSDK.preloadModifyFromRemote(context);
    }

    public static void isDebug(boolean debug) {
        f129 = debug;
        if (f128 != null) {
            f128.isDebug(debug);
        }
    }

    public static void userLogout(Context context) {
        m111();
        f128.userLogout(context);
    }

    public static void sync(Context context, YouzanToken token) {
        m111();
        f128.sync(context, token);
        if (token != null) {
            YZWebSDK.syncToken(token.getAccessToken());
        }
    }
}
