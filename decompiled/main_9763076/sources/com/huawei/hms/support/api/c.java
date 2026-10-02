package com.huawei.hms.support.api;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.transport.DatagramTransport;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: compiled from: PendingResultImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements DatagramTransport.a {
    final /* synthetic */ AtomicBoolean a;
    final /* synthetic */ a b;

    c(a aVar, AtomicBoolean atomicBoolean) {
        this.b = aVar;
        this.a = atomicBoolean;
    }

    @Override // com.huawei.hms.support.api.transport.DatagramTransport.a
    public void a(int i, IMessageEntity iMessageEntity) {
        if (!this.a.get()) {
            this.b.a(i, iMessageEntity);
        }
        this.b.a.countDown();
    }
}
