package com.google.zxing.client.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.DecodeHintType;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class DecodeHintManager {
    private static final String TAG = DecodeHintManager.class.getSimpleName();
    private static final Pattern COMMA = Pattern.compile(",");

    private DecodeHintManager() {
    }

    public static Map<DecodeHintType, Object> parseDecodeHints(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null || extras.isEmpty()) {
            return null;
        }
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        for (DecodeHintType hintType : DecodeHintType.values()) {
            if (hintType != DecodeHintType.CHARACTER_SET && hintType != DecodeHintType.NEED_RESULT_POINT_CALLBACK && hintType != DecodeHintType.POSSIBLE_FORMATS) {
                String hintName = hintType.name();
                if (extras.containsKey(hintName)) {
                    if (hintType.getValueType().equals(Void.class)) {
                        hints.put(hintType, Boolean.TRUE);
                    } else {
                        Object hintData = extras.get(hintName);
                        if (hintType.getValueType().isInstance(hintData)) {
                            hints.put(hintType, hintData);
                        } else {
                            Log.w(TAG, "Ignoring hint " + hintType + " because it is not assignable from " + hintData);
                        }
                    }
                }
            }
        }
        Log.i(TAG, "Hints from the Intent: " + hints);
        return hints;
    }
}
