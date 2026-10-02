package com.tencent.android.tpush.stat.b;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends h {
    public b(Context context, int i) {
        super(context, i);
    }

    @Override // com.tencent.android.tpush.stat.b.h
    public int a() {
        return 2;
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected boolean b() {
        return com.tencent.android.tpush.stat.a.h.a(this.b, "android.permission.WRITE_EXTERNAL_STORAGE") && Environment.getExternalStorageState().equals("mounted");
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected String c() {
        String str;
        String str2 = null;
        synchronized (this) {
            this.a.b("read mid from InternalStorage");
            File file = new File(Environment.getExternalStorageDirectory(), e());
            if (file != null) {
                try {
                    Iterator it = a.a(file).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str = null;
                            break;
                        }
                        String[] strArrSplit = ((String) it.next()).split(",");
                        if (strArrSplit.length == 2 && strArrSplit[0].equals(f())) {
                            this.a.b("read mid from InternalStorage:" + strArrSplit[1]);
                            str = strArrSplit[1];
                            break;
                        }
                    }
                    str2 = str;
                } catch (IOException e) {
                    this.a.d(e.toString());
                }
            }
        }
        return str2;
    }

    /* JADX WARN: Code duplicated, block: B:38:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12, types: [java.io.BufferedWriter] */
    @Override // com.tencent.android.tpush.stat.b.h
    protected void a(String str) {
        synchronized (this) {
            this.a.b("write mid to InternalStorage");
            a.a(Environment.getExternalStorageDirectory() + "/" + d());
            BufferedWriter externalStorageDirectory = Environment.getExternalStorageDirectory();
            File file = new File(externalStorageDirectory, e());
            if (file != null) {
                try {
                    try {
                        externalStorageDirectory = new BufferedWriter(new FileWriter(file));
                        try {
                            externalStorageDirectory.write(f() + "," + str);
                            externalStorageDirectory.write("\n");
                            if (externalStorageDirectory != 0) {
                                try {
                                    externalStorageDirectory.close();
                                } catch (Exception e) {
                                }
                            }
                        } catch (IOException e2) {
                            e = e2;
                            this.a.d(e.toString());
                            if (externalStorageDirectory != 0) {
                                try {
                                    externalStorageDirectory.close();
                                } catch (Exception e3) {
                                }
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (externalStorageDirectory != 0) {
                            try {
                                externalStorageDirectory.close();
                            } catch (Exception e4) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e = e5;
                    externalStorageDirectory = 0;
                } catch (Throwable th2) {
                    th = th2;
                    externalStorageDirectory = 0;
                    if (externalStorageDirectory != 0) {
                        externalStorageDirectory.close();
                    }
                    throw th;
                }
            }
        }
    }
}
