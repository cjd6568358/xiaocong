package com.xiaomi.network;

import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class b extends Fallback {
    Fallback i;
    final /* synthetic */ Fallback j;
    final /* synthetic */ HostManager k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    b(HostManager hostManager, String str, Fallback fallback) {
        super(str);
        this.k = hostManager;
        this.j = fallback;
        this.i = this.j;
        this.b = this.b;
        if (this.j != null) {
            this.f = this.j.f;
        }
    }

    @Override // com.xiaomi.network.Fallback
    public synchronized ArrayList<String> a(boolean z) {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        if (this.i != null) {
            arrayList.addAll(this.i.a(true));
        }
        synchronized (HostManager.mReservedHosts) {
            ArrayList<String> arrayList2 = HostManager.mReservedHosts.get(this.b);
            if (arrayList2 != null) {
                ArrayList<String> arrayListA = this.i != null ? this.i.a(false) : arrayList;
                for (String str : arrayList2) {
                    if (arrayListA.indexOf(str) == -1) {
                        arrayList.add(str);
                    }
                }
                arrayList.remove(this.b);
                arrayList.add(this.b);
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.network.Fallback
    public synchronized void a(String str, AccessHistory accessHistory) {
        if (this.i != null) {
            this.i.a(str, accessHistory);
        }
    }

    @Override // com.xiaomi.network.Fallback
    public boolean b() {
        return false;
    }
}
