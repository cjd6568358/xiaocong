package com.baidu.cloud.media.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class d {
    private String a;
    private List<String> b;
    private a c;
    private Context d;
    private String f;
    private String e = null;
    private String g = null;

    public static abstract class a {
        public abstract void a(int i);

        public abstract void a(List<String> list);
    }

    public d(Context context, String str) {
        this.a = Constants.MAIN_VERSION_TAG;
        this.b = null;
        this.c = null;
        this.d = context.getApplicationContext();
        this.f = str;
        this.a = Constants.MAIN_VERSION_TAG;
        this.b = new ArrayList();
        this.c = null;
    }

    private long a(String str) {
        try {
            String[] strArrSplit = str.split(":|=|,");
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i].trim().equals("BANDWIDTH")) {
                    return Long.parseLong(strArrSplit[i + 1].trim());
                }
            }
            return 0L;
        } catch (Exception e) {
            Log.d("M3U8Parser", Constants.MAIN_VERSION_TAG + e.getMessage());
            return 0L;
        }
    }

    private void a() {
        SharedPreferences.Editor editorEdit = this.d.getSharedPreferences("__cyberplayer_dl_sec", 0).edit();
        editorEdit.putString(this.f, this.e);
        if (Build.VERSION.SDK_INT >= 9) {
            editorEdit.apply();
        } else {
            editorEdit.commit();
        }
    }

    private void a(String str, List<String> list) {
        String str2;
        String str3 = null;
        boolean z = false;
        if (str == null || TextUtils.isEmpty(str)) {
            this.c.a(1);
            return;
        }
        Scanner scanner = new Scanner(str);
        StringBuilder sb = new StringBuilder();
        long j = 0;
        boolean z2 = false;
        String str4 = null;
        while (scanner.hasNextLine()) {
            try {
                String strNextLine = scanner.nextLine();
                if (!z2 && !strNextLine.startsWith("#EXTM3U")) {
                    scanner.close();
                    this.c.a(4);
                    return;
                }
                if (!TextUtils.isEmpty(str4) && str4.startsWith("#EXT-X-STREAM-INF:")) {
                    long jA = a(str4);
                    if (jA > j) {
                        str2 = strNextLine;
                    } else {
                        jA = j;
                        str2 = str3;
                    }
                    str3 = str2;
                    j = jA;
                    z = true;
                } else if (!TextUtils.isEmpty(str4) && str4.startsWith("#EXTINF:")) {
                    String strSubstring = strNextLine.contains("://") ? Constants.MAIN_VERSION_TAG : this.a;
                    if (strNextLine.startsWith("/")) {
                        strSubstring = strSubstring.substring(0, strSubstring.indexOf("/", strSubstring.indexOf("://") + 3));
                    } else if (strNextLine.startsWith("../")) {
                        if (strSubstring.endsWith("/")) {
                            strSubstring = strSubstring.substring(0, strSubstring.length() - 1);
                        }
                        strSubstring = strSubstring.substring(0, strSubstring.lastIndexOf("/") + 1);
                        strNextLine = strNextLine.substring(3);
                    }
                    list.add(strSubstring + strNextLine);
                    sb.append(String.valueOf(list.size()) + ".ts\n");
                } else if (TextUtils.isEmpty(strNextLine) || !strNextLine.startsWith("#EXT-X-KEY:")) {
                    sb.append(strNextLine + "\n");
                } else {
                    String strB = b(strNextLine);
                    if (strB == null) {
                        scanner.close();
                        this.c.a(6);
                        return;
                    }
                    sb.append(strB + "\n");
                }
                z2 = true;
                str4 = strNextLine;
            } catch (Exception e) {
                Log.d("M3U8Parser", Log.getStackTraceString(e));
                this.c.a(2);
                return;
            }
        }
        scanner.close();
        if (z) {
            this.b.clear();
            a(com.baidu.cloud.media.download.a.a((str3.startsWith("http://") ? Constants.MAIN_VERSION_TAG : this.a) + str3), this.b);
            return;
        }
        if (this.e != null) {
            a();
        }
        if (com.baidu.cloud.media.download.a.a(sb.toString().getBytes(), this.f)) {
            this.c.a(this.b);
        } else {
            this.c.a(5);
        }
    }

    private String b(String str) {
        String strA;
        try {
            boolean zContains = str.contains("=media-drm-player-binding");
            boolean zContains2 = str.contains("=media-drm-safe-code");
            boolean zContains3 = str.contains("=media-drm-token");
            if (zContains || zContains2 || zContains3) {
                if (this.e == null) {
                    String strSubstring = str.substring(str.indexOf("URI=\"") + 5);
                    String strSubstring2 = strSubstring.substring(0, strSubstring.indexOf("\""));
                    try {
                        strA = VideoDownloadManager.b().a();
                        if (TextUtils.isEmpty(strA)) {
                            strA = "pid-android-1";
                        }
                    } catch (Exception e) {
                        strA = "pid-android-1";
                    }
                    String str2 = strSubstring2 + "&playerId=" + strA;
                    if (zContains3) {
                        str2 = str2 + "&token=" + this.g;
                    }
                    this.e = LocalHlsSec.bytes2HexStr(new LocalHlsSec().crypt(this.d, LocalHlsSec.hexStr2Bytes(new JSONObject(com.baidu.cloud.media.download.a.a(str2)).getString("encryptedVideoKey")), 0));
                }
                try {
                    if (zContains) {
                        str = str.replace("media-drm-player-binding", "media-drm-local-key");
                    } else if (zContains2) {
                        str = str.replace("media-drm-safe-code", "media-drm-local-key");
                    } else if (zContains3) {
                        str = str.replace("media-drm-token", "media-drm-local-key");
                    }
                } catch (Exception e2) {
                    e = e2;
                    Log.d("M3U8Parser", Constants.MAIN_VERSION_TAG, e);
                }
            }
        } catch (Exception e3) {
            e = e3;
            str = null;
        }
        return str;
    }

    public int a(String str, String str2, a aVar) {
        if (TextUtils.isEmpty(str) || aVar == null) {
            return 1;
        }
        this.g = str2;
        this.c = aVar;
        this.b.clear();
        if (TextUtils.isEmpty(this.a)) {
            this.a = str.substring(0, str.lastIndexOf(47) + 1);
        }
        a(com.baidu.cloud.media.download.a.a(str), this.b);
        return 0;
    }
}
