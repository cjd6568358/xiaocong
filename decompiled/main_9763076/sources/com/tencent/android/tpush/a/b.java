package com.tencent.android.tpush.a;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class b implements Runnable {
    final /* synthetic */ List a;

    b(List list) {
        this.a = list;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0103 A[Catch: Exception -> 0x0123, TRY_LEAVE, TryCatch #7 {Exception -> 0x0123, blocks: (B:26:0x00fc, B:28:0x0103), top: B:68:0x00fc }] */
    @Override // java.lang.Runnable
    public void run() throws Throwable {
        String str;
        BufferedWriter bufferedWriter = null;
        try {
            try {
                try {
                    String strC = a.c();
                    if (strC == null) {
                        try {
                            this.a.clear();
                            if (0 != 0) {
                                bufferedWriter.close();
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            Log.e("XGLogger", "close file stream error", e);
                            return;
                        }
                    }
                    String str2 = strC + File.separator + "log";
                    String str3 = str2 + "-" + com.tencent.android.tpush.service.e.b.a() + "_1.txt";
                    File file = new File(str3);
                    file.getParentFile().mkdirs();
                    File file2 = file;
                    String str4 = str3;
                    int i = 2;
                    while (true) {
                        if (!file2.exists()) {
                            str = str4;
                            break;
                        }
                        str4 = str2 + "-" + com.tencent.android.tpush.service.e.b.a() + "_" + i + ".txt";
                        file2 = new File(str4);
                        if (i > 10) {
                            Log.w("XGLogger", "Unexpected error here, so many existed error file.");
                            str = str4;
                            break;
                        }
                        i++;
                    }
                    Log.v("XGLogger", "Write log file: " + file2.getName());
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(str));
                    try {
                        Iterator it = this.a.iterator();
                        while (it.hasNext()) {
                            bufferedWriter2.write(((String) it.next()) + "\n");
                        }
                        try {
                            this.a.clear();
                            if (bufferedWriter2 != null) {
                                bufferedWriter2.close();
                            }
                        } catch (Exception e2) {
                            Log.e("XGLogger", "close file stream error", e2);
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                        bufferedWriter = bufferedWriter2;
                        e.printStackTrace();
                        this.a.clear();
                        if (bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                    } catch (Exception e4) {
                        e = e4;
                        bufferedWriter = bufferedWriter2;
                        Log.e("XGLogger", "write logs to file error", e);
                        try {
                            this.a.clear();
                            if (bufferedWriter != null) {
                                bufferedWriter.close();
                            }
                        } catch (Exception e5) {
                            Log.e("XGLogger", "close file stream error", e5);
                        }
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        try {
                            this.a.clear();
                            if (bufferedWriter != null) {
                                bufferedWriter.close();
                            }
                        } catch (Exception e6) {
                            Log.e("XGLogger", "close file stream error", e6);
                        }
                        throw th;
                    }
                    a.d();
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException e7) {
                e = e7;
            } catch (Exception e8) {
                e = e8;
            }
            this.a.clear();
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (Exception e9) {
            Log.e("XGLogger", "close file stream error", e9);
        }
        e.printStackTrace();
        a.d();
    }
}
