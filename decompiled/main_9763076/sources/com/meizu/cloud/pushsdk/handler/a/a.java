package com.meizu.cloud.pushsdk.handler.a;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import bsh.ParserConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a<T> implements com.meizu.cloud.pushsdk.handler.c {
    private com.meizu.cloud.pushsdk.handler.a a;
    private Context b;
    private Map<Integer, String> c;

    protected abstract void a(T t, com.meizu.cloud.pushsdk.notification.e eVar);

    protected abstract T c(Intent intent);

    protected a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.b = context.getApplicationContext();
        this.a = aVar;
        this.c = new HashMap();
        this.c.put(2, "MESSAGE_TYPE_PUSH_SERVICE_V2");
        this.c.put(4, "MESSAGE_TYPE_PUSH_SERVICE_V3");
        this.c.put(16, "MESSAGE_TYPE_REGISTER");
        this.c.put(32, "MESSAGE_TYPE_UNREGISTER");
        this.c.put(8, "MESSAGE_TYPE_THROUGH");
        this.c.put(64, "MESSAGE_TYPE_NOTIFICATION_CLICK");
        this.c.put(Integer.valueOf(ParserConstants.LSHIFTASSIGN), "MESSAGE_TYPE_NOTIFICATION_DELETE");
        this.c.put(256, "MESSAGE_TYPE_PUSH_SWITCH_STATUS");
        this.c.put(Integer.valueOf(WXMediaMessage.TITLE_LENGTH_LIMIT), "MESSAGE_TYPE_PUSH_REGISTER_STATUS");
        this.c.put(2048, "MESSAGE_TYPE_PUSH_SUBTAGS_STATUS");
        this.c.put(Integer.valueOf(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT), "MESSAGE_TYPE_PUSH_UNREGISTER_STATUS");
        this.c.put(4096, "MESSAGE_TYPE_PUSH_SUBALIAS_STATUS");
        this.c.put(8192, "MESSAGE_TYPE_SCHEDULE_NOTIFICATION");
        this.c.put(16384, "MESSAGE_TYPE_RECEIVE_NOTIFY_MESSAGE");
    }

    protected com.meizu.cloud.pushsdk.notification.e a(T t) {
        return null;
    }

    protected void b(T t) {
    }

    protected void c(T t) {
    }

    protected int d(T t) {
        return 0;
    }

    protected void e(T t) {
    }

    protected String d(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY);
        if (TextUtils.isEmpty(stringExtra)) {
            String strC = com.meizu.cloud.pushsdk.util.c.c(c());
            if (TextUtils.isEmpty(strC)) {
                String strB = MzSystemUtils.b(c());
                com.meizu.cloud.pushsdk.util.c.b(c(), strB);
                DebugLogger.e("AbstractMessageHandler", "force get deviceId " + strB);
                return strB;
            }
            return strC;
        }
        return stringExtra;
    }

    protected String e(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
    }

    protected String f(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
    }

    protected String g(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SERVICE_DEFAULT_PACKAGE_NAME);
        if (TextUtils.isEmpty(stringExtra)) {
            return c().getPackageName();
        }
        return stringExtra;
    }

    protected String h(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_TIMES_TAMP);
        DebugLogger.e("AbstractMessageHandler", "receive push timestamp from pushservice " + stringExtra);
        if (TextUtils.isEmpty(stringExtra)) {
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
        return stringExtra;
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean b(Intent intent) {
        boolean z = false;
        boolean z2 = true;
        if (a(intent)) {
            DebugLogger.e("AbstractMessageHandler", "current message Type " + a(a()));
            T tC = c(intent);
            DebugLogger.e("AbstractMessageHandler", "current Handler message " + tC);
            b(tC);
            switch (d(tC)) {
                case 0:
                    DebugLogger.e("AbstractMessageHandler", "schedule send message off, send message directly");
                    z = true;
                    break;
                case 1:
                    DebugLogger.e("AbstractMessageHandler", "expire notification, dont show message");
                    z2 = false;
                    break;
                case 2:
                    DebugLogger.e("AbstractMessageHandler", "notification on time ,show message");
                    z = true;
                    break;
                case 3:
                    DebugLogger.e("AbstractMessageHandler", "schedule notification");
                    e(tC);
                    z = true;
                    z2 = false;
                    break;
                default:
                    z2 = false;
                    break;
            }
            if (z && z2) {
                a(tC, a(tC));
                c(tC);
                DebugLogger.e("AbstractMessageHandler", "send message end ");
            }
        }
        return z;
    }

    public com.meizu.cloud.pushsdk.handler.a b() {
        return this.a;
    }

    public Context c() {
        return this.b;
    }

    public String i(Intent intent) {
        return intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_METHOD);
    }

    public boolean a(String str) {
        try {
            return c().getPackageName().equals(new JSONObject(str).getString("appId"));
        } catch (Exception e) {
            DebugLogger.e("AbstractMessageHandler", "parse notification error");
            return false;
        }
    }

    public String a(String str, Map<String, String> map) {
        if (TextUtils.isEmpty(str)) {
            if (map == null) {
                str = null;
            } else {
                String str2 = map.get("sk");
                str = TextUtils.isEmpty(str2) ? com.meizu.cloud.pushsdk.b.f.e.a((Map) map).toString() : str2;
            }
        }
        DebugLogger.e("AbstractMessageHandler", "self json " + str);
        return str;
    }

    private String a(int i) {
        return this.c.get(Integer.valueOf(i));
    }

    protected boolean a(int i, String str) {
        boolean zF = true;
        if (i == 0) {
            zF = com.meizu.cloud.pushsdk.util.c.e(c(), str);
        } else if (i == 1) {
            zF = com.meizu.cloud.pushsdk.util.c.f(c(), str);
        }
        DebugLogger.e("AbstractMessageHandler", str + (i == 0 ? " canNotificationMessage " : " canThroughMessage ") + zF);
        return zF;
    }
}
