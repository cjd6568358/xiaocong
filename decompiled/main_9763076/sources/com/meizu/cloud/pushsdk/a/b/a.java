package com.meizu.cloud.pushsdk.a.b;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends ThreadPoolExecutor {
    a(int i, ThreadFactory threadFactory) {
        super(i, i, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), threadFactory);
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable runnable) {
        C0029a c0029a = new C0029a((com.meizu.cloud.pushsdk.a.f.c) runnable);
        execute(c0029a);
        return c0029a;
    }

    /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.a.b.a$a, reason: collision with other inner class name */
    private static final class C0029a extends FutureTask<com.meizu.cloud.pushsdk.a.f.c> implements Comparable<C0029a> {
        private final com.meizu.cloud.pushsdk.a.f.c a;

        public C0029a(com.meizu.cloud.pushsdk.a.f.c cVar) {
            super(cVar, null);
            this.a = cVar;
        }

        @Override // java.lang.Comparable
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(C0029a c0029a) {
            com.meizu.cloud.pushsdk.a.a.d dVarA = this.a.a();
            com.meizu.cloud.pushsdk.a.a.d dVarA2 = c0029a.a.a();
            return dVarA == dVarA2 ? this.a.a - c0029a.a.a : dVarA2.ordinal() - dVarA.ordinal();
        }
    }
}
