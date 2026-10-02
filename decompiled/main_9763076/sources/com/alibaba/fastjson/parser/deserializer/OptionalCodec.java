package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OptionalCodec implements ObjectDeserializer, ObjectSerializer {
    public static OptionalCodec instance = new OptionalCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == OptionalInt.class) {
            Integer numCastToInt = TypeUtils.castToInt(defaultJSONParser.parseObject((Class) Integer.class));
            if (numCastToInt == null) {
                return (T) OptionalInt.empty();
            }
            return (T) OptionalInt.of(numCastToInt.intValue());
        }
        if (type == OptionalLong.class) {
            Long lCastToLong = TypeUtils.castToLong(defaultJSONParser.parseObject((Class) Long.class));
            if (lCastToLong == null) {
                return (T) OptionalLong.empty();
            }
            return (T) OptionalLong.of(lCastToLong.longValue());
        }
        if (type == OptionalDouble.class) {
            Double dCastToDouble = TypeUtils.castToDouble(defaultJSONParser.parseObject((Class) Double.class));
            if (dCastToDouble == null) {
                return (T) OptionalDouble.empty();
            }
            return (T) OptionalDouble.of(dCastToDouble.doubleValue());
        }
        Object object = defaultJSONParser.parseObject(TypeUtils.unwrapOptional(type));
        if (object == null) {
            return (T) Optional.empty();
        }
        return (T) Optional.of(object);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
            return;
        }
        if (object instanceof Optional) {
            Optional<?> optional = (Optional) object;
            Object value = optional.isPresent() ? optional.get() : null;
            serializer.write(value);
            return;
        }
        if (object instanceof OptionalDouble) {
            OptionalDouble optional2 = (OptionalDouble) object;
            if (optional2.isPresent()) {
                double value2 = optional2.getAsDouble();
                serializer.write(Double.valueOf(value2));
                return;
            } else {
                serializer.writeNull();
                return;
            }
        }
        if (object instanceof OptionalInt) {
            OptionalInt optional3 = (OptionalInt) object;
            if (optional3.isPresent()) {
                int value3 = optional3.getAsInt();
                serializer.out.writeInt(value3);
                return;
            } else {
                serializer.writeNull();
                return;
            }
        }
        if (object instanceof OptionalLong) {
            OptionalLong optional4 = (OptionalLong) object;
            if (optional4.isPresent()) {
                long value4 = optional4.getAsLong();
                serializer.out.writeLong(value4);
                return;
            } else {
                serializer.writeNull();
                return;
            }
        }
        throw new JSONException("not support optional : " + object.getClass());
    }
}
