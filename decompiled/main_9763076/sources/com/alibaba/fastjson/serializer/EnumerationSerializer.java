package com.alibaba.fastjson.serializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumerationSerializer implements ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        Type elementType = null;
        if (out.isEnabled(SerializerFeature.WriteClassName) && (fieldType instanceof ParameterizedType)) {
            ParameterizedType param = (ParameterizedType) fieldType;
            elementType = param.getActualTypeArguments()[0];
        }
        Enumeration<?> e = (Enumeration) object;
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        try {
            out.append('[');
            int i = 0;
            while (e.hasMoreElements()) {
                try {
                    Object item = e.nextElement();
                    i++;
                    if (i != 0) {
                        out.append(',');
                    }
                    if (item == null) {
                        out.writeNull();
                    } else {
                        ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                        itemSerializer.write(serializer, item, Integer.valueOf(i - 1), elementType, 0);
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
}
