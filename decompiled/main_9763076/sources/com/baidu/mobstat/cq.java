package com.baidu.mobstat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cq extends bk {
    static cq a = new cq();

    private cq() {
    }

    public static cq a() {
        return a;
    }

    @Override // com.baidu.mobstat.bk
    public SharedPreferences a(Context context) {
        return Build.VERSION.SDK_INT >= 11 ? context.getSharedPreferences("baidu_mtj_sdk_record", 4) : context.getSharedPreferences("baidu_mtj_sdk_record", 0);
    }

    protected void a(Context context, long j) {
        b(context, "session_first_visit_time", j);
    }

    protected Long b(Context context) {
        return Long.valueOf(a(context, "session_first_visit_time", 0L));
    }

    protected void b(Context context, long j) {
        b(context, "session_last_visit_time", j);
    }

    protected Long c(Context context) {
        return Long.valueOf(a(context, "session_last_visit_time", 0L));
    }

    protected void c(Context context, long j) {
        b(context, "session_visit_interval", j);
    }

    protected Long d(Context context) {
        return Long.valueOf(a(context, "session_visit_interval", 0L));
    }

    protected void a(Context context, String str) {
        b(context, "session_today_visit_count", str);
    }

    protected String e(Context context) {
        return a(context, "session_today_visit_count", Constants.MAIN_VERSION_TAG);
    }

    protected void b(Context context, String str) {
        b(context, "session_recent_visit", str);
    }

    protected String f(Context context) {
        return a(context, "session_recent_visit", Constants.MAIN_VERSION_TAG);
    }
}
