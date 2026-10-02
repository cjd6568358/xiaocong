package com.ixiaocong.smarthome.phone.android.common.utils;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TimeZoneUtil {
    public static Long stringToTime(String Time, int mode) {
        Date dt = new Date();
        switch (mode) {
            case 0:
                String Time2 = Time.replace("T", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).substring(0, 18);
                SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    dt = Format.parse(Time2);
                } catch (Exception e) {
                }
                break;
            case 1:
                String[] array_1 = Time.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                String Time3 = MonStringFormat(array_1[1].toString()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + array_1[2].toString() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + array_1[3].toString() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + array_1[5].toString();
                SimpleDateFormat Format2 = new SimpleDateFormat("MM dd HH:mm:ss yyyy");
                try {
                    dt = Format2.parse(Time3);
                } catch (Exception e2) {
                }
                break;
            case 2:
                String[] array_2 = Time.substring(5, 25).split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                String Time4 = array_2[0].toString() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + MonStringFormat(array_2[1].toString()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + array_2[2].toString() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + array_2[3].toString();
                SimpleDateFormat Format3 = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
                try {
                    dt = Format3.parse(Time4);
                } catch (Exception e3) {
                }
                break;
            case 3:
                SimpleDateFormat Format4 = new SimpleDateFormat("MM/dd HH:mm");
                try {
                    dt = Format4.parse(Time);
                } catch (Exception e4) {
                }
                break;
            case 4:
                SimpleDateFormat Format5 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                try {
                    dt = Format5.parse(Time);
                } catch (Exception e5) {
                }
                break;
            case 5:
                SimpleDateFormat Format6 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    dt = Format6.parse(Time);
                } catch (Exception e6) {
                }
                break;
            case 6:
                SimpleDateFormat Format7 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                try {
                    dt = Format7.parse(Time);
                } catch (Exception e7) {
                }
                break;
            case 7:
                SimpleDateFormat Format8 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try {
                    dt = Format8.parse(Time);
                } catch (Exception e8) {
                }
                break;
            case 8:
                SimpleDateFormat Format9 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dt = Format9.parse(Time);
                } catch (Exception e9) {
                }
                break;
            case 9:
                SimpleDateFormat Format10 = new SimpleDateFormat("yyyy-MM");
                try {
                    dt = Format10.parse(Time);
                } catch (Exception e10) {
                }
                break;
        }
        return Long.valueOf(dt.getTime());
    }

    public static String timeToString(Long sec, int mode) {
        Date date = new Date();
        date.setTime(sec.longValue());
        switch (mode) {
            case 0:
                SimpleDateFormat Format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String time = Format.format(date);
                return time;
            case 1:
                SimpleDateFormat Format2 = new SimpleDateFormat("yyyy/MM/dd");
                String time2 = Format2.format(date);
                return time2;
            case 2:
                SimpleDateFormat Format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time3 = Format3.format(date);
                return time3;
            case 3:
                SimpleDateFormat Format4 = new SimpleDateFormat("yyyy-MM-dd");
                String time4 = Format4.format(date);
                return time4;
            case 4:
                SimpleDateFormat Format5 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                String time5 = Format5.format(date);
                return time5;
            case 5:
                SimpleDateFormat Format6 = new SimpleDateFormat("yyyy年MM月dd日");
                String time6 = Format6.format(date);
                return time6;
            case 6:
                SimpleDateFormat Format7 = new SimpleDateFormat("HH:mm:ss");
                String time7 = Format7.format(date);
                return time7;
            case 7:
                SimpleDateFormat Format8 = new SimpleDateFormat("MM/dd HH:mm");
                String time8 = Format8.format(date);
                return time8;
            case 8:
                SimpleDateFormat Format9 = new SimpleDateFormat("HH:mm");
                String time9 = Format9.format(date);
                return time9;
            case 9:
                SimpleDateFormat Format10 = new SimpleDateFormat("MM/dd\nHH:mm");
                String time10 = Format10.format(date);
                return time10;
            case 10:
                SimpleDateFormat Format11 = new SimpleDateFormat("yyyy:MM:dd");
                String time11 = Format11.format(date);
                return time11;
            case 11:
                SimpleDateFormat Format12 = new SimpleDateFormat("yyyy-MM");
                String time12 = Format12.format(date);
                return time12;
            case 12:
                SimpleDateFormat Format13 = new SimpleDateFormat("yyyyMM");
                String time13 = Format13.format(date);
                return time13;
            case 13:
                SimpleDateFormat Format14 = new SimpleDateFormat("HH:mm:ss");
                String time14 = Format14.format(date);
                return time14;
            default:
                return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String MonStringFormat(String MonString) {
        String MM = Constants.MAIN_VERSION_TAG;
        String[] arrayW = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String[] arrayD = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (int n = 0; n < 12; n++) {
            if (arrayW[n].contains(MonString)) {
                MM = arrayD[n].toString();
            }
        }
        return MM;
    }

    public static String getBeforeOneDayF(String time) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(5);
        c.set(5, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getAfterOneDayF(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(5);
        c.set(5, day + 1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }
}
