package com.facebook.react.bridge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaOnlyMap implements ReadableMap, WritableMap {
    private final Map mBackingMap;

    public static JavaOnlyMap of(Object... keysAndValues) {
        return new JavaOnlyMap(keysAndValues);
    }

    private JavaOnlyMap(Object... keysAndValues) {
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("You must provide the same number of keys and values");
        }
        this.mBackingMap = new HashMap();
        for (int i = 0; i < keysAndValues.length; i += 2) {
            this.mBackingMap.put(keysAndValues[i], keysAndValues[i + 1]);
        }
    }

    public JavaOnlyMap() {
        this.mBackingMap = new HashMap();
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public boolean hasKey(String name) {
        return this.mBackingMap.containsKey(name);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public boolean isNull(String name) {
        return this.mBackingMap.get(name) == null;
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public boolean getBoolean(String name) {
        return ((Boolean) this.mBackingMap.get(name)).booleanValue();
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public double getDouble(String name) {
        return ((Double) this.mBackingMap.get(name)).doubleValue();
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public int getInt(String name) {
        return ((Integer) this.mBackingMap.get(name)).intValue();
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public String getString(String name) {
        return (String) this.mBackingMap.get(name);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public JavaOnlyMap getMap(String name) {
        return (JavaOnlyMap) this.mBackingMap.get(name);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public JavaOnlyArray getArray(String name) {
        return (JavaOnlyArray) this.mBackingMap.get(name);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public Dynamic getDynamic(String name) {
        return DynamicFromMap.create(this, name);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public ReadableType getType(String name) {
        Object value = this.mBackingMap.get(name);
        if (value == null) {
            return ReadableType.Null;
        }
        if (value instanceof Number) {
            return ReadableType.Number;
        }
        if (value instanceof String) {
            return ReadableType.String;
        }
        if (value instanceof Boolean) {
            return ReadableType.Boolean;
        }
        if (value instanceof ReadableMap) {
            return ReadableType.Map;
        }
        if (value instanceof ReadableArray) {
            return ReadableType.Array;
        }
        if (value instanceof Dynamic) {
            return ((Dynamic) value).getType();
        }
        throw new IllegalArgumentException("Invalid value " + value.toString() + " for key " + name + "contained in JavaOnlyMap");
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public ReadableMapKeySetIterator keySetIterator() {
        return new ReadableMapKeySetIterator() { // from class: com.facebook.react.bridge.JavaOnlyMap.1
            Iterator<String> mIterator;

            {
                this.mIterator = JavaOnlyMap.this.mBackingMap.keySet().iterator();
            }

            @Override // com.facebook.react.bridge.ReadableMapKeySetIterator
            public boolean hasNextKey() {
                return this.mIterator.hasNext();
            }

            @Override // com.facebook.react.bridge.ReadableMapKeySetIterator
            public String nextKey() {
                return this.mIterator.next();
            }
        };
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putBoolean(String key, boolean value) {
        this.mBackingMap.put(key, Boolean.valueOf(value));
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putDouble(String key, double value) {
        this.mBackingMap.put(key, Double.valueOf(value));
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putInt(String key, int value) {
        this.mBackingMap.put(key, Integer.valueOf(value));
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putString(String key, String value) {
        this.mBackingMap.put(key, value);
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putMap(String key, WritableMap value) {
        this.mBackingMap.put(key, value);
    }

    @Override // com.facebook.react.bridge.WritableMap
    public void putArray(String key, WritableArray value) {
        this.mBackingMap.put(key, value);
    }

    public String toString() {
        return this.mBackingMap.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JavaOnlyMap that = (JavaOnlyMap) o;
        if (this.mBackingMap != null) {
            if (this.mBackingMap.equals(that.mBackingMap)) {
                return true;
            }
        } else if (that.mBackingMap == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.mBackingMap != null) {
            return this.mBackingMap.hashCode();
        }
        return 0;
    }
}
