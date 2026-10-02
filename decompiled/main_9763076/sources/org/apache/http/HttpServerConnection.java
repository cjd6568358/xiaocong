package org.apache.http;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface HttpServerConnection extends HttpConnection {
    void flush() throws IOException;

    void receiveRequestEntity(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws HttpException, IOException;

    HttpRequest receiveRequestHeader() throws HttpException, IOException;

    void sendResponseEntity(HttpResponse httpResponse) throws HttpException, IOException;

    void sendResponseHeader(HttpResponse httpResponse) throws HttpException, IOException;
}
