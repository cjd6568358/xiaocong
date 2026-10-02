package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class SerializableSerializer extends StdSerializer<JsonSerializable> {
    public static final SerializableSerializer instance = new SerializableSerializer();
    private static final AtomicReference<ObjectMapper> _mapperReference = new AtomicReference<>();

    protected SerializableSerializer() {
        super(JsonSerializable.class);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(JsonSerializable value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        value.serialize(jgen, provider);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public final void serializeWithType(JsonSerializable value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        value.serializeWithType(jgen, provider, typeSer);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode objectNode = createObjectNode();
        String schemaType = "any";
        String objectProperties = null;
        String itemDefinition = null;
        if (typeHint != null) {
            Class<?> rawClass = TypeFactory.rawClass(typeHint);
            if (rawClass.isAnnotationPresent(JsonSerializableSchema.class)) {
                JsonSerializableSchema schemaInfo = (JsonSerializableSchema) rawClass.getAnnotation(JsonSerializableSchema.class);
                schemaType = schemaInfo.schemaType();
                if (!JsonSerializableSchema.NO_VALUE.equals(schemaInfo.schemaObjectPropertiesDefinition())) {
                    objectProperties = schemaInfo.schemaObjectPropertiesDefinition();
                }
                if (!JsonSerializableSchema.NO_VALUE.equals(schemaInfo.schemaItemDefinition())) {
                    itemDefinition = schemaInfo.schemaItemDefinition();
                }
            }
        }
        objectNode.put("type", schemaType);
        if (objectProperties != null) {
            try {
                objectNode.put("properties", _getObjectMapper().readTree(objectProperties));
            } catch (IOException e) {
                throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaObjectPropertiesDefinition value");
            }
        }
        if (itemDefinition != null) {
            try {
                objectNode.put("items", _getObjectMapper().readTree(itemDefinition));
            } catch (IOException e2) {
                throw new JsonMappingException("Failed to parse @JsonSerializableSchema.schemaItemDefinition value");
            }
        }
        return objectNode;
    }

    private static final synchronized ObjectMapper _getObjectMapper() {
        ObjectMapper mapper;
        mapper = _mapperReference.get();
        if (mapper == null) {
            mapper = new ObjectMapper();
            _mapperReference.set(mapper);
        }
        return mapper;
    }
}
