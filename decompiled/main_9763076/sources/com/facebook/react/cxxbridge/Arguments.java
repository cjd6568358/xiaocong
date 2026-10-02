package com.facebook.react.cxxbridge;

import android.os.Bundle;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Arguments {
    private static Object makeNativeObject(Object object) {
        if (object == null) {
            return null;
        }
        if ((object instanceof Float) || (object instanceof Long) || (object instanceof Byte) || (object instanceof Short)) {
            return new Double(((Number) object).doubleValue());
        }
        if (object.getClass().isArray()) {
            return makeNativeArray(object);
        }
        if (object instanceof List) {
            return makeNativeArray((List) object);
        }
        if (object instanceof Map) {
            return makeNativeMap((Map<String, Object>) object);
        }
        if (object instanceof Bundle) {
            return makeNativeMap((Bundle) object);
        }
        return object;
    }

    public static WritableNativeArray makeNativeArray(List objects) {
        WritableNativeArray nativeArray = new WritableNativeArray();
        if (objects != null) {
            Iterator it = objects.iterator();
            while (it.hasNext()) {
                Object elem = makeNativeObject(it.next());
                if (elem == null) {
                    nativeArray.pushNull();
                } else if (elem instanceof Boolean) {
                    nativeArray.pushBoolean(((Boolean) elem).booleanValue());
                } else if (elem instanceof Integer) {
                    nativeArray.pushInt(((Integer) elem).intValue());
                } else if (elem instanceof Double) {
                    nativeArray.pushDouble(((Double) elem).doubleValue());
                } else if (elem instanceof String) {
                    nativeArray.pushString((String) elem);
                } else if (elem instanceof WritableNativeArray) {
                    nativeArray.pushArray((WritableNativeArray) elem);
                } else if (elem instanceof WritableNativeMap) {
                    nativeArray.pushMap((WritableNativeMap) elem);
                } else {
                    throw new IllegalArgumentException("Could not convert " + elem.getClass());
                }
            }
        }
        return nativeArray;
    }

    public static <T> WritableNativeArray makeNativeArray(final Object objects) {
        return objects == null ? new WritableNativeArray() : makeNativeArray((List) new AbstractList() { // from class: com.facebook.react.cxxbridge.Arguments.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return Array.getLength(objects);
            }

            @Override // java.util.AbstractList, java.util.List
            public Object get(int index) {
                return Array.get(objects, index);
            }
        });
    }

    private static void addEntry(WritableNativeMap nativeMap, String key, Object value) {
        Object value2 = makeNativeObject(value);
        if (value2 == null) {
            nativeMap.putNull(key);
            return;
        }
        if (value2 instanceof Boolean) {
            nativeMap.putBoolean(key, ((Boolean) value2).booleanValue());
            return;
        }
        if (value2 instanceof Integer) {
            nativeMap.putInt(key, ((Integer) value2).intValue());
            return;
        }
        if (value2 instanceof Number) {
            nativeMap.putDouble(key, ((Number) value2).doubleValue());
            return;
        }
        if (value2 instanceof String) {
            nativeMap.putString(key, (String) value2);
        } else if (value2 instanceof WritableNativeArray) {
            nativeMap.putArray(key, (WritableNativeArray) value2);
        } else {
            if (value2 instanceof WritableNativeMap) {
                nativeMap.putMap(key, (WritableNativeMap) value2);
                return;
            }
            throw new IllegalArgumentException("Could not convert " + value2.getClass());
        }
    }

    public static WritableNativeMap makeNativeMap(Map<String, Object> objects) {
        WritableNativeMap nativeMap = new WritableNativeMap();
        if (objects != null) {
            for (Map.Entry<String, Object> entry : objects.entrySet()) {
                addEntry(nativeMap, entry.getKey(), entry.getValue());
            }
        }
        return nativeMap;
    }

    public static WritableNativeMap makeNativeMap(Bundle bundle) {
        WritableNativeMap nativeMap = new WritableNativeMap();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                addEntry(nativeMap, key, bundle.get(key));
            }
        }
        return nativeMap;
    }
}
