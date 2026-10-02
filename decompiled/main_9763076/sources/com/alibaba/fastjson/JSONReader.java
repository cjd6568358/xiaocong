package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.io.Closeable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONReader implements Closeable {
    private final DefaultJSONParser parser;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.parser.close();
    }
}
