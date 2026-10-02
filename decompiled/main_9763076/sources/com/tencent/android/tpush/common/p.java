package com.tencent.android.tpush.common;

import android.content.Context;
import com.tencent.android.tpush.SettingsContentProvider;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class p {
    public static volatile p a = null;
    private Context b;

    public static p a(Context context) {
        if (a == null) {
            synchronized (p.class) {
                if (a == null) {
                    a = new p(context);
                }
            }
        }
        return a;
    }

    private p(Context context) {
        this.b = context.getApplicationContext();
    }

    public r a() {
        return new r(this.b);
    }

    public String a(String str, String str2) {
        try {
            return SettingsContentProvider.getStringValue(this.b.getContentResolver().query(SettingsContentProvider.getContentUri(this.b, str, SettingsContentProvider.STRING_TYPE), null, null, null, null), str2);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SettingsPreferences", "error = ", th);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public long a(String str, long j) {
        try {
            return SettingsContentProvider.getLongValue(this.b.getContentResolver().query(SettingsContentProvider.getContentUri(this.b, str, SettingsContentProvider.LONG_TYPE), null, null, null, null), j);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SettingsPreferences", "error = ", th);
            return 0L;
        }
    }

    public int a(String str, int i) {
        try {
            return SettingsContentProvider.getIntValue(this.b.getContentResolver().query(SettingsContentProvider.getContentUri(this.b, str, SettingsContentProvider.INT_TYPE), null, null, null, null), i);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SettingsPreferences", "error = ", th);
            return 0;
        }
    }
}
