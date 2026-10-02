package com.youzan.androidsdk.basic.tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: compiled from: HttpCookie.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class b {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final TimeZone f35 = TimeZone.getTimeZone("GMT");

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final ThreadLocal<DateFormat> f36 = new ThreadLocal<DateFormat>() { // from class: com.youzan.androidsdk.basic.tool.b.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters and merged with bridge method [inline-methods] */
        public DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            rfc1123.setLenient(false);
            rfc1123.setTimeZone(b.f35);
            return rfc1123;
        }
    };

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private final String f37;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private final String f38;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private final boolean f39;

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private final boolean f40;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private final String f41;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private final String f42;

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private final boolean f43;

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private final boolean f44;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private final long f45;

    private b(a builder) {
        this.f41 = builder.f50;
        this.f42 = builder.f51;
        this.f45 = builder.f52;
        this.f37 = builder.f53;
        this.f38 = builder.f55;
        this.f39 = builder.f47;
        this.f43 = builder.f48;
        this.f44 = builder.f49;
        this.f40 = builder.f54;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static String m21(Date value) {
        return f36.get().format(value);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public String m26() {
        return this.f41;
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    public String m27() {
        return this.f42;
    }

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    public boolean m28() {
        return this.f44;
    }

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    public long m29() {
        return this.f45;
    }

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    public boolean m31() {
        return this.f40;
    }

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    public String m23() {
        return this.f37;
    }

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    public String m24() {
        return this.f38;
    }

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    public boolean m25() {
        return this.f43;
    }

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    public boolean m30() {
        return this.f39;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.f41);
        result.append('=');
        result.append(this.f42);
        if (this.f44) {
            if (this.f45 == Long.MIN_VALUE) {
                result.append("; max-age=0");
            } else {
                result.append("; expires=").append(m21(new Date(this.f45)));
            }
        }
        if (!this.f40) {
            result.append("; domain=").append(this.f37);
        }
        result.append("; path=").append(this.f38);
        if (this.f39) {
            result.append("; secure");
        }
        if (this.f43) {
            result.append("; httponly");
        }
        return result.toString();
    }

    /* JADX INFO: compiled from: HttpCookie.java */
    public static final class a {

        /* JADX INFO: renamed from: ι, reason: contains not printable characters */
        private static final long f46 = 253402300799999L;

        /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
        boolean f47;

        /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
        boolean f48;

        /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
        boolean f49;

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        String f50;

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        String f51;

        /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
        String f53;

        /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
        boolean f54;

        /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
        long f52 = 253402300799999L;

        /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
        String f55 = "/";

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public a m36(String name) {
            this.f50 = name;
            return this;
        }

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        public a m38(String value) {
            this.f51 = value != null ? value.trim() : null;
            return this;
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public a m35(long expiresAt) {
            if (expiresAt <= 0) {
                expiresAt = Long.MIN_VALUE;
            }
            if (expiresAt > 253402300799999L) {
                expiresAt = 253402300799999L;
            }
            this.f52 = expiresAt;
            this.f49 = true;
            return this;
        }

        /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
        public a m39(String domain) {
            return m33(domain, false);
        }

        /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
        public a m41(String domain) {
            return m33(domain, true);
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        private a m33(String domain, boolean hostOnly) {
            this.f53 = domain;
            this.f54 = hostOnly;
            return this;
        }

        /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
        public a m42(String path) {
            this.f55 = path;
            return this;
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public a m34() {
            this.f47 = true;
            return this;
        }

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        public a m37() {
            this.f48 = true;
            return this;
        }

        /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
        public b m40() {
            return new b(this);
        }
    }
}
