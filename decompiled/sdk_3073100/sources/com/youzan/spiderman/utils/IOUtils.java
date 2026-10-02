package com.youzan.spiderman.utils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class IOUtils {
    public static void writeStringToFile(String fileName, String string) throws IOException {
        FileWriter out = new FileWriter(fileName);
        try {
            out.write(string);
        } finally {
            out.close();
        }
    }

    public static BufferedInputStream openFile(File file) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        return in;
    }

    public static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                Logger.e("IOUtils", e);
            }
        }
    }
}
