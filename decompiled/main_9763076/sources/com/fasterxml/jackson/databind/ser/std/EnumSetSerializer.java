package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.EnumSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumSetSerializer extends AsArraySerializerBase<EnumSet<? extends Enum<?>>> {
    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public /* bridge */ /* synthetic */ AsArraySerializerBase<EnumSet<? extends Enum<?>>> withResolved(BeanProperty x0, TypeSerializer x1, JsonSerializer x2) {
        return withResolved2(x0, x1, (JsonSerializer<?>) x2);
    }

    public EnumSetSerializer(JavaType elemType, BeanProperty property) {
        super(EnumSet.class, elemType, true, null, property, null);
    }

    public EnumSetSerializer(EnumSetSerializer src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> valueSerializer) {
        super(src, property, vts, valueSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public EnumSetSerializer _withValueTypeSerializer(TypeSerializer vts) {
        return this;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    /* JADX INFO: renamed from: withResolved, reason: avoid collision after fix types in other method */
    public AsArraySerializerBase<EnumSet<? extends Enum<?>>> withResolved2(BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
        return new EnumSetSerializer(this, property, vts, elementSerializer);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(EnumSet<? extends Enum<?>> value) {
        return value == null || value.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(EnumSet<? extends Enum<?>> value) {
        return value.size() == 1;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase
    public void serializeContents(EnumSet<? extends Enum<?>> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        JsonSerializer<Object> enumSer = this._elementSerializer;
        for (Enum<?> en : value) {
            if (enumSer == null) {
                enumSer = provider.findValueSerializer(en.getDeclaringClass(), this._property);
            }
            enumSer.serialize(en, jgen, provider);
        }
    }
}
