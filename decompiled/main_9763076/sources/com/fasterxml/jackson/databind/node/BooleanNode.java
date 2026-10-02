package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tencent.bugly.Bugly;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BooleanNode extends ValueNode {
    public static final BooleanNode TRUE = new BooleanNode();
    public static final BooleanNode FALSE = new BooleanNode();

    private BooleanNode() {
    }

    public static BooleanNode getTrue() {
        return TRUE;
    }

    public static BooleanNode getFalse() {
        return FALSE;
    }

    public static BooleanNode valueOf(boolean b) {
        return b ? TRUE : FALSE;
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return this == TRUE ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isBoolean() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean booleanValue() {
        return this == TRUE;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return this == TRUE ? "true" : Bugly.SDK_IS_DEV;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean asBoolean() {
        return this == TRUE;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean asBoolean(boolean defaultValue) {
        return this == TRUE;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public int asInt(int defaultValue) {
        return this == TRUE ? 1 : 0;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public long asLong(long defaultValue) {
        return this == TRUE ? 1L : 0L;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public double asDouble(double defaultValue) {
        return this == TRUE ? 1.0d : 0.0d;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeBoolean(this == TRUE);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        return o == this;
    }
}
