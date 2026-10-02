package com.alibaba.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONObject extends JSON implements Serializable, Cloneable, InvocationHandler, Map<String, Object> {
    private static final long serialVersionUID = 1;
    private final Map<String, Object> map;

    public JSONObject() {
        this(16, false);
    }

    public JSONObject(Map<String, Object> map) {
        this.map = map;
    }

    public JSONObject(boolean ordered) {
        this(16, ordered);
    }

    public JSONObject(int initialCapacity) {
        this(initialCapacity, false);
    }

    public JSONObject(int initialCapacity, boolean ordered) {
        if (ordered) {
            this.map = new LinkedHashMap(initialCapacity);
        } else {
            this.map = new HashMap(initialCapacity);
        }
    }

    @Override // java.util.Map
    public int size() {
        return this.map.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override // java.util.Map
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override // java.util.Map
    public Object get(Object key) {
        Object val = this.map.get(key);
        if (val == null && (key instanceof Number)) {
            return this.map.get(key.toString());
        }
        return val;
    }

    public JSONObject getJSONObject(String key) {
        Object value = this.map.get(key);
        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }
        if (value instanceof String) {
            return JSON.parseObject((String) value);
        }
        return (JSONObject) toJSON(value);
    }

    public JSONArray getJSONArray(String key) {
        Object value = this.map.get(key);
        if (value instanceof JSONArray) {
            return (JSONArray) value;
        }
        if (value instanceof String) {
            return (JSONArray) JSON.parse((String) value);
        }
        return (JSONArray) toJSON(value);
    }

    public <T> T getObject(String str, Class<T> cls) {
        return (T) TypeUtils.castToJavaBean(this.map.get(str), cls);
    }

    public <T> T getObject(String str, Type type) {
        return (T) TypeUtils.cast(this.map.get(str), type, ParserConfig.getGlobalInstance());
    }

    public <T> T getObject(String str, TypeReference typeReference) {
        T t = (T) this.map.get(str);
        return typeReference == null ? t : (T) TypeUtils.cast(t, typeReference.getType(), ParserConfig.getGlobalInstance());
    }

    public Boolean getBoolean(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return TypeUtils.castToBoolean(value);
    }

    public byte[] getBytes(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return TypeUtils.castToBytes(value);
    }

    public boolean getBooleanValue(String key) {
        Object value = get(key);
        Boolean booleanVal = TypeUtils.castToBoolean(value);
        if (booleanVal == null) {
            return false;
        }
        return booleanVal.booleanValue();
    }

    public Byte getByte(String key) {
        Object value = get(key);
        return TypeUtils.castToByte(value);
    }

    public byte getByteValue(String key) {
        Object value = get(key);
        Byte byteVal = TypeUtils.castToByte(value);
        if (byteVal == null) {
            return (byte) 0;
        }
        return byteVal.byteValue();
    }

    public Short getShort(String key) {
        Object value = get(key);
        return TypeUtils.castToShort(value);
    }

    public short getShortValue(String key) {
        Object value = get(key);
        Short shortVal = TypeUtils.castToShort(value);
        if (shortVal == null) {
            return (short) 0;
        }
        return shortVal.shortValue();
    }

    public Integer getInteger(String key) {
        Object value = get(key);
        return TypeUtils.castToInt(value);
    }

    public int getIntValue(String key) {
        Object value = get(key);
        Integer intVal = TypeUtils.castToInt(value);
        if (intVal == null) {
            return 0;
        }
        return intVal.intValue();
    }

    public Long getLong(String key) {
        Object value = get(key);
        return TypeUtils.castToLong(value);
    }

    public long getLongValue(String key) {
        Object value = get(key);
        Long longVal = TypeUtils.castToLong(value);
        if (longVal == null) {
            return 0L;
        }
        return longVal.longValue();
    }

    public Float getFloat(String key) {
        Object value = get(key);
        return TypeUtils.castToFloat(value);
    }

    public float getFloatValue(String key) {
        Object value = get(key);
        Float floatValue = TypeUtils.castToFloat(value);
        if (floatValue == null) {
            return 0.0f;
        }
        return floatValue.floatValue();
    }

    public Double getDouble(String key) {
        Object value = get(key);
        return TypeUtils.castToDouble(value);
    }

    public double getDoubleValue(String key) {
        Object value = get(key);
        Double doubleValue = TypeUtils.castToDouble(value);
        if (doubleValue == null) {
            return 0.0d;
        }
        return doubleValue.doubleValue();
    }

    public BigDecimal getBigDecimal(String key) {
        Object value = get(key);
        return TypeUtils.castToBigDecimal(value);
    }

    public BigInteger getBigInteger(String key) {
        Object value = get(key);
        return TypeUtils.castToBigInteger(value);
    }

    public String getString(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public Date getDate(String key) {
        Object value = get(key);
        return TypeUtils.castToDate(value);
    }

    public java.sql.Date getSqlDate(String key) {
        Object value = get(key);
        return TypeUtils.castToSqlDate(value);
    }

    public Timestamp getTimestamp(String key) {
        Object value = get(key);
        return TypeUtils.castToTimestamp(value);
    }

    @Override // java.util.Map
    public Object put(String key, Object value) {
        return this.map.put(key, value);
    }

    public JSONObject fluentPut(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends String, ? extends Object> m) {
        this.map.putAll(m);
    }

    public JSONObject fluentPutAll(Map<? extends String, ? extends Object> m) {
        this.map.putAll(m);
        return this;
    }

    @Override // java.util.Map
    public void clear() {
        this.map.clear();
    }

    public JSONObject fluentClear() {
        this.map.clear();
        return this;
    }

    @Override // java.util.Map
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    public JSONObject fluentRemove(Object key) {
        this.map.remove(key);
        return this;
    }

    @Override // java.util.Map
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override // java.util.Map
    public Collection<Object> values() {
        return this.map.values();
    }

    @Override // java.util.Map
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public Object clone() {
        return new JSONObject((Map<String, Object>) (this.map instanceof LinkedHashMap ? new LinkedHashMap(this.map) : new HashMap(this.map)));
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.map.hashCode();
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 1) {
            if (method.getName().equals("equals")) {
                return Boolean.valueOf(equals(args[0]));
            }
            Class<?> returnType = method.getReturnType();
            if (returnType != Void.TYPE) {
                throw new JSONException("illegal setter");
            }
            String name = null;
            JSONField annotation = (JSONField) method.getAnnotation(JSONField.class);
            if (annotation != null && annotation.name().length() != 0) {
                name = annotation.name();
            }
            if (name == null) {
                String name2 = method.getName();
                if (!name2.startsWith("set")) {
                    throw new JSONException("illegal setter");
                }
                String name3 = name2.substring(3);
                if (name3.length() == 0) {
                    throw new JSONException("illegal setter");
                }
                name = Character.toLowerCase(name3.charAt(0)) + name3.substring(1);
            }
            this.map.put(name, args[0]);
            return null;
        }
        if (parameterTypes.length == 0) {
            Class<?> returnType2 = method.getReturnType();
            if (returnType2 == Void.TYPE) {
                throw new JSONException("illegal getter");
            }
            String name4 = null;
            JSONField annotation2 = (JSONField) method.getAnnotation(JSONField.class);
            if (annotation2 != null && annotation2.name().length() != 0) {
                name4 = annotation2.name();
            }
            if (name4 == null) {
                String name5 = method.getName();
                if (name5.startsWith("get")) {
                    String name6 = name5.substring(3);
                    if (name6.length() == 0) {
                        throw new JSONException("illegal getter");
                    }
                    name4 = Character.toLowerCase(name6.charAt(0)) + name6.substring(1);
                } else if (name5.startsWith("is")) {
                    String name7 = name5.substring(2);
                    if (name7.length() == 0) {
                        throw new JSONException("illegal getter");
                    }
                    name4 = Character.toLowerCase(name7.charAt(0)) + name7.substring(1);
                } else {
                    if (name5.startsWith("hashCode")) {
                        return Integer.valueOf(hashCode());
                    }
                    if (name5.startsWith("toString")) {
                        return toString();
                    }
                    throw new JSONException("illegal getter");
                }
            }
            Object value = this.map.get(name4);
            return TypeUtils.cast(value, method.getGenericReturnType(), ParserConfig.getGlobalInstance());
        }
        throw new UnsupportedOperationException(method.toGenericString());
    }

    public Map<String, Object> getInnerMap() {
        return this.map;
    }
}
