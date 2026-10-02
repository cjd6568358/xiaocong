package com.hianalytics.android.v1;

import android.content.Context;
import android.os.Handler;
import com.tencent.android.tpush.common.Constants;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HiAnalytics {
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static boolean timerOn = false;
    private static int reportPeriod = 0;

    public static void onEvent(Context context, String str, String str2) {
        if (context == null) {
            com.hianalytics.android.b.a.a.h();
            return;
        }
        if (str == null || str.equals(Constants.MAIN_VERSION_TAG)) {
            com.hianalytics.android.b.a.a.h();
            return;
        }
        if (str2 == null || str2.equals(Constants.MAIN_VERSION_TAG)) {
            com.hianalytics.android.b.a.a.h();
            return;
        }
        Handler handlerG = com.hianalytics.android.b.a.a.g();
        if (handlerG != null) {
            handlerG.post(new a(context, str, str2, System.currentTimeMillis()));
        }
        com.hianalytics.android.b.a.a.h();
    }

    public static void onPause(Context context) {
        if (context == null) {
            com.hianalytics.android.b.a.a.h();
            return;
        }
        Handler handlerG = com.hianalytics.android.b.a.a.g();
        if (handlerG != null) {
            handlerG.post(new d(context, 0, System.currentTimeMillis()));
        }
        com.hianalytics.android.b.a.a.h();
    }

    public static void onReport(Context context) {
        if (context == null) {
            com.hianalytics.android.b.a.a.h();
            return;
        }
        Handler handlerG = com.hianalytics.android.b.a.a.g();
        if (handlerG != null) {
            handlerG.post(new d(context, 2, System.currentTimeMillis()));
        }
        com.hianalytics.android.b.a.a.h();
    }

    public static void onResume(Context context) {
        if (context == null) {
            com.hianalytics.android.b.a.a.h();
            return;
        }
        Handler handlerG = com.hianalytics.android.b.a.a.g();
        if (handlerG != null) {
            handlerG.post(new d(context, 1, System.currentTimeMillis()));
        }
        com.hianalytics.android.b.a.a.h();
    }

    public static void setEventSize(int i) {
        if (i >= 0) {
            com.hianalytics.android.b.a.a.a(i);
        }
    }

    public static void setMaxMsg(Long l) {
        if (l.longValue() >= 1000) {
            com.hianalytics.android.b.a.a.c(l);
        }
    }

    public static void setMaxSessionIDTimeOut(long j) {
        if (j >= 30) {
            com.hianalytics.android.b.a.a.d(Long.valueOf(60 * j));
        }
    }

    public static void setRecordExpireTimeOut(Long l) {
        if (l.longValue() >= 24) {
            com.hianalytics.android.b.a.a.b(Long.valueOf(l.longValue() * 60 * 60));
        }
    }

    public static void setReportTimer(Context context, int i) {
        try {
            if (i == 0) {
                timerOn = false;
                scheduler.shutdown();
                scheduler = Executors.newScheduledThreadPool(1);
            } else if (!timerOn) {
                timerOn = true;
                com.hianalytics.android.b.a.a.h();
                scheduler.scheduleAtFixedRate(new d(context, 2, System.currentTimeMillis()), i, i, TimeUnit.SECONDS);
            } else if (timerOn && i != reportPeriod) {
                reportPeriod = i;
                com.hianalytics.android.b.a.a.h();
                scheduler.shutdown();
                ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1);
                scheduler = scheduledExecutorServiceNewScheduledThreadPool;
                scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(new d(context, 2, System.currentTimeMillis()), i, i, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.getMessage();
            com.hianalytics.android.b.a.a.h();
            e.printStackTrace();
        }
    }

    public static void setReportWhenFull(boolean z) {
        com.hianalytics.android.b.a.a.a(z);
    }

    public static void setSessionExpireTimeOut(Long l) {
        if (l.longValue() >= 30) {
            com.hianalytics.android.b.a.a.a(l);
        }
    }
}
