package com.baidu.android.bbalbs.common.util;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import bsh.ParserConstants;
import com.baidu.android.bbalbs.common.a.d;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.crypto.Cipher;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b {
    private static final String a = new String(com.baidu.android.bbalbs.common.a.b.a(new byte[]{77, 122, 65, 121, 77, 84, 73, 120, 77, 68, 73, 61})) + new String(com.baidu.android.bbalbs.common.a.b.a(new byte[]{90, 71, 108, 106, 100, 87, 82, 112, 89, 87, 73, 61}));
    private static C0005b e;
    private final Context b;
    private int c = 0;
    private PublicKey d;

    /* JADX INFO: Access modifiers changed from: private */
    static class a {
        public ApplicationInfo a;
        public int b;
        public boolean c;
        public boolean d;

        private a() {
            this.b = 0;
            this.c = false;
            this.d = false;
        }

        /* synthetic */ a(com.baidu.android.bbalbs.common.util.c cVar) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.baidu.android.bbalbs.common.util.b$b, reason: collision with other inner class name */
    private static class C0005b {
        public String a;
        public String b;
        public int c;

        private C0005b() {
            this.c = 2;
        }

        /* synthetic */ C0005b(com.baidu.android.bbalbs.common.util.c cVar) {
            this();
        }

        public static C0005b a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("deviceid");
                String string2 = jSONObject.getString(MidEntity.TAG_IMEI);
                int i = jSONObject.getInt(MidEntity.TAG_VER);
                if (TextUtils.isEmpty(string) || string2 == null) {
                    return null;
                }
                C0005b c0005b = new C0005b();
                c0005b.a = string;
                c0005b.b = string2;
                c0005b.c = i;
                return c0005b;
            } catch (JSONException e) {
                b.b(e);
                return null;
            }
        }

        public String a() {
            try {
                return new JSONObject().put("deviceid", this.a).put(MidEntity.TAG_IMEI, this.b).put(MidEntity.TAG_VER, this.c).toString();
            } catch (JSONException e) {
                b.b(e);
                return null;
            }
        }

        public String b() {
            String str = this.b;
            if (TextUtils.isEmpty(str)) {
                str = PushConstants.PUSH_TYPE_NOTIFY;
            }
            return this.a + "|" + new StringBuffer(str).reverse().toString();
        }
    }

    static class c {
        static boolean a(String str, int i) {
            try {
                Os.chmod(str, i);
                return true;
            } catch (ErrnoException e) {
                b.b(e);
                return false;
            }
        }
    }

    private b(Context context) throws Throwable {
        this.b = context.getApplicationContext();
        a();
    }

    public static String a(Context context) {
        return c(context).b();
    }

    /* JADX WARN: Code duplicated, block: B:41:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.FileReader] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    private static String a(File file) throws Throwable {
        Throwable th;
        FileReader fileReader;
        String str = 0;
        str = 0;
        str = 0;
        try {
            try {
                fileReader = new FileReader(file);
                try {
                    char[] cArr = new char[8192];
                    CharArrayWriter charArrayWriter = new CharArrayWriter();
                    while (true) {
                        int i = fileReader.read(cArr);
                        if (i <= 0) {
                            break;
                        }
                        charArrayWriter.write(cArr, 0, i);
                    }
                    String string = charArrayWriter.toString();
                    str = string;
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                            str = string;
                        } catch (Exception e2) {
                            b(e2);
                            str = string;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    b(e);
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Exception e4) {
                            b(e4);
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                    try {
                        str.close();
                    } catch (Exception e5) {
                        b(e5);
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            fileReader = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                str.close();
            }
            throw th;
        }
        return str;
    }

    private static String a(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String str = Constants.MAIN_VERSION_TAG;
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & Constants.NETWORK_TYPE_UNCONNECTED);
            str = hexString.length() == 1 ? str + PushConstants.PUSH_TYPE_NOTIFY + hexString : str + hexString;
        }
        return str.toLowerCase();
    }

    private List<a> a(Intent intent, boolean z) {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = this.b.getPackageManager();
        List<ResolveInfo> listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers != null) {
            for (ResolveInfo resolveInfo : listQueryBroadcastReceivers) {
                if (resolveInfo.activityInfo != null && resolveInfo.activityInfo.applicationInfo != null) {
                    try {
                        Bundle bundle = packageManager.getReceiverInfo(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name), ParserConstants.LSHIFTASSIGN).metaData;
                        if (bundle != null) {
                            String string = bundle.getString("galaxy_data");
                            if (!TextUtils.isEmpty(string)) {
                                byte[] bArrA = com.baidu.android.bbalbs.common.a.b.a(string.getBytes("utf-8"));
                                JSONObject jSONObject = new JSONObject(new String(bArrA));
                                a aVar = new a(null);
                                aVar.b = jSONObject.getInt(LogFactory.PRIORITY_KEY);
                                aVar.a = resolveInfo.activityInfo.applicationInfo;
                                if (this.b.getPackageName().equals(resolveInfo.activityInfo.applicationInfo.packageName)) {
                                    aVar.d = true;
                                }
                                if (z) {
                                    String string2 = bundle.getString("galaxy_sf");
                                    if (!TextUtils.isEmpty(string2)) {
                                        PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.applicationInfo.packageName, 64);
                                        JSONArray jSONArray = jSONObject.getJSONArray("sigs");
                                        String[] strArr = new String[jSONArray.length()];
                                        for (int i = 0; i < strArr.length; i++) {
                                            strArr[i] = jSONArray.getString(i);
                                        }
                                        if (a(strArr, a(packageInfo.signatures))) {
                                            byte[] bArrA2 = a(com.baidu.android.bbalbs.common.a.b.a(string2.getBytes()), this.d);
                                            if (bArrA2 != null && Arrays.equals(bArrA2, d.a(bArrA))) {
                                                aVar.c = true;
                                            }
                                        }
                                    }
                                }
                                arrayList.add(aVar);
                            }
                        }
                    } catch (Exception e2) {
                    }
                }
            }
        }
        Collections.sort(arrayList, new com.baidu.android.bbalbs.common.util.c(this));
        return arrayList;
    }

    private void a() throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(com.baidu.android.bbalbs.common.util.a.a());
            try {
                this.d = CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream).getPublicKey();
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception e2) {
                        b(e2);
                    }
                }
            } catch (Exception e3) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception e4) {
                        b(e4);
                    }
                }
            } catch (Throwable th) {
                byteArrayInputStream2 = byteArrayInputStream;
                th = th;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (Exception e5) {
                        b(e5);
                    }
                }
                throw th;
            }
        } catch (Exception e6) {
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:39:0x0056 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @SuppressLint({"NewApi"})
    private boolean a(String str) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        int i = Build.VERSION.SDK_INT >= 24 ? 0 : 1;
        try {
            try {
                FileOutputStream fileOutputStreamOpenFileOutput = this.b.openFileOutput("libcuid.so", i);
                try {
                    fileOutputStreamOpenFileOutput.write(str.getBytes());
                    fileOutputStreamOpenFileOutput.flush();
                    if (fileOutputStreamOpenFileOutput != null) {
                        try {
                            fileOutputStreamOpenFileOutput.close();
                        } catch (Exception e2) {
                            b(e2);
                        }
                    }
                    if (i == 0) {
                        return c.a(new File(this.b.getFilesDir(), "libcuid.so").getAbsolutePath(), 436);
                    }
                    return true;
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream = fileOutputStreamOpenFileOutput;
                    try {
                        b(e);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e4) {
                                b(e4);
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (Exception e5) {
                                b(e5);
                            }
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            fileOutputStream = null;
        }
    }

    private boolean a(String str, String str2) {
        try {
            return Settings.System.putString(this.b.getContentResolver(), str, str2);
        } catch (Exception e2) {
            b(e2);
            return false;
        }
    }

    private boolean a(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
            return false;
        }
        HashSet hashSet = new HashSet();
        for (String str : strArr) {
            hashSet.add(str);
        }
        HashSet hashSet2 = new HashSet();
        for (String str2 : strArr2) {
            hashSet2.add(str2);
        }
        return hashSet.equals(hashSet2);
    }

    private static byte[] a(byte[] bArr, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, publicKey);
        return cipher.doFinal(bArr);
    }

    private String[] a(Signature[] signatureArr) {
        String[] strArr = new String[signatureArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = a(d.a(signatureArr[i].toByteArray()));
        }
        return strArr;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x026e A[EDGE_INSN: B:102:0x026e->B:28:0x00ce BREAK  A[LOOP:1: B:20:0x009a->B:110:0x009a], PHI: r3
  0x026e: PHI (r3v6 com.baidu.android.bbalbs.common.util.b$b) = 
  (r3v5 com.baidu.android.bbalbs.common.util.b$b)
  (r3v5 com.baidu.android.bbalbs.common.util.b$b)
  (r3v20 com.baidu.android.bbalbs.common.util.b$b)
 binds: [B:13:0x004e, B:15:0x0061, B:107:0x026e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    private C0005b b() throws Throwable {
        boolean z;
        C0005b c0005bA;
        C0005b c0005bD;
        String strH;
        C0005b c0005b;
        String name;
        String strE = null;
        Object[] objArr = 0;
        boolean z2 = false;
        List<a> listA = a(new Intent("com.baidu.intent.action.GALAXY").setPackage(this.b.getPackageName()), true);
        if (listA == null || listA.size() == 0) {
            for (int i = 0; i < 3; i++) {
                Log.w("DeviceId", "galaxy lib host missing meta-data,make sure you know the right way to integrate galaxy");
            }
            z = false;
        } else {
            a aVar = listA.get(0);
            boolean z3 = aVar.c;
            if (!aVar.c) {
                for (int i2 = 0; i2 < 3; i2++) {
                    Log.w("DeviceId", "galaxy config err, In the release version of the signature should be matched");
                }
            }
            z = z3;
        }
        File file = new File(this.b.getFilesDir(), "libcuid.so");
        C0005b c0005bA2 = file.exists() ? C0005b.a(f(a(file))) : null;
        if (c0005bA2 != null) {
            c0005bA = c0005bA2;
            break;
        }
        this.c |= 16;
        List<a> listA2 = a(new Intent("com.baidu.intent.action.GALAXY"), z);
        if (listA2 == null) {
            c0005bA = c0005bA2;
            break;
        }
        File filesDir = this.b.getFilesDir();
        if ("files".equals(filesDir.getName())) {
            name = "files";
        } else {
            Log.e("DeviceId", "fetal error:: app files dir name is unexpectedly :: " + filesDir.getAbsolutePath());
            name = filesDir.getName();
        }
        Iterator<a> it = listA2.iterator();
        while (true) {
            if (!it.hasNext()) {
                c0005bA = c0005bA2;
                break;
            }
            a next = it.next();
            if (!next.d) {
                File file2 = new File(new File(next.a.dataDir, name), "libcuid.so");
                if (file2.exists()) {
                    c0005bA = C0005b.a(f(a(file2)));
                    if (c0005bA != null) {
                        break;
                    }
                } else {
                    c0005bA = c0005bA2;
                }
                c0005bA2 = c0005bA;
            }
        }
        if (c0005bA == null) {
            c0005bA = C0005b.a(f(b("com.baidu.deviceid.v2")));
        }
        boolean zC = c("android.permission.READ_EXTERNAL_STORAGE");
        if (c0005bA == null && zC) {
            this.c |= 2;
            c0005bD = e();
        } else {
            c0005bD = c0005bA;
        }
        if (c0005bD == null) {
            this.c |= 8;
            c0005bD = d();
        }
        if (c0005bD == null && zC) {
            this.c |= 1;
            strH = h(Constants.MAIN_VERSION_TAG);
            c0005bD = d(strH);
            z2 = true;
        } else {
            strH = null;
        }
        if (c0005bD == null) {
            this.c |= 4;
            if (!z2) {
                strH = h(Constants.MAIN_VERSION_TAG);
            }
            C0005b c0005b2 = new C0005b(objArr == true ? 1 : 0);
            String strB = b(this.b);
            c0005b2.a = com.baidu.android.bbalbs.common.a.c.a((Build.VERSION.SDK_INT < 23 ? strH + strB + UUID.randomUUID().toString() : "com.baidu" + strB).getBytes(), true);
            c0005b2.b = strH;
            c0005b = c0005b2;
        } else {
            c0005b = c0005bD;
        }
        File file3 = new File(this.b.getFilesDir(), "libcuid.so");
        if ((this.c & 16) != 0 || !file3.exists()) {
            String strE2 = TextUtils.isEmpty(null) ? e(c0005b.a()) : null;
            a(strE2);
            strE = strE2;
        }
        boolean zC2 = c();
        if (zC2 && ((this.c & 2) != 0 || TextUtils.isEmpty(b("com.baidu.deviceid.v2")))) {
            if (TextUtils.isEmpty(strE)) {
                strE = e(c0005b.a());
            }
            a("com.baidu.deviceid.v2", strE);
        }
        if (c("android.permission.WRITE_EXTERNAL_STORAGE")) {
            File file4 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
            if ((this.c & 8) != 0 || !file4.exists()) {
                if (TextUtils.isEmpty(strE)) {
                    strE = e(c0005b.a());
                }
                g(strE);
            }
        }
        if (zC2 && ((this.c & 1) != 0 || TextUtils.isEmpty(b("com.baidu.deviceid")))) {
            a("com.baidu.deviceid", c0005b.a);
            a("bd_setting_i", c0005b.b);
        }
        if (zC2 && !TextUtils.isEmpty(c0005b.b)) {
            File file5 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            if ((this.c & 2) != 0 || !file5.exists()) {
                b(c0005b.b, c0005b.a);
            }
        }
        return c0005b;
    }

    public static String b(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        return TextUtils.isEmpty(string) ? Constants.MAIN_VERSION_TAG : string;
    }

    private String b(String str) {
        try {
            return Settings.System.getString(this.b.getContentResolver(), str);
        } catch (Exception e2) {
            b(e2);
            return null;
        }
    }

    private static void b(String str, String str2) {
        File file;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file2 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
        File file3 = new File(file2, ".cuid");
        try {
            if (file2.exists() && !file2.isDirectory()) {
                Random random = new Random();
                File parentFile = file2.getParentFile();
                String name = file2.getName();
                do {
                    file = new File(parentFile, name + random.nextInt() + ".tmp");
                } while (file.exists());
                file2.renameTo(file);
                file.delete();
            }
            file2.mkdirs();
            FileWriter fileWriter = new FileWriter(file3, false);
            fileWriter.write(com.baidu.android.bbalbs.common.a.b.a(com.baidu.android.bbalbs.common.a.a.a(a, a, (str + "=" + str2).getBytes()), "utf-8"));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e2) {
        } catch (Exception e3) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Throwable th) {
    }

    private static C0005b c(Context context) {
        if (e == null) {
            synchronized (C0005b.class) {
                if (e == null) {
                    SystemClock.uptimeMillis();
                    e = new b(context).b();
                    SystemClock.uptimeMillis();
                }
            }
        }
        return e;
    }

    private boolean c() {
        return c("android.permission.WRITE_SETTINGS");
    }

    private boolean c(String str) {
        return this.b.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    private C0005b d() {
        com.baidu.android.bbalbs.common.util.c cVar = null;
        String strB = b("com.baidu.deviceid");
        String strB2 = b("bd_setting_i");
        if (TextUtils.isEmpty(strB2)) {
            strB2 = h(Constants.MAIN_VERSION_TAG);
            if (!TextUtils.isEmpty(strB2)) {
                a("bd_setting_i", strB2);
            }
        }
        if (TextUtils.isEmpty(strB)) {
            strB = b(com.baidu.android.bbalbs.common.a.c.a(("com.baidu" + strB2 + b(this.b)).getBytes(), true));
        }
        if (TextUtils.isEmpty(strB)) {
            return null;
        }
        C0005b c0005b = new C0005b(cVar);
        c0005b.a = strB;
        c0005b.b = strB2;
        return c0005b;
    }

    /* JADX WARN: Code duplicated, block: B:53:0x00c3  */
    private C0005b d(String str) {
        String str2;
        com.baidu.android.bbalbs.common.util.c cVar = null;
        boolean z = false;
        boolean z2 = Build.VERSION.SDK_INT < 23;
        if (z2 && TextUtils.isEmpty(str)) {
            return null;
        }
        String str3 = Constants.MAIN_VERSION_TAG;
        File file = new File(Environment.getExternalStorageDirectory(), "baidu/.cuid");
        if (!file.exists()) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            z = true;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append("\r\n");
            }
            bufferedReader.close();
            String[] strArrSplit = new String(com.baidu.android.bbalbs.common.a.a.b(a, a, com.baidu.android.bbalbs.common.a.b.a(sb.toString().getBytes()))).split("=");
            if (strArrSplit == null || strArrSplit.length != 2) {
                str2 = str;
            } else if (z2 && str.equals(strArrSplit[0])) {
                str3 = strArrSplit[1];
                str2 = str;
            } else if (z2) {
                str2 = str;
            } else {
                if (TextUtils.isEmpty(str)) {
                    str = strArrSplit[1];
                }
                str3 = strArrSplit[1];
                str2 = str;
            }
            if (!z) {
                try {
                    b(str2, str3);
                } catch (FileNotFoundException e2) {
                } catch (IOException e3) {
                } catch (Exception e4) {
                }
            }
        } catch (FileNotFoundException e5) {
            str2 = str;
        } catch (IOException e6) {
            str2 = str;
        } catch (Exception e7) {
            str2 = str;
        }
        if (TextUtils.isEmpty(str3)) {
            return null;
        }
        C0005b c0005b = new C0005b(cVar);
        c0005b.a = str3;
        c0005b.b = str2;
        return c0005b;
    }

    private C0005b e() throws Throwable {
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
        if (file.exists()) {
            String strA = a(file);
            if (!TextUtils.isEmpty(strA)) {
                try {
                    return C0005b.a(new String(com.baidu.android.bbalbs.common.a.a.b(a, a, com.baidu.android.bbalbs.common.a.b.a(strA.getBytes()))));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String e(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return com.baidu.android.bbalbs.common.a.b.a(com.baidu.android.bbalbs.common.a.a.a(a, a, str.getBytes()), "utf-8");
        } catch (UnsupportedEncodingException e2) {
            b(e2);
            return Constants.MAIN_VERSION_TAG;
        } catch (Exception e3) {
            b(e3);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static String f(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new String(com.baidu.android.bbalbs.common.a.a.b(a, a, com.baidu.android.bbalbs.common.a.b.a(str.getBytes())));
        } catch (Exception e2) {
            b(e2);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static void g(String str) {
        File file;
        File file2 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
        File file3 = new File(file2, ".cuid2");
        try {
            if (file2.exists() && !file2.isDirectory()) {
                Random random = new Random();
                File parentFile = file2.getParentFile();
                String name = file2.getName();
                do {
                    file = new File(parentFile, name + random.nextInt() + ".tmp");
                } while (file.exists());
                file2.renameTo(file);
                file.delete();
            }
            file2.mkdirs();
            FileWriter fileWriter = new FileWriter(file3, false);
            fileWriter.write(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e2) {
        } catch (Exception e3) {
        }
    }

    private String h(String str) {
        String deviceId;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.b.getSystemService("phone");
            deviceId = telephonyManager != null ? telephonyManager.getDeviceId() : null;
        } catch (Exception e2) {
            Log.e("DeviceId", "Read IMEI failed", e2);
        }
        String strI = i(deviceId);
        return TextUtils.isEmpty(strI) ? str : strI;
    }

    private static String i(String str) {
        return (str == null || !str.contains(":")) ? str : Constants.MAIN_VERSION_TAG;
    }
}
