package com.baidu.mobstat;

import android.net.LocalServerSocket;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class dd {
    private LocalServerSocket a;

    public final synchronized boolean a() {
        boolean z;
        try {
            if (this.a == null) {
                this.a = new LocalServerSocket("com.baidu.mobstat.bplus");
                z = true;
            } else {
                z = false;
            }
        } catch (IOException e) {
        }
        return z;
    }

    public final synchronized void b() {
        if (this.a != null) {
            try {
                this.a.close();
                this.a = null;
            } catch (IOException e) {
            }
        }
    }
}
