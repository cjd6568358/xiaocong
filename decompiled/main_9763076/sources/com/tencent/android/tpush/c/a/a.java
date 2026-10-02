package com.tencent.android.tpush.c.a;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends com.tencent.android.tpush.c.d {
    @Override // com.tencent.android.tpush.c.d
    public void a(Context context) {
        try {
            Class<?> cls = Class.forName("com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl");
            cls.getMethod("registerPush", Context.class).invoke(cls, context);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "registerPush FCM Error for InvocationTargetException: " + cause.getMessage());
            cause.printStackTrace();
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "registerPush FCM Error, are you import otherpush package? " + e2);
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public void b(Context context) {
        try {
            Class<?> cls = Class.forName("com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl");
            cls.getMethod("unregisterPush", Context.class).invoke(cls, context);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "unregisterPush FCM Error, are you import otherpush package? " + e);
        }
    }

    @Override // com.tencent.android.tpush.c.d
    public String c(Context context) {
        try {
            Class<?> cls = Class.forName("com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl");
            Object objInvoke = cls.getMethod("getToken", Context.class).invoke(cls, context);
            if (objInvoke != null) {
                return objInvoke.toString();
            }
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "getToken Error for InvocationTargetException: " + cause.getMessage());
            cause.printStackTrace();
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.d(Constants.OTHER_PUSH_TAG, "getToken Error", e2);
        }
        return null;
    }

    @Override // com.tencent.android.tpush.c.d
    public boolean d(Context context) {
        boolean z = false;
        try {
            if (context.getResources().getAssets().open("google-services.json") == null) {
                com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "isConfig : no google-services.json file");
            } else if (com.tencent.android.tpush.c.a.a(context, "com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl")) {
                z = true;
            }
        } catch (IOException e) {
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "isConfig :" + e);
        }
        return z;
    }

    @Override // com.tencent.android.tpush.c.d
    public String a() {
        return "fcm";
    }
}
