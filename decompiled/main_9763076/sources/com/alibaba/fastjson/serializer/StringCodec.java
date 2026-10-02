package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StringCodec implements ObjectDeserializer, ObjectSerializer {
    public static StringCodec instance = new StringCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        write(serializer, (String) object);
    }

    public void write(JSONSerializer serializer, String value) {
        SerializeWriter out = serializer.out;
        if (value == null) {
            out.writeNull(SerializerFeature.WriteNullStringAsEmpty);
        } else {
            out.writeString(value);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == StringBuffer.class) {
            JSONLexer jSONLexer = defaultJSONParser.lexer;
            if (jSONLexer.token() == 4) {
                String strStringVal = jSONLexer.stringVal();
                jSONLexer.nextToken(16);
                return (T) new StringBuffer(strStringVal);
            }
            Object obj2 = defaultJSONParser.parse();
            if (obj2 != null) {
                return (T) new StringBuffer(obj2.toString());
            }
            return null;
        }
        if (type == StringBuilder.class) {
            JSONLexer jSONLexer2 = defaultJSONParser.lexer;
            if (jSONLexer2.token() == 4) {
                String strStringVal2 = jSONLexer2.stringVal();
                jSONLexer2.nextToken(16);
                return (T) new StringBuilder(strStringVal2);
            }
            Object obj3 = defaultJSONParser.parse();
            if (obj3 != null) {
                return (T) new StringBuilder(obj3.toString());
            }
            return null;
        }
        return (T) deserialze(defaultJSONParser);
    }

    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        JSONLexer lexer = defaultJSONParser.getLexer();
        if (lexer.token() == 4) {
            T t = (T) lexer.stringVal();
            lexer.nextToken(16);
            return t;
        }
        if (lexer.token() == 2) {
            T t2 = (T) lexer.numberString();
            lexer.nextToken(16);
            return t2;
        }
        Object obj = defaultJSONParser.parse();
        if (obj == null) {
            return null;
        }
        return (T) obj.toString();
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }
}
