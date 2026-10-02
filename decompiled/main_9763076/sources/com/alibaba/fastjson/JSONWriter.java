package com.alibaba.fastjson;

import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONWriter implements Closeable, Flushable {
    private SerializeWriter writer;

    @Override // java.io.Flushable
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.writer.close();
    }
}
