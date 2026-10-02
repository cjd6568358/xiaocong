package com.tencent.android.tpush.b;

import android.content.Intent;
import com.tencent.android.tpush.common.MessageKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j implements Runnable {
    final /* synthetic */ Intent a;
    final /* synthetic */ i b;

    j(i iVar, Intent intent) {
        this.b = iVar;
        this.a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        String stringExtra = this.a.getStringExtra(MessageKey.MSG_DATE);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            if (com.tencent.android.tpush.service.e.m.b(stringExtra) || (!com.tencent.android.tpush.service.e.m.b(stringExtra) && simpleDateFormat.parse(stringExtra).compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date()))) == 0)) {
                if (com.tencent.android.tpush.service.e.m.a(this.a)) {
                    this.b.a(this.a);
                }
            } else if (!com.tencent.android.tpush.service.e.m.b(stringExtra) && simpleDateFormat.parse(stringExtra).compareTo(simpleDateFormat.parse(simpleDateFormat.format(new Date()))) < 0) {
                this.b.a(this.a);
            }
        } catch (ParseException e) {
            com.tencent.android.tpush.a.a.j(i.b, "try to handlerPushMessage, but ParseException : " + e);
        }
    }
}
