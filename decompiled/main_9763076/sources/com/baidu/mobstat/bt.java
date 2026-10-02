package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bt {
    private static bt a = new bt();
    private boolean b = false;

    public static bt a() {
        return a;
    }

    private bt() {
    }

    public void a(Context context, boolean z) {
        db.a("openExceptonAnalysis");
        if (!this.b) {
            this.b = true;
            bl.a().a(context);
            if (!z) {
                NativeCrashHandler.init(context);
            }
        }
    }
}
