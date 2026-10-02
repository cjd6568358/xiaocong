package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonParser;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class NumericNode extends ValueNode {
    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract String asText();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract BigInteger bigIntegerValue();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract boolean canConvertToInt();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract boolean canConvertToLong();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract BigDecimal decimalValue();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract double doubleValue();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract int intValue();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract long longValue();

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public abstract JsonParser.NumberType numberType();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract Number numberValue();

    protected NumericNode() {
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public final boolean isNumber() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public int asInt() {
        return intValue();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public int asInt(int defaultValue) {
        return intValue();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public long asLong() {
        return longValue();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public long asLong(long defaultValue) {
        return longValue();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public double asDouble() {
        return doubleValue();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public double asDouble(double defaultValue) {
        return doubleValue();
    }
}
