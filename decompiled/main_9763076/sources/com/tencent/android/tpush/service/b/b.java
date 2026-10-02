package com.tencent.android.tpush.service.b;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.service.channel.security.f;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static b a = null;
    private static String f = null;
    private static long g = 0;
    private Context b;
    private a d;
    private String c = "182.254.116.117";
    private int e = 300;

    private b(Context context) {
        this.b = null;
        this.d = null;
        this.b = context;
        this.d = new a(context, "tpns.qq.com");
    }

    public static b a(Context context) {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b(context);
                }
            }
        }
        return a;
    }

    public String a(String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec("azIoMLoU".getBytes("utf-8"), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(2, secretKeySpec);
            return new String(cipher.doFinal(f.b(str)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized String a() {
        String strB;
        try {
            strB = this.d.b();
            if (!b(strB)) {
                strB = c("tpns.qq.com");
            }
        } catch (Throwable th) {
            th.printStackTrace();
            strB = null;
        }
        return strB;
    }

    public static boolean b(String str) {
        if (str == null || str.length() < 7 || str.length() > 15 || Constants.MAIN_VERSION_TAG.equals(str)) {
            return false;
        }
        return Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}").matcher(str).find();
    }

    private String h(String str) {
        return "http://182.254.116.117/d?dn=99e2d153e4d0527186ebed5ac5608367&id=6&ttl=1";
    }

    public synchronized String c(String str) {
        return e(str);
    }

    public String d(String str) {
        String strTrim = str.trim();
        if (strTrim.length() < 8) {
            return null;
        }
        String strA = a(strTrim);
        ArrayList arrayList = new ArrayList();
        if (b(strA)) {
            arrayList.add(strA);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ips", strA);
                jSONObject.put(MessageKey.MSG_TTL, 300);
                jSONObject.put("exp", System.currentTimeMillis() + 300000);
                this.d.a(jSONObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            int iIndexOf = strA.indexOf(44);
            if (iIndexOf > 8) {
                String strSubstring = strA.substring(iIndexOf + 1, strA.length());
                if (strSubstring != null && strSubstring.trim().length() > 0) {
                    this.e = Integer.valueOf(strSubstring).intValue();
                    if (this.e < 10) {
                        this.e = 300;
                    }
                }
                com.tencent.android.tpush.a.a.c("httpDns", "ttl:" + strSubstring + "," + this.e);
            }
            try {
                String strSubstring2 = strA.substring(0, iIndexOf);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("ips", strSubstring2);
                jSONObject2.put(MessageKey.MSG_TTL, this.e);
                jSONObject2.put("exp", System.currentTimeMillis() + ((long) (this.e * 1000)));
                this.d.a(jSONObject2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return this.d.b();
    }

    /* JADX WARN: Code duplicated, block: B:49:0x0070 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public synchronized String e(String str) {
        BufferedReader bufferedReader;
        String strD;
        try {
            URLConnection uRLConnectionOpenConnection = new URL(h(str)).openConnection();
            uRLConnectionOpenConnection.setConnectTimeout(PushConstants.WORK_RECEIVER_EVENTCORE_ERROR);
            bufferedReader = new BufferedReader(new InputStreamReader(uRLConnectionOpenConnection.getInputStream()));
            strD = null;
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        com.tencent.android.tpush.a.a.c("httpDns", "getAddrByName line:" + line);
                        if (TextUtils.isEmpty(strD)) {
                            strD = d(line);
                        }
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        strD = null;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        } catch (Exception e5) {
            e = e5;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            throw th;
        }
        return strD;
    }

    public synchronized boolean b() {
        return f("tpns.qq.com");
    }

    /* JADX WARN: Code duplicated, block: B:67:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public synchronized boolean f(String str) {
        BufferedReader bufferedReader;
        boolean z = false;
        synchronized (this) {
            try {
                try {
                    URLConnection uRLConnectionOpenConnection = new URL(h(str)).openConnection();
                    uRLConnectionOpenConnection.setConnectTimeout(PushConstants.WORK_RECEIVER_EVENTCORE_ERROR);
                    bufferedReader = new BufferedReader(new InputStreamReader(uRLConnectionOpenConnection.getInputStream()));
                    try {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            com.tencent.android.tpush.a.a.c("httpDns", "getAddrByName line:" + line);
                            if (line.trim().length() >= 8) {
                                z = true;
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } else if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e7) {
                e = e7;
                bufferedReader = null;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = null;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
        }
        return z;
    }

    public static synchronized String c() {
        String strA;
        if (Math.abs(System.currentTimeMillis() - g) < 600000 && !t.c(f)) {
            com.tencent.android.tpush.a.a.i("httpDns", "Use the cached DNS tpns.qq.com -> " + f);
            strA = f;
        } else {
            c cVar = new c("tpns.qq.com");
            Thread thread = new Thread(cVar);
            thread.start();
            try {
                thread.join(4000L);
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c("httpDns", "t.join", e);
            }
            strA = cVar.a();
            com.tencent.android.tpush.a.a.i("httpDns", "DNS tpns.qq.com -> " + strA);
            if (t.c(strA)) {
                strA = d();
            } else {
                f = strA;
                g = System.currentTimeMillis();
            }
        }
        return strA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String i(String str) {
        InetAddress inetAddress;
        if (t.c(str)) {
            return null;
        }
        try {
            System.nanoTime();
            long jNanoTime = System.nanoTime();
            InetAddress[] allByName = InetAddress.getAllByName(str);
            long jNanoTime2 = System.nanoTime();
            if (allByName == null || allByName.length <= 0) {
                inetAddress = null;
            } else {
                inetAddress = allByName[0];
                com.tencent.android.tpush.a.a.i("httpDns", "DNS " + str + " -> " + inetAddress + " in " + ((jNanoTime2 - jNanoTime) / 1000000) + "ms");
            }
            if (inetAddress != null) {
                return inetAddress.getHostAddress();
            }
            return null;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("httpDns", "NSLookup error: ", th);
            return null;
        }
    }

    private static String d() {
        return new Random().nextInt(1) == 0 ? "203.205.179.220" : "203.205.179.210";
    }
}
