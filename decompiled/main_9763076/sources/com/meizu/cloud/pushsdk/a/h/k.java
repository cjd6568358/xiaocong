package com.meizu.cloud.pushsdk.a.h;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface k extends Closeable, Flushable {
    void a(a aVar, long j) throws IOException;

    @Override // java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.a.h.l
    void close() throws IOException;

    void flush() throws IOException;
}
