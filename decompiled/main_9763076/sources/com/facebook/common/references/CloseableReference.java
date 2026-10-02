package com.facebook.common.references;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import java.io.Closeable;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class CloseableReference<T> implements Closeable, Cloneable {
    private boolean mIsClosed = false;
    private final SharedReference<T> mSharedReference;
    private static Class<CloseableReference> TAG = CloseableReference.class;
    private static final ResourceReleaser<Closeable> DEFAULT_CLOSEABLE_RELEASER = new ResourceReleaser<Closeable>() { // from class: com.facebook.common.references.CloseableReference.1
        @Override // com.facebook.common.references.ResourceReleaser
        public void release(Closeable value) {
            try {
                Closeables.close(value, true);
            } catch (IOException e) {
            }
        }
    };

    private CloseableReference(SharedReference<T> sharedReference) {
        this.mSharedReference = (SharedReference) Preconditions.checkNotNull(sharedReference);
        sharedReference.addReference();
    }

    private CloseableReference(T t, ResourceReleaser<T> resourceReleaser) {
        this.mSharedReference = new SharedReference<>(t, resourceReleaser);
    }

    /* JADX WARN: Incorrect types in method signature: <T::Ljava/io/Closeable;>(TT;)Lcom/facebook/common/references/CloseableReference<TT;>; */
    public static CloseableReference of(Closeable closeable) {
        if (closeable == null) {
            return null;
        }
        return new CloseableReference(closeable, DEFAULT_CLOSEABLE_RELEASER);
    }

    public static <T> CloseableReference<T> of(T t, ResourceReleaser<T> resourceReleaser) {
        if (t == null) {
            return null;
        }
        return new CloseableReference<>(t, resourceReleaser);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (!this.mIsClosed) {
                this.mIsClosed = true;
                this.mSharedReference.deleteReference();
            }
        }
    }

    public synchronized T get() {
        Preconditions.checkState(!this.mIsClosed);
        return this.mSharedReference.get();
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public synchronized CloseableReference<T> m60clone() {
        Preconditions.checkState(isValid());
        return new CloseableReference<>(this.mSharedReference);
    }

    public synchronized CloseableReference<T> cloneOrNull() {
        return isValid() ? new CloseableReference<>(this.mSharedReference) : null;
    }

    public synchronized boolean isValid() {
        return !this.mIsClosed;
    }

    protected void finalize() throws Throwable {
        try {
            synchronized (this) {
                if (!this.mIsClosed) {
                    FLog.w(TAG, "Finalized without closing: %x %x (type = %s)", Integer.valueOf(System.identityHashCode(this)), Integer.valueOf(System.identityHashCode(this.mSharedReference)), this.mSharedReference.get().getClass().getSimpleName());
                    close();
                    super.finalize();
                    return;
                }
                super.finalize();
            }
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
    }

    public synchronized int getValueHash() {
        return isValid() ? System.identityHashCode(this.mSharedReference.get()) : 0;
    }

    public static boolean isValid(CloseableReference<?> ref) {
        return ref != null && ref.isValid();
    }

    public static <T> CloseableReference<T> cloneOrNull(CloseableReference<T> ref) {
        if (ref != null) {
            return ref.cloneOrNull();
        }
        return null;
    }

    public static void closeSafely(CloseableReference<?> ref) {
        if (ref != null) {
            ref.close();
        }
    }
}
