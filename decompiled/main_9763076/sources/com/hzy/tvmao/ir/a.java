package com.hzy.tvmao.ir;

import com.hzy.tvmao.utils.LogUtil;
import com.hzy.tvmao.utils.c;

/* JADX INFO: compiled from: IRManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a a;
    private boolean b = false;

    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    public void a(int i, String str) {
        a(i, str, 0L);
    }

    public void a(int i, String str, long j) {
        if (str != null && str.contains("&")) {
            String[] strArrSplit = str.split("&");
            if (strArrSplit.length <= 1) {
                str = null;
            } else if (this.b) {
                str = strArrSplit[1];
                LogUtil.d(String.valueOf(this.b) + strArrSplit[1]);
                this.b = false;
            } else {
                str = strArrSplit[0];
                LogUtil.d(String.valueOf(this.b) + strArrSplit[0]);
                this.b = true;
            }
        }
        int[] iArrC = c.c(str);
        if (iArrC != null) {
            a(i, iArrC, j);
        }
    }

    public void a(int i, int[] iArr, long j) {
    }
}
