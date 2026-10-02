package com.ixiaocong.smarthome.phone.android.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    public static String LOG_PATH = Environment.getExternalStorageDirectory().getPath() + "/ixiaocong/crash/";
    private static AppCrashHandler mInstance = null;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mSystemDefaultHandler = null;
    private Map<String, String> infos = new HashMap();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    private DateFormat formatter_ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private AppCrashHandler() {
    }

    public static AppCrashHandler getInstance() {
        if (mInstance == null) {
            mInstance = new AppCrashHandler();
        }
        return mInstance;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable ex) {
        XcLogger.e("AAAAA", LOG_PATH);
        if (XcLogger.DEBUG) {
            handleException(ex);
            if (this.mSystemDefaultHandler != null) {
                this.mSystemDefaultHandler.uncaughtException(thread, ex);
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    public void init(Context context) {
        this.mContext = context;
        this.mSystemDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.ixiaocong.smarthome.phone.android.crash.AppCrashHandler$1] */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() { // from class: com.ixiaocong.smarthome.phone.android.crash.AppCrashHandler.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                ToastUtils.showShort(AppCrashHandler.this.mContext, "很抱歉,小葱出现异常,即将退出");
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(this.mContext);
        saveCrashInfo2File(ex);
        return true;
    }

    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 1);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + Constants.MAIN_VERSION_TAG;
                this.infos.put("versionName", versionName);
                this.infos.put("versionCode", versionCode);
            }
            String OSVersion = Build.VERSION.RELEASE;
            String Phone_model = Build.MODEL;
            this.infos.put("安卓系统版本", OSVersion);
            this.infos.put("手机型号", Phone_model);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "：" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String time = this.formatter.format(new Date());
            String fileName = "crash_" + time + ".log";
            if (Environment.getExternalStorageState().equals("mounted")) {
                File dir = new File(LOG_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(LOG_PATH + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
