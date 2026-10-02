package com.tencent.android.tpush;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b extends Handler {
    final /* synthetic */ XGDownloadService a;

    b(XGDownloadService xGDownloadService) {
        this.a = xGDownloadService;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.arg1;
        message.getData();
        switch (message.what) {
            case 0:
                Uri uriFromFile = Uri.fromFile(this.a.e);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(uriFromFile, "application/vnd.android.package-archive");
                this.a.i = PendingIntent.getActivity(this.a, i, intent, 0);
                this.a.g.flags = 16;
                this.a.g.defaults = 1;
                this.a.f.notify(i, this.a.g);
                this.a.stopSelf();
                break;
            case 1:
                this.a.g.flags = 16;
                this.a.f.notify(i, this.a.g);
                break;
            default:
                this.a.stopSelf();
                break;
        }
    }
}
