package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class RefQueueWorker implements Runnable {
    protected final RefQueueHandler refHandler;
    protected final ReferenceQueue<?> refQueue;
    protected volatile Thread workerThread;

    public RefQueueWorker(ReferenceQueue<?> queue, RefQueueHandler handler) {
        throw new RuntimeException("Stub!");
    }

    @Override // java.lang.Runnable
    public void run() {
        throw new RuntimeException("Stub!");
    }

    public void shutdown() {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
