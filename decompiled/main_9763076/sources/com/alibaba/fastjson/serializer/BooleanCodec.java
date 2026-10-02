package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BooleanCodec implements ObjectDeserializer, ObjectSerializer {
    public static final BooleanCodec instance = new BooleanCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        Boolean value = (Boolean) object;
        if (value == null) {
            out.writeNull(SerializerFeature.WriteNullBooleanAsFalse);
        } else if (value.booleanValue()) {
            out.write("true");
        } else {
            out.write(Bugly.SDK_IS_DEV);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        try {
            if (jSONLexer.token() == 6) {
                jSONLexer.nextToken(16);
                obj2 = (T) Boolean.TRUE;
            } else if (jSONLexer.token() == 7) {
                jSONLexer.nextToken(16);
                obj2 = (T) Boolean.FALSE;
            } else if (jSONLexer.token() == 2) {
                int iIntValue = jSONLexer.intValue();
                jSONLexer.nextToken(16);
                if (iIntValue == 1) {
                    obj2 = (T) Boolean.TRUE;
                } else {
                    obj2 = (T) Boolean.FALSE;
                }
            } else {
                Object obj3 = defaultJSONParser.parse();
                if (obj3 == null) {
                    return null;
                }
                obj2 = (T) TypeUtils.castToBoolean(obj3);
            }
            return type == AtomicBoolean.class ? (T) new AtomicBoolean(((Boolean) obj2).booleanValue()) : (T) obj2;
        } catch (Exception e) {
            throw new JSONException("parseBoolean error, field : " + obj, e);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 6;
    }
}
