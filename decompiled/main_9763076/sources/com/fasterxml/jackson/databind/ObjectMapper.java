package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectMapper extends ObjectCodec implements Versioned {
    protected DeserializationConfig _deserializationConfig;
    protected DefaultDeserializationContext _deserializationContext;
    protected InjectableValues _injectableValues;
    protected final JsonFactory _jsonFactory;
    protected final HashMap<ClassKey, Class<?>> _mixInAnnotations;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
    protected final RootNameLookup _rootNames;
    protected SerializationConfig _serializationConfig;
    protected SerializerFactory _serializerFactory;
    protected DefaultSerializerProvider _serializerProvider;
    protected SubtypeResolver _subtypeResolver;
    protected TypeFactory _typeFactory;
    private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
    protected static final ClassIntrospector DEFAULT_INTROSPECTOR = BasicClassIntrospector.instance;
    protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR = new JacksonAnnotationIntrospector();
    protected static final VisibilityChecker<?> STD_VISIBILITY_CHECKER = VisibilityChecker.Std.defaultInstance();
    protected static final BaseSettings DEFAULT_BASE = new BaseSettings(DEFAULT_INTROSPECTOR, DEFAULT_ANNOTATION_INTROSPECTOR, STD_VISIBILITY_CHECKER, null, TypeFactory.defaultInstance(), null, StdDateFormat.instance, null, Locale.getDefault(), TimeZone.getTimeZone("GMT"));

    public enum DefaultTyping {
        JAVA_LANG_OBJECT,
        OBJECT_AND_NON_CONCRETE,
        NON_CONCRETE_AND_ARRAYS,
        NON_FINAL
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public /* bridge */ /* synthetic */ Iterator readValues(JsonParser x0, TypeReference x1) throws IOException {
        return readValues(x0, (TypeReference<?>) x1);
    }

    public static class DefaultTypeResolverBuilder extends StdTypeResolverBuilder {
        protected final DefaultTyping _appliesFor;

        public DefaultTypeResolverBuilder(DefaultTyping t) {
            this._appliesFor = t;
        }

        @Override // com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder, com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
        public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
            if (useForType(baseType)) {
                return super.buildTypeDeserializer(config, baseType, subtypes);
            }
            return null;
        }

        @Override // com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder, com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
        public TypeSerializer buildTypeSerializer(SerializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
            if (useForType(baseType)) {
                return super.buildTypeSerializer(config, baseType, subtypes);
            }
            return null;
        }

        public boolean useForType(JavaType t) {
            switch (this._appliesFor) {
                case NON_CONCRETE_AND_ARRAYS:
                    if (t.isArrayType()) {
                        t = t.getContentType();
                    }
                    break;
                case OBJECT_AND_NON_CONCRETE:
                    break;
                case NON_FINAL:
                    if (t.isArrayType()) {
                        t = t.getContentType();
                    }
                    return !t.isFinal();
                default:
                    return t.getRawClass() == Object.class;
            }
            return t.getRawClass() == Object.class || !t.isConcrete();
        }
    }

    public ObjectMapper() {
        this(null, null, null);
    }

    public ObjectMapper(JsonFactory jf) {
        this(jf, null, null);
    }

    public ObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
        this._mixInAnnotations = new HashMap<>();
        this._rootDeserializers = new ConcurrentHashMap<>(64, 0.6f, 2);
        if (jf == null) {
            this._jsonFactory = new MappingJsonFactory(this);
        } else {
            this._jsonFactory = jf;
            if (jf.getCodec() == null) {
                this._jsonFactory.setCodec(this);
            }
        }
        this._subtypeResolver = new StdSubtypeResolver();
        this._rootNames = new RootNameLookup();
        this._typeFactory = TypeFactory.defaultInstance();
        this._serializationConfig = new SerializationConfig(DEFAULT_BASE, this._subtypeResolver, this._mixInAnnotations);
        this._deserializationConfig = new DeserializationConfig(DEFAULT_BASE, this._subtypeResolver, this._mixInAnnotations);
        this._serializerProvider = sp == null ? new DefaultSerializerProvider.Impl() : sp;
        this._deserializationContext = dc == null ? new DefaultDeserializationContext.Impl(BeanDeserializerFactory.instance) : dc;
        this._serializerFactory = BeanSerializerFactory.instance;
    }

    @Override // com.fasterxml.jackson.core.Versioned
    public Version version() {
        return DatabindVersion.instance.version();
    }

    public ObjectMapper registerModule(Module module) {
        String name = module.getModuleName();
        if (name == null) {
            throw new IllegalArgumentException("Module without defined name");
        }
        Version version = module.version();
        if (version == null) {
            throw new IllegalArgumentException("Module without defined version");
        }
        module.setupModule(new Module.SetupContext() { // from class: com.fasterxml.jackson.databind.ObjectMapper.1
            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public Version getMapperVersion() {
                return ObjectMapper.this.version();
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public <C extends ObjectCodec> C getOwner() {
                return this;
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public TypeFactory getTypeFactory() {
                return ObjectMapper.this._typeFactory;
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public boolean isEnabled(MapperFeature f) {
                return this.isEnabled(f);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public boolean isEnabled(DeserializationFeature f) {
                return this.isEnabled(f);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public boolean isEnabled(SerializationFeature f) {
                return this.isEnabled(f);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public boolean isEnabled(JsonFactory.Feature f) {
                return this.isEnabled(f);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public boolean isEnabled(JsonParser.Feature f) {
                return this.isEnabled(f);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public boolean isEnabled(JsonGenerator.Feature f) {
                return this.isEnabled(f);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addDeserializers(Deserializers d) {
                DeserializerFactory df = this._deserializationContext._factory.withAdditionalDeserializers(d);
                this._deserializationContext = this._deserializationContext.with(df);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addKeyDeserializers(KeyDeserializers d) {
                DeserializerFactory df = this._deserializationContext._factory.withAdditionalKeyDeserializers(d);
                this._deserializationContext = this._deserializationContext.with(df);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addBeanDeserializerModifier(BeanDeserializerModifier modifier) {
                DeserializerFactory df = this._deserializationContext._factory.withDeserializerModifier(modifier);
                this._deserializationContext = this._deserializationContext.with(df);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addSerializers(Serializers s) {
                this._serializerFactory = this._serializerFactory.withAdditionalSerializers(s);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addKeySerializers(Serializers s) {
                this._serializerFactory = this._serializerFactory.withAdditionalKeySerializers(s);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addBeanSerializerModifier(BeanSerializerModifier modifier) {
                this._serializerFactory = this._serializerFactory.withSerializerModifier(modifier);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addAbstractTypeResolver(AbstractTypeResolver resolver) {
                DeserializerFactory df = this._deserializationContext._factory.withAbstractTypeResolver(resolver);
                this._deserializationContext = this._deserializationContext.with(df);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addTypeModifier(TypeModifier modifier) {
                TypeFactory f = this._typeFactory;
                this.setTypeFactory(f.withModifier(modifier));
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addValueInstantiators(ValueInstantiators instantiators) {
                DeserializerFactory df = this._deserializationContext._factory.withValueInstantiators(instantiators);
                this._deserializationContext = this._deserializationContext.with(df);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void insertAnnotationIntrospector(AnnotationIntrospector ai) {
                this._deserializationConfig = this._deserializationConfig.withInsertedAnnotationIntrospector(ai);
                this._serializationConfig = this._serializationConfig.withInsertedAnnotationIntrospector(ai);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void appendAnnotationIntrospector(AnnotationIntrospector ai) {
                this._deserializationConfig = this._deserializationConfig.withAppendedAnnotationIntrospector(ai);
                this._serializationConfig = this._serializationConfig.withAppendedAnnotationIntrospector(ai);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void registerSubtypes(Class<?>... subtypes) {
                this.registerSubtypes(subtypes);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void registerSubtypes(NamedType... subtypes) {
                this.registerSubtypes(subtypes);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void setMixInAnnotations(Class<?> target, Class<?> mixinSource) {
                this.addMixInAnnotations(target, mixinSource);
            }

            @Override // com.fasterxml.jackson.databind.Module.SetupContext
            public void addDeserializationProblemHandler(DeserializationProblemHandler handler) {
                this.addHandler(handler);
            }
        });
        return this;
    }

    public SerializationConfig getSerializationConfig() {
        return this._serializationConfig;
    }

    public DeserializationConfig getDeserializationConfig() {
        return this._deserializationConfig;
    }

    public ObjectMapper setSerializerFactory(SerializerFactory f) {
        this._serializerFactory = f;
        return this;
    }

    public ObjectMapper setSerializerProvider(DefaultSerializerProvider p) {
        this._serializerProvider = p;
        return this;
    }

    public SerializerProvider getSerializerProvider() {
        return this._serializerProvider;
    }

    public final void setMixInAnnotations(Map<Class<?>, Class<?>> sourceMixins) {
        this._mixInAnnotations.clear();
        if (sourceMixins != null && sourceMixins.size() > 0) {
            for (Map.Entry<Class<?>, Class<?>> en : sourceMixins.entrySet()) {
                this._mixInAnnotations.put(new ClassKey(en.getKey()), en.getValue());
            }
        }
    }

    public final void addMixInAnnotations(Class<?> target, Class<?> mixinSource) {
        this._mixInAnnotations.put(new ClassKey(target), mixinSource);
    }

    public final Class<?> findMixInClassFor(Class<?> cls) {
        if (this._mixInAnnotations == null) {
            return null;
        }
        return this._mixInAnnotations.get(new ClassKey(cls));
    }

    public final int mixInCount() {
        if (this._mixInAnnotations == null) {
            return 0;
        }
        return this._mixInAnnotations.size();
    }

    public VisibilityChecker<?> getVisibilityChecker() {
        return this._serializationConfig.getDefaultVisibilityChecker();
    }

    public void setVisibilityChecker(VisibilityChecker<?> vc) {
        this._deserializationConfig = this._deserializationConfig.with(vc);
        this._serializationConfig = this._serializationConfig.with(vc);
    }

    public ObjectMapper setVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility) {
        this._deserializationConfig = this._deserializationConfig.withVisibility(forMethod, visibility);
        this._serializationConfig = this._serializationConfig.withVisibility(forMethod, visibility);
        return this;
    }

    public SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    public ObjectMapper setSubtypeResolver(SubtypeResolver str) {
        this._subtypeResolver = str;
        this._deserializationConfig = this._deserializationConfig.with(str);
        this._serializationConfig = this._serializationConfig.with(str);
        return this;
    }

    public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector ai) {
        this._serializationConfig = this._serializationConfig.with(ai);
        this._deserializationConfig = this._deserializationConfig.with(ai);
        return this;
    }

    public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy s) {
        this._serializationConfig = this._serializationConfig.with(s);
        this._deserializationConfig = this._deserializationConfig.with(s);
        return this;
    }

    public ObjectMapper setSerializationInclusion(JsonInclude.Include incl) {
        this._serializationConfig = this._serializationConfig.withSerializationInclusion(incl);
        return this;
    }

    public ObjectMapper enableDefaultTyping() {
        return enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping dti) {
        return enableDefaultTyping(dti, JsonTypeInfo.As.WRAPPER_ARRAY);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping applicability, JsonTypeInfo.As includeAs) {
        TypeResolverBuilder<?> typer = new DefaultTypeResolverBuilder(applicability);
        return setDefaultTyping(typer.init(JsonTypeInfo.Id.CLASS, null).inclusion(includeAs));
    }

    public ObjectMapper enableDefaultTypingAsProperty(DefaultTyping applicability, String propertyName) {
        TypeResolverBuilder<?> typer = new DefaultTypeResolverBuilder(applicability);
        return setDefaultTyping(typer.init(JsonTypeInfo.Id.CLASS, null).inclusion(JsonTypeInfo.As.PROPERTY).typeProperty(propertyName));
    }

    public ObjectMapper disableDefaultTyping() {
        return setDefaultTyping(null);
    }

    public ObjectMapper setDefaultTyping(TypeResolverBuilder<?> typer) {
        this._deserializationConfig = this._deserializationConfig.with(typer);
        this._serializationConfig = this._serializationConfig.with(typer);
        return this;
    }

    public void registerSubtypes(Class<?>... classes) {
        getSubtypeResolver().registerSubtypes(classes);
    }

    public void registerSubtypes(NamedType... types) {
        getSubtypeResolver().registerSubtypes(types);
    }

    public TypeFactory getTypeFactory() {
        return this._typeFactory;
    }

    public ObjectMapper setTypeFactory(TypeFactory f) {
        this._typeFactory = f;
        this._deserializationConfig = this._deserializationConfig.with(f);
        this._serializationConfig = this._serializationConfig.with(f);
        return this;
    }

    public JavaType constructType(Type t) {
        return this._typeFactory.constructType(t);
    }

    public ObjectMapper setNodeFactory(JsonNodeFactory f) {
        this._deserializationConfig = this._deserializationConfig.with(f);
        return this;
    }

    public ObjectMapper addHandler(DeserializationProblemHandler h) {
        this._deserializationConfig = this._deserializationConfig.withHandler(h);
        return this;
    }

    public ObjectMapper clearProblemHandlers() {
        this._deserializationConfig = this._deserializationConfig.withNoProblemHandlers();
        return this;
    }

    public void setFilters(FilterProvider filterProvider) {
        this._serializationConfig = this._serializationConfig.withFilters(filterProvider);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public JsonFactory getJsonFactory() {
        return this._jsonFactory;
    }

    public ObjectMapper setDateFormat(DateFormat dateFormat) {
        this._deserializationConfig = this._deserializationConfig.with(dateFormat);
        this._serializationConfig = this._serializationConfig.with(dateFormat);
        return this;
    }

    public Object setHandlerInstantiator(HandlerInstantiator hi) {
        this._deserializationConfig = this._deserializationConfig.with(hi);
        this._serializationConfig = this._serializationConfig.with(hi);
        return this;
    }

    public ObjectMapper setInjectableValues(InjectableValues injectableValues) {
        this._injectableValues = injectableValues;
        return this;
    }

    public ObjectMapper setLocale(Locale l) {
        this._deserializationConfig = this._deserializationConfig.with(l);
        this._serializationConfig = this._serializationConfig.with(l);
        return this;
    }

    public ObjectMapper setTimeZone(TimeZone tz) {
        this._deserializationConfig = this._deserializationConfig.with(tz);
        this._serializationConfig = this._serializationConfig.with(tz);
        return this;
    }

    public ObjectMapper configure(MapperFeature f, boolean state) {
        this._serializationConfig = state ? this._serializationConfig.with(f) : this._serializationConfig.without(f);
        this._deserializationConfig = state ? this._deserializationConfig.with(f) : this._deserializationConfig.without(f);
        return this;
    }

    public ObjectMapper configure(SerializationFeature f, boolean state) {
        this._serializationConfig = state ? this._serializationConfig.with(f) : this._serializationConfig.without(f);
        return this;
    }

    public ObjectMapper configure(DeserializationFeature f, boolean state) {
        this._deserializationConfig = state ? this._deserializationConfig.with(f) : this._deserializationConfig.without(f);
        return this;
    }

    public ObjectMapper configure(JsonParser.Feature f, boolean state) {
        this._jsonFactory.configure(f, state);
        return this;
    }

    public ObjectMapper configure(JsonGenerator.Feature f, boolean state) {
        this._jsonFactory.configure(f, state);
        return this;
    }

    public ObjectMapper enable(MapperFeature... f) {
        this._deserializationConfig = this._deserializationConfig.with(f);
        this._serializationConfig = this._serializationConfig.with(f);
        return this;
    }

    public ObjectMapper disable(MapperFeature... f) {
        this._deserializationConfig = this._deserializationConfig.without(f);
        this._serializationConfig = this._serializationConfig.without(f);
        return this;
    }

    public ObjectMapper enable(DeserializationFeature feature) {
        this._deserializationConfig = this._deserializationConfig.with(feature);
        return this;
    }

    public ObjectMapper enable(DeserializationFeature first, DeserializationFeature... f) {
        this._deserializationConfig = this._deserializationConfig.with(first, f);
        return this;
    }

    public ObjectMapper disable(DeserializationFeature feature) {
        this._deserializationConfig = this._deserializationConfig.without(feature);
        return this;
    }

    public ObjectMapper disable(DeserializationFeature first, DeserializationFeature... f) {
        this._deserializationConfig = this._deserializationConfig.without(first, f);
        return this;
    }

    public ObjectMapper enable(SerializationFeature f) {
        this._serializationConfig = this._serializationConfig.with(f);
        return this;
    }

    public ObjectMapper enable(SerializationFeature first, SerializationFeature... f) {
        this._serializationConfig = this._serializationConfig.with(first, f);
        return this;
    }

    public ObjectMapper disable(SerializationFeature f) {
        this._serializationConfig = this._serializationConfig.without(f);
        return this;
    }

    public ObjectMapper disable(SerializationFeature first, SerializationFeature... f) {
        this._serializationConfig = this._serializationConfig.without(first, f);
        return this;
    }

    public boolean isEnabled(MapperFeature f) {
        return this._serializationConfig.isEnabled(f);
    }

    public boolean isEnabled(SerializationFeature f) {
        return this._serializationConfig.isEnabled(f);
    }

    public boolean isEnabled(DeserializationFeature f) {
        return this._deserializationConfig.isEnabled(f);
    }

    public boolean isEnabled(JsonFactory.Feature f) {
        return this._jsonFactory.isEnabled(f);
    }

    public boolean isEnabled(JsonParser.Feature f) {
        return this._jsonFactory.isEnabled(f);
    }

    public boolean isEnabled(JsonGenerator.Feature f) {
        return this._jsonFactory.isEnabled(f);
    }

    public JsonNodeFactory getNodeFactory() {
        return this._deserializationConfig.getNodeFactory();
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T readValue(JsonParser jsonParser, Class<T> cls) throws IOException {
        return (T) _readValue(getDeserializationConfig(), jsonParser, this._typeFactory.constructType(cls));
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException {
        return (T) _readValue(getDeserializationConfig(), jsonParser, this._typeFactory.constructType(typeReference));
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public final <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException {
        return (T) _readValue(getDeserializationConfig(), jsonParser, (JavaType) resolvedType);
    }

    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException {
        return (T) _readValue(getDeserializationConfig(), jsonParser, javaType);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T extends TreeNode> T readTree(JsonParser jp) throws IOException {
        DeserializationConfig cfg = getDeserializationConfig();
        JsonToken t = jp.getCurrentToken();
        if (t == null) {
            JsonToken t2 = jp.nextToken();
            if (t2 == null) {
                return null;
            }
        }
        JsonNode n = (JsonNode) _readValue(cfg, jp, JSON_NODE_TYPE);
        if (n == null) {
            n = getNodeFactory().nullNode();
        }
        return n;
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> MappingIterator<T> readValues(JsonParser jp, ResolvedType valueType) throws IOException {
        return readValues(jp, (JavaType) valueType);
    }

    public <T> MappingIterator<T> readValues(JsonParser jp, JavaType valueType) throws IOException {
        DeserializationConfig config = getDeserializationConfig();
        DeserializationContext ctxt = createDeserializationContext(jp, config);
        JsonDeserializer<?> deser = _findRootDeserializer(ctxt, valueType);
        return new MappingIterator<>(valueType, jp, ctxt, deser, false, null);
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> MappingIterator<T> readValues(JsonParser jp, Class<T> valueType) throws IOException {
        return readValues(jp, this._typeFactory.constructType(valueType));
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> MappingIterator<T> readValues(JsonParser jp, TypeReference<?> valueTypeRef) throws IOException {
        return readValues(jp, this._typeFactory.constructType(valueTypeRef));
    }

    public JsonNode readTree(InputStream in) throws IOException {
        JsonNode n = (JsonNode) _readMapAndClose(this._jsonFactory.createJsonParser(in), JSON_NODE_TYPE);
        return n == null ? NullNode.instance : n;
    }

    public JsonNode readTree(Reader r) throws IOException {
        JsonNode n = (JsonNode) _readMapAndClose(this._jsonFactory.createJsonParser(r), JSON_NODE_TYPE);
        return n == null ? NullNode.instance : n;
    }

    public JsonNode readTree(String content) throws IOException {
        JsonNode n = (JsonNode) _readMapAndClose(this._jsonFactory.createJsonParser(content), JSON_NODE_TYPE);
        return n == null ? NullNode.instance : n;
    }

    public JsonNode readTree(byte[] content) throws IOException {
        JsonNode n = (JsonNode) _readMapAndClose(this._jsonFactory.createJsonParser(content), JSON_NODE_TYPE);
        return n == null ? NullNode.instance : n;
    }

    public JsonNode readTree(File file) throws IOException {
        JsonNode n = (JsonNode) _readMapAndClose(this._jsonFactory.createJsonParser(file), JSON_NODE_TYPE);
        return n == null ? NullNode.instance : n;
    }

    public JsonNode readTree(URL source) throws IOException {
        JsonNode n = (JsonNode) _readMapAndClose(this._jsonFactory.createJsonParser(source), JSON_NODE_TYPE);
        return n == null ? NullNode.instance : n;
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public void writeValue(JsonGenerator jgen, Object value) throws IOException {
        SerializationConfig config = getSerializationConfig();
        if (config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _writeCloseableValue(jgen, value, config);
            return;
        }
        _serializerProvider(config).serializeValue(jgen, value);
        if (config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jgen.flush();
        }
    }

    public void writeTree(JsonGenerator jgen, JsonNode rootNode) throws IOException {
        SerializationConfig config = getSerializationConfig();
        _serializerProvider(config).serializeValue(jgen, rootNode);
        if (config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jgen.flush();
        }
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public ObjectNode createObjectNode() {
        return this._deserializationConfig.getNodeFactory().objectNode();
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public ArrayNode createArrayNode() {
        return this._deserializationConfig.getNodeFactory().arrayNode();
    }

    @Override // com.fasterxml.jackson.core.ObjectCodec
    public JsonParser treeAsTokens(TreeNode n) {
        return new TreeTraversingParser((JsonNode) n, this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.core.ObjectCodec
    public <T> T treeToValue(TreeNode treeNode, Class<T> cls) throws JsonProcessingException {
        try {
            return treeNode.getClass().isAssignableFrom(cls) ? treeNode : (T) readValue(treeAsTokens(treeNode), cls);
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw new IllegalArgumentException(e2.getMessage(), e2);
        }
    }

    public <T extends JsonNode> T valueToTree(Object fromValue) throws IllegalArgumentException {
        if (fromValue == null) {
            return null;
        }
        TokenBuffer buf = new TokenBuffer(this);
        try {
            writeValue(buf, fromValue);
            JsonParser jp = buf.asParser();
            T t = (T) readTree(jp);
            jp.close();
            return t;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public boolean canSerialize(Class<?> type) {
        return _serializerProvider(getSerializationConfig()).hasSerializerFor(type);
    }

    public boolean canDeserialize(JavaType type) {
        return createDeserializationContext(null, getDeserializationConfig()).hasValueDeserializerFor(type);
    }

    public <T> T readValue(File file, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(file), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(File file, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(file), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(File file, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(file), javaType);
    }

    public <T> T readValue(URL url, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(url), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(URL url, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(url), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(URL url, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(url), javaType);
    }

    public <T> T readValue(String str, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(str), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(String str, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(str), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(String str, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(str), javaType);
    }

    public <T> T readValue(Reader reader, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(reader), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(Reader reader, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(reader), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(Reader reader, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(reader), javaType);
    }

    public <T> T readValue(InputStream inputStream, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(inputStream), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(InputStream inputStream, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(inputStream), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(InputStream inputStream, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(inputStream), javaType);
    }

    public <T> T readValue(byte[] bArr, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(bArr), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(byte[] bArr, int i, int i2, Class<T> cls) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(bArr, i, i2), this._typeFactory.constructType(cls));
    }

    public <T> T readValue(byte[] bArr, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(bArr), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(byte[] bArr, int i, int i2, TypeReference typeReference) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(bArr, i, i2), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(byte[] bArr, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(bArr), javaType);
    }

    public <T> T readValue(byte[] bArr, int i, int i2, JavaType javaType) throws IOException {
        return (T) _readMapAndClose(this._jsonFactory.createJsonParser(bArr, i, i2), javaType);
    }

    public void writeValue(File resultFile, Object value) throws IOException {
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(resultFile, JsonEncoding.UTF8), value);
    }

    public void writeValue(OutputStream out, Object value) throws IOException {
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(out, JsonEncoding.UTF8), value);
    }

    public void writeValue(Writer w, Object value) throws IOException {
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(w), value);
    }

    public String writeValueAsString(Object value) throws IOException {
        SegmentedStringWriter sw = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(sw), value);
        return sw.getAndClear();
    }

    public byte[] writeValueAsBytes(Object value) throws IOException {
        ByteArrayBuilder bb = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(bb, JsonEncoding.UTF8), value);
        byte[] result = bb.toByteArray();
        bb.release();
        return result;
    }

    public ObjectWriter writer() {
        return new ObjectWriter(this, getSerializationConfig());
    }

    public ObjectWriter writer(SerializationFeature feature) {
        return new ObjectWriter(this, getSerializationConfig().with(feature));
    }

    public ObjectWriter writer(SerializationFeature first, SerializationFeature... other) {
        return new ObjectWriter(this, getSerializationConfig().with(first, other));
    }

    public ObjectWriter writer(DateFormat df) {
        return new ObjectWriter(this, getSerializationConfig().with(df));
    }

    public ObjectWriter writerWithView(Class<?> serializationView) {
        return new ObjectWriter(this, getSerializationConfig().withView(serializationView));
    }

    public ObjectWriter writerWithType(Class<?> rootType) {
        JavaType t = rootType == null ? null : this._typeFactory.constructType(rootType);
        return new ObjectWriter(this, getSerializationConfig(), t, null);
    }

    public ObjectWriter writerWithType(JavaType rootType) {
        return new ObjectWriter(this, getSerializationConfig(), rootType, null);
    }

    public ObjectWriter writerWithType(TypeReference<?> rootType) {
        JavaType t = rootType == null ? null : this._typeFactory.constructType(rootType);
        return new ObjectWriter(this, getSerializationConfig(), t, null);
    }

    public ObjectWriter writer(PrettyPrinter pp) {
        if (pp == null) {
            pp = ObjectWriter.NULL_PRETTY_PRINTER;
        }
        return new ObjectWriter(this, getSerializationConfig(), null, pp);
    }

    public ObjectWriter writerWithDefaultPrettyPrinter() {
        return new ObjectWriter(this, getSerializationConfig(), null, _defaultPrettyPrinter());
    }

    public ObjectWriter writer(FilterProvider filterProvider) {
        return new ObjectWriter(this, getSerializationConfig().withFilters(filterProvider));
    }

    public ObjectWriter writer(FormatSchema schema) {
        return new ObjectWriter(this, getSerializationConfig(), schema);
    }

    public ObjectReader reader() {
        return new ObjectReader(this, getDeserializationConfig()).with(this._injectableValues);
    }

    public ObjectReader reader(DeserializationFeature feature) {
        return new ObjectReader(this, getDeserializationConfig().with(feature));
    }

    public ObjectReader reader(DeserializationFeature first, DeserializationFeature... other) {
        return new ObjectReader(this, getDeserializationConfig().with(first, other));
    }

    public ObjectReader readerForUpdating(Object valueToUpdate) {
        JavaType t = this._typeFactory.constructType(valueToUpdate.getClass());
        return new ObjectReader(this, getDeserializationConfig(), t, valueToUpdate, (FormatSchema) null, this._injectableValues);
    }

    public ObjectReader reader(JavaType type) {
        return new ObjectReader(this, getDeserializationConfig(), type, (Object) null, (FormatSchema) null, this._injectableValues);
    }

    public ObjectReader reader(Class<?> type) {
        return reader(this._typeFactory.constructType(type));
    }

    public ObjectReader reader(TypeReference<?> type) {
        return reader(this._typeFactory.constructType(type));
    }

    public ObjectReader reader(JsonNodeFactory f) {
        return new ObjectReader(this, getDeserializationConfig()).with(f);
    }

    public ObjectReader reader(FormatSchema schema) {
        return new ObjectReader(this, getDeserializationConfig(), (JavaType) null, (Object) null, schema, this._injectableValues);
    }

    public ObjectReader reader(InjectableValues injectableValues) {
        return new ObjectReader(this, getDeserializationConfig(), (JavaType) null, (Object) null, (FormatSchema) null, injectableValues);
    }

    public ObjectReader readerWithView(Class<?> view) {
        return new ObjectReader(this, getDeserializationConfig().withView(view));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T convertValue(Object obj, Class<T> cls) throws IllegalArgumentException {
        if (obj == 0) {
            return null;
        }
        return !obj.getClass().isAssignableFrom(cls) ? (T) _convert(obj, this._typeFactory.constructType(cls)) : obj;
    }

    public <T> T convertValue(Object obj, TypeReference<?> typeReference) throws IllegalArgumentException {
        return (T) convertValue(obj, this._typeFactory.constructType(typeReference));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T convertValue(Object obj, JavaType javaType) throws IllegalArgumentException {
        if (obj == 0) {
            return null;
        }
        return !obj.getClass().isAssignableFrom(javaType.getRawClass()) ? (T) _convert(obj, javaType) : obj;
    }

    protected Object _convert(Object fromValue, JavaType toValueType) throws IllegalArgumentException {
        Object objDeserialize;
        TokenBuffer buf = new TokenBuffer(this);
        try {
            SerializationConfig config = getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE);
            _serializerProvider(config).serializeValue(buf, fromValue);
            JsonParser jp = buf.asParser();
            DeserializationConfig deserConfig = getDeserializationConfig();
            JsonToken t = _initForReading(jp);
            if (t == JsonToken.VALUE_NULL) {
                objDeserialize = _findRootDeserializer(createDeserializationContext(jp, deserConfig), toValueType).getNullValue();
            } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
                objDeserialize = null;
            } else {
                DeserializationContext ctxt = createDeserializationContext(jp, deserConfig);
                JsonDeserializer<Object> deser = _findRootDeserializer(ctxt, toValueType);
                objDeserialize = deser.deserialize(jp, ctxt);
            }
            jp.close();
            return objDeserialize;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public JsonSchema generateJsonSchema(Class<?> t) throws JsonMappingException {
        return _serializerProvider(getSerializationConfig()).generateJsonSchema(t);
    }

    protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
        return this._serializerProvider.createInstance(config, this._serializerFactory);
    }

    protected PrettyPrinter _defaultPrettyPrinter() {
        return new DefaultPrettyPrinter();
    }

    protected final void _configAndWriteValue(JsonGenerator jgen, Object value) throws IOException {
        SerializationConfig cfg = getSerializationConfig();
        if (cfg.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jgen.useDefaultPrettyPrinter();
        }
        if (cfg.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _configAndWriteCloseable(jgen, value, cfg);
            return;
        }
        boolean closed = false;
        try {
            _serializerProvider(cfg).serializeValue(jgen, value);
            closed = true;
            jgen.close();
        } finally {
            if (!closed) {
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
        }
    }

    protected final void _configAndWriteValue(JsonGenerator jgen, Object value, Class<?> viewClass) throws IOException {
        SerializationConfig cfg = getSerializationConfig().withView(viewClass);
        if (cfg.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jgen.useDefaultPrettyPrinter();
        }
        if (cfg.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _configAndWriteCloseable(jgen, value, cfg);
            return;
        }
        boolean closed = false;
        try {
            _serializerProvider(cfg).serializeValue(jgen, value);
            closed = true;
            jgen.close();
        } finally {
            if (!closed) {
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private final void _configAndWriteCloseable(JsonGenerator jgen, Object value, SerializationConfig cfg) throws IOException {
        Closeable toClose = (Closeable) value;
        try {
            _serializerProvider(cfg).serializeValue(jgen, value);
            jgen = null;
            jgen.close();
            toClose = null;
            toClose.close();
        } finally {
            if (jgen != null) {
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    private final void _writeCloseableValue(JsonGenerator jgen, Object value, SerializationConfig cfg) throws IOException {
        Closeable toClose = (Closeable) value;
        try {
            _serializerProvider(cfg).serializeValue(jgen, value);
            if (cfg.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jgen.flush();
            }
            toClose = null;
            toClose.close();
        } finally {
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e) {
                }
            }
        }
    }

    protected final DefaultDeserializationContext createDeserializationContext(JsonParser jp, DeserializationConfig cfg) {
        return this._deserializationContext.createInstance(cfg, jp, this._injectableValues);
    }

    protected Object _readValue(DeserializationConfig cfg, JsonParser jp, JavaType valueType) throws IOException {
        Object result;
        JsonToken t = _initForReading(jp);
        if (t == JsonToken.VALUE_NULL) {
            result = _findRootDeserializer(createDeserializationContext(jp, cfg), valueType).getNullValue();
        } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
            result = null;
        } else {
            DeserializationContext ctxt = createDeserializationContext(jp, cfg);
            JsonDeserializer<Object> deser = _findRootDeserializer(ctxt, valueType);
            if (cfg.useRootWrapping()) {
                result = _unwrapAndDeserialize(jp, ctxt, cfg, valueType, deser);
            } else {
                result = deser.deserialize(jp, ctxt);
            }
        }
        jp.clearCurrentToken();
        return result;
    }

    protected Object _readMapAndClose(JsonParser jp, JavaType valueType) throws IOException {
        Object result;
        try {
            JsonToken t = _initForReading(jp);
            if (t == JsonToken.VALUE_NULL) {
                result = _findRootDeserializer(createDeserializationContext(jp, getDeserializationConfig()), valueType).getNullValue();
            } else if (t == JsonToken.END_ARRAY || t == JsonToken.END_OBJECT) {
                result = null;
            } else {
                DeserializationConfig cfg = getDeserializationConfig();
                DeserializationContext ctxt = createDeserializationContext(jp, cfg);
                JsonDeserializer<Object> deser = _findRootDeserializer(ctxt, valueType);
                if (cfg.useRootWrapping()) {
                    result = _unwrapAndDeserialize(jp, ctxt, cfg, valueType, deser);
                } else {
                    result = deser.deserialize(jp, ctxt);
                }
            }
            jp.clearCurrentToken();
            return result;
        } finally {
            try {
                jp.close();
            } catch (IOException e) {
            }
        }
    }

    protected JsonToken _initForReading(JsonParser jp) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == null && (t = jp.nextToken()) == null) {
            throw JsonMappingException.from(jp, "No content to map due to end-of-input");
        }
        return t;
    }

    protected Object _unwrapAndDeserialize(JsonParser jp, DeserializationContext ctxt, DeserializationConfig config, JavaType rootType, JsonDeserializer<Object> deser) throws IOException {
        String expName = config.getRootName();
        if (expName == null) {
            SerializedString sstr = this._rootNames.findRootName(rootType, config);
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
        Object result = deser.deserialize(jp, ctxt);
        if (jp.nextToken() != JsonToken.END_OBJECT) {
            throw JsonMappingException.from(jp, "Current token not END_OBJECT (to match wrapper object with root name '" + expName + "'), but " + jp.getCurrentToken());
        }
        return result;
    }

    protected JsonDeserializer<Object> _findRootDeserializer(DeserializationContext ctxt, JavaType valueType) throws JsonMappingException {
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
}
