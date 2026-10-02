package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class NodeCursor extends JsonStreamContext {
    protected String _currentName;
    protected final NodeCursor _parent;

    public abstract boolean currentHasChildren();

    public abstract JsonNode currentNode();

    public abstract JsonToken endToken();

    public abstract JsonToken nextToken();

    public abstract JsonToken nextValue();

    public NodeCursor(int contextType, NodeCursor p) {
        this._type = contextType;
        this._index = -1;
        this._parent = p;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public final NodeCursor getParent() {
        return this._parent;
    }

    @Override // com.fasterxml.jackson.core.JsonStreamContext
    public final String getCurrentName() {
        return this._currentName;
    }

    public void overrideCurrentName(String name) {
        this._currentName = name;
    }

    public final NodeCursor iterateChildren() {
        JsonNode n = currentNode();
        if (n == null) {
            throw new IllegalStateException("No current node");
        }
        if (n.isArray()) {
            return new Array(n, this);
        }
        if (n.isObject()) {
            return new Object(n, this);
        }
        throw new IllegalStateException("Current node of type " + n.getClass().getName());
    }

    protected static final class RootValue extends NodeCursor {
        protected boolean _done;
        protected JsonNode _node;

        @Override // com.fasterxml.jackson.databind.node.NodeCursor, com.fasterxml.jackson.core.JsonStreamContext
        public /* bridge */ /* synthetic */ JsonStreamContext getParent() {
            return super.getParent();
        }

        public RootValue(JsonNode n, NodeCursor p) {
            super(0, p);
            this._done = false;
            this._node = n;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public void overrideCurrentName(String name) {
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken nextToken() {
            if (!this._done) {
                this._done = true;
                return this._node.asToken();
            }
            this._node = null;
            return null;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken nextValue() {
            return nextToken();
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken endToken() {
            return null;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonNode currentNode() {
            return this._node;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public boolean currentHasChildren() {
            return false;
        }
    }

    protected static final class Array extends NodeCursor {
        protected Iterator<JsonNode> _contents;
        protected JsonNode _currentNode;

        @Override // com.fasterxml.jackson.databind.node.NodeCursor, com.fasterxml.jackson.core.JsonStreamContext
        public /* bridge */ /* synthetic */ JsonStreamContext getParent() {
            return super.getParent();
        }

        public Array(JsonNode n, NodeCursor p) {
            super(1, p);
            this._contents = n.elements();
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken nextToken() {
            if (!this._contents.hasNext()) {
                this._currentNode = null;
                return null;
            }
            this._currentNode = this._contents.next();
            return this._currentNode.asToken();
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken nextValue() {
            return nextToken();
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken endToken() {
            return JsonToken.END_ARRAY;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonNode currentNode() {
            return this._currentNode;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public boolean currentHasChildren() {
            return ((ContainerNode) currentNode()).size() > 0;
        }
    }

    protected static final class Object extends NodeCursor {
        protected Iterator<Map.Entry<String, JsonNode>> _contents;
        protected Map.Entry<String, JsonNode> _current;
        protected boolean _needEntry;

        @Override // com.fasterxml.jackson.databind.node.NodeCursor, com.fasterxml.jackson.core.JsonStreamContext
        public /* bridge */ /* synthetic */ JsonStreamContext getParent() {
            return super.getParent();
        }

        public Object(JsonNode n, NodeCursor p) {
            super(2, p);
            this._contents = ((ObjectNode) n).fields();
            this._needEntry = true;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken nextToken() {
            if (this._needEntry) {
                if (!this._contents.hasNext()) {
                    this._currentName = null;
                    this._current = null;
                    return null;
                }
                this._needEntry = false;
                this._current = this._contents.next();
                this._currentName = this._current == null ? null : this._current.getKey();
                return JsonToken.FIELD_NAME;
            }
            this._needEntry = true;
            return this._current.getValue().asToken();
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken nextValue() {
            JsonToken t = nextToken();
            if (t == JsonToken.FIELD_NAME) {
                return nextToken();
            }
            return t;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonToken endToken() {
            return JsonToken.END_OBJECT;
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public JsonNode currentNode() {
            if (this._current == null) {
                return null;
            }
            return this._current.getValue();
        }

        @Override // com.fasterxml.jackson.databind.node.NodeCursor
        public boolean currentHasChildren() {
            return ((ContainerNode) currentNode()).size() > 0;
        }
    }
}
