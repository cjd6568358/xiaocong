package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface HostNameResolver {
    InetAddress resolve(String str) throws IOException;
}
