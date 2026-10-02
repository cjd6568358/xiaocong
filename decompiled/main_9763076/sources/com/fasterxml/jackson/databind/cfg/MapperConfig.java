package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class MapperConfig<T extends MapperConfig<T>> implements ClassIntrospector.MixInResolver {
    protected final BaseSettings _base;
    protected final int _mapperFeatures;

    public abstract Class<?> getActiveView();

    public abstract SubtypeResolver getSubtypeResolver();

    public abstract BeanDescription introspectClassAnnotations(JavaType javaType);

    public abstract BeanDescription introspectDirectClassAnnotations(JavaType javaType);

    public abstract boolean useRootWrapping();

    public abstract T with(MapperFeature... mapperFeatureArr);

    public abstract T without(MapperFeature... mapperFeatureArr);

    protected MapperConfig(BaseSettings base, int mapperFeatures) {
        this._base = base;
        this._mapperFeatures = mapperFeatures;
    }

    protected MapperConfig(MapperConfig<T> src) {
        this._base = src._base;
        this._mapperFeatures = src._mapperFeatures;
    }

    public static <F extends Enum<F> & ConfigFeature> int collectFeatureDefaults(Class<F> cls) {
        int mask = 0;
        for (Object obj : (Enum[]) cls.getEnumConstants()) {
            if (((ConfigFeature) obj).enabledByDefault()) {
                mask |= ((ConfigFeature) obj).getMask();
            }
        }
        return mask;
    }

    public final boolean isEnabled(MapperFeature f) {
        return (this._mapperFeatures & f.getMask()) != 0;
    }

    public final boolean isAnnotationProcessingEnabled() {
        return isEnabled(MapperFeature.USE_ANNOTATIONS);
    }

    public final boolean canOverrideAccessModifiers() {
        return isEnabled(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);
    }

    public final boolean shouldSortPropertiesAlphabetically() {
        return isEnabled(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    }

    public ClassIntrospector getClassIntrospector() {
        return this._base.getClassIntrospector();
    }

    public AnnotationIntrospector getAnnotationIntrospector() {
        return this._base.getAnnotationIntrospector();
    }

    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        return this._base.getVisibilityChecker();
    }

    public final PropertyNamingStrategy getPropertyNamingStrategy() {
        return this._base.getPropertyNamingStrategy();
    }

    public final HandlerInstantiator getHandlerInstantiator() {
        return this._base.getHandlerInstantiator();
    }

    public final TypeResolverBuilder<?> getDefaultTyper(JavaType baseType) {
        return this._base.getTypeResolverBuilder();
    }

    public final TypeFactory getTypeFactory() {
        return this._base.getTypeFactory();
    }

    public final JavaType constructType(Class<?> cls) {
        return getTypeFactory().constructType(cls, (TypeBindings) null);
    }

    public final JavaType constructType(TypeReference<?> valueTypeRef) {
        return getTypeFactory().constructType(valueTypeRef.getType(), (TypeBindings) null);
    }

    public JavaType constructSpecializedType(JavaType baseType, Class<?> subclass) {
        return getTypeFactory().constructSpecializedType(baseType, subclass);
    }

    public BeanDescription introspectClassAnnotations(Class<?> cls) {
        return introspectClassAnnotations(constructType(cls));
    }

    public BeanDescription introspectDirectClassAnnotations(Class<?> cls) {
        return introspectDirectClassAnnotations(constructType(cls));
    }

    public final DateFormat getDateFormat() {
        return this._base.getDateFormat();
    }

    public final Locale getLocale() {
        return this._base.getLocale();
    }

    public final TimeZone getTimeZone() {
        return this._base.getTimeZone();
    }

    public TypeResolverBuilder<?> typeResolverBuilderInstance(Annotated annotated, Class<? extends TypeResolverBuilder<?>> builderClass) {
        TypeResolverBuilder<?> builder;
        HandlerInstantiator hi = getHandlerInstantiator();
        return (hi == null || (builder = hi.typeResolverBuilderInstance(this, annotated, builderClass)) == null) ? (TypeResolverBuilder) ClassUtil.createInstance(builderClass, canOverrideAccessModifiers()) : builder;
    }

    public TypeIdResolver typeIdResolverInstance(Annotated annotated, Class<? extends TypeIdResolver> resolverClass) {
        TypeIdResolver builder;
        HandlerInstantiator hi = getHandlerInstantiator();
        return (hi == null || (builder = hi.typeIdResolverInstance(this, annotated, resolverClass)) == null) ? (TypeIdResolver) ClassUtil.createInstance(resolverClass, canOverrideAccessModifiers()) : builder;
    }
}
