package com.tencent.wxop.stat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class at implements h {
    final /* synthetic */ aq a;

    at(aq aqVar) {
        this.a = aqVar;
    }

    @Override // com.tencent.wxop.stat.h
    public void a() {
        StatServiceImpl.c();
        if (au.b().a > 0) {
            StatServiceImpl.commitEvents(this.a.d, -1);
        }
    }

    @Override // com.tencent.wxop.stat.h
    public void b() {
        au.b().a(this.a.a, (h) null, this.a.c, true);
        StatServiceImpl.d();
    }
}
