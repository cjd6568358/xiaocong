package com.youzan.spiderman.cache;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.youzan.spiderman.utils.PermissionUtil;
import java.io.File;

/* JADX INFO: compiled from: FilePath.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    public static final String a = g.class.getSimpleName();
    private static String b = null;
    private static boolean c = false;

    public static void a(Context context) {
        File extPath;
        try {
            if (PermissionUtil.hasExtStroragePermision(context) && "mounted".equals(Environment.getExternalStorageState()) && (extPath = context.getExternalFilesDir(null)) != null) {
                b = extPath.getAbsolutePath() + File.separator + "spider_porval";
                c = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(b)) {
            b = context.getFilesDir().getAbsolutePath() + File.separator + "spider_porval";
            c = true;
        }
        try {
            a(b());
            a(c());
            a(d());
            a(e());
            a(f());
            a(g());
            a(h());
            a(i());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private static void a(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean a() {
        return c;
    }

    public static String b() {
        return String.format("%s%s%s", b, File.separator, "preload_res");
    }

    public static String c() {
        return String.format("%s%s%s", b, File.separator, "download_dir");
    }

    public static String d() {
        return String.format("%s%s%s", b, File.separator, "stream_download_dir");
    }

    public static String e() {
        return String.format("%s%s%s", b, File.separator, "preference_dir");
    }

    public static String f() {
        return String.format("%s%s%s", b(), File.separator, "YZScriptCaches");
    }

    public static String g() {
        return String.format("%s%s%s", b(), File.separator, "YZImageCaches");
    }

    public static String h() {
        return String.format("%s%s%s", b(), File.separator, "YZHtmlContent");
    }

    public static String i() {
        return String.format("%s%s%s", b(), File.separator, "YZHtmlHeader");
    }
}
