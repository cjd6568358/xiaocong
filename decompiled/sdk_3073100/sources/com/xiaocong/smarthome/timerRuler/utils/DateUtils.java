package com.xiaocong.smarthome.timerRuler.utils;

import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DateUtils {
    public static long getTodayStart(long currentTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(currentTime));
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return calendar.getTimeInMillis();
    }

    public static String getTimeByCurrentSecond(int currentSecond) {
        int currentSecond2 = currentSecond / 60;
        int minute = currentSecond2 % 60;
        int hour = currentSecond2 / 60;
        if (hour >= 24) {
            hour %= 24;
        }
        return (hour < 10 ? "0" + hour : Integer.valueOf(hour)) + ":" + (minute < 10 ? "0" + minute : Integer.valueOf(minute));
    }

    public static String getTimeByCurrentHours(int currentSecond) {
        int currentSecond2 = (currentSecond * 10) / 60;
        int minute = currentSecond2 % 60;
        int hour = currentSecond2 / 60;
        if (hour >= 24) {
            hour %= 24;
        }
        return (hour < 10 ? "0" + hour : Integer.valueOf(hour)) + ":" + (minute < 10 ? "0" + minute : Integer.valueOf(minute));
    }
}
