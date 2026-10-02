package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONSerializer extends SerializeFilterable {
    protected final SerializeConfig config;
    protected SerialContext context;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private String indent;
    private int indentCount;
    protected Locale locale;
    public final SerializeWriter out;
    protected IdentityHashMap<Object, SerialContext> references;
    protected TimeZone timeZone;

    public JSONSerializer() {
        this(new SerializeWriter(), SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeWriter out) {
        this(out, SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeWriter out, SerializeConfig config) {
        this.indentCount = 0;
        this.indent = "\t";
        this.references = null;
        this.timeZone = JSON.defaultTimeZone;
        this.locale = JSON.defaultLocale;
        this.out = out;
        this.config = config;
    }

    public String getDateFormatPattern() {
        return this.dateFormat instanceof SimpleDateFormat ? ((SimpleDateFormat) this.dateFormat).toPattern() : this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null && this.dateFormatPattern != null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.locale);
            this.dateFormat.setTimeZone(this.timeZone);
        }
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormatPattern = dateFormat;
        if (this.dateFormat != null) {
            this.dateFormat = null;
        }
    }

    public void setContext(SerialContext parent, Object object, Object fieldName, int features) {
        setContext(parent, object, fieldName, features, 0);
    }

    public void setContext(SerialContext parent, Object object, Object fieldName, int features, int fieldFeatures) {
        if (!this.out.disableCircularReferenceDetect) {
            this.context = new SerialContext(parent, object, fieldName, features, fieldFeatures);
            if (this.references == null) {
                this.references = new IdentityHashMap<>();
            }
            this.references.put(object, this.context);
        }
    }

    public final boolean isWriteClassName(Type fieldType, Object obj) {
        return this.out.isEnabled(SerializerFeature.WriteClassName) && !(fieldType == null && this.out.isEnabled(SerializerFeature.NotWriteRootClassName) && (this.context == null || this.context.parent == null));
    }

    public boolean containsReference(Object value) {
        SerialContext refContext;
        if (this.references == null || (refContext = this.references.get(value)) == null) {
            return false;
        }
        Object fieldName = refContext.fieldName;
        return fieldName == null || (fieldName instanceof Integer) || (fieldName instanceof String);
    }

    public void writeReference(Object object) {
        SerialContext context = this.context;
        Object current = context.object;
        if (object == current) {
            this.out.write("{\"$ref\":\"@\"}");
            return;
        }
        SerialContext parentContext = context.parent;
        if (parentContext != null && object == parentContext.object) {
            this.out.write("{\"$ref\":\"..\"}");
            return;
        }
        SerialContext rootContext = context;
        while (rootContext.parent != null) {
            rootContext = rootContext.parent;
        }
        if (object == rootContext.object) {
            this.out.write("{\"$ref\":\"$\"}");
            return;
        }
        this.out.write("{\"$ref\":\"");
        this.out.write(this.references.get(object).toString());
        this.out.write("\"}");
    }

    public void incrementIndent() {
        this.indentCount++;
    }

    public void decrementIdent() {
        this.indentCount--;
    }

    public void println() {
        this.out.write(10);
        for (int i = 0; i < this.indentCount; i++) {
            this.out.write(this.indent);
        }
    }

    public SerializeWriter getWriter() {
        return this.out;
    }

    public String toString() {
        return this.out.toString();
    }

    public void config(SerializerFeature feature, boolean state) {
        this.out.config(feature, state);
    }

    public boolean isEnabled(SerializerFeature feature) {
        return this.out.isEnabled(feature);
    }

    public void writeNull() {
        this.out.writeNull();
    }

    public final void write(Object object) {
        if (object == null) {
            this.out.writeNull();
            return;
        }
        Class<?> clazz = object.getClass();
        ObjectSerializer writer = getObjectWriter(clazz);
        try {
            writer.write(this, object, null, null, 0);
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFieldName(Object object, Object fieldName) {
        writeWithFieldName(object, fieldName, null, 0);
    }

    public final void writeWithFieldName(Object object, Object fieldName, Type fieldType, int fieldFeatures) {
        try {
            if (object == null) {
                this.out.writeNull();
            } else {
                Class<?> clazz = object.getClass();
                ObjectSerializer writer = getObjectWriter(clazz);
                writer.write(this, object, fieldName, fieldType, fieldFeatures);
            }
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public final void writeWithFormat(Object object, String format) {
        if (object instanceof Date) {
            DateFormat dateFormat = getDateFormat();
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat(format, this.locale);
                dateFormat.setTimeZone(this.timeZone);
            }
            String text = dateFormat.format((Date) object);
            this.out.writeString(text);
            return;
        }
        if (object instanceof byte[]) {
            byte[] bytes = (byte[]) object;
            if ("gzip".equals(format) || "gzip,base64".equals(format)) {
                GZIPOutputStream gzipOut = null;
                try {
                    try {
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        if (bytes.length < 512) {
                            GZIPOutputStream gzipOut2 = new GZIPOutputStream(byteOut, bytes.length);
                            gzipOut = gzipOut2;
                        } else {
                            GZIPOutputStream gzipOut3 = new GZIPOutputStream(byteOut);
                            gzipOut = gzipOut3;
                        }
                        gzipOut.write(bytes);
                        gzipOut.finish();
                        this.out.writeByteArray(byteOut.toByteArray());
                        IOUtils.close(gzipOut);
                        return;
                    } catch (IOException ex) {
                        throw new JSONException("write gzipBytes error", ex);
                    }
                } catch (Throwable th) {
                    IOUtils.close(gzipOut);
                    throw th;
                }
            }
            if ("hex".equals(format)) {
                this.out.writeHex(bytes);
                return;
            } else {
                this.out.writeByteArray(bytes);
                return;
            }
        }
        write(object);
    }

    public final void write(String text) {
        StringCodec.instance.write(this, text);
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return this.config.getObjectWriter(clazz);
    }
}
