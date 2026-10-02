package com.tencent.android.tpush.service.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.c.f;
import com.tencent.android.tpush.service.e.m;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static void a(Context context, String str) {
        SharedPreferences defaultSharedPreferences;
        if (context != null && !m.b(str)) {
            for (String str2 : str.split(";;")) {
                try {
                    String[] strArrSplit = str2.split(",");
                    String str3 = strArrSplit[0];
                    String str4 = strArrSplit[1];
                    if (strArrSplit.length == 4 && str4.length() == 1) {
                        String str5 = strArrSplit[2];
                        String str6 = strArrSplit[3];
                        if (!m.b(str3)) {
                            if (Build.VERSION.SDK_INT >= 11) {
                                defaultSharedPreferences = context.getSharedPreferences(str3, 4);
                            } else {
                                defaultSharedPreferences = context.getSharedPreferences(str3, 0);
                            }
                        } else {
                            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                        }
                        SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
                        if (!m.b(str5) && !m.b(str6)) {
                            if ("S".equals(str4)) {
                                editorEdit.putString(str5, str6);
                            } else if ("L".equals(str4)) {
                                editorEdit.putLong(str5, Long.valueOf(str6).longValue());
                            } else if ("I".equals(str4)) {
                                editorEdit.putInt(str5, Integer.valueOf(str6).intValue());
                            } else if ("F".equals(str4)) {
                                editorEdit.putFloat(str5, Float.valueOf(str6).floatValue());
                            } else if ("B".equals(str4)) {
                                editorEdit.putBoolean(str5, Boolean.valueOf(str6).booleanValue());
                            }
                        }
                        editorEdit.commit();
                        Log.e(Constants.LogTag, defaultSharedPreferences + "," + str5 + "," + str6);
                    }
                } catch (Throwable th) {
                    Log.e(Constants.LogTag, "eeee", th);
                }
            }
        }
    }

    public static void b(Context context, String str) {
        if (context != null && !m.b(str)) {
            for (String str2 : str.split(";;")) {
                try {
                    String[] strArrSplit = str2.split(",");
                    String str3 = strArrSplit[0];
                    if (strArrSplit.length == 3 && str3.length() == 1) {
                        String str4 = strArrSplit[1];
                        String str5 = strArrSplit[2];
                        if (!m.b(str4) && !m.b(str5)) {
                            if ("S".equals(str3)) {
                                f.a(context).a(str4, str5);
                            } else if ("L".equals(str3)) {
                                f.a(context).a(str4, Long.valueOf(str5).longValue());
                            } else if ("I".equals(str3)) {
                                f.a(context).a(str4, Integer.valueOf(str5).intValue());
                            } else if ("F".equals(str3)) {
                                f.a(context).a(str4, Float.valueOf(str5).floatValue());
                            }
                        }
                    }
                } catch (Throwable th) {
                }
            }
        }
    }
}
