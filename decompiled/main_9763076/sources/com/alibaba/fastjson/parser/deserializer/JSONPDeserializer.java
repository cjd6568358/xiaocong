package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexerBase;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONPDeserializer implements ObjectDeserializer {
    public static final JSONPDeserializer instance = new JSONPDeserializer();

    /* JADX WARN: Type inference failed for: r2v0, types: [T, com.alibaba.fastjson.JSONPObject] */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        int i;
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.getLexer();
        String strScanSymbolUnQuoted = jSONLexerBase.scanSymbolUnQuoted(defaultJSONParser.getSymbolTable());
        jSONLexerBase.nextToken();
        int i2 = jSONLexerBase.token();
        if (i2 == 25) {
            strScanSymbolUnQuoted = (strScanSymbolUnQuoted + ".") + jSONLexerBase.scanSymbolUnQuoted(defaultJSONParser.getSymbolTable());
            jSONLexerBase.nextToken();
            i2 = jSONLexerBase.token();
        }
        ?? r2 = (T) new JSONPObject(strScanSymbolUnQuoted);
        if (i2 != 10) {
            throw new JSONException("illegal jsonp : " + jSONLexerBase.info());
        }
        jSONLexerBase.nextToken();
        while (true) {
            r2.addParameter(defaultJSONParser.parse());
            i = jSONLexerBase.token();
            if (i != 16) {
                break;
            }
            jSONLexerBase.nextToken();
        }
        if (i == 11) {
            jSONLexerBase.nextToken();
            if (jSONLexerBase.token() == 24) {
                jSONLexerBase.nextToken();
            }
            return r2;
        }
        throw new JSONException("illegal jsonp : " + jSONLexerBase.info());
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 0;
    }
}
