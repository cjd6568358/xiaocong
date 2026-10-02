package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import java.lang.Enum;
import java.lang.reflect.Method;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumResolver<T extends Enum<T>> {
    protected final Class<T> _enumClass;
    protected final T[] _enums;
    protected final HashMap<String, T> _enumsById;

    protected EnumResolver(Class<T> enumClass, T[] enums, HashMap<String, T> map) {
        this._enumClass = enumClass;
        this._enums = enums;
        this._enumsById = map;
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructFor(Class<ET> enumCls, AnnotationIntrospector ai) {
        ET[] enumValues = enumCls.getEnumConstants();
        if (enumValues == null) {
            throw new IllegalArgumentException("No enum constants for class " + enumCls.getName());
        }
        HashMap<String, ET> map = new HashMap<>();
        for (ET e : enumValues) {
            map.put(ai.findEnumValue(e), e);
        }
        return new EnumResolver<>(enumCls, enumValues, map);
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingToString(Class<ET> enumCls) {
        ET[] enumValues = enumCls.getEnumConstants();
        HashMap<String, ET> map = new HashMap<>();
        int i = enumValues.length;
        while (true) {
            i--;
            if (i >= 0) {
                ET e = enumValues[i];
                map.put(e.toString(), e);
            } else {
                return new EnumResolver<>(enumCls, enumValues, map);
            }
        }
    }

    public static <ET extends Enum<ET>> EnumResolver<ET> constructUsingMethod(Class<ET> enumCls, Method accessor) {
        ET[] enumValues = enumCls.getEnumConstants();
        HashMap<String, ET> map = new HashMap<>();
        int i = enumValues.length;
        while (true) {
            i--;
            if (i >= 0) {
                ET en = enumValues[i];
                try {
                    Object o = accessor.invoke(en, new Object[0]);
                    if (o != null) {
                        map.put(o.toString(), en);
                    }
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to access @JsonValue of Enum value " + en + ": " + e.getMessage());
                }
            } else {
                return new EnumResolver<>(enumCls, enumValues, map);
            }
        }
    }

    public static EnumResolver<?> constructUnsafe(Class<?> rawEnumCls, AnnotationIntrospector ai) {
        return constructFor(rawEnumCls, ai);
    }

    public static EnumResolver<?> constructUnsafeUsingToString(Class<?> rawEnumCls) {
        return constructUsingToString(rawEnumCls);
    }

    public static EnumResolver<?> constructUnsafeUsingMethod(Class<?> rawEnumCls, Method accessor) {
        return constructUsingMethod(rawEnumCls, accessor);
    }

    public T findEnum(String key) {
        return this._enumsById.get(key);
    }

    public T getEnum(int index) {
        if (index < 0 || index >= this._enums.length) {
            return null;
        }
        return this._enums[index];
    }

    public Class<T> getEnumClass() {
        return this._enumClass;
    }

    public int lastValidIndex() {
        return this._enums.length - 1;
    }
}
