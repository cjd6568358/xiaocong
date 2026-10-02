package com.tencent.android.tpush.b;

import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.encrypt.Rijndael;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {
    private Context g;
    private Intent h;
    private long a = -1;
    private long b = -1;
    private long c = -1;
    private String d = Constants.MAIN_VERSION_TAG;
    private long e = -1;
    private long f = -1;
    private a i = null;

    private n(Context context, Intent intent) {
        this.g = null;
        this.h = null;
        this.g = context;
        this.h = intent;
    }

    public static n a(Context context, Intent intent) {
        n nVar = new n(context, intent);
        String strDecrypt = Rijndael.decrypt(intent.getStringExtra("content"));
        nVar.d = strDecrypt;
        nVar.a = intent.getLongExtra(MessageKey.MSG_ID, -1L);
        nVar.b = intent.getLongExtra("accId", -1L);
        nVar.c = intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, -1L);
        nVar.e = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, -1L);
        nVar.f = intent.getLongExtra("type", -1L);
        a fVar = null;
        switch ((int) nVar.f) {
            case 1:
                fVar = new f(strDecrypt);
                break;
            case 2:
                fVar = new o(strDecrypt);
                break;
            case 3:
                d.a().b(context, strDecrypt);
                XGPushManager.msgAck(context, nVar);
                break;
            default:
                com.tencent.android.tpush.a.a.i(Constants.LogTag, "error type for message, drop it, type:" + nVar.f + ",intent:" + intent);
                XGPushManager.msgAck(context, nVar);
                break;
        }
        if (fVar != null) {
            nVar.i = fVar;
            nVar.i.b();
        }
        return nVar;
    }

    public void a() {
        if (this.i.c() == 1) {
            b.b(this.g, this);
        }
    }

    public long b() {
        return this.a;
    }

    public long c() {
        return this.b;
    }

    public long d() {
        return this.c;
    }

    public long e() {
        return this.e;
    }

    public String f() {
        return this.d;
    }

    public a g() {
        return this.i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PushMessageManager [msgId=").append(this.a).append(", accessId=").append(this.b).append(", busiMsgId=").append(this.c).append(", content=").append(this.d).append(", timestamps=").append(this.e).append(", type=").append(this.f).append(", intent=").append(this.h).append(", messageHolder=").append(this.i).append("]");
        return sb.toString();
    }
}
