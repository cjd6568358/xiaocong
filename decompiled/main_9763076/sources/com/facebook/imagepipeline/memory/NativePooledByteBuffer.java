package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativePooledByteBuffer implements PooledByteBuffer {
    CloseableReference<NativeMemoryChunk> mBufRef;
    private final int mSize;

    public NativePooledByteBuffer(CloseableReference<NativeMemoryChunk> bufRef, int size) {
        Preconditions.checkNotNull(bufRef);
        Preconditions.checkArgument(size >= 0 && size <= bufRef.get().getSize());
        this.mBufRef = bufRef.m60clone();
        this.mSize = size;
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBuffer
    public synchronized int size() {
        ensureValid();
        return this.mSize;
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBuffer
    public synchronized byte read(int offset) {
        byte b;
        synchronized (this) {
            ensureValid();
            Preconditions.checkArgument(offset >= 0);
            Preconditions.checkArgument(offset < this.mSize);
            b = this.mBufRef.get().read(offset);
        }
        return b;
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBuffer
    public synchronized void read(int offset, byte[] buffer, int bufferOffset, int length) {
        ensureValid();
        Preconditions.checkArgument(offset + length <= this.mSize);
        this.mBufRef.get().read(offset, buffer, bufferOffset, length);
    }

    @Override // com.facebook.imagepipeline.memory.PooledByteBuffer
    public synchronized boolean isClosed() {
        return !CloseableReference.isValid(this.mBufRef);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        CloseableReference.closeSafely(this.mBufRef);
        this.mBufRef = null;
    }

    synchronized void ensureValid() {
        if (isClosed()) {
            throw new PooledByteBuffer.ClosedException();
        }
    }
}
