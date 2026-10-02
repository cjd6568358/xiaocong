package com.facebook.imagepipeline.memory;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface PooledByteBufferFactory {
    PooledByteBuffer newByteBuffer(InputStream inputStream) throws IOException;

    PooledByteBuffer newByteBuffer(InputStream inputStream, int i) throws IOException;

    PooledByteBuffer newByteBuffer(byte[] bArr);

    PooledByteBufferOutputStream newOutputStream();

    PooledByteBufferOutputStream newOutputStream(int i);
}
