package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import com.facebook.proguard.annotations.DoNotStrip;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class ReadableNativeMap extends NativeMap implements ReadableMap {
    @Override // com.facebook.react.bridge.ReadableMap
    public native ReadableNativeArray getArray(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native boolean getBoolean(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native double getDouble(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native int getInt(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native ReadableNativeMap getMap(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native String getString(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native ReadableType getType(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native boolean hasKey(String str);

    @Override // com.facebook.react.bridge.ReadableMap
    public native boolean isNull(String str);

    static {
        ReactBridge.staticInit();
    }

    protected ReadableNativeMap(HybridData hybridData) {
        super(hybridData);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public Dynamic getDynamic(String name) {
        return DynamicFromMap.create(this, name);
    }

    @Override // com.facebook.react.bridge.ReadableMap
    public ReadableMapKeySetIterator keySetIterator() {
        return new ReadableNativeMapKeySetIterator(this);
    }

    public HashMap<String, Object> toHashMap() {
        ReadableMapKeySetIterator iterator = keySetIterator();
        HashMap<String, Object> hashMap = new HashMap<>();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (getType(key)) {
                case Null:
                    hashMap.put(key, null);
                    break;
                case Boolean:
                    hashMap.put(key, Boolean.valueOf(getBoolean(key)));
                    break;
                case Number:
                    hashMap.put(key, Double.valueOf(getDouble(key)));
                    break;
                case String:
                    hashMap.put(key, getString(key));
                    break;
                case Map:
                    hashMap.put(key, getMap(key).toHashMap());
                    break;
                case Array:
                    hashMap.put(key, getArray(key).toArrayList());
                    break;
                default:
                    throw new IllegalArgumentException("Could not convert object with key: " + key + ".");
            }
        }
        return hashMap;
    }

    @DoNotStrip
    private static class ReadableNativeMapKeySetIterator implements ReadableMapKeySetIterator {

        @DoNotStrip
        private final HybridData mHybridData;

        @DoNotStrip
        private final ReadableNativeMap mMap;

        private static native HybridData initHybrid(ReadableNativeMap readableNativeMap);

        @Override // com.facebook.react.bridge.ReadableMapKeySetIterator
        public native boolean hasNextKey();

        @Override // com.facebook.react.bridge.ReadableMapKeySetIterator
        public native String nextKey();

        public ReadableNativeMapKeySetIterator(ReadableNativeMap readableNativeMap) {
            this.mMap = readableNativeMap;
            this.mHybridData = initHybrid(readableNativeMap);
        }
    }
}
