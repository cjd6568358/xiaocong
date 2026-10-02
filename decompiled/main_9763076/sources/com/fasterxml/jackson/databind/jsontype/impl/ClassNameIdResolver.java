package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.EnumMap;
import java.util.EnumSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClassNameIdResolver extends TypeIdResolverBase {
    public ClassNameIdResolver(JavaType baseType, TypeFactory typeFactory) {
        super(baseType, typeFactory);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CLASS;
    }

    public void registerSubtype(Class<?> type, String name) {
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public String idFromValue(Object value) {
        return _idFrom(value, value.getClass());
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public String idFromValueAndType(Object value, Class<?> type) {
        return _idFrom(value, type);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public JavaType typeFromId(String id) throws Throwable {
        if (id.indexOf(60) > 0) {
            return this._typeFactory.constructFromCanonical(id);
        }
        try {
            Class<?> cls = ClassUtil.findClass(id);
            return this._typeFactory.constructSpecializedType(this._baseType, cls);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid type id '" + id + "' (for id type 'Id.class'): no such class found");
        } catch (Exception e2) {
            throw new IllegalArgumentException("Invalid type id '" + id + "' (for id type 'Id.class'): " + e2.getMessage(), e2);
        }
    }

    protected final String _idFrom(Object value, Class<?> cls) {
        if (Enum.class.isAssignableFrom(cls) && !cls.isEnum()) {
            cls = cls.getSuperclass();
        }
        String str = cls.getName();
        if (str.startsWith("java.util")) {
            if (value instanceof EnumSet) {
                Class<?> enumClass = ClassUtil.findEnumType((EnumSet<?>) value);
                return TypeFactory.defaultInstance().constructCollectionType(EnumSet.class, enumClass).toCanonical();
            }
            if (value instanceof EnumMap) {
                Class<?> enumClass2 = ClassUtil.findEnumType((EnumMap<?, ?>) value);
                return TypeFactory.defaultInstance().constructMapType(EnumMap.class, enumClass2, Object.class).toCanonical();
            }
            String end = str.substring(9);
            if ((end.startsWith(".Arrays$") || end.startsWith(".Collections$")) && str.indexOf("List") >= 0) {
                return "java.util.ArrayList";
            }
            return str;
        }
        if (str.indexOf(36) >= 0) {
            Class<?> outer = ClassUtil.getOuterClass(cls);
            if (outer != null) {
                Class<?> staticType = this._baseType.getRawClass();
                if (ClassUtil.getOuterClass(staticType) == null) {
                    return this._baseType.getRawClass().getName();
                }
                return str;
            }
            return str;
        }
        return str;
    }
}
