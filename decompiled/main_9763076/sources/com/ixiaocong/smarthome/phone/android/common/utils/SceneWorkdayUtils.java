package com.ixiaocong.smarthome.phone.android.common.utils;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.WorkdayWeekModel;
import com.xiaocong.smarthome.network.util.XCHttpLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneWorkdayUtils {
    public static String cycleTime(String workday) {
        if (!TextUtils.isEmpty(workday)) {
            String[] data = workday.split(",");
            if (data.length == 7) {
                return " 每天";
            }
            List<String> list = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                if (data[i].equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                    list.add("日");
                } else if (data[i].equals("1")) {
                    list.add("一");
                } else if (data[i].equals("2")) {
                    list.add("二");
                } else if (data[i].equals("3")) {
                    list.add("三");
                } else if (data[i].equals("4")) {
                    list.add("四");
                } else if (data[i].equals("5")) {
                    list.add("五");
                } else if (data[i].equals("6")) {
                    list.add("六");
                }
            }
            String cycleTime = " 周";
            for (int j = 0; j < list.size(); j++) {
                if (list.size() == 1 || j == list.size() - 1) {
                    cycleTime = cycleTime + list.get(j);
                } else {
                    cycleTime = cycleTime + list.get(j) + "、";
                }
            }
            return cycleTime;
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static List<WorkdayWeekModel> getWeekList(String workday) {
        List<WorkdayWeekModel> weekList = new ArrayList<>();
        String[] workdayData = new String[0];
        if (!TextUtils.isEmpty(workday)) {
            workdayData = workday.split(",");
        }
        for (int i = 0; i < 7; i++) {
            WorkdayWeekModel weekModel = new WorkdayWeekModel();
            if (workdayData.length == 0) {
                weekModel.setChecked(false);
            } else if (workdayData.length == 7) {
                weekModel.setChecked(true);
            } else {
                for (int j = 0; j < workdayData.length; j++) {
                    if (workdayData[j].equals(String.valueOf(i))) {
                        weekModel.setChecked(true);
                        XcLogger.e("weekModel", workdayData[j] + "---" + i);
                        break;
                    }
                }
            }
            weekModel.setWeekValue(i);
            if (i == 1) {
                weekModel.setWeekName("周一");
                weekList.add(0, weekModel);
            } else if (i == 2) {
                weekModel.setWeekName("周二");
                weekList.add(1, weekModel);
            } else if (i == 3) {
                weekModel.setWeekName("周三");
                weekList.add(2, weekModel);
            } else if (i == 4) {
                weekModel.setWeekName("周四");
                weekList.add(3, weekModel);
            } else if (i == 5) {
                weekModel.setWeekName("周五");
                weekList.add(4, weekModel);
            } else if (i == 6) {
                weekModel.setWeekName("周六");
                weekList.add(5, weekModel);
            } else if (i == 0) {
                weekModel.setWeekName("周日");
                weekList.add(weekModel);
            }
        }
        return weekList;
    }

    public static List<Integer> getWeekData(String workday) {
        List<Integer> weekData = new ArrayList<>();
        if (!TextUtils.isEmpty(workday)) {
            String[] workdayData = workday.split(",");
            for (String str : workdayData) {
                weekData.add(Integer.valueOf(str));
            }
        }
        return weekData;
    }

    public static int getHourIndex(int type, String time) {
        int index;
        int size = 0;
        try {
            if (!TextUtils.isEmpty(time)) {
                index = getHourList().indexOf(time.substring(0, time.indexOf(":")));
                if (index != -1) {
                    XCHttpLog.e("WheelView index = " + index);
                } else {
                    XCHttpLog.e("WheelView index = -1//" + index);
                    if (type == 0) {
                        index = 0;
                    } else {
                        size = getHourList().size();
                        index = size - 1;
                    }
                }
            } else {
                XCHttpLog.e("WheelView 数据为空--" + time);
                if (type == 0) {
                    index = 0;
                } else {
                    size = getHourList().size();
                    index = size - 1;
                }
            }
            return index;
        } catch (Exception e) {
            e.printStackTrace();
            XCHttpLog.e("WheelView==" + e.toString());
            if (type == 0) {
                int index2 = size;
                return index2;
            }
            int index3 = getHourList().size() - 1;
            return index3;
        }
    }

    public static int getMinIndex(int type, String time) {
        int index;
        int size = 0;
        try {
            if (!TextUtils.isEmpty(time)) {
                index = getMinList().indexOf(time.substring(time.indexOf(":") + 1));
                if (index != -1) {
                    XCHttpLog.e("WheelView index = " + index);
                } else {
                    XCHttpLog.e("WheelView index = -1//" + index);
                    if (type == 0) {
                        index = 0;
                    } else {
                        size = getMinList().size();
                        index = size - 1;
                    }
                }
            } else {
                XCHttpLog.e("WheelView 数据为空--" + time);
                if (type == 0) {
                    index = 0;
                } else {
                    size = getMinList().size();
                    index = size - 1;
                }
            }
            return index;
        } catch (Exception e) {
            e.printStackTrace();
            XCHttpLog.e("WheelView==" + e.toString());
            if (type == 0) {
                int index2 = size;
                return index2;
            }
            int index3 = getMinList().size() - 1;
            return index3;
        }
    }

    public static List<String> getHourList() {
        String[] PLANETS_HOUR = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        return Arrays.asList(PLANETS_HOUR);
    }

    public static List<String> getMinList() {
        String[] PLANETS_MIN = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
        return Arrays.asList(PLANETS_MIN);
    }
}
