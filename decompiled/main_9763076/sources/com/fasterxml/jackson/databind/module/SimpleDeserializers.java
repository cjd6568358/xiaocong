package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleDeserializers implements Deserializers {
    protected HashMap<ClassKey, JsonDeserializer<?>> _classMappings = null;

    public <T> void addDeserializer(Class<T> forClass, JsonDeserializer<? extends T> deser) {
        ClassKey key = new ClassKey(forClass);
        if (this._classMappings == null) {
            this._classMappings = new HashMap<>();
        }
        this._classMappings.put(key, deser);
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findArrayDeserializer(ArrayType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findCollectionDeserializer(CollectionType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findCollectionLikeDeserializer(CollectionLikeType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findMapDeserializer(MapType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(type.getRawClass()));
    }

    @Override // com.fasterxml.jackson.databind.deser.Deserializers
    public JsonDeserializer<?> findTreeNodeDeserializer(Class<? extends JsonNode> nodeType, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        if (this._classMappings == null) {
            return null;
        }
        return this._classMappings.get(new ClassKey(nodeType));
    }
}
