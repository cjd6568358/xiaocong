package com.fasterxml.jackson.databind.jsontype;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface TypeIdResolver {
    JsonTypeInfo.Id getMechanism();

    String idFromBaseType();

    String idFromValue(Object obj);

    String idFromValueAndType(Object obj, Class<?> cls);

    void init(JavaType javaType);

    JavaType typeFromId(String str);
}
