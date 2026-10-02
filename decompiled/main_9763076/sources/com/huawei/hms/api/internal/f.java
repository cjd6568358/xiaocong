package com.huawei.hms.api.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.ResponseHeader;
import com.huawei.hms.support.api.transport.DatagramTransport;

/* JADX INFO: compiled from: IPCCallback.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends com.huawei.hms.core.aidl.d.a {
    private final Class<? extends IMessageEntity> a;
    private final DatagramTransport.a b;

    public f(Class<? extends IMessageEntity> cls, DatagramTransport.a aVar) {
        this.a = cls;
        this.b = aVar;
    }

    @Override // com.huawei.hms.core.aidl.d
    public void a(com.huawei.hms.core.aidl.b bVar) throws RemoteException {
        if (bVar == null || TextUtils.isEmpty(bVar.a)) {
            com.huawei.hms.support.log.a.d("IPCCallback", "In call, URI cannot be empty.");
            throw new RemoteException();
        }
        com.huawei.hms.core.aidl.f fVarA = com.huawei.hms.core.aidl.a.a(bVar.c());
        ResponseHeader responseHeader = new ResponseHeader();
        fVarA.a(bVar.b, responseHeader);
        IMessageEntity iMessageEntityA = null;
        if (bVar.b() > 0 && (iMessageEntityA = a()) != null) {
            fVarA.a(bVar.a(), iMessageEntityA);
        }
        this.b.a(responseHeader.getStatusCode(), iMessageEntityA);
    }

    protected IMessageEntity a() {
        if (this.a != null) {
            try {
                return this.a.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                com.huawei.hms.support.log.a.d("IPCCallback", "In newResponseInstance, instancing exception." + e.getMessage());
            }
        }
        return null;
    }
}
