package com.alibaba.fastjson.serializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BeforeFilter implements SerializeFilter {
    private static final ThreadLocal<JSONSerializer> serializerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Character> seperatorLocal = new ThreadLocal<>();
    private static final Character COMMA = ',';

    public abstract void writeBefore(Object obj);

    final char writeBefore(JSONSerializer serializer, Object object, char seperator) {
        serializerLocal.set(serializer);
        seperatorLocal.set(Character.valueOf(seperator));
        writeBefore(object);
        serializerLocal.set(null);
        return seperatorLocal.get().charValue();
    }
}
