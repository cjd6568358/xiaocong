package com.hzy.tvmao.b;

/* JADX INFO: compiled from: IRDateControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends a {
    public String b = "TestIrCodeControl_getremoteids";
    public String c = "TestIrCodeControl_getRemoteIdData";
    public String d = "TestIrCodeControl_first";
    public String e = "TestIrCodeControl_other";
    public String f = "TestIrCodeControl_stb";
    public String g = "TestIrCodeControl_iptv";
    public String h = "TestIrCodeControl_post";
    public final String i = "ACMatchCodeControl";

    public void a(int i, int i2, int i3, int i4, String str, a.c cVar) {
        new g(this, this, cVar, this.b, i, i2, i3, i4, str).a();
    }

    public void a(String str, int i, String str2, boolean z, boolean z2, a.c cVar) {
        new i(this, this, cVar, this.b, str, i, str2, z, z2).a();
    }

    public void a(int i, int i2, String str, a.c cVar) {
        new j(this, this, cVar, this.b, i, i2, str).a();
    }

    public void a(int i, a.c cVar) {
        new k(this, this, cVar, "ACMatchCodeControl", i).a();
    }

    public void a(int i, int i2, a.c cVar) {
        new h(this, this, cVar, "getTVPowerTestKeys", i, i2).a();
    }
}
