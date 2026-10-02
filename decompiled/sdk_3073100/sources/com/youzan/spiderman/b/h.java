package com.youzan.spiderman.b;

import com.youzan.spiderman.utils.Logger;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: compiled from: ZanLruCache.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class h<K, V> {
    private static final String i = h.class.getSimpleName();
    private final LinkedHashMap<K, V> a;
    private long b;
    private long c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;

    public h(long maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.c = maxSize;
        this.a = new LinkedHashMap<>(0, 0.75f, true);
        this.b = b();
        Logger.i(i, "size: " + String.valueOf(this.b / 1024), new Object[0]);
    }

    public final LinkedHashMap<K, V> e() {
        return this.a;
    }

    public void a(LinkedHashMap<K, V> map) {
        if (map != null) {
            synchronized (this) {
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    K key = entry.getKey();
                    V value = entry.getValue();
                    this.d++;
                    this.b += c(key, value);
                    V previous = this.a.put(key, value);
                    if (previous != null) {
                        this.b -= c(key, previous);
                    }
                }
            }
            a(this.c);
        }
    }

    public boolean a(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        return this.a.containsKey(key);
    }

    public final V b(K k) {
        V vPut;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v = this.a.get(k);
            if (v != null) {
                this.g++;
                return v;
            }
            this.h++;
            V vC = c(k);
            if (vC == null) {
                return null;
            }
            synchronized (this) {
                this.e++;
                vPut = this.a.put(k, vC);
                if (vPut != null) {
                    this.a.put(k, vPut);
                } else {
                    this.b += c(k, vC);
                }
            }
            if (vPut != null) {
                a(false, k, vC, vPut);
                return vPut;
            }
            a(this.c);
            return vC;
        }
    }

    public final V b(K key, V value) {
        V previous;
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.d++;
            this.b += c(key, value);
            previous = this.a.put(key, value);
            if (previous != null) {
                this.b -= c(key, previous);
            }
        }
        if (previous != null) {
            a(false, key, previous, value);
        }
        a(this.c);
        Logger.i(i, "map size:" + this.a.size(), new Object[0]);
        return previous;
    }

    public void a(long maxSize) {
        K key;
        V value;
        while (true) {
            synchronized (this) {
                if (this.b < 0 || (this.a.isEmpty() && this.b != 0)) {
                    break;
                }
                if (this.b > maxSize && !this.a.isEmpty()) {
                    Map.Entry<K, V> toEvict = this.a.entrySet().iterator().next();
                    key = toEvict.getKey();
                    value = toEvict.getValue();
                    this.a.remove(key);
                    this.b -= c(key, value);
                    this.f++;
                } else {
                    return;
                }
            }
            a(true, key, value, null);
        }
        throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
    }

    protected void a(boolean evicted, K key, V oldValue, V newValue) {
    }

    protected V c(K key) {
        return null;
    }

    private long c(K key, V value) {
        long result = a(key, value);
        if (result < 0) {
            throw new IllegalStateException("Negative size: " + key + "=" + value);
        }
        return result;
    }

    protected long b() {
        return 0L;
    }

    protected long a(K key, V value) {
        return 1L;
    }

    public final synchronized String toString() {
        String str;
        synchronized (this) {
            int accesses = this.g + this.h;
            int hitPercent = accesses != 0 ? (this.g * 100) / accesses : 0;
            str = String.format("ZanLruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Long.valueOf(this.c), Integer.valueOf(this.g), Integer.valueOf(this.h), Integer.valueOf(hitPercent));
        }
        return str;
    }
}
