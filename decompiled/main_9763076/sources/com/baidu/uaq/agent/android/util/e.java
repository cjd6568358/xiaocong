package com.baidu.uaq.agent.android.util;

import android.content.Context;
import com.baidu.uaq.agent.android.customtransmission.APMUploadConfigure;
import com.baidu.uaq.agent.android.logging.a;
import com.baidu.uaq.agent.android.logging.b;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: compiled from: MultiLogPersistentUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private static final a LOG = b.bg();
    private static String cH = null;
    private final ArrayList<String> cL = new ArrayList<>();
    private Context j;
    private final String uploadName;

    public e(Context context, String uploadName) {
        this.j = context;
        this.uploadName = uploadName;
        bz();
    }

    private void a(String uploadName, String log, File defaultFile) {
        if (uploadName.equals(APMUploadConfigure.APMUPLOADNAME)) {
            b(log, defaultFile);
        } else {
            a(log, defaultFile);
        }
    }

    public void e(String uploadName, String log) {
        File defaultFile = U(uploadName);
        if (defaultFile == null) {
            return;
        }
        if (defaultFile.length() + ((long) log.length()) <= 10240) {
            a(uploadName, log, defaultFile);
            return;
        }
        if (this.cL.size() < 10) {
            a(defaultFile, log);
            return;
        }
        Collections.sort(this.cL);
        String fileName = this.cL.remove(0);
        LOG.E("expire with file:  " + fileName);
        File fileToDelete = new File(fileName);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
        a(defaultFile, log);
    }

    private void a(File defaultFile, String log) {
        String defaultFilePath = defaultFile.getAbsolutePath();
        int endIndex = defaultFilePath.lastIndexOf(File.separator) + 1;
        if (endIndex < 0 || endIndex > defaultFilePath.length()) {
            LOG.warning("in toPersistentFile, StringIndexOutOfBoundsException happened!");
            return;
        }
        String targetFilePath = defaultFilePath.substring(0, endIndex) + this.uploadName + "_" + System.currentTimeMillis();
        defaultFile.renameTo(new File(targetFilePath));
        this.cL.add(targetFilePath);
        a(this.uploadName, log, defaultFile);
    }

    private void a(String log, File defaultFile) {
        try {
            FileOutputStream os = new FileOutputStream(defaultFile, true);
            DataOutputStream dos = new DataOutputStream(os);
            LOG.E("writeToDefaultCustomFile log size:" + log.length());
            dos.writeLong(log.length());
            dos.writeChars(log);
            dos.flush();
            dos.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
    }

    private void b(String log, File defaultFile) {
        try {
            FileOutputStream os = new FileOutputStream(defaultFile, true);
            os.write(log.getBytes());
            os.write(",".getBytes());
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
    }

    public boolean R(String newestFile) {
        File f = new File(newestFile);
        this.cL.remove(newestFile);
        if (f.exists()) {
            return f.delete();
        }
        return true;
    }

    public ArrayList<String> bx() {
        Collections.sort(this.cL);
        return this.cL;
    }

    public String S(String filePath) throws Throwable {
        String string = null;
        FileReader reader = null;
        try {
            try {
                File f = new File(filePath);
                if (f.exists()) {
                    FileReader reader2 = new FileReader(f);
                    try {
                        StringBuilder result = new StringBuilder();
                        char[] block = new char[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                        while (true) {
                            int length = reader2.read(block);
                            if (length == -1) {
                                break;
                            }
                            result.append(block, 0, length);
                        }
                        string = result.toString();
                        if (reader2 != null) {
                            try {
                                reader2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e2) {
                        e = e2;
                        reader = reader2;
                        e.printStackTrace();
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        reader = reader2;
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } else {
                    if (!filePath.contains(this.uploadName)) {
                        LOG.error("log file not exists: " + filePath);
                    }
                    if (0 != 0) {
                        try {
                            reader.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e6) {
            e = e6;
        }
        return string;
    }

    public ArrayList<String> T(String filePath) throws Throwable {
        FileInputStream inputStream = null;
        ArrayList<String> strList = new ArrayList<>();
        try {
            try {
                FileInputStream inputStream2 = new FileInputStream(filePath);
                try {
                    int size = inputStream2.available();
                    int offset = 0;
                    if (size > 0) {
                        while (offset < size) {
                            DataInputStream dataInputStream = new DataInputStream(inputStream2);
                            long fileSize = dataInputStream.readLong();
                            char[] dataChars = new char[(int) fileSize];
                            for (int i = 0; i < fileSize; i++) {
                                dataChars[i] = dataInputStream.readChar();
                            }
                            String fileContent = new String(dataChars, 0, (int) fileSize);
                            strList.add(fileContent);
                            offset = (int) (((long) (offset + 8)) + fileSize);
                        }
                    }
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    inputStream = inputStream2;
                } catch (EOFException e2) {
                    inputStream = inputStream2;
                    LOG.E("read end");
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } catch (IOException e4) {
                    inputStream = inputStream2;
                    LOG.E("read end");
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStream2;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (EOFException e7) {
        } catch (IOException e8) {
        }
        return strList;
    }

    public File U(String name) {
        File file = new File(by() + name + "_ini");
        if (!file.exists() && !V(name)) {
            return null;
        }
        return file;
    }

    private String by() {
        if (cH == null) {
            cH = this.j.getFilesDir().getAbsolutePath() + "/apm/";
        }
        return cH;
    }

    private boolean V(String name) {
        String logPath = by();
        try {
            File logDir = new File(logPath);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            File logFile = new File(logPath + name + "_ini");
            if (!logFile.exists()) {
                logFile.createNewFile();
                if (!this.cL.contains(logFile.getAbsolutePath())) {
                    this.cL.add(logFile.getAbsolutePath());
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
            return false;
        }
    }

    private void bz() {
        if (!this.cL.isEmpty()) {
            this.cL.clear();
        }
        File logDir = new File(by());
        V(this.uploadName);
        String[] list = logDir.list();
        if (list != null && list.length != 0) {
            for (String item : list) {
                if (!this.cL.contains(by() + item) && item.contains(this.uploadName)) {
                    this.cL.add(by() + item);
                }
            }
            Collections.reverse(this.cL);
        }
    }
}
