package com.baidu.cloud.media.player.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f {
    private String a;
    private String b;
    private String c;

    public f(Context context) {
        b(context);
    }

    private void b(Context context) {
        try {
            this.c = a(context);
            this.a = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
            this.b = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName();
        } catch (Exception e) {
            Log.d("BaseInfo", Constants.MAIN_VERSION_TAG + e.getMessage());
        }
    }

    public String a(Context context) {
        NetworkInfo activeNetworkInfo;
        NetworkInfo.State state;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable()) {
            return "NONE";
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo != null && (state = networkInfo.getState()) != null && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING)) {
            return "WIFI";
        }
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        if (networkInfo2 != null) {
            NetworkInfo.State state2 = networkInfo2.getState();
            networkInfo2.getSubtypeName();
            if (state2 != null && (state2 == NetworkInfo.State.CONNECTED || state2 == NetworkInfo.State.CONNECTING)) {
                switch (activeNetworkInfo.getSubtype()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return "2G";
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return "3G";
                    case 13:
                        return "4G";
                    default:
                        return "UNKNOWN";
                }
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }
}
