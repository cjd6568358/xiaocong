package bolts;

import java.io.Closeable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CancellationTokenRegistration implements Closeable {
    private Runnable action;
    private boolean closed;
    private final Object lock;
    private CancellationTokenSource tokenSource;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            if (!this.closed) {
                this.closed = true;
                this.tokenSource.unregister(this);
                this.tokenSource = null;
                this.action = null;
            }
        }
    }
}
