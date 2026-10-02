package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.tencent.android.tpush.common.Constants;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JacksonAnnotationIntrospector extends AnnotationIntrospector {
    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return DatabindVersion.instance.version();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean isHandled(Annotation ann) {
        Class<? extends Annotation> acls = ann.annotationType();
        return acls.getAnnotation(JacksonAnnotation.class) != null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean isAnnotationBundle(Annotation ann) {
        return ann.annotationType().getAnnotation(JacksonAnnotationsInside.class) != null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findEnumValue(Enum<?> value) {
        return value.name();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findRootName(AnnotatedClass ac) {
        JsonRootName ann = (JsonRootName) ac.getAnnotation(JsonRootName.class);
        if (ann == null) {
            return null;
        }
        return ann.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String[] findPropertiesToIgnore(Annotated ac) {
        JsonIgnoreProperties ignore = (JsonIgnoreProperties) ac.getAnnotation(JsonIgnoreProperties.class);
        if (ignore == null) {
            return null;
        }
        return ignore.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
        JsonIgnoreProperties ignore = (JsonIgnoreProperties) ac.getAnnotation(JsonIgnoreProperties.class);
        if (ignore == null) {
            return null;
        }
        return Boolean.valueOf(ignore.ignoreUnknown());
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean isIgnorableType(AnnotatedClass ac) {
        JsonIgnoreType ignore = (JsonIgnoreType) ac.getAnnotation(JsonIgnoreType.class);
        if (ignore == null) {
            return null;
        }
        return Boolean.valueOf(ignore.value());
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findFilterId(AnnotatedClass ac) {
        JsonFilter ann = (JsonFilter) ac.getAnnotation(JsonFilter.class);
        if (ann != null) {
            String id = ann.value();
            if (id.length() > 0) {
                return id;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
        JsonAutoDetect ann = (JsonAutoDetect) ac.getAnnotation(JsonAutoDetect.class);
        return ann == null ? checker : checker.with(ann);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public AnnotationIntrospector.ReferenceProperty findReferenceType(AnnotatedMember member) {
        JsonManagedReference ref1 = (JsonManagedReference) member.getAnnotation(JsonManagedReference.class);
        if (ref1 != null) {
            return AnnotationIntrospector.ReferenceProperty.managed(ref1.value());
        }
        JsonBackReference ref2 = (JsonBackReference) member.getAnnotation(JsonBackReference.class);
        if (ref2 != null) {
            return AnnotationIntrospector.ReferenceProperty.back(ref2.value());
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
        JsonUnwrapped ann = (JsonUnwrapped) member.getAnnotation(JsonUnwrapped.class);
        if (ann == null || !ann.enabled()) {
            return null;
        }
        String prefix = ann.prefix();
        String suffix = ann.suffix();
        return NameTransformer.simpleTransformer(prefix, suffix);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean hasIgnoreMarker(AnnotatedMember m) {
        return _isIgnorable(m);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean hasRequiredMarker(AnnotatedMember m) {
        JsonProperty ann = (JsonProperty) m.getAnnotation(JsonProperty.class);
        if (ann != null) {
            return Boolean.valueOf(ann.required());
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findInjectableValueId(AnnotatedMember m) {
        JacksonInject ann = (JacksonInject) m.getAnnotation(JacksonInject.class);
        if (ann == null) {
            return null;
        }
        String id = ann.value();
        if (id.length() == 0) {
            if (!(m instanceof AnnotatedMethod)) {
                return m.getRawType().getName();
            }
            AnnotatedMethod am = (AnnotatedMethod) m;
            if (am.getParameterCount() == 0) {
                return m.getRawType().getName();
            }
            return am.getRawParameterType(0).getName();
        }
        return id;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
        return _findTypeResolver(config, ac, baseType);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
        if (baseType.isContainerType()) {
            return null;
        }
        return _findTypeResolver(config, am, baseType);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
        if (!containerType.isContainerType()) {
            throw new IllegalArgumentException("Must call method with a container type (got " + containerType + ")");
        }
        return _findTypeResolver(config, am, containerType);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public List<NamedType> findSubtypes(Annotated a) {
        JsonSubTypes t = (JsonSubTypes) a.getAnnotation(JsonSubTypes.class);
        if (t == null) {
            return null;
        }
        JsonSubTypes.Type[] types = t.value();
        ArrayList<NamedType> result = new ArrayList<>(types.length);
        for (JsonSubTypes.Type type : types) {
            result.add(new NamedType(type.value(), type.name()));
        }
        return result;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findTypeName(AnnotatedClass ac) {
        JsonTypeName tn = (JsonTypeName) ac.getAnnotation(JsonTypeName.class);
        if (tn == null) {
            return null;
        }
        return tn.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findSerializer(Annotated a) {
        Class<? extends JsonSerializer<?>> serClass;
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann == null || (serClass = ann.using()) == JsonSerializer.None.class) {
            JsonRawValue annRaw = (JsonRawValue) a.getAnnotation(JsonRawValue.class);
            if (annRaw != null && annRaw.value()) {
                Class<?> cls = a.getRawType();
                return new RawSerializer(cls);
            }
            return null;
        }
        return serClass;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<? extends JsonSerializer<?>> findKeySerializer(Annotated a) {
        Class<? extends JsonSerializer<?>> serClass;
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann == null || (serClass = ann.keyUsing()) == JsonSerializer.None.class) {
            return null;
        }
        return serClass;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<? extends JsonSerializer<?>> findContentSerializer(Annotated a) {
        Class<? extends JsonSerializer<?>> serClass;
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann == null || (serClass = ann.contentUsing()) == JsonSerializer.None.class) {
            return null;
        }
        return serClass;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonInclude.Include findSerializationInclusion(Annotated a, JsonInclude.Include defValue) {
        JsonInclude inc = (JsonInclude) a.getAnnotation(JsonInclude.class);
        if (inc != null) {
            return inc.value();
        }
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann != null) {
            JsonSerialize.Inclusion i2 = ann.include();
            switch (i2) {
                case ALWAYS:
                    return JsonInclude.Include.ALWAYS;
                case NON_NULL:
                    return JsonInclude.Include.NON_NULL;
                case NON_DEFAULT:
                    return JsonInclude.Include.NON_DEFAULT;
                case NON_EMPTY:
                    return JsonInclude.Include.NON_EMPTY;
                default:
                    return defValue;
            }
        }
        return defValue;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findSerializationType(Annotated am) {
        Class<?> cls;
        JsonSerialize ann = (JsonSerialize) am.getAnnotation(JsonSerialize.class);
        if (ann == null || (cls = ann.as()) == NoClass.class) {
            return null;
        }
        return cls;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
        Class<?> cls;
        JsonSerialize ann = (JsonSerialize) am.getAnnotation(JsonSerialize.class);
        if (ann == null || (cls = ann.keyAs()) == NoClass.class) {
            return null;
        }
        return cls;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
        Class<?> cls;
        JsonSerialize ann = (JsonSerialize) am.getAnnotation(JsonSerialize.class);
        if (ann == null || (cls = ann.contentAs()) == NoClass.class) {
            return null;
        }
        return cls;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonSerialize.Typing findSerializationTyping(Annotated a) {
        JsonSerialize ann = (JsonSerialize) a.getAnnotation(JsonSerialize.class);
        if (ann == null) {
            return null;
        }
        return ann.typing();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?>[] findViews(Annotated a) {
        JsonView ann = (JsonView) a.getAnnotation(JsonView.class);
        if (ann == null) {
            return null;
        }
        return ann.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean isTypeId(AnnotatedMember member) {
        return Boolean.valueOf(member.hasAnnotation(JsonTypeId.class));
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public ObjectIdInfo findObjectIdInfo(Annotated ann) {
        JsonIdentityInfo info = (JsonIdentityInfo) ann.getAnnotation(JsonIdentityInfo.class);
        if (info == null || info.generator() == ObjectIdGenerators.None.class) {
            return null;
        }
        return new ObjectIdInfo(info.property(), info.scope(), info.generator());
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonFormat.Value findFormat(AnnotatedMember member) {
        JsonFormat ann = (JsonFormat) member.getAnnotation(JsonFormat.class);
        if (ann == null) {
            return null;
        }
        return new JsonFormat.Value(ann);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
        JsonPropertyOrder order = (JsonPropertyOrder) ac.getAnnotation(JsonPropertyOrder.class);
        if (order == null) {
            return null;
        }
        return order.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Boolean findSerializationSortAlphabetically(AnnotatedClass ac) {
        JsonPropertyOrder order = (JsonPropertyOrder) ac.getAnnotation(JsonPropertyOrder.class);
        if (order == null) {
            return null;
        }
        return Boolean.valueOf(order.alphabetic());
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findSerializationName(AnnotatedMethod am) {
        JsonGetter ann = (JsonGetter) am.getAnnotation(JsonGetter.class);
        if (ann != null) {
            return ann.value();
        }
        JsonProperty pann = (JsonProperty) am.getAnnotation(JsonProperty.class);
        if (pann != null) {
            return pann.value();
        }
        if (am.hasAnnotation(JsonSerialize.class) || am.hasAnnotation(JsonView.class)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean hasAsValueAnnotation(AnnotatedMethod am) {
        JsonValue ann = (JsonValue) am.getAnnotation(JsonValue.class);
        return ann != null && ann.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findSerializationName(AnnotatedField af) {
        JsonProperty pann = (JsonProperty) af.getAnnotation(JsonProperty.class);
        if (pann != null) {
            return pann.value();
        }
        if (af.hasAnnotation(JsonSerialize.class) || af.hasAnnotation(JsonView.class)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<? extends JsonDeserializer<?>> findDeserializer(Annotated a) {
        Class<? extends JsonDeserializer<?>> deserClass;
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        if (ann == null || (deserClass = ann.using()) == JsonDeserializer.None.class) {
            return null;
        }
        return deserClass;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<? extends KeyDeserializer> findKeyDeserializer(Annotated a) {
        Class<? extends KeyDeserializer> deserClass;
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        if (ann == null || (deserClass = ann.keyUsing()) == KeyDeserializer.None.class) {
            return null;
        }
        return deserClass;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<? extends JsonDeserializer<?>> findContentDeserializer(Annotated a) {
        Class<? extends JsonDeserializer<?>> deserClass;
        JsonDeserialize ann = (JsonDeserialize) a.getAnnotation(JsonDeserialize.class);
        if (ann == null || (deserClass = ann.contentUsing()) == JsonDeserializer.None.class) {
            return null;
        }
        return deserClass;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
        Class<?> cls;
        JsonDeserialize ann = (JsonDeserialize) am.getAnnotation(JsonDeserialize.class);
        if (ann == null || (cls = ann.as()) == NoClass.class) {
            return null;
        }
        return cls;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
        Class<?> cls;
        JsonDeserialize ann = (JsonDeserialize) am.getAnnotation(JsonDeserialize.class);
        if (ann == null || (cls = ann.keyAs()) == NoClass.class) {
            return null;
        }
        return cls;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
        Class<?> cls;
        JsonDeserialize ann = (JsonDeserialize) am.getAnnotation(JsonDeserialize.class);
        if (ann == null || (cls = ann.contentAs()) == NoClass.class) {
            return null;
        }
        return cls;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Object findValueInstantiator(AnnotatedClass ac) {
        JsonValueInstantiator ann = (JsonValueInstantiator) ac.getAnnotation(JsonValueInstantiator.class);
        if (ann == null) {
            return null;
        }
        return ann.value();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public Class<?> findPOJOBuilder(AnnotatedClass ac) {
        JsonDeserialize ann = (JsonDeserialize) ac.getAnnotation(JsonDeserialize.class);
        if (ann == null || ann.builder() == NoClass.class) {
            return null;
        }
        return ann.builder();
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
        JsonPOJOBuilder ann = (JsonPOJOBuilder) ac.getAnnotation(JsonPOJOBuilder.class);
        if (ann == null) {
            return null;
        }
        return new JsonPOJOBuilder.Value(ann);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findDeserializationName(AnnotatedMethod am) {
        JsonSetter ann = (JsonSetter) am.getAnnotation(JsonSetter.class);
        if (ann != null) {
            return ann.value();
        }
        JsonProperty pann = (JsonProperty) am.getAnnotation(JsonProperty.class);
        if (pann != null) {
            return pann.value();
        }
        if (am.hasAnnotation(JsonDeserialize.class) || am.hasAnnotation(JsonView.class) || am.hasAnnotation(JsonBackReference.class) || am.hasAnnotation(JsonManagedReference.class)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
        return am.hasAnnotation(JsonAnySetter.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
        return am.hasAnnotation(JsonAnyGetter.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public boolean hasCreatorAnnotation(Annotated a) {
        return a.hasAnnotation(JsonCreator.class);
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findDeserializationName(AnnotatedField af) {
        JsonProperty pann = (JsonProperty) af.getAnnotation(JsonProperty.class);
        if (pann != null) {
            return pann.value();
        }
        if (af.hasAnnotation(JsonDeserialize.class) || af.hasAnnotation(JsonView.class) || af.hasAnnotation(JsonBackReference.class) || af.hasAnnotation(JsonManagedReference.class)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
    public String findDeserializationName(AnnotatedParameter param) {
        JsonProperty pann;
        if (param == null || (pann = (JsonProperty) param.getAnnotation(JsonProperty.class)) == null) {
            return null;
        }
        return pann.value();
    }

    protected boolean _isIgnorable(Annotated a) {
        JsonIgnore ann = (JsonIgnore) a.getAnnotation(JsonIgnore.class);
        return ann != null && ann.value();
    }

    protected TypeResolverBuilder<?> _findTypeResolver(MapperConfig<?> config, Annotated ann, JavaType baseType) {
        TypeResolverBuilder<?> b;
        JsonTypeInfo info = (JsonTypeInfo) ann.getAnnotation(JsonTypeInfo.class);
        JsonTypeResolver resAnn = (JsonTypeResolver) ann.getAnnotation(JsonTypeResolver.class);
        if (resAnn != null) {
            if (info == null) {
                return null;
            }
            b = config.typeResolverBuilderInstance(ann, resAnn.value());
        } else {
            if (info == null) {
                return null;
            }
            if (info.use() == JsonTypeInfo.Id.NONE) {
                return _constructNoTypeResolverBuilder();
            }
            b = _constructStdTypeResolverBuilder();
        }
        JsonTypeIdResolver idResInfo = (JsonTypeIdResolver) ann.getAnnotation(JsonTypeIdResolver.class);
        TypeIdResolver idRes = idResInfo == null ? null : config.typeIdResolverInstance(ann, idResInfo.value());
        if (idRes != null) {
            idRes.init(baseType);
        }
        TypeResolverBuilder<?> b2 = b.init(info.use(), idRes);
        JsonTypeInfo.As inclusion = info.include();
        if (inclusion == JsonTypeInfo.As.EXTERNAL_PROPERTY && (ann instanceof AnnotatedClass)) {
            inclusion = JsonTypeInfo.As.PROPERTY;
        }
        TypeResolverBuilder<?> b3 = b2.inclusion(inclusion).typeProperty(info.property());
        Class<?> defaultImpl = info.defaultImpl();
        if (defaultImpl != JsonTypeInfo.None.class) {
            b3 = b3.defaultImpl(defaultImpl);
        }
        return b3.typeIdVisibility(info.visible());
    }

    protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
        return new StdTypeResolverBuilder();
    }

    protected StdTypeResolverBuilder _constructNoTypeResolverBuilder() {
        return StdTypeResolverBuilder.noTypeInfoBuilder();
    }
}
