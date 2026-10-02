package com.youzan.androidsdk.basic.tool;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.youzan.androidsdk.YouzanException;

/* JADX INFO: compiled from: SchemeIntent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class d {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private static final String f57 = "mailto";

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private static final String f58 = "geo";

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private static final String f59 = "网页请求打开应用";

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final String f60 = "weixin";

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final String f61 = "alipays";

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private static final String f62 = "mqqwpa";

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private static final String f63 = "sms";

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private static final String f64 = "打开";

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private static final String f65 = "系统未安装相应应用";

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private static final String f66 = "tel";

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static boolean m47(Intent intent, Activity activity) throws YouzanException {
        Activity target = activity.getParent();
        if (target == null) {
            target = activity;
        }
        try {
            intent.setFlags(536870912);
            return target.startActivityIfNeeded(intent, -1);
        } catch (ActivityNotFoundException e) {
            throw new YouzanException(f65);
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static void m45(Intent intent, Context context) throws YouzanException {
        try {
            intent.setFlags(276824064);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            throw new YouzanException(f65);
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static boolean m48(String scheme) {
        return f63.equalsIgnoreCase(scheme) || f66.equalsIgnoreCase(scheme) || f57.equalsIgnoreCase(scheme) || f58.equalsIgnoreCase(scheme);
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    public static boolean m50(String scheme) {
        return f60.equalsIgnoreCase(scheme) || f61.equalsIgnoreCase(scheme) || f62.equalsIgnoreCase(scheme);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static boolean m46(Context context, Uri uri) {
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme) || !m48(scheme)) {
            return false;
        }
        m44(context, uri, f59);
        return true;
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    public static boolean m49(Context context, Uri uri) {
        String scheme = uri.getScheme();
        if (TextUtils.isEmpty(scheme) || !m50(scheme)) {
            return false;
        }
        try {
            Intent intent = Intent.parseUri(uri.toString(), 1);
            if (context instanceof Activity) {
                return m47(intent, (Activity) context);
            }
            m45(intent, context);
            return true;
        } catch (YouzanException e) {
            Toast.makeText(context, e.getMsg(), 0).show();
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static void m44(final Context context, final Uri uri, String msg) {
        new AlertDialog.Builder(context).setMessage(msg).setPositiveButton(f64, new DialogInterface.OnClickListener() { // from class: com.youzan.androidsdk.basic.tool.d.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(uri);
                if (!(context instanceof Activity)) {
                    intent.setFlags(276824064);
                }
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setIcon(R.drawable.ic_dialog_alert).show();
    }
}
