package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.type.TypeFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeIdResolverBase implements TypeIdResolver {
    protected final JavaType _baseType;
    protected final TypeFactory _typeFactory;

    protected TypeIdResolverBase(JavaType baseType, TypeFactory typeFactory) {
        this._baseType = baseType;
        this._typeFactory = typeFactory;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public void init(JavaType bt) {
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeIdResolver
    public String idFromBaseType() {
        return idFromValueAndType(null, this._baseType.getRawClass());
    }
}
