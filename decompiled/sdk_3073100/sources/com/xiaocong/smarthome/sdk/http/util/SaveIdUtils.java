package com.xiaocong.smarthome.sdk.http.util;

import android.os.Environment;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SaveIdUtils {
    private static String LOG_PATH = Environment.getExternalStorageDirectory().getPath();

    public static void saveCrashInfo2File(String fileContent, String filePath, String fileName) {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                File dir = new File(LOG_PATH + filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(LOG_PATH + filePath + fileName);
                fos.write(fileContent.getBytes());
                fos.close();
            }
        } catch (Exception e) {
        }
    }

    public static String getFileValue(String key, String path) {
        try {
            if (TextUtils.isEmpty(readeContent(path))) {
                return "";
            }
            Map<String, String> params = (Map) JSON.parse(readeContent(path));
            if (!key.equals("fdd2dcmd0")) {
                return "";
            }
            String content = params.get("fdd2dcmd0");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean checkFileExists(String name) {
        try {
            if (!name.equals("")) {
                File path = Environment.getExternalStorageDirectory();
                File newPath = new File(path.toString() + name);
                return newPath.exists();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String readeContent(String path) {
        StringBuilder sb = new StringBuilder("");
        if (Environment.getExternalStorageState().equals("mounted")) {
            try {
                FileInputStream input = new FileInputStream(LOG_PATH + path + "fda23d0");
                byte[] temp = new byte[3072];
                while (true) {
                    int len = input.read(temp);
                    if (len <= 0) {
                        break;
                    }
                    sb.append(new String(temp, 0, len));
                }
                input.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }
}
