package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.type.SimpleType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BasicClassIntrospector extends ClassIntrospector {
    protected static final BasicBeanDescription BOOLEAN_DESC;
    protected static final BasicBeanDescription INT_DESC;
    protected static final BasicBeanDescription LONG_DESC;
    protected static final BasicBeanDescription STRING_DESC;
    public static final BasicClassIntrospector instance;

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public /* bridge */ /* synthetic */ BeanDescription forClassAnnotations(MapperConfig x0, JavaType x1, ClassIntrospector.MixInResolver x2) {
        return forClassAnnotations((MapperConfig<?>) x0, x1, x2);
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public /* bridge */ /* synthetic */ BeanDescription forDirectClassAnnotations(MapperConfig x0, JavaType x1, ClassIntrospector.MixInResolver x2) {
        return forDirectClassAnnotations((MapperConfig<?>) x0, x1, x2);
    }

    static {
        AnnotatedClass ac = AnnotatedClass.constructWithoutSuperTypes(String.class, null, null);
        STRING_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(String.class), ac);
        AnnotatedClass ac2 = AnnotatedClass.constructWithoutSuperTypes(Boolean.TYPE, null, null);
        BOOLEAN_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Boolean.TYPE), ac2);
        AnnotatedClass ac3 = AnnotatedClass.constructWithoutSuperTypes(Integer.TYPE, null, null);
        INT_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Integer.TYPE), ac3);
        AnnotatedClass ac4 = AnnotatedClass.constructWithoutSuperTypes(Long.TYPE, null, null);
        LONG_DESC = BasicBeanDescription.forOtherUse(null, SimpleType.constructUnsafe(Long.TYPE), ac4);
        instance = new BasicClassIntrospector();
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forSerialization(SerializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
        BasicBeanDescription desc = _findCachedDesc(type);
        if (desc == null) {
            return BasicBeanDescription.forSerialization(collectProperties(cfg, type, r, true, "set"));
        }
        return desc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forDeserialization(DeserializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
        BasicBeanDescription desc = _findCachedDesc(type);
        if (desc == null) {
            return BasicBeanDescription.forDeserialization(collectProperties(cfg, type, r, false, "set"));
        }
        return desc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forDeserializationWithBuilder(DeserializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
        return BasicBeanDescription.forDeserialization(collectPropertiesWithBuilder(cfg, type, r, false));
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forCreation(DeserializationConfig cfg, JavaType type, ClassIntrospector.MixInResolver r) {
        BasicBeanDescription desc = _findCachedDesc(type);
        if (desc == null) {
            return BasicBeanDescription.forDeserialization(collectProperties(cfg, type, r, false, "set"));
        }
        return desc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forClassAnnotations(MapperConfig<?> cfg, JavaType type, ClassIntrospector.MixInResolver r) {
        boolean useAnnotations = cfg.isAnnotationProcessingEnabled();
        AnnotatedClass ac = AnnotatedClass.construct(type.getRawClass(), useAnnotations ? cfg.getAnnotationIntrospector() : null, r);
        return BasicBeanDescription.forOtherUse(cfg, type, ac);
    }

    @Override // com.fasterxml.jackson.databind.introspect.ClassIntrospector
    public BasicBeanDescription forDirectClassAnnotations(MapperConfig<?> cfg, JavaType type, ClassIntrospector.MixInResolver r) {
        boolean useAnnotations = cfg.isAnnotationProcessingEnabled();
        AnnotationIntrospector ai = cfg.getAnnotationIntrospector();
        Class<?> rawClass = type.getRawClass();
        if (!useAnnotations) {
            ai = null;
        }
        AnnotatedClass ac = AnnotatedClass.constructWithoutSuperTypes(rawClass, ai, r);
        return BasicBeanDescription.forOtherUse(cfg, type, ac);
    }

    protected POJOPropertiesCollector collectProperties(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r, boolean forSerialization, String mutatorPrefix) {
        boolean useAnnotations = config.isAnnotationProcessingEnabled();
        AnnotatedClass ac = AnnotatedClass.construct(type.getRawClass(), useAnnotations ? config.getAnnotationIntrospector() : null, r);
        return constructPropertyCollector(config, ac, type, forSerialization, mutatorPrefix).collect();
    }

    protected POJOPropertiesCollector collectPropertiesWithBuilder(MapperConfig<?> config, JavaType type, ClassIntrospector.MixInResolver r, boolean forSerialization) {
        boolean useAnnotations = config.isAnnotationProcessingEnabled();
        AnnotationIntrospector ai = useAnnotations ? config.getAnnotationIntrospector() : null;
        AnnotatedClass ac = AnnotatedClass.construct(type.getRawClass(), ai, r);
        JsonPOJOBuilder.Value builderConfig = ai == null ? null : ai.findPOJOBuilderConfig(ac);
        String mutatorPrefix = builderConfig == null ? "with" : builderConfig.withPrefix;
        return constructPropertyCollector(config, ac, type, forSerialization, mutatorPrefix).collect();
    }

    protected POJOPropertiesCollector constructPropertyCollector(MapperConfig<?> config, AnnotatedClass ac, JavaType type, boolean forSerialization, String mutatorPrefix) {
        return new POJOPropertiesCollector(config, forSerialization, type, ac, mutatorPrefix);
    }

    protected BasicBeanDescription _findCachedDesc(JavaType type) {
        Class<?> cls = type.getRawClass();
        if (cls == String.class) {
            return STRING_DESC;
        }
        if (cls == Boolean.TYPE) {
            return BOOLEAN_DESC;
        }
        if (cls == Integer.TYPE) {
            return INT_DESC;
        }
        if (cls == Long.TYPE) {
            return LONG_DESC;
        }
        return null;
    }
}
