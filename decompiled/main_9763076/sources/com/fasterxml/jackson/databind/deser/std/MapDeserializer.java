package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class MapDeserializer extends ContainerDeserializerBase<Map<Object, Object>> implements ContextualDeserializer, ResolvableDeserializer {
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected final boolean _hasDefaultCreator;
    protected HashSet<String> _ignorableProperties;
    protected final KeyDeserializer _keyDeserializer;
    protected final JavaType _mapType;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected boolean _standardStringKey;
    protected final JsonDeserializer<Object> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;
    protected final TypeDeserializer _valueTypeDeserializer;

    public MapDeserializer(JavaType mapType, ValueInstantiator valueInstantiator, KeyDeserializer keyDeser, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser) {
        super(Map.class);
        this._mapType = mapType;
        this._keyDeserializer = keyDeser;
        this._valueDeserializer = valueDeser;
        this._valueTypeDeserializer = valueTypeDeser;
        this._valueInstantiator = valueInstantiator;
        this._hasDefaultCreator = valueInstantiator.canCreateUsingDefault();
        this._delegateDeserializer = null;
        this._propertyBasedCreator = null;
        this._standardStringKey = _isStdKeyDeser(mapType, keyDeser);
    }

    protected MapDeserializer(MapDeserializer src) {
        super(src._valueClass);
        this._mapType = src._mapType;
        this._keyDeserializer = src._keyDeserializer;
        this._valueDeserializer = src._valueDeserializer;
        this._valueTypeDeserializer = src._valueTypeDeserializer;
        this._valueInstantiator = src._valueInstantiator;
        this._propertyBasedCreator = src._propertyBasedCreator;
        this._delegateDeserializer = src._delegateDeserializer;
        this._hasDefaultCreator = src._hasDefaultCreator;
        this._ignorableProperties = src._ignorableProperties;
        this._standardStringKey = src._standardStringKey;
    }

    protected MapDeserializer(MapDeserializer src, KeyDeserializer keyDeser, JsonDeserializer<Object> valueDeser, TypeDeserializer valueTypeDeser, HashSet<String> ignorable) {
        super(src._valueClass);
        this._mapType = src._mapType;
        this._keyDeserializer = keyDeser;
        this._valueDeserializer = valueDeser;
        this._valueTypeDeserializer = valueTypeDeser;
        this._valueInstantiator = src._valueInstantiator;
        this._propertyBasedCreator = src._propertyBasedCreator;
        this._delegateDeserializer = src._delegateDeserializer;
        this._hasDefaultCreator = src._hasDefaultCreator;
        this._ignorableProperties = ignorable;
        this._standardStringKey = _isStdKeyDeser(this._mapType, keyDeser);
    }

    protected MapDeserializer withResolved(KeyDeserializer keyDeser, TypeDeserializer valueTypeDeser, JsonDeserializer<?> valueDeser, HashSet<String> ignorable) {
        return (this._keyDeserializer == keyDeser && this._valueDeserializer == valueDeser && this._valueTypeDeserializer == valueTypeDeser && this._ignorableProperties == ignorable) ? this : new MapDeserializer(this, keyDeser, valueDeser, valueTypeDeser, ignorable);
    }

    protected final boolean _isStdKeyDeser(JavaType mapType, KeyDeserializer keyDeser) {
        JavaType keyType;
        if (keyDeser == null || (keyType = mapType.getKeyType()) == null) {
            return true;
        }
        Class<?> rawKeyType = keyType.getRawClass();
        return (rawKeyType == String.class || rawKeyType == Object.class) && isDefaultKeyDeserializer(keyDeser);
    }

    public void setIgnorableProperties(String[] ignorable) {
        this._ignorableProperties = (ignorable == null || ignorable.length == 0) ? null : ArrayBuilders.arrayToSet(ignorable);
    }

    @Override // com.fasterxml.jackson.databind.deser.ResolvableDeserializer
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            JavaType delegateType = this._valueInstantiator.getDelegateType(ctxt.getConfig());
            if (delegateType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._mapType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            this._delegateDeserializer = findDeserializer(ctxt, delegateType, null);
        }
        if (this._valueInstantiator.canCreateFromObjectWith()) {
            SettableBeanProperty[] creatorProps = this._valueInstantiator.getFromObjectArguments(ctxt.getConfig());
            this._propertyBasedCreator = PropertyBasedCreator.construct(ctxt, this._valueInstantiator, creatorProps);
        }
        this._standardStringKey = _isStdKeyDeser(this._mapType, this._keyDeserializer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [com.fasterxml.jackson.databind.deser.std.MapDeserializer] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.fasterxml.jackson.databind.KeyDeserializer] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r8v3, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        ?? CreateContextual2;
        String[] strArrFindPropertiesToIgnore;
        Object obj = this._keyDeserializer;
        if (obj == null) {
            CreateContextual = deserializationContext.findKeyDeserializer(this._mapType.getKeyType(), beanProperty);
        } else if (obj instanceof ContextualKeyDeserializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualKeyDeserializer) obj).createContextual(deserializationContext, beanProperty);
        }
        CreateContextual = obj;
        Object obj2 = this._valueDeserializer;
        if (obj2 == null) {
            CreateContextual2 = deserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), beanProperty);
        } else if (obj2 instanceof ContextualDeserializer) {
            CreateContextual2 = obj2;
            CreateContextual2 = ((ContextualDeserializer) obj2).createContextual(deserializationContext, beanProperty);
        }
        CreateContextual2 = obj2;
        TypeDeserializer typeDeserializerForProperty = this._valueTypeDeserializer;
        if (typeDeserializerForProperty != null) {
            typeDeserializerForProperty = typeDeserializerForProperty.forProperty(beanProperty);
        }
        HashSet<String> hashSet = this._ignorableProperties;
        AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        if (annotationIntrospector != null && beanProperty != null && (strArrFindPropertiesToIgnore = annotationIntrospector.findPropertiesToIgnore(beanProperty.getMember())) != null) {
            hashSet = hashSet == null ? new HashSet<>() : new HashSet<>(hashSet);
            for (String str : strArrFindPropertiesToIgnore) {
                hashSet.add(str);
            }
        }
        return withResolved(CreateContextual, typeDeserializerForProperty, CreateContextual2, hashSet);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JavaType getContentType() {
        return this._mapType.getContentType();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Map<Object, Object> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._propertyBasedCreator != null) {
            return _deserializeUsingCreator(jp, ctxt);
        }
        if (this._delegateDeserializer != null) {
            return (Map) this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        if (!this._hasDefaultCreator) {
            throw ctxt.instantiationException(getMapClass(), "No default constructor found");
        }
        JsonToken t = jp.getCurrentToken();
        if (t != JsonToken.START_OBJECT && t != JsonToken.FIELD_NAME && t != JsonToken.END_OBJECT) {
            if (t == JsonToken.VALUE_STRING) {
                return (Map) this._valueInstantiator.createFromString(ctxt, jp.getText());
            }
            throw ctxt.mappingException(getMapClass());
        }
        Map<Object, Object> result = (Map) this._valueInstantiator.createUsingDefault(ctxt);
        if (this._standardStringKey) {
            _readAndBindStringMap(jp, ctxt, result);
            return result;
        }
        _readAndBind(jp, ctxt, result);
        return result;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Map<Object, Object> deserialize(JsonParser jp, DeserializationContext ctxt, Map<Object, Object> result) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t != JsonToken.START_OBJECT && t != JsonToken.FIELD_NAME) {
            throw ctxt.mappingException(getMapClass());
        }
        if (this._standardStringKey) {
            _readAndBindStringMap(jp, ctxt, result);
        } else {
            _readAndBind(jp, ctxt, result);
        }
        return result;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromObject(jp, ctxt);
    }

    public final Class<?> getMapClass() {
        return this._mapType.getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer
    public JavaType getValueType() {
        return this._mapType;
    }

    protected final void _readAndBind(JsonParser jp, DeserializationContext ctxt, Map<Object, Object> result) throws IOException {
        Object value;
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        KeyDeserializer keyDes = this._keyDeserializer;
        JsonDeserializer<Object> valueDes = this._valueDeserializer;
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        while (t == JsonToken.FIELD_NAME) {
            String fieldName = jp.getCurrentName();
            Object key = keyDes.deserializeKey(fieldName, ctxt);
            JsonToken t2 = jp.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains(fieldName)) {
                jp.skipChildren();
            } else {
                if (t2 == JsonToken.VALUE_NULL) {
                    value = null;
                } else if (typeDeser == null) {
                    value = valueDes.deserialize(jp, ctxt);
                } else {
                    value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
                }
                result.put(key, value);
            }
            t = jp.nextToken();
        }
    }

    protected final void _readAndBindStringMap(JsonParser jp, DeserializationContext ctxt, Map<Object, Object> result) throws IOException {
        Object value;
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        JsonDeserializer<Object> valueDes = this._valueDeserializer;
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        while (t == JsonToken.FIELD_NAME) {
            String fieldName = jp.getCurrentName();
            JsonToken t2 = jp.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains(fieldName)) {
                jp.skipChildren();
            } else {
                if (t2 == JsonToken.VALUE_NULL) {
                    value = null;
                } else if (typeDeser == null) {
                    value = valueDes.deserialize(jp, ctxt);
                } else {
                    value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
                }
                result.put(fieldName, value);
            }
            t = jp.nextToken();
        }
    }

    public Map<Object, Object> _deserializeUsingCreator(JsonParser jp, DeserializationContext ctxt) throws Throwable {
        Object value;
        PropertyBasedCreator creator = this._propertyBasedCreator;
        PropertyValueBuffer buffer = creator.startBuilding(jp, ctxt);
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        JsonDeserializer<Object> valueDes = this._valueDeserializer;
        TypeDeserializer typeDeser = this._valueTypeDeserializer;
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            JsonToken t2 = jp.nextToken();
            if (this._ignorableProperties != null && this._ignorableProperties.contains(propName)) {
                jp.skipChildren();
            } else {
                SettableBeanProperty prop = creator.findCreatorProperty(propName);
                if (prop != null) {
                    Object value2 = prop.deserialize(jp, ctxt);
                    if (buffer.assignParameter(prop.getPropertyIndex(), value2)) {
                        jp.nextToken();
                        try {
                            Map<Object, Object> result = (Map) creator.build(ctxt, buffer);
                            _readAndBind(jp, ctxt, result);
                            return result;
                        } catch (Exception e) {
                            wrapAndThrow(e, this._mapType.getRawClass());
                            return null;
                        }
                    }
                } else {
                    String fieldName = jp.getCurrentName();
                    Object key = this._keyDeserializer.deserializeKey(fieldName, ctxt);
                    if (t2 == JsonToken.VALUE_NULL) {
                        value = null;
                    } else if (typeDeser == null) {
                        value = valueDes.deserialize(jp, ctxt);
                    } else {
                        value = valueDes.deserializeWithType(jp, ctxt, typeDeser);
                    }
                    buffer.bufferMapProperty(key, value);
                }
            }
            t = jp.nextToken();
        }
        try {
            return (Map) creator.build(ctxt, buffer);
        } catch (Exception e2) {
            wrapAndThrow(e2, this._mapType.getRawClass());
            return null;
        }
    }

    protected void wrapAndThrow(Throwable t, Object ref) throws Throwable {
        while ((t instanceof InvocationTargetException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw ((Error) t);
        }
        if ((t instanceof IOException) && !(t instanceof JsonMappingException)) {
            throw ((IOException) t);
        }
        throw JsonMappingException.wrapWithPath(t, ref, (String) null);
    }
}
