package org.apache.http.entity;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public interface ContentProducer {
    void writeTo(OutputStream outputStream) throws IOException;
}
