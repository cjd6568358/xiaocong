package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.b.a.i;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UTDevice {
    public static String getUtdid(Context context) {
        a aVarB = b.b(context);
        return (aVarB == null || i.m99a(aVarB.f())) ? "ffffffffffffffffffffffff" : aVarB.f();
    }

    public static String getUtdidForUpdate(Context context) {
        String strH = c.a(context).h();
        return (strH == null || i.m99a(strH)) ? "ffffffffffffffffffffffff" : strH;
    }
}
