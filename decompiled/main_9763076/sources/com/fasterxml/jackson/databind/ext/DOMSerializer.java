package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DOMSerializer extends StdSerializer<Node> {
    protected final DOMImplementationLS _domImpl;

    public DOMSerializer() {
        super(Node.class);
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            this._domImpl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        } catch (Exception e) {
            throw new IllegalStateException("Could not instantiate DOMImplementationRegistry: " + e.getMessage(), e);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(Node value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._domImpl == null) {
            throw new IllegalStateException("Could not find DOM LS");
        }
        LSSerializer writer = this._domImpl.createLSSerializer();
        jgen.writeString(writer.writeToString(value));
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        return createSchemaNode(SettingsContentProvider.STRING_TYPE, true);
    }
}
