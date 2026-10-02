package com.baidu.mobstat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class cu {
    private static final Proxy a = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.172", 80));
    private static final Proxy b = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80));

    public static String a() {
        try {
            return Environment.getExternalStorageState();
        } catch (Exception e) {
            return null;
        }
    }

    public static File a(String str) {
        File externalStorageDirectory;
        if (!"mounted".equals(a())) {
            return null;
        }
        try {
            externalStorageDirectory = Environment.getExternalStorageDirectory();
        } catch (Exception e) {
            externalStorageDirectory = null;
        }
        if (externalStorageDirectory != null) {
            return new File(externalStorageDirectory, str);
        }
        return null;
    }

    public static void a(Context context, String str, String str2, boolean z) throws Throwable {
        FileOutputStream fileOutputStream;
        Throwable th;
        if (context != null) {
            try {
                try {
                    FileOutputStream fileOutputStreamOpenFileOutput = context.openFileOutput(str, z ? WXMediaMessage.THUMB_LENGTH_LIMIT : 0);
                    try {
                        da.a(new ByteArrayInputStream(str2.getBytes("utf-8")), fileOutputStreamOpenFileOutput);
                        da.a(fileOutputStreamOpenFileOutput);
                    } catch (Throwable th2) {
                        fileOutputStream = fileOutputStreamOpenFileOutput;
                        th = th2;
                        da.a(fileOutputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    fileOutputStream = null;
                    th = th3;
                }
            } catch (Exception e) {
                da.a(null);
            }
        }
    }

    public static void a(String str, String str2, boolean z) throws Throwable {
        FileOutputStream fileOutputStream;
        File parentFile;
        FileOutputStream fileOutputStream2 = null;
        try {
            File fileA = a(str);
            if (fileA == null) {
                fileOutputStream = null;
            } else {
                if (!fileA.exists() && (parentFile = fileA.getParentFile()) != null) {
                    parentFile.mkdirs();
                }
                fileOutputStream = new FileOutputStream(fileA, z);
                try {
                    da.a(new ByteArrayInputStream(str2.getBytes("utf-8")), fileOutputStream);
                } catch (Exception e) {
                    fileOutputStream2 = fileOutputStream;
                    da.a(fileOutputStream2);
                    return;
                } catch (Throwable th) {
                    fileOutputStream2 = fileOutputStream;
                    th = th;
                    da.a(fileOutputStream2);
                    throw th;
                }
            }
            da.a(fileOutputStream);
        } catch (Exception e2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String a(Context context, String str) throws Throwable {
        FileInputStream fileInputStreamOpenFileInput;
        Throwable th;
        FileInputStream fileInputStream = null;
        try {
            fileInputStreamOpenFileInput = context.openFileInput(str);
            try {
                byte[] bArrA = a(fileInputStreamOpenFileInput);
                if (bArrA != null) {
                    String str2 = new String(bArrA, "utf-8");
                    da.a(fileInputStreamOpenFileInput);
                    return str2;
                }
                da.a(fileInputStreamOpenFileInput);
            } catch (Exception e) {
                fileInputStream = fileInputStreamOpenFileInput;
                da.a(fileInputStream);
            } catch (Throwable th2) {
                th = th2;
                da.a(fileInputStreamOpenFileInput);
                throw th;
            }
        } catch (Exception e2) {
        } catch (Throwable th3) {
            fileInputStreamOpenFileInput = null;
            th = th3;
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static String b(String str) throws Throwable {
        FileInputStream fileInputStream;
        Throwable th;
        File fileA = a(str);
        if (fileA != null && fileA.exists()) {
            FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new FileInputStream(fileA);
                try {
                    byte[] bArrA = a(fileInputStream);
                    if (bArrA != null) {
                        String str2 = new String(bArrA, "utf-8");
                        da.a(fileInputStream);
                        return str2;
                    }
                    da.a(fileInputStream);
                } catch (Exception e) {
                    fileInputStream2 = fileInputStream;
                    da.a(fileInputStream2);
                } catch (Throwable th2) {
                    th = th2;
                    da.a(fileInputStream);
                    throw th;
                }
            } catch (Exception e2) {
            } catch (Throwable th3) {
                fileInputStream = null;
                th = th3;
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    private static byte[] a(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (da.a(inputStream, byteArrayOutputStream)) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public static boolean b(Context context, String str) {
        return context.deleteFile(str);
    }

    public static boolean c(String str) {
        File fileA = a(str);
        if (fileA == null || !fileA.isFile()) {
            return false;
        }
        return fileA.delete();
    }

    public static boolean c(Context context, String str) {
        return context.getFileStreamPath(str).exists();
    }

    public static HttpURLConnection d(Context context, String str) {
        return a(context, str, SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH, SQLiteDatabase.SQLITE_MAX_LIKE_PATTERN_LENGTH);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0085  */
    @SuppressLint({"DefaultLocale"})
    public static HttpURLConnection a(Context context, String str, int i, int i2) {
        HttpURLConnection httpURLConnection;
        URL url = new URL(str);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(1);
        if (networkInfo2 != null && networkInfo2.isAvailable()) {
            db.a("WIFI is available");
            httpURLConnection = (HttpURLConnection) url.openConnection();
        } else if (networkInfo == null || !networkInfo.isAvailable()) {
            httpURLConnection = null;
        } else {
            String extraInfo = networkInfo.getExtraInfo();
            String lowerCase = extraInfo != null ? extraInfo.toLowerCase() : Constants.MAIN_VERSION_TAG;
            db.a(lowerCase);
            if (lowerCase.startsWith("cmwap") || lowerCase.startsWith("uniwap") || lowerCase.startsWith("3gwap")) {
                httpURLConnection = (HttpURLConnection) url.openConnection(a);
            } else if (!lowerCase.startsWith("ctwap")) {
                httpURLConnection = null;
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection(b);
            }
        }
        if (httpURLConnection == null) {
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        httpURLConnection.setConnectTimeout(i);
        httpURLConnection.setReadTimeout(i2);
        return httpURLConnection;
    }

    public static boolean e(Context context, String str) {
        try {
            return context.checkCallingOrSelfPermission(str) != -1;
        } catch (Exception e) {
            db.b("Check permission failed.");
            return false;
        }
    }
}
