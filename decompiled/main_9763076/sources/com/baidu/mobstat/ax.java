package com.baidu.mobstat;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.jar.JarFile;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ax {
    private static volatile DexClassLoader a;
    private static volatile boolean b = false;

    public static Class<?> a(Context context, String str) {
        DexClassLoader dexClassLoaderA = a(context);
        if (dexClassLoaderA == null) {
            return null;
        }
        return dexClassLoaderA.loadClass(str);
    }

    private static synchronized DexClassLoader a(Context context) {
        DexClassLoader dexClassLoader = null;
        synchronized (ax.class) {
            if (a != null) {
                dexClassLoader = a;
            } else {
                File fileStreamPath = context.getFileStreamPath(".remote.jar");
                if (fileStreamPath == null || fileStreamPath.isFile()) {
                    if (!b(context, fileStreamPath.getAbsolutePath())) {
                        bd.a("remote jar version lower than min limit, need delete");
                        if (fileStreamPath.isFile()) {
                            fileStreamPath.delete();
                        }
                    } else if (!c(context, fileStreamPath.getAbsolutePath())) {
                        bd.a("remote jar md5 is not right, need delete");
                        if (fileStreamPath.isFile()) {
                            fileStreamPath.delete();
                        }
                    } else {
                        try {
                            a = new DexClassLoader(fileStreamPath.getAbsolutePath(), context.getDir("outdex", 0).getAbsolutePath(), null, context.getClassLoader());
                        } catch (Exception e) {
                            bd.a(e);
                        }
                        dexClassLoader = a;
                    }
                }
            }
        }
        return dexClassLoader;
    }

    private static boolean b(Context context, String str) throws Throwable {
        int iIntValue;
        String strB = b(str);
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        try {
            iIntValue = Integer.valueOf(strB).intValue();
        } catch (Exception e) {
            bd.b(e);
            iIntValue = 0;
        }
        return iIntValue >= 4;
    }

    public static synchronized void a(Context context, l lVar) {
        if (!b) {
            if (!de.n(context)) {
                bd.a("isWifiAvailable = false, will not to update");
            } else if (!lVar.a(context)) {
                bd.a("check time, will not to update");
            } else {
                bd.a("can start update config");
                new ay(context, lVar).start();
                b = true;
            }
        }
    }

    private static boolean c(Context context, String str) throws Throwable {
        String strA = cz.a(new File(str));
        bd.a("remote.jar local file digest value digest = " + strA);
        if (TextUtils.isEmpty(strA)) {
            bd.a("remote.jar local file digest value fail");
            return false;
        }
        String strB = b(str);
        bd.a("remote.jar local file digest value version = " + strB);
        if (TextUtils.isEmpty(strB)) {
            return false;
        }
        String strD = d(context, strB);
        bd.a("remote.jar config digest value remoteJarMd5 = " + strD);
        if (TextUtils.isEmpty(strD)) {
            bd.a("remote.jar config digest value lost");
            return false;
        }
        return strA.equals(strD);
    }

    private static String d(Context context, String str) {
        return az.a(context).c(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str) throws Throwable {
        JarFile jarFile = null;
        try {
            try {
                File file = new File(str);
                if (file.exists()) {
                    bd.b("file size: " + file.length());
                }
                JarFile jarFile2 = new JarFile(str);
                try {
                    String value = jarFile2.getManifest().getMainAttributes().getValue("Plugin-Version");
                    if (jarFile2 == null) {
                        return value;
                    }
                    try {
                        jarFile2.close();
                        return value;
                    } catch (Exception e) {
                        return value;
                    }
                } catch (Exception e2) {
                    e = e2;
                    jarFile = jarFile2;
                    bd.a(e);
                    bd.a("baidu remote sdk is not ready" + str);
                    if (jarFile != null) {
                        try {
                            jarFile.close();
                        } catch (Exception e3) {
                        }
                    }
                    return Constants.MAIN_VERSION_TAG;
                } catch (Throwable th) {
                    th = th;
                    jarFile = jarFile2;
                    if (jarFile != null) {
                        try {
                            jarFile.close();
                        } catch (Exception e4) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e5) {
            e = e5;
        }
    }
}
