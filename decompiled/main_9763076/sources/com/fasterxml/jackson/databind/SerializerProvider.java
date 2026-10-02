package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.SerializerCache;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.impl.ReadOnlyClassToSerializerMap;
import com.fasterxml.jackson.databind.ser.impl.TypeWrappedSerializer;
import com.fasterxml.jackson.databind.ser.impl.UnknownSerializer;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class SerializerProvider {
    protected static final boolean CACHE_UNKNOWN_MAPPINGS = false;
    protected final SerializationConfig _config;
    protected DateFormat _dateFormat;
    protected JsonSerializer<Object> _keySerializer;
    protected final ReadOnlyClassToSerializerMap _knownSerializers;
    protected JsonSerializer<Object> _nullKeySerializer;
    protected JsonSerializer<Object> _nullValueSerializer;
    protected final RootNameLookup _rootNames;
    protected final Class<?> _serializationView;
    protected final SerializerCache _serializerCache;
    protected final SerializerFactory _serializerFactory;
    protected JsonSerializer<Object> _unknownTypeSerializer;
    protected static final JavaType TYPE_OBJECT = TypeFactory.defaultInstance().uncheckedSimpleType(Object.class);
    public static final JsonSerializer<Object> DEFAULT_NULL_KEY_SERIALIZER = new FailingSerializer("Null key for a Map not allowed in JSON (use a converting NullKeySerializer?)");
    public static final JsonSerializer<Object> DEFAULT_UNKNOWN_SERIALIZER = new UnknownSerializer();

    public abstract WritableObjectId findObjectId(Object obj, ObjectIdGenerator<?> objectIdGenerator);

    public abstract ObjectIdGenerator<?> objectIdGeneratorInstance(Annotated annotated, ObjectIdInfo objectIdInfo) throws JsonMappingException;

    public abstract JsonSerializer<Object> serializerInstance(Annotated annotated, Object obj) throws JsonMappingException;

    public SerializerProvider() {
        this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
        this._nullValueSerializer = NullSerializer.instance;
        this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
        this._config = null;
        this._serializerFactory = null;
        this._serializerCache = new SerializerCache();
        this._knownSerializers = null;
        this._rootNames = new RootNameLookup();
        this._serializationView = null;
    }

    protected SerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        this._unknownTypeSerializer = DEFAULT_UNKNOWN_SERIALIZER;
        this._nullValueSerializer = NullSerializer.instance;
        this._nullKeySerializer = DEFAULT_NULL_KEY_SERIALIZER;
        if (config == null) {
            throw new NullPointerException();
        }
        this._serializerFactory = f;
        this._config = config;
        this._serializerCache = src._serializerCache;
        this._unknownTypeSerializer = src._unknownTypeSerializer;
        this._keySerializer = src._keySerializer;
        this._nullValueSerializer = src._nullValueSerializer;
        this._nullKeySerializer = src._nullKeySerializer;
        this._rootNames = src._rootNames;
        this._knownSerializers = this._serializerCache.getReadOnlyLookupMap();
        this._serializationView = config.getActiveView();
    }

    public void setDefaultKeySerializer(JsonSerializer<Object> ks) {
        if (ks == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._keySerializer = ks;
    }

    public void setNullValueSerializer(JsonSerializer<Object> nvs) {
        if (nvs == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._nullValueSerializer = nvs;
    }

    public void setNullKeySerializer(JsonSerializer<Object> nks) {
        if (nks == null) {
            throw new IllegalArgumentException("Can not pass null JsonSerializer");
        }
        this._nullKeySerializer = nks;
    }

    public final SerializationConfig getConfig() {
        return this._config;
    }

    public final boolean isEnabled(MapperFeature feature) {
        return this._config.isEnabled(feature);
    }

    public final boolean isEnabled(SerializationFeature feature) {
        return this._config.isEnabled(feature);
    }

    public final boolean canOverrideAccessModifiers() {
        return this._config.canOverrideAccessModifiers();
    }

    public final AnnotationIntrospector getAnnotationIntrospector() {
        return this._config.getAnnotationIntrospector();
    }

    public final Class<?> getSerializationView() {
        return this._serializationView;
    }

    public final FilterProvider getFilterProvider() {
        return this._config.getFilterProvider();
    }

    public Locale getLocale() {
        return this._config.getLocale();
    }

    public TimeZone getTimeZone() {
        return this._config.getTimeZone();
    }

    public JavaType constructType(Type type) {
        return this._config.getTypeFactory().constructType(type);
    }

    public JavaType constructSpecializedType(JavaType baseType, Class<?> subclass) {
        return this._config.constructSpecializedType(baseType, subclass);
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public JsonSerializer<Object> findValueSerializer(Class<?> valueType, BeanProperty property) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializerUntypedValueSerializer = this._knownSerializers.untypedValueSerializer(valueType);
        if (jsonSerializerUntypedValueSerializer != null || (jsonSerializerUntypedValueSerializer = this._serializerCache.untypedValueSerializer(valueType)) != null || (jsonSerializerUntypedValueSerializer = this._serializerCache.untypedValueSerializer(this._config.constructType(valueType))) != null || (jsonSerializerUntypedValueSerializer = _createAndCacheUntypedSerializer(valueType, property)) != null) {
            return _handleContextual(jsonSerializerUntypedValueSerializer, property);
        }
        JsonSerializer<Object> ser = getUnknownTypeSerializer(valueType);
        return ser;
    }

    public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializerUntypedValueSerializer = this._knownSerializers.untypedValueSerializer(valueType);
        if (jsonSerializerUntypedValueSerializer != null || (jsonSerializerUntypedValueSerializer = this._serializerCache.untypedValueSerializer(valueType)) != null || (jsonSerializerUntypedValueSerializer = _createAndCacheUntypedSerializer(valueType, property)) != null) {
            return _handleContextual(jsonSerializerUntypedValueSerializer, property);
        }
        JsonSerializer<Object> ser = getUnknownTypeSerializer(valueType.getRawClass());
        return ser;
    }

    public JsonSerializer<Object> findTypedValueSerializer(Class<?> valueType, boolean cache, BeanProperty property) throws JsonMappingException {
        JsonSerializer<Object> ser = this._knownSerializers.typedValueSerializer(valueType);
        if (ser != null) {
            return ser;
        }
        JsonSerializer<Object> ser2 = this._serializerCache.typedValueSerializer(valueType);
        if (ser2 != null) {
            return ser2;
        }
        JsonSerializer<Object> ser3 = findValueSerializer(valueType, property);
        TypeSerializer typeSer = this._serializerFactory.createTypeSerializer(this._config, this._config.constructType(valueType));
        if (typeSer != null) {
            ser3 = new TypeWrappedSerializer(typeSer.forProperty(property), ser3);
        }
        if (cache) {
            this._serializerCache.addTypedSerializer(valueType, ser3);
        }
        return ser3;
    }

    public JsonSerializer<Object> findTypedValueSerializer(JavaType valueType, boolean cache, BeanProperty property) throws JsonMappingException {
        JsonSerializer<Object> ser = this._knownSerializers.typedValueSerializer(valueType);
        if (ser != null) {
            return ser;
        }
        JsonSerializer<Object> ser2 = this._serializerCache.typedValueSerializer(valueType);
        if (ser2 != null) {
            return ser2;
        }
        JsonSerializer<Object> ser3 = findValueSerializer(valueType, property);
        TypeSerializer typeSer = this._serializerFactory.createTypeSerializer(this._config, valueType);
        if (typeSer != null) {
            ser3 = new TypeWrappedSerializer(typeSer.forProperty(property), ser3);
        }
        if (cache) {
            this._serializerCache.addTypedSerializer(valueType, ser3);
        }
        return ser3;
    }

    public JsonSerializer<Object> findKeySerializer(JavaType keyType, BeanProperty property) throws JsonMappingException {
        JsonSerializer<Object> jsonSerializerCreateKeySerializer = this._serializerFactory.createKeySerializer(this._config, keyType);
        if (jsonSerializerCreateKeySerializer == null) {
            if (this._keySerializer == null) {
                jsonSerializerCreateKeySerializer = StdKeySerializers.getStdKeySerializer(keyType);
            } else {
                jsonSerializerCreateKeySerializer = this._keySerializer;
            }
        }
        return _handleContextualResolvable(jsonSerializerCreateKeySerializer, property);
    }

    public JsonSerializer<Object> getDefaultNullKeySerializer() {
        return this._nullKeySerializer;
    }

    public JsonSerializer<Object> getDefaultNullValueSerializer() {
        return this._nullValueSerializer;
    }

    public JsonSerializer<Object> findNullKeySerializer(JavaType serializationType, BeanProperty property) throws JsonMappingException {
        return getDefaultNullKeySerializer();
    }

    public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
        return getDefaultNullValueSerializer();
    }

    public JsonSerializer<Object> getUnknownTypeSerializer(Class<?> unknownType) {
        return this._unknownTypeSerializer;
    }

    public final void defaultSerializeValue(Object value, JsonGenerator jgen) throws IOException {
        if (value == null) {
            getDefaultNullValueSerializer().serialize(null, jgen, this);
        } else {
            Class<?> cls = value.getClass();
            findTypedValueSerializer(cls, true, (BeanProperty) null).serialize(value, jgen, this);
        }
    }

    public final void defaultSerializeField(String fieldName, Object value, JsonGenerator jgen) throws IOException {
        jgen.writeFieldName(fieldName);
        if (value == null) {
            getDefaultNullValueSerializer().serialize(null, jgen, this);
        } else {
            Class<?> cls = value.getClass();
            findTypedValueSerializer(cls, true, (BeanProperty) null).serialize(value, jgen, this);
        }
    }

    public final void defaultSerializeDateValue(long timestamp, JsonGenerator jgen) throws IOException {
        if (isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
            jgen.writeNumber(timestamp);
        } else {
            jgen.writeString(_dateFormat().format(new Date(timestamp)));
        }
    }

    public final void defaultSerializeDateValue(Date date, JsonGenerator jgen) throws IOException {
        if (isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
            jgen.writeNumber(date.getTime());
        } else {
            jgen.writeString(_dateFormat().format(date));
        }
    }

    public void defaultSerializeDateKey(long timestamp, JsonGenerator jgen) throws IOException {
        if (isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
            jgen.writeFieldName(String.valueOf(timestamp));
        } else {
            jgen.writeFieldName(_dateFormat().format(new Date(timestamp)));
        }
    }

    public void defaultSerializeDateKey(Date date, JsonGenerator jgen) throws IOException {
        if (isEnabled(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS)) {
            jgen.writeFieldName(String.valueOf(date.getTime()));
        } else {
            jgen.writeFieldName(_dateFormat().format(date));
        }
    }

    public final void defaultSerializeNull(JsonGenerator jgen) throws IOException {
        getDefaultNullValueSerializer().serialize(null, jgen, this);
    }

    protected void _reportIncompatibleRootType(Object value, JavaType rootType) throws IOException {
        if (rootType.isPrimitive()) {
            Class<?> wrapperType = ClassUtil.wrapperType(rootType.getRawClass());
            if (wrapperType.isAssignableFrom(value.getClass())) {
                return;
            }
        }
        throw new JsonMappingException("Incompatible types: declared root type (" + rootType + ") vs " + value.getClass().getName());
    }

    protected JsonSerializer<Object> _findExplicitUntypedSerializer(Class<?> runtimeType, BeanProperty property) {
        JsonSerializer<Object> ser = this._knownSerializers.untypedValueSerializer(runtimeType);
        if (ser != null) {
            return ser;
        }
        JsonSerializer<Object> ser2 = this._serializerCache.untypedValueSerializer(runtimeType);
        if (ser2 != null) {
            return ser2;
        }
        try {
            return _createAndCacheUntypedSerializer(runtimeType, property);
        } catch (Exception e) {
            return null;
        }
    }

    protected JsonSerializer<Object> _createAndCacheUntypedSerializer(Class<?> type, BeanProperty property) throws JsonMappingException {
        try {
            JsonSerializer<Object> ser = _createUntypedSerializer(this._config.constructType(type), property);
            if (ser != null) {
                this._serializerCache.addAndResolveNonTypedSerializer(type, ser, this);
            }
            return ser;
        } catch (IllegalArgumentException iae) {
            throw new JsonMappingException(iae.getMessage(), null, iae);
        }
    }

    protected JsonSerializer<Object> _createAndCacheUntypedSerializer(JavaType type, BeanProperty property) throws JsonMappingException {
        try {
            JsonSerializer<Object> ser = _createUntypedSerializer(type, property);
            if (ser != null) {
                this._serializerCache.addAndResolveNonTypedSerializer(type, ser, this);
            }
            return ser;
        } catch (IllegalArgumentException iae) {
            throw new JsonMappingException(iae.getMessage(), null, iae);
        }
    }

    protected JsonSerializer<Object> _createUntypedSerializer(JavaType type, BeanProperty property) throws JsonMappingException {
        return this._serializerFactory.createSerializer(this, type, property);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected JsonSerializer<Object> _handleContextualResolvable(JsonSerializer<?> jsonSerializer, BeanProperty property) throws JsonMappingException {
        if (jsonSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer) jsonSerializer).resolve(this);
        }
        return _handleContextual(jsonSerializer, property);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected JsonSerializer<Object> _handleResolvable(JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        if (jsonSerializer instanceof ResolvableSerializer) {
            ((ResolvableSerializer) jsonSerializer).resolve(this);
        }
        return jsonSerializer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected JsonSerializer<Object> _handleContextual(JsonSerializer<?> jsonSerializer, BeanProperty property) throws JsonMappingException {
        if (jsonSerializer instanceof ContextualSerializer) {
            JsonSerializer<?> ser = ((ContextualSerializer) jsonSerializer).createContextual(this, property);
            return ser;
        }
        return jsonSerializer;
    }

    protected final DateFormat _dateFormat() {
        if (this._dateFormat != null) {
            return this._dateFormat;
        }
        DateFormat df = (DateFormat) this._config.getDateFormat().clone();
        this._dateFormat = df;
        return df;
    }
}
