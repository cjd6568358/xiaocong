package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectReader extends ObjectCodec implements Versioned {
    private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
    protected final DeserializationConfig _config;
    protected final DefaultDeserializationContext _context;
    protected final InjectableValues _injectableValues;
    protected final JsonFactory _jsonFactory;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
    protected final RootNameLookup _rootNames;
    protected final FormatSchema _schema;
    protected final boolean _unwrapRoot;
    protected final Object _valueToUpdate;
    protected final JavaType _valueType;

    protected ObjectReader(ObjectMapper mapper, DeserializationConfig config) {
        this(mapper, config, (JavaType) null, (Object) null, (FormatSchema) null, (InjectableValues) null);
    }

    protected ObjectReader(ObjectMapper mapper, DeserializationConfig config, JavaType valueType, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues) {
        this._config = config;
        this._context = mapper._deserializationContext;
        this._rootDeserializers = mapper._rootDeserializers;
        this._jsonFactory = mapper._jsonFactory;
        this._rootNames = mapper._rootNames;
        this._valueType = valueType;
        this._valueToUpdate = valueToUpdate;
        if (valueToUpdate != null && valueType.isArrayType()) {
            throw new IllegalArgumentException("Can not update an array value");
        }
        this._schema = schema;
        this._injectableValues = injectableValues;
        this._unwrapRoot = config.useRootWrapping();
    }

    protected ObjectReader(ObjectReader base, DeserializationConfig config, JavaType valueType, Object valueToUpdate, FormatSchema schema, InjectableValues injectableValues) {
        this._config = config;
        this._context = base._context;
        this._rootDeserializers = base._rootDeserializers;
        this._jsonFactory = base._jsonFactory;
        this._rootNames = base._rootNames;
        this._valueType = valueType;
        this._valueToUpdate = valueToUpdate;
        if (valueToUpdate != null && valueType.isArrayType()) {
            throw new IllegalArgumentException("Can not update an array value");
        }
        this._schema = schema;
        this._injectableValues = injectableValues;
        this._unwrapRoot = config.useRootWrapping();
    }

    protected ObjectReader(ObjectReader base, DeserializationConfig config) {
        this._config = config;
        this._context = base._context;
        this._rootDeserializers = base._rootDeserializers;
        this._jsonFactory = base._jsonFactory;
        this._rootNames = base._rootNames;
        this._valueType = base._valueType;
        this._valueToUpdate = base._valueToUpdate;
        this._schema = base._schema;
        this._injectableValues = base._injectableValues;
        this._unwrapRoot = config.useRootWrapping();
    }

    @Override // com.fasterxml.jackson.core.Versioned
    public Version version() {
        return DatabindVersion.instance.version();
    }

    public ObjectReader with(DeserializationFeature feature) {
        DeserializationConfig newConfig = this._config.with(feature);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader with(DeserializationFeature first, DeserializationFeature... other) {
        DeserializationConfig newConfig = this._config.with(first, other);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader withFeatures(DeserializationFeature... features) {
        DeserializationConfig newConfig = this._config.withFeatures(features);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader without(DeserializationFeature feature) {
        DeserializationConfig newConfig = this._config.without(feature);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader without(DeserializationFeature first, DeserializationFeature... other) {
        DeserializationConfig newConfig = this._config.without(first, other);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader withoutFeatures(DeserializationFeature... features) {
        DeserializationConfig newConfig = this._config.withoutFeatures(features);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader with(InjectableValues injectableValues) {
        return this._injectableValues == injectableValues ? this : new ObjectReader(this, this._config, this._valueType, this._valueToUpdate, this._schema, injectableValues);
    }

    public ObjectReader with(JsonNodeFactory f) {
        DeserializationConfig newConfig = this._config.with(f);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader withRootName(String rootName) {
        DeserializationConfig newConfig = this._config.withRootName(rootName);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader with(FormatSchema schema) {
        return this._schema == schema ? this : new ObjectReader(this, this._config, this._valueType, this._valueToUpdate, schema, this._injectableValues);
    }

    public ObjectReader withType(JavaType valueType) {
        return (valueType == null || !valueType.equals(this._valueType)) ? new ObjectReader(this, this._config, valueType, this._valueToUpdate, this._schema, this._injectableValues) : this;
    }

    public ObjectReader withType(Class<?> valueType) {
        return withType(this._config.constructType(valueType));
    }

    public ObjectReader withType(Type valueType) {
        return withType(this._config.getTypeFactory().constructType(valueType));
    }

    public ObjectReader withType(TypeReference<?> valueTypeRef) {
        return withType(this._config.getTypeFactory().constructType(valueTypeRef.getType()));
    }

    public ObjectReader withValueToUpdate(Object value) {
        if (value != this._valueToUpdate) {
            if (value == null) {
                throw new IllegalArgumentException("cat not update null value");
            }
            JavaType t = this._valueType == null ? this._config.constructType(value.getClass()) : this._valueType;
            return new ObjectReader(this, this._config, t, value, this._schema, this._injectableValues);
        }
        return this;
    }

    public ObjectReader withView(Class<?> activeView) {
        DeserializationConfig newConfig = this._config.withView(activeView);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader with(Locale l) {
        DeserializationConfig newConfig = this._config.with(l);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public ObjectReader with(TimeZone tz) {
        DeserializationConfig newConfig = this._config.with(tz);
        return newConfig == this._config ? this : new ObjectReader(this, newConfig);
    }

    public boolean isEnabled(DeserializationFeature f) {
        return this._config.isEnabled(f);
    }

    public boolean isEnabled(MapperFeature f) {
        return this._config.isEnabled(f);
    }

    public boolean isEnabled(JsonParser.Feature f) {
        return this._jsonFactory.isEnabled(f);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public JsonFactory getJsonFactory() {
        return this._jsonFactory;
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public <T> T readValue(JsonParser jsonParser) throws IOException {
        return (T) _bind(jsonParser);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T readValue(JsonParser jsonParser, Class<T> cls) throws IOException {
        return (T) withType((Class<?>) cls).readValue(jsonParser);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException {
        return (T) withType(typeReference).readValue(jsonParser);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException {
        return (T) withType((JavaType) resolvedType).readValue(jsonParser);
    }

    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException {
        return (T) withType(javaType).readValue(jsonParser);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T extends TreeNode> T readTree(JsonParser jp) throws IOException {
        return _bindAsTree(jp);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> Iterator<T> readValues(JsonParser jp, Class<T> valueType) throws IOException {
        return withType((Class<?>) valueType).readValues(jp);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> Iterator<T> readValues(JsonParser jp, TypeReference<?> valueTypeRef) throws IOException {
        return withType(valueTypeRef).readValues(jp);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> Iterator<T> readValues(JsonParser jp, ResolvedType valueType) throws IOException {
        return readValues(jp, (JavaType) valueType);
    }

    public <T> Iterator<T> readValues(JsonParser jp, JavaType valueType) throws IOException {
        return withType(valueType).readValues(jp);
    }

    public <T> T readValue(InputStream inputStream) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(inputStream));
    }

    public <T> T readValue(Reader reader) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(reader));
    }

    public <T> T readValue(String str) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(str));
    }

    public <T> T readValue(byte[] bArr) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(bArr));
    }

    public <T> T readValue(byte[] bArr, int i, int i2) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(bArr, i, i2));
    }

    public <T> T readValue(File file) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(file));
    }

    public <T> T readValue(URL url) throws IOException {
        return (T) _bindAndClose(this._jsonFactory.createJsonParser(url));
    }

    public <T> T readValue(JsonNode jsonNode) throws IOException {
        return (T) _bindAndClose(treeAsTokens(jsonNode));
    }

    public JsonNode readTree(InputStream in) throws IOException {
        return _bindAndCloseAsTree(this._jsonFactory.createJsonParser(in));
    }

    public JsonNode readTree(Reader r) throws IOException {
        return _bindAndCloseAsTree(this._jsonFactory.createJsonParser(r));
    }

    public JsonNode readTree(String content) throws IOException {
        return _bindAndCloseAsTree(this._jsonFactory.createJsonParser(content));
    }

    public <T> MappingIterator<T> readValues(JsonParser jp) throws IOException {
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), false, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(InputStream src) throws IOException {
        JsonParser jp = this._jsonFactory.createJsonParser(src);
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(Reader src) throws IOException {
        JsonParser jp = this._jsonFactory.createJsonParser(src);
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(String json) throws IOException {
        JsonParser jp = this._jsonFactory.createJsonParser(json);
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(byte[] src, int offset, int length) throws IOException {
        JsonParser jp = this._jsonFactory.createJsonParser(src, offset, length);
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), true, this._valueToUpdate);
    }

    public final <T> MappingIterator<T> readValues(byte[] src) throws IOException {
        return readValues(src, 0, src.length);
    }

    public <T> MappingIterator<T> readValues(File src) throws IOException {
        JsonParser jp = this._jsonFactory.createJsonParser(src);
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), true, this._valueToUpdate);
    }

    public <T> MappingIterator<T> readValues(URL src) throws IOException {
        JsonParser jp = this._jsonFactory.createJsonParser(src);
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        DeserializationContext ctxt = createDeserializationContext(jp, this._config);
        return new MappingIterator<>(this._valueType, jp, ctxt, _findRootDeserializer(ctxt, this._valueType), true, this._valueToUpdate);
    }

    protected final DefaultDeserializationContext createDeserializationContext(JsonParser jp, DeserializationConfig cfg) {
        return this._context.createInstance(cfg, jp, this._injectableValues);
    }

    protected Object _bind(JsonParser jp) throws IOException {
        Object result;
        JsonToken t = _initForReading(jp);
        if (t == JsonToken.VALUE_NULL) {
            if (this._valueToUpdate == null) {
                result = _findRootDeserializer(createDeserializationContext(jp, this._config), this._valueType).getNullValue();
            } else {
                result = this._valueToUpdate;
            }
        } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
            result = this._valueToUpdate;
        } else {
            DeserializationContext ctxt = createDeserializationContext(jp, this._config);
            JsonDeserializer<Object> deser = _findRootDeserializer(ctxt, this._valueType);
            if (this._unwrapRoot) {
                result = _unwrapAndDeserialize(jp, ctxt, this._valueType, deser);
            } else if (this._valueToUpdate == null) {
                result = deser.deserialize(jp, ctxt);
            } else {
                deser.deserialize(jp, ctxt, this._valueToUpdate);
                result = this._valueToUpdate;
            }
        }
        jp.clearCurrentToken();
        return result;
    }

    protected Object _bindAndClose(JsonParser jp) throws IOException {
        Object result;
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        try {
            JsonToken t = _initForReading(jp);
            if (t == JsonToken.VALUE_NULL) {
                if (this._valueToUpdate == null) {
                    result = _findRootDeserializer(createDeserializationContext(jp, this._config), this._valueType).getNullValue();
                } else {
                    result = this._valueToUpdate;
                }
            } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
                result = this._valueToUpdate;
            } else {
                DeserializationContext ctxt = createDeserializationContext(jp, this._config);
                JsonDeserializer<Object> deser = _findRootDeserializer(ctxt, this._valueType);
                if (this._unwrapRoot) {
                    result = _unwrapAndDeserialize(jp, ctxt, this._valueType, deser);
                } else if (this._valueToUpdate == null) {
                    result = deser.deserialize(jp, ctxt);
                } else {
                    deser.deserialize(jp, ctxt, this._valueToUpdate);
                    result = this._valueToUpdate;
                }
            }
            return result;
        } finally {
            try {
                jp.close();
            } catch (IOException e) {
            }
        }
    }

    protected JsonNode _bindAsTree(JsonParser jp) throws IOException {
        JsonNode result;
        JsonToken t = _initForReading(jp);
        if (t == JsonToken.VALUE_NULL || t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
            result = NullNode.instance;
        } else {
            DeserializationContext ctxt = createDeserializationContext(jp, this._config);
            JsonDeserializer<Object> deser = _findRootDeserializer(ctxt, JSON_NODE_TYPE);
            if (this._unwrapRoot) {
                result = (JsonNode) _unwrapAndDeserialize(jp, ctxt, JSON_NODE_TYPE, deser);
            } else {
                result = (JsonNode) deser.deserialize(jp, ctxt);
            }
        }
        jp.clearCurrentToken();
        return result;
    }

    protected JsonNode _bindAndCloseAsTree(JsonParser jp) throws IOException {
        if (this._schema != null) {
            jp.setSchema(this._schema);
        }
        try {
            return _bindAsTree(jp);
        } finally {
            try {
                jp.close();
            } catch (IOException e) {
            }
        }
    }

    protected static JsonToken _initForReading(JsonParser jp) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == null && (t = jp.nextToken()) == null) {
            throw JsonMappingException.from(jp, "No content to map due to end-of-input");
        }
        return t;
    }

    protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext ctxt, JavaType valueType) throws JsonMappingException {
        if (valueType == null) {
            throw new JsonMappingException("No value type configured for ObjectReader");
        }
        JsonDeserializer<Object> deser = this._rootDeserializers.get(valueType);
        if (deser != null) {
            return deser;
        }
        JsonDeserializer<Object> deser2 = ctxt.findRootValueDeserializer(valueType);
        if (deser2 == null) {
            throw new JsonMappingException("Can not find a deserializer for type " + valueType);
        }
        this._rootDeserializers.put(valueType, deser2);
        return deser2;
    }

    protected Object _unwrapAndDeserialize(JsonParser jp, DeserializationContext ctxt, JavaType rootType, JsonDeserializer<Object> deser) throws IOException {
        Object result;
        String expName = this._config.getRootName();
        if (expName == null) {
            SerializedString sstr = this._rootNames.findRootName(rootType, this._config);
            expName = sstr.getValue();
        }
        if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
            throw JsonMappingException.from(jp, "Current token not START_OBJECT (needed to unwrap root name '" + expName + "'), but " + jp.getCurrentToken());
        }
        if (jp.nextToken() != JsonToken.FIELD_NAME) {
            throw JsonMappingException.from(jp, "Current token not FIELD_NAME (to contain expected root name '" + expName + "'), but " + jp.getCurrentToken());
        }
        String actualName = jp.getCurrentName();
        if (!expName.equals(actualName)) {
            throw JsonMappingException.from(jp, "Root name '" + actualName + "' does not match expected ('" + expName + "') for type " + rootType);
        }
        jp.nextToken();
        if (this._valueToUpdate == null) {
            result = deser.deserialize(jp, ctxt);
        } else {
            deser.deserialize(jp, ctxt, this._valueToUpdate);
            result = this._valueToUpdate;
        }
        if (jp.nextToken() != JsonToken.END_OBJECT) {
            throw JsonMappingException.from(jp, "Current token not END_OBJECT (to match wrapper object with root name '" + expName + "'), but " + jp.getCurrentToken());
        }
        return result;
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public JsonNode createArrayNode() {
        return this._config.getNodeFactory().arrayNode();
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public JsonNode createObjectNode() {
        return this._config.getNodeFactory().objectNode();
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public JsonParser treeAsTokens(TreeNode n) {
        return new TreeTraversingParser((JsonNode) n, this);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T treeToValue(TreeNode treeNode, Class<T> cls) throws JsonProcessingException {
        try {
            return (T) readValue(treeAsTokens(treeNode), cls);
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw new IllegalArgumentException(e2.getMessage(), e2);
        }
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public void writeValue(JsonGenerator jgen, Object value) throws IOException {
        throw new UnsupportedOperationException("Not implemented for ObjectReader");
    }
}
