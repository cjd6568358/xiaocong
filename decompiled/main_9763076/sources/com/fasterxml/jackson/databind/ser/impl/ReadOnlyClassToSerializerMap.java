package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ReadOnlyClassToSerializerMap {
    protected final SerializerCache.TypeKey _cacheKey = new SerializerCache.TypeKey(getClass(), false);
    protected final JsonSerializerMap _map;

    private ReadOnlyClassToSerializerMap(JsonSerializerMap map) {
        this._map = map;
    }

    public ReadOnlyClassToSerializerMap instance() {
        return new ReadOnlyClassToSerializerMap(this._map);
    }

    public static ReadOnlyClassToSerializerMap from(HashMap<SerializerCache.TypeKey, JsonSerializer<Object>> src) {
        return new ReadOnlyClassToSerializerMap(new JsonSerializerMap(src));
    }

    public JsonSerializer<Object> typedValueSerializer(JavaType type) {
        this._cacheKey.resetTyped(type);
        return this._map.find(this._cacheKey);
    }

    public JsonSerializer<Object> typedValueSerializer(Class<?> cls) {
        this._cacheKey.resetTyped(cls);
        return this._map.find(this._cacheKey);
    }

    public JsonSerializer<Object> untypedValueSerializer(Class<?> cls) {
        this._cacheKey.resetUntyped(cls);
        return this._map.find(this._cacheKey);
    }

    public JsonSerializer<Object> untypedValueSerializer(JavaType type) {
        this._cacheKey.resetUntyped(type);
        return this._map.find(this._cacheKey);
    }
}
