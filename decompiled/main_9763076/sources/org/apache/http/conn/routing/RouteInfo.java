package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface RouteInfo {
    int getHopCount();

    HttpHost getHopTarget(int i);

    LayerType getLayerType();

    InetAddress getLocalAddress();

    HttpHost getProxyHost();

    HttpHost getTargetHost();

    TunnelType getTunnelType();

    boolean isLayered();

    boolean isSecure();

    boolean isTunnelled();
}
