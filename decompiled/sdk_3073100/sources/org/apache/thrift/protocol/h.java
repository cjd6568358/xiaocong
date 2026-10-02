package org.apache.thrift.protocol;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class h {
    private static int a = Integer.MAX_VALUE;

    public static void a(e eVar, byte b) throws org.apache.thrift.f {
        a(eVar, b, a);
    }

    public static void a(e eVar, byte b, int i) throws org.apache.thrift.f {
        int i2 = 0;
        if (i <= 0) {
            throw new org.apache.thrift.f("Maximum skip depth exceeded");
        }
        switch (b) {
            case 2:
                eVar.q();
                return;
            case 3:
                eVar.r();
                return;
            case 4:
                eVar.v();
                return;
            case 5:
            case 7:
            case 9:
            default:
                return;
            case 6:
                eVar.s();
                return;
            case 8:
                eVar.t();
                return;
            case 10:
                eVar.u();
                return;
            case 11:
                eVar.x();
                return;
            case 12:
                eVar.g();
                while (true) {
                    b bVarI = eVar.i();
                    if (bVarI.b == 0) {
                        eVar.h();
                        return;
                    } else {
                        a(eVar, bVarI.b, i - 1);
                        eVar.j();
                    }
                }
                break;
            case 13:
                d dVarK = eVar.k();
                while (i2 < dVarK.c) {
                    a(eVar, dVarK.a, i - 1);
                    a(eVar, dVarK.b, i - 1);
                    i2++;
                }
                eVar.l();
                return;
            case 14:
                i iVarO = eVar.o();
                while (i2 < iVarO.b) {
                    a(eVar, iVarO.a, i - 1);
                    i2++;
                }
                eVar.p();
                return;
            case 15:
                c cVarM = eVar.m();
                while (i2 < cVarM.b) {
                    a(eVar, cVarM.a, i - 1);
                    i2++;
                }
                eVar.n();
                return;
        }
    }
}
