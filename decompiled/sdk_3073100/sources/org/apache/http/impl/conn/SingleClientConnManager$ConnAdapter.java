package org.apache.http.impl.conn;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;

/* JADX INFO: Access modifiers changed from: protected */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SingleClientConnManager$ConnAdapter extends AbstractPooledConnAdapter {
    final /* synthetic */ SingleClientConnManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected SingleClientConnManager$ConnAdapter(SingleClientConnManager singleClientConnManager, SingleClientConnManager$PoolEntry entry, HttpRoute route) {
        super((ClientConnectionManager) null, (AbstractPoolEntry) null);
        this.this$0 = singleClientConnManager;
        throw new RuntimeException("Stub!");
    }
}
