package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseJsonNode extends JsonNode implements JsonSerializable {
    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public abstract JsonToken asToken();

    public abstract void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    public abstract void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException;

    protected BaseJsonNode() {
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode findValue(String fieldName) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public final JsonNode findPath(String fieldName) {
        JsonNode value = findValue(fieldName);
        if (value == null) {
            return MissingNode.getInstance();
        }
        return value;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode findParent(String fieldName) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonParser traverse() {
        return new TreeTraversingParser(this);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonParser.NumberType numberType() {
        return null;
    }
}
