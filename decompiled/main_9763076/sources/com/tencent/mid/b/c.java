package com.tencent.mid.b;

import android.content.Context;
import android.os.Environment;
import com.tencent.mid.util.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends f {
    public c(Context context, int i) {
        super(context, i);
    }

    @Override // com.tencent.mid.b.f
    public int a() {
        return 2;
    }

    @Override // com.tencent.mid.b.f
    protected void a(a aVar) {
    }

    @Override // com.tencent.mid.b.f
    protected void a(String str) {
        BufferedWriter bufferedWriter;
        synchronized (this) {
            b.b("write mid to InternalStorage");
            b.a(Environment.getExternalStorageDirectory() + "/" + e());
            File file = new File(Environment.getExternalStorageDirectory(), f());
            if (file != null) {
                BufferedWriter bufferedWriter2 = null;
                try {
                    bufferedWriter = new BufferedWriter(new FileWriter(file));
                    try {
                        bufferedWriter.write(h() + "," + str);
                        bufferedWriter.write("\n");
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (Exception e) {
                            }
                        }
                    } catch (IOException e2) {
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (Exception e3) {
                            }
                        }
                    } catch (Throwable th) {
                        bufferedWriter2 = bufferedWriter;
                        th = th;
                        if (bufferedWriter2 != null) {
                            try {
                                bufferedWriter2.close();
                            } catch (Exception e4) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    bufferedWriter = null;
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
    }

    @Override // com.tencent.mid.b.f
    protected boolean b() {
        try {
            return Util.checkPermission(this.c, "android.permission.WRITE_EXTERNAL_STORAGE") && "mounted".equals(Environment.getExternalStorageState());
        } catch (Throwable th) {
            b.b("checkPermission " + th);
            return false;
        }
    }

    @Override // com.tencent.mid.b.f
    protected String c() {
        String str;
        String str2 = null;
        synchronized (this) {
            b.b("read mid from InternalStorage  version code = 4.06");
            File file = new File(Environment.getExternalStorageDirectory(), f());
            if (file != null) {
                try {
                    Iterator<String> it = b.a(file).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str = null;
                            break;
                        }
                        String[] strArrSplit = it.next().split(",");
                        if (strArrSplit.length == 2 && strArrSplit[0].equals(h())) {
                            b.b("read mid from InternalStorage:" + strArrSplit[1]);
                            str = strArrSplit[1];
                            break;
                        }
                    }
                    str2 = str;
                } catch (IOException e) {
                }
            }
        }
        return str2;
    }

    @Override // com.tencent.mid.b.f
    protected a d() {
        return null;
    }
}
