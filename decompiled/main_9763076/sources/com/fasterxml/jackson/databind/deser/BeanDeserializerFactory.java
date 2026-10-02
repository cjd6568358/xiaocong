package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.impl.FieldProperty;
import com.fasterxml.jackson.databind.deser.impl.MethodProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedObjectIdGenerator;
import com.fasterxml.jackson.databind.deser.impl.SetterlessProperty;
import com.fasterxml.jackson.databind.deser.std.JdkDeserializers;
import com.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.SimpleBeanPropertyDefinition;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanDeserializerFactory extends BasicDeserializerFactory {
    private static final Class<?>[] INIT_CAUSE_PARAMS = {Throwable.class};
    private static final Class<?>[] NO_VIEWS = new Class[0];
    public static final BeanDeserializerFactory instance = new BeanDeserializerFactory(new DeserializerFactoryConfig());

    public BeanDeserializerFactory(DeserializerFactoryConfig config) {
        super(config);
    }

    @Override // com.fasterxml.jackson.databind.deser.BasicDeserializerFactory
    public DeserializerFactory withConfig(DeserializerFactoryConfig config) {
        if (this._factoryConfig != config) {
            if (getClass() != BeanDeserializerFactory.class) {
                throw new IllegalStateException("Subtype of BeanDeserializerFactory (" + getClass().getName() + ") has not properly overridden method 'withAdditionalDeserializers': can not instantiate subtype with additional deserializer definitions");
            }
            return new BeanDeserializerFactory(config);
        }
        return this;
    }

    protected JsonDeserializer<Object> _findCustomBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        for (Deserializers d : this._factoryConfig.deserializers()) {
            JsonDeserializer<?> deser = d.findBeanDeserializer(type, config, beanDesc);
            if (deser != null) {
                return deser;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<Object> createBeanDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
        JavaType concreteType;
        DeserializationConfig config = ctxt.getConfig();
        JsonDeserializer<Object> custom = _findCustomBeanDeserializer(type, config, beanDesc);
        if (custom == null) {
            if (type.isThrowable()) {
                return buildThrowableDeserializer(ctxt, type, beanDesc);
            }
            if (type.isAbstract() && (concreteType = materializeAbstractType(config, beanDesc)) != null) {
                return buildBeanDeserializer(ctxt, concreteType, config.introspect(concreteType));
            }
            JsonDeserializer<Object> deser = findStdDeserializer(config, type);
            if (deser != null) {
                return deser;
            }
            if (!isPotentialBeanType(type.getRawClass())) {
                return null;
            }
            return buildBeanDeserializer(ctxt, type, beanDesc);
        }
        return custom;
    }

    @Override // com.fasterxml.jackson.databind.deser.DeserializerFactory
    public JsonDeserializer<Object> createBuilderBasedDeserializer(DeserializationContext ctxt, JavaType valueType, BeanDescription beanDesc, Class<?> builderClass) throws JsonMappingException {
        JavaType builderType = ctxt.constructType(builderClass);
        BeanDescription builderDesc = ctxt.getConfig().introspectForBuilder(builderType);
        return buildBuilderBasedDeserializer(ctxt, valueType, builderDesc);
    }

    protected JsonDeserializer<Object> findStdDeserializer(DeserializationConfig config, JavaType type) throws JsonMappingException {
        JavaType referencedType;
        Class<?> cls = type.getRawClass();
        JsonDeserializer<Object> deser = _simpleDeserializers.get(new ClassKey(cls));
        if (deser == null) {
            if (AtomicReference.class.isAssignableFrom(cls)) {
                TypeFactory tf = config.getTypeFactory();
                JavaType[] params = tf.findTypeParameters(type, AtomicReference.class);
                if (params == null || params.length < 1) {
                    referencedType = TypeFactory.unknownType();
                } else {
                    referencedType = params[0];
                }
                JsonDeserializer<?> d2 = new JdkDeserializers.AtomicReferenceDeserializer(referencedType);
                return d2;
            }
            JsonDeserializer<?> d = this.optionalHandlers.findDeserializer(type, config);
            if (d != null) {
                return d;
            }
            return null;
        }
        return deser;
    }

    protected JavaType materializeAbstractType(DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        JavaType abstractType = beanDesc.getType();
        for (AbstractTypeResolver r : this._factoryConfig.abstractTypeResolvers()) {
            JavaType concrete = r.resolveAbstractType(config, abstractType);
            if (concrete != null) {
                return concrete;
            }
        }
        return null;
    }

    public JsonDeserializer<Object> buildBeanDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
        JsonDeserializer<?> deserializer;
        ValueInstantiator valueInstantiator = findValueInstantiator(ctxt, beanDesc);
        BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(ctxt, beanDesc);
        builder.setValueInstantiator(valueInstantiator);
        addBeanProps(ctxt, beanDesc, builder);
        addObjectIdReader(ctxt, beanDesc, builder);
        addReferenceProperties(ctxt, beanDesc, builder);
        addInjectables(ctxt, beanDesc, builder);
        DeserializationConfig config = ctxt.getConfig();
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
                builder = mod.updateBuilder(config, beanDesc, builder);
            }
        }
        if (type.isAbstract() && !valueInstantiator.canInstantiate()) {
            deserializer = builder.buildAbstract();
        } else {
            deserializer = builder.build();
        }
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod2 : this._factoryConfig.deserializerModifiers()) {
                deserializer = mod2.modifyDeserializer(config, beanDesc, deserializer);
            }
        }
        return deserializer;
    }

    protected JsonDeserializer<Object> buildBuilderBasedDeserializer(DeserializationContext ctxt, JavaType valueType, BeanDescription builderDesc) throws JsonMappingException {
        ValueInstantiator valueInstantiator = findValueInstantiator(ctxt, builderDesc);
        DeserializationConfig config = ctxt.getConfig();
        BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(ctxt, builderDesc);
        builder.setValueInstantiator(valueInstantiator);
        addBeanProps(ctxt, builderDesc, builder);
        addObjectIdReader(ctxt, builderDesc, builder);
        addReferenceProperties(ctxt, builderDesc, builder);
        addInjectables(ctxt, builderDesc, builder);
        JsonPOJOBuilder.Value builderConfig = builderDesc.findPOJOBuilderConfig();
        String buildMethodName = builderConfig == null ? "build" : builderConfig.buildMethodName;
        AnnotatedMethod buildMethod = builderDesc.findMethod(buildMethodName, null);
        if (buildMethod != null && config.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(buildMethod.getMember());
        }
        builder.setPOJOBuilder(buildMethod, builderConfig);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
                builder = mod.updateBuilder(config, builderDesc, builder);
            }
        }
        JsonDeserializer<?> deserializer = builder.buildBuilderBased(valueType, buildMethodName);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod2 : this._factoryConfig.deserializerModifiers()) {
                deserializer = mod2.modifyDeserializer(config, builderDesc, deserializer);
            }
        }
        return deserializer;
    }

    protected void addObjectIdReader(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
        JavaType idType;
        SettableBeanProperty idProp;
        ObjectIdGenerator<?> gen;
        ObjectIdInfo objectIdInfo = beanDesc.getObjectIdInfo();
        if (objectIdInfo != null) {
            Class<?> implClass = objectIdInfo.getGeneratorType();
            if (implClass == ObjectIdGenerators.PropertyGenerator.class) {
                String propName = objectIdInfo.getPropertyName();
                idProp = builder.findProperty(propName);
                if (idProp == null) {
                    throw new IllegalArgumentException("Invalid Object Id definition for " + beanDesc.getBeanClass().getName() + ": can not find property with name '" + propName + "'");
                }
                idType = idProp.getType();
                gen = new PropertyBasedObjectIdGenerator(objectIdInfo.getScope());
            } else {
                JavaType type = ctxt.constructType(implClass);
                idType = ctxt.getTypeFactory().findTypeParameters(type, ObjectIdGenerator.class)[0];
                idProp = null;
                gen = ctxt.objectIdGeneratorInstance(beanDesc.getClassInfo(), objectIdInfo);
            }
            JsonDeserializer<?> deser = ctxt.findRootValueDeserializer(idType);
            builder.setObjectIdReader(ObjectIdReader.construct(idType, objectIdInfo.getPropertyName(), gen, deser, idProp));
        }
    }

    public JsonDeserializer<Object> buildThrowableDeserializer(DeserializationContext ctxt, JavaType type, BeanDescription beanDesc) throws JsonMappingException {
        DeserializationConfig config = ctxt.getConfig();
        BeanDeserializerBuilder builder = constructBeanDeserializerBuilder(ctxt, beanDesc);
        builder.setValueInstantiator(findValueInstantiator(ctxt, beanDesc));
        addBeanProps(ctxt, beanDesc, builder);
        AnnotatedMethod am = beanDesc.findMethod("initCause", INIT_CAUSE_PARAMS);
        if (am != null) {
            SimpleBeanPropertyDefinition propDef = new SimpleBeanPropertyDefinition(am, "cause");
            SettableBeanProperty prop = constructSettableProperty(ctxt, beanDesc, propDef, am.getGenericParameterType(0));
            if (prop != null) {
                builder.addOrReplaceProperty(prop, true);
            }
        }
        builder.addIgnorable("localizedMessage");
        builder.addIgnorable("suppressed");
        builder.addIgnorable("message");
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
                builder = mod.updateBuilder(config, beanDesc, builder);
            }
        }
        JsonDeserializer<?> deserializer = builder.build();
        if (deserializer instanceof BeanDeserializer) {
            deserializer = new ThrowableDeserializer((BeanDeserializer) deserializer);
        }
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod2 : this._factoryConfig.deserializerModifiers()) {
                deserializer = mod2.modifyDeserializer(config, beanDesc, deserializer);
            }
        }
        return deserializer;
    }

    protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext ctxt, BeanDescription beanDesc) {
        return new BeanDeserializerBuilder(beanDesc, ctxt.getConfig());
    }

    protected void addBeanProps(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
        Collection<String> ignored2;
        AnnotationIntrospector intr = ctxt.getAnnotationIntrospector();
        Boolean B = intr.findIgnoreUnknownProperties(beanDesc.getClassInfo());
        if (B != null) {
            boolean ignoreAny = B.booleanValue();
            builder.setIgnoreUnknownProperties(ignoreAny);
        }
        Set<String> ignored = ArrayBuilders.arrayToSet(intr.findPropertiesToIgnore(beanDesc.getClassInfo()));
        for (String propName : ignored) {
            builder.addIgnorable(propName);
        }
        AnnotatedMethod anySetter = beanDesc.findAnySetter();
        if (anySetter != null) {
            builder.setAnySetter(constructAnySetter(ctxt, beanDesc, anySetter));
        }
        if (anySetter == null && (ignored2 = beanDesc.getIgnoredPropertyNames()) != null) {
            for (String propName2 : ignored2) {
                builder.addIgnorable(propName2);
            }
        }
        boolean useGettersAsSetters = ctxt.isEnabled(MapperFeature.USE_GETTERS_AS_SETTERS) && ctxt.isEnabled(MapperFeature.AUTO_DETECT_GETTERS);
        List<BeanPropertyDefinition> propDefs = filterBeanProps(ctxt, beanDesc, builder, beanDesc.findProperties(), ignored);
        if (this._factoryConfig.hasDeserializerModifiers()) {
            for (BeanDeserializerModifier mod : this._factoryConfig.deserializerModifiers()) {
                propDefs = mod.updateProperties(ctxt.getConfig(), beanDesc, propDefs);
            }
        }
        for (BeanPropertyDefinition propDef : propDefs) {
            if (propDef.hasConstructorParameter()) {
                builder.addCreatorProperty(propDef);
            } else {
                SettableBeanProperty prop = null;
                if (propDef.hasSetter()) {
                    Type propertyType = propDef.getSetter().getGenericParameterType(0);
                    prop = constructSettableProperty(ctxt, beanDesc, propDef, propertyType);
                } else if (propDef.hasField()) {
                    Type propertyType2 = propDef.getField().getGenericType();
                    prop = constructSettableProperty(ctxt, beanDesc, propDef, propertyType2);
                } else if (useGettersAsSetters && propDef.hasGetter()) {
                    AnnotatedMethod getter = propDef.getGetter();
                    Class<?> rawPropertyType = getter.getRawType();
                    if (Collection.class.isAssignableFrom(rawPropertyType) || Map.class.isAssignableFrom(rawPropertyType)) {
                        prop = constructSetterlessProperty(ctxt, beanDesc, propDef);
                    }
                }
                if (prop != null) {
                    Class<?>[] views = propDef.findViews();
                    if (views == null && !ctxt.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)) {
                        views = NO_VIEWS;
                    }
                    prop.setViews(views);
                    builder.addProperty(prop);
                }
            }
        }
    }

    protected List<BeanPropertyDefinition> filterBeanProps(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder, List<BeanPropertyDefinition> propDefsIn, Set<String> ignored) throws JsonMappingException {
        ArrayList<BeanPropertyDefinition> result = new ArrayList<>(Math.max(4, propDefsIn.size()));
        HashMap<Class<?>, Boolean> ignoredTypes = new HashMap<>();
        for (BeanPropertyDefinition property : propDefsIn) {
            String name = property.getName();
            if (!ignored.contains(name)) {
                if (!property.hasConstructorParameter()) {
                    Class<?> rawPropertyType = null;
                    if (property.hasSetter()) {
                        rawPropertyType = property.getSetter().getRawParameterType(0);
                    } else if (property.hasField()) {
                        rawPropertyType = property.getField().getRawType();
                    }
                    if (rawPropertyType != null && isIgnorableType(ctxt.getConfig(), beanDesc, rawPropertyType, ignoredTypes)) {
                        builder.addIgnorable(name);
                    }
                }
                result.add(property);
            }
        }
        return result;
    }

    protected void addReferenceProperties(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
        Type genericType;
        Map<String, AnnotatedMember> refs = beanDesc.findBackReferenceProperties();
        if (refs != null) {
            for (Map.Entry<String, AnnotatedMember> en : refs.entrySet()) {
                String name = en.getKey();
                AnnotatedMember m = en.getValue();
                if (m instanceof AnnotatedMethod) {
                    genericType = ((AnnotatedMethod) m).getGenericParameterType(0);
                } else {
                    genericType = m.getRawType();
                }
                SimpleBeanPropertyDefinition propDef = new SimpleBeanPropertyDefinition(m);
                builder.addBackReferenceProperty(name, constructSettableProperty(ctxt, beanDesc, propDef, genericType));
            }
        }
    }

    protected void addInjectables(DeserializationContext ctxt, BeanDescription beanDesc, BeanDeserializerBuilder builder) throws JsonMappingException {
        Map<Object, AnnotatedMember> raw = beanDesc.findInjectables();
        if (raw != null) {
            boolean fixAccess = ctxt.canOverrideAccessModifiers();
            for (Map.Entry<Object, AnnotatedMember> entry : raw.entrySet()) {
                AnnotatedMember m = entry.getValue();
                if (fixAccess) {
                    m.fixAccess();
                }
                builder.addInjectable(m.getName(), beanDesc.resolveType(m.getGenericType()), beanDesc.getClassAnnotations(), m, entry.getKey());
            }
        }
    }

    protected SettableAnyProperty constructAnySetter(DeserializationContext ctxt, BeanDescription beanDesc, AnnotatedMethod setter) throws JsonMappingException {
        if (ctxt.canOverrideAccessModifiers()) {
            setter.fixAccess();
        }
        JavaType type = beanDesc.bindingsForBeanType().resolveType(setter.getGenericParameterType(1));
        BeanProperty.Std property = new BeanProperty.Std(setter.getName(), type, beanDesc.getClassAnnotations(), setter);
        JavaType type2 = resolveType(ctxt, beanDesc, type, setter);
        JsonDeserializer<Object> deser = findDeserializerFromAnnotation(ctxt, setter);
        if (deser != null) {
            return new SettableAnyProperty(property, setter, type2, deser);
        }
        return new SettableAnyProperty(property, setter, modifyTypeByAnnotation(ctxt, setter, type2), (JsonDeserializer<Object>) null);
    }

    protected SettableBeanProperty constructSettableProperty(DeserializationContext ctxt, BeanDescription beanDesc, BeanPropertyDefinition propDef, Type jdkType) throws JsonMappingException {
        SettableBeanProperty prop;
        AnnotatedMember mutator = propDef.getMutator();
        if (ctxt.canOverrideAccessModifiers()) {
            mutator.fixAccess();
        }
        JavaType t0 = beanDesc.resolveType(jdkType);
        BeanProperty.Std property = new BeanProperty.Std(propDef.getName(), t0, beanDesc.getClassAnnotations(), mutator);
        JavaType type = resolveType(ctxt, beanDesc, t0, mutator);
        if (type != t0) {
            property.withType(type);
        }
        JsonDeserializer<?> jsonDeserializerFindDeserializerFromAnnotation = findDeserializerFromAnnotation(ctxt, mutator);
        JavaType type2 = modifyTypeByAnnotation(ctxt, mutator, type);
        TypeDeserializer typeDeser = (TypeDeserializer) type2.getTypeHandler();
        if (mutator instanceof AnnotatedMethod) {
            prop = new MethodProperty(propDef, type2, typeDeser, beanDesc.getClassAnnotations(), (AnnotatedMethod) mutator);
        } else {
            prop = new FieldProperty(propDef, type2, typeDeser, beanDesc.getClassAnnotations(), (AnnotatedField) mutator);
        }
        if (jsonDeserializerFindDeserializerFromAnnotation != null) {
            prop = prop.withValueDeserializer(jsonDeserializerFindDeserializerFromAnnotation);
        }
        AnnotationIntrospector.ReferenceProperty ref = propDef.findReferenceType();
        if (ref != null && ref.isManagedReference()) {
            prop.setManagedReferenceName(ref.getName());
        }
        return prop;
    }

    protected SettableBeanProperty constructSetterlessProperty(DeserializationContext ctxt, BeanDescription beanDesc, BeanPropertyDefinition propDef) throws JsonMappingException {
        AnnotatedMethod getter = propDef.getGetter();
        if (ctxt.canOverrideAccessModifiers()) {
            getter.fixAccess();
        }
        JavaType type = getter.getType(beanDesc.bindingsForBeanType());
        JsonDeserializer<?> jsonDeserializerFindDeserializerFromAnnotation = findDeserializerFromAnnotation(ctxt, getter);
        JavaType type2 = modifyTypeByAnnotation(ctxt, getter, type);
        TypeDeserializer typeDeser = (TypeDeserializer) type2.getTypeHandler();
        SettableBeanProperty prop = new SetterlessProperty(propDef, type2, typeDeser, beanDesc.getClassAnnotations(), getter);
        if (jsonDeserializerFindDeserializerFromAnnotation != null) {
            return prop.withValueDeserializer(jsonDeserializerFindDeserializerFromAnnotation);
        }
        return prop;
    }

    protected boolean isPotentialBeanType(Class<?> type) {
        String typeStr = ClassUtil.canBeABeanType(type);
        if (typeStr != null) {
            throw new IllegalArgumentException("Can not deserialize Class " + type.getName() + " (of type " + typeStr + ") as a Bean");
        }
        if (ClassUtil.isProxyType(type)) {
            throw new IllegalArgumentException("Can not deserialize Proxy class " + type.getName() + " as a Bean");
        }
        String typeStr2 = ClassUtil.isLocalType(type, true);
        if (typeStr2 != null) {
            throw new IllegalArgumentException("Can not deserialize Class " + type.getName() + " (of type " + typeStr2 + ") as a Bean");
        }
        return true;
    }

    protected boolean isIgnorableType(DeserializationConfig config, BeanDescription beanDesc, Class<?> type, Map<Class<?>, Boolean> ignoredTypes) {
        Boolean status = ignoredTypes.get(type);
        if (status == null) {
            BeanDescription desc = config.introspectClassAnnotations(type);
            status = config.getAnnotationIntrospector().isIgnorableType(desc.getClassInfo());
            if (status == null) {
                status = Boolean.FALSE;
            }
        }
        return status.booleanValue();
    }
}
