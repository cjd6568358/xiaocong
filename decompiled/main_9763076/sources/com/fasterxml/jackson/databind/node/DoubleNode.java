package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberOutput;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class DoubleNode extends NumericNode {
    protected final double _value;

    public DoubleNode(double v) {
        this._value = v;
    }

    public static DoubleNode valueOf(double v) {
        return new DoubleNode(v);
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonParser.NumberType numberType() {
        return JsonParser.NumberType.DOUBLE;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isFloatingPointNumber() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isDouble() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public boolean canConvertToInt() {
        return this._value >= -2.147483648E9d && this._value <= 2.147483647E9d;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public boolean canConvertToLong() {
        return this._value >= -9.223372036854776E18d && this._value <= 9.223372036854776E18d;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public Number numberValue() {
        return Double.valueOf(this._value);
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public int intValue() {
        return (int) this._value;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public long longValue() {
        return (long) this._value;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public double doubleValue() {
        return this._value;
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public BigDecimal decimalValue() {
        return BigDecimal.valueOf(this._value);
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public BigInteger bigIntegerValue() {
        return decimalValue().toBigInteger();
    }

    @Override // com.fasterxml.jackson.databind.node.NumericNode, com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return NumberOutput.toString(this._value);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeNumber(this._value);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o != null && o.getClass() == getClass() && ((DoubleNode) o)._value == this._value;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this._value);
        return ((int) l) ^ ((int) (l >> 32));
    }
}
