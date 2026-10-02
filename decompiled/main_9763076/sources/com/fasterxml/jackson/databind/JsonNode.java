package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JsonNode implements TreeNode, Iterable<JsonNode> {
    protected static final List<JsonNode> NO_NODES = Collections.emptyList();
    protected static final List<String> NO_STRINGS = Collections.emptyList();

    public abstract String asText();

    @Override // com.fasterxml.jackson.core.TreeNode
    public abstract JsonToken asToken();

    public abstract <T extends JsonNode> T deepCopy();

    public abstract boolean equals(Object obj);

    public abstract JsonNode findParent(String str);

    public abstract List<JsonNode> findParents(String str, List<JsonNode> list);

    public abstract JsonNode findPath(String str);

    public abstract JsonNode findValue(String str);

    public abstract List<JsonNode> findValues(String str, List<JsonNode> list);

    public abstract List<String> findValuesAsText(String str, List<String> list);

    @Override // com.fasterxml.jackson.core.TreeNode
    public abstract JsonParser.NumberType numberType();

    public abstract JsonNode path(int i);

    public abstract JsonNode path(String str);

    public abstract String toString();

    @Override // com.fasterxml.jackson.core.TreeNode
    public abstract JsonParser traverse();

    protected JsonNode() {
    }

    public boolean isValueNode() {
        return false;
    }

    public boolean isContainerNode() {
        return false;
    }

    public boolean isMissingNode() {
        return false;
    }

    public boolean isArray() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isPojo() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public boolean isIntegralNumber() {
        return false;
    }

    public boolean isFloatingPointNumber() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isLong() {
        return false;
    }

    public boolean isDouble() {
        return false;
    }

    public boolean isBigDecimal() {
        return false;
    }

    public boolean isBigInteger() {
        return false;
    }

    public boolean isTextual() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isBinary() {
        return false;
    }

    public boolean canConvertToInt() {
        return false;
    }

    public boolean canConvertToLong() {
        return false;
    }

    public String textValue() {
        return null;
    }

    public byte[] binaryValue() throws IOException {
        return null;
    }

    public boolean booleanValue() {
        return false;
    }

    public Number numberValue() {
        return null;
    }

    public int intValue() {
        return 0;
    }

    public long longValue() {
        return 0L;
    }

    public double doubleValue() {
        return 0.0d;
    }

    public BigDecimal decimalValue() {
        return BigDecimal.ZERO;
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.ZERO;
    }

    public JsonNode get(int index) {
        return null;
    }

    public JsonNode get(String fieldName) {
        return null;
    }

    public int asInt() {
        return asInt(0);
    }

    public int asInt(int defaultValue) {
        return defaultValue;
    }

    public long asLong() {
        return asLong(0L);
    }

    public long asLong(long defaultValue) {
        return defaultValue;
    }

    public double asDouble() {
        return asDouble(0.0d);
    }

    public double asDouble(double defaultValue) {
        return defaultValue;
    }

    public boolean asBoolean() {
        return asBoolean(false);
    }

    public boolean asBoolean(boolean defaultValue) {
        return defaultValue;
    }

    public boolean has(String fieldName) {
        return get(fieldName) != null;
    }

    public boolean has(int index) {
        return get(index) != null;
    }

    public final List<JsonNode> findValues(String fieldName) {
        List<JsonNode> result = findValues(fieldName, null);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    public final List<String> findValuesAsText(String fieldName) {
        List<String> result = findValuesAsText(fieldName, null);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    public final List<JsonNode> findParents(String fieldName) {
        List<JsonNode> result = findParents(fieldName, null);
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    public int size() {
        return 0;
    }

    @Override // java.lang.Iterable
    public final Iterator<JsonNode> iterator() {
        return elements();
    }

    public Iterator<JsonNode> elements() {
        return NO_NODES.iterator();
    }

    public Iterator<String> fieldNames() {
        return NO_STRINGS.iterator();
    }

    public Iterator<Map.Entry<String, JsonNode>> fields() {
        Collection<Map.Entry<String, JsonNode>> coll = Collections.emptyList();
        return coll.iterator();
    }

    public JsonNode with(String propertyName) {
        throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + getClass().getName() + "), can not call with() on it");
    }

    public JsonNode withArray(String propertyName) {
        throw new UnsupportedOperationException("JsonNode not of type ObjectNode (but " + getClass().getName() + "), can not call withArray() on it");
    }
}
