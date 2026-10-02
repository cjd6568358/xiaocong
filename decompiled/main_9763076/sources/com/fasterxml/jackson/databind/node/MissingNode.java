package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MissingNode extends BaseJsonNode {
    private static final MissingNode instance = new MissingNode();

    private MissingNode() {
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public <T extends JsonNode> T deepCopy() {
        return this;
    }

    public static MissingNode getInstance() {
        return instance;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.NOT_AVAILABLE;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isMissingNode() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return Constants.MAIN_VERSION_TAG;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(String fieldName) {
        return this;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(int index) {
        return this;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeNull();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jg, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        jg.writeNull();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        return o == this;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        return Constants.MAIN_VERSION_TAG;
    }
}
