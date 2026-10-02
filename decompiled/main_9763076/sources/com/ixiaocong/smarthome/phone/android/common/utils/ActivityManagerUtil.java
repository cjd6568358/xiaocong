package com.ixiaocong.smarthome.phone.android.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import java.util.Stack;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
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

    public void AppExit(Context context) {
        try {
            popAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService("activity");
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
