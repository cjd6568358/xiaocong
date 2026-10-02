package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.impl.CreatorCollector;
import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.deser.std.EnumMapDeserializer;
import com.fasterxml.jackson.databind.deser.std.EnumSetDeserializer;
import com.fasterxml.jackson.databind.deser.std.JacksonDeserializers;
import com.fasterxml.jackson.databind.deser.std.JdkDeserializers;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.fasterxml.jackson.databind.deser.std.PrimitiveArrayDeserializers;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializers;
import com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BasicDeserializerFactory extends DeserializerFactory {
    static final HashMap<String, Class<? extends Collection>> _collectionFallbacks;
    static final HashMap<String, Class<? extends Map>> _mapFallbacks;
    protected final DeserializerFactoryConfig _factoryConfig;
    protected OptionalHandlerFactory optionalHandlers = OptionalHandlerFactory.instance;
    protected static final HashMap<ClassKey, JsonDeserializer<Object>> _simpleDeserializers = new HashMap<>();
    protected static final HashMap<JavaType, JsonDeserializer<Object>> _arrayDeserializers = PrimitiveArrayDeserializers.getAll();
    protected static final HashMap<JavaType, KeyDeserializer> _keyDeserializers = StdKeyDeserializers.constructAll();

    protected abstract DeserializerFactory withConfig(DeserializerFactoryConfig deserializerFactoryConfig);

    static {
        _add(_simpleDeserializers, Object.class, new UntypedObjectDeserializer());
        StringDeserializer stringDeserializer = new StringDeserializer();
        _add(_simpleDeserializers, String.class, stringDeserializer);
        _add(_simpleDeserializers, CharSequence.class, stringDeserializer);
        _add(_simpleDeserializers, NumberDeserializers.all());
        _add(_simpleDeserializers, DateDeserializers.all());
        _add(_simpleDeserializers, JdkDeserializers.all());
        _add(_simpleDeserializers, JacksonDeserializers.all());
        _mapFallbacks = new HashMap<>();
        _mapFallbacks.put(Map.class.getName(), LinkedHashMap.class);
        _mapFallbacks.put(ConcurrentMap.class.getName(), ConcurrentHashMap.class);
        _mapFallbacks.put(SortedMap.class.getName(), TreeMap.class);
        _mapFallbacks.put("java.util.NavigableMap", TreeMap.class);
        try {
            _mapFallbacks.put(Class.forName("java.util.ConcurrentNavigableMap").getName(), (Class<? extends Map>) Class.forName("java.util.ConcurrentSkipListMap"));
        } catch (ClassNotFoundException e) {
        }
        _collectionFallbacks = new HashMap<>();
        _collectionFallbacks.put(Collection.class.getName(), ArrayList.class);
        _collectionFallbacks.put(List.class.getName(), ArrayList.class);
        _collectionFallbacks.put(Set.class.getName(), HashSet.class);
        _collectionFallbacks.put(SortedSet.class.getName(), TreeSet.class);
        _collectionFallbacks.put(Queue.class.getName(), LinkedList.class);
        _collectionFallbacks.put("java.util.Deque", LinkedList.class);
        _collectionFallbacks.put("java.util.NavigableSet", TreeSet.class);
    }

    private static void _add(Map<ClassKey, JsonDeserializer<Object>> desers, StdDeserializer<?>[] serializers) {
        for (StdDeserializer<?> ser : serializers) {
            _add(desers, ser.getValueClass(), ser);
        }
    }

    private static void _add(Map<ClassKey, JsonDeserializer<Object>> desers, Class<?> valueClass, StdDeserializer<?> stdDeser) {
        desers.put(new ClassKey(valueClass), stdDeser);
    }

    protected BasicDeserializerFactory(DeserializerFactoryConfig config) {
        this._factoryConfig = config;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public final DeserializerFactory withAdditionalDeserializers(Deserializers additional) {
        return withConfig(this._factoryConfig.withAdditionalDeserializers(additional));
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public final DeserializerFactory withAdditionalKeyDeserializers(KeyDeserializers additional) {
        return withConfig(this._factoryConfig.withAdditionalKeyDeserializers(additional));
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public final DeserializerFactory withDeserializerModifier(BeanDeserializerModifier modifier) {
        return withConfig(this._factoryConfig.withDeserializerModifier(modifier));
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public final DeserializerFactory withAbstractTypeResolver(AbstractTypeResolver resolver) {
        return withConfig(this._factoryConfig.withAbstractTypeResolver(resolver));
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public final DeserializerFactory withValueInstantiators(ValueInstantiators instantiators) {
        return withConfig(this._factoryConfig.withValueInstantiators(instantiators));
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JavaType mapAbstractType(DeserializationConfig config, JavaType type) throws JsonMappingException {
        while (true) {
            JavaType next = _mapAbstractType2(config, type);
            if (next == null) {
                return type;
            }
            Class<?> prevCls = type.getRawClass();
            Class<?> nextCls = next.getRawClass();
            if (prevCls == nextCls || !prevCls.isAssignableFrom(nextCls)) {
                throw new IllegalArgumentException("Invalid abstract type resolution from " + type + " to " + next + ": latter is not a subtype of former");
            }
            type = next;
        }
    }

    private JavaType _mapAbstractType2(DeserializationConfig config, JavaType type) throws JsonMappingException {
        Class<?> currClass = type.getRawClass();
        if (this._factoryConfig.hasAbstractTypeResolvers()) {
            for (AbstractTypeResolver resolver : this._factoryConfig.abstractTypeResolvers()) {
                JavaType concrete = resolver.findTypeMapping(config, type);
                if (concrete != null && concrete.getRawClass() != currClass) {
                    return concrete;
                }
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public ValueInstantiator findValueInstantiator(DeserializationContext ctxt, BeanDescription beanDesc) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        ValueInstantiator instantiator = null;
        AnnotatedClass ac = beanDesc.getClassInfo();
        Object instDef = ctxt.getAnnotationIntrospector().findValueInstantiator(ac);
        if (instDef != null) {
            instantiator = _valueInstantiatorInstance(config, ac, instDef);
        }
        if (instantiator == null && (instantiator = _findStdValueInstantiator(config, beanDesc)) == null) {
            instantiator = _constructDefaultValueInstantiator(ctxt, beanDesc);
        }
        if (this._factoryConfig.hasValueInstantiators()) {
            for (ValueInstantiators insts : this._factoryConfig.valueInstantiators()) {
                instantiator = insts.findValueInstantiator(config, beanDesc, instantiator);
                if (instantiator == null) {
                    throw new JsonMappingException("Broken registered ValueInstantiators (of type " + insts.getClass().getName() + "): returned null ValueInstantiator");
                }
            }
        }
        return instantiator;
    }

    private ValueInstantiator _findStdValueInstantiator(DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        return JacksonDeserializers.findValueInstantiator(config, beanDesc);
    }

    protected ValueInstantiator _constructDefaultValueInstantiator(DeserializationContext ctxt, BeanDescription beanDesc) throws JsonMappingException {
        AnnotatedConstructor defaultCtor;
        boolean fixAccess = ctxt.canOverrideAccessModifiers();
        CreatorCollector creators = new CreatorCollector(beanDesc, fixAccess);
        AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
        if (beanDesc.getType().isConcrete() && (defaultCtor = beanDesc.findDefaultConstructor()) != null) {
            if (fixAccess) {
                ClassUtil.checkAndFixAccess(defaultCtor.getAnnotated());
            }
            creators.setDefaultConstructor(defaultCtor);
        }
        DeserializationConfig config = ctxt.getConfig();
        VisibilityChecker<?> vchecker = config.getAnnotationIntrospector().findAutoDetectVisibility(beanDesc.getClassInfo(), config.getDefaultVisibilityChecker());
        _addDeserializerFactoryMethods(ctxt, beanDesc, vchecker, intr, creators);
        _addDeserializerConstructors(ctxt, beanDesc, vchecker, intr, creators);
        return creators.constructValueInstantiator(config);
    }

    public ValueInstantiator _valueInstantiatorInstance(DeserializationConfig config, Annotated annotated, Object instDef) throws JsonMappingException {
        if (instDef == null) {
            return null;
        }
        if (instDef instanceof ValueInstantiator) {
            return (ValueInstantiator) instDef;
        }
        if (!(instDef instanceof Class)) {
            throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + instDef.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
        }
        Class<?> instClass = (Class) instDef;
        if (instClass == NoClass.class) {
            return null;
        }
        if (!ValueInstantiator.class.isAssignableFrom(instClass)) {
            throw new IllegalStateException("AnnotationIntrospector returned Class " + instClass.getName() + "; expected Class<ValueInstantiator>");
        }
        HandlerInstantiator hi = config.getHandlerInstantiator();
        if (hi != null) {
            return hi.valueInstantiatorInstance(config, annotated, instClass);
        }
        return (ValueInstantiator) ClassUtil.createInstance(instClass, config.canOverrideAccessModifiers());
    }

    protected void _addDeserializerConstructors(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators) throws JsonMappingException {
        for (AnnotatedConstructor ctor : beanDesc.getConstructors()) {
            int argCount = ctor.getParameterCount();
            if (argCount >= 1) {
                boolean isCreator = intr.hasCreatorAnnotation(ctor);
                boolean isVisible = vchecker.isCreatorVisible(ctor);
                if (argCount == 1) {
                    _handleSingleArgumentConstructor(ctxt, beanDesc, vchecker, intr, creators, ctor, isCreator, isVisible);
                } else if (isCreator || isVisible) {
                    AnnotatedParameter nonAnnotatedParam = null;
                    int namedCount = 0;
                    int injectCount = 0;
                    CreatorProperty[] properties = new CreatorProperty[argCount];
                    for (int i = 0; i < argCount; i++) {
                        AnnotatedParameter param = ctor.getParameter(i);
                        String name = param == null ? null : intr.findDeserializationName(param);
                        Object injectId = intr.findInjectableValueId(param);
                        if (name != null && name.length() > 0) {
                            namedCount++;
                            properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                        } else if (injectId != null) {
                            injectCount++;
                            properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                        } else if (nonAnnotatedParam == null) {
                            nonAnnotatedParam = param;
                        }
                    }
                    if (isCreator || namedCount > 0 || injectCount > 0) {
                        if (namedCount + injectCount == argCount) {
                            creators.addPropertyCreator(ctor, properties);
                        } else if (namedCount == 0 && injectCount + 1 == argCount) {
                            creators.addDelegatingCreator(ctor, properties);
                        } else {
                            throw new IllegalArgumentException("Argument #" + nonAnnotatedParam.getIndex() + " of constructor " + ctor + " has no property name annotation; must have name when multiple-paramater constructor annotated as Creator");
                        }
                    }
                }
            }
        }
    }

    protected boolean _handleSingleArgumentConstructor(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, AnnotatedConstructor ctor, boolean isCreator, boolean isVisible) throws JsonMappingException {
        AnnotatedParameter param = ctor.getParameter(0);
        String name = intr.findDeserializationName(param);
        Object injectId = intr.findInjectableValueId(param);
        if (injectId != null || (name != null && name.length() > 0)) {
            CreatorProperty[] properties = {constructCreatorProperty(ctxt, beanDesc, name, 0, param, injectId)};
            creators.addPropertyCreator(ctor, properties);
            return true;
        }
        Class<?> type = ctor.getRawParameterType(0);
        if (type == String.class) {
            if (isCreator || isVisible) {
                creators.addStringCreator(ctor);
            }
            return true;
        }
        if (type == Integer.TYPE || type == Integer.class) {
            if (isCreator || isVisible) {
                creators.addIntCreator(ctor);
            }
            return true;
        }
        if (type == Long.TYPE || type == Long.class) {
            if (isCreator || isVisible) {
                creators.addLongCreator(ctor);
            }
            return true;
        }
        if (type == Double.TYPE || type == Double.class) {
            if (isCreator || isVisible) {
                creators.addDoubleCreator(ctor);
            }
            return true;
        }
        if (isCreator) {
            creators.addDelegatingCreator(ctor, null);
            return true;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x006e  */
    /* JADX WARN: Code duplicated, block: B:26:0x0097 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:27:0x0099  */
    /* JADX WARN: Code duplicated, block: B:28:0x00a8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x00aa  */
    /* JADX WARN: Code duplicated, block: B:53:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x00b9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x0094 A[SYNTHETIC] */
    protected void _addDeserializerFactoryMethods(DeserializationContext ctxt, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators) throws JsonMappingException {
        AnnotatedParameter nonAnnotatedParam;
        CreatorProperty[] properties;
        int namedCount;
        int injectCount;
        int i;
        AnnotatedParameter param;
        String name;
        Object injectId;
        DeserializationConfig config = ctxt.getConfig();
        for (AnnotatedMethod factory : beanDesc.getFactoryMethods()) {
            int argCount = factory.getParameterCount();
            if (argCount >= 1) {
                boolean isCreator = intr.hasCreatorAnnotation(factory);
                if (argCount == 1) {
                    AnnotatedParameter param2 = factory.getParameter(0);
                    String name2 = intr.findDeserializationName(param2);
                    if (intr.findInjectableValueId(param2) == null && (name2 == null || name2.length() == 0)) {
                        _handleSingleArgumentFactory(config, beanDesc, vchecker, intr, creators, factory, isCreator);
                    } else {
                        nonAnnotatedParam = null;
                        properties = new CreatorProperty[argCount];
                        namedCount = 0;
                        injectCount = 0;
                        for (i = 0; i < argCount; i++) {
                            param = factory.getParameter(i);
                            name = intr.findDeserializationName(param);
                            injectId = intr.findInjectableValueId(param);
                            if (name == null && name.length() > 0) {
                                namedCount++;
                                properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                            } else if (injectId != null) {
                                injectCount++;
                                properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                            } else if (nonAnnotatedParam == null) {
                                nonAnnotatedParam = param;
                            }
                        }
                        if (!isCreator || namedCount > 0 || injectCount > 0) {
                            if (namedCount + injectCount == argCount) {
                                creators.addPropertyCreator(factory, properties);
                            } else if (namedCount != 0 && injectCount + 1 == argCount) {
                                creators.addDelegatingCreator(factory, properties);
                            } else {
                                throw new IllegalArgumentException("Argument #" + nonAnnotatedParam.getIndex() + " of factory method " + factory + " has no property name annotation; must have name when multiple-paramater constructor annotated as Creator");
                            }
                        }
                    }
                } else if (intr.hasCreatorAnnotation(factory)) {
                    nonAnnotatedParam = null;
                    properties = new CreatorProperty[argCount];
                    namedCount = 0;
                    injectCount = 0;
                    while (i < argCount) {
                        param = factory.getParameter(i);
                        name = intr.findDeserializationName(param);
                        injectId = intr.findInjectableValueId(param);
                        if (name == null) {
                            if (injectId != null) {
                                injectCount++;
                                properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                            } else if (nonAnnotatedParam == null) {
                                nonAnnotatedParam = param;
                            }
                        } else if (injectId != null) {
                            injectCount++;
                            properties[i] = constructCreatorProperty(ctxt, beanDesc, name, i, param, injectId);
                        } else if (nonAnnotatedParam == null) {
                            nonAnnotatedParam = param;
                        }
                    }
                    if (!isCreator) {
                    }
                    if (namedCount + injectCount == argCount) {
                        creators.addPropertyCreator(factory, properties);
                    } else {
                        if (namedCount != 0) {
                        }
                        throw new IllegalArgumentException("Argument #" + nonAnnotatedParam.getIndex() + " of factory method " + factory + " has no property name annotation; must have name when multiple-paramater constructor annotated as Creator");
                    }
                } else {
                    continue;
                }
            }
        }
    }

    protected boolean _handleSingleArgumentFactory(DeserializationConfig config, BeanDescription beanDesc, VisibilityChecker<?> vchecker, AnnotationIntrospector intr, CreatorCollector creators, AnnotatedMethod factory, boolean isCreator) throws JsonMappingException {
        Class<?> type = factory.getRawParameterType(0);
        if (type == String.class) {
            if (!isCreator && !vchecker.isCreatorVisible(factory)) {
                return true;
            }
            creators.addStringCreator(factory);
            return true;
        }
        if (type == Integer.TYPE || type == Integer.class) {
            if (!isCreator && !vchecker.isCreatorVisible(factory)) {
                return true;
            }
            creators.addIntCreator(factory);
            return true;
        }
        if (type == Long.TYPE || type == Long.class) {
            if (!isCreator && !vchecker.isCreatorVisible(factory)) {
                return true;
            }
            creators.addLongCreator(factory);
            return true;
        }
        if (type == Double.TYPE || type == Double.class) {
            if (!isCreator && !vchecker.isCreatorVisible(factory)) {
                return true;
            }
            creators.addDoubleCreator(factory);
            return true;
        }
        if (type == Boolean.TYPE || type == Boolean.class) {
            if (!isCreator && !vchecker.isCreatorVisible(factory)) {
                return true;
            }
            creators.addBooleanCreator(factory);
            return true;
        }
        if (!intr.hasCreatorAnnotation(factory)) {
            return false;
        }
        creators.addDelegatingCreator(factory, null);
        return true;
    }

    protected CreatorProperty constructCreatorProperty(DeserializationContext ctxt, BeanDescription beanDesc, String name, int index, AnnotatedParameter param, Object injectableValueId) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        JavaType t0 = config.getTypeFactory().constructType(param.getParameterType(), beanDesc.bindingsForBeanType());
        BeanProperty.Std property = new BeanProperty.Std(name, t0, beanDesc.getClassAnnotations(), param);
        JavaType type = resolveType(ctxt, beanDesc, t0, param);
        if (type != t0) {
            property.withType(type);
        }
        JsonDeserializer<?> jsonDeserializerFindDeserializerFromAnnotation = findDeserializerFromAnnotation(ctxt, param);
        JavaType type2 = modifyTypeByAnnotation(ctxt, param, type);
        TypeDeserializer typeDeser = (TypeDeserializer) type2.getTypeHandler();
        if (typeDeser == null) {
            typeDeser = findTypeDeserializer(config, type2);
        }
        CreatorProperty prop = new CreatorProperty(name, type2, typeDeser, beanDesc.getClassAnnotations(), param, index, injectableValueId);
        if (jsonDeserializerFindDeserializerFromAnnotation != null) {
            return prop.withValueDeserializer(jsonDeserializerFindDeserializerFromAnnotation);
        }
        return prop;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createArrayDeserializer(DeserializationContext ctxt, ArrayType type, BeanDescription beanDesc) throws JsonMappingException {
        JavaType elemType = type.getContentType();
        JsonDeserializer<?> jsonDeserializer = (JsonDeserializer) elemType.getValueHandler();
        if (jsonDeserializer == null) {
            JsonDeserializer<?> deser = _arrayDeserializers.get(elemType);
            if (deser != null) {
                JsonDeserializer<?> custom = _findCustomArrayDeserializer(type, ctxt.getConfig(), beanDesc, null, jsonDeserializer);
                return custom != null ? custom : deser;
            }
            if (elemType.isPrimitive()) {
                throw new IllegalArgumentException("Internal error: primitive type (" + type + ") passed, no array deserializer found");
            }
        }
        TypeDeserializer elemTypeDeser = (TypeDeserializer) elemType.getTypeHandler();
        if (elemTypeDeser == null) {
            elemTypeDeser = findTypeDeserializer(ctxt.getConfig(), elemType);
        }
        JsonDeserializer<?> custom2 = _findCustomArrayDeserializer(type, ctxt.getConfig(), beanDesc, elemTypeDeser, jsonDeserializer);
        return custom2 != null ? custom2 : new ObjectArrayDeserializer(type, jsonDeserializer, elemTypeDeser);
    }

    protected JsonDeserializer<?> _findCustomArrayDeserializer(ArrayType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findArrayDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createCollectionDeserializer(DeserializationContext deserializationContext, CollectionType collectionType, BeanDescription beanDescription) throws JsonMappingException {
        CollectionType collectionType2;
        JavaType contentType = collectionType.getContentType();
        JsonDeserializer<?> jsonDeserializer = (JsonDeserializer) contentType.getValueHandler();
        TypeDeserializer typeDeserializerFindTypeDeserializer = (TypeDeserializer) contentType.getTypeHandler();
        if (typeDeserializerFindTypeDeserializer == null) {
            typeDeserializerFindTypeDeserializer = findTypeDeserializer(deserializationContext.getConfig(), contentType);
        }
        JsonDeserializer<?> jsonDeserializer_findCustomCollectionDeserializer = _findCustomCollectionDeserializer(collectionType, deserializationContext.getConfig(), beanDescription, typeDeserializerFindTypeDeserializer, jsonDeserializer);
        if (jsonDeserializer_findCustomCollectionDeserializer == null) {
            Class<?> rawClass = collectionType.getRawClass();
            if (jsonDeserializer == null && EnumSet.class.isAssignableFrom(rawClass)) {
                return new EnumSetDeserializer(contentType, null);
            }
            if (collectionType.isInterface() || collectionType.isAbstract()) {
                collectionType2 = collectionType;
                Class<? extends Collection> cls = _collectionFallbacks.get(rawClass.getName());
                if (cls == null) {
                    throw new IllegalArgumentException("Can not find a deserializer for non-concrete Collection type " + collectionType);
                }
                CollectionType collectionType3 = (CollectionType) deserializationContext.getConfig().constructSpecializedType(collectionType, cls);
                beanDescription = deserializationContext.getConfig().introspectForCreation(collectionType3);
                collectionType2 = collectionType3;
            }
            collectionType2 = collectionType;
            ValueInstantiator valueInstantiatorFindValueInstantiator = findValueInstantiator(deserializationContext, beanDescription);
            if (contentType.getRawClass() == String.class) {
                return new StringCollectionDeserializer(collectionType2, jsonDeserializer, valueInstantiatorFindValueInstantiator);
            }
            return new CollectionDeserializer(collectionType2, jsonDeserializer, typeDeserializerFindTypeDeserializer, valueInstantiatorFindValueInstantiator);
        }
        return jsonDeserializer_findCustomCollectionDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createCollectionLikeDeserializer(DeserializationContext ctxt, CollectionLikeType type, BeanDescription beanDesc) throws JsonMappingException {
        JavaType contentType = type.getContentType();
        JsonDeserializer<?> jsonDeserializer = (JsonDeserializer) contentType.getValueHandler();
        TypeDeserializer contentTypeDeser = (TypeDeserializer) contentType.getTypeHandler();
        if (contentTypeDeser == null) {
            contentTypeDeser = findTypeDeserializer(ctxt.getConfig(), contentType);
        }
        return _findCustomCollectionLikeDeserializer(type, ctxt.getConfig(), beanDesc, contentTypeDeser, jsonDeserializer);
    }

    protected JsonDeserializer<?> _findCustomCollectionDeserializer(CollectionType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findCollectionDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomCollectionLikeDeserializer(CollectionLikeType type, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findCollectionLikeDeserializer(type, config, beanDesc, elementTypeDeserializer, elementDeserializer);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createMapDeserializer(DeserializationContext ctxt, MapType type, BeanDescription beanDesc) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        JavaType keyType = type.getKeyType();
        JavaType contentType = type.getContentType();
        JsonDeserializer<?> jsonDeserializer = (JsonDeserializer) contentType.getValueHandler();
        KeyDeserializer keyDes = (KeyDeserializer) keyType.getValueHandler();
        TypeDeserializer contentTypeDeser = (TypeDeserializer) contentType.getTypeHandler();
        if (contentTypeDeser == null) {
            contentTypeDeser = findTypeDeserializer(config, contentType);
        }
        JsonDeserializer<?> custom = _findCustomMapDeserializer(type, config, beanDesc, keyDes, contentTypeDeser, jsonDeserializer);
        if (custom == null) {
            Class<?> mapClass = type.getRawClass();
            if (EnumMap.class.isAssignableFrom(mapClass)) {
                Class<?> kt = keyType.getRawClass();
                if (kt == null || !kt.isEnum()) {
                    throw new IllegalArgumentException("Can not construct EnumMap; generic (key) type not available");
                }
                return new EnumMapDeserializer(type, null, jsonDeserializer);
            }
            if (type.isInterface() || type.isAbstract()) {
                Class<? extends Map> fallback = _mapFallbacks.get(mapClass.getName());
                if (fallback == null) {
                    throw new IllegalArgumentException("Can not find a deserializer for non-concrete Map type " + type);
                }
                type = (MapType) config.constructSpecializedType(type, fallback);
                beanDesc = config.introspectForCreation(type);
            }
            ValueInstantiator inst = findValueInstantiator(ctxt, beanDesc);
            MapDeserializer md = new MapDeserializer(type, inst, keyDes, jsonDeserializer, contentTypeDeser);
            md.setIgnorableProperties(config.getAnnotationIntrospector().findPropertiesToIgnore(beanDesc.getClassInfo()));
            return md;
        }
        return custom;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createMapLikeDeserializer(DeserializationContext ctxt, MapLikeType type, BeanDescription beanDesc) throws JsonMappingException {
        JavaType keyType = type.getKeyType();
        JavaType contentType = type.getContentType();
        JsonDeserializer<?> jsonDeserializer = (JsonDeserializer) contentType.getValueHandler();
        KeyDeserializer keyDes = (KeyDeserializer) keyType.getValueHandler();
        TypeDeserializer contentTypeDeser = (TypeDeserializer) contentType.getTypeHandler();
        if (contentTypeDeser == null) {
            contentTypeDeser = findTypeDeserializer(ctxt.getConfig(), contentType);
        }
        return _findCustomMapLikeDeserializer(type, ctxt.getConfig(), beanDesc, keyDes, contentTypeDeser, jsonDeserializer);
    }

    protected JsonDeserializer<?> _findCustomMapDeserializer(MapType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findMapDeserializer(type, config, beanDesc, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    protected JsonDeserializer<?> _findCustomMapLikeDeserializer(MapLikeType type, DeserializationConfig config, BeanDescription beanDesc, KeyDeserializer keyDeserializer, TypeDeserializer elementTypeDeserializer, JsonDeserializer<?> elementDeserializer) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findMapLikeDeserializer(type, config, beanDesc, keyDeserializer, elementTypeDeserializer, elementDeserializer);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createEnumDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> enumClass = type.getRawClass();
        JsonDeserializer<?> custom = _findCustomEnumDeserializer(enumClass, ctxt.getConfig(), beanDesc);
        if (custom == null) {
            for (AnnotatedMethod factory : beanDesc.getFactoryMethods()) {
                if (ctxt.getAnnotationIntrospector().hasCreatorAnnotation(factory)) {
                    int argCount = factory.getParameterCount();
                    if (argCount == 1) {
                        Class<?> returnType = factory.getRawReturnType();
                        if (returnType.isAssignableFrom(enumClass)) {
                            return EnumDeserializer.deserializerForCreator(ctxt.getConfig(), enumClass, factory);
                        }
                    }
                    throw new IllegalArgumentException("Unsuitable method (" + factory + ") decorated with @JsonCreator (for Enum type " + enumClass.getName() + ")");
                }
            }
            return new EnumDeserializer(constructEnumResolver(enumClass, ctxt.getConfig(), beanDesc.findJsonValueMethod()));
        }
        return custom;
    }

    protected JsonDeserializer<?> _findCustomEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findEnumDeserializer(type, config, beanDesc);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<?> createTreeDeserializer(DeserializationConfig config, JavaType nodeType, BeanDescription beanDesc) throws JsonMappingException {
        Class<?> rawClass = nodeType.getRawClass();
        JsonDeserializer<?> custom = _findCustomTreeNodeDeserializer(rawClass, config, beanDesc);
        return custom != null ? custom : JsonNodeDeserializer.getDeserializer(rawClass);
    }

    protected JsonDeserializer<?> _findCustomTreeNodeDeserializer(Class<? extends JsonNode> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findTreeNodeDeserializer(type, config, beanDesc);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public TypeDeserializer findTypeDeserializer(DeserializationConfig config, JavaType baseType) throws JsonMappingException {
        JavaType defaultType;
        Class<?> cls = baseType.getRawClass();
        BeanDescription bean = config.introspectClassAnnotations(cls);
        AnnotatedClass ac = bean.getClassInfo();
        AnnotationIntrospector ai = config.getAnnotationIntrospector();
        TypeResolverBuilder<?> b = ai.findTypeResolver(config, ac, baseType);
        Collection<NamedType> subtypes = null;
        if (b == null) {
            b = config.getDefaultTyper(baseType);
            if (b == null) {
                return null;
            }
        } else {
            subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(ac, config, ai);
        }
        if (b.getDefaultImpl() == null && baseType.isAbstract() && (defaultType = mapAbstractType(config, baseType)) != null && defaultType.getRawClass() != baseType.getRawClass()) {
            b = b.defaultImpl(defaultType.getRawClass());
        }
        return b.buildTypeDeserializer(config, baseType, subtypes);
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public KeyDeserializer createKeyDeserializer(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        if (this._factoryConfig.hasKeyDeserializers()) {
            BeanDescription beanDesc = config.introspectClassAnnotations(type.getRawClass());
            for (KeyDeserializers d : this._factoryConfig.keyDeserializers()) {
                KeyDeserializer deser = d.findKeyDeserializer(type, config, beanDesc);
                if (deser != null) {
                    return deser;
                }
            }
        }
        Class<?> raw = type.getRawClass();
        if (raw == String.class || raw == Object.class) {
            return StdKeyDeserializers.constructStringKeyDeserializer(config, type);
        }
        KeyDeserializer kdes = _keyDeserializers.get(type);
        if (kdes == null) {
            if (type.isEnumType()) {
                return _createEnumKeyDeserializer(ctxt, type);
            }
            return StdKeyDeserializers.findStringBasedKeyDeserializer(config, type);
        }
        return kdes;
    }

    private KeyDeserializer _createEnumKeyDeserializer(DeserializationContext ctxt, JavaType type) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        BeanDescription beanDesc = config.introspect(type);
        JsonDeserializer<?> des = findDeserializerFromAnnotation(ctxt, beanDesc.getClassInfo());
        if (des != null) {
            return StdKeyDeserializers.constructDelegatingKeyDeserializer(config, type, des);
        }
        Class<?> enumClass = type.getRawClass();
        JsonDeserializer<?> custom = _findCustomEnumDeserializer(enumClass, config, beanDesc);
        if (custom != null) {
            return StdKeyDeserializers.constructDelegatingKeyDeserializer(config, type, des);
        }
        EnumResolver<?> enumRes = constructEnumResolver(enumClass, config, beanDesc.findJsonValueMethod());
        for (AnnotatedMethod factory : beanDesc.getFactoryMethods()) {
            if (config.getAnnotationIntrospector().hasCreatorAnnotation(factory)) {
                int argCount = factory.getParameterCount();
                if (argCount == 1) {
                    Class<?> returnType = factory.getRawReturnType();
                    if (returnType.isAssignableFrom(enumClass)) {
                        if (factory.getGenericParameterType(0) != String.class) {
                            throw new IllegalArgumentException("Parameter #0 type for factory method (" + factory + ") not suitable, must be java.lang.String");
                        }
                        if (config.canOverrideAccessModifiers()) {
                            ClassUtil.checkAndFixAccess(factory.getMember());
                        }
                        return StdKeyDeserializers.constructEnumKeyDeserializer(enumRes, factory);
                    }
                }
                throw new IllegalArgumentException("Unsuitable method (" + factory + ") decorated with @JsonCreator (for Enum type " + enumClass.getName() + ")");
            }
        }
        return StdKeyDeserializers.constructEnumKeyDeserializer(enumRes);
    }

    public TypeDeserializer findPropertyTypeDeserializer(DeserializationConfig config, JavaType baseType, AnnotatedMember annotated) throws JsonMappingException {
        AnnotationIntrospector ai = config.getAnnotationIntrospector();
        TypeResolverBuilder<?> b = ai.findPropertyTypeResolver(config, annotated, baseType);
        if (b == null) {
            return findTypeDeserializer(config, baseType);
        }
        Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(annotated, config, ai);
        return b.buildTypeDeserializer(config, baseType, subtypes);
    }

    public TypeDeserializer findPropertyContentTypeDeserializer(DeserializationConfig config, JavaType containerType, AnnotatedMember propertyEntity) throws JsonMappingException {
        AnnotationIntrospector ai = config.getAnnotationIntrospector();
        TypeResolverBuilder<?> b = ai.findPropertyContentTypeResolver(config, propertyEntity, containerType);
        JavaType contentType = containerType.getContentType();
        if (b == null) {
            return findTypeDeserializer(config, contentType);
        }
        Collection<NamedType> subtypes = config.getSubtypeResolver().collectAndResolveSubtypes(propertyEntity, config, ai);
        return b.buildTypeDeserializer(config, contentType, subtypes);
    }

    protected JsonDeserializer<Object> findDeserializerFromAnnotation(DeserializationContext ctxt, Annotated ann) throws JsonMappingException {
        Object deserDef = ctxt.getAnnotationIntrospector().findDeserializer(ann);
        if (deserDef == null) {
            return null;
        }
        return ctxt.deserializerInstance(ann, deserDef);
    }

    protected <T extends JavaType> T modifyTypeByAnnotation(DeserializationContext deserializationContext, Annotated annotated, T t) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializerDeserializerInstance;
        KeyDeserializer keyDeserializerKeyDeserializerInstance;
        AnnotationIntrospector annotationIntrospector = deserializationContext.getAnnotationIntrospector();
        Class<?> clsFindDeserializationType = annotationIntrospector.findDeserializationType(annotated, t);
        if (clsFindDeserializationType != null) {
            try {
                t = (T) t.narrowBy(clsFindDeserializationType);
            } catch (IllegalArgumentException e) {
                throw new JsonMappingException("Failed to narrow type " + t + " with concrete-type annotation (value " + clsFindDeserializationType.getName() + "), method '" + annotated.getName() + "': " + e.getMessage(), null, e);
            }
        }
        if (!t.isContainerType()) {
            return t;
        }
        Class<?> clsFindDeserializationKeyType = annotationIntrospector.findDeserializationKeyType(annotated, t.getKeyType());
        if (clsFindDeserializationKeyType != null) {
            if (!(t instanceof MapLikeType)) {
                throw new JsonMappingException("Illegal key-type annotation: type " + t + " is not a Map(-like) type");
            }
            try {
                t = (T) t.narrowKey(clsFindDeserializationKeyType);
            } catch (IllegalArgumentException e2) {
                throw new JsonMappingException("Failed to narrow key type " + t + " with key-type annotation (" + clsFindDeserializationKeyType.getName() + "): " + e2.getMessage(), null, e2);
            }
        }
        JavaType keyType = t.getKeyType();
        if (keyType != null && keyType.getValueHandler() == null && (keyDeserializerKeyDeserializerInstance = deserializationContext.keyDeserializerInstance(annotated, annotationIntrospector.findKeyDeserializer(annotated))) != null) {
            t = ((MapLikeType) t).withKeyValueHandler(keyDeserializerKeyDeserializerInstance);
            t.getKeyType();
        }
        Class<?> clsFindDeserializationContentType = annotationIntrospector.findDeserializationContentType(annotated, t.getContentType());
        if (clsFindDeserializationContentType != null) {
            try {
                t = (T) t.narrowContentsBy(clsFindDeserializationContentType);
            } catch (IllegalArgumentException e3) {
                throw new JsonMappingException("Failed to narrow content type " + t + " with content-type annotation (" + clsFindDeserializationContentType.getName() + "): " + e3.getMessage(), null, e3);
            }
        }
        return (t.getContentType().getValueHandler() != null || (jsonDeserializerDeserializerInstance = deserializationContext.deserializerInstance(annotated, annotationIntrospector.findContentDeserializer(annotated))) == null) ? t : (T) t.withContentValueHandler(jsonDeserializerDeserializerInstance);
    }

    protected JavaType resolveType(DeserializationContext ctxt, BeanDescription beanDesc, JavaType type, AnnotatedMember member) throws JsonMappingException {
        TypeDeserializer valueTypeDeser;
        TypeDeserializer contentTypeDeser;
        if (type.isContainerType()) {
            AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
            JavaType keyType = type.getKeyType();
            if (keyType != null) {
                Object kdDef = intr.findKeyDeserializer(member);
                KeyDeserializer kd = ctxt.keyDeserializerInstance(member, kdDef);
                if (kd != null) {
                    type = ((MapLikeType) type).withKeyValueHandler(kd);
                    type.getKeyType();
                }
            }
            Object cdDef = intr.findContentDeserializer(member);
            JsonDeserializer<?> cd = ctxt.deserializerInstance(member, cdDef);
            if (cd != null) {
                type = type.withContentValueHandler(cd);
            }
            if ((member instanceof AnnotatedMember) && (contentTypeDeser = findPropertyContentTypeDeserializer(ctxt.getConfig(), type, member)) != null) {
                type = type.withContentTypeHandler(contentTypeDeser);
            }
        }
        if (member instanceof AnnotatedMember) {
            valueTypeDeser = findPropertyTypeDeserializer(ctxt.getConfig(), type, member);
        } else {
            valueTypeDeser = findTypeDeserializer(ctxt.getConfig(), type);
        }
        if (valueTypeDeser != null) {
            return type.withTypeHandler(valueTypeDeser);
        }
        return type;
    }

    protected EnumResolver<?> constructEnumResolver(Class<?> enumClass, DeserializationConfig config, AnnotatedMethod jsonValueMethod) {
        if (jsonValueMethod != null) {
            Method accessor = jsonValueMethod.getAnnotated();
            if (config.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess(accessor);
            }
            return EnumResolver.constructUnsafeUsingMethod(enumClass, accessor);
        }
        if (config.isEnabled(DeserializationFeature.READ_ENUMS_USING_TO_STRING)) {
            return EnumResolver.constructUnsafeUsingToString(enumClass);
        }
        return EnumResolver.constructUnsafe(enumClass, config.getAnnotationIntrospector());
    }

    protected AnnotatedMethod _findJsonValueFor(DeserializationConfig config, JavaType enumType) {
        if (enumType == null) {
            return null;
        }
        BeanDescription beanDesc = config.introspect(enumType);
        return beanDesc.findJsonValueMethod();
    }
}
