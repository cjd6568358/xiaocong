package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DefaultSerializerProvider extends SerializerProvider {
    protected ArrayList<ObjectIdGenerator<?>> _objectIdGenerators;
    protected IdentityHashMap<Object, WritableObjectId> _seenObjectIds;

    public abstract DefaultSerializerProvider createInstance(SerializationConfig serializationConfig, SerializerFactory serializerFactory);

    protected DefaultSerializerProvider() {
    }

    protected DefaultSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }

    public void serializeValue(JsonGenerator jgen, Object value) throws IOException {
        JsonSerializer<Object> ser;
        boolean wrap;
        if (value == null) {
            ser = getDefaultNullValueSerializer();
            wrap = false;
        } else {
            Class<?> cls = value.getClass();
            ser = findTypedValueSerializer(cls, true, (BeanProperty) null);
            String rootName = this._config.getRootName();
            if (rootName == null) {
                wrap = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
                if (wrap) {
                    jgen.writeStartObject();
                    jgen.writeFieldName(this._rootNames.findRootName(value.getClass(), this._config));
                }
            } else if (rootName.length() == 0) {
                wrap = false;
            } else {
                wrap = true;
                jgen.writeStartObject();
                jgen.writeFieldName(rootName);
            }
        }
        try {
            ser.serialize(value, jgen, this);
            if (wrap) {
                jgen.writeEndObject();
            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg == null) {
                msg = "[no message for " + e.getClass().getName() + "]";
            }
            throw new JsonMappingException(msg, e);
        }
    }

    public void serializeValue(JsonGenerator jgen, Object value, JavaType rootType) throws IOException {
        JsonSerializer<Object> ser;
        boolean wrap;
        if (value == null) {
            ser = getDefaultNullValueSerializer();
            wrap = false;
        } else {
            if (!rootType.getRawClass().isAssignableFrom(value.getClass())) {
                _reportIncompatibleRootType(value, rootType);
            }
            ser = findTypedValueSerializer(rootType, true, (BeanProperty) null);
            wrap = this._config.isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
            if (wrap) {
                jgen.writeStartObject();
                jgen.writeFieldName(this._rootNames.findRootName(rootType, this._config));
            }
        }
        try {
            ser.serialize(value, jgen, this);
            if (wrap) {
                jgen.writeEndObject();
            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg == null) {
                msg = "[no message for " + e.getClass().getName() + "]";
            }
            throw new JsonMappingException(msg, e);
        }
    }

    public JsonSchema generateJsonSchema(Class<?> type) throws JsonMappingException {
        if (type == null) {
            throw new IllegalArgumentException("A class must be provided");
        }
        Object objFindValueSerializer = findValueSerializer(type, (BeanProperty) null);
        JsonNode schemaNode = objFindValueSerializer instanceof SchemaAware ? ((SchemaAware) objFindValueSerializer).getSchema(this, null) : JsonSchema.getDefaultSchemaNode();
        if (!(schemaNode instanceof ObjectNode)) {
            throw new IllegalArgumentException("Class " + type.getName() + " would not be serialized as a JSON object and therefore has no schema");
        }
        return new JsonSchema((ObjectNode) schemaNode);
    }

    public boolean hasSerializerFor(Class<?> cls) {
        return _findExplicitUntypedSerializer(cls, null) != null;
    }

    public int cachedSerializersCount() {
        return this._serializerCache.size();
    }

    public void flushCachedSerializers() {
        this._serializerCache.flush();
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
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

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public WritableObjectId findObjectId(Object forPojo, ObjectIdGenerator<?> generatorType) {
        if (this._seenObjectIds == null) {
            this._seenObjectIds = new IdentityHashMap<>();
        } else {
            WritableObjectId oid = this._seenObjectIds.get(forPojo);
            if (oid != null) {
                return oid;
            }
        }
        ObjectIdGenerator<?> generator = null;
        if (this._objectIdGenerators == null) {
            this._objectIdGenerators = new ArrayList<>(8);
        } else {
            int len = this._objectIdGenerators.size();
            for (int i = 0; i < len; i++) {
                ObjectIdGenerator<?> gen = this._objectIdGenerators.get(i);
                if (gen.canUseFor(generatorType)) {
                    generator = gen;
                    break;
                }
            }
        }
        if (generator == null) {
            generator = generatorType.newForSerialization(this);
            this._objectIdGenerators.add(generator);
        }
        WritableObjectId oid2 = new WritableObjectId(generator);
        this._seenObjectIds.put(forPojo, oid2);
        return oid2;
    }

    @Override // com.fasterxml.jackson.databind.SerializerProvider
    public JsonSerializer<Object> serializerInstance(Annotated annotated, Object serDef) throws JsonMappingException {
        JsonSerializer<?> ser;
        if (serDef == null) {
            return null;
        }
        if (serDef instanceof JsonSerializer) {
            ser = (JsonSerializer) serDef;
        } else {
            if (!(serDef instanceof Class)) {
                throw new IllegalStateException("AnnotationIntrospector returned serializer definition of type " + serDef.getClass().getName() + "; expected type JsonSerializer or Class<JsonSerializer> instead");
            }
            Class<?> serClass = (Class) serDef;
            if (serClass == JsonSerializer.None.class || serClass == NoClass.class) {
                return null;
            }
            if (!JsonSerializer.class.isAssignableFrom(serClass)) {
                throw new IllegalStateException("AnnotationIntrospector returned Class " + serClass.getName() + "; expected Class<JsonSerializer>");
            }
            HandlerInstantiator hi = this._config.getHandlerInstantiator();
            if (hi != null) {
                ser = hi.serializerInstance(this._config, annotated, serClass);
            } else {
                ser = (JsonSerializer) ClassUtil.createInstance(serClass, this._config.canOverrideAccessModifiers());
            }
        }
        return _handleResolvable(ser);
    }

    public static final class Impl extends DefaultSerializerProvider {
        public Impl() {
        }

        protected Impl(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
            super(src, config, f);
        }

        @Override // com.fasterxml.jackson.databind.ser.DefaultSerializerProvider
        public Impl createInstance(SerializationConfig config, SerializerFactory jsf) {
            return new Impl(this, config, jsf);
        }
    }
}
