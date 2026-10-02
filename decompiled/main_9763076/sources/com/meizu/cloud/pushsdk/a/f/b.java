package com.meizu.cloud.pushsdk.a.f;

import com.meizu.cloud.pushsdk.a.d.i;
import com.meizu.cloud.pushsdk.a.d.k;
import java.io.File;
import java.io.IOException;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b {
    public static String a = null;

    public static k a(com.meizu.cloud.pushsdk.a.a.b bVar) throws com.meizu.cloud.pushsdk.a.c.a {
        try {
            i.a aVarA = new i.a().a(bVar.e());
            a(aVarA, bVar);
            switch (bVar.c()) {
                case 0:
                    aVarA = aVarA.a();
                    break;
                case 1:
                    aVarA = aVarA.a(bVar.q());
                    break;
                case 2:
                    aVarA = aVarA.c(bVar.q());
                    break;
                case 3:
                    aVarA = aVarA.b(bVar.q());
                    break;
                case 4:
                    aVarA = aVarA.b();
                    break;
                case 5:
                    aVarA = aVarA.d(bVar.q());
                    break;
            }
            bVar.a(new com.meizu.cloud.pushsdk.a.d.e(aVarA.c()));
            return bVar.n().a();
        } catch (IOException e) {
            throw new com.meizu.cloud.pushsdk.a.c.a(e);
        }
    }

    public static k b(com.meizu.cloud.pushsdk.a.a.b bVar) throws com.meizu.cloud.pushsdk.a.c.a {
        try {
            i.a aVarA = new i.a().a(bVar.e());
            a(aVarA, bVar);
            bVar.a(new com.meizu.cloud.pushsdk.a.d.e(aVarA.a().c()));
            k kVarA = bVar.n().a();
            com.meizu.cloud.pushsdk.a.i.b.a(kVarA, bVar.l(), bVar.m());
            return kVarA;
        } catch (IOException e) {
            try {
                File file = new File(bVar.l() + File.separator + bVar.m());
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            throw new com.meizu.cloud.pushsdk.a.c.a(e);
        }
    }

    public static k c(com.meizu.cloud.pushsdk.a.a.b bVar) throws com.meizu.cloud.pushsdk.a.c.a {
        try {
            i.a aVarA = new i.a().a(bVar.e());
            a(aVarA, bVar);
            bVar.a(new com.meizu.cloud.pushsdk.a.d.e(aVarA.a(new d(bVar.r(), bVar.k())).c()));
            return bVar.n().a();
        } catch (IOException e) {
            throw new com.meizu.cloud.pushsdk.a.c.a(e);
        }
    }

    public static void a(i.a aVar, com.meizu.cloud.pushsdk.a.a.b bVar) {
        if (bVar.i() != null) {
            aVar.a(HTTP.USER_AGENT, bVar.i());
        } else if (a != null) {
            bVar.a(a);
            aVar.a(HTTP.USER_AGENT, a);
        }
        com.meizu.cloud.pushsdk.a.d.c cVarS = bVar.s();
        if (cVarS != null) {
            aVar.a(cVarS);
            if (bVar.i() != null && !cVarS.b().contains(HTTP.USER_AGENT)) {
                aVar.a(HTTP.USER_AGENT, bVar.i());
            }
        }
    }
}
