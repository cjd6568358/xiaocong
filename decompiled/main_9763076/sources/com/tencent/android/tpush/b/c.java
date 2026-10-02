package com.tencent.android.tpush.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class c extends BroadcastReceiver {
    c() {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(Constants.FLAG_PACK_NAME);
        com.tencent.android.tpush.a.a.e("MessageHelper", "onReceive: Notification has canceled! pkg name: " + stringExtra);
        if (!t.c(stringExtra) && stringExtra.equals(context.getPackageName())) {
            d.a().f(context, intent.getLongExtra(MessageKey.MSG_ID, -1L));
            Intent intent2 = new Intent(Constants.ACTION_FEEDBACK);
            intent2.setPackage(stringExtra);
            intent2.putExtras(intent);
            intent2.putExtra(Constants.FEEDBACK_TAG, 4);
            context.sendBroadcast(intent2);
            Intent intent3 = new Intent("com.tencent.android.tpush.action.PUSH_CANCELLED.RESULT.V3");
            intent3.putExtras(intent);
            com.tencent.android.tpush.service.d.a.e(context, intent3);
            context.sendBroadcast(intent3);
        }
    }
}
