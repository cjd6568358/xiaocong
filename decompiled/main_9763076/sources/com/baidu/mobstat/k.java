package com.baidu.mobstat;

import android.system.ErrnoException;
import android.system.Os;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k {
    static boolean a(String str, int i) {
        try {
            Os.chmod(str, i);
            return true;
        } catch (ErrnoException e) {
            g.b(e);
            return false;
        }
    }
}
