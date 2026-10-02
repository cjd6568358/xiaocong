package com.tencent.android.tpush.service.e;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.service.cache.CacheManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private static volatile e a = null;
    private Context b;
    private Map c = new HashMap(10);
    private Map d = new HashMap(10);

    private e(Context context) {
        this.b = null;
        this.b = context.getApplicationContext();
        this.d.put(-1L, Constants.MAIN_VERSION_TAG);
    }

    public static e a(Context context) {
        if (a == null) {
            synchronized (e.class) {
                if (a == null) {
                    a = new e(context);
                }
            }
        }
        return a;
    }

    public String a(long j) {
        if (this.d.containsKey(Long.valueOf(j))) {
            return (String) this.d.get(Long.valueOf(j));
        }
        List<String> registerInfos = CacheManager.getRegisterInfos(this.b);
        if (registerInfos != null) {
            for (String str : registerInfos) {
                RegisterEntity registerInfoByPkgName = CacheManager.getRegisterInfoByPkgName(str);
                if (registerInfoByPkgName != null) {
                    this.d.put(Long.valueOf(registerInfoByPkgName.accessId), a(str));
                }
            }
        }
        return this.d.get(Long.valueOf(j)) == null ? Constants.MAIN_VERSION_TAG : (String) this.d.get(Long.valueOf(j));
    }

    public String a(String str) {
        if (str == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        if (this.c.containsKey(str)) {
            return (String) this.c.get(str);
        }
        List<PackageInfo> installedPackages = this.b.getPackageManager().getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo packageInfo : installedPackages) {
                if (str.equals(packageInfo.packageName)) {
                    this.c.put(str, packageInfo.versionName);
                    return packageInfo.versionName;
                }
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }
}
