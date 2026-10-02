package com.youzan.spiderman.cache;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import com.youzan.spiderman.utils.FileUtil;
import com.youzan.spiderman.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: CacheMerger.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    public static void a(final Context context, final String path) {
        if (context == null || TextUtils.isEmpty(path)) {
            Logger.e("CacheMerger", "context or path is null when unpack zip", new Object[0]);
            return;
        }
        final String md5 = a(path);
        final a mergedZipPref = (a) d.a(a.class, "merged_zip");
        if (!mergedZipPref.a(md5)) {
            new Thread(new Runnable() { // from class: com.youzan.spiderman.cache.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.b(context, path, md5, mergedZipPref);
                }
            }).start();
        }
    }

    private static String a(String path) {
        int tagEnd = path.lastIndexOf(46);
        int tagStart = path.lastIndexOf(95, tagEnd);
        if (tagStart < 0 || tagEnd < 0) {
            throw new IllegalArgumentException("the zip file path format should be ${name}_${md5}.zip");
        }
        String md5 = path.substring(tagStart + 1, tagEnd);
        if (TextUtils.isEmpty(md5) || md5.length() != 10) {
            throw new IllegalArgumentException("md5 value in file name of zip should be 10 chars length");
        }
        return md5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String path, String tag, a mergedZipPref) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(path);
            File preloadDir = new File(g.b());
            if (!preloadDir.exists() && !preloadDir.mkdirs()) {
                Logger.e("CacheMerger", "cannot mkdir for file:" + preloadDir, new Object[0]);
                if (inputStream != null) {
                    try {
                        return;
                    } catch (IOException e) {
                        return;
                    }
                }
                return;
            }
            if (FileUtil.unpackToOverrideInDir(inputStream, preloadDir)) {
                mergedZipPref.b(tag);
                d.a(mergedZipPref, "merged_zip");
                com.youzan.spiderman.b.f.a().d();
            }
            inputStream.close();
            inputStream = null;
        } catch (IOException e2) {
            Logger.e("CacheMerger", e2);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: compiled from: CacheMerger.java */
    public static class a {
        private Set<String> a;

        public a() {
            this.a = null;
            this.a = new HashSet();
        }

        boolean a(String tag) {
            return this.a.contains(tag);
        }

        void b(String tag) {
            this.a.add(tag);
        }
    }
}
