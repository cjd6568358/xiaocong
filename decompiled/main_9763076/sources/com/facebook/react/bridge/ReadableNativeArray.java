package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class ReadableNativeArray extends NativeArray implements ReadableArray {
    @Override // com.facebook.react.bridge.ReadableArray
    public native ReadableNativeArray getArray(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native boolean getBoolean(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native double getDouble(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native int getInt(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native ReadableNativeMap getMap(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native String getString(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native ReadableType getType(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native boolean isNull(int i);

    @Override // com.facebook.react.bridge.ReadableArray
    public native int size();

    static {
        ReactBridge.staticInit();
    }

    protected ReadableNativeArray(HybridData hybridData) {
        super(hybridData);
    }

    public ArrayList<Object> toArrayList() {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            switch (getType(i)) {
                case Null:
                    arrayList.add(null);
                    break;
                case Boolean:
                    arrayList.add(Boolean.valueOf(getBoolean(i)));
                    break;
                case Number:
                    arrayList.add(Double.valueOf(getDouble(i)));
                    break;
                case String:
                    arrayList.add(getString(i));
                    break;
                case Map:
                    arrayList.add(getMap(i).toHashMap());
                    break;
                case Array:
                    arrayList.add(getArray(i).toArrayList());
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object at index: " + i + ".");
            }
        }
        return arrayList;
    }
}
