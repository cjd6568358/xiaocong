package com.baidu.cloud.media.player.a;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e {
    private String a = "android";
    private String b;
    private int c;
    private String d;
    private String e;
    private String f;
    private int g;
    private int h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;

    public e(Context context) {
        a(context);
    }

    private void a(Context context) {
        try {
            this.b = Build.VERSION.RELEASE;
            this.c = Build.VERSION.SDK_INT;
            this.d = Build.MODEL;
            this.e = Build.BRAND;
            this.f = Build.CPU_ABI;
            this.i = Locale.getDefault().getLanguage();
            this.l = Settings.Secure.getString(context.getContentResolver(), "android_id");
            this.j = this.l;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= 17) {
                ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
            } else {
                ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            }
            if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                this.g = displayMetrics.heightPixels;
                this.h = displayMetrics.widthPixels;
            } else {
                this.g = displayMetrics.widthPixels;
                this.h = displayMetrics.heightPixels;
            }
            if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") != -1) {
                this.k = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            }
            try {
                String string = Settings.System.getString(context.getContentResolver(), "com.baidu.deviceid");
                if (string == null || string.equals(Constants.MAIN_VERSION_TAG)) {
                    return;
                }
                String string2 = Settings.System.getString(context.getContentResolver(), "bd_setting_i");
                if (TextUtils.isEmpty(string2)) {
                    string2 = PushConstants.PUSH_TYPE_NOTIFY;
                }
                this.m = string + "|" + new StringBuffer(string2).reverse().toString();
            } catch (Exception e) {
                Log.d("BaseInfo", Constants.MAIN_VERSION_TAG + e.getMessage());
            }
        } catch (Exception e2) {
            Log.d("BaseInfo", Constants.MAIN_VERSION_TAG + e2.getMessage());
        }
    }
}
