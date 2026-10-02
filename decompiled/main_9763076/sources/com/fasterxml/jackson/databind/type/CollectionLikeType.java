package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CollectionLikeType extends TypeBase {
    protected final JavaType _elementType;

    protected CollectionLikeType(Class<?> collT, JavaType elemT, Object valueHandler, Object typeHandler) {
        super(collT, elemT.hashCode(), valueHandler, typeHandler);
        this._elementType = elemT;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    protected JavaType _narrow(Class<?> subclass) {
        return new CollectionLikeType(subclass, this._elementType, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType narrowContentsBy(Class<?> contentClass) {
        return contentClass == this._elementType.getRawClass() ? this : new CollectionLikeType(this._class, this._elementType.narrowBy(contentClass), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType widenContentsBy(Class<?> contentClass) {
        return contentClass == this._elementType.getRawClass() ? this : new CollectionLikeType(this._class, this._elementType.widenBy(contentClass), this._valueHandler, this._typeHandler);
    }

    public static CollectionLikeType construct(Class<?> rawType, JavaType elemT) {
        return new CollectionLikeType(rawType, elemT, null, null);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public CollectionLikeType withTypeHandler(Object h) {
        return new CollectionLikeType(this._class, this._elementType, this._valueHandler, h);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public CollectionLikeType withContentTypeHandler(Object h) {
        return new CollectionLikeType(this._class, this._elementType.withTypeHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public CollectionLikeType withValueHandler(Object h) {
        return new CollectionLikeType(this._class, this._elementType, h, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public CollectionLikeType withContentValueHandler(Object h) {
        return new CollectionLikeType(this._class, this._elementType.withValueHandler(h), this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isContainerType() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isCollectionLikeType() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType getContentType() {
        return this._elementType;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public int containedTypeCount() {
        return 1;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType containedType(int index) {
        if (index == 0) {
            return this._elementType;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public String containedTypeName(int index) {
        if (index == 0) {
            return "E";
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
        this._elementType.getGenericSignature(sb);
        sb.append(">;");
        return sb;
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase
    protected String buildCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._class.getName());
        if (this._elementType != null) {
            sb.append('<');
            sb.append(this._elementType.toCanonical());
            sb.append('>');
        }
        return sb.toString();
    }

    public boolean isTrueCollectionType() {
        return Collection.class.isAssignableFrom(this._class);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o.getClass() == getClass()) {
            CollectionLikeType other = (CollectionLikeType) o;
            return this._class == other._class && this._elementType.equals(other._elementType);
        }
        return false;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public String toString() {
        return "[collection-like type; class " + this._class.getName() + ", contains " + this._elementType + "]";
    }
}
