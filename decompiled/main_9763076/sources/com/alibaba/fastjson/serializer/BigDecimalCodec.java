package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BigDecimalCodec implements ObjectDeserializer, ObjectSerializer {
    public static final BigDecimalCodec instance = new BigDecimalCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        String outText;
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        BigDecimal val = (BigDecimal) object;
        if (out.isEnabled(SerializerFeature.WriteBigDecimalAsPlain)) {
            outText = val.toPlainString();
        } else {
            outText = val.toString();
        }
        out.write(outText);
        if (out.isEnabled(SerializerFeature.WriteClassName) && fieldType != BigDecimal.class && val.scale() == 0) {
            out.write(46);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            return (T) deserialze(defaultJSONParser);
        } catch (Exception e) {
            throw new JSONException("parseDecimal error, field : " + obj, e);
        }
    }

    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            T t = (T) jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            return t;
        }
        if (jSONLexer.token() == 3) {
            T t2 = (T) jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            return t2;
        }
        Object obj = defaultJSONParser.parse();
        return (T) (obj == null ? null : TypeUtils.castToBigDecimal(obj));
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
