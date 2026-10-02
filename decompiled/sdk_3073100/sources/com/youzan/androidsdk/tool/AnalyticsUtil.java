package com.youzan.androidsdk.tool;

import android.content.Context;
import com.youzan.androidsdk.YouzanLog;
import com.youzan.androidsdk.basic.BuildConfig;
import com.youzan.mobile.growinganalytics.AnalyticsAPI;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AnalyticsUtil {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static boolean f572 = false;

    public static void initAnalytics(Context context, String clientId) {
        if (!f572) {
            try {
                AnalyticsAPI.get(context).setAppId("yzy_appsdk");
                AnalyticsAPI.setDebug(false);
                AnalyticsAPI.setSendPageAction(false);
                AnalyticsAPI.setAutoEventEnable(false);
                AnalyticsAPI.get(context).registerSuperProperties("appsdk_version", BuildConfig.VERSION_NAME);
                AnalyticsAPI.get(context).registerSuperProperties("package_name", context.getPackageName());
                AnalyticsAPI analyticsAPI = AnalyticsAPI.get(context);
                if (clientId == null) {
                    clientId = "";
                }
                analyticsAPI.registerSuperProperties("client_id", clientId);
                AnalyticsAPI.get(context).track("appsdk_init");
                f572 = true;
            } catch (Exception e) {
                YouzanLog.e("initAnalytics exception" + e);
            }
        }
    }

    public static void doStatistic(Context context, String name, String desc, Map<String, String> staticData) {
        try {
            AnalyticsAPI analyticsAPI = AnalyticsAPI.get(context);
            if (analyticsAPI != null) {
                analyticsAPI.buildEvent(name).desc(desc).params(staticData).track();
            }
        } catch (NullPointerException e) {
            YouzanLog.e("doStatistic exception" + e);
        }
    }

    public static void statisticWebviewInit(Context context) {
        try {
            AnalyticsAPI analyticsAPI = AnalyticsAPI.get(context);
            if (analyticsAPI != null) {
                analyticsAPI.track("appsdk_webview_init");
            }
        } catch (Exception e) {
            YouzanLog.e("statistic webview init exception" + e);
        }
    }

    public static void statisticWebviewLoadPage(Context context, String url) {
        try {
            AnalyticsAPI analyticsAPI = AnalyticsAPI.get(context);
            if (analyticsAPI != null) {
                Map<String, String> params = new HashMap<>();
                params.put("url", url);
                analyticsAPI.buildEvent("appsdk_webview_load_page").params(params).track();
            }
        } catch (Exception e) {
            YouzanLog.e("statistic webview load page exception" + e);
        }
    }
}
