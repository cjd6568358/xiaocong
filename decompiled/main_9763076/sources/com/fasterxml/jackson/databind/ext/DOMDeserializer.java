package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class DOMDeserializer<T> extends FromStringDeserializer<T> {
    static final DocumentBuilderFactory _parserFactory = DocumentBuilderFactory.newInstance();

    @Override // com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
    public abstract T _deserialize(String str, DeserializationContext deserializationContext);

    static {
        _parserFactory.setNamespaceAware(true);
    }

    protected DOMDeserializer(Class<T> cls) {
        super(cls);
    }

    protected final Document parse(String value) throws IllegalArgumentException {
        try {
            return _parserFactory.newDocumentBuilder().parse(new InputSource(new StringReader(value)));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON String as XML: " + e.getMessage(), e);
        }
    }

    public static class NodeDeserializer extends DOMDeserializer<Node> {
        public NodeDeserializer() {
            super(Node.class);
        }

        @Override // com.fasterxml.jackson.databind.ext.DOMDeserializer, com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Node _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return parse(value);
        }
    }

    public static class DocumentDeserializer extends DOMDeserializer<Document> {
        public DocumentDeserializer() {
            super(Document.class);
        }

        @Override // com.fasterxml.jackson.databind.ext.DOMDeserializer, com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
        public Document _deserialize(String value, DeserializationContext ctxt) throws IllegalArgumentException {
            return parse(value);
        }
    }
}
