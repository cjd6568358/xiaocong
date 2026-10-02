package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class d implements Runnable {
    private Context a;
    private int b;
    private long c;

    public d(Context context, int i, long j) {
        this.a = context;
        this.b = i;
        this.c = j;
    }

    private void a(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putLong("last_millis", this.c);
        editorEdit.commit();
    }

    private static void a(SharedPreferences sharedPreferences, long j) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        String strValueOf = String.valueOf(j);
        editorEdit.remove("session_id");
        editorEdit.remove("refer_id");
        editorEdit.putString("session_id", strValueOf);
        editorEdit.putString("refer_id", Constants.MAIN_VERSION_TAG);
        editorEdit.putLong("end_millis", j);
        editorEdit.commit();
    }

    private void b(SharedPreferences sharedPreferences) {
        String string;
        boolean z;
        JSONObject jSONObject = new JSONObject();
        Context context = this.a;
        StringBuffer stringBuffer = new StringBuffer(Constants.MAIN_VERSION_TAG);
        SharedPreferences sharedPreferencesA = com.hianalytics.android.b.a.c.a(context, "sessioncontext");
        String string2 = sharedPreferencesA.getString("session_id", Constants.MAIN_VERSION_TAG);
        if (Constants.MAIN_VERSION_TAG.equals(string2)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            string2 = String.valueOf(jCurrentTimeMillis);
            SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
            editorEdit.putString("session_id", string2);
            editorEdit.putLong("end_millis", jCurrentTimeMillis);
            editorEdit.commit();
        }
        String str = string2;
        String string3 = sharedPreferencesA.getString("refer_id", Constants.MAIN_VERSION_TAG);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            com.hianalytics.android.b.a.a.h();
            string = null;
        } else {
            stringBuffer.append(com.hianalytics.android.b.a.a.c(context)[0]).append(",").append(telephonyManager.getNetworkOperatorName().replace(',', '&')).append(",").append(str).append(",").append(string3);
            string = stringBuffer.toString();
        }
        if (string != null) {
            try {
                if (sharedPreferences.getString("activities", Constants.MAIN_VERSION_TAG).trim().length() > 0) {
                    String[] strArrSplit = sharedPreferences.getString("activities", Constants.MAIN_VERSION_TAG).split(";");
                    JSONArray jSONArray = new JSONArray();
                    for (String str2 : strArrSplit) {
                        jSONArray.put(str2);
                    }
                    jSONObject.put("b", jSONArray);
                    z = false;
                } else {
                    z = true;
                }
                if (sharedPreferences.getString("events", Constants.MAIN_VERSION_TAG).trim().length() > 0) {
                    String[] strArrSplit2 = sharedPreferences.getString("events", Constants.MAIN_VERSION_TAG).split(";");
                    JSONArray jSONArray2 = new JSONArray();
                    for (String str3 : strArrSplit2) {
                        jSONArray2.put(str3);
                    }
                    jSONObject.put("e", jSONArray2);
                    z = false;
                }
                jSONObject.put("h", string);
                jSONObject.put("type", "termination");
                Handler handlerF = com.hianalytics.android.b.a.a.f();
                if (handlerF != null) {
                    handlerF.post(new c(this.a, jSONObject, z));
                }
                com.hianalytics.android.b.a.a.h();
            } catch (JSONException e) {
                Log.e("HiAnalytics", "onTerminate: JSONException.", e);
                e.printStackTrace();
            }
        }
        SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
        editorEdit2.putString("activities", Constants.MAIN_VERSION_TAG);
        editorEdit2.remove("events");
        editorEdit2.commit();
    }

    private boolean c(SharedPreferences sharedPreferences) {
        return this.c - sharedPreferences.getLong("last_millis", -1L) > com.hianalytics.android.b.a.a.a().longValue() * 1000;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SharedPreferences sharedPreferencesA;
        try {
            Context context = this.a;
            long j = this.c;
            SharedPreferences sharedPreferencesA2 = com.hianalytics.android.b.a.c.a(context, "sessioncontext");
            if (!Constants.MAIN_VERSION_TAG.equals(sharedPreferencesA2.getString("session_id", Constants.MAIN_VERSION_TAG)) && j - sharedPreferencesA2.getLong("end_millis", 0L) <= com.hianalytics.android.b.a.a.c().longValue() * 1000) {
                SharedPreferences.Editor editorEdit = sharedPreferencesA2.edit();
                editorEdit.putLong("end_millis", j);
                editorEdit.commit();
            } else {
                a(sharedPreferencesA2, j);
            }
            if (this.b != 0) {
                if (this.b != 1) {
                    if (this.b != 2 || (sharedPreferencesA = com.hianalytics.android.b.a.c.a(this.a, "state")) == null) {
                        return;
                    }
                    b(sharedPreferencesA);
                    return;
                }
                Context context2 = this.a;
                this.a = context2;
                SharedPreferences sharedPreferencesA3 = com.hianalytics.android.b.a.c.a(context2, "state");
                if (sharedPreferencesA3 == null || !c(sharedPreferencesA3)) {
                    return;
                }
                b(sharedPreferencesA3);
                a(sharedPreferencesA3);
                return;
            }
            Context context3 = this.a;
            if (this.a != context3) {
                com.hianalytics.android.b.a.a.h();
                return;
            }
            this.a = context3;
            SharedPreferences sharedPreferencesA4 = com.hianalytics.android.b.a.c.a(context3, "state");
            if (sharedPreferencesA4 != null) {
                long j2 = sharedPreferencesA4.getLong("last_millis", -1L);
                if (j2 == -1) {
                    com.hianalytics.android.b.a.a.h();
                } else {
                    long j3 = this.c - j2;
                    long j4 = sharedPreferencesA4.getLong("duration", 0L);
                    SharedPreferences.Editor editorEdit2 = sharedPreferencesA4.edit();
                    String string = sharedPreferencesA4.getString("activities", Constants.MAIN_VERSION_TAG);
                    String name = context3.getClass().getName();
                    if (!Constants.MAIN_VERSION_TAG.equals(string)) {
                        string = String.valueOf(string) + ";";
                    }
                    String str = String.valueOf(string) + name + "," + new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(j2)) + "," + (j3 / 1000);
                    editorEdit2.remove("activities");
                    editorEdit2.putString("activities", str);
                    editorEdit2.putLong("duration", j4 + j3);
                    editorEdit2.commit();
                }
                if (c(sharedPreferencesA4)) {
                    b(sharedPreferencesA4);
                    a(sharedPreferencesA4);
                } else if (com.hianalytics.android.b.a.a.d(context3)) {
                    b(sharedPreferencesA4);
                    a(sharedPreferencesA4);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            com.hianalytics.android.b.a.a.h();
            e.printStackTrace();
        }
    }
}
