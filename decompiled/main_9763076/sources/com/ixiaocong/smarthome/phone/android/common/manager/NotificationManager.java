package com.ixiaocong.smarthome.phone.android.common.manager;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NotificationManager {
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            try {
                Class<?> cls = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = cls.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = cls.getDeclaredField("OP_POST_NOTIFICATION");
                int value = ((Integer) opPostNotificationValue.get(Integer.class)).intValue();
                boolean isFirst = ((Boolean) SpUtils.getFromLocal(context, "jfkadlj", "jkcien", false)).booleanValue();
                return isFirst || ((Integer) checkOpNoThrowMethod.invoke(mAppOps, Integer.valueOf(value), Integer.valueOf(uid), pkg)).intValue() == 0;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (NoSuchFieldException e3) {
                e3.printStackTrace();
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
        return false;
    }
}
