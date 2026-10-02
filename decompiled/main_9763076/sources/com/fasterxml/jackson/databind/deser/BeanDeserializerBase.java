package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
import com.fasterxml.jackson.databind.deser.impl.ManagedReferenceProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.deser.impl.UnwrappedPropertyHandler;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BeanDeserializerBase extends StdDeserializer<Object> implements ContextualDeserializer, ResolvableDeserializer {
    protected SettableAnyProperty _anySetter;
    protected final Map<String, SettableBeanProperty> _backRefs;
    protected final BeanPropertyMap _beanProperties;
    protected final JavaType _beanType;
    protected final Annotations _classAnnotations;
    protected JsonDeserializer<Object> _delegateDeserializer;
    protected ExternalTypeHandler _externalTypeIdHandler;
    protected final HashSet<String> _ignorableProps;
    protected final boolean _ignoreAllUnknown;
    protected final ValueInjector[] _injectables;
    protected final boolean _needViewProcesing;
    protected boolean _nonStandardCreation;
    protected final ObjectIdReader _objectIdReader;
    protected PropertyBasedCreator _propertyBasedCreator;
    protected HashMap<ClassKey, JsonDeserializer<Object>> _subDeserializers;
    protected UnwrappedPropertyHandler _unwrappedPropertyHandler;
    protected final ValueInstantiator _valueInstantiator;
    protected boolean _vanillaProcessing;

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public abstract JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer);

    public abstract BeanDeserializerBase withIgnorableProperties(HashSet<String> hashSet);

    public abstract BeanDeserializerBase withObjectIdReader(ObjectIdReader objectIdReader);

    protected BeanDeserializerBase(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, HashSet<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
        super(beanDesc.getType());
        AnnotatedClass ac = beanDesc.getClassInfo();
        this._classAnnotations = ac.getAnnotations();
        this._beanType = beanDesc.getType();
        this._valueInstantiator = builder.getValueInstantiator();
        this._beanProperties = properties;
        this._backRefs = backRefs;
        this._ignorableProps = ignorableProps;
        this._ignoreAllUnknown = ignoreAllUnknown;
        this._anySetter = builder.getAnySetter();
        List<ValueInjector> injectables = builder.getInjectables();
        this._injectables = (injectables == null || injectables.isEmpty()) ? null : (ValueInjector[]) injectables.toArray(new ValueInjector[injectables.size()]);
        this._objectIdReader = builder.getObjectIdReader();
        this._nonStandardCreation = this._unwrappedPropertyHandler != null || this._valueInstantiator.canCreateUsingDelegate() || this._valueInstantiator.canCreateFromObjectWith() || !this._valueInstantiator.canCreateUsingDefault();
        this._needViewProcesing = hasViews;
        this._vanillaProcessing = (this._nonStandardCreation || this._injectables != null || this._needViewProcesing || this._objectIdReader == null) ? false : true;
    }

    protected BeanDeserializerBase(BeanDeserializerBase src) {
        this(src, src._ignoreAllUnknown);
    }

    protected BeanDeserializerBase(BeanDeserializerBase src, boolean ignoreAllUnknown) {
        super(src._beanType);
        this._classAnnotations = src._classAnnotations;
        this._beanType = src._beanType;
        this._valueInstantiator = src._valueInstantiator;
        this._delegateDeserializer = src._delegateDeserializer;
        this._propertyBasedCreator = src._propertyBasedCreator;
        this._beanProperties = src._beanProperties;
        this._backRefs = src._backRefs;
        this._ignorableProps = src._ignorableProps;
        this._ignoreAllUnknown = ignoreAllUnknown;
        this._anySetter = src._anySetter;
        this._injectables = src._injectables;
        this._objectIdReader = src._objectIdReader;
        this._nonStandardCreation = src._nonStandardCreation;
        this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
        this._needViewProcesing = src._needViewProcesing;
        this._vanillaProcessing = src._vanillaProcessing;
    }

    protected BeanDeserializerBase(BeanDeserializerBase src, NameTransformer unwrapper) {
        super(src._beanType);
        this._classAnnotations = src._classAnnotations;
        this._beanType = src._beanType;
        this._valueInstantiator = src._valueInstantiator;
        this._delegateDeserializer = src._delegateDeserializer;
        this._propertyBasedCreator = src._propertyBasedCreator;
        this._backRefs = src._backRefs;
        this._ignorableProps = src._ignorableProps;
        this._ignoreAllUnknown = unwrapper != null || src._ignoreAllUnknown;
        this._anySetter = src._anySetter;
        this._injectables = src._injectables;
        this._objectIdReader = src._objectIdReader;
        this._nonStandardCreation = src._nonStandardCreation;
        this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
        if (unwrapper != null) {
            if (this._unwrappedPropertyHandler != null) {
                this._unwrappedPropertyHandler.renameAll(unwrapper);
            }
            this._beanProperties = src._beanProperties.renameAll(unwrapper);
        } else {
            this._beanProperties = src._beanProperties;
        }
        this._needViewProcesing = src._needViewProcesing;
        this._vanillaProcessing = false;
    }

    public BeanDeserializerBase(BeanDeserializerBase src, ObjectIdReader oir) {
        super(src._beanType);
        this._classAnnotations = src._classAnnotations;
        this._beanType = src._beanType;
        this._valueInstantiator = src._valueInstantiator;
        this._delegateDeserializer = src._delegateDeserializer;
        this._propertyBasedCreator = src._propertyBasedCreator;
        this._backRefs = src._backRefs;
        this._ignorableProps = src._ignorableProps;
        this._ignoreAllUnknown = src._ignoreAllUnknown;
        this._anySetter = src._anySetter;
        this._injectables = src._injectables;
        this._nonStandardCreation = src._nonStandardCreation;
        this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
        this._needViewProcesing = src._needViewProcesing;
        this._vanillaProcessing = src._vanillaProcessing;
        this._objectIdReader = oir;
        if (oir == null) {
            this._beanProperties = src._beanProperties;
        } else {
            this._beanProperties = src._beanProperties.withProperty(new ObjectIdValueProperty(oir));
        }
    }

    public BeanDeserializerBase(BeanDeserializerBase src, HashSet<String> ignorableProps) {
        super(src._beanType);
        this._classAnnotations = src._classAnnotations;
        this._beanType = src._beanType;
        this._valueInstantiator = src._valueInstantiator;
        this._delegateDeserializer = src._delegateDeserializer;
        this._propertyBasedCreator = src._propertyBasedCreator;
        this._backRefs = src._backRefs;
        this._ignorableProps = ignorableProps;
        this._ignoreAllUnknown = src._ignoreAllUnknown;
        this._anySetter = src._anySetter;
        this._injectables = src._injectables;
        this._nonStandardCreation = src._nonStandardCreation;
        this._unwrappedPropertyHandler = src._unwrappedPropertyHandler;
        this._needViewProcesing = src._needViewProcesing;
        this._vanillaProcessing = src._vanillaProcessing;
        this._objectIdReader = src._objectIdReader;
        this._beanProperties = src._beanProperties;
    }

    @Override // com.fasterxml.jackson.databind.deser.ResolvableDeserializer
    public void resolve(DeserializationContext ctxt) throws JsonMappingException {
        JsonDeserializer<?> cd;
        ExternalTypeHandler.Builder extTypes = null;
        if (this._valueInstantiator.canCreateFromObjectWith()) {
            SettableBeanProperty[] creatorProps = this._valueInstantiator.getFromObjectArguments(ctxt.getConfig());
            this._propertyBasedCreator = PropertyBasedCreator.construct(ctxt, this._valueInstantiator, creatorProps);
            for (SettableBeanProperty prop : this._propertyBasedCreator.properties()) {
                if (prop.hasValueTypeDeserializer()) {
                    TypeDeserializer typeDeser = prop.getValueTypeDeserializer();
                    if (typeDeser.getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
                        if (extTypes == null) {
                            extTypes = new ExternalTypeHandler.Builder();
                        }
                        extTypes.addExternal(prop, typeDeser.getPropertyName());
                    }
                }
            }
        }
        UnwrappedPropertyHandler unwrapped = null;
        for (SettableBeanProperty origProp : this._beanProperties) {
            SettableBeanProperty prop2 = origProp;
            if (!prop2.hasValueDeserializer()) {
                prop2 = prop2.withValueDeserializer(findDeserializer(ctxt, prop2.getType(), prop2));
            } else {
                Object valueDeserializer = prop2.getValueDeserializer();
                if ((valueDeserializer instanceof ContextualDeserializer) && (cd = ((ContextualDeserializer) valueDeserializer).createContextual(ctxt, prop2)) != valueDeserializer) {
                    prop2 = prop2.withValueDeserializer(cd);
                }
            }
            SettableBeanProperty prop3 = _resolveManagedReferenceProperty(ctxt, prop2);
            SettableBeanProperty u = _resolveUnwrappedProperty(ctxt, prop3);
            if (u != null) {
                if (unwrapped == null) {
                    unwrapped = new UnwrappedPropertyHandler();
                }
                unwrapped.addProperty(u);
            } else {
                SettableBeanProperty prop4 = _resolveInnerClassValuedProperty(ctxt, prop3);
                if (prop4 != origProp) {
                    this._beanProperties.replace(prop4);
                }
                if (prop4.hasValueTypeDeserializer()) {
                    TypeDeserializer typeDeser2 = prop4.getValueTypeDeserializer();
                    if (typeDeser2.getTypeInclusion() == JsonTypeInfo.As.EXTERNAL_PROPERTY) {
                        if (extTypes == null) {
                            extTypes = new ExternalTypeHandler.Builder();
                        }
                        extTypes.addExternal(prop4, typeDeser2.getPropertyName());
                        this._beanProperties.remove(prop4);
                    }
                }
            }
        }
        if (this._anySetter != null && !this._anySetter.hasValueDeserializer()) {
            this._anySetter = this._anySetter.withValueDeserializer(findDeserializer(ctxt, this._anySetter.getType(), this._anySetter.getProperty()));
        }
        if (this._valueInstantiator.canCreateUsingDelegate()) {
            JavaType delegateType = this._valueInstantiator.getDelegateType(ctxt.getConfig());
            if (delegateType == null) {
                throw new IllegalArgumentException("Invalid delegate-creator definition for " + this._beanType + ": value instantiator (" + this._valueInstantiator.getClass().getName() + ") returned true for 'canCreateUsingDelegate()', but null for 'getDelegateType()'");
            }
            AnnotatedWithParams delegateCreator = this._valueInstantiator.getDelegateCreator();
            BeanProperty property = new BeanProperty.Std(null, delegateType, this._classAnnotations, delegateCreator);
            this._delegateDeserializer = findDeserializer(ctxt, delegateType, property);
        }
        if (extTypes != null) {
            this._externalTypeIdHandler = extTypes.build();
            this._nonStandardCreation = true;
        }
        this._unwrappedPropertyHandler = unwrapped;
        if (unwrapped != null) {
            this._nonStandardCreation = true;
        }
        this._vanillaProcessing = this._vanillaProcessing && !this._nonStandardCreation;
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        JavaType idType;
        SettableBeanProperty idProp;
        ObjectIdGenerator<?> idGen;
        ObjectIdReader oir = this._objectIdReader;
        String[] ignorals = null;
        AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
        if (property != null && intr != null) {
            AnnotatedMember accessor = property.getMember();
            ignorals = intr.findPropertiesToIgnore(accessor);
            ObjectIdInfo objectIdInfo = intr.findObjectIdInfo(accessor);
            if (objectIdInfo != null) {
                Class<?> implClass = objectIdInfo.getGeneratorType();
                if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
                    String propName = objectIdInfo.getPropertyName();
                    idProp = findProperty(propName);
                    if (idProp == null) {
                        throw new IllegalArgumentException("Invalid Object Id definition for " + getBeanClass().getName() + ": can not find property with name '" + propName + "'");
                    }
                    idType = idProp.getType();
                    idGen = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
                } else {
                    JavaType type = ctxt.constructType(implClass);
                    idType = ctxt.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
                    idProp = null;
                    idGen = ctxt.objectIdGeneratorInstance(accessor, objectIdInfo);
                }
                JsonDeserializer<?> deser = ctxt.findRootValueDeserializer(idType);
                oir = ObjectIdReader.construct(idType, objectIdInfo.getPropertyName(), idGen, deser, idProp);
            }
        }
        BeanDeserializerBase contextual = this;
        if (oir != null && oir != this._objectIdReader) {
            contextual = contextual.withObjectIdReader(oir);
        }
        if (ignorals != null && ignorals.length != 0) {
            HashSet<String> newIgnored = ArrayBuilders.setAndArray(contextual._ignorableProps, ignorals);
            return contextual.withIgnorableProperties(newIgnored);
        }
        return contextual;
    }

    protected SettableBeanProperty _resolveManagedReferenceProperty(DeserializationContext ctxt, SettableBeanProperty prop) {
        SettableBeanProperty backProp;
        String refName = prop.getManagedReferenceName();
        if (refName != null) {
            JsonDeserializer<?> valueDeser = prop.getValueDeserializer();
            boolean isContainer = false;
            if (valueDeser instanceof BeanDeserializerBase) {
                backProp = ((BeanDeserializerBase) valueDeser).findBackReference(refName);
            } else if (valueDeser instanceof ContainerDeserializerBase) {
                JsonDeserializer<?> contentDeser = ((ContainerDeserializerBase) valueDeser).getContentDeserializer();
                if (!(contentDeser instanceof BeanDeserializerBase)) {
                    String deserName = contentDeser == null ? "NULL" : contentDeser.getClass().getName();
                    throw new IllegalArgumentException("Can not handle managed/back reference '" + refName + "': value deserializer is of type ContainerDeserializerBase, but content type is not handled by a BeanDeserializer  (instead it's of type " + deserName + ")");
                }
                backProp = ((BeanDeserializerBase) contentDeser).findBackReference(refName);
                isContainer = true;
            } else if (valueDeser instanceof AbstractDeserializer) {
                backProp = ((AbstractDeserializer) valueDeser).findBackReference(refName);
            } else {
                throw new IllegalArgumentException("Can not handle managed/back reference '" + refName + "': type for value deserializer is not BeanDeserializer or ContainerDeserializerBase, but " + valueDeser.getClass().getName());
            }
            if (backProp == null) {
                throw new IllegalArgumentException("Can not handle managed/back reference '" + refName + "': no back reference property found from type " + prop.getType());
            }
            JavaType referredType = this._beanType;
            JavaType backRefType = backProp.getType();
            if (!backRefType.getRawClass().isAssignableFrom(referredType.getRawClass())) {
                throw new IllegalArgumentException("Can not handle managed/back reference '" + refName + "': back reference type (" + backRefType.getRawClass().getName() + ") not compatible with managed type (" + referredType.getRawClass().getName() + ")");
            }
            return new ManagedReferenceProperty(prop, refName, backProp, this._classAnnotations, isContainer);
        }
        return prop;
    }

    protected SettableBeanProperty _resolveUnwrappedProperty(DeserializationContext ctxt, SettableBeanProperty prop) {
        NameTransformer unwrapper;
        JsonDeserializer<Object> orig;
        JsonDeserializer<Object> unwrapping;
        AnnotatedMember am = prop.getMember();
        if (am == null || (unwrapper = ctxt.getAnnotationIntrospector().findUnwrappingNameTransformer(am)) == null || (unwrapping = (orig = prop.getValueDeserializer()).unwrappingDeserializer(unwrapper)) == orig || unwrapping == null) {
            return null;
        }
        return prop.withValueDeserializer(unwrapping);
    }

    protected SettableBeanProperty _resolveInnerClassValuedProperty(DeserializationContext ctxt, SettableBeanProperty prop) {
        Class<?> valueClass;
        Class<?> enclosing;
        JsonDeserializer<Object> deser = prop.getValueDeserializer();
        if (deser instanceof BeanDeserializerBase) {
            BeanDeserializerBase bd = (BeanDeserializerBase) deser;
            ValueInstantiator vi = bd.getValueInstantiator();
            if (!vi.canCreateUsingDefault() && (enclosing = ClassUtil.getOuterClass((valueClass = prop.getType().getRawClass()))) != null && enclosing == this._beanType.getRawClass()) {
                for (Constructor<?> ctor : valueClass.getConstructors()) {
                    Class<?>[] paramTypes = ctor.getParameterTypes();
                    if (paramTypes.length == 1 && paramTypes[0] == enclosing) {
                        if (ctxt.getConfig().canOverrideAccessModifiers()) {
                            ClassUtil.checkAndFixAccess(ctor);
                        }
                        return new InnerClassProperty(prop, ctor);
                    }
                }
                return prop;
            }
            return prop;
        }
        return prop;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public boolean hasProperty(String propertyName) {
        return this._beanProperties.find(propertyName) != null;
    }

    public boolean hasViews() {
        return this._needViewProcesing;
    }

    public int getPropertyCount() {
        return this._beanProperties.size();
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Collection<Object> getKnownPropertyNames() {
        ArrayList<Object> names = new ArrayList<>();
        for (SettableBeanProperty prop : this._beanProperties) {
            names.add(prop.getName());
        }
        return names;
    }

    public final Class<?> getBeanClass() {
        return this._beanType.getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer
    public JavaType getValueType() {
        return this._beanType;
    }

    public Iterator<SettableBeanProperty> properties() {
        if (this._beanProperties == null) {
            throw new IllegalStateException("Can only call after BeanDeserializer has been resolved");
        }
        return this._beanProperties.iterator();
    }

    public Iterator<SettableBeanProperty> creatorProperties() {
        return this._propertyBasedCreator == null ? Collections.emptyList().iterator() : this._propertyBasedCreator.properties().iterator();
    }

    public SettableBeanProperty findProperty(String propertyName) {
        SettableBeanProperty prop = this._beanProperties == null ? null : this._beanProperties.find(propertyName);
        if (prop == null && this._propertyBasedCreator != null) {
            return this._propertyBasedCreator.findCreatorProperty(propertyName);
        }
        return prop;
    }

    public SettableBeanProperty findBackReference(String logicalName) {
        if (this._backRefs == null) {
            return null;
        }
        return this._backRefs.get(logicalName);
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public final Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        JsonToken t;
        return (this._objectIdReader == null || (t = jp.getCurrentToken()) == null || !t.isScalarValue()) ? typeDeserializer.deserializeTypedFromObject(jp, ctxt) : deserializeFromObjectId(jp, ctxt);
    }

    protected Object deserializeFromObjectId(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Object id = this._objectIdReader.deserializer.deserialize(jp, ctxt);
        ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator);
        Object pojo = roid.item;
        if (pojo == null) {
            throw new IllegalStateException("Could not resolve Object Id [" + id + "] -- unresolved forward-reference?");
        }
        return pojo;
    }

    protected void injectValues(DeserializationContext ctxt, Object bean) throws IOException {
        ValueInjector[] arr$ = this._injectables;
        for (ValueInjector injector : arr$) {
            injector.inject(ctxt, bean);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer
    protected void handleUnknownProperty(JsonParser jp, DeserializationContext ctxt, Object beanOrClass, String propName) throws IOException {
        if (this._ignoreAllUnknown || (this._ignorableProps != null && this._ignorableProps.contains(propName))) {
            jp.skipChildren();
        } else {
            super.handleUnknownProperty(jp, ctxt, beanOrClass, propName);
        }
    }

    protected Object handleUnknownProperties(DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws IOException {
        unknownTokens.writeEndObject();
        JsonParser bufferParser = unknownTokens.asParser();
        while (bufferParser.nextToken() != JsonToken.END_OBJECT) {
            String propName = bufferParser.getCurrentName();
            bufferParser.nextToken();
            handleUnknownProperty(bufferParser, ctxt, bean, propName);
        }
        return bean;
    }

    protected JsonDeserializer<Object> _findSubclassDeserializer(DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws IOException {
        JsonDeserializer<Object> subDeser;
        synchronized (this) {
            subDeser = this._subDeserializers == null ? null : this._subDeserializers.get(new ClassKey(bean.getClass()));
        }
        if (subDeser != null) {
            return subDeser;
        }
        JavaType type = ctxt.constructType(bean.getClass());
        JsonDeserializer<Object> subDeser2 = ctxt.findRootValueDeserializer(type);
        if (subDeser2 != null) {
            synchronized (this) {
                if (this._subDeserializers == null) {
                    this._subDeserializers = new HashMap<>();
                }
                this._subDeserializers.put(new ClassKey(bean.getClass()), subDeser2);
            }
        }
        return subDeser2;
    }

    public void wrapAndThrow(Throwable t, Object bean, String fieldName, DeserializationContext ctxt) throws Throwable {
        while ((t instanceof InvocationTargetException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw ((Error) t);
        }
        boolean wrap = ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            if (!wrap || !(t instanceof JsonMappingException)) {
                throw ((IOException) t);
            }
        } else if (!wrap && (t instanceof RuntimeException)) {
            throw ((RuntimeException) t);
        }
        throw JsonMappingException.wrapWithPath(t, bean, fieldName);
    }

    public void wrapAndThrow(Throwable t, Object bean, int index, DeserializationContext ctxt) throws Throwable {
        while ((t instanceof InvocationTargetException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw ((Error) t);
        }
        boolean wrap = ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            if (!wrap || !(t instanceof JsonMappingException)) {
                throw ((IOException) t);
            }
        } else if (!wrap && (t instanceof RuntimeException)) {
            throw ((RuntimeException) t);
        }
        throw JsonMappingException.wrapWithPath(t, bean, index);
    }

    protected void wrapInstantiationProblem(Throwable t, DeserializationContext ctxt) throws Throwable {
        while ((t instanceof InvocationTargetException) && t.getCause() != null) {
            t = t.getCause();
        }
        if (t instanceof Error) {
            throw ((Error) t);
        }
        boolean wrap = ctxt == null || ctxt.isEnabled(DeserializationFeature.WRAP_EXCEPTIONS);
        if (t instanceof IOException) {
            throw ((IOException) t);
        }
        if (!wrap && (t instanceof RuntimeException)) {
            throw ((RuntimeException) t);
        }
        throw ctxt.instantiationException(this._beanType.getRawClass(), t);
    }
}
