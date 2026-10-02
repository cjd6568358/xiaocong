package com.huawei.hms.support.api;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.transport.DatagramTransport;

/* JADX INFO: compiled from: PendingResultImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements DatagramTransport.a {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
    public void a(int i, IMessageEntity iMessageEntity) {
        this.a.a(i, iMessageEntity);
        this.a.a.countDown();
    }
}
