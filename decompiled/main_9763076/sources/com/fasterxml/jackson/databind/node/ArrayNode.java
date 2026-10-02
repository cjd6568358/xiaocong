package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ArrayNode extends ContainerNode<ArrayNode> {
    protected ArrayList<JsonNode> _children;

    public ArrayNode(JsonNodeFactory nc) {
        super(nc);
    }

    protected ArrayNode(JsonNodeFactory nc, ArrayList<JsonNode> children) {
        super(nc);
        this._children = children;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode deepCopy() {
        if (this._children == null) {
            return new ArrayNode(this._nodeFactory);
        }
        int len = this._children.size();
        ArrayList<JsonNode> newKids = new ArrayList<>(Math.max(4, len));
        for (int i = 0; i < len; i++) {
            newKids.add(this._children.get(i).deepCopy());
        }
        return new ArrayNode(this._nodeFactory, newKids);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.START_ARRAY;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isArray() {
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
        return this._children == null ? ContainerNode.NoNodesIterator.instance() : this._children.iterator();
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode get(int index) {
        if (index < 0 || this._children == null || index >= this._children.size()) {
            return null;
        }
        return this._children.get(index);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode get(String fieldName) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(String fieldName) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode path(int index) {
        return (index < 0 || this._children == null || index >= this._children.size()) ? MissingNode.getInstance() : this._children.get(index);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        jg.writeStartArray();
        if (this._children != null) {
            for (JsonNode n : this._children) {
                ((BaseJsonNode) n).serialize(jg, provider);
            }
        }
        jg.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jg, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForArray(this, jg);
        if (this._children != null) {
            for (JsonNode n : this._children) {
                ((BaseJsonNode) n).serialize(jg, provider);
            }
        }
        typeSer.writeTypeSuffixForArray(this, jg);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode findValue(String fieldName) {
        if (this._children != null) {
            for (JsonNode node : this._children) {
                JsonNode value = node.findValue(fieldName);
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
            for (JsonNode node : this._children) {
                foundSoFar = node.findValues(fieldName, foundSoFar);
            }
        }
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public List<String> findValuesAsText(String fieldName, List<String> foundSoFar) {
        if (this._children != null) {
            for (JsonNode node : this._children) {
                foundSoFar = node.findValuesAsText(fieldName, foundSoFar);
            }
        }
        return foundSoFar;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public ObjectNode findParent(String fieldName) {
        if (this._children != null) {
            for (JsonNode node : this._children) {
                JsonNode parent = node.findParent(fieldName);
                if (parent != null) {
                    return (ObjectNode) parent;
                }
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar) {
        if (this._children != null) {
            for (JsonNode node : this._children) {
                foundSoFar = node.findParents(fieldName, foundSoFar);
            }
        }
        return foundSoFar;
    }

    public JsonNode set(int index, JsonNode value) {
        if (value == null) {
            value = nullNode();
        }
        return _set(index, value);
    }

    public ArrayNode add(JsonNode value) {
        if (value == null) {
            value = nullNode();
        }
        _add(value);
        return this;
    }

    public ArrayNode addAll(ArrayNode other) {
        int len = other.size();
        if (len > 0) {
            if (this._children == null) {
                this._children = new ArrayList<>(len + 2);
            }
            other.addContentsTo(this._children);
        }
        return this;
    }

    public ArrayNode addAll(Collection<JsonNode> nodes) {
        int len = nodes.size();
        if (len > 0) {
            if (this._children == null) {
                this._children = new ArrayList<>(nodes);
            } else {
                this._children.addAll(nodes);
            }
        }
        return this;
    }

    public ArrayNode insert(int index, JsonNode value) {
        if (value == null) {
            value = nullNode();
        }
        _insert(index, value);
        return this;
    }

    public JsonNode remove(int index) {
        if (index < 0 || this._children == null || index >= this._children.size()) {
            return null;
        }
        return this._children.remove(index);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode
    public ArrayNode removeAll() {
        this._children = null;
        return this;
    }

    public ArrayNode addArray() {
        ArrayNode n = arrayNode();
        _add(n);
        return n;
    }

    public ObjectNode addObject() {
        ObjectNode n = objectNode();
        _add(n);
        return n;
    }

    public ArrayNode addPOJO(Object value) {
        if (value == null) {
            addNull();
        } else {
            _add(POJONode(value));
        }
        return this;
    }

    public ArrayNode addNull() {
        _add(nullNode());
        return this;
    }

    public ArrayNode add(int v) {
        _add(numberNode(v));
        return this;
    }

    public ArrayNode add(Integer value) {
        return value == null ? addNull() : _add(numberNode(value.intValue()));
    }

    public ArrayNode add(long v) {
        return _add(numberNode(v));
    }

    public ArrayNode add(Long value) {
        return value == null ? addNull() : _add(numberNode(value.longValue()));
    }

    public ArrayNode add(float v) {
        return _add(numberNode(v));
    }

    public ArrayNode add(Float value) {
        return value == null ? addNull() : _add(numberNode(value.floatValue()));
    }

    public ArrayNode add(double v) {
        return _add(numberNode(v));
    }

    public ArrayNode add(Double value) {
        return value == null ? addNull() : _add(numberNode(value.doubleValue()));
    }

    public ArrayNode add(BigDecimal v) {
        return v == null ? addNull() : _add(numberNode(v));
    }

    public ArrayNode add(String v) {
        return v == null ? addNull() : _add(textNode(v));
    }

    public ArrayNode add(boolean v) {
        return _add(booleanNode(v));
    }

    public ArrayNode add(Boolean value) {
        return value == null ? addNull() : _add(booleanNode(value.booleanValue()));
    }

    public ArrayNode add(byte[] v) {
        return v == null ? addNull() : _add(binaryNode(v));
    }

    public ArrayNode insertArray(int index) {
        ArrayNode n = arrayNode();
        _insert(index, n);
        return n;
    }

    public ObjectNode insertObject(int index) {
        ObjectNode n = objectNode();
        _insert(index, n);
        return n;
    }

    public ArrayNode insertPOJO(int index, Object value) {
        return value == null ? insertNull(index) : _insert(index, POJONode(value));
    }

    public ArrayNode insertNull(int index) {
        _insert(index, nullNode());
        return this;
    }

    public ArrayNode insert(int index, int v) {
        _insert(index, numberNode(v));
        return this;
    }

    public ArrayNode insert(int index, Integer value) {
        if (value == null) {
            insertNull(index);
        } else {
            _insert(index, numberNode(value.intValue()));
        }
        return this;
    }

    public ArrayNode insert(int index, long v) {
        return _insert(index, numberNode(v));
    }

    public ArrayNode insert(int index, Long value) {
        return value == null ? insertNull(index) : _insert(index, numberNode(value.longValue()));
    }

    public ArrayNode insert(int index, float v) {
        return _insert(index, numberNode(v));
    }

    public ArrayNode insert(int index, Float value) {
        return value == null ? insertNull(index) : _insert(index, numberNode(value.floatValue()));
    }

    public ArrayNode insert(int index, double v) {
        return _insert(index, numberNode(v));
    }

    public ArrayNode insert(int index, Double value) {
        return value == null ? insertNull(index) : _insert(index, numberNode(value.doubleValue()));
    }

    public ArrayNode insert(int index, BigDecimal v) {
        return v == null ? insertNull(index) : _insert(index, numberNode(v));
    }

    public ArrayNode insert(int index, String v) {
        return v == null ? insertNull(index) : _insert(index, textNode(v));
    }

    public ArrayNode insert(int index, boolean v) {
        return _insert(index, booleanNode(v));
    }

    public ArrayNode insert(int index, Boolean value) {
        return value == null ? insertNull(index) : _insert(index, booleanNode(value.booleanValue()));
    }

    public ArrayNode insert(int index, byte[] v) {
        return v == null ? insertNull(index) : _insert(index, binaryNode(v));
    }

    protected void addContentsTo(List<JsonNode> dst) {
        if (this._children != null) {
            for (JsonNode n : this._children) {
                dst.add(n);
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o != null && o.getClass() == getClass()) {
            ArrayNode other = (ArrayNode) o;
            if (this._children == null || this._children.size() == 0) {
                return other.size() == 0;
            }
            return other._sameChildren(this._children);
        }
        return false;
    }

    public int hashCode() {
        if (this._children == null) {
            return 1;
        }
        int hash = this._children.size();
        for (JsonNode n : this._children) {
            if (n != null) {
                hash ^= n.hashCode();
            }
        }
        return hash;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        StringBuilder sb = new StringBuilder((size() << 4) + 16);
        sb.append('[');
        if (this._children != null) {
            int len = this._children.size();
            for (int i = 0; i < len; i++) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(this._children.get(i).toString());
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public JsonNode _set(int index, JsonNode value) {
        if (this._children == null || index < 0 || index >= this._children.size()) {
            throw new IndexOutOfBoundsException("Illegal index " + index + ", array size " + size());
        }
        return this._children.set(index, value);
    }

    private ArrayNode _add(JsonNode node) {
        if (this._children == null) {
            this._children = new ArrayList<>();
        }
        this._children.add(node);
        return this;
    }

    private ArrayNode _insert(int index, JsonNode node) {
        if (this._children == null) {
            this._children = new ArrayList<>();
            this._children.add(node);
        } else if (index < 0) {
            this._children.add(0, node);
        } else if (index >= this._children.size()) {
            this._children.add(node);
        } else {
            this._children.add(index, node);
        }
        return this;
    }

    private boolean _sameChildren(ArrayList<JsonNode> otherChildren) {
        int len = otherChildren.size();
        if (size() != len) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (!this._children.get(i).equals(otherChildren.get(i))) {
                return false;
            }
        }
        return true;
    }
}
