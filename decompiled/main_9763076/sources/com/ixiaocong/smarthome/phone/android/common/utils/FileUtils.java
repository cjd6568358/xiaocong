package com.ixiaocong.smarthome.phone.android.common.utils;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FileUtils {
    public static String getFileFormat(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return Constants.MAIN_VERSION_TAG;
        }
        int point = fileName.lastIndexOf(46);
        return fileName.substring(point + 1);
    }

    public static boolean checkFileExists(Context context, String name) {
        if (!name.equals(Constants.MAIN_VERSION_TAG)) {
            File path = context.getFilesDir();
            File newPath = new File(path.toString() + name);
            boolean status = newPath.exists();
            XcLogger.i("checkFileExists", path.toString());
            return status;
        }
        return false;
    }
}
