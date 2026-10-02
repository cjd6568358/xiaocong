package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectNode extends ContainerNode<ObjectNode> {
    protected LinkedHashMap<String, JsonNode> _children;

    public ObjectNode(JsonNodeFactory nc) {
        super(nc);
        this._children = null;
    }

    protected ObjectNode(JsonNodeFactory nc, LinkedHashMap<String, JsonNode> children) {
        super(nc);
        this._children = null;
        this._children = children;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode deepCopy() {
        if (this._children == null) {
            return new ObjectNode(this._nodeFactory);
        }
        int len = this._children.size();
        LinkedHashMap<String, JsonNode> newKids = new LinkedHashMap<>(Math.max(4, len));
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            newKids.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return new ObjectNode(this._nodeFactory, newKids);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.START_OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isObject() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode
    public int size() {
        if (this._children == null) {
            return 0;
        }
        return this._children.size();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<JsonNode> elements() {
        return this._children == null ? ContainerNode.NoNodesIterator.instance() : this._children.values().iterator();
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode get(int index) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode get(String fieldName) {
        if (this._children != null) {
            return this._children.get(fieldName);
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<String> fieldNames() {
        return this._children == null ? ContainerNode.NoStringsIterator.instance() : this._children.keySet().iterator();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(int index) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(String fieldName) {
        JsonNode n;
        return (this._children == null || (n = this._children.get(fieldName)) == null) ? MissingNode.getInstance() : n;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<Map.Entry<String, JsonNode>> fields() {
        return this._children == null ? NoFieldsIterator.instance : this._children.entrySet().iterator();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode with(String propertyName) {
        if (this._children == null) {
            this._children = new LinkedHashMap<>();
        } else {
            JsonNode n = this._children.get(propertyName);
            if (n != null) {
                if (n instanceof ObjectNode) {
                    return (ObjectNode) n;
                }
                throw new UnsupportedOperationException("Property '" + propertyName + "' has value that is not of type ObjectNode (but " + n.getClass().getName() + ")");
            }
        }
        ObjectNode result = objectNode();
        this._children.put(propertyName, result);
        return result;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode withArray(String propertyName) {
        if (this._children == null) {
            this._children = new LinkedHashMap<>();
        } else {
            JsonNode n = this._children.get(propertyName);
            if (n != null) {
                if (n instanceof ArrayNode) {
                    return (ArrayNode) n;
                }
                throw new UnsupportedOperationException("Property '" + propertyName + "' has value that is not of type ArrayNode (but " + n.getClass().getName() + ")");
            }
        }
        ArrayNode result = arrayNode();
        this._children.put(propertyName, result);
        return result;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode findValue(String fieldName) {
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
                if (fieldName.equals(entry.getKey())) {
                    return entry.getValue();
                }
                JsonNode value = entry.getValue().findValue(fieldName);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar) {
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
                if (fieldName.equals(entry.getKey())) {
                    if (foundSoFar == null) {
                        foundSoFar = new ArrayList<>();
                    }
                    foundSoFar.add(entry.getValue());
                } else {
                    foundSoFar = entry.getValue().findValues(fieldName, foundSoFar);
                }
            }
        }
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
                if (fieldName.equals(entry.getKey())) {
                    if (foundSoFar == null) {
                        foundSoFar = new ArrayList<>();
                    }
                    foundSoFar.add(entry.getValue().asText());
                } else {
                    foundSoFar = entry.getValue().findValuesAsText(fieldName, foundSoFar);
                }
            }
        }
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public ObjectNode findParent(String fieldName) {
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
                if (!fieldName.equals(entry.getKey())) {
                    JsonNode value = entry.getValue().findParent(fieldName);
                    if (value != null) {
                        return (ObjectNode) value;
                    }
                } else {
                    return this;
                }
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
                if (fieldName.equals(entry.getKey())) {
                    if (foundSoFar == null) {
                        foundSoFar = new ArrayList<>();
                    }
                    foundSoFar.add(this);
                } else {
                    foundSoFar = entry.getValue().findParents(fieldName, foundSoFar);
                }
            }
        }
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartObject();
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
                jg.writeFieldName(en.getKey());
                ((BaseJsonNode) en.getValue()).serialize(jg, provider);
            }
        }
        jg.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jg, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForObject(this, jg);
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
                jg.writeFieldName(en.getKey());
                ((BaseJsonNode) en.getValue()).serialize(jg, provider);
            }
        }
        typeSer.writeTypeSuffixForObject(this, jg);
    }

    public JsonNode put(String fieldName, JsonNode value) {
        if (value == null) {
            value = nullNode();
        }
        return _put(fieldName, value);
    }

    public JsonNode remove(String fieldName) {
        if (this._children != null) {
            return this._children.remove(fieldName);
        }
        return null;
    }

    public ObjectNode remove(Collection<String> fieldNames) {
        if (this._children != null) {
            for (String fieldName : fieldNames) {
                this._children.remove(fieldName);
            }
        }
        return this;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode
    public ObjectNode removeAll() {
        this._children = null;
        return this;
    }

    public JsonNode putAll(Map<String, JsonNode> properties) {
        if (this._children == null) {
            this._children = new LinkedHashMap<>(properties);
        } else {
            for (Map.Entry<String, JsonNode> en : properties.entrySet()) {
                JsonNode n = en.getValue();
                if (n == null) {
                    n = nullNode();
                }
                this._children.put(en.getKey(), n);
            }
        }
        return this;
    }

    public JsonNode putAll(ObjectNode other) {
        int len = other.size();
        if (len > 0) {
            if (this._children == null) {
                this._children = new LinkedHashMap<>(len);
            }
            other.putContentsTo(this._children);
        }
        return this;
    }

    public ObjectNode retain(Collection<String> fieldNames) {
        if (this._children != null) {
            Iterator<Map.Entry<String, JsonNode>> entries = this._children.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, JsonNode> entry = entries.next();
                if (!fieldNames.contains(entry.getKey())) {
                    entries.remove();
                }
            }
        }
        return this;
    }

    public ObjectNode retain(String... fieldNames) {
        return retain(Arrays.asList(fieldNames));
    }

    public ArrayNode putArray(String fieldName) {
        ArrayNode n = arrayNode();
        _put(fieldName, n);
        return n;
    }

    public ObjectNode putObject(String fieldName) {
        ObjectNode n = objectNode();
        _put(fieldName, n);
        return n;
    }

    public ObjectNode putPOJO(String fieldName, Object pojo) {
        _put(fieldName, POJONode(pojo));
        return this;
    }

    public ObjectNode putNull(String fieldName) {
        _put(fieldName, nullNode());
        return this;
    }

    public ObjectNode put(String fieldName, int v) {
        _put(fieldName, numberNode(v));
        return this;
    }

    public ObjectNode put(String fieldName, Integer value) {
        if (value == null) {
            _put(fieldName, nullNode());
        } else {
            _put(fieldName, numberNode(value.intValue()));
        }
        return this;
    }

    public ObjectNode put(String fieldName, long v) {
        _put(fieldName, numberNode(v));
        return this;
    }

    public ObjectNode put(String fieldName, Long value) {
        if (value == null) {
            _put(fieldName, nullNode());
        } else {
            _put(fieldName, numberNode(value.longValue()));
        }
        return this;
    }

    public ObjectNode put(String fieldName, float v) {
        _put(fieldName, numberNode(v));
        return this;
    }

    public ObjectNode put(String fieldName, Float value) {
        if (value == null) {
            _put(fieldName, nullNode());
        } else {
            _put(fieldName, numberNode(value.floatValue()));
        }
        return this;
    }

    public ObjectNode put(String fieldName, double v) {
        _put(fieldName, numberNode(v));
        return this;
    }

    public ObjectNode put(String fieldName, Double value) {
        if (value == null) {
            _put(fieldName, nullNode());
        } else {
            _put(fieldName, numberNode(value.doubleValue()));
        }
        return this;
    }

    public ObjectNode put(String fieldName, BigDecimal v) {
        if (v == null) {
            putNull(fieldName);
        } else {
            _put(fieldName, numberNode(v));
        }
        return this;
    }

    public ObjectNode put(String fieldName, String v) {
        if (v == null) {
            putNull(fieldName);
        } else {
            _put(fieldName, textNode(v));
        }
        return this;
    }

    public ObjectNode put(String fieldName, boolean v) {
        _put(fieldName, booleanNode(v));
        return this;
    }

    public ObjectNode put(String fieldName, Boolean value) {
        if (value == null) {
            _put(fieldName, nullNode());
        } else {
            _put(fieldName, booleanNode(value.booleanValue()));
        }
        return this;
    }

    public ObjectNode put(String fieldName, byte[] v) {
        if (v == null) {
            _put(fieldName, nullNode());
        } else {
            _put(fieldName, binaryNode(v));
        }
        return this;
    }

    protected void putContentsTo(Map<String, JsonNode> dst) {
        if (this._children != null) {
            for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
                dst.put(en.getKey(), en.getValue());
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o.getClass() == getClass()) {
            ObjectNode other = (ObjectNode) o;
            if (other.size() != size()) {
                return false;
            }
            if (this._children == null) {
                return true;
            }
            for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
                String key = en.getKey();
                JsonNode value = en.getValue();
                JsonNode otherValue = other.get(key);
                if (otherValue == null || !otherValue.equals(value)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this._children == null) {
            return -1;
        }
        return this._children.hashCode();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        StringBuilder sb = new StringBuilder((size() << 4) + 32);
        sb.append("{");
        if (this._children != null) {
            int count = 0;
            for (Map.Entry<String, JsonNode> en : this._children.entrySet()) {
                if (count > 0) {
                    sb.append(",");
                }
                count++;
                TextNode.appendQuoted(sb, en.getKey());
                sb.append(':');
                sb.append(en.getValue().toString());
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private final JsonNode _put(String fieldName, JsonNode value) {
        if (this._children == null) {
            this._children = new LinkedHashMap<>();
        }
        return this._children.put(fieldName, value);
    }

    protected static class NoFieldsIterator implements Iterator<Map.Entry<String, JsonNode>> {
        static final NoFieldsIterator instance = new NoFieldsIterator();

        private NoFieldsIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public Map.Entry<String, JsonNode> next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new IllegalStateException();
        }
    }
}
