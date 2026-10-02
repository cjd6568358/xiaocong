package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsonschema.SchemaAware;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AsArraySerializerBase<T> extends ContainerSerializer<T> implements ContextualSerializer {
    protected PropertySerializerMap _dynamicSerializers;
    protected final JsonSerializer<Object> _elementSerializer;
    protected final JavaType _elementType;
    protected final BeanProperty _property;
    protected final boolean _staticTyping;
    protected final TypeSerializer _valueTypeSerializer;

    protected abstract void serializeContents(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    public abstract AsArraySerializerBase<T> withResolved(BeanProperty beanProperty, TypeSerializer typeSerializer, JsonSerializer<?> jsonSerializer);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected AsArraySerializerBase(Class<?> cls, JavaType et, boolean staticTyping, TypeSerializer vts, BeanProperty property, JsonSerializer<Object> elementSerializer) {
        super(cls, false);
        boolean z = false;
        this._elementType = et;
        if (staticTyping || (et != null && et.isFinal())) {
            z = true;
        }
        this._staticTyping = z;
        this._valueTypeSerializer = vts;
        this._property = property;
        this._elementSerializer = elementSerializer;
        this._dynamicSerializers = PropertySerializerMap.emptyMap();
    }

    protected AsArraySerializerBase(AsArraySerializerBase<?> src, BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer) {
        super(src);
        this._elementType = src._elementType;
        this._staticTyping = src._staticTyping;
        this._valueTypeSerializer = vts;
        this._property = src._property;
        this._elementSerializer = elementSerializer;
        this._dynamicSerializers = src._dynamicSerializers;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.fasterxml.jackson.databind.JsonSerializer<?>, com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase, com.fasterxml.jackson.databind.ser.std.AsArraySerializerBase<T>] */
    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        TypeSerializer typeSerializerForProperty = this._valueTypeSerializer;
        if (typeSerializerForProperty != null) {
            typeSerializerForProperty = typeSerializerForProperty.forProperty(beanProperty);
        }
        Object obj = this._elementSerializer;
        if (obj == null) {
            if (this._staticTyping && this._elementType != null) {
                CreateContextual = obj;
                CreateContextual = obj;
                CreateContextual = serializerProvider.findValueSerializer(this._elementType, beanProperty);
            }
        } else if (obj instanceof ContextualSerializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualSerializer) obj).createContextual(serializerProvider, beanProperty);
        }
        CreateContextual = obj;
        CreateContextual = obj;
        CreateContextual = obj;
        CreateContextual = obj;
        if (CreateContextual != this._elementSerializer || beanProperty != this._property || this._valueTypeSerializer != typeSerializerForProperty) {
            return withResolved(beanProperty, typeSerializerForProperty, CreateContextual);
        }
        return this;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return this._elementType;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED) && hasSingleElement(value)) {
            serializeContents(value, jgen, provider);
            return;
        }
        jgen.writeStartArray();
        serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public final void serializeWithType(T value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForArray(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        ObjectNode o = createSchemaNode("array", true);
        JavaType contentType = null;
        if (typeHint != null) {
            JavaType javaType = provider.constructType(typeHint);
            contentType = javaType.getContentType();
            if (contentType == null && (typeHint instanceof ParameterizedType)) {
                Type[] typeArgs = ((ParameterizedType) typeHint).getActualTypeArguments();
                if (typeArgs.length == 1) {
                    contentType = provider.constructType(typeArgs[0]);
                }
            }
        }
        if (contentType == null && this._elementType != null) {
            contentType = this._elementType;
        }
        if (contentType != null) {
            JsonNode schemaNode = null;
            if (contentType.getRawClass() != Object.class) {
                Object objFindValueSerializer = provider.findValueSerializer(contentType, this._property);
                if (objFindValueSerializer instanceof SchemaAware) {
                    schemaNode = ((SchemaAware) objFindValueSerializer).getSchema(provider, null);
                }
            }
            if (schemaNode == null) {
                schemaNode = JsonSchema.getDefaultSchemaNode();
            }
            o.put("items", schemaNode);
        }
        return o;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicSerializers = result.map;
        }
        return result.serializer;
    }
}
