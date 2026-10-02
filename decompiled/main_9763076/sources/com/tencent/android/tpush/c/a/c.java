package com.tencent.android.tpush.c.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.service.e.h;
import com.tencent.android.tpush.service.e.m;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c extends BroadcastReceiver {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws Throwable {
        if (intent != null) {
            try {
                String action = intent.getAction();
                if (!m.b(action)) {
                    if ("com.huawei.android.push.intent.REGISTRATION".equals(action)) {
                        byte[] byteArrayExtra = intent.getByteArrayExtra("device_token");
                        if (byteArrayExtra != null) {
                            this.a.d = new String(byteArrayExtra, HTTP.UTF_8);
                            if (!m.b(this.a.d)) {
                                h.b(context, "huawei_token", this.a.d);
                            }
                        }
                    } else if ("com.huawei.android.push.intent.RECEIVE".equals(action)) {
                        com.tencent.android.tpush.a.a.c("OtherPushHuaWeiImpl", "reciver action com.huawei.android.push.intent.RECEIVE");
                    } else if ("com.huawei.intent.action.PUSH_STATE".equals(action)) {
                        com.tencent.android.tpush.a.a.c("OtherPushHuaWeiImpl", "reciver action com.huawei.intent.action.PUSH_STATEE");
                    }
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c("OtherPushHuaWeiImpl", "registerHuaweiRecevier ", th);
                t.a("receiver token error" + th.getLocalizedMessage(), this.a.b);
            }
        }
    }
}
