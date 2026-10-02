package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class DeserializerCache {
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _cachedDeserializers = new ConcurrentHashMap<>(64, 0.75f, 2);
    protected final HashMap<JavaType, JsonDeserializer<Object>> _incompleteDeserializers = new HashMap<>(8);

    public int cachedDeserializersCount() {
        return this._cachedDeserializers.size();
    }

    public void flushCachedDeserializers() {
        this._cachedDeserializers.clear();
    }

    public JsonDeserializer<Object> findValueDeserializer(DeserializationContext ctxt, DeserializerFactory factory, JavaType propertyType) throws JsonMappingException {
        JsonDeserializer<Object> deser = _findCachedDeserializer(propertyType);
        if (deser != null) {
            return deser;
        }
        JsonDeserializer<Object> deser2 = _createAndCacheValueDeserializer(ctxt, factory, propertyType);
        if (deser2 == null) {
            deser2 = _handleUnknownValueDeserializer(propertyType);
        }
        return deser2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KeyDeserializer findKeyDeserializer(DeserializationContext ctxt, DeserializerFactory factory, JavaType type) throws JsonMappingException {
        KeyDeserializer keyDeserializerCreateKeyDeserializer = factory.createKeyDeserializer(ctxt, type);
        if (keyDeserializerCreateKeyDeserializer == 0) {
            KeyDeserializer kd = _handleUnknownKeyDeserializer(type);
            return kd;
        }
        if (keyDeserializerCreateKeyDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer) keyDeserializerCreateKeyDeserializer).resolve(ctxt);
            return keyDeserializerCreateKeyDeserializer;
        }
        return keyDeserializerCreateKeyDeserializer;
    }

    public boolean hasValueDeserializerFor(DeserializationContext ctxt, DeserializerFactory factory, JavaType type) {
        JsonDeserializer<Object> deser = _findCachedDeserializer(type);
        if (deser == null) {
            try {
                deser = _createAndCacheValueDeserializer(ctxt, factory, type);
            } catch (Exception e) {
                return false;
            }
        }
        return deser != null;
    }

    protected JsonDeserializer<Object> _findCachedDeserializer(JavaType type) {
        if (type == null) {
            throw new IllegalArgumentException("Null JavaType passed");
        }
        return this._cachedDeserializers.get(type);
    }

    protected JsonDeserializer<Object> _createAndCacheValueDeserializer(DeserializationContext ctxt, DeserializerFactory factory, JavaType type) throws JsonMappingException {
        JsonDeserializer<Object> deser;
        synchronized (this._incompleteDeserializers) {
            JsonDeserializer<Object> deser2 = _findCachedDeserializer(type);
            if (deser2 != null) {
                return deser2;
            }
            int count = this._incompleteDeserializers.size();
            if (count > 0 && (deser = this._incompleteDeserializers.get(type)) != null) {
                return deser;
            }
            try {
                JsonDeserializer<Object> jsonDeserializer_createAndCache2 = _createAndCache2(ctxt, factory, type);
                if (count == 0 && this._incompleteDeserializers.size() > 0) {
                    this._incompleteDeserializers.clear();
                }
                return jsonDeserializer_createAndCache2;
            } catch (Throwable th) {
                if (count == 0 && this._incompleteDeserializers.size() > 0) {
                    this._incompleteDeserializers.clear();
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected JsonDeserializer<Object> _createAndCache2(DeserializationContext deserializationContext, DeserializerFactory deserializerFactory, JavaType javaType) throws JsonMappingException {
        try {
            JsonDeserializer<Object> jsonDeserializer_createDeserializer = _createDeserializer(deserializationContext, deserializerFactory, javaType);
            if (jsonDeserializer_createDeserializer == 0) {
                return null;
            }
            boolean z = jsonDeserializer_createDeserializer instanceof ResolvableDeserializer;
            boolean zIsCachable = jsonDeserializer_createDeserializer.isCachable();
            if (z) {
                this._incompleteDeserializers.put(javaType, jsonDeserializer_createDeserializer);
                ((ResolvableDeserializer) jsonDeserializer_createDeserializer).resolve(deserializationContext);
                this._incompleteDeserializers.remove(javaType);
            }
            if (zIsCachable) {
                this._cachedDeserializers.put(javaType, jsonDeserializer_createDeserializer);
                return jsonDeserializer_createDeserializer;
            }
            return jsonDeserializer_createDeserializer;
        } catch (IllegalArgumentException e) {
            throw new JsonMappingException(e.getMessage(), null, e);
        }
    }

    protected JsonDeserializer<Object> _createDeserializer(DeserializationContext ctxt, DeserializerFactory factory, JavaType type) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        if (type.isAbstract() || type.isMapLikeType() || type.isCollectionLikeType()) {
            type = factory.mapAbstractType(config, type);
        }
        BeanDescription beanDesc = config.introspect(type);
        JsonDeserializer<Object> deser = findDeserializerFromAnnotation(ctxt, beanDesc.getClassInfo());
        if (deser == null) {
            JavaType newType = modifyTypeByAnnotation(ctxt, beanDesc.getClassInfo(), type);
            if (newType != type) {
                type = newType;
                beanDesc = config.introspect(newType);
            }
            Class<?> builder = beanDesc.findPOJOBuilder();
            if (builder != null) {
                return factory.createBuilderBasedDeserializer(ctxt, type, beanDesc, builder);
            }
            if (type.isEnumType()) {
                return factory.createEnumDeserializer(ctxt, type, beanDesc);
            }
            if (type.isContainerType()) {
                if (type.isArrayType()) {
                    return factory.createArrayDeserializer(ctxt, (ArrayType) type, beanDesc);
                }
                if (type.isMapLikeType()) {
                    MapLikeType mlt = (MapLikeType) type;
                    if (mlt.isTrueMapType()) {
                        return factory.createMapDeserializer(ctxt, (MapType) mlt, beanDesc);
                    }
                    return factory.createMapLikeDeserializer(ctxt, mlt, beanDesc);
                }
                if (type.isCollectionLikeType()) {
                    CollectionLikeType clt = (CollectionLikeType) type;
                    if (clt.isTrueCollectionType()) {
                        return factory.createCollectionDeserializer(ctxt, (CollectionType) clt, beanDesc);
                    }
                    return factory.createCollectionLikeDeserializer(ctxt, clt, beanDesc);
                }
            }
            if (JsonNode.class.isAssignableFrom(type.getRawClass())) {
                return factory.createTreeDeserializer(config, type, beanDesc);
            }
            return factory.createBeanDeserializer(ctxt, type, beanDesc);
        }
        return deser;
    }

    protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext ctxt, Annotated ann) throws JsonMappingException {
        Object deserDef = ctxt.getAnnotationIntrospector().findDeserializer(ann);
        if (deserDef == null) {
            return null;
        }
        return ctxt.deserializerInstance(ann, deserDef);
    }

    private JavaType modifyTypeByAnnotation(DeserializationContext ctxt, Annotated a, JavaType type) throws JsonMappingException {
        Object cdDef;
        Object kdDef;
        KeyDeserializer kd;
        AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
        Class<?> subclass = intr.findDeserializationType(a, type);
        if (subclass != null) {
            try {
                type = type.narrowBy(subclass);
            } catch (IllegalArgumentException iae) {
                throw new JsonMappingException("Failed to narrow type " + type + " with concrete-type annotation (value " + subclass.getName() + "), method '" + a.getName() + "': " + iae.getMessage(), null, iae);
            }
        }
        if (type.isContainerType()) {
            Class<?> keyClass = intr.findDeserializationKeyType(a, type.getKeyType());
            if (keyClass != null) {
                if (!(type instanceof MapLikeType)) {
                    throw new JsonMappingException("Illegal key-type annotation: type " + type + " is not a Map(-like) type");
                }
                try {
                    type = ((MapLikeType) type).narrowKey(keyClass);
                } catch (IllegalArgumentException iae2) {
                    throw new JsonMappingException("Failed to narrow key type " + type + " with key-type annotation (" + keyClass.getName() + "): " + iae2.getMessage(), null, iae2);
                }
            }
            JavaType keyType = type.getKeyType();
            if (keyType != null && keyType.getValueHandler() == null && (kdDef = intr.findKeyDeserializer(a)) != null && (kd = ctxt.keyDeserializerInstance(a, kdDef)) != null) {
                type = ((MapLikeType) type).withKeyValueHandler(kd);
                type.getKeyType();
            }
            Class<?> cc = intr.findDeserializationContentType(a, type.getContentType());
            if (cc != null) {
                try {
                    type = type.narrowContentsBy(cc);
                } catch (IllegalArgumentException iae3) {
                    throw new JsonMappingException("Failed to narrow content type " + type + " with content-type annotation (" + cc.getName() + "): " + iae3.getMessage(), null, iae3);
                }
            }
            JavaType contentType = type.getContentType();
            if (contentType.getValueHandler() == null && (cdDef = intr.findContentDeserializer(a)) != null) {
                JsonDeserializer<?> cd = null;
                if (cdDef instanceof JsonDeserializer) {
                } else {
                    Class<?> cdClass = _verifyAsClass(cdDef, "findContentDeserializer", JsonDeserializer.None.class);
                    if (cdClass != null) {
                        cd = ctxt.deserializerInstance(a, cdClass);
                    }
                }
                if (cd != null) {
                    return type.withContentValueHandler(cd);
                }
                return type;
            }
            return type;
        }
        return type;
    }

    private Class<?> _verifyAsClass(Object src, String methodName, Class<?> noneClass) {
        if (src == null) {
            return null;
        }
        if (!(src instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector." + methodName + "() returned value of type " + src.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
        }
        Class<?> cls = (Class) src;
        if (cls == noneClass || cls == NoClass.class) {
            return null;
        }
        return cls;
    }

    protected JsonDeserializer<Object> _handleUnknownValueDeserializer(JavaType type) throws JsonMappingException {
        Class<?> rawClass = type.getRawClass();
        if (!ClassUtil.isConcrete(rawClass)) {
            throw new JsonMappingException("Can not find a Value deserializer for abstract type " + type);
        }
        throw new JsonMappingException("Can not find a Value deserializer for type " + type);
    }

    protected KeyDeserializer _handleUnknownKeyDeserializer(JavaType type) throws JsonMappingException {
        throw new JsonMappingException("Can not find a (Map) Key deserializer for type " + type);
    }
}
