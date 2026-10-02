package com.facebook.react.bridge;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JavaOnlyArray implements ReadableArray, WritableArray {
    private final List mBackingList;

    public static JavaOnlyArray from(List list) {
        return new JavaOnlyArray(list);
    }

    private JavaOnlyArray(List list) {
        this.mBackingList = new ArrayList(list);
    }

    public JavaOnlyArray() {
        this.mBackingList = new ArrayList();
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public int size() {
        return this.mBackingList.size();
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public boolean isNull(int index) {
        return this.mBackingList.get(index) == null;
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public double getDouble(int index) {
        return ((Double) this.mBackingList.get(index)).doubleValue();
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public int getInt(int index) {
        return ((Integer) this.mBackingList.get(index)).intValue();
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public String getString(int index) {
        return (String) this.mBackingList.get(index);
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public JavaOnlyArray getArray(int index) {
        return (JavaOnlyArray) this.mBackingList.get(index);
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public boolean getBoolean(int index) {
        return ((Boolean) this.mBackingList.get(index)).booleanValue();
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public JavaOnlyMap getMap(int index) {
        return (JavaOnlyMap) this.mBackingList.get(index);
    }

    @Override // com.facebook.react.bridge.ReadableArray
    public ReadableType getType(int index) {
        Object object = this.mBackingList.get(index);
        if (object == null) {
            return ReadableType.Null;
        }
        if (object instanceof Boolean) {
            return ReadableType.Boolean;
        }
        if ((object instanceof Double) || (object instanceof Float) || (object instanceof Integer)) {
            return ReadableType.Number;
        }
        if (object instanceof String) {
            return ReadableType.String;
        }
        if (object instanceof ReadableArray) {
            return ReadableType.Array;
        }
        if (object instanceof ReadableMap) {
            return ReadableType.Map;
        }
        return null;
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushBoolean(boolean value) {
        this.mBackingList.add(Boolean.valueOf(value));
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushInt(int value) {
        this.mBackingList.add(Integer.valueOf(value));
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushString(String value) {
        this.mBackingList.add(value);
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushArray(WritableArray array) {
        this.mBackingList.add(array);
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushMap(WritableMap map) {
        this.mBackingList.add(map);
    }

    @Override // com.facebook.react.bridge.WritableArray
    public void pushNull() {
        this.mBackingList.add(null);
    }

    public String toString() {
        return this.mBackingList.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JavaOnlyArray that = (JavaOnlyArray) o;
        if (this.mBackingList != null) {
            if (this.mBackingList.equals(that.mBackingList)) {
                return true;
            }
        } else if (that.mBackingList == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.mBackingList != null) {
            return this.mBackingList.hashCode();
        }
        return 0;
    }
}
