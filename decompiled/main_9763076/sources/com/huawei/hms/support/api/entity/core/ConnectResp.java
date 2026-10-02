package com.huawei.hms.support.api.entity.core;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.a.a;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ConnectResp implements IMessageEntity {

    @a
    public List<Integer> protocolVersion = Arrays.asList(1, 2);

    @a
    public String sessionId;

    public String toString() {
        StringBuilder sb = new StringBuilder("protocol version:");
        Iterator<Integer> it = this.protocolVersion.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(',');
        }
        return sb.toString();
    }
}
