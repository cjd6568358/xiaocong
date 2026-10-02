package com.tencent.android.tpush;

import android.content.DialogInterface;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class n implements DialogInterface.OnClickListener {
    final /* synthetic */ Intent a;
    final /* synthetic */ XGPushActivity b;

    n(XGPushActivity xGPushActivity, Intent intent) {
        this.b = xGPushActivity;
        this.a = intent;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.putExtra("action", 5);
        this.b.broadcastToTPushService(this.a);
        Intent intent = new Intent(this.b, (Class<?>) XGDownloadService.class);
        intent.putExtras(this.a);
        intent.putExtra(Constants.FLAG_PACKAGE_DOWNLOAD_URL, this.a.getStringExtra(Constants.FLAG_PACKAGE_DOWNLOAD_URL));
        this.b.startService(intent);
        this.b.finish();
    }
}
