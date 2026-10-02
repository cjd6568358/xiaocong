package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a implements Runnable {
    private Context a;
    private String b;
    private String c;
    private long d;

    public a(Context context, String str, String str2, long j) {
        this.a = context;
        this.b = str.replace(",", "^");
        this.c = str2.replace(",", "^");
        this.d = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            SharedPreferences sharedPreferencesA = com.hianalytics.android.b.a.c.a(this.a, "state");
            if (sharedPreferencesA == null) {
                com.hianalytics.android.b.a.a.h();
                return;
            }
            String string = sharedPreferencesA.getString("events", Constants.MAIN_VERSION_TAG);
            if (!Constants.MAIN_VERSION_TAG.equals(string)) {
                string = String.valueOf(string) + ";";
            }
            String str = String.valueOf(string) + this.b + "," + this.c + "," + new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US).format(new Date(this.d));
            if (str.split(";").length <= com.hianalytics.android.b.a.a.d()) {
                SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
                editorEdit.remove("events");
                editorEdit.putString("events", str);
                editorEdit.commit();
                com.hianalytics.android.b.a.a.h();
            }
            if (com.hianalytics.android.b.a.a.d(this.a)) {
                if (!com.hianalytics.android.b.a.a.e()) {
                    sharedPreferencesA.edit().remove("events").commit();
                } else {
                    com.hianalytics.android.b.a.a.h();
                    HiAnalytics.onReport(this.a);
                }
            }
        } catch (Exception e) {
            e.getMessage();
            com.hianalytics.android.b.a.a.h();
            e.printStackTrace();
        }
    }
}
