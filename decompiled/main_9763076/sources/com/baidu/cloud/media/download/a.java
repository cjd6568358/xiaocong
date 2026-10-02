package com.baidu.cloud.media.download;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.BuglyStrategy;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class a {
    /* JADX WARN: Code duplicated, block: B:176:0x0130 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:186:0x013a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:188:0x0135 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:201:? A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v27, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v28, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v31, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.io.BufferedOutputStream] */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v27 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.io.BufferedInputStream] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9 */
    public static int a(String str, String str2) throws Throwable {
        ?? r3;
        ?? r6;
        ?? r4;
        ?? r5;
        ?? r7;
        boolean zExists;
        String str3;
        ?? r2 = -2;
        e = -2;
        e = -2;
        e = -2;
        e = -2;
        e = -2;
        r2 = -2;
        r2 = -2;
        r2 = -2;
        ?? e = -2;
        ?? r8 = 0;
         = 0;
         = 0;
         = 0;
         = 0;
         = 0;
         = 0;
        r8 = 0;
         = 0;
        ?? r9 = 0;
        ?? r10 = 0;
        int i = -1;
        String str4 = str2 + ".tmp";
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            ?? e2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                e2.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                e2.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                e2.connect();
                if (e2.getResponseCode() / 100 != 2) {
                    if (0 != 0) {
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e3) {
                            e = e3;
                        }
                    }
                    if (0 != 0) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e4) {
                            e = e4;
                        }
                    }
                    if (e2 != 0) {
                        try {
                            e2.disconnect();
                        } catch (Exception e5) {
                            e2 = e5;
                        }
                    }
                } else if (e2.getContentType().equals("text/html")) {
                    Log.d("DownloadUtils", "contentType is text/html now, not valid network");
                    if (0 != 0) {
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e6) {
                        }
                    }
                    if (0 != 0) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e7) {
                        }
                    }
                    if (e2 != 0) {
                        try {
                            e2.disconnect();
                        } catch (Exception e8) {
                            e2 = e8;
                        }
                    }
                    i = -2;
                } else {
                    BufferedInputStream bufferedInputStream2 = new BufferedInputStream(e2.getInputStream());
                    try {
                        try {
                            File file = new File(str4);
                            try {
                                File file2 = new File(str2);
                                if (!file.getParentFile().exists()) {
                                    file.getParentFile().mkdirs();
                                }
                                if (file.exists()) {
                                    file.delete();
                                }
                                if (file2.exists()) {
                                    file2.delete();
                                }
                                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
                                try {
                                    byte[] bArr = new byte[5120];
                                    while (true) {
                                        int i2 = bufferedInputStream2.read(bArr);
                                        if (i2 <= 0) {
                                            break;
                                        }
                                        bufferedOutputStream2.write(bArr, 0, i2);
                                    }
                                    if (file == null || !(zExists = file.exists())) {
                                        Log.d("DownloadUtils", "tmFile not exists");
                                        r9 = "tmFile not exists";
                                    } else if (file.renameTo(file2)) {
                                        i = 1;
                                    } else {
                                        str3 = "rename failed";
                                        Log.d("DownloadUtils", "rename failed");
                                    }
                                    if (bufferedInputStream2 != null) {
                                        try {
                                            r9 = str3;
                                            r9 = zExists;
                                            bufferedInputStream2.close();
                                        } catch (Exception e9) {
                                            e = e9;
                                        }
                                    }
                                    if (bufferedOutputStream2 != null) {
                                        try {
                                            bufferedOutputStream2.close();
                                        } catch (Exception e10) {
                                            e = e10;
                                        }
                                    }
                                    if (e2 != 0) {
                                        try {
                                            e2.disconnect();
                                        } catch (Exception e11) {
                                            e2 = e11;
                                        }
                                    }
                                } catch (Exception e12) {
                                    r10 = bufferedOutputStream2;
                                    r5 = bufferedInputStream2;
                                    r7 = file;
                                    e = e12;
                                    r4 = e2;
                                    try {
                                        Log.d("DownloadUtils", "http save exception message=" + e.getMessage());
                                        if (r7 != 0) {
                                            try {
                                                r7.delete();
                                            } catch (Exception e13) {
                                            }
                                        }
                                        if ((e instanceof SocketException) || (e instanceof ConnectException)) {
                                            if (r5 != 0) {
                                                try {
                                                    r5.close();
                                                } catch (Exception e14) {
                                                }
                                            }
                                            if (r10 != 0) {
                                                try {
                                                    r10.close();
                                                } catch (Exception e15) {
                                                }
                                            }
                                            if (r4 != 0) {
                                                try {
                                                    r4.disconnect();
                                                } catch (Exception e16) {
                                                }
                                            }
                                            return r2 == true ? 1 : 0;
                                        }
                                        if (r5 != 0) {
                                            try {
                                                r5.close();
                                            } catch (Exception e17) {
                                            }
                                        }
                                        if (r10 != 0) {
                                            try {
                                                r10.close();
                                            } catch (Exception e18) {
                                            }
                                        }
                                        if (r4 == 0) {
                                            return i;
                                        }
                                        try {
                                            r4.disconnect();
                                            return i;
                                        } catch (Exception e19) {
                                            return i;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        r6 = r5;
                                        r3 = r4;
                                        r8 = r10;
                                        if (r6 != 0) {
                                            try {
                                                r6.close();
                                            } catch (Exception e20) {
                                            }
                                        }
                                        if (r8 != 0) {
                                            try {
                                                r8.close();
                                            } catch (Exception e21) {
                                            }
                                        }
                                        if (r3 != 0) {
                                            throw th;
                                        }
                                        try {
                                            r3.disconnect();
                                            throw th;
                                        } catch (Exception e22) {
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th2) {
                                    r3 = e2;
                                    r8 = bufferedOutputStream2;
                                    th = th2;
                                    r6 = bufferedInputStream2;
                                    if (r6 != 0) {
                                        r6.close();
                                    }
                                    if (r8 != 0) {
                                        r8.close();
                                    }
                                    if (r3 != 0) {
                                        throw th;
                                    }
                                    r3.disconnect();
                                    throw th;
                                }
                            } catch (Exception e23) {
                                r5 = bufferedInputStream2;
                                r7 = file;
                                r4 = e2;
                                e = e23;
                            }
                        } catch (Exception e24) {
                            r5 = bufferedInputStream2;
                            r7 = 0;
                            r4 = e2;
                            e = e24;
                        }
                    } catch (Throwable th3) {
                        r3 = e2;
                        th = th3;
                        r6 = bufferedInputStream2;
                    }
                }
                return i;
            } catch (Exception e25) {
                r5 = r9;
                r7 = r9;
                r4 = e2;
                e = e25;
                r2 = e;
                r10 = r9;
            } catch (Throwable th4) {
                r3 = e2;
                r6 = r9;
                th = th4;
                r8 = r9;
            }
        } catch (Exception e26) {
            e = e26;
            r4 = 0;
            r5 = 0;
            r7 = 0;
        } catch (Throwable th5) {
            th = th5;
            r3 = 0;
            r6 = 0;
        }
    }

    /* JADX WARN: Code duplicated, block: B:68:0x0097 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:78:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:? A[SYNTHETIC] */
    public static String a(String str) throws Throwable {
        BufferedReader bufferedReader;
        HttpURLConnection httpURLConnection;
        HttpURLConnection httpURLConnection2 = null;
        BufferedReader bufferedReader2 = null;
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection3.setConnectTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                httpURLConnection3.setReadTimeout(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                httpURLConnection3.connect();
                if (httpURLConnection3.getResponseCode() / 100 != 2 || httpURLConnection3.getContentLength() > 5242880) {
                    if (0 != 0) {
                        try {
                            bufferedReader2.close();
                        } catch (Exception e) {
                        }
                    }
                    if (httpURLConnection3 != null) {
                        try {
                            httpURLConnection3.disconnect();
                        } catch (Exception e2) {
                        }
                    }
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection3.getInputStream()));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line + "\n");
                    } catch (Exception e3) {
                        httpURLConnection = httpURLConnection3;
                        e = e3;
                    } catch (Throwable th) {
                        httpURLConnection2 = httpURLConnection3;
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e4) {
                            }
                        }
                        if (httpURLConnection2 != null) {
                            throw th;
                        }
                        try {
                            httpURLConnection2.disconnect();
                            throw th;
                        } catch (Exception e5) {
                            throw th;
                        }
                    }
                }
                String string = sb.toString();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e6) {
                    }
                }
                if (httpURLConnection3 != null) {
                    try {
                        httpURLConnection3.disconnect();
                    } catch (Exception e7) {
                    }
                }
                return string;
            } catch (Exception e8) {
                bufferedReader = null;
                e = e8;
                httpURLConnection = httpURLConnection3;
            } catch (Throwable th2) {
                bufferedReader = null;
                httpURLConnection2 = httpURLConnection3;
                th = th2;
            }
        } catch (Exception e9) {
            e = e9;
            httpURLConnection = null;
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
        try {
            Log.d("DownloadUtils", Constants.MAIN_VERSION_TAG, e);
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e10) {
                }
            }
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e11) {
                }
            }
            return null;
        } catch (Throwable th4) {
            th = th4;
            httpURLConnection2 = httpURLConnection;
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (httpURLConnection2 != null) {
                throw th;
            }
            httpURLConnection2.disconnect();
            throw th;
        }
    }

    public static boolean a(Context context) {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (allNetworkInfo = connectivityManager.getAllNetworkInfo()) == null || allNetworkInfo.length <= 0) {
            return false;
        }
        for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(byte[] bArr, String str) throws Throwable {
        File file;
        boolean z;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                file = new File(str);
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(bArr);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                        z = true;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e) {
                                Log.d("DownloadUtils", Constants.MAIN_VERSION_TAG + e.getMessage());
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        Log.d("DownloadUtils", Constants.MAIN_VERSION_TAG + Log.getStackTraceString(e));
                        if (file != null && file.exists()) {
                            file.delete();
                        }
                        z = false;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                Log.d("DownloadUtils", Constants.MAIN_VERSION_TAG + e3.getMessage());
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e4) {
                                Log.d("DownloadUtils", Constants.MAIN_VERSION_TAG + e4.getMessage());
                            }
                        }
                        throw th;
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e6) {
            e = e6;
            file = null;
        }
        return z;
    }

    public static String b(String str) {
        byte[] bytes = str.getBytes();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr2 = new char[32];
            int i = 0;
            for (int i2 = 0; i2 < 16; i2++) {
                byte b = bArrDigest[i2];
                int i3 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i3 + 1;
                cArr2[i3] = cArr[b & 15];
            }
            return new String(cArr2);
        } catch (NoSuchAlgorithmException e) {
            Log.e("DownloadUtils", Constants.MAIN_VERSION_TAG, e);
            return null;
        }
    }
}
