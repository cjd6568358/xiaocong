package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
import com.fasterxml.jackson.databind.ser.impl.StringArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumMapSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.ser.std.InetAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import com.fasterxml.jackson.databind.ser.std.StdContainerSerializers;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BasicSerializerFactory extends SerializerFactory {
    protected static final HashMap<String, JsonSerializer<?>> _concrete = new HashMap<>();
    protected static final HashMap<String, Class<? extends JsonSerializer<?>>> _concreteLazy = new HashMap<>();
    protected final SerializerFactoryConfig _factoryConfig;
    protected OptionalHandlerFactory optionalHandlers = OptionalHandlerFactory.instance;

    @Override // com.fasterxml.jackson.databind.ser.SerializerFactory
    public abstract JsonSerializer<Object> createSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanProperty beanProperty) throws JsonMappingException;

    protected abstract Iterable<Serializers> customSerializers();

    public abstract SerializerFactory withConfig(SerializerFactoryConfig serializerFactoryConfig);

    static {
        _concrete.put(String.class.getName(), new StringSerializer());
        ToStringSerializer sls = ToStringSerializer.instance;
        _concrete.put(StringBuffer.class.getName(), sls);
        _concrete.put(StringBuilder.class.getName(), sls);
        _concrete.put(Character.class.getName(), sls);
        _concrete.put(Character.TYPE.getName(), sls);
        NumberSerializers.addAll(_concrete);
        _concrete.put(Boolean.TYPE.getName(), new BooleanSerializer(true));
        _concrete.put(Boolean.class.getName(), new BooleanSerializer(false));
        JsonSerializer<?> ns = new NumberSerializers.NumberSerializer();
        _concrete.put(BigInteger.class.getName(), ns);
        _concrete.put(BigDecimal.class.getName(), ns);
        _concrete.put(Calendar.class.getName(), CalendarSerializer.instance);
        DateSerializer dateSer = DateSerializer.instance;
        _concrete.put(Date.class.getName(), dateSer);
        _concrete.put(Timestamp.class.getName(), dateSer);
        _concrete.put(java.sql.Date.class.getName(), new SqlDateSerializer());
        _concrete.put(Time.class.getName(), new SqlTimeSerializer());
        for (Map.Entry<Class<?>, Object> en : new StdJdkSerializers().provide()) {
            Object value = en.getValue();
            if (value instanceof JsonSerializer) {
                _concrete.put(en.getKey().getName(), (JsonSerializer) value);
            } else if (value instanceof Class) {
                Class<? extends JsonSerializer<?>> cls = (Class) value;
                _concreteLazy.put(en.getKey().getName(), cls);
            } else {
                throw new IllegalStateException("Internal error: unrecognized value of type " + en.getClass().getName());
            }
        }
        _concreteLazy.put(TokenBuffer.class.getName(), TokenBufferSerializer.class);
    }

    protected BasicSerializerFactory(SerializerFactoryConfig config) {
        this._factoryConfig = config == null ? new SerializerFactoryConfig() : config;
    }

    @Override // com.fasterxml.jackson.databind.ser.SerializerFactory
    public final SerializerFactory withAdditionalSerializers(Serializers additional) {
        return withConfig(this._factoryConfig.withAdditionalSerializers(additional));
    }

    @Override // com.fasterxml.jackson.databind.ser.SerializerFactory
    public final SerializerFactory withAdditionalKeySerializers(Serializers additional) {
        return withConfig(this._factoryConfig.withAdditionalKeySerializers(additional));
    }

    @Override // com.fasterxml.jackson.databind.ser.SerializerFactory
    public final SerializerFactory withSerializerModifier(BeanSerializerModifier modifier) {
        return withConfig(this._factoryConfig.withSerializerModifier(modifier));
    }

    @Override // com.fasterxml.jackson.databind.ser.SerializerFactory
    public JsonSerializer<Object> createKeySerializer(SerializationConfig config, JavaType type) {
        if (!this._factoryConfig.hasKeySerializers()) {
            return null;
        }
        BeanDescription beanDesc = config.introspectClassAnnotations(type.getRawClass());
        JsonSerializer<?> ser = null;
        for (Serializers serializers : this._factoryConfig.keySerializers()) {
            ser = serializers.findSerializer(config, type, beanDesc);
            if (ser != null) {
                return ser;
            }
        }
        return ser;
    }

    @Override // com.fasterxml.jackson.databind.ser.SerializerFactory
    public TypeSerializer createTypeSerializer(SerializationConfig config, JavaType baseType) {
        BeanDescription bean = config.introspectClassAnnotations(baseType.getRawClass());
        AnnotatedClass ac = bean.getClassInfo();
        AnnotationIntrospector ai = config.getAnnotationIntrospector();
        TypeResolverBuilder<?> b = ai.findTypeResolver(config, ac, baseType);
        Collection<NamedType> subtypes = null;
        if (b == null) {
            b = config.getDefaultTyper(baseType);
        } else {
            subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(ac, config, ai);
        }
        if (b == null) {
            return null;
        }
        return b.buildTypeSerializer(config, baseType, subtypes);
    }

    public final JsonSerializer<?> getNullSerializer() {
        return NullSerializer.instance;
    }

    protected final JsonSerializer<?> findSerializerByLookup(JavaType type, SerializationConfig config, BeanDescription beanDesc, boolean staticTyping) {
        Class<?> raw = type.getRawClass();
        String clsName = raw.getName();
        JsonSerializer<?> ser = _concrete.get(clsName);
        if (ser == null) {
            Class<? extends JsonSerializer<?>> serClass = _concreteLazy.get(clsName);
            if (serClass != null) {
                try {
                    return serClass.newInstance();
                } catch (Exception e) {
                    throw new IllegalStateException("Failed to instantiate standard serializer (of type " + serClass.getName() + "): " + e.getMessage(), e);
                }
            }
            return null;
        }
        return ser;
    }

    protected final JsonSerializer<?> findSerializerByAnnotations(SerializerProvider prov, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> raw = type.getRawClass();
        if (JsonSerializable.class.isAssignableFrom(raw)) {
            return SerializableSerializer.instance;
        }
        AnnotatedMethod valueMethod = beanDesc.findJsonValueMethod();
        if (valueMethod != null) {
            Method m = valueMethod.getAnnotated();
            if (prov.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess(m);
            }
            JsonSerializer<Object> ser = findSerializerFromAnnotation(prov, valueMethod);
            return new JsonValueSerializer(m, ser);
        }
        return null;
    }

    protected final JsonSerializer<?> findSerializerByPrimaryType(SerializerProvider prov, JavaType type, BeanDescription beanDesc, boolean staticTyping) throws JsonMappingException {
        Class<?> raw = type.getRawClass();
        if (InetAddress.class.isAssignableFrom(raw)) {
            return InetAddressSerializer.instance;
        }
        if (TimeZone.class.isAssignableFrom(raw)) {
            return TimeZoneSerializer.instance;
        }
        if (Charset.class.isAssignableFrom(raw)) {
            return ToStringSerializer.instance;
        }
        JsonSerializer<?> ser = this.optionalHandlers.findSerializer(prov.getConfig(), type);
        if (ser == null) {
            if (Number.class.isAssignableFrom(raw)) {
                return NumberSerializers.NumberSerializer.instance;
            }
            if (Enum.class.isAssignableFrom(raw)) {
                return EnumSerializer.construct(raw, prov.getConfig(), beanDesc);
            }
            if (Calendar.class.isAssignableFrom(raw)) {
                return CalendarSerializer.instance;
            }
            if (Date.class.isAssignableFrom(raw)) {
                return DateSerializer.instance;
            }
            return null;
        }
        return ser;
    }

    protected final JsonSerializer<?> findSerializerByAddonType(SerializationConfig config, JavaType javaType, BeanDescription beanDesc, boolean staticTyping) throws JsonMappingException {
        Class<?> type = javaType.getRawClass();
        if (Iterator.class.isAssignableFrom(type)) {
            return buildIteratorSerializer(config, javaType, beanDesc, staticTyping);
        }
        if (Iterable.class.isAssignableFrom(type)) {
            return buildIterableSerializer(config, javaType, beanDesc, staticTyping);
        }
        if (CharSequence.class.isAssignableFrom(type)) {
            return ToStringSerializer.instance;
        }
        return null;
    }

    protected JsonSerializer<Object> findSerializerFromAnnotation(SerializerProvider prov, Annotated a) throws JsonMappingException {
        Object serDef = prov.getAnnotationIntrospector().findSerializer(a);
        if (serDef == null) {
            return null;
        }
        return prov.serializerInstance(a, serDef);
    }

    protected JsonSerializer<?> buildContainerSerializer(SerializerProvider prov, JavaType type, BeanDescription beanDesc, BeanProperty property, boolean staticTyping) throws JsonMappingException {
        SerializationConfig config = prov.getConfig();
        JavaType elementType = type.getContentType();
        TypeSerializer elementTypeSerializer = createTypeSerializer(config, elementType);
        if (elementTypeSerializer != null) {
            staticTyping = false;
        }
        JsonSerializer<Object> elementValueSerializer = _findContentSerializer(prov, beanDesc.getClassInfo(), property);
        if (type.isMapLikeType()) {
            MapLikeType mlt = (MapLikeType) type;
            JsonSerializer<Object> keySerializer = _findKeySerializer(prov, beanDesc.getClassInfo(), property);
            if (mlt.isTrueMapType()) {
                return buildMapSerializer(config, (MapType) mlt, beanDesc, staticTyping, keySerializer, elementTypeSerializer, elementValueSerializer);
            }
            for (Serializers serializers : customSerializers()) {
                JsonSerializer<?> ser = serializers.findMapLikeSerializer(config, (MapLikeType) type, beanDesc, keySerializer, elementTypeSerializer, elementValueSerializer);
                if (ser != null) {
                    return ser;
                }
            }
            return null;
        }
        if (type.isCollectionLikeType()) {
            CollectionLikeType clt = (CollectionLikeType) type;
            if (clt.isTrueCollectionType()) {
                return buildCollectionSerializer(config, (CollectionType) clt, beanDesc, property, staticTyping, elementTypeSerializer, elementValueSerializer);
            }
            for (Serializers serializers2 : customSerializers()) {
                JsonSerializer<?> ser2 = serializers2.findCollectionLikeSerializer(config, (CollectionLikeType) type, beanDesc, elementTypeSerializer, elementValueSerializer);
                if (ser2 != null) {
                    return ser2;
                }
            }
            return null;
        }
        if (type.isArrayType()) {
            return buildArraySerializer(config, (ArrayType) type, beanDesc, staticTyping, elementTypeSerializer, elementValueSerializer);
        }
        return null;
    }

    protected JsonSerializer<?> buildCollectionSerializer(SerializationConfig config, CollectionType type, BeanDescription beanDesc, BeanProperty property, boolean staticTyping, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) throws JsonMappingException {
        for (Serializers serializers : customSerializers()) {
            JsonSerializer<?> ser = serializers.findCollectionSerializer(config, type, beanDesc, elementTypeSerializer, elementValueSerializer);
            if (ser != null) {
                return ser;
            }
        }
        Class<?> raw = type.getRawClass();
        if (EnumSet.class.isAssignableFrom(raw)) {
            JavaType enumType = type.getContentType();
            if (!enumType.isEnumType()) {
                enumType = null;
            }
            JsonSerializer<?> ser2 = StdContainerSerializers.enumSetSerializer(enumType);
            return ser2;
        }
        Class<?> elementRaw = type.getContentType().getRawClass();
        if (isIndexedList(raw)) {
            if (elementRaw == String.class && (elementValueSerializer == null || ClassUtil.isJacksonStdImpl(elementValueSerializer))) {
                JsonSerializer<?> ser3 = IndexedStringListSerializer.instance;
                return ser3;
            }
            JsonSerializer<?> ser4 = StdContainerSerializers.indexedListSerializer(type.getContentType(), staticTyping, elementTypeSerializer, property, elementValueSerializer);
            return ser4;
        }
        if (elementRaw == String.class && (elementValueSerializer == null || ClassUtil.isJacksonStdImpl(elementValueSerializer))) {
            JsonSerializer<?> ser5 = StringCollectionSerializer.instance;
            return ser5;
        }
        JsonSerializer<?> ser6 = StdContainerSerializers.collectionSerializer(type.getContentType(), staticTyping, elementTypeSerializer, property, elementValueSerializer);
        return ser6;
    }

    protected boolean isIndexedList(Class<?> cls) {
        return RandomAccess.class.isAssignableFrom(cls);
    }

    protected JsonSerializer<?> buildMapSerializer(SerializationConfig config, MapType type, BeanDescription beanDesc, boolean staticTyping, JsonSerializer<Object> keySerializer, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) throws JsonMappingException {
        for (Serializers serializers : customSerializers()) {
            JsonSerializer<?> ser = serializers.findMapSerializer(config, type, beanDesc, keySerializer, elementTypeSerializer, elementValueSerializer);
            if (ser != null) {
                return ser;
            }
        }
        if (EnumMap.class.isAssignableFrom(type.getRawClass())) {
            JavaType keyType = type.getKeyType();
            EnumValues enums = null;
            if (keyType.isEnumType()) {
                enums = EnumValues.construct(keyType.getRawClass(), config.getAnnotationIntrospector());
            }
            JsonSerializer<?> ser2 = new EnumMapSerializer(type.getContentType(), staticTyping, enums, elementTypeSerializer, elementValueSerializer);
            return ser2;
        }
        JsonSerializer<?> ser3 = MapSerializer.construct(config.getAnnotationIntrospector().findPropertiesToIgnore(beanDesc.getClassInfo()), type, staticTyping, elementTypeSerializer, keySerializer, elementValueSerializer);
        return ser3;
    }

    protected JsonSerializer<?> buildArraySerializer(SerializationConfig config, ArrayType type, BeanDescription beanDesc, boolean staticTyping, TypeSerializer elementTypeSerializer, JsonSerializer<Object> elementValueSerializer) throws JsonMappingException {
        Class<?> raw = type.getRawClass();
        if (elementValueSerializer == null || ClassUtil.isJacksonStdImpl(elementValueSerializer)) {
            if (String[].class == raw) {
                return StringArraySerializer.instance;
            }
            JsonSerializer<?> ser = StdArraySerializers.findStandardImpl(raw);
            if (ser != null) {
                return ser;
            }
        }
        return new ObjectArraySerializer(type.getContentType(), staticTyping, elementTypeSerializer, elementValueSerializer);
    }

    protected JsonSerializer<?> buildIteratorSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc, boolean staticTyping) throws JsonMappingException {
        JavaType valueType = type.containedType(0);
        if (valueType == null) {
            valueType = TypeFactory.unknownType();
        }
        TypeSerializer vts = createTypeSerializer(config, valueType);
        return StdContainerSerializers.iteratorSerializer(valueType, usesStaticTyping(config, beanDesc, vts, null), vts);
    }

    protected JsonSerializer<?> buildIterableSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc, boolean staticTyping) throws JsonMappingException {
        JavaType valueType = type.containedType(0);
        if (valueType == null) {
            valueType = TypeFactory.unknownType();
        }
        TypeSerializer vts = createTypeSerializer(config, valueType);
        return StdContainerSerializers.iterableSerializer(valueType, usesStaticTyping(config, beanDesc, vts, null), vts);
    }

    protected <T extends JavaType> T modifyTypeByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T t) {
        Class<?> clsFindSerializationType = serializationConfig.getAnnotationIntrospector().findSerializationType(annotated);
        if (clsFindSerializationType != null) {
            try {
                t = (T) t.widenBy(clsFindSerializationType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Failed to widen type " + t + " with concrete-type annotation (value " + clsFindSerializationType.getName() + "), method '" + annotated.getName() + "': " + e.getMessage());
            }
        }
        return (T) modifySecondaryTypesByAnnotation(serializationConfig, annotated, t);
    }

    protected static <T extends JavaType> T modifySecondaryTypesByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T t) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        if (t.isContainerType()) {
            Class<?> clsFindSerializationKeyType = annotationIntrospector.findSerializationKeyType(annotated, t.getKeyType());
            if (clsFindSerializationKeyType != null) {
                if (!(t instanceof MapType)) {
                    throw new IllegalArgumentException("Illegal key-type annotation: type " + t + " is not a Map type");
                }
                try {
                    t = (T) ((MapType) t).widenKey(clsFindSerializationKeyType);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Failed to narrow key type " + t + " with key-type annotation (" + clsFindSerializationKeyType.getName() + "): " + e.getMessage());
                }
            }
            Class<?> clsFindSerializationContentType = annotationIntrospector.findSerializationContentType(annotated, t.getContentType());
            if (clsFindSerializationContentType == null) {
                return t;
            }
            try {
                return (T) t.widenContentsBy(clsFindSerializationContentType);
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Failed to narrow content type " + t + " with content-type annotation (" + clsFindSerializationContentType.getName() + "): " + e2.getMessage());
            }
        }
        return t;
    }

    protected JsonSerializer<Object> _findKeySerializer(SerializerProvider prov, Annotated a, BeanProperty property) throws JsonMappingException {
        AnnotatedMember m;
        Object serDef;
        AnnotationIntrospector intr = prov.getAnnotationIntrospector();
        if (property != null && (m = property.getMember()) != null && (serDef = intr.findKeySerializer(m)) != null) {
            return prov.serializerInstance(m, serDef);
        }
        Object serDef2 = intr.findKeySerializer(a);
        if (serDef2 != null) {
            return prov.serializerInstance(a, serDef2);
        }
        return null;
    }

    protected JsonSerializer<Object> _findContentSerializer(SerializerProvider prov, Annotated a, BeanProperty property) throws JsonMappingException {
        AnnotatedMember m;
        Object serDef;
        AnnotationIntrospector intr = prov.getAnnotationIntrospector();
        if (property != null && (m = property.getMember()) != null && (serDef = intr.findContentSerializer(m)) != null) {
            return prov.serializerInstance(m, serDef);
        }
        Object serDef2 = intr.findContentSerializer(a);
        if (serDef2 != null) {
            return prov.serializerInstance(a, serDef2);
        }
        return null;
    }

    protected boolean usesStaticTyping(SerializationConfig config, BeanDescription beanDesc, TypeSerializer typeSer, BeanProperty property) {
        if (typeSer != null) {
            return false;
        }
        AnnotationIntrospector intr = config.getAnnotationIntrospector();
        JsonSerialize.Typing t = intr.findSerializationTyping(beanDesc.getClassInfo());
        if (t != null) {
            if (t == JsonSerialize.Typing.STATIC) {
                return true;
            }
        } else if (config.isEnabled(MapperFeature.USE_STATIC_TYPING)) {
            return true;
        }
        if (property == null) {
            return false;
        }
        JavaType type = property.getType();
        if (!type.isContainerType()) {
            return false;
        }
        if (intr.findSerializationContentType(property.getMember(), property.getType()) != null) {
            return true;
        }
        return (type instanceof MapType) && intr.findSerializationKeyType(property.getMember(), property.getType()) != null;
    }

    protected Class<?> _verifyAsClass(Object src, String methodName, Class<?> noneClass) {
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
}
