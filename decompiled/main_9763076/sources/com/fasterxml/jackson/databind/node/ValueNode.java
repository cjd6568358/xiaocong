package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ValueNode extends BaseJsonNode {
    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public abstract JsonToken asToken();

    protected ValueNode() {
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public <T extends JsonNode> T deepCopy() {
        return this;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isValueNode() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jg, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForScalar(this, jg);
        serialize(jg, provider);
        typeSer.writeTypeSuffixForScalar(this, jg);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(String fieldName) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(int index) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        return asText();
    }
}
