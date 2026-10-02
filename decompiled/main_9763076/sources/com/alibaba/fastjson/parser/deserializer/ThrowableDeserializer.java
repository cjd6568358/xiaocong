package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ThrowableDeserializer extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig mapping, Class<?> clazz) {
        super(mapping, clazz, clazz);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        Object obj2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser.setResolveStatus(0);
        } else if (jSONLexer.token() != 12) {
            throw new JSONException("syntax error");
        }
        Throwable th = null;
        Class<?> clsCheckAutoType = null;
        if (type != null && (type instanceof Class)) {
            Class<?> cls = (Class) type;
            if (Throwable.class.isAssignableFrom(cls)) {
                clsCheckAutoType = cls;
            }
        }
        String strStringVal = null;
        StackTraceElement[] stackTraceElementArr = null;
        Map<String, Object> map = null;
        while (true) {
            String strScanSymbol = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable());
            if (strScanSymbol == null) {
                if (jSONLexer.token() == 13) {
                    jSONLexer.nextToken(16);
                    break;
                }
                if (jSONLexer.token() != 16 || !jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            jSONLexer.nextTokenWithColon(4);
            if (JSON.DEFAULT_TYPE_KEY.equals(strScanSymbol)) {
                if (jSONLexer.token() == 4) {
                    clsCheckAutoType = defaultJSONParser.getConfig().checkAutoType(jSONLexer.stringVal(), Throwable.class);
                    jSONLexer.nextToken(16);
                } else {
                    throw new JSONException("syntax error");
                }
            } else if ("message".equals(strScanSymbol)) {
                if (jSONLexer.token() == 8) {
                    strStringVal = null;
                } else if (jSONLexer.token() == 4) {
                    strStringVal = jSONLexer.stringVal();
                } else {
                    throw new JSONException("syntax error");
                }
                jSONLexer.nextToken();
            } else if ("cause".equals(strScanSymbol)) {
                th = (Throwable) deserialze(defaultJSONParser, null, "cause");
            } else if ("stackTrace".equals(strScanSymbol)) {
                stackTraceElementArr = (StackTraceElement[]) defaultJSONParser.parseObject((Class) StackTraceElement[].class);
            } else {
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(strScanSymbol, defaultJSONParser.parse());
            }
            if (jSONLexer.token() == 13) {
                jSONLexer.nextToken(16);
                break;
            }
        }
        if (clsCheckAutoType == null) {
            obj2 = (T) new Exception(strStringVal, th);
        } else {
            if (!Throwable.class.isAssignableFrom(clsCheckAutoType)) {
                throw new JSONException("type not match, not Throwable. " + clsCheckAutoType.getName());
            }
            try {
                obj2 = (T) createException(strStringVal, th, clsCheckAutoType);
                if (obj2 == null) {
                    obj2 = (T) new Exception(strStringVal, th);
                }
            } catch (Exception e) {
                throw new JSONException("create instance error", e);
            }
        }
        if (stackTraceElementArr != null) {
            ((Throwable) obj2).setStackTrace(stackTraceElementArr);
        }
        if (map == null) {
            return (T) obj2;
        }
        JavaBeanDeserializer javaBeanDeserializer = null;
        if (clsCheckAutoType != null) {
            if (clsCheckAutoType == this.clazz) {
                javaBeanDeserializer = this;
            } else {
                ObjectDeserializer deserializer = defaultJSONParser.getConfig().getDeserializer(clsCheckAutoType);
                if (deserializer instanceof JavaBeanDeserializer) {
                    javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                }
            }
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            FieldDeserializer fieldDeserializer = javaBeanDeserializer.getFieldDeserializer(key);
            if (fieldDeserializer != null) {
                fieldDeserializer.setValue(obj2, value);
            }
        }
        return (T) obj2;
    }

    private Throwable createException(String message, Throwable cause, Class<?> exClass) throws Exception {
        Constructor<?> defaultConstructor = null;
        Constructor<?> messageConstructor = null;
        Constructor<?> causeConstructor = null;
        for (Constructor<?> constructor : exClass.getConstructors()) {
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == 0) {
                defaultConstructor = constructor;
            } else if (types.length == 1 && types[0] == String.class) {
                messageConstructor = constructor;
            } else if (types.length == 2 && types[0] == String.class && types[1] == Throwable.class) {
                causeConstructor = constructor;
            }
        }
        if (causeConstructor != null) {
            return (Throwable) causeConstructor.newInstance(message, cause);
        }
        if (messageConstructor != null) {
            return (Throwable) messageConstructor.newInstance(message);
        }
        if (defaultConstructor != null) {
            return (Throwable) defaultConstructor.newInstance(new Object[0]);
        }
        return null;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer, com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}
