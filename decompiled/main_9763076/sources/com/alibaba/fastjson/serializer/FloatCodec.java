package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FloatCodec implements ObjectDeserializer, ObjectSerializer {
    public static FloatCodec instance = new FloatCodec();
    private NumberFormat decimalFormat;

    public FloatCodec() {
    }

    public FloatCodec(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    public FloatCodec(String decimalFormat) {
        this(new DecimalFormat(decimalFormat));
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        float floatValue = ((Float) object).floatValue();
        if (this.decimalFormat != null) {
            String floatText = this.decimalFormat.format(floatValue);
            out.write(floatText);
        } else {
            out.writeFloat(floatValue, true);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            return (T) deserialze(defaultJSONParser);
        } catch (Exception e) {
            throw new JSONException("parseLong error, field : " + obj, e);
        }
    }

    public static <T> T deserialze(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            String strNumberString = jSONLexer.numberString();
            jSONLexer.nextToken(16);
            return (T) Float.valueOf(Float.parseFloat(strNumberString));
        }
        if (jSONLexer.token() == 3) {
            float fFloatValue = jSONLexer.floatValue();
            jSONLexer.nextToken(16);
            return (T) Float.valueOf(fFloatValue);
        }
        Object obj = defaultJSONParser.parse();
        if (obj == null) {
            return null;
        }
        return (T) TypeUtils.castToFloat(obj);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
