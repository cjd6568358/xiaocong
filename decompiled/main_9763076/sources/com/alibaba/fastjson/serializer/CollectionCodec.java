package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CollectionCodec implements ObjectDeserializer, ObjectSerializer {
    public static final CollectionCodec instance = new CollectionCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Type elementType = null;
        if (out.isEnabled(SerializerFeature.WriteClassName) || SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName)) {
            elementType = TypeUtils.getCollectionItemType(fieldType);
        }
        Collection<?> collection = (Collection) object;
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        if (out.isEnabled(SerializerFeature.WriteClassName)) {
            if (HashSet.class == collection.getClass()) {
                out.append((CharSequence) "Set");
            } else if (TreeSet.class == collection.getClass()) {
                out.append((CharSequence) "TreeSet");
            }
        }
        try {
            out.append('[');
            int i = 0;
            for (Object item : collection) {
                try {
                    i++;
                    if (i != 0) {
                        out.append(',');
                    }
                    if (item == null) {
                        out.writeNull();
                    } else {
                        Class<?> clazz = item.getClass();
                        if (clazz == Integer.class) {
                            out.writeInt(((Integer) item).intValue());
                        } else if (clazz == Long.class) {
                            out.writeLong(((Long) item).longValue());
                            if (out.isEnabled(SerializerFeature.WriteClassName)) {
                                out.write(76);
                            }
                        } else {
                            ObjectSerializer itemSerializer = serializer.getObjectWriter(clazz);
                            if (SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName) && (itemSerializer instanceof JavaBeanSerializer)) {
                                JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) itemSerializer;
                                javaBeanSerializer.writeNoneASM(serializer, item, Integer.valueOf(i - 1), elementType, features);
                            } else {
                                itemSerializer.write(serializer, item, Integer.valueOf(i - 1), elementType, features);
                            }
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    serializer.context = context;
                    throw th;
                }
            }
            out.append(']');
            serializer.context = context;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [T, com.alibaba.fastjson.JSONArray, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.Collection] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (defaultJSONParser.lexer.token() == 8) {
            defaultJSONParser.lexer.nextToken(16);
            return null;
        }
        if (type == JSONArray.class) {
            ?? r0 = (T) new JSONArray();
            defaultJSONParser.parseArray((Collection) r0);
            return r0;
        }
        ?? r2 = (T) TypeUtils.createCollection(type);
        defaultJSONParser.parseArray(TypeUtils.getCollectionItemType(type), r2, obj);
        return r2;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }
}
