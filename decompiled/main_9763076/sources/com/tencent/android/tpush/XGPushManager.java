package com.tencent.android.tpush;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.data.MessageId;
import com.tencent.android.tpush.encrypt.Rijndael;
import com.tencent.android.tpush.service.XGPushServiceV3;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushMsg;
import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XGPushManager {
    public static final String ENABLE_SERVICE_SUFFIX = ".enableService";
    public static final int OPERATION_FAIL = 1;
    public static final int OPERATION_REQ_REGISTER = 100;
    public static final int OPERATION_REQ_UNREGISTER = 101;
    public static final int OPERATION_SUCCESS = 0;
    private static final String a = XGPushManager.class.getSimpleName();
    public static Map lastSuccessRegisterMap = new HashMap();
    private static Context b = null;
    private static XGPushNotifactionCallback c = null;
    public static int enableService = -1;
    private static Long d = 0L;
    private static Map e = new ConcurrentHashMap();

    private XGPushManager() {
    }

    public static Context getContext() {
        return b;
    }

    public static void setContext(Context context) {
        if (b == null && context != null) {
            b = context.getApplicationContext();
        }
    }

    public static void startPushService(Context context) {
        if (context != null) {
            setContext(context);
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.f(a, context.getPackageName() + "call start Push Service");
            }
            com.tencent.android.tpush.common.t.g(context);
            if (com.tencent.android.tpush.common.t.c(context) == 0) {
                com.tencent.android.tpush.common.t.e(context);
            }
        }
    }

    static void a(Context context) {
        if (context != null) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.f(a, context.getPackageName() + " call stop Push Service");
            }
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), XGPushServiceV3.class);
            context.stopService(intent);
        }
    }

    public static int getServiceStatus(Context context) {
        if (context != null) {
            return com.tencent.android.tpush.common.t.c(context);
        }
        return 0;
    }

    public static void registerPush(Context context) {
        registerPush(context, new u());
    }

    public static void registerPush(Context context, XGIOperateCallback xGIOperateCallback) {
        if (xGIOperateCallback == null) {
            throw new IllegalArgumentException("The callback parameter can not be null!");
        }
        a(context, null, null, -1, null, xGIOperateCallback, -1L, null);
    }

    public static void registerPush(Context context, String str, XGIOperateCallback xGIOperateCallback) {
        if (xGIOperateCallback == null) {
            throw new IllegalArgumentException("The callback parameter can not be null!");
        }
        registerPush(context, str, PushConstants.PUSH_TYPE_NOTIFY, 0, null, xGIOperateCallback);
    }

    public static void registerPush(Context context, String str) {
        if (context == null || str == null) {
            com.tencent.android.tpush.a.a.j(Constants.LogTag, "the parameter context or account of registerPush is invalid.");
        } else {
            registerPush(context, str, new ab());
        }
    }

    public static void bindAccount(Context context, String str, XGIOperateCallback xGIOperateCallback) {
        if (context == null || str == null) {
            com.tencent.android.tpush.a.a.j(Constants.LogTag, "the parameter context or account of registerPush is invalid.");
        } else {
            registerPush(context, str, PushConstants.PUSH_TYPE_NOTIFY, 32, null, xGIOperateCallback);
        }
    }

    public static void bindAccount(Context context, String str) {
        if (context == null || str == null) {
            com.tencent.android.tpush.a.a.j(Constants.LogTag, "the parameter context or account of registerPush is invalid.");
        } else {
            bindAccount(context, str, new ac(str));
        }
    }

    public static void appendAccount(Context context, String str, XGIOperateCallback xGIOperateCallback) {
        if (context == null || str == null) {
            com.tencent.android.tpush.a.a.j(Constants.LogTag, "the parameter context or account of registerPush is invalid.");
        } else {
            registerPush(context, str, PushConstants.PUSH_TYPE_NOTIFY, 48, null, xGIOperateCallback);
        }
    }

    public static void appendAccount(Context context, String str) {
        appendAccount(context, str, new ad(str));
    }

    public static void delAccount(Context context, String str, XGIOperateCallback xGIOperateCallback) {
        if (context == null || str == null) {
            com.tencent.android.tpush.a.a.j(Constants.LogTag, "the parameter context or account of registerPush is invalid.");
        } else {
            com.tencent.android.tpush.a.a.f(Constants.LogTag, "Action delAccout " + str);
            registerPush(context, str, PushConstants.PUSH_TYPE_NOTIFY, 16, null, xGIOperateCallback);
        }
    }

    public static void delAccount(Context context, String str) {
        delAccount(context, str, new ae(str));
    }

    public static void delAllAccount(Context context) {
        delAllAccount(context, new af());
    }

    public static void delAllAccount(Context context, XGIOperateCallback xGIOperateCallback) {
        delAccount(context, "*", xGIOperateCallback);
    }

    public static void registerPush(Context context, String str, String str2, int i, String str3, XGIOperateCallback xGIOperateCallback) {
        if (xGIOperateCallback == null) {
            throw new IllegalArgumentException("The callback parameter can not be null!");
        }
        if (context == null || com.tencent.android.tpush.common.t.c(str) || com.tencent.android.tpush.common.t.c(str2) || i < 0) {
            xGIOperateCallback.onFail(null, 10001, "The context, account, ticket or ticketType is(are) invalid!");
        } else {
            a(context, str, str2, i, str3, xGIOperateCallback, -1L, null);
        }
    }

    public static void unregisterPush(Context context) {
        if (context == null) {
            com.tencent.android.tpush.a.a.i(Constants.LogTag, "the context of unregisterPush is null");
        } else {
            unregisterPush(context, new ag());
        }
    }

    public static void unregisterPush(Context context, XGIOperateCallback xGIOperateCallback) {
        a(context, xGIOperateCallback, XGPushConfig.getAccessId(context), XGPushConfig.getAccessKey(context));
    }

    static void a(Context context, XGIOperateCallback xGIOperateCallback, long j, String str) {
        if (context == null) {
            setContext(context);
            if (xGIOperateCallback != null) {
                xGIOperateCallback.onFail(null, 10001, "The context parameter can not be null!");
                return;
            }
            throw new IllegalArgumentException("The context parameter can not be null!");
        }
        com.tencent.android.tpush.common.g.a().a(new ah(context.getApplicationContext(), xGIOperateCallback, j, str));
    }

    public static void setTag(Context context, String str) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(a, "Action -> setTag with tag = " + str);
        }
        a(context, str, 1, -1L);
    }

    public static void setKeyValueTag(Context context, String str, String str2) {
        if (context == null || str == null || str.trim().length() == 0 || str2 == null || str2.trim().length() == 0) {
            com.tencent.android.tpush.a.a.j(a, "setKeyValueTag context or tagKey or tagValue invalid.");
            return;
        }
        String str3 = str + "::::" + str2;
        com.tencent.android.tpush.a.a.f(a, "Action -> setKeyValueTag with tag = " + str3);
        a(context, str3, 3, -1L);
    }

    public static void sendCommReport2Service(Context context, String str, String str2) {
        long accessId = XGPushConfig.getAccessId(context);
        Intent intent = new Intent("com.tencent.android.tpush.action.COMM_REPORT.V3");
        intent.putExtra("type", 1L);
        intent.putExtra("accessId", Rijndael.encrypt(Constants.MAIN_VERSION_TAG + accessId));
        intent.putExtra(MessageKey.MSG_ID, 1000L);
        intent.putExtra("broadcastId", 0L);
        intent.putExtra("msgTimestamp", System.currentTimeMillis() / 1000);
        intent.putExtra("clientTimestamp", System.currentTimeMillis() / 1000);
        intent.putExtra("pkgName", context.getPackageName());
        intent.putExtra("msg", Rijndael.encrypt(str));
        intent.putExtra("ext", Rijndael.encrypt(str2));
        context.sendBroadcast(intent);
    }

    public static void deleteKeyValueTag(Context context, String str, String str2) {
        if (context == null || str == null || str.trim().length() == 0) {
            com.tencent.android.tpush.a.a.j(a, "deleteKeyValueTag context or tagKey invalid.");
            return;
        }
        String str3 = str + "::::" + str2;
        com.tencent.android.tpush.a.a.f(a, "Action -> deleteKeyValueTag with tag = " + str3);
        a(context, str3, 4, -1L);
    }

    public static void deleteTag(Context context, String str) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(a, "Action -> deleteTag with tag = " + str);
        }
        if (context == null || str == null || str.trim().length() == 0) {
            com.tencent.android.tpush.a.a.j(a, "context is null or tagName invalid.");
        } else {
            a(context, str, 2, -1L);
        }
    }

    static void a(Context context, String str, int i, long j) {
        if (context == null) {
            throw new IllegalArgumentException("The context parameter can not be null!");
        }
        if (com.tencent.android.tpush.common.t.a(context) <= 0) {
            if (str == null) {
                throw new IllegalArgumentException("The tagName parameter can not be null!");
            }
            if (j <= 0) {
                j = XGPushConfig.getAccessId(context);
            }
            if (j < 0) {
                throw new IllegalArgumentException("The accessId not set!");
            }
            Intent intent = new Intent("com.tencent.android.tpush.action.TAG.V3");
            intent.putExtra("accId", j);
            intent.putExtra(Constants.FLAG_PACK_NAME, Rijndael.encrypt(context.getPackageName()));
            intent.putExtra(Constants.FLAG_TAG_TYPE, i);
            intent.putExtra(Constants.FLAG_TAG_NAME, Rijndael.encrypt(str));
            context.sendBroadcast(intent);
        }
    }

    static XGPushClickedResult a(Activity activity) {
        Intent intent;
        String stringExtra;
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(Constants.LogTag, ">>> onActivityStarted activity=" + activity);
        }
        if (activity == null || activity.getIntent() == null || (stringExtra = (intent = activity.getIntent()).getStringExtra(Constants.TAG_TPUSH_MESSAGE)) == null || !stringExtra.equalsIgnoreCase("true") || com.tencent.android.tpush.common.t.a(activity.getApplicationContext()) > 0) {
            return null;
        }
        XGPushClickedResult xGPushClickedResult = new XGPushClickedResult();
        xGPushClickedResult.parseIntent(intent);
        intent.removeExtra(Constants.TAG_TPUSH_MESSAGE);
        com.tencent.android.tpush.common.g.a().a(new ai(activity, intent));
        return xGPushClickedResult;
    }

    public static boolean isNotificationOpened(Context context) {
        return com.tencent.android.tpush.service.e.d.a(context);
    }

    public static XGPushClickedResult onActivityStarted(Activity activity) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(Constants.PushMessageLogTag, ">>> onActivityStarted " + activity);
        }
        if (activity == null || activity.getIntent() == null || !com.tencent.android.tpush.common.t.h(activity)) {
            return null;
        }
        Intent intent = activity.getIntent();
        if (intent != null) {
            try {
                if (intent.hasExtra(Constants.TAG_TPUSH_NOTIFICATION)) {
                    Serializable serializableExtra = intent.getSerializableExtra(Constants.TAG_TPUSH_NOTIFICATION);
                    intent.removeExtra(Constants.TAG_TPUSH_NOTIFICATION);
                    if (serializableExtra != null && (serializableExtra instanceof XGPushClickedResult)) {
                        XGPushClickedResult xGPushClickedResult = (XGPushClickedResult) serializableExtra;
                        xGPushClickedResult.parseIntent(intent);
                        return xGPushClickedResult;
                    }
                }
            } catch (Exception e2) {
                com.tencent.android.tpush.a.a.c(a, "onActivityStarted", e2);
            }
        }
        return null;
    }

    public static long addLocalNotification(Context context, XGLocalMessage xGLocalMessage) {
        return a(context, xGLocalMessage, -1L);
    }

    static long a(Context context, XGLocalMessage xGLocalMessage, long j) {
        try {
            if (context == null || xGLocalMessage == null) {
                com.tencent.android.tpush.a.a.i(a, "addLocalNotification context == null or msg == null");
                return -1L;
            }
            if (!TpnsSecurity.checkTpnsSecurityLibSo(context)) {
                return -1L;
            }
            if (j <= 0) {
                j = XGPushConfig.getAccessId(context);
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            StringBuilder sb = new StringBuilder(64);
            sb.append(j).append(xGLocalMessage.getMsgId()).append(context.getPackageName()).append(TextUtils.isEmpty(xGLocalMessage.getTitle()) ? Constants.MAIN_VERSION_TAG : xGLocalMessage.getTitle()).append(TextUtils.isEmpty(xGLocalMessage.getContent()) ? Constants.MAIN_VERSION_TAG : xGLocalMessage.getContent());
            String custom_content = xGLocalMessage.getCustom_content();
            if (TextUtils.isEmpty(custom_content) || new JSONObject(custom_content).length() == 0) {
                custom_content = Constants.MAIN_VERSION_TAG;
            }
            sb.append(custom_content);
            if (xGLocalMessage.getType() == 1) {
                sb.append(TextUtils.isEmpty(xGLocalMessage.getUrl()) ? Constants.MAIN_VERSION_TAG : xGLocalMessage.getUrl()).append(TextUtils.isEmpty(xGLocalMessage.getIntent()) ? Constants.MAIN_VERSION_TAG : xGLocalMessage.getIntent()).append(TextUtils.isEmpty(xGLocalMessage.getActivity()) ? Constants.MAIN_VERSION_TAG : xGLocalMessage.getActivity());
            }
            String string = sb.toString();
            String str = Constants.LOCAL_MESSAGE_FLAG + com.tencent.android.tpush.encrypt.a.a(string);
            long expirationTimeMs = xGLocalMessage.getExpirationTimeMs();
            com.tencent.android.tpush.common.n.b(context, str, expirationTimeMs);
            com.tencent.android.tpush.a.a.e(Constants.LogTag, string + ",tag:" + str + ",exp:" + expirationTimeMs);
            TpnsPushMsg tpnsPushMsg = new TpnsPushMsg();
            tpnsPushMsg.msgId = xGLocalMessage.getMsgId();
            tpnsPushMsg.accessId = j;
            tpnsPushMsg.appPkgName = context.getPackageName();
            tpnsPushMsg.busiMsgId = xGLocalMessage.getBusiMsgId();
            tpnsPushMsg.timestamp = jCurrentTimeMillis / 1000;
            tpnsPushMsg.serverTime = -jCurrentTimeMillis;
            tpnsPushMsg.ttl = xGLocalMessage.getTtl();
            tpnsPushMsg.type = xGLocalMessage.getType();
            tpnsPushMsg.multiPkg = 0L;
            tpnsPushMsg.date = xGLocalMessage.getDate();
            tpnsPushMsg.content = "{\"title\":\"" + xGLocalMessage.getTitle() + "\",\"content\":\"" + a(xGLocalMessage.getContent()) + "\",\"builder_id\":" + xGLocalMessage.getBuilderId() + ",\"custom_content\":" + xGLocalMessage.getCustom_content() + ",\"ring\":" + xGLocalMessage.getRing() + ",\"vibrate\":" + xGLocalMessage.getVibrate() + ",\"lights\":" + xGLocalMessage.getLights() + ",\"n_id\":" + xGLocalMessage.getNotificationId() + ",\"ring_raw\":\"" + xGLocalMessage.getRing_raw() + "\",\"icon_type\":" + xGLocalMessage.getIcon_type() + ",\"icon_res\":\"" + xGLocalMessage.getIcon_res() + "\",\"style_id\":" + xGLocalMessage.getStyle_id() + ",\"small_icon\":\"" + xGLocalMessage.getSmall_icon() + "\",\"clearable\":1,\"accept_time\":[{\"start\":{\"hour\":\"" + xGLocalMessage.getHour() + "\",\"min\":\"" + xGLocalMessage.getMin() + "\"},\"end\":{\"hour\":\"23\",\"min\":\"59\"}}],\"action\":{\"action_type\":" + xGLocalMessage.getAction_type() + ",\"activity\":\"" + xGLocalMessage.getActivity() + "\",\"browser\":{\"url\":\"" + xGLocalMessage.getUrl() + "\"},\"intent\":\"" + xGLocalMessage.getIntent() + "\",\"package_name\":{\"packageDownloadUrl\":\"" + xGLocalMessage.getPackageDownloadUrl() + "\",\"packageName\":\"" + xGLocalMessage.getPackageName() + "\"}}}";
            com.tencent.android.tpush.service.channel.a aVar = new com.tencent.android.tpush.service.channel.a(0, "127.0.0.1");
            Intent intent = new Intent(Constants.ACTION_INTERNAL_PUSH_MESSAGE);
            intent.setPackage(tpnsPushMsg.appPkgName);
            intent.putExtra(MessageKey.MSG_ID, tpnsPushMsg.msgId);
            intent.putExtra("content", Rijndael.encrypt(tpnsPushMsg.content));
            intent.putExtra(MessageKey.MSG_DATE, tpnsPushMsg.date);
            intent.putExtra("type", tpnsPushMsg.type);
            intent.putExtra("accId", tpnsPushMsg.accessId);
            intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, tpnsPushMsg.busiMsgId);
            intent.putExtra(MessageKey.MSG_CREATE_TIMESTAMPS, tpnsPushMsg.timestamp);
            intent.putExtra(MessageKey.MSG_CREATE_MULTIPKG, tpnsPushMsg.multiPkg);
            intent.putExtra(MessageKey.MSG_SERVER_TIME, tpnsPushMsg.serverTime * 1000);
            intent.putExtra(MessageKey.MSG_TTL, tpnsPushMsg.ttl);
            intent.putExtra(MessageKey.MSG_SERVICE_ACK, true);
            intent.putExtra(MessageKey.MSG_EXTRA_HOST, com.tencent.android.tpush.service.e.m.c(aVar.d()));
            intent.putExtra(MessageKey.MSG_EXTRA_PORT, aVar.e());
            intent.putExtra(MessageKey.MSG_EXTRA_PACT, com.tencent.android.tpush.service.s.a(aVar.b()));
            intent.putExtra(MessageKey.MSG_EXTRA_PUSHTIME, jCurrentTimeMillis);
            com.tencent.android.tpush.b.i.a(context).b(intent);
            return -jCurrentTimeMillis;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "addLocalNotification ", th);
            return 0L;
        }
    }

    public static XGPushNotifactionCallback getNotifactionCallback() {
        return c;
    }

    public static void setNotifactionCallback(XGPushNotifactionCallback xGPushNotifactionCallback) {
        c = xGPushNotifactionCallback;
    }

    public static void clearLocalNotifications(Context context) {
        if (context == null) {
            com.tencent.android.tpush.a.a.i(a, "clearLocalNotifications  context==null.");
        } else if (com.tencent.android.tpush.common.t.a(context) <= 0) {
            com.tencent.android.tpush.common.g.a().a(new v(context.getApplicationContext()));
        }
    }

    static void a(Context context, Intent intent) {
        com.tencent.android.tpush.b.d.a().e(context, intent.getLongExtra(MessageKey.MSG_ID, -1L));
        Intent intent2 = new Intent("com.tencent.android.tpush.action.PUSH_CLICK.RESULT.V3");
        intent2.putExtras(intent);
        intent2.putExtra(Constants.FLAG_PACK_NAME, context.getPackageName());
        intent2.putExtra(Constants.FLAG_CLICK_TIME, System.currentTimeMillis() / 1000);
        com.tencent.android.tpush.service.d.a.e(context, intent2);
        context.sendBroadcast(intent2);
    }

    public static void onMessageClicked(Context context, XGPushTextMessage xGPushTextMessage) {
        a(context, xGPushTextMessage.a(), "com.tencent.android.tpush.action.PUSH_CLICK.RESULT.V3");
    }

    public static void onMessageCleared(Context context, XGPushTextMessage xGPushTextMessage) {
        a(context, xGPushTextMessage.a(), "com.tencent.android.tpush.action.PUSH_CANCELLED.RESULT.V3");
    }

    private static void a(Context context, Intent intent, String str) {
        if (context != null && intent != null && str != null) {
            Intent intent2 = new Intent(str);
            intent2.putExtras(intent);
            if ("com.tencent.android.tpush.action.PUSH_CANCELLED.RESULT.V3".equals(str)) {
                intent2.putExtra("action", 2);
            }
            intent2.putExtra(Constants.FLAG_PACK_NAME, context.getPackageName());
            intent2.putExtra(Constants.FLAG_CLICK_TIME, System.currentTimeMillis() / 1000);
            com.tencent.android.tpush.service.d.a.e(context, intent2);
            context.sendBroadcast(intent2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, Intent intent) {
        if (intent != null) {
            Intent intent2 = new Intent(Constants.ACTION_FEEDBACK);
            intent2.setPackage(context.getPackageName());
            intent2.putExtra(Constants.FEEDBACK_TAG, 4);
            intent2.putExtra(Constants.FEEDBACK_ERROR_CODE, 0);
            intent2.putExtras(intent);
            context.sendBroadcast(intent2);
        }
    }

    public static void onActivityStoped(Activity activity) {
        if (activity == null) {
        }
    }

    public static void setPushNotificationBuilder(Context context, int i, XGPushNotificationBuilder xGPushNotificationBuilder) {
        if (context == null) {
            throw new IllegalArgumentException("context is null.");
        }
        if (i < 1 || i > 4096) {
            throw new IllegalArgumentException("notificationBulderId不在范围[1, 4096].");
        }
        if (xGPushNotificationBuilder != null) {
            com.tencent.android.tpush.b.b.a(context, i, xGPushNotificationBuilder);
        }
    }

    public static void setDefaultNotificationBuilder(Context context, XGPushNotificationBuilder xGPushNotificationBuilder) {
        if (context != null && xGPushNotificationBuilder != null) {
            com.tencent.android.tpush.b.b.a(context, 0, xGPushNotificationBuilder);
        }
    }

    public static XGPushNotificationBuilder getDefaultNotificationBuilder(Context context) {
        XGPushNotificationBuilder notificationBuilder = getNotificationBuilder(context, 0);
        if (notificationBuilder == null) {
            com.tencent.android.tpush.b.b.a(context);
        }
        return notificationBuilder;
    }

    public static XGPushNotificationBuilder getNotificationBuilder(Context context, int i) {
        if (context != null) {
            return com.tencent.android.tpush.b.b.a(context, i);
        }
        Log.e(Constants.LogTag, "getNotificationBuilder  context == null");
        return null;
    }

    public static void cancelNotifaction(Context context, int i) {
        try {
            ((NotificationManager) context.getSystemService("notification")).cancel(i);
        } catch (Exception e2) {
        }
    }

    public static void cancelAllNotifaction(Context context) {
        try {
            ((NotificationManager) context.getSystemService("notification")).cancelAll();
        } catch (Exception e2) {
        }
    }

    public static void msgAck(Context context, com.tencent.android.tpush.b.n nVar) {
        if (context != null && nVar != null) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.a(a, "Action -> msgAck(" + context.getPackageName() + "," + nVar.b() + ")");
            }
            com.tencent.android.tpush.a.a.a(3, nVar.b());
            if (nVar.b() > 0) {
                MessageId messageIdA = com.tencent.android.tpush.b.d.a().a(context, context.getPackageName(), nVar.b());
                if (messageIdA == null) {
                    com.tencent.android.tpush.a.a.h(a, "Action -> msgAck(" + context.getPackageName() + "," + nVar.b() + ")error, no the id: " + nVar.b());
                    return;
                }
                Intent intent = new Intent("com.tencent.android.tpush.action.MSG_ACK.V3");
                intent.putExtra(MessageKey.MSG_ID, nVar.b());
                intent.putExtra(Constants.FLAG_PACK_NAME, context.getPackageName());
                intent.putExtra("MessageId", messageIdA);
                context.sendBroadcast(intent);
            }
        }
    }

    public static String getServiceTag(Context context) {
        return !TpnsSecurity.checkTpnsSecurityLibSo(context) ? "xg_service_enable" : Rijndael.encrypt(XGPushConfig.getAccessId(context) + ",xg_service_enable");
    }

    public static void enableService(Context context, boolean z) {
        if (context != null) {
            if (!z) {
                com.tencent.android.tpush.a.a.i(Constants.LogTag, "XG is disable.");
                unregisterPush(context, new w(context));
            }
            enableService = z ? 1 : 0;
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.f(a, "enableService=" + enableService);
            }
            com.tencent.android.tpush.common.m.a(context, context.getPackageName() + ENABLE_SERVICE_SUFFIX, enableService);
        }
    }

    static void a(Context context, String str, String str2, int i, String str3, XGIOperateCallback xGIOperateCallback, long j, String str4) {
        setContext(context);
        if (context == null) {
            xGIOperateCallback.onFail(null, 10001, "The context parameter can not be null!");
            return;
        }
        int i2 = i >> 4;
        if ((i2 <= 0 || i2 > 4) && Math.abs(System.currentTimeMillis() - d.longValue()) < 1000) {
            xGIOperateCallback.onFail(null, 10002, "duplicate register request");
            return;
        }
        d = Long.valueOf(System.currentTimeMillis());
        Long l = (Long) lastSuccessRegisterMap.get(context.getPackageName());
        if (l != null && Math.abs((System.currentTimeMillis() / 1000) - l.longValue()) < 3) {
            xGIOperateCallback.onFail(null, 10002, "duplicate register request");
        } else {
            com.tencent.android.tpush.common.g.a().a(new x(context, xGIOperateCallback, j, str4, str, i, str2, str3));
        }
    }

    static void a(Context context, Intent intent, XGIOperateCallback xGIOperateCallback, boolean z) {
        com.tencent.android.tpush.common.t.e(context);
        ak akVar = new ak(context, intent, xGIOperateCallback);
        try {
            context.registerReceiver(akVar, new IntentFilter("com.tencent.android.tpush.action.SERVICE_START.V3"));
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "Receiver not registered exception error : ", th);
        }
        al alVar = new al(context, intent, xGIOperateCallback);
        try {
            e.put(akVar, alVar);
            com.tencent.android.tpush.common.g.a().a(alVar, 10000L);
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "mapTimeRunnableOfMessage error", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void c(Context context, Intent intent, XGIOperateCallback xGIOperateCallback) {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.f(a, "Action -> Register to xinge server");
        }
        if (xGIOperateCallback != null) {
            try {
                context.registerReceiver(new y(xGIOperateCallback), new IntentFilter("com.tencent.android.tpush.action.REGISTER.RESULT.V3"));
            } catch (Throwable th) {
            }
        }
        context.sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(Context context, Intent intent, XGIOperateCallback xGIOperateCallback) {
        try {
            context.registerReceiver(new z(xGIOperateCallback), new IntentFilter("com.tencent.android.tpush.action.UNREGISTER.RESULT.V3"));
            context.sendBroadcast(intent);
        } catch (Throwable th) {
        }
    }

    private static String a(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            switch (cCharAt) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\"':
                case '/':
                case '\\':
                    sb.append('\\').append(cCharAt);
                    break;
                default:
                    if (cCharAt <= 31) {
                        sb.append(String.format("\\u%04x", Integer.valueOf(cCharAt)));
                    } else {
                        sb.append(cCharAt);
                    }
                    break;
            }
        }
        return sb.toString();
    }

    public static void openNotification(Context context) {
        com.tencent.android.tpush.common.t.l(context);
    }

    public static void openNotificationSettings(Context context) {
        com.tencent.android.tpush.common.t.m(context);
    }
}
