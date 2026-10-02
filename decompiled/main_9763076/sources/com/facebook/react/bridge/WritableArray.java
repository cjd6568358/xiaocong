package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface WritableArray extends ReadableArray {
    void pushArray(WritableArray writableArray);

    void pushBoolean(boolean z);

    void pushInt(int i);

    void pushMap(WritableMap writableMap);

    void pushNull();

    void pushString(String str);
}
