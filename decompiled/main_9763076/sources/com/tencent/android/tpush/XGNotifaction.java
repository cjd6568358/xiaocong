package com.tencent.android.tpush;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGNotifaction {
    private int a;
    private Notification b;
    private String c;
    private String d;
    private String e;
    private Context f;

    public XGNotifaction(Context context, int i, Notification notification, com.tencent.android.tpush.b.f fVar) {
        this.a = 0;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.f = context.getApplicationContext();
        this.a = i;
        this.b = notification;
        this.c = fVar.e();
        this.d = fVar.f();
        this.e = fVar.g();
    }

    public void setNotifyId(int i) {
        this.a = i;
    }

    public String toString() {
        return "XGNotifaction [notifyId=" + this.a + ", title=" + this.c + ", content=" + this.d + ", customContent=" + this.e + "]";
    }

    public boolean doNotify() {
        NotificationManager notificationManager;
        if (this.b == null || this.f == null || (notificationManager = (NotificationManager) this.f.getSystemService("notification")) == null) {
            return false;
        }
        notificationManager.notify(this.a, this.b);
        return true;
    }

    public int getNotifyId() {
        return this.a;
    }

    public Notification getNotifaction() {
        return this.b;
    }

    public String getTitle() {
        return this.c;
    }

    public String getContent() {
        return this.d;
    }

    public String getCustomContent() {
        return this.e;
    }
}
