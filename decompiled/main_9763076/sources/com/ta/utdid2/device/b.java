package com.ta.utdid2.device;

import android.content.Context;
import com.ta.utdid2.b.a.g;
import com.ta.utdid2.b.a.i;
import java.util.zip.Adler32;

/* JADX INFO: compiled from: DeviceInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static a a = null;
    static String k = "d6fc3a4a06adbde89223bvefedc24fecde188aaa9161";
    static final Object e = new Object();

    static long a(a aVar) {
        if (aVar != null) {
            String str = String.format("%s%s%s%s%s", aVar.f(), aVar.getDeviceId(), Long.valueOf(aVar.a()), aVar.e(), aVar.d());
            if (!i.m99a(str)) {
                Adler32 adler32 = new Adler32();
                adler32.reset();
                adler32.update(str.getBytes());
                return adler32.getValue();
            }
        }
        return 0L;
    }

    private static a a(Context context) {
        if (context != null) {
            new a();
            synchronized (e) {
                String value = c.a(context).getValue();
                if (!i.m99a(value)) {
                    String strSubstring = value.endsWith("\n") ? value.substring(0, value.length() - 1) : value;
                    a aVar = new a();
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String strA = g.a(context);
                    String strB = g.b(context);
                    aVar.d(strA);
                    aVar.b(strA);
                    aVar.b(jCurrentTimeMillis);
                    aVar.c(strB);
                    aVar.e(strSubstring);
                    aVar.a(a(aVar));
                    return aVar;
                }
            }
        }
        return null;
    }

    public static synchronized a b(Context context) {
        a aVarA;
        if (a != null) {
            aVarA = a;
        } else if (context != null) {
            aVarA = a(context);
            a = aVarA;
        } else {
            aVarA = null;
        }
        return aVarA;
    }
}
