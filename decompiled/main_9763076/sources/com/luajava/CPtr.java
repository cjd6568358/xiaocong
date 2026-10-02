package com.luajava;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CPtr {
    private long peer;

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return CPtr.class == obj.getClass() && this.peer == ((CPtr) obj).peer;
    }

    protected long getPeer() {
        return this.peer;
    }

    CPtr() {
    }
}
