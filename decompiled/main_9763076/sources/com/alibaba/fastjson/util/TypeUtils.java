package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.SerializeBeanInfo;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.SettingsContentProvider;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.Bugly;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TypeUtils {
    public static boolean compatibleWithFieldName;
    public static boolean compatibleWithJavaBean;
    private static volatile Map<Class, String[]> kotlinIgnores;
    private static volatile boolean kotlinIgnores_error;
    private static volatile boolean kotlin_class_klass_error;
    private static volatile boolean kotlin_error;
    private static volatile Constructor kotlin_kclass_constructor;
    private static volatile Method kotlin_kclass_getConstructors;
    private static volatile Method kotlin_kfunction_getParameters;
    private static volatile Method kotlin_kparameter_getName;
    private static volatile Class kotlin_metadata;
    private static volatile boolean kotlin_metadata_error;
    private static Class<?> optionalClass;
    private static Method oracleDateMethod;
    private static Method oracleTimestampMethod;
    private static Class<?> pathClass;
    private static Class<? extends Annotation> transientClass;
    private static boolean setAccessibleEnable = true;
    private static boolean oracleTimestampMethodInited = false;
    private static boolean oracleDateMethodInited = false;
    private static boolean optionalClassInited = false;
    private static boolean transientClassInited = false;
    private static Class<? extends Annotation> class_OneToMany = null;
    private static boolean class_OneToMany_error = false;
    private static Class<? extends Annotation> class_ManyToMany = null;
    private static boolean class_ManyToMany_error = false;
    private static Method method_HibernateIsInitialized = null;
    private static boolean method_HibernateIsInitialized_error = false;
    private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap(16, 0.75f, 1);
    private static boolean pathClass_error = false;

    static {
        compatibleWithJavaBean = false;
        compatibleWithFieldName = false;
        try {
            compatibleWithJavaBean = "true".equals(IOUtils.getStringProperty("fastjson.compatibleWithJavaBean"));
            compatibleWithFieldName = "true".equals(IOUtils.getStringProperty("fastjson.compatibleWithFieldName"));
        } catch (Throwable th) {
        }
        addBaseClassMappings();
    }

    public static String castToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static Byte castToByte(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Byte.valueOf(((Number) value).byteValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            return Byte.valueOf(Byte.parseByte(strVal));
        }
        throw new JSONException("can not cast to byte, value : " + value);
    }

    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() != 1) {
                throw new JSONException("can not cast to char, value : " + value);
            }
            return Character.valueOf(strVal.charAt(0));
        }
        throw new JSONException("can not cast to char, value : " + value);
    }

    public static Short castToShort(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Short.valueOf(((Number) value).shortValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            return Short.valueOf(Short.parseShort(strVal));
        }
        throw new JSONException("can not cast to short, value : " + value);
    }

    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }
        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }
        if ((value instanceof Map) && ((Map) value).size() == 0) {
            return null;
        }
        return new BigDecimal(strVal);
    }

    public static BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if ((value instanceof Float) || (value instanceof Double)) {
            return BigInteger.valueOf(((Number) value).longValue());
        }
        String strVal = value.toString();
        if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
            return null;
        }
        return new BigInteger(strVal);
    }

    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != 0) {
                strVal = strVal.replaceAll(",", Constants.MAIN_VERSION_TAG);
            }
            return Float.valueOf(Float.parseFloat(strVal));
        }
        throw new JSONException("can not cast to float, value : " + value);
    }

    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Double.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != 0) {
                strVal = strVal.replaceAll(",", Constants.MAIN_VERSION_TAG);
            }
            return Double.valueOf(Double.parseDouble(strVal));
        }
        throw new JSONException("can not cast to double, value : " + value);
    }

    public static Date castToDate(Object value) {
        String format;
        if (value == null) {
            return null;
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        long longValue = -1;
        if (value instanceof Number) {
            long longValue2 = ((Number) value).longValue();
            return new Date(longValue2);
        }
        if (value instanceof String) {
            String strVal = (String) value;
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    Date time = calendar.getTime();
                    dateLexer.close();
                    return time;
                }
                dateLexer.close();
                if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                    String dotnetDateStr = strVal.substring(6, strVal.length() - 2);
                    strVal = dotnetDateStr;
                }
                if (strVal.indexOf(45) != -1) {
                    if (strVal.length() == JSON.DEFFAULT_DATE_FORMAT.length() || (strVal.length() == 22 && JSON.DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
                        format = JSON.DEFFAULT_DATE_FORMAT;
                    } else if (strVal.length() == 10) {
                        format = "yyyy-MM-dd";
                    } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                        format = "yyyy-MM-dd HH:mm:ss";
                    } else if (strVal.length() == 29 && strVal.charAt(26) == ':' && strVal.charAt(28) == '0') {
                        format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                    } else {
                        format = "yyyy-MM-dd HH:mm:ss.SSS";
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
                    dateFormat.setTimeZone(JSON.defaultTimeZone);
                    try {
                        return dateFormat.parse(strVal);
                    } catch (ParseException e) {
                        throw new JSONException("can not cast to Date, value : " + strVal);
                    }
                }
                if (strVal.length() == 0) {
                    return null;
                }
                longValue = Long.parseLong(strVal);
            } catch (Throwable th) {
                dateLexer.close();
                throw th;
            }
        }
        if (longValue < 0) {
            Class<?> clazz = value.getClass();
            if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
                if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                    try {
                        oracleTimestampMethod = clazz.getMethod("toJdbc", new Class[0]);
                    } catch (NoSuchMethodException e2) {
                    } finally {
                        oracleTimestampMethodInited = true;
                    }
                }
                try {
                    Object result = oracleTimestampMethod.invoke(value, new Object[0]);
                    return (Date) result;
                } catch (Exception e3) {
                    throw new JSONException("can not cast oracle.sql.TIMESTAMP to Date", e3);
                }
            }
            if ("oracle.sql.DATE".equals(clazz.getName())) {
                if (oracleDateMethod == null && !oracleDateMethodInited) {
                    try {
                        oracleDateMethod = clazz.getMethod("toJdbc", new Class[0]);
                    } catch (NoSuchMethodException e4) {
                    } finally {
                        oracleDateMethodInited = true;
                    }
                }
                try {
                    Object result2 = oracleDateMethod.invoke(value, new Object[0]);
                    return (Date) result2;
                } catch (Exception e5) {
                    throw new JSONException("can not cast oracle.sql.DATE to Date", e5);
                }
            }
            throw new JSONException("can not cast to Date, value : " + value);
        }
        return new Date(longValue);
    }

    public static java.sql.Date castToSqlDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        }
        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }
        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }
        long longValue = 0;
        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (isNumber(strVal)) {
                longValue = Long.parseLong(strVal);
            } else {
                JSONScanner scanner = new JSONScanner(strVal);
                if (scanner.scanISO8601DateIfMatch(false)) {
                    longValue = scanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + strVal);
                }
            }
        }
        if (longValue <= 0) {
            throw new JSONException("can not cast to Date, value : " + value);
        }
        return new java.sql.Date(longValue);
    }

    public static Timestamp castToTimestamp(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return new Timestamp(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof Timestamp) {
            return (Timestamp) value;
        }
        if (value instanceof Date) {
            return new Timestamp(((Date) value).getTime());
        }
        long longValue = 0;
        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.endsWith(".000000000")) {
                strVal = strVal.substring(0, strVal.length() - 10);
            } else if (strVal.endsWith(".000000")) {
                strVal = strVal.substring(0, strVal.length() - 7);
            }
            if (isNumber(strVal)) {
                longValue = Long.parseLong(strVal);
            } else {
                JSONScanner scanner = new JSONScanner(strVal);
                if (scanner.scanISO8601DateIfMatch(false)) {
                    longValue = scanner.getCalendar().getTime().getTime();
                } else {
                    throw new JSONException("can not cast to Timestamp, value : " + strVal);
                }
            }
        }
        if (longValue <= 0) {
            throw new JSONException("can not cast to Timestamp, value : " + value);
        }
        return new Timestamp(longValue);
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-') {
                if (i != 0) {
                    return false;
                }
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != 0) {
                strVal = strVal.replaceAll(",", Constants.MAIN_VERSION_TAG);
            }
            try {
                return Long.valueOf(Long.parseLong(strVal));
            } catch (NumberFormatException e) {
                JSONScanner dateParser = new JSONScanner(strVal);
                Calendar calendar = null;
                if (dateParser.scanISO8601DateIfMatch(false)) {
                    calendar = dateParser.getCalendar();
                }
                dateParser.close();
                if (calendar != null) {
                    return Long.valueOf(calendar.getTimeInMillis());
                }
            }
        }
        if (value instanceof Map) {
            Map map = (Map) value;
            if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                Iterator iter = map.values().iterator();
                iter.next();
                Object value2 = iter.next();
                return castToLong(value2);
            }
        }
        throw new JSONException("can not cast to long, value : " + value);
    }

    public static Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return Integer.valueOf(((Number) value).intValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(44) != 0) {
                strVal = strVal.replaceAll(",", Constants.MAIN_VERSION_TAG);
            }
            return Integer.valueOf(Integer.parseInt(strVal));
        }
        if (value instanceof Boolean) {
            return Integer.valueOf(((Boolean) value).booleanValue() ? 1 : 0);
        }
        if (value instanceof Map) {
            Map map = (Map) value;
            if (map.size() == 2 && map.containsKey("andIncrement") && map.containsKey("andDecrement")) {
                Iterator iter = map.values().iterator();
                iter.next();
                Object value2 = iter.next();
                return castToInt(value2);
            }
        }
        throw new JSONException("can not cast to int, value : " + value);
    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            return IOUtils.decodeBase64((String) value);
        }
        throw new JSONException("can not cast to int, value : " + value);
    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return Boolean.valueOf(((Number) value).intValue() == 1);
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            if ("true".equalsIgnoreCase(strVal) || "1".equals(strVal)) {
                return Boolean.TRUE;
            }
            if (Bugly.SDK_IS_DEV.equalsIgnoreCase(strVal) || PushConstants.PUSH_TYPE_NOTIFY.equals(strVal)) {
                return Boolean.FALSE;
            }
            if ("Y".equalsIgnoreCase(strVal) || "T".equals(strVal)) {
                return Boolean.TRUE;
            }
            if ("F".equalsIgnoreCase(strVal) || "N".equals(strVal)) {
                return Boolean.FALSE;
            }
        }
        throw new JSONException("can not cast to boolean, value : " + value);
    }

    public static <T> T castToJavaBean(Object obj, Class<T> cls) {
        return (T) cast(obj, (Class) cls, ParserConfig.getGlobalInstance());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj, Class<T> cls, ParserConfig parserConfig) {
        Calendar calendar;
        if (obj == 0) {
            if (cls == Integer.TYPE) {
                return (T) 0;
            }
            if (cls == Long.TYPE) {
                return (T) 0L;
            }
            if (cls == Short.TYPE) {
                return (T) (short) 0;
            }
            if (cls == Byte.TYPE) {
                return (T) (byte) 0;
            }
            if (cls == Float.TYPE) {
                return (T) Float.valueOf(0.0f);
            }
            if (cls == Double.TYPE) {
                return (T) Double.valueOf(0.0d);
            }
            if (cls == Boolean.TYPE) {
                return (T) Boolean.FALSE;
            }
            return null;
        }
        if (cls == null) {
            throw new IllegalArgumentException("clazz is null");
        }
        if (cls != obj.getClass()) {
            if (obj instanceof Map) {
                if (cls != Map.class) {
                    Map map = (Map) obj;
                    if (cls != Object.class || map.containsKey(JSON.DEFAULT_TYPE_KEY)) {
                        return (T) castToJavaBean((Map) obj, cls, parserConfig);
                    }
                    return obj;
                }
                return obj;
            }
            if (cls.isArray()) {
                if (obj instanceof Collection) {
                    Collection collection = (Collection) obj;
                    int i = 0;
                    T t = (T) Array.newInstance(cls.getComponentType(), collection.size());
                    Iterator it = collection.iterator();
                    while (it.hasNext()) {
                        Array.set(t, i, cast(it.next(), (Class) cls.getComponentType(), parserConfig));
                        i++;
                    }
                    return t;
                }
                if (cls == byte[].class) {
                    return (T) castToBytes(obj);
                }
            }
            if (!cls.isAssignableFrom(obj.getClass())) {
                if (cls == Boolean.TYPE || cls == Boolean.class) {
                    return (T) castToBoolean(obj);
                }
                if (cls == Byte.TYPE || cls == Byte.class) {
                    return (T) castToByte(obj);
                }
                if (cls == Character.TYPE || cls == Character.class) {
                    return (T) castToChar(obj);
                }
                if (cls == Short.TYPE || cls == Short.class) {
                    return (T) castToShort(obj);
                }
                if (cls == Integer.TYPE || cls == Integer.class) {
                    return (T) castToInt(obj);
                }
                if (cls == Long.TYPE || cls == Long.class) {
                    return (T) castToLong(obj);
                }
                if (cls == Float.TYPE || cls == Float.class) {
                    return (T) castToFloat(obj);
                }
                if (cls == Double.TYPE || cls == Double.class) {
                    return (T) castToDouble(obj);
                }
                if (cls == String.class) {
                    return (T) castToString(obj);
                }
                if (cls == BigDecimal.class) {
                    return (T) castToBigDecimal(obj);
                }
                if (cls == BigInteger.class) {
                    return (T) castToBigInteger(obj);
                }
                if (cls == Date.class) {
                    return (T) castToDate(obj);
                }
                if (cls == java.sql.Date.class) {
                    return (T) castToSqlDate(obj);
                }
                if (cls == Timestamp.class) {
                    return (T) castToTimestamp(obj);
                }
                if (cls.isEnum()) {
                    return (T) castToEnum(obj, cls, parserConfig);
                }
                if (Calendar.class.isAssignableFrom(cls)) {
                    Date dateCastToDate = castToDate(obj);
                    if (cls == Calendar.class) {
                        calendar = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    } else {
                        try {
                            calendar = (Calendar) cls.newInstance();
                        } catch (Exception e) {
                            throw new JSONException("can not cast to : " + cls.getName(), e);
                        }
                    }
                    calendar.setTime(dateCastToDate);
                    return (T) calendar;
                }
                if (cls.getName().equals("javax.xml.datatype.XMLGregorianCalendar")) {
                    Date dateCastToDate2 = castToDate(obj);
                    Calendar calendar2 = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
                    calendar2.setTime(dateCastToDate2);
                    return (T) CalendarCodec.instance.createXMLGregorianCalendar(calendar2);
                }
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                        return null;
                    }
                    if (cls == Currency.class) {
                        return (T) Currency.getInstance(str);
                    }
                    if (cls == Locale.class) {
                        return (T) toLocale(str);
                    }
                }
                throw new JSONException("can not cast to : " + cls.getName());
            }
            return obj;
        }
        return obj;
    }

    public static Locale toLocale(String strVal) {
        String[] items = strVal.split("_");
        if (items.length == 1) {
            return new Locale(items[0]);
        }
        if (items.length == 2) {
            return new Locale(items[0], items[1]);
        }
        return new Locale(items[0], items[1], items[2]);
    }

    public static <T> T castToEnum(Object obj, Class<T> cls, ParserConfig parserConfig) {
        try {
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0) {
                    return null;
                }
                return (T) Enum.valueOf(cls, str);
            }
            if (obj instanceof Number) {
                int iIntValue = ((Number) obj).intValue();
                T[] enumConstants = cls.getEnumConstants();
                if (iIntValue < enumConstants.length) {
                    return enumConstants[iIntValue];
                }
            }
            throw new JSONException("can not cast to : " + cls.getName());
        } catch (Exception e) {
            throw new JSONException("can not cast to : " + cls.getName(), e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T cast(Object obj, Type type, ParserConfig parserConfig) {
        if (obj == 0) {
            return null;
        }
        if (type instanceof Class) {
            return (T) cast(obj, (Class) type, parserConfig);
        }
        if (type instanceof ParameterizedType) {
            return (T) cast(obj, (ParameterizedType) type, parserConfig);
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.length() == 0 || "null".equals(str) || "NULL".equals(str)) {
                return null;
            }
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : " + type);
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [T, java.util.HashMap, java.util.Map] */
    public static <T> T cast(Object obj, ParameterizedType parameterizedType, ParserConfig parserConfig) {
        T t;
        Type rawType = parameterizedType.getRawType();
        if (rawType == Set.class || rawType == HashSet.class || rawType == TreeSet.class || rawType == List.class || rawType == ArrayList.class) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                if (rawType == Set.class || rawType == HashSet.class) {
                    t = (T) new HashSet();
                } else if (rawType == TreeSet.class) {
                    t = (T) new TreeSet();
                } else {
                    t = (T) new ArrayList();
                }
                Iterator<T> it = ((Iterable) obj).iterator();
                while (it.hasNext()) {
                    ((Collection) t).add(cast(it.next(), type, parserConfig));
                }
                return t;
            }
        }
        if (rawType == Map.class || rawType == HashMap.class) {
            Type type2 = parameterizedType.getActualTypeArguments()[0];
            Type type3 = parameterizedType.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                ?? r10 = (T) new HashMap();
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    r10.put(cast(entry.getKey(), type2, parserConfig), cast(entry.getValue(), type3, parserConfig));
                }
                return r10;
            }
        }
        if ((obj instanceof String) && ((String) obj).length() == 0) {
            return null;
        }
        if (parameterizedType.getActualTypeArguments().length == 1 && (parameterizedType.getActualTypeArguments()[0] instanceof WildcardType)) {
            return (T) cast(obj, rawType, parserConfig);
        }
        throw new JSONException("can not cast to : " + parameterizedType);
    }

    public static <T> T castToJavaBean(Map<String, Object> map, Class<T> cls, ParserConfig parserConfig) {
        JSONObject jSONObject;
        int iIntValue;
        try {
            if (cls == StackTraceElement.class) {
                String str = (String) map.get("className");
                String str2 = (String) map.get("methodName");
                String str3 = (String) map.get("fileName");
                Number number = (Number) map.get("lineNumber");
                if (number == null) {
                    iIntValue = 0;
                } else {
                    iIntValue = number.intValue();
                }
                return (T) new StackTraceElement(str, str2, str3, iIntValue);
            }
            Object obj = map.get(JSON.DEFAULT_TYPE_KEY);
            if (obj instanceof String) {
                String str4 = (String) obj;
                if (parserConfig == null) {
                    parserConfig = ParserConfig.global;
                }
                Class<?> clsCheckAutoType = parserConfig.checkAutoType(str4, null);
                if (clsCheckAutoType == null) {
                    throw new ClassNotFoundException(str4 + " not found");
                }
                if (!clsCheckAutoType.equals(cls)) {
                    return (T) castToJavaBean(map, clsCheckAutoType, parserConfig);
                }
            }
            if (cls.isInterface()) {
                if (map instanceof JSONObject) {
                    jSONObject = (JSONObject) map;
                } else {
                    jSONObject = new JSONObject(map);
                }
                if (parserConfig == null) {
                    parserConfig = ParserConfig.getGlobalInstance();
                }
                return parserConfig.getDeserializers().get(cls) != null ? (T) JSON.parseObject(JSON.toJSONString(jSONObject), cls) : (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls}, jSONObject);
            }
            if (cls == Locale.class) {
                Object obj2 = map.get("language");
                Object obj3 = map.get("country");
                if (obj2 instanceof String) {
                    String str5 = (String) obj2;
                    if (obj3 instanceof String) {
                        return (T) new Locale(str5, (String) obj3);
                    }
                    if (obj3 == null) {
                        return (T) new Locale(str5);
                    }
                }
            }
            if (cls == String.class && (map instanceof JSONObject)) {
                return (T) map.toString();
            }
            if (cls == LinkedHashMap.class && (map instanceof JSONObject)) {
                T t = (T) ((JSONObject) map).getInnerMap();
                if (!(t instanceof LinkedHashMap)) {
                    new LinkedHashMap().putAll(t);
                } else {
                    return t;
                }
            }
            if (parserConfig == null) {
                parserConfig = ParserConfig.getGlobalInstance();
            }
            JavaBeanDeserializer javaBeanDeserializer = null;
            ObjectDeserializer deserializer = parserConfig.getDeserializer(cls);
            if (deserializer instanceof JavaBeanDeserializer) {
                javaBeanDeserializer = (JavaBeanDeserializer) deserializer;
            }
            if (javaBeanDeserializer == null) {
                throw new JSONException("can not get javaBeanDeserializer. " + cls.getName());
            }
            return (T) javaBeanDeserializer.createInstance(map, parserConfig);
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    private static void addBaseClassMappings() {
        mappings.put("byte", Byte.TYPE);
        mappings.put("short", Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put(SettingsContentProvider.LONG_TYPE, Long.TYPE);
        mappings.put(SettingsContentProvider.FLOAT_TYPE, Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put(SettingsContentProvider.BOOLEAN_TYPE, Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put("[B", byte[].class);
        mappings.put("[S", short[].class);
        mappings.put("[I", int[].class);
        mappings.put("[J", long[].class);
        mappings.put("[F", float[].class);
        mappings.put("[D", double[].class);
        mappings.put("[C", char[].class);
        mappings.put("[Z", boolean[].class);
        Class<?>[] classes = {Object.class, Cloneable.class, loadClass("java.lang.AutoCloseable"), Exception.class, RuntimeException.class, IllegalAccessError.class, IllegalAccessException.class, IllegalArgumentException.class, IllegalMonitorStateException.class, IllegalStateException.class, IllegalThreadStateException.class, IndexOutOfBoundsException.class, InstantiationError.class, InstantiationException.class, InternalError.class, InterruptedException.class, LinkageError.class, NegativeArraySizeException.class, NoClassDefFoundError.class, NoSuchFieldError.class, NoSuchFieldException.class, NoSuchMethodError.class, NoSuchMethodException.class, NullPointerException.class, NumberFormatException.class, OutOfMemoryError.class, SecurityException.class, StackOverflowError.class, StringIndexOutOfBoundsException.class, TypeNotPresentException.class, VerifyError.class, StackTraceElement.class, HashMap.class, Hashtable.class, TreeMap.class, java.util.IdentityHashMap.class, WeakHashMap.class, LinkedHashMap.class, HashSet.class, LinkedHashSet.class, TreeSet.class, TimeUnit.class, ConcurrentHashMap.class, loadClass("java.util.concurrent.ConcurrentSkipListMap"), loadClass("java.util.concurrent.ConcurrentSkipListSet"), AtomicInteger.class, AtomicLong.class, Collections.EMPTY_MAP.getClass(), BitSet.class, Calendar.class, Date.class, Locale.class, UUID.class, Time.class, java.sql.Date.class, Timestamp.class, SimpleDateFormat.class, JSONObject.class};
        for (Class<?> cls : classes) {
            if (cls != null) {
                mappings.put(cls.getName(), cls);
            }
        }
        String[] awt = {"java.awt.Rectangle", "java.awt.Point", "java.awt.Font", "java.awt.Color"};
        for (String className : awt) {
            Class<?> clazz = loadClass(className);
            if (clazz == null) {
                break;
            }
            mappings.put(clazz.getName(), clazz);
        }
        String[] spring = {"org.springframework.util.LinkedMultiValueMap", "org.springframework.util.LinkedCaseInsensitiveMap", "org.springframework.remoting.support.RemoteInvocation", "org.springframework.remoting.support.RemoteInvocationResult", "org.springframework.security.web.savedrequest.DefaultSavedRequest", "org.springframework.security.web.savedrequest.SavedCookie", "org.springframework.security.web.csrf.DefaultCsrfToken", "org.springframework.security.web.authentication.WebAuthenticationDetails", "org.springframework.security.core.context.SecurityContextImpl", "org.springframework.security.authentication.UsernamePasswordAuthenticationToken", "org.springframework.security.core.authority.SimpleGrantedAuthority", "org.springframework.security.core.userdetails.User"};
        for (String className2 : spring) {
            Class<?> clazz2 = loadClass(className2);
            if (clazz2 != null) {
                mappings.put(clazz2.getName(), clazz2);
            } else {
                return;
            }
        }
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, null);
    }

    public static boolean isPath(Class<?> clazz) {
        if (pathClass == null && !pathClass_error) {
            try {
                pathClass = Class.forName("java.nio.file.Path");
            } catch (Throwable th) {
                pathClass_error = true;
            }
        }
        if (pathClass != null) {
            return pathClass.isAssignableFrom(clazz);
        }
        return false;
    }

    public static Class<?> getClassFromMapping(String className) {
        return mappings.get(className);
    }

    public static Class<?> loadClass(String className, ClassLoader classLoader) {
        if (className == null || className.length() == 0) {
            return null;
        }
        Class<?> clazz = mappings.get(className);
        if (clazz == null) {
            if (className.charAt(0) == '[') {
                Class<?> componentType = loadClass(className.substring(1), classLoader);
                return Array.newInstance(componentType, 0).getClass();
            }
            if (className.startsWith("L") && className.endsWith(";")) {
                String newClassName = className.substring(1, className.length() - 1);
                return loadClass(newClassName, classLoader);
            }
            if (classLoader != null) {
                try {
                    clazz = classLoader.loadClass(className);
                    mappings.put(className, clazz);
                    return clazz;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null && contextClassLoader != classLoader) {
                clazz = contextClassLoader.loadClass(className);
                mappings.put(className, clazz);
                return clazz;
            }
            try {
                clazz = Class.forName(className);
                mappings.put(className, clazz);
                return clazz;
            } catch (Throwable th) {
                return clazz;
            }
        }
        return clazz;
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> beanType, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy) {
        return buildBeanInfo(beanType, aliasMap, propertyNamingStrategy, false);
    }

    public static SerializeBeanInfo buildBeanInfo(Class<?> beanType, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy, boolean fieldBased) {
        int features;
        List<FieldInfo> fieldInfoList;
        List<FieldInfo> sortedFieldList;
        JSONType jsonType = (JSONType) getAnnotation(beanType, JSONType.class);
        String[] orders = null;
        String typeName = null;
        String typeKey = null;
        if (jsonType != null) {
            orders = jsonType.orders();
            typeName = jsonType.typeName();
            if (typeName.length() == 0) {
                typeName = null;
            }
            PropertyNamingStrategy jsonTypeNaming = jsonType.naming();
            if (jsonTypeNaming != null && jsonTypeNaming != PropertyNamingStrategy.CamelCase) {
                propertyNamingStrategy = jsonTypeNaming;
            }
            features = SerializerFeature.of(jsonType.serialzeFeatures());
            for (Class<?> supperClass = beanType.getSuperclass(); supperClass != null && supperClass != Object.class; supperClass = supperClass.getSuperclass()) {
                JSONType superJsonType = (JSONType) getAnnotation(supperClass, JSONType.class);
                if (superJsonType == null) {
                    break;
                }
                typeKey = superJsonType.typeKey();
                if (typeKey.length() != 0) {
                    break;
                }
            }
            for (Class<?> interfaceClass : beanType.getInterfaces()) {
                JSONType superJsonType2 = (JSONType) getAnnotation(interfaceClass, JSONType.class);
                if (superJsonType2 != null) {
                    typeKey = superJsonType2.typeKey();
                    if (typeKey.length() != 0) {
                        break;
                    }
                }
            }
            if (typeKey != null && typeKey.length() == 0) {
                typeKey = null;
            }
        } else {
            features = 0;
        }
        Map<String, Field> fieldCacheMap = new HashMap<>();
        ParserConfig.parserAllFieldToCache(beanType, fieldCacheMap);
        if (fieldBased) {
            fieldInfoList = computeGettersWithFieldBase(beanType, aliasMap, false, propertyNamingStrategy);
        } else {
            fieldInfoList = computeGetters(beanType, jsonType, aliasMap, fieldCacheMap, false, propertyNamingStrategy);
        }
        FieldInfo[] fields = new FieldInfo[fieldInfoList.size()];
        fieldInfoList.toArray(fields);
        if (orders != null && orders.length != 0) {
            if (fieldBased) {
                sortedFieldList = computeGettersWithFieldBase(beanType, aliasMap, true, propertyNamingStrategy);
            } else {
                sortedFieldList = computeGetters(beanType, jsonType, aliasMap, fieldCacheMap, true, propertyNamingStrategy);
            }
        } else {
            sortedFieldList = new ArrayList<>(fieldInfoList);
            Collections.sort(sortedFieldList);
        }
        FieldInfo[] sortedFields = new FieldInfo[sortedFieldList.size()];
        sortedFieldList.toArray(sortedFields);
        if (Arrays.equals(sortedFields, fields)) {
            sortedFields = fields;
        }
        return new SerializeBeanInfo(beanType, jsonType, typeName, typeKey, features, fields, sortedFields);
    }

    public static List<FieldInfo> computeGettersWithFieldBase(Class<?> clazz, Map<String, String> aliasMap, boolean sorted, PropertyNamingStrategy propertyNamingStrategy) {
        Map<String, FieldInfo> fieldInfoMap = new LinkedHashMap<>();
        for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] fields = currentClass.getDeclaredFields();
            computeFields(currentClass, aliasMap, propertyNamingStrategy, fieldInfoMap, fields);
        }
        return getFieldInfos(clazz, sorted, fieldInfoMap);
    }

    /* JADX WARN: Code duplicated, block: B:127:0x02a4  */
    /* JADX WARN: Code duplicated, block: B:129:0x02b7 A[PHI: r11 r12 r13 r16
  0x02b7: PHI (r11v2 'ordinal' int) = (r11v1 'ordinal' int), (r11v5 'ordinal' int) binds: [B:80:0x01be, B:128:0x02aa] A[DONT_GENERATE, DONT_INLINE]
  0x02b7: PHI (r12v2 'serialzeFeatures' int) = (r12v1 'serialzeFeatures' int), (r12v5 'serialzeFeatures' int) binds: [B:80:0x01be, B:128:0x02aa] A[DONT_GENERATE, DONT_INLINE]
  0x02b7: PHI (r13v2 'parserFeatures' int) = (r13v1 'parserFeatures' int), (r13v5 'parserFeatures' int) binds: [B:80:0x01be, B:128:0x02aa] A[DONT_GENERATE, DONT_INLINE]
  0x02b7: PHI (r16v2 'label' java.lang.String) = (r16v1 'label' java.lang.String), (r16v5 'label' java.lang.String) binds: [B:80:0x01be, B:128:0x02aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:131:0x02c1  */
    /* JADX WARN: Code duplicated, block: B:166:0x0370 A[DONT_INVERT, PHI: r6
  0x0370: PHI (r6v4 'propertyName' java.lang.String) = (r6v3 'propertyName' java.lang.String), (r6v8 'propertyName' java.lang.String) binds: [B:163:0x0364, B:165:0x036e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:167:0x0372  */
    /* JADX WARN: Code duplicated, block: B:170:0x0380  */
    /* JADX WARN: Code duplicated, block: B:210:0x003a A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:217:0x003a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x01c0  */
    public static List<FieldInfo> computeGetters(Class<?> clazz, JSONType jsonType, Map<String, String> aliasMap, Map<String, Field> fieldCacheMap, boolean sorted, PropertyNamingStrategy propertyNamingStrategy) {
        String propertyName;
        String propertyName2;
        String propertyName3;
        String propertyName4;
        char ch;
        Field field;
        Constructor creatorConstructor;
        Map<String, FieldInfo> fieldInfoMap = new LinkedHashMap<>();
        boolean kotlin2 = isKotlin(clazz);
        Constructor<?>[] declaredConstructors = null;
        Annotation[][] paramAnnotationArrays = (Annotation[][]) null;
        String[] paramNames = null;
        short[] paramNameMapping = null;
        Method[] methods = clazz.getMethods();
        int length = methods.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                Method method = methods[i2];
                String methodName = method.getName();
                int ordinal = 0;
                int serialzeFeatures = 0;
                int parserFeatures = 0;
                String label = null;
                if (!Modifier.isStatic(method.getModifiers()) && !method.getReturnType().equals(Void.TYPE) && method.getParameterTypes().length == 0 && method.getReturnType() != ClassLoader.class && ((!methodName.equals("getMetaClass") || !method.getReturnType().getName().equals("groovy.lang.MetaClass")) && ((!methodName.equals("getSuppressed") || method.getDeclaringClass() != Throwable.class) && (!kotlin2 || !isKotlinIgnore(clazz, methodName))))) {
                    JSONField annotation = (JSONField) method.getAnnotation(JSONField.class);
                    if (annotation == null) {
                        annotation = getSuperMethodAnnotation(clazz, method);
                    }
                    if (annotation == null && kotlin2) {
                        if (declaredConstructors == null && (creatorConstructor = getKoltinConstructor((declaredConstructors = clazz.getDeclaredConstructors()))) != null) {
                            paramAnnotationArrays = creatorConstructor.getParameterAnnotations();
                            paramNames = getKoltinConstructorParameters(clazz);
                            if (paramNames != null) {
                                String[] paramNames_sorted = new String[paramNames.length];
                                System.arraycopy(paramNames, 0, paramNames_sorted, 0, paramNames.length);
                                Arrays.sort(paramNames_sorted);
                                paramNameMapping = new short[paramNames.length];
                                for (short p = 0; p < paramNames.length; p = (short) (p + 1)) {
                                    int index = Arrays.binarySearch(paramNames_sorted, paramNames[p]);
                                    paramNameMapping[index] = p;
                                }
                                paramNames = paramNames_sorted;
                            }
                        }
                        if (paramNames != null && paramNameMapping != null && methodName.startsWith("get")) {
                            String propertyName5 = decapitalize(methodName.substring(3));
                            int p2 = Arrays.binarySearch(paramNames, propertyName5);
                            if (p2 < 0) {
                                for (int i3 = 0; i3 < paramNames.length; i3++) {
                                    if (propertyName5.equalsIgnoreCase(paramNames[i3])) {
                                        p2 = i3;
                                        break;
                                    }
                                }
                            }
                            if (p2 >= 0) {
                                short index2 = paramNameMapping[p2];
                                Annotation[] paramAnnotations = paramAnnotationArrays[index2];
                                if (paramAnnotations != null) {
                                    for (Annotation paramAnnotation : paramAnnotations) {
                                        if (paramAnnotation instanceof JSONField) {
                                            annotation = (JSONField) paramAnnotation;
                                            break;
                                        }
                                    }
                                }
                                if (annotation == null && (field = ParserConfig.getFieldFromCache(propertyName5, fieldCacheMap)) != null) {
                                    annotation = (JSONField) field.getAnnotation(JSONField.class);
                                }
                            }
                        }
                    }
                    if (annotation != null) {
                        if (annotation.serialize()) {
                            ordinal = annotation.ordinal();
                            serialzeFeatures = SerializerFeature.of(annotation.serialzeFeatures());
                            parserFeatures = Feature.of(annotation.parseFeatures());
                            if (annotation.name().length() != 0) {
                                String propertyName6 = annotation.name();
                                if (aliasMap == null || (propertyName6 = aliasMap.get(propertyName6)) != null) {
                                    FieldInfo fieldInfo = new FieldInfo(propertyName6, method, null, clazz, null, ordinal, serialzeFeatures, parserFeatures, annotation, null, null);
                                    fieldInfoMap.put(propertyName6, fieldInfo);
                                }
                            } else {
                                if (annotation.label().length() != 0) {
                                    label = annotation.label();
                                }
                                if (!methodName.startsWith("get")) {
                                    if (methodName.length() < 4) {
                                    }
                                } else if (!methodName.startsWith("is")) {
                                }
                            }
                        }
                    } else if (!methodName.startsWith("get")) {
                        if (methodName.length() < 4 && !methodName.equals("getClass") && (!methodName.equals("getDeclaringClass") || !clazz.isEnum())) {
                            char c3 = methodName.charAt(3);
                            if (Character.isUpperCase(c3) || c3 > 512) {
                                if (compatibleWithJavaBean) {
                                    propertyName3 = decapitalize(methodName.substring(3));
                                } else {
                                    propertyName3 = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                                }
                                propertyName4 = getPropertyNameByCompatibleFieldName(fieldCacheMap, methodName, propertyName3, 3);
                            } else if (c3 == '_') {
                                propertyName4 = methodName.substring(4);
                            } else if (c3 == 'f') {
                                propertyName4 = methodName.substring(3);
                            } else if (methodName.length() >= 5 && Character.isUpperCase(methodName.charAt(4))) {
                                propertyName4 = decapitalize(methodName.substring(3));
                            }
                            boolean ignore = isJSONTypeIgnore(clazz, propertyName4);
                            if (!ignore) {
                                Field field2 = ParserConfig.getFieldFromCache(propertyName4, fieldCacheMap);
                                if (field2 == null && propertyName4.length() > 1 && (ch = propertyName4.charAt(1)) >= 'A' && ch <= 'Z') {
                                    String javaBeanCompatiblePropertyName = decapitalize(methodName.substring(3));
                                    field2 = ParserConfig.getFieldFromCache(javaBeanCompatiblePropertyName, fieldCacheMap);
                                }
                                JSONField fieldAnnotation = null;
                                if (field2 != null && (fieldAnnotation = (JSONField) field2.getAnnotation(JSONField.class)) != null) {
                                    if (fieldAnnotation.serialize()) {
                                        ordinal = fieldAnnotation.ordinal();
                                        serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                                        parserFeatures = Feature.of(fieldAnnotation.parseFeatures());
                                        if (fieldAnnotation.name().length() != 0) {
                                            propertyName4 = fieldAnnotation.name();
                                            if (aliasMap == null || (propertyName4 = aliasMap.get(propertyName4)) != null) {
                                            }
                                        }
                                        if (fieldAnnotation.label().length() != 0) {
                                            label = fieldAnnotation.label();
                                        }
                                        if (aliasMap != null) {
                                        }
                                        if (propertyNamingStrategy != null) {
                                            propertyName4 = propertyNamingStrategy.translate(propertyName4);
                                        }
                                        FieldInfo fieldInfo2 = new FieldInfo(propertyName4, method, field2, clazz, null, ordinal, serialzeFeatures, parserFeatures, annotation, fieldAnnotation, label);
                                        fieldInfoMap.put(propertyName4, fieldInfo2);
                                        if (!methodName.startsWith("is")) {
                                        }
                                    }
                                } else if (aliasMap != null || (propertyName4 = aliasMap.get(propertyName4)) != null) {
                                    if (propertyNamingStrategy != null) {
                                        propertyName4 = propertyNamingStrategy.translate(propertyName4);
                                    }
                                    FieldInfo fieldInfo3 = new FieldInfo(propertyName4, method, field2, clazz, null, ordinal, serialzeFeatures, parserFeatures, annotation, fieldAnnotation, label);
                                    fieldInfoMap.put(propertyName4, fieldInfo3);
                                    if (!methodName.startsWith("is")) {
                                    }
                                }
                            }
                        }
                    } else if (!methodName.startsWith("is") && methodName.length() >= 3 && (method.getReturnType() == Boolean.TYPE || method.getReturnType() == Boolean.class)) {
                        char c2 = methodName.charAt(2);
                        if (Character.isUpperCase(c2)) {
                            if (compatibleWithJavaBean) {
                                propertyName2 = decapitalize(methodName.substring(2));
                            } else {
                                propertyName2 = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
                            }
                            propertyName = getPropertyNameByCompatibleFieldName(fieldCacheMap, methodName, propertyName2, 2);
                        } else if (c2 == '_') {
                            propertyName = methodName.substring(3);
                        } else if (c2 == 'f') {
                            propertyName = methodName.substring(2);
                        }
                        boolean ignore2 = isJSONTypeIgnore(clazz, propertyName);
                        if (!ignore2) {
                            Field field3 = ParserConfig.getFieldFromCache(propertyName, fieldCacheMap);
                            if (field3 == null) {
                                field3 = ParserConfig.getFieldFromCache(methodName, fieldCacheMap);
                            }
                            JSONField fieldAnnotation2 = null;
                            if (field3 != null && (fieldAnnotation2 = (JSONField) field3.getAnnotation(JSONField.class)) != null) {
                                if (fieldAnnotation2.serialize()) {
                                    ordinal = fieldAnnotation2.ordinal();
                                    serialzeFeatures = SerializerFeature.of(fieldAnnotation2.serialzeFeatures());
                                    parserFeatures = Feature.of(fieldAnnotation2.parseFeatures());
                                    if (fieldAnnotation2.name().length() != 0) {
                                        propertyName = fieldAnnotation2.name();
                                        if (aliasMap == null || (propertyName = aliasMap.get(propertyName)) != null) {
                                        }
                                    }
                                    if (fieldAnnotation2.label().length() != 0) {
                                        label = fieldAnnotation2.label();
                                    }
                                    if (aliasMap != null) {
                                        if (propertyNamingStrategy != null) {
                                            propertyName = propertyNamingStrategy.translate(propertyName);
                                        }
                                        if (!fieldInfoMap.containsKey(propertyName)) {
                                            FieldInfo fieldInfo4 = new FieldInfo(propertyName, method, field3, clazz, null, ordinal, serialzeFeatures, parserFeatures, annotation, fieldAnnotation2, label);
                                            fieldInfoMap.put(propertyName, fieldInfo4);
                                        }
                                    } else {
                                        if (propertyNamingStrategy != null) {
                                            propertyName = propertyNamingStrategy.translate(propertyName);
                                        }
                                        if (!fieldInfoMap.containsKey(propertyName)) {
                                            FieldInfo fieldInfo5 = new FieldInfo(propertyName, method, field3, clazz, null, ordinal, serialzeFeatures, parserFeatures, annotation, fieldAnnotation2, label);
                                            fieldInfoMap.put(propertyName, fieldInfo5);
                                        }
                                    }
                                }
                            } else if (aliasMap != null || (propertyName = aliasMap.get(propertyName)) != null) {
                                if (propertyNamingStrategy != null) {
                                    propertyName = propertyNamingStrategy.translate(propertyName);
                                }
                                if (!fieldInfoMap.containsKey(propertyName)) {
                                    FieldInfo fieldInfo6 = new FieldInfo(propertyName, method, field3, clazz, null, ordinal, serialzeFeatures, parserFeatures, annotation, fieldAnnotation2, label);
                                    fieldInfoMap.put(propertyName, fieldInfo6);
                                }
                            }
                        }
                    }
                }
                i = i2 + 1;
            } else {
                Field[] fields = clazz.getFields();
                computeFields(clazz, aliasMap, propertyNamingStrategy, fieldInfoMap, fields);
                return getFieldInfos(clazz, sorted, fieldInfoMap);
            }
        }
    }

    private static List<FieldInfo> getFieldInfos(Class<?> clazz, boolean sorted, Map<String, FieldInfo> fieldInfoMap) {
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        String[] orders = null;
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation != null) {
            orders = annotation.orders();
        }
        if (orders != null && orders.length > 0) {
            LinkedHashMap<String, FieldInfo> map = new LinkedHashMap<>(fieldInfoList.size());
            for (FieldInfo field : fieldInfoMap.values()) {
                map.put(field.name, field);
            }
            for (String item : orders) {
                FieldInfo field2 = map.get(item);
                if (field2 != null) {
                    fieldInfoList.add(field2);
                    map.remove(item);
                }
            }
            Iterator<FieldInfo> it = map.values().iterator();
            while (it.hasNext()) {
                fieldInfoList.add(it.next());
            }
        } else {
            for (FieldInfo fieldInfo : fieldInfoMap.values()) {
                fieldInfoList.add(fieldInfo);
            }
            if (sorted) {
                Collections.sort(fieldInfoList);
            }
        }
        return fieldInfoList;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x006b A[DONT_INVERT, PHI: r2
  0x006b: PHI (r2v2 'propertyName' java.lang.String) = (r2v1 'propertyName' java.lang.String), (r2v6 'propertyName' java.lang.String) binds: [B:17:0x005f, B:19:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:21:0x006d  */
    /* JADX WARN: Code duplicated, block: B:24:0x007b  */
    /* JADX WARN: Code duplicated, block: B:30:0x0013 A[SYNTHETIC] */
    private static void computeFields(Class<?> clazz, Map<String, String> aliasMap, PropertyNamingStrategy propertyNamingStrategy, Map<String, FieldInfo> fieldInfoMap, Field[] fields) {
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                JSONField fieldAnnotation = (JSONField) field.getAnnotation(JSONField.class);
                int ordinal = 0;
                int serialzeFeatures = 0;
                int parserFeatures = 0;
                String propertyName = field.getName();
                String label = null;
                if (fieldAnnotation != null) {
                    if (fieldAnnotation.serialize()) {
                        ordinal = fieldAnnotation.ordinal();
                        serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                        parserFeatures = Feature.of(fieldAnnotation.parseFeatures());
                        if (fieldAnnotation.name().length() != 0) {
                            propertyName = fieldAnnotation.name();
                        }
                        if (fieldAnnotation.label().length() != 0) {
                            label = fieldAnnotation.label();
                        }
                        if (aliasMap != null) {
                            if (propertyNamingStrategy != null) {
                                propertyName = propertyNamingStrategy.translate(propertyName);
                            }
                            if (!fieldInfoMap.containsKey(propertyName)) {
                                FieldInfo fieldInfo = new FieldInfo(propertyName, null, field, clazz, null, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, label);
                                fieldInfoMap.put(propertyName, fieldInfo);
                            }
                        } else {
                            if (propertyNamingStrategy != null) {
                                propertyName = propertyNamingStrategy.translate(propertyName);
                            }
                            if (!fieldInfoMap.containsKey(propertyName)) {
                                FieldInfo fieldInfo2 = new FieldInfo(propertyName, null, field, clazz, null, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, label);
                                fieldInfoMap.put(propertyName, fieldInfo2);
                            }
                        }
                    }
                } else if (aliasMap != null || (propertyName = aliasMap.get(propertyName)) != null) {
                    if (propertyNamingStrategy != null) {
                        propertyName = propertyNamingStrategy.translate(propertyName);
                    }
                    if (!fieldInfoMap.containsKey(propertyName)) {
                        FieldInfo fieldInfo3 = new FieldInfo(propertyName, null, field, clazz, null, ordinal, serialzeFeatures, parserFeatures, null, fieldAnnotation, label);
                        fieldInfoMap.put(propertyName, fieldInfo3);
                    }
                }
            }
        }
    }

    private static String getPropertyNameByCompatibleFieldName(Map<String, Field> fieldCacheMap, String methodName, String propertyName, int fromIdx) {
        if (!compatibleWithFieldName || fieldCacheMap.containsKey(propertyName)) {
            return propertyName;
        }
        String tempPropertyName = methodName.substring(fromIdx);
        return fieldCacheMap.containsKey(tempPropertyName) ? tempPropertyName : propertyName;
    }

    public static JSONField getSuperMethodAnnotation(Class<?> clazz, Method method) {
        JSONField annotation;
        JSONField annotation2;
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            Class<?>[] types = method.getParameterTypes();
            for (Class<?> interfaceClass : interfaces) {
                for (Method interfaceMethod : interfaceClass.getMethods()) {
                    Class<?>[] interfaceTypes = interfaceMethod.getParameterTypes();
                    if (interfaceTypes.length == types.length && interfaceMethod.getName().equals(method.getName())) {
                        boolean match = true;
                        for (int i = 0; i < types.length; i++) {
                            if (!interfaceTypes[i].equals(types[i])) {
                                match = false;
                                break;
                            }
                        }
                        if (match && (annotation2 = (JSONField) interfaceMethod.getAnnotation(JSONField.class)) != null) {
                            return annotation2;
                        }
                    }
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && Modifier.isAbstract(superClass.getModifiers())) {
            Class<?>[] types2 = method.getParameterTypes();
            for (Method interfaceMethod2 : superClass.getMethods()) {
                Class<?>[] interfaceTypes2 = interfaceMethod2.getParameterTypes();
                if (interfaceTypes2.length == types2.length && interfaceMethod2.getName().equals(method.getName())) {
                    boolean match2 = true;
                    for (int i2 = 0; i2 < types2.length; i2++) {
                        if (!interfaceTypes2[i2].equals(types2[i2])) {
                            match2 = false;
                            break;
                        }
                    }
                    if (match2 && (annotation = (JSONField) interfaceMethod2.getAnnotation(JSONField.class)) != null) {
                        return annotation;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> clazz, String propertyName) {
        JSONType jsonType = (JSONType) getAnnotation(clazz, JSONType.class);
        if (jsonType != null) {
            String[] fields = jsonType.includes();
            if (fields.length > 0) {
                for (String str : fields) {
                    if (propertyName.equals(str)) {
                        return false;
                    }
                }
                return true;
            }
            for (String str2 : jsonType.ignores()) {
                if (propertyName.equals(str2)) {
                    return true;
                }
            }
        }
        return (clazz.getSuperclass() == Object.class || clazz.getSuperclass() == null || !isJSONTypeIgnore(clazz.getSuperclass(), propertyName)) ? false : true;
    }

    public static boolean isGenericParamType(Type type) {
        Type superType;
        if (type instanceof ParameterizedType) {
            return true;
        }
        if (!(type instanceof Class) || (superType = ((Class) type).getGenericSuperclass()) == Object.class) {
            return false;
        }
        return isGenericParamType(superType);
    }

    public static Type getGenericParamType(Type type) {
        if (!(type instanceof ParameterizedType) && (type instanceof Class)) {
            return getGenericParamType(((Class) type).getGenericSuperclass());
        }
        return type;
    }

    public static Type unwrapOptional(Type type) {
        if (!optionalClassInited) {
            try {
                optionalClass = Class.forName("java.util.Optional");
            } catch (Exception e) {
            } finally {
                optionalClassInited = true;
            }
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() == optionalClass) {
                return parameterizedType.getActualTypeArguments()[0];
            }
            return type;
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        if (type instanceof TypeVariable) {
            Type boundType = ((TypeVariable) type).getBounds()[0];
            return (Class) boundType;
        }
        if (type instanceof WildcardType) {
            Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            if (upperBounds.length == 1) {
                return getClass(upperBounds[0]);
            }
        }
        return Object.class;
    }

    public static Field getField(Class<?> clazz, String fieldName, Field[] declaredFields) {
        char c0;
        char c1;
        for (Field field : declaredFields) {
            String itemName = field.getName();
            if (!fieldName.equals(itemName)) {
                if (fieldName.length() > 2 && (c0 = fieldName.charAt(0)) >= 'a' && c0 <= 'z' && (c1 = fieldName.charAt(1)) >= 'A' && c1 <= 'Z' && fieldName.equalsIgnoreCase(itemName)) {
                    return field;
                }
            } else {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            return getField(superClass, fieldName, superClass.getDeclaredFields());
        }
        return null;
    }

    public static int getParserFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) getAnnotation(clazz, JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return Feature.of(annotation.parseFeatures());
    }

    public static String decapitalize(String name) {
        if (name != null && name.length() != 0) {
            if (name.length() <= 1 || !Character.isUpperCase(name.charAt(1)) || !Character.isUpperCase(name.charAt(0))) {
                char[] chars = name.toCharArray();
                chars[0] = Character.toLowerCase(chars[0]);
                return new String(chars);
            }
            return name;
        }
        return name;
    }

    static void setAccessible(AccessibleObject obj) {
        if (setAccessibleEnable && !obj.isAccessible()) {
            try {
                obj.setAccessible(true);
            } catch (AccessControlException e) {
                setAccessibleEnable = false;
            }
        }
    }

    public static Type getCollectionItemType(Type fieldType) {
        Type itemType = null;
        if (fieldType instanceof ParameterizedType) {
            Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            if (actualTypeArgument instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualTypeArgument;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    actualTypeArgument = upperBounds[0];
                }
            }
            itemType = actualTypeArgument;
        } else if (fieldType instanceof Class) {
            Class<?> clazz = (Class) fieldType;
            if (!clazz.getName().startsWith("java.")) {
                Type superClass = clazz.getGenericSuperclass();
                itemType = getCollectionItemType(superClass);
            }
        }
        if (itemType == null) {
            return Object.class;
        }
        return itemType;
    }

    public static Class<?> getCollectionItemClass(Type fieldType) {
        if (fieldType instanceof ParameterizedType) {
            Type actualTypeArgument = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            if (actualTypeArgument instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualTypeArgument;
                Type[] upperBounds = wildcardType.getUpperBounds();
                if (upperBounds.length == 1) {
                    actualTypeArgument = upperBounds[0];
                }
            }
            if (actualTypeArgument instanceof Class) {
                Class<?> itemClass = (Class) actualTypeArgument;
                if (Modifier.isPublic(itemClass.getModifiers())) {
                    return itemClass;
                }
                throw new JSONException("can not create ASMParser");
            }
            throw new JSONException("can not create ASMParser");
        }
        return Object.class;
    }

    public static Collection createCollection(Type type) {
        Type itemType;
        Class<?> rawClass = getRawClass(type);
        if (rawClass == AbstractCollection.class || rawClass == Collection.class) {
            Collection list = new ArrayList();
            return list;
        }
        if (rawClass.isAssignableFrom(HashSet.class)) {
            Collection list2 = new HashSet();
            return list2;
        }
        if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
            Collection list3 = new LinkedHashSet();
            return list3;
        }
        if (rawClass.isAssignableFrom(TreeSet.class)) {
            Collection list4 = new TreeSet();
            return list4;
        }
        if (rawClass.isAssignableFrom(ArrayList.class)) {
            Collection list5 = new ArrayList();
            return list5;
        }
        if (rawClass.isAssignableFrom(EnumSet.class)) {
            if (type instanceof ParameterizedType) {
                itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                itemType = Object.class;
            }
            Collection list6 = EnumSet.noneOf((Class) itemType);
            return list6;
        }
        try {
            Collection list7 = (Collection) rawClass.newInstance();
            return list7;
        } catch (Exception e) {
            throw new JSONException("create instance error, class " + rawClass.getName());
        }
    }

    public static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        }
        throw new JSONException("TODO");
    }

    public static boolean isProxy(Class<?> clazz) {
        for (Class<?> item : clazz.getInterfaces()) {
            String interfaceName = item.getName();
            if (interfaceName.equals("net.sf.cglib.proxy.Factory") || interfaceName.equals("org.springframework.cglib.proxy.Factory") || interfaceName.equals("javassist.util.proxy.ProxyObject") || interfaceName.equals("org.apache.ibatis.javassist.util.proxy.ProxyObject")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTransient(Method method) {
        boolean z = true;
        if (method == null) {
            return false;
        }
        if (!transientClassInited) {
            try {
                transientClass = Class.forName("java.beans.Transient");
            } catch (Exception e) {
            } finally {
                transientClassInited = true;
            }
        }
        if (transientClass == null) {
            return false;
        }
        Annotation annotation = method.getAnnotation(transientClass);
        return annotation != null;
    }

    public static boolean isAnnotationPresentOneToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_OneToMany == null && !class_OneToMany_error) {
            try {
                class_OneToMany = Class.forName("javax.persistence.OneToMany");
            } catch (Throwable th) {
                class_OneToMany_error = true;
            }
        }
        if (class_OneToMany != null) {
            return method.isAnnotationPresent(class_OneToMany);
        }
        return false;
    }

    public static boolean isAnnotationPresentManyToMany(Method method) {
        if (method == null) {
            return false;
        }
        if (class_ManyToMany == null && !class_ManyToMany_error) {
            try {
                class_ManyToMany = Class.forName("javax.persistence.ManyToMany");
            } catch (Throwable th) {
                class_ManyToMany_error = true;
            }
        }
        if (class_ManyToMany != null) {
            return method.isAnnotationPresent(class_OneToMany) || method.isAnnotationPresent(class_ManyToMany);
        }
        return false;
    }

    public static boolean isHibernateInitialized(Object object) {
        if (object == null) {
            return false;
        }
        if (method_HibernateIsInitialized == null && !method_HibernateIsInitialized_error) {
            try {
                Class<?> class_Hibernate = Class.forName("org.hibernate.Hibernate");
                method_HibernateIsInitialized = class_Hibernate.getMethod("isInitialized", Object.class);
            } catch (Throwable th) {
                method_HibernateIsInitialized_error = true;
            }
        }
        if (method_HibernateIsInitialized != null) {
            try {
                Boolean initialized = (Boolean) method_HibernateIsInitialized.invoke(null, object);
                return initialized.booleanValue();
            } catch (Throwable th2) {
            }
        }
        return true;
    }

    public static long fnv1a_64_lower(String key) {
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch != '_' && ch != '-') {
                if (ch >= 'A' && ch <= 'Z') {
                    ch = (char) (ch + ' ');
                }
                hashCode = (hashCode ^ ((long) ch)) * 1099511628211L;
            }
        }
        return hashCode;
    }

    public static long fnv1a_64(String key) {
        long hashCode = -3750763034362895579L;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            hashCode = (hashCode ^ ((long) ch)) * 1099511628211L;
        }
        return hashCode;
    }

    public static boolean isKotlin(Class clazz) {
        if (kotlin_metadata == null && !kotlin_metadata_error) {
            try {
                kotlin_metadata = Class.forName("kotlin.Metadata");
            } catch (Throwable th) {
                kotlin_metadata_error = true;
            }
        }
        if (kotlin_metadata == null) {
            return false;
        }
        return clazz.isAnnotationPresent(kotlin_metadata);
    }

    public static Constructor getKoltinConstructor(Constructor[] constructors) {
        Constructor creatorConstructor = null;
        for (Constructor constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if ((parameterTypes.length <= 0 || !parameterTypes[parameterTypes.length - 1].getName().equals("kotlin.jvm.internal.DefaultConstructorMarker")) && (creatorConstructor == null || creatorConstructor.getParameterTypes().length < parameterTypes.length)) {
                creatorConstructor = constructor;
            }
        }
        return creatorConstructor;
    }

    public static String[] getKoltinConstructorParameters(Class clazz) {
        if (kotlin_kclass_constructor == null && !kotlin_class_klass_error) {
            try {
                kotlin_kclass_constructor = Class.forName("kotlin.reflect.jvm.internal.KClassImpl").getConstructor(Class.class);
            } catch (Throwable th) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kclass_constructor == null) {
            return null;
        }
        if (kotlin_kclass_getConstructors == null && !kotlin_class_klass_error) {
            try {
                kotlin_kclass_getConstructors = Class.forName("kotlin.reflect.jvm.internal.KClassImpl").getMethod("getConstructors", new Class[0]);
            } catch (Throwable th2) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kfunction_getParameters == null && !kotlin_class_klass_error) {
            try {
                kotlin_kfunction_getParameters = Class.forName("kotlin.reflect.KFunction").getMethod("getParameters", new Class[0]);
            } catch (Throwable th3) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_kparameter_getName == null && !kotlin_class_klass_error) {
            try {
                kotlin_kparameter_getName = Class.forName("kotlin.reflect.KParameter").getMethod("getName", new Class[0]);
            } catch (Throwable th4) {
                kotlin_class_klass_error = true;
            }
        }
        if (kotlin_error) {
            return null;
        }
        Object constructor = null;
        try {
            Object kclassImpl = kotlin_kclass_constructor.newInstance(clazz);
            Iterable it = (Iterable) kotlin_kclass_getConstructors.invoke(kclassImpl, new Object[0]);
            Iterator iterator = it.iterator();
            while (iterator.hasNext()) {
                constructor = iterator.next();
                iterator.hasNext();
            }
            List parameters = (List) kotlin_kfunction_getParameters.invoke(constructor, new Object[0]);
            String[] names = new String[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                names[i] = (String) kotlin_kparameter_getName.invoke(param, new Object[0]);
            }
            return names;
        } catch (Throwable e) {
            e.printStackTrace();
            kotlin_error = true;
            return null;
        }
    }

    private static boolean isKotlinIgnore(Class clazz, String methodName) {
        String[] ignores;
        if (kotlinIgnores == null && !kotlinIgnores_error) {
            try {
                Map<Class, String[]> map = new HashMap<>();
                map.put(Class.forName("kotlin.ranges.CharRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.IntRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.LongRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.ClosedFloatRange"), new String[]{"getEndInclusive", "isEmpty"});
                map.put(Class.forName("kotlin.ranges.ClosedDoubleRange"), new String[]{"getEndInclusive", "isEmpty"});
                kotlinIgnores = map;
            } catch (Throwable th) {
                kotlinIgnores_error = true;
            }
        }
        if (kotlinIgnores == null || (ignores = kotlinIgnores.get(clazz)) == null) {
            return false;
        }
        return Arrays.binarySearch(ignores, methodName) >= 0;
    }

    public static <A extends Annotation> A getAnnotation(Class<?> cls, Class<A> cls2) {
        A a = (A) cls.getAnnotation(cls2);
        if (a != null) {
            return a;
        }
        if (cls.getAnnotations().length > 0) {
            for (Annotation annotation : cls.getAnnotations()) {
                A a2 = (A) annotation.annotationType().getAnnotation(cls2);
                if (a2 != null) {
                    return a2;
                }
            }
        }
        return null;
    }
}
