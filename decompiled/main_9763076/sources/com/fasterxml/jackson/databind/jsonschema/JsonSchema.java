package com.fasterxml.jackson.databind.jsonschema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JsonSchema {
    private final ObjectNode schema;

    @JsonCreator
    public JsonSchema(ObjectNode schema) {
        this.schema = schema;
    }

    @JsonValue
    public ObjectNode getSchemaNode() {
        return this.schema;
    }

    public String toString() {
        return this.schema.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && (o instanceof JsonSchema)) {
            JsonSchema other = (JsonSchema) o;
            if (this.schema == null) {
                return other.schema == null;
            }
            return this.schema.equals(other.schema);
        }
        return false;
    }

    public static JsonNode getDefaultSchemaNode() {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("type", "any");
        return objectNode;
    }
}
