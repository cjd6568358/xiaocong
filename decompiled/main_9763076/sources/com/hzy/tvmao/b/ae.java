package com.hzy.tvmao.b;

/* JADX INFO: compiled from: TVWallDataControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ae extends a {
    public static ae b;
    public static String c = "getProgramData";

    public static ae c() {
        if (b == null) {
            b = new ae();
        }
        return b;
    }

    public void a(int i, String str, a.c cVar) {
        new af(this, this, cVar, c, i, str).a();
    }
}
