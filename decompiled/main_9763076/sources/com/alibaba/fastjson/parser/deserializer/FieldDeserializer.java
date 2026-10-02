package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class FieldDeserializer {
    protected final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public abstract void parseField(DefaultJSONParser defaultJSONParser, Object obj, Type type, Map<String, Object> map);

    public FieldDeserializer(Class<?> clazz, FieldInfo fieldInfo) {
        this.clazz = clazz;
        this.fieldInfo = fieldInfo;
    }

    public int getFastMatchToken() {
        return 0;
    }

    public void setValue(Object object, boolean value) {
        setValue(object, Boolean.valueOf(value));
    }

    public void setValue(Object object, int value) {
        setValue(object, Integer.valueOf(value));
    }

    public void setValue(Object object, long value) {
        setValue(object, Long.valueOf(value));
    }

    public void setValue(Object object, String value) {
        setValue(object, (Object) value);
    }

    public void setValue(Object object, Object value) {
        if (value != null || !this.fieldInfo.fieldClass.isPrimitive()) {
            if (this.fieldInfo.fieldClass == String.class && this.fieldInfo.format != null && this.fieldInfo.format.equals("trim")) {
                value = ((String) value).trim();
            }
            try {
                Method method = this.fieldInfo.method;
                if (method != null) {
                    if (this.fieldInfo.getOnly) {
                        if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                            AtomicInteger atomic = (AtomicInteger) method.invoke(object, new Object[0]);
                            if (atomic != null) {
                                atomic.set(((AtomicInteger) value).get());
                            }
                        } else if (this.fieldInfo.fieldClass == AtomicLong.class) {
                            AtomicLong atomic2 = (AtomicLong) method.invoke(object, new Object[0]);
                            if (atomic2 != null) {
                                atomic2.set(((AtomicLong) value).get());
                            }
                        } else if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                            AtomicBoolean atomic3 = (AtomicBoolean) method.invoke(object, new Object[0]);
                            if (atomic3 != null) {
                                atomic3.set(((AtomicBoolean) value).get());
                            }
                        } else if (Map.class.isAssignableFrom(method.getReturnType())) {
                            Map map = (Map) method.invoke(object, new Object[0]);
                            if (map != null) {
                                map.putAll((Map) value);
                            }
                        } else {
                            Collection collection = (Collection) method.invoke(object, new Object[0]);
                            if (collection != null && value != null) {
                                collection.clear();
                                collection.addAll((Collection) value);
                            }
                        }
                    } else {
                        method.invoke(object, value);
                    }
                } else {
                    Field field = this.fieldInfo.field;
                    if (this.fieldInfo.getOnly) {
                        if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                            AtomicInteger atomic4 = (AtomicInteger) field.get(object);
                            if (atomic4 != null) {
                                atomic4.set(((AtomicInteger) value).get());
                            }
                        } else if (this.fieldInfo.fieldClass == AtomicLong.class) {
                            AtomicLong atomic5 = (AtomicLong) field.get(object);
                            if (atomic5 != null) {
                                atomic5.set(((AtomicLong) value).get());
                            }
                        } else if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                            AtomicBoolean atomic6 = (AtomicBoolean) field.get(object);
                            if (atomic6 != null) {
                                atomic6.set(((AtomicBoolean) value).get());
                            }
                        } else if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                            Map map2 = (Map) field.get(object);
                            if (map2 != null) {
                                map2.putAll((Map) value);
                            }
                        } else {
                            Collection collection2 = (Collection) field.get(object);
                            if (collection2 != null && value != null) {
                                collection2.clear();
                                collection2.addAll((Collection) value);
                            }
                        }
                    } else if (field != null) {
                        field.set(object, value);
                    }
                }
            } catch (Exception e) {
                throw new JSONException("set property error, " + this.fieldInfo.name, e);
            }
        }
    }
}
