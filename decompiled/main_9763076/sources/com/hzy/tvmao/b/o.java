package com.hzy.tvmao.b;

/* JADX INFO: compiled from: OperaterControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class o extends a {
    public static String b = "getAreaID";
    public static String c = "getOperater";
    public static String d = "getAreaIr";
    public static String e = "getLineUps";
    public static String f = "getIPTV";
    public static String g = "getSearchSTB";

    public void a(String str, String str2, String str3, a.c cVar) {
        new p(this, this, cVar, b, str, str2, str3).a();
    }

    public void a(int i, a.c cVar) {
        new q(this, this, cVar, c, i).a();
    }

    public void b(int i, a.c cVar) {
        new r(this, this, cVar, f, i).a();
    }

    public void a(String str, int i, a.c cVar) {
        new s(this, this, cVar, g, str, i).a();
    }

    public void a(int i, int i2, String str, a.c cVar) {
        new t(this, this, cVar, g, i, i2, str).a();
    }
}
