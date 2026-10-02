package com.tencent.android.tpush;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.encrypt.Rijndael;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushActivity extends Activity {
    static Application.ActivityLifecycleCallbacks activityCallBack = null;
    static List activityNames = null;
    static String activityName = Constants.MAIN_VERSION_TAG;
    static long msgId = 0;
    static long msgBuildId = 0;

    private boolean checkIntent(Intent intent) {
        if (intent == null || !intent.hasExtra(MessageKey.MSG_PORTECT_TAG)) {
            return false;
        }
        String stringExtra = intent.getStringExtra(MessageKey.MSG_PORTECT_TAG);
        if (com.tencent.android.tpush.common.t.c(stringExtra)) {
            return false;
        }
        try {
            Long lValueOf = Long.valueOf(Rijndael.decrypt(stringExtra));
            return lValueOf.longValue() > 0 && System.currentTimeMillis() >= lValueOf.longValue();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Intent intent = getIntent();
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.e(Constants.LogTag, "XGPushActivity receiver intent:" + intent);
            }
            if (checkIntent(intent)) {
                int intExtra = intent.getIntExtra(Constants.FLAG_ACTION_TYPE, 1);
                if (intExtra == 1) {
                    pushClickedResult(intent);
                    return;
                }
                if (intExtra == 4) {
                    pushClickedPackageResult(intent);
                    return;
                }
                if (intExtra == 2) {
                    showAlertDialog(0, intent);
                    return;
                } else if (intExtra == 3) {
                    showAlertDialog(1, intent);
                    return;
                } else {
                    finish();
                    return;
                }
            }
            finish();
        } catch (Throwable th) {
            Log.w(Constants.LogTag, "warning", th);
            try {
                finish();
            } catch (Throwable th2) {
            }
        }
    }

    static void initActivityCallBack(Application application) {
        if (activityCallBack == null) {
            activityCallBack = new g();
            if (application != null) {
                try {
                    ((Application) application.getApplicationContext()).registerActivityLifecycleCallbacks(activityCallBack);
                } catch (Exception e) {
                }
            }
        }
    }

    public static void addActivityNames(String str) {
        if (!com.tencent.android.tpush.common.t.c(str)) {
            if (activityNames == null) {
                activityNames = new ArrayList();
            }
            if (!activityNames.contains(str)) {
                activityNames.add(str);
            }
        }
    }

    public static boolean isMonitorActivityNames(String str) {
        return (activityNames == null || com.tencent.android.tpush.common.t.c(str) || !activityNames.contains(str)) ? false : true;
    }

    private void pushClickedResult(Intent intent) {
        String stringExtra = intent.getStringExtra("activity") != null ? intent.getStringExtra("activity") : Constants.MAIN_VERSION_TAG;
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.e(Constants.PushMessageLogTag, "activity intent =" + intent + "activity = " + stringExtra + "intent.getFlags()" + intent.getFlags());
        }
        if (intent != null) {
            msgId = intent.getLongExtra(MessageKey.MSG_ID, 0L);
            msgBuildId = intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L);
            activityName = stringExtra;
        }
        Intent intent2 = new Intent();
        intent2.addFlags(intent.getFlags());
        intent2.addFlags(536870912);
        intent2.setClassName(getApplicationContext(), stringExtra);
        intent.putExtra(Constants.TAG_TPUSH_MESSAGE, "true");
        intent2.putExtras(intent);
        intent2.putExtra(Constants.TAG_TPUSH_NOTIFICATION, XGPushManager.a((Activity) this));
        try {
            initActivityCallBack(getApplication());
            startActivity(intent2);
        } catch (ActivityNotFoundException e) {
        }
        finish();
    }

    private void pushClickedPackageResult(Intent intent) {
        broadcastToTPushService(intent);
        ResolveInfo appMainActivity = getAppMainActivity(intent.getStringExtra(Constants.FLAG_PACKAGE_NAME));
        if (appMainActivity != null) {
            String str = appMainActivity.activityInfo.name;
            String str2 = appMainActivity.activityInfo.packageName;
            Intent intent2 = new Intent();
            intent2.putExtras(intent);
            intent2.setComponent(new ComponentName(str2, str));
            creatDialog(0, intent2);
            return;
        }
        creatDialog(1, intent);
    }

    private ResolveInfo getAppMainActivity(String str) {
        try {
            PackageManager packageManager = getPackageManager();
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            Collections.sort(listQueryIntentActivities, new ResolveInfo.DisplayNameComparator(packageManager));
            for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                String str2 = resolveInfo.activityInfo.name;
                if (resolveInfo.activityInfo.packageName.equals(str)) {
                    return resolveInfo;
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "查找主Activity出错", th);
        }
        return null;
    }

    private void creatDialog(int i, Intent intent) {
        if (i == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setOnCancelListener(new l(this, intent)).setTitle("提示").setMessage("是否确定打开此应用？").setPositiveButton("打开", new k(this, intent)).setNegativeButton("取消", new j(this, intent));
            builder.create().show();
        } else if (i == 1) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setOnCancelListener(new o(this, intent)).setTitle("提示").setMessage("本地未发现此应用，建议去下载！").setPositiveButton("下载", new n(this, intent)).setNegativeButton("取消", new m(this, intent));
            builder2.create().show();
        }
    }

    private void showAlertDialog(int i, Intent intent) {
        if (i == 0) {
            String stringExtra = intent.getStringExtra("activity");
            if (intent.getIntExtra(Constants.FLAG_ACTION_CONFIRM, 0) == 1) {
                new AlertDialog.Builder(this).setTitle("提示").setCancelable(false).setMessage("是否打开网站:" + stringExtra + "?").setPositiveButton("确认", new q(this, stringExtra, intent)).setNegativeButton("取消", new p(this, intent)).show();
                return;
            } else {
                openUrl(stringExtra, intent);
                return;
            }
        }
        if (i == 1) {
            if (intent.getIntExtra(Constants.FLAG_ACTION_CONFIRM, 0) == 1) {
                new AlertDialog.Builder(this).setTitle("提示").setCancelable(false).setMessage("继续打开Intent?").setPositiveButton("确认", new i(this, intent)).setNegativeButton("取消", new h(this, intent)).show();
            } else {
                openIntent(intent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openIntent(Intent intent) {
        try {
            Uri uri = Uri.parse(intent.getStringExtra("activity"));
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(uri);
            if (intent2.resolveActivity(getPackageManager()) != null) {
                broadcastToTPushService(intent);
                startActivity(intent2);
            }
            finish();
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "openIntent error.", th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openUrl(String str, Intent intent) {
        try {
            Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            broadcastToTPushService(intent);
            startActivity(intent2);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "openUrl error.", th);
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastToTPushService(Intent intent) {
        XGPushManager.a(getApplicationContext(), intent);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
    }
}
