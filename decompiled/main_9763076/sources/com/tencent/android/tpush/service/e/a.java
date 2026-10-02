package com.tencent.android.tpush.service.e;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import bsh.ParserConstants;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static final String b = a.class.getSimpleName();
    private static Uri c = Uri.parse("content://telephony/carriers/preferapn");
    public static int a = 0;

    public static byte a(Context context) {
        switch (c(context)) {
            case 1:
                return (byte) 2;
            case 2:
                return (byte) 3;
            case 4:
                return (byte) 1;
            case 8:
                return (byte) 4;
            case 16:
                return (byte) 5;
            case 32:
                return (byte) 6;
            case 64:
                return (byte) 7;
            case 256:
                return (byte) 8;
            case WXMediaMessage.TITLE_LENGTH_LIMIT /* 512 */:
                return (byte) 9;
            case WXMediaMessage.DESCRIPTION_LENGTH_LIMIT /* 1024 */:
                return (byte) 10;
            case 2048:
                return JceStruct.STRUCT_END;
            default:
                return (byte) 0;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public static String b(Context context) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = context.getContentResolver().query(c, null, null, null, null);
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Exception e) {
                    }
                }
                return null;
            }
            try {
                cursorQuery.moveToFirst();
                if (cursorQuery.isAfterLast()) {
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception e2) {
                        }
                    }
                    return null;
                }
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("proxy"));
                if (cursorQuery == null) {
                    return string;
                }
                try {
                    cursorQuery.close();
                    return string;
                } catch (Exception e3) {
                    return string;
                }
            } catch (Exception e4) {
                cursor = cursorQuery;
                if (cursor == null) {
                    return Constants.MAIN_VERSION_TAG;
                }
                try {
                    cursor.close();
                    return Constants.MAIN_VERSION_TAG;
                } catch (Exception e5) {
                    return Constants.MAIN_VERSION_TAG;
                }
            } catch (Throwable th) {
                th = th;
                if (cursorQuery != null) {
                    try {
                        cursorQuery.close();
                    } catch (Exception e6) {
                    }
                }
                throw th;
            }
        } catch (Exception e7) {
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
    }

    public static int c(Context context) throws Throwable {
        NetworkInfo activeNetworkInfo;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                if (activeNetworkInfo.getTypeName().toUpperCase(Locale.US).equals("WIFI")) {
                    return 2;
                }
                if (activeNetworkInfo.getExtraInfo() == null) {
                    return ParserConstants.LSHIFTASSIGN;
                }
                String lowerCase = activeNetworkInfo.getExtraInfo().toLowerCase(Locale.US);
                if (lowerCase.startsWith("cmwap")) {
                    return 1;
                }
                if (lowerCase.startsWith("cmnet") || lowerCase.startsWith("epc.tmobile.com")) {
                    return 4;
                }
                if (lowerCase.startsWith("uniwap")) {
                    return 16;
                }
                if (lowerCase.startsWith("uninet")) {
                    return 8;
                }
                if (lowerCase.startsWith("wap")) {
                    return 64;
                }
                if (lowerCase.startsWith("net")) {
                    return 32;
                }
                if (lowerCase.startsWith("ctwap")) {
                    return WXMediaMessage.TITLE_LENGTH_LIMIT;
                }
                if (lowerCase.startsWith("ctnet")) {
                    return 256;
                }
                if (lowerCase.startsWith("3gwap")) {
                    return WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
                }
                if (lowerCase.startsWith("3gnet")) {
                    return 2048;
                }
                if (lowerCase.startsWith("#777")) {
                    String strB = b(context);
                    if (strB == null || strB.length() <= 0) {
                        return 256;
                    }
                    return WXMediaMessage.TITLE_LENGTH_LIMIT;
                }
                return ParserConstants.LSHIFTASSIGN;
            }
            return ParserConstants.LSHIFTASSIGN;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(b, "getMProxyType>>> ", e);
        }
    }

    public static boolean a() {
        try {
            Process processExec = Runtime.getRuntime().exec("ping -c 1 -w 10 www.qq.com");
            int iWaitFor = processExec.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
            while (bufferedReader.readLine() != null) {
            }
            bufferedReader.close();
            processExec.destroy();
            return iWaitFor == 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public static boolean d(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
            }
        } catch (Exception e) {
            if (a()) {
                return true;
            }
            com.tencent.android.tpush.a.a.c(b, "APNUtil -> checkNetWork", e);
            a++;
            if (a >= 5) {
                a = 0;
                return true;
            }
        }
        return false;
    }
}
