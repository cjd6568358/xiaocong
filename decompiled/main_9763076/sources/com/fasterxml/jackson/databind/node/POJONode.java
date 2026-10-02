package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class POJONode extends ValueNode {
    protected final Object _value;

    public POJONode(Object v) {
        this._value = v;
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.VALUE_EMBEDDED_OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isPojo() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public byte[] binaryValue() throws IOException {
        return this._value instanceof byte[] ? (byte[]) this._value : super.binaryValue();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return this._value == null ? "null" : this._value.toString();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean asBoolean(boolean defaultValue) {
        if (this._value != null && (this._value instanceof Boolean)) {
            return ((Boolean) this._value).booleanValue();
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public int asInt(int defaultValue) {
        if (this._value instanceof Number) {
            int defaultValue2 = ((Number) this._value).intValue();
            return defaultValue2;
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public long asLong(long defaultValue) {
        if (this._value instanceof Number) {
            long defaultValue2 = ((Number) this._value).longValue();
            return defaultValue2;
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public double asDouble(double defaultValue) {
        if (this._value instanceof Number) {
            double defaultValue2 = ((Number) this._value).doubleValue();
            return defaultValue2;
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        if (this._value == null) {
            jg.writeNull();
        } else {
            jg.writeObject(this._value);
        }
    }

    public Object getPojo() {
        return this._value;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o.getClass() == getClass()) {
            POJONode other = (POJONode) o;
            if (this._value == null) {
                return other._value == null;
            }
            return this._value.equals(other._value);
        }
        return false;
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        return String.valueOf(this._value);
    }
}
