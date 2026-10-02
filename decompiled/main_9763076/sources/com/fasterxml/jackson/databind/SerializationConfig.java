package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SerializationConfig extends MapperConfigBase<SerializationFeature, SerializationConfig> {
    protected final FilterProvider _filterProvider;
    protected final int _serFeatures;
    protected JsonInclude.Include _serializationInclusion;

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public /* bridge */ /* synthetic */ MapperConfigBase with(VisibilityChecker x0) {
        return with((VisibilityChecker<?>) x0);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public /* bridge */ /* synthetic */ MapperConfigBase with(TypeResolverBuilder x0) {
        return with((TypeResolverBuilder<?>) x0);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public /* bridge */ /* synthetic */ MapperConfigBase withView(Class x0) {
        return withView((Class<?>) x0);
    }

    public SerializationConfig(BaseSettings base, SubtypeResolver str, Map<ClassKey, Class<?>> mixins) {
        super(base, str, mixins);
        this._serializationInclusion = null;
        this._serFeatures = collectFeatureDefaults(SerializationFeature.class);
        this._filterProvider = null;
    }

    private SerializationConfig(SerializationConfig src, SubtypeResolver str) {
        super(src, str);
        this._serializationInclusion = null;
        this._serFeatures = src._serFeatures;
        this._serializationInclusion = src._serializationInclusion;
        this._filterProvider = src._filterProvider;
    }

    private SerializationConfig(SerializationConfig src, int mapperFeatures, int serFeatures) {
        super(src, mapperFeatures);
        this._serializationInclusion = null;
        this._serFeatures = serFeatures;
        this._serializationInclusion = src._serializationInclusion;
        this._filterProvider = src._filterProvider;
    }

    private SerializationConfig(SerializationConfig src, BaseSettings base) {
        super(src, base);
        this._serializationInclusion = null;
        this._serFeatures = src._serFeatures;
        this._serializationInclusion = src._serializationInclusion;
        this._filterProvider = src._filterProvider;
    }

    private SerializationConfig(SerializationConfig src, FilterProvider filters) {
        super(src);
        this._serializationInclusion = null;
        this._serFeatures = src._serFeatures;
        this._serializationInclusion = src._serializationInclusion;
        this._filterProvider = filters;
    }

    private SerializationConfig(SerializationConfig src, Class<?> view) {
        super(src, view);
        this._serializationInclusion = null;
        this._serFeatures = src._serFeatures;
        this._serializationInclusion = src._serializationInclusion;
        this._filterProvider = src._filterProvider;
    }

    private SerializationConfig(SerializationConfig src, JsonInclude.Include incl) {
        super(src);
        this._serializationInclusion = null;
        this._serFeatures = src._serFeatures;
        this._serializationInclusion = incl;
        this._filterProvider = src._filterProvider;
    }

    private SerializationConfig(SerializationConfig src, String rootName) {
        super(src, rootName);
        this._serializationInclusion = null;
        this._serFeatures = src._serFeatures;
        this._serializationInclusion = src._serializationInclusion;
        this._filterProvider = src._filterProvider;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public SerializationConfig with(MapperFeature... features) {
        int newMapperFlags = this._mapperFeatures;
        for (MapperFeature f : features) {
            newMapperFlags |= f.getMask();
        }
        return newMapperFlags == this._mapperFeatures ? this : new SerializationConfig(this, newMapperFlags, this._serFeatures);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public SerializationConfig without(MapperFeature... features) {
        int newMapperFlags = this._mapperFeatures;
        for (MapperFeature f : features) {
            newMapperFlags &= f.getMask() ^ (-1);
        }
        return newMapperFlags == this._mapperFeatures ? this : new SerializationConfig(this, newMapperFlags, this._serFeatures);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(AnnotationIntrospector ai) {
        return _withBase(this._base.withAnnotationIntrospector(ai));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector ai) {
        return _withBase(this._base.withAppendedAnnotationIntrospector(ai));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector ai) {
        return _withBase(this._base.withInsertedAnnotationIntrospector(ai));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(ClassIntrospector ci) {
        return _withBase(this._base.withClassIntrospector(ci));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(DateFormat df) {
        SerializationConfig cfg = new SerializationConfig(this, this._base.withDateFormat(df));
        if (df == null) {
            return cfg.with(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return cfg.without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(HandlerInstantiator hi) {
        return _withBase(this._base.withHandlerInstantiator(hi));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(PropertyNamingStrategy pns) {
        return _withBase(this._base.withPropertyNamingStrategy(pns));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig withRootName(String rootName) {
        if (rootName == null) {
            if (this._rootName == null) {
                return this;
            }
        } else if (rootName.equals(this._rootName)) {
            return this;
        }
        return new SerializationConfig(this, rootName);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(SubtypeResolver str) {
        return str == this._subtypeResolver ? this : new SerializationConfig(this, str);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(TypeFactory tf) {
        return _withBase(this._base.withTypeFactory(tf));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(TypeResolverBuilder<?> trb) {
        return _withBase(this._base.withTypeResolverBuilder(trb));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig withView(Class<?> view) {
        return this._view == view ? this : new SerializationConfig(this, view);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(VisibilityChecker<?> vc) {
        return _withBase(this._base.withVisibilityChecker(vc));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig withVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility) {
        return _withBase(this._base.withVisibility(forMethod, visibility));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(Locale l) {
        return _withBase(this._base.with(l));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public SerializationConfig with(TimeZone tz) {
        return _withBase(this._base.with(tz));
    }

    private final SerializationConfig _withBase(BaseSettings newBase) {
        return this._base == newBase ? this : new SerializationConfig(this, newBase);
    }

    public SerializationConfig with(SerializationFeature feature) {
        int newSerFeatures = this._serFeatures | feature.getMask();
        return newSerFeatures == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures);
    }

    public SerializationConfig with(SerializationFeature first, SerializationFeature... features) {
        int newSerFeatures = this._serFeatures | first.getMask();
        for (SerializationFeature f : features) {
            newSerFeatures |= f.getMask();
        }
        return newSerFeatures == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures);
    }

    public SerializationConfig withFeatures(SerializationFeature... features) {
        int newSerFeatures = this._serFeatures;
        for (SerializationFeature f : features) {
            newSerFeatures |= f.getMask();
        }
        return newSerFeatures == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures);
    }

    public SerializationConfig without(SerializationFeature feature) {
        int newSerFeatures = this._serFeatures & (feature.getMask() ^ (-1));
        return newSerFeatures == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures);
    }

    public SerializationConfig without(SerializationFeature first, SerializationFeature... features) {
        int newSerFeatures = this._serFeatures & (first.getMask() ^ (-1));
        for (SerializationFeature f : features) {
            newSerFeatures &= f.getMask() ^ (-1);
        }
        return newSerFeatures == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures);
    }

    public SerializationConfig withoutFeatures(SerializationFeature... features) {
        int newSerFeatures = this._serFeatures;
        for (SerializationFeature f : features) {
            newSerFeatures &= f.getMask() ^ (-1);
        }
        return newSerFeatures == this._serFeatures ? this : new SerializationConfig(this, this._mapperFeatures, newSerFeatures);
    }

    public SerializationConfig withFilters(FilterProvider filterProvider) {
        return filterProvider == this._filterProvider ? this : new SerializationConfig(this, filterProvider);
    }

    public SerializationConfig withSerializationInclusion(JsonInclude.Include incl) {
        return this._serializationInclusion == incl ? this : new SerializationConfig(this, incl);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public boolean useRootWrapping() {
        if (this._rootName != null) {
            return this._rootName.length() > 0;
        }
        return isEnabled(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public AnnotationIntrospector getAnnotationIntrospector() {
        return isEnabled(MapperFeature.USE_ANNOTATIONS) ? super.getAnnotationIntrospector() : AnnotationIntrospector.nopInstance();
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public BeanDescription introspectClassAnnotations(JavaType type) {
        return getClassIntrospector().forClassAnnotations(this, type, this);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public BeanDescription introspectDirectClassAnnotations(JavaType type) {
        return getClassIntrospector().forDirectClassAnnotations(this, type, this);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public VisibilityChecker<?> getDefaultVisibilityChecker() {
        VisibilityChecker<?> vchecker = super.getDefaultVisibilityChecker();
        if (!isEnabled(MapperFeature.AUTO_DETECT_GETTERS)) {
            vchecker = vchecker.withGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_IS_GETTERS)) {
            vchecker = vchecker.withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
            return vchecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return vchecker;
    }

    public final boolean isEnabled(SerializationFeature f) {
        return (this._serFeatures & f.getMask()) != 0;
    }

    public final int getSerializationFeatures() {
        return this._serFeatures;
    }

    public JsonInclude.Include getSerializationInclusion() {
        return this._serializationInclusion != null ? this._serializationInclusion : JsonInclude.Include.ALWAYS;
    }

    public FilterProvider getFilterProvider() {
        return this._filterProvider;
    }

    public <T extends BeanDescription> T introspect(JavaType javaType) {
        return (T) getClassIntrospector().forSerialization(this, javaType, this);
    }

    public String toString() {
        return "[SerializationConfig: flags=0x" + Integer.toHexString(this._serFeatures) + "]";
    }
}
