package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaBeanDeserializer implements ObjectDeserializer {
    private final Map<String, FieldDeserializer> alterNameFieldDeserializers;
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private ConcurrentMap<String, Object> extraFieldDeserializers;
    private final FieldDeserializer[] fieldDeserializers;
    private transient long[] smartMatchHashArray;
    private transient short[] smartMatchHashArrayMapping;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz, Type type) {
        this(config, JavaBeanInfo.build(clazz, type, config.propertyNamingStrategy, config.fieldBased, config.compatibleWithJavaBean));
    }

    public JavaBeanDeserializer(ParserConfig config, JavaBeanInfo beanInfo) {
        this.clazz = beanInfo.clazz;
        this.beanInfo = beanInfo;
        Map<String, FieldDeserializer> alterNameFieldDeserializers = null;
        this.sortedFieldDeserializers = new FieldDeserializer[beanInfo.sortedFields.length];
        int size = beanInfo.sortedFields.length;
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo = beanInfo.sortedFields[i];
            FieldDeserializer fieldDeserializer = config.createFieldDeserializer(config, beanInfo, fieldInfo);
            this.sortedFieldDeserializers[i] = fieldDeserializer;
            for (String name : fieldInfo.alternateNames) {
                if (alterNameFieldDeserializers == null) {
                    alterNameFieldDeserializers = new HashMap<>();
                }
                alterNameFieldDeserializers.put(name, fieldDeserializer);
            }
        }
        this.alterNameFieldDeserializers = alterNameFieldDeserializers;
        this.fieldDeserializers = new FieldDeserializer[beanInfo.fields.length];
        int size2 = beanInfo.fields.length;
        for (int i2 = 0; i2 < size2; i2++) {
            this.fieldDeserializers[i2] = getFieldDeserializer(beanInfo.fields[i2].name);
        }
    }

    public FieldDeserializer getFieldDeserializer(String key) {
        return getFieldDeserializer(key, null);
    }

    public FieldDeserializer getFieldDeserializer(String key, int[] setFlags) {
        if (key == null) {
            return null;
        }
        int low = 0;
        int high = this.sortedFieldDeserializers.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            String fieldName = this.sortedFieldDeserializers[mid].fieldInfo.name;
            int cmp = fieldName.compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                if (isSetFlag(mid, setFlags)) {
                    return null;
                }
                return this.sortedFieldDeserializers[mid];
            }
        }
        if (this.alterNameFieldDeserializers != null) {
            return this.alterNameFieldDeserializers.get(key);
        }
        return null;
    }

    static boolean isSetFlag(int i, int[] setFlags) {
        if (setFlags == null) {
            return false;
        }
        int flagIndex = i / 32;
        int bitIndex = i % 32;
        return flagIndex < setFlags.length && (setFlags[flagIndex] & (1 << bitIndex)) != 0;
    }

    public Object createInstance(DefaultJSONParser parser, Type type) {
        Object object;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            Class<?> clazz = (Class) type;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            JSONObject obj = new JSONObject();
            return Proxy.newProxyInstance(loader, new Class[]{clazz}, obj);
        }
        if (this.beanInfo.defaultConstructor == null && this.beanInfo.factoryMethod == null) {
            return null;
        }
        if (this.beanInfo.factoryMethod != null && this.beanInfo.defaultConstructorParameterSize > 0) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.defaultConstructor;
            if (this.beanInfo.defaultConstructorParameterSize == 0) {
                if (constructor != null) {
                    object = constructor.newInstance(new Object[0]);
                } else {
                    object = this.beanInfo.factoryMethod.invoke(null, new Object[0]);
                }
            } else {
                ParseContext context = parser.getContext();
                if (context == null || context.object == null) {
                    throw new JSONException("can't create non-static inner class instance.");
                }
                if (type instanceof Class) {
                    String typeName = ((Class) type).getName();
                    int lastIndex = typeName.lastIndexOf(36);
                    String parentClassName = typeName.substring(0, lastIndex);
                    Object ctxObj = context.object;
                    String parentName = ctxObj.getClass().getName();
                    Object param = null;
                    if (!parentName.equals(parentClassName)) {
                        ParseContext parentContext = context.parent;
                        if (parentContext != null && parentContext.object != null && (("java.util.ArrayList".equals(parentName) || "java.util.List".equals(parentName) || "java.util.Collection".equals(parentName) || "java.util.Map".equals(parentName) || "java.util.HashMap".equals(parentName)) && parentContext.object.getClass().getName().equals(parentClassName))) {
                            param = parentContext.object;
                        }
                    } else {
                        param = ctxObj;
                    }
                    if (param == null) {
                        throw new JSONException("can't create non-static inner class instance.");
                    }
                    object = constructor.newInstance(param);
                } else {
                    throw new JSONException("can't create non-static inner class instance.");
                }
            }
            if (parser != null && parser.lexer.isEnabled(Feature.InitStringFieldAsEmpty)) {
                for (FieldInfo fieldInfo : this.beanInfo.fields) {
                    if (fieldInfo.fieldClass == String.class) {
                        try {
                            fieldInfo.set(object, Constants.MAIN_VERSION_TAG);
                        } catch (Exception e) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                        }
                    }
                }
            }
            return object;
        } catch (JSONException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e3);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        return (T) deserialze(defaultJSONParser, type, obj, 0);
    }

    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, int i) {
        return (T) deserialze(defaultJSONParser, type, obj, null, i, null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2) {
        Enum<?> enumScanEnum;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 14) {
            throw new JSONException(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_ERROR);
        }
        T t = (T) createInstance(defaultJSONParser, type);
        int i = 0;
        int length = this.sortedFieldDeserializers.length;
        while (i < length) {
            char c = i == length + (-1) ? ']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[i];
            Class<?> cls = fieldDeserializer.fieldInfo.fieldClass;
            if (cls == Integer.TYPE) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanInt(c));
            } else if (cls == String.class) {
                fieldDeserializer.setValue((Object) t, jSONLexer.scanString(c));
            } else if (cls == Long.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanLong(c));
            } else if (cls.isEnum()) {
                char current = jSONLexer.getCurrent();
                if (current == '\"' || current == 'n') {
                    enumScanEnum = jSONLexer.scanEnum(cls, defaultJSONParser.getSymbolTable(), c);
                } else if (current >= '0' && current <= '9') {
                    enumScanEnum = ((EnumDeserializer) ((DefaultFieldDeserializer) fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(jSONLexer.scanInt(c));
                } else {
                    enumScanEnum = scanEnum(jSONLexer, c);
                }
                fieldDeserializer.setValue(t, enumScanEnum);
            } else if (cls == Boolean.TYPE) {
                fieldDeserializer.setValue(t, jSONLexer.scanBoolean(c));
            } else if (cls == Float.TYPE) {
                fieldDeserializer.setValue(t, Float.valueOf(jSONLexer.scanFloat(c)));
            } else if (cls == Double.TYPE) {
                fieldDeserializer.setValue(t, Double.valueOf(jSONLexer.scanDouble(c)));
            } else if (cls == Date.class && jSONLexer.getCurrent() == '1') {
                fieldDeserializer.setValue(t, new Date(jSONLexer.scanLong(c)));
            } else if (cls == BigDecimal.class) {
                fieldDeserializer.setValue(t, jSONLexer.scanDecimal(c));
            } else {
                jSONLexer.nextToken(14);
                fieldDeserializer.setValue(t, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType, fieldDeserializer.fieldInfo.name));
                if (jSONLexer.token() == 15) {
                    break;
                }
                check(jSONLexer, c == ']' ? 15 : 16);
            }
            i++;
        }
        jSONLexer.nextToken(16);
        return t;
    }

    protected void check(JSONLexer lexer, int token) {
        if (lexer.token() != token) {
            throw new JSONException("syntax error");
        }
    }

    protected Enum<?> scanEnum(JSONLexer lexer, char seperator) {
        throw new JSONException("illegal enum. " + lexer.info());
    }

    /* JADX WARN: Code duplicated, block: B:110:0x0219 A[PHI: r31 r41 r60
  0x0219: PHI (r31v1 java.lang.Object) = 
  (r31v0 java.lang.Object)
  (r31v2 java.lang.Object)
  (r31v2 java.lang.Object)
  (r31v3 java.lang.Object)
  (r31v3 java.lang.Object)
  (r31v4 java.lang.Object)
  (r31v4 java.lang.Object)
  (r31v5 java.lang.Object)
  (r31v5 java.lang.Object)
  (r31v6 java.lang.Object)
  (r31v6 java.lang.Object)
  (r31v0 java.lang.Object)
  (r31v7 java.lang.Object)
  (r31v7 java.lang.Object)
  (r31v8 java.lang.Object)
  (r31v8 java.lang.Object)
  (r31v9 java.lang.Object)
  (r31v9 java.lang.Object)
  (r31v0 java.lang.Object)
  (r31v10 java.lang.Object)
  (r31v10 java.lang.Object)
  (r31v11 java.lang.Object)
  (r31v11 java.lang.Object)
  (r31v12 java.lang.Object)
  (r31v12 java.lang.Object)
  (r31v13 java.lang.Object)
  (r31v13 java.lang.Object)
  (r31v14 java.lang.Object)
  (r31v14 java.lang.Object)
 binds: [B:102:0x01ef, B:126:0x0266, B:109:0x0215, B:137:0x0298, B:135:0x028e, B:181:0x035e, B:179:0x0353, B:191:0x038d, B:189:0x0382, B:201:0x03bc, B:199:0x03b1, B:244:0x048b, B:240:0x047b, B:238:0x0470, B:232:0x0456, B:230:0x044b, B:224:0x0431, B:222:0x0426, B:211:0x03e4, B:216:0x040c, B:214:0x0401, B:171:0x032f, B:169:0x0324, B:163:0x030a, B:161:0x02ff, B:155:0x02e6, B:153:0x02db, B:145:0x02bc, B:143:0x02b1] A[DONT_GENERATE, DONT_INLINE]
  0x0219: PHI (r41v1 boolean) = 
  (r41v0 boolean)
  (r41v0 boolean)
  (r41v2 boolean)
  (r41v0 boolean)
  (r41v3 boolean)
  (r41v0 boolean)
  (r41v4 boolean)
  (r41v0 boolean)
  (r41v5 boolean)
  (r41v0 boolean)
  (r41v6 boolean)
  (r41v7 boolean)
  (r41v0 boolean)
  (r41v8 boolean)
  (r41v0 boolean)
  (r41v9 boolean)
  (r41v0 boolean)
  (r41v10 boolean)
  (r41v0 boolean)
  (r41v0 boolean)
  (r41v11 boolean)
  (r41v0 boolean)
  (r41v12 boolean)
  (r41v0 boolean)
  (r41v13 boolean)
  (r41v0 boolean)
  (r41v14 boolean)
  (r41v0 boolean)
  (r41v15 boolean)
 binds: [B:102:0x01ef, B:126:0x0266, B:109:0x0215, B:137:0x0298, B:135:0x028e, B:181:0x035e, B:179:0x0353, B:191:0x038d, B:189:0x0382, B:201:0x03bc, B:199:0x03b1, B:244:0x048b, B:240:0x047b, B:238:0x0470, B:232:0x0456, B:230:0x044b, B:224:0x0431, B:222:0x0426, B:211:0x03e4, B:216:0x040c, B:214:0x0401, B:171:0x032f, B:169:0x0324, B:163:0x030a, B:161:0x02ff, B:155:0x02e6, B:153:0x02db, B:145:0x02bc, B:143:0x02b1] A[DONT_GENERATE, DONT_INLINE]
  0x0219: PHI (r60v1 boolean) = 
  (r60v0 boolean)
  (r60v0 boolean)
  (r60v2 boolean)
  (r60v0 boolean)
  (r60v3 boolean)
  (r60v0 boolean)
  (r60v4 boolean)
  (r60v0 boolean)
  (r60v5 boolean)
  (r60v0 boolean)
  (r60v6 boolean)
  (r60v0 boolean)
  (r60v0 boolean)
  (r60v7 boolean)
  (r60v0 boolean)
  (r60v8 boolean)
  (r60v0 boolean)
  (r60v9 boolean)
  (r60v0 boolean)
  (r60v0 boolean)
  (r60v10 boolean)
  (r60v0 boolean)
  (r60v11 boolean)
  (r60v0 boolean)
  (r60v12 boolean)
  (r60v0 boolean)
  (r60v13 boolean)
  (r60v0 boolean)
  (r60v14 boolean)
 binds: [B:102:0x01ef, B:126:0x0266, B:109:0x0215, B:137:0x0298, B:135:0x028e, B:181:0x035e, B:179:0x0353, B:191:0x038d, B:189:0x0382, B:201:0x03bc, B:199:0x03b1, B:244:0x048b, B:240:0x047b, B:238:0x0470, B:232:0x0456, B:230:0x044b, B:224:0x0431, B:222:0x0426, B:211:0x03e4, B:216:0x040c, B:214:0x0401, B:171:0x032f, B:169:0x0324, B:163:0x030a, B:161:0x02ff, B:155:0x02e6, B:153:0x02db, B:145:0x02bc, B:143:0x02b1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:111:0x021b A[Catch: all -> 0x04e3, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:113:0x0227 A[Catch: all -> 0x04e3, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:245:0x048f  */
    /* JADX WARN: Code duplicated, block: B:247:0x0495 A[Catch: all -> 0x04e3, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:252:0x04a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:291:0x05b9 A[Catch: all -> 0x04e3, TRY_ENTER, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:293:0x05c1 A[Catch: all -> 0x04e3, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:297:0x05d2 A[Catch: all -> 0x04e3, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:299:0x05eb A[Catch: all -> 0x04e3, TryCatch #2 {all -> 0x04e3, blocks: (B:98:0x01ca, B:100:0x01d3, B:103:0x01f1, B:129:0x0270, B:141:0x02a3, B:144:0x02b7, B:128:0x026a, B:149:0x02c7, B:151:0x02cd, B:154:0x02e1, B:159:0x02f1, B:162:0x0305, B:167:0x0316, B:170:0x032a, B:173:0x0335, B:183:0x0364, B:193:0x0393, B:203:0x03c2, B:205:0x03c8, B:208:0x03d8, B:210:0x03e0, B:212:0x03e6, B:215:0x0407, B:220:0x0418, B:223:0x042c, B:228:0x043d, B:231:0x0451, B:236:0x0462, B:239:0x0476, B:242:0x0481, B:197:0x039f, B:200:0x03b7, B:187:0x0370, B:190:0x0388, B:177:0x0341, B:180:0x0359, B:133:0x027c, B:136:0x0293, B:107:0x0203, B:125:0x0261, B:111:0x021b, B:113:0x0227, B:115:0x0231, B:247:0x0495, B:253:0x04a9, B:255:0x04b8, B:257:0x04c6, B:258:0x04cc, B:260:0x04db, B:261:0x04e2, B:286:0x0594, B:264:0x04e8, B:266:0x04f2, B:268:0x04fe, B:269:0x0505, B:270:0x0519, B:273:0x0525, B:275:0x052b, B:276:0x0532, B:278:0x0538, B:279:0x053f, B:280:0x0554, B:283:0x0562, B:284:0x0577, B:285:0x0593, B:291:0x05b9, B:295:0x05c5, B:297:0x05d2, B:299:0x05eb, B:304:0x0604, B:306:0x0616, B:307:0x062e, B:309:0x0640, B:311:0x0648, B:301:0x05f5, B:303:0x05fd, B:315:0x066a, B:316:0x0672, B:293:0x05c1, B:319:0x0677, B:321:0x067d), top: B:474:0x01ca }] */
    /* JADX WARN: Code duplicated, block: B:318:0x0675 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:326:0x06a4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:327:0x06a6 A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:330:0x06b9 A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:333:0x06ca A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:334:0x06cc A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:336:0x06d7 A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:340:0x06eb A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:341:0x06ed A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:343:0x06f3 A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:352:0x0715 A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:353:0x071f A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:355:0x072f A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:358:0x073c A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:362:0x074d A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:364:0x0755 A[Catch: all -> 0x019a, TryCatch #5 {all -> 0x019a, blocks: (B:18:0x0049, B:20:0x0050, B:26:0x006a, B:28:0x007a, B:30:0x0084, B:34:0x008c, B:43:0x00ae, B:51:0x00ca, B:53:0x00d4, B:58:0x00e8, B:60:0x00f0, B:62:0x00fe, B:64:0x010a, B:75:0x0133, B:77:0x013b, B:82:0x0152, B:84:0x017a, B:85:0x0185, B:86:0x0199, B:72:0x012a, B:92:0x01a9, B:94:0x01b0, B:95:0x01b5, B:119:0x023e, B:121:0x0244, B:368:0x077e, B:370:0x0788, B:371:0x0791, B:373:0x0798, B:375:0x07a2, B:377:0x07b8, B:379:0x07c2, B:381:0x07c8, B:382:0x07ce, B:384:0x07d4, B:385:0x07da, B:387:0x07e0, B:388:0x07e7, B:390:0x07ed, B:391:0x07f3, B:393:0x07f9, B:394:0x0800, B:396:0x0806, B:399:0x080f, B:378:0x07bd, B:434:0x08b5, B:436:0x08bd, B:438:0x08cb, B:439:0x08d3, B:441:0x08d9, B:443:0x08ed, B:450:0x0940, B:447:0x092b, B:449:0x0933, B:457:0x095e, B:458:0x0982, B:445:0x08fa, B:446:0x092a, B:402:0x081d, B:405:0x0838, B:407:0x0844, B:409:0x0850, B:411:0x085a, B:413:0x0860, B:414:0x0866, B:416:0x086c, B:417:0x0872, B:419:0x0878, B:420:0x087f, B:422:0x0885, B:423:0x088b, B:425:0x0891, B:426:0x0898, B:428:0x089e, B:431:0x08a7, B:410:0x0855, B:451:0x0944, B:460:0x0984, B:465:0x099e, B:466:0x09a7, B:322:0x0687, B:324:0x0695, B:327:0x06a6, B:328:0x06b1, B:330:0x06b9, B:332:0x06c1, B:362:0x074d, B:364:0x0755, B:366:0x075c, B:367:0x077d, B:334:0x06cc, B:336:0x06d7, B:337:0x06e2, B:341:0x06ed, B:343:0x06f3, B:345:0x06f9, B:347:0x06ff, B:349:0x0705, B:351:0x070b, B:352:0x0715, B:353:0x071f, B:355:0x072f, B:357:0x0737, B:358:0x073c, B:360:0x0744, B:361:0x074c), top: B:479:0x0045, inners: #0, #1, #3 }] */
    /* JADX WARN: Code duplicated, block: B:468:0x09ac  */
    /* JADX WARN: Code duplicated, block: B:484:0x0231 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:486:0x066a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:488:0x05fd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:489:0x06c1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:490:0x06e9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:491:0x0737 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:492:0x0744 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:493:0x075c A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:509:0x026a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:510:0x026a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:89:0x019d  */
    protected <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj, Object obj2, int i, int[] iArr) throws Throwable {
        HashMap map;
        Object[] objArr;
        String strStringVal;
        int i2;
        if (type == JSON.class || type == JSONObject.class) {
            return (T) defaultJSONParser.parse();
        }
        JSONLexerBase jSONLexerBase = (JSONLexerBase) defaultJSONParser.lexer;
        ParserConfig config = defaultJSONParser.getConfig();
        int i3 = jSONLexerBase.token();
        if (i3 == 8) {
            jSONLexerBase.nextToken(16);
            return null;
        }
        ParseContext context = defaultJSONParser.getContext();
        if (obj2 != null && context != null) {
            context = context.parent;
        }
        ParseContext context2 = null;
        try {
            if (i3 == 13) {
                jSONLexerBase.nextToken(16);
                if (obj2 == null) {
                    obj2 = createInstance(defaultJSONParser, type);
                }
                if (0 != 0) {
                    context2.object = obj2;
                }
                defaultJSONParser.setContext(context);
                return (T) obj2;
            }
            if (i3 == 14) {
                int i4 = Feature.SupportArrayToBean.mask;
                if (((this.beanInfo.parserFeatures & i4) == 0 && !jSONLexerBase.isEnabled(Feature.SupportArrayToBean) && (i & i4) == 0) ? false : true) {
                    T t = (T) deserialzeArrayMapping(defaultJSONParser, type, obj, obj2);
                    if (0 != 0) {
                        context2.object = obj2;
                    }
                    defaultJSONParser.setContext(context);
                    return t;
                }
            }
            if (i3 != 12 && i3 != 16) {
                if (jSONLexerBase.isBlankInput()) {
                    if (0 != 0) {
                        context2.object = obj2;
                    }
                    defaultJSONParser.setContext(context);
                    return null;
                }
                if (i3 == 4) {
                    String strStringVal2 = jSONLexerBase.stringVal();
                    if (strStringVal2.length() == 0) {
                        jSONLexerBase.nextToken();
                        if (0 != 0) {
                            context2.object = obj2;
                        }
                        defaultJSONParser.setContext(context);
                        return null;
                    }
                    if (this.beanInfo.jsonType != null) {
                        for (Class<?> cls : this.beanInfo.jsonType.seeAlso()) {
                            if (Enum.class.isAssignableFrom(cls)) {
                                try {
                                    T t2 = (T) Enum.valueOf(cls, strStringVal2);
                                    if (0 != 0) {
                                        context2.object = obj2;
                                    }
                                    defaultJSONParser.setContext(context);
                                    return t2;
                                } catch (IllegalArgumentException e) {
                                }
                            }
                        }
                    }
                } else if (i3 == 5) {
                    jSONLexerBase.getCalendar();
                }
                if (i3 != 14 || jSONLexerBase.getCurrent() != ']') {
                    StringBuffer stringBufferAppend = new StringBuffer().append("syntax error, expect {, actual ").append(jSONLexerBase.tokenName()).append(", pos ").append(jSONLexerBase.pos());
                    if (obj instanceof String) {
                        stringBufferAppend.append(", fieldName ").append(obj);
                    }
                    stringBufferAppend.append(", fastjson-version ").append(JSON.VERSION);
                    throw new JSONException(stringBufferAppend.toString());
                }
                jSONLexerBase.next();
                jSONLexerBase.nextToken();
                if (0 != 0) {
                    context2.object = obj2;
                }
                defaultJSONParser.setContext(context);
                return null;
            }
            if (defaultJSONParser.resolveStatus == 2) {
                defaultJSONParser.resolveStatus = 0;
            }
            String str = this.beanInfo.typeKey;
            int i5 = 0;
            HashMap map2 = null;
            while (true) {
                String strScanSymbol = null;
                FieldDeserializer fieldDeserializer = null;
                FieldInfo fieldInfo = null;
                Class<?> cls2 = null;
                JSONField annotation = null;
                try {
                    if (i5 < this.sortedFieldDeserializers.length) {
                        fieldDeserializer = this.sortedFieldDeserializers[i5];
                        fieldInfo = fieldDeserializer.fieldInfo;
                        cls2 = fieldInfo.fieldClass;
                        annotation = fieldInfo.getAnnotation();
                    }
                    boolean z = false;
                    boolean z2 = false;
                    Object objValueOf = null;
                    if (fieldDeserializer == null) {
                        if (!z) {
                            strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                            if (strScanSymbol == null) {
                                i2 = jSONLexerBase.token();
                                if (i2 == 13) {
                                    jSONLexerBase.nextToken(16);
                                    map = map2;
                                    break;
                                }
                                if (i2 != 16) {
                                }
                            }
                            if ("$ref" != strScanSymbol) {
                            }
                            if (str == null) {
                            }
                            jSONLexerBase.nextTokenWithColon(4);
                            if (jSONLexerBase.token() != 4) {
                                throw new JSONException("syntax error");
                            }
                            strStringVal = jSONLexerBase.stringVal();
                            jSONLexerBase.nextToken(16);
                            if (strStringVal.equals(this.beanInfo.typeName)) {
                            }
                            if (jSONLexerBase.token() == 13) {
                                jSONLexerBase.nextToken();
                                map = map2;
                                break;
                            }
                            map = map2;
                        }
                        if (obj2 == null) {
                            map = map2;
                        } else {
                            map = map2;
                        }
                        if (!z) {
                            if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                if (jSONLexerBase.token() == 13) {
                                    jSONLexerBase.nextToken();
                                    break;
                                }
                            } else {
                                if (jSONLexerBase.token() == 17) {
                                    throw new JSONException("syntax error, unexpect token ':'");
                                }
                                if (jSONLexerBase.token() != 16) {
                                    if (jSONLexerBase.token() == 13) {
                                        jSONLexerBase.nextToken(16);
                                        break;
                                    }
                                    if (jSONLexerBase.token() != 18) {
                                    }
                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                }
                                continue;
                            }
                        } else {
                            if (z2) {
                                if (obj2 == null) {
                                    map.put(fieldInfo.name, objValueOf);
                                } else if (objValueOf != null) {
                                    fieldDeserializer.setValue(obj2, objValueOf);
                                } else if (cls2 != Integer.TYPE) {
                                    fieldDeserializer.setValue(obj2, objValueOf);
                                }
                                if (iArr != null) {
                                    int i6 = i5 / 32;
                                    iArr[i6] = iArr[i6] | (1 >> (i5 % 32));
                                }
                                if (jSONLexerBase.matchStat != 4) {
                                    break;
                                }
                                break;
                                break;
                            }
                            fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                            if (jSONLexerBase.token() != 16) {
                                if (jSONLexerBase.token() == 13) {
                                    jSONLexerBase.nextToken(16);
                                    break;
                                }
                                if (jSONLexerBase.token() != 18) {
                                }
                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                            }
                            continue;
                        }
                    } else {
                        char[] cArr = fieldInfo.name_chars;
                        if (cls2 != Integer.TYPE && cls2 != Integer.class) {
                            if (cls2 != Long.TYPE && cls2 != Long.class) {
                                if (cls2 != String.class) {
                                    if (cls2 != Date.class || fieldInfo.format != null) {
                                        if (cls2 != BigDecimal.class) {
                                            if (cls2 != BigInteger.class) {
                                                if (cls2 != Boolean.TYPE && cls2 != Boolean.class) {
                                                    if (cls2 != Float.TYPE && cls2 != Float.class) {
                                                        if (cls2 != Double.TYPE && cls2 != Double.class) {
                                                            if (!cls2.isEnum() || !(defaultJSONParser.getConfig().getDeserializer(cls2) instanceof EnumDeserializer) || (annotation != null && annotation.deserializeUsing() != Void.class)) {
                                                                if (cls2 != int[].class) {
                                                                    if (cls2 != float[].class) {
                                                                        if (cls2 == float[][].class) {
                                                                            objValueOf = jSONLexerBase.scanFieldFloatArray2(cArr);
                                                                            if (jSONLexerBase.matchStat > 0) {
                                                                                z = true;
                                                                                z2 = true;
                                                                            } else if (jSONLexerBase.matchStat == -2) {
                                                                                map = map2;
                                                                            }
                                                                        } else if (jSONLexerBase.matchField(cArr)) {
                                                                            z = true;
                                                                        } else {
                                                                            map = map2;
                                                                        }
                                                                        if (!z) {
                                                                            strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                                            if (strScanSymbol == null) {
                                                                                i2 = jSONLexerBase.token();
                                                                                if (i2 == 13) {
                                                                                    jSONLexerBase.nextToken(16);
                                                                                    map = map2;
                                                                                    break;
                                                                                }
                                                                                if (i2 != 16) {
                                                                                }
                                                                            }
                                                                            if ("$ref" != strScanSymbol) {
                                                                            }
                                                                            if (str == null) {
                                                                            }
                                                                            jSONLexerBase.nextTokenWithColon(4);
                                                                            if (jSONLexerBase.token() != 4) {
                                                                                throw new JSONException("syntax error");
                                                                            }
                                                                            strStringVal = jSONLexerBase.stringVal();
                                                                            jSONLexerBase.nextToken(16);
                                                                            if (strStringVal.equals(this.beanInfo.typeName)) {
                                                                            }
                                                                            if (jSONLexerBase.token() == 13) {
                                                                                jSONLexerBase.nextToken();
                                                                                map = map2;
                                                                                break;
                                                                            }
                                                                            map = map2;
                                                                        }
                                                                        if (obj2 == null) {
                                                                            map = map2;
                                                                        } else {
                                                                            map = map2;
                                                                        }
                                                                        if (!z) {
                                                                            if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                                if (jSONLexerBase.token() == 13) {
                                                                                    jSONLexerBase.nextToken();
                                                                                    break;
                                                                                }
                                                                            } else {
                                                                                if (jSONLexerBase.token() == 17) {
                                                                                    throw new JSONException("syntax error, unexpect token ':'");
                                                                                }
                                                                                if (jSONLexerBase.token() != 16) {
                                                                                    if (jSONLexerBase.token() == 13) {
                                                                                        jSONLexerBase.nextToken(16);
                                                                                        break;
                                                                                    }
                                                                                    if (jSONLexerBase.token() != 18) {
                                                                                    }
                                                                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                                }
                                                                                continue;
                                                                            }
                                                                        } else {
                                                                            if (z2) {
                                                                                if (obj2 == null) {
                                                                                    map.put(fieldInfo.name, objValueOf);
                                                                                } else if (objValueOf != null) {
                                                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                                                } else if (cls2 != Integer.TYPE) {
                                                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                                                }
                                                                                if (iArr != null) {
                                                                                    int i7 = i5 / 32;
                                                                                    iArr[i7] = iArr[i7] | (1 >> (i5 % 32));
                                                                                }
                                                                                if (jSONLexerBase.matchStat != 4) {
                                                                                    break;
                                                                                }
                                                                                break;
                                                                                break;
                                                                            }
                                                                            fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                                            if (jSONLexerBase.token() != 16) {
                                                                                if (jSONLexerBase.token() == 13) {
                                                                                    jSONLexerBase.nextToken(16);
                                                                                    break;
                                                                                }
                                                                                if (jSONLexerBase.token() != 18) {
                                                                                }
                                                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                            }
                                                                            continue;
                                                                        }
                                                                    } else {
                                                                        objValueOf = jSONLexerBase.scanFieldFloatArray(cArr);
                                                                        if (jSONLexerBase.matchStat > 0) {
                                                                            z = true;
                                                                            z2 = true;
                                                                        } else if (jSONLexerBase.matchStat == -2) {
                                                                            map = map2;
                                                                        }
                                                                        if (!z) {
                                                                            strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                                            if (strScanSymbol == null) {
                                                                                i2 = jSONLexerBase.token();
                                                                                if (i2 == 13) {
                                                                                    jSONLexerBase.nextToken(16);
                                                                                    map = map2;
                                                                                    break;
                                                                                }
                                                                                if (i2 != 16) {
                                                                                }
                                                                            }
                                                                            if ("$ref" != strScanSymbol) {
                                                                            }
                                                                            if (str == null) {
                                                                            }
                                                                            jSONLexerBase.nextTokenWithColon(4);
                                                                            if (jSONLexerBase.token() != 4) {
                                                                                throw new JSONException("syntax error");
                                                                            }
                                                                            strStringVal = jSONLexerBase.stringVal();
                                                                            jSONLexerBase.nextToken(16);
                                                                            if (strStringVal.equals(this.beanInfo.typeName)) {
                                                                            }
                                                                            if (jSONLexerBase.token() == 13) {
                                                                                jSONLexerBase.nextToken();
                                                                                map = map2;
                                                                                break;
                                                                            }
                                                                            map = map2;
                                                                        }
                                                                        if (obj2 == null) {
                                                                            map = map2;
                                                                        } else {
                                                                            map = map2;
                                                                        }
                                                                        if (!z) {
                                                                            if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                                if (jSONLexerBase.token() == 13) {
                                                                                    jSONLexerBase.nextToken();
                                                                                    break;
                                                                                }
                                                                            } else {
                                                                                if (jSONLexerBase.token() == 17) {
                                                                                    throw new JSONException("syntax error, unexpect token ':'");
                                                                                }
                                                                                if (jSONLexerBase.token() != 16) {
                                                                                    if (jSONLexerBase.token() == 13) {
                                                                                        jSONLexerBase.nextToken(16);
                                                                                        break;
                                                                                    }
                                                                                    if (jSONLexerBase.token() != 18) {
                                                                                    }
                                                                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                                }
                                                                                continue;
                                                                            }
                                                                        } else {
                                                                            if (z2) {
                                                                                if (obj2 == null) {
                                                                                    map.put(fieldInfo.name, objValueOf);
                                                                                } else if (objValueOf != null) {
                                                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                                                } else if (cls2 != Integer.TYPE) {
                                                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                                                }
                                                                                if (iArr != null) {
                                                                                    int i8 = i5 / 32;
                                                                                    iArr[i8] = iArr[i8] | (1 >> (i5 % 32));
                                                                                }
                                                                                if (jSONLexerBase.matchStat != 4) {
                                                                                    break;
                                                                                }
                                                                                break;
                                                                                break;
                                                                            }
                                                                            fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                                            if (jSONLexerBase.token() != 16) {
                                                                                if (jSONLexerBase.token() == 13) {
                                                                                    jSONLexerBase.nextToken(16);
                                                                                    break;
                                                                                }
                                                                                if (jSONLexerBase.token() != 18) {
                                                                                }
                                                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                            }
                                                                            continue;
                                                                        }
                                                                    }
                                                                } else {
                                                                    objValueOf = jSONLexerBase.scanFieldIntArray(cArr);
                                                                    if (jSONLexerBase.matchStat > 0) {
                                                                        z = true;
                                                                        z2 = true;
                                                                    } else if (jSONLexerBase.matchStat == -2) {
                                                                        map = map2;
                                                                    }
                                                                    if (!z) {
                                                                        strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                                        if (strScanSymbol == null) {
                                                                            i2 = jSONLexerBase.token();
                                                                            if (i2 == 13) {
                                                                                jSONLexerBase.nextToken(16);
                                                                                map = map2;
                                                                                break;
                                                                            }
                                                                            if (i2 != 16) {
                                                                            }
                                                                        }
                                                                        if ("$ref" != strScanSymbol) {
                                                                        }
                                                                        if (str == null) {
                                                                        }
                                                                        jSONLexerBase.nextTokenWithColon(4);
                                                                        if (jSONLexerBase.token() != 4) {
                                                                            throw new JSONException("syntax error");
                                                                        }
                                                                        strStringVal = jSONLexerBase.stringVal();
                                                                        jSONLexerBase.nextToken(16);
                                                                        if (strStringVal.equals(this.beanInfo.typeName)) {
                                                                        }
                                                                        if (jSONLexerBase.token() == 13) {
                                                                            jSONLexerBase.nextToken();
                                                                            map = map2;
                                                                            break;
                                                                        }
                                                                        map = map2;
                                                                    }
                                                                    if (obj2 == null) {
                                                                        map = map2;
                                                                    } else {
                                                                        map = map2;
                                                                    }
                                                                    if (!z) {
                                                                        if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                            if (jSONLexerBase.token() == 13) {
                                                                                jSONLexerBase.nextToken();
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            if (jSONLexerBase.token() == 17) {
                                                                                throw new JSONException("syntax error, unexpect token ':'");
                                                                            }
                                                                            if (jSONLexerBase.token() != 16) {
                                                                                if (jSONLexerBase.token() == 13) {
                                                                                    jSONLexerBase.nextToken(16);
                                                                                    break;
                                                                                }
                                                                                if (jSONLexerBase.token() != 18) {
                                                                                }
                                                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                            }
                                                                            continue;
                                                                        }
                                                                    } else {
                                                                        if (z2) {
                                                                            if (obj2 == null) {
                                                                                map.put(fieldInfo.name, objValueOf);
                                                                            } else if (objValueOf != null) {
                                                                                fieldDeserializer.setValue(obj2, objValueOf);
                                                                            } else if (cls2 != Integer.TYPE) {
                                                                                fieldDeserializer.setValue(obj2, objValueOf);
                                                                            }
                                                                            if (iArr != null) {
                                                                                int i9 = i5 / 32;
                                                                                iArr[i9] = iArr[i9] | (1 >> (i5 % 32));
                                                                            }
                                                                            if (jSONLexerBase.matchStat != 4) {
                                                                                break;
                                                                            }
                                                                            break;
                                                                            break;
                                                                        }
                                                                        fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                                        if (jSONLexerBase.token() != 16) {
                                                                            if (jSONLexerBase.token() == 13) {
                                                                                jSONLexerBase.nextToken(16);
                                                                                break;
                                                                            }
                                                                            if (jSONLexerBase.token() != 18) {
                                                                            }
                                                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                        }
                                                                        continue;
                                                                    }
                                                                }
                                                            } else if (!(fieldDeserializer instanceof DefaultFieldDeserializer)) {
                                                                if (!z) {
                                                                    strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                                    if (strScanSymbol == null) {
                                                                        i2 = jSONLexerBase.token();
                                                                        if (i2 == 13) {
                                                                            jSONLexerBase.nextToken(16);
                                                                            map = map2;
                                                                            break;
                                                                        }
                                                                        if (i2 != 16) {
                                                                        }
                                                                    }
                                                                    if ("$ref" != strScanSymbol) {
                                                                    }
                                                                    if (str == null) {
                                                                    }
                                                                    jSONLexerBase.nextTokenWithColon(4);
                                                                    if (jSONLexerBase.token() != 4) {
                                                                        throw new JSONException("syntax error");
                                                                    }
                                                                    strStringVal = jSONLexerBase.stringVal();
                                                                    jSONLexerBase.nextToken(16);
                                                                    if (strStringVal.equals(this.beanInfo.typeName)) {
                                                                    }
                                                                    if (jSONLexerBase.token() == 13) {
                                                                        jSONLexerBase.nextToken();
                                                                        map = map2;
                                                                        break;
                                                                    }
                                                                    map = map2;
                                                                }
                                                                if (obj2 == null) {
                                                                    map = map2;
                                                                } else {
                                                                    map = map2;
                                                                }
                                                                if (!z) {
                                                                    if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                        if (jSONLexerBase.token() == 13) {
                                                                            jSONLexerBase.nextToken();
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        if (jSONLexerBase.token() == 17) {
                                                                            throw new JSONException("syntax error, unexpect token ':'");
                                                                        }
                                                                        if (jSONLexerBase.token() != 16) {
                                                                            if (jSONLexerBase.token() == 13) {
                                                                                jSONLexerBase.nextToken(16);
                                                                                break;
                                                                            }
                                                                            if (jSONLexerBase.token() != 18) {
                                                                            }
                                                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                        }
                                                                        continue;
                                                                    }
                                                                } else {
                                                                    if (z2) {
                                                                        if (obj2 == null) {
                                                                            map.put(fieldInfo.name, objValueOf);
                                                                        } else if (objValueOf != null) {
                                                                            fieldDeserializer.setValue(obj2, objValueOf);
                                                                        } else if (cls2 != Integer.TYPE) {
                                                                            fieldDeserializer.setValue(obj2, objValueOf);
                                                                        }
                                                                        if (iArr != null) {
                                                                            int i10 = i5 / 32;
                                                                            iArr[i10] = iArr[i10] | (1 >> (i5 % 32));
                                                                        }
                                                                        if (jSONLexerBase.matchStat != 4) {
                                                                            break;
                                                                        }
                                                                        break;
                                                                        break;
                                                                    }
                                                                    fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                                    if (jSONLexerBase.token() != 16) {
                                                                        if (jSONLexerBase.token() == 13) {
                                                                            jSONLexerBase.nextToken(16);
                                                                            break;
                                                                        }
                                                                        if (jSONLexerBase.token() != 18) {
                                                                        }
                                                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                    }
                                                                    continue;
                                                                }
                                                            } else {
                                                                objValueOf = scanEnum(jSONLexerBase, cArr, ((DefaultFieldDeserializer) fieldDeserializer).fieldValueDeserilizer);
                                                                if (jSONLexerBase.matchStat > 0) {
                                                                    z = true;
                                                                    z2 = true;
                                                                } else if (jSONLexerBase.matchStat == -2) {
                                                                    map = map2;
                                                                }
                                                                if (!z) {
                                                                    strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                                    if (strScanSymbol == null) {
                                                                        i2 = jSONLexerBase.token();
                                                                        if (i2 == 13) {
                                                                            jSONLexerBase.nextToken(16);
                                                                            map = map2;
                                                                            break;
                                                                        }
                                                                        if (i2 != 16) {
                                                                        }
                                                                    }
                                                                    if ("$ref" != strScanSymbol) {
                                                                    }
                                                                    if (str == null) {
                                                                    }
                                                                    jSONLexerBase.nextTokenWithColon(4);
                                                                    if (jSONLexerBase.token() != 4) {
                                                                        throw new JSONException("syntax error");
                                                                    }
                                                                    strStringVal = jSONLexerBase.stringVal();
                                                                    jSONLexerBase.nextToken(16);
                                                                    if (strStringVal.equals(this.beanInfo.typeName)) {
                                                                    }
                                                                    if (jSONLexerBase.token() == 13) {
                                                                        jSONLexerBase.nextToken();
                                                                        map = map2;
                                                                        break;
                                                                    }
                                                                    map = map2;
                                                                }
                                                                if (obj2 == null) {
                                                                    map = map2;
                                                                } else {
                                                                    map = map2;
                                                                }
                                                                if (!z) {
                                                                    if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                        if (jSONLexerBase.token() == 13) {
                                                                            jSONLexerBase.nextToken();
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        if (jSONLexerBase.token() == 17) {
                                                                            throw new JSONException("syntax error, unexpect token ':'");
                                                                        }
                                                                        if (jSONLexerBase.token() != 16) {
                                                                            if (jSONLexerBase.token() == 13) {
                                                                                jSONLexerBase.nextToken(16);
                                                                                break;
                                                                            }
                                                                            if (jSONLexerBase.token() != 18) {
                                                                            }
                                                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                        }
                                                                        continue;
                                                                    }
                                                                } else {
                                                                    if (z2) {
                                                                        if (obj2 == null) {
                                                                            map.put(fieldInfo.name, objValueOf);
                                                                        } else if (objValueOf != null) {
                                                                            fieldDeserializer.setValue(obj2, objValueOf);
                                                                        } else if (cls2 != Integer.TYPE) {
                                                                            fieldDeserializer.setValue(obj2, objValueOf);
                                                                        }
                                                                        if (iArr != null) {
                                                                            int i11 = i5 / 32;
                                                                            iArr[i11] = iArr[i11] | (1 >> (i5 % 32));
                                                                        }
                                                                        if (jSONLexerBase.matchStat != 4) {
                                                                            break;
                                                                        }
                                                                        break;
                                                                        break;
                                                                    }
                                                                    fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                                    if (jSONLexerBase.token() != 16) {
                                                                        if (jSONLexerBase.token() == 13) {
                                                                            jSONLexerBase.nextToken(16);
                                                                            break;
                                                                        }
                                                                        if (jSONLexerBase.token() != 18) {
                                                                        }
                                                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                    }
                                                                    continue;
                                                                }
                                                            }
                                                        } else {
                                                            objValueOf = Double.valueOf(jSONLexerBase.scanFieldDouble(cArr));
                                                            if (jSONLexerBase.matchStat > 0) {
                                                                z = true;
                                                                z2 = true;
                                                            } else if (jSONLexerBase.matchStat == -2) {
                                                                map = map2;
                                                            }
                                                            if (!z) {
                                                                strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                                if (strScanSymbol == null) {
                                                                    i2 = jSONLexerBase.token();
                                                                    if (i2 == 13) {
                                                                        jSONLexerBase.nextToken(16);
                                                                        map = map2;
                                                                        break;
                                                                    }
                                                                    if (i2 != 16) {
                                                                    }
                                                                }
                                                                if ("$ref" != strScanSymbol) {
                                                                }
                                                                if (str == null) {
                                                                }
                                                                jSONLexerBase.nextTokenWithColon(4);
                                                                if (jSONLexerBase.token() != 4) {
                                                                    throw new JSONException("syntax error");
                                                                }
                                                                strStringVal = jSONLexerBase.stringVal();
                                                                jSONLexerBase.nextToken(16);
                                                                if (strStringVal.equals(this.beanInfo.typeName)) {
                                                                }
                                                                if (jSONLexerBase.token() == 13) {
                                                                    jSONLexerBase.nextToken();
                                                                    map = map2;
                                                                    break;
                                                                }
                                                                map = map2;
                                                            }
                                                            if (obj2 == null) {
                                                                map = map2;
                                                            } else {
                                                                map = map2;
                                                            }
                                                            if (!z) {
                                                                if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                    if (jSONLexerBase.token() == 13) {
                                                                        jSONLexerBase.nextToken();
                                                                        break;
                                                                    }
                                                                } else {
                                                                    if (jSONLexerBase.token() == 17) {
                                                                        throw new JSONException("syntax error, unexpect token ':'");
                                                                    }
                                                                    if (jSONLexerBase.token() != 16) {
                                                                        if (jSONLexerBase.token() == 13) {
                                                                            jSONLexerBase.nextToken(16);
                                                                            break;
                                                                        }
                                                                        if (jSONLexerBase.token() != 18) {
                                                                        }
                                                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                    }
                                                                    continue;
                                                                }
                                                            } else {
                                                                if (z2) {
                                                                    if (obj2 == null) {
                                                                        map.put(fieldInfo.name, objValueOf);
                                                                    } else if (objValueOf != null) {
                                                                        fieldDeserializer.setValue(obj2, objValueOf);
                                                                    } else if (cls2 != Integer.TYPE) {
                                                                        fieldDeserializer.setValue(obj2, objValueOf);
                                                                    }
                                                                    if (iArr != null) {
                                                                        int i12 = i5 / 32;
                                                                        iArr[i12] = iArr[i12] | (1 >> (i5 % 32));
                                                                    }
                                                                    if (jSONLexerBase.matchStat != 4) {
                                                                        break;
                                                                    }
                                                                    break;
                                                                    break;
                                                                }
                                                                fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                                if (jSONLexerBase.token() != 16) {
                                                                    if (jSONLexerBase.token() == 13) {
                                                                        jSONLexerBase.nextToken(16);
                                                                        break;
                                                                    }
                                                                    if (jSONLexerBase.token() != 18) {
                                                                    }
                                                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                }
                                                                continue;
                                                            }
                                                        }
                                                    } else {
                                                        objValueOf = Float.valueOf(jSONLexerBase.scanFieldFloat(cArr));
                                                        if (jSONLexerBase.matchStat > 0) {
                                                            z = true;
                                                            z2 = true;
                                                        } else if (jSONLexerBase.matchStat == -2) {
                                                            map = map2;
                                                        }
                                                        if (!z) {
                                                            strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                            if (strScanSymbol == null) {
                                                                i2 = jSONLexerBase.token();
                                                                if (i2 == 13) {
                                                                    jSONLexerBase.nextToken(16);
                                                                    map = map2;
                                                                    break;
                                                                }
                                                                if (i2 != 16) {
                                                                }
                                                            }
                                                            if ("$ref" != strScanSymbol) {
                                                            }
                                                            if (str == null) {
                                                            }
                                                            jSONLexerBase.nextTokenWithColon(4);
                                                            if (jSONLexerBase.token() != 4) {
                                                                throw new JSONException("syntax error");
                                                            }
                                                            strStringVal = jSONLexerBase.stringVal();
                                                            jSONLexerBase.nextToken(16);
                                                            if (strStringVal.equals(this.beanInfo.typeName)) {
                                                            }
                                                            if (jSONLexerBase.token() == 13) {
                                                                jSONLexerBase.nextToken();
                                                                map = map2;
                                                                break;
                                                            }
                                                            map = map2;
                                                        }
                                                        if (obj2 == null) {
                                                            map = map2;
                                                        } else {
                                                            map = map2;
                                                        }
                                                        if (!z) {
                                                            if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                                if (jSONLexerBase.token() == 13) {
                                                                    jSONLexerBase.nextToken();
                                                                    break;
                                                                }
                                                            } else {
                                                                if (jSONLexerBase.token() == 17) {
                                                                    throw new JSONException("syntax error, unexpect token ':'");
                                                                }
                                                                if (jSONLexerBase.token() != 16) {
                                                                    if (jSONLexerBase.token() == 13) {
                                                                        jSONLexerBase.nextToken(16);
                                                                        break;
                                                                    }
                                                                    if (jSONLexerBase.token() != 18) {
                                                                    }
                                                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                                }
                                                                continue;
                                                            }
                                                        } else {
                                                            if (z2) {
                                                                if (obj2 == null) {
                                                                    map.put(fieldInfo.name, objValueOf);
                                                                } else if (objValueOf != null) {
                                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                                } else if (cls2 != Integer.TYPE) {
                                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                                }
                                                                if (iArr != null) {
                                                                    int i13 = i5 / 32;
                                                                    iArr[i13] = iArr[i13] | (1 >> (i5 % 32));
                                                                }
                                                                if (jSONLexerBase.matchStat != 4) {
                                                                    break;
                                                                }
                                                                break;
                                                                break;
                                                            }
                                                            fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                            if (jSONLexerBase.token() != 16) {
                                                                if (jSONLexerBase.token() == 13) {
                                                                    jSONLexerBase.nextToken(16);
                                                                    break;
                                                                }
                                                                if (jSONLexerBase.token() != 18) {
                                                                }
                                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                            }
                                                            continue;
                                                        }
                                                    }
                                                } else {
                                                    objValueOf = Boolean.valueOf(jSONLexerBase.scanFieldBoolean(cArr));
                                                    if (jSONLexerBase.matchStat > 0) {
                                                        z = true;
                                                        z2 = true;
                                                    } else if (jSONLexerBase.matchStat == -2) {
                                                        map = map2;
                                                    }
                                                    if (!z) {
                                                        strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                        if (strScanSymbol == null) {
                                                            i2 = jSONLexerBase.token();
                                                            if (i2 == 13) {
                                                                jSONLexerBase.nextToken(16);
                                                                map = map2;
                                                                break;
                                                            }
                                                            if (i2 != 16) {
                                                            }
                                                        }
                                                        if ("$ref" != strScanSymbol) {
                                                        }
                                                        if (str == null) {
                                                        }
                                                        jSONLexerBase.nextTokenWithColon(4);
                                                        if (jSONLexerBase.token() != 4) {
                                                            throw new JSONException("syntax error");
                                                        }
                                                        strStringVal = jSONLexerBase.stringVal();
                                                        jSONLexerBase.nextToken(16);
                                                        if (strStringVal.equals(this.beanInfo.typeName)) {
                                                        }
                                                        if (jSONLexerBase.token() == 13) {
                                                            jSONLexerBase.nextToken();
                                                            map = map2;
                                                            break;
                                                        }
                                                        map = map2;
                                                    }
                                                    if (obj2 == null) {
                                                        map = map2;
                                                    } else {
                                                        map = map2;
                                                    }
                                                    if (!z) {
                                                        if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                            if (jSONLexerBase.token() == 13) {
                                                                jSONLexerBase.nextToken();
                                                                break;
                                                            }
                                                        } else {
                                                            if (jSONLexerBase.token() == 17) {
                                                                throw new JSONException("syntax error, unexpect token ':'");
                                                            }
                                                            if (jSONLexerBase.token() != 16) {
                                                                if (jSONLexerBase.token() == 13) {
                                                                    jSONLexerBase.nextToken(16);
                                                                    break;
                                                                }
                                                                if (jSONLexerBase.token() != 18) {
                                                                }
                                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                            }
                                                            continue;
                                                        }
                                                    } else {
                                                        if (z2) {
                                                            if (obj2 == null) {
                                                                map.put(fieldInfo.name, objValueOf);
                                                            } else if (objValueOf != null) {
                                                                fieldDeserializer.setValue(obj2, objValueOf);
                                                            } else if (cls2 != Integer.TYPE) {
                                                                fieldDeserializer.setValue(obj2, objValueOf);
                                                            }
                                                            if (iArr != null) {
                                                                int i14 = i5 / 32;
                                                                iArr[i14] = iArr[i14] | (1 >> (i5 % 32));
                                                            }
                                                            if (jSONLexerBase.matchStat != 4) {
                                                                break;
                                                            }
                                                            break;
                                                            break;
                                                        }
                                                        fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                        if (jSONLexerBase.token() != 16) {
                                                            if (jSONLexerBase.token() == 13) {
                                                                jSONLexerBase.nextToken(16);
                                                                break;
                                                            }
                                                            if (jSONLexerBase.token() != 18) {
                                                            }
                                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                        }
                                                        continue;
                                                    }
                                                }
                                            } else {
                                                objValueOf = jSONLexerBase.scanFieldBigInteger(cArr);
                                                if (jSONLexerBase.matchStat > 0) {
                                                    z = true;
                                                    z2 = true;
                                                } else if (jSONLexerBase.matchStat == -2) {
                                                    map = map2;
                                                }
                                                if (!z) {
                                                    strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                    if (strScanSymbol == null) {
                                                        i2 = jSONLexerBase.token();
                                                        if (i2 == 13) {
                                                            jSONLexerBase.nextToken(16);
                                                            map = map2;
                                                            break;
                                                        }
                                                        if (i2 != 16) {
                                                        }
                                                    }
                                                    if ("$ref" != strScanSymbol) {
                                                    }
                                                    if (str == null) {
                                                    }
                                                    jSONLexerBase.nextTokenWithColon(4);
                                                    if (jSONLexerBase.token() != 4) {
                                                        throw new JSONException("syntax error");
                                                    }
                                                    strStringVal = jSONLexerBase.stringVal();
                                                    jSONLexerBase.nextToken(16);
                                                    if (strStringVal.equals(this.beanInfo.typeName)) {
                                                    }
                                                    if (jSONLexerBase.token() == 13) {
                                                        jSONLexerBase.nextToken();
                                                        map = map2;
                                                        break;
                                                    }
                                                    map = map2;
                                                }
                                                if (obj2 == null) {
                                                    map = map2;
                                                } else {
                                                    map = map2;
                                                }
                                                if (!z) {
                                                    if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                        if (jSONLexerBase.token() == 13) {
                                                            jSONLexerBase.nextToken();
                                                            break;
                                                        }
                                                    } else {
                                                        if (jSONLexerBase.token() == 17) {
                                                            throw new JSONException("syntax error, unexpect token ':'");
                                                        }
                                                        if (jSONLexerBase.token() != 16) {
                                                            if (jSONLexerBase.token() == 13) {
                                                                jSONLexerBase.nextToken(16);
                                                                break;
                                                            }
                                                            if (jSONLexerBase.token() != 18) {
                                                            }
                                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                        }
                                                        continue;
                                                    }
                                                } else {
                                                    if (z2) {
                                                        if (obj2 == null) {
                                                            map.put(fieldInfo.name, objValueOf);
                                                        } else if (objValueOf != null) {
                                                            fieldDeserializer.setValue(obj2, objValueOf);
                                                        } else if (cls2 != Integer.TYPE) {
                                                            fieldDeserializer.setValue(obj2, objValueOf);
                                                        }
                                                        if (iArr != null) {
                                                            int i15 = i5 / 32;
                                                            iArr[i15] = iArr[i15] | (1 >> (i5 % 32));
                                                        }
                                                        if (jSONLexerBase.matchStat != 4) {
                                                            break;
                                                        }
                                                        break;
                                                        break;
                                                    }
                                                    fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                    if (jSONLexerBase.token() != 16) {
                                                        if (jSONLexerBase.token() == 13) {
                                                            jSONLexerBase.nextToken(16);
                                                            break;
                                                        }
                                                        if (jSONLexerBase.token() != 18) {
                                                        }
                                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                    }
                                                    continue;
                                                }
                                            }
                                        } else {
                                            objValueOf = jSONLexerBase.scanFieldDecimal(cArr);
                                            if (jSONLexerBase.matchStat > 0) {
                                                z = true;
                                                z2 = true;
                                            } else if (jSONLexerBase.matchStat == -2) {
                                                map = map2;
                                            }
                                            if (!z) {
                                                strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                                if (strScanSymbol == null) {
                                                    i2 = jSONLexerBase.token();
                                                    if (i2 == 13) {
                                                        jSONLexerBase.nextToken(16);
                                                        map = map2;
                                                        break;
                                                    }
                                                    if (i2 != 16) {
                                                    }
                                                }
                                                if ("$ref" != strScanSymbol) {
                                                }
                                                if (str == null) {
                                                }
                                                jSONLexerBase.nextTokenWithColon(4);
                                                if (jSONLexerBase.token() != 4) {
                                                    throw new JSONException("syntax error");
                                                }
                                                strStringVal = jSONLexerBase.stringVal();
                                                jSONLexerBase.nextToken(16);
                                                if (strStringVal.equals(this.beanInfo.typeName)) {
                                                }
                                                if (jSONLexerBase.token() == 13) {
                                                    jSONLexerBase.nextToken();
                                                    map = map2;
                                                    break;
                                                }
                                                map = map2;
                                            }
                                            if (obj2 == null) {
                                                map = map2;
                                            } else {
                                                map = map2;
                                            }
                                            if (!z) {
                                                if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                    if (jSONLexerBase.token() == 13) {
                                                        jSONLexerBase.nextToken();
                                                        break;
                                                    }
                                                } else {
                                                    if (jSONLexerBase.token() == 17) {
                                                        throw new JSONException("syntax error, unexpect token ':'");
                                                    }
                                                    if (jSONLexerBase.token() != 16) {
                                                        if (jSONLexerBase.token() == 13) {
                                                            jSONLexerBase.nextToken(16);
                                                            break;
                                                        }
                                                        if (jSONLexerBase.token() != 18) {
                                                        }
                                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                    }
                                                    continue;
                                                }
                                            } else {
                                                if (z2) {
                                                    if (obj2 == null) {
                                                        map.put(fieldInfo.name, objValueOf);
                                                    } else if (objValueOf != null) {
                                                        fieldDeserializer.setValue(obj2, objValueOf);
                                                    } else if (cls2 != Integer.TYPE) {
                                                        fieldDeserializer.setValue(obj2, objValueOf);
                                                    }
                                                    if (iArr != null) {
                                                        int i16 = i5 / 32;
                                                        iArr[i16] = iArr[i16] | (1 >> (i5 % 32));
                                                    }
                                                    if (jSONLexerBase.matchStat != 4) {
                                                        break;
                                                    }
                                                    break;
                                                    break;
                                                }
                                                fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                                if (jSONLexerBase.token() != 16) {
                                                    if (jSONLexerBase.token() == 13) {
                                                        jSONLexerBase.nextToken(16);
                                                        break;
                                                    }
                                                    if (jSONLexerBase.token() != 18) {
                                                    }
                                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                }
                                                continue;
                                            }
                                        }
                                    } else {
                                        objValueOf = jSONLexerBase.scanFieldDate(cArr);
                                        if (jSONLexerBase.matchStat > 0) {
                                            z = true;
                                            z2 = true;
                                        } else if (jSONLexerBase.matchStat == -2) {
                                            map = map2;
                                        }
                                        if (!z) {
                                            strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                            if (strScanSymbol == null) {
                                                i2 = jSONLexerBase.token();
                                                if (i2 == 13) {
                                                    jSONLexerBase.nextToken(16);
                                                    map = map2;
                                                    break;
                                                }
                                                if (i2 != 16) {
                                                }
                                            }
                                            if ("$ref" != strScanSymbol) {
                                            }
                                            if (str == null) {
                                            }
                                            jSONLexerBase.nextTokenWithColon(4);
                                            if (jSONLexerBase.token() != 4) {
                                                throw new JSONException("syntax error");
                                            }
                                            strStringVal = jSONLexerBase.stringVal();
                                            jSONLexerBase.nextToken(16);
                                            if (strStringVal.equals(this.beanInfo.typeName)) {
                                            }
                                            if (jSONLexerBase.token() == 13) {
                                                jSONLexerBase.nextToken();
                                                map = map2;
                                                break;
                                            }
                                            map = map2;
                                        }
                                        if (obj2 == null) {
                                            map = map2;
                                        } else {
                                            map = map2;
                                        }
                                        if (!z) {
                                            if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                                if (jSONLexerBase.token() == 13) {
                                                    jSONLexerBase.nextToken();
                                                    break;
                                                }
                                            } else {
                                                if (jSONLexerBase.token() == 17) {
                                                    throw new JSONException("syntax error, unexpect token ':'");
                                                }
                                                if (jSONLexerBase.token() != 16) {
                                                    if (jSONLexerBase.token() == 13) {
                                                        jSONLexerBase.nextToken(16);
                                                        break;
                                                    }
                                                    if (jSONLexerBase.token() != 18) {
                                                    }
                                                    throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                                }
                                                continue;
                                            }
                                        } else {
                                            if (z2) {
                                                if (obj2 == null) {
                                                    map.put(fieldInfo.name, objValueOf);
                                                } else if (objValueOf != null) {
                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                } else if (cls2 != Integer.TYPE) {
                                                    fieldDeserializer.setValue(obj2, objValueOf);
                                                }
                                                if (iArr != null) {
                                                    int i17 = i5 / 32;
                                                    iArr[i17] = iArr[i17] | (1 >> (i5 % 32));
                                                }
                                                if (jSONLexerBase.matchStat != 4) {
                                                    break;
                                                }
                                                break;
                                                break;
                                            }
                                            fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                            if (jSONLexerBase.token() != 16) {
                                                if (jSONLexerBase.token() == 13) {
                                                    jSONLexerBase.nextToken(16);
                                                    break;
                                                }
                                                if (jSONLexerBase.token() != 18) {
                                                }
                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                            }
                                            continue;
                                        }
                                    }
                                } else {
                                    objValueOf = jSONLexerBase.scanFieldString(cArr);
                                    if (jSONLexerBase.matchStat > 0) {
                                        z = true;
                                        z2 = true;
                                    } else if (jSONLexerBase.matchStat == -2) {
                                        map = map2;
                                    }
                                    if (!z) {
                                        strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                        if (strScanSymbol == null) {
                                            i2 = jSONLexerBase.token();
                                            if (i2 == 13) {
                                                jSONLexerBase.nextToken(16);
                                                map = map2;
                                                break;
                                            }
                                            if (i2 != 16) {
                                            }
                                        }
                                        if ("$ref" != strScanSymbol) {
                                        }
                                        if (str == null) {
                                        }
                                        jSONLexerBase.nextTokenWithColon(4);
                                        if (jSONLexerBase.token() != 4) {
                                            throw new JSONException("syntax error");
                                        }
                                        strStringVal = jSONLexerBase.stringVal();
                                        jSONLexerBase.nextToken(16);
                                        if (strStringVal.equals(this.beanInfo.typeName)) {
                                        }
                                        if (jSONLexerBase.token() == 13) {
                                            jSONLexerBase.nextToken();
                                            map = map2;
                                            break;
                                        }
                                        map = map2;
                                    }
                                    if (obj2 == null) {
                                        map = map2;
                                    } else {
                                        map = map2;
                                    }
                                    if (!z) {
                                        if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                            if (jSONLexerBase.token() == 13) {
                                                jSONLexerBase.nextToken();
                                                break;
                                            }
                                        } else {
                                            if (jSONLexerBase.token() == 17) {
                                                throw new JSONException("syntax error, unexpect token ':'");
                                            }
                                            if (jSONLexerBase.token() != 16) {
                                                if (jSONLexerBase.token() == 13) {
                                                    jSONLexerBase.nextToken(16);
                                                    break;
                                                }
                                                if (jSONLexerBase.token() != 18) {
                                                }
                                                throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                            }
                                            continue;
                                        }
                                    } else {
                                        if (z2) {
                                            if (obj2 == null) {
                                                map.put(fieldInfo.name, objValueOf);
                                            } else if (objValueOf != null) {
                                                fieldDeserializer.setValue(obj2, objValueOf);
                                            } else if (cls2 != Integer.TYPE) {
                                                fieldDeserializer.setValue(obj2, objValueOf);
                                            }
                                            if (iArr != null) {
                                                int i18 = i5 / 32;
                                                iArr[i18] = iArr[i18] | (1 >> (i5 % 32));
                                            }
                                            if (jSONLexerBase.matchStat != 4) {
                                                break;
                                            }
                                            break;
                                            break;
                                        }
                                        fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                        if (jSONLexerBase.token() != 16) {
                                            if (jSONLexerBase.token() == 13) {
                                                jSONLexerBase.nextToken(16);
                                                break;
                                            }
                                            if (jSONLexerBase.token() != 18) {
                                            }
                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                        }
                                        continue;
                                    }
                                }
                            } else {
                                objValueOf = Long.valueOf(jSONLexerBase.scanFieldLong(cArr));
                                if (jSONLexerBase.matchStat > 0) {
                                    z = true;
                                    z2 = true;
                                } else if (jSONLexerBase.matchStat == -2) {
                                    map = map2;
                                }
                                if (!z) {
                                    strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                    if (strScanSymbol == null) {
                                        i2 = jSONLexerBase.token();
                                        if (i2 == 13) {
                                            jSONLexerBase.nextToken(16);
                                            map = map2;
                                            break;
                                        }
                                        if (i2 != 16) {
                                        }
                                    }
                                    if ("$ref" != strScanSymbol) {
                                    }
                                    if (str == null) {
                                    }
                                    jSONLexerBase.nextTokenWithColon(4);
                                    if (jSONLexerBase.token() != 4) {
                                        throw new JSONException("syntax error");
                                    }
                                    strStringVal = jSONLexerBase.stringVal();
                                    jSONLexerBase.nextToken(16);
                                    if (strStringVal.equals(this.beanInfo.typeName)) {
                                    }
                                    if (jSONLexerBase.token() == 13) {
                                        jSONLexerBase.nextToken();
                                        map = map2;
                                        break;
                                    }
                                    map = map2;
                                }
                                if (obj2 == null) {
                                    map = map2;
                                } else {
                                    map = map2;
                                }
                                if (!z) {
                                    if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                        if (jSONLexerBase.token() == 13) {
                                            jSONLexerBase.nextToken();
                                            break;
                                        }
                                    } else {
                                        if (jSONLexerBase.token() == 17) {
                                            throw new JSONException("syntax error, unexpect token ':'");
                                        }
                                        if (jSONLexerBase.token() != 16) {
                                            if (jSONLexerBase.token() == 13) {
                                                jSONLexerBase.nextToken(16);
                                                break;
                                            }
                                            if (jSONLexerBase.token() != 18) {
                                            }
                                            throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                        }
                                        continue;
                                    }
                                } else {
                                    if (z2) {
                                        if (obj2 == null) {
                                            map.put(fieldInfo.name, objValueOf);
                                        } else if (objValueOf != null) {
                                            fieldDeserializer.setValue(obj2, objValueOf);
                                        } else if (cls2 != Integer.TYPE) {
                                            fieldDeserializer.setValue(obj2, objValueOf);
                                        }
                                        if (iArr != null) {
                                            int i19 = i5 / 32;
                                            iArr[i19] = iArr[i19] | (1 >> (i5 % 32));
                                        }
                                        if (jSONLexerBase.matchStat != 4) {
                                            break;
                                        }
                                        break;
                                        break;
                                    }
                                    fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                    if (jSONLexerBase.token() != 16) {
                                        if (jSONLexerBase.token() == 13) {
                                            jSONLexerBase.nextToken(16);
                                            break;
                                        }
                                        if (jSONLexerBase.token() != 18) {
                                        }
                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                    }
                                    continue;
                                }
                            }
                        } else {
                            objValueOf = Integer.valueOf(jSONLexerBase.scanFieldInt(cArr));
                            if (jSONLexerBase.matchStat > 0) {
                                z = true;
                                z2 = true;
                            } else if (jSONLexerBase.matchStat == -2) {
                                map = map2;
                            }
                            if (!z) {
                                strScanSymbol = jSONLexerBase.scanSymbol(defaultJSONParser.symbolTable);
                                if (strScanSymbol == null) {
                                    i2 = jSONLexerBase.token();
                                    if (i2 == 13) {
                                        jSONLexerBase.nextToken(16);
                                        map = map2;
                                        break;
                                    }
                                    if (i2 != 16 && jSONLexerBase.isEnabled(Feature.AllowArbitraryCommas)) {
                                        map = map2;
                                    }
                                }
                                if ("$ref" != strScanSymbol && context != null) {
                                    jSONLexerBase.nextTokenWithColon(4);
                                    int i20 = jSONLexerBase.token();
                                    if (i20 != 4) {
                                        throw new JSONException("illegal ref, " + JSONToken.name(i20));
                                    }
                                    String strStringVal3 = jSONLexerBase.stringVal();
                                    if ("@".equals(strStringVal3)) {
                                        obj2 = context.object;
                                    } else if ("..".equals(strStringVal3)) {
                                        ParseContext parseContext = context.parent;
                                        if (parseContext.object != null) {
                                            obj2 = parseContext.object;
                                        } else {
                                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(parseContext, strStringVal3));
                                            defaultJSONParser.resolveStatus = 1;
                                        }
                                    } else if ("$".equals(strStringVal3)) {
                                        ParseContext parseContext2 = context;
                                        while (parseContext2.parent != null) {
                                            parseContext2 = parseContext2.parent;
                                        }
                                        if (parseContext2.object != null) {
                                            obj2 = parseContext2.object;
                                        } else {
                                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(parseContext2, strStringVal3));
                                            defaultJSONParser.resolveStatus = 1;
                                        }
                                    } else {
                                        Object objResolveReference = defaultJSONParser.resolveReference(strStringVal3);
                                        if (objResolveReference != null) {
                                            obj2 = objResolveReference;
                                        } else {
                                            defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(context, strStringVal3));
                                            defaultJSONParser.resolveStatus = 1;
                                        }
                                    }
                                    jSONLexerBase.nextToken(13);
                                    if (jSONLexerBase.token() != 13) {
                                        throw new JSONException("illegal ref");
                                    }
                                    jSONLexerBase.nextToken(16);
                                    defaultJSONParser.setContext(context, obj2, obj);
                                    if (context2 != null) {
                                        context2.object = obj2;
                                    }
                                    defaultJSONParser.setContext(context);
                                    return (T) obj2;
                                }
                                if ((str == null && str.equals(strScanSymbol)) || JSON.DEFAULT_TYPE_KEY == strScanSymbol) {
                                    jSONLexerBase.nextTokenWithColon(4);
                                    if (jSONLexerBase.token() != 4) {
                                        throw new JSONException("syntax error");
                                    }
                                    strStringVal = jSONLexerBase.stringVal();
                                    jSONLexerBase.nextToken(16);
                                    if (strStringVal.equals(this.beanInfo.typeName) && !defaultJSONParser.isEnabled(Feature.IgnoreAutoType)) {
                                        ObjectDeserializer seeAlso = getSeeAlso(config, this.beanInfo, strStringVal);
                                        Class<?> clsCheckAutoType = null;
                                        if (seeAlso == null) {
                                            clsCheckAutoType = config.checkAutoType(strStringVal, TypeUtils.getClass(type));
                                            seeAlso = defaultJSONParser.getConfig().getDeserializer(clsCheckAutoType);
                                        }
                                        T t3 = (T) seeAlso.deserialze(defaultJSONParser, clsCheckAutoType, obj);
                                        if (seeAlso instanceof JavaBeanDeserializer) {
                                            JavaBeanDeserializer javaBeanDeserializer = (JavaBeanDeserializer) seeAlso;
                                            if (str != null) {
                                                javaBeanDeserializer.getFieldDeserializer(str).setValue((Object) t3, strStringVal);
                                            }
                                        }
                                        if (context2 != null) {
                                            context2.object = obj2;
                                        }
                                        defaultJSONParser.setContext(context);
                                        return t3;
                                    }
                                    if (jSONLexerBase.token() == 13) {
                                        jSONLexerBase.nextToken();
                                        map = map2;
                                        break;
                                    }
                                    map = map2;
                                }
                            }
                            if (obj2 == null || map2 != null) {
                                map = map2;
                            } else {
                                obj2 = createInstance(defaultJSONParser, type);
                                map = obj2 == null ? new HashMap(this.fieldDeserializers.length) : map2;
                                context2 = defaultJSONParser.setContext(context, obj2, obj);
                                if (iArr == null) {
                                    iArr = new int[(this.fieldDeserializers.length / 32) + 1];
                                }
                            }
                            if (!z) {
                                if (!parseField(defaultJSONParser, strScanSymbol, obj2, type, map, iArr)) {
                                    if (jSONLexerBase.token() == 13) {
                                        jSONLexerBase.nextToken();
                                        break;
                                    }
                                } else {
                                    if (jSONLexerBase.token() == 17) {
                                        throw new JSONException("syntax error, unexpect token ':'");
                                    }
                                    if (jSONLexerBase.token() != 16) {
                                        if (jSONLexerBase.token() == 13) {
                                            jSONLexerBase.nextToken(16);
                                            break;
                                        }
                                        if (jSONLexerBase.token() != 18) {
                                        }
                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                    }
                                    continue;
                                }
                            } else {
                                if (z2) {
                                    if (obj2 == null) {
                                        map.put(fieldInfo.name, objValueOf);
                                    } else if (objValueOf != null) {
                                        fieldDeserializer.setValue(obj2, objValueOf);
                                    } else if (cls2 != Integer.TYPE && cls2 != Long.TYPE && cls2 != Float.TYPE && cls2 != Double.TYPE && cls2 != Boolean.TYPE) {
                                        fieldDeserializer.setValue(obj2, objValueOf);
                                    }
                                    if (iArr != null) {
                                        int i110 = i5 / 32;
                                        iArr[i110] = iArr[i110] | (1 >> (i5 % 32));
                                    }
                                    if (jSONLexerBase.matchStat != 4) {
                                        break;
                                    }
                                    break;
                                }
                                fieldDeserializer.parseField(defaultJSONParser, obj2, type, map);
                                if (jSONLexerBase.token() != 16) {
                                    continue;
                                } else {
                                    if (jSONLexerBase.token() == 13) {
                                        jSONLexerBase.nextToken(16);
                                        break;
                                    }
                                    if (jSONLexerBase.token() != 18 || jSONLexerBase.token() == 1) {
                                        throw new JSONException("syntax error, unexpect token " + JSONToken.name(jSONLexerBase.token()));
                                    }
                                }
                            }
                        }
                    }
                    i5++;
                    map2 = map;
                } catch (Throwable th) {
                    th = th;
                    if (context2 != null) {
                        context2.object = obj2;
                    }
                    defaultJSONParser.setContext(context);
                    throw th;
                }
            }
            if (obj2 == null) {
                if (map == null) {
                    T t4 = (T) createInstance(defaultJSONParser, type);
                    if (context2 == null) {
                        context2 = defaultJSONParser.setContext(context, t4, obj);
                    }
                    if (context2 != null) {
                        context2.object = t4;
                    }
                    defaultJSONParser.setContext(context);
                    return t4;
                }
                String[] strArr = this.beanInfo.creatorConstructorParameters;
                if (strArr != null) {
                    objArr = new Object[strArr.length];
                    for (int i21 = 0; i21 < strArr.length; i21++) {
                        Object objRemove = map.remove(strArr[i21]);
                        if (objRemove == null) {
                            Type type2 = this.beanInfo.creatorConstructorParameterTypes[i21];
                            FieldInfo fieldInfo2 = this.beanInfo.fields[i21];
                            if (type2 == Byte.TYPE) {
                                objRemove = (byte) 0;
                            } else if (type2 == Short.TYPE) {
                                objRemove = (short) 0;
                            } else if (type2 == Integer.TYPE) {
                                objRemove = 0;
                            } else if (type2 == Long.TYPE) {
                                objRemove = 0L;
                            } else if (type2 == Float.TYPE) {
                                objRemove = Float.valueOf(0.0f);
                            } else if (type2 == Double.TYPE) {
                                objRemove = Double.valueOf(0.0d);
                            } else if (type2 == Boolean.TYPE) {
                                objRemove = Boolean.FALSE;
                            } else if (type2 == String.class && (fieldInfo2.parserFeatures & Feature.InitStringFieldAsEmpty.mask) != 0) {
                                objRemove = Constants.MAIN_VERSION_TAG;
                            }
                        }
                        objArr[i21] = objRemove;
                    }
                } else {
                    FieldInfo[] fieldInfoArr = this.beanInfo.fields;
                    int length = fieldInfoArr.length;
                    objArr = new Object[length];
                    for (int i22 = 0; i22 < length; i22++) {
                        FieldInfo fieldInfo3 = fieldInfoArr[i22];
                        Object objValueOf2 = map.get(fieldInfo3.name);
                        if (objValueOf2 == null) {
                            Type type3 = fieldInfo3.fieldType;
                            if (type3 == Byte.TYPE) {
                                objValueOf2 = (byte) 0;
                            } else if (type3 == Short.TYPE) {
                                objValueOf2 = (short) 0;
                            } else if (type3 == Integer.TYPE) {
                                objValueOf2 = 0;
                            } else if (type3 == Long.TYPE) {
                                objValueOf2 = 0L;
                            } else if (type3 == Float.TYPE) {
                                objValueOf2 = Float.valueOf(0.0f);
                            } else if (type3 == Double.TYPE) {
                                objValueOf2 = Double.valueOf(0.0d);
                            } else if (type3 == Boolean.TYPE) {
                                objValueOf2 = Boolean.FALSE;
                            } else if (type3 == String.class && (fieldInfo3.parserFeatures & Feature.InitStringFieldAsEmpty.mask) != 0) {
                                objValueOf2 = Constants.MAIN_VERSION_TAG;
                            }
                        }
                        objArr[i22] = objValueOf2;
                    }
                }
                if (this.beanInfo.creatorConstructor != null) {
                    try {
                        obj2 = this.beanInfo.creatorConstructor.newInstance(objArr);
                        if (strArr != null) {
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                FieldDeserializer fieldDeserializer2 = getFieldDeserializer(entry.getKey());
                                if (fieldDeserializer2 != null) {
                                    fieldDeserializer2.setValue(obj2, entry.getValue());
                                }
                            }
                        }
                    } catch (Exception e2) {
                        throw new JSONException("create instance error, " + strArr + ", " + this.beanInfo.creatorConstructor.toGenericString(), e2);
                    }
                } else if (this.beanInfo.factoryMethod != null) {
                    try {
                        obj2 = this.beanInfo.factoryMethod.invoke(null, objArr);
                    } catch (Exception e3) {
                        throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e3);
                    }
                }
                context2.object = obj2;
            }
            Method method = this.beanInfo.buildMethod;
            if (method == null) {
                if (context2 != null) {
                    context2.object = obj2;
                }
                defaultJSONParser.setContext(context);
                return (T) obj2;
            }
            try {
                T t5 = (T) method.invoke(obj2, new Object[0]);
                if (context2 != null) {
                    context2.object = obj2;
                }
                defaultJSONParser.setContext(context);
                return t5;
            } catch (Exception e4) {
                throw new JSONException("build object error", e4);
            }
        } catch (Throwable th2) {
            th = th2;
            if (context2 != null) {
                context2.object = obj2;
            }
            defaultJSONParser.setContext(context);
            throw th;
        }
    }

    protected Enum scanEnum(JSONLexerBase lexer, char[] name_chars, ObjectDeserializer fieldValueDeserilizer) {
        EnumDeserializer enumDeserializer = null;
        if (fieldValueDeserilizer instanceof EnumDeserializer) {
            enumDeserializer = (EnumDeserializer) fieldValueDeserilizer;
        }
        if (enumDeserializer == null) {
            lexer.matchStat = -1;
            return null;
        }
        long enumNameHashCode = lexer.scanFieldSymbol(name_chars);
        if (lexer.matchStat > 0) {
            return enumDeserializer.getEnumByHashCode(enumNameHashCode);
        }
        return null;
    }

    public boolean parseField(DefaultJSONParser parser, String key, Object object, Type objectType, Map<String, Object> fieldValues, int[] setFlags) {
        FieldDeserializer fieldDeserializer;
        JSONLexer lexer = parser.lexer;
        int disableFieldSmartMatchMask = Feature.DisableFieldSmartMatch.mask;
        if (lexer.isEnabled(disableFieldSmartMatchMask) || (this.beanInfo.parserFeatures & disableFieldSmartMatchMask) != 0) {
            fieldDeserializer = getFieldDeserializer(key);
        } else {
            fieldDeserializer = smartMatch(key, setFlags);
        }
        int mask = Feature.SupportNonPublicField.mask;
        if (fieldDeserializer == null && (lexer.isEnabled(mask) || (this.beanInfo.parserFeatures & mask) != 0)) {
            if (this.extraFieldDeserializers == null) {
                ConcurrentHashMap extraFieldDeserializers = new ConcurrentHashMap(1, 0.75f, 1);
                for (Class<?> superclass = this.clazz; superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                    Field[] fields = superclass.getDeclaredFields();
                    for (Field field : fields) {
                        String fieldName = field.getName();
                        if (getFieldDeserializer(fieldName) == null) {
                            int fieldModifiers = field.getModifiers();
                            if ((fieldModifiers & 16) == 0 && (fieldModifiers & 8) == 0) {
                                extraFieldDeserializers.put(fieldName, field);
                            }
                        }
                    }
                }
                this.extraFieldDeserializers = extraFieldDeserializers;
            }
            Object deserOrField = this.extraFieldDeserializers.get(key);
            if (deserOrField != null) {
                if (deserOrField instanceof FieldDeserializer) {
                    fieldDeserializer = (FieldDeserializer) deserOrField;
                } else {
                    Field field2 = (Field) deserOrField;
                    field2.setAccessible(true);
                    fieldDeserializer = new DefaultFieldDeserializer(parser.getConfig(), this.clazz, new FieldInfo(key, field2.getDeclaringClass(), field2.getType(), field2.getGenericType(), field2, 0, 0, 0));
                    this.extraFieldDeserializers.put(key, fieldDeserializer);
                }
            }
        }
        if (fieldDeserializer == null) {
            if (!lexer.isEnabled(Feature.IgnoreNotMatch)) {
                throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + key);
            }
            for (FieldDeserializer fieldDeser : this.sortedFieldDeserializers) {
                FieldInfo fieldInfo = fieldDeser.fieldInfo;
                if (fieldInfo.unwrapped && (fieldDeser instanceof DefaultFieldDeserializer)) {
                    if (fieldInfo.field != null) {
                        DefaultFieldDeserializer defaultFieldDeserializer = (DefaultFieldDeserializer) fieldDeser;
                        ObjectDeserializer fieldValueDeser = defaultFieldDeserializer.getFieldValueDeserilizer(parser.getConfig());
                        if (fieldValueDeser instanceof JavaBeanDeserializer) {
                            JavaBeanDeserializer javaBeanFieldValueDeserializer = (JavaBeanDeserializer) fieldValueDeser;
                            FieldDeserializer unwrappedFieldDeser = javaBeanFieldValueDeserializer.getFieldDeserializer(key);
                            if (unwrappedFieldDeser != null) {
                                try {
                                    Object fieldObject = fieldInfo.field.get(object);
                                    if (fieldObject == null) {
                                        fieldObject = ((JavaBeanDeserializer) fieldValueDeser).createInstance(parser, fieldInfo.fieldType);
                                        fieldDeser.setValue(object, fieldObject);
                                    }
                                    lexer.nextTokenWithColon(defaultFieldDeserializer.getFastMatchToken());
                                    unwrappedFieldDeser.parseField(parser, fieldObject, objectType, fieldValues);
                                    return true;
                                } catch (Exception e) {
                                    throw new JSONException("parse unwrapped field error.", e);
                                }
                            }
                        } else if (fieldValueDeser instanceof MapDeserializer) {
                            MapDeserializer javaBeanFieldValueDeserializer2 = (MapDeserializer) fieldValueDeser;
                            try {
                                Map<Object, Object> mapCreateMap = (Map) fieldInfo.field.get(object);
                                if (mapCreateMap == null) {
                                    mapCreateMap = javaBeanFieldValueDeserializer2.createMap(fieldInfo.fieldType);
                                    fieldDeser.setValue(object, mapCreateMap);
                                }
                                lexer.nextTokenWithColon();
                                Object fieldValue = parser.parse(key);
                                mapCreateMap.put(key, fieldValue);
                                return true;
                            } catch (Exception e2) {
                                throw new JSONException("parse unwrapped field error.", e2);
                            }
                        }
                    } else if (fieldInfo.method.getParameterTypes().length == 2) {
                        lexer.nextTokenWithColon();
                        Object fieldValue2 = parser.parse(key);
                        try {
                            fieldInfo.method.invoke(object, key, fieldValue2);
                            return true;
                        } catch (Exception e3) {
                            throw new JSONException("parse unwrapped field error.", e3);
                        }
                    }
                }
            }
            parser.parseExtra(object, key);
            return false;
        }
        int fieldIndex = -1;
        for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
            if (this.sortedFieldDeserializers[i] == fieldDeserializer) {
                fieldIndex = i;
                break;
            }
        }
        if (fieldIndex != -1 && setFlags != null && key.startsWith("_") && isSetFlag(fieldIndex, setFlags)) {
            parser.parseExtra(object, key);
            return false;
        }
        lexer.nextTokenWithColon(fieldDeserializer.getFastMatchToken());
        fieldDeserializer.parseField(parser, object, objectType, fieldValues);
        return true;
    }

    public FieldDeserializer smartMatch(String key) {
        return smartMatch(key, null);
    }

    public FieldDeserializer smartMatch(String key, int[] setFlags) {
        if (key == null) {
            return null;
        }
        FieldDeserializer fieldDeserializer = getFieldDeserializer(key, setFlags);
        if (fieldDeserializer == null) {
            long smartKeyHash = TypeUtils.fnv1a_64_lower(key);
            if (this.smartMatchHashArray == null) {
                long[] hashArray = new long[this.sortedFieldDeserializers.length];
                for (int i = 0; i < this.sortedFieldDeserializers.length; i++) {
                    hashArray[i] = TypeUtils.fnv1a_64_lower(this.sortedFieldDeserializers[i].fieldInfo.name);
                }
                Arrays.sort(hashArray);
                this.smartMatchHashArray = hashArray;
            }
            int pos = Arrays.binarySearch(this.smartMatchHashArray, smartKeyHash);
            boolean is = false;
            if (pos < 0 && (is = key.startsWith("is"))) {
                long smartKeyHash2 = TypeUtils.fnv1a_64_lower(key.substring(2));
                pos = Arrays.binarySearch(this.smartMatchHashArray, smartKeyHash2);
            }
            if (pos >= 0) {
                if (this.smartMatchHashArrayMapping == null) {
                    short[] mapping = new short[this.smartMatchHashArray.length];
                    Arrays.fill(mapping, (short) -1);
                    for (int i2 = 0; i2 < this.sortedFieldDeserializers.length; i2++) {
                        int p = Arrays.binarySearch(this.smartMatchHashArray, TypeUtils.fnv1a_64_lower(this.sortedFieldDeserializers[i2].fieldInfo.name));
                        if (p >= 0) {
                            mapping[p] = (short) i2;
                        }
                    }
                    this.smartMatchHashArrayMapping = mapping;
                }
                short s = this.smartMatchHashArrayMapping[pos];
                if (s != -1 && !isSetFlag(s, setFlags)) {
                    fieldDeserializer = this.sortedFieldDeserializers[s];
                }
            }
            if (fieldDeserializer != null) {
                FieldInfo fieldInfo = fieldDeserializer.fieldInfo;
                if ((fieldInfo.parserFeatures & Feature.DisableFieldSmartMatch.mask) != 0) {
                    return null;
                }
                Class<?> cls = fieldInfo.fieldClass;
                if (is && cls != Boolean.TYPE && cls != Boolean.class) {
                    return null;
                }
                return fieldDeserializer;
            }
            return fieldDeserializer;
        }
        return fieldDeserializer;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public Object createInstance(Map<String, Object> map, ParserConfig config) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.beanInfo.creatorConstructor == null && this.beanInfo.factoryMethod == null) {
            Object object = createInstance((DefaultJSONParser) null, this.clazz);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                FieldDeserializer fieldDeser = smartMatch(key);
                if (fieldDeser != null) {
                    Type paramType = fieldDeser.fieldInfo.fieldType;
                    fieldDeser.setValue(object, TypeUtils.cast(value, paramType, config));
                }
            }
            if (this.beanInfo.buildMethod != null) {
                try {
                    Object builtObj = this.beanInfo.buildMethod.invoke(object, new Object[0]);
                    return builtObj;
                } catch (Exception e) {
                    throw new JSONException("build object error", e);
                }
            }
            return object;
        }
        FieldInfo[] fieldInfoList = this.beanInfo.fields;
        int size = fieldInfoList.length;
        Object[] params = new Object[size];
        for (int i = 0; i < size; i++) {
            FieldInfo fieldInfo = fieldInfoList[i];
            Object param = map.get(fieldInfo.name);
            if (param == null) {
                Class<?> fieldClass = fieldInfo.fieldClass;
                if (fieldClass == Integer.TYPE) {
                    param = 0;
                } else if (fieldClass == Long.TYPE) {
                    param = 0L;
                } else if (fieldClass == Short.TYPE) {
                    param = (short) 0;
                } else if (fieldClass == Byte.TYPE) {
                    param = (byte) 0;
                } else if (fieldClass == Float.TYPE) {
                    param = Float.valueOf(0.0f);
                } else if (fieldClass == Double.TYPE) {
                    param = Double.valueOf(0.0d);
                } else if (fieldClass == Character.TYPE) {
                    param = '0';
                } else if (fieldClass == Boolean.TYPE) {
                    param = false;
                }
            }
            params[i] = param;
        }
        if (this.beanInfo.creatorConstructor != null) {
            try {
                Object object2 = this.beanInfo.creatorConstructor.newInstance(params);
                return object2;
            } catch (Exception e2) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), e2);
            }
        }
        if (this.beanInfo.factoryMethod == null) {
            return null;
        }
        try {
            Object object3 = this.beanInfo.factoryMethod.invoke(null, params);
            return object3;
        } catch (Exception e3) {
            throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), e3);
        }
    }

    protected JavaBeanDeserializer getSeeAlso(ParserConfig config, JavaBeanInfo beanInfo, String typeName) {
        if (beanInfo.jsonType == null) {
            return null;
        }
        for (Class<?> seeAlsoClass : beanInfo.jsonType.seeAlso()) {
            ObjectDeserializer seeAlsoDeser = config.getDeserializer(seeAlsoClass);
            if (seeAlsoDeser instanceof JavaBeanDeserializer) {
                JavaBeanDeserializer seeAlsoJavaBeanDeser = (JavaBeanDeserializer) seeAlsoDeser;
                JavaBeanInfo subBeanInfo = seeAlsoJavaBeanDeser.beanInfo;
                if (!subBeanInfo.typeName.equals(typeName)) {
                    JavaBeanDeserializer subSeeAlso = getSeeAlso(config, subBeanInfo, typeName);
                    if (subSeeAlso != null) {
                        return subSeeAlso;
                    }
                } else {
                    return seeAlsoJavaBeanDeser;
                }
            }
        }
        return null;
    }
}
