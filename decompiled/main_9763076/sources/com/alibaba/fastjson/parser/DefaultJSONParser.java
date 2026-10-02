package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONPathException;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.PropertyProcessable;
import com.alibaba.fastjson.parser.deserializer.ResolveFieldDeserializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultJSONParser implements Closeable {
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    private String[] autoTypeAccept;
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected FieldTypeResolver fieldTypeResolver;
    public final Object input;
    public final JSONLexer lexer;
    public int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    public final SymbolTable symbolTable;

    static {
        Class<?>[] classes = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, BigInteger.class, BigDecimal.class, String.class};
        for (Class<?> clazz : classes) {
            primitiveClasses.add(clazz);
        }
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.lexer.getLocale());
            this.dateFormat.setTimeZone(this.lexer.getTimeZone());
        }
        return this.dateFormat;
    }

    public DefaultJSONParser(String input, ParserConfig config) {
        this(input, new JSONScanner(input, JSON.DEFAULT_PARSER_FEATURE), config);
    }

    public DefaultJSONParser(String input, ParserConfig config, int features) {
        this(input, new JSONScanner(input, features), config);
    }

    public DefaultJSONParser(char[] input, int length, ParserConfig config, int features) {
        this(input, new JSONScanner(input, length, features), config);
    }

    public DefaultJSONParser(Object input, JSONLexer lexer, ParserConfig config) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.fieldTypeResolver = null;
        this.autoTypeAccept = null;
        this.lexer = lexer;
        this.input = input;
        this.config = config;
        this.symbolTable = config.symbolTable;
        int ch = lexer.getCurrent();
        if (ch == 123) {
            lexer.next();
            ((JSONLexerBase) lexer).token = 12;
        } else if (ch == 91) {
            lexer.next();
            ((JSONLexerBase) lexer).token = 14;
        } else {
            lexer.nextToken();
        }
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final Object parseObject(Map object, Object fieldName) {
        Object key;
        Object time;
        Object obj;
        FieldDeserializer fieldDeserializer;
        JSONLexer lexer = this.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken();
            return null;
        }
        if (lexer.token() == 13) {
            lexer.nextToken();
            return object;
        }
        if (lexer.token() != 12 && lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + lexer.tokenName() + ", " + lexer.info());
        }
        ParseContext context = this.context;
        try {
            Map map = object instanceof JSONObject ? ((JSONObject) object).getInnerMap() : object;
            boolean setContextFlag = false;
            while (true) {
                lexer.skipWhitespace();
                char ch = lexer.getCurrent();
                if (lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        lexer.next();
                        lexer.skipWhitespace();
                        ch = lexer.getCurrent();
                    }
                }
                boolean isObjectKey = false;
                if (ch == '\"') {
                    key = lexer.scanSymbol(this.symbolTable, '\"');
                    lexer.skipWhitespace();
                    if (lexer.getCurrent() != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos() + ", name " + key);
                    }
                } else {
                    if (ch == '}') {
                        lexer.next();
                        lexer.resetStringPosition();
                        lexer.nextToken();
                        if (!setContextFlag) {
                            if (this.context != null && fieldName == this.context.fieldName && object == this.context.object) {
                                context = this.context;
                            } else {
                                ParseContext contextR = setContext(object, fieldName);
                                if (context == null) {
                                    context = contextR;
                                }
                            }
                        }
                        setContext(context);
                        return object;
                    }
                    if (ch == '\'') {
                        if (!lexer.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("syntax error");
                        }
                        key = lexer.scanSymbol(this.symbolTable, '\'');
                        lexer.skipWhitespace();
                        if (lexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + lexer.pos());
                        }
                    } else {
                        if (ch == 26) {
                            throw new JSONException("syntax error");
                        }
                        if (ch == ',') {
                            throw new JSONException("syntax error");
                        }
                        if ((ch >= '0' && ch <= '9') || ch == '-') {
                            lexer.resetStringPosition();
                            lexer.scanNumber();
                            try {
                                key = lexer.token() == 2 ? lexer.integerValue() : lexer.decimalValue(true);
                                if (lexer.getCurrent() != ':') {
                                    throw new JSONException("parse number key error" + lexer.info());
                                }
                            } catch (NumberFormatException e) {
                                throw new JSONException("parse number key error" + lexer.info());
                            }
                        } else if (ch == '{' || ch == '[') {
                            lexer.nextToken();
                            key = parse();
                            isObjectKey = true;
                        } else {
                            if (!lexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                                throw new JSONException("syntax error");
                            }
                            key = lexer.scanSymbolUnQuoted(this.symbolTable);
                            lexer.skipWhitespace();
                            char ch2 = lexer.getCurrent();
                            if (ch2 != ':') {
                                throw new JSONException("expect ':' at " + lexer.pos() + ", actual " + ch2);
                            }
                        }
                    }
                }
                if (!isObjectKey) {
                    lexer.next();
                    lexer.skipWhitespace();
                }
                char ch3 = lexer.getCurrent();
                lexer.resetStringPosition();
                if (key == JSON.DEFAULT_TYPE_KEY && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    String typeName = lexer.scanSymbol(this.symbolTable, '\"');
                    if (lexer.isEnabled(Feature.IgnoreAutoType)) {
                        continue;
                    } else {
                        Class<?> clazz = (object == null || !object.getClass().getName().equals(typeName)) ? this.config.checkAutoType(typeName, null) : object.getClass();
                        if (clazz != null) {
                            lexer.nextToken(16);
                            if (lexer.token() != 13) {
                                setResolveStatus(2);
                                if (this.context != null && fieldName != null && !(fieldName instanceof Integer) && !(this.context.fieldName instanceof Integer)) {
                                    popContext();
                                }
                                if (object.size() <= 0) {
                                    Object objDeserialze = this.config.getDeserializer(clazz).deserialze(this, clazz, fieldName);
                                    setContext(context);
                                    return objDeserialze;
                                }
                                Object newObj = TypeUtils.cast((Object) object, (Class<Object>) clazz, this.config);
                                parseObject(newObj);
                                setContext(context);
                                return newObj;
                            }
                            lexer.nextToken(16);
                            Object instance = null;
                            try {
                                ObjectDeserializer deserializer = this.config.getDeserializer(clazz);
                                if (deserializer instanceof JavaBeanDeserializer) {
                                    JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
                                    instance = javaBeanDeserializer.createInstance(this, clazz);
                                    for (Object o : map.entrySet()) {
                                        Map.Entry entry = (Map.Entry) o;
                                        Object entryKey = entry.getKey();
                                        if ((entryKey instanceof String) && (fieldDeserializer = javaBeanDeserializer.getFieldDeserializer((String) entryKey)) != null) {
                                            fieldDeserializer.setValue(instance, entry.getValue());
                                        }
                                    }
                                }
                                if (instance == null) {
                                    instance = clazz == Cloneable.class ? new HashMap() : "java.util.Collections$EmptyMap".equals(typeName) ? Collections.emptyMap() : clazz.newInstance();
                                }
                                setContext(context);
                                return instance;
                            } catch (Exception e2) {
                                throw new JSONException("create instance error", e2);
                            }
                        }
                        map.put(JSON.DEFAULT_TYPE_KEY, typeName);
                    }
                } else {
                    if (key == "$ref" && context != null && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                        lexer.nextToken(4);
                        if (lexer.token() != 4) {
                            throw new JSONException("illegal ref, " + JSONToken.name(lexer.token()));
                        }
                        String ref = lexer.stringVal();
                        lexer.nextToken(13);
                        Object refValue = null;
                        if ("@".equals(ref)) {
                            if (this.context != null) {
                                ParseContext thisContext = this.context;
                                Object thisObj = thisContext.object;
                                if ((thisObj instanceof Object[]) || (thisObj instanceof Collection)) {
                                    refValue = thisObj;
                                } else if (thisContext.parent != null) {
                                    refValue = thisContext.parent.object;
                                }
                                obj = refValue;
                            } else {
                                obj = null;
                            }
                        } else if ("..".equals(ref)) {
                            if (context.object != null) {
                                obj = context.object;
                            } else {
                                addResolveTask(new ResolveTask(context, ref));
                                setResolveStatus(1);
                                obj = null;
                            }
                        } else if ("$".equals(ref)) {
                            ParseContext rootContext = context;
                            while (rootContext.parent != null) {
                                rootContext = rootContext.parent;
                            }
                            if (rootContext.object != null) {
                                refValue = rootContext.object;
                            } else {
                                addResolveTask(new ResolveTask(rootContext, ref));
                                setResolveStatus(1);
                            }
                            obj = refValue;
                        } else {
                            addResolveTask(new ResolveTask(context, ref));
                            setResolveStatus(1);
                            obj = null;
                        }
                        if (lexer.token() != 13) {
                            throw new JSONException("syntax error");
                        }
                        lexer.nextToken(16);
                        setContext(context);
                        return obj;
                    }
                    if (!setContextFlag) {
                        if (this.context != null && fieldName == this.context.fieldName && object == this.context.object) {
                            context = this.context;
                        } else {
                            ParseContext contextR2 = setContext(object, fieldName);
                            if (context == null) {
                                context = contextR2;
                            }
                            setContextFlag = true;
                        }
                    }
                    if (object.getClass() == JSONObject.class && key == null) {
                        key = "null";
                    }
                    if (ch3 == '\"') {
                        lexer.scanString();
                        String strValue = lexer.stringVal();
                        time = strValue;
                        if (lexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                            JSONScanner iso8601Lexer = new JSONScanner(strValue);
                            if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                time = iso8601Lexer.getCalendar().getTime();
                            }
                            iso8601Lexer.close();
                        }
                        map.put(key, time);
                    } else if ((ch3 >= '0' && ch3 <= '9') || ch3 == '-') {
                        lexer.scanNumber();
                        time = lexer.token() == 2 ? lexer.integerValue() : lexer.decimalValue(lexer.isEnabled(Feature.UseBigDecimal));
                        map.put(key, time);
                    } else if (ch3 == '[') {
                        lexer.nextToken();
                        JSONArray list = new JSONArray();
                        if (fieldName == null || fieldName.getClass() == Integer.class) {
                        }
                        if (fieldName == null) {
                            setContext(context);
                        }
                        parseArray(list, key);
                        map.put(key, lexer.isEnabled(Feature.UseObjectArray) ? list.toArray() : list);
                        if (lexer.token() == 13) {
                            lexer.nextToken();
                            setContext(context);
                            return object;
                        }
                        if (lexer.token() != 16) {
                            throw new JSONException("syntax error");
                        }
                    } else if (ch3 == '{') {
                        lexer.nextToken();
                        boolean parentIsArray = fieldName != null && fieldName.getClass() == Integer.class;
                        Map input = new JSONObject(lexer.isEnabled(Feature.OrderedField));
                        ParseContext ctxLocal = parentIsArray ? null : setContext(context, input, key);
                        Object obj2 = null;
                        boolean objParsed = false;
                        if (this.fieldTypeResolver != null) {
                            String resolveFieldName = key != null ? key.toString() : null;
                            Type fieldType = this.fieldTypeResolver.resolve(object, resolveFieldName);
                            if (fieldType != null) {
                                ObjectDeserializer fieldDeser = this.config.getDeserializer(fieldType);
                                obj2 = fieldDeser.deserialze(this, fieldType, key);
                                objParsed = true;
                            }
                        }
                        if (!objParsed) {
                            obj2 = parseObject(input, key);
                        }
                        if (ctxLocal != null && input != obj2) {
                            ctxLocal.object = object;
                        }
                        if (key != null) {
                            checkMapResolve(object, key.toString());
                        }
                        map.put(key, obj2);
                        if (parentIsArray) {
                            setContext(obj2, key);
                        }
                        if (lexer.token() == 13) {
                            lexer.nextToken();
                            setContext(context);
                            setContext(context);
                            return object;
                        }
                        if (lexer.token() != 16) {
                            throw new JSONException("syntax error, " + lexer.tokenName());
                        }
                        if (parentIsArray) {
                            popContext();
                        } else {
                            setContext(context);
                        }
                    } else {
                        lexer.nextToken();
                        Object value = parse();
                        map.put(key, value);
                        if (lexer.token() == 13) {
                            lexer.nextToken();
                            setContext(context);
                            return object;
                        }
                        if (lexer.token() != 16) {
                            throw new JSONException("syntax error, position at " + lexer.pos() + ", name " + key);
                        }
                    }
                    lexer.skipWhitespace();
                    char ch4 = lexer.getCurrent();
                    if (ch4 != ',') {
                        if (ch4 != '}') {
                            throw new JSONException("syntax error, position at " + lexer.pos() + ", name " + key);
                        }
                        lexer.next();
                        lexer.resetStringPosition();
                        lexer.nextToken();
                        setContext(time, key);
                        setContext(context);
                        return object;
                    }
                    lexer.next();
                }
            }
        } catch (Throwable th) {
            setContext(context);
            throw th;
        }
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public <T> T parseObject(Class<T> cls) {
        return (T) parseObject(cls, (Object) null);
    }

    public <T> T parseObject(Type type) {
        return (T) parseObject(type, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T parseObject(Type type, Object obj) {
        int i = this.lexer.token();
        if (i == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (i == 4) {
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            }
            if (type == char[].class) {
                String strStringVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strStringVal.toCharArray();
            }
        }
        try {
            return (T) this.config.getDeserializer(type).deserialze(this, type, obj);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable th) {
            throw new JSONException(th.getMessage(), th);
        }
    }

    public void parseArray(Class<?> clazz, Collection array) {
        parseArray((Type) clazz, array);
    }

    public void parseArray(Type type, Collection array) {
        parseArray(type, array, null);
    }

    public void parseArray(Type type, Collection array, Object fieldName) {
        ObjectDeserializer deserializer;
        Object objDeserialze;
        String value;
        int token = this.lexer.token();
        if (token == 21 || token == 22) {
            this.lexer.nextToken();
            token = this.lexer.token();
        }
        if (token != 14) {
            throw new JSONException("exepct '[', but " + JSONToken.name(token) + ", " + this.lexer.info());
        }
        if (Integer.TYPE == type) {
            deserializer = IntegerCodec.instance;
            this.lexer.nextToken(2);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            this.lexer.nextToken(4);
        } else {
            deserializer = this.config.getDeserializer(type);
            this.lexer.nextToken(deserializer.getFastMatchToken());
        }
        ParseContext context = this.context;
        setContext(array, fieldName);
        int i = 0;
        while (true) {
            try {
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (this.lexer.token() == 16) {
                        this.lexer.nextToken();
                    }
                }
                if (this.lexer.token() != 15) {
                    if (Integer.TYPE == type) {
                        Object val = IntegerCodec.instance.deserialze(this, null, null);
                        array.add(val);
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            value = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj = parse();
                            if (obj == null) {
                                value = null;
                            } else {
                                value = obj.toString();
                            }
                        }
                        array.add(value);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                            objDeserialze = null;
                        } else {
                            objDeserialze = deserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        array.add(objDeserialze);
                        checkListResolve(array);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i++;
                } else {
                    setContext(context);
                    this.lexer.nextToken(16);
                    return;
                }
            } catch (Throwable th) {
                setContext(context);
                throw th;
            }
        }
    }

    public Object[] parseArray(Type[] types) {
        Object value;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        if (this.lexer.token() != 14) {
            throw new JSONException("syntax error : " + this.lexer.tokenName());
        }
        Object[] list = new Object[types.length];
        if (types.length == 0) {
            this.lexer.nextToken(15);
            if (this.lexer.token() != 15) {
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(16);
            return new Object[0];
        }
        this.lexer.nextToken(2);
        for (int i = 0; i < types.length; i++) {
            if (this.lexer.token() == 8) {
                value = null;
                this.lexer.nextToken(16);
            } else {
                Type type = types[i];
                if (type == Integer.TYPE || type == Integer.class) {
                    if (this.lexer.token() == 2) {
                        value = Integer.valueOf(this.lexer.intValue());
                        this.lexer.nextToken(16);
                    } else {
                        Object value2 = parse();
                        value = TypeUtils.cast(value2, type, this.config);
                    }
                } else if (type == String.class) {
                    if (this.lexer.token() == 4) {
                        value = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                    } else {
                        Object value3 = parse();
                        value = TypeUtils.cast(value3, type, this.config);
                    }
                } else {
                    boolean isArray = false;
                    Class<?> componentType = null;
                    if (i == types.length - 1 && (type instanceof Class)) {
                        Class<?> clazz = (Class) type;
                        isArray = clazz.isArray();
                        componentType = clazz.getComponentType();
                    }
                    if (isArray && this.lexer.token() != 14) {
                        List<Object> varList = new ArrayList<>();
                        ObjectDeserializer derializer = this.config.getDeserializer(componentType);
                        int fastMatch = derializer.getFastMatchToken();
                        if (this.lexer.token() != 15) {
                            while (true) {
                                Object item = derializer.deserialze(this, type, null);
                                varList.add(item);
                                if (this.lexer.token() != 16) {
                                    break;
                                }
                                this.lexer.nextToken(fastMatch);
                            }
                            if (this.lexer.token() != 15) {
                                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                            }
                        }
                        value = TypeUtils.cast(varList, type, this.config);
                    } else {
                        value = this.config.getDeserializer(type).deserialze(this, type, Integer.valueOf(i));
                    }
                }
            }
            list[i] = value;
            if (this.lexer.token() == 15) {
                break;
            }
            if (this.lexer.token() != 16) {
                throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
            }
            if (i == types.length - 1) {
                this.lexer.nextToken(15);
            } else {
                this.lexer.nextToken(2);
            }
        }
        if (this.lexer.token() != 15) {
            throw new JSONException("syntax error");
        }
        this.lexer.nextToken(16);
        return list;
    }

    public void parseObject(Object object) {
        Object fieldValue;
        Class<?> clazz = object.getClass();
        JavaBeanDeserializer beanDeser = null;
        ObjectDeserializer deserizer = this.config.getDeserializer(clazz);
        if (deserizer instanceof JavaBeanDeserializer) {
            beanDeser = (JavaBeanDeserializer) deserizer;
        }
        if (this.lexer.token() != 12 && this.lexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
        while (true) {
            String key = this.lexer.scanSymbol(this.symbolTable);
            if (key == null) {
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                } else if (this.lexer.token() != 16 || !this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                }
            }
            FieldDeserializer fieldDeser = null;
            if (beanDeser != null) {
                fieldDeser = beanDeser.getFieldDeserializer(key);
            }
            if (fieldDeser == null) {
                if (!this.lexer.isEnabled(Feature.IgnoreNotMatch)) {
                    throw new JSONException("setter not found, class " + clazz.getName() + ", property " + key);
                }
                this.lexer.nextTokenWithColon();
                parse();
                if (this.lexer.token() == 13) {
                    this.lexer.nextToken();
                    return;
                }
            } else {
                Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
                Type fieldType = fieldDeser.fieldInfo.fieldType;
                if (fieldClass == Integer.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    fieldValue = IntegerCodec.instance.deserialze(this, fieldType, null);
                } else if (fieldClass == String.class) {
                    this.lexer.nextTokenWithColon(4);
                    fieldValue = StringCodec.deserialze(this);
                } else if (fieldClass == Long.TYPE) {
                    this.lexer.nextTokenWithColon(2);
                    fieldValue = LongCodec.instance.deserialze(this, fieldType, null);
                } else {
                    ObjectDeserializer fieldValueDeserializer = this.config.getDeserializer(fieldClass, fieldType);
                    this.lexer.nextTokenWithColon(fieldValueDeserializer.getFastMatchToken());
                    fieldValue = fieldValueDeserializer.deserialze(this, fieldType, null);
                }
                fieldDeser.setValue(object, fieldValue);
                if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                    this.lexer.nextToken(16);
                    return;
                }
            }
        }
    }

    public void acceptType(String typeName) {
        JSONLexer lexer = this.lexer;
        lexer.nextTokenWithColon();
        if (lexer.token() != 4) {
            throw new JSONException("type not match error");
        }
        if (typeName.equals(lexer.stringVal())) {
            lexer.nextToken();
            if (lexer.token() == 16) {
                lexer.nextToken();
                return;
            }
            return;
        }
        throw new JSONException("type not match error");
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int resolveStatus) {
        this.resolveStatus = resolveStatus;
    }

    public Object getObject(String path) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (path.equals(this.contextArray[i].toString())) {
                return this.contextArray[i].object;
            }
        }
        return null;
    }

    public void checkListResolve(Collection array) {
        if (this.resolveStatus == 1) {
            if (array instanceof List) {
                int index = array.size() - 1;
                List list = (List) array;
                ResolveTask task = getLastResolveTask();
                task.fieldDeserializer = new ResolveFieldDeserializer(this, list, index);
                task.ownerContext = this.context;
                setResolveStatus(0);
                return;
            }
            ResolveTask task2 = getLastResolveTask();
            task2.fieldDeserializer = new ResolveFieldDeserializer(array);
            task2.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public void checkMapResolve(Map object, Object fieldName) {
        if (this.resolveStatus == 1) {
            ResolveFieldDeserializer fieldResolver = new ResolveFieldDeserializer(object, fieldName);
            ResolveTask task = getLastResolveTask();
            task.fieldDeserializer = fieldResolver;
            task.ownerContext = this.context;
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map object) {
        return parseObject(object, (Object) null);
    }

    public JSONObject parseObject() {
        JSONObject object = new JSONObject(this.lexer.isEnabled(Feature.OrderedField));
        return (JSONObject) parseObject((Map) object);
    }

    public final void parseArray(Collection array) {
        parseArray(array, (Object) null);
    }

    public final void parseArray(Collection array, Object fieldName) {
        Object value;
        JSONLexer lexer = this.lexer;
        if (lexer.token() == 21 || lexer.token() == 22) {
            lexer.nextToken();
        }
        if (lexer.token() != 14) {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(lexer.token()) + ", pos " + lexer.pos() + ", fieldName " + fieldName);
        }
        lexer.nextToken(4);
        ParseContext context = this.context;
        setContext(array, fieldName);
        int i = 0;
        while (true) {
            try {
                if (lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (lexer.token() == 16) {
                        lexer.nextToken();
                    }
                }
                switch (lexer.token()) {
                    case 2:
                        value = lexer.integerValue();
                        lexer.nextToken(16);
                        break;
                    case 3:
                        if (lexer.isEnabled(Feature.UseBigDecimal)) {
                            value = lexer.decimalValue(true);
                        } else {
                            value = lexer.decimalValue(false);
                        }
                        lexer.nextToken(16);
                        break;
                    case 4:
                        String stringLiteral = lexer.stringVal();
                        lexer.nextToken(16);
                        if (lexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                            JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                            if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                value = iso8601Lexer.getCalendar().getTime();
                            } else {
                                value = stringLiteral;
                            }
                            iso8601Lexer.close();
                        } else {
                            value = stringLiteral;
                        }
                        break;
                    case 5:
                    case 9:
                    case 10:
                    case 11:
                    case 13:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 21:
                    case 22:
                    default:
                        value = parse();
                        break;
                    case 6:
                        value = Boolean.TRUE;
                        lexer.nextToken(16);
                        break;
                    case 7:
                        value = Boolean.FALSE;
                        lexer.nextToken(16);
                        break;
                    case 8:
                        value = null;
                        lexer.nextToken(4);
                        break;
                    case 12:
                        JSONObject object = new JSONObject(lexer.isEnabled(Feature.OrderedField));
                        value = parseObject(object, Integer.valueOf(i));
                        break;
                    case 14:
                        Collection items = new JSONArray();
                        parseArray(items, Integer.valueOf(i));
                        if (lexer.isEnabled(Feature.UseObjectArray)) {
                            value = items.toArray();
                        } else {
                            value = items;
                        }
                        break;
                    case 15:
                        lexer.nextToken(16);
                        setContext(context);
                        return;
                    case 20:
                        throw new JSONException("unclosed jsonArray");
                    case 23:
                        value = null;
                        lexer.nextToken(4);
                        break;
                }
                array.add(value);
                checkListResolve(array);
                if (lexer.token() == 16) {
                    lexer.nextToken(4);
                }
                i++;
            } catch (Throwable th) {
                setContext(context);
                throw th;
            }
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public void addResolveTask(ResolveTask task) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(task);
    }

    public ResolveTask getLastResolveTask() {
        return this.resolveTaskList.get(this.resolveTaskList.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return this.fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setContext(ParseContext context) {
        if (!this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = context;
        }
    }

    public void popContext() {
        if (!this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = this.context.parent;
            if (this.contextArrayIndex > 0) {
                this.contextArrayIndex--;
                this.contextArray[this.contextArrayIndex] = null;
            }
        }
    }

    public ParseContext setContext(Object object, Object fieldName) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, object, fieldName);
    }

    public ParseContext setContext(ParseContext parent, Object object, Object fieldName) {
        if (this.lexer.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        this.context = new ParseContext(parent, object, fieldName);
        addContext(this.context);
        return this.context;
    }

    private void addContext(ParseContext context) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        if (this.contextArray == null) {
            this.contextArray = new ParseContext[8];
        } else if (i >= this.contextArray.length) {
            int newLen = (this.contextArray.length * 3) / 2;
            ParseContext[] newArray = new ParseContext[newLen];
            System.arraycopy(this.contextArray, 0, newArray, 0, this.contextArray.length);
            this.contextArray = newArray;
        }
        this.contextArray[i] = context;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parse(Object fieldName) {
        JSONLexer lexer = this.lexer;
        switch (lexer.token()) {
            case 2:
                Number intValue = lexer.integerValue();
                lexer.nextToken();
                return intValue;
            case 3:
                Number value = lexer.decimalValue(lexer.isEnabled(Feature.UseBigDecimal));
                lexer.nextToken();
                return value;
            case 4:
                String stringLiteral = lexer.stringVal();
                lexer.nextToken(16);
                if (lexer.isEnabled(Feature.AllowISO8601DateFormat)) {
                    JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                    try {
                        if (iso8601Lexer.scanISO8601DateIfMatch()) {
                            return iso8601Lexer.getCalendar().getTime();
                        }
                    } finally {
                        iso8601Lexer.close();
                    }
                }
                return stringLiteral;
            case 5:
            case 10:
            case 11:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 24:
            case 25:
            default:
                throw new JSONException("syntax error, " + lexer.info());
            case 6:
                lexer.nextToken();
                return Boolean.TRUE;
            case 7:
                lexer.nextToken();
                return Boolean.FALSE;
            case 8:
                lexer.nextToken();
                return null;
            case 9:
                lexer.nextToken(18);
                if (lexer.token() != 18) {
                    throw new JSONException("syntax error");
                }
                lexer.nextToken(10);
                accept(10);
                long time = lexer.integerValue().longValue();
                accept(2);
                accept(11);
                return new Date(time);
            case 12:
                JSONObject object = new JSONObject(lexer.isEnabled(Feature.OrderedField));
                return parseObject(object, fieldName);
            case 14:
                JSONArray array = new JSONArray();
                parseArray(array, fieldName);
                return lexer.isEnabled(Feature.UseObjectArray) ? array.toArray() : array;
            case 18:
                String identifier = lexer.stringVal();
                if ("NaN".equals(identifier)) {
                    lexer.nextToken();
                    return null;
                }
                throw new JSONException("syntax error, " + lexer.info());
            case 20:
                if (lexer.isBlankInput()) {
                    return null;
                }
                throw new JSONException("unterminated json string, " + lexer.info());
            case 21:
                lexer.nextToken();
                HashSet<Object> set = new HashSet<>();
                parseArray(set, fieldName);
                return set;
            case 22:
                lexer.nextToken();
                TreeSet<Object> treeSet = new TreeSet<>();
                parseArray(treeSet, fieldName);
                return treeSet;
            case 23:
                lexer.nextToken();
                return null;
            case 26:
                byte[] bytes = lexer.bytesValue();
                lexer.nextToken();
                return bytes;
        }
    }

    public boolean isEnabled(Feature feature) {
        return this.lexer.isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int token) {
        JSONLexer lexer = this.lexer;
        if (lexer.token() == token) {
            lexer.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(lexer.token()));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer lexer = this.lexer;
        try {
            if (lexer.isEnabled(Feature.AutoCloseSource) && lexer.token() != 20) {
                throw new JSONException("not close json text, token : " + JSONToken.name(lexer.token()));
            }
            lexer.close();
        } catch (Throwable th) {
            lexer.close();
            throw th;
        }
    }

    public Object resolveReference(String ref) {
        if (this.contextArray == null) {
            return null;
        }
        for (int i = 0; i < this.contextArray.length && i < this.contextArrayIndex; i++) {
            ParseContext context = this.contextArray[i];
            if (context.toString().equals(ref)) {
                return context.object;
            }
        }
        return null;
    }

    public void handleResovleTask(Object value) {
        Object refValue;
        if (this.resolveTaskList != null) {
            int size = this.resolveTaskList.size();
            for (int i = 0; i < size; i++) {
                ResolveTask task = this.resolveTaskList.get(i);
                String ref = task.referenceValue;
                Object object = null;
                if (task.ownerContext != null) {
                    object = task.ownerContext.object;
                }
                if (ref.startsWith("$")) {
                    refValue = getObject(ref);
                    if (refValue == null) {
                        try {
                            refValue = JSONPath.eval(value, ref);
                        } catch (JSONPathException e) {
                        }
                    }
                } else {
                    refValue = task.context.object;
                }
                FieldDeserializer fieldDeser = task.fieldDeserializer;
                if (fieldDeser != null) {
                    if (refValue != null && refValue.getClass() == JSONObject.class && fieldDeser.fieldInfo != null && !Map.class.isAssignableFrom(fieldDeser.fieldInfo.fieldClass)) {
                        Object root = this.contextArray[0].object;
                        refValue = JSONPath.eval(root, ref);
                    }
                    fieldDeser.setValue(object, refValue);
                }
            }
        }
    }

    public static class ResolveTask {
        public final ParseContext context;
        public FieldDeserializer fieldDeserializer;
        public ParseContext ownerContext;
        public final String referenceValue;

        public ResolveTask(ParseContext context, String referenceValue) {
            this.context = context;
            this.referenceValue = referenceValue;
        }
    }

    public void parseExtra(Object object, String key) {
        Object value;
        JSONLexer lexer = this.lexer;
        lexer.nextTokenWithColon();
        Type type = null;
        if (this.extraTypeProviders != null) {
            for (ExtraTypeProvider extraProvider : this.extraTypeProviders) {
                type = extraProvider.getExtraType(object, key);
            }
        }
        if (type == null) {
            value = parse();
        } else {
            value = parseObject(type);
        }
        if (object instanceof ExtraProcessable) {
            ExtraProcessable extraProcessable = (ExtraProcessable) object;
            extraProcessable.processExtra(key, value);
            return;
        }
        if (this.extraProcessors != null) {
            for (ExtraProcessor process : this.extraProcessors) {
                process.processExtra(object, key, value);
            }
        }
        if (this.resolveStatus == 1) {
            this.resolveStatus = 0;
        }
    }

    public Object parse(PropertyProcessable object, Object fieldName) {
        String key;
        Object value;
        if (this.lexer.token() != 12) {
            String msg = "syntax error, expect {, actual " + this.lexer.tokenName();
            if (fieldName instanceof String) {
                msg = (msg + ", fieldName ") + fieldName;
            }
            String msg2 = (msg + ", ") + this.lexer.info();
            JSONArray array = new JSONArray();
            parseArray(array, fieldName);
            if (array.size() == 1) {
                Object first = array.get(0);
                if (first instanceof JSONObject) {
                    return (JSONObject) first;
                }
            }
            throw new JSONException(msg2);
        }
        ParseContext context = this.context;
        int i = 0;
        while (true) {
            try {
                this.lexer.skipWhitespace();
                char ch = this.lexer.getCurrent();
                if (this.lexer.isEnabled(Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        this.lexer.next();
                        this.lexer.skipWhitespace();
                        ch = this.lexer.getCurrent();
                    }
                }
                if (ch == '\"') {
                    key = this.lexer.scanSymbol(this.symbolTable, '\"');
                    this.lexer.skipWhitespace();
                    if (this.lexer.getCurrent() != ':') {
                        throw new JSONException("expect ':' at " + this.lexer.pos());
                    }
                } else {
                    if (ch == '}') {
                        this.lexer.next();
                        this.lexer.resetStringPosition();
                        this.lexer.nextToken(16);
                        setContext(context);
                        return object;
                    }
                    if (ch == '\'') {
                        if (!this.lexer.isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("syntax error");
                        }
                        key = this.lexer.scanSymbol(this.symbolTable, '\'');
                        this.lexer.skipWhitespace();
                        if (this.lexer.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + this.lexer.pos());
                        }
                    } else {
                        if (!this.lexer.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                            throw new JSONException("syntax error");
                        }
                        key = this.lexer.scanSymbolUnQuoted(this.symbolTable);
                        this.lexer.skipWhitespace();
                        char ch2 = this.lexer.getCurrent();
                        if (ch2 != ':') {
                            throw new JSONException("expect ':' at " + this.lexer.pos() + ", actual " + ch2);
                        }
                    }
                }
                this.lexer.next();
                this.lexer.skipWhitespace();
                this.lexer.getCurrent();
                this.lexer.resetStringPosition();
                if (key != JSON.DEFAULT_TYPE_KEY || this.lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    this.lexer.nextToken();
                    if (i != 0) {
                        setContext(context);
                    }
                    Type valueType = object.getType(key);
                    if (this.lexer.token() == 8) {
                        value = null;
                        this.lexer.nextToken();
                    } else {
                        value = parseObject(valueType, key);
                    }
                    object.apply(key, value);
                    setContext(context, value, key);
                    setContext(context);
                    int tok = this.lexer.token();
                    if (tok == 20 || tok == 15) {
                        break;
                    }
                    if (tok == 13) {
                        this.lexer.nextToken();
                        setContext(context);
                        return object;
                    }
                } else {
                    String typeName = this.lexer.scanSymbol(this.symbolTable, '\"');
                    Class<?> clazz = this.config.checkAutoType(typeName, null);
                    if (!Map.class.isAssignableFrom(clazz)) {
                        ObjectDeserializer deserializer = this.config.getDeserializer(clazz);
                        this.lexer.nextToken(16);
                        setResolveStatus(2);
                        if (context != null && !(fieldName instanceof Integer)) {
                            popContext();
                        }
                        Map map = (Map) deserializer.deserialze(this, clazz, fieldName);
                        setContext(context);
                        return map;
                    }
                    this.lexer.nextToken(16);
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        setContext(context);
                        return object;
                    }
                }
                i++;
            } catch (Throwable th) {
                setContext(context);
                throw th;
            }
        }
        setContext(context);
        return object;
    }
}
