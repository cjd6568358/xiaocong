package com.baidu.mobstat;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final enum ap extends ao {
    ap(String str, int i, int i2) {
        super(str, i, i2, null);
    }

    @Override // com.baidu.mobstat.ao
    public void a(Context context) {
        if (au.a(context).b(context)) {
            try {
                Intent intent = new Intent(context, Class.forName("com.baidu.bottom.service.BottomService"));
                intent.putExtra("SDK_PRODUCT_LY", "MS");
                context.startService(intent);
            } catch (Throwable th) {
                db.b(th);
            }
        }
    }
}
