package com.xiaocong.smarthome.httplib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ZIPUtils {
    public static void UnZipFolder(File zipFile, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFile.getPath()));
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry == null) {
                break;
            }
            String szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                File folder = new File(outPathString + File.separator + szName.substring(0, szName.length() - 1));
                folder.mkdirs();
            } else {
                File file = new File(outPathString + File.separator + szName);
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                while (true) {
                    int len = inZip.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
        if (zipFile.exists()) {
            zipFile.delete();
        }
    }
}
