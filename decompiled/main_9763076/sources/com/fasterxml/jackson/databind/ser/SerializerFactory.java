package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class SerializerFactory {
    public abstract JsonSerializer<Object> createKeySerializer(SerializationConfig serializationConfig, JavaType javaType) throws JsonMappingException;

    public abstract JsonSerializer<Object> createSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException;

    public abstract TypeSerializer createTypeSerializer(SerializationConfig serializationConfig, JavaType javaType) throws JsonMappingException;

    public abstract SerializerFactory withAdditionalKeySerializers(Serializers serializers);

    public abstract SerializerFactory withAdditionalSerializers(Serializers serializers);

    public abstract SerializerFactory withSerializerModifier(BeanSerializerModifier beanSerializerModifier);
}
