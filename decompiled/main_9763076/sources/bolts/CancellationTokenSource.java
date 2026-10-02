package bolts;

import java.io.Closeable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ScheduledFuture;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CancellationTokenSource implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final Object lock;
    private final List<CancellationTokenRegistration> registrations;
    private ScheduledFuture<?> scheduledCancellation;

    public boolean isCancellationRequested() {
        boolean z;
        synchronized (this.lock) {
            throwIfClosed();
            z = this.cancellationRequested;
        }
        return z;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this.lock) {
            if (!this.closed) {
                cancelScheduledCancellation();
                for (CancellationTokenRegistration registration : this.registrations) {
                    registration.close();
                }
                this.registrations.clear();
                this.closed = true;
            }
        }
    }

    void unregister(CancellationTokenRegistration registration) {
        synchronized (this.lock) {
            throwIfClosed();
            this.registrations.remove(registration);
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(isCancellationRequested()));
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void cancelScheduledCancellation() {
        if (this.scheduledCancellation != null) {
            this.scheduledCancellation.cancel(true);
            this.scheduledCancellation = null;
        }
    }
}
