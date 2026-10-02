package okhttp3.internal.cache;

import java.io.IOException;
import okio.Sink;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface CacheRequest {
    void abort();

    Sink body() throws IOException;
}
