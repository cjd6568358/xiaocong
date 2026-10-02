package com.xiaomi.mipush.sdk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import com.xiaomi.push.service.aw;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PushMessageHandler extends IntentService {
    private static List<MiPushClient.MiPushClientCallback> a = new ArrayList();

    interface a extends Serializable {
    }

    public PushMessageHandler() {
        super("mipush message handler");
    }

    protected static void a() {
        synchronized (a) {
            a.clear();
        }
    }

    public static void a(long j, String str, String str2) {
        synchronized (a) {
            Iterator<MiPushClient.MiPushClientCallback> it = a.iterator();
            while (it.hasNext()) {
                it.next().onInitializeResult(j, str, str2);
            }
        }
    }

    public static void a(Context context, MiPushMessage miPushMessage) {
        synchronized (a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : a) {
                if (a(miPushMessage.getCategory(), miPushClientCallback.getCategory())) {
                    miPushClientCallback.onReceiveMessage(miPushMessage.getContent(), miPushMessage.getAlias(), miPushMessage.getTopic(), miPushMessage.isNotified());
                    miPushClientCallback.onReceiveMessage(miPushMessage);
                }
            }
        }
    }

    public static void a(Context context, a aVar) {
        String str = null;
        if (aVar instanceof MiPushMessage) {
            a(context, (MiPushMessage) aVar);
            return;
        }
        if (aVar instanceof MiPushCommandMessage) {
            MiPushCommandMessage miPushCommandMessage = (MiPushCommandMessage) aVar;
            String command = miPushCommandMessage.getCommand();
            if ("register".equals(command)) {
                List<String> commandArguments = miPushCommandMessage.getCommandArguments();
                if (commandArguments != null && !commandArguments.isEmpty()) {
                    str = commandArguments.get(0);
                }
                a(miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), str);
                return;
            }
            if ("set-alias".equals(command) || "unset-alias".equals(command) || "accept-time".equals(command)) {
                a(context, miPushCommandMessage.getCategory(), command, miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), miPushCommandMessage.getCommandArguments());
                return;
            }
            if ("subscribe-topic".equals(command)) {
                List<String> commandArguments2 = miPushCommandMessage.getCommandArguments();
                a(context, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), (commandArguments2 == null || commandArguments2.isEmpty()) ? null : commandArguments2.get(0));
            } else if ("unsubscibe-topic".equals(command)) {
                List<String> commandArguments3 = miPushCommandMessage.getCommandArguments();
                b(context, miPushCommandMessage.getCategory(), miPushCommandMessage.getResultCode(), miPushCommandMessage.getReason(), (commandArguments3 == null || commandArguments3.isEmpty()) ? null : commandArguments3.get(0));
            }
        }
    }

    protected static void a(Context context, String str, long j, String str2, String str3) {
        synchronized (a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : a) {
                if (a(str, miPushClientCallback.getCategory())) {
                    miPushClientCallback.onSubscribeResult(j, str2, str3);
                }
            }
        }
    }

    protected static void a(Context context, String str, String str2, long j, String str3, List<String> list) {
        synchronized (a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : a) {
                if (a(str, miPushClientCallback.getCategory())) {
                    miPushClientCallback.onCommandResult(str2, j, str3, list);
                }
            }
        }
    }

    protected static boolean a(String str, String str2) {
        return (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) || TextUtils.equals(str, str2);
    }

    protected static void b(Context context, String str, long j, String str2, String str3) {
        synchronized (a) {
            for (MiPushClient.MiPushClientCallback miPushClientCallback : a) {
                if (a(str, miPushClientCallback.getCategory())) {
                    miPushClientCallback.onUnsubscribeResult(j, str2, str3);
                }
            }
        }
    }

    public static boolean b() {
        return a.isEmpty();
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        ResolveInfo next;
        try {
            if ("com.xiaomi.mipush.sdk.WAKEUP".equals(intent.getAction())) {
                if (com.xiaomi.mipush.sdk.a.a(this).i()) {
                    u.a(this).a();
                    String stringExtra = intent.getStringExtra("waker_pkgname");
                    JSONObject jSONObject = new JSONObject();
                    if (TextUtils.isEmpty(stringExtra)) {
                        jSONObject.put("wakerPkgName", stringExtra);
                    }
                    jSONObject.put("selfPkgName", getPackageName());
                    aw.a().a("system_running_log", "wake_up_app", 1L, jSONObject.toString());
                    return;
                }
                return;
            }
            if (1 == PushMessageHelper.getPushMode(this)) {
                if (b()) {
                    com.xiaomi.channel.commonutils.logger.b.d("receive a message before application calling initialize");
                    return;
                }
                a aVarA = s.a(this).a(intent);
                if (aVarA != null) {
                    a(this, aVarA);
                    return;
                }
                return;
            }
            Intent intent2 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
            intent2.setPackage(getPackageName());
            intent2.putExtras(intent);
            try {
                List<ResolveInfo> listQueryBroadcastReceivers = getPackageManager().queryBroadcastReceivers(intent2, 32);
                if (listQueryBroadcastReceivers == null) {
                    next = null;
                    break;
                }
                Iterator<ResolveInfo> it = listQueryBroadcastReceivers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (next.activityInfo != null && next.activityInfo.packageName.equals(getPackageName())) {
                        break;
                    }
                }
                if (next != null) {
                    ((PushMessageReceiver) Class.forName(next.activityInfo.name).newInstance()).onReceive(getApplicationContext(), intent2);
                } else {
                    com.xiaomi.channel.commonutils.logger.b.d("cannot find the receiver to handler this message, check your manifest");
                }
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
        }
    }
}
