package com.baidu.mobstat;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class y {
    public static final y a = new z("AP_LIST", 0, 0);
    public static final y b;
    public static final y c;
    public static final y d;
    public static final y e;
    private static final /* synthetic */ y[] g;
    private int f;

    public abstract x a();

    /* synthetic */ y(String str, int i, int i2, z zVar) {
        this(str, i, i2);
    }

    public static y valueOf(String str) {
        return (y) Enum.valueOf(y.class, str);
    }

    public static y[] values() {
        return (y[]) g.clone();
    }

    static {
        final int i = 4;
        final int i2 = 3;
        final int i3 = 2;
        final int i4 = 1;
        final String str = "APP_LIST";
        b = new y(str, i4, i4) { // from class: com.baidu.mobstat.aa
            {
                z zVar = null;
            }

            @Override // com.baidu.mobstat.y
            public x a() {
                return new aj();
            }
        };
        final String str2 = "APP_TRACE";
        c = new y(str2, i3, i3) { // from class: com.baidu.mobstat.ab
            {
                z zVar = null;
            }

            @Override // com.baidu.mobstat.y
            public x a() {
                return new ak();
            }
        };
        final String str3 = "APP_CHANGE";
        d = new y(str3, i2, i2) { // from class: com.baidu.mobstat.ac
            {
                z zVar = null;
            }

            @Override // com.baidu.mobstat.y
            public x a() {
                return new ai();
            }
        };
        final String str4 = "APP_APK";
        e = new y(str4, i, i) { // from class: com.baidu.mobstat.ad
            {
                z zVar = null;
            }

            @Override // com.baidu.mobstat.y
            public x a() {
                return new ah();
            }
        };
        g = new y[]{a, b, c, d, e};
    }

    private y(String str, int i, int i2) {
        super(str, i);
        this.f = i2;
    }

    @Override // java.lang.Enum
    public String toString() {
        return String.valueOf(this.f);
    }

    public synchronized ArrayList<w> a(int i, int i2) {
        ArrayList<w> arrayList;
        arrayList = new ArrayList<>();
        x xVarA = null;
        try {
            try {
                xVarA = a();
                if (xVarA.a()) {
                    arrayList = xVarA.a(i, i2);
                    if (xVarA != null) {
                        xVarA.close();
                    }
                } else if (xVarA != null) {
                    xVarA.close();
                }
            } catch (Exception e2) {
                bd.b(e2);
                if (xVarA != null) {
                    xVarA.close();
                }
            }
        } catch (Throwable th) {
            if (xVarA != null) {
                xVarA.close();
            }
            throw th;
        }
        return arrayList;
    }

    public synchronized long a(long j, String str) {
        long jA;
        jA = -1;
        x xVarA = null;
        try {
            try {
                xVarA = a();
                if (xVarA.a()) {
                    jA = xVarA.a(String.valueOf(j), str);
                    if (xVarA != null) {
                        xVarA.close();
                    }
                } else if (xVarA != null) {
                    xVarA.close();
                }
            } catch (Exception e2) {
                bd.b(e2);
                if (xVarA != null) {
                    xVarA.close();
                }
            }
        } catch (Throwable th) {
            if (xVarA != null) {
                xVarA.close();
            }
            throw th;
        }
        return jA;
    }

    public synchronized int a(ArrayList<Long> arrayList) {
        int i;
        int i2 = 0;
        synchronized (this) {
            if (arrayList != null) {
                if (arrayList.size() != 0) {
                    x xVarA = null;
                    try {
                        try {
                            xVarA = a();
                            if (xVarA.a()) {
                                int size = arrayList.size();
                                int i3 = 0;
                                while (i3 < size) {
                                    if (xVarA.b(arrayList.get(i3).longValue())) {
                                        i3++;
                                        i2++;
                                    } else if (xVarA != null) {
                                        xVarA.close();
                                    }
                                }
                                if (xVarA != null) {
                                    xVarA.close();
                                    i = i2;
                                } else {
                                    i = i2;
                                }
                                i2 = i;
                            } else if (xVarA != null) {
                                xVarA.close();
                            }
                        } catch (Exception e2) {
                            i = i2;
                            bd.b(e2);
                            if (xVarA != null) {
                                xVarA.close();
                            }
                        }
                    } catch (Throwable th) {
                        if (xVarA != null) {
                            xVarA.close();
                        }
                        throw th;
                    }
                }
            }
        }
        return i2;
    }

    public synchronized List<String> a(int i) {
        List<String> arrayList;
        arrayList = new ArrayList<>();
        ArrayList<Long> arrayList2 = new ArrayList<>();
        ArrayList<w> arrayList3 = new ArrayList<>();
        a(arrayList, arrayList2, arrayList3, i, HttpStatus.SC_INTERNAL_SERVER_ERROR);
        if (arrayList3.size() != 0 && arrayList.size() == 0 && arrayList2.size() == 0) {
            w wVar = arrayList3.get(0);
            long jA = wVar.a();
            String strB = wVar.b();
            arrayList2.add(Long.valueOf(jA));
            arrayList.add(strB);
        }
        int iA = a(arrayList2);
        if (iA != arrayList.size()) {
            arrayList = arrayList.subList(0, iA);
        }
        return arrayList;
    }

    private int a(List<String> list, ArrayList<Long> arrayList, ArrayList<w> arrayList2, int i, int i2) {
        int i3 = 0;
        int iC = c();
        int i4 = 0;
        int i5 = i2;
        while (iC > 0) {
            int i6 = iC < i5 ? iC : i5;
            ArrayList<w> arrayListA = a(i6, i4);
            if (i4 == 0 && arrayListA.size() != 0) {
                arrayList2.add(arrayListA.get(0));
            }
            for (w wVar : arrayListA) {
                long jA = wVar.a();
                String strB = wVar.b();
                int length = strB.length();
                if (i3 + length > i) {
                    break;
                }
                arrayList.add(Long.valueOf(jA));
                list.add(strB);
                i3 += length;
            }
            iC -= i6;
            i4 += i6;
            i5 = i6;
        }
        return i3;
    }

    public synchronized boolean b() {
        return c() == 0;
    }

    public synchronized boolean b(int i) {
        return c() >= i;
    }

    private int c() {
        x xVarA = null;
        try {
            xVarA = a();
            if (xVarA.a()) {
                int iB = xVarA.b();
            }
        } catch (Exception e2) {
            bd.b(e2);
        } finally {
            if (xVarA != null) {
                xVarA.close();
            }
        }
        return 0;
    }
}
