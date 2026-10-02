package com.tencent.mm.opensdk.diffdev.a;

import com.tencent.mm.opensdk.diffdev.OAuthListener;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class c implements Runnable {
    final /* synthetic */ b ac;

    c(b bVar) {
        this.ac = bVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.ac.ab.Y);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((OAuthListener) it.next()).onQrcodeScanned();
        }
    }
}
