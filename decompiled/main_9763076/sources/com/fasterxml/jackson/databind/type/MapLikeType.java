package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MapLikeType extends TypeBase {
    protected final JavaType _keyType;
    protected final JavaType _valueType;

    protected MapLikeType(Class<?> mapType, JavaType keyT, JavaType valueT, Object valueHandler, Object typeHandler) {
        super(mapType, keyT.hashCode() ^ valueT.hashCode(), valueHandler, typeHandler);
        this._keyType = keyT;
        this._valueType = valueT;
    }

    public static MapLikeType construct(Class<?> rawType, JavaType keyT, JavaType valueT) {
        return new MapLikeType(rawType, keyT, valueT, null, null);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    protected JavaType _narrow(Class<?> subclass) {
        return new MapLikeType(subclass, this._keyType, this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType narrowContentsBy(Class<?> contentClass) {
        return contentClass == this._valueType.getRawClass() ? this : new MapLikeType(this._class, this._keyType, this._valueType.narrowBy(contentClass), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType widenContentsBy(Class<?> contentClass) {
        return contentClass == this._valueType.getRawClass() ? this : new MapLikeType(this._class, this._keyType, this._valueType.widenBy(contentClass), this._valueHandler, this._typeHandler);
    }

    public JavaType narrowKey(Class<?> keySubclass) {
        return keySubclass == this._keyType.getRawClass() ? this : new MapLikeType(this._class, this._keyType.narrowBy(keySubclass), this._valueType, this._valueHandler, this._typeHandler);
    }

    public JavaType widenKey(Class<?> keySubclass) {
        return keySubclass == this._keyType.getRawClass() ? this : new MapLikeType(this._class, this._keyType.widenBy(keySubclass), this._valueType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public MapLikeType withTypeHandler(Object h) {
        return new MapLikeType(this._class, this._keyType, this._valueType, this._valueHandler, h);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public MapLikeType withContentTypeHandler(Object h) {
        return new MapLikeType(this._class, this._keyType, this._valueType.withTypeHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public MapLikeType withValueHandler(Object h) {
        return new MapLikeType(this._class, this._keyType, this._valueType, h, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public MapLikeType withContentValueHandler(Object h) {
        return new MapLikeType(this._class, this._keyType, this._valueType.withValueHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase
    protected String buildCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._class.getName());
        if (this._keyType != null) {
            sb.append('<');
            sb.append(this._keyType.toCanonical());
            sb.append(',');
            sb.append(this._valueType.toCanonical());
            sb.append('>');
        }
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isContainerType() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isMapLikeType() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType getKeyType() {
        return this._keyType;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType getContentType() {
        return this._valueType;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public int containedTypeCount() {
        return 2;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType containedType(int index) {
        if (index == 0) {
            return this._keyType;
        }
        if (index == 1) {
            return this._valueType;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public String containedTypeName(int index) {
        if (index == 0) {
            return "K";
        }
        if (index == 1) {
            return "V";
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getErasedSignature(StringBuilder sb) {
        return _classSignature(this._class, sb, true);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getGenericSignature(StringBuilder sb) {
        _classSignature(this._class, sb, false);
        sb.append('<');
        this._keyType.getGenericSignature(sb);
        this._valueType.getGenericSignature(sb);
        sb.append(">;");
        return sb;
    }

    public MapLikeType withKeyTypeHandler(Object h) {
        return new MapLikeType(this._class, this._keyType.withTypeHandler(h), this._valueType, this._valueHandler, this._typeHandler);
    }

    public MapLikeType withKeyValueHandler(Object h) {
        return new MapLikeType(this._class, this._keyType.withValueHandler(h), this._valueType, this._valueHandler, this._typeHandler);
    }

    public boolean isTrueMapType() {
        return Map.class.isAssignableFrom(this._class);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public String toString() {
        return "[map-like type; class " + this._class.getName() + ", " + this._keyType + " -> " + this._valueType + "]";
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o.getClass() == getClass()) {
            MapLikeType other = (MapLikeType) o;
            return this._class == other._class && this._keyType.equals(other._keyType) && this._valueType.equals(other._valueType);
        }
        return false;
    }
}
