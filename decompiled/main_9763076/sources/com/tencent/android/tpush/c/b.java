package com.tencent.android.tpush.c;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.g;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.encrypt.Rijndael;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static void a(Context context) {
        if (context == null) {
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "updateToken Error: context is null");
        } else {
            g.a().a(new c(context));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context) {
        String token = XGPushConfig.getToken(context);
        if (token == null) {
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "updateToken Error: get XG Token is null");
            return;
        }
        long accessId = XGPushConfig.getAccessId(context);
        String strF = e.a(context).f();
        String strD = e.a(context).d();
        com.tencent.android.tpush.a.a.f(Constants.OTHER_PUSH_TAG, "other push token is : " + strD + " other push type: " + strF);
        if (t.c(strF) || t.c(strD)) {
            com.tencent.android.tpush.a.a.h(Constants.OTHER_PUSH_TAG, "updateToken Error: get otherPushType or otherPushToken is null");
            return;
        }
        Intent intent = new Intent("com.tencent.android.tpush.action.UPDATE_OTHER_PUSH_TOKEN.V3");
        intent.putExtra("accId", Rijndael.encrypt(Constants.MAIN_VERSION_TAG + accessId));
        intent.putExtra(Constants.FLAG_TOKEN, Rijndael.encrypt(token));
        intent.putExtra("other_push_type", Rijndael.encrypt(strF));
        intent.putExtra(Constants.OTHER_PUSH_TOKEN, Rijndael.encrypt(strD));
        context.sendBroadcast(intent);
    }
}
