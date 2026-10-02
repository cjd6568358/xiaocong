package com.alibaba.fastjson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TypeReference<T> {
    protected final Type type;
    static ConcurrentMap<Type, Type> classTypeCache = new ConcurrentHashMap(16, 0.75f, 1);
    public static final Type LIST_STRING = new TypeReference<List<String>>() { // from class: com.alibaba.fastjson.TypeReference.1
    }.getType();

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        Type cachedType = classTypeCache.get(type);
        if (cachedType == null) {
            classTypeCache.putIfAbsent(type, type);
            cachedType = classTypeCache.get(type);
        }
        this.type = cachedType;
    }

    public Type getType() {
        return this.type;
    }
}
