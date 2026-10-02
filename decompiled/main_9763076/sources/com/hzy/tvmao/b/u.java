package com.hzy.tvmao.b;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class u extends a {
    public static u b;

    public static u c() {
        if (b == null) {
            b = new u();
        }
        return b;
    }

    public void a(int i, int i2, String str, String str2, a.c cVar) {
        new v(this, this, cVar, "task_getprogramguide", i, i2, str, str2).a();
    }

    public void a(String str, short s, int i, String str2, boolean z, a.c cVar) {
        new w(this, this, cVar, "task_searchchannel", s, str, i, str2, z).a();
    }

    public void a(String str, short s, a.c cVar) {
        new x(this, this, cVar, "accurateSearchProgram", s, str).a();
    }

    public void a(int i, String str, String str2, a.c cVar) {
        new y(this, this, cVar, "task_searchchannel", i, str, str2).a();
    }

    public void a(int i, String str, int i2, a.c cVar) {
        new z(this, this, cVar, "task_searchweek_program", i, str, i2).a();
    }

    public void b(String str, short s, a.c cVar) {
        new aa(this, this, cVar, "task_searchweek_program", str, s).a();
    }

    public void a(int i, String str, a.c cVar) {
        new ab(this, this, cVar, "task_searchprogram", i, str).a();
    }
}
