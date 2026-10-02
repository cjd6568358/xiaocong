package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Throwables;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativePooledByteBufferFactory implements PooledByteBufferFactory {
    private final NativeMemoryChunkPool mPool;
    private final PooledByteStreams mPooledByteStreams;

    public NativePooledByteBufferFactory(NativeMemoryChunkPool pool, PooledByteStreams pooledByteStreams) {
        this.mPool = pool;
        this.mPooledByteStreams = pooledByteStreams;
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBufferFactory
    public NativePooledByteBuffer newByteBuffer(InputStream inputStream) throws Throwable {
        NativePooledByteBufferOutputStream outputStream = new NativePooledByteBufferOutputStream(this.mPool);
        try {
            return newByteBuf(inputStream, outputStream);
        } finally {
            outputStream.close();
        }
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBufferFactory
    public NativePooledByteBuffer newByteBuffer(byte[] bytes) throws Throwable {
        NativePooledByteBufferOutputStream outputStream = new NativePooledByteBufferOutputStream(this.mPool, bytes.length);
        try {
            try {
                outputStream.write(bytes, 0, bytes.length);
                NativePooledByteBuffer byteBuffer = outputStream.toByteBuffer();
                outputStream.close();
                return byteBuffer;
            } catch (IOException ioe) {
                throw Throwables.propagate(ioe);
            }
        } catch (Throwable th) {
            outputStream.close();
            throw th;
        }
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBufferFactory
    public NativePooledByteBuffer newByteBuffer(InputStream inputStream, int initialCapacity) throws Throwable {
        NativePooledByteBufferOutputStream outputStream = new NativePooledByteBufferOutputStream(this.mPool, initialCapacity);
        try {
            return newByteBuf(inputStream, outputStream);
        } finally {
            outputStream.close();
        }
    }

    NativePooledByteBuffer newByteBuf(InputStream inputStream, NativePooledByteBufferOutputStream outputStream) throws IOException {
        this.mPooledByteStreams.copy(inputStream, outputStream);
        return outputStream.toByteBuffer();
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBufferFactory
    public NativePooledByteBufferOutputStream newOutputStream() {
        return new NativePooledByteBufferOutputStream(this.mPool);
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBufferFactory
    public NativePooledByteBufferOutputStream newOutputStream(int initialCapacity) {
        return new NativePooledByteBufferOutputStream(this.mPool, initialCapacity);
    }
}
