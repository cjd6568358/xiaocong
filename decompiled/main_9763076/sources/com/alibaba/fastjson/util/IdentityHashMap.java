package com.alibaba.fastjson.util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IdentityHashMap<K, V> {
    private final Entry<K, V>[] buckets;
    private final int indexMask;

    public IdentityHashMap() {
        this(8192);
    }

    public IdentityHashMap(int tableSize) {
        this.indexMask = tableSize - 1;
        this.buckets = new Entry[tableSize];
    }

    public final V get(K key) {
        int hash = System.identityHashCode(key);
        int bucket = hash & this.indexMask;
        for (Entry<K, V> entry = this.buckets[bucket]; entry != null; entry = entry.next) {
            if (key == entry.key) {
                return entry.value;
            }
        }
        return null;
    }

    public Class findClass(String keyString) {
        for (int i = 0; i < this.buckets.length; i++) {
            Entry<K, V> entry = this.buckets[i];
            if (entry != null) {
                for (Entry<K, V> entry2 = entry; entry2 != null; entry2 = entry2.next) {
                    Object key = entry.key;
                    if (key instanceof Class) {
                        Class clazz = (Class) key;
                        String className = clazz.getName();
                        if (className.equals(keyString)) {
                            return clazz;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean put(K key, V value) {
        int hash = System.identityHashCode(key);
        int bucket = hash & this.indexMask;
        for (Entry<K, V> entry = this.buckets[bucket]; entry != null; entry = entry.next) {
            if (key == entry.key) {
                entry.value = value;
                return true;
            }
        }
        Entry<K, V> entry2 = new Entry<>(key, value, hash, this.buckets[bucket]);
        this.buckets[bucket] = entry2;
        return false;
    }

    protected static final class Entry<K, V> {
        public final int hashCode;
        public final K key;
        public final Entry<K, V> next;
        public V value;

        public Entry(K key, V value, int hash, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hashCode = hash;
        }
    }
}
