package com.huawei.hms.update.a;

import android.content.Context;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: compiled from: ThreadWrapper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i implements com.huawei.hms.update.a.a.a {
    private static final Executor b = Executors.newSingleThreadExecutor();
    private final com.huawei.hms.update.a.a.a a;

    public i(com.huawei.hms.update.a.a.a aVar) {
        com.huawei.hms.c.a.a(aVar, "update must not be null.");
        this.a = aVar;
    }

    @Override // com.huawei.hms.update.a.a.a
    public Context a() {
        return this.a.a();
    }

    @Override // com.huawei.hms.update.a.a.a
    public void b() {
        this.a.b();
    }

    @Override // com.huawei.hms.update.a.a.a
    public void a(com.huawei.hms.update.a.a.b bVar) {
        b.execute(new j(this, bVar));
    }

    @Override // com.huawei.hms.update.a.a.a
    public void a(com.huawei.hms.update.a.a.b bVar, com.huawei.hms.update.a.a.c cVar) {
        b.execute(new k(this, bVar, cVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.huawei.hms.update.a.a.b c(com.huawei.hms.update.a.a.b bVar) {
        return new l(bVar);
    }
}
