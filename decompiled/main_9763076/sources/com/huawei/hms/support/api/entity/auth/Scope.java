package com.huawei.hms.support.api.entity.auth;

import com.huawei.hms.core.aidl.IMessageEntity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Scope implements IMessageEntity {
    private final String mScopeUri;

    public Scope() {
        this.mScopeUri = null;
    }

    public Scope(String str) {
        this.mScopeUri = str;
    }

    public String getScopeUri() {
        return this.mScopeUri;
    }

    public boolean equeals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Scope)) {
            return false;
        }
        return this.mScopeUri.equals(((Scope) obj).mScopeUri);
    }
}
