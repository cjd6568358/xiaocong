package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdTypeResolverBuilder implements TypeResolverBuilder<StdTypeResolverBuilder> {
    protected TypeIdResolver _customIdResolver;
    protected Class<?> _defaultImpl;
    protected JsonTypeInfo.Id _idType;
    protected JsonTypeInfo.As _includeAs;
    protected boolean _typeIdVisible = false;
    protected String _typeProperty;

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public /* bridge */ /* synthetic */ TypeResolverBuilder defaultImpl(Class x0) {
        return defaultImpl((Class<?>) x0);
    }

    public static StdTypeResolverBuilder noTypeInfoBuilder() {
        return new StdTypeResolverBuilder().init(JsonTypeInfo.Id.NONE, (TypeIdResolver) null);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder init(JsonTypeInfo.Id idType, TypeIdResolver idRes) {
        if (idType == null) {
            throw new IllegalArgumentException("idType can not be null");
        }
        this._idType = idType;
        this._customIdResolver = idRes;
        this._typeProperty = idType.getDefaultPropertyName();
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public TypeSerializer buildTypeSerializer(SerializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        TypeIdResolver idRes = idResolver(config, baseType, subtypes, true, false);
        switch (this._includeAs) {
            case WRAPPER_ARRAY:
                return new AsArrayTypeSerializer(idRes, null);
            case PROPERTY:
                return new AsPropertyTypeSerializer(idRes, null, this._typeProperty);
            case WRAPPER_OBJECT:
                return new AsWrapperTypeSerializer(idRes, null);
            case EXTERNAL_PROPERTY:
                return new AsExternalTypeSerializer(idRes, null, this._typeProperty);
            default:
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType, Collection<NamedType> subtypes) {
        if (this._idType == JsonTypeInfo.Id.NONE) {
            return null;
        }
        TypeIdResolver idRes = idResolver(config, baseType, subtypes, false, true);
        switch (this._includeAs) {
            case WRAPPER_ARRAY:
                return new AsArrayTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            case PROPERTY:
                return new AsPropertyTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            case WRAPPER_OBJECT:
                return new AsWrapperTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            case EXTERNAL_PROPERTY:
                return new AsExternalTypeDeserializer(baseType, idRes, this._typeProperty, this._typeIdVisible, this._defaultImpl);
            default:
                throw new IllegalStateException("Do not know how to construct standard type serializer for inclusion type: " + this._includeAs);
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder inclusion(JsonTypeInfo.As includeAs) {
        if (includeAs == null) {
            throw new IllegalArgumentException("includeAs can not be null");
        }
        this._includeAs = includeAs;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder typeProperty(String typeIdPropName) {
        if (typeIdPropName == null || typeIdPropName.length() == 0) {
            typeIdPropName = this._idType.getDefaultPropertyName();
        }
        this._typeProperty = typeIdPropName;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder defaultImpl(Class<?> defaultImpl) {
        this._defaultImpl = defaultImpl;
        return this;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public StdTypeResolverBuilder typeIdVisibility(boolean isVisible) {
        this._typeIdVisible = isVisible;
        return this;
    }

    public String getTypeProperty() {
        return this._typeProperty;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder
    public Class<?> getDefaultImpl() {
        return this._defaultImpl;
    }

    public boolean isTypeIdVisible() {
        return this._typeIdVisible;
    }

    protected TypeIdResolver idResolver(MapperConfig<?> config, JavaType baseType, Collection<NamedType> subtypes, boolean forSer, boolean forDeser) {
        if (this._customIdResolver != null) {
            return this._customIdResolver;
        }
        if (this._idType == null) {
            throw new IllegalStateException("Can not build, 'init()' not yet called");
        }
        switch (this._idType) {
            case CLASS:
                return new ClassNameIdResolver(baseType, config.getTypeFactory());
            case MINIMAL_CLASS:
                return new MinimalClassNameIdResolver(baseType, config.getTypeFactory());
            case NAME:
                return TypeNameIdResolver.construct(config, baseType, subtypes, forSer, forDeser);
            case NONE:
                return null;
            default:
                throw new IllegalStateException("Do not know how to construct standard type id resolver for idType: " + this._idType);
        }
    }
}
