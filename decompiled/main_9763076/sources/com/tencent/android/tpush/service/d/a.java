package com.tencent.android.tpush.service.d;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.stat.StatReportStrategy;
import com.tencent.android.tpush.stat.c;
import com.tencent.android.tpush.stat.event.d;
import com.tencent.android.tpush.stat.event.e;
import com.tencent.android.tpush.stat.h;
import java.util.ArrayList;
import java.util.Properties;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static Context a = null;

    public static void a(Context context) {
        c.b(true);
        c.a(StatReportStrategy.INSTANT);
        h.b(context);
        h.e(context);
        a = context.getApplicationContext();
    }

    public static void a() {
        h.a(a, -1);
    }

    public static void a(ArrayList arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            try {
                h.a(a, arrayList);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("XgStat", "reportSrvAck", th);
            }
        }
    }

    public static void a(Intent intent) {
        if (intent != null) {
            try {
                long longExtra = intent.getLongExtra("type", 0L);
                long longExtra2 = intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L);
                long longExtra3 = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
                long longExtra4 = intent.getLongExtra(MessageKey.MSG_ID, 0L);
                long longExtra5 = intent.getLongExtra("accId", 0L);
                Properties properties = new Properties();
                properties.setProperty("type", Constants.MAIN_VERSION_TAG + longExtra);
                properties.setProperty(MessageKey.MSG_BUSI_MSG_ID, Constants.MAIN_VERSION_TAG + longExtra2);
                properties.setProperty(MessageKey.MSG_ID, Constants.MAIN_VERSION_TAG + longExtra4);
                h.a(a, "SdkAck", properties, longExtra5, longExtra3);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("XgStat", "reportSDKAck", th);
            }
        }
    }

    public static void a(Context context, String str, JSONObject jSONObject) {
        try {
            a(context, new com.tencent.android.tpush.stat.event.a(context, str, jSONObject, "Axg" + XGPushConfig.getAccessId(context), true));
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("XgStat", "reportXGStat ", th);
        }
    }

    public static void b(Context context, String str, JSONObject jSONObject) {
        try {
            a(context, new e(context, str, jSONObject, "Axg" + XGPushConfig.getAccessId(context), true));
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("XgStat", "reportXGLBS ", th);
        }
    }

    public static void a(Context context, d dVar) {
        h.a(context, dVar);
    }

    public static void b(ArrayList arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            com.tencent.android.tpush.a.a.i("XgStat", "ServiceStat reportAck 15 with null list ");
            return;
        }
        try {
            h.b(a, arrayList);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("XgStat", "reportAck", th);
        }
    }

    public static void c(ArrayList arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            try {
                h.c(a, arrayList);
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("XgStat", "reportNotifactionClickedOrClear", th);
            }
        }
    }

    private static void a(Context context, Intent intent, String str) {
        if (intent != null && !t.c(str)) {
            try {
                long longExtra = intent.getLongExtra(MessageKey.MSG_ID, 0L);
                if (longExtra >= 0) {
                    long longExtra2 = intent.getLongExtra("type", 1L);
                    long longExtra3 = intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L);
                    long longExtra4 = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
                    long longExtra5 = intent.getLongExtra("accId", 0L);
                    Properties properties = new Properties();
                    properties.setProperty("type", Constants.MAIN_VERSION_TAG + longExtra2);
                    properties.setProperty(MessageKey.MSG_BUSI_MSG_ID, Constants.MAIN_VERSION_TAG + longExtra3);
                    properties.setProperty(MessageKey.MSG_ID, Constants.MAIN_VERSION_TAG + longExtra);
                    if (str.equals("Action")) {
                        properties.put("action", Constants.MAIN_VERSION_TAG + intent.getIntExtra("action", 0));
                    }
                    h.a(context.getApplicationContext(), str, properties, longExtra5, longExtra4);
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("XgStat", "reportSDKAck", th);
            }
        }
    }

    public static void a(Context context, Intent intent) {
        a(context, intent, "OtherPull");
    }

    public static void b(Context context, Intent intent) {
        a(context, intent, "SdkAck");
    }

    public static void c(Context context, Intent intent) {
        a(context, intent, "Verify");
    }

    public static void d(Context context, Intent intent) {
        a(context, intent, "SHOW");
    }

    public static void e(Context context, Intent intent) {
        a(context, intent, "Action");
    }
}
