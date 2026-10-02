package com.facebook.react.uimanager;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactStylesDiffMap {
    final ReadableMap mBackingMap;

    public ReactStylesDiffMap(ReadableMap props) {
        this.mBackingMap = props;
    }

    public boolean hasKey(String name) {
        return this.mBackingMap.hasKey(name);
    }

    public boolean isNull(String name) {
        return this.mBackingMap.isNull(name);
    }

    public boolean getBoolean(String name, boolean restoreNullToDefaultValue) {
        if (this.mBackingMap.isNull(name)) {
            return restoreNullToDefaultValue;
        }
        boolean restoreNullToDefaultValue2 = this.mBackingMap.getBoolean(name);
        return restoreNullToDefaultValue2;
    }

    public double getDouble(String name, double restoreNullToDefaultValue) {
        if (this.mBackingMap.isNull(name)) {
            return restoreNullToDefaultValue;
        }
        double restoreNullToDefaultValue2 = this.mBackingMap.getDouble(name);
        return restoreNullToDefaultValue2;
    }

    public float getFloat(String name, float restoreNullToDefaultValue) {
        if (this.mBackingMap.isNull(name)) {
            return restoreNullToDefaultValue;
        }
        float restoreNullToDefaultValue2 = (float) this.mBackingMap.getDouble(name);
        return restoreNullToDefaultValue2;
    }

    public int getInt(String name, int restoreNullToDefaultValue) {
        if (this.mBackingMap.isNull(name)) {
            return restoreNullToDefaultValue;
        }
        int restoreNullToDefaultValue2 = this.mBackingMap.getInt(name);
        return restoreNullToDefaultValue2;
    }

    public String getString(String name) {
        return this.mBackingMap.getString(name);
    }

    public ReadableArray getArray(String key) {
        return this.mBackingMap.getArray(key);
    }

    public ReadableMap getMap(String key) {
        return this.mBackingMap.getMap(key);
    }

    public Dynamic getDynamic(String key) {
        return this.mBackingMap.getDynamic(key);
    }

    public String toString() {
        return "{ " + getClass().getSimpleName() + ": " + this.mBackingMap.toString() + " }";
    }
}
