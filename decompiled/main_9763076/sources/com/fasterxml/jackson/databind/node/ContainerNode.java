package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.tencent.android.tpush.common.Constants;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ContainerNode<T extends ContainerNode<T>> extends BaseJsonNode {
    protected final JsonNodeFactory _nodeFactory;

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public abstract JsonToken asToken();

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public abstract ObjectNode findParent(String str);

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public abstract List<JsonNode> findParents(String str, List<JsonNode> list);

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public abstract JsonNode findValue(String str);

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public abstract List<JsonNode> findValues(String str, List<JsonNode> list);

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public abstract List<String> findValuesAsText(String str, List<String> list);

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract JsonNode get(int i);

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract JsonNode get(String str);

    public abstract T removeAll();

    @Override // com.fasterxml.jackson.databind.JsonNode
    public abstract int size();

    protected ContainerNode(JsonNodeFactory nc) {
        this._nodeFactory = nc;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isContainerNode() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return Constants.MAIN_VERSION_TAG;
    }

    public final ArrayNode arrayNode() {
        return this._nodeFactory.arrayNode();
    }

    public final ObjectNode objectNode() {
        return this._nodeFactory.objectNode();
    }

    public final NullNode nullNode() {
        return this._nodeFactory.nullNode();
    }

    public final BooleanNode booleanNode(boolean v) {
        return this._nodeFactory.booleanNode(v);
    }

    public final NumericNode numberNode(byte v) {
        return this._nodeFactory.numberNode(v);
    }

    public final NumericNode numberNode(short v) {
        return this._nodeFactory.numberNode(v);
    }

    public final NumericNode numberNode(int v) {
        return this._nodeFactory.numberNode(v);
    }

    public final NumericNode numberNode(long v) {
        return this._nodeFactory.numberNode(v);
    }

    public final NumericNode numberNode(float v) {
        return this._nodeFactory.numberNode(v);
    }

    public final NumericNode numberNode(double v) {
        return this._nodeFactory.numberNode(v);
    }

    public final NumericNode numberNode(BigDecimal v) {
        return this._nodeFactory.numberNode(v);
    }

    public final TextNode textNode(String text) {
        return this._nodeFactory.textNode(text);
    }

    public final BinaryNode binaryNode(byte[] data) {
        return this._nodeFactory.binaryNode(data);
    }

    public final BinaryNode binaryNode(byte[] data, int offset, int length) {
        return this._nodeFactory.binaryNode(data, offset, length);
    }

    public final POJONode POJONode(Object pojo) {
        return this._nodeFactory.POJONode(pojo);
    }

    protected static class NoNodesIterator implements Iterator<JsonNode> {
        static final NoNodesIterator instance = new NoNodesIterator();

        private NoNodesIterator() {
        }

        public static NoNodesIterator instance() {
            return instance;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public JsonNode next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new IllegalStateException();
        }
    }

    protected static class NoStringsIterator implements Iterator<String> {
        static final NoStringsIterator instance = new NoStringsIterator();

        private NoStringsIterator() {
        }

        public static NoStringsIterator instance() {
            return instance;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public String next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new IllegalStateException();
        }
    }
}
