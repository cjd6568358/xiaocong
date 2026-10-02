package com.meizu.cloud.pushsdk.notification.a;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends c {
    public a(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    protected void a(Notification.Builder builder, MessageV3 messageV3) {
        Bitmap bitmapA;
        if (com.meizu.cloud.pushsdk.util.b.a()) {
            Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
            if (messageV3.getmNotificationStyle() != null && !a() && !TextUtils.isEmpty(messageV3.getmNotificationStyle().getExpandableImageUrl()) && (bitmapA = a(messageV3.getmNotificationStyle().getExpandableImageUrl())) != null) {
                bigPictureStyle.setBigContentTitle(messageV3.getTitle());
                bigPictureStyle.setSummaryText(messageV3.getContent());
                bigPictureStyle.bigPicture(bitmapA);
                builder.setStyle(bigPictureStyle);
            }
        }
    }
}
