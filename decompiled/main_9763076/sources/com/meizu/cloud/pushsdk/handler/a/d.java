package com.meizu.cloud.pushsdk.handler.a;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends a<String> {
    public d(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public String c(Intent intent) {
        String stringExtra = intent.getStringExtra("registration_id");
        com.meizu.cloud.pushsdk.util.c.a(c(), stringExtra);
        return stringExtra;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(String str, com.meizu.cloud.pushsdk.notification.e eVar) {
        if (b() != null) {
            b().a(c(), str);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start RegisterMessageHandler match");
        return PushConstants.MZ_PUSH_ON_REGISTER_ACTION.equals(intent.getAction()) || (PushConstants.REGISTRATION_CALLBACK_INTENT.equals(intent.getAction()) && !TextUtils.isEmpty(intent.getStringExtra("registration_id")));
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 16;
    }
}
