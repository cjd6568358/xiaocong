package com.meizu.cloud.pushsdk.a.d;

import com.tencent.android.tpush.common.Constants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpHost;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final int f;
    private final List<String> g;
    private final List<String> h;
    private final String i;
    private final String j;

    /* synthetic */ f(a aVar, AnonymousClass1 anonymousClass1) {
        this(aVar);
    }

    private f(a aVar) {
        this.b = aVar.a;
        this.c = a(aVar.b, false);
        this.d = a(aVar.c, false);
        this.e = aVar.d;
        this.f = aVar.a();
        this.g = a(aVar.f, false);
        this.h = aVar.g != null ? a(aVar.g, true) : null;
        this.i = aVar.h != null ? a(aVar.h, false) : null;
        this.j = aVar.toString();
    }

    public boolean a() {
        return this.b.equals("https");
    }

    public String b() {
        if (this.c.isEmpty()) {
            return Constants.MAIN_VERSION_TAG;
        }
        int length = this.b.length() + 3;
        return this.j.substring(length, m.a(this.j, length, this.j.length(), ":@"));
    }

    public String c() {
        if (this.d.isEmpty()) {
            return Constants.MAIN_VERSION_TAG;
        }
        return this.j.substring(this.j.indexOf(58, this.b.length() + 3) + 1, this.j.indexOf(64));
    }

    public static int a(String str) {
        if (str.equals(HttpHost.DEFAULT_SCHEME_NAME)) {
            return 80;
        }
        if (str.equals("https")) {
            return 443;
        }
        return -1;
    }

    static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append('/');
            sb.append(list.get(i));
        }
    }

    public List<String> d() {
        int iIndexOf = this.j.indexOf(47, this.b.length() + 3);
        int iA = m.a(this.j, iIndexOf, this.j.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < iA) {
            int i = iIndexOf + 1;
            iIndexOf = m.a(this.j, i, iA, '/');
            arrayList.add(this.j.substring(i, iIndexOf));
        }
        return arrayList;
    }

    public String e() {
        if (this.h == null) {
            return null;
        }
        int iIndexOf = this.j.indexOf(63) + 1;
        return this.j.substring(iIndexOf, m.a(this.j, iIndexOf + 1, this.j.length(), '#'));
    }

    static void b(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i = 0; i < size; i += 2) {
            String str = list.get(i);
            String str2 = list.get(i + 1);
            if (i > 0) {
                sb.append('&');
            }
            sb.append(str);
            if (str2 != null) {
                sb.append('=');
                sb.append(str2);
            }
        }
    }

    static List<String> b(String str) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i <= str.length()) {
            int iIndexOf = str.indexOf(38, i);
            if (iIndexOf == -1) {
                iIndexOf = str.length();
            }
            int iIndexOf2 = str.indexOf(61, i);
            if (iIndexOf2 == -1 || iIndexOf2 > iIndexOf) {
                arrayList.add(str.substring(i, iIndexOf));
                arrayList.add(null);
            } else {
                arrayList.add(str.substring(i, iIndexOf2));
                arrayList.add(str.substring(iIndexOf2 + 1, iIndexOf));
            }
            i = iIndexOf + 1;
        }
        return arrayList;
    }

    public String f() {
        if (this.i == null) {
            return null;
        }
        return this.j.substring(this.j.indexOf(35) + 1);
    }

    public a g() {
        a aVar = new a();
        aVar.a = this.b;
        aVar.b = b();
        aVar.c = c();
        aVar.d = this.e;
        aVar.e = this.f != a(this.b) ? this.f : -1;
        aVar.f.clear();
        aVar.f.addAll(d());
        aVar.a(e());
        aVar.h = f();
        return aVar;
    }

    public static f c(String str) {
        a aVar = new a();
        if (aVar.a((f) null, str) == a.EnumC0030a.SUCCESS) {
            return aVar.b();
        }
        return null;
    }

    /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.a.d.f$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[a.EnumC0030a.values().length];

        static {
            try {
                a[a.EnumC0030a.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.EnumC0030a.INVALID_HOST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.EnumC0030a.UNSUPPORTED_SCHEME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[a.EnumC0030a.MISSING_SCHEME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[a.EnumC0030a.INVALID_PORT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public boolean equals(Object obj) {
        return (obj instanceof f) && ((f) obj).j.equals(this.j);
    }

    public int hashCode() {
        return this.j.hashCode();
    }

    public String toString() {
        return this.j;
    }

    public static final class a {
        String a;
        String d;
        List<String> g;
        String h;
        String b = Constants.MAIN_VERSION_TAG;
        String c = Constants.MAIN_VERSION_TAG;
        int e = -1;
        final List<String> f = new ArrayList();

        /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.a.d.f$a$a, reason: collision with other inner class name */
        enum EnumC0030a {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST
        }

        public a() {
            this.f.add(Constants.MAIN_VERSION_TAG);
        }

        int a() {
            return this.e != -1 ? this.e : f.a(this.a);
        }

        public a a(String str) {
            this.g = str != null ? f.b(f.a(str, " \"'<>#", true, false, true, true)) : null;
            return this;
        }

        public a a(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            this.g.add(f.a(str, " \"'<>#&=", false, false, true, true));
            this.g.add(str2 != null ? f.a(str2, " \"'<>#&=", false, false, true, true) : null);
            return this;
        }

        public f b() {
            if (this.a == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.d == null) {
                throw new IllegalStateException("host == null");
            }
            return new f(this, null);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append("://");
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                sb.append(this.b);
                if (!this.c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.c);
                }
                sb.append('@');
            }
            if (this.d.indexOf(58) != -1) {
                sb.append('[');
                sb.append(this.d);
                sb.append(']');
            } else {
                sb.append(this.d);
            }
            int iA = a();
            if (iA != f.a(this.a)) {
                sb.append(':');
                sb.append(iA);
            }
            f.a(sb, this.f);
            if (this.g != null) {
                sb.append('?');
                f.b(sb, this.g);
            }
            if (this.h != null) {
                sb.append('#');
                sb.append(this.h);
            }
            return sb.toString();
        }

        EnumC0030a a(f fVar, String str) {
            int iA;
            int iA2 = m.a(str, 0, str.length());
            int iB = m.b(str, iA2, str.length());
            if (b(str, iA2, iB) != -1) {
                if (str.regionMatches(true, iA2, "https:", 0, 6)) {
                    this.a = "https";
                    iA2 += "https:".length();
                } else if (str.regionMatches(true, iA2, "http:", 0, 5)) {
                    this.a = HttpHost.DEFAULT_SCHEME_NAME;
                    iA2 += "http:".length();
                } else {
                    return EnumC0030a.UNSUPPORTED_SCHEME;
                }
            } else if (fVar != null) {
                this.a = fVar.b;
            } else {
                return EnumC0030a.MISSING_SCHEME;
            }
            boolean z = false;
            boolean z2 = false;
            int iC = c(str, iA2, iB);
            if (iC >= 2 || fVar == null || !fVar.b.equals(this.a)) {
                int i = iA2 + iC;
                while (true) {
                    boolean z3 = z2;
                    boolean z4 = z;
                    int i2 = i;
                    int iA3 = m.a(str, i2, iB, "@/\\?#");
                    switch (iA3 != iB ? str.charAt(iA3) : (byte) -1) {
                        case -1:
                        case 35:
                        case 47:
                        case 63:
                        case 92:
                            int iD = d(str, i2, iA3);
                            if (iD + 1 < iA3) {
                                this.d = e(str, i2, iD);
                                this.e = g(str, iD + 1, iA3);
                                if (this.e == -1) {
                                    return EnumC0030a.INVALID_PORT;
                                }
                            } else {
                                this.d = e(str, i2, iD);
                                this.e = f.a(this.a);
                            }
                            if (this.d == null) {
                                return EnumC0030a.INVALID_HOST;
                            }
                            iA2 = iA3;
                            break;
                        case 64:
                            if (!z3) {
                                int iA4 = m.a(str, i2, iA3, ':');
                                String strA = f.a(str, i2, iA4, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                if (z4) {
                                    strA = this.b + "%40" + strA;
                                }
                                this.b = strA;
                                if (iA4 != iA3) {
                                    z3 = true;
                                    this.c = f.a(str, iA4 + 1, iA3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                }
                                z4 = true;
                            } else {
                                this.c += "%40" + f.a(str, i2, iA3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                            }
                            i = iA3 + 1;
                            z2 = z3;
                            continue;
                            z = z4;
                            break;
                        default:
                            z2 = z3;
                            i = i2;
                            continue;
                            z = z4;
                            break;
                    }
                }
            } else {
                this.b = fVar.b();
                this.c = fVar.c();
                this.d = fVar.e;
                this.e = fVar.f;
                this.f.clear();
                this.f.addAll(fVar.d());
                if (iA2 == iB || str.charAt(iA2) == '#') {
                    a(fVar.e());
                }
            }
            int iA5 = m.a(str, iA2, iB, "?#");
            a(str, iA2, iA5);
            if (iA5 >= iB || str.charAt(iA5) != '?') {
                iA = iA5;
            } else {
                iA = m.a(str, iA5, iB, '#');
                this.g = f.b(f.a(str, iA5 + 1, iA, " \"'<>#", true, false, true, true));
            }
            if (iA < iB && str.charAt(iA) == '#') {
                this.h = f.a(str, iA + 1, iB, Constants.MAIN_VERSION_TAG, true, false, false, false);
            }
            return EnumC0030a.SUCCESS;
        }

        private void a(String str, int i, int i2) {
            if (i != i2) {
                char cCharAt = str.charAt(i);
                if (cCharAt == '/' || cCharAt == '\\') {
                    this.f.clear();
                    this.f.add(Constants.MAIN_VERSION_TAG);
                    i++;
                } else {
                    this.f.set(this.f.size() - 1, Constants.MAIN_VERSION_TAG);
                }
                int i3 = i;
                while (i3 < i2) {
                    int iA = m.a(str, i3, i2, "/\\");
                    boolean z = iA < i2;
                    a(str, i3, iA, z, true);
                    if (z) {
                        iA++;
                    }
                    i3 = iA;
                }
            }
        }

        private void a(String str, int i, int i2, boolean z, boolean z2) {
            String strA = f.a(str, i, i2, " \"<>^`{}|/\\?#", z2, false, false, true);
            if (!b(strA)) {
                if (c(strA)) {
                    c();
                    return;
                }
                if (this.f.get(this.f.size() - 1).isEmpty()) {
                    this.f.set(this.f.size() - 1, strA);
                } else {
                    this.f.add(strA);
                }
                if (z) {
                    this.f.add(Constants.MAIN_VERSION_TAG);
                }
            }
        }

        private boolean b(String str) {
            return str.equals(".") || str.equalsIgnoreCase("%2e");
        }

        private boolean c(String str) {
            return str.equals("..") || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        private void c() {
            if (this.f.remove(this.f.size() - 1).isEmpty() && !this.f.isEmpty()) {
                this.f.set(this.f.size() - 1, Constants.MAIN_VERSION_TAG);
            } else {
                this.f.add(Constants.MAIN_VERSION_TAG);
            }
        }

        private static int b(String str, int i, int i2) {
            if (i2 - i < 2) {
                return -1;
            }
            char cCharAt = str.charAt(i);
            if ((cCharAt < 'a' || cCharAt > 'z') && (cCharAt < 'A' || cCharAt > 'Z')) {
                return -1;
            }
            for (int i3 = i + 1; i3 < i2; i3++) {
                char cCharAt2 = str.charAt(i3);
                if ((cCharAt2 < 'a' || cCharAt2 > 'z') && ((cCharAt2 < 'A' || cCharAt2 > 'Z') && ((cCharAt2 < '0' || cCharAt2 > '9') && cCharAt2 != '+' && cCharAt2 != '-' && cCharAt2 != '.'))) {
                    if (cCharAt2 == ':') {
                        return i3;
                    }
                    return -1;
                }
            }
            return -1;
        }

        private static int c(String str, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                char cCharAt = str.charAt(i);
                if (cCharAt != '\\' && cCharAt != '/') {
                    break;
                }
                i3++;
                i++;
            }
            return i3;
        }

        private static int d(String str, int i, int i2) {
            int i3 = i;
            while (i3 < i2) {
                switch (str.charAt(i3)) {
                    case ':':
                        return i3;
                    case '[':
                        break;
                    default:
                        continue;
                        i3++;
                        break;
                }
                do {
                    i3++;
                    if (i3 >= i2) {
                        break;
                    }
                } while (str.charAt(i3) != ']');
                i3++;
            }
            return i2;
        }

        private static String e(String str, int i, int i2) {
            String strA = f.a(str, i, i2, false);
            if (strA.contains(":")) {
                InetAddress inetAddressF = (strA.startsWith("[") && strA.endsWith("]")) ? f(strA, 1, strA.length() - 1) : f(strA, 0, strA.length());
                if (inetAddressF == null) {
                    return null;
                }
                byte[] address = inetAddressF.getAddress();
                if (address.length == 16) {
                    return a(address);
                }
                throw new AssertionError();
            }
            return m.a(strA);
        }

        /* JADX WARN: Code duplicated, block: B:27:0x0042  */
        /* JADX WARN: Code duplicated, block: B:41:0x006c A[LOOP:1: B:26:0x0040->B:41:0x006c, LOOP_END] */
        /* JADX WARN: Code duplicated, block: B:60:0x004c A[EDGE_INSN: B:60:0x004c->B:29:0x004c BREAK  A[LOOP:1: B:26:0x0040->B:41:0x006c], SYNTHETIC] */
        private static InetAddress f(String str, int i, int i2) {
            int i3;
            int i4;
            int i5;
            int iA;
            byte[] bArr = new byte[16];
            int i6 = i;
            int i7 = -1;
            int i8 = -1;
            int i9 = 0;
            while (i6 < i2) {
                if (i9 == bArr.length) {
                    return null;
                }
                if (i6 + 2 <= i2 && str.regionMatches(i6, "::", 0, 2)) {
                    if (i8 != -1) {
                        return null;
                    }
                    i6 += 2;
                    i8 = i9 + 2;
                    if (i6 == i2) {
                        i9 = i8;
                        break;
                    }
                    i9 = i8;
                    i3 = 0;
                    i4 = i6;
                    while (i4 < i2) {
                        iA = f.a(str.charAt(i4));
                        if (iA == -1) {
                            break;
                            break;
                        }
                        i3 = (i3 << 4) + iA;
                        i4++;
                    }
                    i5 = i4 - i6;
                    if (i5 != 0) {
                    }
                    return null;
                }
                if (i9 != 0) {
                    if (str.regionMatches(i6, ":", 0, 1)) {
                        i6++;
                    } else {
                        if (str.regionMatches(i6, ".", 0, 1) && a(str, i7, i2, bArr, i9 - 2)) {
                            i9 += 2;
                            break;
                        }
                        return null;
                    }
                }
                i3 = 0;
                i4 = i6;
                while (i4 < i2) {
                    iA = f.a(str.charAt(i4));
                    if (iA == -1) {
                        break;
                    }
                    i3 = (i3 << 4) + iA;
                    i4++;
                }
                i5 = i4 - i6;
                if (i5 != 0 || i5 > 4) {
                    return null;
                }
                int i10 = i9 + 1;
                bArr[i9] = (byte) ((i3 >>> 8) & 255);
                i9 = i10 + 1;
                bArr[i10] = (byte) (i3 & 255);
                i7 = i6;
                i6 = i4;
            }
            if (i9 != bArr.length) {
                if (i8 == -1) {
                    return null;
                }
                System.arraycopy(bArr, i8, bArr, bArr.length - (i9 - i8), i9 - i8);
                Arrays.fill(bArr, i8, (bArr.length - i9) + i8, (byte) 0);
            }
            try {
                return InetAddress.getByAddress(bArr);
            } catch (UnknownHostException e) {
                throw new AssertionError();
            }
        }

        private static boolean a(String str, int i, int i2, byte[] bArr, int i3) {
            int i4 = i;
            int i5 = i3;
            while (i4 < i2) {
                if (i5 == bArr.length) {
                    return false;
                }
                if (i5 != i3) {
                    if (str.charAt(i4) != '.') {
                        return false;
                    }
                    i4++;
                }
                int i6 = 0;
                int i7 = i4;
                while (i7 < i2) {
                    char cCharAt = str.charAt(i7);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    if ((i6 != 0 || i4 == i7) && (i6 = ((i6 * 10) + cCharAt) - 48) <= 255) {
                        i7++;
                    }
                    return false;
                }
                if (i7 - i4 == 0) {
                    return false;
                }
                bArr[i5] = (byte) i6;
                i5++;
                i4 = i7;
            }
            return i5 == i3 + 4;
        }

        private static String a(byte[] bArr) {
            int i = 0;
            int i2 = 0;
            int i3 = -1;
            int i4 = 0;
            while (i4 < bArr.length) {
                int i5 = i4;
                while (i5 < 16 && bArr[i5] == 0 && bArr[i5 + 1] == 0) {
                    i5 += 2;
                }
                int i6 = i5 - i4;
                if (i6 > i2) {
                    i2 = i6;
                    i3 = i4;
                }
                i4 = i5 + 2;
            }
            com.meizu.cloud.pushsdk.a.h.a aVar = new com.meizu.cloud.pushsdk.a.h.a();
            while (i < bArr.length) {
                if (i == i3) {
                    aVar.b(58);
                    i += i2;
                    if (i == 16) {
                        aVar.b(58);
                    }
                } else {
                    if (i > 0) {
                        aVar.b(58);
                    }
                    aVar.d(((bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | (bArr[i + 1] & Constants.NETWORK_TYPE_UNCONNECTED));
                    i += 2;
                }
            }
            return aVar.h();
        }

        private static int g(String str, int i, int i2) {
            try {
                int i3 = Integer.parseInt(f.a(str, i, i2, Constants.MAIN_VERSION_TAG, false, false, false, true));
                if (i3 <= 0 || i3 > 65535) {
                    return -1;
                }
                return i3;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    static String a(String str, boolean z) {
        return a(str, 0, str.length(), z);
    }

    private List<String> a(List<String> list, boolean z) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            arrayList.add(next != null ? a(next, z) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    static String a(String str, int i, int i2, boolean z) {
        for (int i3 = i; i3 < i2; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '%' || (cCharAt == '+' && z)) {
                com.meizu.cloud.pushsdk.a.h.a aVar = new com.meizu.cloud.pushsdk.a.h.a();
                aVar.a(str, i, i3);
                a(aVar, str, i3, i2, z);
                return aVar.h();
            }
        }
        return str.substring(i, i2);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0042  */
    static void a(com.meizu.cloud.pushsdk.a.h.a aVar, String str, int i, int i2, boolean z) {
        int iCharCount = i;
        while (iCharCount < i2) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt == 37 && iCharCount + 2 < i2) {
                int iA = a(str.charAt(iCharCount + 1));
                int iA2 = a(str.charAt(iCharCount + 2));
                if (iA != -1 && iA2 != -1) {
                    aVar.b((iA << 4) + iA2);
                    iCharCount += 2;
                } else {
                    aVar.a(iCodePointAt);
                }
            } else if (iCodePointAt == 43 && z) {
                aVar.b(32);
            } else {
                aVar.a(iCodePointAt);
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
    }

    static boolean a(String str, int i, int i2) {
        return i + 2 < i2 && str.charAt(i) == '%' && a(str.charAt(i + 1)) != -1 && a(str.charAt(i + 2)) != -1;
    }

    static int a(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'a' && c <= 'f') {
            return (c - 'a') + 10;
        }
        if (c < 'A' || c > 'F') {
            return -1;
        }
        return (c - 'A') + 10;
    }

    static String a(String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        int iCharCount = i;
        while (iCharCount < i2) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt >= 32 && iCodePointAt != 127 && ((iCodePointAt < 128 || !z4) && str2.indexOf(iCodePointAt) == -1 && ((iCodePointAt != 37 || (z && (!z2 || a(str, iCharCount, i2)))) && (iCodePointAt != 43 || !z3)))) {
                iCharCount += Character.charCount(iCodePointAt);
            } else {
                com.meizu.cloud.pushsdk.a.h.a aVar = new com.meizu.cloud.pushsdk.a.h.a();
                aVar.a(str, i, iCharCount);
                a(aVar, str, iCharCount, i2, str2, z, z2, z3, z4);
                return aVar.h();
            }
        }
        return str.substring(i, i2);
    }

    static void a(com.meizu.cloud.pushsdk.a.h.a aVar, String str, int i, int i2, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        com.meizu.cloud.pushsdk.a.h.a aVar2 = null;
        while (i < i2) {
            int iCodePointAt = str.codePointAt(i);
            if (!z || (iCodePointAt != 9 && iCodePointAt != 10 && iCodePointAt != 12 && iCodePointAt != 13)) {
                if (iCodePointAt == 43 && z3) {
                    aVar.b(z ? "+" : "%2B");
                } else if (iCodePointAt < 32 || iCodePointAt == 127 || ((iCodePointAt >= 128 && z4) || str2.indexOf(iCodePointAt) != -1 || (iCodePointAt == 37 && (!z || (z2 && !a(str, i, i2)))))) {
                    if (aVar2 == null) {
                        aVar2 = new com.meizu.cloud.pushsdk.a.h.a();
                    }
                    aVar2.a(iCodePointAt);
                    while (!aVar2.c()) {
                        int iF = aVar2.f() & Constants.NETWORK_TYPE_UNCONNECTED;
                        aVar.b(37);
                        aVar.b((int) a[(iF >> 4) & 15]);
                        aVar.b((int) a[iF & 15]);
                    }
                } else {
                    aVar.a(iCodePointAt);
                }
            }
            i += Character.charCount(iCodePointAt);
        }
    }

    static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4);
    }
}
