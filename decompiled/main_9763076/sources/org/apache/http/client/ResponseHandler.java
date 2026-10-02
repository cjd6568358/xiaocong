package org.apache.http.client;

import java.io.IOException;
import org.apache.http.HttpResponse;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface ResponseHandler<T> {
    T handleResponse(HttpResponse httpResponse) throws IOException;
}
