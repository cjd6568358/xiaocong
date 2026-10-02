package com.fasterxml.jackson.databind.util;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LRUMap<K, V> extends LinkedHashMap<K, V> {
    protected final int _maxEntries;

    public LRUMap(int initialEntries, int maxEntries) {
        super(initialEntries, 0.8f, true);
        this._maxEntries = maxEntries;
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > this._maxEntries;
    }
}
