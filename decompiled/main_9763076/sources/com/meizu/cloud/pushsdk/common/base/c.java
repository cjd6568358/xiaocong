package com.meizu.cloud.pushsdk.common.base;

import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c implements com.meizu.cloud.pushsdk.common.b.c.a {
    private String a;
    private BufferedWriter b;
    private b c = new b("lo");

    public c(String str) {
        this.a = str;
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00ba  */
    private synchronized void a() throws IOException {
        boolean z = false;
        synchronized (this) {
            if (!TextUtils.isEmpty(this.a)) {
                File file = new File(this.a);
                if (!file.exists() && !file.mkdirs()) {
                    Log.e("EncryptLogger", "create dir " + this.a + " failed!");
                } else {
                    File file2 = new File(file, "logs_v2.txt");
                    if (!file2.exists() && !file2.createNewFile()) {
                        Log.e("EncryptLogger", "create new file logs_v2.txt failed!");
                    } else {
                        if (file2 == null || file2.length() < 31457280) {
                            z = true;
                        } else {
                            String parent = file2.getParent();
                            File file3 = new File(parent, "logs_v2_old.txt");
                            if (!file3.exists() ? !file2.renameTo(new File(parent, "logs_v2_old.txt")) : !(file3.delete() && file2.renameTo(new File(parent, "logs_v2_old.txt")))) {
                                z = true;
                            }
                        }
                        this.b = new BufferedWriter(new FileWriter(file2, z));
                    }
                }
            }
        }
    }

    private synchronized void b() {
        if (this.b != null) {
            try {
                this.b.close();
            } catch (IOException e) {
            }
        }
    }

    @Override // com.meizu.cloud.pushsdk.common.b.c.a
    public void a(com.meizu.cloud.pushsdk.common.b.c.a.EnumC0033a enumC0033a, String str, String str2) {
        String str3;
        try {
            a();
            if (this.b != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("/");
                if (enumC0033a == com.meizu.cloud.pushsdk.common.b.c.a.EnumC0033a.DEBUG) {
                    str3 = "D";
                } else if (enumC0033a == com.meizu.cloud.pushsdk.common.b.c.a.EnumC0033a.INFO) {
                    str3 = "I";
                } else {
                    str3 = enumC0033a == com.meizu.cloud.pushsdk.common.b.c.a.EnumC0033a.WARN ? "W" : "E";
                }
                sb.append(str3);
                sb.append(": ");
                sb.append(str2);
                this.b.append((CharSequence) this.c.a(sb.toString().getBytes(Charset.forName(HTTP.UTF_8))));
                this.b.append((CharSequence) "\r\n");
                this.b.flush();
            }
        } catch (Exception e) {
        } finally {
            b();
        }
    }
}
