package com.tencent.android.tpush.stat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static Map b = new ConcurrentHashMap();
    private static volatile Handler c = null;
    private static volatile int d = 0;
    private static volatile String e = Constants.MAIN_VERSION_TAG;
    private static volatile String f = Constants.MAIN_VERSION_TAG;
    private static com.tencent.android.tpush.stat.a.f g = com.tencent.android.tpush.stat.a.e.b();
    private static Thread.UncaughtExceptionHandler h = null;
    private static Context i = null;
    static volatile long a = 0;
    private static String j = null;
    private static volatile SharedPreferences k = null;

    public static Context a(Context context) {
        return context != null ? context : i;
    }

    public static void b(Context context) {
        if (context != null) {
            i = context.getApplicationContext();
        }
    }

    static boolean c(Context context) {
        long jA = com.tencent.android.tpush.stat.a.g.a(context, c.c, 0L);
        long jA2 = com.tencent.android.tpush.stat.a.e.a("2.0.6");
        boolean z = true;
        if (jA2 <= jA) {
            g.e("MTA is disable for current version:" + jA2 + ",wakeup version:" + jA);
            z = false;
        }
        c.a(z);
        return z;
    }

    static boolean a(String str) {
        return str == null || str.length() == 0;
    }

    static synchronized void d(Context context) {
        if (context != null) {
            if (c == null && c(context)) {
                Context applicationContext = context.getApplicationContext();
                i = applicationContext;
                HandlerThread handlerThread = new HandlerThread("XgStat");
                handlerThread.start();
                c = new Handler(handlerThread.getLooper());
                c.post(new i(applicationContext));
            }
        }
    }

    public static Handler e(Context context) {
        if (c == null) {
            synchronized (h.class) {
                if (c == null) {
                    try {
                        d(context);
                    } catch (Throwable th) {
                        g.a(th);
                        c.a(false);
                    }
                }
            }
        }
        return c;
    }

    static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (c.b.d != 0) {
                jSONObject2.put(NotifyType.VIBRATE, c.b.d);
            }
            jSONObject.put(Integer.toString(c.b.a), jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            if (c.a.d != 0) {
                jSONObject3.put(NotifyType.VIBRATE, c.a.d);
            }
            jSONObject.put(Integer.toString(c.a.a), jSONObject3);
        } catch (JSONException e2) {
            g.b((Throwable) e2);
        }
        return jSONObject;
    }

    static void a(Context context, long j2) {
        a(new com.tencent.android.tpush.stat.event.g(context, d, a(), j2));
    }

    static int b(Context context, long j2) {
        boolean z = true;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (a == 0) {
            a = com.tencent.android.tpush.stat.a.g.a(i, "_INTER_MTA_NEXT_DAY", 0L);
        }
        if (d != 0 && jCurrentTimeMillis < a) {
            z = false;
        }
        if (z) {
            d = com.tencent.android.tpush.stat.a.e.a();
            a = com.tencent.android.tpush.stat.a.e.c();
            com.tencent.android.tpush.stat.a.g.b(i, "_INTER_MTA_NEXT_DAY", a);
            a(context, j2);
        }
        return d;
    }

    public static void a(Context context, com.tencent.android.tpush.stat.event.d dVar) {
        if (c.c()) {
            if (i == null) {
                d(context);
            }
            if (e(a(context)) != null) {
                c.post(new n(dVar));
            }
        }
    }

    public static void a(Context context, String str, Properties properties, long j2, long j3) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null) {
                g.e("The Context of StatService.trackCustomEvent() can not be null!");
                return;
            }
            if (a(str)) {
                g.e("The event_id of StatService.trackCustomEvent() can not be null or empty.");
                return;
            }
            com.tencent.android.tpush.stat.event.b bVar = new com.tencent.android.tpush.stat.event.b(str, null, properties);
            if (e(contextA) != null) {
                c.post(new o(contextA, j2, bVar, j3));
            }
        }
    }

    public static void a(Context context, ArrayList arrayList) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null) {
                g.e("The Context of StatService.trackCustomEvent() can not be null!");
                return;
            }
            if (arrayList == null || arrayList.size() == 0) {
                g.e("The reportList of StatService.trackCustomEvent() can not be null or empty.");
            } else if (e(contextA) != null) {
                c.post(new p(arrayList, contextA));
            }
        }
    }

    public static void b(Context context, ArrayList arrayList) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null) {
                g.e("The Context of StatService.trackCustomEvent() can not be null!");
                return;
            }
            if (arrayList == null || arrayList.size() == 0) {
                g.e("The reportList of StatService.trackCustomEvent() can not be null or empty.");
            } else if (e(contextA) != null) {
                c.post(new q(arrayList, contextA));
            }
        }
    }

    public static void c(Context context, ArrayList arrayList) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null) {
                g.e("The Context of StatService.trackCustomEvent() can not be null!");
                return;
            }
            if (arrayList == null || arrayList.size() == 0) {
                g.e("The reportList of StatService.trackCustomEvent() can not be null or empty.");
            } else if (e(contextA) != null) {
                c.post(new r(arrayList, contextA));
            }
        }
    }

    public static void a(Context context, int i2) {
        if (c.c()) {
            if (c.b()) {
                g.b("commitEvents, maxNumber=" + i2);
            }
            Context contextA = a(context);
            if (contextA == null) {
                g.e("The Context of StatService.commitEvents() can not be null!");
                return;
            }
            if (i2 < -1 || i2 == 0) {
                g.e("The maxNumber of StatService.commitEvents() should be -1 or bigger than 0.");
            } else if (a.a(contextA).c() && e(contextA) != null) {
                c.post(new s());
            }
        }
    }

    static void a(List list) {
        g.h("sentEventList size:" + list.size());
        if (a.a(i).c()) {
            f.b(i).b(list, new t(list));
        } else {
            b(list);
        }
    }

    static void a(com.tencent.android.tpush.stat.event.d dVar) {
        if (a.a(i).c()) {
            f.b(i).a(dVar, new j(dVar));
        } else {
            b(Arrays.asList(dVar));
        }
    }

    static synchronized void b(List list) {
        if (list != null) {
            try {
                if (k != null) {
                    g.h("store event size:" + list.size());
                    SharedPreferences.Editor editorEdit = k.edit();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        editorEdit.putLong(it.next().toString(), System.currentTimeMillis());
                    }
                    editorEdit.commit();
                }
            } catch (Exception e2) {
                g.b((Throwable) e2);
            }
        }
    }

    static synchronized void c(List list) {
        if (list != null) {
            try {
                if (k != null) {
                    g.h("delete event size:" + list.size());
                    SharedPreferences.Editor editorEdit = k.edit();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        editorEdit.remove(it.next().toString());
                    }
                    editorEdit.commit();
                }
            } catch (Exception e2) {
                g.b((Throwable) e2);
            }
        }
    }

    static synchronized void d(List list) {
        if (list != null) {
            try {
                if (k != null) {
                    SharedPreferences.Editor editorEdit = k.edit();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        String string = it.next().toString();
                        int i2 = k.getInt(string, 1);
                        if (i2 > 0 && i2 <= c.f()) {
                            editorEdit.putInt(string, i2 + 1);
                        } else {
                            editorEdit.remove(string);
                        }
                    }
                    editorEdit.commit();
                }
            } catch (Exception e2) {
                g.b((Throwable) e2);
            }
        }
    }

    static void e(List list) {
        if (list != null && list.size() != 0) {
            f.b(i).b(list, new k(list));
        }
    }

    public static void a(Context context, String str, long j2) {
        String str2 = new String(str);
        if (e(context) != null) {
            c.post(new l(str2, context, j2));
        }
    }

    public static void b(Context context, String str, long j2) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null || str == null || str.length() == 0) {
                g.e("The Context or pageName of StatService.trackBeginPage() can not be null or empty!");
            } else {
                a(contextA, str, j2);
            }
        }
    }

    private static void b(Context context, String str, long j2, long j3, long j4) {
        String str2 = new String(str);
        if (e(context) != null) {
            c.post(new m(str2, context, j2, j3, j4));
        }
    }

    public static void c(Context context, String str, long j2) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null || str == null || str.length() == 0) {
                g.e("The Context or pageName of StatService.trackEndPage() can not be null or empty!");
            } else {
                b(contextA, str, j2, 0L, 0L);
            }
        }
    }

    public static void a(Context context, String str, long j2, long j3, long j4) {
        if (c.c()) {
            Context contextA = a(context);
            if (contextA == null || str == null || str.length() == 0) {
                g.e("The Context or pageName of StatService.trackEndPage() can not be null or empty!");
            } else {
                b(contextA, str, j2, j3, j4);
            }
        }
    }

    static void b() {
        Map<String, ?> all;
        if (k != null && (all = k.getAll()) != null && all.size() > 0) {
            ArrayList arrayList = new ArrayList(10);
            Iterator<Map.Entry<String, ?>> it = all.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getKey());
                if (arrayList.size() == 10) {
                    e(arrayList);
                    arrayList.clear();
                }
            }
            e(arrayList);
            arrayList.clear();
        }
    }
}
