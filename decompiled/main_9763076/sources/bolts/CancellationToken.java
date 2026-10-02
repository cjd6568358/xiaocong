package bolts;

import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CancellationToken {
    private final CancellationTokenSource tokenSource;

    public boolean isCancellationRequested() {
        return this.tokenSource.isCancellationRequested();
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(this.tokenSource.isCancellationRequested()));
    }
}
