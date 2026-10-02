package com.baidu.cloud.media.player;

import android.os.Bundle;
import android.text.TextUtils;
import com.baidu.cloud.media.player.misc.IMediaFormat;
import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public Bundle a;
    public String b;
    public long c;
    public long d;
    public long e;
    public final ArrayList<a> f = new ArrayList<>();
    public a g;
    public a h;

    public static class a {
        public Bundle a;
        public final int b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public long h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public long r;

        public a(int i) {
            this.b = i;
        }

        public int a(String str, int i) {
            String strA = a(str);
            if (TextUtils.isEmpty(strA)) {
                return i;
            }
            try {
                return Integer.parseInt(strA);
            } catch (NumberFormatException e) {
                return i;
            }
        }

        public long a(String str, long j) {
            String strA = a(str);
            if (TextUtils.isEmpty(strA)) {
                return j;
            }
            try {
                return Long.parseLong(strA);
            } catch (NumberFormatException e) {
                return j;
            }
        }

        public String a() {
            return !TextUtils.isEmpty(this.e) ? this.e : "N/A";
        }

        public String a(String str) {
            return this.a.getString(str);
        }

        public int b(String str) {
            return a(str, 0);
        }

        public String b() {
            if (this.i <= 0 || this.j <= 0) {
                return "N/A";
            }
            return (this.o <= 0 || this.p <= 0) ? String.format(Locale.US, "%d x %d", Integer.valueOf(this.i), Integer.valueOf(this.j)) : String.format(Locale.US, "%d x %d [SAR %d:%d]", Integer.valueOf(this.i), Integer.valueOf(this.j), Integer.valueOf(this.o), Integer.valueOf(this.p));
        }

        public long c(String str) {
            return a(str, 0L);
        }

        public String c() {
            if (this.h <= 0) {
                return "N/A";
            }
            return this.h < 1000 ? String.format(Locale.US, "%d bit/s", Long.valueOf(this.h)) : String.format(Locale.US, "%d kb/s", Long.valueOf(this.h / 1000));
        }

        public String d() {
            return this.q <= 0 ? "N/A" : String.format(Locale.US, "%d Hz", Integer.valueOf(this.q));
        }
    }

    public static b a(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        b bVar = new b();
        bVar.a = bundle;
        bVar.b = bVar.a("format");
        bVar.c = bVar.b("duration_us");
        bVar.d = bVar.b("start_us");
        bVar.e = bVar.b("bitrate");
        int iA = bVar.a("video", -1);
        int iA2 = bVar.a("audio", -1);
        ArrayList<Bundle> arrayListC = bVar.c("streams");
        if (arrayListC == null) {
            return bVar;
        }
        int i = -1;
        for (Bundle bundle2 : arrayListC) {
            i++;
            if (bundle2 != null) {
                a aVar = new a(i);
                aVar.a = bundle2;
                aVar.c = aVar.a("type");
                aVar.d = aVar.a("language");
                if (!TextUtils.isEmpty(aVar.c)) {
                    aVar.e = aVar.a("codec_name");
                    aVar.f = aVar.a("codec_profile");
                    aVar.g = aVar.a("codec_long_name");
                    aVar.h = aVar.b("bitrate");
                    if (aVar.c.equalsIgnoreCase("video")) {
                        aVar.i = aVar.b(IMediaFormat.KEY_WIDTH);
                        aVar.j = aVar.b(IMediaFormat.KEY_HEIGHT);
                        aVar.k = aVar.b("fps_num");
                        aVar.l = aVar.b("fps_den");
                        aVar.m = aVar.b("tbr_num");
                        aVar.n = aVar.b("tbr_den");
                        aVar.o = aVar.b("sar_num");
                        aVar.p = aVar.b("sar_den");
                        if (iA == i) {
                            bVar.g = aVar;
                        }
                    } else if (aVar.c.equalsIgnoreCase("audio")) {
                        aVar.q = aVar.b("sample_rate");
                        aVar.r = aVar.c("channel_layout");
                        if (iA2 == i) {
                            bVar.h = aVar;
                        }
                    }
                    bVar.f.add(aVar);
                }
            }
        }
        return bVar;
    }

    public int a(String str, int i) {
        String strA = a(str);
        if (TextUtils.isEmpty(strA)) {
            return i;
        }
        try {
            return Integer.parseInt(strA);
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public long a(String str, long j) {
        String strA = a(str);
        if (TextUtils.isEmpty(strA)) {
            return j;
        }
        try {
            return Long.parseLong(strA);
        } catch (NumberFormatException e) {
            return j;
        }
    }

    public String a(String str) {
        return this.a.getString(str);
    }

    public long b(String str) {
        return a(str, 0L);
    }

    public ArrayList<Bundle> c(String str) {
        return this.a.getParcelableArrayList(str);
    }
}
