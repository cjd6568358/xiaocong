package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleSerializers extends Serializers.Base {
    protected HashMap<ClassKey, JsonSerializer<?>> _classMappings = null;
    protected HashMap<ClassKey, JsonSerializer<?>> _interfaceMappings = null;

    public void addSerializer(JsonSerializer<?> ser) {
        Class<?> cls = ser.handledType();
        if (cls == null || cls == Object.class) {
            throw new IllegalArgumentException("JsonSerializer of type " + ser.getClass().getName() + " does not define valid handledType() -- must either register with method that takes type argument  or make serializer extend 'com.fasterxml.jackson.databind.ser.std.SerializerBase'");
        }
        _addSerializer(cls, ser);
    }

    public <T> void addSerializer(Class<? extends T> type, JsonSerializer<T> ser) {
        _addSerializer(type, ser);
    }

    private void _addSerializer(Class<?> cls, JsonSerializer<?> ser) {
        ClassKey key = new ClassKey(cls);
        if (cls.isInterface()) {
            if (this._interfaceMappings == null) {
                this._interfaceMappings = new HashMap<>();
            }
            this._interfaceMappings.put(key, ser);
        } else {
            if (this._classMappings == null) {
                this._classMappings = new HashMap<>();
            }
            this._classMappings.put(key, ser);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
        JsonSerializer<?> ser;
        Class<?> cls = type.getRawClass();
        ClassKey key = new ClassKey(cls);
        if (cls.isInterface()) {
            if (this._interfaceMappings != null) {
                JsonSerializer<?> ser2 = this._interfaceMappings.get(key);
                if (ser2 != null) {
                    return ser2;
                }
            }
        } else if (this._classMappings != null) {
            JsonSerializer<?> ser3 = this._classMappings.get(key);
            if (ser3 != null) {
                return ser3;
            }
            for (Class<?> curr = cls; curr != null; curr = curr.getSuperclass()) {
                key.reset(curr);
                JsonSerializer<?> ser4 = this._classMappings.get(key);
                if (ser4 != null) {
                    return ser4;
                }
            }
        }
        if (this._interfaceMappings != null) {
            JsonSerializer<?> ser5 = _findInterfaceMapping(cls, key);
            if (ser5 != null) {
                return ser5;
            }
            if (!cls.isInterface()) {
                do {
                    cls = cls.getSuperclass();
                    if (cls != null) {
                        ser = _findInterfaceMapping(cls, key);
                    }
                } while (ser == null);
                return ser;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findArraySerializer(SerializationConfig config, ArrayType type, BeanDescription beanDesc, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
        return findSerializer(config, type, beanDesc);
    }

    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findCollectionSerializer(SerializationConfig config, CollectionType type, BeanDescription beanDesc, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
        return findSerializer(config, type, beanDesc);
    }

    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findCollectionLikeSerializer(SerializationConfig config, CollectionLikeType type, BeanDescription beanDesc, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
        return findSerializer(config, type, beanDesc);
    }

    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findMapSerializer(SerializationConfig config, MapType type, BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
        return findSerializer(config, type, beanDesc);
    }

    @Override // com.fasterxml.jackson.databind.ser.Serializers.Base, com.fasterxml.jackson.databind.ser.Serializers
    public JsonSerializer<?> findMapLikeSerializer(SerializationConfig config, MapLikeType type, BeanDescription beanDesc, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) {
        return findSerializer(config, type, beanDesc);
    }

    protected JsonSerializer<?> _findInterfaceMapping(Class<?> cls, ClassKey key) {
        for (Class<?> iface : cls.getInterfaces()) {
            key.reset(iface);
            JsonSerializer<?> ser = this._interfaceMappings.get(key);
            if (ser == null) {
                JsonSerializer<?> ser2 = _findInterfaceMapping(iface, key);
                if (ser2 != null) {
                    return ser2;
                }
            } else {
                return ser;
            }
        }
        return null;
    }
}
