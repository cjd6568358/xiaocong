package com.tencent.android.tpush;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class XGPushNotificationBuilder {
    public static final String BASIC_NOTIFICATION_BUILDER_TYPE = "basic";
    public static final String CHANNEL_ID = "xg-channle-id";
    public static final String CHANNEL_NAME = "message";
    public static final String CUSTOM_NOTIFICATION_BUILDER_TYPE = "custom";
    private static Object w = null;
    protected String u;
    protected Integer a = null;
    protected PendingIntent b = null;
    protected RemoteViews c = null;
    protected RemoteViews d = null;
    protected Integer e = null;
    protected PendingIntent f = null;
    protected Integer g = null;
    protected Integer h = null;
    protected Integer i = null;
    protected Integer j = null;
    protected Integer k = null;
    protected Integer l = null;
    protected Integer m = null;
    protected Uri n = null;
    protected CharSequence o = null;
    protected long[] p = null;
    protected Long q = null;
    protected Integer r = null;
    protected Bitmap s = null;
    protected Integer t = null;
    protected Integer v = null;

    protected abstract void a(JSONObject jSONObject);

    protected abstract void b(JSONObject jSONObject);

    public abstract Notification buildNotification(Context context);

    public abstract String getType();

    public void encode(JSONObject jSONObject) {
        a(jSONObject);
        com.tencent.android.tpush.common.e.a(jSONObject, "audioStringType", this.a);
        com.tencent.android.tpush.common.e.a(jSONObject, "defaults", this.e);
        com.tencent.android.tpush.common.e.a(jSONObject, "flags", this.g);
        com.tencent.android.tpush.common.e.a(jSONObject, MessageKey.MSG_ICON, this.h);
        com.tencent.android.tpush.common.e.a(jSONObject, "iconLevel", this.i);
        com.tencent.android.tpush.common.e.a(jSONObject, "ledARGB", this.j);
        com.tencent.android.tpush.common.e.a(jSONObject, "ledOffMS", this.k);
        com.tencent.android.tpush.common.e.a(jSONObject, "ledOnMS", this.l);
        com.tencent.android.tpush.common.e.a(jSONObject, "number", this.m);
        com.tencent.android.tpush.common.e.a(jSONObject, "sound", this.n);
        com.tencent.android.tpush.common.e.a(jSONObject, "smallIcon", this.r);
        com.tencent.android.tpush.common.e.a(jSONObject, "notificationLargeIcon", this.t);
        if (this.p != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.p.length; i++) {
                sb.append(String.valueOf(this.p[i]));
                if (i != this.p.length - 1) {
                    sb.append(",");
                }
            }
            com.tencent.android.tpush.common.e.a(jSONObject, MessageKey.MSG_VIBRATE, sb.toString());
        }
        com.tencent.android.tpush.common.e.a(jSONObject, "notificationId", this.v);
    }

    public void decode(String str) {
        JSONObject jSONObject = new JSONObject(str);
        b(jSONObject);
        this.a = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "audioStringType", null);
        this.e = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "defaults", null);
        this.g = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "flags", null);
        this.h = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, MessageKey.MSG_ICON, null);
        this.i = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "iconLevel", null);
        this.j = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "ledARGB", null);
        this.k = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "ledOffMS", null);
        this.l = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "ledOnMS", null);
        this.m = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "number", null);
        String str2 = (String) com.tencent.android.tpush.common.e.b(jSONObject, "sound", null);
        this.r = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "smallIcon", null);
        this.t = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "notificationLargeIcon", null);
        if (str2 != null) {
            this.n = Uri.parse(str2);
        }
        String str3 = (String) com.tencent.android.tpush.common.e.b(jSONObject, MessageKey.MSG_VIBRATE, null);
        if (str3 != null) {
            String[] strArrSplit = str3.split(",");
            int length = strArrSplit.length;
            this.p = new long[length];
            for (int i = 0; i < length; i++) {
                try {
                    this.p[i] = Long.valueOf(strArrSplit[i]).longValue();
                } catch (NumberFormatException e) {
                }
            }
        }
        this.v = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "notificationId", null);
    }

    public String getTitle(Context context) {
        if (this.u == null) {
            this.u = (String) context.getApplicationContext().getPackageManager().getApplicationLabel(context.getApplicationInfo());
        }
        return this.u;
    }

    public void setTitle(String str) {
        this.u = str;
    }

    public int getApplicationIcon(Context context) {
        return context.getApplicationInfo().icon;
    }

    @SuppressLint({"NewApi"})
    public Notification getChannelNotification(Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        if (this.r != null) {
            builder.setSmallIcon(this.r.intValue());
        }
        if (this.t != null) {
            try {
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), this.t.intValue()));
            } catch (OutOfMemoryError e) {
            }
        }
        if (this.s != null) {
            builder.setLargeIcon(this.s);
        }
        if (this.u == null) {
            this.u = getTitle(context);
        } else {
            builder.setContentTitle(this.u);
        }
        if (this.o != null && this.c == null) {
            bigTextStyle.bigText(this.o);
            builder.setStyle(bigTextStyle);
            builder.setContentText(this.o);
            builder.setTicker(this.o);
        } else {
            builder.setContentText(this.o);
            builder.setTicker(this.o);
        }
        if (Build.VERSION.SDK_INT >= 26 && context.getApplicationInfo().targetSdkVersion >= 26) {
            try {
                if (w == null) {
                    com.tencent.android.tpush.a.a.e(Constants.LogTag, "XGPushNotification create notificationChannle");
                    Class<?> cls = Class.forName("android.app.NotificationChannel");
                    w = cls.getConstructor(String.class, CharSequence.class, Integer.TYPE).newInstance(CHANNEL_ID, "message", 4);
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                    notificationManager.getClass().getMethod("createNotificationChannel", cls).invoke(notificationManager, w);
                }
                builder.getClass().getMethod("setChannelId", String.class).invoke(builder, CHANNEL_ID);
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.j(Constants.LogTag, "XGPushNotification create channel Error: " + e2.getMessage());
                e2.printStackTrace();
            }
        }
        return builder.build();
    }

    private Notification b(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        if (this.r != null) {
            builder.setSmallIcon(this.r.intValue());
        }
        if (this.t != null) {
            try {
                builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), this.t.intValue()));
            } catch (OutOfMemoryError e) {
            }
        }
        if (this.s != null) {
            builder.setLargeIcon(this.s);
        }
        if (this.u == null) {
            this.u = getTitle(context);
        } else {
            builder.setContentTitle(this.u);
        }
        if (this.o != null && this.c == null) {
            bigTextStyle.bigText(this.o);
            builder.setStyle(bigTextStyle);
            builder.setContentText(this.o);
            builder.setTicker(this.o);
        } else {
            builder.setContentText(this.o);
            builder.setTicker(this.o);
        }
        return builder.build();
    }

    protected Notification a(Context context) {
        Notification notificationB;
        new Notification();
        if (this.v == null) {
            this.v = 0;
        }
        com.tencent.android.tpush.a.a.f(Constants.LogTag, "XGPushNotification Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT + ", targetSDK:" + context.getApplicationInfo().targetSdkVersion);
        if (Build.VERSION.SDK_INT >= 26 && context.getApplicationInfo().targetSdkVersion >= 26) {
            notificationB = getChannelNotification(context);
        } else {
            notificationB = b(context);
        }
        if (this.a != null) {
            notificationB.audioStreamType = this.a.intValue();
        }
        if (this.b != null) {
            notificationB.contentIntent = this.b;
        }
        if (this.c != null) {
            if (Build.MANUFACTURER.trim().toLowerCase().equals("oppo")) {
                com.tencent.android.tpush.a.a.h(Constants.LogTag, "XGPushNotification: Oppo Rom not allow custom contentview. Not set it");
            } else {
                notificationB.contentView = this.c;
            }
        }
        if (this.e != null) {
            notificationB.defaults = this.e.intValue();
        }
        if (this.h != null) {
            notificationB.icon = this.h.intValue();
        }
        if (this.f != null) {
            notificationB.deleteIntent = this.f;
        }
        if (this.g != null) {
            notificationB.flags = this.g.intValue();
        } else {
            notificationB.flags = 16;
        }
        if (this.i != null) {
            notificationB.iconLevel = this.i.intValue();
        }
        if (this.j != null) {
            notificationB.ledARGB = this.j.intValue();
        }
        if (this.k != null) {
            notificationB.ledOffMS = this.k.intValue();
        }
        if (this.l != null) {
            notificationB.ledOnMS = this.l.intValue();
        }
        if (this.m != null) {
            notificationB.number = this.m.intValue();
        }
        if (this.n != null) {
            notificationB.sound = this.n;
        }
        if (this.p != null) {
            notificationB.vibrate = this.p;
        }
        if (this.q != null) {
            notificationB.when = this.q.longValue();
        } else {
            notificationB.when = System.currentTimeMillis();
        }
        return notificationB;
    }

    public int getAudioStringType() {
        return this.a.intValue();
    }

    public XGPushNotificationBuilder setAudioStringType(int i) {
        this.a = Integer.valueOf(i);
        return this;
    }

    public PendingIntent getContentIntent() {
        return this.b;
    }

    public XGPushNotificationBuilder setContentIntent(PendingIntent pendingIntent) {
        this.b = pendingIntent;
        return this;
    }

    public XGPushNotificationBuilder setContentView(RemoteViews remoteViews) {
        this.c = remoteViews;
        return this;
    }

    public XGPushNotificationBuilder setbigContentView(RemoteViews remoteViews) {
        this.d = remoteViews;
        return this;
    }

    public int getDefaults() {
        return this.e.intValue();
    }

    public XGPushNotificationBuilder setDefaults(int i) {
        if (this.e == null) {
            this.e = Integer.valueOf(i);
        } else {
            this.e = Integer.valueOf(this.e.intValue() | i);
        }
        return this;
    }

    public int getFlags() {
        return this.g.intValue();
    }

    public XGPushNotificationBuilder setFlags(int i) {
        if (this.g == null) {
            this.g = Integer.valueOf(i);
        } else {
            this.g = Integer.valueOf(this.g.intValue() | i);
        }
        return this;
    }

    public Integer getIcon() {
        return this.h;
    }

    public XGPushNotificationBuilder setIcon(Integer num) {
        this.h = num;
        return this;
    }

    public Integer getSmallIcon() {
        return this.r;
    }

    public XGPushNotificationBuilder setSmallIcon(Integer num) {
        this.r = num;
        return this;
    }

    public Bitmap getLargeIcon() {
        return this.s;
    }

    public XGPushNotificationBuilder setLargeIcon(Bitmap bitmap) {
        this.s = bitmap;
        return this;
    }

    public XGPushNotificationBuilder setNotificationLargeIcon(int i) {
        this.t = Integer.valueOf(i);
        return this;
    }

    public Integer getNotificationLargeIcon() {
        return this.t;
    }

    public int getIconLevel() {
        return this.i.intValue();
    }

    public XGPushNotificationBuilder setIconLevel(int i) {
        this.i = Integer.valueOf(i);
        return this;
    }

    public int getLedARGB() {
        return this.j.intValue();
    }

    public XGPushNotificationBuilder setLedARGB(int i) {
        this.j = Integer.valueOf(i);
        return this;
    }

    public int getLedOffMS() {
        return this.k.intValue();
    }

    public XGPushNotificationBuilder setLedOffMS(int i) {
        this.k = Integer.valueOf(i);
        return this;
    }

    public int getLedOnMS() {
        return this.l.intValue();
    }

    public XGPushNotificationBuilder setLedOnMS(int i) {
        this.l = Integer.valueOf(i);
        return this;
    }

    public int getNumber() {
        return this.m.intValue();
    }

    public XGPushNotificationBuilder setNumber(int i) {
        this.m = Integer.valueOf(i);
        return this;
    }

    public Uri getSound() {
        return this.n;
    }

    public XGPushNotificationBuilder setSound(Uri uri) {
        this.n = uri;
        return this;
    }

    public CharSequence getTickerText() {
        return this.o;
    }

    public XGPushNotificationBuilder setTickerText(CharSequence charSequence) {
        this.o = charSequence;
        return this;
    }

    public long[] getVibrate() {
        return this.p;
    }

    public XGPushNotificationBuilder setVibrate(long[] jArr) {
        this.p = jArr;
        return this;
    }

    public long getWhen() {
        return this.q.longValue();
    }

    public XGPushNotificationBuilder setWhen(long j) {
        this.q = Long.valueOf(j);
        return this;
    }
}
