package org.apache.http.client.methods;

import java.net.URI;
import org.apache.http.HttpRequest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HttpUriRequest extends HttpRequest {
    void abort() throws UnsupportedOperationException;

    String getMethod();

    URI getURI();

    boolean isAborted();
}
