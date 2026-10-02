package com.tencent.android.tpush.e;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.e.m;
import com.tencent.android.tpush.stat.a.h;
import java.util.Locale;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private String a;
    private String e;
    private String f;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String b = "Axg%lu";
    private int c = 0;
    private String d = "%d";
    private int g = BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN;
    private int h = 0;
    private int i = m.d();

    public a(Context context) {
        this.a = m.i(context);
        this.e = h.f(context);
        this.f = CacheManager.getToken(context);
        DisplayMetrics displayMetricsC = h.c(context);
        this.j = displayMetricsC.widthPixels + "*" + displayMetricsC.heightPixels;
        this.k = Build.MODEL;
        this.l = Locale.getDefault().getLanguage();
        this.m = "3.24";
        this.n = Build.MANUFACTURER;
        this.o = "%s";
    }

    public String a() {
        StringBuffer stringBuffer = new StringBuffer("[{");
        stringBuffer.append("\"idx\":").append(this.c);
        stringBuffer.append(",\"ts\":").append(this.d);
        stringBuffer.append(",\"et\":").append(this.g);
        stringBuffer.append(",\"si\":").append(this.h);
        if (this.a != null) {
            stringBuffer.append(",\"ui\":\"").append(this.a).append("\"");
        }
        if (this.b != null) {
            stringBuffer.append(",\"ky\":\"").append(this.b).append("\"");
        }
        if (this.f != null) {
            stringBuffer.append(",\"mid\":\"").append(this.f).append("\"");
        }
        if (this.e != null) {
            stringBuffer.append(",\"mc\":\"").append(this.e).append("\"");
        }
        stringBuffer.append(",\"ev\":{");
        stringBuffer.append("\"ov\":\"").append(this.i).append("\"");
        if (this.j != null) {
            stringBuffer.append(",\"sr\":\"").append(this.j).append("\"");
        }
        if (this.k != null) {
            stringBuffer.append(",\"md\":\"").append(this.k).append("\"");
        }
        if (this.l != null) {
            stringBuffer.append(",\"lg\":\"").append(this.l).append("\"");
        }
        if (this.m != null) {
            stringBuffer.append(",\"sv\":\"").append(this.m).append("\"");
        }
        if (this.n != null) {
            stringBuffer.append(",\"mf\":\"").append(this.n).append("\"");
        }
        if (this.o != null) {
            stringBuffer.append(",\"apn\":\"").append(this.o).append("\"");
        }
        stringBuffer.append("}}]");
        return stringBuffer.toString();
    }
}
