package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.type.ResolvedType;
import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JavaType extends ResolvedType {
    protected final Class<?> _class;
    protected final int _hashCode;
    protected final Object _typeHandler;
    protected final Object _valueHandler;

    protected abstract JavaType _narrow(Class<?> cls);

    public abstract boolean equals(Object obj);

    public abstract StringBuilder getErasedSignature(StringBuilder sb);

    public abstract StringBuilder getGenericSignature(StringBuilder sb);

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public abstract boolean isContainerType();

    public abstract JavaType narrowContentsBy(Class<?> cls);

    public abstract String toString();

    public abstract JavaType widenContentsBy(Class<?> cls);

    public abstract JavaType withContentTypeHandler(Object obj);

    public abstract JavaType withContentValueHandler(Object obj);

    public abstract JavaType withTypeHandler(Object obj);

    public abstract JavaType withValueHandler(Object obj);

    protected JavaType(Class<?> raw, int additionalHash, Object valueHandler, Object typeHandler) {
        this._class = raw;
        this._hashCode = raw.getName().hashCode() + additionalHash;
        this._valueHandler = valueHandler;
        this._typeHandler = typeHandler;
    }

    public JavaType narrowBy(Class<?> subclass) {
        if (subclass != this._class) {
            _assertSubclass(subclass, this._class);
            JavaType result = _narrow(subclass);
            if (this._valueHandler != result.getValueHandler()) {
                result = result.withValueHandler(this._valueHandler);
            }
            if (this._typeHandler != result.getTypeHandler()) {
                result = result.withTypeHandler(this._typeHandler);
            }
            return result;
        }
        return this;
    }

    public JavaType forcedNarrowBy(Class<?> subclass) {
        if (subclass != this._class) {
            JavaType result = _narrow(subclass);
            if (this._valueHandler != result.getValueHandler()) {
                result = result.withValueHandler(this._valueHandler);
            }
            if (this._typeHandler != result.getTypeHandler()) {
                result = result.withTypeHandler(this._typeHandler);
            }
            return result;
        }
        return this;
    }

    public JavaType widenBy(Class<?> superclass) {
        if (superclass != this._class) {
            _assertSubclass(this._class, superclass);
            return _widen(superclass);
        }
        return this;
    }

    protected JavaType _widen(Class<?> superclass) {
        return _narrow(superclass);
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final Class<?> getRawClass() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean hasRawClass(Class<?> clz) {
        return this._class == clz;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isAbstract() {
        return Modifier.isAbstract(this._class.getModifiers());
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isConcrete() {
        int mod = this._class.getModifiers();
        return (mod & 1536) == 0 || this._class.isPrimitive();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isThrowable() {
        return Throwable.class.isAssignableFrom(this._class);
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isArrayType() {
        return false;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isEnumType() {
        return this._class.isEnum();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isInterface() {
        return this._class.isInterface();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isPrimitive() {
        return this._class.isPrimitive();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isFinal() {
        return Modifier.isFinal(this._class.getModifiers());
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isCollectionLikeType() {
        return false;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isMapLikeType() {
        return false;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean hasGenericTypes() {
        return containedTypeCount() > 0;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public JavaType getKeyType() {
        return null;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public JavaType getContentType() {
        return null;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public int containedTypeCount() {
        return 0;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public JavaType containedType(int index) {
        return null;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public String containedTypeName(int index) {
        return null;
    }

    public <T> T getValueHandler() {
        return (T) this._valueHandler;
    }

    public <T> T getTypeHandler() {
        return (T) this._typeHandler;
    }

    public String getGenericSignature() {
        StringBuilder sb = new StringBuilder(40);
        getGenericSignature(sb);
        return sb.toString();
    }

    public String getErasedSignature() {
        StringBuilder sb = new StringBuilder(40);
        getErasedSignature(sb);
        return sb.toString();
    }

    protected void _assertSubclass(Class<?> subclass, Class<?> superClass) {
        if (!this._class.isAssignableFrom(subclass)) {
            throw new IllegalArgumentException("Class " + subclass.getName() + " is not assignable to " + this._class.getName());
        }
    }

    public final int hashCode() {
        return this._hashCode;
    }
}
