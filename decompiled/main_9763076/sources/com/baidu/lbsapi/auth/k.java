package com.baidu.lbsapi.auth;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k implements e.a<String> {
    final /* synthetic */ String a;
    final /* synthetic */ LBSAuthManager b;

    k(LBSAuthManager lBSAuthManager, String str) {
        this.b = lBSAuthManager;
        this.a = str;
    }

    @Override // com.baidu.lbsapi.auth.e.a
    public void a(String str) {
        this.b.a(str, this.a);
    }
}
