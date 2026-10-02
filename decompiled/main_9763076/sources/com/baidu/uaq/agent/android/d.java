package com.baidu.uaq.agent.android;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.baidu.uaq.agent.android.util.h;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: AndroidAgentImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d implements b {
    private final Context j;
    private f k;
    private com.baidu.uaq.agent.android.harvest.bean.c l;
    private com.baidu.uaq.agent.android.harvest.bean.a m;
    private com.baidu.uaq.agent.android.crashes.d n;
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private static final UAQ AGENT = UAQ.getInstance();

    public d(Context unknownContext) throws c {
        if (TextUtils.isEmpty(AGENT.getConfig().getAPIKey())) {
            LOG.E("License key invalid cannot start.");
            throw new c("This license key is null");
        }
        this.j = b(unknownContext);
        this.k = new f(this.j);
        if (!AGENT.getConfig().getAPIKey().equals(this.k.v())) {
            LOG.E("License key has changed. Clearing saved state.");
            this.k.clear();
            this.k.d(AGENT.getConfig().getDataReportLimit());
            this.k.e(System.currentTimeMillis());
        }
        if (AGENT.getConfig().isNativeControlDRP() && AGENT.getConfig().getDataReportPeriod() != this.k.getDataReportPeriod()) {
            this.k.c(AGENT.getConfig().getDataReportPeriod());
            this.k.k();
        }
        this.k.b(AGENT.getConfig().getAPIKey());
        this.n = new com.baidu.uaq.agent.android.crashes.g(this.j);
    }

    public static void a(Context context) {
        try {
            a.a(new d(context));
            a.start();
        } catch (c e) {
            LOG.a("Failed to initialize the agent: ", e);
        }
    }

    @Override // com.baidu.uaq.agent.android.b
    public void start() {
        if (AGENT.getConfig().isReportCrashes()) {
            com.baidu.uaq.agent.android.crashes.c.a(this.n);
        }
    }

    @Override // com.baidu.uaq.agent.android.b
    public void shutdown() {
        g.stop();
    }

    @Override // com.baidu.uaq.agent.android.b
    public String g() {
        return h.i(this.j);
    }

    @Override // com.baidu.uaq.agent.android.b
    public String h() {
        return h.h(this.j);
    }

    @Override // com.baidu.uaq.agent.android.b
    public com.baidu.uaq.agent.android.harvest.bean.c e() {
        if (this.l != null) {
            return this.l;
        }
        com.baidu.uaq.agent.android.harvest.bean.c info = new com.baidu.uaq.agent.android.harvest.bean.c();
        info.g("Android");
        info.h(Build.VERSION.RELEASE);
        info.n(Build.VERSION.INCREMENTAL);
        info.j(Build.MODEL);
        info.k("AndroidAgent");
        info.l(a.getVersion() + "." + a.b());
        info.i(Build.MANUFACTURER);
        if (AGENT.getConfig().isUsePersistentUUID()) {
            info.m(com.baidu.uaq.agent.android.util.c.g(this.j));
        } else {
            info.m(com.baidu.uaq.agent.android.util.c.f(this.j));
        }
        info.o(System.getProperty("os.arch"));
        info.p(System.getProperty("java.vm.version"));
        info.q(com.baidu.uaq.agent.android.util.c.e(this.j).name().toLowerCase());
        info.r(AGENT.getConfig().getCuid());
        this.l = info;
        return this.l;
    }

    @Override // com.baidu.uaq.agent.android.b
    public com.baidu.uaq.agent.android.harvest.bean.a f() {
        if (this.m != null) {
            return this.m;
        }
        String packageName = this.j.getPackageName();
        PackageManager packageManager = this.j.getPackageManager();
        String appVersion = Constants.MAIN_VERSION_TAG;
        String appName = Constants.MAIN_VERSION_TAG;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            if (packageInfo != null && packageInfo.versionName != null && packageInfo.versionName.length() > 0) {
                appVersion = packageInfo.versionName;
            }
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
            if (info != null) {
                appName = packageManager.getApplicationLabel(info).toString();
            } else {
                appName = packageName;
            }
        } catch (PackageManager.NameNotFoundException | SecurityException e) {
            LOG.a("Caught error while getApplicationInformation: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        this.m = new com.baidu.uaq.agent.android.harvest.bean.a(appName, appVersion, packageName);
        return this.m;
    }

    @Override // com.baidu.uaq.agent.android.b
    public com.baidu.uaq.agent.android.harvest.bean.d i() {
        com.baidu.uaq.agent.android.harvest.bean.d envInfo = new com.baidu.uaq.agent.android.harvest.bean.d();
        long[] free = new long[2];
        StatFs rootStatFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        StatFs externalStatFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        try {
            free[0] = rootStatFs.getAvailableBlocksLong() * rootStatFs.getBlockSizeLong();
            free[1] = externalStatFs.getAvailableBlocksLong() * rootStatFs.getBlockSizeLong();
        } catch (Exception e) {
            LOG.a("Caught error while getEnvironmentInformation: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        if (free[0] < 0) {
            free[0] = 0;
        }
        if (free[1] < 0) {
            free[1] = 0;
        }
        envInfo.a(free);
        envInfo.i(0L);
        envInfo.setOrientation(this.j.getResources().getConfiguration().orientation);
        envInfo.s(h.h(this.j));
        envInfo.t(g());
        return envInfo;
    }

    private static Context b(Context context) {
        if (!(context instanceof Application)) {
            return context.getApplicationContext();
        }
        return context;
    }
}
