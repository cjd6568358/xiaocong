package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultFieldDeserializer extends FieldDeserializer {
    protected ObjectDeserializer fieldValueDeserilizer;

    public DefaultFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
    }

    public ObjectDeserializer getFieldValueDeserilizer(ParserConfig config) {
        if (this.fieldValueDeserilizer == null) {
            JSONField annotation = this.fieldInfo.getAnnotation();
            if (annotation != null && annotation.deserializeUsing() != Void.class) {
                Class<?> deserializeUsing = annotation.deserializeUsing();
                try {
                    this.fieldValueDeserilizer = (ObjectDeserializer) deserializeUsing.newInstance();
                } catch (Exception ex) {
                    throw new JSONException("create deserializeUsing ObjectDeserializer error", ex);
                }
            } else {
                this.fieldValueDeserilizer = config.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
            }
        }
        return this.fieldValueDeserilizer;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) {
        Object value;
        if (this.fieldValueDeserilizer == null) {
            getFieldValueDeserilizer(parser.getConfig());
        }
        ObjectDeserializer fieldValueDeserilizer = this.fieldValueDeserilizer;
        Type fieldType = this.fieldInfo.fieldType;
        if (objectType instanceof ParameterizedType) {
            ParseContext objContext = parser.getContext();
            if (objContext != null) {
                objContext.type = objectType;
            }
            if (fieldType != objectType) {
                fieldType = FieldInfo.getFieldType(this.clazz, objectType, fieldType);
                fieldValueDeserilizer = parser.getConfig().getDeserializer(fieldType);
            }
        }
        if ((fieldValueDeserilizer instanceof JavaBeanDeserializer) && this.fieldInfo.parserFeatures != 0) {
            JavaBeanDeserializer javaBeanDeser = (JavaBeanDeserializer) fieldValueDeserilizer;
            value = javaBeanDeser.deserialze(parser, fieldType, this.fieldInfo.name, this.fieldInfo.parserFeatures);
        } else if (this.fieldInfo.format != null && (fieldValueDeserilizer instanceof ContextObjectDeserializer)) {
            value = ((ContextObjectDeserializer) fieldValueDeserilizer).deserialze(parser, fieldType, this.fieldInfo.name, this.fieldInfo.format, this.fieldInfo.parserFeatures);
        } else {
            value = fieldValueDeserilizer.deserialze(parser, fieldType, this.fieldInfo.name);
        }
        if ((value instanceof byte[]) && ("gzip".equals(this.fieldInfo.format) || "gzip,base64".equals(this.fieldInfo.format))) {
            byte[] bytes = (byte[]) value;
            try {
                GZIPInputStream gzipIn = new GZIPInputStream(new ByteArrayInputStream(bytes));
                try {
                    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                    while (true) {
                        byte[] buf = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                        int len = gzipIn.read(buf);
                        if (len == -1) {
                            break;
                        } else if (len > 0) {
                            byteOut.write(buf, 0, len);
                        }
                    }
                    value = byteOut.toByteArray();
                } catch (IOException e) {
                    ex = e;
                    throw new JSONException("unzip bytes error.", ex);
                }
            } catch (IOException e2) {
                ex = e2;
            }
        }
        if (parser.getResolveStatus() == 1) {
            DefaultJSONParser.ResolveTask task = parser.getLastResolveTask();
            task.fieldDeserializer = this;
            task.ownerContext = parser.getContext();
            parser.setResolveStatus(0);
            return;
        }
        if (object == null) {
            fieldValues.put(this.fieldInfo.name, value);
        } else {
            setValue(object, value);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public int getFastMatchToken() {
        if (this.fieldValueDeserilizer != null) {
            return this.fieldValueDeserilizer.getFastMatchToken();
        }
        return 2;
    }
}
