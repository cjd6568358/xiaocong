package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import bsh.ParserConstants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class NavUtils {
    public static boolean shouldUpRecreateTask(Activity sourceActivity, Intent targetIntent) {
        if (Build.VERSION.SDK_INT >= 16) {
            return sourceActivity.shouldUpRecreateTask(targetIntent);
        }
        String action = sourceActivity.getIntent().getAction();
        return (action == null || action.equals("android.intent.action.MAIN")) ? false : true;
    }

    public static void navigateUpTo(Activity sourceActivity, Intent upIntent) {
        if (Build.VERSION.SDK_INT >= 16) {
            sourceActivity.navigateUpTo(upIntent);
            return;
        }
        upIntent.addFlags(67108864);
        sourceActivity.startActivity(upIntent);
        sourceActivity.finish();
    }

    public static Intent getParentActivityIntent(Activity sourceActivity) {
        Intent result;
        Intent result2;
        if (Build.VERSION.SDK_INT < 16 || (result2 = sourceActivity.getParentActivityIntent()) == null) {
            String parentName = getParentActivityName(sourceActivity);
            if (parentName == null) {
                return null;
            }
            ComponentName target = new ComponentName(sourceActivity, parentName);
            try {
                String grandparent = getParentActivityName(sourceActivity, target);
                if (grandparent == null) {
                    result = Intent.makeMainActivity(target);
                } else {
                    result = new Intent().setComponent(target);
                }
                return result;
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + parentName + "' in manifest");
                return null;
            }
        }
        return result2;
    }

    public static Intent getParentActivityIntent(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivity = getParentActivityName(context, componentName);
        if (parentActivity == null) {
            return null;
        }
        ComponentName target = new ComponentName(componentName.getPackageName(), parentActivity);
        String grandparent = getParentActivityName(context, target);
        if (grandparent == null) {
            return Intent.makeMainActivity(target);
        }
        return new Intent().setComponent(target);
    }

    public static String getParentActivityName(Activity sourceActivity) {
        try {
            return getParentActivityName(sourceActivity, sourceActivity.getComponentName());
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String getParentActivityName(Context context, ComponentName componentName) throws PackageManager.NameNotFoundException {
        String parentActivity;
        String result;
        PackageManager pm = context.getPackageManager();
        ActivityInfo info = pm.getActivityInfo(componentName, ParserConstants.LSHIFTASSIGN);
        if (Build.VERSION.SDK_INT < 16 || (result = info.parentActivityName) == null) {
            if (info.metaData != null && (parentActivity = info.metaData.getString("android.support.PARENT_ACTIVITY")) != null) {
                if (parentActivity.charAt(0) == '.') {
                    parentActivity = context.getPackageName() + parentActivity;
                }
                return parentActivity;
            }
            return null;
        }
        return result;
    }
}
