package com.tencent.android.mzpush;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MZPushMessageReceiver extends MzPushMessageReceiver {
    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onNotificationArrived(Context context, String str, String str2, String str3) {
        Log.i("XG_MZPUSH", "onNotificationArrived : title : " + str + " , content = " + str2 + " , selfDefineContentString = " + str3);
        Intent intent = new Intent(Constants.ACTION_FEEDBACK);
        intent.putExtra(Constants.FEEDBACK_TAG, 5);
        intent.putExtra(Constants.PUSH_CHANNEL, 5);
        intent.putExtra("content", str2);
        intent.putExtra("title", str);
        intent.putExtra("custom_content", str3);
        intent.putExtra("type", 2);
        if (str3 != null) {
            try {
                if (!TextUtils.isEmpty(str3)) {
                    JSONObject jSONObject = new JSONObject(str3);
                    if (!jSONObject.isNull(MessageKey.MSG_ID)) {
                        intent.putExtra(MessageKey.MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_ID)));
                    }
                    if (!jSONObject.isNull(MessageKey.MSG_BUSI_MSG_ID)) {
                        intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_BUSI_MSG_ID)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
        super.onNotificationArrived(context, str, str2, str3);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onNotificationClicked(Context context, String str, String str2, String str3) {
        Log.i("XG_MZPUSH", "onNotificationClicked title " + str + "content " + str2 + " selfDefineContentString " + str3);
        super.onNotificationClicked(context, str, str2, str3);
        Intent intent = new Intent(Constants.ACTION_FEEDBACK);
        intent.putExtra(Constants.FEEDBACK_TAG, 4);
        intent.putExtra(Constants.PUSH_CHANNEL, 5);
        intent.putExtra("content", str2);
        intent.putExtra("title", str);
        intent.putExtra("custom_content", str3);
        intent.putExtra("action", 0);
        intent.putExtra(MessageKey.MSG_CREATE_TIMESTAMPS, System.currentTimeMillis() / 1000);
        intent.putExtra("type", 2);
        if (str3 != null) {
            try {
                if (!TextUtils.isEmpty(str3)) {
                    JSONObject jSONObject = new JSONObject(str3);
                    if (!jSONObject.isNull(MessageKey.MSG_ID)) {
                        intent.putExtra(MessageKey.MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_ID)));
                    }
                    if (!jSONObject.isNull(MessageKey.MSG_BUSI_MSG_ID)) {
                        intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_BUSI_MSG_ID)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onNotificationDeleted(Context context, String str, String str2, String str3) {
        Log.i("XG_MZPUSH", "onNotificationDeleted title " + str + "content " + str2 + " selfDefineContentString " + str3);
        super.onNotificationDeleted(context, str, str2, str3);
        Intent intent = new Intent(Constants.ACTION_FEEDBACK);
        intent.putExtra(Constants.FEEDBACK_TAG, 4);
        intent.putExtra(Constants.PUSH_CHANNEL, 5);
        intent.putExtra("content", str2);
        intent.putExtra("title", str);
        intent.putExtra("custom_content", str3);
        intent.putExtra("action", 2);
        intent.putExtra(MessageKey.MSG_CREATE_TIMESTAMPS, System.currentTimeMillis() / 1000);
        intent.putExtra("type", 2);
        if (str3 != null) {
            try {
                if (!TextUtils.isEmpty(str3)) {
                    JSONObject jSONObject = new JSONObject(str3);
                    if (!jSONObject.isNull(MessageKey.MSG_ID)) {
                        intent.putExtra(MessageKey.MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_ID)));
                    }
                    if (!jSONObject.isNull(MessageKey.MSG_BUSI_MSG_ID)) {
                        intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_BUSI_MSG_ID)));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver, com.meizu.cloud.pushsdk.common.base.WorkReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Log.i("XG_MZPUSH", "onReceive : Intent Package" + intent.getPackage() + "///" + intent.describeContents());
        super.onReceive(context, intent);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onMessage(Context context, String str) {
        Log.i("XG_MZPUSH", "onMessage " + str);
        Intent intent = new Intent(Constants.ACTION_PUSH_MESSAGE);
        intent.putExtra(Constants.PUSH_CHANNEL, 5);
        intent.putExtra("content", str);
        intent.putExtra("custom_content", Constants.MAIN_VERSION_TAG);
        intent.putExtra("type", 1);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        Log.d("XG_MZPUSH", "onPushStatus: arg1 : " + pushSwitchStatus);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    @Deprecated
    public void onRegister(Context context, String str) {
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        Log.i("XG_MZPUSH", "onRegisterStatus " + registerStatus + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + context.getPackageName());
        Log.i("XG_MZPUSH", "onRegister pushID " + registerStatus.getPushId());
        Intent intent = new Intent(Constants.ACTION_FEEDBACK);
        intent.putExtra(Constants.FEEDBACK_ERROR_CODE, registerStatus.code);
        intent.putExtra(Constants.OTHER_PUSH_TOKEN, registerStatus.getPushId());
        intent.putExtra(Constants.FEEDBACK_TAG, 1);
        intent.putExtra(Constants.PUSH_CHANNEL, 5);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        Log.i(MzPushMessageReceiver.TAG, "onSubAliasStatus " + subAliasStatus + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + context.getPackageName());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        Log.i("XG_MZPUSH", "onSubTagsStatus " + subTagsStatus + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + context.getPackageName());
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    @Deprecated
    public void onUnRegister(Context context, boolean z) {
        Log.i("XG_MZPUSH", "onUnRegister " + z);
    }

    @Override // com.meizu.cloud.pushsdk.MzPushMessageReceiver
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        Log.i("XG_MZPUSH", "onUnRegisterStatus " + unRegisterStatus + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + context.getPackageName());
    }
}
