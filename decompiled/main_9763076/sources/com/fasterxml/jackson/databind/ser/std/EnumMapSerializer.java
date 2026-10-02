package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.util.EnumValues;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class EnumMapSerializer extends ContainerSerializer<EnumMap<? extends Enum<?>, ?>> implements ContextualSerializer {
    protected final EnumValues _keyEnums;
    protected final BeanProperty _property;
    protected final boolean _staticTyping;
    protected final JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final TypeSerializer _valueTypeSerializer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnumMapSerializer(JavaType valueType, boolean staticTyping, EnumValues keyEnums, TypeSerializer vts, JsonSerializer<Object> valueSerializer) {
        super(EnumMap.class, false);
        boolean z = false;
        this._property = null;
        if (staticTyping || (valueType != null && valueType.isFinal())) {
            z = true;
        }
        this._staticTyping = z;
        this._valueType = valueType;
        this._keyEnums = keyEnums;
        this._valueTypeSerializer = vts;
        this._valueSerializer = valueSerializer;
    }

    public EnumMapSerializer(EnumMapSerializer src, BeanProperty property, JsonSerializer<?> ser) {
        super(src);
        this._property = property;
        this._staticTyping = src._staticTyping;
        this._valueType = src._valueType;
        this._keyEnums = src._keyEnums;
        this._valueTypeSerializer = src._valueTypeSerializer;
        this._valueSerializer = ser;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public EnumMapSerializer _withValueTypeSerializer(TypeSerializer vts) {
        return new EnumMapSerializer(this._valueType, this._staticTyping, this._keyEnums, vts, this._valueSerializer);
    }

    public EnumMapSerializer withValueSerializer(BeanProperty prop, JsonSerializer<?> ser) {
        return (this._property == prop && ser == this._valueSerializer) ? this : new EnumMapSerializer(this, prop, ser);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider provider, BeanProperty property) throws JsonMappingException {
        if (this._valueSerializer == null) {
            if (this._staticTyping) {
                return withValueSerializer(property, provider.findValueSerializer(this._valueType, property));
            }
            return this;
        }
        if (this._valueSerializer instanceof ContextualSerializer) {
            return withValueSerializer(property, ((ContextualSerializer) this._valueSerializer).createContextual(provider, property));
        }
        return this;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return this._valueType;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._valueSerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(EnumMap<? extends Enum<?>, ?> value) {
        return value == null || value.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(EnumMap<? extends Enum<?>, ?> value) {
        return value.size() == 1;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(EnumMap<? extends Enum<?>, ?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (!value.isEmpty()) {
            serializeContents(value, jgen, provider);
        }
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(EnumMap<? extends Enum<?>, ?> value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForObject(value, jgen);
        if (!value.isEmpty()) {
            serializeContents(value, jgen, provider);
        }
        typeSer.writeTypeSuffixForObject(value, jgen);
    }

    protected void serializeContents(EnumMap<? extends Enum<?>, ?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        JsonSerializer<Object> currSerializer;
        if (this._valueSerializer != null) {
            serializeContentsUsing(value, jgen, provider, this._valueSerializer);
            return;
        }
        JsonSerializer<Object> prevSerializer = null;
        Class<?> prevClass = null;
        EnumValues keyEnums = this._keyEnums;
        Iterator i$ = value.entrySet().iterator();
        while (i$.hasNext()) {
            Map.Entry<? extends Enum<?>, ?> entry = (Map.Entry) i$.next();
            Enum<?> key = (Enum) entry.getKey();
            if (keyEnums == null) {
                StdSerializer<?> ser = (StdSerializer) provider.findValueSerializer(key.getDeclaringClass(), this._property);
                keyEnums = ((EnumSerializer) ser).getEnumValues();
            }
            jgen.writeFieldName(keyEnums.serializedValueFor(key));
            Object valueElem = entry.getValue();
            if (valueElem == null) {
                provider.defaultSerializeNull(jgen);
            } else {
                Class<?> cc = valueElem.getClass();
                if (cc == prevClass) {
                    currSerializer = prevSerializer;
                } else {
                    currSerializer = provider.findValueSerializer(cc, this._property);
                    prevSerializer = currSerializer;
                    prevClass = cc;
                }
                try {
                    currSerializer.serialize(valueElem, jgen, provider);
                } catch (Exception e) {
                    wrapAndThrow(provider, e, value, ((Enum) entry.getKey()).name());
                }
            }
        }
    }

    protected void serializeContentsUsing(EnumMap<? extends Enum<?>, ?> value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> valueSer) throws IOException {
        EnumValues keyEnums = this._keyEnums;
        Iterator i$ = value.entrySet().iterator();
        while (i$.hasNext()) {
            Map.Entry<? extends Enum<?>, ?> entry = (Map.Entry) i$.next();
            Enum<?> key = (Enum) entry.getKey();
            if (keyEnums == null) {
                StdSerializer<?> ser = (StdSerializer) provider.findValueSerializer(key.getDeclaringClass(), this._property);
                keyEnums = ((EnumSerializer) ser).getEnumValues();
            }
            jgen.writeFieldName(keyEnums.serializedValueFor(key));
            Object valueElem = entry.getValue();
            if (valueElem == null) {
                provider.defaultSerializeNull(jgen);
            } else {
                try {
                    valueSer.serialize(valueElem, jgen, provider);
                } catch (Exception e) {
                    wrapAndThrow(provider, e, value, ((Enum) entry.getKey()).name());
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode o = createSchemaNode("object", true);
        if (typeHint instanceof ParameterizedType) {
            Type[] typeArgs = ((ParameterizedType) typeHint).getActualTypeArguments();
            if (typeArgs.length == 2) {
                JavaType enumType = provider.constructType(typeArgs[0]);
                JavaType valueType = provider.constructType(typeArgs[1]);
                ObjectNode propsNode = JsonNodeFactory.instance.objectNode();
                for (Enum<?> enumValue : (Enum[]) enumType.getRawClass().getEnumConstants()) {
                    Object objFindValueSerializer = provider.findValueSerializer(valueType.getRawClass(), this._property);
                    JsonNode schemaNode = objFindValueSerializer instanceof SchemaAware ? ((SchemaAware) objFindValueSerializer).getSchema(provider, null) : JsonSchema.getDefaultSchemaNode();
                    propsNode.put(provider.getConfig().getAnnotationIntrospector().findEnumValue(enumValue), schemaNode);
                }
                o.put("properties", propsNode);
            }
        }
        return o;
    }
}
