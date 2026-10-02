package org.apache.http.conn;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface ConnectionReleaseTrigger {
    void abortConnection() throws IOException;

    void releaseConnection() throws IOException;
}
