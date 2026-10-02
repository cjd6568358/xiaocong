package com.youzan.spiderman.utils;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class FileUtil {
    public static boolean checkFileExists(String path) {
        if (StringUtils.isNotEmpty(path)) {
            File newFile = new File(path);
            return newFile.exists() && newFile.isFile();
        }
        throw new IllegalArgumentException("Path cannot be empty");
    }

    public static void renameToFile(String oldName, String newName) {
        File oldFile = new File(oldName);
        oldFile.renameTo(new File(newName));
    }

    public static String getFileContent(File file) throws Throwable {
        String str;
        BufferedInputStream in = null;
        try {
            if (file.exists()) {
                BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    int totalLen = (int) file.length();
                    byte[] buffer = new byte[totalLen];
                    for (int readCount = 0; readCount < totalLen; readCount += in2.read(buffer, readCount, Math.min(totalLen - readCount, 4096))) {
                    }
                    str = new String(buffer, AsyncHttpResponseHandler.DEFAULT_CHARSET);
                    IOUtils.closeSilently(in2);
                } catch (Throwable th) {
                    th = th;
                    in = in2;
                    IOUtils.closeSilently(in);
                    throw th;
                }
            } else {
                str = "";
                IOUtils.closeSilently(null);
            }
            return str;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static String getFileContent(String filePath) throws IOException {
        return getFileContent(new File(filePath));
    }

    public static boolean writeStreamToFile(File file, InputStream inputStream) throws Throwable {
        boolean z = false;
        OutputStream outputStream = null;
        byte[] buf = new byte[4096];
        try {
            try {
                if (file.exists() || file.createNewFile()) {
                    if (inputStream != null) {
                        OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(file));
                        while (true) {
                            try {
                                int n = inputStream.read(buf, 0, 4096);
                                if (n == -1) {
                                    break;
                                }
                                outputStream2.write(buf, 0, n);
                            } catch (IOException e) {
                                e = e;
                                outputStream = outputStream2;
                                e.printStackTrace();
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                outputStream = outputStream2;
                                if (outputStream != null) {
                                    try {
                                        outputStream.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                        outputStream2.close();
                        outputStream = null;
                    }
                    z = true;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                } else {
                    if (0 != 0) {
                        try {
                            outputStream.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e10) {
            e = e10;
        }
        return z;
    }

    public static boolean writeContentToFile(File file, String content) throws Throwable {
        boolean z = false;
        PrintWriter writer = null;
        try {
            try {
                if (file.exists() || file.createNewFile()) {
                    PrintWriter writer2 = new PrintWriter(new FileOutputStream(file));
                    try {
                        writer2.println(content);
                        writer2.close();
                        z = true;
                        if (writer2 != null) {
                            writer2.close();
                        }
                        writer = writer2;
                    } catch (IOException e) {
                        e = e;
                        writer = writer2;
                        e.printStackTrace();
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        writer = writer2;
                        if (writer != null) {
                            writer.close();
                        }
                        throw th;
                    }
                } else if (0 != 0) {
                    writer.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
        return z;
    }

    public static boolean writeContentToFile(String filePath, String content) {
        File file = new File(filePath);
        return writeContentToFile(file, content);
    }

    public static void createParentDirIfNeeded(File file) throws IOException {
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            createParentDirIfNeeded(parentDir.getParentFile());
            createDir(parentDir);
        }
    }

    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            createParentDirIfNeeded(file);
            file.createNewFile();
        }
        return file;
    }

    public static void createDir(File dir) throws IOException {
        if (dir == null) {
            throw new IllegalArgumentException("dir cannot be null");
        }
        if ((!dir.exists() || !dir.isDirectory()) && !dir.mkdirs()) {
            throw new IOException(String.format("Failed to create dir path %s", dir.getAbsolutePath()));
        }
    }

    public static void deleteFile(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static boolean unpackToOverrideInDir(InputStream inputStream, File destDir) throws Throwable {
        boolean z = false;
        ZipInputStream zis = null;
        try {
            try {
                ZipInputStream zis2 = new ZipInputStream(new BufferedInputStream(inputStream));
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        ZipEntry ze = zis2.getNextEntry();
                        if (ze == null) {
                            break;
                        }
                        String filename = ze.getName();
                        File destFile = new File(destDir, filename);
                        if (!destFile.exists()) {
                            if (ze.isDirectory()) {
                                File fmd = new File(destDir, filename);
                                fmd.mkdirs();
                            } else {
                                FileOutputStream fout = new FileOutputStream(destFile);
                                while (true) {
                                    int count = zis2.read(buffer);
                                    if (count == -1) {
                                        break;
                                    }
                                    fout.write(buffer, 0, count);
                                }
                                fout.close();
                                zis2.closeEntry();
                            }
                        }
                    }
                    zis2.close();
                    zis = null;
                    z = true;
                    if (0 != 0) {
                        try {
                            zis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    zis = zis2;
                    Logger.e("error", e);
                    if (zis != null) {
                        try {
                            zis.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    zis = zis2;
                    if (zis != null) {
                        try {
                            zis.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
        return z;
    }
}
