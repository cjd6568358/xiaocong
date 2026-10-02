package com.fasterxml.jackson.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class InternCache extends LinkedHashMap<String, String> {
    private static final int MAX_ENTRIES = 192;
    public static final InternCache instance = new InternCache();

    private InternCache() {
        super(MAX_ENTRIES, 0.8f, true);
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
        return size() > MAX_ENTRIES;
    }

    public synchronized String intern(String input) {
        String result;
        result = get(input);
        if (result == null) {
            result = input.intern();
            put(result, result);
        }
        return result;
    }
}
