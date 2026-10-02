package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AbstractDateDeserializer extends ContextObjectDeserializer implements ObjectDeserializer {
    protected abstract <T> T cast(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2);

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, null, 0);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ContextObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, String str, int i) {
        Object objValueOf;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            objValueOf = Long.valueOf(jSONLexer.longValue());
            jSONLexer.nextToken(16);
        } else if (jSONLexer.token() == 4) {
            String strStringVal = jSONLexer.stringVal();
            if (str != null) {
                SimpleDateFormat simpleDateFormat = null;
                try {
                    simpleDateFormat = new SimpleDateFormat(str, JSON.defaultLocale);
                } catch (IllegalArgumentException e) {
                    if (str.equals("yyyy-MM-ddTHH:mm:ss.SSS")) {
                        str = "yyyy-MM-dd'T'HH:mm:ss.SSS";
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    } else if (str.equals("yyyy-MM-ddTHH:mm:ss")) {
                        str = "yyyy-MM-dd'T'HH:mm:ss";
                        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    }
                }
                try {
                    objValueOf = simpleDateFormat.parse(strStringVal);
                } catch (ParseException e2) {
                    if (str.equals("yyyy-MM-dd'T'HH:mm:ss.SSS") && strStringVal.length() == 19) {
                        try {
                            objValueOf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(strStringVal);
                        } catch (ParseException e3) {
                            objValueOf = null;
                        }
                    } else {
                        objValueOf = null;
                    }
                }
            } else {
                objValueOf = null;
            }
            if (objValueOf == null) {
                objValueOf = strStringVal;
                jSONLexer.nextToken(16);
                if (jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner jSONScanner = new JSONScanner(strStringVal);
                    if (jSONScanner.scanISO8601DateIfMatch()) {
                        objValueOf = jSONScanner.getCalendar().getTime();
                    }
                    jSONScanner.close();
                }
            }
        } else if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            objValueOf = null;
        } else if (jSONLexer.token() == 12) {
            jSONLexer.nextToken();
            if (jSONLexer.token() == 4) {
                if (JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal())) {
                    jSONLexer.nextToken();
                    defaultJSONParser.accept(17);
                    Type typeCheckAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), null);
                    if (typeCheckAutoType != null) {
                        type = typeCheckAutoType;
                    }
                    defaultJSONParser.accept(4);
                    defaultJSONParser.accept(16);
                }
                jSONLexer.nextTokenWithColon(2);
                if (jSONLexer.token() == 2) {
                    long jLongValue = jSONLexer.longValue();
                    jSONLexer.nextToken();
                    objValueOf = Long.valueOf(jLongValue);
                    defaultJSONParser.accept(13);
                } else {
                    throw new JSONException("syntax error : " + jSONLexer.tokenName());
                }
            } else {
                throw new JSONException("syntax error");
            }
        } else if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser.setResolveStatus(0);
            defaultJSONParser.accept(16);
            if (jSONLexer.token() == 4) {
                if (!"val".equals(jSONLexer.stringVal())) {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken();
                defaultJSONParser.accept(17);
                objValueOf = defaultJSONParser.parse();
                defaultJSONParser.accept(13);
            } else {
                throw new JSONException("syntax error");
            }
        } else {
            objValueOf = defaultJSONParser.parse();
        }
        return (T) cast(defaultJSONParser, type, obj, objValueOf);
    }
}
