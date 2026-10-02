package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface EofSensorWatcher {
    boolean eofDetected(InputStream inputStream) throws IOException;

    boolean streamAbort(InputStream inputStream) throws IOException;

    boolean streamClosed(InputStream inputStream) throws IOException;
}
