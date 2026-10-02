package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class StdSerializer<T> extends JsonSerializer<T> implements SchemaAware {
    protected final Class<T> _handledType;

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public abstract void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    protected StdSerializer(Class<T> t) {
        this._handledType = t;
    }

    protected StdSerializer(JavaType javaType) {
        this._handledType = (Class<T>) javaType.getRawClass();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected StdSerializer(Class<?> cls, boolean dummy) {
        this._handledType = cls;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public final Class<T> handledType() {
        return this._handledType;
    }

    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return createSchemaNode(SettingsContentProvider.STRING_TYPE);
    }

    protected ObjectNode createObjectNode() {
        return JsonNodeFactory.instance.objectNode();
    }

    protected ObjectNode createSchemaNode(String type) {
        ObjectNode schema = createObjectNode();
        schema.put("type", type);
        return schema;
    }

    protected ObjectNode createSchemaNode(String type, boolean isOptional) {
        ObjectNode schema = createSchemaNode(type);
        if (!isOptional) {
            schema.put("required", !isOptional);
        }
        return schema;
    }

    public void wrapAndThrow(SerializerProvider provider, Throwable t, Object bean, String fieldName) throws Throwable {
        while ((t instanceof InvocationTargetException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw ((Error) t);
        }
        boolean wrap = provider == null || provider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            if (!wrap || !(t instanceof JsonMappingException)) {
                throw ((IOException) t);
            }
        } else if (!wrap && (t instanceof RuntimeException)) {
            throw ((RuntimeException) t);
        }
        throw JsonMappingException.wrapWithPath(t, bean, fieldName);
    }

    public void wrapAndThrow(SerializerProvider provider, Throwable t, Object bean, int index) throws Throwable {
        while ((t instanceof InvocationTargetException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw ((Error) t);
        }
        boolean wrap = provider == null || provider.isEnabled(SerializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            if (!wrap || !(t instanceof JsonMappingException)) {
                throw ((IOException) t);
            }
        } else if (!wrap && (t instanceof RuntimeException)) {
            throw ((RuntimeException) t);
        }
        throw JsonMappingException.wrapWithPath(t, bean, index);
    }

    protected boolean isDefaultSerializer(JsonSerializer<?> serializer) {
        return (serializer == null || serializer.getClass().getAnnotation(JacksonStdImpl.class) == null) ? false : true;
    }
}
