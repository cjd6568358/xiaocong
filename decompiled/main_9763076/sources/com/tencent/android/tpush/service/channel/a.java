package com.tencent.android.tpush.service.channel;

import android.util.SparseArray;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a b = new a();
    private SparseArray a = new SparseArray();

    public static a a() {
        return b;
    }

    public a() {
    }

    public a(Object... objArr) {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < objArr.length) {
                this.a.put(((Integer) objArr[i2]).intValue(), objArr[i2 + 1]);
                i = i2 + 2;
            } else {
                return;
            }
        }
    }

    public void a(int i, Object obj) {
        this.a.put(i, obj);
    }

    public boolean b() {
        return ((Boolean) this.a.get(2, false)).booleanValue();
    }

    public long c() {
        return ((Long) this.a.get(3, 0L)).longValue();
    }

    public String d() {
        return (String) this.a.get(0, Constants.MAIN_VERSION_TAG);
    }

    public int e() {
        return ((Integer) this.a.get(1, 0)).intValue();
    }
}
