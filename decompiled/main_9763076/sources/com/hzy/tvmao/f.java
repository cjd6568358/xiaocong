package com.hzy.tvmao;

import com.hzy.tvmao.interf.IRequestResult;

/* JADX INFO: compiled from: KookongSDK.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f implements com.hzy.tvmao.b.a.c {
    private final /* synthetic */ IRequestResult a;

    f(IRequestResult iRequestResult) {
        this.a = iRequestResult;
    }

    @Override // com.hzy.tvmao.b.a.c
    public void a(com.hzy.tvmao.b.a.a aVar) {
        KookongSDK.parseControlResponseBean(aVar, this.a);
    }
}
