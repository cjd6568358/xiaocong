package com.ta.utdid2.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import com.ta.utdid2.b.a.d;
import com.ta.utdid2.b.a.h;
import com.ta.utdid2.b.a.i;
import com.tencent.android.tpush.common.Constants;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: AidStorageController.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static final String TAG = c.class.getName();
    private static Map<String, String> a = new ConcurrentHashMap();
    private static Map<String, Long> b = new ConcurrentHashMap();

    public static void a(Context context, String str, String str2, String str3) {
        if (context == null) {
            Log.e(TAG, "no context!");
            return;
        }
        String strC = c(str, str3);
        long jCurrentTimeMillis = System.currentTimeMillis();
        a.put(strC, str2);
        b.put(strC, Long.valueOf(jCurrentTimeMillis));
        SharedPreferences sharedPreferences = context.getSharedPreferences("OfJbkLdFbPOMbGyP", 0);
        if (Build.VERSION.SDK_INT >= 9) {
            h.a(sharedPreferences.edit().putString("EvQwnbilKezpOJey".concat(strC), str2));
            h.a(sharedPreferences.edit().putLong("rKrMJgyAEbVtSQGi".concat(strC), jCurrentTimeMillis));
        } else {
            sharedPreferences.edit().putString("EvQwnbilKezpOJey".concat(strC), str2).commit();
            sharedPreferences.edit().putLong("rKrMJgyAEbVtSQGi".concat(strC), jCurrentTimeMillis).commit();
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static String m96a(Context context, String str, String str2) {
        if (context == null) {
            Log.e(TAG, "no context!");
            return Constants.MAIN_VERSION_TAG;
        }
        String strC = c(str, str2);
        String str3 = a.get(strC);
        if (d.e) {
            Log.d(TAG, "cache AID:" + str3);
        }
        if (i.m99a(str3)) {
            String string = context.getSharedPreferences("OfJbkLdFbPOMbGyP", 0).getString("EvQwnbilKezpOJey".concat(strC), Constants.MAIN_VERSION_TAG);
            a.put(strC, string);
            return string;
        }
        return str3;
    }

    public static long a(Context context, String str, String str2) {
        if (context == null) {
            Log.e(TAG, "no context!");
            return 0L;
        }
        String strC = c(str, str2);
        Long lValueOf = Long.valueOf(b.containsKey(strC) ? b.get(strC).longValue() : 0L);
        if (d.e) {
            Log.d(TAG, "cache AIDGenTime:" + lValueOf);
        }
        if (lValueOf.longValue() == 0) {
            lValueOf = Long.valueOf(context.getSharedPreferences("OfJbkLdFbPOMbGyP", 0).getLong("rKrMJgyAEbVtSQGi".concat(strC), 0L));
            b.put(strC, lValueOf);
        }
        return lValueOf.longValue();
    }

    private static String c(String str, String str2) {
        String strEncodeToString;
        if (Build.VERSION.SDK_INT >= 8) {
            strEncodeToString = com.ta.utdid2.b.a.c.encodeToString(str.concat(str2).getBytes(), 2);
        } else {
            strEncodeToString = com.ta.utdid2.b.a.b.encodeToString(str.concat(str2).getBytes(), 2);
        }
        if (d.e) {
            Log.d(TAG, "encodedName:" + strEncodeToString);
        }
        return strEncodeToString;
    }
}
