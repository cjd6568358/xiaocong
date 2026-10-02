package skin.support.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinFileUtils {
    public static String getSkinDir(Context context) {
        File skinDir = new File(getCacheDir(context), "skins");
        if (!skinDir.exists()) {
            skinDir.mkdirs();
        }
        return skinDir.getAbsolutePath();
    }

    private static String getCacheDir(Context context) {
        File cacheDir;
        return (Environment.getExternalStorageState().equals("mounted") && (cacheDir = context.getExternalCacheDir()) != null && (cacheDir.exists() || cacheDir.mkdirs())) ? cacheDir.getAbsolutePath() : context.getCacheDir().getAbsolutePath();
    }

    public static boolean isFileExists(String path) {
        return !TextUtils.isEmpty(path) && new File(path).exists();
    }
}
