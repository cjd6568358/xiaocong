package com.tencent.android.tpush.service.c;

import android.content.Context;
import com.tencent.android.tpush.data.MessageId;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j implements Runnable {
    int a;
    Context b;
    final /* synthetic */ a c;

    public j(a aVar, Context context, int i) {
        this.c = aVar;
        this.b = context;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        switch (this.a) {
            case 1:
                this.c.c(this.b, (MessageId) null);
                break;
            case 2:
                this.c.a(this.b, (Long) (-1L));
                break;
            case 3:
            default:
                com.tencent.android.tpush.a.a.i("SrvMessageManager", "unknown report type");
                break;
            case 4:
                this.c.b(this.b, (MessageId) null);
                break;
        }
    }
}
