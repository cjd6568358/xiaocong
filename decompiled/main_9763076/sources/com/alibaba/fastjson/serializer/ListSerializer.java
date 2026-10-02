package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ListSerializer implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws Throwable {
        boolean writeClassName = serializer.out.isEnabled(SerializerFeature.WriteClassName) || SerializerFeature.isEnabled(features, SerializerFeature.WriteClassName);
        SerializeWriter out = serializer.out;
        Type elementType = null;
        if (writeClassName) {
            elementType = TypeUtils.getCollectionItemType(fieldType);
        }
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        List<?> list = (List) object;
        if (list.size() == 0) {
            out.append((CharSequence) "[]");
            return;
        }
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        try {
            if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                out.append('[');
                serializer.incrementIndent();
                int i = 0;
                for (Object item : list) {
                    if (i != 0) {
                        out.append(',');
                    }
                    serializer.println();
                    if (item != null) {
                        if (serializer.containsReference(item)) {
                            serializer.writeReference(item);
                        } else {
                            ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                            try {
                                SerialContext itemContext = new SerialContext(context, object, fieldName, 0, 0);
                                serializer.context = itemContext;
                                itemSerializer.write(serializer, item, Integer.valueOf(i), elementType, features);
                            } catch (Throwable th) {
                                th = th;
                                serializer.context = context;
                                throw th;
                            }
                        }
                    } else {
                        serializer.out.writeNull();
                    }
                    i++;
                }
                serializer.decrementIdent();
                serializer.println();
                out.append(']');
                serializer.context = context;
                return;
            }
            out.append('[');
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                Object item2 = list.get(i2);
                if (i2 != 0) {
                    out.append(',');
                }
                if (item2 == null) {
                    out.append((CharSequence) "null");
                } else {
                    Class<?> clazz = item2.getClass();
                    if (clazz == Integer.class) {
                        out.writeInt(((Integer) item2).intValue());
                    } else if (clazz == Long.class) {
                        long val = ((Long) item2).longValue();
                        if (writeClassName) {
                            out.writeLong(val);
                            out.write(76);
                        } else {
                            out.writeLong(val);
                        }
                    } else if ((SerializerFeature.DisableCircularReferenceDetect.mask & features) != 0) {
                        serializer.getObjectWriter(item2.getClass()).write(serializer, item2, Integer.valueOf(i2), elementType, features);
                    } else {
                        if (!out.disableCircularReferenceDetect) {
                            SerialContext itemContext2 = new SerialContext(context, object, fieldName, 0, 0);
                            serializer.context = itemContext2;
                        }
                        if (serializer.containsReference(item2)) {
                            serializer.writeReference(item2);
                        } else {
                            ObjectSerializer itemSerializer2 = serializer.getObjectWriter(item2.getClass());
                            if ((SerializerFeature.WriteClassName.mask & features) != 0 && (itemSerializer2 instanceof JavaBeanSerializer)) {
                                JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer) itemSerializer2;
                                javaBeanSerializer.writeNoneASM(serializer, item2, Integer.valueOf(i2), elementType, features);
                            } else {
                                itemSerializer2.write(serializer, item2, Integer.valueOf(i2), elementType, features);
                            }
                        }
                    }
                }
            }
            out.append(']');
            serializer.context = context;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
