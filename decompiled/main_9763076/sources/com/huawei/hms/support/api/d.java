package com.huawei.hms.support.api;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.transport.DatagramTransport;

/* JADX INFO: compiled from: PendingResultImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements DatagramTransport.a {
    final /* synthetic */ a.HandlerC0019a a;
    final /* synthetic */ ResultCallback b;
    final /* synthetic */ a c;

    d(a aVar, a.HandlerC0019a handlerC0019a, ResultCallback resultCallback) {
        this.c = aVar;
        this.a = handlerC0019a;
        this.b = resultCallback;
    }

    @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
    public void a(int i, IMessageEntity iMessageEntity) {
        this.c.a(i, iMessageEntity);
        this.a.a(this.b, this.c.b);
    }
}
