package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AnnotationIntrospector implements Versioned {
    @Override // com.fasterxml.jackson.core.Versioned
    public abstract Version version();

    public static class ReferenceProperty {
        private final String _name;
        private final Type _type;

        public enum Type {
            MANAGED_REFERENCE,
            BACK_REFERENCE
        }

        public ReferenceProperty(Type t, String n) {
            this._type = t;
            this._name = n;
        }

        public static ReferenceProperty managed(String name) {
            return new ReferenceProperty(Type.MANAGED_REFERENCE, name);
        }

        public static ReferenceProperty back(String name) {
            return new ReferenceProperty(Type.BACK_REFERENCE, name);
        }

        public Type getType() {
            return this._type;
        }

        public String getName() {
            return this._name;
        }

        public boolean isManagedReference() {
            return this._type == Type.MANAGED_REFERENCE;
        }

        public boolean isBackReference() {
            return this._type == Type.BACK_REFERENCE;
        }
    }

    public static AnnotationIntrospector nopInstance() {
        return NopAnnotationIntrospector.instance;
    }

    public static AnnotationIntrospector pair(AnnotationIntrospector a1, AnnotationIntrospector a2) {
        return new Pair(a1, a2);
    }

    public Collection<AnnotationIntrospector> allIntrospectors() {
        return Collections.singletonList(this);
    }

    public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> result) {
        result.add(this);
        return result;
    }

    public boolean isHandled(Annotation ann) {
        return false;
    }

    public boolean isAnnotationBundle(Annotation ann) {
        return false;
    }

    public ObjectIdInfo findObjectIdInfo(Annotated ann) {
        return null;
    }

    public String findRootName(AnnotatedClass ac) {
        return null;
    }

    public String[] findPropertiesToIgnore(Annotated ac) {
        return null;
    }

    public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
        return null;
    }

    public Boolean isIgnorableType(AnnotatedClass ac) {
        return null;
    }

    public Object findFilterId(AnnotatedClass ac) {
        return null;
    }

    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
        return checker;
    }

    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
        return null;
    }

    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
        return null;
    }

    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
        return null;
    }

    public List<NamedType> findSubtypes(Annotated a) {
        return null;
    }

    public String findTypeName(AnnotatedClass ac) {
        return null;
    }

    public ReferenceProperty findReferenceType(AnnotatedMember member) {
        return null;
    }

    public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
        return null;
    }

    public boolean hasIgnoreMarker(AnnotatedMember m) {
        return false;
    }

    public Object findInjectableValueId(AnnotatedMember m) {
        return null;
    }

    public Boolean hasRequiredMarker(AnnotatedMember m) {
        return null;
    }

    public Class<?>[] findViews(Annotated a) {
        return null;
    }

    public JsonFormat.Value findFormat(AnnotatedMember member) {
        return null;
    }

    public Boolean isTypeId(AnnotatedMember member) {
        return null;
    }

    public Object findSerializer(Annotated am) {
        return null;
    }

    public Object findKeySerializer(Annotated am) {
        return null;
    }

    public Object findContentSerializer(Annotated am) {
        return null;
    }

    public JsonInclude.Include findSerializationInclusion(Annotated a, JsonInclude.Include defValue) {
        return defValue;
    }

    public Class<?> findSerializationType(Annotated a) {
        return null;
    }

    public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
        return null;
    }

    public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
        return null;
    }

    public JsonSerialize.Typing findSerializationTyping(Annotated a) {
        return null;
    }

    public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
        return null;
    }

    public Boolean findSerializationSortAlphabetically(AnnotatedClass ac) {
        return null;
    }

    public String findSerializationName(AnnotatedMethod am) {
        return null;
    }

    public boolean hasAsValueAnnotation(AnnotatedMethod am) {
        return false;
    }

    public String findEnumValue(Enum<?> value) {
        return null;
    }

    public String findSerializationName(AnnotatedField af) {
        return null;
    }

    public Object findDeserializer(Annotated am) {
        return null;
    }

    public Object findKeyDeserializer(Annotated am) {
        return null;
    }

    public Object findContentDeserializer(Annotated am) {
        return null;
    }

    public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
        return null;
    }

    public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
        return null;
    }

    public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
        return null;
    }

    public Object findValueInstantiator(AnnotatedClass ac) {
        return null;
    }

    public Class<?> findPOJOBuilder(AnnotatedClass ac) {
        return null;
    }

    public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
        return null;
    }

    public String findDeserializationName(AnnotatedMethod am) {
        return null;
    }

    public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
        return false;
    }

    public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
        return false;
    }

    public boolean hasCreatorAnnotation(Annotated a) {
        return false;
    }

    public String findDeserializationName(AnnotatedField af) {
        return null;
    }

    public String findDeserializationName(AnnotatedParameter param) {
        return null;
    }

    public static class Pair extends AnnotationIntrospector {
        protected final AnnotationIntrospector _primary;
        protected final AnnotationIntrospector _secondary;

        public Pair(AnnotationIntrospector p, AnnotationIntrospector s) {
            this._primary = p;
            this._secondary = s;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector, com.fasterxml.jackson.core.Versioned
        public Version version() {
            return this._primary.version();
        }

        public static AnnotationIntrospector create(AnnotationIntrospector primary, AnnotationIntrospector secondary) {
            if (primary == null) {
                return secondary;
            }
            return secondary == null ? primary : new Pair(primary, secondary);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Collection<AnnotationIntrospector> allIntrospectors() {
            return allIntrospectors(new ArrayList());
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Collection<AnnotationIntrospector> allIntrospectors(Collection<AnnotationIntrospector> result) {
            this._primary.allIntrospectors(result);
            this._secondary.allIntrospectors(result);
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean isHandled(Annotation ann) {
            return this._primary.isHandled(ann) || this._secondary.isHandled(ann);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean isAnnotationBundle(Annotation ann) {
            return this._primary.isAnnotationBundle(ann) || this._secondary.isAnnotationBundle(ann);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findRootName(AnnotatedClass ac) {
            String name2;
            String name1 = this._primary.findRootName(ac);
            if (name1 == null) {
                return this._secondary.findRootName(ac);
            }
            return (name1.length() > 0 || (name2 = this._secondary.findRootName(ac)) == null) ? name1 : name2;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String[] findPropertiesToIgnore(Annotated ac) {
            String[] result = this._primary.findPropertiesToIgnore(ac);
            if (result == null) {
                return this._secondary.findPropertiesToIgnore(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Boolean findIgnoreUnknownProperties(AnnotatedClass ac) {
            Boolean result = this._primary.findIgnoreUnknownProperties(ac);
            if (result == null) {
                return this._secondary.findIgnoreUnknownProperties(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Boolean isIgnorableType(AnnotatedClass ac) {
            Boolean result = this._primary.isIgnorableType(ac);
            if (result == null) {
                return this._secondary.isIgnorableType(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findFilterId(AnnotatedClass ac) {
            Object id = this._primary.findFilterId(ac);
            if (id == null) {
                return this._secondary.findFilterId(ac);
            }
            return id;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
            return this._primary.findAutoDetectVisibility(ac, this._secondary.findAutoDetectVisibility(ac, checker));
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
            TypeResolverBuilder<?> b = this._primary.findTypeResolver(config, ac, baseType);
            if (b == null) {
                return this._secondary.findTypeResolver(config, ac, baseType);
            }
            return b;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
            TypeResolverBuilder<?> b = this._primary.findPropertyTypeResolver(config, am, baseType);
            if (b == null) {
                return this._secondary.findPropertyTypeResolver(config, am, baseType);
            }
            return b;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
            TypeResolverBuilder<?> b = this._primary.findPropertyContentTypeResolver(config, am, baseType);
            if (b == null) {
                return this._secondary.findPropertyContentTypeResolver(config, am, baseType);
            }
            return b;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public List<NamedType> findSubtypes(Annotated a) {
            List<NamedType> types1 = this._primary.findSubtypes(a);
            List<NamedType> types2 = this._secondary.findSubtypes(a);
            if (types1 == null || types1.isEmpty()) {
                return types2;
            }
            if (types2 == null || types2.isEmpty()) {
                return types1;
            }
            ArrayList<NamedType> result = new ArrayList<>(types1.size() + types2.size());
            result.addAll(types1);
            result.addAll(types2);
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findTypeName(AnnotatedClass ac) {
            String name = this._primary.findTypeName(ac);
            if (name == null || name.length() == 0) {
                return this._secondary.findTypeName(ac);
            }
            return name;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public ReferenceProperty findReferenceType(AnnotatedMember member) {
            ReferenceProperty ref = this._primary.findReferenceType(member);
            if (ref == null) {
                return this._secondary.findReferenceType(member);
            }
            return ref;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public NameTransformer findUnwrappingNameTransformer(AnnotatedMember member) {
            NameTransformer value = this._primary.findUnwrappingNameTransformer(member);
            if (value == null) {
                return this._secondary.findUnwrappingNameTransformer(member);
            }
            return value;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findInjectableValueId(AnnotatedMember m) {
            Object value = this._primary.findInjectableValueId(m);
            if (value == null) {
                return this._secondary.findInjectableValueId(m);
            }
            return value;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean hasIgnoreMarker(AnnotatedMember m) {
            return this._primary.hasIgnoreMarker(m) || this._secondary.hasIgnoreMarker(m);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Boolean hasRequiredMarker(AnnotatedMember m) {
            Boolean value = this._primary.hasRequiredMarker(m);
            if (value == null) {
                return this._secondary.hasRequiredMarker(m);
            }
            return value;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findSerializer(Annotated am) {
            Object result = this._primary.findSerializer(am);
            if (result == null) {
                return this._secondary.findSerializer(am);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findKeySerializer(Annotated a) {
            Object result = this._primary.findKeySerializer(a);
            if (result == null || result == JsonSerializer.None.class || result == NoClass.class) {
                return this._secondary.findKeySerializer(a);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findContentSerializer(Annotated a) {
            Object result = this._primary.findContentSerializer(a);
            if (result == null || result == JsonSerializer.None.class || result == NoClass.class) {
                return this._secondary.findContentSerializer(a);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public JsonInclude.Include findSerializationInclusion(Annotated a, JsonInclude.Include defValue) {
            return this._primary.findSerializationInclusion(a, this._secondary.findSerializationInclusion(a, defValue));
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findSerializationType(Annotated a) {
            Class<?> result = this._primary.findSerializationType(a);
            if (result == null) {
                return this._secondary.findSerializationType(a);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findSerializationKeyType(Annotated am, JavaType baseType) {
            Class<?> result = this._primary.findSerializationKeyType(am, baseType);
            if (result == null) {
                return this._secondary.findSerializationKeyType(am, baseType);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findSerializationContentType(Annotated am, JavaType baseType) {
            Class<?> result = this._primary.findSerializationContentType(am, baseType);
            if (result == null) {
                return this._secondary.findSerializationContentType(am, baseType);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public JsonSerialize.Typing findSerializationTyping(Annotated a) {
            JsonSerialize.Typing result = this._primary.findSerializationTyping(a);
            if (result == null) {
                return this._secondary.findSerializationTyping(a);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?>[] findViews(Annotated a) {
            Class<?>[] result = this._primary.findViews(a);
            if (result == null) {
                return this._secondary.findViews(a);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Boolean isTypeId(AnnotatedMember member) {
            Boolean b = this._primary.isTypeId(member);
            if (b == null) {
                return this._secondary.isTypeId(member);
            }
            return b;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public ObjectIdInfo findObjectIdInfo(Annotated ann) {
            ObjectIdInfo result = this._primary.findObjectIdInfo(ann);
            if (result == null) {
                return this._secondary.findObjectIdInfo(ann);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public JsonFormat.Value findFormat(AnnotatedMember member) {
            JsonFormat.Value result = this._primary.findFormat(member);
            if (result == null) {
                return this._secondary.findFormat(member);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
            String[] result = this._primary.findSerializationPropertyOrder(ac);
            if (result == null) {
                return this._secondary.findSerializationPropertyOrder(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Boolean findSerializationSortAlphabetically(AnnotatedClass ac) {
            Boolean result = this._primary.findSerializationSortAlphabetically(ac);
            if (result == null) {
                return this._secondary.findSerializationSortAlphabetically(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findSerializationName(AnnotatedMethod am) {
            String str2;
            String result = this._primary.findSerializationName(am);
            if (result == null) {
                return this._secondary.findSerializationName(am);
            }
            if (result.length() == 0 && (str2 = this._secondary.findSerializationName(am)) != null) {
                return str2;
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean hasAsValueAnnotation(AnnotatedMethod am) {
            return this._primary.hasAsValueAnnotation(am) || this._secondary.hasAsValueAnnotation(am);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findEnumValue(Enum<?> value) {
            String result = this._primary.findEnumValue(value);
            if (result == null) {
                return this._secondary.findEnumValue(value);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findSerializationName(AnnotatedField af) {
            String str2;
            String result = this._primary.findSerializationName(af);
            if (result == null) {
                return this._secondary.findSerializationName(af);
            }
            if (result.length() == 0 && (str2 = this._secondary.findSerializationName(af)) != null) {
                return str2;
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findDeserializer(Annotated am) {
            Object result = this._primary.findDeserializer(am);
            if (result == null) {
                return this._secondary.findDeserializer(am);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findKeyDeserializer(Annotated am) {
            Object result = this._primary.findKeyDeserializer(am);
            if (result == null || result == KeyDeserializer.None.class || result == NoClass.class) {
                return this._secondary.findKeyDeserializer(am);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findContentDeserializer(Annotated am) {
            Object result = this._primary.findContentDeserializer(am);
            if (result == null || result == JsonDeserializer.None.class || result == NoClass.class) {
                return this._secondary.findContentDeserializer(am);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findDeserializationType(Annotated am, JavaType baseType) {
            Class<?> result = this._primary.findDeserializationType(am, baseType);
            if (result == null) {
                return this._secondary.findDeserializationType(am, baseType);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findDeserializationKeyType(Annotated am, JavaType baseKeyType) {
            Class<?> result = this._primary.findDeserializationKeyType(am, baseKeyType);
            if (result == null) {
                return this._secondary.findDeserializationKeyType(am, baseKeyType);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findDeserializationContentType(Annotated am, JavaType baseContentType) {
            Class<?> result = this._primary.findDeserializationContentType(am, baseContentType);
            if (result == null) {
                return this._secondary.findDeserializationContentType(am, baseContentType);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Object findValueInstantiator(AnnotatedClass ac) {
            Object result = this._primary.findValueInstantiator(ac);
            if (result == null) {
                return this._secondary.findValueInstantiator(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public Class<?> findPOJOBuilder(AnnotatedClass ac) {
            Class<?> result = this._primary.findPOJOBuilder(ac);
            if (result == null) {
                return this._secondary.findPOJOBuilder(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public JsonPOJOBuilder.Value findPOJOBuilderConfig(AnnotatedClass ac) {
            JsonPOJOBuilder.Value result = this._primary.findPOJOBuilderConfig(ac);
            if (result == null) {
                return this._secondary.findPOJOBuilderConfig(ac);
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findDeserializationName(AnnotatedMethod am) {
            String str2;
            String result = this._primary.findDeserializationName(am);
            if (result == null) {
                return this._secondary.findDeserializationName(am);
            }
            if (result.length() == 0 && (str2 = this._secondary.findDeserializationName(am)) != null) {
                return str2;
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean hasAnySetterAnnotation(AnnotatedMethod am) {
            return this._primary.hasAnySetterAnnotation(am) || this._secondary.hasAnySetterAnnotation(am);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean hasAnyGetterAnnotation(AnnotatedMethod am) {
            return this._primary.hasAnyGetterAnnotation(am) || this._secondary.hasAnyGetterAnnotation(am);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public boolean hasCreatorAnnotation(Annotated a) {
            return this._primary.hasCreatorAnnotation(a) || this._secondary.hasCreatorAnnotation(a);
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findDeserializationName(AnnotatedField af) {
            String str2;
            String result = this._primary.findDeserializationName(af);
            if (result == null) {
                return this._secondary.findDeserializationName(af);
            }
            if (result.length() == 0 && (str2 = this._secondary.findDeserializationName(af)) != null) {
                return str2;
            }
            return result;
        }

        @Override // com.fasterxml.jackson.databind.AnnotationIntrospector
        public String findDeserializationName(AnnotatedParameter param) {
            String result = this._primary.findDeserializationName(param);
            if (result == null) {
                return this._secondary.findDeserializationName(param);
            }
            return result;
        }
    }
}
