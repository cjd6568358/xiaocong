package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.routing.HttpRoute;

/* JADX INFO: Access modifiers changed from: protected */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SingleClientConnManager$PoolEntry extends AbstractPoolEntry {
    final /* synthetic */ SingleClientConnManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected SingleClientConnManager$PoolEntry(SingleClientConnManager singleClientConnManager) {
        super((ClientConnectionOperator) null, (HttpRoute) null);
        this.this$0 = singleClientConnManager;
        throw new RuntimeException("Stub!");
    }

    protected void close() throws IOException {
        throw new RuntimeException("Stub!");
    }

    protected void shutdown() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
