package com.huawei.hms.support.a;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: compiled from: GenericTypeReflector.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a {
    public static Class<?> a(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getRawType();
        }
        if (type instanceof TypeVariable) {
            TypeVariable typeVariable = (TypeVariable) type;
            if (typeVariable.getBounds().length == 0) {
                return Object.class;
            }
            return a(typeVariable.getBounds()[0]);
        }
        throw new IllegalArgumentException("not supported: " + type.getClass());
    }
}
