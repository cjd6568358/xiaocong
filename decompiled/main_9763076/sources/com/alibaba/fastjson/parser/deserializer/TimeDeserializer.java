package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import java.lang.reflect.Type;
import java.sql.Time;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TimeDeserializer implements ObjectDeserializer {
    public static final TimeDeserializer instance = new TimeDeserializer();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        long timeInMillis;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 16) {
            jSONLexer.nextToken(4);
            if (jSONLexer.token() != 4) {
                throw new JSONException("syntax error");
            }
            jSONLexer.nextTokenWithColon(2);
            if (jSONLexer.token() != 2) {
                throw new JSONException("syntax error");
            }
            long jLongValue = jSONLexer.longValue();
            jSONLexer.nextToken(13);
            if (jSONLexer.token() != 13) {
                throw new JSONException("syntax error");
            }
            jSONLexer.nextToken(16);
            return (T) new Time(jLongValue);
        }
        T t = (T) defaultJSONParser.parse();
        if (t == 0) {
            return null;
        }
        if (!(t instanceof Time)) {
            if (t instanceof Number) {
                return (T) new Time(((Number) t).longValue());
            }
            if (t instanceof String) {
                String str = (String) t;
                if (str.length() == 0) {
                    return null;
                }
                JSONScanner jSONScanner = new JSONScanner(str);
                if (jSONScanner.scanISO8601DateIfMatch()) {
                    timeInMillis = jSONScanner.getCalendar().getTimeInMillis();
                } else {
                    boolean z = true;
                    for (int i = 0; i < str.length(); i++) {
                        char cCharAt = str.charAt(i);
                        if (cCharAt < '0' || cCharAt > '9') {
                            z = false;
                            break;
                        }
                    }
                    if (!z) {
                        jSONScanner.close();
                        return (T) Time.valueOf(str);
                    }
                    timeInMillis = Long.parseLong(str);
                }
                jSONScanner.close();
                return (T) new Time(timeInMillis);
            }
            throw new JSONException("parse error");
        }
        return t;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
