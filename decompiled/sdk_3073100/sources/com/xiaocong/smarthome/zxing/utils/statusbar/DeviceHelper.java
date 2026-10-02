package com.xiaocong.smarthome.zxing.utils.statusbar;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceHelper {
    private static String sFlymeVersionName;
    private static String sMiuiVersionName;
    private static final String[] MEIZUBOARD = {"m9", "M9", "mx", "MX"};
    private static boolean sIsTabletChecked = false;
    private static boolean sIsTabletValue = false;

    static {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            try {
                Properties properties = new Properties();
                properties.load(fileInputStream2);
                Class<?> clzSystemProperties = Class.forName("android.os.SystemProperties");
                Method getMethod = clzSystemProperties.getDeclaredMethod("get", String.class);
                sMiuiVersionName = getLowerCaseName(properties, getMethod, "ro.miui.ui.version.name");
                sFlymeVersionName = getLowerCaseName(properties, getMethod, "ro.build.display.id");
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean isFlyme() {
        return !TextUtils.isEmpty(sFlymeVersionName) && sFlymeVersionName.contains("flyme");
    }

    public static boolean isMIUI() {
        return !TextUtils.isEmpty(sMiuiVersionName);
    }

    public static boolean isMIUIV5() {
        return "v5".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV6() {
        return "v6".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV7() {
        return "v7".equals(sMiuiVersionName);
    }

    public static boolean isMIUIV8() {
        return "v8".equals(sMiuiVersionName);
    }

    public static boolean isMeizu() {
        return isPhone(MEIZUBOARD) || isFlyme();
    }

    public static boolean isZUKZ1() {
        String board = Build.MODEL;
        return board != null && board.toLowerCase().contains("zuk z1");
    }

    public static boolean isZTKC2016() {
        String board = Build.MODEL;
        return board != null && board.toLowerCase().contains("zte c2016");
    }

    private static boolean isPhone(String[] boards) {
        String board = Build.BOARD;
        if (board == null) {
            return false;
        }
        for (String board1 : boards) {
            if (board.equals(board1)) {
                return true;
            }
        }
        return false;
    }

    private static String getLowerCaseName(Properties p, Method get, String key) {
        String name = p.getProperty(key);
        if (name == null) {
            try {
                name = (String) get.invoke(null, key);
            } catch (Exception e) {
            }
        }
        return name != null ? name.toLowerCase() : name;
    }
}
