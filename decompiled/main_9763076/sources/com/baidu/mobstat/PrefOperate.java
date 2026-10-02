package com.baidu.mobstat;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.Bugly;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PrefOperate {
    public static void loadMetaDataConfig(Context context) {
        SendStrategyEnum sendStrategyEnum = SendStrategyEnum.APP_START;
        try {
            String strA = de.a(context, "BaiduMobAd_EXCEPTION_LOG");
            if (!TextUtils.isEmpty(strA) && "true".equals(strA)) {
                bt.a().a(context, false);
            }
        } catch (Exception e) {
            db.a(e);
        }
        try {
            String strA2 = de.a(context, "BaiduMobAd_SEND_STRATEGY");
            if (!TextUtils.isEmpty(strA2)) {
                if (strA2.equals(SendStrategyEnum.APP_START.name())) {
                    sendStrategyEnum = SendStrategyEnum.APP_START;
                    bj.a().a(context, sendStrategyEnum.ordinal());
                } else if (strA2.equals(SendStrategyEnum.ONCE_A_DAY.name())) {
                    sendStrategyEnum = SendStrategyEnum.ONCE_A_DAY;
                    bj.a().a(context, sendStrategyEnum.ordinal());
                    bj.a().b(context, 24);
                } else if (strA2.equals(SendStrategyEnum.SET_TIME_INTERVAL.name())) {
                    sendStrategyEnum = SendStrategyEnum.SET_TIME_INTERVAL;
                    bj.a().a(context, sendStrategyEnum.ordinal());
                }
            }
        } catch (Exception e2) {
            db.a(e2);
            sendStrategyEnum = sendStrategyEnum;
        }
        try {
            String strA3 = de.a(context, "BaiduMobAd_TIME_INTERVAL");
            if (!TextUtils.isEmpty(strA3)) {
                int i = Integer.parseInt(strA3);
                if (sendStrategyEnum.ordinal() == SendStrategyEnum.SET_TIME_INTERVAL.ordinal() && i > 0 && i <= 24) {
                    bj.a().b(context, i);
                }
            }
        } catch (Exception e3) {
            db.a(e3);
        }
        try {
            String strA4 = de.a(context, "BaiduMobAd_ONLY_WIFI");
            if (!TextUtils.isEmpty(strA4)) {
                if ("true".equals(strA4)) {
                    bj.a().a(context, true);
                } else if (Bugly.SDK_IS_DEV.equals(strA4)) {
                    bj.a().a(context, false);
                }
            }
        } catch (Exception e4) {
            db.a(e4);
        }
    }
}
