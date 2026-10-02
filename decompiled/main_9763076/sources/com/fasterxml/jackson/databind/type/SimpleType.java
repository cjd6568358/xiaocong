package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SimpleType extends TypeBase {
    protected final String[] _typeNames;
    protected final JavaType[] _typeParameters;

    protected SimpleType(Class<?> cls) {
        this(cls, null, null, null, null);
    }

    protected SimpleType(Class<?> cls, String[] typeNames, JavaType[] typeParams, Object valueHandler, Object typeHandler) {
        super(cls, 0, valueHandler, typeHandler);
        if (typeNames == null || typeNames.length == 0) {
            this._typeNames = null;
            this._typeParameters = null;
        } else {
            this._typeNames = typeNames;
            this._typeParameters = typeParams;
        }
    }

    public static SimpleType constructUnsafe(Class<?> raw) {
        return new SimpleType(raw, null, null, null, null);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    protected JavaType _narrow(Class<?> subclass) {
        return new SimpleType(subclass, this._typeNames, this._typeParameters, this._valueHandler, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType narrowContentsBy(Class<?> subclass) {
        throw new IllegalArgumentException("Internal error: SimpleType.narrowContentsBy() should never be called");
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType widenContentsBy(Class<?> subclass) {
        throw new IllegalArgumentException("Internal error: SimpleType.widenContentsBy() should never be called");
    }

    public static SimpleType construct(Class<?> cls) {
        if (Map.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Can not construct SimpleType for a Map (class: " + cls.getName() + ")");
        }
        if (Collection.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Can not construct SimpleType for a Collection (class: " + cls.getName() + ")");
        }
        if (cls.isArray()) {
            throw new IllegalArgumentException("Can not construct SimpleType for an array (class: " + cls.getName() + ")");
        }
        return new SimpleType(cls);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withTypeHandler(Object h) {
        return new SimpleType(this._class, this._typeNames, this._typeParameters, this._valueHandler, h);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType withContentTypeHandler(Object h) {
        throw new IllegalArgumentException("Simple types have no content types; can not call withContenTypeHandler()");
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withValueHandler(Object h) {
        return h == this._valueHandler ? this : new SimpleType(this._class, this._typeNames, this._typeParameters, h, this._typeHandler);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public SimpleType withContentValueHandler(Object h) {
        throw new IllegalArgumentException("Simple types have no content types; can not call withContenValueHandler()");
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase
    protected String buildCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._class.getName());
        if (this._typeParameters != null && this._typeParameters.length > 0) {
            sb.append('<');
            boolean first = true;
            JavaType[] arr$ = this._typeParameters;
            for (JavaType t : arr$) {
                if (first) {
                    first = false;
                } else {
                    sb.append(',');
                }
                sb.append(t.toCanonical());
            }
            sb.append('>');
        }
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isContainerType() {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public int containedTypeCount() {
        if (this._typeParameters == null) {
            return 0;
        }
        return this._typeParameters.length;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public JavaType containedType(int index) {
        if (index < 0 || this._typeParameters == null || index >= this._typeParameters.length) {
            return null;
        }
        return this._typeParameters[index];
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public String containedTypeName(int index) {
        if (index < 0 || this._typeNames == null || index >= this._typeNames.length) {
            return null;
        }
        return this._typeNames[index];
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getErasedSignature(StringBuilder sb) {
        return _classSignature(this._class, sb, true);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getGenericSignature(StringBuilder sb) {
        _classSignature(this._class, sb, false);
        if (this._typeParameters != null) {
            sb.append('<');
            JavaType[] arr$ = this._typeParameters;
            for (JavaType param : arr$) {
                sb = param.getGenericSignature(sb);
            }
            sb.append('>');
        }
        sb.append(';');
        return sb;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public String toString() {
        StringBuilder sb = new StringBuilder(40);
        sb.append("[simple type, class ").append(buildCanonicalName()).append(']');
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        SimpleType other = (SimpleType) o;
        if (other._class != this._class) {
            return false;
        }
        JavaType[] p1 = this._typeParameters;
        JavaType[] p2 = other._typeParameters;
        if (p1 == null) {
            return p2 == null || p2.length == 0;
        }
        if (p2 == null || p1.length != p2.length) {
            return false;
        }
        int len = p1.length;
        for (int i = 0; i < len; i++) {
            if (!p1[i].equals(p2[i])) {
                return false;
            }
        }
        return true;
    }
}
