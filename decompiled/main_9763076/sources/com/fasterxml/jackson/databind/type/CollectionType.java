package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class CollectionType extends CollectionLikeType {
    private CollectionType(Class<?> collT, JavaType elemT, Object valueHandler, Object typeHandler) {
        super(collT, elemT, valueHandler, typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    protected JavaType _narrow(Class<?> subclass) {
        return new CollectionType(subclass, this._elementType, null, null);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public JavaType narrowContentsBy(Class<?> contentClass) {
        return contentClass == this._elementType.getRawClass() ? this : new CollectionType(this._class, this._elementType.narrowBy(contentClass), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public JavaType widenContentsBy(Class<?> contentClass) {
        return contentClass == this._elementType.getRawClass() ? this : new CollectionType(this._class, this._elementType.widenBy(contentClass), this._valueHandler, this._typeHandler);
    }

    public static CollectionType construct(Class<?> rawType, JavaType elemT) {
        return new CollectionType(rawType, elemT, null, null);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public CollectionType withTypeHandler(Object h) {
        return new CollectionType(this._class, this._elementType, this._valueHandler, h);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public CollectionType withContentTypeHandler(Object h) {
        return new CollectionType(this._class, this._elementType.withTypeHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public CollectionType withValueHandler(Object h) {
        return new CollectionType(this._class, this._elementType, h, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public CollectionType withContentValueHandler(Object h) {
        return new CollectionType(this._class, this._elementType.withValueHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.CollectionLikeType, com.fasterxml.jackson.databind.JavaType
    public String toString() {
        return "[collection type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }
}
