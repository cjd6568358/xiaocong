package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LongCodec implements ObjectDeserializer, ObjectSerializer {
    public static LongCodec instance = new LongCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        long value = ((Long) object).longValue();
        out.writeLong(value);
        if (out.isEnabled(SerializerFeature.WriteClassName) && value <= 2147483647L && value >= -2147483648L && fieldType != Long.class && fieldType != Long.TYPE) {
            out.write(76);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Long lCastToLong;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        try {
            int i = jSONLexer.token();
            if (i == 2) {
                long jLongValue = jSONLexer.longValue();
                jSONLexer.nextToken(16);
                lCastToLong = Long.valueOf(jLongValue);
            } else {
                if (i == 12) {
                    JSONObject jSONObject = new JSONObject(true);
                    defaultJSONParser.parseObject((Map) jSONObject);
                    lCastToLong = TypeUtils.castToLong(jSONObject);
                } else {
                    lCastToLong = TypeUtils.castToLong(defaultJSONParser.parse());
                }
                if (lCastToLong == null) {
                    return null;
                }
            }
            return type == AtomicLong.class ? (T) new AtomicLong(lCastToLong.longValue()) : (T) lCastToLong;
        } catch (Exception e) {
            throw new JSONException("parseLong error, field : " + obj, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
