package com.baidu.uaq.agent.android;

/* JADX INFO: compiled from: NullAgentImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e implements b {
    public static final e o = new e();

    @Override // com.baidu.uaq.agent.android.b
    public void start() {
    }

    @Override // com.baidu.uaq.agent.android.b
    public void shutdown() {
    }

    @Override // com.baidu.uaq.agent.android.b
    public String g() {
        return "NULL";
    }

    @Override // com.baidu.uaq.agent.android.b
    public String h() {
        return "unknown";
    }

    @Override // com.baidu.uaq.agent.android.b
    public com.baidu.uaq.agent.android.harvest.bean.c e() {
        com.baidu.uaq.agent.android.harvest.bean.c devInfo = new com.baidu.uaq.agent.android.harvest.bean.c();
        devInfo.g("Android");
        devInfo.h("2.3");
        devInfo.i("Fake");
        devInfo.j("NullAgent");
        devInfo.k("AndroidAgent");
        devInfo.l("2.123");
        devInfo.m("389C9738-A761-44DE-8A66-1668CFD67DA1");
        devInfo.o("Fake Arch");
        devInfo.p("1.7.0");
        devInfo.q("Fake Size");
        devInfo.n("a.b.c");
        return devInfo;
    }

    @Override // com.baidu.uaq.agent.android.b
    public com.baidu.uaq.agent.android.harvest.bean.a f() {
        return new com.baidu.uaq.agent.android.harvest.bean.a("null", "0.0", "null");
    }

    @Override // com.baidu.uaq.agent.android.b
    public com.baidu.uaq.agent.android.harvest.bean.d i() {
        return new com.baidu.uaq.agent.android.harvest.bean.d(0L, 1, "none", "none", new long[]{0, 0});
    }
}
