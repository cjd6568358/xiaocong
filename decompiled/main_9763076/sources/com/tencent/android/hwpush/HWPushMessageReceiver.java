package com.tencent.android.hwpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.hms.support.api.push.PushReceiver;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HWPushMessageReceiver extends PushReceiver {
    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onToken(Context context, String str, Bundle bundle) {
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public boolean onPushMsg(Context context, byte[] bArr, Bundle bundle) {
        try {
            String str = new String(bArr, HTTP.UTF_8);
            Log.i(Constants.PUSH_CHANNEL, "PUSH message content :" + str);
            Intent intent = new Intent(Constants.ACTION_PUSH_MESSAGE);
            intent.putExtra(Constants.PUSH_CHANNEL, 4);
            intent.putExtra("content", str);
            intent.putExtra("custom_content", Constants.MAIN_VERSION_TAG);
            intent.putExtra("type", 1);
            intent.setPackage(context.getPackageName());
            context.sendBroadcast(intent);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onEvent(Context context, PushReceiver.Event event, Bundle bundle) {
        int i = 0;
        String string = bundle.getString(PushReceiver.BOUND_KEY.pushMsgKey);
        Log.i(Constants.PUSH_CHANNEL, "onEvent content : " + string);
        Intent intent = new Intent(Constants.ACTION_FEEDBACK);
        intent.putExtra(Constants.FEEDBACK_TAG, 4);
        intent.putExtra(Constants.PUSH_CHANNEL, 4);
        intent.putExtra("custom_content", string);
        intent.putExtra("action", 0);
        intent.putExtra("type", 2);
        if (string != null) {
            try {
                if (!TextUtils.isEmpty(string)) {
                    JSONArray jSONArray = new JSONArray(string);
                    if (jSONArray.length() > 0) {
                        while (true) {
                            int i2 = i;
                            if (i2 >= jSONArray.length()) {
                                break;
                            }
                            JSONObject jSONObject = (JSONObject) jSONArray.opt(i2);
                            if (!jSONObject.isNull(MessageKey.MSG_ID)) {
                                intent.putExtra(MessageKey.MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_ID)));
                            }
                            if (!jSONObject.isNull(MessageKey.MSG_BUSI_MSG_ID)) {
                                intent.putExtra(MessageKey.MSG_BUSI_MSG_ID, Long.valueOf(jSONObject.getString(MessageKey.MSG_BUSI_MSG_ID)));
                            }
                            i = i2 + 1;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra(MessageKey.MSG_CREATE_TIMESTAMPS, System.currentTimeMillis() / 1000);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
        super.onEvent(context, event, bundle);
    }

    @Override // com.huawei.hms.support.api.push.PushReceiver
    public void onPushState(Context context, boolean z) {
    }
}
