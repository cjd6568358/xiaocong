package com.meizu.cloud.pushsdk.handler.a;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.MPushMessage;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends c {
    public b(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start MessageV2Handler match");
        return a(0, g(intent)) && PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_SHOW.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j */
    public MessageV3 c(Intent intent) {
        MPushMessage mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
        return MessageV3.parse(g(intent), d(intent), mPushMessage.getTaskId(), mPushMessage);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    public void a(MessageV3 messageV3, com.meizu.cloud.pushsdk.notification.e eVar) {
        if (eVar != null) {
            eVar.c(messageV3);
            b().b(c(), messageV3.getTitle(), messageV3.getContent(), a(messageV3.getWebUrl(), messageV3.getParamsMap()));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: a */
    public void b(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.d.c(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: b */
    public void c(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.d.a(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }
}
