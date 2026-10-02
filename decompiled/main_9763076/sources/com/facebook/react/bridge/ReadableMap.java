package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ReadableMap {
    ReadableArray getArray(String str);

    boolean getBoolean(String str);

    double getDouble(String str);

    Dynamic getDynamic(String str);

    int getInt(String str);

    ReadableMap getMap(String str);

    String getString(String str);

    ReadableType getType(String str);

    boolean hasKey(String str);

    boolean isNull(String str);

    ReadableMapKeySetIterator keySetIterator();
}
