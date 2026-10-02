package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class HandlerInstantiator {
    public abstract JsonDeserializer<?> deserializerInstance(DeserializationConfig deserializationConfig, Annotated annotated, Class<?> cls);

    public abstract KeyDeserializer keyDeserializerInstance(DeserializationConfig deserializationConfig, Annotated annotated, Class<?> cls);

    public abstract JsonSerializer<?> serializerInstance(SerializationConfig serializationConfig, Annotated annotated, Class<?> cls);

    public abstract TypeIdResolver typeIdResolverInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> cls);

    public abstract TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> mapperConfig, Annotated annotated, Class<?> cls);

    public ValueInstantiator valueInstantiatorInstance(MapperConfig<?> config, Annotated annotated, Class<?> resolverClass) {
        return null;
    }

    public ObjectIdGenerator<?> objectIdGeneratorInstance(MapperConfig<?> config, Annotated annotated, Class<?> implClass) {
        return null;
    }
}
