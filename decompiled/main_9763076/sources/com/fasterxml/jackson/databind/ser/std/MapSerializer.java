package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class MapSerializer extends ContainerSerializer<Map<?, ?>> implements ContextualSerializer {
    protected static final JavaType UNSPECIFIED_TYPE = TypeFactory.unknownType();
    protected PropertySerializerMap _dynamicValueSerializers;
    protected final HashSet<String> _ignoredEntries;
    protected JsonSerializer<Object> _keySerializer;
    protected final JavaType _keyType;
    protected final BeanProperty _property;
    protected JsonSerializer<Object> _valueSerializer;
    protected final JavaType _valueType;
    protected final boolean _valueTypeIsStatic;
    protected final TypeSerializer _valueTypeSerializer;

    protected MapSerializer(HashSet<String> ignoredEntries, JavaType keyType, JavaType valueType, boolean valueTypeIsStatic, TypeSerializer vts, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer) {
        super(Map.class, false);
        this._ignoredEntries = ignoredEntries;
        this._keyType = keyType;
        this._valueType = valueType;
        this._valueTypeIsStatic = valueTypeIsStatic;
        this._valueTypeSerializer = vts;
        this._keySerializer = keySerializer;
        this._valueSerializer = valueSerializer;
        this._dynamicValueSerializers = PropertySerializerMap.emptyMap();
        this._property = null;
    }

    protected MapSerializer(MapSerializer src, BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, HashSet<String> ignored) {
        super(Map.class, false);
        this._ignoredEntries = ignored;
        this._keyType = src._keyType;
        this._valueType = src._valueType;
        this._valueTypeIsStatic = src._valueTypeIsStatic;
        this._valueTypeSerializer = src._valueTypeSerializer;
        this._keySerializer = keySerializer;
        this._valueSerializer = valueSerializer;
        this._dynamicValueSerializers = src._dynamicValueSerializers;
        this._property = property;
    }

    protected MapSerializer(MapSerializer src, TypeSerializer vts) {
        super(Map.class, false);
        this._ignoredEntries = src._ignoredEntries;
        this._keyType = src._keyType;
        this._valueType = src._valueType;
        this._valueTypeIsStatic = src._valueTypeIsStatic;
        this._valueTypeSerializer = vts;
        this._keySerializer = src._keySerializer;
        this._valueSerializer = src._valueSerializer;
        this._dynamicValueSerializers = src._dynamicValueSerializers;
        this._property = src._property;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public MapSerializer _withValueTypeSerializer(TypeSerializer vts) {
        return new MapSerializer(this, vts);
    }

    public MapSerializer withResolved(BeanProperty property, JsonSerializer<?> keySerializer, JsonSerializer<?> valueSerializer, HashSet<String> ignored) {
        return new MapSerializer(this, property, keySerializer, valueSerializer, ignored);
    }

    public static MapSerializer construct(String[] ignoredList, JavaType mapType, boolean staticValueType, TypeSerializer vts, JsonSerializer<Object> keySerializer, JsonSerializer<Object> valueSerializer) {
        JavaType keyType;
        JavaType valueType;
        HashSet<String> ignoredEntries = toSet(ignoredList);
        if (mapType == null) {
            valueType = UNSPECIFIED_TYPE;
            keyType = valueType;
        } else {
            keyType = mapType.getKeyType();
            valueType = mapType.getContentType();
        }
        if (!staticValueType) {
            staticValueType = valueType != null && valueType.isFinal();
        }
        return new MapSerializer(ignoredEntries, keyType, valueType, staticValueType, vts, keySerializer, valueSerializer);
    }

    private static HashSet<String> toSet(String[] ignoredEntries) {
        if (ignoredEntries == null || ignoredEntries.length == 0) {
            return null;
        }
        HashSet<String> result = new HashSet<>(ignoredEntries.length);
        for (String prop : ignoredEntries) {
            result.add(prop);
        }
        return result;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.fasterxml.jackson.databind.ser.std.MapSerializer] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        ?? CreateContextual2;
        String[] strArrFindPropertiesToIgnore;
        Object obj = this._valueSerializer;
        if (obj == null) {
            if (this._valueTypeIsStatic) {
                CreateContextual = obj;
                CreateContextual = serializerProvider.findValueSerializer(this._valueType, beanProperty);
            }
        } else if (obj instanceof ContextualSerializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualSerializer) obj).createContextual(serializerProvider, beanProperty);
        }
        CreateContextual = obj;
        CreateContextual = obj;
        Object obj2 = this._keySerializer;
        if (obj2 == null) {
            CreateContextual2 = serializerProvider.findKeySerializer(this._keyType, beanProperty);
        } else if (obj2 instanceof ContextualSerializer) {
            CreateContextual2 = obj2;
            CreateContextual2 = ((ContextualSerializer) obj2).createContextual(serializerProvider, beanProperty);
        }
        CreateContextual2 = obj2;
        HashSet<String> hashSet = this._ignoredEntries;
        AnnotationIntrospector annotationIntrospector = serializerProvider.getAnnotationIntrospector();
        if (annotationIntrospector != null && beanProperty != null && (strArrFindPropertiesToIgnore = annotationIntrospector.findPropertiesToIgnore(beanProperty.getMember())) != null) {
            hashSet = hashSet == null ? new HashSet<>() : new HashSet<>(hashSet);
            for (String str : strArrFindPropertiesToIgnore) {
                hashSet.add(str);
            }
        }
        return withResolved(beanProperty, CreateContextual2, CreateContextual, hashSet);
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
    public boolean isEmpty(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(Map<?, ?> value) {
        return value.size() == 1;
    }

    public JsonSerializer<?> getKeySerializer() {
        return this._keySerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Map<?, ?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (!value.isEmpty()) {
            if (provider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
                value = _orderEntries(value);
            }
            if (this._valueSerializer != null) {
                serializeFieldsUsing(value, jgen, provider, this._valueSerializer);
            } else {
                serializeFields(value, jgen, provider);
            }
        }
        jgen.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(Map<?, ?> value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForObject(value, jgen);
        if (!value.isEmpty()) {
            if (provider.isEnabled(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)) {
                value = _orderEntries(value);
            }
            if (this._valueSerializer != null) {
                serializeFieldsUsing(value, jgen, provider, this._valueSerializer);
            } else {
                serializeFields(value, jgen, provider);
            }
        }
        typeSer.writeTypeSuffixForObject(value, jgen);
    }

    public void serializeFields(Map<?, ?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._valueTypeSerializer != null) {
            serializeTypedFields(value, jgen, provider);
            return;
        }
        JsonSerializer<Object> keySerializer = this._keySerializer;
        HashSet<String> ignored = this._ignoredEntries;
        boolean skipNulls = !provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES);
        PropertySerializerMap serializers = this._dynamicValueSerializers;
        for (Map.Entry<?, ?> entry : value.entrySet()) {
            Object valueElem = entry.getValue();
            Object keyElem = entry.getKey();
            if (keyElem == null) {
                provider.findNullKeySerializer(this._keyType, this._property).serialize(null, jgen, provider);
            } else if (!skipNulls || valueElem != null) {
                if (ignored == null || !ignored.contains(keyElem)) {
                    keySerializer.serialize(keyElem, jgen, provider);
                }
            }
            if (valueElem == null) {
                provider.defaultSerializeNull(jgen);
            } else {
                Class<?> cc = valueElem.getClass();
                JsonSerializer<Object> serializer = serializers.serializerFor(cc);
                if (serializer == null) {
                    if (this._valueType.hasGenericTypes()) {
                        serializer = _findAndAddDynamic(serializers, provider.constructSpecializedType(this._valueType, cc), provider);
                    } else {
                        serializer = _findAndAddDynamic(serializers, cc, provider);
                    }
                    serializers = this._dynamicValueSerializers;
                }
                try {
                    serializer.serialize(valueElem, jgen, provider);
                } catch (Exception e) {
                    String keyDesc = Constants.MAIN_VERSION_TAG + keyElem;
                    wrapAndThrow(provider, e, value, keyDesc);
                }
            }
        }
    }

    protected void serializeFieldsUsing(Map<?, ?> value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
        JsonSerializer<Object> keySerializer = this._keySerializer;
        HashSet<String> ignored = this._ignoredEntries;
        TypeSerializer typeSer = this._valueTypeSerializer;
        boolean skipNulls = !provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES);
        for (Map.Entry<?, ?> entry : value.entrySet()) {
            Object valueElem = entry.getValue();
            Object keyElem = entry.getKey();
            if (keyElem == null) {
                provider.findNullKeySerializer(this._keyType, this._property).serialize(null, jgen, provider);
            } else if (!skipNulls || valueElem != null) {
                if (ignored == null || !ignored.contains(keyElem)) {
                    keySerializer.serialize(keyElem, jgen, provider);
                }
            }
            if (valueElem == null) {
                provider.defaultSerializeNull(jgen);
            } else if (typeSer == null) {
                try {
                    ser.serialize(valueElem, jgen, provider);
                } catch (Exception e) {
                    String keyDesc = Constants.MAIN_VERSION_TAG + keyElem;
                    wrapAndThrow(provider, e, value, keyDesc);
                }
            } else {
                ser.serializeWithType(valueElem, jgen, provider, typeSer);
            }
        }
    }

    protected void serializeTypedFields(Map<?, ?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        JsonSerializer<Object> currSerializer;
        JsonSerializer<Object> keySerializer = this._keySerializer;
        JsonSerializer<Object> prevValueSerializer = null;
        Class<?> prevValueClass = null;
        HashSet<String> ignored = this._ignoredEntries;
        boolean skipNulls = !provider.isEnabled(SerializationFeature.WRITE_NULL_MAP_VALUES);
        for (Map.Entry<?, ?> entry : value.entrySet()) {
            Object valueElem = entry.getValue();
            Object keyElem = entry.getKey();
            if (keyElem == null) {
                provider.findNullKeySerializer(this._keyType, this._property).serialize(null, jgen, provider);
            } else if (!skipNulls || valueElem != null) {
                if (ignored == null || !ignored.contains(keyElem)) {
                    keySerializer.serialize(keyElem, jgen, provider);
                }
            }
            if (valueElem == null) {
                provider.defaultSerializeNull(jgen);
            } else {
                Class<?> cc = valueElem.getClass();
                if (cc == prevValueClass) {
                    currSerializer = prevValueSerializer;
                } else {
                    currSerializer = provider.findValueSerializer(cc, this._property);
                    prevValueSerializer = currSerializer;
                    prevValueClass = cc;
                }
                try {
                    currSerializer.serializeWithType(valueElem, jgen, provider, this._valueTypeSerializer);
                } catch (Exception e) {
                    String keyDesc = Constants.MAIN_VERSION_TAG + keyElem;
                    wrapAndThrow(provider, e, value, keyDesc);
                }
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        ObjectNode o = createSchemaNode("object", true);
        return o;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicValueSerializers = result.map;
        }
        return result.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, JavaType type, SerializerProvider provider) throws JsonMappingException {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSerializer(type, provider, this._property);
        if (map != result.map) {
            this._dynamicValueSerializers = result.map;
        }
        return result.serializer;
    }

    protected Map<?, ?> _orderEntries(Map<?, ?> input) {
        return input instanceof SortedMap ? input : new TreeMap<>(input);
    }
}
