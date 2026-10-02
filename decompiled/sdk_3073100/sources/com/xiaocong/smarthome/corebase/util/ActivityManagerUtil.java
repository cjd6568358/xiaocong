package com.xiaocong.smarthome.corebase.util;

import android.app.Activity;
import java.util.Stack;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ActivityManagerUtil {
    private static Stack<Activity> activityStack;
    private static ActivityManagerUtil instance;

    private ActivityManagerUtil() {
    }

    public static synchronized ActivityManagerUtil getScreenManager() {
        if (instance == null) {
            instance = new ActivityManagerUtil();
        }
        return instance;
    }

    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.push(activity);
    }
}
