package com.huawei.hms.c;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/* JADX INFO: compiled from: PackageManagerHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private final PackageManager a;

    /* JADX INFO: compiled from: PackageManagerHelper.java */
    public enum a {
        ENABLED,
        DISABLED,
        NOT_INSTALLED
    }

    public e(Context context) {
        this.a = context.getPackageManager();
    }

    public a a(String str) {
        a aVar;
        try {
            if (this.a.getApplicationInfo(str, 0).enabled) {
                aVar = a.ENABLED;
            } else {
                aVar = a.DISABLED;
            }
            return aVar;
        } catch (PackageManager.NameNotFoundException e) {
            return a.NOT_INSTALLED;
        }
    }

    public int b(String str) {
        try {
            PackageInfo packageInfo = this.a.getPackageInfo(str, 16);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
            return 0;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public String c(String str) {
        try {
            PackageInfo packageInfo = this.a.getPackageInfo(str, 16);
            if (packageInfo != null && packageInfo.versionName != null) {
                return packageInfo.versionName;
            }
            return Constants.MAIN_VERSION_TAG;
        } catch (PackageManager.NameNotFoundException e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public String d(String str) {
        byte[] bArrE = e(str);
        if (bArrE == null || bArrE.length == 0) {
            return null;
        }
        return b.b(f.a(bArrE), true);
    }

    private byte[] e(String str) {
        InputStream inputStreamA = null;
        try {
            try {
                PackageInfo packageInfo = this.a.getPackageInfo(str, 64);
                if (packageInfo != null && packageInfo.signatures.length > 0) {
                    inputStreamA = c.a(packageInfo.signatures[0].toByteArray());
                    byte[] encoded = CertificateFactory.getInstance("X.509").generateCertificate(inputStreamA).getEncoded();
                    c.a(inputStreamA);
                    return encoded;
                }
                c.a((InputStream) null);
            } catch (Throwable th) {
                c.a((InputStream) null);
                throw th;
            }
        } catch (PackageManager.NameNotFoundException | IOException | CertificateException e) {
            com.huawei.hms.support.log.a.d("PackageManagerHelper", "Failed to get application signature certificate fingerprint." + e.getMessage());
            c.a(inputStreamA);
        }
        com.huawei.hms.support.log.a.d("PackageManagerHelper", "Failed to get application signature certificate fingerprint.");
        return new byte[0];
    }

    public boolean a(String str, String str2) {
        try {
            PackageInfo packageInfo = this.a.getPackageInfo(str, 8);
            if (packageInfo == null || packageInfo.providers == null) {
                return false;
            }
            ProviderInfo[] providerInfoArr = packageInfo.providers;
            for (ProviderInfo providerInfo : providerInfoArr) {
                if (str2.equals(providerInfo.authority)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean a(String str, String str2, String str3) {
        boolean zEqualsIgnoreCase = false;
        PackageInfo packageArchiveInfo = this.a.getPackageArchiveInfo(str, 64);
        if (packageArchiveInfo != null && packageArchiveInfo.signatures.length > 0 && str2.equals(packageArchiveInfo.packageName)) {
            InputStream inputStreamA = null;
            try {
                try {
                    inputStreamA = c.a(packageArchiveInfo.signatures[0].toByteArray());
                    zEqualsIgnoreCase = str3.equalsIgnoreCase(b.b(f.a(CertificateFactory.getInstance("X.509").generateCertificate(inputStreamA).getEncoded()), true));
                } finally {
                    c.a(inputStreamA);
                }
            } catch (IOException | CertificateException e) {
                com.huawei.hms.support.log.a.d("PackageManagerHelper", "Failed to get application signature certificate fingerprint." + e.getMessage());
                c.a(inputStreamA);
            }
        }
        return zEqualsIgnoreCase;
    }
}
