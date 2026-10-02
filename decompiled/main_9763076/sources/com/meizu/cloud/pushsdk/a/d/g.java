package com.meizu.cloud.pushsdk.a.d;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static final Pattern a = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
    private static final Pattern b = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    private final String c;
    private final String d;
    private final String e;
    private final String f;

    private g(String str, String str2, String str3, String str4) {
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
    }

    public static g a(String str) {
        Matcher matcher = a.matcher(str);
        if (!matcher.lookingAt()) {
            return null;
        }
        String lowerCase = matcher.group(1).toLowerCase(Locale.US);
        String lowerCase2 = matcher.group(2).toLowerCase(Locale.US);
        Matcher matcher2 = b.matcher(str);
        String str2 = null;
        for (int iEnd = matcher.end(); iEnd < str.length(); iEnd = matcher2.end()) {
            matcher2.region(iEnd, str.length());
            if (!matcher2.lookingAt()) {
                return null;
            }
            String strGroup = matcher2.group(1);
            if (strGroup != null && strGroup.equalsIgnoreCase("charset")) {
                String strGroup2 = matcher2.group(2) != null ? matcher2.group(2) : matcher2.group(3);
                if (str2 != null && !strGroup2.equalsIgnoreCase(str2)) {
                    throw new IllegalArgumentException("Multiple different charsets: " + str);
                }
                str2 = strGroup2;
            }
        }
        return new g(str, lowerCase, lowerCase2, str2);
    }

    public String a() {
        return this.d;
    }

    public Charset b() {
        if (this.f != null) {
            return Charset.forName(this.f);
        }
        return null;
    }

    public String toString() {
        return this.c;
    }

    public boolean equals(Object obj) {
        return (obj instanceof g) && ((g) obj).c.equals(this.c);
    }

    public int hashCode() {
        return this.c.hashCode();
    }
}
