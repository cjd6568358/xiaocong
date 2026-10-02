package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DefaultDeserializationContext extends DeserializationContext {
    protected LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> _objectIds;

    public abstract DefaultDeserializationContext createInstance(DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues);

    public abstract DefaultDeserializationContext with(DeserializerFactory deserializerFactory);

    protected DefaultDeserializationContext(DeserializerFactory df, DeserializerCache cache) {
        super(df, cache);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext src, DeserializationConfig config, JsonParser jp, InjectableValues values) {
        super(src, config, jp, values);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext src, DeserializerFactory factory) {
        super(src, factory);
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public ReadableObjectId findObjectId(Object id, ObjectIdGenerator<?> generator) {
        ObjectIdGenerator.IdKey key = generator.key(id);
        if (this._objectIds == null) {
            this._objectIds = new LinkedHashMap<>();
        } else {
            ReadableObjectId entry = this._objectIds.get(key);
            if (entry != null) {
                return entry;
            }
        }
        ReadableObjectId entry2 = new ReadableObjectId(id);
        this._objectIds.put(key, entry2);
        return entry2;
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public ObjectIdGenerator<?> objectIdGeneratorInstance(Annotated annotated, ObjectIdInfo objectIdInfo) throws JsonMappingException {
        ObjectIdGenerator<?> gen;
        Class<?> implClass = objectIdInfo.getGeneratorType();
        HandlerInstantiator hi = this._config.getHandlerInstantiator();
        if (hi != null) {
            gen = hi.objectIdGeneratorInstance(this._config, annotated, implClass);
        } else {
            gen = (ObjectIdGenerator) ClassUtil.createInstance(implClass, this._config.canOverrideAccessModifiers());
        }
        return gen.forScope(objectIdInfo.getScope());
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public JsonDeserializer<Object> deserializerInstance(Annotated annotated, Object deserDef) throws JsonMappingException {
        JsonDeserializer<?> deser = null;
        if (deserDef != null) {
            if (deserDef instanceof JsonDeserializer) {
                deser = (JsonDeserializer) deserDef;
            } else {
                if (!(deserDef instanceof Class)) {
                    throw new IllegalStateException("AnnotationIntrospector returned deserializer definition of type " + deserDef.getClass().getName() + "; expected type JsonDeserializer or Class<JsonDeserializer> instead");
                }
                Class<?> deserClass = (Class) deserDef;
                if (deserClass != JsonDeserializer.None.class && deserClass != NoClass.class) {
                    if (!JsonDeserializer.class.isAssignableFrom(deserClass)) {
                        throw new IllegalStateException("AnnotationIntrospector returned Class " + deserClass.getName() + "; expected Class<JsonDeserializer>");
                    }
                    HandlerInstantiator hi = this._config.getHandlerInstantiator();
                    if (hi != null) {
                        deser = hi.deserializerInstance(this._config, annotated, deserClass);
                    } else {
                        deser = (JsonDeserializer) ClassUtil.createInstance(deserClass, this._config.canOverrideAccessModifiers());
                    }
                }
            }
            if (deser instanceof ResolvableDeserializer) {
                ((ResolvableDeserializer) deser).resolve(this);
            }
        }
        return deser;
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public final KeyDeserializer keyDeserializerInstance(Annotated annotated, Object deserDef) throws JsonMappingException {
        KeyDeserializer deser = null;
        if (deserDef != null) {
            if (deserDef instanceof KeyDeserializer) {
                deser = (KeyDeserializer) deserDef;
            } else {
                if (!(deserDef instanceof Class)) {
                    throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + deserDef.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
                }
                Class<?> deserClass = (Class) deserDef;
                if (deserClass != KeyDeserializer.None.class && deserClass != NoClass.class) {
                    if (!KeyDeserializer.class.isAssignableFrom(deserClass)) {
                        throw new IllegalStateException("AnnotationIntrospector returned Class " + deserClass.getName() + "; expected Class<KeyDeserializer>");
                    }
                    HandlerInstantiator hi = this._config.getHandlerInstantiator();
                    if (hi != null) {
                        deser = hi.keyDeserializerInstance(this._config, annotated, deserClass);
                    } else {
                        deser = (KeyDeserializer) ClassUtil.createInstance(deserClass, this._config.canOverrideAccessModifiers());
                    }
                }
            }
            if (deser instanceof ResolvableDeserializer) {
                ((ResolvableDeserializer) deser).resolve(this);
            }
        }
        return deser;
    }

    public static final class Impl extends DefaultDeserializationContext {
        public Impl(DeserializerFactory df) {
            super(df, (DeserializerCache) null);
        }

        protected Impl(Impl src, DeserializationConfig config, JsonParser jp, InjectableValues values) {
            super(src, config, jp, values);
        }

        protected Impl(Impl src, DeserializerFactory factory) {
            super(src, factory);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext createInstance(DeserializationConfig config, JsonParser jp, InjectableValues values) {
            return new Impl(this, config, jp, values);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext with(DeserializerFactory factory) {
            return new Impl(this, factory);
        }
    }
}
