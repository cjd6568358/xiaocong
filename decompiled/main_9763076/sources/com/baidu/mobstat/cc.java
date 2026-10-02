package com.baidu.mobstat;

import android.content.Context;
import java.io.File;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cc implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ by b;

    cc(by byVar, Context context) {
        this.b = byVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        String[] list;
        try {
            File filesDir = this.a.getFilesDir();
            if (filesDir != null && filesDir.exists() && (list = filesDir.list(new cd(this))) != null && list.length != 0) {
                try {
                    Arrays.sort(list, new ce(this));
                } catch (Exception e) {
                    db.b(e);
                }
                int i = 0;
                for (String str : list) {
                    String strA = cu.a(this.a, str);
                    if (!this.b.b(this.a, strA)) {
                        by.b(this.a, str, strA);
                        i++;
                        if (i >= 5) {
                            return;
                        }
                    } else {
                        cu.b(this.a, str);
                        i = 0;
                    }
                }
            }
        } catch (Exception e2) {
            db.b(e2);
        }
    }
}
