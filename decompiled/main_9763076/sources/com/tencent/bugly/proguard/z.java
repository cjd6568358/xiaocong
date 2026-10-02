package com.tencent.bugly.proguard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import bsh.ParserConstants;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.HttpHost;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class z {
    private static Map<String, String> a = null;
    private static boolean b = false;

    public static String a(Throwable th) {
        if (th == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            return stringWriter.getBuffer().toString();
        } catch (Throwable th2) {
            if (!x.a(th2)) {
                th2.printStackTrace();
            }
            return "fail";
        }
    }

    public static String a() {
        return a(System.currentTimeMillis());
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(j));
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    public static String a(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
        } catch (Exception e) {
            return new Date().toString();
        }
    }

    private static byte[] a(byte[] bArr, int i, String str) {
        if (bArr != null && i != -1) {
            x.c("rqdp{  enD:} %d %d", Integer.valueOf(bArr.length), Integer.valueOf(i));
            try {
                ag agVarA = a.a(i);
                if (agVarA == null) {
                    return null;
                }
                agVarA.a(str);
                return agVarA.b(bArr);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return null;
            }
        }
        return bArr;
    }

    private static byte[] b(byte[] bArr, int i, String str) {
        if (bArr != null && i != -1) {
            try {
                ag agVarA = a.a(i);
                if (agVarA == null) {
                    return null;
                }
                agVarA.a(str);
                return agVarA.a(bArr);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                x.d("encrytype %d %s", Integer.valueOf(i), str);
                return null;
            }
        }
        return bArr;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0049 A[Catch: Throwable -> 0x0054, all -> 0x007b, LOOP:0: B:15:0x0049->B:17:0x004f, LOOP_START, TryCatch #11 {all -> 0x007b, blocks: (B:13:0x0038, B:15:0x0049, B:17:0x004f, B:29:0x0070, B:31:0x0076, B:40:0x008e, B:20:0x0055, B:22:0x005b), top: B:82:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:17:0x004f A[Catch: Throwable -> 0x0054, all -> 0x007b, LOOP:0: B:15:0x0049->B:17:0x004f, LOOP_END, TRY_LEAVE, TryCatch #11 {all -> 0x007b, blocks: (B:13:0x0038, B:15:0x0049, B:17:0x004f, B:29:0x0070, B:31:0x0076, B:40:0x008e, B:20:0x0055, B:22:0x005b), top: B:82:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x005b A[Catch: all -> 0x007b, TRY_LEAVE, TryCatch #11 {all -> 0x007b, blocks: (B:13:0x0038, B:15:0x0049, B:17:0x004f, B:29:0x0070, B:31:0x0076, B:40:0x008e, B:20:0x0055, B:22:0x005b), top: B:82:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0076 A[Catch: Throwable -> 0x0054, all -> 0x007b, LOOP:1: B:29:0x0070->B:31:0x0076, LOOP_END, TRY_LEAVE, TryCatch #11 {all -> 0x007b, blocks: (B:13:0x0038, B:15:0x0049, B:17:0x004f, B:29:0x0070, B:31:0x0076, B:40:0x008e, B:20:0x0055, B:22:0x005b), top: B:82:0x0009 }] */
    /* JADX WARN: Code duplicated, block: B:69:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:70:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x0060 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x007e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:91:0x0070 A[EDGE_INSN: B:91:0x0070->B:93:? BREAK  A[LOOP:0: B:15:0x0049->B:17:0x004f], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:92:0x008e A[EDGE_INSN: B:92:0x008e->B:40:0x008e BREAK  A[LOOP:1: B:29:0x0070->B:31:0x0076], SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.util.zip.ZipOutputStream] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    public static byte[] a(File file, String str, String str2) throws Throwable {
        FileInputStream fileInputStream;
        ZipOutputStream zipOutputStream;
        ByteArrayInputStream byteArrayInputStream;
        byte[] bArr;
        int i;
        int i2;
        byte[] byteArray = null;
        ?? r2 = new Object[0];
        x.c("rqdp{  ZF start}", r2);
        try {
            if (file != null) {
                try {
                    if (file.exists() && file.canRead()) {
                        FileInputStream fileInputStream2 = new FileInputStream(file);
                        try {
                            str2 = file.getName();
                            fileInputStream = fileInputStream2;
                        } catch (Throwable th) {
                            fileInputStream = fileInputStream2;
                            r2 = 0;
                            th = th;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (r2 != 0) {
                                try {
                                    r2.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            x.c("rqdp{  ZF end}", new Object[0]);
                            throw th;
                        }
                    } else {
                        fileInputStream = null;
                    }
                    try {
                        byteArrayInputStream = new ByteArrayInputStream(str.getBytes(HTTP.UTF_8));
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
                        try {
                            zipOutputStream.setMethod(8);
                            zipOutputStream.putNextEntry(new ZipEntry(str2));
                            bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                            if (fileInputStream != null) {
                                while (true) {
                                    i2 = fileInputStream.read(bArr);
                                    if (i2 > 0) {
                                        break;
                                    }
                                    zipOutputStream.write(bArr, 0, i2);
                                }
                            }
                            while (true) {
                                i = byteArrayInputStream.read(bArr);
                                if (i > 0) {
                                    break;
                                }
                                zipOutputStream.write(bArr, 0, i);
                            }
                            zipOutputStream.closeEntry();
                            zipOutputStream.flush();
                            zipOutputStream.finish();
                            byteArray = byteArrayOutputStream.toByteArray();
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            try {
                                zipOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                            x.c("rqdp{  ZF end}", new Object[0]);
                        } catch (Throwable th2) {
                            th = th2;
                            if (!x.a(th)) {
                                th.printStackTrace();
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (zipOutputStream != null) {
                                zipOutputStream.close();
                            }
                            x.c("rqdp{  ZF end}", new Object[0]);
                        }
                    } catch (Throwable th3) {
                        r2 = 0;
                        th = th3;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (r2 != 0) {
                            r2.close();
                        }
                        x.c("rqdp{  ZF end}", new Object[0]);
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    zipOutputStream = null;
                    fileInputStream = null;
                }
            } else {
                fileInputStream = null;
                byteArrayInputStream = new ByteArrayInputStream(str.getBytes(HTTP.UTF_8));
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                zipOutputStream = new ZipOutputStream(byteArrayOutputStream2);
                zipOutputStream.setMethod(8);
                zipOutputStream.putNextEntry(new ZipEntry(str2));
                bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                if (fileInputStream != null) {
                    while (true) {
                        i2 = fileInputStream.read(bArr);
                        if (i2 > 0) {
                            break;
                            break;
                        }
                        zipOutputStream.write(bArr, 0, i2);
                    }
                }
                while (true) {
                    i = byteArrayInputStream.read(bArr);
                    if (i > 0) {
                        break;
                        break;
                    }
                    zipOutputStream.write(bArr, 0, i);
                }
                zipOutputStream.closeEntry();
                zipOutputStream.flush();
                zipOutputStream.finish();
                byteArray = byteArrayOutputStream2.toByteArray();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                zipOutputStream.close();
                x.c("rqdp{  ZF end}", new Object[0]);
            }
            return byteArray;
        } catch (Throwable th5) {
            th = th5;
        }
    }

    public static byte[] a(byte[] bArr, int i) {
        if (bArr != null && i != -1) {
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(bArr.length);
            objArr[1] = i == 2 ? "Gzip" : "zip";
            x.c("[Util] Zip %d bytes data with type %s", objArr);
            try {
                ab abVarA = aa.a(i);
                if (abVarA == null) {
                    return null;
                }
                return abVarA.a(bArr);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return null;
            }
        }
        return bArr;
    }

    public static byte[] b(byte[] bArr, int i) {
        if (bArr != null && i != -1) {
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(bArr.length);
            objArr[1] = i == 2 ? "Gzip" : "zip";
            x.c("[Util] Unzip %d bytes data with type %s", objArr);
            try {
                ab abVarA = aa.a(i);
                if (abVarA == null) {
                    return null;
                }
                return abVarA.b(bArr);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return null;
            }
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, int i, int i2, String str) {
        if (bArr == null) {
            return null;
        }
        try {
            return a(a(bArr, 2), 1, str);
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2, String str) {
        try {
            return b(b(bArr, 1, str), 2);
        } catch (Exception e) {
            if (!x.a(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static long b() {
        try {
            return (((System.currentTimeMillis() + ((long) TimeZone.getDefault().getRawOffset())) / 86400000) * 86400000) - ((long) TimeZone.getDefault().getRawOffset());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1L;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & Constants.NETWORK_TYPE_UNCONNECTED);
            if (hexString.length() == 1) {
                stringBuffer.append(PushConstants.PUSH_TYPE_NOTIFY);
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static String b(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "NULL";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(bArr);
            return a(messageDigest.digest());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:82:0x00e7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x00ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static boolean a(File file, File file2, int i) throws Throwable {
        ZipOutputStream zipOutputStream;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        x.c("rqdp{  ZF start}", new Object[0]);
        if (file == null || file2 == null || file.equals(file2)) {
            x.d("rqdp{  err ZF 1R!}", new Object[0]);
            return false;
        }
        if (!file.exists() || !file.canRead()) {
            x.d("rqdp{  !sFile.exists() || !sFile.canRead(),pls check ,return!}", new Object[0]);
            return false;
        }
        try {
            if (file2.getParentFile() != null && !file2.getParentFile().exists()) {
                file2.getParentFile().mkdirs();
            }
            if (!file2.exists()) {
                file2.createNewFile();
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        if (!file2.exists() || !file2.canRead()) {
            return false;
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file2)));
                try {
                    zipOutputStream.setMethod(8);
                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                    byte[] bArr = new byte[5000];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 <= 0) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, i2);
                    }
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        zipOutputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    x.c("rqdp{  ZF end}", new Object[0]);
                    return true;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = fileInputStream;
                    try {
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (zipOutputStream != null) {
                            try {
                                zipOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        x.c("rqdp{  ZF end}", new Object[0]);
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (zipOutputStream != null) {
                            zipOutputStream.close();
                        }
                        x.c("rqdp{  ZF end}", new Object[0]);
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                zipOutputStream = null;
                fileInputStream2 = fileInputStream;
            }
        } catch (Throwable th5) {
            th = th5;
            zipOutputStream = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean] */
    public static ArrayList<String> a(Context context, String[] strArr) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        ?? r1 = 0;
        r1 = 0;
        ArrayList<String> arrayList = new ArrayList<>();
        ?? J = com.tencent.bugly.crashreport.common.info.a.a(context).J();
        try {
            if (J != 0) {
                ArrayList<String> arrayList2 = new ArrayList<>();
                arrayList2.add(new String("unknown(low memory)"));
                return arrayList2;
            }
            try {
                Process processExec = Runtime.getRuntime().exec(strArr);
                bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        arrayList.add(line);
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = null;
                    }
                }
                bufferedReader2 = new BufferedReader(new InputStreamReader(processExec.getErrorStream()));
                while (true) {
                    try {
                        String line2 = bufferedReader2.readLine();
                        if (line2 != null) {
                            arrayList.add(line2);
                        } else {
                            try {
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (!x.a(th)) {
                            th.printStackTrace();
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (bufferedReader2 != null) {
                            try {
                                bufferedReader2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        return null;
                    }
                }
                bufferedReader.close();
                try {
                    bufferedReader2.close();
                    return arrayList;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return arrayList;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader2 = null;
                bufferedReader = null;
            }
        } catch (Throwable th4) {
            th = th4;
            r1 = J;
        }
    }

    public static String a(Context context, String str) throws Throwable {
        if (str == null || str.trim().equals(Constants.MAIN_VERSION_TAG)) {
            return Constants.MAIN_VERSION_TAG;
        }
        if (a == null) {
            a = new HashMap();
            ArrayList<String> arrayListA = a(context, new String[]{"/system/bin/sh", "-c", "getprop"});
            if (arrayListA != null && arrayListA.size() > 0) {
                x.b(z.class, "Successfully get 'getprop' list.", new Object[0]);
                Pattern patternCompile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
                Iterator<String> it = arrayListA.iterator();
                while (it.hasNext()) {
                    Matcher matcher = patternCompile.matcher(it.next());
                    if (matcher.find()) {
                        a.put(matcher.group(1), matcher.group(2));
                    }
                }
                x.b(z.class, "System properties number: %d.", Integer.valueOf(a.size()));
            }
        }
        if (a.containsKey(str)) {
            return a.get(str);
        }
        return "fail";
    }

    public static void b(long j) {
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean a(String str) {
        return str == null || str.trim().length() <= 0;
    }

    public static void b(String str) {
        if (str != null) {
            File file = new File(str);
            if (file.isFile() && file.exists() && file.canWrite()) {
                file.delete();
            }
        }
    }

    public static byte[] c(long j) {
        try {
            return new StringBuilder().append(j).toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long c(byte[] bArr) {
        if (bArr == null) {
            return -1L;
        }
        try {
            return Long.parseLong(new String(bArr, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public static Context a(Context context) {
        Context applicationContext;
        return (context == null || (applicationContext = context.getApplicationContext()) == null) ? context : applicationContext;
    }

    public static String b(Throwable th) {
        if (th == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    public static void a(Class<?> cls, String str, Object obj, Object obj2) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(null, obj);
        } catch (Exception e) {
        }
    }

    public static Object a(String str, String str2, Object obj, Class<?>[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(null, objArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static void a(Parcel parcel, Map<String, PlugInBean> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList arrayList = new ArrayList(size);
        ArrayList arrayList2 = new ArrayList(size);
        for (Map.Entry<String, PlugInBean> entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putInt("pluginNum", arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            bundle.putString("pluginKey" + i, (String) arrayList.get(i));
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            bundle.putString("pluginVal" + i2 + "plugInId", ((PlugInBean) arrayList2.get(i2)).a);
            bundle.putString("pluginVal" + i2 + "plugInUUID", ((PlugInBean) arrayList2.get(i2)).c);
            bundle.putString("pluginVal" + i2 + "plugInVersion", ((PlugInBean) arrayList2.get(i2)).b);
        }
        parcel.writeBundle(bundle);
    }

    public static Map<String, PlugInBean> a(Parcel parcel) {
        HashMap map;
        Bundle bundle = parcel.readBundle();
        if (bundle == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int iIntValue = ((Integer) bundle.get("pluginNum")).intValue();
        for (int i = 0; i < iIntValue; i++) {
            arrayList.add(bundle.getString("pluginKey" + i));
        }
        for (int i2 = 0; i2 < iIntValue; i2++) {
            arrayList2.add(new PlugInBean(bundle.getString("pluginVal" + i2 + "plugInId"), bundle.getString("pluginVal" + i2 + "plugInVersion"), bundle.getString("pluginVal" + i2 + "plugInUUID")));
        }
        if (arrayList.size() == arrayList2.size()) {
            HashMap map2 = new HashMap(arrayList.size());
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                map2.put(arrayList.get(i3), PlugInBean.class.cast(arrayList2.get(i3)));
            }
            map = map2;
        } else {
            x.e("map plugin parcel error!", new Object[0]);
            map = null;
        }
        return map;
    }

    public static void b(Parcel parcel, Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            parcel.writeBundle(null);
            return;
        }
        int size = map.size();
        ArrayList<String> arrayList = new ArrayList<>(size);
        ArrayList<String> arrayList2 = new ArrayList<>(size);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arrayList.add(entry.getKey());
            arrayList2.add(entry.getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("keys", arrayList);
        bundle.putStringArrayList("values", arrayList2);
        parcel.writeBundle(bundle);
    }

    public static Map<String, String> b(Parcel parcel) {
        HashMap map;
        Bundle bundle = parcel.readBundle();
        if (bundle == null) {
            return null;
        }
        ArrayList<String> stringArrayList = bundle.getStringArrayList("keys");
        ArrayList<String> stringArrayList2 = bundle.getStringArrayList("values");
        if (stringArrayList != null && stringArrayList2 != null && stringArrayList.size() == stringArrayList2.size()) {
            HashMap map2 = new HashMap(stringArrayList.size());
            for (int i = 0; i < stringArrayList.size(); i++) {
                map2.put(stringArrayList.get(i), stringArrayList2.get(i));
            }
            map = map2;
        } else {
            x.e("map parcel error!", new Object[0]);
            map = null;
        }
        return map;
    }

    public static byte[] a(Parcelable parcelable) {
        Parcel parcelObtain = Parcel.obtain();
        parcelable.writeToParcel(parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        return bArrMarshall;
    }

    public static <T> T a(byte[] bArr, Parcelable.Creator<T> creator) {
        T tCreateFromParcel;
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.unmarshall(bArr, 0, bArr.length);
        parcelObtain.setDataPosition(0);
        try {
            try {
                tCreateFromParcel = creator.createFromParcel(parcelObtain);
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
            } catch (Throwable th) {
                th.printStackTrace();
                if (parcelObtain != null) {
                    parcelObtain.recycle();
                }
                tCreateFromParcel = null;
            }
            return tCreateFromParcel;
        } catch (Throwable th2) {
            if (parcelObtain != null) {
                parcelObtain.recycle();
            }
            throw th2;
        }
    }

    public static String a(Context context, int i, String str) throws Throwable {
        if (!AppInfo.a(context, "android.permission.READ_LOGS")) {
            x.d("no read_log permission!", new Object[0]);
            return null;
        }
        String[] strArr = str == null ? new String[]{"logcat", "-d", "-v", "threadtime"} : new String[]{"logcat", "-d", "-v", "threadtime", "-s", str};
        Process process = null;
        StringBuilder sb = new StringBuilder();
        try {
            try {
                Process processExec = Runtime.getRuntime().exec(strArr);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line).append("\n");
                        if (i > 0 && sb.length() > i) {
                            sb.delete(0, sb.length() - i);
                        }
                    }
                    String string = sb.toString();
                    if (processExec == null) {
                        return string;
                    }
                    try {
                        processExec.getOutputStream().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        processExec.getInputStream().close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    try {
                        processExec.getErrorStream().close();
                        return string;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    process = processExec;
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                    String string2 = sb.append("\n[error:" + th.toString() + "]").toString();
                    if (process == null) {
                        return string2;
                    }
                    try {
                        process.getOutputStream().close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    try {
                        process.getInputStream().close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    try {
                        process.getErrorStream().close();
                        return string2;
                    } catch (IOException e6) {
                        e6.printStackTrace();
                        return string2;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static Map<String, String> a(int i, boolean z) {
        HashMap map = new HashMap(12);
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        if (allStackTraces == null) {
            return null;
        }
        Thread.currentThread().getId();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Thread, StackTraceElement[]> entry : allStackTraces.entrySet()) {
            sb.setLength(0);
            if (entry.getValue() != null && entry.getValue().length != 0) {
                for (StackTraceElement stackTraceElement : entry.getValue()) {
                    if (i > 0 && sb.length() >= i) {
                        sb.append("\n[Stack over limit size :" + i + " , has been cutted !]");
                        break;
                    }
                    sb.append(stackTraceElement.toString()).append("\n");
                }
                map.put(entry.getKey().getName() + "(" + entry.getKey().getId() + ")", sb.toString());
            }
        }
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.DataInputStream] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    public static synchronized byte[] a(int i) {
        ?? r2;
        byte[] encoded;
        DataInputStream dataInputStream;
        try {
            try {
                try {
                    encoded = new byte[16];
                    dataInputStream = new DataInputStream(new FileInputStream(new File("/dev/urandom")));
                    try {
                        dataInputStream.readFully(encoded);
                        dataInputStream.close();
                        r2 = dataInputStream;
                    } catch (Exception e) {
                        e = e;
                        x.e("Failed to read from /dev/urandom : %s", e);
                        if (dataInputStream != null) {
                            dataInputStream.close();
                        }
                        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                        r2 = 128;
                        keyGenerator.init(ParserConstants.LSHIFTASSIGN, new SecureRandom());
                        encoded = keyGenerator.generateKey().getEncoded();
                    }
                } catch (Exception e2) {
                    if (!x.b(e2)) {
                        e2.printStackTrace();
                    }
                    encoded = null;
                }
            } catch (Exception e3) {
                e = e3;
                dataInputStream = null;
            } catch (Throwable th) {
                th = th;
                r2 = 0;
                if (r2 != 0) {
                    r2.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return encoded;
    }

    @TargetApi(19)
    public static byte[] a(int i, byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            if (Build.VERSION.SDK_INT < 21 || b) {
                cipher.init(i, secretKeySpec, new IvParameterSpec(bArr2));
            } else {
                try {
                    cipher.init(i, secretKeySpec, new GCMParameterSpec(cipher.getBlockSize() << 3, bArr2));
                } catch (InvalidAlgorithmParameterException e) {
                    b = true;
                    throw e;
                }
            }
            return cipher.doFinal(bArr);
        } catch (Exception e2) {
            if (!x.b(e2)) {
                e2.printStackTrace();
            }
            return null;
        }
    }

    public static byte[] b(int i, byte[] bArr, byte[] bArr2) {
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bArr2));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, publicKeyGeneratePublic);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            if (!x.b(e)) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        x.c("[Util] try to lock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (file.exists()) {
                if (System.currentTimeMillis() - file.lastModified() < j) {
                    return false;
                }
                x.c("[Util] lock file(%s) is expired, unlock it", str);
                b(context, str);
            }
            if (file.createNewFile()) {
                x.c("[Util] successfully locked file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                return true;
            }
            x.c("[Util] Failed to locked file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return false;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        x.c("[Util] try to unlock file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            File file = new File(context.getFilesDir() + File.separator + str);
            if (!file.exists()) {
                return true;
            }
            if (!file.delete()) {
                return false;
            }
            x.c("[Util] successfully unlocked file:%s (pid=%d | tid=%d)", str, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            return true;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    public static String a(File file) throws Throwable {
        BufferedReader bufferedReader;
        String string = 0;
        string = 0;
        string = 0;
        string = 0;
        string = 0;
        string = 0;
        if (file != null && file.exists()) {
            try {
                if (file.canRead()) {
                    try {
                        StringBuilder sb = new StringBuilder();
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                        while (true) {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                                sb.append(line);
                                sb.append("\n");
                            } catch (Throwable th) {
                                th = th;
                                x.a(th);
                                if (bufferedReader != null) {
                                    try {
                                        bufferedReader.close();
                                    } catch (Exception e) {
                                        x.a(e);
                                    }
                                }
                            }
                        }
                        string = sb.toString();
                        try {
                            bufferedReader.close();
                        } catch (Exception e2) {
                            x.a(e2);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader = null;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        }
        return string;
    }

    private static BufferedReader b(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
        } catch (Throwable th) {
            x.a(th);
            return null;
        }
    }

    public static BufferedReader a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            File file = new File(str, str2);
            if (file.exists() && file.canRead()) {
                return b(file);
            }
            return null;
        } catch (NullPointerException e) {
            x.a(e);
            return null;
        }
    }

    public static Thread a(Runnable runnable, String str) {
        try {
            Thread thread = new Thread(runnable);
            thread.setName(str);
            thread.start();
            return thread;
        } catch (Throwable th) {
            x.e("[Util] Failed to start a thread to execute task with message: %s", th.getMessage());
            return null;
        }
    }

    public static boolean a(Runnable runnable) {
        if (runnable != null) {
            w wVarA = w.a();
            if (wVarA != null) {
                return wVarA.a(runnable);
            }
            String[] strArrSplit = runnable.getClass().getName().split("\\.");
            if (a(runnable, strArrSplit[strArrSplit.length - 1]) != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean c(String str) {
        if (str == null || str.trim().length() <= 0) {
            return false;
        }
        if (str.length() > 255) {
            x.a("URL(%s)'s length is larger than 255.", str);
            return false;
        }
        if (!str.toLowerCase().startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            x.a("URL(%s) is not start with \"http\".", str);
            return false;
        }
        if (str.toLowerCase().contains("qq.com")) {
            return true;
        }
        x.a("URL(%s) does not contain \"qq.com\".", str);
        return false;
    }

    public static SharedPreferences a(String str, Context context) {
        if (context != null) {
            return context.getSharedPreferences(str, 0);
        }
        return null;
    }

    public static String b(String str, String str2) {
        return (com.tencent.bugly.crashreport.common.info.a.b() == null || com.tencent.bugly.crashreport.common.info.a.b().E == null) ? Constants.MAIN_VERSION_TAG : com.tencent.bugly.crashreport.common.info.a.b().E.getString(str, str2);
    }

    public static String d(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            if (i != 0) {
                stringBuffer.append(':');
            }
            String hexString = Integer.toHexString(bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED);
            if (hexString.length() == 1) {
                hexString = PushConstants.PUSH_TYPE_NOTIFY + hexString;
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString().toUpperCase();
    }
}
