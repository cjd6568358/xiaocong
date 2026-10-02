package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

/* JADX INFO: compiled from: JsonNodeDeserializer.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class BaseNodeDeserializer<N extends JsonNode> extends StdDeserializer<N> {
    public BaseNodeDeserializer(Class<N> nodeClass) {
        super((Class<?>) nodeClass);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
    }

    protected void _reportProblem(JsonParser jp, String msg) throws JsonMappingException {
        throw new JsonMappingException(msg, jp.getTokenLocation());
    }

    protected void _handleDuplicateField(String fieldName, ObjectNode objectNode, JsonNode oldValue, JsonNode newValue) throws JsonProcessingException {
    }

    protected final ObjectNode deserializeObject(JsonParser jp, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
        JsonNode value;
        ObjectNode node = nodeFactory.objectNode();
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        while (t == JsonToken.FIELD_NAME) {
            String fieldName = jp.getCurrentName();
            switch (jp.nextToken()) {
                case START_OBJECT:
                    value = deserializeObject(jp, ctxt, nodeFactory);
                    break;
                case START_ARRAY:
                    value = deserializeArray(jp, ctxt, nodeFactory);
                    break;
                case VALUE_STRING:
                    value = nodeFactory.textNode(jp.getText());
                    break;
                default:
                    value = deserializeAny(jp, ctxt, nodeFactory);
                    break;
            }
            JsonNode old = node.put(fieldName, value);
            if (old != null) {
                _handleDuplicateField(fieldName, node, old, value);
            }
            t = jp.nextToken();
        }
        return node;
    }

    protected final ArrayNode deserializeArray(JsonParser jp, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
        ArrayNode node = nodeFactory.arrayNode();
        while (true) {
            JsonToken t = jp.nextToken();
            if (t == null) {
                throw ctxt.mappingException("Unexpected end-of-input when binding data into ArrayNode");
            }
            switch (t) {
                case START_OBJECT:
                    node.add(deserializeObject(jp, ctxt, nodeFactory));
                    break;
                case START_ARRAY:
                    node.add(deserializeArray(jp, ctxt, nodeFactory));
                    break;
                case VALUE_STRING:
                    node.add(nodeFactory.textNode(jp.getText()));
                    break;
                case END_ARRAY:
                    return node;
                default:
                    node.add(deserializeAny(jp, ctxt, nodeFactory));
                    break;
            }
        }
    }

    protected final JsonNode deserializeAny(JsonParser jp, DeserializationContext ctxt, JsonNodeFactory nodeFactory) throws IOException {
        switch (jp.getCurrentToken()) {
            case START_OBJECT:
                return deserializeObject(jp, ctxt, nodeFactory);
            case START_ARRAY:
                return deserializeArray(jp, ctxt, nodeFactory);
            case VALUE_STRING:
                return nodeFactory.textNode(jp.getText());
            case END_ARRAY:
            default:
                throw ctxt.mappingException(getValueClass());
            case FIELD_NAME:
                return deserializeObject(jp, ctxt, nodeFactory);
            case VALUE_EMBEDDED_OBJECT:
                Object ob = jp.getEmbeddedObject();
                if (ob == null) {
                    return nodeFactory.nullNode();
                }
                Class<?> type = ob.getClass();
                if (type == byte[].class) {
                    return nodeFactory.binaryNode((byte[]) ob);
                }
                return nodeFactory.POJONode(ob);
            case VALUE_NUMBER_INT:
                JsonParser.NumberType nt = jp.getNumberType();
                if (nt == JsonParser.NumberType.BIG_INTEGER || ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return nodeFactory.numberNode(jp.getBigIntegerValue());
                }
                if (nt == JsonParser.NumberType.INT) {
                    return nodeFactory.numberNode(jp.getIntValue());
                }
                return nodeFactory.numberNode(jp.getLongValue());
            case VALUE_NUMBER_FLOAT:
                if (jp.getNumberType() == JsonParser.NumberType.BIG_DECIMAL || ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return nodeFactory.numberNode(jp.getDecimalValue());
                }
                return nodeFactory.numberNode(jp.getDoubleValue());
            case VALUE_TRUE:
                return nodeFactory.booleanNode(true);
            case VALUE_FALSE:
                return nodeFactory.booleanNode(false);
            case VALUE_NULL:
                return nodeFactory.nullNode();
        }
    }
}
