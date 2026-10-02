package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectWriter implements Versioned {
    protected static final PrettyPrinter NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
    protected final SerializationConfig _config;
    protected final JsonFactory _jsonFactory;
    protected final PrettyPrinter _prettyPrinter;
    protected final JavaType _rootType;
    protected final FormatSchema _schema;
    protected final SerializerFactory _serializerFactory;
    protected final DefaultSerializerProvider _serializerProvider;

    protected ObjectWriter(ObjectMapper mapper, SerializationConfig config, JavaType rootType, PrettyPrinter pp) {
        this._config = config;
        this._serializerProvider = mapper._serializerProvider;
        this._serializerFactory = mapper._serializerFactory;
        this._jsonFactory = mapper._jsonFactory;
        this._rootType = rootType;
        this._prettyPrinter = pp;
        this._schema = null;
    }

    protected ObjectWriter(ObjectMapper mapper, SerializationConfig config) {
        this._config = config;
        this._serializerProvider = mapper._serializerProvider;
        this._serializerFactory = mapper._serializerFactory;
        this._jsonFactory = mapper._jsonFactory;
        this._rootType = null;
        this._prettyPrinter = null;
        this._schema = null;
    }

    protected ObjectWriter(ObjectMapper mapper, SerializationConfig config, FormatSchema s) {
        this._config = config;
        this._serializerProvider = mapper._serializerProvider;
        this._serializerFactory = mapper._serializerFactory;
        this._jsonFactory = mapper._jsonFactory;
        this._rootType = null;
        this._prettyPrinter = null;
        this._schema = s;
    }

    protected ObjectWriter(ObjectWriter base, SerializationConfig config, JavaType rootType, PrettyPrinter pp, FormatSchema s) {
        this._config = config;
        this._serializerProvider = base._serializerProvider;
        this._serializerFactory = base._serializerFactory;
        this._jsonFactory = base._jsonFactory;
        this._rootType = rootType;
        this._prettyPrinter = pp;
        this._schema = s;
    }

    protected ObjectWriter(ObjectWriter base, SerializationConfig config) {
        this._config = config;
        this._serializerProvider = base._serializerProvider;
        this._serializerFactory = base._serializerFactory;
        this._jsonFactory = base._jsonFactory;
        this._schema = base._schema;
        this._rootType = base._rootType;
        this._prettyPrinter = base._prettyPrinter;
    }

    @Override // com.fasterxml.jackson.core.Versioned
    public Version version() {
        return DatabindVersion.instance.version();
    }

    public ObjectWriter with(SerializationFeature feature) {
        SerializationConfig newConfig = this._config.with(feature);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(SerializationFeature first, SerializationFeature... other) {
        SerializationConfig newConfig = this._config.with(first, other);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withFeatures(SerializationFeature... features) {
        SerializationConfig newConfig = this._config.withFeatures(features);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter without(SerializationFeature feature) {
        SerializationConfig newConfig = this._config.without(feature);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter without(SerializationFeature first, SerializationFeature... other) {
        SerializationConfig newConfig = this._config.without(first, other);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withoutFeatures(SerializationFeature... features) {
        SerializationConfig newConfig = this._config.withoutFeatures(features);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(DateFormat df) {
        SerializationConfig newConfig = this._config.with(df);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withDefaultPrettyPrinter() {
        return with(new DefaultPrettyPrinter());
    }

    public ObjectWriter with(FilterProvider filterProvider) {
        return filterProvider == this._config.getFilterProvider() ? this : new ObjectWriter(this, this._config.withFilters(filterProvider));
    }

    public ObjectWriter with(PrettyPrinter pp) {
        if (pp != this._prettyPrinter) {
            if (pp == null) {
                pp = NULL_PRETTY_PRINTER;
            }
            return new ObjectWriter(this, this._config, this._rootType, pp, this._schema);
        }
        return this;
    }

    public ObjectWriter withRootName(String rootName) {
        SerializationConfig newConfig = this._config.withRootName(rootName);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter withSchema(FormatSchema schema) {
        return this._schema == schema ? this : new ObjectWriter(this, this._config, this._rootType, this._prettyPrinter, schema);
    }

    public ObjectWriter withType(JavaType rootType) {
        return rootType == this._rootType ? this : new ObjectWriter(this, this._config, rootType, this._prettyPrinter, this._schema);
    }

    public ObjectWriter withType(Class<?> rootType) {
        return withType(this._config.constructType(rootType));
    }

    public ObjectWriter withType(TypeReference<?> rootType) {
        return withType(this._config.getTypeFactory().constructType(rootType.getType()));
    }

    public ObjectWriter withView(Class<?> view) {
        SerializationConfig newConfig = this._config.withView(view);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(Locale l) {
        SerializationConfig newConfig = this._config.with(l);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public ObjectWriter with(TimeZone tz) {
        SerializationConfig newConfig = this._config.with(tz);
        return newConfig == this._config ? this : new ObjectWriter(this, newConfig);
    }

    public boolean isEnabled(SerializationFeature f) {
        return this._config.isEnabled(f);
    }

    public boolean isEnabled(MapperFeature f) {
        return this._config.isEnabled(f);
    }

    public boolean isEnabled(JsonParser.Feature f) {
        return this._jsonFactory.isEnabled(f);
    }

    public JsonFactory getJsonFactory() {
        return this._jsonFactory;
    }

    public TypeFactory getTypeFactory() {
        return this._config.getTypeFactory();
    }

    public void writeValue(JsonGenerator jgen, Object value) throws IOException {
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _writeCloseableValue(jgen, value, this._config);
            return;
        }
        if (this._rootType == null) {
            _serializerProvider(this._config).serializeValue(jgen, value);
        } else {
            _serializerProvider(this._config).serializeValue(jgen, value, this._rootType);
        }
        if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jgen.flush();
        }
    }

    public void writeValue(File resultFile, Object value) throws IOException {
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(resultFile, JsonEncoding.UTF8), value);
    }

    public void writeValue(OutputStream out, Object value) throws IOException {
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(out, JsonEncoding.UTF8), value);
    }

    public void writeValue(Writer w, Object value) throws IOException {
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(w), value);
    }

    public String writeValueAsString(Object value) throws IOException {
        SegmentedStringWriter sw = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(sw), value);
        return sw.getAndClear();
    }

    public byte[] writeValueAsBytes(Object value) throws IOException {
        ByteArrayBuilder bb = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
        _configAndWriteValue(this._jsonFactory.createJsonGenerator(bb, JsonEncoding.UTF8), value);
        byte[] result = bb.toByteArray();
        bb.release();
        return result;
    }

    public boolean canSerialize(Class<?> type) {
        return _serializerProvider(this._config).hasSerializerFor(type);
    }

    protected DefaultSerializerProvider _serializerProvider(SerializationConfig config) {
        return this._serializerProvider.createInstance(config, this._serializerFactory);
    }

    protected final void _configAndWriteValue(JsonGenerator jgen, Object value) throws IOException {
        if (this._prettyPrinter != null) {
            PrettyPrinter pp = this._prettyPrinter;
            if (pp == NULL_PRETTY_PRINTER) {
                pp = null;
            }
            jgen.setPrettyPrinter(pp);
        } else if (this._config.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jgen.useDefaultPrettyPrinter();
        }
        if (this._schema != null) {
            jgen.setSchema(this._schema);
        }
        if (this._config.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) && (value instanceof Closeable)) {
            _configAndWriteCloseable(jgen, value, this._config);
            return;
        }
        boolean closed = false;
        try {
            if (this._rootType == null) {
                _serializerProvider(this._config).serializeValue(jgen, value);
            } else {
                _serializerProvider(this._config).serializeValue(jgen, value, this._rootType);
            }
            closed = true;
            jgen.close();
        } finally {
            if (!closed) {
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private final void _configAndWriteCloseable(JsonGenerator jgen, Object value, SerializationConfig cfg) throws IOException {
        Closeable toClose = (Closeable) value;
        try {
            if (this._rootType == null) {
                _serializerProvider(cfg).serializeValue(jgen, value);
            } else {
                _serializerProvider(cfg).serializeValue(jgen, value);
            }
            if (this._schema != null) {
                jgen.setSchema(this._schema);
            }
            jgen = null;
            jgen.close();
            toClose = null;
            toClose.close();
        } finally {
            if (jgen != null) {
                try {
                    jgen.close();
                } catch (IOException e) {
                }
            }
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    private final void _writeCloseableValue(JsonGenerator jgen, Object value, SerializationConfig cfg) throws IOException {
        Closeable toClose = (Closeable) value;
        try {
            if (this._rootType == null) {
                _serializerProvider(cfg).serializeValue(jgen, value);
            } else {
                _serializerProvider(cfg).serializeValue(jgen, value, this._rootType);
            }
            if (this._config.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jgen.flush();
            }
            toClose = null;
            toClose.close();
        } finally {
            if (toClose != null) {
                try {
                    toClose.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
