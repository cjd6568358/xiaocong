package com.facebook.common.logging;

import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.io.PrintWriter;
import java.io.StringWriter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FLogDefaultLoggingDelegate implements LoggingDelegate {
    public static final FLogDefaultLoggingDelegate sInstance = new FLogDefaultLoggingDelegate();
    private String mApplicationTag = "unknown";
    private int mMinimumLoggingLevel = 5;

    public static FLogDefaultLoggingDelegate getInstance() {
        return sInstance;
    }

    private FLogDefaultLoggingDelegate() {
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public boolean isLoggable(int level) {
        return this.mMinimumLoggingLevel <= level;
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void v(String tag, String msg) {
        println(2, tag, msg);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void d(String tag, String msg) {
        println(3, tag, msg);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void d(String tag, String msg, Throwable tr) {
        println(3, tag, msg, tr);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void i(String tag, String msg) {
        println(4, tag, msg);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void w(String tag, String msg) {
        println(5, tag, msg);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void w(String tag, String msg, Throwable tr) {
        println(5, tag, msg, tr);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void e(String tag, String msg) {
        println(6, tag, msg);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void e(String tag, String msg, Throwable tr) {
        println(6, tag, msg, tr);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void wtf(String tag, String msg) {
        println(6, tag, msg);
    }

    @Override // com.facebook.common.logging.LoggingDelegate
    public void wtf(String tag, String msg, Throwable tr) {
        println(6, tag, msg, tr);
    }

    private void println(int priority, String tag, String msg) {
        Log.println(priority, prefixTag(tag), msg);
    }

    private void println(int priority, String tag, String msg, Throwable tr) {
        Log.println(priority, prefixTag(tag), getMsg(msg, tr));
    }

    private String prefixTag(String tag) {
        if (this.mApplicationTag != null) {
            return this.mApplicationTag + ":" + tag;
        }
        return tag;
    }

    private static String getMsg(String msg, Throwable tr) {
        return msg + '\n' + getStackTraceString(tr);
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        return sw.toString();
    }
}
