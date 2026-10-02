package com.tencent.android.tpush;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGCustomPushNotificationBuilder extends XGPushNotificationBuilder {
    private Integer w = null;
    private Integer x = null;
    private Integer y = null;
    private Integer z = null;
    private Integer A = null;
    private Integer B = null;
    private Integer C = null;
    private Bitmap D = null;

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    protected void a(JSONObject jSONObject) {
        com.tencent.android.tpush.common.e.a(jSONObject, "layoutId", this.w);
        com.tencent.android.tpush.common.e.a(jSONObject, "layoutIconId", this.x);
        com.tencent.android.tpush.common.e.a(jSONObject, "layoutTitleId", this.y);
        com.tencent.android.tpush.common.e.a(jSONObject, "layoutTextId", this.z);
        com.tencent.android.tpush.common.e.a(jSONObject, "layoutIconDrawableId", this.A);
        com.tencent.android.tpush.common.e.a(jSONObject, "statusBarIconDrawableId", this.B);
        com.tencent.android.tpush.common.e.a(jSONObject, "layoutTimeId", this.C);
    }

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    protected void b(JSONObject jSONObject) {
        this.w = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "layoutId", null);
        this.x = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "layoutIconId", null);
        this.y = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "layoutTitleId", null);
        this.z = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "layoutTextId", null);
        this.A = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "layoutIconDrawableId", null);
        this.B = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "statusBarIconDrawableId", null);
        this.C = (Integer) com.tencent.android.tpush.common.e.b(jSONObject, "layoutTimeId", null);
    }

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    public Notification buildNotification(Context context) {
        if (this.w != null) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), this.w.intValue());
            if (this.y != null) {
                remoteViews.setTextViewText(this.y.intValue(), getTitle(context));
            }
            if (this.z != null) {
                remoteViews.setTextViewText(this.z.intValue(), this.o);
            }
            if (this.x != null) {
                remoteViews.setImageViewResource(this.x.intValue(), this.A.intValue());
            }
            if (this.D != null) {
                remoteViews.setImageViewBitmap(this.x.intValue(), this.D);
            }
            if (this.B != null) {
                remoteViews.setTextViewText(this.B.intValue(), getTitle(context));
            }
            if (this.C != null) {
                remoteViews.setTextViewText(this.C.intValue(), String.valueOf(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
            }
            this.c = remoteViews;
            return a(context);
        }
        return a(context);
    }

    public int getLayoutId() {
        return this.w.intValue();
    }

    public XGCustomPushNotificationBuilder setLayoutId(int i) {
        this.w = Integer.valueOf(i);
        return this;
    }

    public Integer getLayoutIconId() {
        return this.x;
    }

    public XGCustomPushNotificationBuilder setLayoutIconId(int i) {
        this.x = Integer.valueOf(i);
        return this;
    }

    public int getLayoutTitleId() {
        return this.y.intValue();
    }

    public XGCustomPushNotificationBuilder setLayoutTitleId(int i) {
        this.y = Integer.valueOf(i);
        return this;
    }

    public int getLayoutTextId() {
        return this.z.intValue();
    }

    public int getLayoutTimeId() {
        return this.C.intValue();
    }

    public XGCustomPushNotificationBuilder setLayoutTextId(int i) {
        this.z = Integer.valueOf(i);
        return this;
    }

    public XGCustomPushNotificationBuilder setLayoutTimeId(int i) {
        this.C = Integer.valueOf(i);
        return this;
    }

    public int getLayoutIconDrawableId() {
        return this.A.intValue();
    }

    public XGCustomPushNotificationBuilder setLayoutIconDrawableId(int i) {
        this.A = Integer.valueOf(i);
        return this;
    }

    public XGCustomPushNotificationBuilder setLayoutIconDrawableBmp(Bitmap bitmap) {
        this.D = bitmap;
        return this;
    }

    @Override // com.tencent.android.tpush.XGPushNotificationBuilder
    public String getType() {
        return XGPushNotificationBuilder.CUSTOM_NOTIFICATION_BUILDER_TYPE;
    }
}
