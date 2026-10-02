package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class OutputDecorator {
    public abstract OutputStream decorate(IOContext iOContext, OutputStream outputStream) throws IOException;

    public abstract Writer decorate(IOContext iOContext, Writer writer) throws IOException;
}
