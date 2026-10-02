package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ac {
    public static long a = 0;
    private static final LinkedList<Pair<Integer, com.xiaomi.xmpush.thrift.ab>> b = new LinkedList<>();

    public static class a {
        Notification a;
        long b = 0;
    }

    public static class b {
        public String a;
        public long b = 0;
    }

    private static int a(Context context, String str, String str2) {
        if (str.equals(context.getPackageName())) {
            return context.getResources().getIdentifier(str2, "drawable", str);
        }
        return 0;
    }

    private static Notification a(Notification notification) {
        Object objA = com.xiaomi.channel.commonutils.reflect.a.a(notification, "extraNotification");
        if (objA != null) {
            com.xiaomi.channel.commonutils.reflect.a.a(objA, "setCustomizedIcon", true);
        }
        return notification;
    }

    private static Notification a(Notification notification, String str) {
        try {
            Field declaredField = Notification.class.getDeclaredField("extraNotification");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(notification);
            Method declaredMethod = obj.getClass().getDeclaredMethod("setTargetPkg", CharSequence.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, str);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
        }
        return notification;
    }

    private static PendingIntent a(Context context, com.xiaomi.xmpush.thrift.ab abVar, com.xiaomi.xmpush.thrift.r rVar, byte[] bArr) {
        if (rVar != null && !TextUtils.isEmpty(rVar.g)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(rVar.g));
            intent.addFlags(268435456);
            return PendingIntent.getActivity(context, 0, intent, 134217728);
        }
        if (b(abVar)) {
            Intent intent2 = new Intent();
            intent2.setComponent(new ComponentName("com.xiaomi.xmsf", "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent2.putExtra("mipush_payload", bArr);
            intent2.putExtra("mipush_notified", true);
            intent2.addCategory(String.valueOf(rVar.q()));
            return PendingIntent.getService(context, 0, intent2, 134217728);
        }
        Intent intent3 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent3.setComponent(new ComponentName(abVar.f, "com.xiaomi.mipush.sdk.PushMessageHandler"));
        intent3.putExtra("mipush_payload", bArr);
        intent3.putExtra("mipush_notified", true);
        intent3.addCategory(String.valueOf(rVar.q()));
        return PendingIntent.getService(context, 0, intent3, 134217728);
    }

    private static Bitmap a(Context context, int i) {
        return a(context.getResources().getDrawable(i));
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight > 0 ? intrinsicHeight : 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    /* JADX WARN: Code duplicated, block: B:78:0x01ac  */
    @SuppressLint({"NewApi"})
    private static a a(Context context, com.xiaomi.xmpush.thrift.ab abVar, byte[] bArr, RemoteViews remoteViews, PendingIntent pendingIntent) throws Throwable {
        boolean z;
        a aVar = new a();
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        String strA = a(abVar);
        Map<String, String> mapS = rVarM.s();
        Notification.Builder builder = new Notification.Builder(context);
        String[] strArrA = a(context, rVarM);
        builder.setContentTitle(strArrA[0]);
        builder.setContentText(strArrA[1]);
        if (remoteViews != null) {
            builder.setContent(remoteViews);
        } else if (Build.VERSION.SDK_INT >= 16) {
            builder.setStyle(new Notification.BigTextStyle().bigText(strArrA[1]));
        }
        builder.setWhen(System.currentTimeMillis());
        String str = mapS == null ? null : mapS.get("notification_show_when");
        if (!TextUtils.isEmpty(str)) {
            builder.setShowWhen(Boolean.parseBoolean(str));
        } else if (Build.VERSION.SDK_INT >= 24) {
            builder.setShowWhen(true);
        }
        builder.setContentIntent(pendingIntent);
        int iA = a(context, strA, "mipush_notification");
        int iA2 = a(context, strA, "mipush_small_notification");
        if (iA <= 0 || iA2 <= 0) {
            builder.setSmallIcon(f(context, strA));
        } else {
            builder.setLargeIcon(a(context, iA));
            builder.setSmallIcon(iA2);
        }
        String str2 = mapS == null ? null : mapS.get("__dynamic_icon_uri");
        boolean z2 = (mapS != null && Boolean.parseBoolean(mapS.get("__adiom"))) || com.xiaomi.channel.commonutils.android.g.b();
        if (str2 == null || !z2) {
            z = false;
        } else {
            Bitmap bitmapB = null;
            if (str2.startsWith("http")) {
                ag.b bVarA = ag.a(context, str2);
                if (bVarA != null) {
                    bitmapB = bVarA.a;
                    aVar.b = bVarA.b;
                }
            } else {
                bitmapB = ag.b(context, str2);
            }
            if (bitmapB != null) {
                builder.setLargeIcon(bitmapB);
                z = true;
            } else {
                z = false;
            }
        }
        if (mapS != null && Build.VERSION.SDK_INT >= 24) {
            String str3 = mapS.get("notification_group");
            boolean z3 = Boolean.parseBoolean(mapS.get("notification_is_summary"));
            com.xiaomi.channel.commonutils.reflect.a.a(builder, "setGroup", str3);
            com.xiaomi.channel.commonutils.reflect.a.a(builder, "setGroupSummary", Boolean.valueOf(z3));
        }
        builder.setAutoCancel(true);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (mapS != null && mapS.containsKey("ticker")) {
            builder.setTicker(mapS.get("ticker"));
        }
        if (jCurrentTimeMillis - a > 10000) {
            a = jCurrentTimeMillis;
            int iC = e(context, strA) ? c(context, strA) : rVarM.f;
            builder.setDefaults(iC);
            if (mapS != null && (iC & 1) != 0) {
                String str4 = mapS.get("sound_uri");
                if (!TextUtils.isEmpty(str4) && str4.startsWith("android.resource://" + strA)) {
                    builder.setDefaults(iC ^ 1);
                    builder.setSound(Uri.parse(str4));
                }
            }
        }
        Notification notification = builder.getNotification();
        if (z && com.xiaomi.channel.commonutils.android.g.a()) {
            a(notification);
        }
        aVar.a = notification;
        return aVar;
    }

    public static b a(Context context, com.xiaomi.xmpush.thrift.ab abVar, byte[] bArr) {
        Notification notification;
        b bVar = new b();
        if (com.xiaomi.channel.commonutils.android.b.d(context, a(abVar)) == com.xiaomi.channel.commonutils.android.b.a.NOT_ALLOWED) {
            com.xiaomi.channel.commonutils.logger.b.a("Do not notify because user block " + a(abVar) + "‘s notification");
            return bVar;
        }
        if (ay.a(context, abVar)) {
            com.xiaomi.channel.commonutils.logger.b.a("Do not notify because user block " + ay.a(abVar) + "‘s notification");
            return bVar;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        RemoteViews remoteViewsB = b(context, abVar, bArr);
        PendingIntent pendingIntentA = a(context, abVar, rVarM, bArr);
        if (pendingIntentA == null) {
            com.xiaomi.channel.commonutils.logger.b.a("The click PendingIntent is null. ");
            return bVar;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            a aVarA = a(context, abVar, bArr, remoteViewsB, pendingIntentA);
            bVar.b = aVarA.b;
            bVar.a = a(abVar);
            notification = aVarA.a;
        } else {
            Notification notification2 = new Notification(f(context, a(abVar)), null, System.currentTimeMillis());
            String[] strArrA = a(context, rVarM);
            try {
                notification2.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class).invoke(notification2, context, strArrA[0], strArrA[1], pendingIntentA);
            } catch (IllegalAccessException e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
            } catch (IllegalArgumentException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            } catch (NoSuchMethodException e3) {
                com.xiaomi.channel.commonutils.logger.b.a(e3);
            } catch (InvocationTargetException e4) {
                com.xiaomi.channel.commonutils.logger.b.a(e4);
            }
            Map<String, String> mapS = rVarM.s();
            if (mapS != null && mapS.containsKey("ticker")) {
                notification2.tickerText = mapS.get("ticker");
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - a > 10000) {
                a = jCurrentTimeMillis;
                int iC = e(context, a(abVar)) ? c(context, a(abVar)) : rVarM.f;
                notification2.defaults = iC;
                if (mapS != null && (iC & 1) != 0) {
                    String str = mapS.get("sound_uri");
                    if (!TextUtils.isEmpty(str) && str.startsWith("android.resource://" + a(abVar))) {
                        notification2.defaults = iC ^ 1;
                        notification2.sound = Uri.parse(str);
                    }
                }
            }
            notification2.flags |= 16;
            if (remoteViewsB != null) {
                notification2.contentView = remoteViewsB;
            }
            notification = notification2;
        }
        if (com.xiaomi.channel.commonutils.android.g.a() && Build.VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(rVarM.b())) {
            notification.extras.putString("message_id", rVarM.b());
        }
        String str2 = rVarM.s() == null ? null : rVarM.s().get("message_count");
        if (com.xiaomi.channel.commonutils.android.g.a() && str2 != null) {
            try {
                a(notification, Integer.parseInt(str2));
            } catch (NumberFormatException e5) {
                com.xiaomi.channel.commonutils.logger.b.a(e5);
            }
        }
        if ("com.xiaomi.xmsf".equals(context.getPackageName())) {
            a(notification, a(abVar));
        }
        if ("com.xiaomi.xmsf".equals(a(abVar))) {
            ay.a(context, abVar, notification);
        }
        int iQ = rVarM.q() + ((a(abVar).hashCode() / 10) * 10);
        notificationManager.notify(iQ, notification);
        Pair<Integer, com.xiaomi.xmpush.thrift.ab> pair = new Pair<>(Integer.valueOf(iQ), abVar);
        synchronized (b) {
            b.add(pair);
            if (b.size() > 100) {
                b.remove();
            }
        }
        return bVar;
    }

    static String a(com.xiaomi.xmpush.thrift.ab abVar) {
        com.xiaomi.xmpush.thrift.r rVarM;
        if ("com.xiaomi.xmsf".equals(abVar.f) && (rVarM = abVar.m()) != null && rVarM.s() != null) {
            String str = rVarM.s().get("miui_package_name");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return abVar.f;
    }

    private static void a(Notification notification, int i) {
        Object objA = com.xiaomi.channel.commonutils.reflect.a.a(notification, "extraNotification");
        if (objA != null) {
            com.xiaomi.channel.commonutils.reflect.a.a(objA, "setMessageCount", Integer.valueOf(i));
        }
    }

    public static void a(Context context, String str, int i) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int iHashCode = ((str.hashCode() / 10) * 10) + i;
        LinkedList linkedList = new LinkedList();
        if (i >= 0) {
            notificationManager.cancel(iHashCode);
        }
        synchronized (b) {
            for (Pair<Integer, com.xiaomi.xmpush.thrift.ab> pair : b) {
                com.xiaomi.xmpush.thrift.ab abVar = (com.xiaomi.xmpush.thrift.ab) pair.second;
                if (abVar != null) {
                    String strA = a(abVar);
                    if (i >= 0) {
                        if (iHashCode == ((Integer) pair.first).intValue() && TextUtils.equals(strA, str)) {
                            linkedList.add(pair);
                        }
                    } else if (i == -1 && TextUtils.equals(strA, str)) {
                        notificationManager.cancel(((Integer) pair.first).intValue());
                        linkedList.add(pair);
                    }
                }
            }
            if (b != null) {
                b.removeAll(linkedList);
                a((LinkedList<Pair<Integer, com.xiaomi.xmpush.thrift.ab>>) linkedList);
            }
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        LinkedList linkedList = new LinkedList();
        synchronized (b) {
            for (Pair<Integer, com.xiaomi.xmpush.thrift.ab> pair : b) {
                com.xiaomi.xmpush.thrift.ab abVar = (com.xiaomi.xmpush.thrift.ab) pair.second;
                if (abVar != null) {
                    String strA = a(abVar);
                    com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
                    if (rVarM != null && TextUtils.equals(strA, str)) {
                        String strH = rVarM.h();
                        String strJ = rVarM.j();
                        if (!TextUtils.isEmpty(strH) && !TextUtils.isEmpty(strJ) && a(str2, strH) && a(str3, strJ)) {
                            notificationManager.cancel(((Integer) pair.first).intValue());
                            linkedList.add(pair);
                        }
                    }
                }
            }
            if (b != null) {
                b.removeAll(linkedList);
                a((LinkedList<Pair<Integer, com.xiaomi.xmpush.thrift.ab>>) linkedList);
            }
        }
    }

    private static void a(LinkedList<Pair<Integer, com.xiaomi.xmpush.thrift.ab>> linkedList) {
        if (linkedList == null || linkedList.size() <= 0) {
            return;
        }
        aw.a().a("category_clear_notification", "clear_notification", linkedList.size(), "");
    }

    public static boolean a(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100 && Arrays.asList(runningAppProcessInfo.pkgList).contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean a(String str, String str2) {
        return TextUtils.isEmpty(str) || str2.contains(str);
    }

    public static boolean a(Map<String, String> map) {
        if (map == null || !map.containsKey("notify_foreground")) {
            return true;
        }
        return "1".equals(map.get("notify_foreground"));
    }

    private static String[] a(Context context, com.xiaomi.xmpush.thrift.r rVar) {
        String strH = rVar.h();
        String strJ = rVar.j();
        Map<String, String> mapS = rVar.s();
        if (mapS != null) {
            int iIntValue = Float.valueOf((context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().density) + 0.5f).intValue();
            if (iIntValue <= 320) {
                String str = mapS.get("title_short");
                if (!TextUtils.isEmpty(str)) {
                    strH = str;
                }
                String str2 = mapS.get("description_short");
                if (TextUtils.isEmpty(str2)) {
                    str2 = strJ;
                }
                strJ = str2;
            } else if (iIntValue > 360) {
                String str3 = mapS.get("title_long");
                if (!TextUtils.isEmpty(str3)) {
                    strH = str3;
                }
                String str4 = mapS.get("description_long");
                if (!TextUtils.isEmpty(str4)) {
                    strJ = str4;
                }
            }
        }
        return new String[]{strH, strJ};
    }

    private static RemoteViews b(Context context, com.xiaomi.xmpush.thrift.ab abVar, byte[] bArr) {
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        String strA = a(abVar);
        Map<String, String> mapS = rVarM.s();
        if (mapS == null) {
            return null;
        }
        String str = mapS.get("layout_name");
        String str2 = mapS.get("layout_value");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(strA);
            int identifier = resourcesForApplication.getIdentifier(str, "layout", strA);
            if (identifier == 0) {
                return null;
            }
            RemoteViews remoteViews = new RemoteViews(strA, identifier);
            try {
                JSONObject jSONObject = new JSONObject(str2);
                if (jSONObject.has("text")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("text");
                    Iterator<String> itKeys = jSONObject2.keys();
                    while (itKeys.hasNext()) {
                        String next = itKeys.next();
                        String string = jSONObject2.getString(next);
                        int identifier2 = resourcesForApplication.getIdentifier(next, "id", strA);
                        if (identifier2 > 0) {
                            remoteViews.setTextViewText(identifier2, string);
                        }
                    }
                }
                if (jSONObject.has("image")) {
                    JSONObject jSONObject3 = jSONObject.getJSONObject("image");
                    Iterator<String> itKeys2 = jSONObject3.keys();
                    while (itKeys2.hasNext()) {
                        String next2 = itKeys2.next();
                        String string2 = jSONObject3.getString(next2);
                        int identifier3 = resourcesForApplication.getIdentifier(next2, "id", strA);
                        int identifier4 = resourcesForApplication.getIdentifier(string2, "drawable", strA);
                        if (identifier3 > 0) {
                            remoteViews.setImageViewResource(identifier3, identifier4);
                        }
                    }
                }
                if (jSONObject.has("time")) {
                    JSONObject jSONObject4 = jSONObject.getJSONObject("time");
                    Iterator<String> itKeys3 = jSONObject4.keys();
                    while (itKeys3.hasNext()) {
                        String next3 = itKeys3.next();
                        String string3 = jSONObject4.getString(next3);
                        if (string3.length() == 0) {
                            string3 = "yy-MM-dd hh:mm";
                        }
                        int identifier5 = resourcesForApplication.getIdentifier(next3, "id", strA);
                        if (identifier5 > 0) {
                            remoteViews.setTextViewText(identifier5, new SimpleDateFormat(string3).format(new Date(System.currentTimeMillis())));
                        }
                    }
                }
                return remoteViews;
            } catch (JSONException e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
                return null;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return null;
        }
    }

    public static void b(Context context, String str) {
        a(context, str, -1);
    }

    static void b(Context context, String str, int i) {
        context.getSharedPreferences("pref_notify_type", 0).edit().putInt(str, i).commit();
    }

    public static boolean b(com.xiaomi.xmpush.thrift.ab abVar) {
        com.xiaomi.xmpush.thrift.r rVarM = abVar.m();
        return rVarM != null && rVarM.v();
    }

    static int c(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).getInt(str, Integer.MAX_VALUE);
    }

    static void d(Context context, String str) {
        context.getSharedPreferences("pref_notify_type", 0).edit().remove(str).commit();
    }

    static boolean e(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).contains(str);
    }

    private static int f(Context context, String str) {
        int iA = a(context, str, "mipush_notification");
        int iA2 = a(context, str, "mipush_small_notification");
        if (iA <= 0) {
            iA = iA2 > 0 ? iA2 : context.getApplicationInfo().icon;
        }
        return (iA != 0 || Build.VERSION.SDK_INT < 9) ? iA : context.getApplicationInfo().logo;
    }
}
