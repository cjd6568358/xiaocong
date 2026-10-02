package com.tencent.android.tpush.b;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.XGBasicPushNotificationBuilder;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushActivity;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.tencent.android.tpush.XGPushNotificationBuilder;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.common.t;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static volatile BroadcastReceiver a = null;

    private static String a(int i) {
        return "TPUSH_NOTIF_BUILDID_" + String.valueOf(i);
    }

    public static synchronized XGPushNotificationBuilder a(Context context) {
        return new XGBasicPushNotificationBuilder().setFlags(16);
    }

    public static void a(Context context, int i, XGPushNotificationBuilder xGPushNotificationBuilder) {
        String strA = a(i);
        JSONObject jSONObject = new JSONObject();
        xGPushNotificationBuilder.encode(jSONObject);
        JSONObject jSONObject2 = new JSONObject();
        com.tencent.android.tpush.common.e.a(jSONObject2, xGPushNotificationBuilder.getType(), jSONObject.toString());
        com.tencent.android.tpush.common.n.b(context, strA, jSONObject2.toString());
    }

    public static XGPushNotificationBuilder a(Context context, int i) throws JSONException {
        String strA;
        String string;
        XGPushNotificationBuilder xGPushNotificationBuilder = null;
        if (context != null && (strA = com.tencent.android.tpush.common.n.a(context, a(i), (String) null)) != null) {
            try {
                JSONObject jSONObject = new JSONObject(strA);
                if (jSONObject.has(XGPushNotificationBuilder.BASIC_NOTIFICATION_BUILDER_TYPE)) {
                    XGBasicPushNotificationBuilder xGBasicPushNotificationBuilder = new XGBasicPushNotificationBuilder();
                    try {
                        xGPushNotificationBuilder = xGBasicPushNotificationBuilder;
                        string = jSONObject.getString(XGPushNotificationBuilder.BASIC_NOTIFICATION_BUILDER_TYPE);
                        xGPushNotificationBuilder.decode(string);
                    } catch (JSONException e) {
                        xGPushNotificationBuilder = xGBasicPushNotificationBuilder;
                        e = e;
                        com.tencent.android.tpush.a.a.c(Constants.LogTag, Constants.MAIN_VERSION_TAG, e);
                    }
                } else if (jSONObject.has(XGPushNotificationBuilder.CUSTOM_NOTIFICATION_BUILDER_TYPE)) {
                    XGCustomPushNotificationBuilder xGCustomPushNotificationBuilder = new XGCustomPushNotificationBuilder();
                    try {
                        xGPushNotificationBuilder = xGCustomPushNotificationBuilder;
                        string = jSONObject.getString(XGPushNotificationBuilder.CUSTOM_NOTIFICATION_BUILDER_TYPE);
                        xGPushNotificationBuilder.decode(string);
                    } catch (JSONException e2) {
                        xGPushNotificationBuilder = xGCustomPushNotificationBuilder;
                        e = e2;
                        com.tencent.android.tpush.a.a.c(Constants.LogTag, Constants.MAIN_VERSION_TAG, e);
                    }
                }
            } catch (JSONException e3) {
                e = e3;
            }
        }
        return xGPushNotificationBuilder;
    }

    public static String b(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(context.getPackageName());
            for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 0)) {
                if (resolveInfo.activityInfo != null) {
                    return resolveInfo.activityInfo.name;
                }
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("MessageHelper", "get Activity error", th);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0024  */
    public static Intent a(Context context, g gVar, boolean z, n nVar) {
        Intent intent = null;
        switch (gVar.a) {
            case 1:
                Intent intent2 = new Intent(Constants.ACTION_INTERNAL_PUSH_MESSAGE);
                String strB = gVar.b;
                if (t.c(strB)) {
                    strB = b(context);
                }
                int i = 538968064;
                if (gVar.c == null || gVar.c.a <= 0) {
                    if (z) {
                        i = SQLiteDatabase.CREATE_IF_NECESSARY;
                    }
                    intent2.addFlags(i);
                    intent2.setFlags(67239936);
                } else {
                    intent2.setFlags(gVar.c.a);
                }
                intent2.putExtra("activity", strB);
                intent2.putExtra(MessageKey.MSG_ID, nVar.b());
                intent2.putExtra(MessageKey.MSG_BUSI_MSG_ID, nVar.d());
                intent2.putExtra(Constants.FLAG_NOTIFICATION_ACTION_TYPE, 1);
                intent2.putExtra(Constants.FLAG_ACTION_TYPE, 1);
                intent2.setClass(context.getApplicationContext(), XGPushActivity.class);
                intent = intent2;
                if (intent != null) {
                    intent.putExtra(Constants.FLAG_ACTION_CONFIRM, gVar.g);
                }
                return intent;
            case 2:
                intent = new Intent(Constants.ACTION_INTERNAL_PUSH_MESSAGE);
                intent.putExtra("activity", gVar.f);
                intent.putExtra(Constants.FLAG_ACTION_TYPE, gVar.a);
                intent.putExtra(Constants.FLAG_NOTIFICATION_ACTION_TYPE, 2);
                intent.setClass(context.getApplicationContext(), XGPushActivity.class);
                if (intent != null) {
                    intent.putExtra(Constants.FLAG_ACTION_CONFIRM, gVar.g);
                }
                return intent;
            case 3:
                intent = new Intent(Constants.ACTION_INTERNAL_PUSH_MESSAGE);
                intent.putExtra("activity", gVar.d);
                intent.putExtra(Constants.FLAG_ACTION_TYPE, gVar.a);
                intent.putExtra(Constants.FLAG_NOTIFICATION_ACTION_TYPE, 3);
                intent.putExtra(MessageKey.MSG_ID, nVar.b());
                intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, nVar.d());
                intent.setClass(context.getApplicationContext(), XGPushActivity.class);
                if (intent != null) {
                    intent.putExtra(Constants.FLAG_ACTION_CONFIRM, gVar.g);
                }
                return intent;
            case 4:
                intent = new Intent(Constants.ACTION_INTERNAL_PUSH_MESSAGE);
                String str = gVar.h;
                if (!t.c(str)) {
                    intent.putExtra(Constants.FLAG_ACTION_TYPE, gVar.a);
                    intent.putExtra(Constants.FLAG_PACKAGE_DOWNLOAD_URL, gVar.j);
                    intent.putExtra(Constants.FLAG_PACKAGE_NAME, str);
                    intent.putExtra("activity", str);
                    intent.putExtra(Constants.FLAG_NOTIFICATION_ACTION_TYPE, 4);
                    intent.setClass(context.getApplicationContext(), XGPushActivity.class);
                    if (intent != null) {
                        intent.putExtra(Constants.FLAG_ACTION_CONFIRM, gVar.g);
                    }
                }
                return intent;
            default:
                com.tencent.android.tpush.a.a.i("MessageHelper", "unkown type" + gVar.a);
                if (intent != null) {
                    intent.putExtra(Constants.FLAG_ACTION_CONFIRM, gVar.g);
                }
                return intent;
        }
    }

    public static void a(Context context, n nVar) throws Throwable {
        int identifier;
        int identifier2;
        f fVar = (f) nVar.g();
        g gVarM = fVar.m();
        XGPushNotificationBuilder xGPushNotificationBuilderA = a(context, fVar.h());
        if (xGPushNotificationBuilderA == null || fVar.t() == 1) {
            if (xGPushNotificationBuilderA == null) {
                xGPushNotificationBuilderA = XGPushManager.getDefaultNotificationBuilder(context);
            }
            if (xGPushNotificationBuilderA == null) {
                xGPushNotificationBuilderA = a(context);
            }
            if (fVar.k() != 0) {
                xGPushNotificationBuilderA.setFlags(16);
            }
            if (fVar.i() != 0) {
                if (!TextUtils.isEmpty(fVar.p()) && (identifier = context.getResources().getIdentifier(fVar.p(), "raw", context.getPackageName())) > 0) {
                    xGPushNotificationBuilderA.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + identifier));
                } else {
                    xGPushNotificationBuilderA.setDefaults(1);
                }
            }
            if (fVar.j() != 0) {
                xGPushNotificationBuilderA.setDefaults(2);
            }
            if (fVar.o() != 0) {
                xGPushNotificationBuilderA.setFlags(1);
            }
            String strR = fVar.r();
            if (strR != null && !TextUtils.isEmpty(strR)) {
                int identifier3 = context.getResources().getIdentifier(strR, "drawable", context.getPackageName());
                if (identifier3 > 0) {
                    xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(identifier3));
                } else {
                    int identifier4 = context.getResources().getIdentifier("notification_icon", "drawable", context.getPackageName());
                    if (identifier4 > 0) {
                        xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(identifier4));
                    } else {
                        xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(context.getApplicationInfo().icon));
                    }
                }
            } else if (xGPushNotificationBuilderA.getSmallIcon() == null) {
                int identifier5 = context.getResources().getIdentifier("notification_icon", "drawable", context.getPackageName());
                if (identifier5 > 0) {
                    xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(identifier5));
                } else {
                    xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(context.getApplicationInfo().icon));
                }
            }
            int iS = fVar.s();
            String strQ = fVar.q();
            Integer layoutIconId = null;
            if (xGPushNotificationBuilderA instanceof XGCustomPushNotificationBuilder) {
                layoutIconId = ((XGCustomPushNotificationBuilder) xGPushNotificationBuilderA).getLayoutIconId();
            }
            if (strQ != null && !TextUtils.isEmpty(strQ)) {
                if (iS <= 0) {
                    int identifier6 = context.getResources().getIdentifier(strQ, "drawable", context.getPackageName());
                    if (identifier6 > 0) {
                        xGPushNotificationBuilderA.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), identifier6));
                        if (layoutIconId != null) {
                            ((XGCustomPushNotificationBuilder) xGPushNotificationBuilderA).setLayoutIconDrawableId(identifier6);
                        }
                    } else {
                        xGPushNotificationBuilderA.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), context.getApplicationInfo().icon));
                    }
                } else {
                    a(strQ, xGPushNotificationBuilderA, context, layoutIconId);
                }
            } else if (xGPushNotificationBuilderA.getLargeIcon() == null && xGPushNotificationBuilderA.getNotificationLargeIcon() == null) {
                xGPushNotificationBuilderA.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), context.getApplicationInfo().icon));
            }
        }
        if (fVar.n() > 0) {
            xGPushNotificationBuilderA.setIcon(Integer.valueOf(fVar.n()));
        }
        if (xGPushNotificationBuilderA.getSmallIcon() == null && xGPushNotificationBuilderA.getLargeIcon() == null && xGPushNotificationBuilderA.getIcon() == null) {
            int identifier7 = context.getResources().getIdentifier("notification_icon", "drawable", context.getPackageName());
            if (identifier7 > 0) {
                xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(identifier7));
            } else {
                xGPushNotificationBuilderA.setSmallIcon(Integer.valueOf(context.getApplicationInfo().icon));
            }
            xGPushNotificationBuilderA.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), context.getApplicationInfo().icon));
        }
        xGPushNotificationBuilderA.setTitle(fVar.e());
        xGPushNotificationBuilderA.setTickerText(fVar.f());
        boolean z = false;
        String strG = fVar.g();
        if (!t.c(strG) && !"{}".equalsIgnoreCase(strG)) {
            z = true;
        }
        Intent intentA = a(context, gVarM, z, nVar);
        if (intentA == null) {
            com.tencent.android.tpush.a.a.i("MessageHelper", "intent is null");
            return;
        }
        if (z) {
            intentA.putExtra("custom_content", fVar.g());
        }
        intentA.putExtra(Constants.TAG_TPUSH_MESSAGE, "true");
        intentA.putExtra("title", Rijndael.encrypt(fVar.e()));
        intentA.putExtra("content", Rijndael.encrypt(fVar.f()));
        if (fVar.g() != null) {
            intentA.putExtra("custom_content", Rijndael.encrypt(fVar.g()));
        }
        intentA.putExtra(MessageKey.MSG_ID, nVar.b());
        intentA.putExtra("accId", nVar.c());
        intentA.putExtra(MessageKey.MSG_BUSI_MSG_ID, nVar.d());
        intentA.putExtra(MessageKey.MSG_CREATE_TIMESTAMPS, nVar.e());
        intentA.putExtra(MessageKey.MSG_PORTECT_TAG, Rijndael.encrypt(Constants.MAIN_VERSION_TAG + (System.currentTimeMillis() - 1000)));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int iL = fVar.l();
        int iB = iL <= 0 ? b(context, fVar.h()) : iL;
        if (iB == -1) {
            notificationManager.cancelAll();
        }
        intentA.putExtra(MessageKey.NOTIFACTION_ID, iB);
        int i = 134217728;
        if (gVarM.c != null && gVarM.c.b > 0) {
            i = gVarM.c.b;
        }
        if (a == null) {
            a = new c();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".APP_PUSH_CANCELLED.RESULT");
            context.getApplicationContext().registerReceiver(a, intentFilter);
        }
        Intent intent = new Intent(context.getPackageName() + ".APP_PUSH_CANCELLED.RESULT");
        intent.putExtra(Constants.FLAG_PACK_NAME, context.getPackageName());
        intent.putExtra("action", 2);
        intent.putExtra(Constants.FLAG_CLICK_TIME, System.currentTimeMillis() / 1000);
        intent.setPackage(context.getPackageName());
        intent.putExtras(intentA);
        if (Build.VERSION.SDK_INT == 19) {
            PendingIntent.getActivity(context.getApplicationContext(), iB, intentA, i).cancel();
        }
        xGPushNotificationBuilderA.setContentIntent(PendingIntent.getActivity(context.getApplicationContext(), iB, intentA, i));
        if (Build.VERSION.SDK_INT >= 16 && (identifier2 = context.getResources().getIdentifier("xg_notification", "layout", context.getPackageName())) != 0) {
            int identifier8 = context.getResources().getIdentifier("xg_notification_icon", "id", context.getPackageName());
            int identifier9 = context.getResources().getIdentifier("xg_notification_style_title", "id", context.getPackageName());
            int identifier10 = context.getResources().getIdentifier("xg_notification_date", "id", context.getPackageName());
            int identifier11 = context.getResources().getIdentifier("xg_notification_style", "id", context.getPackageName());
            int identifier12 = context.getResources().getIdentifier("xg_notification_style_content", "id", context.getPackageName());
            if (identifier8 != 0 && identifier9 != 0 && identifier10 != 0 && identifier12 != 0) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), identifier2);
                remoteViews.setTextViewText(identifier9, fVar.e());
                remoteViews.setTextViewText(identifier12, fVar.f());
                remoteViews.setTextViewText(identifier10, String.valueOf(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
                remoteViews.setImageViewResource(identifier8, context.getApplicationInfo().icon);
                remoteViews.setViewVisibility(identifier11, 0);
                xGPushNotificationBuilderA.setContentView(remoteViews);
            }
        }
        Notification notificationBuildNotification = xGPushNotificationBuilderA.buildNotification(context);
        notificationBuildNotification.deleteIntent = PendingIntent.getBroadcast(context.getApplicationContext(), iB, intent, i);
        XGPushNotifactionCallback notifactionCallback = XGPushManager.getNotifactionCallback();
        if (notifactionCallback == null) {
            notificationManager.notify(iB, notificationBuildNotification);
        } else {
            com.tencent.android.tpush.a.a.e("MessageHelper", "call notifactionCallback:" + notificationBuildNotification);
            notifactionCallback.handleNotify(new XGNotifaction(context, iB, notificationBuildNotification, fVar));
        }
        Intent intent2 = new Intent(Constants.ACTION_FEEDBACK);
        intent2.putExtra(Constants.FEEDBACK_ERROR_CODE, 0);
        intent2.setPackage(context.getPackageName());
        intent2.putExtras(intentA);
        intent2.putExtra(Constants.FEEDBACK_TAG, 5);
        intent2.putExtra(MessageKey.NOTIFACTION_ID, iB);
        context.sendBroadcast(intent2);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0080 A[Catch: IOException -> 0x0097, TryCatch #0 {IOException -> 0x0097, blocks: (B:8:0x007b, B:10:0x0080, B:12:0x0085, B:14:0x008a, B:16:0x008f), top: B:96:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:12:0x0085 A[Catch: IOException -> 0x0097, TryCatch #0 {IOException -> 0x0097, blocks: (B:8:0x007b, B:10:0x0080, B:12:0x0085, B:14:0x008a, B:16:0x008f), top: B:96:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:14:0x008a A[Catch: IOException -> 0x0097, TryCatch #0 {IOException -> 0x0097, blocks: (B:8:0x007b, B:10:0x0080, B:12:0x0085, B:14:0x008a, B:16:0x008f), top: B:96:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:16:0x008f A[Catch: IOException -> 0x0097, TRY_LEAVE, TryCatch #0 {IOException -> 0x0097, blocks: (B:8:0x007b, B:10:0x0080, B:12:0x0085, B:14:0x008a, B:16:0x008f), top: B:96:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:52:0x0105 A[Catch: IOException -> 0x011d, TryCatch #14 {IOException -> 0x011d, blocks: (B:50:0x0100, B:52:0x0105, B:54:0x010a, B:56:0x010f, B:58:0x0114), top: B:104:0x0100 }] */
    /* JADX WARN: Code duplicated, block: B:54:0x010a A[Catch: IOException -> 0x011d, TryCatch #14 {IOException -> 0x011d, blocks: (B:50:0x0100, B:52:0x0105, B:54:0x010a, B:56:0x010f, B:58:0x0114), top: B:104:0x0100 }] */
    /* JADX WARN: Code duplicated, block: B:56:0x010f A[Catch: IOException -> 0x011d, TryCatch #14 {IOException -> 0x011d, blocks: (B:50:0x0100, B:52:0x0105, B:54:0x010a, B:56:0x010f, B:58:0x0114), top: B:104:0x0100 }] */
    /* JADX WARN: Code duplicated, block: B:58:0x0114 A[Catch: IOException -> 0x011d, TRY_LEAVE, TryCatch #14 {IOException -> 0x011d, blocks: (B:50:0x0100, B:52:0x0105, B:54:0x010a, B:56:0x010f, B:58:0x0114), top: B:104:0x0100 }] */
    /* JADX WARN: Code duplicated, block: B:66:0x012c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:67:0x012e A[Catch: IOException -> 0x0145, TryCatch #8 {IOException -> 0x0145, blocks: (B:65:0x0129, B:67:0x012e, B:69:0x0133, B:71:0x0138, B:73:0x013d), top: B:98:0x0129 }] */
    /* JADX WARN: Code duplicated, block: B:69:0x0133 A[Catch: IOException -> 0x0145, TryCatch #8 {IOException -> 0x0145, blocks: (B:65:0x0129, B:67:0x012e, B:69:0x0133, B:71:0x0138, B:73:0x013d), top: B:98:0x0129 }] */
    /* JADX WARN: Code duplicated, block: B:71:0x0138 A[Catch: IOException -> 0x0145, TryCatch #8 {IOException -> 0x0145, blocks: (B:65:0x0129, B:67:0x012e, B:69:0x0133, B:71:0x0138, B:73:0x013d), top: B:98:0x0129 }] */
    /* JADX WARN: Code duplicated, block: B:73:0x013d A[Catch: IOException -> 0x0145, TRY_LEAVE, TryCatch #8 {IOException -> 0x0145, blocks: (B:65:0x0129, B:67:0x012e, B:69:0x0133, B:71:0x0138, B:73:0x013d), top: B:98:0x0129 }] */
    /* JADX WARN: Code duplicated, block: B:98:0x0129 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9 */
    private static void a(String str, XGPushNotificationBuilder xGPushNotificationBuilder, Context context, Integer num) throws Throwable {
        ?? r3;
        InputStream content;
        HttpGet httpGet;
        ?? r2;
        InputStream inputStream;
        HttpGet httpGet2;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        HttpEntity httpEntity = null;
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setSoTimeout(basicHttpParams, PushConstants.WORK_RECEIVER_EVENTCORE_ERROR);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, PushConstants.WORK_RECEIVER_EVENTCORE_ERROR);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 4000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 4000);
        ConnManagerParams.setTimeout(basicHttpParams, 4000L);
        InputStream inputStream2 = null;
        ByteArrayOutputStream byteArrayOutputStream3 = null;
        HttpEntity httpEntity2 = null;
        try {
            URL url = new URL(str);
            httpGet = new HttpGet(url.toURI());
            try {
                httpGet.addHeader("X-Online-Host", url.getHost());
                defaultHttpClient.getParams().setParameter("http.socket.timeout", 20000);
                defaultHttpClient.getParams().setParameter("http.connection.timeout", 20000);
                HttpResponse httpResponseExecute = defaultHttpClient.execute(httpGet);
                if (httpResponseExecute.getStatusLine().getStatusCode() != 200) {
                    xGPushNotificationBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), context.getApplicationInfo().icon));
                    if (0 != 0) {
                        try {
                            httpEntity2.consumeContent();
                            if (0 != 0) {
                                inputStream2.close();
                            }
                            if (0 != 0) {
                                byteArrayOutputStream3.close();
                            }
                            if (httpGet != null) {
                                httpGet.abort();
                            }
                            if (defaultHttpClient != null) {
                                defaultHttpClient.getConnectionManager().shutdown();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (0 != 0) {
                            inputStream2.close();
                        }
                        if (0 != 0) {
                            byteArrayOutputStream3.close();
                        }
                        if (httpGet != null) {
                            httpGet.abort();
                        }
                        if (defaultHttpClient != null) {
                            defaultHttpClient.getConnectionManager().shutdown();
                        }
                    }
                } else {
                    HttpEntity entity = httpResponseExecute.getEntity();
                    if (entity != null) {
                        try {
                            content = entity.getContent();
                            try {
                                byteArrayOutputStream = new ByteArrayOutputStream();
                                try {
                                    byte[] bArr = new byte[WXMediaMessage.DESCRIPTION_LENGTH_LIMIT];
                                    while (true) {
                                        int i = content.read(bArr);
                                        if (i == -1) {
                                            break;
                                        } else {
                                            byteArrayOutputStream.write(bArr, 0, i);
                                        }
                                    }
                                    httpEntity = null;
                                    Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
                                    xGPushNotificationBuilder.setLargeIcon(bitmapDecodeByteArray);
                                    byteArrayOutputStream2 = byteArrayOutputStream;
                                    if (num != null) {
                                        ((XGCustomPushNotificationBuilder) xGPushNotificationBuilder).setLayoutIconDrawableBmp(bitmapDecodeByteArray);
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    httpEntity = entity;
                                    r2 = byteArrayOutputStream;
                                    inputStream = content;
                                    httpGet2 = httpGet;
                                    try {
                                        e.printStackTrace();
                                        if (httpEntity != null) {
                                            try {
                                                httpEntity.consumeContent();
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                                return;
                                            }
                                        }
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        if (r2 != 0) {
                                            r2.close();
                                        }
                                        if (httpGet2 != null) {
                                            httpGet2.abort();
                                        }
                                        if (defaultHttpClient != null) {
                                            defaultHttpClient.getConnectionManager().shutdown();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th) {
                                        th = th;
                                        httpGet = httpGet2;
                                        content = inputStream;
                                        r3 = r2;
                                        if (httpEntity != null) {
                                            try {
                                                httpEntity.consumeContent();
                                                if (content != null) {
                                                    content.close();
                                                }
                                                if (r3 != 0) {
                                                    r3.close();
                                                }
                                                if (httpGet != null) {
                                                    httpGet.abort();
                                                }
                                                if (defaultHttpClient != null) {
                                                    defaultHttpClient.getConnectionManager().shutdown();
                                                }
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                                throw th;
                                            }
                                        } else {
                                            if (content != null) {
                                                content.close();
                                            }
                                            if (r3 != 0) {
                                                r3.close();
                                            }
                                            if (httpGet != null) {
                                                httpGet.abort();
                                            }
                                            if (defaultHttpClient != null) {
                                                defaultHttpClient.getConnectionManager().shutdown();
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    httpEntity = entity;
                                    r3 = byteArrayOutputStream;
                                    if (httpEntity != null) {
                                        httpEntity.consumeContent();
                                        if (content != null) {
                                            content.close();
                                        }
                                        if (r3 != 0) {
                                            r3.close();
                                        }
                                        if (httpGet != null) {
                                            httpGet.abort();
                                        }
                                        if (defaultHttpClient != null) {
                                            defaultHttpClient.getConnectionManager().shutdown();
                                        }
                                    } else {
                                        if (content != null) {
                                            content.close();
                                        }
                                        if (r3 != 0) {
                                            r3.close();
                                        }
                                        if (httpGet != null) {
                                            httpGet.abort();
                                        }
                                        if (defaultHttpClient != null) {
                                            defaultHttpClient.getConnectionManager().shutdown();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Exception e5) {
                                e = e5;
                                inputStream = content;
                                httpGet2 = httpGet;
                                r2 = 0;
                                httpEntity = entity;
                            } catch (Throwable th3) {
                                th = th3;
                                r3 = 0;
                                httpEntity = entity;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            inputStream = null;
                            httpGet2 = httpGet;
                            r2 = 0;
                            httpEntity = entity;
                        } catch (Throwable th4) {
                            th = th4;
                            r3 = 0;
                            content = null;
                            httpEntity = entity;
                        }
                    } else {
                        byteArrayOutputStream2 = null;
                        content = null;
                    }
                    if (entity != null) {
                        try {
                            byteArrayOutputStream2 = byteArrayOutputStream;
                            entity.consumeContent();
                            if (content != null) {
                                content.close();
                            }
                            if (byteArrayOutputStream2 != null) {
                                byteArrayOutputStream2.close();
                            }
                            if (httpGet != null) {
                                httpGet.abort();
                            }
                            if (defaultHttpClient != null) {
                                defaultHttpClient.getConnectionManager().shutdown();
                            }
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    } else {
                        if (content != null) {
                            content.close();
                        }
                        if (byteArrayOutputStream2 != null) {
                            byteArrayOutputStream2.close();
                        }
                        if (httpGet != null) {
                            httpGet.abort();
                        }
                        if (defaultHttpClient != null) {
                            defaultHttpClient.getConnectionManager().shutdown();
                        }
                    }
                }
            } catch (Exception e8) {
                e = e8;
                r2 = httpEntity;
                inputStream = httpEntity;
                httpGet2 = httpGet;
            } catch (Throwable th5) {
                th = th5;
                r3 = httpEntity;
                content = httpEntity;
            }
        } catch (Exception e9) {
            e = e9;
            r2 = 0;
            inputStream = null;
            httpGet2 = null;
        } catch (Throwable th6) {
            th = th6;
            r3 = 0;
            content = null;
            httpGet = null;
        }
    }

    public static void b(Context context, n nVar) {
        if (nVar.g() instanceof f) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.f("MessageHelper", "Action -> showNotification " + nVar.f());
            }
            f fVar = (f) nVar.g();
            if (fVar == null || fVar.m() == null) {
                com.tencent.android.tpush.a.a.i("MessageHelper", "showNotification holder == null || holder.getAction() == null");
            } else {
                a(context, nVar);
            }
        }
    }

    private static synchronized int b(Context context, int i) {
        int iA;
        Throwable th;
        try {
            String str = "_XINGE_NOTIF_NUMBER_" + String.valueOf(i);
            iA = com.tencent.android.tpush.common.n.a(context, str, 0);
            if (iA >= 2147483646) {
                iA = 0;
            }
            try {
                com.tencent.android.tpush.common.n.b(context, str, iA + 1);
            } catch (Throwable th2) {
                th = th2;
                com.tencent.android.tpush.a.a.c("MessageHelper", Constants.MAIN_VERSION_TAG, th);
            }
        } catch (Throwable th3) {
            iA = 0;
            th = th3;
        }
        return iA;
    }
}
