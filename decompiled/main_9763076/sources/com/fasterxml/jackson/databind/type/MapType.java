package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MapType extends MapLikeType {
    private MapType(Class<?> mapType, JavaType keyT, JavaType valueT, Object valueHandler, Object typeHandler) {
        super(mapType, keyT, valueT, valueHandler, typeHandler);
    }

    public static MapType construct(Class<?> rawType, JavaType keyT, JavaType valueT) {
        return new MapType(rawType, keyT, valueT, null, null);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    protected JavaType _narrow(Class<?> subclass) {
        return new MapType(subclass, this._keyType, this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public JavaType narrowContentsBy(Class<?> contentClass) {
        return contentClass == this._valueType.getRawClass() ? this : new MapType(this._class, this._keyType, this._valueType.narrowBy(contentClass), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public JavaType widenContentsBy(Class<?> contentClass) {
        return contentClass == this._valueType.getRawClass() ? this : new MapType(this._class, this._keyType, this._valueType.widenBy(contentClass), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType
    public JavaType narrowKey(Class<?> keySubclass) {
        return keySubclass == this._keyType.getRawClass() ? this : new MapType(this._class, this._keyType.narrowBy(keySubclass), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType
    public JavaType widenKey(Class<?> keySubclass) {
        return keySubclass == this._keyType.getRawClass() ? this : new MapType(this._class, this._keyType.widenBy(keySubclass), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public MapType withTypeHandler(Object h) {
        return new MapType(this._class, this._keyType, this._valueType, this._valueHandler, h);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public MapType withContentTypeHandler(Object h) {
        return new MapType(this._class, this._keyType, this._valueType.withTypeHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public MapType withValueHandler(Object h) {
        return new MapType(this._class, this._keyType, this._valueType, h, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public MapType withContentValueHandler(Object h) {
        return new MapType(this._class, this._keyType, this._valueType.withValueHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType
    public MapType withKeyTypeHandler(Object h) {
        return new MapType(this._class, this._keyType.withTypeHandler(h), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType
    public MapType withKeyValueHandler(Object h) {
        return new MapType(this._class, this._keyType.withValueHandler(h), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.MapLikeType, com.fasterxml.jackson.databind.JavaType
    public String toString() {
        return "[map type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
    }
}
