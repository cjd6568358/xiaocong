package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualKeyDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerCache;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.TypeWrappedDeserializer;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.LinkedNode;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DeserializationContext {
    private static final int MAX_ERROR_STR_LEN = 500;
    protected ArrayBuilders _arrayBuilders;
    protected final DeserializerCache _cache;
    protected final DeserializationConfig _config;
    protected DateFormat _dateFormat;
    protected final DeserializerFactory _factory;
    protected final int _featureFlags;
    protected final InjectableValues _injectableValues;
    protected ObjectBuffer _objectBuffer;
    protected JsonParser _parser;
    protected final Class<?> _view;

    public abstract JsonDeserializer<Object> deserializerInstance(Annotated annotated, Object obj) throws JsonMappingException;

    public abstract ReadableObjectId findObjectId(Object obj, ObjectIdGenerator<?> objectIdGenerator);

    public abstract KeyDeserializer keyDeserializerInstance(Annotated annotated, Object obj) throws JsonMappingException;

    public abstract ObjectIdGenerator<?> objectIdGeneratorInstance(Annotated annotated, ObjectIdInfo objectIdInfo) throws JsonMappingException;

    protected DeserializationContext(DeserializerFactory df) {
        this(df, (DeserializerCache) null);
    }

    protected DeserializationContext(DeserializerFactory df, DeserializerCache cache) {
        if (df == null) {
            throw new IllegalArgumentException("Can not pass null DeserializerFactory");
        }
        this._factory = df;
        this._cache = cache == null ? new DeserializerCache() : cache;
        this._featureFlags = 0;
        this._config = null;
        this._injectableValues = null;
        this._view = null;
    }

    protected DeserializationContext(DeserializationContext src, DeserializerFactory factory) {
        this._cache = src._cache;
        this._factory = factory;
        this._config = src._config;
        this._featureFlags = src._featureFlags;
        this._view = src._view;
        this._parser = src._parser;
        this._injectableValues = src._injectableValues;
    }

    protected DeserializationContext(DeserializationContext src, DeserializationConfig config, JsonParser jp, InjectableValues injectableValues) {
        this._cache = src._cache;
        this._factory = src._factory;
        this._config = config;
        this._featureFlags = config.getDeserializationFeatures();
        this._view = config.getActiveView();
        this._parser = jp;
        this._injectableValues = injectableValues;
    }

    public DeserializationConfig getConfig() {
        return this._config;
    }

    public final boolean isEnabled(DeserializationFeature feat) {
        return (this._featureFlags & feat.getMask()) != 0;
    }

    public final boolean isEnabled(MapperFeature feat) {
        return this._config.isEnabled(feat);
    }

    public final AnnotationIntrospector getAnnotationIntrospector() {
        return this._config.getAnnotationIntrospector();
    }

    public final JsonParser getParser() {
        return this._parser;
    }

    public final Object findInjectableValue(Object valueId, BeanProperty forProperty, Object beanInstance) {
        if (this._injectableValues == null) {
            throw new IllegalStateException("No 'injectableValues' configured, can not inject value with id [" + valueId + "]");
        }
        return this._injectableValues.findInjectableValue(valueId, this, forProperty, beanInstance);
    }

    public final Class<?> getActiveView() {
        return this._view;
    }

    public final boolean canOverrideAccessModifiers() {
        return this._config.canOverrideAccessModifiers();
    }

    public final Base64Variant getBase64Variant() {
        return this._config.getBase64Variant();
    }

    public final JsonNodeFactory getNodeFactory() {
        return this._config.getNodeFactory();
    }

    public final TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public Locale getLocale() {
        return this._config.getLocale();
    }

    public TimeZone getTimeZone() {
        return this._config.getTimeZone();
    }

    public boolean hasValueDeserializerFor(JavaType type) {
        return this._cache.hasValueDeserializerFor(this, this._factory, type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final JsonDeserializer<Object> findContextualValueDeserializer(JavaType type, BeanProperty property) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializerFindValueDeserializer = this._cache.findValueDeserializer(this, this._factory, type);
        if (jsonDeserializerFindValueDeserializer != 0 && (jsonDeserializerFindValueDeserializer instanceof ContextualDeserializer)) {
            return ((ContextualDeserializer) jsonDeserializerFindValueDeserializer).createContextual(this, property);
        }
        return jsonDeserializerFindValueDeserializer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object>] */
    public final JsonDeserializer<Object> findRootValueDeserializer(JavaType javaType) throws JsonMappingException {
        ?? CreateContextual;
        Object objFindValueDeserializer = this._cache.findValueDeserializer(this, this._factory, javaType);
        if (objFindValueDeserializer == null) {
            return null;
        }
        if (objFindValueDeserializer instanceof ContextualDeserializer) {
            CreateContextual = objFindValueDeserializer;
            CreateContextual = ((ContextualDeserializer) objFindValueDeserializer).createContextual(this, null);
        }
        CreateContextual = objFindValueDeserializer;
        TypeDeserializer typeDeserializerFindTypeDeserializer = this._factory.findTypeDeserializer(this._config, javaType);
        return typeDeserializerFindTypeDeserializer != null ? new TypeWrappedDeserializer(typeDeserializerFindTypeDeserializer.forProperty(null), CreateContextual) : CreateContextual;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final KeyDeserializer findKeyDeserializer(JavaType keyType, BeanProperty property) throws JsonMappingException {
        KeyDeserializer keyDeserializerFindKeyDeserializer = this._cache.findKeyDeserializer(this, this._factory, keyType);
        if (keyDeserializerFindKeyDeserializer instanceof ContextualKeyDeserializer) {
            KeyDeserializer kd = ((ContextualKeyDeserializer) keyDeserializerFindKeyDeserializer).createContextual(this, property);
            return kd;
        }
        return keyDeserializerFindKeyDeserializer;
    }

    public final JavaType constructType(Class<?> cls) {
        return this._config.constructType(cls);
    }

    public Class<?> findClass(String className) throws ClassNotFoundException {
        return ClassUtil.findClass(className);
    }

    public final ObjectBuffer leaseObjectBuffer() {
        ObjectBuffer buf = this._objectBuffer;
        if (buf == null) {
            return new ObjectBuffer();
        }
        this._objectBuffer = null;
        return buf;
    }

    public final void returnObjectBuffer(ObjectBuffer buf) {
        if (this._objectBuffer == null || buf.initialCapacity() >= this._objectBuffer.initialCapacity()) {
            this._objectBuffer = buf;
        }
    }

    public final ArrayBuilders getArrayBuilders() {
        if (this._arrayBuilders == null) {
            this._arrayBuilders = new ArrayBuilders();
        }
        return this._arrayBuilders;
    }

    public Date parseDate(String dateStr) throws IllegalArgumentException {
        try {
            return getDateFormat().parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Failed to parse Date value '" + dateStr + "': " + e.getMessage());
        }
    }

    public Calendar constructCalendar(Date d) {
        Calendar c = Calendar.getInstance(getTimeZone());
        c.setTime(d);
        return c;
    }

    public boolean handleUnknownProperty(JsonParser jp, JsonDeserializer<?> deser, Object instanceOrClass, String propName) throws IOException {
        LinkedNode<DeserializationProblemHandler> h = this._config.getProblemHandlers();
        if (h != null) {
            while (h != null) {
                if (h.value().handleUnknownProperty(this, jp, deser, instanceOrClass, propName)) {
                    return true;
                }
                h = h.next();
            }
        }
        return false;
    }

    public void reportUnknownProperty(Object instanceOrClass, String fieldName, JsonDeserializer<?> deser) throws JsonMappingException {
        if (!isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)) {
            return;
        }
        Collection<Object> propIds = deser == null ? null : deser.getKnownPropertyNames();
        throw UnrecognizedPropertyException.from(this._parser, instanceOrClass, fieldName, propIds);
    }

    public JsonMappingException mappingException(Class<?> targetClass) {
        return mappingException(targetClass, this._parser.getCurrentToken());
    }

    public JsonMappingException mappingException(Class<?> targetClass, JsonToken token) {
        String clsName = _calcName(targetClass);
        return JsonMappingException.from(this._parser, "Can not deserialize instance of " + clsName + " out of " + token + " token");
    }

    public JsonMappingException mappingException(String message) {
        return JsonMappingException.from(getParser(), message);
    }

    public JsonMappingException instantiationException(Class<?> instClass, Throwable t) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + instClass.getName() + ", problem: " + t.getMessage(), t);
    }

    public JsonMappingException instantiationException(Class<?> instClass, String msg) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + instClass.getName() + ", problem: " + msg);
    }

    public JsonMappingException weirdStringException(Class<?> instClass, String msg) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + instClass.getName() + " from String value '" + _valueDesc() + "': " + msg);
    }

    public JsonMappingException weirdNumberException(Class<?> instClass, String msg) {
        return JsonMappingException.from(this._parser, "Can not construct instance of " + instClass.getName() + " from number value (" + _valueDesc() + "): " + msg);
    }

    public JsonMappingException weirdKeyException(Class<?> keyClass, String keyValue, String msg) {
        return JsonMappingException.from(this._parser, "Can not construct Map key of type " + keyClass.getName() + " from String \"" + _desc(keyValue) + "\": " + msg);
    }

    public JsonMappingException wrongTokenException(JsonParser jp, JsonToken expToken, String msg) {
        return JsonMappingException.from(jp, "Unexpected token (" + jp.getCurrentToken() + "), expected " + expToken + ": " + msg);
    }

    public JsonMappingException unknownTypeException(JavaType type, String id) {
        return JsonMappingException.from(this._parser, "Could not resolve type id '" + id + "' into a subtype of " + type);
    }

    protected DateFormat getDateFormat() {
        if (this._dateFormat != null) {
            return this._dateFormat;
        }
        DateFormat df = (DateFormat) this._config.getDateFormat().clone();
        this._dateFormat = df;
        return df;
    }

    protected String determineClassName(Object instance) {
        return ClassUtil.getClassDescription(instance);
    }

    protected String _calcName(Class<?> cls) {
        return cls.isArray() ? _calcName(cls.getComponentType()) + "[]" : cls.getName();
    }

    protected String _valueDesc() {
        try {
            return _desc(this._parser.getText());
        } catch (Exception e) {
            return "[N/A]";
        }
    }

    protected String _desc(String desc) {
        if (desc.length() > 500) {
            return desc.substring(0, 500) + "]...[" + desc.substring(desc.length() - 500);
        }
        return desc;
    }
}
