package com.meizu.cloud.pushsdk.handler.a.a;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.MPushMessage;
import com.meizu.cloud.pushsdk.notification.e;
import com.meizu.cloud.pushsdk.util.c;
import com.meizu.cloud.pushsdk.util.d;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends com.meizu.cloud.pushsdk.handler.a.a<MessageV3> {
    public a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public MessageV3 c(Intent intent) {
        MessageV3 messageV3;
        String str = null;
        try {
            DebugLogger.e("AbstractMessageHandler", "parse message V3");
            messageV3 = (MessageV3) intent.getParcelableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
            if (messageV3 == null) {
            }
        } catch (Exception e) {
            DebugLogger.e("AbstractMessageHandler", "cannot get messageV3");
            if (0 != 0) {
                messageV3 = null;
            }
        } finally {
            if (str == null) {
                DebugLogger.e("AbstractMessageHandler", "parse MessageV2 to MessageV3");
                MPushMessage mPushMessage = (MPushMessage) intent.getSerializableExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE);
                MessageV3.parse(g(intent), d(intent), mPushMessage.getTaskId(), mPushMessage);
            }
        }
        return messageV3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(MessageV3 messageV3, e eVar) {
        c.a(c(), messageV3.getPackageName(), 0);
        Intent intentA = a(c(), messageV3);
        if (intentA != null) {
            intentA.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            try {
                c().startActivity(intentA);
            } catch (Exception e) {
                DebugLogger.e("AbstractMessageHandler", "Click message StartActivity error " + e.getMessage());
            }
        }
        if (!TextUtils.isEmpty(messageV3.getTitle()) && !TextUtils.isEmpty(messageV3.getContent())) {
            b().a(c(), messageV3.getTitle(), messageV3.getContent(), a(messageV3.getWebUrl(), messageV3.getParamsMap()));
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationClickMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 64;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(MessageV3 messageV3) {
        d.e(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }

    private Intent a(Context context, MessageV3 messageV3) {
        Intent uri;
        if (messageV3.getClickType() == 0) {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(messageV3.getUploadDataPackageName());
            if (launchIntentForPackage != null && messageV3.getParamsMap() != null) {
                for (Map.Entry<String, String> entry : messageV3.getParamsMap().entrySet()) {
                    DebugLogger.i("AbstractMessageHandler", " launcher activity key " + entry.getKey() + " value " + entry.getValue());
                    if (!TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                        launchIntentForPackage.putExtra(entry.getKey(), b(entry.getValue()));
                    }
                }
                return launchIntentForPackage;
            }
            return launchIntentForPackage;
        }
        if (1 == messageV3.getClickType()) {
            String str = Constants.MAIN_VERSION_TAG;
            if (messageV3.getParamsMap() != null) {
                for (Map.Entry<String, String> entry2 : messageV3.getParamsMap().entrySet()) {
                    DebugLogger.i("AbstractMessageHandler", " key " + entry2.getKey() + " value " + entry2.getValue());
                    String str2 = (TextUtils.isEmpty(entry2.getKey()) || TextUtils.isEmpty(entry2.getValue())) ? str : str + "S." + entry2.getKey() + "=" + b(entry2.getValue()) + ";";
                    DebugLogger.i("AbstractMessageHandler", "paramValue " + str2);
                    str = str2;
                }
            }
            String str3 = "intent:#Intent;component=" + messageV3.getUploadDataPackageName() + "/" + messageV3.getActivity() + (TextUtils.isEmpty(str) ? ";" : ";" + str) + MessageKey.MSG_ACCEPT_TIME_END;
            DebugLogger.i("AbstractMessageHandler", "open activity intent uri " + str3);
            try {
                uri = Intent.parseUri(str3, 1);
            } catch (URISyntaxException e) {
                DebugLogger.e("AbstractMessageHandler", "parse Uri error " + e.getMessage());
                uri = null;
            }
            return uri;
        }
        if (2 == messageV3.getClickType()) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(messageV3.getWebUrl()));
            String uriPackageName = messageV3.getUriPackageName();
            if (!TextUtils.isEmpty(uriPackageName)) {
                intent.setPackage(uriPackageName);
                DebugLogger.i("AbstractMessageHandler", "set uri package " + uriPackageName);
                return intent;
            }
            return intent;
        }
        if (3 != messageV3.getClickType()) {
            return null;
        }
        DebugLogger.i("AbstractMessageHandler", "CLICK_TYPE_SELF_DEFINE_ACTION");
        return null;
    }

    private String b(String str) {
        try {
            str = URLEncoder.encode(str, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            DebugLogger.i("AbstractMessageHandler", "encode url fail");
        }
        Log.i("AbstractMessageHandler", "encode all value is " + str);
        return str;
    }
}
