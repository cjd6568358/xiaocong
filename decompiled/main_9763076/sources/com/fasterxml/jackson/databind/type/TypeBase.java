package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class TypeBase extends JavaType implements JsonSerializable {
    volatile String _canonicalName;

    protected abstract String buildCanonicalName();

    @Override // com.fasterxml.jackson.databind.JavaType
    public abstract StringBuilder getErasedSignature(StringBuilder sb);

    @Override // com.fasterxml.jackson.databind.JavaType
    public abstract StringBuilder getGenericSignature(StringBuilder sb);

    protected TypeBase(Class<?> raw, int hash, Object valueHandler, Object typeHandler) {
        super(raw, hash, valueHandler, typeHandler);
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public String toCanonical() {
        String str = this._canonicalName;
        if (str == null) {
            return buildCanonicalName();
        }
        return str;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public <T> T getValueHandler() {
        return (T) this._valueHandler;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public <T> T getTypeHandler() {
        return (T) this._typeHandler;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForScalar(this, jgen);
        serialize(jgen, provider);
        typeSer.writeTypeSuffixForScalar(this, jgen);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(toCanonical());
    }

    protected static StringBuilder _classSignature(Class<?> cls, StringBuilder sb, boolean trailingSemicolon) {
        if (cls.isPrimitive()) {
            if (cls == Boolean.TYPE) {
                sb.append('Z');
            } else if (cls == Byte.TYPE) {
                sb.append('B');
            } else if (cls == Short.TYPE) {
                sb.append('S');
            } else if (cls == Character.TYPE) {
                sb.append('C');
            } else if (cls == Integer.TYPE) {
                sb.append('I');
            } else if (cls == Long.TYPE) {
                sb.append('J');
            } else if (cls == Float.TYPE) {
                sb.append('F');
            } else if (cls == Double.TYPE) {
                sb.append('D');
            } else if (cls == Void.TYPE) {
                sb.append('V');
            } else {
                throw new IllegalStateException("Unrecognized primitive type: " + cls.getName());
            }
        } else {
            sb.append('L');
            String name = cls.getName();
            int len = name.length();
            for (int i = 0; i < len; i++) {
                char c = name.charAt(i);
                if (c == '.') {
                    c = '/';
                }
                sb.append(c);
            }
            if (trailingSemicolon) {
                sb.append(';');
            }
        }
        return sb;
    }
}
