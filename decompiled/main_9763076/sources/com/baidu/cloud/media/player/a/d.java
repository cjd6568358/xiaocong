package com.baidu.cloud.media.player.a;

import android.content.Context;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.io.PrintWriter;
import java.io.StringWriter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements Thread.UncaughtExceptionHandler {
    private static d a = new d();
    private Thread.UncaughtExceptionHandler b = null;
    private Context c = null;

    private d() {
    }

    public static d a() {
        return a;
    }

    public void a(Context context) {
        if (this.b == null) {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        if (this.c == null) {
            this.c = context.getApplicationContext();
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            printWriter.close();
            String string = stringWriter.toString();
            if (string.contains("com.baidu.cloud.media")) {
                b.a(this.c).c(string);
            }
        } catch (Exception e) {
            Log.d("SDKCrashHandler", Constants.MAIN_VERSION_TAG + e.getMessage());
        }
        if (this.b.equals(this)) {
            return;
        }
        this.b.uncaughtException(thread, th);
    }
}
