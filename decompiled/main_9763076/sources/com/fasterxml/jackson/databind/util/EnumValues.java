package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class EnumValues {
    private final EnumMap<?, SerializedString> _values;

    private EnumValues(Map<Enum<?>, SerializedString> v) {
        this._values = new EnumMap<>(v);
    }

    public static EnumValues construct(Class<Enum<?>> enumClass, AnnotationIntrospector intr) {
        return constructFromName(enumClass, intr);
    }

    public static EnumValues constructFromName(Class<Enum<?>> enumClass, AnnotationIntrospector intr) {
        Class<? extends Enum<?>> cls = ClassUtil.findEnumType(enumClass);
        Enum<?>[] values = (Enum[]) cls.getEnumConstants();
        if (values != null) {
            Map<Enum<?>, SerializedString> map = new HashMap<>();
            for (Enum<?> en : values) {
                String value = intr.findEnumValue(en);
                map.put(en, new SerializedString(value));
            }
            return new EnumValues(map);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + enumClass.getName());
    }

    public static EnumValues constructFromToString(Class<Enum<?>> enumClass, AnnotationIntrospector intr) {
        Class<? extends Enum<?>> cls = ClassUtil.findEnumType(enumClass);
        Enum<?>[] values = (Enum[]) cls.getEnumConstants();
        if (values != null) {
            Map<Enum<?>, SerializedString> map = new HashMap<>();
            for (Enum<?> en : values) {
                map.put(en, new SerializedString(en.toString()));
            }
            return new EnumValues(map);
        }
        throw new IllegalArgumentException("Can not determine enum constants for Class " + enumClass.getName());
    }

    public SerializedString serializedValueFor(Enum<?> key) {
        return this._values.get(key);
    }

    public Collection<SerializedString> values() {
        return this._values.values();
    }
}
