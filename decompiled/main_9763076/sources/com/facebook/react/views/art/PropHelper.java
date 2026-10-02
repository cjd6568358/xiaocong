package com.facebook.react.views.art;

import com.facebook.react.bridge.ReadableArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PropHelper {
    static float[] toFloatArray(ReadableArray value) {
        if (value == null) {
            return null;
        }
        float[] result = new float[value.size()];
        toFloatArray(value, result);
        return result;
    }

    static int toFloatArray(ReadableArray value, float[] into) {
        int length = value.size() > into.length ? into.length : value.size();
        for (int i = 0; i < length; i++) {
            into[i] = (float) value.getDouble(i);
        }
        return value.size();
    }
}
