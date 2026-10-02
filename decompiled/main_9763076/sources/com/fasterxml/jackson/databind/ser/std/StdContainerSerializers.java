package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedListSerializer;
import com.fasterxml.jackson.databind.ser.impl.IteratorSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdContainerSerializers {
    protected StdContainerSerializers() {
    }

    public static ContainerSerializer<?> indexedListSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> valueSerializer) {
        return new IndexedListSerializer(elemType, staticTyping, vts, null, valueSerializer);
    }

    public static ContainerSerializer<?> collectionSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> valueSerializer) {
        return new CollectionSerializer(elemType, staticTyping, vts, null, valueSerializer);
    }

    public static ContainerSerializer<?> iteratorSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts) {
        return new IteratorSerializer(elemType, staticTyping, vts, (BeanProperty) null);
    }

    public static ContainerSerializer<?> iterableSerializer(JavaType elemType, boolean staticTyping, TypeSerializer vts) {
        return new IterableSerializer(elemType, staticTyping, vts, (BeanProperty) null);
    }

    public static JsonSerializer<?> enumSetSerializer(JavaType enumType) {
        return new EnumSetSerializer(enumType, null);
    }
}
