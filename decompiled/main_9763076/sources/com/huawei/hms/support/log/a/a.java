package com.huawei.hms.support.log.a;

import android.content.Context;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: FileLogNode.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a implements com.huawei.hms.support.log.c {
    private File a;

    @Override // com.huawei.hms.support.log.c
    public void a(Context context, String str) {
        File externalFilesDir;
        if (context == null || str == null || str.isEmpty()) {
            Log.e("FileLogNode", "Failed to initialize the file logger, parameter error.");
            return;
        }
        if (this.a == null && (externalFilesDir = context.getExternalFilesDir(null)) != null) {
            File file = new File(externalFilesDir, "Log");
            if (file.isDirectory() || file.mkdirs()) {
                this.a = new File(file, str + ".log");
                this.a.setReadable(true);
                this.a.setWritable(true);
                this.a.setExecutable(false, false);
                return;
            }
        }
        Log.e("FileLogNode", "Failed to initialize the file logger.");
    }

    @Override // com.huawei.hms.support.log.c
    public void a(String str, int i, String str2, String str3) throws Throwable {
        if (this.a != null && str != null) {
            String str4 = str + '\n';
            if (a(str4)) {
                b(str4);
            }
        }
    }

    private boolean a(String str) {
        if (this.a.length() + ((long) str.length()) > 524288) {
            if (!this.a.renameTo(new File(this.a.getPath() + ".bak"))) {
                Log.w("FileLogNode", "Failed to backup the log file.");
                return false;
            }
        }
        return true;
    }

    private void b(String str) throws Throwable {
        BufferedOutputStream bufferedOutputStream;
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        FileOutputStream fileOutputStream2;
        OutputStreamWriter outputStreamWriter2 = null;
        outputStreamWriter2 = null;
        outputStreamWriter2 = null;
        bufferedOutputStream = null;
        outputStreamWriter2 = null;
        outputStreamWriter2 = null;
        BufferedOutputStream bufferedOutputStream2 = null;
        outputStreamWriter2 = null;
        outputStreamWriter2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(this.a, true);
                try {
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                    try {
                        outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, HTTP.UTF_8);
                        try {
                            outputStreamWriter.write(str);
                            outputStreamWriter.flush();
                            a(outputStreamWriter);
                            a(bufferedOutputStream);
                            a(fileOutputStream);
                        } catch (FileNotFoundException e) {
                            bufferedOutputStream2 = bufferedOutputStream;
                            fileOutputStream2 = fileOutputStream;
                            try {
                                Log.w("FileLogNode", "Exception when writing the log file.");
                                a(outputStreamWriter);
                                a(bufferedOutputStream2);
                                a(fileOutputStream2);
                            } catch (Throwable th) {
                                fileOutputStream = fileOutputStream2;
                                bufferedOutputStream = bufferedOutputStream2;
                                outputStreamWriter2 = outputStreamWriter;
                                th = th;
                                a(outputStreamWriter2);
                                a(bufferedOutputStream);
                                a(fileOutputStream);
                                throw th;
                            }
                        } catch (IOException e2) {
                            outputStreamWriter2 = outputStreamWriter;
                            Log.w("FileLogNode", "Exception when writing the log file.");
                            a(outputStreamWriter2);
                            a(bufferedOutputStream);
                            a(fileOutputStream);
                        } catch (Throwable th2) {
                            outputStreamWriter2 = outputStreamWriter;
                            th = th2;
                            a(outputStreamWriter2);
                            a(bufferedOutputStream);
                            a(fileOutputStream);
                            throw th;
                        }
                    } catch (FileNotFoundException e3) {
                        outputStreamWriter = null;
                        bufferedOutputStream2 = bufferedOutputStream;
                        fileOutputStream2 = fileOutputStream;
                    } catch (IOException e4) {
                    }
                } catch (FileNotFoundException e5) {
                    outputStreamWriter = null;
                    fileOutputStream2 = fileOutputStream;
                } catch (IOException e6) {
                    bufferedOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedOutputStream = null;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (FileNotFoundException e7) {
            outputStreamWriter = null;
            fileOutputStream2 = null;
        } catch (IOException e8) {
            bufferedOutputStream = null;
            fileOutputStream = null;
        } catch (Throwable th5) {
            th = th5;
            bufferedOutputStream = null;
            fileOutputStream = null;
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w("FileLogNode", "Exception when closing the closeable.");
            }
        }
    }

    /* JADX INFO: renamed from: com.huawei.hms.support.log.a.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: FileLogNode.java */
    public static class C0020a implements com.huawei.hms.support.log.c {
        private final com.huawei.hms.support.log.c a;
        private com.huawei.hms.support.log.c b;
        private final Executor c = Executors.newSingleThreadExecutor();

        public C0020a(com.huawei.hms.support.log.c cVar) {
            this.a = cVar;
        }

        @Override // com.huawei.hms.support.log.c
        public void a(Context context, String str) {
            this.c.execute(new b(this, context, str));
            if (this.b != null) {
                this.b.a(context, str);
            }
        }

        @Override // com.huawei.hms.support.log.c
        public void a(String str, int i, String str2, String str3) {
            this.c.execute(new c(this, str, i, str2, str3));
            if (this.b != null) {
                this.b.a(str, i, str2, str3);
            }
        }
    }
}
