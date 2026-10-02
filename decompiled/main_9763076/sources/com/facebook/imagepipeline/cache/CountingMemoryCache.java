package com.facebook.imagepipeline.cache;

import android.os.SystemClock;
import com.android.internal.util.Predicate;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CountingMemoryCache<K, V> implements MemoryTrimmable, MemoryCache<K, V> {
    static final long PARAMS_INTERCHECK_INTERVAL_MS = TimeUnit.MINUTES.toMillis(5);
    private final CacheTrimStrategy mCacheTrimStrategy;
    final CountingLruMap<K, Entry<K, V>> mCachedEntries;
    final CountingLruMap<K, Entry<K, V>> mExclusiveEntries;
    private long mLastCacheParamsCheck = SystemClock.uptimeMillis();
    protected MemoryCacheParams mMemoryCacheParams;
    private final Supplier<MemoryCacheParams> mMemoryCacheParamsSupplier;
    private final ValueDescriptor<V> mValueDescriptor;

    public interface CacheTrimStrategy {
    }

    public interface EntryStateObserver<K> {
        void onExclusivityChanged(K k, boolean z);
    }

    static class Entry<K, V> {
        public int clientCount = 0;
        public boolean isOrphan = false;
        public final K key;
        public final EntryStateObserver<K> observer;
        public final CloseableReference<V> valueRef;

        private Entry(K k, CloseableReference<V> closeableReference, EntryStateObserver<K> entryStateObserver) {
            this.key = (K) Preconditions.checkNotNull(k);
            this.valueRef = (CloseableReference) Preconditions.checkNotNull(CloseableReference.cloneOrNull(closeableReference));
            this.observer = entryStateObserver;
        }

        static <K, V> Entry<K, V> of(K key, CloseableReference<V> valueRef, EntryStateObserver<K> observer) {
            return new Entry<>(key, valueRef, observer);
        }
    }

    public CountingMemoryCache(ValueDescriptor<V> valueDescriptor, CacheTrimStrategy cacheTrimStrategy, Supplier<MemoryCacheParams> memoryCacheParamsSupplier) {
        this.mValueDescriptor = valueDescriptor;
        this.mExclusiveEntries = new CountingLruMap<>(wrapValueDescriptor(valueDescriptor));
        this.mCachedEntries = new CountingLruMap<>(wrapValueDescriptor(valueDescriptor));
        this.mCacheTrimStrategy = cacheTrimStrategy;
        this.mMemoryCacheParamsSupplier = memoryCacheParamsSupplier;
        this.mMemoryCacheParams = this.mMemoryCacheParamsSupplier.get();
    }

    private ValueDescriptor<Entry<K, V>> wrapValueDescriptor(final ValueDescriptor<V> evictableValueDescriptor) {
        return new ValueDescriptor<Entry<K, V>>() { // from class: com.facebook.imagepipeline.cache.CountingMemoryCache.1
            @Override // com.facebook.imagepipeline.cache.ValueDescriptor
            public int getSizeInBytes(Entry<K, V> entry) {
                return evictableValueDescriptor.getSizeInBytes(entry.valueRef.get());
            }
        };
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public CloseableReference<V> cache(K key, CloseableReference<V> valueRef) {
        return cache(key, valueRef, null);
    }

    public CloseableReference<V> cache(K key, CloseableReference<V> valueRef, EntryStateObserver<K> observer) {
        Entry<K, V> oldExclusive;
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(valueRef);
        maybeUpdateCacheParams();
        CloseableReference<V> oldRefToClose = null;
        CloseableReference<V> clientRef = null;
        synchronized (this) {
            oldExclusive = this.mExclusiveEntries.remove(key);
            Entry<K, V> oldEntry = this.mCachedEntries.remove(key);
            if (oldEntry != null) {
                makeOrphan(oldEntry);
                oldRefToClose = referenceToClose(oldEntry);
            }
            if (canCacheNewValue(valueRef.get())) {
                Entry<K, V> newEntry = Entry.of(key, valueRef, observer);
                this.mCachedEntries.put(key, newEntry);
                clientRef = newClientReference(newEntry);
            }
        }
        CloseableReference.closeSafely(oldRefToClose);
        maybeNotifyExclusiveEntryRemoval(oldExclusive);
        maybeEvictEntries();
        return clientRef;
    }

    private synchronized boolean canCacheNewValue(V value) {
        int newValueSize;
        newValueSize = this.mValueDescriptor.getSizeInBytes(value);
        return newValueSize <= this.mMemoryCacheParams.maxCacheEntrySize && getInUseCount() <= this.mMemoryCacheParams.maxCacheEntries + (-1) && getInUseSizeInBytes() <= this.mMemoryCacheParams.maxCacheSize - newValueSize;
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public CloseableReference<V> get(K key) {
        Entry<K, V> oldExclusive;
        Preconditions.checkNotNull(key);
        CloseableReference<V> clientRef = null;
        synchronized (this) {
            oldExclusive = this.mExclusiveEntries.remove(key);
            Entry<K, V> entry = this.mCachedEntries.get(key);
            if (entry != null) {
                clientRef = newClientReference(entry);
            }
        }
        maybeNotifyExclusiveEntryRemoval(oldExclusive);
        maybeUpdateCacheParams();
        maybeEvictEntries();
        return clientRef;
    }

    private synchronized CloseableReference<V> newClientReference(final Entry<K, V> entry) {
        increaseClientCount(entry);
        return CloseableReference.of(entry.valueRef.get(), new ResourceReleaser<V>() { // from class: com.facebook.imagepipeline.cache.CountingMemoryCache.2
            @Override // com.facebook.common.references.ResourceReleaser
            public void release(V unused) {
                CountingMemoryCache.this.releaseClientReference(entry);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseClientReference(Entry<K, V> entry) {
        boolean isExclusiveAdded;
        CloseableReference<V> oldRefToClose;
        Preconditions.checkNotNull(entry);
        synchronized (this) {
            decreaseClientCount(entry);
            isExclusiveAdded = maybeAddToExclusives(entry);
            oldRefToClose = referenceToClose(entry);
        }
        CloseableReference.closeSafely(oldRefToClose);
        if (!isExclusiveAdded) {
            entry = null;
        }
        maybeNotifyExclusiveEntryInsertion(entry);
        maybeUpdateCacheParams();
        maybeEvictEntries();
    }

    private synchronized boolean maybeAddToExclusives(Entry<K, V> entry) {
        boolean z;
        if (entry.isOrphan || entry.clientCount != 0) {
            z = false;
        } else {
            this.mExclusiveEntries.put(entry.key, entry);
            z = true;
        }
        return z;
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public int removeAll(Predicate<K> predicate) {
        ArrayList<Entry<K, V>> oldExclusives;
        ArrayList<Entry<K, V>> oldEntries;
        synchronized (this) {
            oldExclusives = this.mExclusiveEntries.removeAll(predicate);
            oldEntries = this.mCachedEntries.removeAll(predicate);
            makeOrphans(oldEntries);
        }
        maybeClose(oldEntries);
        maybeNotifyExclusiveEntryRemoval(oldExclusives);
        maybeUpdateCacheParams();
        maybeEvictEntries();
        return oldEntries.size();
    }

    @Override // com.facebook.imagepipeline.cache.MemoryCache
    public synchronized boolean contains(Predicate<K> predicate) {
        return !this.mCachedEntries.getMatchingEntries(predicate).isEmpty();
    }

    private synchronized void maybeUpdateCacheParams() {
        if (this.mLastCacheParamsCheck + PARAMS_INTERCHECK_INTERVAL_MS <= SystemClock.uptimeMillis()) {
            this.mLastCacheParamsCheck = SystemClock.uptimeMillis();
            this.mMemoryCacheParams = this.mMemoryCacheParamsSupplier.get();
        }
    }

    private void maybeEvictEntries() {
        ArrayList<Entry<K, V>> oldEntries;
        synchronized (this) {
            int maxCount = Math.min(this.mMemoryCacheParams.maxEvictionQueueEntries, this.mMemoryCacheParams.maxCacheEntries - getInUseCount());
            int maxSize = Math.min(this.mMemoryCacheParams.maxEvictionQueueSize, this.mMemoryCacheParams.maxCacheSize - getInUseSizeInBytes());
            oldEntries = trimExclusivelyOwnedEntries(maxCount, maxSize);
            makeOrphans(oldEntries);
        }
        maybeClose(oldEntries);
        maybeNotifyExclusiveEntryRemoval(oldEntries);
    }

    private synchronized ArrayList<Entry<K, V>> trimExclusivelyOwnedEntries(int count, int size) {
        ArrayList<Entry<K, V>> oldEntries;
        int count2 = Math.max(count, 0);
        int size2 = Math.max(size, 0);
        if (this.mExclusiveEntries.getCount() <= count2 && this.mExclusiveEntries.getSizeInBytes() <= size2) {
            oldEntries = null;
        } else {
            oldEntries = new ArrayList<>();
            while (true) {
                if (this.mExclusiveEntries.getCount() <= count2 && this.mExclusiveEntries.getSizeInBytes() <= size2) {
                    break;
                }
                K key = this.mExclusiveEntries.getFirstKey();
                this.mExclusiveEntries.remove(key);
                oldEntries.add(this.mCachedEntries.remove(key));
            }
        }
        return oldEntries;
    }

    private void maybeClose(ArrayList<Entry<K, V>> oldEntries) {
        if (oldEntries != null) {
            for (Entry<K, V> oldEntry : oldEntries) {
                CloseableReference.closeSafely(referenceToClose(oldEntry));
            }
        }
    }

    private void maybeNotifyExclusiveEntryRemoval(ArrayList<Entry<K, V>> entries) {
        if (entries != null) {
            for (Entry<K, V> entry : entries) {
                maybeNotifyExclusiveEntryRemoval(entry);
            }
        }
    }

    private static <K, V> void maybeNotifyExclusiveEntryRemoval(Entry<K, V> entry) {
        if (entry != null && entry.observer != null) {
            entry.observer.onExclusivityChanged(entry.key, false);
        }
    }

    private static <K, V> void maybeNotifyExclusiveEntryInsertion(Entry<K, V> entry) {
        if (entry != null && entry.observer != null) {
            entry.observer.onExclusivityChanged(entry.key, true);
        }
    }

    private synchronized void makeOrphans(ArrayList<Entry<K, V>> oldEntries) {
        if (oldEntries != null) {
            for (Entry<K, V> oldEntry : oldEntries) {
                makeOrphan(oldEntry);
            }
        }
    }

    private synchronized void makeOrphan(Entry<K, V> entry) {
        synchronized (this) {
            Preconditions.checkNotNull(entry);
            Preconditions.checkState(entry.isOrphan ? false : true);
            entry.isOrphan = true;
        }
    }

    private synchronized void increaseClientCount(Entry<K, V> entry) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkState(!entry.isOrphan);
        entry.clientCount++;
    }

    private synchronized void decreaseClientCount(Entry<K, V> entry) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkState(entry.clientCount > 0);
        entry.clientCount--;
    }

    private synchronized CloseableReference<V> referenceToClose(Entry<K, V> entry) {
        Preconditions.checkNotNull(entry);
        return (entry.isOrphan && entry.clientCount == 0) ? entry.valueRef : null;
    }

    public synchronized int getInUseCount() {
        return this.mCachedEntries.getCount() - this.mExclusiveEntries.getCount();
    }

    public synchronized int getInUseSizeInBytes() {
        return this.mCachedEntries.getSizeInBytes() - this.mExclusiveEntries.getSizeInBytes();
    }
}
