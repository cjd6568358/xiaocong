package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfigBase;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.LinkedNode;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class DeserializationConfig extends MapperConfigBase<DeserializationFeature, DeserializationConfig> {
    protected final int _deserFeatures;
    protected final JsonNodeFactory _nodeFactory;
    protected final LinkedNode<DeserializationProblemHandler> _problemHandlers;

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

    public DeserializationConfig(BaseSettings base, SubtypeResolver str, Map<ClassKey, Class<?>> mixins) {
        super(base, str, mixins);
        this._deserFeatures = collectFeatureDefaults(DeserializationFeature.class);
        this._nodeFactory = JsonNodeFactory.instance;
        this._problemHandlers = null;
    }

    private DeserializationConfig(DeserializationConfig src, SubtypeResolver str) {
        super(src, str);
        this._deserFeatures = src._deserFeatures;
        this._nodeFactory = src._nodeFactory;
        this._problemHandlers = src._problemHandlers;
    }

    private DeserializationConfig(DeserializationConfig src, int mapperFeatures, int deserFeatures) {
        super(src, mapperFeatures);
        this._deserFeatures = deserFeatures;
        this._nodeFactory = src._nodeFactory;
        this._problemHandlers = src._problemHandlers;
    }

    private DeserializationConfig(DeserializationConfig src, BaseSettings base) {
        super(src, base);
        this._deserFeatures = src._deserFeatures;
        this._nodeFactory = src._nodeFactory;
        this._problemHandlers = src._problemHandlers;
    }

    private DeserializationConfig(DeserializationConfig src, JsonNodeFactory f) {
        super(src);
        this._deserFeatures = src._deserFeatures;
        this._problemHandlers = src._problemHandlers;
        this._nodeFactory = f;
    }

    private DeserializationConfig(DeserializationConfig src, LinkedNode<DeserializationProblemHandler> problemHandlers) {
        super(src);
        this._deserFeatures = src._deserFeatures;
        this._problemHandlers = problemHandlers;
        this._nodeFactory = src._nodeFactory;
    }

    private DeserializationConfig(DeserializationConfig src, String rootName) {
        super(src, rootName);
        this._deserFeatures = src._deserFeatures;
        this._problemHandlers = src._problemHandlers;
        this._nodeFactory = src._nodeFactory;
    }

    private DeserializationConfig(DeserializationConfig src, Class<?> view) {
        super(src, view);
        this._deserFeatures = src._deserFeatures;
        this._problemHandlers = src._problemHandlers;
        this._nodeFactory = src._nodeFactory;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public DeserializationConfig with(MapperFeature... features) {
        int newMapperFlags = this._mapperFeatures;
        for (MapperFeature f : features) {
            newMapperFlags |= f.getMask();
        }
        return newMapperFlags == this._mapperFeatures ? this : new DeserializationConfig(this, newMapperFlags, this._deserFeatures);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public DeserializationConfig without(MapperFeature... features) {
        int newMapperFlags = this._mapperFeatures;
        for (MapperFeature f : features) {
            newMapperFlags &= f.getMask() ^ (-1);
        }
        return newMapperFlags == this._mapperFeatures ? this : new DeserializationConfig(this, newMapperFlags, this._deserFeatures);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(ClassIntrospector ci) {
        return _withBase(this._base.withClassIntrospector(ci));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(AnnotationIntrospector ai) {
        return _withBase(this._base.withAnnotationIntrospector(ai));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(VisibilityChecker<?> vc) {
        return _withBase(this._base.withVisibilityChecker(vc));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig withVisibility(PropertyAccessor forMethod, JsonAutoDetect.Visibility visibility) {
        return _withBase(this._base.withVisibility(forMethod, visibility));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(TypeResolverBuilder<?> trb) {
        return _withBase(this._base.withTypeResolverBuilder(trb));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(SubtypeResolver str) {
        return this._subtypeResolver == str ? this : new DeserializationConfig(this, str);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(PropertyNamingStrategy pns) {
        return _withBase(this._base.withPropertyNamingStrategy(pns));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig withRootName(String rootName) {
        if (rootName == null) {
            if (this._rootName == null) {
                return this;
            }
        } else if (rootName.equals(this._rootName)) {
            return this;
        }
        return new DeserializationConfig(this, rootName);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(TypeFactory tf) {
        return _withBase(this._base.withTypeFactory(tf));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(DateFormat df) {
        return _withBase(this._base.withDateFormat(df));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(HandlerInstantiator hi) {
        return _withBase(this._base.withHandlerInstantiator(hi));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig withInsertedAnnotationIntrospector(AnnotationIntrospector ai) {
        return _withBase(this._base.withInsertedAnnotationIntrospector(ai));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig withAppendedAnnotationIntrospector(AnnotationIntrospector ai) {
        return _withBase(this._base.withAppendedAnnotationIntrospector(ai));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig withView(Class<?> view) {
        return this._view == view ? this : new DeserializationConfig(this, view);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(Locale l) {
        return _withBase(this._base.with(l));
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfigBase
    public DeserializationConfig with(TimeZone tz) {
        return _withBase(this._base.with(tz));
    }

    private final DeserializationConfig _withBase(BaseSettings newBase) {
        return this._base == newBase ? this : new DeserializationConfig(this, newBase);
    }

    public DeserializationConfig with(JsonNodeFactory f) {
        return this._nodeFactory == f ? this : new DeserializationConfig(this, f);
    }

    public DeserializationConfig withHandler(DeserializationProblemHandler h) {
        return LinkedNode.contains(this._problemHandlers, h) ? this : new DeserializationConfig(this, (LinkedNode<DeserializationProblemHandler>) new LinkedNode(h, this._problemHandlers));
    }

    public DeserializationConfig withNoProblemHandlers() {
        return this._problemHandlers == null ? this : new DeserializationConfig(this, (LinkedNode<DeserializationProblemHandler>) null);
    }

    public DeserializationConfig with(DeserializationFeature feature) {
        int newDeserFeatures = this._deserFeatures | feature.getMask();
        return newDeserFeatures == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, newDeserFeatures);
    }

    public DeserializationConfig with(DeserializationFeature first, DeserializationFeature... features) {
        int newDeserFeatures = this._deserFeatures | first.getMask();
        for (DeserializationFeature f : features) {
            newDeserFeatures |= f.getMask();
        }
        return newDeserFeatures == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, newDeserFeatures);
    }

    public DeserializationConfig withFeatures(DeserializationFeature... features) {
        int newDeserFeatures = this._deserFeatures;
        for (DeserializationFeature f : features) {
            newDeserFeatures |= f.getMask();
        }
        return newDeserFeatures == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, newDeserFeatures);
    }

    public DeserializationConfig without(DeserializationFeature feature) {
        int newDeserFeatures = this._deserFeatures & (feature.getMask() ^ (-1));
        return newDeserFeatures == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, newDeserFeatures);
    }

    public DeserializationConfig without(DeserializationFeature first, DeserializationFeature... features) {
        int newDeserFeatures = this._deserFeatures & (first.getMask() ^ (-1));
        for (DeserializationFeature f : features) {
            newDeserFeatures &= f.getMask() ^ (-1);
        }
        return newDeserFeatures == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, newDeserFeatures);
    }

    public DeserializationConfig withoutFeatures(DeserializationFeature... features) {
        int newDeserFeatures = this._deserFeatures;
        for (DeserializationFeature f : features) {
            newDeserFeatures &= f.getMask() ^ (-1);
        }
        return newDeserFeatures == this._deserFeatures ? this : new DeserializationConfig(this, this._mapperFeatures, newDeserFeatures);
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public AnnotationIntrospector getAnnotationIntrospector() {
        return isEnabled(MapperFeature.USE_ANNOTATIONS) ? super.getAnnotationIntrospector() : NopAnnotationIntrospector.instance;
    }

    @Override // com.fasterxml.jackson.databind.cfg.MapperConfig
    public boolean useRootWrapping() {
        if (this._rootName != null) {
            return this._rootName.length() > 0;
        }
        return isEnabled(DeserializationFeature.UNWRAP_ROOT_VALUE);
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
        if (!isEnabled(MapperFeature.AUTO_DETECT_SETTERS)) {
            vchecker = vchecker.withSetterVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_CREATORS)) {
            vchecker = vchecker.withCreatorVisibility(JsonAutoDetect.Visibility.NONE);
        }
        if (!isEnabled(MapperFeature.AUTO_DETECT_FIELDS)) {
            return vchecker.withFieldVisibility(JsonAutoDetect.Visibility.NONE);
        }
        return vchecker;
    }

    public final boolean isEnabled(DeserializationFeature f) {
        return (this._deserFeatures & f.getMask()) != 0;
    }

    public final int getDeserializationFeatures() {
        return this._deserFeatures;
    }

    public LinkedNode<DeserializationProblemHandler> getProblemHandlers() {
        return this._problemHandlers;
    }

    public Base64Variant getBase64Variant() {
        return Base64Variants.getDefaultVariant();
    }

    public final JsonNodeFactory getNodeFactory() {
        return this._nodeFactory;
    }

    public <T extends BeanDescription> T introspect(JavaType javaType) {
        return (T) getClassIntrospector().forDeserialization(this, javaType, this);
    }

    public <T extends BeanDescription> T introspectForCreation(JavaType javaType) {
        return (T) getClassIntrospector().forCreation(this, javaType, this);
    }

    public <T extends BeanDescription> T introspectForBuilder(JavaType javaType) {
        return (T) getClassIntrospector().forDeserializationWithBuilder(this, javaType, this);
    }
}
