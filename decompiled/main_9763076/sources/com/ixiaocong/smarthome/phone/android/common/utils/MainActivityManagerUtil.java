package com.ixiaocong.smarthome.phone.android.common.utils;

import android.app.Activity;
import java.util.Stack;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MainActivityManagerUtil {
    private static Stack<Activity> activityStack;
    private static MainActivityManagerUtil instance;

    private MainActivityManagerUtil() {
    }

    public static synchronized MainActivityManagerUtil getScreenManager() {
        if (instance == null) {
            instance = new MainActivityManagerUtil();
        }
        return instance;
    }

    public void popActivity(Activity activity) {
        if (activityStack != null && activity != null) {
            activityStack.remove(activity);
        }
    }

    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.push(activity);
    }

    public void popAllActivity() {
        Activity activity;
        while (activityStack != null && !activityStack.isEmpty() && (activity = activityStack.pop()) != null) {
            activity.finish();
        }
    }
}
