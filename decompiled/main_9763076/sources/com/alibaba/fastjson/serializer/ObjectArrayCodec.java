package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectArrayCodec implements ObjectDeserializer, ObjectSerializer {
    public static final ObjectArrayCodec instance = new ObjectArrayCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        Object[] array = (Object[]) object;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        int size = array.length;
        int end = size - 1;
        if (end == -1) {
            out.append((CharSequence) "[]");
            return;
        }
        SerialContext context = serializer.context;
        serializer.setContext(context, object, fieldName, 0);
        Class<?> preClazz = null;
        ObjectSerializer preWriter = null;
        try {
            out.append('[');
            if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                serializer.incrementIndent();
                serializer.println();
                for (int i = 0; i < size; i++) {
                    if (i != 0) {
                        out.write(44);
                        serializer.println();
                    }
                    serializer.write(array[i]);
                }
                serializer.decrementIdent();
                serializer.println();
                out.write(93);
                return;
            }
            for (int i2 = 0; i2 < end; i2++) {
                Object item = array[i2];
                if (item == null) {
                    out.append((CharSequence) "null,");
                } else {
                    if (serializer.containsReference(item)) {
                        serializer.writeReference(item);
                    } else {
                        Class<?> clazz = item.getClass();
                        if (clazz == preClazz) {
                            preWriter.write(serializer, item, null, null, 0);
                        } else {
                            preClazz = clazz;
                            preWriter = serializer.getObjectWriter(clazz);
                            preWriter.write(serializer, item, null, null, 0);
                        }
                    }
                    out.append(',');
                }
            }
            Object item2 = array[end];
            if (item2 == null) {
                out.append((CharSequence) "null]");
            } else {
                if (serializer.containsReference(item2)) {
                    serializer.writeReference(item2);
                } else {
                    serializer.writeWithFieldName(item2, Integer.valueOf(end));
                }
                out.append(']');
            }
        } finally {
            serializer.context = context;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [T, byte[]] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Class<?> componentType;
        Type genericComponentType;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = jSONLexer.token();
        if (i == 8) {
            jSONLexer.nextToken(16);
            return null;
        }
        if (i == 4 || i == 26) {
            ?? r4 = (T) jSONLexer.bytesValue();
            jSONLexer.nextToken(16);
            if (r4.length == 0 && type != byte[].class) {
                return null;
            }
            return r4;
        }
        if (type instanceof GenericArrayType) {
            genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            if (genericComponentType instanceof TypeVariable) {
                TypeVariable typeVariable = (TypeVariable) genericComponentType;
                Type type2 = defaultJSONParser.getContext().type;
                if (type2 instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type2;
                    Type rawType = parameterizedType.getRawType();
                    Type type3 = null;
                    if (rawType instanceof Class) {
                        TypeVariable<Class<T>>[] typeParameters = ((Class) rawType).getTypeParameters();
                        for (int i2 = 0; i2 < typeParameters.length; i2++) {
                            if (typeParameters[i2].getName().equals(typeVariable.getName())) {
                                type3 = parameterizedType.getActualTypeArguments()[i2];
                            }
                        }
                    }
                    if (type3 instanceof Class) {
                        componentType = (Class) type3;
                    } else {
                        componentType = Object.class;
                    }
                } else {
                    componentType = TypeUtils.getClass(typeVariable.getBounds()[0]);
                }
            } else {
                componentType = TypeUtils.getClass(genericComponentType);
            }
        } else {
            componentType = ((Class) type).getComponentType();
            genericComponentType = componentType;
        }
        JSONArray jSONArray = new JSONArray();
        defaultJSONParser.parseArray(genericComponentType, jSONArray, obj);
        return (T) toObjectArray(defaultJSONParser, componentType, jSONArray);
    }

    private <T> T toObjectArray(DefaultJSONParser defaultJSONParser, Class<?> cls, JSONArray jSONArray) {
        Object objectArray;
        if (jSONArray == null) {
            return null;
        }
        int size = jSONArray.size();
        T t = (T) Array.newInstance(cls, size);
        for (int i = 0; i < size; i++) {
            Object obj = jSONArray.get(i);
            if (obj == jSONArray) {
                Array.set(t, i, t);
            } else if (cls.isArray()) {
                if (cls.isInstance(obj)) {
                    objectArray = obj;
                } else {
                    objectArray = toObjectArray(defaultJSONParser, cls, (JSONArray) obj);
                }
                Array.set(t, i, objectArray);
            } else {
                Object objCast = null;
                if (obj instanceof JSONArray) {
                    boolean z = false;
                    JSONArray jSONArray2 = (JSONArray) obj;
                    int size2 = jSONArray2.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        if (jSONArray2.get(i2) == jSONArray) {
                            jSONArray2.set(i, t);
                            z = true;
                        }
                    }
                    if (z) {
                        objCast = jSONArray2.toArray();
                    }
                }
                if (objCast == null) {
                    objCast = TypeUtils.cast(obj, (Class<Object>) cls, defaultJSONParser.getConfig());
                }
                Array.set(t, i, objCast);
            }
        }
        jSONArray.setRelatedArray(t);
        jSONArray.setComponentType(cls);
        return t;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }
}
