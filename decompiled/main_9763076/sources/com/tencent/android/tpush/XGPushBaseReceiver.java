package com.tencent.android.tpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.data.MessageId;
import com.tencent.android.tpush.encrypt.Rijndael;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class XGPushBaseReceiver extends BroadcastReceiver {
    public static final int SUCCESS = 0;
    long a = 0;

    public abstract void onDeleteTagResult(Context context, int i, String str);

    public abstract void onNotifactionClickedResult(Context context, XGPushClickedResult xGPushClickedResult);

    public abstract void onNotifactionShowedResult(Context context, XGPushShowedResult xGPushShowedResult);

    public abstract void onRegisterResult(Context context, int i, XGPushRegisterResult xGPushRegisterResult);

    public abstract void onSetTagResult(Context context, int i, String str);

    public abstract void onTextMessage(Context context, XGPushTextMessage xGPushTextMessage);

    public abstract void onUnregisterResult(Context context, int i);

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (context != null && intent != null) {
            try {
                if (com.tencent.android.tpush.common.t.a(context) <= 0) {
                    String action = intent.getAction();
                    if (Constants.ACTION_PUSH_MESSAGE.equals(action)) {
                        a(context, intent);
                    } else if (Constants.ACTION_FEEDBACK.equals(action)) {
                        b(context, intent);
                    } else {
                        com.tencent.android.tpush.a.a.i(Constants.PushMessageLogTag, "未知的action:" + action);
                    }
                }
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(Constants.PushMessageLogTag, "onReceive handle error.", th);
            }
        }
    }

    private void a(Context context, Intent intent) {
        int intExtra = intent.getIntExtra(Constants.PUSH_CHANNEL, 0);
        if (intExtra == 0) {
            com.tencent.android.tpush.b.n nVarA = com.tencent.android.tpush.b.n.a(context, intent);
            if (nVarA.g().c() == 2) {
                XGPushTextMessage xGPushTextMessage = new XGPushTextMessage();
                xGPushTextMessage.title = nVarA.g().e();
                xGPushTextMessage.content = nVarA.g().f();
                xGPushTextMessage.customContent = nVarA.g().g();
                xGPushTextMessage.a(intent);
                onTextMessage(context, xGPushTextMessage);
                return;
            }
            return;
        }
        XGPushTextMessage xGPushTextMessage2 = new XGPushTextMessage();
        xGPushTextMessage2.pushChannel = intExtra;
        xGPushTextMessage2.content = intent.getStringExtra("content");
        xGPushTextMessage2.title = intent.getStringExtra("title");
        xGPushTextMessage2.customContent = intent.getStringExtra("custom_content");
        intent.putExtra("accId", XGPushConfig.getAccessId(context));
        c(context, intent);
        onTextMessage(context, xGPushTextMessage2);
    }

    private void b(Context context, Intent intent) {
        int intExtra = intent.getIntExtra(Constants.FEEDBACK_TAG, -1);
        int intExtra2 = intent.getIntExtra(Constants.FEEDBACK_ERROR_CODE, -1);
        switch (intExtra) {
            case 1:
                XGPushRegisterResult xGPushRegisterResult = new XGPushRegisterResult();
                if (intent.getIntExtra(Constants.PUSH_CHANNEL, 0) == 0) {
                    xGPushRegisterResult.parseIntent(intent);
                    a(context, intExtra2, xGPushRegisterResult, "SdkRegister");
                } else {
                    xGPushRegisterResult.h = intent.getIntExtra(Constants.PUSH_CHANNEL, 0);
                    xGPushRegisterResult.g = intent.getStringExtra(Constants.OTHER_PUSH_TOKEN);
                }
                onRegisterResult(context, intExtra2, xGPushRegisterResult);
                break;
            case 2:
                XGPushRegisterResult xGPushRegisterResult2 = new XGPushRegisterResult();
                xGPushRegisterResult2.parseIntent(intent);
                a(context, intExtra2, xGPushRegisterResult2, "SdkUnRegister");
                onUnregisterResult(context, intExtra2);
                break;
            case 3:
                String strDecrypt = Rijndael.decrypt(intent.getStringExtra(Constants.FLAG_TAG_NAME));
                if (!com.tencent.android.tpush.service.e.m.b(strDecrypt)) {
                    int intExtra3 = intent.getIntExtra(Constants.FLAG_TAG_TYPE, -1);
                    if (intExtra3 == 1) {
                        onSetTagResult(context, intExtra2, strDecrypt);
                    } else if (intExtra3 == 2) {
                        onDeleteTagResult(context, intExtra2, strDecrypt);
                    } else {
                        com.tencent.android.tpush.a.a.i(Constants.PushMessageLogTag, "错误的标签处理类型：" + intExtra3 + " ,标签名：" + strDecrypt);
                    }
                }
                break;
            case 4:
                intent.getIntExtra("action", 2);
                long longExtra = intent.getLongExtra("accId", 0L);
                List accessidList = XGPushConfig.getAccessidList(context);
                if (accessidList != null && accessidList.size() > 0 && accessidList.contains(Long.valueOf(longExtra))) {
                    XGPushClickedResult xGPushClickedResult = new XGPushClickedResult();
                    xGPushClickedResult.parseIntent(intent);
                    onNotifactionClickedResult(context, xGPushClickedResult);
                }
                if (intent.getIntExtra(Constants.PUSH_CHANNEL, 0) != 0) {
                    XGPushClickedResult xGPushClickedResult2 = new XGPushClickedResult();
                    xGPushClickedResult2.content = intent.getStringExtra("content");
                    xGPushClickedResult2.title = intent.getStringExtra("title");
                    xGPushClickedResult2.customContent = intent.getStringExtra("custom_content");
                    xGPushClickedResult2.pushChannel = intent.getIntExtra(Constants.PUSH_CHANNEL, 0);
                    xGPushClickedResult2.actionType = intent.getIntExtra("action", 0);
                    intent.putExtra("accId", XGPushConfig.getAccessId(context));
                    Intent intent2 = new Intent("com.tencent.android.tpush.action.PUSH_CLICK.RESULT.V3");
                    intent2.putExtras(intent);
                    context.sendBroadcast(intent2);
                    onNotifactionClickedResult(context, xGPushClickedResult2);
                }
                break;
            case 5:
                XGPushShowedResult xGPushShowedResult = new XGPushShowedResult();
                if (intent.getIntExtra(Constants.PUSH_CHANNEL, 0) == 0) {
                    xGPushShowedResult.parseIntent(intent);
                } else {
                    xGPushShowedResult.c = intent.getStringExtra("content");
                    xGPushShowedResult.b = intent.getStringExtra("title");
                    xGPushShowedResult.d = intent.getStringExtra("custom_content");
                    xGPushShowedResult.h = intent.getIntExtra(Constants.PUSH_CHANNEL, 0);
                    intent.putExtra("accId", XGPushConfig.getAccessId(context));
                    c(context, intent);
                }
                onNotifactionShowedResult(context, xGPushShowedResult);
                break;
            default:
                com.tencent.android.tpush.a.a.i(Constants.PushMessageLogTag, "未知的feedbackType:" + intExtra);
                break;
        }
    }

    private void c(Context context, Intent intent) {
        MessageId messageId = new MessageId();
        messageId.id = intent.getLongExtra(MessageKey.MSG_ID, 0L);
        messageId.isAck = (short) 0;
        messageId.accessId = intent.getLongExtra("accId", 0L);
        messageId.host = intent.getLongExtra(MessageKey.MSG_EXTRA_HOST, 0L);
        messageId.port = intent.getIntExtra(MessageKey.MSG_EXTRA_PORT, 0);
        messageId.pact = intent.getByteExtra(MessageKey.MSG_EXTRA_PACT, (byte) 0);
        messageId.apn = com.tencent.android.tpush.service.e.m.k(context);
        messageId.isp = com.tencent.android.tpush.service.e.m.l(context);
        messageId.pushTime = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
        messageId.serviceHost = intent.getStringExtra(MessageKey.MSG_SERVICE_PACKAGE_NAME);
        messageId.receivedTime = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
        messageId.pkgName = context.getPackageName();
        messageId.busiMsgId = intent.getLongExtra(MessageKey.MSG_BUSI_MSG_ID, 0L);
        messageId.timestamp = intent.getLongExtra(MessageKey.MSG_CREATE_TIMESTAMPS, 0L);
        messageId.msgType = intent.getLongExtra("type", 0L);
        messageId.multiPkg = intent.getLongExtra(MessageKey.MSG_CREATE_MULTIPKG, 0L);
        messageId.date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        intent.putExtra("MessageId", messageId);
        Intent intent2 = new Intent("com.tencent.android.tpush.action.MSG_ACK.V3");
        intent2.putExtras(intent);
        context.sendBroadcast(intent2);
    }

    private void a(Context context, int i, XGPushRegisterResult xGPushRegisterResult, String str) {
        JSONObject jSONObject;
        int i2;
        int i3;
        int i4 = xGPushRegisterResult.e >> 4;
        if (i4 <= 0 || i4 > 4) {
            JSONObject jSONObject2 = new JSONObject();
            String strA = com.tencent.android.tpush.service.e.h.a(context, "register_json", Constants.MAIN_VERSION_TAG);
            if (com.tencent.android.tpush.service.e.m.b(strA)) {
                jSONObject = jSONObject2;
            } else {
                try {
                    jSONObject = new JSONObject(strA);
                } catch (Throwable th) {
                    com.tencent.android.tpush.a.a.i(Constants.PushMessageLogTag, "JSONObject" + th);
                    jSONObject = jSONObject2;
                }
            }
            int iOptInt = jSONObject.optInt("suc_cnt", 0);
            int iOptInt2 = jSONObject.optInt("failed_cnt", 0);
            if (i == 0) {
                i2 = iOptInt2;
                i3 = iOptInt + 1;
            } else {
                i2 = iOptInt2 + 1;
                i3 = iOptInt;
            }
            try {
                jSONObject.put("suc_cnt", i3);
                jSONObject.put("failed_cnt", i2);
            } catch (JSONException e) {
            }
            JSONObject json = xGPushRegisterResult != null ? xGPushRegisterResult.toJson() : new JSONObject();
            try {
                json.put("errorCode", i);
                json.put("np", (int) com.tencent.android.tpush.service.e.m.k(context));
            } catch (Exception e2) {
            }
            try {
                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("details");
                if (jSONArrayOptJSONArray == null) {
                    jSONArrayOptJSONArray = new JSONArray();
                }
                jSONArrayOptJSONArray.put(json);
                jSONObject.put("details", jSONArrayOptJSONArray);
            } catch (Exception e3) {
            }
            com.tencent.android.tpush.a.a.e("SdkStat", "new reprot js" + jSONObject.toString());
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.a == 0) {
                this.a = com.tencent.android.tpush.service.e.h.a(context, "register_last_report", 0L);
                if (this.a == 0) {
                    this.a = jCurrentTimeMillis;
                    com.tencent.android.tpush.service.e.h.b(context, "register_last_report", this.a);
                }
            }
            if (i3 + i2 >= 10 || jCurrentTimeMillis - this.a >= 43200000) {
                com.tencent.android.tpush.service.d.a.a(context, str, jSONObject);
                com.tencent.android.tpush.service.e.h.b(context, "register_json", Constants.MAIN_VERSION_TAG);
                this.a = jCurrentTimeMillis;
                com.tencent.android.tpush.service.e.h.b(context, "register_last_report", this.a);
                return;
            }
            com.tencent.android.tpush.service.e.h.b(context, "register_json", jSONObject.toString());
        }
    }
}
