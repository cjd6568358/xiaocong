package com.huawei.hms.support.api.entity.core;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.a.a;
import com.huawei.hms.support.api.entity.auth.Scope;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DisconnectInfo implements IMessageEntity {

    @a
    public List<String> apiNameList;

    @a
    public List<Scope> scopeList;

    public DisconnectInfo() {
    }

    public DisconnectInfo(List<Scope> list, List<String> list2) {
        this.scopeList = list;
        this.apiNameList = list2;
    }

    public List<String> getApiNameList() {
        return this.apiNameList;
    }

    public List<Scope> getScopeList() {
        return this.scopeList;
    }
}
